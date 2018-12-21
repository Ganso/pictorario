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
	Private ActivarAlarmasIcono As ImageView
	Private ActivarAlarmasLabel As Label
	Private BotonVolver As Button
	Private ProtegerVisualizacionCheck As CheckBox
	Private ProtegerVisualizacionIcono As ImageView
	Private ProtegerVisualizacionLabel As Label
	Private Titulo As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Configuracion")
	ActivarAlarmasCheck.Checked=Starter.AlarmasActivadas
	ProtegerVisualizacionCheck.Checked=Starter.AplicacionProtegida
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

Sub BotonVolver_Click
	Activity.Finish
End Sub

Sub ActivarAlarmasCheck_CheckedChange(Checked As Boolean)
	Starter.AlarmasActivadas=ActivarAlarmasCheck.Checked
	CallSub(Starter,"Guardar_Configuracion")
End Sub