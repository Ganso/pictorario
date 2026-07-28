# Guía para agentes

Todo lo que hace falta saber para trabajar en Pictorario sin tener que reconstruirlo leyendo el código. Si algo de aquí contradice al código, gana el código y **hay que actualizar este fichero**.

---

## Qué es esto

Aplicación Android que muestra horarios con pictogramas de ARASAAC, pensada para niños con Trastorno del Espectro Autista. Un adulto configura las secuencias de actividades; el niño ve un reloj con sectores de colores y pictogramas que le dice qué toca ahora.

- **Autor**: Javier Prieto Martínez. Software libre, repo `Ganso/pictorario`.
- **Publicada en Google Play** como `javi.prieto.pictorario`.
- La versión 1.x estaba escrita en **B4A (Basic4Android)** y se conserva congelada en [`b4a/`](b4a/). La 2.0 es una reescritura en Kotlin + Compose.
- La ficha **fue retirada de Play** por no actualizar el nivel de API. Eso condiciona todas las decisiones de permisos: ver [PUBLICACION.md](PUBLICACION.md).

## Documentos del proyecto

| Fichero | Para qué |
|---|---|
| [README.md](README.md) | Presentación, cómo compilar, ideas para más adelante |
| [MIGRACION.md](MIGRACION.md) | **Por qué la aplicación es como es.** Decisiones, hallazgos al portar cada pantalla, y la tabla de correcciones respecto al original |
| [PUBLICACION.md](PUBLICACION.md) | Firma, permisos, política de Familias, notas de versión, checklist de envío |
| [PRUEBAS.md](PRUEBAS.md) | Batería de pruebas manual, para móvil real |
| `docs/pictorarioprivacy.html` | Política de privacidad, se publica en ganso.org |
| `docs/referencia-b4a/` | Capturas de la app original corriendo, para comparar |
| `docs/comparativas/` | Comparativas original/nuevo de la migración del reloj |

**Antes de tocar el reloj, el editor o las alarmas, lee la sección correspondiente de MIGRACION.md.** Contiene hallazgos que costaron caro y que no se deducen del código.

---

## Convenciones

- **Código y comentarios en inglés. Textos de usuario en español**, con acentuación correcta. Los comentarios explican *por qué*, no *qué*.
- Sin `TODO` ni comentarios que describan lo obvio.
- Documentos y mensajes de commit **en español**.
- El proyecto hermano `~/codigo/colorear` es la referencia de convenciones de build.

## Compilar y probar

```bash
./gradlew test                 # 63 tests JVM, sin Android
./gradlew assembleDebug
./build_and_copy.sh            # compila, arranca un emulador si hace falta, instala y abre
./build_and_copy.sh --dry-run  # sólo compilar
./build_and_copy.sh --headless # emulador sin ventana
```

- El AVD por defecto es `pictorario_test` (Pixel 6, API 36). El SDK está en `~/Android/Sdk`.
- **El APK de depuración se instala como `javi.prieto.pictorario.debug`**, con sufijo `-debug` en el `versionName`, para poder convivir con la versión publicada.
- Emulador a mano: `~/Android/Sdk/emulator/emulator -avd pictorario_test -no-audio -no-boot-anim -gpu host`.

### Probar en el emulador

El campo de texto de Compose **no acepta texto inyectado** con `adb shell input text` ni con `keyevent` en este emulador: recibe el foco pero queda vacío. Para todo lo demás, `adb` va bien:

```bash
adb shell uiautomator dump /sdcard/u.xml && adb shell cat /sdcard/u.xml   # árbol de la interfaz
adb exec-out screencap -p > /tmp/x.png
adb shell input swipe X Y X Y 1500                                        # pulsación larga
```

Para preparar un estado concreto, lo más rápido es escribir el documento persistido directamente:

```bash
adb push datos.json /data/local/tmp/d.json
adb shell "run-as javi.prieto.pictorario.debug cp /data/local/tmp/d.json files/pictorario.json"
```

**Con la app parada**, o al arrancar reescribirá el fichero desde su copia en memoria y perderás el cambio.

Los avisos superpuestos (`Notice`) duran ~2,2 s: hay que capturar **durante** la acción, no después, o parecerá que no salen.

---

## Arquitectura

Una sola `Activity`, sin Navigation-Compose: son seis pantallas y no hay enlaces profundos.

```
MainActivity.kt        Única Activity. Gestiona el intent de la alarma
PictorarioApp.kt       Application: contenedor único de DataStore y repositorio
domain/                KOTLIN PURO, cero imports de Android → testeable en JVM
  Model.kt             Activity, Board, Sequence, Settings, AppData (@Serializable)
  ClockGeometry.kt     hora→ángulo, sectores, posición de botones, hit-test
  ActivityRules.kt     ordenación, solapes, alta de actividad, changeTime
  AlarmCalculator.kt   próxima alarma (recibe el "ahora" por parámetro)
  TimeFormat.kt · Palette.kt · SampleData.kt
data/
  AppDataStore.kt      DataStore<AppData> con Serializer JSON propio
  PictogramRepository.kt  ficheros en filesDir/pictogramas, siembra desde assets
  ArasaacApi.kt        HttpURLConnection: búsqueda y descarga concurrente
alarm/
  AlarmScheduler.kt · AlarmReceiver.kt · BootReceiver.kt · Notifications.kt
ui/
  PictorarioState.kt   Estado global. Sustituye al singleton Starter.bas de B4A
  AppRoot.kt           sealed interface Screen + host de pantallas
  home/ clock/ editor/ picker/ settings/ about/ common/ theme/
```

### Reglas de arquitectura

1. **`domain/` no importa nada de Android.** Ni `Offset`, ni `Context`, ni `Color`. Tiene un `Point` propio por eso. Toda la lógica verificable vive aquí y se prueba en JVM.
2. **`PictorarioState` es el único estado mutable.** Se crea en `MainActivity` y se pasa a las pantallas. No hay ViewModel por pantalla.
3. **`DataStore` y `PictogramRepository` son singletons de proceso**, en `PictorarioApp`. Ver la trampa correspondiente más abajo.
4. Toda escritura reprograma la alarma automáticamente, vía `onDataChanged`.

### Persistencia

Un único documento JSON en `filesDir/pictorario.json`, vía DataStore con serializador propio. Legible con `adb`, que es deliberado: facilita el soporte. Los pictogramas son ficheros `filesDir/pictogramas/<idArasaac>.png`; el id de ARASAAC es la clave en todo el sistema.

**No se migran los datos de la versión B4A.** Al actualizar desde la 1.x la configuración se reinicia.

---

## Trampas conocidas

Cada una costó una regresión. No las repitas.

### `TooltipBox` no propaga el `weight` de un `Row`

`Help` envuelve en `TooltipBox`. Si le pasas un `Modifier.weight(1f)` y dentro pones `fillMaxWidth()`, **el hijo se mide contra la fila entera** y expulsa a sus hermanos fuera de pantalla. Se llevó por delante el botón «Hasta» del editor y el de cambiar vista del reloj.

Dentro de un `Row`: pon el peso en un `Box` normal y el `Help` dentro.

### La ayuda va sobre el control, no sobre la etiqueta

`Help` colgado del texto de una fila no aparece nunca: el dedo aterriza en la casilla o el botón. Envuelve la fila entera.

### DataStore no admite dos instancias sobre el mismo fichero

El receptor de alarma y la interfaz leen el mismo documento. Crear un `DataStore` en cada sitio lanza `IllegalStateException` y **mata la aplicación en cuanto salta una alarma**. Por eso está en `PictorarioApp` con ámbito de proceso — que además hace que sobreviva a que se recree la Activity.

### La alarma no debe deducir qué actividad le toca

`AlarmReceiver` recibe los índices de secuencia y actividad **dentro del intent**, decididos al armarla. Deducirlos del reloj al dispararse hacía que un retraso de un solo minuto concluyera que la actividad era la de mañana: no mostraba nada y sólo reprogramaba.

### Alarmas sin permiso: `setAndAllowWhileIdle`, nunca `setWindow`

`setWindow` no está exento del modo de reposo y puede quedarse esperando horas, justo cuando más falta hace. Con permiso se usa `setAlarmClock`.

### La herradura del tablero de secuencia completa

El recorte de `Visualizacion.bas:132,143` **no es un bug**. El vértice de cierre escala la X por `3·R` y la Y por `R`; leerlo como un punto polar deforma visiblemente la punta derecha. Está reproducido vértice a vértice en `ClockBoard.notchPath`. No lo «simplifiques» a un `drawArc`.

### El reloj se mide contra el ANCHO

Centro en `(50%W, 60%W)` y radio `45%W`. Tomarlos del alto deforma la esfera en pantallas altas.

---

## Alarmas: cómo funciona el conjunto

1. Cualquier escritura del documento dispara `AlarmScheduler.reschedule`.
2. Se arma **una sola** alarma, la más próxima, con los índices en el intent.
3. Se publica la notificación persistente id=1 («Próxima actividad»).
4. Al saltar, `AlarmReceiver` muestra la notificación id=2 —con intent a pantalla completa y `FLAG_INSISTENT`— y rearma la siguiente.
5. `MainActivity` recibe el intent, retira la notificación, quita el bloqueo de pantalla y muestra `AlarmDialog` sobre el tablero.
6. `BootReceiver` rearma tras `BOOT_COMPLETED`, `MY_PACKAGE_REPLACED`, `TIME_SET` y `TIMEZONE_CHANGED`.

---

## Permisos: no añadir a la ligera

La ficha fue retirada una vez. El criterio es **pedir lo mínimo**. Los declarados son:

`INTERNET` · `VIBRATE` · `RECEIVE_BOOT_COMPLETED` · `POST_NOTIFICATIONS` · `SCHEDULE_EXACT_ALARM` · `USE_FULL_SCREEN_INTENT`

- **`USE_FULL_SCREEN_INTENT` está sujeto a revisión de Play.** Se declara porque sin él el aviso no cumple su función para un niño que no lee; la justificación está redactada en PUBLICACION.md. Se probó primero sin él y no servía.
- **`USE_EXACT_ALARM` NO se declara.** La puntualidad exacta se pide al usuario con `SCHEDULE_EXACT_ALARM`, que concede desde los ajustes.
- Añadir cualquier permiso nuevo exige justificarlo en PUBLICACION.md.

## Firma

- Clave de subida: `app/upload-keystore-rsa.jks`, RSA 4096, alias `upload`. Credenciales en `keystore.properties` (600, ignorado por git). Copia en `~/copias-seguridad-claves/`.
- SHA-1 registrada en Play: `63:49:AF:0F:07:7F:52:EE:7A:20:3D:65:9B:3D:18:39:64:77:15:80`.
- La firma de aplicación es de Google (Play App Signing).
- **Nunca versionar** `*.jks`, `*.keystore`, `*.pem`, `keystore.properties`. El `.gitignore` ya lo cubre.

## Cadena de herramientas

AGP 8.10.1 · Kotlin 2.1.20 · Gradle 8.11.1 · JVM 21 · compileSdk y targetSdk 36 · minSdk 26.

Compose BOM 2025.12.01, activity-compose 1.11.0, core-ktx 1.17.0, lifecycle-runtime-compose 2.9.4, datastore-core 1.1.7, kotlinx-serialization-json 1.9.0, JUnit 4.

**Las versiones están fijadas a las últimas compatibles con AGP 8.10.1.** Las más recientes de AndroidX exigen AGP 9.1 y compileSdk 37; actualizar es una tarea aparte, anotada en el README.

Dependencias que **no** se usan a propósito, para mantener el proyecto ligero: Navigation-Compose, Retrofit/OkHttp, Coil/Glide, Room, WorkManager, Hilt/Koin. Si crees que hace falta alguna, revisa primero por qué se descartó en MIGRACION.md.

## API de ARASAAC

Sin autenticación ni rate limiting conocido.

- Búsqueda: `GET https://api.arasaac.org/api/pictograms/es/search/<texto>` → array JSON, campo `_id` (entero).
- Imagen: `GET https://static.arasaac.org/pictograms/<id>/<id>_500.png`.

El idioma está fijado a `es`. Las descargas van en paralelo con `Semaphore(6)`.

## Tests

8 clases, 63 tests, JUnit 4 plano, sin mocks ni Robolectric. Todo en `app/src/test/java/es/pictorario/app/domain/` salvo `ArasaacResponseTest`, que valida el parseo contra una respuesta real recortada como fixture — **sin red**, para que la suite sea determinista.

Nombres de método como frase en inglés. `AlarmCalculator` recibe el «ahora» por parámetro precisamente para poder probarlo.

**Lo que toques del dominio, cúbrelo con tests.** Lo que sea de interfaz, verifícalo en el emulador y adjunta captura.

---

## Antes de dar algo por bueno

1. `./gradlew assembleDebug assembleRelease test` en verde. El release importa: **R8 sólo actúa ahí**.
2. Si tocaste interfaz, **repasa las seis pantallas**, no sólo la que cambiaste. Tres regresiones se colaron por comprobar sólo una.
3. Si tocaste permisos, verifica el artefacto real:
   `aapt2 dump permissions app/build/outputs/apk/release/app-release.apk`
4. Anota en MIGRACION.md lo que hayas descubierto y actualiza este fichero si cambian las reglas.
