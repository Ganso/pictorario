B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8
@EndOfDesignText@
' Proyecto desarrollado por Javier Prieto Martínez
' Información, código fuente, documentación, etc. en http://blog.ganso.org/proyectos/pictorario
' Este código fuente se ofrece con una licencia Creative Commons de tipo Reconocimiento-NoComercial-CompartirIgual 3.0 España (CC BY-NC-SA 3.0 ES)

#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
End Sub

Sub Globals
	Private BarraBusqueda As EditText
	Private BotonBuscar As Label
	Private Buscar As Panel
	Private ListadoPictogramas As ScrollView
	Private Titulo As Label
	Private BotonCancelar As Button
	
	'Texto por defecto para la barra de búsqueda
	Dim TextoBuscarPorDefecto="Buscar pictograma por texto" As String
	
	'Máximo número de Líneas y Columnas a mostrar
	Dim NumColumnas As Int = 3
	Dim NumLineas As Int = 20
	
	'Mosaico de iconos
	Dim BotonIcono(NumLineas,NumColumnas) As Button
	
	'Tamaño de cada icono
	Dim TamIcono As Int
		
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("SeleccionarPictograma")
	BotonBuscar.SetBackgroundImage(LoadBitmap(File.DirAssets,"buscar.png"))
	BarraBusqueda.Text=TextoBuscarPorDefecto
	TamIcono=ListadoPictogramas.Width/3
	DibujaIconos
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub BotonIcono_Click
	Dim BotonPulsado As Button
	BotonPulsado=Sender
	CallSubDelayed2(ConfigurarSecuencia,"PictogramaElegido",BotonPulsado.Tag)
	Activity.Finish
End Sub

Sub BotonBuscar_Click
	BuscarTexto	
End Sub

Sub BarraBusqueda_FocusChanged (TieneFoco As Boolean)
	'Si pulsamos sobre el texto por defecto, lo vacía
	If TieneFoco==True And BarraBusqueda.Text==TextoBuscarPorDefecto Then
		BarraBusqueda.Text=""
		Activity.Invalidate
	End If
End Sub

Sub BarraBusqueda_EnterPressed
	BuscarTexto
End Sub

Sub BuscarTexto
	Dim id(100) As Int
	Dim URL(100) As String
	'Dim URLencontrada As String
	Dim i,x,y As Int
	
	If BarraBusqueda.Text<>TextoBuscarPorDefecto Then 'Comprueba que hemos cambiado el texto por defecto
		Dim im As IME
		im.Initialize("")
		im.HideKeyboard 'Ocultamos el teclado
		
		ProgressDialogShow("Buscando pictogramas")
		
		'Busca en la web de ARASAAC la cadena
		Dim Web As HttpJob
		Web.Initialize("buscapictogramas",Me)
		Dim su As StringUtils
		Web.Download("https://api.arasaac.org/api/pictograms/es/search/"&su.EncodeUrl(BarraBusqueda.Text,"UTF8").Replace("+","%20"))
		Wait For (Web) JobDone(j As HttpJob)
		Dim encontrados As Int = 0
		If j.Success Then
			Dim jp As JSONParser
			jp.Initialize(j.GetString)
			Dim PictogramasEncontrados As List = jp.NextArray
			For Each Pictograma As Map In PictogramasEncontrados
				id(encontrados)=Pictograma.Get("_id")
				encontrados=encontrados+1
			Next
		Else
			Msgbox("La búsqueda no ha producido resultados."&CRLF&CRLF&"Pruebe otros términos, y compruebe que la conexión a Internet está activa."&CRLF&CRLF&"Las búsquedas no funcionan si no hay conexión a la red.","NO SE ENCUENTRAN PICTOGRAMAS")
		End If
		j.Release
		Web.Release

		ProgressDialogShow("Descargando imágenes")

		'Es necesario hacer dos bloques independientes para detectar URLs y luego descargar para que funcione correctamente el anidamiento de WAIT FORs

		For i=0 To encontrados-1
			If File.Exists(Starter.DirPictogramas,id(i)&".png")==False Then
				'Wait For(EncontrarURLporId(id(i))) Complete (URLencontrada As String)
				'URL(i)=URLencontrada
				'Se descarga directamente la versión de 500px de la URL de pictogramas pregenerados
				URL(i)="https://static.arasaac.org/pictograms/"&id(i)&"/"&id(i)&"_500.png"
			End If
		Next
		
		For i=0 To encontrados-1
			If File.Exists(Starter.DirPictogramas,id(i)&".png")==False Then
					Wait For (DescargarPictograma(id(i),URL(i))) Complete (Resultado As Int)
			End If
		Next
		
		ProgressDialogShow("Preparando listado")
	
		For x=0 To NumColumnas-1
			For y=0 To NumLineas-1
				BotonIcono(y,x).Visible=False 'Oculta todos los botones
			Next
		Next
		
		x=0
		y=0
		For i=0 To encontrados-1 'Muestra los nuevos iconos
			If y<NumLineas Then
				BotonIcono(y,x).SetBackgroundImage(LoadBitmap(File.Combine(File.DirInternal,"/pictogramas"),id(i)&".png"))
				BotonIcono(y,x).Tag=id(i)
				BotonIcono(y,x).Visible=True
				ListadoPictogramas.Panel.Height=(y+1)*TamIcono
				x=x+1
				If (x=NumColumnas) Then
					x=0
					y=y+1
				End If
			End If
		Next

		ProgressDialogHide
	End If
End Sub

Sub DescargarPictograma(id As Int,URL As String) As ResumableSub
	'Descarga un pictograma conociendo su ID y su URL
	
	Dim Web3 As HttpJob
	Web3.Initialize("bajapictograma",Me)
	Web3.Download(URL)

	Wait For (Web3) JobDone(j3 As HttpJob)
	If j3.Success Then
		Dim Fichero As OutputStream
		Fichero = File.OpenOutput(Starter.DirPictogramas,id&".png",False)
		File.Copy2(Web3.GetInputStream, Fichero)
		Fichero.Close
	End If
	j3.Release
	Web3.Release
	Return 0
End Sub

'Ya no es necesaria (se accede a static.arasaac.org, ya que todos los pictogramas están siempre pregenerados)
'Sub EncontrarURLporId(id As Int) As ResumableSub
'	'Encuentra la URL del fichero de imagen de un ID determinado
'	
'	Dim URL As String
'	Dim Web2 As HttpJob
'	
'	Web2.Initialize("encuentaid",Me)
'	Web2.Download("https://api.arasaac.org/api/pictograms/"&id&"?url=true")
'
'	Wait For (Web2) JobDone(j2 As HttpJob)
'	If j2.Success Then
'		Dim jp As JSONParser
'		jp.Initialize(j2.GetString)
'		Dim m As Map
'		m=jp.NextObject
'		URL=m.Get("image")
'	End If
'	j2.Release
'	Web2.Release
'	
'	Return URL
'End Sub

Sub BotonCancelar_Click
	CallSubDelayed2(ConfigurarSecuencia,"PictogramaElegido",-1) '-1 indica que se ha cancelado
	Activity.Finish
End Sub

Sub DibujaIconos
	Dim x,y As Int
	
	For x=0 To NumColumnas-1
		For y=0 To NumLineas-1
			BotonIcono(y,x).Initialize("BotonIcono")
			ListadoPictogramas.Panel.AddView(BotonIcono(y,x),x*TamIcono,y*TamIcono,TamIcono,TamIcono)
			BotonIcono(y,x).Visible=False
		Next
	Next
	ListadoPictogramas.Panel.Height=0 'Ningún icono visible
	
	RellenarIconos
End Sub

Sub RellenarIconos
	'Rellena la vista inicial de iconos
	Dim fileList As List
	Dim i,x,y,NumFicheros,id As Int
	Dim NombreFich As String
	
	fileList = File.ListFiles(Starter.DirPictogramas)
	fileList.Sort(False)

	NumFicheros=fileList.Size
	If NumFicheros>(NumLineas*NumColumnas) Then
		NumFicheros=NumLineas*NumColumnas
	End If
	
	x=0
	y=0
	For i=0 To NumFicheros-1
		If y<NumLineas Then
			NombreFich=fileList.Get(i)
			BotonIcono(y,x).SetBackgroundImage(LoadBitmap(Starter.DirPictogramas,NombreFich))
			id=Regex.Replace("[^0-9]",NombreFich,"")
			BotonIcono(y,x).Tag=id
			BotonIcono(y,x).Visible=True
			ListadoPictogramas.Panel.Height=(y+1)*TamIcono
			x=x+1
			If (x=NumColumnas) Then
				x=0
				y=y+1
			End If
		End If
	Next	
End Sub

Sub Activity_KeyPress (KeyCode As Int)
	If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsar atrás...
		Sleep(0) 'No hace nada
	End If
End Sub