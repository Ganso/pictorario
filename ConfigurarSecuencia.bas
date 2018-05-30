B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim SeparacionHorizontal=25%X As Int  'Separación horizontal entre casillas
	Dim TamCasilla=60dip As Int 'Tamaño vertical de las casillas de configuración
	Dim SeparacionCasillas=5dip As Int 'Separación vertical entre casillas
	
	Dim ColorDeFondo=0xFFF0FFFF As Int

	Private ParametrosSecuencia As ScrollView
	
	Dim EtiquetaInicial As Label
	Dim ConfigDescripcion As EditText
	Dim ConfigPictograma As Label
	Dim EtiqTipoTablero As Label
	Dim ConfigTipoTablero As Label
	Dim EtiqIndicarHora As Label
	Dim ConfigIndicarHora As Label
	Dim EtiqTamIcono As Label
	Dim ConfigTamIcono As SeekBar
	
	Dim EtiqActividades As Label
	
	Dim ConfigDescripcionAct(Starter.MaxActividades) As EditText
	Dim ConfigHoraInicioAct(Starter.MaxActividades) As Label
	Dim ConfigHoraFinalAct(Starter.MaxActividades) As Label
	Dim ConfigPictogramaAct(Starter.MaxActividades) As Label
	Dim ConfigOpcionesAct(Starter.MaxActividades) As Label

	Dim BotonAnadirActividad As Button
	Dim BotonAceptar As Button	
	Dim BotonCancelar As Button
	
	Dim Inicializando As Boolean
	
	''' LISTA DE PICTOGRAMAS

	Dim ListaPictogramas As ListView
	Dim ListaPictogramasVisible As Boolean
	
	Dim PictogramaEditado As Int
		'-1 si editando el de la secuencia
		'>=0 si editando el de una actividad

	Dim DescripcionSecuenciaPorDefecto="Pulsa aquí para poner un nombre de secuencia" As String
	Dim DescripcionActividadPorDefecto="Nombre de la nueva actividad" As String

End Sub

Sub Activity_Create(FirstTime As Boolean)

	'Trabajamos en la secuencia MaxSecuencias (la siguiente a la última disponible)

	Starter.Secuencia(Starter.MaxSecuencias).Initialize
	Starter.Secuencia(Starter.MaxSecuencias).tablero.Initialize
	
	If (Starter.SecuenciaActiva==Starter.MaxSecuencias) Then 'Si es nueva, inicializamos
		Starter.Secuencia(Starter.MaxSecuencias).num_actividades=0
		Starter.Secuencia(Starter.MaxSecuencias).descripcion=DescripcionSecuenciaPorDefecto
		Starter.Secuencia(Starter.MaxSecuencias).pictograma="reloj_6"
		Starter.Secuencia(Starter.MaxSecuencias).tablero.tipo=3
		Starter.Secuencia(Starter.MaxSecuencias).tablero.tam_icono=20
		Starter.Secuencia(Starter.MaxSecuencias).tablero.indicar_hora=1
	Else
		CallSub3(Starter,"CopiarSecuencias",Starter.SecuenciaActiva,Starter.MaxSecuencias) 'Si no, copiamos
	End If
	
	DibujarConfigurarSecuencia
	Inicializar_Lista_Pictogramas

End Sub
	
Sub DibujarConfigurarSecuencia

	Dim Posicion As Int

	If ParametrosSecuencia.IsInitialized==True Then
		Posicion=ParametrosSecuencia.ScrollPosition
	Else
		Posicion=0
	End If

	Activity.RemoveAllViews
	Activity.LoadLayout("ConfigurarSecuencia")
	
	Inicializando=True 'Para evitar que se lancen procesos de cambio mientras se incializa
	
	EtiquetaInicial.Initialize("EtiquetaInicial")
	EtiquetaInicial.Text="Crear nueva secuencia"
	If (Starter.SecuenciaActiva<Starter.MaxSecuencias) Then
		EtiquetaInicial.Text="Editar secuencia"
	End If
	EtiquetaInicial.TextColor=Colors.Black
	EtiquetaInicial.TextSize=24
	EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD
	EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	ParametrosSecuencia.Panel.AddView(EtiquetaInicial,0,0,100%X,80dip)
	
	ConfigPictograma.Initialize("ConfigPictograma")
	ConfigPictograma.SetBackgroundImage(LoadBitmap(File.DirAssets,Starter.Secuencia(Starter.MaxSecuencias).pictograma&".png"))
	ParametrosSecuencia.Panel.AddView(ConfigPictograma,100%X-TamCasilla-SeparacionCasillas,EtiquetaInicial.Top+EtiquetaInicial.Height+SeparacionCasillas,TamCasilla,TamCasilla)

	ConfigDescripcion.Initialize("ConfigDescripcion")
	ConfigDescripcion.Text=Starter.Secuencia(Starter.MaxSecuencias).descripcion
	ConfigDescripcion.TextColor=Colors.White
	ConfigDescripcion.TextSize=16
	ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD
	ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	ConfigDescripcion.Color=Colors.DarkGray
	ParametrosSecuencia.Panel.AddView(ConfigDescripcion,SeparacionCasillas,EtiquetaInicial.Top+EtiquetaInicial.Height+SeparacionCasillas,ConfigPictograma.Left-2*SeparacionCasillas,TamCasilla)
	
	EtiqTipoTablero.Initialize("EtiqTipoTablero")
	EtiqTipoTablero.Text="Tipo de tablero:"
	EtiqTipoTablero.TextColor=Colors.Black
	EtiqTipoTablero.TextSize=16
	EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL
	ParametrosSecuencia.Panel.AddView(EtiqTipoTablero,SeparacionCasillas,ConfigDescripcion.Top+ConfigDescripcion.Height+SeparacionCasillas,SeparacionHorizontal,TamCasilla)

	ConfigTipoTablero.Initialize("ConfigTipoTablero")
	ConfigTipoTablero.Text=Starter.DescripcionTablero(Starter.Secuencia(Starter.MaxSecuencias).tablero.tipo)
	ConfigTipoTablero.TextColor=Colors.Black
	ConfigTipoTablero.TextSize=16
	ConfigTipoTablero.Color=ColorDeFondo
	ConfigTipoTablero.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	ParametrosSecuencia.Panel.AddView(ConfigTipoTablero,SeparacionHorizontal+SeparacionCasillas,ConfigDescripcion.Top+ConfigDescripcion.Height+SeparacionCasillas,100%X-SeparacionHorizontal-2*SeparacionCasillas,TamCasilla)

	EtiqIndicarHora.Initialize("EtiqIndicarHora")
	EtiqIndicarHora.Text="Indicar hora actual:"
	EtiqIndicarHora.TextColor=Colors.Black
	EtiqIndicarHora.TextSize=16
	EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL
	ParametrosSecuencia.Panel.AddView(EtiqIndicarHora,SeparacionCasillas,ConfigTipoTablero.Top+ConfigTipoTablero.Height+SeparacionCasillas,SeparacionHorizontal,TamCasilla)

	ConfigIndicarHora.Initialize("ConfigIndicarHora")
	ConfigIndicarHora.Text=Starter.DescripcionMinutero(Starter.Secuencia(Starter.MaxSecuencias).tablero.indicar_hora)
	ConfigIndicarHora.TextColor=Colors.Black
	ConfigIndicarHora.TextSize=16
	ConfigIndicarHora.Color=ColorDeFondo
	ConfigIndicarHora.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	ParametrosSecuencia.Panel.AddView(ConfigIndicarHora,SeparacionHorizontal+SeparacionCasillas,ConfigTipoTablero.Top+ConfigTipoTablero.Height+SeparacionCasillas,100%X-SeparacionHorizontal-2*SeparacionCasillas,TamCasilla)

	EtiqTamIcono.Initialize("EtiqTamIcono")
	EtiqTamIcono.Text="Tamaño de los iconos:"
	EtiqTamIcono.TextColor=Colors.Black
	EtiqTamIcono.TextSize=16
	EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL
	ParametrosSecuencia.Panel.AddView(EtiqTamIcono,SeparacionCasillas,ConfigIndicarHora.Top+ConfigIndicarHora.Height+SeparacionCasillas,SeparacionHorizontal,TamCasilla)

	ConfigTamIcono.Initialize("ConfigTamIcono")
	ConfigTamIcono.Value=Starter.Secuencia(Starter.MaxSecuencias).tablero.tam_icono
	ConfigTamIcono.Max=30
	ConfigTamIcono.Color=ColorDeFondo
	ParametrosSecuencia.Panel.AddView(ConfigTamIcono,SeparacionHorizontal+SeparacionCasillas,ConfigIndicarHora.Top+ConfigIndicarHora.Height+SeparacionCasillas,100%X-SeparacionHorizontal-2*SeparacionCasillas,TamCasilla)

	EtiqActividades.Initialize("EtiqActividades")
	EtiqActividades.Text="Actividades"
	EtiqActividades.TextColor=Colors.Black
	EtiqActividades.TextSize=24
	EtiqActividades.Typeface=Typeface.DEFAULT_BOLD
	EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	ParametrosSecuencia.Panel.AddView(EtiqActividades,SeparacionCasillas,ConfigTamIcono.Top+ConfigTamIcono.Height+SeparacionCasillas,100%X-2*SeparacionCasillas,TamCasilla)

	Dim InicioVertical As Int
	Dim FinVertical As Int
	
	FinVertical=EtiqActividades.Top+EtiqActividades.Height
	InicioVertical=FinVertical+SeparacionCasillas
	
	For Act=0 To Starter.Secuencia(Starter.MaxSecuencias).num_actividades-1

		If (Act>0) Then
			InicioVertical=ConfigHoraInicioAct(Act-1).Top+ConfigHoraInicioAct(Act-1).Height+4*SeparacionCasillas
		End If
	
		ConfigPictogramaAct(Act).Initialize("ConfigPictogramaAct")
		ConfigPictogramaAct(Act).Tag=Act
		ConfigPictogramaAct(Act).SetBackgroundImage(LoadBitmap(File.DirAssets,Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).Pictograma&".png"))
		ParametrosSecuencia.Panel.AddView(ConfigPictogramaAct(Act),100%X-TamCasilla-SeparacionCasillas,InicioVertical,TamCasilla,TamCasilla)

		ConfigDescripcionAct(Act).Initialize("ConfigDescripcionAct")
		ConfigDescripcionAct(Act).Tag=Act
		ConfigDescripcionAct(Act).Text=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).Descripcion
		ConfigDescripcionAct(Act).TextColor=Colors.Black
		ConfigDescripcionAct(Act).TextSize=16
		ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
		ConfigDescripcionAct(Act).Color=Starter.Colores(Act)
		ParametrosSecuencia.Panel.AddView(ConfigDescripcionAct(Act),SeparacionCasillas,InicioVertical,ConfigPictogramaAct(Act).Left-2*SeparacionCasillas,TamCasilla)

		ConfigHoraInicioAct(Act).Initialize("ConfigHoraInicioAct")
		ConfigHoraInicioAct(Act).Tag=Act
		ConfigHoraInicioAct(Act).Text="Desde"&CRLF&NumberFormat(Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_inicio,2,0)&":"&NumberFormat(Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_inicio,2,0)
		ConfigHoraInicioAct(Act).TextColor=Colors.Black
		ConfigHoraInicioAct(Act).TextSize=16
		ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
		ConfigHoraInicioAct(Act).Color=ColorDeFondo
		ParametrosSecuencia.Panel.AddView(ConfigHoraInicioAct(Act),3*SeparacionCasillas,ConfigDescripcionAct(Act).Top+ConfigDescripcionAct(Act).Height+SeparacionCasillas,50%X-3*SeparacionCasillas-TamCasilla/2,TamCasilla)

		ConfigHoraFinalAct(Act).Initialize("ConfigHoraFinalAct")
		ConfigHoraFinalAct(Act).Tag=Act
		ConfigHoraFinalAct(Act).Text="Hasta"&CRLF&NumberFormat(Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_fin,2,0)&":"&NumberFormat(Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_fin,2,0)
		ConfigHoraFinalAct(Act).TextColor=Colors.Black
		ConfigHoraFinalAct(Act).TextSize=16
		ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
		ConfigHoraFinalAct(Act).Color=ColorDeFondo
		ParametrosSecuencia.Panel.AddView(ConfigHoraFinalAct(Act),ConfigHoraInicioAct(Act).Left+ConfigHoraInicioAct(Act).Width+SeparacionCasillas,ConfigDescripcionAct(Act).Top+ConfigDescripcionAct(Act).Height+SeparacionCasillas,50%X-3*SeparacionCasillas-TamCasilla/2,TamCasilla)
	
		ConfigOpcionesAct(Act).Initialize("ConfigOpcionesAct")
		ConfigOpcionesAct(Act).Tag=Act
		ConfigOpcionesAct(Act).SetBackgroundImage(LoadBitmap(File.DirAssets,"engranaje.png"))
		ParametrosSecuencia.Panel.AddView(ConfigOpcionesAct(Act),100%X-TamCasilla-SeparacionCasillas,ConfigHoraFinalAct(Act).Top,TamCasilla,TamCasilla)

		FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHoraFinalAct(Act).Height

	Next
	
	BotonAnadirActividad.Initialize("BotonAnadirActividad")
	BotonAnadirActividad.Text="Añadir Actividad"
	BotonAnadirActividad.TextSize=16
	BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	BotonAnadirActividad.TextColor=Colors.Black
	If Starter.Secuencia(Starter.MaxSecuencias).num_actividades==Starter.MaxActividades Then
		BotonAnadirActividad.Enabled=False
	End If
	ParametrosSecuencia.Panel.AddView(BotonAnadirActividad,SeparacionCasillas,FinVertical+SeparacionCasillas,100%X-2*SeparacionCasillas,TamCasilla)	
	
	BotonAceptar.Initialize("BotonAceptar")
	BotonAceptar.Text="Aceptar"
	BotonAceptar.TextSize=16
	BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	BotonAceptar.TextColor=Colors.Black
	ParametrosSecuencia.Panel.AddView(BotonAceptar,SeparacionCasillas,BotonAnadirActividad.Top+BotonAnadirActividad.Height,50%X-2*SeparacionCasillas,TamCasilla)

	If Starter.Secuencia(Starter.MaxSecuencias).num_actividades==0 Then
		BotonAceptar.Enabled=False
	End If

	BotonCancelar.Initialize("BotonCancelar")
	BotonCancelar.Text="Cancelar"
	BotonCancelar.TextSize=16
	BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	BotonCancelar.TextColor=Colors.Black
	ParametrosSecuencia.Panel.AddView(BotonCancelar,50%X+SeparacionCasillas,BotonAceptar.Top,50%X-2*SeparacionCasillas,TamCasilla)

	ParametrosSecuencia.Panel.Height=BotonCancelar.Top+BotonCancelar.Height+SeparacionCasillas

	ParametrosSecuencia.ScrollPosition=Posicion
	Sleep(0)
	ParametrosSecuencia.ScrollPosition=Posicion

	Inicializando=False

End Sub

Sub ConfigTipoTablero_Click
	Dim TiposTablero As List
	Dim resultado As Int
	
	TiposTablero.Initialize
	TiposTablero.AddAll(Starter.DescripcionTablero)
	
	resultado=InputList(TiposTablero,"Tipo de tablero",Starter.Secuencia(Starter.MaxSecuencias).tablero.tipo)
	If (resultado>=0) Then
		Starter.Secuencia(Starter.MaxSecuencias).tablero.tipo=resultado
		DibujarConfigurarSecuencia
	End If
End Sub

Sub ConfigIndicarHora_Click
	Dim TiposIndicador As List
	Dim resultado As Int
	
	TiposIndicador.Initialize
	TiposIndicador.AddAll(Starter.DescripcionMinutero)
	
	resultado=InputList(TiposIndicador,"Indicar hora actual",Starter.Secuencia(Starter.MaxSecuencias).tablero.indicar_hora)
	If (resultado>=0) Then
		Starter.Secuencia(Starter.MaxSecuencias).tablero.indicar_hora=resultado
		DibujarConfigurarSecuencia
	End If
End Sub

Sub ConfigTamIcono_ValueChanged (Valor As Int, Cambio As Boolean)
	If (Cambio==True) Then
		Starter.Secuencia(Starter.MaxSecuencias).tablero.tam_icono=Valor
	End If
End Sub

Sub BotonCancelar_Click 
	SalidaConfigurarSecuencia
End Sub

Sub SalidaConfigurarSecuencia
	If Msgbox2("Se perderán todos los cambios realizados."&CRLF&CRLF&"¿Está seguro de que desea salir sin guardarlos?","Cancelar cambios","Sí","","No",Null)==DialogResponse.POSITIVE Then
		Activity.Finish
	End If
End Sub	

Sub BotonAceptar_Click
	If (Starter.SecuenciaActiva==Starter.MaxSecuencias) Then 'Si es nueva, añadimos
		Starter.NumSecuencias=Starter.NumSecuencias+1	
		CallSub3(Starter,"CopiarSecuencias",Starter.MaxSecuencias,Starter.NumSecuencias-1)
	Else
		CallSub3(Starter,"CopiarSecuencias",Starter.MaxSecuencias,Starter.SecuenciaActiva) 'Si no, volvemos a copiar
	End If
	CallSub(Starter,"Guardar_Configuracion")
	Activity.Finish
End Sub
	
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ConfigPictograma_Click
	Dim im As IME
	im.Initialize("")
	im.HideKeyboard
	PictogramaEditado=-1
	Activity.AddView(ListaPictogramas, 5dip, 5dip, 100%X-10dip, 100%Y-10dip)
	ListaPictogramasVisible=True
End Sub

Sub ConfigPictogramaAct_Click
	Dim BotonPulsado As Label
	Dim im As IME
	im.Initialize("")
	im.HideKeyboard
	BotonPulsado=Sender
	PictogramaEditado=BotonPulsado.Tag
	Activity.AddView(ListaPictogramas, 5dip, 5dip, 100%X-10dip, 100%Y-10dip)
	ListaPictogramasVisible=True
End Sub

Sub ConfigOpcionesAct_Click
	Dim BotonPulsado As Label
	Dim Act As Int
	Dim Opciones As List
	Dim resultado As Int
	
	BotonPulsado=Sender
	Act=BotonPulsado.Tag
	
	Opciones.Initialize2(Array As String("Borrar actividad","CANCELAR"))
	resultado=InputList(Opciones,"Acción",-1)

	If resultado=0 Then
		For nAct=Act To Starter.Secuencia(Starter.MaxSecuencias).num_actividades-2
			'CallSub3(Starter,"IntercambiarActividades",Starter.MaxSecuencias,nAct,nAct+1)
			Starter.ActividadSecuencia(Starter.MaxSecuencias,nAct)=Starter.ActividadSecuencia(Starter.MaxSecuencias,nAct+1)
		Next
		Starter.Secuencia(Starter.MaxSecuencias).num_actividades=Starter.Secuencia(Starter.MaxSecuencias).num_actividades-1
	End If
	DibujarConfigurarSecuencia
End Sub

Sub ConfigHoraInicioAct_Click
	Dim DialogoTiempo As TimeDialog
	Dim BotonPulsado As Label
	Dim Resultado As Int
	Dim Act As Int

	BotonPulsado=Sender
	Act=BotonPulsado.Tag

	DialogoTiempo.Hour=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_inicio
	DialogoTiempo.Minute=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_inicio
	DialogoTiempo.Is24Hours=False
	Resultado=DialogoTiempo.Show("Indica la hora inicial de la actividad","Hora inicial","Aceptar","Cancelar","",Null)

	If Resultado=DialogResponse.POSITIVE Then
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_inicio=DialogoTiempo.Hour
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_inicio=DialogoTiempo.Minute
		If DialogoTiempo.Hour*60+DialogoTiempo.Minute>Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_fin*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_fin Then			
			'Se ha intentado poner una hora inicial posterior a la final
			'Se pone como hora de fin la misma de inicio
			Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_inicio
			Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_inicio
		End If
		If OrdenarActividades==True Then
			Msgbox("Se ha colocado la actividad en su posición correcta.","Actividades reorganizadas")
		End If
		DibujarConfigurarSecuencia
	End If
End Sub

Sub OrdenarActividades As Boolean
	Dim i,j As Int
	Dim hm_1,hm_2 As Int
	Dim ActInt As Actividad
	Dim IntercambioRealizado As Boolean
	
	IntercambioRealizado=False
	
	'Ordena las actividades por hora de inicio siguiendo el método de la burbuja
	For i=1 To Starter.Secuencia(Starter.MaxSecuencias).num_actividades-1
		For j=0 To Starter.Secuencia(Starter.MaxSecuencias).num_actividades-2
			hm_1=Starter.ActividadSecuencia(Starter.MaxSecuencias,j).hora_inicio*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,j).minuto_inicio
			hm_2=Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).hora_inicio*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).minuto_inicio
			If hm_1>hm_2 Then
				ActInt=Starter.ActividadSecuencia(Starter.MaxSecuencias,j)
				Starter.ActividadSecuencia(Starter.MaxSecuencias,j)=Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1)
				Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1)=ActInt
				IntercambioRealizado=True
			End If
		Next
	Next

	'Después de los cambios, comprobamos que no se hayan generado solapes
	QuitarSolapes

	Return IntercambioRealizado
End Sub

Sub QuitarSolapes As Boolean
	Dim hm_1,hm_2 As Int
	Dim j As Int
	Dim resultado As Boolean

	resultado=False
	'Comprueba que la hora de fin de una actividad no sea mayor a la de inicio de la siguiente
	For j=0 To Starter.Secuencia(Starter.MaxSecuencias).num_actividades-2
		hm_1=Starter.ActividadSecuencia(Starter.MaxSecuencias,j).hora_fin*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,j).minuto_fin
		hm_2=Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).hora_inicio*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).minuto_inicio
		If hm_1>hm_2 Then 'Si es así, las iguala
			Starter.ActividadSecuencia(Starter.MaxSecuencias,j).hora_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).hora_inicio
			Starter.ActividadSecuencia(Starter.MaxSecuencias,j).minuto_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,j+1).minuto_inicio
			resultado=True
		End If
	Next

	Return resultado
End Sub

Sub ConfigHoraFinalAct_Click
	Dim DialogoTiempo As TimeDialog
	Dim BotonPulsado As Label
	Dim Resultado As Int
	Dim Act As Int

	BotonPulsado=Sender
	Act=BotonPulsado.Tag

	DialogoTiempo.Hour=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_fin
	DialogoTiempo.Minute=Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_fin
	DialogoTiempo.Is24Hours=False
	Resultado=DialogoTiempo.Show("Indica la hora final de la actividad","Hora final","Aceptar","Cancelar","",Null)

	If Resultado=DialogResponse.POSITIVE Then
		If DialogoTiempo.Hour*60+DialogoTiempo.Minute<Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_inicio*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_inicio Then
			'Se ha intentado poner una hora final anterior a la incial
			Msgbox("La hora de finalización de una actividad no puede ser anterior a la de inicio.","Hora inválida")
		Else
			Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).hora_fin=DialogoTiempo.Hour
			Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).minuto_fin=DialogoTiempo.Minute
			If QuitarSolapes==True Then
				Msgbox("Se ha corregido la hora final de la actividad para evitar solapes.","Hora final corregida")
			End If
			DibujarConfigurarSecuencia
		End If
	End If
End Sub

Sub ListaPictogramas_ItemClick (Position As Int, Value As Object)
	ListaPictogramas.RemoveView
	ListaPictogramasVisible=False
	If PictogramaEditado==-1 Then
		Starter.Secuencia(Starter.MaxSecuencias).pictograma=Value
	Else
		Starter.ActividadSecuencia(Starter.MaxSecuencias,PictogramaEditado).Pictograma=Value
	End If
	DibujarConfigurarSecuencia
End Sub

Sub Inicializar_Lista_Pictogramas
	Dim fileList As List
	Dim file1 As String
	Dim file2 As String
	Dim n As Int

	If ListaPictogramas.IsInitialized=False Then

		ProgressDialogShow("Inicializando lista de pictogramas")

		ListaPictogramas.Initialize("ListaPictogramas")
		ListaPictogramas.Color=Colors.LightGray
		ListaPictogramas.TwoLinesAndBitmap.Label.TextColor=Colors.Black
		ListaPictogramas.TwoLinesAndBitmap.SecondLabel.Visible=False
		ListaPictogramas.TwoLinesAndBitmap.ImageView.Width=50dip
		ListaPictogramas.TwoLinesAndBitmap.ImageView.Height=50dip
		ListaPictogramas.Padding=Array As Int(5dip,5dip,5dip,5dip)
		ListaPictogramas.FastScrollEnabled = True

		fileList = File.ListFiles(File.DirAssets)
		fileList.Sort(True)

		For n = 0 To fileList.Size-1
			Sleep(0)
			file1 = fileList.Get(n)
			If file1.Contains(".png") Then
				file2=file1.Replace(".png","")
				Dim Bitmap1 As Bitmap
				Bitmap1.InitializeSample(File.DirAssets, file1, 50dip, 50dip)
				ListaPictogramas.AddTwoLinesAndBitmap(file2,"",Bitmap1)
			End If
		Next
		ListaPictogramasVisible=False
		
		ProgressDialogHide

	End If
	

End Sub

Sub ConfigDescripcionAct_FocusChanged (TieneFoco As Boolean)
	Dim BotonPulsado As EditText
	Dim Act As Int

	If TieneFoco==True Then
		BotonPulsado=Sender
		Act=BotonPulsado.Tag
		If ConfigDescripcionAct(Act).Text==DescripcionActividadPorDefecto Then
			ConfigDescripcionAct(Act).Text=""
			Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).Descripcion=""
			Activity.Invalidate
		End If
	End If
End Sub


Sub ConfigDescripcionAct_TextChanged (Old As String, New As String)
	Dim BotonPulsado As EditText
	Dim Act As Int

	If Inicializando==False Then
		BotonPulsado=Sender
		Act=BotonPulsado.Tag
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Act).Descripcion=New
	End If
End Sub

Sub ConfigDescripcion_TextChanged (Old As String, New As String)
	Starter.Secuencia(Starter.MaxSecuencias).descripcion=New
End Sub

Sub ConfigDescripcion_FocusChanged (TieneFoco As Boolean)
	If TieneFoco==True And ConfigDescripcion.Text==DescripcionSecuenciaPorDefecto Then
		ConfigDescripcion.Text=""
		Starter.Secuencia(Starter.MaxSecuencias).descripcion=""
		Activity.Invalidate
	End If
End Sub

Sub BotonAnadirActividad_Click
	Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).Descripcion=DescripcionActividadPorDefecto
	'Pone como hora de incio la final de la actividad anterior
	If (Starter.Secuencia(Starter.MaxSecuencias).num_actividades>0) Then
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_inicio=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades-1).hora_fin
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_inicio=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades-1).minuto_fin
	Else
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_inicio=8
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_inicio=0
	End If
	'Suma 30 minutos a la hora inicial para calcular la final
	Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_inicio
	Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_inicio+30
	If Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin>59 Then
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin-60
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_fin=Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_fin+1
	End If
	'...pero sin pasarse de las 23:59
	If Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_fin*60+Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin>(23*60+59) Then
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).hora_fin=23
		Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).minuto_fin=59
	End If
	
	Starter.ActividadSecuencia(Starter.MaxSecuencias,Starter.Secuencia(Starter.MaxSecuencias).num_actividades).Pictograma="jugar"
	Starter.Secuencia(Starter.MaxSecuencias).num_actividades=Starter.Secuencia(Starter.MaxSecuencias).num_actividades+1
	DibujarConfigurarSecuencia
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsar atrás...
		If ListaPictogramasVisible==True Then 'Si está abierta la lista de pictogramas, la cierra
			ListaPictogramas.RemoveView
			ListaPictogramasVisible=False
		Else 'Si esá cerrada, llama a la rutina de salida
			SalidaConfigurarSecuencia
		End If
	End If
	Return True
End Sub