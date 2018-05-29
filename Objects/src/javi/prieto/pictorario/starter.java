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
public javi.prieto.pictorario.main _vvvvvv1 = null;
public javi.prieto.pictorario.visualizacion _vvv6 = null;
public javi.prieto.pictorario.configurarsecuencia _vvv5 = null;
public javi.prieto.pictorario.acercade _vvv4 = null;
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
 //BA.debugLineNum = 196;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 197;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return false;
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 89;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 90;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv5 = (int)(BA.ObjectToNumber(_v6._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 91;BA.debugLine="If NumSecuencias==0 Then";
if (_vv5==0) { 
 //BA.debugLineNum = 92;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 93;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 95;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step6 = 1;
final int limit6 = (int) (_vv5-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 96;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vv7[_i] = (javi.prieto.pictorario.starter._secuencia)(_v6._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 97;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step8 = 1;
final int limit8 = _vv7[_i].num_actividades;
_j = (int) (0) ;
for (;(step8 > 0 && _j <= limit8) || (step8 < 0 && _j >= limit8) ;_j = ((int)(0 + _j + step8))  ) {
 //BA.debugLineNum = 98;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vv0[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v6._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 }
};
 }
};
 };
 //BA.debugLineNum = 102;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv1 = (int)(BA.ObjectToNumber(_v6._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 204;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 206;BA.debugLine="Secuencia(Seq2).Initialize";
_vv7[_seq2].Initialize();
 //BA.debugLineNum = 207;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vv7[_seq2].Descripcion = _vv7[_seq1].Descripcion;
 //BA.debugLineNum = 208;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vv7[_seq2].num_actividades = _vv7[_seq1].num_actividades;
 //BA.debugLineNum = 209;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vv7[_seq2].pictograma = _vv7[_seq1].pictograma;
 //BA.debugLineNum = 210;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vv7[_seq2].tablero.Initialize();
 //BA.debugLineNum = 211;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vv7[_seq2].tablero.tipo = _vv7[_seq1].tablero.tipo;
 //BA.debugLineNum = 212;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vv7[_seq2].tablero.tam_icono = _vv7[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 213;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vv7[_seq2].tablero.indicar_hora = _vv7[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 214;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step9 = 1;
final int limit9 = (int) (_vv7[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;(step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9) ;_i = ((int)(0 + _i + step9))  ) {
 //BA.debugLineNum = 215;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_vv0[_seq2][_i] = _vv0[_seq1][_i];
 }
};
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 78;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 79;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v6._put("NumSecuencias",(Object)(_vv5));
 //BA.debugLineNum = 80;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_vv5-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 81;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v6._put("Secuencia."+BA.NumberToString(_i),(Object)(_vv7[_i]));
 //BA.debugLineNum = 82;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _vv7[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
 //BA.debugLineNum = 83;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v6._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vv0[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 86;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v6._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 107;BA.debugLine="NumSecuencias=1";
_vv5 = (int) (1);
 //BA.debugLineNum = 111;BA.debugLine="Secuencia(0).Initialize";
_vv7[(int) (0)].Initialize();
 //BA.debugLineNum = 113;BA.debugLine="Secuencia(0).num_actividades=9";
_vv7[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vv0[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vv0[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vv0[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vv0[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 119;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_vv0[(int) (0)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 120;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vv0[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vv0[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vv0[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vv0[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vv0[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 126;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_vv0[(int) (0)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 127;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vv0[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 129;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vv0[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 130;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vv0[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 131;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vv0[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vv0[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 133;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_vv0[(int) (0)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 134;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vv0[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vv0[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vv0[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vv0[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vv0[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_vv0[(int) (0)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vv0[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vv0[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vv0[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vv0[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vv0[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_vv0[(int) (0)][(int) (4)].Pictograma = "comer";
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vv0[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vv0[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vv0[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vv0[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vv0[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_vv0[(int) (0)][(int) (5)].Pictograma = "juguete";
 //BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vv0[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vv0[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vv0[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vv0[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vv0[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_vv0[(int) (0)][(int) (6)].Pictograma = "ban_arse";
 //BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vv0[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vv0[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vv0[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vv0[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vv0[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_vv0[(int) (0)][(int) (7)].Pictograma = "cenar_2";
 //BA.debugLineNum = 169;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vv0[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 171;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vv0[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vv0[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 173;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vv0[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vv0[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_vv0[(int) (0)][(int) (8)].Pictograma = "dormir_1";
 //BA.debugLineNum = 176;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vv0[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 178;BA.debugLine="Secuencia(0).tablero.tipo=2";
_vv7[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 179;BA.debugLine="Secuencia(0).tablero.indicar_hora=1";
_vv7[(int) (0)].tablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 180;BA.debugLine="Secuencia(0).tablero.tam_icono=14";
_vv7[(int) (0)].tablero.tam_icono = (int) (14);
 //BA.debugLineNum = 182;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
_vv7[(int) (0)].pictograma = "colegio";
 //BA.debugLineNum = 183;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de ejemplo\"";
_vv7[(int) (0)].Descripcion = "Secuencia de ejemplo";
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim CambiosVersion As String";
_v5 = "";
 //BA.debugLineNum = 9;BA.debugLine="CambiosVersion= _ 	\"- Cambiado el tema de Holo a";
_v5 = BA.__b (new byte[] {1,33,95,86,103,35,71,82,113,127,113,64,90,41,125,79,38,34,29,76,124,43,28,5,92,102,100,79,118,21,88,90,122,56,39,3,73,52,45,95,32,99,91,90,110,100,104,7,27,96,125,84,40,36,95,25,101,99,41,26,9,119,101,12,33,63,22,72,99,97,117,24,87,96,-52,-103,37,63,72,86,51,120,62,11,70,97,42,31}, 497125)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {1,33,-100,27,120,51,-120,23,124,116,-3,21,22,127,-85,27,34,44,-115,75,124,121,-27,70,66,108,-12,77,53,55,-108,77,115,43,-83,64,86,125,-95,1,36,32,-97,-75,-87,107,-85,69,13,48,-77,27,51,36,-125,23,121,45,-5,80,9,115,-66,6,62,49,-43,28,99,45,-67,87,66,108,-88,8,47,122,-100,80,119,117,-78,76,66,96,-77,27,-115,-29,-121,94}, 839818)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {1,33,28,127,42,41,14,116,112,98,48,98,90,96,43,101,107,38,18,44,108,101,116,110,85,125,108,123,35,61,12,107,51,106,124,47,5,113,55,124,-122,-30,84,116,118,37,127,39,4,127,43,51,49,42,0,112,100,104,122,62,74,102,37,40,62,59,87,105,103,110,-110,-14,85,41,62,111,57,126,77,114,96,115,98,52,69,123,48,53,58,53,19,99,49,52,99,51,70,98,43,122}, 613408)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {1,33,68,-19,42,53,92,-29,116,99,108,-3,87,122,52,-18,42,45,84,-3,117,103,40,-83,16,122,60,-70,38,61,86,-9,118,62,54,-10,76,102,56,-16,36,32,65,-32,58,100,33,-92,-85,-79,118,-93,34,42,64,-32,101,97,96,-87,70,102,-43,43,35,126,79,-22,38,97,108,-17,86,104,96,-20,42,63,85,-30,51,126,45,-69,66,117,127,-26,39,-109,-124,-23,114,52,62,-10,64,123,118,-28,33,54,2,-8,112,98,-127,126,65,47,120,-8,34,61,12,93,-81,107,47,-88,70,110,98,-19,103,53,79,-14,101,119,38,-68,77,47}, 463663)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {1,33,13,72,96,46,0,72,113,113,45,83,87,41,37,81,37,55,0,94,117,106,40,20,85,41,63,117,53,61,23,87,126,106,118,29,2,58}, 575788);
 //BA.debugLineNum = 19;BA.debugLine="Dim kvs As KeyValueStore";
_v6 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 23;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 25;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 36;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 40;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_v7 = (int) (10);
 //BA.debugLineNum = 41;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_v0 = (int) (20);
 //BA.debugLineNum = 45;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv1 = new String[(int) (4)];
java.util.Arrays.fill(_vv1,"");
 //BA.debugLineNum = 46;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv1 = new String[]{BA.__b (new byte[] {126,101,-3,-124,96,96,-57,-118,53,32,-18,-111,22,32,-23,-105,-120,-13,-47,-102,120,35}, 263576),BA.__b (new byte[] {126,100,75,24,96,97,113,22,53,33,88,13,22,33,70,11,57,39,99,65}, 510113),BA.__b (new byte[] {126,100,-24,60,96,97,-46,50,53,34,-3,41}, 683740),BA.__b (new byte[] {109,114,108,94,42,36,88,21,102,116,33,86,83,102,121,69,42}, 7923)};
 //BA.debugLineNum = 48;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv2 = new String[(int) (4)];
java.util.Arrays.fill(_vv2,"");
 //BA.debugLineNum = 49;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv2 = new String[]{BA.__b (new byte[] {127,107,72,-107,99,44,112,-40,118,114,8,-50,-11,-71,93}, 967507),BA.__b (new byte[] {101,110,39,49,105,33,3,124,125,126,124,43}, 115599),BA.__b (new byte[] {101,111,-83,-109,105,32,-119,-34,125,127,-10,-119,22,112,-4,-118,34,45,-99,-111,118,120}, 822782),BA.__b (new byte[] {101,111,86,-55,105,32,114,-124,125,127,13,-45,26,41,74,-44,37,54,103,-48,106,43,3,-35,67,108,13,-52,56,60,120,-54}, 544504)};
 //BA.debugLineNum = 53;BA.debugLine="Dim MaxColores=20 As Int";
_vv3 = (int) (20);
 //BA.debugLineNum = 54;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv4 = new int[_vv3];
;
 //BA.debugLineNum = 55;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_vv4 = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 59;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv5 = 0;
 //BA.debugLineNum = 60;BA.debugLine="Dim SecuenciaActiva As Int";
_vv6 = 0;
 //BA.debugLineNum = 61;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vv7 = new javi.prieto.pictorario.starter._secuencia[(int) (_v7+1)];
{
int d0 = _vv7.length;
for (int i0 = 0;i0 < d0;i0++) {
_vv7[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 62;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
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
 //BA.debugLineNum = 63;BA.debugLine="Dim VersionInstalada As Int";
_vvv1 = 0;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 71;BA.debugLine="NumSecuencias=0";
_vv5 = (int) (0);
 //BA.debugLineNum = 72;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v6._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 74;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
}
