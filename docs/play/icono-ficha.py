"""Compone el icono de la ficha de Play, 512x512.

Reconstruye el logotipo del proyecto igual que se hizo a mano en su día: el
pictograma «reloj» de ARASAAC —el 7230— con cuatro sectores de color añadidos
bajo la esfera. Partir del original a 2500 px y colorear ahí evita reescalar el
icono de 192 px del launcher, que a 512 saldría borroso.

Los sectores no salen de un ojo nuevo: colores, ángulos y radio están medidos
sobre `res/drawable/logotipo.png`, para que el icono de la ficha y el de la
aplicación sean el mismo dibujo.

Los símbolos pictográficos son propiedad del Gobierno de Aragón, creados por
Sergio Palao para ARASAAC, y se distribuyen bajo CC BY-NC-SA.
"""
import os
import urllib.request

from PIL import Image, ImageDraw

SOURCE = "https://static.arasaac.org/pictograms/7230/7230_2500.png"
CACHE = "docs/play/.arasaac-7230.png"

# Medido sobre el pictograma original: la esfera es concéntrica con el lienzo y
# el aro negro empieza en 0.402 del ancho.
DIAL_RADIUS = 0.402

# Medido sobre logotipo.png: cuatro cuadrantes girados 21° respecto a las doce.
SECTOR_START = -21
SECTORS = [
    (0xFE, 0x60, 0x60),   # rojo
    (0xA4, 0xF5, 0xA6),   # verde
    (0x99, 0xF4, 0xF5),   # cian
    (0xFE, 0xF9, 0xA0),   # amarillo
]

# El fondo del icono adaptativo, res/values/colors.xml.
BACKGROUND = (0xF0, 0xFF, 0xFF)

# Lo que ocupa el reloj dentro del icono del launcher. Medido sobre el trazo
# negro y no sobre la caja opaca: el icono de la aplicación lleva un halo gris
# alrededor que, contado, dejaba este reloj un 3 % más grande que aquél.
FILL = 0.865

SIZE = 512
OUT = "docs/play/icono-ficha.png"


def arasaac_clock():
    """El pictograma original, descargado una vez y cacheado."""
    if not os.path.exists(CACHE):
        urllib.request.urlretrieve(SOURCE, CACHE)
    return Image.open(CACHE).convert("RGBA")


def coloured_clock():
    clock = arasaac_clock()
    w, h = clock.size
    cx, cy = w / 2, h / 2
    r = w * DIAL_RADIUS

    # Los sectores van *debajo* del pictograma, de modo que el aro, las marcas,
    # los números y las agujas queden por encima y sigan negros.
    dial = Image.new("RGBA", clock.size, (0, 0, 0, 0))
    d = ImageDraw.Draw(dial)
    for i, colour in enumerate(SECTORS):
        # pieslice mide desde las tres en punto; las doce están 90° antes.
        start = SECTOR_START - 90 + i * 90
        d.pieslice([cx - r, cy - r, cx + r, cy + r], start, start + 90,
                   fill=colour + (255,))

    return Image.alpha_composite(dial, clock)


icon = Image.new("RGB", (SIZE, SIZE), BACKGROUND)
clock = coloured_clock()

# El dibujo no llega al borde de su lienzo: se recorta a su caja antes de
# escalar, o el reloj saldría más pequeño de lo previsto.
clock = clock.crop(clock.getbbox())
side = round(SIZE * FILL)
clock = clock.resize((side, side), Image.LANCZOS)
icon.paste(clock, ((SIZE - side) // 2, (SIZE - side) // 2), clock)

# Play pide «PNG de 32 bits», así que se añade el canal alfa al final y opaco de
# punta a punta. Componer ya en RGBA no valía: `paste` mezcla también el alfa, y
# el borde suavizado del reloj dejaba píxeles translúcidos. La transparencia de
# verdad está desaconsejada de todos modos, porque la tienda recorta el icono
# con su propia máscara y el hueco se vería.
icon.putalpha(255)
icon.save(OUT, "PNG")
print(OUT, icon.size, icon.mode)
