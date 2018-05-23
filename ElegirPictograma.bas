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
	Dim ListaPictogramas As ListView

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Dim fileList As List
	Dim file1 As String
	Dim file2 As String
	Dim n As Int

	ListaPictogramas.Initialize("ListaPictogramas")
	ListaPictogramas.Color=Colors.LightGray
	ListaPictogramas.TwoLinesAndBitmap.Label.TextColor=Colors.Black
	ListaPictogramas.Padding=Array As Int(5dip,5dip,5dip,5dip)
	ListaPictogramas.FastScrollEnabled = True

	fileList = File.ListFiles(File.DirAssets)
	fileList.Sort(True)

	For n = 0 To fileList.Size-1
		file1 = fileList.Get(n)
		If file1.Contains(".png") Then
			file2=file1.Replace(".png","")
			Dim Bitmap1 As Bitmap
			Bitmap1.Initialize(File.DirAssets, file1)
			ListaPictogramas.AddTwoLinesAndBitmap(file2,"",Bitmap1)
		End If
	Next
End Sub

Sub MostrarListaPictogramas(Seleccionado As String)
	Starter.PictogramaSeleccionado=""
	Activity.AddView(ListaPictogramas, 5dip, 5dip, 100%X-10dip, 100%Y-10dip)
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub ListaPictogramas_ItemClick (Position As Int, Value As Object)
	Starter.PictogramaSeleccionado=Value
	ListaPictogramas.RemoveView
	CallSubDelayed(ConfigurarSecuencia,"ConfigPictograma_Actualizar")
	Activity.Finish
End Sub