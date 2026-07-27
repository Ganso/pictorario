B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=8.5
@EndOfDesignText@
' Proyecto desarrollado por Javier Prieto Martínez
' Información, código fuente, documentación, etc. en http://blog.ganso.org/proyectos/pictorario
' Este código fuente se ofrece con una licencia Creative Commons de tipo Reconocimiento-NoComercial-CompartirIgual 3.0 España (CC BY-NC-SA 3.0 ES)

#Region  Service Attributes 
	#StartAtBoot: True
	#StartCommandReturnValue: android.app.Service.START_STICKY
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Service_Create
	
End Sub

Sub Service_Start (StartingIntent As Intent)
	CallSub(Starter,"Cargar_Configuracion")
	Service.StopAutomaticForeground 'Call this when the background task completes (if there is one)
End Sub

Sub Service_Destroy

End Sub
