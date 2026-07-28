# Batería de pruebas antes de publicar

Pensada para pasarla **en un móvil real**, no en el emulador. Marca lo que vayas comprobando y anota cualquier cosa rara aunque parezca menor.

Instala con:

```bash
./build_and_copy.sh --dry-run
```

y pasa el APK al móvil, o conéctalo por USB y usa `./build_and_copy.sh` directamente.

> **Ojo:** el APK de depuración se instala como `javi.prieto.pictorario.debug`, así que **convive** con la versión publicada. Para probar el comportamiento real de publicación, instala el AAB desde un canal de pruebas internas de Play.

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
- [ ] Bloquear el móvil y esperar. **A la hora exacta debe sonar el aviso**, con vibración y con el nombre de la actividad.
- [ ] Al tocar la notificación se abre el tablero de esa secuencia.
- [ ] Después del aviso, la notificación de «Próxima actividad» pasa a la siguiente (o a mañana).

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
- [ ] En «Acerca de», los enlaces abren el navegador: web del autor, ARASAAC, proyecto, vídeo y **política de privacidad**.
- [ ] «Para Teo» se ve con la letra manuscrita.
- [ ] Pulsando la versión salen las novedades.

## 10. Comportamiento general

- [ ] Girar el móvil: la aplicación se mantiene en vertical.
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
