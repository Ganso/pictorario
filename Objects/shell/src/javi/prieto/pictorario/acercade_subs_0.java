package javi.prieto.pictorario;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class acercade_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,25);
if (RapidSub.canDelegate("activity_create")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"AcercaDe\")";
Debug.ShouldStop(67108864);
acercade.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("AcercaDe")),acercade.mostCurrent.activityBA);
 BA.debugLineNum = 29;BA.debugLine="TextoAutor.LoadHtml(\"<html><body><center>\"& _ 	\"<";
Debug.ShouldStop(268435456);
acercade.mostCurrent._textoautor.runVoidMethod ("LoadHtml",(Object)(RemoteObject.concat(RemoteObject.createImmutable("<html><body><center>"),RemoteObject.createImmutable("<strong>Aplicación</strong>: Javier Prieto Martínez (www.ganso.org)<br />"),RemoteObject.createImmutable("<strong>Licencia</strong>: CC (BY-NC-SA)<br />"),RemoteObject.createImmutable("<em>Pulsar icono para web</em><br />"),RemoteObject.createImmutable("</center></body></html>"))));
 BA.debugLineNum = 35;BA.debugLine="TextoArasaac.LoadHtml(\"<html><body><center>\"& _";
Debug.ShouldStop(4);
acercade.mostCurrent._textoarasaac.runVoidMethod ("LoadHtml",(Object)(RemoteObject.concat(RemoteObject.createImmutable("<html><body><center>"),RemoteObject.createImmutable("<strong>Pictogramas</strong>: Sergio Palao<br />"),RemoteObject.createImmutable("<strong>Procedencia</strong>: ARASAAC (www.arasaac.org)<br />"),RemoteObject.createImmutable("<strong>Licencia</strong>: CC (BY-NC-SA)<br />"),RemoteObject.createImmutable("<strong>Propiedad</strong>: Gobierno de Aragón<br />"),RemoteObject.createImmutable("<em>Pulsar icono para web</em><br />"),RemoteObject.createImmutable("</center></body></html>"))));
 BA.debugLineNum = 42;BA.debugLine="End Sub";
Debug.ShouldStop(512);
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
		Debug.PushSubsStack("Activity_Pause (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,48);
if (RapidSub.canDelegate("activity_pause")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 48;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 50;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
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
		Debug.PushSubsStack("Activity_Resume (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,44);
if (RapidSub.canDelegate("activity_resume")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","activity_resume");
 BA.debugLineNum = 44;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(2048);
 BA.debugLineNum = 46;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private Volver As Button";
acercade.mostCurrent._volver = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 17;BA.debugLine="Private Logotipo As ImageView";
acercade.mostCurrent._logotipo = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 18;BA.debugLine="Private Pictogramas As ImageView";
acercade.mostCurrent._pictogramas = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 19;BA.debugLine="Private Pictorario As Label";
acercade.mostCurrent._pictorario = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 20;BA.debugLine="Private Programador As ImageView";
acercade.mostCurrent._programador = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 21;BA.debugLine="Private TextoArasaac As WebView";
acercade.mostCurrent._textoarasaac = RemoteObject.createNew ("anywheresoftware.b4a.objects.WebViewWrapper");
 //BA.debugLineNum = 22;BA.debugLine="Private TextoAutor As WebView";
acercade.mostCurrent._textoautor = RemoteObject.createNew ("anywheresoftware.b4a.objects.WebViewWrapper");
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _logotipo_click() throws Exception{
try {
		Debug.PushSubsStack("Logotipo_Click (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,68);
if (RapidSub.canDelegate("logotipo_click")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","logotipo_click");
 BA.debugLineNum = 68;BA.debugLine="Sub Logotipo_Click";
Debug.ShouldStop(8);
 BA.debugLineNum = 70;BA.debugLine="End Sub";
Debug.ShouldStop(32);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _pictogramas_click() throws Exception{
try {
		Debug.PushSubsStack("Pictogramas_Click (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,63);
if (RapidSub.canDelegate("pictogramas_click")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","pictogramas_click");
RemoteObject _p = RemoteObject.declareNull("anywheresoftware.b4a.phone.Phone.PhoneIntents");
 BA.debugLineNum = 63;BA.debugLine="Sub Pictogramas_Click";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 64;BA.debugLine="Dim p As PhoneIntents";
Debug.ShouldStop(-2147483648);
_p = RemoteObject.createNew ("anywheresoftware.b4a.phone.Phone.PhoneIntents");Debug.locals.put("p", _p);
 BA.debugLineNum = 65;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.arasaac.o";
Debug.ShouldStop(1);
acercade.mostCurrent.__c.runVoidMethod ("StartActivity",acercade.processBA,(Object)((_p.runMethod(false,"OpenBrowser",(Object)(RemoteObject.createImmutable("http://www.arasaac.org"))))));
 BA.debugLineNum = 66;BA.debugLine="End Sub";
Debug.ShouldStop(2);
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
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _programador_click() throws Exception{
try {
		Debug.PushSubsStack("Programador_Click (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,58);
if (RapidSub.canDelegate("programador_click")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","programador_click");
RemoteObject _p = RemoteObject.declareNull("anywheresoftware.b4a.phone.Phone.PhoneIntents");
 BA.debugLineNum = 58;BA.debugLine="Sub Programador_Click";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 59;BA.debugLine="Dim p As PhoneIntents";
Debug.ShouldStop(67108864);
_p = RemoteObject.createNew ("anywheresoftware.b4a.phone.Phone.PhoneIntents");Debug.locals.put("p", _p);
 BA.debugLineNum = 60;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.ganso.org";
Debug.ShouldStop(134217728);
acercade.mostCurrent.__c.runVoidMethod ("StartActivity",acercade.processBA,(Object)((_p.runMethod(false,"OpenBrowser",(Object)(RemoteObject.createImmutable("http://www.ganso.org"))))));
 BA.debugLineNum = 61;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _volver_click() throws Exception{
try {
		Debug.PushSubsStack("Volver_Click (acercade) ","acercade",4,acercade.mostCurrent.activityBA,acercade.mostCurrent,53);
if (RapidSub.canDelegate("volver_click")) return javi.prieto.pictorario.acercade.remoteMe.runUserSub(false, "acercade","volver_click");
 BA.debugLineNum = 53;BA.debugLine="Sub Volver_Click";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 55;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4194304);
acercade.mostCurrent._activity.runVoidMethod ("Finish");
 BA.debugLineNum = 56;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}