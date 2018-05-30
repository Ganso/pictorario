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
	Dim Temporizador As Timer
	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private PanelDescripcion As Panel
	Private PanelReloj As Panel
	Private ImagenPictograma As ImageView
	Private FondoPictograma As Panel
	Private TextoPictograma As Label
	Private PanelAgujas As Panel
	
	Dim Pantalla As Canvas 'Región donde se dibuja la visualización
	Dim PantallaAgujas As Canvas
	
	'Posiciones del Reloj
	Dim CentroX, CentroY, Radio As Float
	Dim MinHora,MaxHora As Int
	
	'Botones de Actividades
	Dim Boton(Starter.MaxActividades) As Button

	Private CambiarVista As Button
	Private DescripcionPictograma As Label
	
	Private Volver As Button
	
	'Ángulo de inicio y fin de cada actividad
	Dim AnguloInicio(Starter.MaxActividades) As Float
	Dim AnguloFin(Starter.MaxActividades) As Float

End Sub

Sub Activity_Create(FirstTime As Boolean)

	Activity.LoadLayout("VisualizarSecuencia")
	DibujarTablero
	Temporizador.Initialize("Temporizador",1000)
	Temporizador.Enabled=True
	
End Sub

Sub DibujarTablero()
	
	CentroX=50%x
	CentroY=60%x
	Radio=45%x
	
	Pantalla.Initialize(PanelReloj)
	PantallaAgujas.Initialize(PanelAgujas)

	'Horas a representar
	If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo<2 Then 'Reloj 12h
		MinHora=1
		MaxHora=12
	Else If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==2 Then 'Reloj 24h
		MinHora=1
		MaxHora=24
	Else 'Arco
		MinHora=Starter.ActividadSecuencia(Starter.SecuenciaActiva,0).hora_inicio
		MaxHora=Starter.ActividadSecuencia(Starter.SecuenciaActiva,Starter.Secuencia(Starter.SecuenciaActiva).num_actividades-1).hora_fin
		If (Starter.ActividadSecuencia(Starter.SecuenciaActiva,Starter.Secuencia(Starter.SecuenciaActiva).num_actividades-1).minuto_fin<>0) Then
			MaxHora=MaxHora+1
		End If
	End If
	
	'Círculo o arco del reloj
	If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo<3 Then 'Reloj
		'Marco y Reloj
		Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,Colors.Gray,True,0)
		Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors.White,True,0)
	Else 'Arco
		Dim Recorte As Path

		Recorte.Initialize(CentroX,CentroY+3%Y)
		Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (SinD(114)*Radio*3)+CentroY)
		Recorte.LineTo(0,100%Y)
		Recorte.LineTo(0,0)
		Recorte.LineTo(100%X,0)
		Recorte.LineTo(100%X,100%Y)
		Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (SinD(81)*Radio)+CentroY)
		Pantalla.ClipPath(Recorte)
		Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,Colors.Gray,True,0)
		Pantalla.RemoveClip

		Recorte.Initialize(CentroX,CentroY)
		Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (SinD(116)*Radio*3)+CentroY)
		Recorte.LineTo(0,100%Y)
		Recorte.LineTo(0,0)
		Recorte.LineTo(100%X,0)
		Recorte.LineTo(100%X,100%Y)
		Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (SinD(80)*Radio)+CentroY)
		Pantalla.ClipPath(Recorte)
		Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors.White,True,0)
		Pantalla.RemoveClip
	End If
		
	For Hora=MinHora To MaxHora Step 1
		'Dibuja un punto en cada hora
		Dim X=HoraMinuto_X(Hora,0,0.95) As Float
		Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float
		'Crea una etiqueta con cada número de hora
		Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightGray,True,0)
		Dim X=HoraMinuto_X(Hora,0,0.85) As Float
		Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float
		Dim NumeroHora As Label
		NumeroHora.Initialize("")
		NumeroHora.Text=(Hora) Mod 24
		NumeroHora.TextColor=Colors.DarkGray
		NumeroHora.Gravity=Gravity.CENTER
		NumeroHora.TextSize=15
		Activity.AddView(NumeroHora,X-15dip,Y-15dip,30dip,30dip)
	Next

	'Ponemos las horas reales con las que vamos a comparar la actual para saber si dibujar o no las manecillas
	Select Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo
		Case 0
			MinHora=0
			MaxHora=11
		Case 1
			MinHora=12
			MaxHora=23
		Case 2
			MinHora=0
			MaxHora=23
	End Select
	
	'Inicializa los valores de los ángulos
	For NActividad=0 To Starter.MaxActividades-1
		AnguloInicio(NActividad)=-1
		AnguloFin(NActividad)=-1
	Next
	
	'Actividades
	For NActividad=0 To Starter.Secuencia(Starter.SecuenciaActiva).num_actividades-1 Step 1
		DibujarActividad(NActividad)
	Next

	'Botones
	For NActividad=0 To Starter.Secuencia(Starter.SecuenciaActiva).num_actividades-1 Step 1
		DibujarBoton(NActividad)
	Next
	
	'Indica modo de visualización
	Select Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo
		Case 0
			CambiarVista.Text="Mañana"
		Case 1
			CambiarVista.Text="Tarde"
		Case 2
			CambiarVista.Text="Día"
		Case 3
			CambiarVista.Text="Secuencia"
	End Select
			
	DibujasAgujas
			
End Sub

Sub DibujasAgujas
	Dim RectanguloVacio As Rect
	Dim HoraActual=DateTime.GetHour(DateTime.Now) As Int
	Dim MinutoActual=DateTime.GetMinute(DateTime.Now) As Int
	Dim SegundoActual=DateTime.GetSecond(DateTime.Now) As Int
	
	RectanguloVacio.Initialize(0,0,PanelAgujas.Width,PanelAgujas.Height)
	PantallaAgujas.DrawRect(RectanguloVacio, Colors.Transparent, True, 0)
	
	If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.indicar_hora>0 And HoraActual>=MinHora And HoraActual<MaxHora) Then
		If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo=3 Or Starter.Secuencia(Starter.SecuenciaActiva).tablero.indicar_hora==1) Then
			PantallaAgujas.DrawLine(CentroX,CentroY,HoraMinuto_X(HoraActual,MinutoActual,0.8),HoraMinuto_Y(HoraActual,MinutoActual,0.8),Colors.Red,8dip)
		Else
			PantallaAgujas.DrawLine(CentroX,CentroY,HoraMinuto_X(HoraActual,MinutoActual,0.6),HoraMinuto_Y(HoraActual,MinutoActual,0.6),Colors.DarkGray,8dip)
			If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.indicar_hora>1) Then
				Dim AnguloMinuto=270+(MinutoActual*6) As Float
				PantallaAgujas.DrawLine(CentroX,CentroY,CentroX+CosD(AnguloMinuto)*Radio*0.8,CentroY+SinD(AnguloMinuto)*Radio*0.75,Colors.DarkGray,6dip)
				If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.indicar_hora>2) Then
					Dim AnguloSegundo=270+(SegundoActual*6) As Float
					PantallaAgujas.DrawLine(CentroX,CentroY,CentroX+CosD(AnguloSegundo)*Radio*0.9,CentroY+SinD(AnguloSegundo)*Radio*0.9,Colors.Red,4dip)
				End If
			End If
		End If
	End If
	
	'Punto Central
	PantallaAgujas.DrawCircle(CentroX,CentroY,Radio*0.1,Colors.Black,True,0)
End Sub

Sub DibujarActividad(NumActividad As Int)
	
	'Descarta las que no estén en nuestra franja
	If ( Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo>1 Or (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==1 And Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_fin>11) Or (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==0 And Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_inicio<12) ) Then
	
		'Captura horas de actividad
		Dim HoraInicio=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_inicio As Int
		Dim MinInicio=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).minuto_inicio As Int
		Dim HoraFin=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_fin As Int
		Dim MinFin=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).minuto_fin As Int
		If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==0 And HoraFin>11) Then
			HoraFin=12
			MinFin=0
		Else If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==1 And HoraInicio<12) Then
			HoraInicio=12
			MinInicio=0
		End If
	
		'Hora Mitad
		Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float
		Dim MinutoMitad=(MinInicio+MinFin)/2 As Float
		
		'Triángulo de recorte
		Dim Recorte As Path
		Recorte.Initialize(CentroX,CentroY)
		Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInicio,1.5) , HoraMinuto_Y(HoraInicio,MinInicio,1.5) )
		Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMitad,1.5), HoraMinuto_Y(HoraMitad,MinutoMitad,1.5))
		Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,1.5) , HoraMinuto_Y(HoraFin,MinFin,1.5) )
		Recorte.LineTo(CentroX,CentroY)
		'Anota los ángulos iniciales y finales de la actividad para despues identificar toques
		AnguloInicio(NumActividad)=NormalizarAngulo(ATan2D(HoraMinuto_Y(HoraInicio,MinInicio,1.5)-CentroY,HoraMinuto_X(HoraInicio,MinInicio,1.5)-CentroX) Mod 360)
		AnguloFin(NumActividad)=NormalizarAngulo(ATan2D(HoraMinuto_Y(HoraFin,MinFin,1.5)-CentroY,HoraMinuto_X(HoraFin,MinFin,1.5)-CentroX) Mod 360)
		If AnguloFin(NumActividad)=0 Then
			AnguloFin(NumActividad)=360
		End If
		
		'Arco de circunferencia
		Pantalla.ClipPath(Recorte)
		Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,Starter.Colores(NumActividad),True,0)
		Pantalla.RemoveClip
		
	End If
	
End Sub

Sub DibujarBoton(NumActividad As Int)		
		
	'Descarta las que no estén en nuestra franja
	If ( Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo>1 Or (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==1 And Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_fin>11) Or (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==0 And Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_inicio<12) ) Then
	
		'Captura horas de actividad
		Dim HoraInicio=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_inicio As Int
		Dim MinInicio=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).minuto_inicio As Int
		Dim HoraFin=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).hora_fin As Int
		Dim MinFin=Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).minuto_fin As Int
		If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==0 And HoraFin>11) Then
			HoraFin=12
			MinFin=0
		Else If (Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==1 And HoraInicio<12) Then
			HoraInicio=12
			MinInicio=0
		End If
	
		'Hora Mitad
		Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float
		Dim MinutoMitad=(MinInicio+MinFin)/2 As Float
		
		'Botón
		Boton(NumActividad).Initialize("BotonActividad")
		Boton(NumActividad).Tag=NumActividad
		Dim DistanciaBoton=0.3+0.1*(NumActividad Mod 3) As Float 'Varía la distancia al centro en grupos de tres
		Dim TamañoIcono=Starter.Secuencia(Starter.SecuenciaActiva).tablero.tam_icono*1%X As Float
		Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,DistanciaBoton)-TamañoIcono/2 As Float
		Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,DistanciaBoton)-TamañoIcono/2 As Float
		'Dim BordeBoton As Rect
		'BordeBoton.Initialize(BotonX-1dip,BotonY-1dip,BotonX+TamañoIcono+2dip,BotonY+TamañoIcono+2dip)
		'Pantalla.DrawRect(BordeBoton,Colors.Black,True,0)
		Activity.AddView(Boton(NumActividad),BotonX,BotonY,TamañoIcono,TamañoIcono)
		Boton(NumActividad).SetBackgroundImage(LoadBitmap(File.DirAssets,Starter.ActividadSecuencia(Starter.SecuenciaActiva,NumActividad).pictograma&".png"))
	End If

End Sub

Sub HoraMinuto_X(Hora As Float,Minuto As Float,Distancia As Float) As Float
	If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==3 Then
		Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(MaxHora-MinHora) As Float
		Return (CosD(Angulo)*Radio*Distancia)+CentroX
	Else
		If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==2 Then
			Hora=Hora/2
			Minuto=Minuto/2
		End If
		Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radio*Distancia)+CentroX
	End If
End Sub

Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Distancia As Float) As Float
	If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==3 Then
		Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(MaxHora-MinHora) As Float
		Return (SinD(Angulo)*Radio*Distancia)+CentroY
	Else
		If Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo==2 Then
			Hora=Hora/2
			Minuto=Minuto/2
		End If
		Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radio*Distancia)+CentroY
	End If
End Sub

Sub Hora24a12 (Hora24 As Int) As Int
	If (Hora24>11) Then
		Return Hora24-12
	Else
		Return Hora24
	End If
End Sub

Sub MinutoLegible (Minuto As Int) As String
	If (Minuto==0) Then
		Return ""
	Else If (Minuto==15) Then
		Return " y cuarto"
	Else If (Minuto==30) Then
		Return " y media"
	Else
		Return ":"&NumberFormat(Minuto,2,0)
	End If
End Sub

Sub BotonActividad_click
	Dim BotonPulsado As Button
	BotonPulsado=Sender
	ActivarBoton(BotonPulsado.Tag)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub PanelReloj_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Sub CambiarVista_Click
	Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo=((Starter.Secuencia(Starter.SecuenciaActiva).tablero.tipo)+1) Mod 4
	Activity.RemoveAllViews
	Activity.Invalidate
	Activity.LoadLayout("VisualizarSecuencia")
	DibujarTablero
End Sub

Sub Volver_Click
	Activity.Finish
End Sub

Sub Temporizador_Tick
	DibujasAgujas	
	Activity.Invalidate
End Sub

Sub PanelAgujas_Touch(Accion As Int, x As Float, y As Float) As Boolean
	Dim Angulo As Float
	Dim i As Int
	If Accion = Activity.ACTION_DOWN Then
		Angulo=NormalizarAngulo(ATan2D(y-CentroY,x-CentroX))
		For i=0 To Starter.Secuencia(Starter.SecuenciaActiva).num_actividades-1
			If Angulo>=AnguloInicio(i) And Angulo<=AnguloFin(i) Then
				ActivarBoton(i)
			End If
		Next
	End If
	Return True
End Sub

Sub NormalizarAngulo(Angulo As Float) As Float
	Angulo=Angulo+90 'Pone el 0 arriba
	If Angulo<0 Then
		Return Angulo+360
	Else
		Return Angulo
	End If
End Sub

Sub ActivarBoton(i As Int)
	ImagenPictograma.Bitmap=LoadBitmap(File.DirAssets,Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).pictograma & ".png")
	TextoPictograma.Text=Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).descripcion.ToUpperCase
	DescripcionPictograma.Text="De "&Hora24a12(Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).hora_inicio)&MinutoLegible(Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).minuto_inicio)&" a "&Hora24a12(Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).hora_fin)&MinutoLegible(Starter.ActividadSecuencia(Starter.SecuenciaActiva,i).minuto_fin)
	FondoPictograma.Color=Starter.Colores(i)
	Boton(i).BringToFront()
End Sub
