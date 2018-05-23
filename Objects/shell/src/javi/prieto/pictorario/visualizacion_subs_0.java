package javi.prieto.pictorario;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class visualizacion_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,35);
if (RapidSub.canDelegate("activity_create")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(4);
 BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
Debug.ShouldStop(16);
visualizacion.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("RelojActividades")),visualizacion.mostCurrent.activityBA);
 BA.debugLineNum = 38;BA.debugLine="DibujarTablero";
Debug.ShouldStop(32);
_dibujartablero();
 BA.debugLineNum = 40;BA.debugLine="End Sub";
Debug.ShouldStop(128);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_pause(RemoteObject _userclosed) throws Exception{
try {
		Debug.PushSubsStack("Activity_Pause (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,306);
if (RapidSub.canDelegate("activity_pause")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 306;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 308;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_resume() throws Exception{
try {
		Debug.PushSubsStack("Activity_Resume (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,302);
if (RapidSub.canDelegate("activity_resume")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","activity_resume");
 BA.debugLineNum = 302;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(8192);
 BA.debugLineNum = 304;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botonactividad_click() throws Exception{
try {
		Debug.PushSubsStack("BotonActividad_click (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,290);
if (RapidSub.canDelegate("botonactividad_click")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","botonactividad_click");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _actividadpulsada = RemoteObject.declareNull("javi.prieto.pictorario.starter._actividad");
 BA.debugLineNum = 290;BA.debugLine="Sub BotonActividad_click";
Debug.ShouldStop(2);
 BA.debugLineNum = 291;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(4);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 292;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(8);
_botonpulsado.setObject(visualizacion.mostCurrent.__c.runMethod(false,"Sender",visualizacion.mostCurrent.activityBA));
 BA.debugLineNum = 293;BA.debugLine="Dim ActividadPulsada As Actividad";
Debug.ShouldStop(16);
_actividadpulsada = RemoteObject.createNew ("javi.prieto.pictorario.starter._actividad");Debug.locals.put("ActividadPulsada", _actividadpulsada);
 BA.debugLineNum = 294;BA.debugLine="ActividadPulsada=Starter.ActividadSecuencia(Start";
Debug.ShouldStop(32);
_actividadpulsada = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag")));Debug.locals.put("ActividadPulsada", _actividadpulsada);
 BA.debugLineNum = 295;BA.debugLine="ImagenPictograma.Bitmap=LoadBitmap(File.DirAssets";
Debug.ShouldStop(64);
visualizacion.mostCurrent._imagenpictograma.runMethod(false,"setBitmap",(visualizacion.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(visualizacion.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.concat(visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"))).getField(true,"Pictograma"),RemoteObject.createImmutable(".png")))).getObject()));
 BA.debugLineNum = 296;BA.debugLine="TextoPictograma.Text=ActividadPulsada.descripcion";
Debug.ShouldStop(128);
visualizacion.mostCurrent._textopictograma.runMethod(true,"setText",BA.ObjectToCharSequence(_actividadpulsada.getField(true,"Descripcion").runMethod(true,"toUpperCase")));
 BA.debugLineNum = 297;BA.debugLine="DescripcionPictograma.Text=\"De \"&Hora24a12(Activi";
Debug.ShouldStop(256);
visualizacion.mostCurrent._descripcionpictograma.runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("De "),_hora24a12(_actividadpulsada.getField(true,"hora_inicio")),_minutolegible(_actividadpulsada.getField(true,"minuto_inicio")),RemoteObject.createImmutable(" a "),_hora24a12(_actividadpulsada.getField(true,"hora_fin")),_minutolegible(_actividadpulsada.getField(true,"minuto_fin")))));
 BA.debugLineNum = 298;BA.debugLine="FondoPictograma.Color=Starter.Colores(BotonPulsad";
Debug.ShouldStop(512);
visualizacion.mostCurrent._fondopictograma.runVoidMethod ("setColor",visualizacion.mostCurrent._starter._colores.getArrayElement(true,BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"))));
 BA.debugLineNum = 299;BA.debugLine="BotonPulsado.BringToFront()";
Debug.ShouldStop(1024);
_botonpulsado.runVoidMethod ("BringToFront");
 BA.debugLineNum = 300;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _cambiarvista_click() throws Exception{
try {
		Debug.PushSubsStack("CambiarVista_Click (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,315);
if (RapidSub.canDelegate("cambiarvista_click")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","cambiarvista_click");
 BA.debugLineNum = 315;BA.debugLine="Sub CambiarVista_Click";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 316;BA.debugLine="Starter.Secuencia(Starter.SecuenciaActiva).tabler";
Debug.ShouldStop(134217728);
visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").setField ("tipo",RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {(visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo")),RemoteObject.createImmutable(1)}, "+",1, 1)),RemoteObject.createImmutable(4)}, "%",0, 1));
 BA.debugLineNum = 317;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(268435456);
visualizacion.mostCurrent._activity.runVoidMethod ("RemoveAllViews");
 BA.debugLineNum = 318;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(536870912);
visualizacion.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 319;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
Debug.ShouldStop(1073741824);
visualizacion.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("RelojActividades")),visualizacion.mostCurrent.activityBA);
 BA.debugLineNum = 320;BA.debugLine="DibujarTablero";
Debug.ShouldStop(-2147483648);
_dibujartablero();
 BA.debugLineNum = 321;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _dibujaractividad(RemoteObject _numactividad) throws Exception{
try {
		Debug.PushSubsStack("DibujarActividad (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,167);
if (RapidSub.canDelegate("dibujaractividad")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","dibujaractividad", _numactividad);
RemoteObject _horainicio = RemoteObject.createImmutable(0);
RemoteObject _mininicio = RemoteObject.createImmutable(0);
RemoteObject _horafin = RemoteObject.createImmutable(0);
RemoteObject _minfin = RemoteObject.createImmutable(0);
RemoteObject _horamitad = RemoteObject.createImmutable(0f);
RemoteObject _minutomitad = RemoteObject.createImmutable(0f);
RemoteObject _recorte = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper");
Debug.locals.put("NumActividad", _numactividad);
 BA.debugLineNum = 167;BA.debugLine="Sub DibujarActividad(NumActividad As Int)";
Debug.ShouldStop(64);
 BA.debugLineNum = 170;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
Debug.ShouldStop(512);
if ((RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) || RemoteObject.solveBoolean(".",BA.ObjectToBoolean((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) && RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_fin"),BA.numberCast(double.class, 11))))) || RemoteObject.solveBoolean(".",BA.ObjectToBoolean((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 0)) && RemoteObject.solveBoolean("<",visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_inicio"),BA.numberCast(double.class, 12))))))) { 
 BA.debugLineNum = 173;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
Debug.ShouldStop(4096);
_horainicio = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_inicio");Debug.locals.put("HoraInicio", _horainicio);Debug.locals.put("HoraInicio", _horainicio);
 BA.debugLineNum = 174;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
Debug.ShouldStop(8192);
_mininicio = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"minuto_inicio");Debug.locals.put("MinInicio", _mininicio);Debug.locals.put("MinInicio", _mininicio);
 BA.debugLineNum = 175;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
Debug.ShouldStop(16384);
_horafin = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_fin");Debug.locals.put("HoraFin", _horafin);Debug.locals.put("HoraFin", _horafin);
 BA.debugLineNum = 176;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
Debug.ShouldStop(32768);
_minfin = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"minuto_fin");Debug.locals.put("MinFin", _minfin);Debug.locals.put("MinFin", _minfin);
 BA.debugLineNum = 177;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
Debug.ShouldStop(65536);
if ((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 0)) && RemoteObject.solveBoolean(">",_horafin,BA.numberCast(double.class, 11)))) { 
 BA.debugLineNum = 178;BA.debugLine="HoraFin=12";
Debug.ShouldStop(131072);
_horafin = BA.numberCast(int.class, 12);Debug.locals.put("HoraFin", _horafin);
 BA.debugLineNum = 179;BA.debugLine="MinFin=0";
Debug.ShouldStop(262144);
_minfin = BA.numberCast(int.class, 0);Debug.locals.put("MinFin", _minfin);
 }else 
{ BA.debugLineNum = 180;BA.debugLine="Else If (Starter.Secuencia(Starter.SecuenciaActi";
Debug.ShouldStop(524288);
if ((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) && RemoteObject.solveBoolean("<",_horainicio,BA.numberCast(double.class, 12)))) { 
 BA.debugLineNum = 181;BA.debugLine="HoraInicio=12";
Debug.ShouldStop(1048576);
_horainicio = BA.numberCast(int.class, 12);Debug.locals.put("HoraInicio", _horainicio);
 BA.debugLineNum = 182;BA.debugLine="MinInicio=0";
Debug.ShouldStop(2097152);
_mininicio = BA.numberCast(int.class, 0);Debug.locals.put("MinInicio", _mininicio);
 }}
;
 BA.debugLineNum = 186;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
Debug.ShouldStop(33554432);
_horamitad = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_horainicio,_horafin}, "+",1, 1)),RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("HoraMitad", _horamitad);Debug.locals.put("HoraMitad", _horamitad);
 BA.debugLineNum = 187;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
Debug.ShouldStop(67108864);
_minutomitad = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_mininicio,_minfin}, "+",1, 1)),RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("MinutoMitad", _minutomitad);Debug.locals.put("MinutoMitad", _minutomitad);
 BA.debugLineNum = 190;BA.debugLine="Dim Recorte As Path";
Debug.ShouldStop(536870912);
_recorte = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper");Debug.locals.put("Recorte", _recorte);
 BA.debugLineNum = 191;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
Debug.ShouldStop(1073741824);
_recorte.runVoidMethod ("Initialize",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy));
 BA.debugLineNum = 192;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
Debug.ShouldStop(-2147483648);
_recorte.runVoidMethod ("LineTo",(Object)(_horaminuto_x(BA.numberCast(float.class, _horainicio),BA.numberCast(float.class, _mininicio),BA.numberCast(float.class, 1.5))),(Object)(_horaminuto_y(BA.numberCast(float.class, _horainicio),BA.numberCast(float.class, _mininicio),BA.numberCast(float.class, 1.5))));
 BA.debugLineNum = 193;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
Debug.ShouldStop(1);
_recorte.runVoidMethod ("LineTo",(Object)(_horaminuto_x(_horamitad,_minutomitad,BA.numberCast(float.class, 1.5))),(Object)(_horaminuto_y(_horamitad,_minutomitad,BA.numberCast(float.class, 1.5))));
 BA.debugLineNum = 194;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,1.5)";
Debug.ShouldStop(2);
_recorte.runVoidMethod ("LineTo",(Object)(_horaminuto_x(BA.numberCast(float.class, _horafin),BA.numberCast(float.class, _minfin),BA.numberCast(float.class, 1.5))),(Object)(_horaminuto_y(BA.numberCast(float.class, _horafin),BA.numberCast(float.class, _minfin),BA.numberCast(float.class, 1.5))));
 BA.debugLineNum = 195;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
Debug.ShouldStop(4);
_recorte.runVoidMethod ("LineTo",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy));
 BA.debugLineNum = 198;BA.debugLine="Pantalla.ClipPath(Recorte)";
Debug.ShouldStop(32);
visualizacion.mostCurrent._pantalla.runVoidMethod ("ClipPath",(Object)((_recorte.getObject())));
 BA.debugLineNum = 199;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,St";
Debug.ShouldStop(64);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._radio,RemoteObject.createImmutable(0.7)}, "*",0, 0))),(Object)(visualizacion.mostCurrent._starter._colores.getArrayElement(true,_numactividad)),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 200;BA.debugLine="Pantalla.RemoveClip";
Debug.ShouldStop(128);
visualizacion.mostCurrent._pantalla.runVoidMethod ("RemoveClip");
 };
 BA.debugLineNum = 204;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _dibujarboton(RemoteObject _numactividad) throws Exception{
try {
		Debug.PushSubsStack("DibujarBoton (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,206);
if (RapidSub.canDelegate("dibujarboton")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","dibujarboton", _numactividad);
RemoteObject _horainicio = RemoteObject.createImmutable(0);
RemoteObject _mininicio = RemoteObject.createImmutable(0);
RemoteObject _horafin = RemoteObject.createImmutable(0);
RemoteObject _minfin = RemoteObject.createImmutable(0);
RemoteObject _horamitad = RemoteObject.createImmutable(0f);
RemoteObject _minutomitad = RemoteObject.createImmutable(0f);
RemoteObject _distanciaboton = RemoteObject.createImmutable(0f);
RemoteObject _tamañoicono = RemoteObject.createImmutable(0f);
RemoteObject _botonx = RemoteObject.createImmutable(0f);
RemoteObject _botony = RemoteObject.createImmutable(0f);
Debug.locals.put("NumActividad", _numactividad);
 BA.debugLineNum = 206;BA.debugLine="Sub DibujarBoton(NumActividad As Int)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 209;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
Debug.ShouldStop(65536);
if ((RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) || RemoteObject.solveBoolean(".",BA.ObjectToBoolean((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) && RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_fin"),BA.numberCast(double.class, 11))))) || RemoteObject.solveBoolean(".",BA.ObjectToBoolean((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 0)) && RemoteObject.solveBoolean("<",visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_inicio"),BA.numberCast(double.class, 12))))))) { 
 BA.debugLineNum = 212;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
Debug.ShouldStop(524288);
_horainicio = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_inicio");Debug.locals.put("HoraInicio", _horainicio);Debug.locals.put("HoraInicio", _horainicio);
 BA.debugLineNum = 213;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
Debug.ShouldStop(1048576);
_mininicio = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"minuto_inicio");Debug.locals.put("MinInicio", _mininicio);Debug.locals.put("MinInicio", _mininicio);
 BA.debugLineNum = 214;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
Debug.ShouldStop(2097152);
_horafin = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"hora_fin");Debug.locals.put("HoraFin", _horafin);Debug.locals.put("HoraFin", _horafin);
 BA.debugLineNum = 215;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
Debug.ShouldStop(4194304);
_minfin = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"minuto_fin");Debug.locals.put("MinFin", _minfin);Debug.locals.put("MinFin", _minfin);
 BA.debugLineNum = 216;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
Debug.ShouldStop(8388608);
if ((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 0)) && RemoteObject.solveBoolean(">",_horafin,BA.numberCast(double.class, 11)))) { 
 BA.debugLineNum = 217;BA.debugLine="HoraFin=12";
Debug.ShouldStop(16777216);
_horafin = BA.numberCast(int.class, 12);Debug.locals.put("HoraFin", _horafin);
 BA.debugLineNum = 218;BA.debugLine="MinFin=0";
Debug.ShouldStop(33554432);
_minfin = BA.numberCast(int.class, 0);Debug.locals.put("MinFin", _minfin);
 }else 
{ BA.debugLineNum = 219;BA.debugLine="Else If (Starter.Secuencia(Starter.SecuenciaActi";
Debug.ShouldStop(67108864);
if ((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1)) && RemoteObject.solveBoolean("<",_horainicio,BA.numberCast(double.class, 12)))) { 
 BA.debugLineNum = 220;BA.debugLine="HoraInicio=12";
Debug.ShouldStop(134217728);
_horainicio = BA.numberCast(int.class, 12);Debug.locals.put("HoraInicio", _horainicio);
 BA.debugLineNum = 221;BA.debugLine="MinInicio=0";
Debug.ShouldStop(268435456);
_mininicio = BA.numberCast(int.class, 0);Debug.locals.put("MinInicio", _mininicio);
 }}
;
 BA.debugLineNum = 225;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
Debug.ShouldStop(1);
_horamitad = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_horainicio,_horafin}, "+",1, 1)),RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("HoraMitad", _horamitad);Debug.locals.put("HoraMitad", _horamitad);
 BA.debugLineNum = 226;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
Debug.ShouldStop(2);
_minutomitad = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_mininicio,_minfin}, "+",1, 1)),RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("MinutoMitad", _minutomitad);Debug.locals.put("MinutoMitad", _minutomitad);
 BA.debugLineNum = 229;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
Debug.ShouldStop(16);
visualizacion.mostCurrent._boton.getArrayElement(false,_numactividad).runVoidMethod ("Initialize",visualizacion.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonActividad")));
 BA.debugLineNum = 230;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
Debug.ShouldStop(32);
visualizacion.mostCurrent._boton.getArrayElement(false,_numactividad).runMethod(false,"setTag",(_numactividad));
 BA.debugLineNum = 231;BA.debugLine="Dim DistanciaBoton=0.5+0.1*(NumActividad Mod 3)";
Debug.ShouldStop(64);
_distanciaboton = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(0.5),RemoteObject.createImmutable(0.1),(RemoteObject.solve(new RemoteObject[] {_numactividad,RemoteObject.createImmutable(3)}, "%",0, 1))}, "+*",1, 0));Debug.locals.put("DistanciaBoton", _distanciaboton);Debug.locals.put("DistanciaBoton", _distanciaboton);
 BA.debugLineNum = 232;BA.debugLine="Dim TamañoIcono=Starter.Secuencia(Starter.Secuen";
Debug.ShouldStop(128);
_tamañoicono = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tam_icono"),visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 1)),visualizacion.mostCurrent.activityBA)}, "*",0, 1));Debug.locals.put("TamañoIcono", _tamañoicono);Debug.locals.put("TamañoIcono", _tamañoicono);
 BA.debugLineNum = 233;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
Debug.ShouldStop(256);
_botonx = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_horaminuto_x(_horamitad,_minutomitad,_distanciaboton),_tamañoicono,RemoteObject.createImmutable(2)}, "-/",1, 0));Debug.locals.put("BotonX", _botonx);Debug.locals.put("BotonX", _botonx);
 BA.debugLineNum = 234;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
Debug.ShouldStop(512);
_botony = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_horaminuto_y(_horamitad,_minutomitad,_distanciaboton),_tamañoicono,RemoteObject.createImmutable(2)}, "-/",1, 0));Debug.locals.put("BotonY", _botony);Debug.locals.put("BotonY", _botony);
 BA.debugLineNum = 238;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Boto";
Debug.ShouldStop(8192);
visualizacion.mostCurrent._activity.runVoidMethod ("AddView",(Object)((visualizacion.mostCurrent._boton.getArrayElement(false,_numactividad).getObject())),(Object)(BA.numberCast(int.class, _botonx)),(Object)(BA.numberCast(int.class, _botony)),(Object)(BA.numberCast(int.class, _tamañoicono)),(Object)(BA.numberCast(int.class, _tamañoicono)));
 BA.debugLineNum = 239;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitma";
Debug.ShouldStop(16384);
visualizacion.mostCurrent._boton.getArrayElement(false,_numactividad).runVoidMethod ("SetBackgroundImageNew",(Object)((visualizacion.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(visualizacion.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.concat(visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,_numactividad).getField(true,"Pictograma"),RemoteObject.createImmutable(".png")))).getObject())));
 };
 BA.debugLineNum = 242;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _dibujartablero() throws Exception{
try {
		Debug.PushSubsStack("DibujarTablero (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,42);
if (RapidSub.canDelegate("dibujartablero")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","dibujartablero");
RemoteObject _recorte = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper");
int _hora = 0;
RemoteObject _x = RemoteObject.createImmutable(0f);
RemoteObject _y = RemoteObject.createImmutable(0f);
RemoteObject _numerohora = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
int _nactividad = 0;
RemoteObject _horaactual = RemoteObject.createImmutable(0);
RemoteObject _minutoactual = RemoteObject.createImmutable(0);
RemoteObject _segundoactual = RemoteObject.createImmutable(0);
RemoteObject _angulominuto = RemoteObject.createImmutable(0f);
RemoteObject _angulosegundo = RemoteObject.createImmutable(0f);
 BA.debugLineNum = 42;BA.debugLine="Sub DibujarTablero()";
Debug.ShouldStop(512);
 BA.debugLineNum = 44;BA.debugLine="CentroX=50%x";
Debug.ShouldStop(2048);
visualizacion._centrox = BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),visualizacion.mostCurrent.activityBA));
 BA.debugLineNum = 45;BA.debugLine="CentroY=60%x";
Debug.ShouldStop(4096);
visualizacion._centroy = BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 60)),visualizacion.mostCurrent.activityBA));
 BA.debugLineNum = 46;BA.debugLine="Radio=45%x";
Debug.ShouldStop(8192);
visualizacion._radio = BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 45)),visualizacion.mostCurrent.activityBA));
 BA.debugLineNum = 48;BA.debugLine="Pantalla.Initialize(PanelReloj)";
Debug.ShouldStop(32768);
visualizacion.mostCurrent._pantalla.runVoidMethod ("Initialize",(Object)((visualizacion.mostCurrent._panelreloj.getObject())));
 BA.debugLineNum = 51;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
Debug.ShouldStop(262144);
if (RemoteObject.solveBoolean("<",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 2))) { 
 BA.debugLineNum = 52;BA.debugLine="MinHora=1";
Debug.ShouldStop(524288);
visualizacion._minhora = BA.numberCast(int.class, 1);
 BA.debugLineNum = 53;BA.debugLine="MaxHora=12";
Debug.ShouldStop(1048576);
visualizacion._maxhora = BA.numberCast(int.class, 12);
 }else 
{ BA.debugLineNum = 54;BA.debugLine="Else If Starter.Secuencia(Starter.SecuenciaActiva";
Debug.ShouldStop(2097152);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 2))) { 
 BA.debugLineNum = 55;BA.debugLine="MinHora=1";
Debug.ShouldStop(4194304);
visualizacion._minhora = BA.numberCast(int.class, 1);
 BA.debugLineNum = 56;BA.debugLine="MaxHora=24";
Debug.ShouldStop(8388608);
visualizacion._maxhora = BA.numberCast(int.class, 24);
 }else {
 BA.debugLineNum = 58;BA.debugLine="MinHora=Starter.ActividadSecuencia(Starter.Secue";
Debug.ShouldStop(33554432);
visualizacion._minhora = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,BA.numberCast(int.class, 0)).getField(true,"hora_inicio");
 BA.debugLineNum = 60;BA.debugLine="MaxHora=Starter.ActividadSecuencia(Starter.Secue";
Debug.ShouldStop(134217728);
visualizacion._maxhora = visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1)).getField(true,"hora_fin");
 BA.debugLineNum = 61;BA.debugLine="If (Starter.ActividadSecuencia(Starter.Secuencia";
Debug.ShouldStop(268435456);
if ((RemoteObject.solveBoolean("!",visualizacion.mostCurrent._starter._actividadsecuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva,RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1)).getField(true,"minuto_fin"),BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 62;BA.debugLine="MaxHora=MaxHora+1";
Debug.ShouldStop(536870912);
visualizacion._maxhora = RemoteObject.solve(new RemoteObject[] {visualizacion._maxhora,RemoteObject.createImmutable(1)}, "+",1, 1);
 };
 }}
;
 BA.debugLineNum = 67;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
Debug.ShouldStop(4);
if (RemoteObject.solveBoolean("<",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 3))) { 
 BA.debugLineNum = 69;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
Debug.ShouldStop(16);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._radio,RemoteObject.createImmutable(1.05)}, "*",0, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"Gray")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 70;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
Debug.ShouldStop(32);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(visualizacion._radio),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"White")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 }else {
 BA.debugLineNum = 72;BA.debugLine="Dim Recorte As Path";
Debug.ShouldStop(128);
_recorte = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper");Debug.locals.put("Recorte", _recorte);
 BA.debugLineNum = 74;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
Debug.ShouldStop(512);
_recorte.runVoidMethod ("Initialize",(Object)(visualizacion._centrox),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._centroy,visualizacion.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 3)),visualizacion.mostCurrent.activityBA)}, "+",1, 0))));
 BA.debugLineNum = 75;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
Debug.ShouldStop(1024);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, 114))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centrox}, "+",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, 114))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centroy}, "+",1, 0))));
 BA.debugLineNum = 76;BA.debugLine="Recorte.LineTo(0,100%Y)";
Debug.ShouldStop(2048);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, 0)),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))));
 BA.debugLineNum = 77;BA.debugLine="Recorte.LineTo(0,0)";
Debug.ShouldStop(4096);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, 0)),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 78;BA.debugLine="Recorte.LineTo(100%X,0)";
Debug.ShouldStop(8192);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 79;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
Debug.ShouldStop(16384);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))));
 BA.debugLineNum = 80;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
Debug.ShouldStop(32768);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, 81))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centrox}, "+",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, 81))),visualizacion._radio}, "*",0, 0)),visualizacion._centroy}, "+",1, 0))));
 BA.debugLineNum = 81;BA.debugLine="Pantalla.ClipPath(Recorte)";
Debug.ShouldStop(65536);
visualizacion.mostCurrent._pantalla.runVoidMethod ("ClipPath",(Object)((_recorte.getObject())));
 BA.debugLineNum = 82;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
Debug.ShouldStop(131072);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._radio,RemoteObject.createImmutable(1.05)}, "*",0, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"Gray")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 83;BA.debugLine="Pantalla.RemoveClip";
Debug.ShouldStop(262144);
visualizacion.mostCurrent._pantalla.runVoidMethod ("RemoveClip");
 BA.debugLineNum = 85;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
Debug.ShouldStop(1048576);
_recorte.runVoidMethod ("Initialize",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy));
 BA.debugLineNum = 86;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
Debug.ShouldStop(2097152);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, 116))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centrox}, "+",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, 116))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centroy}, "+",1, 0))));
 BA.debugLineNum = 87;BA.debugLine="Recorte.LineTo(0,100%Y)";
Debug.ShouldStop(4194304);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, 0)),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))));
 BA.debugLineNum = 88;BA.debugLine="Recorte.LineTo(0,0)";
Debug.ShouldStop(8388608);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, 0)),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 89;BA.debugLine="Recorte.LineTo(100%X,0)";
Debug.ShouldStop(16777216);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 90;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
Debug.ShouldStop(33554432);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),visualizacion.mostCurrent.activityBA))));
 BA.debugLineNum = 91;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
Debug.ShouldStop(67108864);
_recorte.runVoidMethod ("LineTo",(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, 80))),visualizacion._radio,RemoteObject.createImmutable(3)}, "**",0, 0)),visualizacion._centrox}, "+",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, 80))),visualizacion._radio}, "*",0, 0)),visualizacion._centroy}, "+",1, 0))));
 BA.debugLineNum = 92;BA.debugLine="Pantalla.ClipPath(Recorte)";
Debug.ShouldStop(134217728);
visualizacion.mostCurrent._pantalla.runVoidMethod ("ClipPath",(Object)((_recorte.getObject())));
 BA.debugLineNum = 93;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
Debug.ShouldStop(268435456);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(visualizacion._radio),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"White")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 94;BA.debugLine="Pantalla.RemoveClip";
Debug.ShouldStop(536870912);
visualizacion.mostCurrent._pantalla.runVoidMethod ("RemoveClip");
 };
 BA.debugLineNum = 97;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
Debug.ShouldStop(1);
{
final int step44 = 1;
final int limit44 = visualizacion._maxhora.<Integer>get().intValue();
_hora = visualizacion._minhora.<Integer>get().intValue() ;
for (;(step44 > 0 && _hora <= limit44) || (step44 < 0 && _hora >= limit44) ;_hora = ((int)(0 + _hora + step44))  ) {
Debug.locals.put("Hora", _hora);
 BA.debugLineNum = 99;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
Debug.ShouldStop(4);
_x = _horaminuto_x(BA.numberCast(float.class, _hora),BA.numberCast(float.class, 0),BA.numberCast(float.class, 0.95));Debug.locals.put("X", _x);Debug.locals.put("X", _x);
 BA.debugLineNum = 100;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
Debug.ShouldStop(8);
_y = _horaminuto_y(BA.numberCast(float.class, _hora),BA.numberCast(float.class, 0),BA.numberCast(float.class, 0.95));Debug.locals.put("Y", _y);Debug.locals.put("Y", _y);
 BA.debugLineNum = 102;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
Debug.ShouldStop(32);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(_x),(Object)(_y),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._radio,RemoteObject.createImmutable(0.02)}, "*",0, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"LightGray")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 103;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
Debug.ShouldStop(64);
_x = _horaminuto_x(BA.numberCast(float.class, _hora),BA.numberCast(float.class, 0),BA.numberCast(float.class, 0.85));Debug.locals.put("X", _x);Debug.locals.put("X", _x);
 BA.debugLineNum = 104;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
Debug.ShouldStop(128);
_y = _horaminuto_y(BA.numberCast(float.class, _hora),BA.numberCast(float.class, 0),BA.numberCast(float.class, 0.85));Debug.locals.put("Y", _y);Debug.locals.put("Y", _y);
 BA.debugLineNum = 105;BA.debugLine="Dim NumeroHora As Label";
Debug.ShouldStop(256);
_numerohora = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("NumeroHora", _numerohora);
 BA.debugLineNum = 106;BA.debugLine="NumeroHora.Initialize(\"\")";
Debug.ShouldStop(512);
_numerohora.runVoidMethod ("Initialize",visualizacion.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 107;BA.debugLine="NumeroHora.Text=(Hora) Mod 24";
Debug.ShouldStop(1024);
_numerohora.runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable((_hora)),RemoteObject.createImmutable(24)}, "%",0, 1)));
 BA.debugLineNum = 108;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
Debug.ShouldStop(2048);
_numerohora.runMethod(true,"setTextColor",visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"DarkGray"));
 BA.debugLineNum = 109;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
Debug.ShouldStop(4096);
_numerohora.runMethod(true,"setGravity",visualizacion.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER"));
 BA.debugLineNum = 110;BA.debugLine="NumeroHora.TextSize=15";
Debug.ShouldStop(8192);
_numerohora.runMethod(true,"setTextSize",BA.numberCast(float.class, 15));
 BA.debugLineNum = 111;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
Debug.ShouldStop(16384);
visualizacion.mostCurrent._activity.runVoidMethod ("AddView",(Object)((_numerohora.getObject())),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {_x,visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)))}, "-",1, 0))),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {_y,visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)))}, "-",1, 0))),(Object)(visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))),(Object)(visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))));
 }
}Debug.locals.put("Hora", _hora);
;
 BA.debugLineNum = 114;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
Debug.ShouldStop(131072);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 1))) { 
 BA.debugLineNum = 115;BA.debugLine="MinHora=MinHora+12";
Debug.ShouldStop(262144);
visualizacion._minhora = RemoteObject.solve(new RemoteObject[] {visualizacion._minhora,RemoteObject.createImmutable(12)}, "+",1, 1);
 BA.debugLineNum = 116;BA.debugLine="MaxHora=MaxHora+12";
Debug.ShouldStop(524288);
visualizacion._maxhora = RemoteObject.solve(new RemoteObject[] {visualizacion._maxhora,RemoteObject.createImmutable(12)}, "+",1, 1);
 };
 BA.debugLineNum = 120;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
Debug.ShouldStop(8388608);
{
final int step62 = 1;
final int limit62 = RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_nactividad = 0 ;
for (;(step62 > 0 && _nactividad <= limit62) || (step62 < 0 && _nactividad >= limit62) ;_nactividad = ((int)(0 + _nactividad + step62))  ) {
Debug.locals.put("NActividad", _nactividad);
 BA.debugLineNum = 121;BA.debugLine="DibujarActividad(NActividad)";
Debug.ShouldStop(16777216);
_dibujaractividad(BA.numberCast(int.class, _nactividad));
 }
}Debug.locals.put("NActividad", _nactividad);
;
 BA.debugLineNum = 125;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
Debug.ShouldStop(268435456);
{
final int step65 = 1;
final int limit65 = RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_nactividad = 0 ;
for (;(step65 > 0 && _nactividad <= limit65) || (step65 < 0 && _nactividad >= limit65) ;_nactividad = ((int)(0 + _nactividad + step65))  ) {
Debug.locals.put("NActividad", _nactividad);
 BA.debugLineNum = 126;BA.debugLine="DibujarBoton(NActividad)";
Debug.ShouldStop(536870912);
_dibujarboton(BA.numberCast(int.class, _nactividad));
 }
}Debug.locals.put("NActividad", _nactividad);
;
 BA.debugLineNum = 130;BA.debugLine="Dim HoraActual=DateTime.GetHour(DateTime.Now) As";
Debug.ShouldStop(2);
_horaactual = visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"GetHour",(Object)(visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"getNow")));Debug.locals.put("HoraActual", _horaactual);Debug.locals.put("HoraActual", _horaactual);
 BA.debugLineNum = 131;BA.debugLine="Dim MinutoActual=DateTime.GetMinute(DateTime.Now)";
Debug.ShouldStop(4);
_minutoactual = visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"GetMinute",(Object)(visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"getNow")));Debug.locals.put("MinutoActual", _minutoactual);Debug.locals.put("MinutoActual", _minutoactual);
 BA.debugLineNum = 132;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
Debug.ShouldStop(8);
_segundoactual = visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"GetSecond",(Object)(visualizacion.mostCurrent.__c.getField(false,"DateTime").runMethod(true,"getNow")));Debug.locals.put("SegundoActual", _segundoactual);Debug.locals.put("SegundoActual", _segundoactual);
 BA.debugLineNum = 133;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).ta";
Debug.ShouldStop(16);
if ((RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"indicar_hora"),BA.numberCast(double.class, 0)) && RemoteObject.solveBoolean("g",_horaactual,BA.numberCast(double.class, visualizacion._minhora)) && RemoteObject.solveBoolean("<",_horaactual,BA.numberCast(double.class, visualizacion._maxhora)))) { 
 BA.debugLineNum = 134;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
Debug.ShouldStop(32);
if ((RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 3)) || RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"indicar_hora"),BA.numberCast(double.class, 1)))) { 
 BA.debugLineNum = 135;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
Debug.ShouldStop(64);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawLine",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(_horaminuto_x(BA.numberCast(float.class, _horaactual),BA.numberCast(float.class, _minutoactual),BA.numberCast(float.class, 0.8))),(Object)(_horaminuto_y(BA.numberCast(float.class, _horaactual),BA.numberCast(float.class, _minutoactual),BA.numberCast(float.class, 0.8))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"Red")),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 8))))));
 }else {
 BA.debugLineNum = 137;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
Debug.ShouldStop(256);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawLine",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(_horaminuto_x(BA.numberCast(float.class, _horaactual),BA.numberCast(float.class, _minutoactual),BA.numberCast(float.class, 0.6))),(Object)(_horaminuto_y(BA.numberCast(float.class, _horaactual),BA.numberCast(float.class, _minutoactual),BA.numberCast(float.class, 0.6))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"DarkGray")),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 8))))));
 BA.debugLineNum = 138;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).";
Debug.ShouldStop(512);
if ((RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"indicar_hora"),BA.numberCast(double.class, 1)))) { 
 BA.debugLineNum = 139;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
Debug.ShouldStop(1024);
_angulominuto = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(270),(RemoteObject.solve(new RemoteObject[] {_minutoactual,RemoteObject.createImmutable(6)}, "*",0, 1))}, "+",1, 1));Debug.locals.put("AnguloMinuto", _angulominuto);Debug.locals.put("AnguloMinuto", _angulominuto);
 BA.debugLineNum = 140;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+CosD";
Debug.ShouldStop(2048);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawLine",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._centrox,visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, _angulominuto))),visualizacion._radio,RemoteObject.createImmutable(0.8)}, "+**",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._centroy,visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, _angulominuto))),visualizacion._radio,RemoteObject.createImmutable(0.75)}, "+**",1, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"DarkGray")),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 6))))));
 BA.debugLineNum = 141;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva)";
Debug.ShouldStop(4096);
if ((RemoteObject.solveBoolean(">",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"indicar_hora"),BA.numberCast(double.class, 2)))) { 
 BA.debugLineNum = 142;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
Debug.ShouldStop(8192);
_angulosegundo = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(270),(RemoteObject.solve(new RemoteObject[] {_segundoactual,RemoteObject.createImmutable(6)}, "*",0, 1))}, "+",1, 1));Debug.locals.put("AnguloSegundo", _angulosegundo);Debug.locals.put("AnguloSegundo", _angulosegundo);
 BA.debugLineNum = 143;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+Cos";
Debug.ShouldStop(16384);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawLine",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._centrox,visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, _angulosegundo))),visualizacion._radio,RemoteObject.createImmutable(0.9)}, "+**",1, 0))),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._centroy,visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, _angulosegundo))),visualizacion._radio,RemoteObject.createImmutable(0.9)}, "+**",1, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"Red")),(Object)(BA.numberCast(float.class, visualizacion.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 4))))));
 };
 };
 };
 };
 BA.debugLineNum = 150;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.1,Col";
Debug.ShouldStop(2097152);
visualizacion.mostCurrent._pantalla.runVoidMethod ("DrawCircle",(Object)(visualizacion._centrox),(Object)(visualizacion._centroy),(Object)(BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {visualizacion._radio,RemoteObject.createImmutable(0.1)}, "*",0, 0))),(Object)(visualizacion.mostCurrent.__c.getField(false,"Colors").getField(true,"Black")),(Object)(visualizacion.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, 0)));
 BA.debugLineNum = 154;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
Debug.ShouldStop(33554432);
switch (BA.switchObjectToInt(visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(int.class, 0),BA.numberCast(int.class, 1),BA.numberCast(int.class, 2),BA.numberCast(int.class, 3))) {
case 0: {
 BA.debugLineNum = 156;BA.debugLine="CambiarVista.Text=\"Mañana\"";
Debug.ShouldStop(134217728);
visualizacion.mostCurrent._cambiarvista.runMethod(true,"setText",BA.ObjectToCharSequence("Mañana"));
 break; }
case 1: {
 BA.debugLineNum = 158;BA.debugLine="CambiarVista.Text=\"Tarde\"";
Debug.ShouldStop(536870912);
visualizacion.mostCurrent._cambiarvista.runMethod(true,"setText",BA.ObjectToCharSequence("Tarde"));
 break; }
case 2: {
 BA.debugLineNum = 160;BA.debugLine="CambiarVista.Text=\"Día\"";
Debug.ShouldStop(-2147483648);
visualizacion.mostCurrent._cambiarvista.runMethod(true,"setText",BA.ObjectToCharSequence("Día"));
 break; }
case 3: {
 BA.debugLineNum = 162;BA.debugLine="CambiarVista.Text=\"Secuencia\"";
Debug.ShouldStop(2);
visualizacion.mostCurrent._cambiarvista.runMethod(true,"setText",BA.ObjectToCharSequence("Secuencia"));
 break; }
}
;
 BA.debugLineNum = 165;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private PanelDescripcion As Panel";
visualizacion.mostCurrent._paneldescripcion = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");
 //BA.debugLineNum = 16;BA.debugLine="Private PanelReloj As Panel";
visualizacion.mostCurrent._panelreloj = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");
 //BA.debugLineNum = 17;BA.debugLine="Private ImagenPictograma As ImageView";
visualizacion.mostCurrent._imagenpictograma = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 18;BA.debugLine="Private FondoPictograma As Panel";
visualizacion.mostCurrent._fondopictograma = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");
 //BA.debugLineNum = 19;BA.debugLine="Private TextoPictograma As Label";
visualizacion.mostCurrent._textopictograma = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 21;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja el";
visualizacion.mostCurrent._pantalla = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper");
 //BA.debugLineNum = 24;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
visualizacion._centrox = RemoteObject.createImmutable(0f);
visualizacion._centroy = RemoteObject.createImmutable(0f);
visualizacion._radio = RemoteObject.createImmutable(0f);
 //BA.debugLineNum = 25;BA.debugLine="Dim MinHora,MaxHora As Int";
visualizacion._minhora = RemoteObject.createImmutable(0);
visualizacion._maxhora = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 28;BA.debugLine="Dim Boton(Starter.MaxActividades) As Button";
visualizacion.mostCurrent._boton = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {visualizacion.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 30;BA.debugLine="Private CambiarVista As Button";
visualizacion.mostCurrent._cambiarvista = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 31;BA.debugLine="Private DescripcionPictograma As Label";
visualizacion.mostCurrent._descripcionpictograma = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _hora24a12(RemoteObject _hora24) throws Exception{
try {
		Debug.PushSubsStack("Hora24a12 (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,270);
if (RapidSub.canDelegate("hora24a12")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","hora24a12", _hora24);
Debug.locals.put("Hora24", _hora24);
 BA.debugLineNum = 270;BA.debugLine="Sub Hora24a12 (Hora24 As Int) As Int";
Debug.ShouldStop(8192);
 BA.debugLineNum = 271;BA.debugLine="If (Hora24>11) Then";
Debug.ShouldStop(16384);
if ((RemoteObject.solveBoolean(">",_hora24,BA.numberCast(double.class, 11)))) { 
 BA.debugLineNum = 272;BA.debugLine="Return Hora24-12";
Debug.ShouldStop(32768);
if (true) return RemoteObject.solve(new RemoteObject[] {_hora24,RemoteObject.createImmutable(12)}, "-",1, 1);
 }else {
 BA.debugLineNum = 274;BA.debugLine="Return Hora24";
Debug.ShouldStop(131072);
if (true) return _hora24;
 };
 BA.debugLineNum = 276;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return RemoteObject.createImmutable(0);
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _horaminuto_x(RemoteObject _hora,RemoteObject _minuto,RemoteObject _distancia) throws Exception{
try {
		Debug.PushSubsStack("HoraMinuto_X (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,244);
if (RapidSub.canDelegate("horaminuto_x")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","horaminuto_x", _hora, _minuto, _distancia);
RemoteObject _angulo = RemoteObject.createImmutable(0f);
Debug.locals.put("Hora", _hora);
Debug.locals.put("Minuto", _minuto);
Debug.locals.put("Distancia", _distancia);
 BA.debugLineNum = 244;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
Debug.ShouldStop(524288);
 BA.debugLineNum = 245;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
Debug.ShouldStop(1048576);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 3))) { 
 BA.debugLineNum = 246;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
Debug.ShouldStop(2097152);
_angulo = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(120),(RemoteObject.solve(new RemoteObject[] {_hora,_minuto,RemoteObject.createImmutable(60),visualizacion._minhora}, "+/-",2, 0)),RemoteObject.createImmutable(300),(RemoteObject.solve(new RemoteObject[] {visualizacion._maxhora,visualizacion._minhora}, "-",1, 1))}, "+*/",1, 0));Debug.locals.put("Angulo", _angulo);Debug.locals.put("Angulo", _angulo);
 BA.debugLineNum = 247;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
Debug.ShouldStop(4194304);
if (true) return BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(BA.numberCast(double.class, _angulo))),visualizacion._radio,_distancia}, "**",0, 0)),visualizacion._centrox}, "+",1, 0));
 }else {
 BA.debugLineNum = 249;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
Debug.ShouldStop(16777216);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 2))) { 
 BA.debugLineNum = 250;BA.debugLine="Hora=Hora/2";
Debug.ShouldStop(33554432);
_hora = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_hora,RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("Hora", _hora);
 BA.debugLineNum = 251;BA.debugLine="Minuto=Minuto/2";
Debug.ShouldStop(67108864);
_minuto = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_minuto,RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("Minuto", _minuto);
 };
 BA.debugLineNum = 253;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
Debug.ShouldStop(268435456);
if (true) return BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"CosD",(Object)(RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_hora,_minuto,RemoteObject.createImmutable(60)}, "+/",1, 0)),(RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(360),RemoteObject.createImmutable(12)}, "/",0, 0)),RemoteObject.createImmutable(270)}, "*+",1, 0))),visualizacion._radio,_distancia}, "**",0, 0)),visualizacion._centrox}, "+",1, 0));
 };
 BA.debugLineNum = 255;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return RemoteObject.createImmutable(0f);
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _horaminuto_y(RemoteObject _hora,RemoteObject _minuto,RemoteObject _distancia) throws Exception{
try {
		Debug.PushSubsStack("HoraMinuto_Y (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,257);
if (RapidSub.canDelegate("horaminuto_y")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","horaminuto_y", _hora, _minuto, _distancia);
RemoteObject _angulo = RemoteObject.createImmutable(0f);
Debug.locals.put("Hora", _hora);
Debug.locals.put("Minuto", _minuto);
Debug.locals.put("Distancia", _distancia);
 BA.debugLineNum = 257;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
Debug.ShouldStop(1);
 BA.debugLineNum = 258;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
Debug.ShouldStop(2);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 3))) { 
 BA.debugLineNum = 259;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
Debug.ShouldStop(4);
_angulo = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(120),(RemoteObject.solve(new RemoteObject[] {_hora,_minuto,RemoteObject.createImmutable(60),visualizacion._minhora}, "+/-",2, 0)),RemoteObject.createImmutable(300),(RemoteObject.solve(new RemoteObject[] {visualizacion._maxhora,visualizacion._minhora}, "-",1, 1))}, "+*/",1, 0));Debug.locals.put("Angulo", _angulo);Debug.locals.put("Angulo", _angulo);
 BA.debugLineNum = 260;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
Debug.ShouldStop(8);
if (true) return BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(BA.numberCast(double.class, _angulo))),visualizacion._radio,_distancia}, "**",0, 0)),visualizacion._centroy}, "+",1, 0));
 }else {
 BA.debugLineNum = 262;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
Debug.ShouldStop(32);
if (RemoteObject.solveBoolean("=",visualizacion.mostCurrent._starter._secuencia.getArrayElement(false,visualizacion.mostCurrent._starter._secuenciaactiva).getField(false,"tablero").getField(true,"tipo"),BA.numberCast(double.class, 2))) { 
 BA.debugLineNum = 263;BA.debugLine="Hora=Hora/2";
Debug.ShouldStop(64);
_hora = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_hora,RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("Hora", _hora);
 BA.debugLineNum = 264;BA.debugLine="Minuto=Minuto/2";
Debug.ShouldStop(128);
_minuto = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_minuto,RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("Minuto", _minuto);
 };
 BA.debugLineNum = 266;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
Debug.ShouldStop(512);
if (true) return BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {visualizacion.mostCurrent.__c.runMethod(true,"SinD",(Object)(RemoteObject.solve(new RemoteObject[] {(RemoteObject.solve(new RemoteObject[] {_hora,_minuto,RemoteObject.createImmutable(60)}, "+/",1, 0)),(RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(360),RemoteObject.createImmutable(12)}, "/",0, 0)),RemoteObject.createImmutable(270)}, "*+",1, 0))),visualizacion._radio,_distancia}, "**",0, 0)),visualizacion._centroy}, "+",1, 0));
 };
 BA.debugLineNum = 268;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return RemoteObject.createImmutable(0f);
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _minutolegible(RemoteObject _minuto) throws Exception{
try {
		Debug.PushSubsStack("MinutoLegible (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,278);
if (RapidSub.canDelegate("minutolegible")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","minutolegible", _minuto);
Debug.locals.put("Minuto", _minuto);
 BA.debugLineNum = 278;BA.debugLine="Sub MinutoLegible (Minuto As Int) As String";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 279;BA.debugLine="If (Minuto==0) Then";
Debug.ShouldStop(4194304);
if ((RemoteObject.solveBoolean("=",_minuto,BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 280;BA.debugLine="Return \"\"";
Debug.ShouldStop(8388608);
if (true) return BA.ObjectToString("");
 }else 
{ BA.debugLineNum = 281;BA.debugLine="Else If (Minuto==15) Then";
Debug.ShouldStop(16777216);
if ((RemoteObject.solveBoolean("=",_minuto,BA.numberCast(double.class, 15)))) { 
 BA.debugLineNum = 282;BA.debugLine="Return \" y cuarto\"";
Debug.ShouldStop(33554432);
if (true) return BA.ObjectToString(" y cuarto");
 }else 
{ BA.debugLineNum = 283;BA.debugLine="Else If (Minuto==30) Then";
Debug.ShouldStop(67108864);
if ((RemoteObject.solveBoolean("=",_minuto,BA.numberCast(double.class, 30)))) { 
 BA.debugLineNum = 284;BA.debugLine="Return \" y media\"";
Debug.ShouldStop(134217728);
if (true) return BA.ObjectToString(" y media");
 }else {
 BA.debugLineNum = 286;BA.debugLine="Return \":\"&NumberFormat(Minuto,2,0)";
Debug.ShouldStop(536870912);
if (true) return RemoteObject.concat(RemoteObject.createImmutable(":"),visualizacion.mostCurrent.__c.runMethod(true,"NumberFormat",(Object)(BA.numberCast(double.class, _minuto)),(Object)(BA.numberCast(int.class, 2)),(Object)(BA.numberCast(int.class, 0))));
 }}}
;
 BA.debugLineNum = 288;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _panelreloj_touch(RemoteObject _action,RemoteObject _x,RemoteObject _y) throws Exception{
try {
		Debug.PushSubsStack("PanelReloj_Touch (visualizacion) ","visualizacion",3,visualizacion.mostCurrent.activityBA,visualizacion.mostCurrent,311);
if (RapidSub.canDelegate("panelreloj_touch")) return javi.prieto.pictorario.visualizacion.remoteMe.runUserSub(false, "visualizacion","panelreloj_touch", _action, _x, _y);
Debug.locals.put("Action", _action);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
 BA.debugLineNum = 311;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 313;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}