package javi.prieto.pictorario;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class configurarsecuencia_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,57);
if (RapidSub.canDelegate("activity_create")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 57;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 61;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).Initiali";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).runVoidMethod ("Initialize");
 BA.debugLineNum = 62;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero.";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").runVoidMethod ("Initialize");
 BA.debugLineNum = 64;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
Debug.ShouldStop(-2147483648);
if ((RemoteObject.solveBoolean("=",configurarsecuencia.mostCurrent._starter._secuenciaactiva,BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._maxsecuencias)))) { 
 BA.debugLineNum = 65;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("num_actividades",BA.numberCast(int.class, 0));
 BA.debugLineNum = 66;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
Debug.ShouldStop(2);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("Descripcion",BA.ObjectToString("Nombre de la nueva secuencia"));
 BA.debugLineNum = 67;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
Debug.ShouldStop(4);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("pictograma",BA.ObjectToString("reloj_6"));
 BA.debugLineNum = 68;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(8);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("tipo",BA.numberCast(int.class, 3));
 BA.debugLineNum = 69;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(16);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("tam_icono",BA.numberCast(int.class, 20));
 BA.debugLineNum = 70;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("indicar_hora",BA.numberCast(int.class, 0));
 }else {
 BA.debugLineNum = 72;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Secu";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent.__c.runMethodAndSync(false,"CallSubNew3",configurarsecuencia.processBA,(Object)((configurarsecuencia.mostCurrent._starter.getObject())),(Object)(BA.ObjectToString("CopiarSecuencias")),(Object)((configurarsecuencia.mostCurrent._starter._secuenciaactiva)),(Object)((configurarsecuencia.mostCurrent._starter._maxsecuencias)));
 };
 BA.debugLineNum = 75;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(1024);
_dibujarconfigurarsecuencia();
 BA.debugLineNum = 76;BA.debugLine="Inicializar_Lista_Pictogramas";
Debug.ShouldStop(2048);
_inicializar_lista_pictogramas();
 BA.debugLineNum = 78;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
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
		Debug.PushSubsStack("Activity_Pause (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,297);
if (RapidSub.canDelegate("activity_pause")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 297;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(256);
 BA.debugLineNum = 299;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
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
		Debug.PushSubsStack("Activity_Resume (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,293);
if (RapidSub.canDelegate("activity_resume")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","activity_resume");
 BA.debugLineNum = 293;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(16);
 BA.debugLineNum = 295;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botonaceptar_click() throws Exception{
try {
		Debug.PushSubsStack("BotonAceptar_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,282);
if (RapidSub.canDelegate("botonaceptar_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","botonaceptar_click");
 BA.debugLineNum = 282;BA.debugLine="Sub BotonAceptar_Click";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 283;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
Debug.ShouldStop(67108864);
if ((RemoteObject.solveBoolean("=",configurarsecuencia.mostCurrent._starter._secuenciaactiva,BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._maxsecuencias)))) { 
 BA.debugLineNum = 284;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._starter._numsecuencias = RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "+",1, 1);
 BA.debugLineNum = 285;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent.__c.runMethodAndSync(false,"CallSubNew3",configurarsecuencia.processBA,(Object)((configurarsecuencia.mostCurrent._starter.getObject())),(Object)(BA.ObjectToString("CopiarSecuencias")),(Object)((configurarsecuencia.mostCurrent._starter._maxsecuencias)),(Object)((RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._numsecuencias,RemoteObject.createImmutable(1)}, "-",1, 1))));
 }else {
 BA.debugLineNum = 287;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent.__c.runMethodAndSync(false,"CallSubNew3",configurarsecuencia.processBA,(Object)((configurarsecuencia.mostCurrent._starter.getObject())),(Object)(BA.ObjectToString("CopiarSecuencias")),(Object)((configurarsecuencia.mostCurrent._starter._maxsecuencias)),(Object)((configurarsecuencia.mostCurrent._starter._secuenciaactiva)));
 };
 BA.debugLineNum = 289;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent.__c.runMethodAndSync(false,"CallSubNew",configurarsecuencia.processBA,(Object)((configurarsecuencia.mostCurrent._starter.getObject())),(Object)(RemoteObject.createImmutable("Guardar_Configuracion")));
 BA.debugLineNum = 290;BA.debugLine="Activity.Finish";
Debug.ShouldStop(2);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Finish");
 BA.debugLineNum = 291;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botonanadiractividad_click() throws Exception{
try {
		Debug.PushSubsStack("BotonAnadirActividad_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,425);
if (RapidSub.canDelegate("botonanadiractividad_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","botonanadiractividad_click");
 BA.debugLineNum = 425;BA.debugLine="Sub BotonAnadirActividad_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 426;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("Descripcion",BA.ObjectToString("Nueva actividad"));
 BA.debugLineNum = 428;BA.debugLine="If (Starter.Secuencia(Starter.MaxSecuencias).num_";
Debug.ShouldStop(2048);
if ((RemoteObject.solveBoolean(">",configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 429;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(4096);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("hora_inicio",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1)).getField(true,"hora_fin"));
 BA.debugLineNum = 430;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(8192);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("minuto_inicio",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1)).getField(true,"minuto_fin"));
 }else {
 BA.debugLineNum = 432;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(32768);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("hora_inicio",BA.numberCast(int.class, 8));
 BA.debugLineNum = 433;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(65536);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 };
 BA.debugLineNum = 436;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
Debug.ShouldStop(524288);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("hora_fin",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).getField(true,"hora_inicio"));
 BA.debugLineNum = 437;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
Debug.ShouldStop(1048576);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("minuto_fin",RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).getField(true,"minuto_inicio"),RemoteObject.createImmutable(30)}, "+",1, 1));
 BA.debugLineNum = 438;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
Debug.ShouldStop(2097152);
if (RemoteObject.solveBoolean(">",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).getField(true,"minuto_fin"),BA.numberCast(double.class, 59))) { 
 BA.debugLineNum = 439;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(4194304);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("minuto_fin",RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).getField(true,"minuto_fin"),RemoteObject.createImmutable(60)}, "-",1, 1));
 BA.debugLineNum = 440;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("hora_fin",RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).getField(true,"hora_fin"),RemoteObject.createImmutable(1)}, "+",1, 1));
 };
 BA.debugLineNum = 443;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades")).setField ("Pictograma",BA.ObjectToString("jugar"));
 BA.debugLineNum = 444;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_acti";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("num_actividades",RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "+",1, 1));
 BA.debugLineNum = 445;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(268435456);
_dibujarconfigurarsecuencia();
 BA.debugLineNum = 446;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _botoncancelar_click() throws Exception{
try {
		Debug.PushSubsStack("BotonCancelar_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,278);
if (RapidSub.canDelegate("botoncancelar_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","botoncancelar_click");
 BA.debugLineNum = 278;BA.debugLine="Sub BotonCancelar_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 279;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4194304);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Finish");
 BA.debugLineNum = 280;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configdescripcion_textchanged(RemoteObject _old,RemoteObject _new) throws Exception{
try {
		Debug.PushSubsStack("ConfigDescripcion_TextChanged (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,421);
if (RapidSub.canDelegate("configdescripcion_textchanged")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configdescripcion_textchanged", _old, _new);
Debug.locals.put("Old", _old);
Debug.locals.put("New", _new);
 BA.debugLineNum = 421;BA.debugLine="Sub ConfigDescripcion_TextChanged (Old As String,";
Debug.ShouldStop(16);
 BA.debugLineNum = 422;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descripc";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("Descripcion",_new);
 BA.debugLineNum = 423;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configdescripcionact_textchanged(RemoteObject _old,RemoteObject _new) throws Exception{
try {
		Debug.PushSubsStack("ConfigDescripcionAct_TextChanged (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,410);
if (RapidSub.canDelegate("configdescripcionact_textchanged")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configdescripcionact_textchanged", _old, _new);
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.EditTextWrapper");
RemoteObject _act = RemoteObject.createImmutable(0);
Debug.locals.put("Old", _old);
Debug.locals.put("New", _new);
 BA.debugLineNum = 410;BA.debugLine="Sub ConfigDescripcionAct_TextChanged (Old As Strin";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 411;BA.debugLine="Dim BotonPulsado As EditText";
Debug.ShouldStop(67108864);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.EditTextWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 412;BA.debugLine="Dim Act As Int";
Debug.ShouldStop(134217728);
_act = RemoteObject.createImmutable(0);Debug.locals.put("Act", _act);
 BA.debugLineNum = 414;BA.debugLine="If Inicializando==False Then";
Debug.ShouldStop(536870912);
if (RemoteObject.solveBoolean("=",configurarsecuencia._inicializando,configurarsecuencia.mostCurrent.__c.getField(true,"False"))) { 
 BA.debugLineNum = 415;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(1073741824);
_botonpulsado.setObject(configurarsecuencia.mostCurrent.__c.runMethod(false,"Sender",configurarsecuencia.mostCurrent.activityBA));
 BA.debugLineNum = 416;BA.debugLine="Act=BotonPulsado.Tag";
Debug.ShouldStop(-2147483648);
_act = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));Debug.locals.put("Act", _act);
 BA.debugLineNum = 417;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).setField ("Descripcion",_new);
 };
 BA.debugLineNum = 419;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _confighorafinalact_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigHoraFinalAct_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,336);
if (RapidSub.canDelegate("confighorafinalact_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","confighorafinalact_click");
RemoteObject _dialogotiempo = RemoteObject.declareNull("anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _resultado = RemoteObject.createImmutable(0);
RemoteObject _act = RemoteObject.createImmutable(0);
 BA.debugLineNum = 336;BA.debugLine="Sub ConfigHoraFinalAct_Click";
Debug.ShouldStop(32768);
 BA.debugLineNum = 337;BA.debugLine="Dim DialogoTiempo As TimeDialog";
Debug.ShouldStop(65536);
_dialogotiempo = RemoteObject.createNew ("anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog");Debug.locals.put("DialogoTiempo", _dialogotiempo);
 BA.debugLineNum = 338;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(131072);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 339;BA.debugLine="Dim Resultado As Int";
Debug.ShouldStop(262144);
_resultado = RemoteObject.createImmutable(0);Debug.locals.put("Resultado", _resultado);
 BA.debugLineNum = 340;BA.debugLine="Dim Act As Int";
Debug.ShouldStop(524288);
_act = RemoteObject.createImmutable(0);Debug.locals.put("Act", _act);
 BA.debugLineNum = 342;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(2097152);
_botonpulsado.setObject(configurarsecuencia.mostCurrent.__c.runMethod(false,"Sender",configurarsecuencia.mostCurrent.activityBA));
 BA.debugLineNum = 343;BA.debugLine="Act=BotonPulsado.Tag";
Debug.ShouldStop(4194304);
_act = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));Debug.locals.put("Act", _act);
 BA.debugLineNum = 345;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
Debug.ShouldStop(16777216);
_dialogotiempo.runMethod(true,"setHour",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).getField(true,"hora_fin"));
 BA.debugLineNum = 346;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
Debug.ShouldStop(33554432);
_dialogotiempo.runMethod(true,"setMinute",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).getField(true,"minuto_fin"));
 BA.debugLineNum = 347;BA.debugLine="DialogoTiempo.Is24Hours=True";
Debug.ShouldStop(67108864);
_dialogotiempo.runMethod(true,"setIs24Hours",configurarsecuencia.mostCurrent.__c.getField(true,"True"));
 BA.debugLineNum = 348;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora fina";
Debug.ShouldStop(134217728);
_resultado = _dialogotiempo.runMethodAndSync(true,"Show",(Object)(BA.ObjectToString("Indica la hora final de la actividad")),(Object)(BA.ObjectToString("Hora final")),(Object)(BA.ObjectToString("Aceptar")),(Object)(BA.ObjectToString("Cancelar")),(Object)(BA.ObjectToString("")),configurarsecuencia.mostCurrent.activityBA,(Object)((configurarsecuencia.mostCurrent.__c.getField(false,"Null"))));Debug.locals.put("Resultado", _resultado);
 BA.debugLineNum = 350;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
Debug.ShouldStop(536870912);
if (RemoteObject.solveBoolean("=",_resultado,BA.numberCast(double.class, configurarsecuencia.mostCurrent.__c.getField(false,"DialogResponse").getField(true,"POSITIVE")))) { 
 BA.debugLineNum = 351;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).setField ("hora_fin",_dialogotiempo.runMethod(true,"getHour"));
 BA.debugLineNum = 352;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(-2147483648);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).setField ("minuto_fin",_dialogotiempo.runMethod(true,"getMinute"));
 BA.debugLineNum = 353;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 354;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(2);
_dibujarconfigurarsecuencia();
 };
 BA.debugLineNum = 356;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _confighorainicioact_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigHoraInicioAct_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,313);
if (RapidSub.canDelegate("confighorainicioact_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","confighorainicioact_click");
RemoteObject _dialogotiempo = RemoteObject.declareNull("anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
RemoteObject _resultado = RemoteObject.createImmutable(0);
RemoteObject _act = RemoteObject.createImmutable(0);
 BA.debugLineNum = 313;BA.debugLine="Sub ConfigHoraInicioAct_Click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 314;BA.debugLine="Dim DialogoTiempo As TimeDialog";
Debug.ShouldStop(33554432);
_dialogotiempo = RemoteObject.createNew ("anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog");Debug.locals.put("DialogoTiempo", _dialogotiempo);
 BA.debugLineNum = 315;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(67108864);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 316;BA.debugLine="Dim Resultado As Int";
Debug.ShouldStop(134217728);
_resultado = RemoteObject.createImmutable(0);Debug.locals.put("Resultado", _resultado);
 BA.debugLineNum = 317;BA.debugLine="Dim Act As Int";
Debug.ShouldStop(268435456);
_act = RemoteObject.createImmutable(0);Debug.locals.put("Act", _act);
 BA.debugLineNum = 319;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(1073741824);
_botonpulsado.setObject(configurarsecuencia.mostCurrent.__c.runMethod(false,"Sender",configurarsecuencia.mostCurrent.activityBA));
 BA.debugLineNum = 320;BA.debugLine="Act=BotonPulsado.Tag";
Debug.ShouldStop(-2147483648);
_act = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));Debug.locals.put("Act", _act);
 BA.debugLineNum = 322;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
Debug.ShouldStop(2);
_dialogotiempo.runMethod(true,"setHour",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).getField(true,"hora_inicio"));
 BA.debugLineNum = 323;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
Debug.ShouldStop(4);
_dialogotiempo.runMethod(true,"setMinute",configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).getField(true,"minuto_inicio"));
 BA.debugLineNum = 324;BA.debugLine="DialogoTiempo.Is24Hours=True";
Debug.ShouldStop(8);
_dialogotiempo.runMethod(true,"setIs24Hours",configurarsecuencia.mostCurrent.__c.getField(true,"True"));
 BA.debugLineNum = 325;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora inic";
Debug.ShouldStop(16);
_resultado = _dialogotiempo.runMethodAndSync(true,"Show",(Object)(BA.ObjectToString("Indica la hora inicial de la actividad")),(Object)(BA.ObjectToString("Hora inicial")),(Object)(BA.ObjectToString("Aceptar")),(Object)(BA.ObjectToString("Cancelar")),(Object)(BA.ObjectToString("")),configurarsecuencia.mostCurrent.activityBA,(Object)((configurarsecuencia.mostCurrent.__c.getField(false,"Null"))));Debug.locals.put("Resultado", _resultado);
 BA.debugLineNum = 327;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
Debug.ShouldStop(64);
if (RemoteObject.solveBoolean("=",_resultado,BA.numberCast(double.class, configurarsecuencia.mostCurrent.__c.getField(false,"DialogResponse").getField(true,"POSITIVE")))) { 
 BA.debugLineNum = 328;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).setField ("hora_inicio",_dialogotiempo.runMethod(true,"getHour"));
 BA.debugLineNum = 329;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,_act).setField ("minuto_inicio",_dialogotiempo.runMethod(true,"getMinute"));
 BA.debugLineNum = 330;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 331;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(1024);
_dibujarconfigurarsecuencia();
 };
 BA.debugLineNum = 333;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configindicarhora_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigIndicarHora_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,257);
if (RapidSub.canDelegate("configindicarhora_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configindicarhora_click");
RemoteObject _tiposindicador = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
RemoteObject _resultado = RemoteObject.createImmutable(0);
 BA.debugLineNum = 257;BA.debugLine="Sub ConfigIndicarHora_Click";
Debug.ShouldStop(1);
 BA.debugLineNum = 258;BA.debugLine="Dim TiposIndicador As List";
Debug.ShouldStop(2);
_tiposindicador = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");Debug.locals.put("TiposIndicador", _tiposindicador);
 BA.debugLineNum = 259;BA.debugLine="Dim resultado As Int";
Debug.ShouldStop(4);
_resultado = RemoteObject.createImmutable(0);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 261;BA.debugLine="TiposIndicador.Initialize";
Debug.ShouldStop(16);
_tiposindicador.runVoidMethod ("Initialize");
 BA.debugLineNum = 262;BA.debugLine="TiposIndicador.AddAll(Starter.DescripcionMinutero";
Debug.ShouldStop(32);
_tiposindicador.runVoidMethod ("AddAll",(Object)(configurarsecuencia.mostCurrent.__c.runMethod(false, "ArrayToList", (Object)(configurarsecuencia.mostCurrent._starter._descripcionminutero))));
 BA.debugLineNum = 264;BA.debugLine="resultado=InputList(TiposIndicador,\"Indicar hora";
Debug.ShouldStop(128);
_resultado = configurarsecuencia.mostCurrent.__c.runMethodAndSync(true,"InputList",(Object)(_tiposindicador),(Object)(BA.ObjectToCharSequence("Indicar hora actual")),(Object)(configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").getField(true,"indicar_hora")),configurarsecuencia.mostCurrent.activityBA);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 265;BA.debugLine="If (resultado>=0) Then";
Debug.ShouldStop(256);
if ((RemoteObject.solveBoolean("g",_resultado,BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 266;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("indicar_hora",_resultado);
 BA.debugLineNum = 267;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(1024);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 268;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(2048);
_dibujarconfigurarsecuencia();
 };
 BA.debugLineNum = 270;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configpictograma_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigPictograma_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,301);
if (RapidSub.canDelegate("configpictograma_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configpictograma_click");
 BA.debugLineNum = 301;BA.debugLine="Sub ConfigPictograma_Click";
Debug.ShouldStop(4096);
 BA.debugLineNum = 302;BA.debugLine="PictogramaEditado=-1";
Debug.ShouldStop(8192);
configurarsecuencia._pictogramaeditado = BA.numberCast(int.class, -(double) (0 + 1));
 BA.debugLineNum = 303;BA.debugLine="Activity.AddView(ListaPictogramas, 5dip, 5dip, 10";
Debug.ShouldStop(16384);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._listapictogramas.getObject())),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)));
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
public static RemoteObject  _configpictogramaact_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigPictogramaAct_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,306);
if (RapidSub.canDelegate("configpictogramaact_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configpictogramaact_click");
RemoteObject _botonpulsado = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
 BA.debugLineNum = 306;BA.debugLine="Sub ConfigPictogramaAct_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 307;BA.debugLine="Dim BotonPulsado As Button";
Debug.ShouldStop(262144);
_botonpulsado = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");Debug.locals.put("BotonPulsado", _botonpulsado);
 BA.debugLineNum = 308;BA.debugLine="BotonPulsado=Sender";
Debug.ShouldStop(524288);
_botonpulsado.setObject(configurarsecuencia.mostCurrent.__c.runMethod(false,"Sender",configurarsecuencia.mostCurrent.activityBA));
 BA.debugLineNum = 309;BA.debugLine="PictogramaEditado=BotonPulsado.Tag";
Debug.ShouldStop(1048576);
configurarsecuencia._pictogramaeditado = BA.numberCast(int.class, _botonpulsado.runMethod(false,"getTag"));
 BA.debugLineNum = 310;BA.debugLine="Activity.AddView(ListaPictogramas, 5dip, 5dip, 1";
Debug.ShouldStop(2097152);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._listapictogramas.getObject())),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerYToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)));
 BA.debugLineNum = 311;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configtamicono_valuechanged(RemoteObject _valor,RemoteObject _cambio) throws Exception{
try {
		Debug.PushSubsStack("ConfigTamIcono_ValueChanged (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,272);
if (RapidSub.canDelegate("configtamicono_valuechanged")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configtamicono_valuechanged", _valor, _cambio);
Debug.locals.put("Valor", _valor);
Debug.locals.put("Cambio", _cambio);
 BA.debugLineNum = 272;BA.debugLine="Sub ConfigTamIcono_ValueChanged (Valor As Int, Cam";
Debug.ShouldStop(32768);
 BA.debugLineNum = 273;BA.debugLine="If (Cambio==True) Then";
Debug.ShouldStop(65536);
if ((RemoteObject.solveBoolean("=",_cambio,configurarsecuencia.mostCurrent.__c.getField(true,"True")))) { 
 BA.debugLineNum = 274;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("tam_icono",_valor);
 };
 BA.debugLineNum = 276;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _configtipotablero_click() throws Exception{
try {
		Debug.PushSubsStack("ConfigTipoTablero_Click (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,242);
if (RapidSub.canDelegate("configtipotablero_click")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","configtipotablero_click");
RemoteObject _tipostablero = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
RemoteObject _resultado = RemoteObject.createImmutable(0);
 BA.debugLineNum = 242;BA.debugLine="Sub ConfigTipoTablero_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 243;BA.debugLine="Dim TiposTablero As List";
Debug.ShouldStop(262144);
_tipostablero = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");Debug.locals.put("TiposTablero", _tipostablero);
 BA.debugLineNum = 244;BA.debugLine="Dim resultado As Int";
Debug.ShouldStop(524288);
_resultado = RemoteObject.createImmutable(0);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 246;BA.debugLine="TiposTablero.Initialize";
Debug.ShouldStop(2097152);
_tipostablero.runVoidMethod ("Initialize");
 BA.debugLineNum = 247;BA.debugLine="TiposTablero.AddAll(Starter.DescripcionTablero)";
Debug.ShouldStop(4194304);
_tipostablero.runVoidMethod ("AddAll",(Object)(configurarsecuencia.mostCurrent.__c.runMethod(false, "ArrayToList", (Object)(configurarsecuencia.mostCurrent._starter._descripciontablero))));
 BA.debugLineNum = 249;BA.debugLine="resultado=InputList(TiposTablero,\"Tipo de secuenc";
Debug.ShouldStop(16777216);
_resultado = configurarsecuencia.mostCurrent.__c.runMethodAndSync(true,"InputList",(Object)(_tipostablero),(Object)(BA.ObjectToCharSequence("Tipo de secuencia")),(Object)(configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").getField(true,"tipo")),configurarsecuencia.mostCurrent.activityBA);Debug.locals.put("resultado", _resultado);
 BA.debugLineNum = 250;BA.debugLine="If (resultado>=0) Then";
Debug.ShouldStop(33554432);
if ((RemoteObject.solveBoolean("g",_resultado,BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 251;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").setField ("tipo",_resultado);
 BA.debugLineNum = 252;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 253;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(268435456);
_dibujarconfigurarsecuencia();
 };
 BA.debugLineNum = 255;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _dibujarconfigurarsecuencia() throws Exception{
try {
		Debug.PushSubsStack("DibujarConfigurarSecuencia (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,80);
if (RapidSub.canDelegate("dibujarconfigurarsecuencia")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","dibujarconfigurarsecuencia");
RemoteObject _iniciovertical = RemoteObject.createImmutable(0);
RemoteObject _finvertical = RemoteObject.createImmutable(0);
int _act = 0;
 BA.debugLineNum = 80;BA.debugLine="Sub DibujarConfigurarSecuencia";
Debug.ShouldStop(32768);
 BA.debugLineNum = 82;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("RemoveAllViews");
 BA.debugLineNum = 83;BA.debugLine="Activity.LoadLayout(\"ConfigurarSecuencia\")";
Debug.ShouldStop(262144);
configurarsecuencia.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("ConfigurarSecuencia")),configurarsecuencia.mostCurrent.activityBA);
 BA.debugLineNum = 85;BA.debugLine="Inicializando=True 'Para evitar que se lancen pro";
Debug.ShouldStop(1048576);
configurarsecuencia._inicializando = configurarsecuencia.mostCurrent.__c.getField(true,"True");
 BA.debugLineNum = 87;BA.debugLine="EtiquetaInicial.Initialize(\"EtiquetaInicial\")";
Debug.ShouldStop(4194304);
configurarsecuencia.mostCurrent._etiquetainicial.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("EtiquetaInicial")));
 BA.debugLineNum = 88;BA.debugLine="EtiquetaInicial.Text=\"Crear nueva secuencia\"";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"setText",BA.ObjectToCharSequence("Crear nueva secuencia"));
 BA.debugLineNum = 89;BA.debugLine="If (Starter.SecuenciaActiva<Starter.MaxSecuencias";
Debug.ShouldStop(16777216);
if ((RemoteObject.solveBoolean("<",configurarsecuencia.mostCurrent._starter._secuenciaactiva,BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._maxsecuencias)))) { 
 BA.debugLineNum = 90;BA.debugLine="EtiquetaInicial.Text=\"Editar secuencia\"";
Debug.ShouldStop(33554432);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"setText",BA.ObjectToCharSequence("Editar secuencia"));
 };
 BA.debugLineNum = 92;BA.debugLine="EtiquetaInicial.TextColor=Colors.Black";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 93;BA.debugLine="EtiquetaInicial.TextSize=24";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"setTextSize",BA.numberCast(float.class, 24));
 BA.debugLineNum = 94;BA.debugLine="EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(false,"setTypeface",configurarsecuencia.mostCurrent.__c.getField(false,"Typeface").getField(false,"DEFAULT_BOLD"));
 BA.debugLineNum = 95;BA.debugLine="EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VER";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 96;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiquetaInicial";
Debug.ShouldStop(-2147483648);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._etiquetainicial.getObject())),(Object)(BA.numberCast(int.class, 0)),(Object)(BA.numberCast(int.class, 0)),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA)),(Object)(configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 80)))));
 BA.debugLineNum = 98;BA.debugLine="ConfigPictograma.Initialize(\"ConfigPictograma\")";
Debug.ShouldStop(2);
configurarsecuencia.mostCurrent._configpictograma.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigPictograma")));
 BA.debugLineNum = 99;BA.debugLine="ConfigPictograma.SetBackgroundImage(LoadBitmap(Fi";
Debug.ShouldStop(4);
configurarsecuencia.mostCurrent._configpictograma.runVoidMethod ("SetBackgroundImageNew",(Object)((configurarsecuencia.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.concat(configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"pictograma"),RemoteObject.createImmutable(".png")))).getObject())));
 BA.debugLineNum = 100;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogram";
Debug.ShouldStop(8);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configpictograma.getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._tamcasilla,configurarsecuencia._separacioncasillas}, "--",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(configurarsecuencia._tamcasilla),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 102;BA.debugLine="ConfigDescripcion.Initialize(\"ConfigDescripcion\")";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._configdescripcion.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigDescripcion")));
 BA.debugLineNum = 103;BA.debugLine="ConfigDescripcion.Text=Starter.Secuencia(Starter.";
Debug.ShouldStop(64);
configurarsecuencia.mostCurrent._configdescripcion.runMethodAndSync(true,"setText",BA.ObjectToCharSequence(configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"Descripcion")));
 BA.debugLineNum = 104;BA.debugLine="ConfigDescripcion.TextColor=Colors.White";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"White"));
 BA.debugLineNum = 105;BA.debugLine="ConfigDescripcion.TextSize=16";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 106;BA.debugLine="ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._configdescripcion.runMethod(false,"setTypeface",configurarsecuencia.mostCurrent.__c.getField(false,"Typeface").getField(false,"DEFAULT_BOLD"));
 BA.debugLineNum = 107;BA.debugLine="ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_V";
Debug.ShouldStop(1024);
configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 108;BA.debugLine="ConfigDescripcion.Color=Colors.DarkGray";
Debug.ShouldStop(2048);
configurarsecuencia.mostCurrent._configdescripcion.runVoidMethod ("setColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"DarkGray"));
 BA.debugLineNum = 109;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripci";
Debug.ShouldStop(4096);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configdescripcion.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._etiquetainicial.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configpictograma.runMethod(true,"getLeft"),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 111;BA.debugLine="EtiqTipoTablero.Initialize(\"EtiqTipoTablero\")";
Debug.ShouldStop(16384);
configurarsecuencia.mostCurrent._etiqtipotablero.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("EtiqTipoTablero")));
 BA.debugLineNum = 112;BA.debugLine="EtiqTipoTablero.Text=\"Tipo de tablero:\"";
Debug.ShouldStop(32768);
configurarsecuencia.mostCurrent._etiqtipotablero.runMethod(true,"setText",BA.ObjectToCharSequence("Tipo de tablero:"));
 BA.debugLineNum = 113;BA.debugLine="EtiqTipoTablero.TextColor=Colors.Black";
Debug.ShouldStop(65536);
configurarsecuencia.mostCurrent._etiqtipotablero.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 114;BA.debugLine="EtiqTipoTablero.TextSize=16";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._etiqtipotablero.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 115;BA.debugLine="EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL";
Debug.ShouldStop(262144);
configurarsecuencia.mostCurrent._etiqtipotablero.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL"));
 BA.debugLineNum = 116;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTipoTablero";
Debug.ShouldStop(524288);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._etiqtipotablero.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(configurarsecuencia._separacionhorizontal),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 118;BA.debugLine="ConfigTipoTablero.Initialize(\"ConfigTipoTablero\")";
Debug.ShouldStop(2097152);
configurarsecuencia.mostCurrent._configtipotablero.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigTipoTablero")));
 BA.debugLineNum = 119;BA.debugLine="ConfigTipoTablero.Text=Starter.DescripcionTablero";
Debug.ShouldStop(4194304);
configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"setText",BA.ObjectToCharSequence(configurarsecuencia.mostCurrent._starter._descripciontablero.getArrayElement(true,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").getField(true,"tipo"))));
 BA.debugLineNum = 120;BA.debugLine="ConfigTipoTablero.TextColor=Colors.Black";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 121;BA.debugLine="ConfigTipoTablero.TextSize=16";
Debug.ShouldStop(16777216);
configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 122;BA.debugLine="ConfigTipoTablero.Color=ColorDeFondo";
Debug.ShouldStop(33554432);
configurarsecuencia.mostCurrent._configtipotablero.runVoidMethod ("setColor",configurarsecuencia._colordefondo);
 BA.debugLineNum = 123;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTipoTable";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configtipotablero.getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia._separacionhorizontal,configurarsecuencia._separacioncasillas}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configdescripcion.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacionhorizontal,RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "--*",2, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 125;BA.debugLine="EtiqIndicarHora.Initialize(\"EtiqIndicarHora\")";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._etiqindicarhora.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("EtiqIndicarHora")));
 BA.debugLineNum = 126;BA.debugLine="EtiqIndicarHora.Text=\"Indicar hora actual:\"";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._etiqindicarhora.runMethod(true,"setText",BA.ObjectToCharSequence("Indicar hora actual:"));
 BA.debugLineNum = 127;BA.debugLine="EtiqIndicarHora.TextColor=Colors.Black";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent._etiqindicarhora.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 128;BA.debugLine="EtiqIndicarHora.TextSize=16";
Debug.ShouldStop(-2147483648);
configurarsecuencia.mostCurrent._etiqindicarhora.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 129;BA.debugLine="EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent._etiqindicarhora.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL"));
 BA.debugLineNum = 130;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqIndicarHora";
Debug.ShouldStop(2);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._etiqindicarhora.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(configurarsecuencia._separacionhorizontal),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 132;BA.debugLine="ConfigIndicarHora.Initialize(\"ConfigIndicarHora\")";
Debug.ShouldStop(8);
configurarsecuencia.mostCurrent._configindicarhora.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigIndicarHora")));
 BA.debugLineNum = 133;BA.debugLine="ConfigIndicarHora.Text=Starter.DescripcionMinuter";
Debug.ShouldStop(16);
configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"setText",BA.ObjectToCharSequence(configurarsecuencia.mostCurrent._starter._descripcionminutero.getArrayElement(true,configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").getField(true,"indicar_hora"))));
 BA.debugLineNum = 134;BA.debugLine="ConfigIndicarHora.TextColor=Colors.Black";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 135;BA.debugLine="ConfigIndicarHora.TextSize=16";
Debug.ShouldStop(64);
configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 136;BA.debugLine="ConfigIndicarHora.Color=ColorDeFondo";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent._configindicarhora.runVoidMethod ("setColor",configurarsecuencia._colordefondo);
 BA.debugLineNum = 137;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigIndicarHo";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configindicarhora.getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia._separacionhorizontal,configurarsecuencia._separacioncasillas}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configtipotablero.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacionhorizontal,RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "--*",2, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 139;BA.debugLine="EtiqTamIcono.Initialize(\"EtiqTamIcono\")";
Debug.ShouldStop(1024);
configurarsecuencia.mostCurrent._etiqtamicono.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("EtiqTamIcono")));
 BA.debugLineNum = 140;BA.debugLine="EtiqTamIcono.Text=\"Tamaño de los iconos:\"";
Debug.ShouldStop(2048);
configurarsecuencia.mostCurrent._etiqtamicono.runMethod(true,"setText",BA.ObjectToCharSequence("Tamaño de los iconos:"));
 BA.debugLineNum = 141;BA.debugLine="EtiqTamIcono.TextColor=Colors.Black";
Debug.ShouldStop(4096);
configurarsecuencia.mostCurrent._etiqtamicono.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 142;BA.debugLine="EtiqTamIcono.TextSize=16";
Debug.ShouldStop(8192);
configurarsecuencia.mostCurrent._etiqtamicono.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 143;BA.debugLine="EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL";
Debug.ShouldStop(16384);
configurarsecuencia.mostCurrent._etiqtamicono.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL"));
 BA.debugLineNum = 144;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTamIcono,Se";
Debug.ShouldStop(32768);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._etiqtamicono.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(configurarsecuencia._separacionhorizontal),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 146;BA.debugLine="ConfigTamIcono.Initialize(\"ConfigTamIcono\")";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._configtamicono.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigTamIcono")));
 BA.debugLineNum = 147;BA.debugLine="ConfigTamIcono.Value=Starter.Secuencia(Starter.Ma";
Debug.ShouldStop(262144);
configurarsecuencia.mostCurrent._configtamicono.runMethod(true,"setValue",configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(false,"tablero").getField(true,"tam_icono"));
 BA.debugLineNum = 148;BA.debugLine="ConfigTamIcono.Max=30";
Debug.ShouldStop(524288);
configurarsecuencia.mostCurrent._configtamicono.runMethod(true,"setMax",BA.numberCast(int.class, 30));
 BA.debugLineNum = 149;BA.debugLine="ConfigTamIcono.Color=ColorDeFondo";
Debug.ShouldStop(1048576);
configurarsecuencia.mostCurrent._configtamicono.runVoidMethod ("setColor",configurarsecuencia._colordefondo);
 BA.debugLineNum = 150;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTamIcono,";
Debug.ShouldStop(2097152);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configtamicono.getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia._separacionhorizontal,configurarsecuencia._separacioncasillas}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configindicarhora.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacionhorizontal,RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "--*",2, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 152;BA.debugLine="EtiqActividades.Initialize(\"EtiqActividades\")";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._etiqactividades.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("EtiqActividades")));
 BA.debugLineNum = 153;BA.debugLine="EtiqActividades.Text=\"Actividades\"";
Debug.ShouldStop(16777216);
configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"setText",BA.ObjectToCharSequence("Actividades"));
 BA.debugLineNum = 154;BA.debugLine="EtiqActividades.TextColor=Colors.Black";
Debug.ShouldStop(33554432);
configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 155;BA.debugLine="EtiqActividades.TextSize=24";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"setTextSize",BA.numberCast(float.class, 24));
 BA.debugLineNum = 156;BA.debugLine="EtiqActividades.Typeface=Typeface.DEFAULT_BOLD";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._etiqactividades.runMethod(false,"setTypeface",configurarsecuencia.mostCurrent.__c.getField(false,"Typeface").getField(false,"DEFAULT_BOLD"));
 BA.debugLineNum = 157;BA.debugLine="EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VER";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 158;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqActividades";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._etiqactividades.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configtamicono.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configtamicono.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 160;BA.debugLine="Dim InicioVertical As Int";
Debug.ShouldStop(-2147483648);
_iniciovertical = RemoteObject.createImmutable(0);Debug.locals.put("InicioVertical", _iniciovertical);
 BA.debugLineNum = 161;BA.debugLine="Dim FinVertical As Int";
Debug.ShouldStop(1);
_finvertical = RemoteObject.createImmutable(0);Debug.locals.put("FinVertical", _finvertical);
 BA.debugLineNum = 163;BA.debugLine="FinVertical=EtiqActividades.Top+EtiqActividades.H";
Debug.ShouldStop(4);
_finvertical = RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._etiqactividades.runMethod(true,"getHeight")}, "+",1, 1);Debug.locals.put("FinVertical", _finvertical);
 BA.debugLineNum = 164;BA.debugLine="InicioVertical=FinVertical+SeparacionCasillas";
Debug.ShouldStop(8);
_iniciovertical = RemoteObject.solve(new RemoteObject[] {_finvertical,configurarsecuencia._separacioncasillas}, "+",1, 1);Debug.locals.put("InicioVertical", _iniciovertical);
 BA.debugLineNum = 166;BA.debugLine="For Act=0 To Starter.Secuencia(Starter.MaxSecuenc";
Debug.ShouldStop(32);
{
final int step71 = 1;
final int limit71 = RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_act = 0 ;
for (;(step71 > 0 && _act <= limit71) || (step71 < 0 && _act >= limit71) ;_act = ((int)(0 + _act + step71))  ) {
Debug.locals.put("Act", _act);
 BA.debugLineNum = 168;BA.debugLine="If (Act>0) Then";
Debug.ShouldStop(128);
if ((RemoteObject.solveBoolean(">",RemoteObject.createImmutable(_act),BA.numberCast(double.class, 0)))) { 
 BA.debugLineNum = 169;BA.debugLine="InicioVertical=ConfigHoraInicioAct(Act-1).Top+C";
Debug.ShouldStop(256);
_iniciovertical = RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(_act),RemoteObject.createImmutable(1)}, "-",1, 1)).runMethod(true,"getTop"),configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(_act),RemoteObject.createImmutable(1)}, "-",1, 1)).runMethod(true,"getHeight"),RemoteObject.createImmutable(4),configurarsecuencia._separacioncasillas}, "++*",2, 1);Debug.locals.put("InicioVertical", _iniciovertical);
 };
 BA.debugLineNum = 172;BA.debugLine="ConfigPictogramaAct(Act).Initialize(\"ConfigPicto";
Debug.ShouldStop(2048);
configurarsecuencia.mostCurrent._configpictogramaact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigPictogramaAct")));
 BA.debugLineNum = 173;BA.debugLine="ConfigPictogramaAct(Act).Tag=Act";
Debug.ShouldStop(4096);
configurarsecuencia.mostCurrent._configpictogramaact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(false,"setTag",RemoteObject.createImmutable((_act)));
 BA.debugLineNum = 174;BA.debugLine="ConfigPictogramaAct(Act).SetBackgroundImage(Load";
Debug.ShouldStop(8192);
configurarsecuencia.mostCurrent._configpictogramaact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("SetBackgroundImageNew",(Object)((configurarsecuencia.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.concat(configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"Pictograma"),RemoteObject.createImmutable(".png")))).getObject())));
 BA.debugLineNum = 175;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogra";
Debug.ShouldStop(16384);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configpictogramaact.getArrayElement(false,BA.numberCast(int.class, _act)).getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._tamcasilla,configurarsecuencia._separacioncasillas}, "--",2, 1)),(Object)(_iniciovertical),(Object)(configurarsecuencia._tamcasilla),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 177;BA.debugLine="ConfigDescripcionAct(Act).Initialize(\"ConfigDesc";
Debug.ShouldStop(65536);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigDescripcionAct")));
 BA.debugLineNum = 178;BA.debugLine="ConfigDescripcionAct(Act).Tag=Act";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(false,"setTag",RemoteObject.createImmutable((_act)));
 BA.debugLineNum = 179;BA.debugLine="ConfigDescripcionAct(Act).Text=Starter.Actividad";
Debug.ShouldStop(262144);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethodAndSync(true,"setText",BA.ObjectToCharSequence(configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"Descripcion")));
 BA.debugLineNum = 180;BA.debugLine="ConfigDescripcionAct(Act).TextColor=Colors.Black";
Debug.ShouldStop(524288);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 181;BA.debugLine="ConfigDescripcionAct(Act).TextSize=16";
Debug.ShouldStop(1048576);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 182;BA.debugLine="ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity";
Debug.ShouldStop(2097152);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 183;BA.debugLine="ConfigDescripcionAct(Act).Color=Starter.Colores(";
Debug.ShouldStop(4194304);
configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("setColor",configurarsecuencia.mostCurrent._starter._colores.getArrayElement(true,BA.numberCast(int.class, _act)));
 BA.debugLineNum = 184;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripc";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(_iniciovertical),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configpictogramaact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getLeft"),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 186;BA.debugLine="ConfigHoraInicioAct(Act).Initialize(\"ConfigHoraI";
Debug.ShouldStop(33554432);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigHoraInicioAct")));
 BA.debugLineNum = 187;BA.debugLine="ConfigHoraInicioAct(Act).Tag=Act";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(false,"setTag",RemoteObject.createImmutable((_act)));
 BA.debugLineNum = 188;BA.debugLine="ConfigHoraInicioAct(Act).Text=\"Desde\"&CRLF&Numbe";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("Desde"),configurarsecuencia.mostCurrent.__c.getField(true,"CRLF"),configurarsecuencia.mostCurrent.__c.runMethod(true,"NumberFormat",(Object)(BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"hora_inicio"))),(Object)(BA.numberCast(int.class, 2)),(Object)(BA.numberCast(int.class, 0))),RemoteObject.createImmutable(":"),configurarsecuencia.mostCurrent.__c.runMethod(true,"NumberFormat",(Object)(BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"minuto_inicio"))),(Object)(BA.numberCast(int.class, 2)),(Object)(BA.numberCast(int.class, 0))))));
 BA.debugLineNum = 189;BA.debugLine="ConfigHoraInicioAct(Act).TextColor=Colors.Black";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 190;BA.debugLine="ConfigHoraInicioAct(Act).TextSize=16";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 191;BA.debugLine="ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 192;BA.debugLine="ConfigHoraInicioAct(Act).Color=ColorDeFondo";
Debug.ShouldStop(-2147483648);
configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("setColor",configurarsecuencia._colordefondo);
 BA.debugLineNum = 193;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraInic";
Debug.ShouldStop(1);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._confighorainicioact.getArrayElement(false,BA.numberCast(int.class, _act)).getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacioncasillas}, "-",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 195;BA.debugLine="ConfigHoraFinalAct(Act).Initialize(\"ConfigHoraFi";
Debug.ShouldStop(4);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ConfigHoraFinalAct")));
 BA.debugLineNum = 196;BA.debugLine="ConfigHoraFinalAct(Act).Tag=Act";
Debug.ShouldStop(8);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(false,"setTag",RemoteObject.createImmutable((_act)));
 BA.debugLineNum = 197;BA.debugLine="ConfigHoraFinalAct(Act).Text=\"Hasta\"&CRLF&Number";
Debug.ShouldStop(16);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("Hasta"),configurarsecuencia.mostCurrent.__c.getField(true,"CRLF"),configurarsecuencia.mostCurrent.__c.runMethod(true,"NumberFormat",(Object)(BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"hora_fin"))),(Object)(BA.numberCast(int.class, 2)),(Object)(BA.numberCast(int.class, 0))),RemoteObject.createImmutable(":"),configurarsecuencia.mostCurrent.__c.runMethod(true,"NumberFormat",(Object)(BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,BA.numberCast(int.class, _act)).getField(true,"minuto_fin"))),(Object)(BA.numberCast(int.class, 2)),(Object)(BA.numberCast(int.class, 0))))));
 BA.debugLineNum = 198;BA.debugLine="ConfigHoraFinalAct(Act).TextColor=Colors.Black";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 199;BA.debugLine="ConfigHoraFinalAct(Act).TextSize=16";
Debug.ShouldStop(64);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 200;BA.debugLine="ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.C";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 201;BA.debugLine="ConfigHoraFinalAct(Act).Color=ColorDeFondo";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runVoidMethod ("setColor",configurarsecuencia._colordefondo);
 BA.debugLineNum = 202;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraFina";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).getObject())),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacioncasillas,RemoteObject.createImmutable(2)}, "+/",1, 0))),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getTop"),configurarsecuencia.mostCurrent._configdescripcionact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacioncasillas}, "-",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 204;BA.debugLine="FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHo";
Debug.ShouldStop(2048);
_finvertical = RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getTop"),configurarsecuencia.mostCurrent._confighorafinalact.getArrayElement(false,BA.numberCast(int.class, _act)).runMethod(true,"getHeight")}, "+",1, 1);Debug.locals.put("FinVertical", _finvertical);
 }
}Debug.locals.put("Act", _act);
;
 BA.debugLineNum = 208;BA.debugLine="BotonAnadirActividad.Initialize(\"BotonAnadirActiv";
Debug.ShouldStop(32768);
configurarsecuencia.mostCurrent._botonanadiractividad.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonAnadirActividad")));
 BA.debugLineNum = 209;BA.debugLine="BotonAnadirActividad.Text=\"Añadir Actividad\"";
Debug.ShouldStop(65536);
configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"setText",BA.ObjectToCharSequence("Añadir Actividad"));
 BA.debugLineNum = 210;BA.debugLine="BotonAnadirActividad.TextSize=16";
Debug.ShouldStop(131072);
configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 211;BA.debugLine="BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTE";
Debug.ShouldStop(262144);
configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 212;BA.debugLine="BotonAnadirActividad.TextColor=Colors.Black";
Debug.ShouldStop(524288);
configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 213;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
Debug.ShouldStop(1048576);
if (RemoteObject.solveBoolean("=",configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),BA.numberCast(double.class, configurarsecuencia.mostCurrent._starter._maxactividades))) { 
 BA.debugLineNum = 214;BA.debugLine="BotonAnadirActividad.Enabled=False";
Debug.ShouldStop(2097152);
configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"setEnabled",configurarsecuencia.mostCurrent.__c.getField(true,"False"));
 };
 BA.debugLineNum = 216;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAnadirActi";
Debug.ShouldStop(8388608);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._botonanadiractividad.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {_finvertical,configurarsecuencia._separacioncasillas}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),configurarsecuencia.mostCurrent.activityBA),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 218;BA.debugLine="BotonAceptar.Initialize(\"BotonAceptar\")";
Debug.ShouldStop(33554432);
configurarsecuencia.mostCurrent._botonaceptar.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonAceptar")));
 BA.debugLineNum = 219;BA.debugLine="BotonAceptar.Text=\"Aceptar\"";
Debug.ShouldStop(67108864);
configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"setText",BA.ObjectToCharSequence("Aceptar"));
 BA.debugLineNum = 220;BA.debugLine="BotonAceptar.TextSize=16";
Debug.ShouldStop(134217728);
configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 221;BA.debugLine="BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTIC";
Debug.ShouldStop(268435456);
configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 222;BA.debugLine="BotonAceptar.TextColor=Colors.Black";
Debug.ShouldStop(536870912);
configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 223;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAceptar,Se";
Debug.ShouldStop(1073741824);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._botonaceptar.getObject())),(Object)(configurarsecuencia._separacioncasillas),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._botonanadiractividad.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 225;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
Debug.ShouldStop(1);
if (RemoteObject.solveBoolean("=",configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).getField(true,"num_actividades"),BA.numberCast(double.class, 0))) { 
 BA.debugLineNum = 226;BA.debugLine="BotonAceptar.Enabled=False";
Debug.ShouldStop(2);
configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"setEnabled",configurarsecuencia.mostCurrent.__c.getField(true,"False"));
 };
 BA.debugLineNum = 229;BA.debugLine="BotonCancelar.Initialize(\"BotonCancelar\")";
Debug.ShouldStop(16);
configurarsecuencia.mostCurrent._botoncancelar.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("BotonCancelar")));
 BA.debugLineNum = 230;BA.debugLine="BotonCancelar.Text=\"Cancelar\"";
Debug.ShouldStop(32);
configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"setText",BA.ObjectToCharSequence("Cancelar"));
 BA.debugLineNum = 231;BA.debugLine="BotonCancelar.TextSize=16";
Debug.ShouldStop(64);
configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"setTextSize",BA.numberCast(float.class, 16));
 BA.debugLineNum = 232;BA.debugLine="BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTI";
Debug.ShouldStop(128);
configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"setGravity",configurarsecuencia.mostCurrent.__c.getField(false,"Bit").runMethod(true,"Or",(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_VERTICAL")),(Object)(configurarsecuencia.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER_HORIZONTAL"))));
 BA.debugLineNum = 233;BA.debugLine="BotonCancelar.TextColor=Colors.Black";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"setTextColor",configurarsecuencia.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 234;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonCancelar,5";
Debug.ShouldStop(512);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((configurarsecuencia.mostCurrent._botoncancelar.getObject())),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),configurarsecuencia._separacioncasillas}, "+",1, 1)),(Object)(configurarsecuencia.mostCurrent._botonaceptar.runMethod(true,"getTop")),(Object)(RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),configurarsecuencia.mostCurrent.activityBA),RemoteObject.createImmutable(2),configurarsecuencia._separacioncasillas}, "-*",1, 1)),(Object)(configurarsecuencia._tamcasilla));
 BA.debugLineNum = 236;BA.debugLine="ParametrosSecuencia.Panel.Height=BotonCancelar.To";
Debug.ShouldStop(2048);
configurarsecuencia.mostCurrent._parametrossecuencia.runMethod(false,"getPanel").runMethod(true,"setHeight",RemoteObject.solve(new RemoteObject[] {configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"getTop"),configurarsecuencia.mostCurrent._botoncancelar.runMethod(true,"getHeight"),configurarsecuencia._separacioncasillas}, "++",2, 1));
 BA.debugLineNum = 238;BA.debugLine="Inicializando=False";
Debug.ShouldStop(8192);
configurarsecuencia._inicializando = configurarsecuencia.mostCurrent.__c.getField(true,"False");
 BA.debugLineNum = 240;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
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
 //BA.debugLineNum = 16;BA.debugLine="Dim SeparacionHorizontal=25%X As Int  'Separación";
configurarsecuencia._separacionhorizontal = configurarsecuencia.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 25)),configurarsecuencia.mostCurrent.activityBA);
 //BA.debugLineNum = 17;BA.debugLine="Dim TamCasilla=60dip As Int 'Tamaño vertical de l";
configurarsecuencia._tamcasilla = configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 60)));
 //BA.debugLineNum = 18;BA.debugLine="Dim SeparacionCasillas=5dip As Int 'Separación ve";
configurarsecuencia._separacioncasillas = configurarsecuencia.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)));
 //BA.debugLineNum = 20;BA.debugLine="Dim ColorDeFondo=0xFFF0FFFF As Int";
configurarsecuencia._colordefondo = BA.numberCast(int.class, 0xfff0ffff);
 //BA.debugLineNum = 22;BA.debugLine="Private ParametrosSecuencia As ScrollView";
configurarsecuencia.mostCurrent._parametrossecuencia = RemoteObject.createNew ("anywheresoftware.b4a.objects.ScrollViewWrapper");
 //BA.debugLineNum = 24;BA.debugLine="Dim EtiquetaInicial As Label";
configurarsecuencia.mostCurrent._etiquetainicial = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 25;BA.debugLine="Dim ConfigDescripcion As EditText";
configurarsecuencia.mostCurrent._configdescripcion = RemoteObject.createNew ("anywheresoftware.b4a.objects.EditTextWrapper");
 //BA.debugLineNum = 26;BA.debugLine="Dim ConfigPictograma As Button";
configurarsecuencia.mostCurrent._configpictograma = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 27;BA.debugLine="Dim EtiqTipoTablero As Label";
configurarsecuencia.mostCurrent._etiqtipotablero = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 28;BA.debugLine="Dim ConfigTipoTablero As Button";
configurarsecuencia.mostCurrent._configtipotablero = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 29;BA.debugLine="Dim EtiqIndicarHora As Label";
configurarsecuencia.mostCurrent._etiqindicarhora = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 30;BA.debugLine="Dim ConfigIndicarHora As Button";
configurarsecuencia.mostCurrent._configindicarhora = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 31;BA.debugLine="Dim EtiqTamIcono As Label";
configurarsecuencia.mostCurrent._etiqtamicono = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 32;BA.debugLine="Dim ConfigTamIcono As SeekBar";
configurarsecuencia.mostCurrent._configtamicono = RemoteObject.createNew ("anywheresoftware.b4a.objects.SeekBarWrapper");
 //BA.debugLineNum = 34;BA.debugLine="Dim EtiqActividades As Label";
configurarsecuencia.mostCurrent._etiqactividades = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 36;BA.debugLine="Dim ConfigDescripcionAct(Starter.MaxActividades)";
configurarsecuencia.mostCurrent._configdescripcionact = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.EditTextWrapper", new int[] {configurarsecuencia.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 37;BA.debugLine="Dim ConfigHoraInicioAct(Starter.MaxActividades) A";
configurarsecuencia.mostCurrent._confighorainicioact = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {configurarsecuencia.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 38;BA.debugLine="Dim ConfigHoraFinalAct(Starter.MaxActividades) As";
configurarsecuencia.mostCurrent._confighorafinalact = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {configurarsecuencia.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 39;BA.debugLine="Dim ConfigPictogramaAct(Starter.MaxActividades) A";
configurarsecuencia.mostCurrent._configpictogramaact = RemoteObject.createNewArray ("anywheresoftware.b4a.objects.ButtonWrapper", new int[] {configurarsecuencia.mostCurrent._starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 41;BA.debugLine="Dim BotonAnadirActividad As Button";
configurarsecuencia.mostCurrent._botonanadiractividad = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 42;BA.debugLine="Dim BotonAceptar As Button";
configurarsecuencia.mostCurrent._botonaceptar = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 43;BA.debugLine="Dim BotonCancelar As Button";
configurarsecuencia.mostCurrent._botoncancelar = RemoteObject.createNew ("anywheresoftware.b4a.objects.ButtonWrapper");
 //BA.debugLineNum = 45;BA.debugLine="Dim Inicializando As Boolean";
configurarsecuencia._inicializando = RemoteObject.createImmutable(false);
 //BA.debugLineNum = 49;BA.debugLine="Dim ListaPictogramas As ListView";
configurarsecuencia.mostCurrent._listapictogramas = RemoteObject.createNew ("anywheresoftware.b4a.objects.ListViewWrapper");
 //BA.debugLineNum = 51;BA.debugLine="Dim PictogramaEditado As Int";
configurarsecuencia._pictogramaeditado = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static void  _inicializar_lista_pictogramas() throws Exception{
ResumableSub_Inicializar_Lista_Pictogramas rsub = new ResumableSub_Inicializar_Lista_Pictogramas(null);
rsub.resume(null, null);
}
public static class ResumableSub_Inicializar_Lista_Pictogramas extends BA.ResumableSub {
public ResumableSub_Inicializar_Lista_Pictogramas(javi.prieto.pictorario.configurarsecuencia parent) {
this.parent = parent;
}
java.util.LinkedHashMap<String, Object> rsLocals = new java.util.LinkedHashMap<String, Object>();
javi.prieto.pictorario.configurarsecuencia parent;
RemoteObject _filelist = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
RemoteObject _file1 = RemoteObject.createImmutable("");
RemoteObject _file2 = RemoteObject.createImmutable("");
RemoteObject _n = RemoteObject.createImmutable(0);
RemoteObject _bitmap1 = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper");
int step17;
int limit17;

@Override
public void resume(BA ba, RemoteObject result) throws Exception{
try {
		Debug.PushSubsStack("Inicializar_Lista_Pictogramas (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,371);
if (RapidSub.canDelegate("inicializar_lista_pictogramas")) return ;
Debug.locals = rsLocals;Debug.currentSubFrame.locals = rsLocals;

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 BA.debugLineNum = 372;BA.debugLine="Dim fileList As List";
Debug.ShouldStop(524288);
_filelist = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");Debug.locals.put("fileList", _filelist);
 BA.debugLineNum = 373;BA.debugLine="Dim file1 As String";
Debug.ShouldStop(1048576);
_file1 = RemoteObject.createImmutable("");Debug.locals.put("file1", _file1);
 BA.debugLineNum = 374;BA.debugLine="Dim file2 As String";
Debug.ShouldStop(2097152);
_file2 = RemoteObject.createImmutable("");Debug.locals.put("file2", _file2);
 BA.debugLineNum = 375;BA.debugLine="Dim n As Int";
Debug.ShouldStop(4194304);
_n = RemoteObject.createImmutable(0);Debug.locals.put("n", _n);
 BA.debugLineNum = 377;BA.debugLine="If ListaPictogramas.IsInitialized=False Then";
Debug.ShouldStop(16777216);
if (true) break;

case 1:
//if
this.state = 12;
if (RemoteObject.solveBoolean("=",parent.mostCurrent._listapictogramas.runMethod(true,"IsInitialized"),parent.mostCurrent.__c.getField(true,"False"))) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 BA.debugLineNum = 379;BA.debugLine="ProgressDialogShow(\"Inicializando lista de picto";
Debug.ShouldStop(67108864);
parent.mostCurrent.__c.runVoidMethod ("ProgressDialogShow",configurarsecuencia.mostCurrent.activityBA,(Object)(BA.ObjectToCharSequence(RemoteObject.createImmutable("Inicializando lista de pictogramas"))));
 BA.debugLineNum = 381;BA.debugLine="ListaPictogramas.Initialize(\"ListaPictogramas\")";
Debug.ShouldStop(268435456);
parent.mostCurrent._listapictogramas.runVoidMethod ("Initialize",configurarsecuencia.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("ListaPictogramas")));
 BA.debugLineNum = 382;BA.debugLine="ListaPictogramas.Color=Colors.LightGray";
Debug.ShouldStop(536870912);
parent.mostCurrent._listapictogramas.runVoidMethod ("setColor",parent.mostCurrent.__c.getField(false,"Colors").getField(true,"LightGray"));
 BA.debugLineNum = 383;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.Label.TextCol";
Debug.ShouldStop(1073741824);
parent.mostCurrent._listapictogramas.runMethod(false,"getTwoLinesAndBitmap").getField(false,"Label").runMethod(true,"setTextColor",parent.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 BA.debugLineNum = 384;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.SecondLabel.V";
Debug.ShouldStop(-2147483648);
parent.mostCurrent._listapictogramas.runMethod(false,"getTwoLinesAndBitmap").getField(false,"SecondLabel").runMethod(true,"setVisible",parent.mostCurrent.__c.getField(true,"False"));
 BA.debugLineNum = 385;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.ImageView.Wid";
Debug.ShouldStop(1);
parent.mostCurrent._listapictogramas.runMethod(false,"getTwoLinesAndBitmap").getField(false,"ImageView").runMethod(true,"setWidth",parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 50))));
 BA.debugLineNum = 386;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.ImageView.Hei";
Debug.ShouldStop(2);
parent.mostCurrent._listapictogramas.runMethod(false,"getTwoLinesAndBitmap").getField(false,"ImageView").runMethod(true,"setHeight",parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 50))));
 BA.debugLineNum = 387;BA.debugLine="ListaPictogramas.Padding=Array As Int(5dip,5dip,";
Debug.ShouldStop(4);
parent.mostCurrent._listapictogramas.runMethod(false,"setPadding",RemoteObject.createNewArray("int",new int[] {4},new Object[] {parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5))),parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5))),parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5))),parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))}));
 BA.debugLineNum = 388;BA.debugLine="ListaPictogramas.FastScrollEnabled = True";
Debug.ShouldStop(8);
parent.mostCurrent._listapictogramas.runMethod(true,"setFastScrollEnabled",parent.mostCurrent.__c.getField(true,"True"));
 BA.debugLineNum = 390;BA.debugLine="fileList = File.ListFiles(File.DirAssets)";
Debug.ShouldStop(32);
_filelist = parent.mostCurrent.__c.getField(false,"File").runMethod(false,"ListFiles",(Object)(parent.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")));Debug.locals.put("fileList", _filelist);
 BA.debugLineNum = 391;BA.debugLine="fileList.Sort(True)";
Debug.ShouldStop(64);
_filelist.runVoidMethod ("Sort",(Object)(parent.mostCurrent.__c.getField(true,"True")));
 BA.debugLineNum = 393;BA.debugLine="For n = 0 To fileList.Size-1";
Debug.ShouldStop(256);
if (true) break;

case 4:
//for
this.state = 11;
step17 = 1;
limit17 = RemoteObject.solve(new RemoteObject[] {_filelist.runMethod(true,"getSize"),RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_n = BA.numberCast(int.class, 0) ;
Debug.locals.put("n", _n);
this.state = 13;
if (true) break;

case 13:
//C
this.state = 11;
if ((step17 > 0 && _n.<Integer>get().intValue() <= limit17) || (step17 < 0 && _n.<Integer>get().intValue() >= limit17)) this.state = 6;
if (true) break;

case 14:
//C
this.state = 13;
_n = RemoteObject.createImmutable((int)(0 + _n.<Integer>get().intValue() + step17)) ;
Debug.locals.put("n", _n);
if (true) break;

case 6:
//C
this.state = 7;
 BA.debugLineNum = 394;BA.debugLine="Sleep(0)";
Debug.ShouldStop(512);
parent.mostCurrent.__c.runVoidMethod ("Sleep",configurarsecuencia.mostCurrent.activityBA,anywheresoftware.b4a.pc.PCResumableSub.createDebugResumeSub(this),BA.numberCast(int.class, 0));
this.state = 15;
return;
case 15:
//C
this.state = 7;
;
 BA.debugLineNum = 395;BA.debugLine="file1 = fileList.Get(n)";
Debug.ShouldStop(1024);
_file1 = BA.ObjectToString(_filelist.runMethod(false,"Get",(Object)(_n)));Debug.locals.put("file1", _file1);
 BA.debugLineNum = 396;BA.debugLine="If file1.Contains(\".png\") Then";
Debug.ShouldStop(2048);
if (true) break;

case 7:
//if
this.state = 10;
if (_file1.runMethod(true,"contains",(Object)(RemoteObject.createImmutable(".png"))).<Boolean>get().booleanValue()) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 BA.debugLineNum = 397;BA.debugLine="file2=file1.Replace(\".png\",\"\")";
Debug.ShouldStop(4096);
_file2 = _file1.runMethod(true,"replace",(Object)(BA.ObjectToString(".png")),(Object)(RemoteObject.createImmutable("")));Debug.locals.put("file2", _file2);
 BA.debugLineNum = 398;BA.debugLine="Dim Bitmap1 As Bitmap";
Debug.ShouldStop(8192);
_bitmap1 = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper");Debug.locals.put("Bitmap1", _bitmap1);
 BA.debugLineNum = 399;BA.debugLine="Bitmap1.InitializeSample(File.DirAssets, file1";
Debug.ShouldStop(16384);
_bitmap1.runVoidMethod ("InitializeSample",(Object)(parent.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(_file1),(Object)(parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 50)))),(Object)(parent.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 50)))));
 BA.debugLineNum = 400;BA.debugLine="ListaPictogramas.AddTwoLinesAndBitmap(file2,\"\"";
Debug.ShouldStop(32768);
parent.mostCurrent._listapictogramas.runVoidMethod ("AddTwoLinesAndBitmap",(Object)(BA.ObjectToCharSequence(_file2)),(Object)(BA.ObjectToCharSequence("")),(Object)((_bitmap1.getObject())));
 if (true) break;

case 10:
//C
this.state = 14;
;
 if (true) break;
if (true) break;

case 11:
//C
this.state = 12;
Debug.locals.put("n", _n);
;
 BA.debugLineNum = 404;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(524288);
parent.mostCurrent.__c.runVoidMethod ("ProgressDialogHide");
 if (true) break;

case 12:
//C
this.state = -1;
;
 BA.debugLineNum = 408;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
if (true) break;

            }
        }
    }
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}
public static RemoteObject  _listapictogramas_itemclick(RemoteObject _position,RemoteObject _value) throws Exception{
try {
		Debug.PushSubsStack("ListaPictogramas_ItemClick (configurarsecuencia) ","configurarsecuencia",1,configurarsecuencia.mostCurrent.activityBA,configurarsecuencia.mostCurrent,358);
if (RapidSub.canDelegate("listapictogramas_itemclick")) return javi.prieto.pictorario.configurarsecuencia.remoteMe.runUserSub(false, "configurarsecuencia","listapictogramas_itemclick", _position, _value);
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 358;BA.debugLine="Sub ListaPictogramas_ItemClick (Position As Int, V";
Debug.ShouldStop(32);
 BA.debugLineNum = 359;BA.debugLine="ListaPictogramas.RemoveView";
Debug.ShouldStop(64);
configurarsecuencia.mostCurrent._listapictogramas.runVoidMethod ("RemoveView");
 BA.debugLineNum = 360;BA.debugLine="If PictogramaEditado==-1 Then";
Debug.ShouldStop(128);
if (RemoteObject.solveBoolean("=",configurarsecuencia._pictogramaeditado,BA.numberCast(double.class, -(double) (0 + 1)))) { 
 BA.debugLineNum = 361;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
Debug.ShouldStop(256);
configurarsecuencia.mostCurrent._starter._secuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias).setField ("pictograma",BA.ObjectToString(_value));
 }else {
 BA.debugLineNum = 364;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
Debug.ShouldStop(2048);
configurarsecuencia.mostCurrent._starter._actividadsecuencia.getArrayElement(false,configurarsecuencia.mostCurrent._starter._maxsecuencias,configurarsecuencia._pictogramaeditado).setField ("Pictograma",BA.ObjectToString(_value));
 };
 BA.debugLineNum = 367;BA.debugLine="Activity.Invalidate";
Debug.ShouldStop(16384);
configurarsecuencia.mostCurrent._activity.runVoidMethod ("Invalidate");
 BA.debugLineNum = 368;BA.debugLine="DibujarConfigurarSecuencia";
Debug.ShouldStop(32768);
_dibujarconfigurarsecuencia();
 BA.debugLineNum = 369;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
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
}