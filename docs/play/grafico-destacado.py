"""Compone el gráfico destacado de la ficha de Play, 1024x500."""
from PIL import Image, ImageDraw, ImageFilter, ImageFont

W, H = 1024, 500
BRAND = (21, 101, 192)      # el primary de la app, 0xFF1565C0
BRAND_DARK = (10, 62, 124)

FONTS = "/usr/share/fonts/truetype/roboto/unhinted/RobotoTTF/"
bold = lambda s: ImageFont.truetype(FONTS + "Roboto-Bold.ttf", s)
regular = lambda s: ImageFont.truetype(FONTS + "Roboto-Regular.ttf", s)

# --- fondo: degradado diagonal suave -------------------------------------
bg = Image.new("RGB", (W, H), BRAND)
grad = Image.new("L", (W, H))
gd = ImageDraw.Draw(grad)
for x in range(W):
    gd.line([(x, 0), (x, H)], fill=int(255 * (x / W) ** 0.9))
bg = Image.composite(Image.new("RGB", (W, H), BRAND_DARK), bg, grad)

# --- capturas: recortadas de barras y con esquinas redondeadas -----------
def phone(path, height):
    im = Image.open(path).convert("RGB")
    # Fuera la barra de estado y la de navegación: en el gráfico sobran.
    im = im.crop((0, 70, im.width, im.height - 60))
    w = round(im.width * height / im.height)
    im = im.resize((w, height), Image.LANCZOS)

    radius = 26
    mask = Image.new("L", im.size, 0)
    ImageDraw.Draw(mask).rounded_rectangle([0, 0, w - 1, height - 1], radius, fill=255)

    # Marco blanco fino, para que la captura no se funda con el fondo azul.
    frame = 4
    card = Image.new("RGB", (w + frame * 2, height + frame * 2), (255, 255, 255))
    cmask = Image.new("L", card.size, 0)
    ImageDraw.Draw(cmask).rounded_rectangle(
        [0, 0, card.width - 1, card.height - 1], radius + frame, fill=255
    )
    card.paste(im, (frame, frame), mask)
    return card, cmask


def paste_with_shadow(base, card, mask, xy):
    shadow = Image.new("RGBA", base.size, (0, 0, 0, 0))
    sm = Image.new("L", base.size, 0)
    sm.paste(mask, (xy[0] + 6, xy[1] + 10))
    shadow.putalpha(sm.filter(ImageFilter.GaussianBlur(12)))
    base.paste(Image.new("RGB", base.size, (0, 20, 50)), (0, 0), shadow.split()[3])
    base.paste(card, xy, mask)


# Tres pantallas que se distinguen entre sí de un vistazo: una lista, un reloj
# y una cuadrícula. El aviso de alarma queda fuera porque su captura lleva el
# fondo atenuado del diálogo y en miniatura sólo se lee como una pantalla gris,
# y los dos relojes juntos se veían como la misma imagen repetida.
SHOTS = [
    "docs/play/telefono/1-portada.png",
    "docs/play/telefono/2-reloj-secuencia.png",
    "docs/play/telefono/6-selector.png",
]

height = 408
cards = [phone(p, height) for p in SHOTS]
step = 148
top = (H - (height + 8)) // 2
# De atrás hacia delante, para que cada una solape a la anterior.
x = W - 40 - cards[-1][0].width
positions = [x - step * (len(cards) - 1 - i) for i in range(len(cards))]
for (card, mask), px in zip(cards, positions):
    paste_with_shadow(bg, card, mask, (px, top))

# --- lado izquierdo: identidad -------------------------------------------
d = ImageDraw.Draw(bg)

logo = Image.open("app/src/main/res/drawable/logotipo.png").convert("RGBA")
logo = logo.resize((92, 92), Image.LANCZOS)
bg.paste(logo, (52, 74), logo)

d.text((158, 84), "Pictorario", font=bold(50), fill=(255, 255, 255))
d.text((160, 142), "Horarios con pictogramas", font=regular(23), fill=(200, 223, 250))

CLAIMS = [
    "Cada actividad, a su hora y con su dibujo",
    "Aviso a pantalla completa cuando toca",
    "Lectura en voz alta para quien no lee",
]
y = 244
for line in CLAIMS:
    d.ellipse([56, y + 8, 67, y + 19], fill=(255, 255, 255))
    d.text((82, y), line, font=regular(21), fill=(233, 241, 252))
    y += 42

bg.save("docs/play/grafico-destacado.png", "PNG")
print("docs/play/grafico-destacado.png", bg.size, bg.mode)
