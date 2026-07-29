# Batería de pruebas antes de publicar

Pensada para pasarla **en un móvil real**, no en el emulador. Marca lo que vayas comprobando y anota cualquier cosa rara aunque parezca menor.

Con el móvil conectado por USB:

```bash
./build_and_copy.sh
```

compila, instala el APK de depuración y abre la aplicación. Para sólo compilar, `--dry-run`.

> **Ojo:** el APK de depuración se instala como `javi.prieto.pictorario.debug` y **convive** con la versión publicada, lo que va bien para comparar. Pero el permiso de alarmas exactas y el de notificaciones se conceden por separado a cada uno.
>
> **Lo que se sube a producción hay que probarlo como AAB desde un canal de pruebas internas de Play**, no sólo con el APK de depuración: R8 sólo actúa en release y puede sacar a la luz problemas que en depuración no aparecen.

---

## 1. Primer arranque

- [ ] La aplicación arranca y muestra el aviso de novedades. Al aceptarlo no vuelve a salir.
- [ ] Aparecen las tres secuencias de ejemplo con sus pictogramas.
- [ ] Android pide permiso de notificaciones. **Pruébalo también denegándolo**: la aplicación debe seguir funcionando con normalidad, sólo sin avisos.
- [ ] El icono en el lanzador se ve bien, sin recortes raros.

## 2. La pantalla del reloj

- [ ] Al tocar una secuencia se abre su tablero.
- [ ] Los cuatro tipos de tablero se ven correctamente al pulsar el botón de la derecha: reloj de mañana, de tarde, de 24 horas y secuencia completa. Cada vez sale el aviso «Cambiando vista a…».
- [ ] **El tipo de tablero se conserva** al salir y volver a entrar.
- [ ] Tocando un sector de color se selecciona esa actividad: se marca en rojo, aparece su pictograma en el centro y el carrusel de abajo salta a ella.
- [ ] Deslizando el carrusel se selecciona el sector correspondiente en el reloj.
- [ ] La tira de pictogramas de abajo muestra **todas** las actividades y resalta la seleccionada.
- [ ] Con una actividad en curso, la barra de progreso avanza.
- [ ] Con el tablero de mañana sólo se ven las actividades de la mañana; con el de tarde, las de la tarde.

## 3. El editor

- [ ] Crear una secuencia nueva desde cero: descripción, pictograma, tipo de tablero, tamaño de iconos y varias actividades.
- [ ] Cada actividad muestra **«Desde» y «Hasta»** y su engranaje, los tres en la misma fila.
- [ ] Cambiar una hora abre el selector y respeta el formato de 12 o 24 horas configurado.
- [ ] **Provocar un conflicto**: alargar una actividad hasta pisar la siguiente. Debe salir «No se ha podido poner esa hora: se solapaba con «…»» y la hora queda recortada.
- [ ] Poner a una actividad una hora de inicio posterior a su fin: el fin se mueve solo y se avisa.
- [ ] Mover una actividad a una hora que la coloque antes de otra: se reordena sola.
- [ ] Borrar una actividad desde su engranaje.
- [ ] «Cancelar» pide confirmación y descarta los cambios de verdad.
- [ ] «Aceptar» guarda, y al volver a entrar todo sigue ahí.

## 4. Pictogramas

- [ ] Al tocar un pictograma en el editor se abre el buscador, que muestra los ya descargados.
- [ ] **Buscar una palabra** (por ejemplo «perro»): descarga y muestra resultados.
- [ ] Elegir uno lo aplica a la actividad o a la secuencia.
- [ ] **Probar sin conexión** (modo avión): la búsqueda avisa de que no se pudo conectar, sin cerrarse.
- [ ] Los pictogramas descargados siguen ahí tras cerrar y abrir la aplicación.

## 5. Alarmas — lo que estaba fallando

Esta es la parte que más conviene mirar.

### 5.1 Con el permiso de alarmas exactas concedido

- [ ] En una secuencia, activar «Activar alarmas» y poner una actividad **2 minutos** en el futuro.
- [ ] En la portada aparece «Próxima alarma» con la hora correcta.
- [ ] Si sale el aviso amarillo «Las alarmas pueden llegar tarde», pulsar el botón, activar «Alarmas y recordatorios» en los ajustes de Android y volver. **El aviso debe desaparecer.**
- [ ] Bloquear el móvil y esperar. A la hora exacta **la pantalla debe encenderse sola y aparecer el tablero** con un aviso grande: «¡Empieza ahora!», el pictograma, el nombre de la actividad y hasta qué hora dura.
- [ ] El sonido **se repite** hasta pulsar «Entendido».
- [ ] Al pulsar «Entendido» el aviso desaparece, la notificación de alarma se retira y queda el tablero con esa actividad seleccionada.
- [ ] **No deben quedar dos notificaciones**: sólo la de «Próxima actividad», ya apuntando a la siguiente o a mañana.
- [ ] Repetirlo con el móvil desbloqueado y en uso: debe salir como notificación prominente en la parte superior.

### 5.2 Sin el permiso de alarmas exactas

- [ ] Revocarlo en *Ajustes de Android → Aplicaciones → Pictorario → Alarmas y recordatorios*.
- [ ] Repetir la prueba anterior. **El aviso debe salir igualmente**, aunque pueda retrasarse algún minuto si el móvil llevaba rato en reposo.
- [ ] Comprobar que el aviso amarillo vuelve a aparecer en la portada.

### 5.3 Casos límite

- [ ] **Reiniciar el móvil** con una alarma programada: al arrancar, la notificación de «Próxima actividad» debe volver a aparecer sola.
- [ ] **Cambiar la hora del sistema** hacia delante, pasando por encima de la hora de una alarma.
- [ ] Desactivar el interruptor general de alarmas en Configuración: la notificación de próxima actividad desaparece.
- [ ] Con varias secuencias con alarma, se anuncia siempre la más próxima.

## 6. Indicadores de la portada

- [ ] Una secuencia con alarmas activadas muestra el icono de alarma.
- [ ] Al apagar el interruptor general, **ese icono desaparece de todas**.
- [ ] Una secuencia con una actividad en curso muestra el punto verde y el nombre de la actividad.
- [ ] Espera a que termine esa actividad: el punto verde debe desaparecer o pasar a la siguiente sin tocar nada.

## 7. Bloqueo parental

- [ ] Al activarlo en Configuración salen **dos** pantallas seguidas: qué hace el bloqueo, y cómo se quita —con «IMPORTANTE» en negrita—.
- [ ] Con el candado puesto, el niño **sí** puede abrir una secuencia y cerrarla con «Cerrar visualización»; lo que no puede es editar ni cambiar la vista del tablero.
- [ ] El candado aparece abajo, junto a «Salir» en la portada y junto a «Cerrar visualización» en el reloj; nunca flotando en una esquina.
- [ ] El gesto —toque corto y después mantener— quita el candado desde cualquiera de las dos pantallas, y **no** cambia de pantalla al hacerlo.

- [ ] Activarlo en Configuración. Sale la explicación del gesto.
- [ ] En la portada desaparecen los engranajes y los botones de crear, configurar y «Acerca de». Queda «Salir» y el candado.
- [ ] En el tablero desaparece «Cerrar visualización».
- [ ] **El botón Atrás sigue funcionando** y devuelve a la portada.
- [ ] Desbloquear con el gesto: un toque corto en el candado (vibra) y después mantener pulsado (vibra más). Vuelven a aparecer los controles.

## 8. Ayuda al mantener pulsado

- [ ] Mantener el dedo sobre cualquier botón o icono muestra una explicación.
- [ ] **Al soltar no se ejecuta la acción**: mantener pulsado «Configuración» no debe abrir Configuración.
- [ ] Un toque corto sí actúa con normalidad.
- [ ] Probarlo en las seis pantallas: portada, reloj, editor, buscador, configuración y «Acerca de».

## 9. Configuración y «Acerca de»

- [ ] Cambiar el formato horario entre 12 y 24 horas y ver que se refleja en el reloj y en el editor.
- [ ] Cambiar los tres colores de las agujas y comprobarlo en un tablero con hora, minutos y segundos.
- [ ] «Reiniciar configuración» pide confirmación y devuelve las secuencias de ejemplo.
- [ ] Una actividad puede terminar a las 24:00: en «Hasta» se eligen las 12 de la noche y el botón pasa a mostrar `24:00` (o `12:00 de la noche` en formato de 12 horas). El sector llega hasta el final en las cuatro vistas de tablero.
- [ ] En «Acerca de», los enlaces abren el navegador: web del autor, ARASAAC, proyecto, vídeo y **política de privacidad**.
- [ ] «Para Teo» se ve con la letra manuscrita.
- [ ] Pulsando la versión salen las novedades.

## 9 bis. Lectura en voz alta

- [ ] Con el interruptor apagado, ningún toque habla.
- [ ] Encendido: al abrir una secuencia dice su nombre; al tocar un pictograma del reloj o una miniatura del carrusel dice la actividad; al saltar una alarma lee la actividad y su hora de fin.
- [ ] **Deslizar** el carrusel no habla: la voz responde al dedo sobre un pictograma, no al paso de páginas.
- [ ] En el tablero, «Cerrar visualización» y el botón de cambiar vista dicen su nombre al pulsarlos.
- [ ] **Configuración, editor y selector de pictogramas están en silencio**: la voz es para el niño, no para quien configura.
- [ ] La pulsación larga sigue mostrando la ayuda, y el toque corto sigue haciendo lo suyo: la voz no se come ni cambia ninguna acción.
- [ ] Con el volumen de multimedia a cero, la aplicación sigue funcionando con normalidad.
- [ ] En un dispositivo sin motor de voz o sin la voz española instalada, tampoco falla nada: simplemente no habla.

## 9 ter. Copia de seguridad

- [ ] «Guardar copia» abre el selector del sistema con el nombre `pictorario-AAAA-MM-DD.json` y guarda el fichero donde se le diga, Google Drive incluido.
- [ ] El fichero guardado es idéntico a `files/pictorario.json`.
- [ ] «Recuperar copia» ofrece reemplazar o añadir, y hace lo que dice en cada caso.
- [ ] Recuperar en un dispositivo que no tenga esos pictogramas los descarga, con barra de progreso; sin conexión avisa y las secuencias entran igualmente.
- [ ] Un fichero que no sea una copia de Pictorario se rechaza **sin tocar** las secuencias existentes.
- [ ] Los colores de las agujas, la protección y la lectura en voz alta **no** viajan en la copia: son de cada dispositivo.

## 9 quater. Tablet y horizontal

- [ ] Girar el móvil: la esfera cabe entera, con los botones y la hora en una columna propia y la actividad a la derecha.
- [ ] En tablet, la portada muestra las secuencias en dos columnas.
- [ ] Repasar las seis pantallas en las cuatro combinaciones: móvil vertical y horizontal, tablet vertical y horizontal.
- [ ] En tablet aparece «Mejorar calidad de los pictogramas»; al pulsarlo se re-descargan y los ficheros de `files/pictogramas/` pasan de unos 15 kB a unos 200 kB.
- [ ] En móvil ese botón **no** aparece.

## 10. Comportamiento general

- [ ] Girar el móvil no pierde el estado: la secuencia abierta y la actividad seleccionada siguen ahí.
- [ ] Salir con Atrás desde la portada cierra la aplicación.
- [ ] Volver a abrirla conserva todo.
- [ ] Dejar la aplicación en segundo plano un rato y volver: el reloj sigue en hora.
- [ ] **Con poca batería o en ahorro de energía**, comprobar que las alarmas siguen llegando.

---

## Antes de subir a producción

- [ ] `versionCode` mayor que 107 — ahora mismo **200**.
- [ ] `./gradlew test` en verde.
- [ ] Probar el **AAB de publicación** desde un canal de pruebas internas, no sólo el APK de depuración: R8 sólo actúa en release y puede sacar a la luz problemas que en depuración no aparecen.
- [ ] Política de privacidad publicada en `https://ganso.org/pictorarioprivacy.html` y declarada en Play Console.
- [ ] Clave de subida restablecida — ver [PUBLICACION.md](PUBLICACION.md), sección 1 bis.
- [ ] Notas de la versión avisando de que **la configuración se reinicia** al venir de la 1.x.
