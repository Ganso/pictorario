B4A=true
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

	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

'' *****************
'' NOTA: HAY QUE QUITAR EL COLOR Y QUE SEA SECUENCIAL
'' *****************

	Type Actividad ( hora_inicio As Int, minuto_inicio As Int, hora_fin As Int, minuto_fin As Int, Pictograma As String, descripcion As String, color As Int )
	
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
	
	Dim MaxSecuencias=10 As Int 'Número máximo de secuencias
	Dim MaxActividades=20 As Int 'Número máximo de actividades por secuencia

	Dim NumSecuencias As Int 'Número de secuencias
	Dim SecuenciaActiva As Int
	
	Dim ActividadSecuencia(MaxSecuencias,MaxActividades) As Actividad 'Array bidimensional de actividades
	Dim DescripcionSecuencia(MaxSecuencias) As String
	Dim NumActividades(MaxSecuencias) As Int 'Número de actividades de cada secuencia
	Dim TableroSecuencia(MaxSecuencias) As Tablero 'Tablero asociado a cada secuencia
	Dim PictogramaSecuencia(MaxSecuencias) As String 'Pictograma asociado a cada secuencia
	
	Dim Colores() As Int 'Colores para las áreas
	Dim MaxColores=11 As Int
	Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFFffffba,0xFFbaffc9,0xFFbae1ff,0xFFffbaff,0xFFdfffba,0xFFbaffc9,0xFFbae1ff,0xFFffe1b1,0xFFbaffe1)
'	Colores = Array As Int (0xFFf3a683,0xFFf7d794,0xFF778beb,0xFFe77f67,0xFFcf6a87,0xFF786fa6,0xFFf8a5c2,0xFF63cdda,0xFFea8685,0xFFf19066,0xFFf5cd79,0xFF546de5,0xFFe15f41,0xFFc44569,0xFF574b90,0xFFf78fb3,0xFF3dc1d3,0xFFe66767)

End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.

	NumSecuencias=2
	
	'Secuencia 0
	NumActividades(0)=9

	ActividadSecuencia(0,0).hora_inicio=8
	ActividadSecuencia(0,0).minuto_inicio=0
	ActividadSecuencia(0,0).hora_fin=8
	ActividadSecuencia(0,0).minuto_fin=15
	ActividadSecuencia(0,0).pictograma="despertar_1"
	ActividadSecuencia(0,0).descripcion="Despertarse"
	ActividadSecuencia(0,0).color=Colores(0)
	
	ActividadSecuencia(0,1).hora_inicio=8
	ActividadSecuencia(0,1).minuto_inicio=15
	ActividadSecuencia(0,1).hora_fin=8
	ActividadSecuencia(0,1).minuto_fin=30
	ActividadSecuencia(0,1).pictograma="vestirse"
	ActividadSecuencia(0,1).descripcion="Vestirse"
	ActividadSecuencia(0,1).color=Colores(1)
	
	ActividadSecuencia(0,2).hora_inicio=8
	ActividadSecuencia(0,2).minuto_inicio=30
	ActividadSecuencia(0,2).hora_fin=9
	ActividadSecuencia(0,2).minuto_fin=0
	ActividadSecuencia(0,2).pictograma="desayunar"
	ActividadSecuencia(0,2).descripcion="Desayunar"
	ActividadSecuencia(0,2).color=Colores(2)
	
	ActividadSecuencia(0,3).hora_inicio=9
	ActividadSecuencia(0,3).minuto_inicio=0
	ActividadSecuencia(0,3).hora_fin=14
	ActividadSecuencia(0,3).minuto_fin=0
	ActividadSecuencia(0,3).pictograma="colegio"
	ActividadSecuencia(0,3).descripcion="Cole"
	ActividadSecuencia(0,3).color=Colores(3)
		
	ActividadSecuencia(0,4).hora_inicio=14
	ActividadSecuencia(0,4).minuto_inicio=0
	ActividadSecuencia(0,4).hora_fin=15
	ActividadSecuencia(0,4).minuto_fin=0
	ActividadSecuencia(0,4).pictograma="comer"
	ActividadSecuencia(0,4).descripcion="Comer"
	ActividadSecuencia(0,4).color=Colores(4)

	ActividadSecuencia(0,5).hora_inicio=15
	ActividadSecuencia(0,5).minuto_inicio=0
	ActividadSecuencia(0,5).hora_fin=20
	ActividadSecuencia(0,5).minuto_fin=0
	ActividadSecuencia(0,5).pictograma="juguete"
	ActividadSecuencia(0,5).descripcion="Jugar"
	ActividadSecuencia(0,5).color=Colores(5)

	ActividadSecuencia(0,6).hora_inicio=20
	ActividadSecuencia(0,6).minuto_inicio=0
	ActividadSecuencia(0,6).hora_fin=20
	ActividadSecuencia(0,6).minuto_fin=30
	ActividadSecuencia(0,6).pictograma="ban_arse"
	ActividadSecuencia(0,6).descripcion="Bañarse"
	ActividadSecuencia(0,6).color=Colores(6)

	ActividadSecuencia(0,7).hora_inicio=20
	ActividadSecuencia(0,7).minuto_inicio=30
	ActividadSecuencia(0,7).hora_fin=21
	ActividadSecuencia(0,7).minuto_fin=0
	ActividadSecuencia(0,7).pictograma="cenar_2"
	ActividadSecuencia(0,7).descripcion="Cenar"
	ActividadSecuencia(0,7).color=Colores(7)

	ActividadSecuencia(0,8).hora_inicio=21
	ActividadSecuencia(0,8).minuto_inicio=0
	ActividadSecuencia(0,8).hora_fin=21
	ActividadSecuencia(0,8).minuto_fin=30
	ActividadSecuencia(0,8).pictograma="dormir_1"
	ActividadSecuencia(0,8).descripcion="Acostarse"
	ActividadSecuencia(0,8).color=Colores(8)
	
	TableroSecuencia(0).tipo=2
	TableroSecuencia(0).indicar_hora=3
	TableroSecuencia(0).tam_icono=10
	
	PictogramaSecuencia(0)="colegio"
	
	DescripcionSecuencia(0)="Secuencia de prueba de día completo"
	
	'Secuencia 1
	
	NumActividades(1)=4

	ActividadSecuencia(1,0).hora_inicio=8
	ActividadSecuencia(1,0).minuto_inicio=0
	ActividadSecuencia(1,0).hora_fin=8
	ActividadSecuencia(1,0).minuto_fin=15
	ActividadSecuencia(1,0).pictograma="despertar_1"
	ActividadSecuencia(1,0).descripcion="Despertarse"
	ActividadSecuencia(1,0).color=Colores(0)
	
	ActividadSecuencia(1,1).hora_inicio=8
	ActividadSecuencia(1,1).minuto_inicio=15
	ActividadSecuencia(1,1).hora_fin=8
	ActividadSecuencia(1,1).minuto_fin=30
	ActividadSecuencia(1,1).pictograma="vestirse"
	ActividadSecuencia(1,1).descripcion="Vestirse"
	ActividadSecuencia(1,1).color=Colores(1)
	
	ActividadSecuencia(1,2).hora_inicio=8
	ActividadSecuencia(1,2).minuto_inicio=30
	ActividadSecuencia(1,2).hora_fin=9
	ActividadSecuencia(1,2).minuto_fin=0
	ActividadSecuencia(1,2).pictograma="desayunar"
	ActividadSecuencia(1,2).descripcion="Desayunar"
	ActividadSecuencia(1,2).color=Colores(2)
	
	ActividadSecuencia(1,3).hora_inicio=9
	ActividadSecuencia(1,3).minuto_inicio=0
	ActividadSecuencia(1,3).hora_fin=14
	ActividadSecuencia(1,3).minuto_fin=0
	ActividadSecuencia(1,3).pictograma="colegio"
	ActividadSecuencia(1,3).descripcion="Cole"
	ActividadSecuencia(1,3).color=Colores(3)
	
	TableroSecuencia(1).tipo=3
	TableroSecuencia(1).indicar_hora=3
	TableroSecuencia(1).tam_icono=20
	
	PictogramaSecuencia(1)="despertar_1"
	
	DescripcionSecuencia(1)="Mañana de día laborable"

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
