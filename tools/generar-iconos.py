"""Genera los iconos del proyecto a partir del pictograma original de ARASAAC.

El logotipo de Pictorario se hizo a mano añadiendo cuatro sectores de color al
pictograma «reloj» de ARASAAC, el 7230. Los ficheros que había en el repositorio
venían de una copia pequeña, así que el de 512 px que pide Google Play sólo
podía salir borroso. Aquí se rehace todo desde el original a 2500 px.

Los sectores no son un ojo nuevo: colores, ángulos y radio están medidos sobre
el `res/drawable/logotipo.png` que ya existía, y el encuadre de cada icono sobre
el icono que sustituye. El dibujo es el mismo; lo único que cambia es de dónde
salen los píxeles.

Uso, desde la raíz del repositorio:

    python3 tools/generar-iconos.py

Los símbolos pictográficos son propiedad del Gobierno de Aragón, creados por
Sergio Palao para ARASAAC, y se distribuyen bajo CC BY-NC-SA.
"""
import os
import urllib.request

from PIL import Image, ImageDraw

SOURCE = "https://static.arasaac.org/pictograms/7230/7230_2500.png"
CACHE = "tools/.arasaac-7230.png"

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

# El fondo del icono adaptativo, res/values/colors.xml. Sólo lo usa el icono de
# la ficha de Play; en la aplicación lo pinta el propio `adaptive-icon`.
BACKGROUND = (0xF0, 0xFF, 0xFF)

DENSITIES = {"mdpi": 1, "hdpi": 1.5, "xhdpi": 2, "xxhdpi": 3, "xxxhdpi": 4}

# Cuánto del lienzo ocupa el reloj en cada icono, medido sobre su trazo negro.
# El del launcher va holgado a propósito: el lienzo de un icono adaptativo es de
# 108 dp y las máscaras del sistema recortan todo lo que salga de los 72 dp
# centrales, así que el dibujo tiene que caber ahí.
FILL_FOREGROUND = 0.535
FILL_LEGACY = 0.865
FILL_STORE = 0.865


def arasaac_clock():
    """El pictograma original, descargado una vez y cacheado."""
    if not os.path.exists(CACHE):
        urllib.request.urlretrieve(SOURCE, CACHE)
    return Image.open(CACHE).convert("RGBA")


def coloured_clock():
    """El pictograma con los cuatro sectores, recortado a su caja."""
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

    merged = Image.alpha_composite(dial, clock)
    return merged.crop(merged.getbbox())


def scaled(clock, side):
    """Reduce el reloj a [side] px sin ensuciarle el borde.

    Se premultiplica el alfa antes de escalar: si no, la interpolación mezcla el
    RGB de los píxeles transparentes —que en este PNG es negro— con el del
    contorno, y el reloj sale con un cerco oscuro contra el fondo claro.
    """
    return clock.convert("RGBa").resize((side, side), Image.LANCZOS).convert("RGBA")


def transparent_icon(clock, size, fill, path):
    """Un icono de la aplicación: el reloj centrado sobre nada."""
    icon = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    side = round(size * fill)
    piece = scaled(clock, side)
    icon.alpha_composite(piece, ((size - side) // 2, (size - side) // 2))
    os.makedirs(os.path.dirname(path), exist_ok=True)
    icon.save(path, "PNG")
    return path


def store_icon(clock, path, size=512):
    """El icono de la ficha de Play: opaco, porque la tienda le pone su máscara."""
    icon = Image.new("RGB", (size, size), BACKGROUND)
    side = round(size * FILL_STORE)
    piece = scaled(clock, side)
    icon.paste(piece, ((size - side) // 2, (size - side) // 2), piece)
    # Play pide «PNG de 32 bits», así que se añade el canal alfa al final y opaco
    # de punta a punta. Componer ya en RGBA no vale: `paste` mezcla también el
    # alfa y el borde suavizado del reloj deja píxeles translúcidos.
    icon.putalpha(255)
    icon.save(path, "PNG")
    return path


clock = coloured_clock()
written = []

for density, scale in DENSITIES.items():
    folder = "app/src/main/res/mipmap-%s" % density
    written.append(transparent_icon(
        clock, round(108 * scale), FILL_FOREGROUND,
        "%s/ic_launcher_foreground.png" % folder))
    written.append(transparent_icon(
        clock, round(48 * scale), FILL_LEGACY,
        "%s/ic_launcher.png" % folder))

# El logotipo de la portada y de «Acerca de». Sube de 300 a 512 px: se dibuja a
# 80 y a 100 dp, que en una pantalla de densidad 4x son 400 px, más de lo que
# tenía el fichero.
written.append(transparent_icon(clock, 512, FILL_LEGACY,
                                "app/src/main/res/drawable/logotipo.png"))

written.append(store_icon(clock, "docs/play/icono-ficha.png"))

for path in written:
    im = Image.open(path)
    print("%-56s %s %s" % (path, "x".join(map(str, im.size)), im.mode))
