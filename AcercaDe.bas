B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.8
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
	Private Volver As Button
	Private Logotipo As ImageView
	Private Pictogramas As ImageView
	Private Pictorario As Label
	Private Programador As ImageView
	Private TextoArasaac As Label
	Private TextoAutor As Label
	Private ParaTeo As Label
	Private VersionApp As Label
	Private VerVideo As Button
	Private PulsaLosIconos As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Dim cs1,cs2 As CSBuilder

	Activity.LoadLayout("AcercaDe")
	
	TextoAutor.Text=cs1.Initialize.Bold.Append("Aplicación: ").Pop.Append("Javier Prieto Martínez (www.ganso.org)").Append(CRLF).Bold.Append("Licencia: ").Pop.Append("CC (BY-NC-SA)")
	TextoArasaac.Text=cs2.Initialize.Bold.Append("Pictogramas: ").Pop.Append("Sergio Palao").Append(CRLF).Bold.Append("Procedencia: ").Pop.Append("ARASAAC (www.arasaac.org)").Append(CRLF).Bold.Append("Licencia: ").Pop.Append("CC (BY-NC-SA)").Append(CRLF).Bold.Append("Propiedad: ").Pop.Append("Gobierno de Aragón")
	
	'Ajusta cada caja a su altura
	Dim SU As StringUtils
	Do While SU.MeasureMultilineTextHeight(Pictorario,Pictorario.Text)>Pictorario.Height
		Pictorario.TextSize=Pictorario.TextSize-1
	Loop
	Do While SU.MeasureMultilineTextHeight(TextoAutor,TextoAutor.Text)>TextoAutor.Height
		TextoAutor.TextSize=TextoAutor.TextSize-1
	Loop
	Do While SU.MeasureMultilineTextHeight(TextoArasaac,TextoArasaac.Text)>TextoArasaac.Height
		TextoArasaac.TextSize=TextoArasaac.TextSize-1
	Loop
	Do While SU.MeasureMultilineTextHeight(PulsaLosIconos,PulsaLosIconos.Text)>PulsaLosIconos.Height
		PulsaLosIconos.TextSize=PulsaLosIconos.TextSize-1
	Loop
	
'	Dim GreatVives As Typeface
'	GreatVives=Typeface.LoadFromAssets("GreatVibes-Regular.ttf")
'	ParaTeo.Typeface=GreatVives
		
	VersionApp.Text=Application.VersionName
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Volver_Click
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
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://blog.ganso.org/proyectos/pictorario"))
End Sub

Sub VerVideo_Click
	Dim p As PhoneIntents
	'StartActivity(p.OpenBrowser("https://www.youtube.com/watch?v=cjTAGguz5H0"))
	StartActivity(p.OpenBrowser("http://Bit.ly/VideoPictorario"))
End Sub

Sub VersionApp_Click
	Msgbox2("Novedades de la versión:"&CRLF&CRLF&Starter.CambiosVersion,"Versión "&Application.VersionName,"Continuar","","",Null)
End Sub

Sub Activity_KeyPress (KeyCode As Int) 
	If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsar atrás...
		Sleep(0) 'No hace nada
	End If
End Sub