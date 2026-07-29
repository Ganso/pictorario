**Pictorario** es una aplicación móvil para crear y visualizar horarios con pictogramas, especialmente diseñada para facilitar la gestión de su agenda a niños con Trastorno del Espectro Autista, pero también para todos los niños en general que puedan requerir un apoyo gráfico para visualizar fácilmente sus tareas.

Esta aplicación es completamente gratuita, y ha sido creada por su autor como Trabajo Fin de Grado de sus estudios universitarios, estando disponible el código fuente para cualquiera que lo desee.

Los símbolos pictográficos utilizados son propiedad del Gobierno de Aragón y han sido creados por Sergio Palao para ARASAAC (http://arasaac.org) que los distribuye bajo licencia Creative Commons (BY-NC-SA).

[Presentación en Youtube](https://www.youtube.com/watch?v=cjTAGguz5H0)

# Versión 2.0: reescritura en Kotlin

La versión 1.x estaba escrita en [B4A (Basic4Android)](https://www.b4x.com/b4a.html) y se conserva íntegra en la carpeta [`b4a/`](b4a/). La versión 2.0 es una reescritura completa en **Kotlin nativo con Jetpack Compose**, manteniendo la misma interfaz y funcionalidad.

Los motivos de la migración:

- El `targetSdk 29` de la versión B4A ya no permite publicar actualizaciones en Google Play.
- El IDE de B4A es propietario y sólo funciona en Windows.
- Desde Android 10, el sistema bloquea el arranque de una *Activity* desde un servicio en segundo plano, que es justo el mecanismo con el que la versión antigua avisaba de una actividad.

Lo que trae la versión, contado de cara al usuario, está en [CHANGELOG.md](CHANGELOG.md). El registro de cómo se hizo, qué se decidió y qué se corrigió respecto al original está en [MIGRACION.md](MIGRACION.md).

Si vas a trabajar en el proyecto —o si eres una IA a la que le han pedido que lo haga—, empieza por [AGENTS.md](AGENTS.md): resume la arquitectura, las convenciones, los comandos y las trampas conocidas sin necesidad de leer el código.

> **Aviso para quien actualice desde la versión 1.x:** la configuración se reinicia. Los datos antiguos se guardaban en un formato propio de B4A que no se migra, así que la aplicación arranca con las secuencias de ejemplo.

## Publicación

La ficha de Pictorario fue **retirada de Google Play por incumplimiento de políticas**, al no haberse actualizado al nivel de API exigido. La 2.0 vuelve a cumplir y el criterio del proyecto es pedir lo mínimo imprescindible.

Declara seis permisos: internet para descargar pictogramas, vibración, reprogramar la alarma tras reiniciar, notificaciones, alarmas exactas (opcional, lo concede el usuario) y apertura a pantalla completa para el aviso de actividad. Este último está sujeto a revisión de Google Play y su justificación está redactada en [PUBLICACION.md](PUBLICACION.md); se declara porque sin él el aviso no cumple su función para un niño que no lee. **No** se declara `USE_EXACT_ALARM`.

**Los datos no salen del dispositivo.** No hay cuentas, publicidad, analítica ni rastreo. Lo único que se envía a un tercero es el texto que el adulto teclea al buscar un pictograma, que va a ARASAAC sin identificador alguno.

Antes de subir cualquier versión hay que seguir [PUBLICACION.md](PUBLICACION.md), y pasar la batería de pruebas de [PRUEBAS.md](PRUEBAS.md) en un móvil real.

La política de privacidad está en [`docs/pictorarioprivacy.html`](docs/pictorarioprivacy.html) y se publica en <https://ganso.org/pictorarioprivacy.html>.

## Compilar

1. Abrir este directorio con Android Studio y esperar a que termine la sincronización de Gradle.
2. Ejecutar la configuración `app` en un dispositivo o emulador.

Desde la línea de comandos:

```bash
./gradlew assembleDebug
```

Para generar a la vez el APK de depuración y el AAB de publicación:

```bash
./build_and_copy.sh
```

Además de compilar, el script detecta si hay un emulador disponible: usa el que esté corriendo o arranca uno, instala el APK de depuración y abre la aplicación. Para sólo compilar, sin tocar ningún emulador:

```bash
./build_and_copy.sh --dry-run
```

Acepta también `--headless`, para arrancar el emulador sin ventana, y `--avd NOMBRE` para elegir otro distinto de `pictorario_test`.

El AAB sólo sale firmado si existe un fichero `keystore.properties` en la raíz (no versionado) con las claves `storeFile`, `storePassword`, `keyAlias` y `keyPassword`. Sin él, el release se compila igualmente pero sin firmar.

## Preparar un nuevo release

Antes de generar una versión para publicar, editar **`app/build.gradle.kts`** y actualizar estos dos campos dentro de `defaultConfig`:

| Campo         | Tipo   | Descripción                                                                 |
|---------------|--------|-----------------------------------------------------------------------------|
| `versionCode` | Entero | Código interno incremental. Google Play exige que sea **estrictamente mayor** que el de la versión anterior. |
| `versionName` | String | Nombre visible para el usuario (p. ej. `"2.1"`). No afecta a la lógica de actualización, pero es lo que se muestra en la ficha de la Play Store. |

# Histórico

La carpeta [`b4a/`](b4a/) contiene el proyecto original en B4A, congelado tal como quedó en la versión 1.07. No se compila y se conserva únicamente como referencia durante la migración y como memoria del proyecto. Su documentación original está en [`b4a/README.md`](b4a/README.md), e incluye el enlace a la memoria del Trabajo Fin de Grado presentada a la Universidad Internacional de La Rioja.

# Ideas para más adelante

Lista de mejoras que se fueron apartando durante la reescritura. Lo marcado entró finalmente en la 2.0 y está contado en [CHANGELOG.md](CHANGELOG.md); el resto se recoge para no perderlo.

**Accesibilidad y comunicación**
- [x] Lectura en voz alta de la actividad con TTS — opcional, en Configuración
- [ ] Repasar etiquetas de TalkBack, tamaños táctiles y escalado de fuente del sistema
- [ ] Modo alto contraste y tema oscuro
- [ ] Localizar la interfaz a otros idiomas (hoy todo el texto está en castellano)
- [ ] Búsqueda de pictogramas en otros idiomas (la API lo soporta; el código tiene `es` fijo)

**Funcionalidad**
- [ ] Secuencias por día de la semana (hoy una secuencia no distingue días)
- [ ] Marcar actividades como completadas, con refuerzo visual
- [ ] Temporizador o cuenta atrás visual de la actividad en curso
- [ ] Programar varias alarmas a la vez (hoy sólo se programa la más próxima)
- [x] Actividades que terminan a las 24:00 (elegir las 12 de la noche como hora final)
- [ ] Actividades que **cruzan** la medianoche, de un día al siguiente
- [ ] Usar fotos propias como pictogramas (cámara y galería)
- [x] Exportar e importar secuencias, para compartirlas entre cuidadores o dispositivos
- [x] Copia de seguridad y restauración — a un fichero JSON, desde Configuración
- [ ] Widget de pantalla de inicio con la actividad actual
- [ ] Elevar los límites de 10 secuencias y 20 actividades (el de 20 exige ampliar la paleta de colores)

**Presentación**
- [x] Disposición específica para tablet y para horizontal
- [ ] Estilos alternativos de esfera de reloj
- [x] Usar los pictogramas `_2500` en pantallas grandes

**Proyecto**
- [ ] Actualizar a AGP 9.1 y compileSdk 37 para poder usar las últimas versiones de AndroidX
- [ ] CI en GitHub Actions que compile el AAB y ejecute los tests
- [x] Capturas nuevas para la ficha de Play — en [`docs/play/`](docs/play/)
- [x] Gráfico destacado de la ficha — [`docs/play/grafico-destacado.png`](docs/play/grafico-destacado.png)
- [x] Icono de 512 px de la ficha — [`docs/play/icono-ficha.png`](docs/play/icono-ficha.png)
- [ ] Texto breve y descripción completa de la ficha
