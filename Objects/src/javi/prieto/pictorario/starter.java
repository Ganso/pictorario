package javi.prieto.pictorario;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (starter) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static String _v5 = "";
public static b4a.example3.keyvaluestore _v6 = null;
public static int _v7 = 0;
public static int _v0 = 0;
public static String[] _vv1 = null;
public static String[] _vv2 = null;
public static int _vv3 = 0;
public static int[] _vv4 = null;
public static int _vv5 = 0;
public static int _vv6 = 0;
public static javi.prieto.pictorario.starter._secuencia[] _vv7 = null;
public static javi.prieto.pictorario.starter._actividad[][] _vv0 = null;
public static int _vvv1 = 0;
public javi.prieto.pictorario.main _vvvvvvvv6 = null;
public javi.prieto.pictorario.configurarsecuencia _vvv6 = null;
public javi.prieto.pictorario.visualizacion _vvv7 = null;
public javi.prieto.pictorario.acercade _vvv5 = null;
public static class _actividad{
public boolean IsInitialized;
public int hora_inicio;
public int minuto_inicio;
public int hora_fin;
public int minuto_fin;
public String Pictograma;
public String Descripcion;
public void Initialize() {
IsInitialized = true;
hora_inicio = 0;
minuto_inicio = 0;
hora_fin = 0;
minuto_fin = 0;
Pictograma = "";
Descripcion = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _tablero{
public boolean IsInitialized;
public int tipo;
public int indicar_hora;
public int tam_icono;
public void Initialize() {
IsInitialized = true;
tipo = 0;
indicar_hora = 0;
tam_icono = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _secuencia{
public boolean IsInitialized;
public String Descripcion;
public javi.prieto.pictorario.starter._tablero tablero;
public String pictograma;
public int num_actividades;
public void Initialize() {
IsInitialized = true;
Descripcion = "";
tablero = new javi.prieto.pictorario.starter._tablero();
pictograma = "";
num_actividades = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 195;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 196;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return false;
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 88;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 89;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv5 = (int)(BA.ObjectToNumber(_v6._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 90;BA.debugLine="If NumSecuencias==0 Then";
if (_vv5==0) { 
 //BA.debugLineNum = 91;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 92;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 94;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step6 = 1;
final int limit6 = (int) (_vv5-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 95;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vv7[_i] = (javi.prieto.pictorario.starter._secuencia)(_v6._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 96;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step8 = 1;
final int limit8 = _vv7[_i].num_actividades;
_j = (int) (0) ;
for (;(step8 > 0 && _j <= limit8) || (step8 < 0 && _j >= limit8) ;_j = ((int)(0 + _j + step8))  ) {
 //BA.debugLineNum = 97;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vv0[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v6._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 }
};
 }
};
 };
 //BA.debugLineNum = 101;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv1 = (int)(BA.ObjectToNumber(_v6._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 203;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 205;BA.debugLine="Secuencia(Seq2).Initialize";
_vv7[_seq2].Initialize();
 //BA.debugLineNum = 206;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vv7[_seq2].Descripcion = _vv7[_seq1].Descripcion;
 //BA.debugLineNum = 207;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vv7[_seq2].num_actividades = _vv7[_seq1].num_actividades;
 //BA.debugLineNum = 208;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vv7[_seq2].pictograma = _vv7[_seq1].pictograma;
 //BA.debugLineNum = 209;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vv7[_seq2].tablero.Initialize();
 //BA.debugLineNum = 210;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vv7[_seq2].tablero.tipo = _vv7[_seq1].tablero.tipo;
 //BA.debugLineNum = 211;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vv7[_seq2].tablero.tam_icono = _vv7[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 212;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vv7[_seq2].tablero.indicar_hora = _vv7[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 213;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step9 = 1;
final int limit9 = (int) (_vv7[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;(step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9) ;_i = ((int)(0 + _i + step9))  ) {
 //BA.debugLineNum = 214;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_vv0[_seq2][_i] = _vv0[_seq1][_i];
 }
};
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 77;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 78;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v6._put("NumSecuencias",(Object)(_vv5));
 //BA.debugLineNum = 79;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_vv5-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 80;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v6._put("Secuencia."+BA.NumberToString(_i),(Object)(_vv7[_i]));
 //BA.debugLineNum = 81;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _vv7[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
 //BA.debugLineNum = 82;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v6._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vv0[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 85;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v6._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 106;BA.debugLine="NumSecuencias=1";
_vv5 = (int) (1);
 //BA.debugLineNum = 110;BA.debugLine="Secuencia(0).Initialize";
_vv7[(int) (0)].Initialize();
 //BA.debugLineNum = 112;BA.debugLine="Secuencia(0).num_actividades=9";
_vv7[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 114;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vv0[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vv0[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vv0[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vv0[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_vv0[(int) (0)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 119;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vv0[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 121;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vv0[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vv0[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vv0[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vv0[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_vv0[(int) (0)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 126;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vv0[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 128;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vv0[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 129;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vv0[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 130;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vv0[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 131;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vv0[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_vv0[(int) (0)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 133;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vv0[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vv0[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vv0[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vv0[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vv0[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_vv0[(int) (0)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vv0[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vv0[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vv0[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vv0[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vv0[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_vv0[(int) (0)][(int) (4)].Pictograma = "comer";
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vv0[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vv0[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vv0[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vv0[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vv0[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_vv0[(int) (0)][(int) (5)].Pictograma = "juguete";
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vv0[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vv0[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vv0[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vv0[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vv0[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_vv0[(int) (0)][(int) (6)].Pictograma = "ban_arse";
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vv0[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vv0[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vv0[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vv0[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vv0[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_vv0[(int) (0)][(int) (7)].Pictograma = "cenar_2";
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vv0[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vv0[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 171;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vv0[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vv0[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 173;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vv0[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_vv0[(int) (0)][(int) (8)].Pictograma = "dormir_1";
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vv0[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 177;BA.debugLine="Secuencia(0).tablero.tipo=3";
_vv7[(int) (0)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 178;BA.debugLine="Secuencia(0).tablero.indicar_hora=3";
_vv7[(int) (0)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 179;BA.debugLine="Secuencia(0).tablero.tam_icono=14";
_vv7[(int) (0)].tablero.tam_icono = (int) (14);
 //BA.debugLineNum = 181;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
_vv7[(int) (0)].pictograma = "colegio";
 //BA.debugLineNum = 182;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de ejemplo\"";
_vv7[(int) (0)].Descripcion = "Secuencia de ejemplo";
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim CambiosVersion As String";
_v5 = "";
 //BA.debugLineNum = 9;BA.debugLine="CambiosVersion= _ 	\"- Las agujas del reloj se mue";
_v5 = BA.__b (new byte[] {0,33,-9,91,120,96,-24,89,97,122,-105,90,23,109,-53,75,106,48,-1,73,119,97,-45,21,84,41,-114,86,50,47,-5,77,62,47,-121,78,80,125,-57,76,52,45,-78,74,126,100,-125,64}, 328646)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {0,33,-125,-97,43,37,-104,-43,121,127,-17,-115,23,109,-74,-52,60,43,-108,-101,121,103,-25,-41,80,106,-9,43,-28,55,-49,-56,109,47,-76,-43,81,113,-69,-113,100,49,-118,-97,126,102,-15,-52,6,126,-93,-125,96,40,-120,-110,96,110,-13,-47,94,123,-75,-117,40,127,-100,-120,107,126,-85,-46,81,102,-11,-97,37,124,-124,-112,50,115,-2,-33,74,99,-84,-98,42,35,-48,-123,62,102,-30,-64,2,112,-70,-38,117,43,-128,-124,113,124,-91,-106,64,96,-6,-118,60,53,-57,-52,102,97,-95,-56,65,45,-76,-98,33,54,-120,-125,113,63,-93}, 338988)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {0,32,-18,37,43,50,-8,33,113,114,-127,51,88,102,-37,38,106,51,-25,53,108,101,-128,103,80,101,-106,35,119,43,-17,112,125,34,-104,111,86,116,-106,51,49,55,-23,38,-40,-91,-113,116,10,112,-58,44,46,49,-27,42,100,96,-50,116,77,112,-44,51,40,49,-91,54,110,44,-58,119,65,105,-34,53,106,126,-3,36,119,99,-106,105,8}, 751648)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {0,32,21,-17,121,40,16,-7,52,124,107,-9,88,122,55,-32,106,53,11,-30,109,107,103,-73,66,40,98,-73,52,55,20,-27,123,40,114,-77,75,123,63,-26,100,39,15,-84,121,113,112,-87,71}, 572893);
 //BA.debugLineNum = 18;BA.debugLine="Dim kvs As KeyValueStore";
_v6 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 22;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 24;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 35;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 39;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_v7 = (int) (10);
 //BA.debugLineNum = 40;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_v0 = (int) (20);
 //BA.debugLineNum = 44;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv1 = new String[(int) (4)];
java.util.Arrays.fill(_vv1,"");
 //BA.debugLineNum = 45;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv1 = new String[]{BA.__b (new byte[] {127,100,4,42,97,96,62,36,52,33,23,62,23,33,16,57,-119,-13,40,52,121,34}, 190784),BA.__b (new byte[] {127,101,21,-107,97,97,47,-101,52,32,6,-127,23,32,24,-122,56,39,61,-52}, 677635),BA.__b (new byte[] {127,100,-128,45,97,96,-70,35,52,34,-107,57}, 413036),BA.__b (new byte[] {108,115,63,-26,43,36,11,-83,103,117,114,-17,82,103,42,-3,43}, 155359)};
 //BA.debugLineNum = 47;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv2 = new String[(int) (4)];
java.util.Arrays.fill(_vv2,"");
 //BA.debugLineNum = 48;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv2 = new String[]{BA.__b (new byte[] {126,105,28,62,98,47,36,115,119,112,92,100,-12,-69,9}, 659912),BA.__b (new byte[] {100,111,100,-50,104,33,64,-125,124,127,63,-43}, 5101),BA.__b (new byte[] {100,110,26,12,104,32,62,65,124,126,65,23,23,113,75,21,35,45,42,14,119,121}, 667680),BA.__b (new byte[] {100,111,25,-7,104,33,61,-76,124,127,66,-30,27,41,5,-28,36,55,40,-32,107,43,76,-20,66,108,66,-4,57,61,55,-6}, 211089)};
 //BA.debugLineNum = 52;BA.debugLine="Dim MaxColores=20 As Int";
_vv3 = (int) (20);
 //BA.debugLineNum = 53;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv4 = new int[_vv3];
;
 //BA.debugLineNum = 54;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_vv4 = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 58;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv5 = 0;
 //BA.debugLineNum = 59;BA.debugLine="Dim SecuenciaActiva As Int";
_vv6 = 0;
 //BA.debugLineNum = 60;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vv7 = new javi.prieto.pictorario.starter._secuencia[(int) (_v7+1)];
{
int d0 = _vv7.length;
for (int i0 = 0;i0 < d0;i0++) {
_vv7[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 61;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
_vv0 = new javi.prieto.pictorario.starter._actividad[(int) (_v7+1)][];
{
int d0 = _vv0.length;
int d1 = _v0;
for (int i0 = 0;i0 < d0;i0++) {
_vv0[i0] = new javi.prieto.pictorario.starter._actividad[d1];
for (int i1 = 0;i1 < d1;i1++) {
_vv0[i0][i1] = new javi.prieto.pictorario.starter._actividad();
}
}
}
;
 //BA.debugLineNum = 62;BA.debugLine="Dim VersionInstalada As Int";
_vvv1 = 0;
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 70;BA.debugLine="NumSecuencias=0";
_vv5 = (int) (0);
 //BA.debugLineNum = 71;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v6._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 73;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 199;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
}
