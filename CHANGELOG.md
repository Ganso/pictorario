# Registro de cambios

## 2.0

Primera versión desde la 1.07, y un salto completo: Pictorario se ha reescrito
de cero en **Kotlin con Jetpack Compose**. La versión anterior estaba hecha en
B4A con un nivel de API que Google Play dejó de admitir, lo que obligó a retirar
la aplicación de la tienda; ésta vuelve a cumplir y podrá seguir actualizándose.

Se conserva la misma idea y la misma interfaz —el reloj con sectores de colores,
los pictogramas de ARASAAC, las secuencias de actividades—, con todo lo que
sigue añadido o corregido.

### Novedades

**Accesibilidad**

- **Lectura en voz alta.** Con el ajuste activado, la aplicación dice el nombre
  de la actividad al saltar su alarma, al tocar su pictograma en el reloj o en
  el carrusel, y el de la secuencia al abrirla. Acompaña la visualización, que
  es la pantalla que el niño usa por su cuenta; la configuración y el editor no
  hablan. Usa el motor de voz del propio dispositivo: no envía nada a ningún
  sitio.
- **Ayuda al mantener pulsado.** Todos los botones e iconos explican qué hacen
  si se mantiene el dedo encima. El mismo texto sirve de descripción para los
  lectores de pantalla.

**Presentación**

- **Diseño para tablet y para pantalla horizontal.** La aplicación ya no está
  fijada a vertical. En horizontal la pantalla se reparte en tres: el reloj, los
  botones con la hora, y la actividad. En tablet las secuencias se muestran en
  dos columnas y los pictogramas se descargan en alta resolución.
- **Indicadores en la portada.** Cada secuencia muestra un icono de alarma si
  tiene avisos activados, y un punto verde con el nombre de la actividad cuando
  alguna está transcurriendo. La portada se actualiza sola cada minuto.

**Horarios**

- **Copia de seguridad y traspaso.** Se pueden guardar todas las secuencias en
  un fichero —en Google Drive, por ejemplo— y recuperarlas en otro dispositivo,
  reemplazando las que haya o sumándolas. Al recuperar, los pictogramas que
  falten se descargan solos.
- **Actividades hasta las 24:00.** Antes el día terminaba a las 23:59 y el
  último minuto quedaba fuera de toda actividad. Ahora se puede elegir las doce
  de la noche como hora de fin.
- **Aviso cuando una hora no se puede aplicar.** Si la hora elegida choca con
  otra actividad, la aplicación lo dice en lugar de cambiarla en silencio.

**Alarmas**

- **El aviso vuelve a funcionar.** Cuando empieza una actividad, la notificación
  enciende la pantalla y muestra el horario con el pictograma en grande, incluso
  con el móvil bloqueado. Desde Android 10 el mecanismo que usaba la versión
  anterior —arrancar una pantalla desde un servicio— está bloqueado por el
  sistema.
- **La alarma se reprograma** también al cambiar la hora del dispositivo, al
  cambiar de zona horaria y al actualizar la aplicación, no sólo al reiniciar.
- **Puntualidad exacta opcional.** Si el sistema no la concede sola, la portada
  ofrece pedirla; sin ella los avisos siguen llegando, con un pequeño margen.

**Bloqueo parental**

- El bloqueo oculta la edición, pero **el niño sigue pudiendo abrir y cerrar sus
  secuencias** por su cuenta. Antes la única salida del horario era el gesto de
  desbloqueo, que es cosa del adulto.
- Al activarlo se explica en dos pantallas qué hace y, sobre todo, **cómo se
  quita**: no hay otra manera que el toque corto seguido de mantener pulsado.
- El botón Atrás funciona con normalidad en toda la aplicación. La versión
  anterior lo anulaba.

### Correcciones respecto a la versión 1.07

| Qué fallaba | Qué hace ahora |
|---|---|
| Las agujas del reloj se dibujaban elípticas | Circulares |
| Cambiar el tipo de tablero no se guardaba | Se guarda |
| La actividad número 20 salía transparente: faltaba un color en la paleta | Los veinte colores |
| El temporizador del reloj seguía corriendo con la aplicación en segundo plano | Se para al salir de la pantalla |
| Un toque en cualquier punto de la pantalla podía seleccionar una actividad | Sólo dentro de su sector del reloj |
| Los sectores que cruzaban las doce salían vacíos o invertidos | Se dibujan correctamente |
| Cambiar la hora de fin no comparaba bien los minutos, y podía dejar actividades de duración negativa | Comparación correcta de hora y minuto |
| La medianoche se escribía `00:00 a.m.` y el mediodía `00:00 p.m.` | Ambos son `12:00` |
| Una secuencia de una sola hora podía dividir por cero | Corregido |
| El borde exterior de los sectores era poligonal | Arco real |
| La búsqueda de pictogramas se cortaba a 100 resultados y los descargaba de uno en uno | Sin tope artificial, y en paralelo |
| Se declaraban cuatro permisos que no se usaban | Eliminados |
| Seis pictogramas de ejemplo pesaban 25 veces lo necesario | Recomprimidos |

### Notas técnicas

- El proyecto original en B4A se conserva íntegro en [`b4a/`](b4a/) como
  referencia y memoria del proyecto. No se compila.
- Los datos de la versión 1.x no se migran: se guardaban en un formato propio de
  B4A. Al actualizar, la aplicación arranca con las secuencias de ejemplo.
- Permisos declarados: internet, vibración, reprogramar alarmas tras reiniciar,
  notificaciones, alarmas exactas (opcional) y apertura a pantalla completa para
  el aviso. Ni la lectura en voz alta ni la copia de seguridad añadieron
  ninguno. Ver [PUBLICACION.md](PUBLICACION.md).
- Los datos no salen del dispositivo. Lo único que se envía a un tercero es el
  texto que el adulto teclea al buscar un pictograma, que va a ARASAAC sin
  identificador alguno.

---

## 1.07 y anteriores

La versión B4A, publicada entre 2015 y 2019. Su historial está en los mensajes
de commit anteriores a la reescritura y en [`b4a/README.md`](b4a/README.md).
