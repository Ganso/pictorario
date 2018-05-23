package javi.prieto.pictorario;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,36);
if (RapidSub.canDelegate("activity_create")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(8);
 BA.debugLineNum = 38;BA.debugLine="DibujarPortada";
Debug.ShouldStop(32);
_dibujarportada();
 BA.debugLineNum = 39;BA.debugLine="End Sub";
Debug.ShouldStop(64);
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
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,132);
if (RapidSub.canDelegate("activity_pause")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 132;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(8);
 BA.debugLineNum = 134;BA.debugLine="End Sub";
Debug.ShouldStop(32);
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
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,128);
if (RapidSub.canDelegate("activity_resume")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","activity_resume");
 BA.debugLineNum = 128;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 129;BA.debugLine="DibujarPortada";
Debug.ShouldStop(1);
_dibujarportada();
 BA.debugLineNum = 130;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botonacercade_click() throws Exception{
try {
		Debug.PushSubsStack("BotonAcercaDe_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,142);
if (RapidSub.canDelegate("botonacercade_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","botonacercade_click");
 BA.debugLineNum = 142;BA.debugLine="Sub BotonAcercaDe_Click";
Debug.ShouldStop(8192);
 BA.debugLineNum = 143;BA.debugLine="StartActivity(AcercaDe)";
Debug.ShouldStop(16384);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._acercade.getObject())));
 BA.debugLineNum = 144;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botoncrear_click() throws Exception{
try {
		Debug.PushSubsStack("BotonCrear_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,146);
if (RapidSub.canDelegate("botoncrear_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","botoncrear_click");
 BA.debugLineNum = 146;BA.debugLine="Sub BotonCrear_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 147;BA.debugLine="Starter.SecuenciaActiva=Starter.MaxSecuencias";
Debug.ShouldStop(262144);
main.mostCurrent._starter._secuenciaactiva = main.mostCurrent._starter._maxsecuencias;
 BA.debugLineNum = 148;BA.debugLine="StartActivity(ConfigurarSecuencia)";
Debug.ShouldStop(524288);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._configurarsecuencia.getObject())));
 BA.debugLineNum = 149;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botoneditar_click() throws Exception{
try {
		Debug.PushSubsStack("BotonEditar_click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,88);
if (RapidSub.canDelegate("botoneditar_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","botoneditar_click");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _opciones = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
RemoteObject _resultado = RemoteObject.createImmutable(0);
int _sec = 0;
 BA.debugLineNum = 88;BA.debugLine="Sub BotonEditar_click";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 89;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(16777216);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 90;BA.debugLine="Dim Opciones As List";
Debug.ShouldStop(33554432);
_opciones = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");Debug.locals.put("Opciones", _opciones);
 BA.debugLineNum = 91;BA.debugLine="Dim resultado As Int";
Debug.ShouldStop(67108864);
_resultado = RemoteObject.createImmutable(0);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 93;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(268435456);
_botonpulsado.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));
 BA.debugLineNum = 94;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
Debug.ShouldStop(536870912);
main.mostCurrent._starter._secuenciaactiva = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));
 BA.debugLineNum = 95;BA.debugLine="Opciones.Initialize2(Array As String(\"Editar secu";
Debug.ShouldStop(1073741824);
_opciones.runVoidMethod ("Initialize2",(Object)(main.mostCurrent.__c.runMethod(false, "ArrayToList", (Object)(RemoteObject.createNewArray("String",new int[] {3},new Object[] {BA.ObjectToString("Editar secuencia"),BA.ObjectToString("Borrar secuencia"),RemoteObject.createImmutable("CANCELAR")})))));
 BA.debugLineNum = 96;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
Debug.ShouldStop(-2147483648);
_resultado = main.mostCurrent.__c.runMethodAndSync(true,"InputList",(Object)(_opciones),(Object)(BA.ObjectToCharSequence("Acción")),(Object)(BA.numberCast(int.class, -(double) (0 + 1))),main.mostCurrent.activityBA);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 97;BA.debugLine="Select resultado";
Debug.ShouldStop(1);
switch (BA.switchObjectToInt(_resultado,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1))) {
case 0: {
 BA.debugLineNum = 99;BA.debugLine="StartActivity(ConfigurarSecuencia)";
Debug.ShouldStop(4);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._configurarsecuencia.getObject())));
 break; }
case 1: {
 BA.debugLineNum = 101;BA.debugLine="If Msgbox2(\"¿Está seguro de que quiere borrar l";
Debug.ShouldStop(16);
if (RemoteObject.solveBoolean("=",main.mostCurrent.__c.runMethodAndSync(true,"Msgbox2",(Object)(BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("¿Está seguro de que quiere borrar la secuencia \""),main.mostCurrent._starter._secuencia.getArrayElement(false,main.mostCurrent._starter._secuenciaactiva).getField(true,"Descripcion"),RemoteObject.createImmutable("\"?")))),(Object)(BA.ObjectToCharSequence("Borrar secuencia")),(Object)(BA.ObjectToString("Sí")),(Object)(BA.ObjectToString("")),(Object)(BA.ObjectToString("No")),(Object)((main.mostCurrent.__c.getField(false,"Null"))),main.mostCurrent.activityBA),BA.numberCast(double.class, main.mostCurrent.__c.getField(false,"DialogResponse").getField(true,"POSITIVE")))) { 
 BA.debugLineNum = 102;BA.debugLine="For Sec=Starter.SecuenciaActiva To Starter.Num";
Debug.ShouldStop(32);
{
final int step13 = 1;
final int limit13 = RemoteObject.solve(new RemoteObject[] {main.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(2)}, "-",1, 1).<Integer>get().intValue();
_sec = main.mostCurrent._starter._secuenciaactiva.<Integer>get().intValue() ;
for (;(step13 > 0 && _sec <= limit13) || (step13 < 0 && _sec >= limit13) ;_sec = ((int)(0 + _sec + step13))  ) {
Debug.locals.put("Sec", _sec);
 BA.debugLineNum = 104;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Sec+1,Sec";
Debug.ShouldStop(128);
main.mostCurrent.__c.runMethodAndSync(false,"CallSubNew3",main.processBA,(Object)((main.mostCurrent._starter.getObject())),(Object)(BA.ObjectToString("CopiarSecuencias")),(Object)((RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(_sec),RemoteObject.createImmutable(1)}, "+",1, 1))),(Object)(RemoteObject.createImmutable((_sec))));
 }
}Debug.locals.put("Sec", _sec);
;
 BA.debugLineNum = 107;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias-1";
Debug.ShouldStop(1024);
main.mostCurrent._starter._numsecuencias = RemoteObject.solve(new RemoteObject[] {main.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "-",1, 1);
 BA.debugLineNum = 108;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
Debug.ShouldStop(2048);
main.mostCurrent.__c.runMethodAndSync(false,"CallSubNew",main.processBA,(Object)((main.mostCurrent._starter.getObject())),(Object)(RemoteObject.createImmutable("Guardar_Configuracion")));
 BA.debugLineNum = 109;BA.debugLine="DibujarPortada";
Debug.ShouldStop(4096);
_dibujarportada();
 };
 break; }
}
;
 BA.debugLineNum = 112;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botonpictograma_click() throws Exception{
try {
		Debug.PushSubsStack("BotonPictograma_click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,114);
if (RapidSub.canDelegate("botonpictograma_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","botonpictograma_click");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
 BA.debugLineNum = 114;BA.debugLine="Sub BotonPictograma_click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 115;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(262144);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 116;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(524288);
_botonpulsado.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));
 BA.debugLineNum = 117;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
Debug.ShouldStop(1048576);
main.mostCurrent._starter._secuenciaactiva = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));
 BA.debugLineNum = 118;BA.debugLine="StartActivity(Visualizacion)";
Debug.ShouldStop(2097152);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._visualizacion.getObject())));
 BA.debugLineNum = 119;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _dibujarportada() throws Exception{
try {
		Debug.PushSubsStack("DibujarPortada (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,41);
if (RapidSub.canDelegate("dibujarportada")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","dibujarportada");
RemoteObject _act = RemoteObject.createImmutable(0);
 BA.debugLineNum = 41;BA.debugLine="Sub DibujarPortada";
Debug.ShouldStop(256);
 BA.debugLineNum = 43;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1024);
main.mostCurrent._activity.runVoidMethod ("RemoveAllViews");
 BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"Portada\")";
Debug.ShouldStop(2048);
main.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("Portada")),main.mostCurrent.activityBA);
 BA.debugLineNum = 46;BA.debugLine="Dim Act As Int";
Debug.ShouldStop(8192);
_act = RemoteObject.createImmutable(0);Debug.locals.put("Act", _act);
 BA.debugLineNum = 47;BA.debugLine="For Act=0 To Starter.NumSecuencias-1";
Debug.ShouldStop(16384);
{
final int step4 = 1;
final int limit4 = RemoteObject.solve(new RemoteObject[] {main.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_act = BA.numberCast(int.class, 0) ;
for (;(step4 > 0 && _act.<Integer>get().intValue() <= limit4) || (step4 < 0 && _act.<Integer>get().intValue() >= limit4) ;_act = RemoteObject.createImmutable((int)(0 + _act.<Integer>get().intValue() + step4))  ) {
Debug.locals.put("Act", _act);
 BA.debugLineNum = 49;BA.debugLine="PictogramaSecuencia(Act).Initialize(\"BotonPictog";
Debug.ShouldStop(65536);
main.mostCurrent._pictogramasecuencia.getArrayElement(false,_act).runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonPictograma")));
 BA.debugLineNum = 50;BA.debugLine="PictogramaSecuencia(Act).Tag=Act";
Debug.ShouldStop(131072);
main.mostCurrent._pictogramasecuencia.getArrayElement(false,_act).runMethod(false,"setTag",(_act));
 BA.debugLineNum = 51;BA.debugLine="PictogramaSecuencia(Act).Color=Colors.Transparen";
Debug.ShouldStop(262144);
main.mostCurrent._pictogramasecuencia.getArrayElement(false,_act).runVoidMethod ("setColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"Transparent"));
 BA.debugLineNum = 52;BA.debugLine="PictogramaSecuencia(Act).SetBackgroundImage(Load";
Debug.ShouldStop(524288);
main.mostCurrent._pictogramasecuencia.getArrayElement(false,_act).runVoidMethod ("SetBackgroundImageNew",(Object)((main.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.concat(main.mostCurrent._starter._secuencia.getArrayElement(false,_act).getField(true,"pictograma"),RemoteObject.createImmutable(".png")))).getObject())));
 BA.debugLineNum = 53;BA.debugLine="PanelScroll.Panel.AddView(PictogramaSecuencia(Ac";
Debug.ShouldStop(1048576);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((main.mostCurrent._pictogramasecuencia.getArrayElement(false,_act).getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10))),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),_act}, "+*",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))));
 BA.debugLineNum = 55;BA.debugLine="EtiquetaSecuencia(Act).Initialize(\"TextoSecuenci";
Debug.ShouldStop(4194304);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("TextoSecuencia")));
 BA.debugLineNum = 56;BA.debugLine="EtiquetaSecuencia(Act).Tag=Act";
Debug.ShouldStop(8388608);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runMethod(false,"setTag",(_act));
 BA.debugLineNum = 57;BA.debugLine="EtiquetaSecuencia(Act).Text=Starter.Secuencia(Ac";
Debug.ShouldStop(16777216);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runMethod(true,"setText",BA.ObjectToCharSequence(main.mostCurrent._starter._secuencia.getArrayElement(false,_act).getField(true,"Descripcion")));
 BA.debugLineNum = 58;BA.debugLine="EtiquetaSecuencia(Act).TextColor=Colors.Black";
Debug.ShouldStop(33554432);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runMethod(true,"setTextColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 59;BA.debugLine="EtiquetaSecuencia(Act).TextSize=16";
Debug.ShouldStop(67108864);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 60;BA.debugLine="EtiquetaSecuencia(Act).Gravity=Bit.Or(Gravity.CE";
Debug.ShouldStop(134217728);
main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).runMethod(true,"setGravity",main.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"LEFT"))));
 BA.debugLineNum = 61;BA.debugLine="PanelScroll.Panel.AddView(EtiquetaSecuencia(Act)";
Debug.ShouldStop(268435456);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((main.mostCurrent._etiquetasecuencia.getArrayElement(false,_act).getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 110)))),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10))),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),_act}, "+*",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 170)))}, "-",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))));
 BA.debugLineNum = 63;BA.debugLine="EditarSecuencia(Act).Initialize(\"BotonEditar\")";
Debug.ShouldStop(1073741824);
main.mostCurrent._editarsecuencia.getArrayElement(false,_act).runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonEditar")));
 BA.debugLineNum = 64;BA.debugLine="EditarSecuencia(Act).Tag=Act";
Debug.ShouldStop(-2147483648);
main.mostCurrent._editarsecuencia.getArrayElement(false,_act).runMethod(false,"setTag",(_act));
 BA.debugLineNum = 65;BA.debugLine="EditarSecuencia(Act).SetBackgroundImage(LoadBitm";
Debug.ShouldStop(1);
main.mostCurrent._editarsecuencia.getArrayElement(false,_act).runVoidMethod ("SetBackgroundImageNew",(Object)((main.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.createImmutable("engranaje.png"))).getObject())));
 BA.debugLineNum = 66;BA.debugLine="PanelScroll.Panel.AddView(EditarSecuencia(Act),1";
Debug.ShouldStop(2);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((main.mostCurrent._editarsecuencia.getArrayElement(false,_act).getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 60)))}, "-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30))),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),_act}, "+*",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 40)))),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 40)))));
 }
}Debug.locals.put("Act", _act);
;
 BA.debugLineNum = 70;BA.debugLine="BotonCrear.Initialize(\"BotonCrear\")";
Debug.ShouldStop(32);
main.mostCurrent._botoncrear.runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonCrear")));
 BA.debugLineNum = 71;BA.debugLine="BotonCrear.Text=\"Crear Secuencia\"";
Debug.ShouldStop(64);
main.mostCurrent._botoncrear.runMethod(true,"setText",BA.ObjectToCharSequence("Crear Secuencia"));
 BA.debugLineNum = 72;BA.debugLine="BotonCrear.TextSize=20";
Debug.ShouldStop(128);
main.mostCurrent._botoncrear.runMethod(true,"setTextSize",BA.numberCast(float.class, 20));
 BA.debugLineNum = 73;BA.debugLine="BotonCrear.Gravity=Bit.Or(Gravity.CENTER_VERTICAL";
Debug.ShouldStop(256);
main.mostCurrent._botoncrear.runMethod(true,"setGravity",main.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 74;BA.debugLine="BotonCrear.TextColor=Colors.Black";
Debug.ShouldStop(512);
main.mostCurrent._botoncrear.runMethod(true,"setTextColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 75;BA.debugLine="PanelScroll.Panel.AddView(BotonCrear,5dip,90dip*S";
Debug.ShouldStop(1024);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((main.mostCurrent._botoncrear.getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),main.mostCurrent._starter._numsecuencias}, "*",0, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))));
 BA.debugLineNum = 77;BA.debugLine="BotonAcercaDe.Initialize(\"BotonAcercaDe\")";
Debug.ShouldStop(4096);
main.mostCurrent._botonacercade.runVoidMethod ("Initialize",main.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonAcercaDe")));
 BA.debugLineNum = 78;BA.debugLine="BotonAcercaDe.Text=\"Acerca de Pictorario\"";
Debug.ShouldStop(8192);
main.mostCurrent._botonacercade.runMethod(true,"setText",BA.ObjectToCharSequence("Acerca de Pictorario"));
 BA.debugLineNum = 79;BA.debugLine="BotonAcercaDe.TextSize=20";
Debug.ShouldStop(16384);
main.mostCurrent._botonacercade.runMethod(true,"setTextSize",BA.numberCast(float.class, 20));
 BA.debugLineNum = 80;BA.debugLine="BotonAcercaDe.Gravity=Bit.Or(Gravity.CENTER_VERTI";
Debug.ShouldStop(32768);
main.mostCurrent._botonacercade.runMethod(true,"setGravity",main.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(main.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 81;BA.debugLine="BotonAcercaDe.TextColor=Colors.Black";
Debug.ShouldStop(65536);
main.mostCurrent._botonacercade.runMethod(true,"setTextColor",main.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 82;BA.debugLine="PanelScroll.Panel.AddView(BotonAcercaDe,5dip,90di";
Debug.ShouldStop(131072);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((main.mostCurrent._botonacercade.getObject())),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),(RemoteObject.solve(new RemoteObject[] {main.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "+",1, 1)),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "*-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),main.mostCurrent.activityBA),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)),(Object)(main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))));
 BA.debugLineNum = 84;BA.debugLine="PanelScroll.Panel.Height=90dip*(Starter.NumSecuen";
Debug.ShouldStop(524288);
main.mostCurrent._panelscroll.runMethod(false,"getPanel").runMethod(true,"setHeight",RemoteObject.solve(new RemoteObject[] {main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 90))),(RemoteObject.solve(new RemoteObject[] {main.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "+",1, 1)),main.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 100)))}, "*+",1, 1));
 BA.debugLineNum = 86;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 25;BA.debugLine="Dim PictogramaSecuencia(Starter.MaxSecuencias) As";
main.mostCurrent._pictogramasecuencia = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {main.mostCurrent._starter._maxsecuencias.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 26;BA.debugLine="Dim EditarSecuencia(Starter.MaxSecuencias) As But";
main.mostCurrent._editarsecuencia = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {main.mostCurrent._starter._maxsecuencias.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 27;BA.debugLine="Dim EtiquetaSecuencia(Starter.MaxActividades) As";
main.mostCurrent._etiquetasecuencia = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.LabelWrapper", new int[] {main.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 29;BA.debugLine="Dim BotonAcercaDe As Button";
main.mostCurrent._botonacercade = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 30;BA.debugLine="Dim BotonCrear As Button";
main.mostCurrent._botoncrear = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 32;BA.debugLine="Private PanelScroll As ScrollView";
main.mostCurrent._panelscroll = RemoteObject.createNew ("anywheresoftware.b4a.objects.ScrollViewWrapper");
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main_subs_0._process_globals();
configurarsecuencia_subs_0._process_globals();
starter_subs_0._process_globals();
visualizacion_subs_0._process_globals();
acercade_subs_0._process_globals();
main.myClass = BA.getDeviceClass ("javi.prieto.pictorario.main");
configurarsecuencia.myClass = BA.getDeviceClass ("javi.prieto.pictorario.configurarsecuencia");
starter.myClass = BA.getDeviceClass ("javi.prieto.pictorario.starter");
visualizacion.myClass = BA.getDeviceClass ("javi.prieto.pictorario.visualizacion");
acercade.myClass = BA.getDeviceClass ("javi.prieto.pictorario.acercade");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _textosecuencia_click() throws Exception{
try {
		Debug.PushSubsStack("TextoSecuencia_click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,121);
if (RapidSub.canDelegate("textosecuencia_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","textosecuencia_click");
RemoteObject _etiquetapulsada = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
 BA.debugLineNum = 121;BA.debugLine="Sub TextoSecuencia_click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 122;BA.debugLine="Dim EtiquetaPulsada As Label";
Debug.ShouldStop(33554432);
_etiquetapulsada = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("EtiquetaPulsada", _etiquetapulsada);
 BA.debugLineNum = 123;BA.debugLine="EtiquetaPulsada=Sender";
Debug.ShouldStop(67108864);
_etiquetapulsada.setObject(main.mostCurrent.__c.runMethod(false,"Sender",main.mostCurrent.activityBA));
 BA.debugLineNum = 124;BA.debugLine="Starter.SecuenciaActiva=EtiquetaPulsada.Tag";
Debug.ShouldStop(134217728);
main.mostCurrent._starter._secuenciaactiva = BA.numberCast(int.class, _etiquetapulsada.runMethod(false,"getTag"));
 BA.debugLineNum = 125;BA.debugLine="StartActivity(Visualizacion)";
Debug.ShouldStop(268435456);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._visualizacion.getObject())));
 BA.debugLineNum = 126;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _versecuencias_click() throws Exception{
try {
		Debug.PushSubsStack("VerSecuencias_Click (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,137);
if (RapidSub.canDelegate("versecuencias_click")) return javi.prieto.pictorario.main.remoteMe.runUserSub(false, "main","versecuencias_click");
 BA.debugLineNum = 137;BA.debugLine="Sub VerSecuencias_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 138;BA.debugLine="Starter.SecuenciaActiva=1";
Debug.ShouldStop(512);
main.mostCurrent._starter._secuenciaactiva = BA.numberCast(int.class, 1);
 BA.debugLineNum = 139;BA.debugLine="StartActivity(Visualizacion)";
Debug.ShouldStop(1024);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._visualizacion.getObject())));
 BA.debugLineNum = 140;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}