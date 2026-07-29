# Material gráfico de la ficha de Play

Capturas tomadas en emulador con la versión de depuración, sobre las secuencias de
ejemplo que trae una instalación limpia. Se regeneran siempre que cambie la interfaz:
las de tablet no existían en la versión B4A, que estaba fijada a vertical y no tenía
disposición propia para pantallas grandes.

| Carpeta | Dispositivo | Resolución |
|---|---|---|
| `telefono/` | AVD `pictorario_test` — Pixel 6, API 36 | 1080 × 2400 |
| `tablet/` | AVD `pictorario_tablet` — Pixel Tablet, API 36 | 1600 × 2560 y 2560 × 1600 |

### Gráfico destacado

`grafico-destacado.png` — 1024 × 500, la cabecera de la ficha. Se compone de tres
capturas de `telefono/` sobre el azul de la aplicación. Para rehacerlo tras cambiar la
interfaz, desde la raíz del repositorio:

```bash
python3 docs/play/grafico-destacado.py
```

Necesita Pillow y las fuentes Roboto del sistema. Play recorta este gráfico por los lados
en algunas presentaciones de la tienda, así que **nada esencial debe quedar pegado a los
bordes**; el título y los reclamos están dentro del tercio central por eso.

### Icono de la ficha

`icono-ficha.png` — 512 × 512, PNG de 32 bits opaco. Lo genera
[`tools/generar-iconos.py`](../../tools/generar-iconos.py) junto con los iconos de la
aplicación, para que todos salgan del mismo dibujo:

```bash
python3 tools/generar-iconos.py
```

### Capturas

Cómo se regeneran, con la aplicación recién instalada y la configuración reiniciada:

```bash
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0        # 1 para girar
adb exec-out screencap -p > docs/play/telefono/1-portada.png
```

El aviso de actividad no hay que esperarlo: se dispara con el intent de la alarma, como
explica AGENTS.md.

## Qué falta antes de subir la ficha

Esto **no está hecho**; lo decide el autor:

- **Texto breve** (80 caracteres) y **descripción completa** (4000), revisados para
  mencionar lo que trae esta versión: lectura en voz alta, copia de seguridad, y
  disposición para tablet. El texto de *Novedades* ya está escrito: es el mismo
  `VERSION_CHANGES` que la aplicación muestra al arrancar, y cabe en los 500
  caracteres que admite Play.
- Play acepta hasta 8 capturas por tipo de dispositivo y pide juegos separados para
  teléfono, tablet de 7" y tablet de 10". Las de `tablet/` sirven para ambos tamaños de
  tablet, pero conviene comprobar los requisitos vigentes en la Console al subirlas.
