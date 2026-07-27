**Pictorario** es una aplicación móvil para crear y visualizar horarios con pictogramas, especialmente diseñada para facilitar la gestión de su agenda a niños con Trastorno del Espectro Autista, pero también para todos los niños en general que puedan requerir un apoyo gráfico para visualizar fácilmente sus tareas.

Esta aplicación es completamente gratuita, y ha sido creada por su autor como Trabajo Fin de Grado de sus estudios universitarios, estando disponible el código fuente para cualquiera que lo desee.

Los símbolos pictográficos utilizados son propiedad del Gobierno de Aragón y han sido creados por Sergio Palao para ARASAAC (http://arasaac.org) que los distribuye bajo licencia Creative Commons (BY-NC-SA).

[Presentación en Youtube](https://youtu.be/cjTAGguz5H0?si=9tW0Vji1QCRNCyKs)

# Versión 2.0: reescritura en Kotlin

**Migración en curso.** La versión 1.x estaba escrita en [B4A (Basic4Android)](https://www.b4x.com/b4a.html) y se conserva íntegra en la carpeta [`b4a/`](b4a/). La versión 2.0 es una reescritura completa en **Kotlin nativo con Jetpack Compose**, manteniendo la misma interfaz y funcionalidad.

Los motivos de la migración:

- El `targetSdk 29` de la versión B4A ya no permite publicar actualizaciones en Google Play.
- El IDE de B4A es propietario y sólo funciona en Windows.
- Desde Android 10, el sistema bloquea el arranque de una *Activity* desde un servicio en segundo plano, que es justo el mecanismo con el que la versión antigua avisaba de una actividad.

El plan de trabajo completo, fase a fase, está en [MIGRACION.md](MIGRACION.md).

> **Aviso para quien actualice desde la versión 1.x:** la configuración se reinicia. Los datos antiguos se guardaban en un formato propio de B4A que no se migra, así que la aplicación arranca con las secuencias de ejemplo.

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

El AAB sólo sale firmado si existe un fichero `keystore.properties` en la raíz (no versionado) con las claves `storeFile`, `storePassword`, `keyAlias` y `keyPassword`. Sin él, el release se compila igualmente pero sin firmar.

## Preparar un nuevo release

Antes de generar una versión para publicar, editar **`app/build.gradle.kts`** y actualizar estos dos campos dentro de `defaultConfig`:

| Campo         | Tipo   | Descripción                                                                 |
|---------------|--------|-----------------------------------------------------------------------------|
| `versionCode` | Entero | Código interno incremental. Google Play exige que sea **estrictamente mayor** que el de la versión anterior. |
| `versionName` | String | Nombre visible para el usuario (p. ej. `"2.1"`). No afecta a la lógica de actualización, pero es lo que se muestra en la ficha de la Play Store. |

# Histórico

La carpeta [`b4a/`](b4a/) contiene el proyecto original en B4A, congelado tal como quedó en la versión 1.07. No se compila y se conserva únicamente como referencia durante la migración y como memoria del proyecto. Su documentación original está en [`b4a/README.md`](b4a/README.md), e incluye el enlace a la memoria del Trabajo Fin de Grado presentada a la Universidad Internacional de La Rioja.
