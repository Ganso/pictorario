﻿B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.8
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals

	Dim CambiosVersion As String
	CambiosVersion= _
	"- Se mantienen las actividades ordenadas automáticamente."&CRLF&CRLF& _
	"- Se comprueba que una actividad no se solape con la siguiente, y otros tipos de problema con la asignación de horas."&CRLF&CRLF& _
	"- Cuando se actualiza la aplicación, se muestran las novedades."&CRLF&CRLF& _
	"- Correcciones menores en el interfaz para hacerlo más homogéneo."

	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

	Dim kvs As KeyValueStore

	''' TIPOS PERSONALIZADOS

	Type Actividad ( hora_inicio As Int, minuto_inicio As Int, hora_fin As Int, minuto_fin As Int, Pictograma As String, Descripcion As String )
	
	Type Tablero ( tipo As Int, indicar_hora As Int, tam_icono As Int )
	' Tipo 0: Reloj 12h AM
	' Tipo 1: Reloj 12h PM
	' Tipo 2: Reloj 24h
	' Tipo 3: Secuencia
	' Indicar Hora 0: No indicar nada
	' Indicar Hora 1: Solo hora
	' Indicar Hora 2: Hora y minuto
	' Indicar Hora 3: Hora, minuto y segundos
	' Tamaño de icono: Porcentaje del ancho de pantalla que ocupa el icono (valor medio, 10)
	
	Type Secuencia ( Descripcion As String, tablero As Tablero, pictograma As String, num_actividades As Int )
	
	''' VALORES MÁXIMOS
	
	Dim MaxSecuencias=10 As Int 'Número máximo de secuencias
	Dim MaxActividades=20 As Int 'Número máximo de actividades por secuencia

	''' TABLEROS
	
	Dim DescripcionTablero(4) As String
	DescripcionTablero = Array As String("Reloj de 12h (mañana)","Reloj de 12h (tarde)","Reloj de 24h","Arco de secuencia")

	Dim DescripcionMinutero(4) As String
	DescripcionMinutero = Array As String("Sin indicación","Indicar hora","Indicar hora y minutos","Indicar hora, minutos y segundos")

	''' COLORES
	
	Dim MaxColores=20 As Int
	Dim Colores(MaxColores) As Int 'Colores para las áreas
	Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFFffffba,0xFFbaffc9,0xFFbae1ff,0xFFffbaff,0xFFdfffba,0xFFbaffc9,0xFFbae1ff,0xFFffe1b1,0xFFbaffe1,0xFFffb3ba,0xFFffdfba,0xFFffffba,0xFFbaffc9,0xFFbae1ff,0xFFffbaff,0xFFdfffba,0xFFbaffc9,0xFFbae1ff)

	''' CONFIGURACIÓN

	Dim NumSecuencias As Int 'Número de secuencias
	Dim SecuenciaActiva As Int
	Dim Secuencia(MaxSecuencias+1) As Secuencia
	Dim ActividadSecuencia(MaxSecuencias+1,MaxActividades) As Actividad 'Array bidimensional de actividades
	Dim VersionInstalada As Int

End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.

	NumSecuencias=0
	kvs.Initialize(File.DirInternal, "configuracion")
	
	Cargar_Configuracion

	'Inicializar_Con_Ejemplo
	'Guardar_Configuracion
	
End Sub

Sub Guardar_Configuracion
	kvs.Put("NumSecuencias", NumSecuencias)
	For i=0 To NumSecuencias-1
		kvs.Put("Secuencia."&i, Secuencia(i))
		For j=0 To Secuencia(i).num_actividades
			kvs.Put("ActividadSecuencia."&i&"."&j, ActividadSecuencia(i,j))
		Next
	Next
	kvs.Put("VersionInstalada", Application.VersionCode)
End Sub

Sub Cargar_Configuracion
	NumSecuencias=kvs.GetDefault("NumSecuencias",0)
	If NumSecuencias==0 Then
		Inicializar_Con_Ejemplo
		Guardar_Configuracion
	Else
		For i=0 To NumSecuencias-1
			Secuencia(i)=kvs.Get("Secuencia."&i)
			For j=0 To Secuencia(i).num_actividades
				ActividadSecuencia(i,j)=kvs.Get("ActividadSecuencia."&i&"."&j)
			Next
		Next
	End If
	VersionInstalada=kvs.GetDefault("VersionInstalada",-1)
	Log("Versión instalada: "&VersionInstalada)
End Sub

Sub Inicializar_Con_Ejemplo

	NumSecuencias=1
	
	'Secuencia 0
	
	Secuencia(0).Initialize
	
	Secuencia(0).num_actividades=9
	
	ActividadSecuencia(0,0).hora_inicio=8
	ActividadSecuencia(0,0).minuto_inicio=0
	ActividadSecuencia(0,0).hora_fin=8
	ActividadSecuencia(0,0).minuto_fin=15
	ActividadSecuencia(0,0).pictograma="despertar_1"
	ActividadSecuencia(0,0).descripcion="Despertarse"
	
	ActividadSecuencia(0,1).hora_inicio=8
	ActividadSecuencia(0,1).minuto_inicio=15
	ActividadSecuencia(0,1).hora_fin=8
	ActividadSecuencia(0,1).minuto_fin=30
	ActividadSecuencia(0,1).pictograma="vestirse"
	ActividadSecuencia(0,1).descripcion="Vestirse"
	
	ActividadSecuencia(0,2).hora_inicio=8
	ActividadSecuencia(0,2).minuto_inicio=30
	ActividadSecuencia(0,2).hora_fin=9
	ActividadSecuencia(0,2).minuto_fin=0
	ActividadSecuencia(0,2).pictograma="desayunar"
	ActividadSecuencia(0,2).descripcion="Desayunar"
	
	ActividadSecuencia(0,3).hora_inicio=9
	ActividadSecuencia(0,3).minuto_inicio=0
	ActividadSecuencia(0,3).hora_fin=14
	ActividadSecuencia(0,3).minuto_fin=0
	ActividadSecuencia(0,3).pictograma="colegio"
	ActividadSecuencia(0,3).descripcion="Cole"
		
	ActividadSecuencia(0,4).hora_inicio=14
	ActividadSecuencia(0,4).minuto_inicio=0
	ActividadSecuencia(0,4).hora_fin=15
	ActividadSecuencia(0,4).minuto_fin=0
	ActividadSecuencia(0,4).pictograma="comer"
	ActividadSecuencia(0,4).descripcion="Comer"

	ActividadSecuencia(0,5).hora_inicio=15
	ActividadSecuencia(0,5).minuto_inicio=0
	ActividadSecuencia(0,5).hora_fin=20
	ActividadSecuencia(0,5).minuto_fin=0
	ActividadSecuencia(0,5).pictograma="juguete"
	ActividadSecuencia(0,5).descripcion="Jugar"

	ActividadSecuencia(0,6).hora_inicio=20
	ActividadSecuencia(0,6).minuto_inicio=0
	ActividadSecuencia(0,6).hora_fin=20
	ActividadSecuencia(0,6).minuto_fin=30
	ActividadSecuencia(0,6).pictograma="ban_arse"
	ActividadSecuencia(0,6).descripcion="Bañarse"

	ActividadSecuencia(0,7).hora_inicio=20
	ActividadSecuencia(0,7).minuto_inicio=30
	ActividadSecuencia(0,7).hora_fin=21
	ActividadSecuencia(0,7).minuto_fin=0
	ActividadSecuencia(0,7).pictograma="cenar_2"
	ActividadSecuencia(0,7).descripcion="Cenar"

	ActividadSecuencia(0,8).hora_inicio=21
	ActividadSecuencia(0,8).minuto_inicio=0
	ActividadSecuencia(0,8).hora_fin=21
	ActividadSecuencia(0,8).minuto_fin=30
	ActividadSecuencia(0,8).pictograma="dormir_1"
	ActividadSecuencia(0,8).descripcion="Acostarse"

	Secuencia(0).tablero.tipo=2
	Secuencia(0).tablero.indicar_hora=3
	Secuencia(0).tablero.tam_icono=10
	
	Secuencia(0).pictograma="colegio"
	Secuencia(0).descripcion="Secuencia de ejemplo"

End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub

Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)
	'Copia la secuencia Seq1 encima de la Seq2
	Secuencia(Seq2).Initialize
	Secuencia(Seq2).descripcion=Secuencia(Seq1).descripcion
	Secuencia(Seq2).num_actividades=Secuencia(Seq1).num_actividades
	Secuencia(Seq2).pictograma=Secuencia(Seq1).pictograma
	Secuencia(Seq2).tablero.Initialize
	Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tablero.tipo
	Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1).tablero.tam_icono
	Secuencia(Seq2).tablero.indicar_hora=Secuencia(Seq1).tablero.indicar_hora
	For i=0 To Secuencia(Seq1).num_actividades-1
		ActividadSecuencia(Seq2,i)=ActividadSecuencia(Seq1,i)
	Next
End Sub