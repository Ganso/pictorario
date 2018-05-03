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
public static b4a.example3.keyvaluestore _kvs = null;
public static int _maxsecuencias = 0;
public static int _maxactividades = 0;
public static int _maxcolores = 0;
public static int[] _colores = null;
public static int _numsecuencias = 0;
public static int _secuenciaactiva = 0;
public static javi.prieto.pictorario.starter._secuencia[] _secuencia = null;
public static javi.prieto.pictorario.starter._actividad[][] _actividadsecuencia = null;
public javi.prieto.pictorario.main _main = null;
public javi.prieto.pictorario.visualizacion _visualizacion = null;
public javi.prieto.pictorario.acercade _acercade = null;
public static class _actividad{
public boolean IsInitialized;
public int hora_inicio;
public int minuto_inicio;
public int hora_fin;
public int minuto_fin;
public String Pictograma;
public String descripcion;
public void Initialize() {
IsInitialized = true;
hora_inicio = 0;
minuto_inicio = 0;
hora_fin = 0;
minuto_fin = 0;
Pictograma = "";
descripcion = "";
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
public String descripcion;
public javi.prieto.pictorario.starter._tablero tablero;
public String pictograma;
public int num_actividades;
public void Initialize() {
IsInitialized = true;
descripcion = "";
tablero = new javi.prieto.pictorario.starter._tablero();
pictograma = "";
num_actividades = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 217;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 218;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return false;
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 74;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 76;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_numsecuencias = (int)(BA.ObjectToNumber(_kvs._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 77;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 78;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_secuencia[_i] = (javi.prieto.pictorario.starter._secuencia)(_kvs._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 79;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
 //BA.debugLineNum = 80;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecue";
_actividadsecuencia[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_kvs._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 }
};
 }
};
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 64;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 65;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_kvs._put("NumSecuencias",(Object)(_numsecuencias));
 //BA.debugLineNum = 66;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 67;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_kvs._put("Secuencia."+BA.NumberToString(_i),(Object)(_secuencia[_i]));
 //BA.debugLineNum = 68;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
 //BA.debugLineNum = 69;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_kvs._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_actividadsecuencia[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 86;BA.debugLine="NumSecuencias=2";
_numsecuencias = (int) (2);
 //BA.debugLineNum = 90;BA.debugLine="Secuencia(0).Initialize";
_secuencia[(int) (0)].Initialize();
 //BA.debugLineNum = 92;BA.debugLine="Secuencia(0).num_actividades=9";
_secuencia[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 94;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 95;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 96;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 97;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 98;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (0)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 99;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (0)][(int) (0)].descripcion = "Despertarse";
 //BA.debugLineNum = 101;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 102;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 103;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 104;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 105;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 106;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].descripcion = "Vestirse";
 //BA.debugLineNum = 108;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 109;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 110;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_actividadsecuencia[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 111;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 112;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 113;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].descripcion = "Desayunar";
 //BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_actividadsecuencia[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_actividadsecuencia[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 119;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (0)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 120;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (0)][(int) (3)].descripcion = "Cole";
 //BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_actividadsecuencia[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_actividadsecuencia[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 126;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Pictograma = "comer";
 //BA.debugLineNum = 127;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].descripcion = "Comer";
 //BA.debugLineNum = 129;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_actividadsecuencia[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 130;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 131;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 133;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_actividadsecuencia[(int) (0)][(int) (5)].Pictograma = "juguete";
 //BA.debugLineNum = 134;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_actividadsecuencia[(int) (0)][(int) (5)].descripcion = "Jugar";
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Pictograma = "ban_arse";
 //BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_actividadsecuencia[(int) (0)][(int) (6)].descripcion = "Bañarse";
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_actividadsecuencia[(int) (0)][(int) (7)].Pictograma = "cenar_2";
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_actividadsecuencia[(int) (0)][(int) (7)].descripcion = "Cenar";
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_actividadsecuencia[(int) (0)][(int) (8)].Pictograma = "dormir_1";
 //BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_actividadsecuencia[(int) (0)][(int) (8)].descripcion = "Acostarse";
 //BA.debugLineNum = 157;BA.debugLine="Secuencia(0).tablero.tipo=2";
_secuencia[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 158;BA.debugLine="Secuencia(0).tablero.indicar_hora=3";
_secuencia[(int) (0)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 159;BA.debugLine="Secuencia(0).tablero.tam_icono=10";
_secuencia[(int) (0)].tablero.tam_icono = (int) (10);
 //BA.debugLineNum = 161;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
_secuencia[(int) (0)].pictograma = "colegio";
 //BA.debugLineNum = 162;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de prueba de";
_secuencia[(int) (0)].descripcion = "Secuencia de prueba de día completo";
 //BA.debugLineNum = 166;BA.debugLine="Secuencia(1).Initialize";
_secuencia[(int) (1)].Initialize();
 //BA.debugLineNum = 168;BA.debugLine="Secuencia(1).num_actividades=4";
_secuencia[(int) (1)].num_actividades = (int) (4);
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 171;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_actividadsecuencia[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(1,0).hora_fin=8";
_actividadsecuencia[(int) (1)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 173;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=15";
_actividadsecuencia[(int) (1)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(1,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (1)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (1)][(int) (0)].descripcion = "Despertarse";
 //BA.debugLineNum = 177;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 178;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=15";
_actividadsecuencia[(int) (1)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 179;BA.debugLine="ActividadSecuencia(1,1).hora_fin=8";
_actividadsecuencia[(int) (1)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 180;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=30";
_actividadsecuencia[(int) (1)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 181;BA.debugLine="ActividadSecuencia(1,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (1)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 182;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (1)][(int) (1)].descripcion = "Vestirse";
 //BA.debugLineNum = 184;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 185;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=30";
_actividadsecuencia[(int) (1)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 186;BA.debugLine="ActividadSecuencia(1,2).hora_fin=9";
_actividadsecuencia[(int) (1)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 187;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=0";
_actividadsecuencia[(int) (1)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 188;BA.debugLine="ActividadSecuencia(1,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (1)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 189;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (1)][(int) (2)].descripcion = "Desayunar";
 //BA.debugLineNum = 191;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=9";
_actividadsecuencia[(int) (1)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 192;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=0";
_actividadsecuencia[(int) (1)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 193;BA.debugLine="ActividadSecuencia(1,3).hora_fin=14";
_actividadsecuencia[(int) (1)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 194;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=0";
_actividadsecuencia[(int) (1)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 195;BA.debugLine="ActividadSecuencia(1,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (1)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 196;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (1)][(int) (3)].descripcion = "Cole";
 //BA.debugLineNum = 198;BA.debugLine="Secuencia(1).tablero.tipo=3";
_secuencia[(int) (1)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 199;BA.debugLine="Secuencia(1).tablero.indicar_hora=3";
_secuencia[(int) (1)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 200;BA.debugLine="Secuencia(1).tablero.tam_icono=20";
_secuencia[(int) (1)].tablero.tam_icono = (int) (20);
 //BA.debugLineNum = 202;BA.debugLine="Secuencia(1).pictograma=\"despertar_1\"";
_secuencia[(int) (1)].pictograma = "despertar_1";
 //BA.debugLineNum = 204;BA.debugLine="Secuencia(1).descripcion=\"Mañana de día laborable";
_secuencia[(int) (1)].descripcion = "Mañana de día laborable";
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim kvs As KeyValueStore";
_kvs = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 15;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 17;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 28;BA.debugLine="Type Secuencia ( descripcion As String, tablero A";
;
 //BA.debugLineNum = 32;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_maxsecuencias = (int) (10);
 //BA.debugLineNum = 33;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_maxactividades = (int) (20);
 //BA.debugLineNum = 37;BA.debugLine="Dim MaxColores=11 As Int";
_maxcolores = (int) (11);
 //BA.debugLineNum = 38;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_colores = new int[_maxcolores];
;
 //BA.debugLineNum = 39;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_colores = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1)};
 //BA.debugLineNum = 43;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_numsecuencias = 0;
 //BA.debugLineNum = 44;BA.debugLine="Dim SecuenciaActiva As Int";
_secuenciaactiva = 0;
 //BA.debugLineNum = 45;BA.debugLine="Dim Secuencia(MaxActividades) As Secuencia";
_secuencia = new javi.prieto.pictorario.starter._secuencia[_maxactividades];
{
int d0 = _secuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
_secuencia[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 46;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias,MaxActividad";
_actividadsecuencia = new javi.prieto.pictorario.starter._actividad[_maxsecuencias][];
{
int d0 = _actividadsecuencia.length;
int d1 = _maxactividades;
for (int i0 = 0;i0 < d0;i0++) {
_actividadsecuencia[i0] = new javi.prieto.pictorario.starter._actividad[d1];
for (int i1 = 0;i1 < d1;i1++) {
_actividadsecuencia[i0][i1] = new javi.prieto.pictorario.starter._actividad();
}
}
}
;
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 55;BA.debugLine="NumSecuencias=0";
_numsecuencias = (int) (0);
 //BA.debugLineNum = 56;BA.debugLine="kvs.Initialize(File.DirDefaultExternal, \"configur";
_kvs._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"configuracion");
 //BA.debugLineNum = 58;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 221;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 208;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
}
