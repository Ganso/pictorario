package javi.prieto.pictorario;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class starter_subs_0 {


public static RemoteObject  _application_error(RemoteObject _error,RemoteObject _stacktrace) throws Exception{
try {
		Debug.PushSubsStack("Application_Error (starter) ","starter",2,starter.processBA,starter.mostCurrent,187);
if (RapidSub.canDelegate("application_error")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","application_error", _error, _stacktrace);
Debug.locals.put("Error", _error);
Debug.locals.put("StackTrace", _stacktrace);
 BA.debugLineNum = 187;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 188;BA.debugLine="Return True";
Debug.ShouldStop(134217728);
if (true) return starter.mostCurrent.__c.getField(true,"True");
 BA.debugLineNum = 189;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return RemoteObject.createImmutable(false);
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _cargar_configuracion() throws Exception{
try {
		Debug.PushSubsStack("Cargar_Configuracion (starter) ","starter",2,starter.processBA,starter.mostCurrent,81);
if (RapidSub.canDelegate("cargar_configuracion")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","cargar_configuracion");
int _i = 0;
int _j = 0;
 BA.debugLineNum = 81;BA.debugLine="Sub Cargar_Configuracion";
Debug.ShouldStop(65536);
 BA.debugLineNum = 82;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
Debug.ShouldStop(131072);
starter._numsecuencias = BA.numberCast(int.class, starter._kvs.runMethod(false,"_getdefault",(Object)(BA.ObjectToString("NumSecuencias")),(Object)(RemoteObject.createImmutable((0)))));
 BA.debugLineNum = 83;BA.debugLine="If NumSecuencias==0 Then";
Debug.ShouldStop(262144);
if (RemoteObject.solveBoolean("=",starter._numsecuencias,BA.numberCast(double.class, 0))) { 
 BA.debugLineNum = 84;BA.debugLine="Inicializar_Con_Ejemplo";
Debug.ShouldStop(524288);
_inicializar_con_ejemplo();
 BA.debugLineNum = 85;BA.debugLine="Guardar_Configuracion";
Debug.ShouldStop(1048576);
_guardar_configuracion();
 }else {
 BA.debugLineNum = 87;BA.debugLine="For i=0 To NumSecuencias-1";
Debug.ShouldStop(4194304);
{
final int step6 = 1;
final int limit6 = RemoteObject.solve(new RemoteObject[] {starter._numsecuencias,RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_i = 0 ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 88;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
Debug.ShouldStop(8388608);
starter._secuencia.setArrayElement ((starter._kvs.runMethod(false,"_get",(Object)(RemoteObject.concat(RemoteObject.createImmutable("Secuencia."),RemoteObject.createImmutable(_i))))),BA.numberCast(int.class, _i));
 BA.debugLineNum = 89;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
Debug.ShouldStop(16777216);
{
final int step8 = 1;
final int limit8 = starter._secuencia.getArrayElement(false,BA.numberCast(int.class, _i)).getField(true,"num_actividades").<Integer>get().intValue();
_j = 0 ;
for (;(step8 > 0 && _j <= limit8) || (step8 < 0 && _j >= limit8) ;_j = ((int)(0 + _j + step8))  ) {
Debug.locals.put("j", _j);
 BA.debugLineNum = 90;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
Debug.ShouldStop(33554432);
starter._actividadsecuencia.setArrayElement ((starter._kvs.runMethod(false,"_get",(Object)(RemoteObject.concat(RemoteObject.createImmutable("ActividadSecuencia."),RemoteObject.createImmutable(_i),RemoteObject.createImmutable("."),RemoteObject.createImmutable(_j))))),BA.numberCast(int.class, _i),BA.numberCast(int.class, _j));
 }
}Debug.locals.put("j", _j);
;
 }
}Debug.locals.put("i", _i);
;
 };
 BA.debugLineNum = 94;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _copiarsecuencias(RemoteObject _seq1,RemoteObject _seq2) throws Exception{
try {
		Debug.PushSubsStack("CopiarSecuencias (starter) ","starter",2,starter.processBA,starter.mostCurrent,195);
if (RapidSub.canDelegate("copiarsecuencias")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","copiarsecuencias", _seq1, _seq2);
int _i = 0;
Debug.locals.put("Seq1", _seq1);
Debug.locals.put("Seq2", _seq2);
 BA.debugLineNum = 195;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
Debug.ShouldStop(4);
 BA.debugLineNum = 197;BA.debugLine="Secuencia(Seq2).Initialize";
Debug.ShouldStop(16);
starter._secuencia.getArrayElement(false,_seq2).runVoidMethod ("Initialize");
 BA.debugLineNum = 198;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
Debug.ShouldStop(32);
starter._secuencia.getArrayElement(false,_seq2).setField ("Descripcion",starter._secuencia.getArrayElement(false,_seq1).getField(true,"Descripcion"));
 BA.debugLineNum = 199;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
Debug.ShouldStop(64);
starter._secuencia.getArrayElement(false,_seq2).setField ("num_actividades",starter._secuencia.getArrayElement(false,_seq1).getField(true,"num_actividades"));
 BA.debugLineNum = 200;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
Debug.ShouldStop(128);
starter._secuencia.getArrayElement(false,_seq2).setField ("pictograma",starter._secuencia.getArrayElement(false,_seq1).getField(true,"pictograma"));
 BA.debugLineNum = 201;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
Debug.ShouldStop(256);
starter._secuencia.getArrayElement(false,_seq2).getField(false,"tablero").runVoidMethod ("Initialize");
 BA.debugLineNum = 202;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
Debug.ShouldStop(512);
starter._secuencia.getArrayElement(false,_seq2).getField(false,"tablero").setField ("tipo",starter._secuencia.getArrayElement(false,_seq1).getField(false,"tablero").getField(true,"tipo"));
 BA.debugLineNum = 203;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
Debug.ShouldStop(1024);
starter._secuencia.getArrayElement(false,_seq2).getField(false,"tablero").setField ("tam_icono",starter._secuencia.getArrayElement(false,_seq1).getField(false,"tablero").getField(true,"tam_icono"));
 BA.debugLineNum = 204;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
Debug.ShouldStop(2048);
starter._secuencia.getArrayElement(false,_seq2).getField(false,"tablero").setField ("indicar_hora",starter._secuencia.getArrayElement(false,_seq1).getField(false,"tablero").getField(true,"indicar_hora"));
 BA.debugLineNum = 205;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
Debug.ShouldStop(4096);
{
final int step9 = 1;
final int limit9 = RemoteObject.solve(new RemoteObject[] {starter._secuencia.getArrayElement(false,_seq1).getField(true,"num_actividades"),RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_i = 0 ;
for (;(step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9) ;_i = ((int)(0 + _i + step9))  ) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 206;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
Debug.ShouldStop(8192);
starter._actividadsecuencia.setArrayElement (starter._actividadsecuencia.getArrayElement(false,_seq1,BA.numberCast(int.class, _i)),_seq2,BA.numberCast(int.class, _i));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 208;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _guardar_configuracion() throws Exception{
try {
		Debug.PushSubsStack("Guardar_Configuracion (starter) ","starter",2,starter.processBA,starter.mostCurrent,71);
if (RapidSub.canDelegate("guardar_configuracion")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","guardar_configuracion");
int _i = 0;
int _j = 0;
 BA.debugLineNum = 71;BA.debugLine="Sub Guardar_Configuracion";
Debug.ShouldStop(64);
 BA.debugLineNum = 72;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
Debug.ShouldStop(128);
starter._kvs.runVoidMethod ("_put",(Object)(BA.ObjectToString("NumSecuencias")),(Object)((starter._numsecuencias)));
 BA.debugLineNum = 73;BA.debugLine="For i=0 To NumSecuencias-1";
Debug.ShouldStop(256);
{
final int step2 = 1;
final int limit2 = RemoteObject.solve(new RemoteObject[] {starter._numsecuencias,RemoteObject.createImmutable(1)}, "-",1, 1).<Integer>get().intValue();
_i = 0 ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 74;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
Debug.ShouldStop(512);
starter._kvs.runVoidMethod ("_put",(Object)(RemoteObject.concat(RemoteObject.createImmutable("Secuencia."),RemoteObject.createImmutable(_i))),(Object)((starter._secuencia.getArrayElement(false,BA.numberCast(int.class, _i)))));
 BA.debugLineNum = 75;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
Debug.ShouldStop(1024);
{
final int step4 = 1;
final int limit4 = starter._secuencia.getArrayElement(false,BA.numberCast(int.class, _i)).getField(true,"num_actividades").<Integer>get().intValue();
_j = 0 ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
Debug.locals.put("j", _j);
 BA.debugLineNum = 76;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
Debug.ShouldStop(2048);
starter._kvs.runVoidMethod ("_put",(Object)(RemoteObject.concat(RemoteObject.createImmutable("ActividadSecuencia."),RemoteObject.createImmutable(_i),RemoteObject.createImmutable("."),RemoteObject.createImmutable(_j))),(Object)((starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, _i),BA.numberCast(int.class, _j)))));
 }
}Debug.locals.put("j", _j);
;
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 79;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _inicializar_con_ejemplo() throws Exception{
try {
		Debug.PushSubsStack("Inicializar_Con_Ejemplo (starter) ","starter",2,starter.processBA,starter.mostCurrent,96);
if (RapidSub.canDelegate("inicializar_con_ejemplo")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","inicializar_con_ejemplo");
 BA.debugLineNum = 96;BA.debugLine="Sub Inicializar_Con_Ejemplo";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 98;BA.debugLine="NumSecuencias=1";
Debug.ShouldStop(2);
starter._numsecuencias = BA.numberCast(int.class, 1);
 BA.debugLineNum = 102;BA.debugLine="Secuencia(0).Initialize";
Debug.ShouldStop(32);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).runVoidMethod ("Initialize");
 BA.debugLineNum = 104;BA.debugLine="Secuencia(0).num_actividades=9";
Debug.ShouldStop(128);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).setField ("num_actividades",BA.numberCast(int.class, 9));
 BA.debugLineNum = 106;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
Debug.ShouldStop(512);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("hora_inicio",BA.numberCast(int.class, 8));
 BA.debugLineNum = 107;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
Debug.ShouldStop(1024);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 108;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
Debug.ShouldStop(2048);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("hora_fin",BA.numberCast(int.class, 8));
 BA.debugLineNum = 109;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
Debug.ShouldStop(4096);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("minuto_fin",BA.numberCast(int.class, 15));
 BA.debugLineNum = 110;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
Debug.ShouldStop(8192);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("Pictograma",BA.ObjectToString("despertar_1"));
 BA.debugLineNum = 111;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
Debug.ShouldStop(16384);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 0)).setField ("Descripcion",BA.ObjectToString("Despertarse"));
 BA.debugLineNum = 113;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
Debug.ShouldStop(65536);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("hora_inicio",BA.numberCast(int.class, 8));
 BA.debugLineNum = 114;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
Debug.ShouldStop(131072);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("minuto_inicio",BA.numberCast(int.class, 15));
 BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
Debug.ShouldStop(262144);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("hora_fin",BA.numberCast(int.class, 8));
 BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
Debug.ShouldStop(524288);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("minuto_fin",BA.numberCast(int.class, 30));
 BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
Debug.ShouldStop(1048576);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("Pictograma",BA.ObjectToString("vestirse"));
 BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
Debug.ShouldStop(2097152);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 1)).setField ("Descripcion",BA.ObjectToString("Vestirse"));
 BA.debugLineNum = 120;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
Debug.ShouldStop(8388608);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("hora_inicio",BA.numberCast(int.class, 8));
 BA.debugLineNum = 121;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
Debug.ShouldStop(16777216);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("minuto_inicio",BA.numberCast(int.class, 30));
 BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
Debug.ShouldStop(33554432);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("hora_fin",BA.numberCast(int.class, 9));
 BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
Debug.ShouldStop(67108864);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("minuto_fin",BA.numberCast(int.class, 0));
 BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
Debug.ShouldStop(134217728);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("Pictograma",BA.ObjectToString("desayunar"));
 BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
Debug.ShouldStop(268435456);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 2)).setField ("Descripcion",BA.ObjectToString("Desayunar"));
 BA.debugLineNum = 127;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
Debug.ShouldStop(1073741824);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("hora_inicio",BA.numberCast(int.class, 9));
 BA.debugLineNum = 128;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
Debug.ShouldStop(-2147483648);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 129;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
Debug.ShouldStop(1);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("hora_fin",BA.numberCast(int.class, 14));
 BA.debugLineNum = 130;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
Debug.ShouldStop(2);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("minuto_fin",BA.numberCast(int.class, 0));
 BA.debugLineNum = 131;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
Debug.ShouldStop(4);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("Pictograma",BA.ObjectToString("colegio"));
 BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
Debug.ShouldStop(8);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 3)).setField ("Descripcion",BA.ObjectToString("Cole"));
 BA.debugLineNum = 134;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
Debug.ShouldStop(32);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("hora_inicio",BA.numberCast(int.class, 14));
 BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
Debug.ShouldStop(64);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
Debug.ShouldStop(128);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("hora_fin",BA.numberCast(int.class, 15));
 BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
Debug.ShouldStop(256);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("minuto_fin",BA.numberCast(int.class, 0));
 BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
Debug.ShouldStop(512);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("Pictograma",BA.ObjectToString("comer"));
 BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
Debug.ShouldStop(1024);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 4)).setField ("Descripcion",BA.ObjectToString("Comer"));
 BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
Debug.ShouldStop(4096);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("hora_inicio",BA.numberCast(int.class, 15));
 BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
Debug.ShouldStop(8192);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
Debug.ShouldStop(16384);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("hora_fin",BA.numberCast(int.class, 20));
 BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
Debug.ShouldStop(32768);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("minuto_fin",BA.numberCast(int.class, 0));
 BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
Debug.ShouldStop(65536);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("Pictograma",BA.ObjectToString("juguete"));
 BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
Debug.ShouldStop(131072);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 5)).setField ("Descripcion",BA.ObjectToString("Jugar"));
 BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
Debug.ShouldStop(524288);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("hora_inicio",BA.numberCast(int.class, 20));
 BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
Debug.ShouldStop(1048576);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
Debug.ShouldStop(2097152);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("hora_fin",BA.numberCast(int.class, 20));
 BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
Debug.ShouldStop(4194304);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("minuto_fin",BA.numberCast(int.class, 30));
 BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
Debug.ShouldStop(8388608);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("Pictograma",BA.ObjectToString("ban_arse"));
 BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
Debug.ShouldStop(16777216);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 6)).setField ("Descripcion",BA.ObjectToString("Bañarse"));
 BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
Debug.ShouldStop(67108864);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("hora_inicio",BA.numberCast(int.class, 20));
 BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
Debug.ShouldStop(134217728);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("minuto_inicio",BA.numberCast(int.class, 30));
 BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
Debug.ShouldStop(268435456);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("hora_fin",BA.numberCast(int.class, 21));
 BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
Debug.ShouldStop(536870912);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("minuto_fin",BA.numberCast(int.class, 0));
 BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
Debug.ShouldStop(1073741824);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("Pictograma",BA.ObjectToString("cenar_2"));
 BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
Debug.ShouldStop(-2147483648);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 7)).setField ("Descripcion",BA.ObjectToString("Cenar"));
 BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
Debug.ShouldStop(2);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("hora_inicio",BA.numberCast(int.class, 21));
 BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
Debug.ShouldStop(4);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("minuto_inicio",BA.numberCast(int.class, 0));
 BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
Debug.ShouldStop(8);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("hora_fin",BA.numberCast(int.class, 21));
 BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
Debug.ShouldStop(16);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("minuto_fin",BA.numberCast(int.class, 30));
 BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
Debug.ShouldStop(32);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("Pictograma",BA.ObjectToString("dormir_1"));
 BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
Debug.ShouldStop(64);
starter._actividadsecuencia.getArrayElement(false,BA.numberCast(int.class, 0),BA.numberCast(int.class, 8)).setField ("Descripcion",BA.ObjectToString("Acostarse"));
 BA.debugLineNum = 169;BA.debugLine="Secuencia(0).tablero.tipo=2";
Debug.ShouldStop(256);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).getField(false,"tablero").setField ("tipo",BA.numberCast(int.class, 2));
 BA.debugLineNum = 170;BA.debugLine="Secuencia(0).tablero.indicar_hora=3";
Debug.ShouldStop(512);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).getField(false,"tablero").setField ("indicar_hora",BA.numberCast(int.class, 3));
 BA.debugLineNum = 171;BA.debugLine="Secuencia(0).tablero.tam_icono=10";
Debug.ShouldStop(1024);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).getField(false,"tablero").setField ("tam_icono",BA.numberCast(int.class, 10));
 BA.debugLineNum = 173;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
Debug.ShouldStop(4096);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).setField ("pictograma",BA.ObjectToString("colegio"));
 BA.debugLineNum = 174;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de ejemplo\"";
Debug.ShouldStop(8192);
starter._secuencia.getArrayElement(false,BA.numberCast(int.class, 0)).setField ("Descripcion",BA.ObjectToString("Secuencia de ejemplo"));
 BA.debugLineNum = 176;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _intercambiaratividades(RemoteObject _act1,RemoteObject _act2) throws Exception{
try {
		Debug.PushSubsStack("IntercambiarAtividades (starter) ","starter",2,starter.processBA,starter.mostCurrent,210);
if (RapidSub.canDelegate("intercambiaratividades")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","intercambiaratividades", _act1, _act2);
RemoteObject _actint = RemoteObject.declareNull("javi.prieto.pictorario.starter._actividad");
Debug.locals.put("Act1", _act1);
Debug.locals.put("Act2", _act2);
 BA.debugLineNum = 210;BA.debugLine="Sub IntercambiarAtividades (Act1 As Int, Act2 As I";
Debug.ShouldStop(131072);
 BA.debugLineNum = 211;BA.debugLine="Dim ActInt As Actividad";
Debug.ShouldStop(262144);
_actint = RemoteObject.createNew ("javi.prieto.pictorario.starter._actividad");Debug.locals.put("ActInt", _actint);
 BA.debugLineNum = 212;BA.debugLine="ActInt=ActividadSecuencia(MaxSecuencias,Act1)";
Debug.ShouldStop(524288);
_actint = starter._actividadsecuencia.getArrayElement(false,starter._maxsecuencias,_act1);Debug.locals.put("ActInt", _actint);
 BA.debugLineNum = 213;BA.debugLine="ActividadSecuencia(MaxSecuencias,Act2)=ActividadS";
Debug.ShouldStop(1048576);
starter._actividadsecuencia.setArrayElement (starter._actividadsecuencia.getArrayElement(false,starter._maxsecuencias,_act1),starter._maxsecuencias,_act2);
 BA.debugLineNum = 214;BA.debugLine="ActividadSecuencia(MaxSecuencias,Act1)=ActInt";
Debug.ShouldStop(2097152);
starter._actividadsecuencia.setArrayElement (_actint,starter._maxsecuencias,_act1);
 BA.debugLineNum = 215;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
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
 //BA.debugLineNum = 11;BA.debugLine="Dim kvs As KeyValueStore";
starter._kvs = RemoteObject.createNew ("b4a.example3.keyvaluestore");
 //BA.debugLineNum = 15;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 17;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 28;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 32;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
starter._maxsecuencias = BA.numberCast(int.class, 10);
 //BA.debugLineNum = 33;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
starter._maxactividades = BA.numberCast(int.class, 20);
 //BA.debugLineNum = 37;BA.debugLine="Dim DescripcionTablero(4) As String";
starter._descripciontablero = RemoteObject.createNewArray ("String", new int[] {4}, new Object[]{});
 //BA.debugLineNum = 38;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
starter._descripciontablero = RemoteObject.createNewArray("String",new int[] {4},new Object[] {BA.ObjectToString("Reloj de 12h (mañana)"),BA.ObjectToString("Reloj de 12h (tarde)"),BA.ObjectToString("Reloj de 24h"),RemoteObject.createImmutable("Arco de secuencia")});
 //BA.debugLineNum = 40;BA.debugLine="Dim DescripcionMinutero(4) As String";
starter._descripcionminutero = RemoteObject.createNewArray ("String", new int[] {4}, new Object[]{});
 //BA.debugLineNum = 41;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
starter._descripcionminutero = RemoteObject.createNewArray("String",new int[] {4},new Object[] {BA.ObjectToString("Sin indicación"),BA.ObjectToString("Indicar hora"),BA.ObjectToString("Indicar hora y minutos"),RemoteObject.createImmutable("Indicar hora, minutos y segundos")});
 //BA.debugLineNum = 45;BA.debugLine="Dim MaxColores=20 As Int";
starter._maxcolores = BA.numberCast(int.class, 20);
 //BA.debugLineNum = 46;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
starter._colores = RemoteObject.createNewArray ("int", new int[] {starter._maxcolores.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 47;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
starter._colores = RemoteObject.createNewArray("int",new int[] {20},new Object[] {BA.numberCast(int.class, 0xffffb3ba),BA.numberCast(int.class, 0xffffdfba),BA.numberCast(int.class, 0xffffffba),BA.numberCast(int.class, 0xffbaffc9),BA.numberCast(int.class, 0xffbae1ff),BA.numberCast(int.class, 0xffffbaff),BA.numberCast(int.class, 0xffdfffba),BA.numberCast(int.class, 0xffbaffc9),BA.numberCast(int.class, 0xffbae1ff),BA.numberCast(int.class, 0xffffe1b1),BA.numberCast(int.class, 0xffbaffe1),BA.numberCast(int.class, 0xffffb3ba),BA.numberCast(int.class, 0xffffdfba),BA.numberCast(int.class, 0xffffffba),BA.numberCast(int.class, 0xffbaffc9),BA.numberCast(int.class, 0xffbae1ff),BA.numberCast(int.class, 0xffffbaff),BA.numberCast(int.class, 0xffdfffba),BA.numberCast(int.class, 0xffbaffc9),BA.numberCast(int.class, 0xffbae1ff)});
 //BA.debugLineNum = 51;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
starter._numsecuencias = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 52;BA.debugLine="Dim SecuenciaActiva As Int";
starter._secuenciaactiva = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 53;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
starter._secuencia = RemoteObject.createNewArray ("javi.prieto.pictorario.starter._secuencia", new int[] {RemoteObject.solve(new RemoteObject[] {starter._maxsecuencias,RemoteObject.createImmutable(1)}, "+",1, 1).<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 54;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
starter._actividadsecuencia = RemoteObject.createNewArray ("javi.prieto.pictorario.starter._actividad", new int[] {RemoteObject.solve(new RemoteObject[] {starter._maxsecuencias,RemoteObject.createImmutable(1)}, "+",1, 1).<Integer>get().intValue(),starter._maxactividades.<Integer>get().intValue()}, new Object[]{});
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _service_create() throws Exception{
try {
		Debug.PushSubsStack("Service_Create (starter) ","starter",2,starter.processBA,starter.mostCurrent,58);
if (RapidSub.canDelegate("service_create")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","service_create");
 BA.debugLineNum = 58;BA.debugLine="Sub Service_Create";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 62;BA.debugLine="NumSecuencias=0";
Debug.ShouldStop(536870912);
starter._numsecuencias = BA.numberCast(int.class, 0);
 BA.debugLineNum = 63;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
Debug.ShouldStop(1073741824);
starter._kvs.runVoidMethod ("_initialize",starter.processBA,(Object)(starter.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirInternal")),(Object)(RemoteObject.createImmutable("configuracion")));
 BA.debugLineNum = 65;BA.debugLine="Cargar_Configuracion";
Debug.ShouldStop(1);
_cargar_configuracion();
 BA.debugLineNum = 69;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _service_destroy() throws Exception{
try {
		Debug.PushSubsStack("Service_Destroy (starter) ","starter",2,starter.processBA,starter.mostCurrent,191);
if (RapidSub.canDelegate("service_destroy")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","service_destroy");
 BA.debugLineNum = 191;BA.debugLine="Sub Service_Destroy";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 193;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _service_start(RemoteObject _startingintent) throws Exception{
try {
		Debug.PushSubsStack("Service_Start (starter) ","starter",2,starter.processBA,starter.mostCurrent,178);
if (RapidSub.canDelegate("service_start")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","service_start", _startingintent);
Debug.locals.put("StartingIntent", _startingintent);
 BA.debugLineNum = 178;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 180;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _service_taskremoved() throws Exception{
try {
		Debug.PushSubsStack("Service_TaskRemoved (starter) ","starter",2,starter.processBA,starter.mostCurrent,182);
if (RapidSub.canDelegate("service_taskremoved")) return javi.prieto.pictorario.starter.remoteMe.runUserSub(false, "starter","service_taskremoved");
 BA.debugLineNum = 182;BA.debugLine="Sub Service_TaskRemoved";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 184;BA.debugLine="End Sub";
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