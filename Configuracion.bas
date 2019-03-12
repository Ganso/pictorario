B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8.5
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
	Private ActivarAlarmasCheck As CheckBox
	Private ActivarAlarmasIcono As Label
	Private ActivarAlarmasLabel As Label
	Private ProtegerVisualizacionCheck As CheckBox
	Private ProtegerVisualizacionIcono As Label
	Private ProtegerVisualizacionLabel As Label
	Private Titulo As Label
	Private FormatoHorasButton As Button
	Private FormatoHorasLabel As Label
	Private ColoresRelojLabel As Label
	Private BotonHoras As Button
	Private BotonMinutos As Button
	Private BotonSegundos As Button
	Private BotonReiniciar As Button
	Private BotonVolver As Button
	Private PanelScroll As ScrollView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Configuracion")
	
	Dim Separador=10dip,Altura=60dip As Int

	ActivarAlarmasCheck.Initialize("ActivarAlarmasCheck")
	ActivarAlarmasIcono.Initialize("ActivarAlarmasIcono")
	ActivarAlarmasLabel.Initialize("ActivarAlarmasLabel")
	ProtegerVisualizacionCheck.Initialize("ProtegerVisualizacionCheck")
	ProtegerVisualizacionIcono.Initialize("ProtegerVisualizacionIcono")
	ProtegerVisualizacionLabel.Initialize("ProtegerVisualizacionLabel")
	FormatoHorasButton.Initialize("FormatoHorasButton")
	FormatoHorasLabel.Initialize("FormatoHorasLabel")
	ColoresRelojLabel.Initialize("ColoresRelojLabel")
	BotonHoras.Initialize("BotonHoras")
	BotonMinutos.Initialize("BotonMinutos")
	BotonSegundos.Initialize("BotonSegundos")
	BotonReiniciar.Initialize("BotonReiniciar")
	BotonVolver.Initialize("BotonVolver")
	
	ActivarAlarmasLabel.Text="Activar alarmas"
	ActivarAlarmasLabel.TextSize=16
	ActivarAlarmasLabel.Gravity=Gravity.CENTER_VERTICAL
	
	ProtegerVisualizacionLabel.Text="Proteger aplicación"
	ProtegerVisualizacionLabel.TextSize=16
	ProtegerVisualizacionLabel.Gravity=Gravity.CENTER_VERTICAL
	
	FormatoHorasLabel.Text="Formato horario"
	FormatoHorasLabel.TextSize=16
	FormatoHorasLabel.Gravity=Gravity.CENTER_VERTICAL
	
	ActivarAlarmasCheck.Checked=Starter.AlarmasActivadas
	ActivarAlarmasCheck.Gravity=Gravity.CENTER
	
	ProtegerVisualizacionCheck.Checked=Starter.AplicacionProtegida
	ProtegerVisualizacionCheck.Gravity=Gravity.CENTER
	
	ColoresRelojLabel.Text="Colores del reloj (horario, minutero y segundero)"
	ColoresRelojLabel.TextSize=16
	ColoresRelojLabel.Gravity=Gravity.CENTER_VERTICAL
	
	BotonHoras.Color=Starter.ColorHoras
	BotonMinutos.Color=Starter.ColorMinutos
	BotonSegundos.Color=Starter.ColorSegundos
	
	If Starter.Formato24h==True Then
		FormatoHorasButton.Text="24 horas"
	Else
		FormatoHorasButton.Text="12 horas"
	End If
	FormatoHorasButton.TextSize=20
	FormatoHorasButton.Gravity=Gravity.CENTER

	BotonReiniciar.Text="Reiniciar configuración"
	BotonReiniciar.TextSize=20
	BotonReiniciar.Gravity=Gravity.CENTER

	BotonVolver.Text="Volver a la portada"
	BotonVolver.TextSize=20
	BotonVolver.Gravity=Gravity.CENTER
	
	ActivarAlarmasIcono.SetBackgroundImage(LoadBitmap(File.DirAssets,"alarma.png"))
	ProtegerVisualizacionIcono.SetBackgroundImage(LoadBitmap(File.DirAssets,"llave.png"))
	
	PanelScroll.Panel.AddView(ActivarAlarmasLabel,10dip,0,100%X-2*Altura-4*Separador,Altura)
	PanelScroll.Panel.AddView(ActivarAlarmasIcono,ActivarAlarmasLabel.Left+ActivarAlarmasLabel.Width+Separador,ActivarAlarmasLabel.Top,Altura,Altura)
	PanelScroll.Panel.AddView(ActivarAlarmasCheck,ActivarAlarmasIcono.Left+ActivarAlarmasIcono.Width+Separador,ActivarAlarmasIcono.Top,Altura,Altura)

	PanelScroll.Panel.AddView(ProtegerVisualizacionLabel,ActivarAlarmasLabel.Left,ActivarAlarmasLabel.Top+ActivarAlarmasLabel.Height+Separador,ActivarAlarmasLabel.Width,ActivarAlarmasLabel.Height)
	PanelScroll.Panel.AddView(ProtegerVisualizacionIcono,ProtegerVisualizacionLabel.Left+ProtegerVisualizacionLabel.Width+Separador,ProtegerVisualizacionLabel.Top,Altura,Altura)
	PanelScroll.Panel.AddView(ProtegerVisualizacionCheck,ProtegerVisualizacionIcono.Left+ProtegerVisualizacionIcono.Width+Separador,ProtegerVisualizacionLabel.Top,Altura,ProtegerVisualizacionLabel.Height)

	PanelScroll.Panel.AddView(FormatoHorasLabel,ProtegerVisualizacionLabel.Left,ProtegerVisualizacionLabel.Top+ProtegerVisualizacionLabel.Height+Separador,ProtegerVisualizacionLabel.Width,ProtegerVisualizacionLabel.Height)
	PanelScroll.Panel.AddView(FormatoHorasButton,ProtegerVisualizacionIcono.Left,FormatoHorasLabel.Top,100%X-ProtegerVisualizacionIcono.Left-Separador,FormatoHorasLabel.Height)

	PanelScroll.Panel.AddView(ColoresRelojLabel,FormatoHorasLabel.Left,FormatoHorasLabel.Top+FormatoHorasLabel.Height+Separador,FormatoHorasLabel.Width,FormatoHorasLabel.Height)

	Dim TamBotonesColores=(100%X-ColoresRelojLabel.Left-ColoresRelojLabel.Width-4*Separador)/3 As Int
	PanelScroll.Panel.AddView(BotonHoras,ColoresRelojLabel.Left+ColoresRelojLabel.Width+Separador,ColoresRelojLabel.Top,TamBotonesColores,TamBotonesColores)
	PanelScroll.Panel.AddView(BotonMinutos,BotonHoras.Left+BotonHoras.Width+Separador,ColoresRelojLabel.Top,TamBotonesColores,TamBotonesColores)
	PanelScroll.Panel.AddView(BotonSegundos,BotonMinutos.Left+BotonMinutos.Width+Separador,ColoresRelojLabel.Top,TamBotonesColores,TamBotonesColores)

	PanelScroll.Panel.AddView(BotonReiniciar,Separador,ColoresRelojLabel.Top+ColoresRelojLabel.Height+Separador,100%X-2*Separador,Altura)

	PanelScroll.Panel.AddView(BotonVolver,Separador,BotonReiniciar.Top+BotonReiniciar.Height+Separador,100%X-2*Separador,Altura)
	
	PanelScroll.Panel.Height=BotonVolver.Top+BotonVolver.Height+Separador
		
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Activity_KeyPress (KeyCode As Int)
	If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsar atrás...
		Sleep(0) 'No hace nada
	End If
End Sub

Sub ProtegerVisualizacionCheck_CheckedChange(Checked As Boolean)
	Starter.AplicacionProtegida=ProtegerVisualizacionCheck.Checked
	If Starter.AplicacionProtegida==True Then
		Msgbox2("Para liberar la protección, hacer:"&CRLF&CRLF&"UNA PULSACIÓN CORTA"&CRLF&"  seguida de"&CRLF&"UNA PULSACIÓN LARGA"&CRLF&CRLF&"Sobre el icono del candado del resto de pantallas.","IMPORTANTE:"&CRLF&"Aplicación protegida","Aceptar","","",LoadBitmap(File.DirAssets,"candado.png"))
	End If
	CallSub(Starter,"Guardar_Configuracion")
End Sub

Sub BotonHoras_Click
	Dim DialogoColor As ColorPickerDialog
	Dim Resultado As Int
	
	DialogoColor.RGB=Starter.ColorHoras
	Resultado=DialogoColor.Show("Color del horario","Seleccionar","Cancelar","", Null)
	If Resultado=DialogResponse.POSITIVE Then
		Starter.ColorHoras=DialogoColor.RGB
		BotonHoras.Color=DialogoColor.RGB
		CallSub(Starter,"Guardar_Configuracion")
	End If
End Sub

Sub BotonMinutos_Click
	Dim DialogoColor As ColorPickerDialog
	Dim Resultado As Int
	
	DialogoColor.RGB=Starter.ColorMinutos
	Resultado=DialogoColor.Show("Color del minutero","Seleccionar","Cancelar","", Null)
	If Resultado=DialogResponse.POSITIVE Then
		Starter.ColorMinutos=DialogoColor.RGB
		BotonMinutos.Color=DialogoColor.RGB
		CallSub(Starter,"Guardar_Configuracion")
	End If
End Sub

Sub BotonSegundos_Click
	Dim DialogoColor As ColorPickerDialog
	Dim Resultado As Int
	
	DialogoColor.RGB=Starter.ColorSegundos
	Resultado=DialogoColor.Show("Color del segundero","Seleccionar","Cancelar","", Null)
	If Resultado=DialogResponse.POSITIVE Then
		Starter.ColorSegundos=DialogoColor.RGB
		BotonSegundos.Color=DialogoColor.RGB
		CallSub(Starter,"Guardar_Configuracion")
	End If
End Sub

Sub BotonVolver_Click
	Activity.Finish
End Sub

Sub ActivarAlarmasCheck_CheckedChange(Checked As Boolean)
	Starter.AlarmasActivadas=ActivarAlarmasCheck.Checked
	CallSub(Starter,"Guardar_Configuracion")
End Sub

Sub FormatoHorasButton_Click
	Starter.Formato24h=Not(Starter.Formato24h)
	If Starter.Formato24h==True Then
		FormatoHorasButton.Text="24 horas"
	Else
		FormatoHorasButton.Text="12 horas"
	End If
	CallSub(Starter,"Guardar_Configuracion")
End Sub

Sub BotonReiniciar_Click
	If Msgbox2("Se borrarán todas las secuencias creadas y se dejará solo la de ejemplo, y se reiniciará toda la configuración."&CRLF&CRLF& _
		"¿Está seguro de que desea hacer esto?","Borrar toda la configuración","Sí","","No",Null)==DialogResponse.POSITIVE Then
		CallSub(Starter,"Inicializar_Con_Ejemplo")
		CallSub(Starter,"BorrarPictogramas")
		CallSub(Starter,"Guardar_Configuracion")
		Activity.Finish
	End If
End Sub