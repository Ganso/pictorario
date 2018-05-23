B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.8
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

	Private Volver As Button
	Private Logotipo As ImageView
	Private Pictogramas As ImageView
	Private Pictorario As Label
	Private Programador As ImageView
	Private TextoArasaac As WebView
	Private TextoAutor As WebView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("AcercaDe")
	
	TextoAutor.LoadHtml("<html><body><center>"& _
	"<strong>Aplicación</strong>: Javier Prieto Martínez (www.ganso.org)<br />"& _
	"<strong>Licencia</strong>: CC (BY-NC-SA)<br />"& _
	"<em>Pulsar icono para web</em><br />"& _
	"</center></body></html>")

	TextoArasaac.LoadHtml("<html><body><center>"& _
	"<strong>Pictogramas</strong>: Sergio Palao<br />"& _
	"<strong>Procedencia</strong>: ARASAAC (www.arasaac.org)<br />"& _
	"<strong>Licencia</strong>: CC (BY-NC-SA)<br />"& _
	"<strong>Propiedad</strong>: Gobierno de Aragón<br />"& _
	"<em>Pulsar icono para web</em><br />"& _
	"</center></body></html>")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Volver_Click
	'Activity.LoadLayout("Portada")
	Activity.Finish
End Sub

Sub Programador_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://www.ganso.org"))
End Sub

Sub Pictogramas_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://www.arasaac.org"))
End Sub

Sub Logotipo_Click
	
End Sub