# Publicación en Google Play

> **La ficha de Pictorario fue retirada de Google Play por incumplimiento de políticas**, al no haberse actualizado al nivel de API exigido. Este documento existe para que la primera versión que se suba cumpla sin margen de duda. La regla de trabajo es **elegir siempre la opción más conservadora**, aunque cueste funcionalidad.

---

## 0. Lo primero de todo: ¿se puede recuperar la ficha?

**Esto hay que resolverlo antes de escribir una sola línea más de código de producto.** Determina si la migración se publica como actualización o como aplicación nueva.

En [Play Console](https://play.google.com/console), con la cuenta del desarrollador:

1. **¿Aparece Pictorario en la lista de aplicaciones?**
   - Si aparece como *removed* / *retirada*, normalmente se puede reinstaurar subiendo una versión que cumpla y solicitando la revisión. Es el mejor escenario: se conservan instalaciones, reseñas e historial.
   - Si aparece como *suspended* / *suspendida*, hay que apelar antes de poder subir nada.
   - Si **no aparece** o el estado es *terminated*, el nombre de paquete `javi.prieto.pictorario` puede haber quedado **bloqueado para siempre**. En ese caso hay que publicar ficha nueva con otro `applicationId`.
2. **Revisar el estado de la cuenta** en *Política → Estado de la aplicación* y el histórico de avisos. Si la cuenta acumula avisos, cualquier envío nuevo se revisa con más severidad.
3. **Comprobar la clave de subida** en *Prueba y lanzamiento → Integridad de la aplicación* (ver la sección «Firma de la aplicación» de [MIGRACION.md](MIGRACION.md)).

**Hasta que esto esté aclarado, el `applicationId` del proyecto es provisional.** Cambiarlo es una línea en `app/build.gradle.kts`.

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
| Firma | Misma clave de subida registrada | `app/upload-keystore.jks`, alias `b4a` | ⚠️ **verificar huella en Console** |

**Aviso sobre la clave:** es **DSA de 1024 bits**, que `keytool` marca como débil y que Play no acepta como clave de firma de aplicación en Play App Signing (exige RSA de 2048 o más). Si la ficha no está ya enrolada, habrá que generar una **clave de subida RSA nueva**. Los comandos están en MIGRACION.md.

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
| `SCHEDULE_EXACT_ALARM` | **Opcional.** Sin él la alarma salta igual, con una ventana de 5 min | Bajo: no se concede solo, lo activa el adulto desde los ajustes del sistema |

### Lo que se ha descartado a propósito

| Permiso | Por qué se descarta |
|---|---|
| **`USE_EXACT_ALARM`** | Google Play lo reserva a aplicaciones cuya **función principal** es despertador o calendario. Pictorario es una agenda visual: defendible, pero discutible para un revisor. Una declaración rechazada bloquearía la republicación, que es justo lo que no nos podemos permitir. |
| **`USE_FULL_SCREEN_INTENT`** | Igualmente restringido a aplicaciones de alarma y de llamadas, y desde Android 14 se revoca por defecto al resto. Era la forma de reproducir el comportamiento original de abrir la pantalla del reloj sobre el dispositivo bloqueado. |
| `FOREGROUND_SERVICE` | Ya no hace falta ningún servicio: la alarma la atiende un `BroadcastReceiver`. Esto esquiva además los tipos obligatorios de servicio en primer plano de API 34+. |
| `WAKE_LOCK` | Innecesario con `AlarmManager`. |
| `WRITE_EXTERNAL_STORAGE`, `WRITE_SETTINGS` | Estaban en la versión B4A **sin usarse**. Eliminados. |

**Consecuencia funcional que hay que aceptar:** cuando llega la hora de una actividad, la versión B4A abría el reloj a pantalla completa. Ahora aparece una notificación prominente con sonido y vibración que abre el reloj al tocarla. Es la diferencia entre pasar la revisión y arriesgarse a no pasarla. Si más adelante la ficha está restablecida y con buen historial, se puede reconsiderar declarar `USE_FULL_SCREEN_INTENT`.

---

## 3. Aplicación dirigida a menores — lo que más se revisa

Pictorario está pensada para niños. Eso activa la **política de Familias**, que es de las más estrictas de Play. Puntos a cubrir en la Console:

- [ ] **Público objetivo y contenido**: declarar el rango de edad. Al incluir menores de 13 años, se aplican requisitos adicionales.
- [ ] **Política de privacidad**: **obligatoria**, con URL pública y accesible. No es opcional para aplicaciones dirigidas a menores aunque no se recoja ningún dato. Debe explicar que:
  - la aplicación **no recoge ni transmite datos personales**;
  - los horarios se guardan **sólo en el dispositivo**;
  - al buscar un pictograma se envía **únicamente el texto tecleado** a `api.arasaac.org`, sin identificador alguno;
  - no hay cuentas, publicidad, analítica ni rastreo.
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
| Identificadores, ubicación, contactos, cuentas | **Nada de esto** | — | — |

No hay analítica, ni SDK de terceros, ni publicidad, ni cuentas de usuario. Las únicas dependencias son AndroidX y kotlinx.serialization.

La copia de seguridad de Android incluye `pictorario.json` (para no perder los horarios al cambiar de móvil) y excluye los pictogramas descargados, que se pueden volver a obtener. Ver `res/xml/backup_rules.xml`.

---

## 5. Antes de cada envío

- [ ] Subir `versionCode` en `app/build.gradle.kts`.
- [ ] `./gradlew test` en verde.
- [ ] `./build_and_copy.sh` genera APK de depuración y AAB de publicación.
- [ ] Probar el AAB de publicación en un dispositivo real, no sólo el APK de depuración: **R8 sólo actúa en release**.
- [ ] Comprobar que la lista de permisos del AAB es exactamente la de la sección 2.
- [ ] Notas de la versión avisando de que **la configuración se reinicia** al venir de la 1.x.

Para ver los permisos que realmente lleva el artefacto:

```bash
~/Android/Sdk/build-tools/*/aapt2 dump permissions app/build/outputs/apk/release/app-release.apk
```

---

## 6. Pendiente de comprobación

Estos puntos dependen de información que sólo está en Play Console o que conviene contrastar contra la política vigente en el momento de subir:

- **Estado de la ficha y del nombre de paquete** (sección 0). Es lo que más condiciona todo lo demás.
- **Huella de la clave de subida** y si la clave DSA sirve.
- **Texto de la política de privacidad** y dónde alojarla.
- **Nivel de API mínimo exigido** en la fecha del envío: Play lo sube cada año, y el proyecto va con `targetSdk 36`. Verificar que sigue siendo suficiente.
