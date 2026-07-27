# Migración de Pictorario: B4A → Kotlin + Jetpack Compose

> **Plan de trabajo aprobado el 27/07/2026. Pendiente de ejecutar.**
> Documento vivo: al completar cada fase, marcarla aquí. La sección «Mejoras futuras» se trasladará al README en la fase 9.

## Contexto

`~/codigo/pictorario` es **Pictorario**, una app Android de horarios visuales con pictogramas ARASAAC para niños con TEA (TFG de Javier Prieto, publicada en Play como `javi.prieto.pictorario`, versionCode 107). Está escrita en **B4A (Basic4Android)** con `targetSdk 29` y `minSdk 5`: Google Play ya no admite actualizaciones con ese targetSdk, el IDE es propietario y sólo funciona en Windows, y varias APIs que usa están rotas o restringidas en Android moderno — en particular el arranque de una Activity desde un Service en background, que **el sistema bloquea silenciosamente desde Android 10** y que es justo el mecanismo con el que la app avisa de una actividad.

El objetivo es **reescribirla en Kotlin nativo con Jetpack Compose**, conservando interfaz y funcionalidad, tomando como referencia de convenciones el proyecto hermano `~/codigo/colorear` (Gradle KTS, AGP 8.10.1 / Kotlin 2.1.20 / JVM 21, firma vía `keystore.properties` con guarda, script `build_and_copy.sh`, lógica en paquetes Kotlin puros testeables en JVM, código en inglés y textos de usuario en español).

**Decisiones tomadas:**

| Decisión | Elección |
|---|---|
| Ubicación | `~/codigo/pictorario` (repo `Ganso/pictorario`), B4A archivado en `b4a/` |
| UI | Jetpack Compose + `Canvas` para el reloj |
| Datos antiguos | **No se migran**: arranque limpio con las 3 secuencias de ejemplo |
| `applicationId` | `javi.prieto.pictorario` (actualización de la ficha existente) |
| `namespace` | `es.pictorario.app` |
| `minSdk` | **26** (Android 8) |
| Bugs heredados | Se corrigen y se documentan |
| Agujas del reloj | Se corrigen a circulares |
| Botón Atrás | Se anula **sólo** con el bloqueo parental activo |

---

## Reorganización del repositorio

Todo el B4A actual (que está versionado en la raíz) se mueve con **`git mv`** para preservar el historial:

```
pictorario/                        (repo Ganso/pictorario, rama master)
├── README.md                      # REESCRITO: proyecto Kotlin + sección de histórico
├── .gitignore                     # NUEVO (hoy no existe): build/, *.apk, *.aab, *.jks, keystore.properties, .gradle/, .idea/, local.properties
├── b4a/                           # HISTÓRICO — el proyecto B4A íntegro, sin tocar
│   ├── README.md                  # el README actual, como memoria del proyecto original
│   ├── pictorario.b4a · *.bas · Files/ · Objects/
├── build.gradle.kts · settings.gradle.kts · gradle.properties · gradlew + wrapper 8.11.1
├── build_and_copy.sh · keystore.properties (no versionado)
└── app/…
```

Detalles:

- **`b4a/Objects/` se conserva**: contiene el APK compilado de referencia (`pictorario.apk`, imprescindible para comparar el reloj en paralelo) y los designer scripts descompilados (`Objects/src/javi/prieto/pictorario/designerscripts/LS_*.java`), que son la única fuente legible de la geometría de los layouts `.bal`.
- **`b4a/AutoBackups/` se elimina del árbol** (12 ZIP, 18 MB, backups automáticos del IDE de 2019-2020, sin valor). Siguen en el historial de git, así que no se pierde nada; sólo deja de pesar en los clones nuevos.
- El repo pasa de no tener `.gitignore` a tenerlo, calcado del de `colorear`.
- Alternativa si se prefiere el nombre literal: `historico/` en vez de `b4a/`. Uso `b4a/` porque es autoexplicativo.

### README

Se reescribe completo, con esta estructura:

1. Qué es Pictorario y para quién (se conserva el texto actual, que está bien).
2. Créditos: ARASAAC / Sergio Palao / Gobierno de Aragón, licencia CC BY-NC-SA; enlace a la memoria del TFG y al vídeo de YouTube.
3. **Nueva sección — «Versión 2.0: reescritura en Kotlin»**: por qué se migró (targetSdk 29 obsoleto para Play, IDE propietario sólo-Windows, arranque de Activity desde Service bloqueado desde Android 10), el stack nuevo, y el aviso de que **la configuración se reinicia al actualizar** porque no se migran los datos del `KeyValueStore` antiguo.
4. Cómo abrir y compilar (Android Studio, `./gradlew assembleDebug`, `./build_and_copy.sh`), y la tabla de `versionCode`/`versionName` para releases, igual que el README de `colorear`.
5. **Tabla de correcciones respecto al original** (la de la sección «Correcciones» de este plan): cada bug del B4A, su ubicación y qué se hizo.
6. **Sección «Histórico»**: qué hay en `b4a/`, que es el proyecto original congelado y no se compila.
7. **TODO de mejoras futuras** (lista de la última sección de este plan).

---

## Estado de la API de ARASAAC — verificado

Comprobado empíricamente contra los servidores el 27/07/2026. `https://arasaac.org/developers/api` es una SPA y no se puede leer con un fetch, así que se probaron los endpoints directamente:

| Comprobación | Resultado |
|---|---|
| `GET api.arasaac.org/api/pictograms/es/search/perro` | **HTTP 200**, `application/json` — sigue funcionando igual |
| `GET static.arasaac.org/pictograms/7229/7229_500.png` | **HTTP 200**, `image/png` — sigue funcionando igual |
| Campo identificador | Sigue siendo **`_id`**, y es **entero numérico** (`"_id":7202`) → confirma `pictogramId: Int` |
| Autenticación | **Ninguna**. Sin API key, sin token, `access-control-allow-origin: *` |
| Rate limiting | Sin cabeceras `ratelimit-*` ni `retry-after`. Aun así, la descarga concurrente se limita con `Semaphore(6)` por buena vecindad |
| Versionado | `/api/…` y `/v1/…` devuelven **respuesta byte a byte idéntica**: son alias. No hay migración pendiente |
| Tamaños de imagen | `_300`, `_500`, `_2500`. Sin sufijo da 404 |
| Endpoint alternativo | `api.arasaac.org/api/pictograms/<id>` ya devuelve el PNG directamente y acepta `plural`, `color`, `download`. El `?url=true` que el B4A dejó comentado (`SeleccionPictogramas.bas:182-203`) ya no hace falta |
| Idiomas | `es` y `en` responden 200. El B4A tiene `es` **hardcodeado** (`SeleccionPictogramas.bas:98`) |

**Conclusión: los dos endpoints del código B4A siguen siendo válidos y se portan sin cambios.** Un detalle a corregir: una búsqueda amplia devuelve **145 resultados**, más que los topes fijos de la app (arrays de 100, muestra sólo 60 — `SeleccionPictogramas.bas:82-83`); en Compose se usa una `LazyVerticalGrid` sin tope artificial.

---

## Qué hay que portar

El código de la Activity `Main` **no está en un `.bas`**: vive dentro de `pictorario.b4a` a partir de la línea 101. `Portada.bas` y `ElegirPictograma.bas` son **código muerto** (no están en el build; `ElegirPictograma.bas:51` ni siquiera compilaría) — **no se portan**.

| Origen (ahora en `b4a/`) | Destino |
|---|---|
| `Main` (`pictorario.b4a:101-416`) — portada | `ui/home/` |
| `Starter.bas` — Service usado como singleton global | `domain/` + `data/` + `alarm/` |
| `Visualizacion.bas` — el reloj de pictogramas | `domain/ClockGeometry.kt` + `ui/clock/` |
| `ConfigurarSecuencia.bas` — editor | `domain/ActivityRules.kt` + `ui/editor/` |
| `SeleccionPictogramas.bas` — buscador ARASAAC | `data/ArasaacApi.kt` + `ui/picker/` |
| `Configuracion.bas` / `AcercaDe.bas` | `ui/settings/` / `ui/about/` |
| `Avisos.bas` + `ArranqueAutomatico.bas` | `alarm/` (receivers, sin Service) |

---

## Arquitectura

```
app/src/main/
├── AndroidManifest.xml
├── assets/pictogramas/            # los 18 PNG iniciales, recomprimidos
├── res/drawable/ · res/font/great_vibes_regular.ttf · res/values/strings.xml
└── java/es/pictorario/app/
    ├── PictorarioApp.kt           # canales de notificación, siembra, contenedor de dependencias manual
    ├── MainActivity.kt            # ÚNICA Activity, portrait, gestión del intent de alarma
    ├── domain/                    # KOTLIN PURO, cero imports de Android → testeable en JVM
    │   ├── Model.kt               # Activity, Board, Sequence, Settings, AppData (@Serializable)
    │   ├── ClockGeometry.kt       # *** hora→ángulo, sectores, posición de botones, hit-test
    │   ├── ActivityRules.kt       # ordenación, solapes, alta de actividad, aritmética de horas
    │   ├── AlarmCalculator.kt     # próxima alarma (recibe el "ahora" por parámetro)
    │   ├── TimeFormat.kt · Palette.kt · SampleData.kt
    ├── data/
    │   ├── AppDataStore.kt        # DataStore<AppData> con Serializer JSON propio
    │   ├── PictogramRepository.kt # filesDir/pictogramas, siembra desde assets, descarga concurrente
    │   ├── ArasaacApi.kt          # HttpURLConnection sobre Dispatchers.IO
    │   └── BitmapCache.kt         # LruCache de ImageBitmap con downsampling
    ├── alarm/
    │   ├── AlarmScheduler.kt      # setAlarmClock de la próxima alarma
    │   ├── AlarmReceiver.kt       # disparo: notifica y reencadena (goAsync)
    │   ├── BootReceiver.kt        # BOOT_COMPLETED, MY_PACKAGE_REPLACED, TIME_SET, TIMEZONE_CHANGED
    │   └── Notifications.kt       # canales, notificación persistente id 1, aviso full-screen id 2
    └── ui/
        ├── AppRoot.kt             # sealed interface Screen + host de pantallas
        ├── theme/
        ├── home/ · clock/ · editor/ · picker/ · settings/ · about/
        └── common/                # LockGesture, TimePicker, ColorPickerDialog, PictogramImage, Dialogs
```

**Sin `ViewModel` por pantalla y sin Navigation-Compose.** Un `PictorarioState` (clase con `mutableStateOf` + `CoroutineScope`) recordado en `MainActivity` sustituye 1:1 al singleton `Starter.bas`, sin la fragilidad del Service global. Para la navegación basta un `sealed interface Screen` con `var current by mutableStateOf(...)`: son 6 pantallas, sin deep links, y el back stack es trivial.

El "buffer de edición" de B4A (`Starter.Secuencia(MaxSecuencias)`, la ranura fantasma que usa `ConfigurarSecuencia.bas:67-79`) desaparece: pasa a ser `var draft by mutableStateOf(sequence.copy())` local al editor. Esto elimina de paso los caminos en que `BotonAceptar` podía copiar sobre un índice equivocado.

### Modelo de datos

Origen en `Starter.bas:28-41`. Cambios deliberados: `List` en vez de arrays de tamaño fijo (`MaxSecuencias=10` / `MaxActividades=20` pasan a constantes de validación en la UI); `enum` en vez de enteros mágicos; `pictogramId: Int` en vez de `String` numérico (confirmado por la API, que devuelve `_id` entero); `SecuenciaActiva` sale del modelo persistido (es estado de UI).

```kotlin
@Serializable data class Activity(
    val startHour: Int, val startMinute: Int, val endHour: Int, val endMinute: Int,
    val pictogramId: Int, val description: String)

@Serializable enum class BoardType { MORNING_12H, AFTERNOON_12H, DAY_24H, FULL_SEQUENCE }
@Serializable enum class TimeIndicator { NONE, HOUR, HOUR_MINUTE, HOUR_MINUTE_SECOND }

@Serializable data class Board(
    val type: BoardType = BoardType.FULL_SEQUENCE,
    val timeIndicator: TimeIndicator = TimeIndicator.HOUR,
    val iconSizePercent: Int = 0)                       // 0..30, % del ancho de pantalla

@Serializable data class Sequence(
    val description: String, val board: Board = Board(),
    val pictogramId: Int = DEFAULT_PICTOGRAM_ID,        // 7229 (reloj)
    val activities: List<Activity> = emptyList(),
    val notifications: Boolean = false)

@Serializable data class Settings(
    val alarmsEnabled: Boolean = true, val appProtected: Boolean = false,
    val format24h: Boolean = false,
    val hourColor: Long = 0xFF000000, val minuteColor: Long = 0xFF0000FF,
    val secondColor: Long = 0xFFFF0000, val installedVersion: Int = -1)

@Serializable data class AppData(
    val sequences: List<Sequence> = emptyList(), val settings: Settings = Settings())
```

**Persistencia: `DataStore<AppData>` (`datastore-core`) con un `Serializer` propio sobre `kotlinx.serialization.json`.** Frente a Room, no hay consultas ni relaciones ni volumen (máx. 10×20). Frente a DataStore Preferences, evita serializar a mano con claves compuestas — exactamente el patrón `"ActividadSecuencia."&i&"."&j` del `KeyValueStore` que abandonamos. Frente a Proto DataStore, ahorra el `.proto`, el plugin protobuf y un IDL duplicado, y deja la configuración legible con un `adb pull`, muy útil para soporte.

Se escribe el documento entero en cada cambio (son KB). El `Flow<AppData>` alimenta la UI y, en cada emisión, dispara `AlarmScheduler.reschedule()` — el equivalente de `Guardar_Configuracion` llamando a `CalcularProximaAlarma` (`Starter.bas:103`), pero automático y sin poder olvidarse.

**Pictogramas**: siguen siendo ficheros `filesDir/pictogramas/<id>.png`. En el primer arranque (o si falta alguno) se copian los 18 de `assets/`, como `CopiarPictogramasIniciales` (`Starter.bas:458`).

### Stack y dependencias

Base copiada de `colorear/app/build.gradle.kts`: `compileSdk 36`, `targetSdk 36`, JVM 21, `isMinifyEnabled = true` en release, `signingConfigs` leyendo `keystore.properties` con guarda `if (keystorePropertiesFile.exists())`. **`minSdk 26`** (elimina las guardas de canales de notificación y deja limpio el código de alarmas). `versionCode = 200`, `versionName = "2.0"`.

Plugins: `com.android.application` 8.10.1, `kotlin.android` 2.1.20, `kotlin.plugin.compose` 2.1.20, `kotlin.plugin.serialization` 2.1.20. Wrapper Gradle 8.11.1.

```
platform("androidx.compose:compose-bom:<última en el primer sync>")
androidx.compose.ui:ui · ui-graphics · ui-tooling-preview  (+ debugImplementation ui-tooling)
androidx.compose.foundation:foundation      // incluye HorizontalPager y LazyVerticalGrid
androidx.compose.material3:material3
androidx.activity:activity-compose · androidx.core:core-ktx
androidx.lifecycle:lifecycle-runtime-compose
androidx.datastore:datastore-core
org.jetbrains.kotlinx:kotlinx-serialization-json
testImplementation junit:junit:4.13.2
```

**Lo que NO se añade**: Navigation-Compose (6 pantallas, sin back stack real), Retrofit/OkHttp (dos endpoints sin auth → `HttpURLConnection` basta), Coil/Glide (los pictogramas son ficheros locales: `BitmapFactory.decodeFile` con `inSampleSize` + `LruCache` son ~40 líneas), Room, WorkManager (no garantiza horas exactas), Hilt/Koin (inyección manual). Coherente con la filosofía de mínimas dependencias de `colorear`.

---

## El reloj — la pieza de mayor riesgo

Todo está en `b4a/Visualizacion.bas`; las proporciones del layout, en `b4a/Objects/src/javi/prieto/pictorario/designerscripts/LS_visualizarsecuencia.java`.

### Coordenadas

`panelreloj.height = 130% del ancho`; centro en `(50%W, 60%W)` y radio `45%W` (`Visualizacion.bas:86-88`). **`CentroY` y `Radio` se miden en porcentaje del ANCHO, no del alto** — mantenerlo o el reloj se deforma en tablets. Se reproduce con `Canvas(Modifier.fillMaxWidth().aspectRatio(1f/1.3f))` y dentro `cx = size.width*0.50f`, `cy = size.width*0.60f`, `r = size.width*0.45f`.

Compose Canvas comparte el convenio de B4A (Y hacia abajo, ángulos desde las 3 en punto en sentido horario), así que **las fórmulas trigonométricas se trasladan literalmente**, sólo convirtiendo grados a radianes.

### Mapeo hora→ángulo (`domain/ClockGeometry.kt`, puro)

De `Visualizacion.bas:389-413`:

```
FULL_SEQUENCE: angle = 120 + (h + m/60 - minHour) * 300 / (maxHour - minHour)
DAY_24H:       angle = ((h + m/60) / 2) * 30 + 270
12H (0 y 1):   angle = (h + m/60) * 30 + 270
```

`minHour`/`maxHour` del arco salen de la primera y última actividad (`Visualizacion.bas:111-115`), con `maxHour++` si `minuto_fin != 0`. **Blindar la división por cero** cuando `maxHour == minHour` (secuencia de una sola actividad de duración nula): `span = max(1, maxHour - minHour)`.

API sin estado global — esto elimina las lecturas de `Starter.SecuenciaActiva` esparcidas por todo `Visualizacion.bas`:

```kotlin
data class ClockGeometry(val boardType: BoardType, val minHour: Int, val maxHour: Int,
                         val center: Offset, val radius: Float) {
    fun angleDegrees(hour: Float, minute: Float): Float
    fun pointAt(hour: Float, minute: Float, distance: Float): Offset
    fun hitTest(offset: Offset, activities: List<Activity>): Int?
}
```

### Sectores: `drawArc` en lugar de `ClipPath`

El original (`Visualizacion.bas:307-332`) recorta un círculo completo con un `Path` de 4 vértices (centro → inicio → mitad → fin), así que **el borde exterior del sector es un polígono de dos tramos rectos, no un arco** — se nota en actividades largas. En Compose:

```kotlin
drawArc(color = Palette.color(index),
        startAngle = geom.angleDegrees(startHour, startMinute),
        sweepAngle = ((end - start) % 360 + 360) % 360,   // normalizado
        useCenter = true, topLeft = ..., size = ...)      // radio * 0.7f
```

Más simple **y** una mejora visual (arcos reales). La normalización del sweep arregla el caso de una actividad que cruza las 12 en relojes de 12/24 h. El sector seleccionado añade `Stroke(5.dp)` en rojo. No hace falta el hack de repintar en blanco para deseleccionar (línea 324): el `Canvas` se redibuja entero desde el estado.

**El arco del tipo 3 NO es un `drawArc` simple.** Corrección tras ver la app original funcionando (`docs/referencia-b4a/tablero-3-secuencia-completa.png`): el polígono de recorte de las líneas 123-147, con sus constantes 114/81 y 116/80 y el `*3` que falta en las líneas 132 y 143, **no es un descuido: es lo que produce la característica forma de herradura**, con los dos extremos inferiores terminados en punta en vez de cortados en recto. Un `drawArc(120f, 300f, useCenter = true)` daría un arco de bordes rectos radiales y **cambiaría el aspecto**.

Por tanto, para el tipo 3 hay que **reproducir el `Path` de recorte tal cual**, con sus constantes, y no simplificarlo. Los `drawArc` sí valen para los sectores de actividad (§ anterior) y para los tipos 0/1/2, que son círculos completos.

**Filtrado por franja** (líneas 287, 341): en tipo 0 se descartan las actividades que empiezan ≥12 h y se recorta el fin a 12:00; en tipo 1, simétrico. Portar tal cual.

### Detección de toque

`PanelAgujas_Touch` (líneas 544-560) tiene **dos bugs**: no comprueba el radio (un toque en la esquina de la pantalla selecciona actividad) y la comparación `angulo >= inicio && angulo <= fin` falla en cualquier sector que cruce 0°. Ambos se corrigen en `ClockGeometry.hitTest`:

```kotlin
if ((p - center).getDistance() > radius * 0.7f) return null           // corrige el radio
activities.indexOfFirst { normalize(a - startAngle(it)) <= sweep(it) } // corrige el cruce de 0°
```

con `normalize(deg) = ((deg % 360) + 360) % 360` sobre el `atan2` crudo. No hace falta el offset de +90° de `NormalizarAngulo` mientras se aplique el mismo espacio angular a sectores y a toques.

### Dos capas y el tick

- **Capa estática** (`ClockBoard`): `Canvas` con `drawWithCache`, recalculado sólo al cambiar secuencia, ajustes o tamaño.
- **Capa dinámica** (`ClockHands`): `Canvas` superpuesto que lee el estado del tick. Sólo se redibuja esta.
- El tick, con `produceState` + `repeatOnLifecycle(STARTED)`, **corrige el bug de `Visualizacion.bas:79`** (el `Timer` de 500 ms nunca se paraba en `Activity_Pause`) sin escribir código de ciclo de vida.
- **Mejora**: periodo de 500 ms sólo si hay segundero o barra de progreso visible; en el resto de casos, esperar al siguiente minuto. Ahorro real en una app que se deja encendida todo el día.

### Agujas

Líneas 263-280. Una sola aguja gruesa (8 dp, `secondColor`) si `boardType == FULL_SEQUENCE` o `timeIndicator == HOUR`; si no, horaria 8 dp/`hourColor`, minutera 6 dp/`minuteColor` (ángulo `270 + min*6`), segundera 4 dp/`secondColor` (`270 + seg*6`), y punto central negro de radio `0.1*R`.

**Corrección acordada**: el original usa factores distintos en X e Y (horaria 0.7/0.6, minutera 0.8/0.75), lo que hace que las agujas describan una elipse. Se unifican a un único factor por aguja → agujas circulares.

Sólo se dibujan si la hora actual cae en `[minHour, maxHour]` de la franja (líneas 167-177, 263) — mantener.

### Botones-pictograma y navegador

- Botones: `Box` con hijos posicionados por `Modifier.offset { }` usando `geom.pointAt(midHour, midMinute, 0.4f + 0.1f * (i % 3))` (línea 363), lado `iconSizePercent%` del ancho. Si `iconSizePercent == 0`, no se emiten (línea 372). Autoselección de la actividad que contiene la hora actual (líneas 378-383).
- `NavegadorActividades` (el `PanelNavigator` de `sd_panelextra`) → `HorizontalPager`, sincronizado en ambos sentidos con el sector seleccionado (`LaunchedEffect` sobre `selected` y sobre `pagerState.settledPage`), equivalente a `SetSelectPanel` (581) y `_Changepanel` (715). Cada página: fondo `Palette.color(i)`, pictograma, descripción (bold 26 sp) y "desde HH:MM hasta HH:MM" (16 sp) — líneas 222-239.
- `ProgresoActividad` → `LinearProgressIndicator` con el cálculo de `DibujarProgreso` (635-655), oculto si cae fuera de [0,100].
- `CambiarVista` cicla `(tipo+1) % 4` con los iconos `manana/tarde/dia/fila.png`. **Se corrige `Visualizacion.bas:495`**: el cambio ahora se persiste en el `DataStore` y sobrevive a salir de la pantalla.
- Con `appProtected`, `CambiarVista` muestra `candado.png` y actúa como cerrojo, y `Volver` se oculta (201-216, 488-513).

---

## Alarmas y notificaciones

`Avisos.bas:26-30` hace `StartActivity(Visualizacion)` desde un Service. **Android 10+ lo bloquea.** Sustitución:

**Notificación con full-screen intent**, el mecanismo que Android ofrece exactamente para despertadores y llamadas:

```kotlin
NotificationCompat.Builder(ctx, CHANNEL_ALARM)          // IMPORTANCE_HIGH
    .setCategory(NotificationCompat.CATEGORY_ALARM)
    .setFullScreenIntent(pendingIntentToMainActivity, true)
    .setContentIntent(...).setAutoCancel(true)
    .setLargeIcon(bitmapDelPictograma)
```

Pantalla apagada o bloqueada → Android lanza la Activity a pantalla completa (igual que antes). Dispositivo en uso → heads-up prominente. `MainActivity` declara `showWhenLocked` y `turnScreenOn`, y al recibir el intent llama a `requestDismissKeyguard`, navega a la pantalla del reloj y abre el `AlarmDialog` (el `Msgbox2` con pictograma de `Visualizacion.bas:678`).

**`AlarmManager.setAlarmClock(AlarmClockInfo(...))`** en vez de `setExactAndAllowWhileIdle`: es la API para despertadores, está exenta de Doze (mejor puntualidad) y muestra el icono de alarma en la barra de estado.

`AlarmReceiver` es un `BroadcastReceiver` (no un Service) que con `goAsync()` notifica y reencadena la siguiente alarma — el papel de `CalcularProximaAlarma`. Vibración de 1 s y tono `RingtoneManager.TYPE_NOTIFICATION`; `FLAG_INSISTENT` para el `n.Insistent = True` de la línea 673. `BootReceiver` escucha `BOOT_COMPLETED` (sustituto de `ArranqueAutomatico.bas`) y además `MY_PACKAGE_REPLACED`, `TIME_SET` y `TIMEZONE_CHANGED`, tres casos que el original no cubría y que dejaban la alarma descolocada.

Notificación persistente id 1 en canal `IMPORTANCE_LOW` con "Próxima actividad: HH:MM — Secuencia ➞ Actividad" (`Starter.bas:546-553`), recreada en cada `scheduleNext`.

**Permisos finales**: `INTERNET`, `VIBRATE`, `RECEIVE_BOOT_COMPLETED`, `POST_NOTIFICATIONS` (runtime, pedido la primera vez que se activan notificaciones en una secuencia, no al arrancar), `USE_EXACT_ALARM`, `SCHEDULE_EXACT_ALARM` con `maxSdkVersion="32"`, `USE_FULL_SCREEN_INTENT`. **Se eliminan** `WRITE_EXTERNAL_STORAGE`, `WRITE_SETTINGS`, `WAKE_LOCK` y `FOREGROUND_SERVICE` — con `setAlarmClock` + `turnScreenOn` no hace falta ningún Service, lo que también esquiva los tipos obligatorios de foreground service de API 34+.

En API 34+, `canUseFullScreenIntent()` puede ser falso; en ese caso, botón en Configuración hacia `ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT` y degradación limpia a heads-up con sonido y vibración. `USE_EXACT_ALARM` y `USE_FULL_SCREEN_INTENT` requieren justificación en Play Console: Pictorario califica como app de alarmas, pero hay que redactarla (fase 9).

---

## Botón Atrás y bloqueo parental

El original anula Atrás en todas las pantallas. **Cambio acordado**: `BackHandler(enabled = settings.appProtected)`. Con el bloqueo parental activo, Atrás se ignora (que es cuando de verdad importa); desbloqueada, la app navega con normalidad, como espera Android moderno y las políticas de Play.

El bloqueo parental oculta engranajes y botones de edición; el desbloqueo es un gesto de pulsación corta (vibra 100 ms) seguida de pulsación larga (vibra 300 ms) — `pictorario.b4a:375-391` y `Visualizacion.bas:506-513`. Se encapsula en `ui/common/LockGesture.kt`.

---

## Plan por fases

Cada fase compila, instala y se puede enseñar.

0. ~~**Reorganización del repo + esqueleto**~~ — **COMPLETADA** (27/07/2026, rama `migracion-kotlin`). Ver «Notas de la fase 0» más abajo.
1. ~~**Dominio puro + tests**~~ — **COMPLETADA** (27/07/2026). 48 tests en verde. Ver «Notas de la fase 1».
2. ~~**Persistencia y portada**~~ — **COMPLETADA** (27/07/2026). 53 tests en verde. Ver «Notas de la fase 2».
3. ~~**Reloj estático**~~ — **COMPLETADA** (27/07/2026). Ver «Notas de la fase 3».
4. ~~**Reloj vivo**~~ — **COMPLETADA** (27/07/2026). Ver «Notas de la fase 4».
5. **Editor** (2 días) — borrador local, selectores de tipo de tablero e indicador, slider de tamaño de icono, checkbox de notificaciones, filas de actividad con `TimePicker` respetando `format24h`, ordenación y solapes con sus avisos, añadir/borrar actividad, aceptar/cancelar. *Verificable*: crear una secuencia nueva y verla en el reloj.
6. **Buscador ARASAAC** (1 día) — `ArasaacApi` + `LazyVerticalGrid` de 3 columnas sin tope artificial, listado inicial de ficheros locales (los más recientes primero), búsqueda y **descarga concurrente** (`coroutineScope { ids.map { async { … } }.awaitAll() }` con `Semaphore(6)`), progreso y manejo de "sin conexión".
7. **Configuración, Acerca de y candado** (1 día) — alarmas, protección, formato horario, los tres colores con `ColorPickerDialog` propio (HSV, sin dependencias), reiniciar configuración; créditos, GreatVibes en "Para Teo", enlaces con `Intent.ACTION_VIEW`, changelog al detectar cambio de `versionCode`; `LockGesture`; `BackHandler` condicionado.
8. **Alarmas** (2 días) — `Notifications`, `AlarmScheduler`, `AlarmReceiver`, `BootReceiver`, permisos de runtime, full-screen intent, `AlarmDialog`. *Verificable*: actividad a 2 minutos vista con la pantalla apagada.
9. **Pulido, README y publicación** (1 día) — icono adaptativo, recompresión de los 18 PNG, reglas R8, `build_and_copy.sh`, **README reescrito** (estructura de la sección «README» de este plan), `strings.xml` completo, revisión de contraste y tamaños táctiles, AAB firmado, justificaciones de permisos para Play Console.

---

## Notas de la fase 0

Hallazgos y desviaciones surgidos al montar el esqueleto:

- **Cuatro iconos eran JPEG con extensión `.png`** (`dia`, `fila`, `manana`, `tarde`, los del botón `CambiarVista`). B4A lo toleraba porque cargaba los ficheros con `LoadBitmap`; AAPT los rechaza al compilar recursos. Convertidos a PNG real.
- **Seis de los 18 pictogramas son de 2500×2500 px** en vez de 500×500, lo que infla el APK. Se recomprimen en la fase 9.
- **Dependencias fijadas a AGP 8.10.1 / compileSdk 36**, la cadena de herramientas de `colorear`. Las últimas versiones de AndroidX (`core-ktx 1.19`, `lifecycle 2.11`, `compose-bom 2026.06`) exigen **AGP 9.1 y compileSdk 37**. Versiones en uso: compose-bom 2025.12.01, activity-compose 1.11.0, core-ktx 1.17.0, lifecycle-runtime-compose 2.9.4, datastore-core 1.1.7, kotlinx-serialization-json 1.9.0. Actualizar a AGP 9 queda como mejora futura.
- **Mejora sobre `colorear`**: el `signingConfig` sólo se asigna si existe `keystore.properties`, de modo que un clon limpio puede compilar el release (sin firmar) en vez de fallar con `SigningConfig "release" is missing required property "storeFile"`.
- `AutoBackups/` (12 ZIP, 18 MB) eliminado del árbol; sigue en el historial de git.
- El repo trabaja en la rama **`migracion-kotlin`**; `master` conserva el proyecto B4A intacto.
- **Sufijo `.debug` en el `applicationId`** de las compilaciones de depuración. Sin él, la app nueva y el APK B4A comparten identificador y no pueden coexistir, que es precisamente lo que exige el plan de verificación. No afecta al release.
- **La versión B4A arranca sin problemas en Android 16 / API 36**, así que sirve como referencia viva y no sólo como capturas. Las capturas de la portada y los cuatro tipos de tablero están en `docs/referencia-b4a/`.
- **Hallazgo sobre el tipo 3**: lo que el análisis estático tomó por un bug (el `*3` ausente en `Visualizacion.bas:132,143`) resulta ser lo que da al tablero su forma de herradura. Ver la sección del reloj.

## Notas de la fase 1

El paquete `domain/` quedó en 7 ficheros, sin un solo import de Android, y `app/src/test/` con **48 tests** repartidos en 6 clases. Todo lo verificable de la migración vive aquí.

- **`Point` propio en vez de `Offset` de Compose.** Para que `domain/` sea de verdad Kotlin puro se define un `data class Point(x, y)` local; la conversión se hará en la capa de UI. Son tres líneas y a cambio los tests no arrastran nada de Android.
- **Color 20 de la paleta**: la serie del original es ColorBrewer Set3 (11 entradas) seguida de Paired (8). El hueco se rellena con el siguiente de Paired, `#CAB2D6`.
- **Bug nuevo encontrado y corregido en `EscribirHora`** (`Visualizacion.bas:441`): en formato de 12 horas restaba 12 sólo si la hora era mayor que 11, y nunca devolvía un cero a doce. Resultado: medianoche se imprimía `00:00 a.m.` y mediodía `00:00 p.m.`. Ahora ambos son `12:00`, que es lo que hacía la propia `Hora24a12` y lo que espera un reloj de 12 horas.
- **Texto del reloj digital verificado contra el original** (`Temporizador_Tick`, líneas 518-535): sufijo « de la noche» sólo a las 0 h, « del mediodía» sólo a las 12 h, « p.m.» a partir de las 13 h y « a.m.» en el resto. No es la partición por franjas que yo había supuesto.
- **`sweepAngle` normalizado**: una actividad que cruza las 12 en un reloj de 12 o 24 horas daba antes un sector negativo o vacío.
- **`hitTest` con comprobación de radio**: el original resolvía sólo por ángulo, así que un toque en una esquina de la pantalla seleccionaba actividad.
- **`visibleActivities` devuelve `IndexedValue`**, conservando el índice original de cada actividad para que el color del sector siga coincidiendo con el de su fila en el editor aunque el tablero descarte actividades.
- **La ranura fantasma desaparece**: `Sequence.activities` es una `List`, así que `num_actividades` y el buffer de edición `Secuencia(MaxSecuencias)` dejan de existir.

## Notas de la fase 2

Persistencia funcionando y portada completa. Verificado en el emulador: las tres secuencias de ejemplo aparecen con sus pictogramas, duplicar y borrar funcionan, y el estado sobrevive a un `am force-stop`.

- **`PictorarioState` sustituye a `Starter.bas`**: en vez de un servicio siempre en marcha con estado global mutable, un objeto normal propiedad de la Activity que lee del `DataStore` y escribe el documento entero en cada cambio.
- **El JSON persistido es legible**: `adb shell run-as javi.prieto.pictorario.debug cat files/pictorario.json` lo muestra formateado, que era una de las razones para elegir DataStore con serializador propio.
- **`ReplaceFileCorruptionHandler`**: un fichero truncado o editado a mano cae a los datos de ejemplo en vez de dejar la app sin arrancar.
- **Caché de pictogramas por bytes, no por número de entradas.** Seis de los assets son de 2500×2500: uno solo ocupa 25 MB descodificado y habría desalojado todo lo demás. El `LruCache` mide `ancho × alto × 4` con un tope de 24 MB, y la descodificación usa `inSampleSize` según el tamaño en pantalla.
- **`safeDrawingPadding()`**: desde Android 15 las apps dibujan de borde a borde por defecto, y sin esto la barra de estado se comía el logotipo. Se detectó en la primera captura del emulador.
- **`LockGesture` adelantado desde la fase 7**, porque el botón del candado forma parte de la portada y dejarlo sin funcionar habría sido peor que implementarlo. La fase 7 sólo tiene que reutilizarlo en la pantalla del reloj.
- **Las pantallas aún no escritas son marcadores** que permiten navegar y volver, de modo que el enrutado ya está probado.

**Punto abierto de aspecto**: los botones de la portada son ahora `Button` de Material 3 —azules y redondeados— frente a los rectángulos grises planos del original. La disposición y los textos son idénticos, pero el estilo no. Comparar `docs/referencia-b4a/portada.png` con la app actual y decidir si se replica el aspecto gris o se acepta el de Material 3.

## Notas de la fase 3

La fase crítica sale bien: los cuatro tableros coinciden con el original. La comparativa está en `docs/comparativas/fase3-tableros.png` (izquierda original, derecha nuevo) y el mapa de diferencias del marco en `docs/comparativas/fase3-diferencias-marco.png`.

**El vértice de cierre del recorte no es un punto polar.** Es el hallazgo de la fase. `Visualizacion.bas:132` y `:143` escriben:

```basic
Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (SinD(81)*Radio)+CentroY)
```

La **X se escala por tres radios y la Y por uno solo**. Leerlo como «un punto a 81° y distancia R» —que es la lectura natural, y la que yo hice primero— acerca demasiado ese vértice a la línea central y deforma visiblemente la punta derecha de la herradura. Corregido reproduciendo las dos escalas por separado. La primera versión pasaba la revisión a ojo; sólo se detectó al recortar y superponer esa zona contra la captura del original.

**Verificación cuantitativa** sobre el tablero de arco, comparando la máscara del marco gris píxel a píxel: desplazamiento vertical óptimo de **0 px** y diámetro idéntico (1019 px en ambos). Las diferencias que quedan están todas explicadas:

- el reloj digital de la cabecera y la caja del pictograma central, que son de la fase 4;
- los glifos de los números de las horas, que Compose mide distinto que las `Label` de B4A;
- líneas de un píxel en los bordes, por antialiasing.

Otros puntos:

- **Los botones van superpuestos sobre la parte baja del panel**, no debajo. `LS_visualizarsecuencia.java` coloca `Volver` a 30 dp del borde inferior del propio `panelreloj`, aprovechando el hueco que deja la esfera. Con un `Column` normal quedaban demasiado abajo.
- **El fondo es `#F0FFFF`**, el mismo azur que usaba el editor. Muestreado de la captura de referencia.
- **`CambiarVista` ya persiste el tipo de tablero**, corrigiendo `Visualizacion.bas:495`. Comprobado en el emulador: se cicla por los cuatro y el cambio sobrevive a salir de la pantalla.
- El centro y el radio se calculan **siempre contra el ancho**, nunca contra el alto: tomarlos del alto deformaría la esfera en tablet.

## Notas de la fase 4

La pantalla del reloj queda completa. Comparativa en `docs/comparativas/fase4-reloj-vivo.png`.

- **El tick se adapta a lo que hay en pantalla.** Con segundero visible late cada 500 ms como el original; si no, duerme hasta el siguiente minuto. En una app pensada para dejarse encendida todo el día, despertar dos veces por segundo sin necesidad es gasto puro. Va atado a `repeatOnLifecycle(STARTED)`, lo que además corrige que el `Timer` de B4A siguiera corriendo en segundo plano.
- **`PanelNavigator` (`sd_panelextra`) se sustituye por `HorizontalPager`** más una tira de miniaturas que hace de indicador de página, que es lo que aquella librería dibujaba. La sincronización es bidireccional: tocar un sector desplaza el carrusel y deslizar el carrusel selecciona el sector.
- **Las miniaturas se reparten el ancho a partes iguales** en vez de tener un tamaño fijo. Con las diez actividades de la secuencia de ejemplo se salían de la pantalla; una secuencia admite hasta veinte.
- **El contorno rojo del sector seleccionado dibuja sólo el arco exterior**, no los dos radios. El original trazaba una circunferencia completa y dejaba que el recorte se comiera los lados rectos, así que las líneas rectas nunca se veían.
- **Las agujas ya no son elípticas.** El original usaba fracciones distintas para X e Y (0,7/0,6 la horaria y 0,8/0,75 la minutera); ahora cada aguja usa una sola fracción.
- **La barra de progreso desaparece cuando la actividad no está en curso**, en vez de mostrar un valor fuera de rango.

---

## Correcciones respecto al original

A documentar en el README:

| Origen | Problema | Corrección |
|---|---|---|
| `ConfigurarSecuencia.bas:504` | `ComparaHoras(h, m, hora_fin, hora_inicio)` — el 4.º argumento debería ser `minuto_fin` | Comparación correcta de hora **y** minuto |
| `Starter.bas:66` | `Colores` declarado con 20 posiciones pero inicializado con 19 → la actividad n.º 20 sale transparente | Paleta con los 20 colores |
| `Visualizacion.bas:79` | El `Timer` de 500 ms nunca se para en `Activity_Pause` | Tick atado al ciclo de vida con `repeatOnLifecycle` |
| `Visualizacion.bas:267-270` | Factores X/Y distintos → agujas elípticas | Agujas circulares |
| `Visualizacion.bas:495` | `CambiarVista` no persiste el tipo de tablero | Se persiste |
| `Visualizacion.bas:544-560` | Hit-test sin comprobar radio y roto en sectores que cruzan 0° | Ambos corregidos en `ClockGeometry.hitTest` |
| `Visualizacion.bas:441` | `EscribirHora` imprimía medianoche como `00:00 a.m.` y mediodía como `00:00 p.m.` | Ambos son `12:00` |
| ~~`Visualizacion.bas:132,143`~~ | ~~Falta un `*3` en el recorte del arco~~ | **NO es un bug**: el vértice escala la X por `3·R` y la Y por `R`, y eso produce la forma de herradura. Se reproduce tal cual |
| `Visualizacion.bas:111-115` | División por cero si `maxHour == minHour` | `span = max(1, maxHour - minHour)` |
| `Visualizacion.bas:307-332` | Sectores con borde exterior poligonal | `drawArc` con arco real |
| `SeleccionPictogramas.bas:82-83` | Topes fijos de 100/60 resultados, cuando la API devuelve hasta 145 | `LazyVerticalGrid` sin tope artificial |
| `SeleccionPictogramas.bas:117` | Descargas secuenciales | Concurrentes con `Semaphore(6)` |
| `Avisos.bas:26-30` | `StartActivity` desde Service: bloqueado en Android 10+ | Notificación con full-screen intent |
| `ArranqueAutomatico.bas` | Sólo reprograma en `BOOT_COMPLETED` | También en `MY_PACKAGE_REPLACED`, `TIME_SET`, `TIMEZONE_CHANGED` |
| Manifest | `WRITE_EXTERNAL_STORAGE`, `WRITE_SETTINGS`, `WAKE_LOCK`, `FOREGROUND_SERVICE` sin uso | Eliminados |

---

## Entorno de pruebas

Montado en la fase 0 sobre el SDK ya instalado en `~/Android/Sdk`:

- **AVD `pictorario_test`**: Pixel 6 (1080×2400, vertical), `system-images;android-36;google_apis;x86_64`, Android 16 / API 36. Acelerado por KVM, accesible sin `sudo` gracias a una ACL sobre `/dev/kvm`.
- **Arranque con ventana**: `~/Android/Sdk/emulator/emulator -avd pictorario_test -no-audio -no-boot-anim -gpu host`. Añadir `-no-window` para automatizar sin interfaz.
- **`mobile-mcp`** (`@mobilenext/mobile-mcp`) declarado en `.mcp.json` del proyecto, para dirigir el emulador por texto de elemento en vez de por coordenadas.
- Como alternativa siempre disponible, `adb` directo: `install -r`, `am start`, `exec-out screencap -p`, `shell input tap/swipe`, `uiautomator dump`, `logcat`.

Para comparar contra la versión antigua, instalar en paralelo el APK B4A: `adb install b4a/Objects/pictorario.apk`. Comparte `applicationId`, así que **no pueden convivir**: instalar uno u otro, o comparar contra capturas.

---

## Verificación

**Tests JVM** en `app/src/test/java/es/pictorario/app/domain/`, estilo `colorear`: JUnit 4 plano, `assertEquals` importado suelto, nombres de método como frase en inglés, sin mocks ni Robolectric. `./gradlew test`.

- **`ClockGeometryTest`** (el más importante) — `angleDegrees` en los 4 tipos (12:00 → 270°+360 en reloj de 12; 6:00 → 90°; tipo 24 h con la hora entre 2; arco con `minHour=8, maxHour=9`: 8:00 → 120°, 8:30 → 270°, 9:00 → 420°); extremos del arco; `sweep` normalizado positivo incluido un sector que cruza las 12; `hitTest` (acierto en el centro angular de cada sector, rechazo por radio > 0.7·R, acierto en sector que cruza 0°, `null` fuera); guarda de `maxHour == minHour`; `pointAt` con distancias 0.4/0.5/0.6 para los índices 0,1,2 y de nuevo 0.4 para el 3.
- **`ActivityRulesTest`** — ordenación por hora de inicio detectando si hubo cambio; `removeOverlaps` (`ConfigurarSecuencia.bas:555-557`); **regresión del bug de la línea 504**: inicio 10:45 con fin 10:50 no se detectaba porque comparaba contra `hora_inicio` en lugar de `minuto_fin`; `addActivity` heredando el fin de la anterior +30 min, primera a las 8:00, tope 23:59.
- **`AlarmCalculatorTest`** — recibe el "ahora" por parámetro, nunca `System.currentTimeMillis()`. Elige la más próxima entre varias secuencias; ignora `notifications=false` y `alarmsEnabled=false`; vuelco a mañana cuando todas pasaron; sin candidatos → `null`; empate exacto con la hora actual (el original usa `<=`, empujándolo a mañana — fijar y documentar el criterio).
- **`TimeFormatTest`** — 13:05 → "1:05 p.m." / "13:05"; medianoche → "12:00 a.m."; mediodía; relleno de ceros.
- **`PaletteTest`** — **exactamente 20 colores**, regresión del bug de `Starter.bas:66`.
- **`SerializationTest`** — round-trip de `AppData` con las 3 secuencias de ejemplo; JSON con campos ausentes cayendo a valores por defecto.

**En dispositivo**, con el APK original (`b4a/Objects/pictorario.apk`) instalado en paralelo para comparar:

- Los 4 tipos de tablero se ven igual, con las mismas horas y sectores.
- Toque en sector y en botón-pictograma seleccionan la misma actividad; el carrusel se sincroniza en ambos sentidos.
- El editor ordena y recorta solapes igual; el `TimePicker` respeta el formato 12/24 h.
- Buscar y descargar un pictograma nuevo; persiste tras reiniciar la app.
- Alarma: actividad a 2 minutos vista, dispositivo bloqueado → salta el aviso a pantalla completa. Reiniciar el móvil y comprobar que sigue programada. Cambiar la hora del sistema y comprobar la reprogramación.
- Bloqueo parental: activar, comprobar que desaparecen los controles de edición y que Atrás queda anulado; desbloquear con el gesto corto+largo; comprobar que desbloqueada Atrás vuelve a funcionar.

**Release**: `./build_and_copy.sh` genera APK debug y AAB release firmado, con `versionCode` > 107.

---

## Mejoras futuras (TODO, sin detalle)

Lista para la sección final del README. **Nada de esto entra en la migración**: la 2.0 es paridad funcional. Se recoge aquí para no perderlo.

**Accesibilidad y comunicación**
- [ ] Lectura en voz alta de la actividad con TTS (la app no tiene voz; útil para quien no lee)
- [ ] Etiquetas de TalkBack, tamaños táctiles y respeto al escalado de fuente del sistema
- [ ] Modo alto contraste y tema oscuro
- [ ] Localizar la interfaz a otros idiomas (hoy todo el texto está en castellano)
- [ ] Búsqueda de pictogramas en otros idiomas (la API lo soporta; el código tiene `es` fijo)

**Funcionalidad**
- [ ] Secuencias por día de la semana (hoy una secuencia no distingue días)
- [ ] Marcar actividades como completadas, con refuerzo visual
- [ ] Temporizador o cuenta atrás visual de la actividad en curso
- [ ] Programar varias alarmas a la vez (hoy sólo se programa la más próxima)
- [ ] Actividades que cruzan la medianoche (el modelo actual topa en 23:59)
- [ ] Usar fotos propias como pictogramas (cámara y galería)
- [ ] Exportar e importar secuencias, para compartirlas entre cuidadores o dispositivos
- [ ] Copia de seguridad y restauración
- [ ] Widget de pantalla de inicio con la actividad actual
- [ ] Elevar los límites de 10 secuencias y 20 actividades (el de 20 exige ampliar la paleta de colores)

**Presentación**
- [ ] Disposición específica para tablet y para horizontal (hoy portrait fijo)
- [ ] Estilos alternativos de esfera de reloj
- [ ] Usar los pictogramas `_2500` en pantallas grandes

**Proyecto**
- [ ] Actualizar a AGP 9.1 y compileSdk 37 para poder usar las últimas versiones de AndroidX
- [ ] CI en GitHub Actions que compile el AAB y ejecute los tests
- [ ] Capturas y material gráfico nuevos para la ficha de Play

---

## Firma de la aplicación

**Estado: keystore localizado y funcionando.** El release se compila y firma correctamente.

| Dato | Valor |
|---|---|
| Fichero | `app/upload-keystore.jks` (no versionado) |
| Formato | JKS |
| Alias | `b4a` |
| Titular | `CN=Javier Prieto Martínez, O=Javi Prieto, C=sp` |
| Creado | 23/05/2018 · válido hasta 20/09/2056 |
| Algoritmo | **DSA de 1024 bits** con SHA256withDSA |
| Huella SHA-256 | `82:8C:FA:F1:40:6B:C0:BE:1B:4A:DB:EB:3C:DA:E7:A3:7D:6E:B3:89:75:E2:F6:8D:44:CD:4C:11:10:9C:6C:66` |
| Huella SHA-1 | `8C:E5:E6:CA:9E:86:32:80:6E:92:19:3B:7C:C9:96:68:F3:FC:23:30` |

La contraseña del almacén sirve también para la clave. Se configura en `keystore.properties` (raíz, permisos 600, ignorado por git):

```properties
storeFile=upload-keystore.jks
storePassword=…
keyAlias=b4a
keyPassword=…
```

### Dos avisos importantes

1. **La clave es DSA de 1024 bits, que `keytool` marca como débil.** Google Play exige **RSA de 2048 bits o más** para la clave de firma de aplicación en Play App Signing. Si la ficha no está aún enrolada en Play App Signing, enrolarla con esta clave puede ser rechazado, y **subir un AAB obliga a estar enrolado**. Hay que comprobarlo en Play Console antes de preparar el release (ver más abajo). Si se confirma el problema, la salida es enrolarse generando una **clave de subida RSA nueva** — Play permite que la clave de subida sea distinta de la de firma.
2. **El APK de `b4a/Objects/pictorario.apk` NO está firmado con esta clave**, sino con la de depuración de B4A (`CN=Anywhere Software`). Es la salida de depuración del IDE, no el artefacto publicado. Por eso no sirve para confirmar que este keystore sea el registrado en Play; eso sólo se puede verificar en Play Console.

### Cómo verificar la clave registrada en Play

1. Entrar en [Play Console](https://play.google.com/console) → aplicación **Pictorario**.
2. Menú lateral: **Prueba y lanzamiento → Integridad de la aplicación** (antes *Configuración → Firma de la aplicación*).
3. Ahí aparecen dos bloques, y hay que mirar los dos:
   - **Certificado de la clave de firma de la aplicación**: con el que Play firma lo que llega a los usuarios.
   - **Certificado de la clave de subida**: con el que hay que firmar lo que se sube.
4. Comparar la huella **SHA-256** de la *clave de subida* con la de la tabla de arriba.
   - **Coincide** → este keystore es el correcto, no hay nada que hacer.
   - **No coincide, o no existe el bloque de clave de subida** → la ficha está en modo antiguo (firmada directamente por el desarrollador) o usa otra clave. Ver el punto siguiente.
   - **No aparece la sección** → la app no está enrolada en Play App Signing.

Para obtener la huella de cualquier keystore en local:

```bash
keytool -list -v -keystore app/upload-keystore.jks -alias b4a
```

### Si la clave no es la correcta o se ha perdido

Sólo hay salida si la app está enrolada en **Play App Signing**: en *Integridad de la aplicación* existe la opción de **solicitar el restablecimiento de la clave de subida**, generando una nueva y subiendo su certificado. Google tarda un par de días en aplicarlo. La clave de *firma* no se puede cambiar nunca; si la app no está enrolada y se pierde esa clave, no hay forma de actualizar la ficha y hay que publicar una nueva con otro `applicationId`.

Para generar una clave de subida nueva (RSA, lo que exige Play hoy):

```bash
keytool -genkeypair -v -keystore upload-keystore.jks -alias upload \
  -keyalg RSA -keysize 4096 -validity 10000
```

y exportar su certificado para enviarlo a Google:

```bash
keytool -export -rfc -keystore upload-keystore.jks -alias upload -file upload_certificate.pem
```

### Copia de seguridad

El keystore es **irrecuperable**: si se pierde y la app no está en Play App Signing, se pierde la capacidad de actualizarla. Hay una copia de los ficheros originales en `~/copias-seguridad-claves/` (fuera del repositorio, permisos 600). **Conviene guardar además una copia en un gestor de contraseñas o en almacenamiento cifrado externo.**

---

## Pendiente de verificar (no bloquea el arranque)

- **Compatibilidad de la clave DSA con Play App Signing** (ver sección anterior). Es el único punto que puede obligar a replantear la publicación.
- **Aviso de pérdida de datos**: al no migrar el `KeyValueStore` antiguo, un usuario existente con secuencias propias las perderá al actualizar. Conviene indicarlo en las notas de la versión y mostrar un aviso de una sola vez en el primer arranque tras actualizar (barato, y evita reseñas negativas).
