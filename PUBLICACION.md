# Publicación en Google Play

> **La ficha de Pictorario fue retirada de Google Play por incumplimiento de políticas**, al no haberse actualizado al nivel de API exigido. Este documento existe para que la primera versión que se suba cumpla sin margen de duda. La regla de trabajo es **elegir siempre la opción más conservadora**, aunque cueste funcionalidad.

---

## 0. Estado de la ficha — RESUELTO

**La ficha se puede actualizar.** Play Console admite subir una versión nueva, así que el `applicationId` `javi.prieto.pictorario` se conserva y con él las instalaciones, las reseñas y el historial. La versión publicada era la **107 (1.07)**; el proyecto va con `versionCode 200` / `versionName 2.0`.

La aplicación **está enrolada en Play App Signing**: el certificado de firma de aplicación pertenece a Google (`CN=Android, O=Google Inc.`). Eso resuelve de raíz la preocupación anterior sobre la clave DSA — la clave con la que Google firma lo que llega a los usuarios es suya y no se toca.

---

## 1. Requisitos técnicos — estado actual

| Requisito | Exigido | En el proyecto | Estado |
|---|---|---|---|
| `targetSdk` | Nivel de API reciente (el motivo de la retirada) | **36** (Android 16) | ✅ |
| `compileSdk` | ≥ `targetSdk` | 36 | ✅ |
| `minSdk` | Libre | 26 (Android 8) | ✅ |
| Formato de subida | Android App Bundle (`.aab`) | `./build_and_copy.sh` genera AAB | ✅ |
| 64 bits | Obligatorio si hay código nativo | Sin código nativo | ✅ (no aplica) |
| `versionCode` | Mayor que el publicado (107) | **200** | ✅ |
| Firma | Clave de subida registrada en Play | RSA 4096, SHA-1 `63:49:AF:0F:…:15:80`, **confirmada por Google** | ✅ |

---

## 1 bis. La clave de subida — RESUELTO

**Google ya ha aplicado el restablecimiento.** Las huellas que devolvió coinciden con la clave generada para el proyecto:

| | |
|---|---|
| SHA-1 según Google | `63:49:AF:0F:07:7F:52:EE:7A:20:3D:65:9B:3D:18:39:64:77:15:80` |
| SHA-1 de `app/upload-keystore-rsa.jks` | `63:49:AF:0F:07:7F:52:EE:7A:20:3D:65:9B:3D:18:39:64:77:15:80` |

El AAB firmado con esa clave ya se puede subir. El resto de esta sección queda como registro de por qué hubo que restablecerla.

### Antecedente

Comparando los certificados descargados de Play con las claves disponibles en local:

| Clave | Titular | Huella SHA-256 |
|---|---|---|
| **Firma de aplicación** (de Google) | `CN=Android, O=Google Inc.` | `30:2B:C6:FB:04:DC:D3:E5:47:FF:0C:82:A1:0B:12:1B:14:E1:68:A1:FE:F9:36:F1:C6:1D:8E:CE:B1:DC:8F:32` |
| **Subida registrada** en Play | `CN=Anywhere Software` | `32:75:24:70:A3:5A:7B:B0:A2:99:11:80:F0:2B:AF:F4:9A:41:B9:D6:B2:B5:E4:4E:8A:A7:CB:73:67:52:E0:03` |
| `firma.keystore` que apareció en local | `CN=Javier Prieto Martínez` | `82:8C:FA:F1:40:6B:C0:BE:…` |

**La clave de subida registrada NO es `firma.keystore`.** Es `CN=Anywhere Software`, que es la **clave de depuración por defecto del IDE de B4A** — la misma con la que está firmado `b4a/Objects/pictorario.apk`, huella idéntica. La versión 1.07 se publicó firmada con ella.

Esa clave venía con el IDE de B4A, que ya no se usa y que sólo funciona en Windows. No está en este equipo, y aunque estuviera, es una clave compartida por todos los desarrolladores de B4A: no es defendible seguir usándola.

### Qué hay que hacer

El proyecto ya trae generada una clave de subida nueva, en condiciones:

| | |
|---|---|
| Fichero | `app/upload-keystore-rsa.jks` (no versionado) |
| Alias | `upload` |
| Algoritmo | **RSA de 4096 bits** |
| Titular | `CN=Javier Prieto Martinez, O=Pictorario, C=ES` |
| Huella SHA-256 | `72:96:F7:68:00:B0:BE:FA:64:31:07:09:ED:44:C3:77:F1:BE:95:F0:C0:74:F2:9C:2A:FF:68:F6:D7:93:D1:E1` |
| Certificado a enviar | `upload_certificate.pem`, en la raíz del proyecto |

Las credenciales están en `keystore.properties` (permisos 600, ignorado por git) y hay copia en `~/copias-seguridad-claves/`. **Guarda una copia adicional en un gestor de contraseñas: si se pierde, hay que volver a pedir un restablecimiento.**

El restablecimiento se solicitó desde **Prueba y lanzamiento → Integridad de la aplicación → Clave de subida**, adjuntando `upload_certificate.pem`, y Google lo aplicó.

---

## 1 ter. El Digital Asset Links JSON: no hace falta

Play ofrece ese fragmento para **Android App Links**: asociar un dominio a la aplicación de modo que al abrir un enlace `https://…` de tu web, Android abra la aplicación en vez del navegador.

**Pictorario no tiene enlaces profundos**: no hay ninguna URL que deba abrir la aplicación. El fragmento se puede ignorar sin consecuencias. Sólo haría falta si algún día se quisiera, por ejemplo, que un enlace del blog abriese una secuencia concreta.

---

## 2. Permisos — postura deliberadamente mínima

La decisión rectora: **la aplicación es plenamente funcional sin ningún permiso sujeto a revisión de políticas.** Se ha renunciado a funcionalidad para no arriesgar el envío.

### Lo que se declara

| Permiso | Para qué | Riesgo |
|---|---|---|
| `INTERNET` | Descargar pictogramas de ARASAAC, sólo cuando el adulto busca | Ninguno |
| `VIBRATE` | Gesto del candado parental y aviso de actividad | Ninguno |
| `RECEIVE_BOOT_COMPLETED` | Reprogramar la alarma tras reiniciar | Ninguno |
| `POST_NOTIFICATIONS` | Avisos de actividad; se pide en runtime y se acepta la negativa | Ninguno |
| `SCHEDULE_EXACT_ALARM` | **Opcional.** Sin él la alarma salta igual, mediante `setAndAllowWhileIdle`, que atraviesa el modo de reposo aunque no sea puntual al segundo | Bajo: no se concede solo, lo activa el adulto desde los ajustes del sistema |

### Lo que se ha descartado a propósito

| Permiso | Por qué se descarta |
|---|---|
| **`USE_EXACT_ALARM`** | Google Play lo reserva a aplicaciones cuya **función principal** es despertador o calendario. Pictorario es una agenda visual: defendible, pero discutible para un revisor. Una declaración rechazada bloquearía la republicación, que es justo lo que no nos podemos permitir. |
| ~~`USE_FULL_SCREEN_INTENT`~~ | **Se ha vuelto a declarar.** Ver la sección 2 bis. |
| `FOREGROUND_SERVICE` | Ya no hace falta ningún servicio: la alarma la atiende un `BroadcastReceiver`. Esto esquiva además los tipos obligatorios de servicio en primer plano de API 34+. |
| `WAKE_LOCK` | Innecesario con `AlarmManager`. |
| `WRITE_EXTERNAL_STORAGE`, `WRITE_SETTINGS` | Estaban en la versión B4A **sin usarse**. Eliminados. |

---

## 2 bis. `USE_FULL_SCREEN_INTENT`: por qué se declara

Primero se renunció a este permiso para no arriesgar la revisión, y el aviso quedó reducido a una notificación. **Probado en un móvil real, no cumplía su función**: llegaba la hora, la notificación se posaba en la bandeja sin sonar de forma reconocible, y el niño no se enteraba de nada. Una agenda visual que no avisa no sirve para lo que existe.

Así que se declara, y hay que justificarlo al subir. **Justificación para Play Console:**

> Pictorario es una agenda visual con alarmas para niños con Trastorno del Espectro Autista, que en muchos casos no saben leer. Su función principal es avisar al niño de que empieza una actividad y mostrarle el pictograma correspondiente. El aviso debe presentar esa imagen a pantalla completa aunque el dispositivo esté bloqueado, porque una notificación de texto no cumple ninguna función para el usuario final. El permiso se usa exclusivamente para los avisos de actividad que el adulto ha programado, nunca con fines publicitarios ni promocionales.

Qué implica en la práctica:

- Con el dispositivo bloqueado o en reposo, Android enciende la pantalla y abre el tablero, con el pictograma de la actividad en grande.
- Con el dispositivo en uso, aparece como notificación prominente.
- El sonido se repite (`FLAG_INSISTENT`) hasta que un adulto pulsa «Entendido», y en ese momento la notificación se retira.

**Sigue sin declararse `USE_EXACT_ALARM`**, que es el otro permiso restringido. La puntualidad exacta se pide al usuario con `SCHEDULE_EXACT_ALARM`, que él concede desde los ajustes del sistema.

Si Google rechazara la declaración, revertirlo es quitar una línea del manifest y la llamada a `setFullScreenIntent`; la aplicación seguiría funcionando con la notificación sola.

---

## 3. Aplicación dirigida a menores — lo que más se revisa

Pictorario está pensada para niños. Eso activa la **política de Familias**, que es de las más estrictas de Play. Puntos a cubrir en la Console:

- [ ] **Público objetivo y contenido**: declarar el rango de edad. Al incluir menores de 13 años, se aplican requisitos adicionales.
- [x] **Política de privacidad**: redactada en `docs/pictorarioprivacy.html`. **Hay que subirla a `https://ganso.org/pictorarioprivacy.html`** y poner esa URL en Play Console. La aplicación ya la enlaza desde «Acerca de».
- [ ] **Formulario de seguridad de los datos**: declarar la búsqueda en ARASAAC como transmisión a un tercero. Es puntual, iniciada por el adulto y sin identificadores, pero **hay que declararla igual**.
- [ ] **Clasificación de contenido**: rellenar el cuestionario. Sin violencia, sin compras, sin contenido generado por usuarios.
- [ ] **Sin publicidad**: marcarlo. La aplicación no tiene ninguna.
- [ ] **Enlaces externos**: la pantalla «Acerca de» abre `ganso.org`, `arasaac.org`, el blog del proyecto y un vídeo de YouTube. En aplicaciones para menores los enlaces externos se revisan; están todos tras texto explicativo y fuera del alcance habitual del niño (la pantalla desaparece con el bloqueo parental activo). **Valorar si conviene mantenerlos.**

---

## 4. Lo que hace la aplicación con los datos

Resumen para rellenar el formulario de seguridad de los datos:

| Dato | Se recoge | Se comparte | Dónde vive |
|---|---|---|---|
| Horarios y actividades creados por el adulto | No sale del dispositivo | No | `files/pictorario.json`, almacenamiento privado |
| Pictogramas descargados | No sale del dispositivo | No | `files/pictogramas/`, almacenamiento privado |
| Texto de búsqueda de pictogramas | — | **Sí, a ARASAAC**, sólo al pulsar buscar | No se almacena |
| Texto leído en voz alta | — | No | El motor de voz es el del propio dispositivo |
| Copia de seguridad exportada por el adulto | Sólo si la pide | La lleva el adulto a donde quiera | Fichero elegido en el selector del sistema |
| Identificadores, ubicación, contactos, cuentas | **Nada de esto** | — | — |

No hay analítica, ni SDK de terceros, ni publicidad, ni cuentas de usuario. Las únicas dependencias son AndroidX y kotlinx.serialization.

La copia de seguridad de Android incluye `pictorario.json` (para no perder los horarios al cambiar de móvil) y excluye los pictogramas descargados, que se pueden volver a obtener. Ver `res/xml/backup_rules.xml`.

La 2.0 permite además que el adulto **guarde y recupere una copia en un fichero JSON**, mediante el selector del sistema (*Storage Access Framework*). No se declara ningún permiso de almacenamiento: el sistema entrega un `Uri` ya autorizado por el usuario para el fichero concreto que él ha elegido. El fichero contiene únicamente lo que el adulto ha escrito —descripciones y horas— y sale del dispositivo sólo si él lo lleva ahí.

La **lectura en voz alta** usa `android.speech.tts.TextToSpeech`, es decir, el motor instalado en el dispositivo. No requiere permiso, no graba nada y no envía texto a ningún servicio propio; lo que el motor del sistema haga con él depende del motor que el usuario tenga configurado. En el formulario de seguridad de los datos no hay nada que declarar por esto.

---

## 5. Antes de cada envío

- [ ] Subir `versionCode` en `app/build.gradle.kts`.
- [ ] `./gradlew test` en verde.
- [ ] `./build_and_copy.sh` genera APK de depuración y AAB de publicación.
- [ ] Probar el AAB de publicación en un dispositivo real, no sólo el APK de depuración: **R8 sólo actúa en release**.
- [ ] Comprobar que la lista de permisos del AAB es exactamente la de la sección 2.
- [ ] Notas de la versión copiadas de `VERSION_CHANGES` — ver la sección 5 bis.
- [ ] Repasar las seis pantallas en las cuatro combinaciones de tamaño y orientación: móvil vertical y horizontal, tablet vertical y horizontal. La 2.0 ya no fija la aplicación a `portrait`.
- [ ] Capturas de la ficha al día: Play pide juegos separados para teléfono y para tablet de 7\" y 10\". Están en `docs/play/`.
- [ ] Subir `app/build/outputs/mapping/release/mapping.txt` en «Desofuscar archivos», o los informes de fallos llegarán ilegibles.

Los textos de la ficha y los cuestionarios de la Console están resueltos en [`docs/play/ficha.md`](docs/play/ficha.md).

Para ver los permisos que realmente lleva el artefacto:

```bash
~/Android/Sdk/build-tools/*/aapt2 dump permissions app/build/outputs/apk/release/app-release.apk
```

---

## 5 bis. Notas de la versión 2.0

Google Play limita este campo a **500 caracteres**. El texto ya está escrito y
vive en el código, como `VERSION_CHANGES` en `ui/about/AboutScreen.kt`: es el
mismo que la aplicación muestra al arrancar tras actualizar y desde el número de
versión de «Acerca de». **Cópialo de ahí**, para que la ficha y la aplicación no
puedan decir cosas distintas. Hoy son 455 caracteres:

```
Pictorario vuelve, reescrito por completo para funcionar en los Android de hoy.

• Lectura en voz alta de las actividades.
• Copia de seguridad: guarda tus horarios y recupéralos en otro móvil.
• El aviso abre el horario a pantalla completa al empezar una actividad.
• Diseño para tablet y para pantalla horizontal.
• Mantén pulsado cualquier botón para ver qué hace.
• Corregidos errores del reloj y de los horarios.

Fallos y sugerencias: javi@ganso.org
```

No se avisa de que la configuración se reinicia. La ficha lleva años retirada de
Play, así que nadie está actualizando desde la 1.07: para quien la instale ahora
es una aplicación nueva, y el aviso sólo sembraría una duda sin motivo. El
detalle sigue anotado en el README y en [CHANGELOG.md](CHANGELOG.md) para quien
venga del proyecto.

El relato completo de la versión está en [CHANGELOG.md](CHANGELOG.md).

### Versión larga, por si hace falta en otro sitio

Ver [CHANGELOG.md](CHANGELOG.md), que es el relato completo y el sitio donde se
mantiene. Aquí no se duplica: dos textos largos que dicen lo mismo acaban
diciendo cosas distintas.

---

## 6. Pendiente de comprobación

Estos puntos dependen de información que sólo está en Play Console o que conviene contrastar contra la política vigente en el momento de subir:

- **Subir la política de privacidad** a `https://ganso.org/pictorarioprivacy.html` y declararla en Play Console.
- **Nivel de API mínimo exigido** en la fecha del envío: Play lo sube cada año, y el proyecto va con `targetSdk 36`. Verificar que sigue siendo suficiente.
