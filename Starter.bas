B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.8
@EndOfDesignText@
' Proyecto desarrollado por Javier Prieto Martínez como parte del TFG del Curso de Adaptación al Grado de Informática de la Universidad Internacional de la Rioja
' Este código fuente se ofrece con una licencia Creative Commons de tipo Reconocimiento-NoComercial-CompartirIgual 3.0 España (CC BY-NC-SA 3.0 ES)

#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals

	Dim CambiosVersion As String
	CambiosVersion= _
	"- Solucionados problemas de descarga de pictogramas desde la web de ARASAAC."&CRLF&CRLF& _
	"- Corregido el tamaño del texto en determinadas configuraciones de pantalla."
	

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
	
	Type Secuencia ( Descripcion As String, tablero As Tablero, pictograma As String, num_actividades As Int, notificaciones As Boolean )
	
	''' ACTIVIDAD CORRESPONDIENTE A LA PRÓXIMA ALARMA
	
	Dim ProximaAlarmaSeq As Int
	Dim ProximaAlarmaAct As Int
	
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
	'Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFFffffba,0xFFbaffc9,0xFFbae1ff,0xFFffbaff,0xFFdfffba,0xFFbaffc9,0xFFbae1ff,0xFFffe1b1,0xFFbaffe1,0xFFffb3ba,0xFFffdfba,0xFFffffba,0xFFbaffc9,0xFFbae1ff,0xFFffbaff,0xFFdfffba,0xFFbaffc9,0xFFbae1ff)
	Colores = Array As Int(0xff8dd3c7,0xffffffb3,0xffbebada,0xfffb8072,0xff80b1d3,0xfffdb462,0xffb3de69,0xfffccde5,0xffd9d9d9,0xffbc80bd,0xffccebc5,0xffa6cee3,0xff1f78b4,0xffb2df8a,0xff33a02c,0xfffb9a99,0xffe31a1c,0xfffdbf6f,0xffff7f00)

	''' CONFIGURACIÓN

	Dim NumSecuencias As Int 'Número de secuencias
	Dim SecuenciaActiva As Int
	Dim Secuencia(MaxSecuencias+1) As Secuencia
	Dim ActividadSecuencia(MaxSecuencias+1,MaxActividades) As Actividad 'Array bidimensional de actividades
	Dim VersionInstalada As Int
	Dim DetectadaVersionAntigua As Boolean
	Dim AlarmasActivadas=True As Boolean
	Dim AplicacionProtegida=False As Boolean
	Dim Formato24h=False As Boolean
	Dim ColorHoras=Colors.Black As Int
	Dim ColorMinutos=Colors.Blue As Int
	Dim ColorSegundos=Colors.Red As Int

	''' VALORES POR DEFECTo
	
	Dim IdPictogramaPorDefecto As Int 'Imagen por defecto para nuevas secuencias
	Dim DirPictogramas As String 'Directorio de trabajo
	Dim PictogramasIniciales(12) As Int 'Listado de pictogramas a precargar en la primera ejecución

End Sub

Sub Service_Create
	NumSecuencias=0
	DirPictogramas=File.Combine(File.DirInternal,"/pictogramas")
	IdPictogramaPorDefecto="7229" 'El pictograma por defecto es el reloj
	PictogramasIniciales = Array As Int (31857,2781,28667,3082,28206,9813,2271,28675,2369,7229,26799,32556)
	kvs.Initialize(File.DirInternal, "configuracion")
	
	Cargar_Configuracion
	CopiarPictogramasIniciales
End Sub

Sub Guardar_Configuracion
	CalcularProximaAlarma
	Dim i,j As Int
	kvs.Put("NumSecuencias", NumSecuencias)
	For i=0 To NumSecuencias-1
		kvs.Put("Secuencia."&i, Secuencia(i))
		For j=0 To Secuencia(i).num_actividades-1
			kvs.Put("ActividadSecuencia."&i&"."&j, ActividadSecuencia(i,j))
		Next
	Next
	kvs.Put("VersionInstalada", Application.VersionCode)
	kvs.Put("AlarmasActivadas", AlarmasActivadas)
	kvs.Put("AplicacionProtegida", AplicacionProtegida)
	kvs.Put("Formato24h", Formato24h)
	kvs.Put("ColorHoras", ColorHoras)
	kvs.Put("ColorMinutos", ColorMinutos)
	kvs.Put("ColorSegundos", ColorSegundos)
End Sub

Sub Cargar_Configuracion
	Dim i,j As Int
	DetectadaVersionAntigua=False
	NumSecuencias=kvs.GetDefault("NumSecuencias",0)
	If NumSecuencias==0 Then
		Inicializar_Con_Ejemplo
		Guardar_Configuracion
	Else
		For i=0 To NumSecuencias-1
			Secuencia(i)=kvs.Get("Secuencia."&i)
			If IsNumber(Secuencia(i).Pictograma)==False Then 'En versiones anteriores el Pictograma era una cadena. Ahora debe ser un número.
				Secuencia(i).Pictograma=IdPictogramaPorDefecto 'Si no es un número, fijamos al ID por defecto.
				DetectadaVersionAntigua=True
			End If
			For j=0 To Secuencia(i).num_actividades-1
				ActividadSecuencia(i,j)=kvs.Get("ActividadSecuencia."&i&"."&j)
				If IsNumber(ActividadSecuencia(i,j).Pictograma)==False Then 'En versiones anteriores el Pictograma era una cadena. Ahora debe ser un número.
					ActividadSecuencia(i,j).Pictograma=IdPictogramaPorDefecto 'Si no es un número, fijamos al ID por defecto.
					DetectadaVersionAntigua=True
				End If
			Next
		Next
	End If
	VersionInstalada=kvs.GetDefault("VersionInstalada",-1)
	AlarmasActivadas=kvs.GetDefault("AlarmasActivadas",True)
	AplicacionProtegida=kvs.GetDefault("AplicacionProtegida",False)
	Formato24h=kvs.GetDefault("Formato24h",False)
	ColorHoras=kvs.GetDefault("ColorHoras",Colors.Black)
	ColorMinutos=kvs.GetDefault("ColorMinutos",Colors.Blue)
	ColorSegundos=kvs.GetDefault("ColorSegundos",Colors.Red)
	CalcularProximaAlarma
End Sub

Sub Inicializar_Con_Ejemplo

	AlarmasActivadas=True 
	AplicacionProtegida=False 
	Formato24h=False 
	ColorHoras=Colors.Black 
	ColorMinutos=Colors.Blue 
	ColorSegundos=Colors.Red 
	
	NumSecuencias=3
	
	'Secuencia 0
	
	Secuencia(0).Initialize
	Secuencia(0).num_actividades=9
	Secuencia(0).tablero.tipo=2
	Secuencia(0).tablero.indicar_hora=1
	Secuencia(0).tablero.tam_icono=0
	Secuencia(0).pictograma=26799
	Secuencia(0).notificaciones=False
	Secuencia(0).descripcion="Día lectivo completo"
	
	ActividadSecuencia(0,0).hora_inicio=8
	ActividadSecuencia(0,0).minuto_inicio=0
	ActividadSecuencia(0,0).hora_fin=8
	ActividadSecuencia(0,0).minuto_fin=15
	ActividadSecuencia(0,0).pictograma=31857
	ActividadSecuencia(0,0).descripcion="Despertarse"
	
	ActividadSecuencia(0,1).hora_inicio=8
	ActividadSecuencia(0,1).minuto_inicio=15
	ActividadSecuencia(0,1).hora_fin=8
	ActividadSecuencia(0,1).minuto_fin=30
	ActividadSecuencia(0,1).pictograma=2781
	ActividadSecuencia(0,1).descripcion="Vestirse"
	
	ActividadSecuencia(0,2).hora_inicio=8
	ActividadSecuencia(0,2).minuto_inicio=30
	ActividadSecuencia(0,2).hora_fin=9
	ActividadSecuencia(0,2).minuto_fin=0
	ActividadSecuencia(0,2).pictograma=28667
	ActividadSecuencia(0,2).descripcion="Desayunar"
	
	ActividadSecuencia(0,3).hora_inicio=9
	ActividadSecuencia(0,3).minuto_inicio=0
	ActividadSecuencia(0,3).hora_fin=14
	ActividadSecuencia(0,3).minuto_fin=0
	ActividadSecuencia(0,3).pictograma=3082
	ActividadSecuencia(0,3).descripcion="Cole"
		
	ActividadSecuencia(0,4).hora_inicio=14
	ActividadSecuencia(0,4).minuto_inicio=0
	ActividadSecuencia(0,4).hora_fin=15
	ActividadSecuencia(0,4).minuto_fin=0
	ActividadSecuencia(0,4).pictograma=28206
	ActividadSecuencia(0,4).descripcion="Comer"

	ActividadSecuencia(0,5).hora_inicio=15
	ActividadSecuencia(0,5).minuto_inicio=0
	ActividadSecuencia(0,5).hora_fin=20
	ActividadSecuencia(0,5).minuto_fin=0
	ActividadSecuencia(0,5).pictograma=9813
	ActividadSecuencia(0,5).descripcion="Jugar"

	ActividadSecuencia(0,6).hora_inicio=20
	ActividadSecuencia(0,6).minuto_inicio=0
	ActividadSecuencia(0,6).hora_fin=20
	ActividadSecuencia(0,6).minuto_fin=30
	ActividadSecuencia(0,6).pictograma=2271
	ActividadSecuencia(0,6).descripcion="Bañarse"

	ActividadSecuencia(0,7).hora_inicio=20
	ActividadSecuencia(0,7).minuto_inicio=30
	ActividadSecuencia(0,7).hora_fin=21
	ActividadSecuencia(0,7).minuto_fin=0
	ActividadSecuencia(0,7).pictograma=28675
	ActividadSecuencia(0,7).descripcion="Cenar"

	ActividadSecuencia(0,8).hora_inicio=21
	ActividadSecuencia(0,8).minuto_inicio=0
	ActividadSecuencia(0,8).hora_fin=21
	ActividadSecuencia(0,8).minuto_fin=30
	ActividadSecuencia(0,8).pictograma=2369
	ActividadSecuencia(0,8).descripcion="Acostarse"

	'Secuencia 1
	
	Secuencia(1).Initialize
	Secuencia(1).num_actividades=6
	Secuencia(1).tablero.tipo=1
	Secuencia(1).tablero.indicar_hora=3
	Secuencia(1).tablero.tam_icono=0
	Secuencia(1).pictograma=9813
	Secuencia(1).notificaciones=False
	Secuencia(1).descripcion="Tarde después del cole"
	
	ActividadSecuencia(1,0).hora_inicio=15
	ActividadSecuencia(1,0).minuto_inicio=0
	ActividadSecuencia(1,0).hora_fin=17
	ActividadSecuencia(1,0).minuto_fin=0
	ActividadSecuencia(1,0).pictograma=9813
	ActividadSecuencia(1,0).descripcion="Jugar"

	ActividadSecuencia(1,1).hora_inicio=17
	ActividadSecuencia(1,1).minuto_inicio=0
	ActividadSecuencia(1,1).hora_fin=18
	ActividadSecuencia(1,1).minuto_fin=0
	ActividadSecuencia(1,1).pictograma=32556
	ActividadSecuencia(1,1).descripcion="Hacer los deberes"

	ActividadSecuencia(1,2).hora_inicio=18
	ActividadSecuencia(1,2).minuto_inicio=0
	ActividadSecuencia(1,2).hora_fin=20
	ActividadSecuencia(1,2).minuto_fin=30
	ActividadSecuencia(1,2).pictograma=9813
	ActividadSecuencia(1,2).descripcion="Jugar"

	ActividadSecuencia(1,3).hora_inicio=20
	ActividadSecuencia(1,3).minuto_inicio=30
	ActividadSecuencia(1,3).hora_fin=21
	ActividadSecuencia(1,3).minuto_fin=00
	ActividadSecuencia(1,3).pictograma=2271
	ActividadSecuencia(1,3).descripcion="Bañarse"

	ActividadSecuencia(1,4).hora_inicio=21
	ActividadSecuencia(1,4).minuto_inicio=0
	ActividadSecuencia(1,4).hora_fin=22
	ActividadSecuencia(1,4).minuto_fin=0
	ActividadSecuencia(1,4).pictograma=28675
	ActividadSecuencia(1,4).descripcion="Cenar"

	ActividadSecuencia(1,5).hora_inicio=22
	ActividadSecuencia(1,5).minuto_inicio=0
	ActividadSecuencia(1,5).hora_fin=22
	ActividadSecuencia(1,5).minuto_fin=30
	ActividadSecuencia(1,5).pictograma=2369
	ActividadSecuencia(1,5).descripcion="Acostarse"
	
	'Secuencia 2
	
	Secuencia(2).Initialize
	Secuencia(2).num_actividades=4
	Secuencia(2).tablero.tipo=3
	Secuencia(2).tablero.indicar_hora=0
	Secuencia(2).tablero.tam_icono=15
	Secuencia(2).pictograma=3082
	Secuencia(2).notificaciones=False
	Secuencia(2).descripcion="Antes de ir al cole"
	
	ActividadSecuencia(2,0).hora_inicio=8
	ActividadSecuencia(2,0).minuto_inicio=0
	ActividadSecuencia(2,0).hora_fin=8
	ActividadSecuencia(2,0).minuto_fin=15
	ActividadSecuencia(2,0).pictograma=2781
	ActividadSecuencia(2,0).descripcion="Vestirse"

	ActividadSecuencia(2,1).hora_inicio=8
	ActividadSecuencia(2,1).minuto_inicio=15
	ActividadSecuencia(2,1).hora_fin=8
	ActividadSecuencia(2,1).minuto_fin=30
	ActividadSecuencia(2,1).pictograma=28667
	ActividadSecuencia(2,1).descripcion="Desayunar"

	ActividadSecuencia(2,2).hora_inicio=8
	ActividadSecuencia(2,2).minuto_inicio=30
	ActividadSecuencia(2,2).hora_fin=8
	ActividadSecuencia(2,2).minuto_fin=35
	ActividadSecuencia(2,2).pictograma=9813
	ActividadSecuencia(2,2).descripcion="Coger un juguete"

	ActividadSecuencia(2,3).hora_inicio=8
	ActividadSecuencia(2,3).minuto_inicio=35
	ActividadSecuencia(2,3).hora_fin=9
	ActividadSecuencia(2,3).minuto_fin=0
	ActividadSecuencia(2,3).pictograma=3082
	ActividadSecuencia(2,3).descripcion="Ir andando al cole"

End Sub

Sub Service_Start (StartingIntent As Intent)
	CancelScheduledService(Avisos)
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
	Secuencia(Seq2).notificaciones=Secuencia(Seq1).notificaciones
	For i=0 To Secuencia(Seq1).num_actividades-1
		CopiarActividad(Seq1,i,Seq2,i)
		'ActividadSecuencia(Seq2,i)=ActividadSecuencia(Seq1,i)
	Next
End Sub

Sub CopiarActividad(Seq1 As Int, Act1 As Int, Seq2 As Int, Act2 As Int)
	'Copia la actividad Seq1:Act1 sobre Seq2:Act2
	ActividadSecuencia(Seq2,Act2).Descripcion=ActividadSecuencia(Seq1,Act1).Descripcion
	ActividadSecuencia(Seq2,Act2).hora_fin=ActividadSecuencia(Seq1,Act1).hora_fin
	ActividadSecuencia(Seq2,Act2).hora_inicio=ActividadSecuencia(Seq1,Act1).hora_inicio
	ActividadSecuencia(Seq2,Act2).minuto_fin=ActividadSecuencia(Seq1,Act1).minuto_fin
	ActividadSecuencia(Seq2,Act2).minuto_inicio=ActividadSecuencia(Seq1,Act1).minuto_inicio
	ActividadSecuencia(Seq2,Act2).Pictograma=ActividadSecuencia(Seq1,Act1).Pictograma
End Sub


Sub CopiarPictogramasIniciales 'Copia los pictogramas necesarios para ejecutar la actividad de ejemplo
	Dim i As Int
	Dim NombreFich As String
	
	'Comprueba que exista el directorio de pictogramas
	If File.IsDirectory(File.DirInternal, "pictogramas") == False Then
		File.MakeDir(File.DirInternal, "pictogramas")
	End If
	
	For i=0 To PictogramasIniciales.Length-1
		NombreFich=PictogramasIniciales(i)&".png"
		If File.Exists(DirPictogramas,NombreFich)==False Then
			'Si no existe, lo copia de Assets al directorio de trabajo
			File.Copy(File.DirAssets,NombreFich,DirPictogramas,NombreFich)
		End If
	Next
End Sub

Sub BorrarPictogramas 'Borra todos los pictogramas descargados
	Dim fileList As List
	Dim i As Int
	Dim NomFich As String

	'Borra todos los descargados
	fileList=File.ListFiles(DirPictogramas)
	For i=0 To fileList.Size-1
		NomFich=fileList.Get(i)
		File.Delete(DirPictogramas,NomFich)
	Next
	
	'Vuelve a copiar los iniciales
	CopiarPictogramasIniciales
End Sub

Sub CalcularProximaAlarma
	'Log( DateTime.Time(DateTime.Now) & ": Inicio de CalcularProximaAlarma")
	
	Dim i,j As Int
	Dim Hora=25*60 As Int
	Dim Minuto=0 As Int
	Dim HoraAct,MinutoAct As Int
	Dim n As Notification
		
	ProximaAlarmaAct=-1
	ProximaAlarmaSeq=-1
	
	For i=0 To NumSecuencias-1
		If Secuencia(i).notificaciones==True And AlarmasActivadas==True Then
			For j=0 To Secuencia(i).num_actividades-1
				HoraAct=ActividadSecuencia(i,j).hora_inicio
				MinutoAct=ActividadSecuencia(i,j).minuto_inicio
				' Si es anterior a la actual, se suma un día
				If (HoraAct*60+MinutoAct)<=( DateTime.GetHour(DateTime.Now)*60+DateTime.GetMinute(DateTime.Now)) Then
					HoraAct=HoraAct+24
				End If
					If (HoraAct*60+MinutoAct < Hora*60+Minuto) Then
						ProximaAlarmaSeq=i
						ProximaAlarmaAct=j
						Hora=HoraAct
						Minuto=MinutoAct
				End If
			Next
		End If
	Next
	
	CancelScheduledService(Avisos)
	n.Cancel(1)
		
	If ProximaAlarmaSeq>=0 Then
		'Se ha encontrado una alarma
		Dim EsManana=False As Boolean
		If Hora>=24 Then
			EsManana=True
			Hora=Hora-24
		End If

		Dim TextoManana="" As String
		Dim HoraEjecucion=DateUtils.SetDateAndTime(DateTime.GetYear(DateTime.Now), DateTime.GetMonth(DateTime.Now),DateTime.GetDayOfMonth(DateTime.Now),Hora,Minuto,0) As Long
		If EsManana==True Then
			Dim p As Period
			p.Days = 1
			HoraEjecucion = DateUtils.AddPeriod(HoraEjecucion, p)
			TextoManana=" (mañana)"
		End If
		
		Dim TextoHora As String
		TextoHora=EscribirHora(Hora,Minuto)
		
		n.Initialize2(n.IMPORTANCE_LOW)
		n.OnGoingEvent = False
		n.Sound = False
		n.Vibrate = False
		n.Light = True
		n.Icon = "iconw"
		n.SetInfo("Próxima actividad en Pictorario" ,TextoHora&TextoManana&": "&Secuencia(ProximaAlarmaSeq).Descripcion&" ➞ "&ActividadSecuencia(ProximaAlarmaSeq,ProximaAlarmaAct).Descripcion, Main)
		n.Notify(1)

		'Service.AutomaticForegroundNotification=n
		'Service.AutomaticForegroundMode = Service.AUTOMATIC_FOREGROUND_ALWAYS
		
		StartServiceAtExact(Avisos,HoraEjecucion,True)
		'Log("Preparando activación automática en: "&DateUtils.TicksToString(HoraEjecucion))
		
	End If

	'Log( DateTime.Time(DateTime.Now) & ": Fin de CalcularProximaAlarma")
	
End Sub

Sub EscribirHora(Hora As Int, Minutos As Int) As String
	Dim Salida As String
	Dim HoraModificada As Int
	If (Formato24h==False And Hora>11) Then
		HoraModificada=Hora-12
	Else
		HoraModificada=Hora
	End If
	Salida=HoraModificada&":"&NumberFormat(Minutos,2,0)
	If Formato24h==False Then
		If Hora<12 Then
			Salida=Salida&" a.m."
		Else
			Salida=Salida&" p.m."
		End If
	End If
	Return Salida
End Sub