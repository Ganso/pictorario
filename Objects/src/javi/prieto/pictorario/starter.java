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
public static String[] _descripciontablero = null;
public static String[] _descripcionminutero = null;
public static int _maxcolores = 0;
public static int[] _colores = null;
public static int _numsecuencias = 0;
public static int _secuenciaactiva = 0;
public static javi.prieto.pictorario.starter._secuencia[] _secuencia = null;
public static javi.prieto.pictorario.starter._actividad[][] _actividadsecuencia = null;
public javi.prieto.pictorario.main _main = null;
public javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public javi.prieto.pictorario.visualizacion _visualizacion = null;
public javi.prieto.pictorario.acercade _acercade = null;
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
 //BA.debugLineNum = 229;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 230;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return false;
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 81;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 82;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_numsecuencias = (int)(BA.ObjectToNumber(_kvs._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 83;BA.debugLine="If NumSecuencias==0 Then";
if (_numsecuencias==0) { 
 //BA.debugLineNum = 84;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 85;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 87;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step6 = 1;
final int limit6 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 88;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_secuencia[_i] = (javi.prieto.pictorario.starter._secuencia)(_kvs._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 89;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step8 = 1;
final int limit8 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step8 > 0 && _j <= limit8) || (step8 < 0 && _j >= limit8) ;_j = ((int)(0 + _j + step8))  ) {
 //BA.debugLineNum = 90;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_actividadsecuencia[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_kvs._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 }
};
 }
};
 };
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 71;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 72;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_kvs._put("NumSecuencias",(Object)(_numsecuencias));
 //BA.debugLineNum = 73;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 74;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_kvs._put("Secuencia."+BA.NumberToString(_i),(Object)(_secuencia[_i]));
 //BA.debugLineNum = 75;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
 //BA.debugLineNum = 76;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_kvs._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_actividadsecuencia[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 98;BA.debugLine="NumSecuencias=1";
_numsecuencias = (int) (1);
 //BA.debugLineNum = 102;BA.debugLine="Secuencia(0).Initialize";
_secuencia[(int) (0)].Initialize();
 //BA.debugLineNum = 104;BA.debugLine="Secuencia(0).num_actividades=9";
_secuencia[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 106;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 107;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 108;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 109;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 110;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (0)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 111;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 113;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 114;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 120;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 121;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_actividadsecuencia[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 127;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_actividadsecuencia[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 128;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 129;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_actividadsecuencia[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 130;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 131;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (0)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 134;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_actividadsecuencia[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_actividadsecuencia[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Pictograma = "comer";
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_actividadsecuencia[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_actividadsecuencia[(int) (0)][(int) (5)].Pictograma = "juguete";
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_actividadsecuencia[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Pictograma = "ban_arse";
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_actividadsecuencia[(int) (0)][(int) (7)].Pictograma = "cenar_2";
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_actividadsecuencia[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_actividadsecuencia[(int) (0)][(int) (8)].Pictograma = "dormir_1";
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_actividadsecuencia[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 169;BA.debugLine="Secuencia(0).tablero.tipo=2";
_secuencia[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 170;BA.debugLine="Secuencia(0).tablero.indicar_hora=3";
_secuencia[(int) (0)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 171;BA.debugLine="Secuencia(0).tablero.tam_icono=10";
_secuencia[(int) (0)].tablero.tam_icono = (int) (10);
 //BA.debugLineNum = 173;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
_secuencia[(int) (0)].pictograma = "colegio";
 //BA.debugLineNum = 174;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de prueba\"";
_secuencia[(int) (0)].Descripcion = "Secuencia de prueba";
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 28;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 32;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_maxsecuencias = (int) (10);
 //BA.debugLineNum = 33;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_maxactividades = (int) (20);
 //BA.debugLineNum = 37;BA.debugLine="Dim DescripcionTablero(4) As String";
_descripciontablero = new String[(int) (4)];
java.util.Arrays.fill(_descripciontablero,"");
 //BA.debugLineNum = 38;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_descripciontablero = new String[]{"Reloj de 12h (mañana)","Reloj de 12h (tarde)","Reloj de 24h","Arco de secuencia"};
 //BA.debugLineNum = 40;BA.debugLine="Dim DescripcionMinutero(4) As String";
_descripcionminutero = new String[(int) (4)];
java.util.Arrays.fill(_descripcionminutero,"");
 //BA.debugLineNum = 41;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_descripcionminutero = new String[]{"Sin indicación","Indicar hora","Indicar hora y minutos","Indicar hora, minutos y segundos"};
 //BA.debugLineNum = 45;BA.debugLine="Dim MaxColores=20 As Int";
_maxcolores = (int) (20);
 //BA.debugLineNum = 46;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_colores = new int[_maxcolores];
;
 //BA.debugLineNum = 47;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_colores = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 51;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_numsecuencias = 0;
 //BA.debugLineNum = 52;BA.debugLine="Dim SecuenciaActiva As Int";
_secuenciaactiva = 0;
 //BA.debugLineNum = 53;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_secuencia = new javi.prieto.pictorario.starter._secuencia[(int) (_maxsecuencias+1)];
{
int d0 = _secuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
_secuencia[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 54;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
_actividadsecuencia = new javi.prieto.pictorario.starter._actividad[(int) (_maxsecuencias+1)][];
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
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 62;BA.debugLine="NumSecuencias=0";
_numsecuencias = (int) (0);
 //BA.debugLineNum = 63;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_kvs._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 65;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
}
