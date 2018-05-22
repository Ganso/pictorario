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
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, anywheresoftware.b4a.ShellBA.class);
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
		    processBA = new anywheresoftware.b4a.ShellBA(this, null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.starter");
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
	}
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
public anywheresoftware.b4a.keywords.Common __c = null;
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
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "copiarsecuencias"))
	return (String) Debug.delegate(processBA, "copiarsecuencias", new Object[] {_seq1,_seq2});
int _i = 0;
RDebugUtils.currentLine=4521984;
 //BA.debugLineNum = 4521984;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
RDebugUtils.currentLine=4521986;
 //BA.debugLineNum = 4521986;BA.debugLine="Secuencia(Seq2).Initialize";
_secuencia[_seq2].Initialize();
RDebugUtils.currentLine=4521987;
 //BA.debugLineNum = 4521987;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_secuencia[_seq2].Descripcion = _secuencia[_seq1].Descripcion;
RDebugUtils.currentLine=4521988;
 //BA.debugLineNum = 4521988;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_secuencia[_seq2].num_actividades = _secuencia[_seq1].num_actividades;
RDebugUtils.currentLine=4521989;
 //BA.debugLineNum = 4521989;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_secuencia[_seq2].pictograma = _secuencia[_seq1].pictograma;
RDebugUtils.currentLine=4521990;
 //BA.debugLineNum = 4521990;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_secuencia[_seq2].tablero.Initialize();
RDebugUtils.currentLine=4521991;
 //BA.debugLineNum = 4521991;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_secuencia[_seq2].tablero.tipo = _secuencia[_seq1].tablero.tipo;
RDebugUtils.currentLine=4521992;
 //BA.debugLineNum = 4521992;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_secuencia[_seq2].tablero.tam_icono = _secuencia[_seq1].tablero.tam_icono;
RDebugUtils.currentLine=4521993;
 //BA.debugLineNum = 4521993;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_secuencia[_seq2].tablero.indicar_hora = _secuencia[_seq1].tablero.indicar_hora;
RDebugUtils.currentLine=4521994;
 //BA.debugLineNum = 4521994;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step9 = 1;
final int limit9 = (int) (_secuencia[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;(step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9) ;_i = ((int)(0 + _i + step9))  ) {
RDebugUtils.currentLine=4521995;
 //BA.debugLineNum = 4521995;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_actividadsecuencia[_seq2][_i] = _actividadsecuencia[_seq1][_i];
 }
};
RDebugUtils.currentLine=4521997;
 //BA.debugLineNum = 4521997;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "guardar_configuracion"))
	return (String) Debug.delegate(processBA, "guardar_configuracion", null);
int _i = 0;
int _j = 0;
RDebugUtils.currentLine=2359296;
 //BA.debugLineNum = 2359296;BA.debugLine="Sub Guardar_Configuracion";
RDebugUtils.currentLine=2359297;
 //BA.debugLineNum = 2359297;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_kvs._put("NumSecuencias",(Object)(_numsecuencias));
RDebugUtils.currentLine=2359298;
 //BA.debugLineNum = 2359298;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step2 = 1;
final int limit2 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
RDebugUtils.currentLine=2359299;
 //BA.debugLineNum = 2359299;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_kvs._put("Secuencia."+BA.NumberToString(_i),(Object)(_secuencia[_i]));
RDebugUtils.currentLine=2359300;
 //BA.debugLineNum = 2359300;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step4 = 1;
final int limit4 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step4 > 0 && _j <= limit4) || (step4 < 0 && _j >= limit4) ;_j = ((int)(0 + _j + step4))  ) {
RDebugUtils.currentLine=2359301;
 //BA.debugLineNum = 2359301;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_kvs._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_actividadsecuencia[_i][_j]));
 }
};
 }
};
RDebugUtils.currentLine=2359304;
 //BA.debugLineNum = 2359304;BA.debugLine="End Sub";
return "";
}
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "application_error"))
	return (Boolean) Debug.delegate(processBA, "application_error", new Object[] {_error,_stacktrace});
RDebugUtils.currentLine=2686976;
 //BA.debugLineNum = 2686976;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
RDebugUtils.currentLine=2686977;
 //BA.debugLineNum = 2686977;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
RDebugUtils.currentLine=2686978;
 //BA.debugLineNum = 2686978;BA.debugLine="End Sub";
return false;
}
public static String  _cargar_configuracion() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "cargar_configuracion"))
	return (String) Debug.delegate(processBA, "cargar_configuracion", null);
int _i = 0;
int _j = 0;
RDebugUtils.currentLine=2424832;
 //BA.debugLineNum = 2424832;BA.debugLine="Sub Cargar_Configuracion";
RDebugUtils.currentLine=2424833;
 //BA.debugLineNum = 2424833;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_numsecuencias = (int)(BA.ObjectToNumber(_kvs._getdefault("NumSecuencias",(Object)(0))));
RDebugUtils.currentLine=2424834;
 //BA.debugLineNum = 2424834;BA.debugLine="If NumSecuencias==0 Then";
if (_numsecuencias==0) { 
RDebugUtils.currentLine=2424835;
 //BA.debugLineNum = 2424835;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
RDebugUtils.currentLine=2424836;
 //BA.debugLineNum = 2424836;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
RDebugUtils.currentLine=2424838;
 //BA.debugLineNum = 2424838;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step6 = 1;
final int limit6 = (int) (_numsecuencias-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
RDebugUtils.currentLine=2424839;
 //BA.debugLineNum = 2424839;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_secuencia[_i] = (javi.prieto.pictorario.starter._secuencia)(_kvs._get("Secuencia."+BA.NumberToString(_i)));
RDebugUtils.currentLine=2424840;
 //BA.debugLineNum = 2424840;BA.debugLine="For j=0 To Secuencia(i).num_actividades";
{
final int step8 = 1;
final int limit8 = _secuencia[_i].num_actividades;
_j = (int) (0) ;
for (;(step8 > 0 && _j <= limit8) || (step8 < 0 && _j >= limit8) ;_j = ((int)(0 + _j + step8))  ) {
RDebugUtils.currentLine=2424841;
 //BA.debugLineNum = 2424841;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_actividadsecuencia[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_kvs._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 }
};
 }
};
 };
RDebugUtils.currentLine=2424845;
 //BA.debugLineNum = 2424845;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "inicializar_con_ejemplo"))
	return (String) Debug.delegate(processBA, "inicializar_con_ejemplo", null);
RDebugUtils.currentLine=2490368;
 //BA.debugLineNum = 2490368;BA.debugLine="Sub Inicializar_Con_Ejemplo";
RDebugUtils.currentLine=2490370;
 //BA.debugLineNum = 2490370;BA.debugLine="NumSecuencias=1";
_numsecuencias = (int) (1);
RDebugUtils.currentLine=2490374;
 //BA.debugLineNum = 2490374;BA.debugLine="Secuencia(0).Initialize";
_secuencia[(int) (0)].Initialize();
RDebugUtils.currentLine=2490376;
 //BA.debugLineNum = 2490376;BA.debugLine="Secuencia(0).num_actividades=9";
_secuencia[(int) (0)].num_actividades = (int) (9);
RDebugUtils.currentLine=2490378;
 //BA.debugLineNum = 2490378;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_inicio = (int) (8);
RDebugUtils.currentLine=2490379;
 //BA.debugLineNum = 2490379;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490380;
 //BA.debugLineNum = 2490380;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_fin = (int) (8);
RDebugUtils.currentLine=2490381;
 //BA.debugLineNum = 2490381;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_fin = (int) (15);
RDebugUtils.currentLine=2490382;
 //BA.debugLineNum = 2490382;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (0)][(int) (0)].Pictograma = "despertar_1";
RDebugUtils.currentLine=2490383;
 //BA.debugLineNum = 2490383;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (0)][(int) (0)].Descripcion = "Despertarse";
RDebugUtils.currentLine=2490385;
 //BA.debugLineNum = 2490385;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_inicio = (int) (8);
RDebugUtils.currentLine=2490386;
 //BA.debugLineNum = 2490386;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
RDebugUtils.currentLine=2490387;
 //BA.debugLineNum = 2490387;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_fin = (int) (8);
RDebugUtils.currentLine=2490388;
 //BA.debugLineNum = 2490388;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_fin = (int) (30);
RDebugUtils.currentLine=2490389;
 //BA.debugLineNum = 2490389;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Pictograma = "vestirse";
RDebugUtils.currentLine=2490390;
 //BA.debugLineNum = 2490390;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Descripcion = "Vestirse";
RDebugUtils.currentLine=2490392;
 //BA.debugLineNum = 2490392;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (2)].hora_inicio = (int) (8);
RDebugUtils.currentLine=2490393;
 //BA.debugLineNum = 2490393;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
RDebugUtils.currentLine=2490394;
 //BA.debugLineNum = 2490394;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_actividadsecuencia[(int) (0)][(int) (2)].hora_fin = (int) (9);
RDebugUtils.currentLine=2490395;
 //BA.debugLineNum = 2490395;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_fin = (int) (0);
RDebugUtils.currentLine=2490396;
 //BA.debugLineNum = 2490396;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Pictograma = "desayunar";
RDebugUtils.currentLine=2490397;
 //BA.debugLineNum = 2490397;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Descripcion = "Desayunar";
RDebugUtils.currentLine=2490399;
 //BA.debugLineNum = 2490399;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_actividadsecuencia[(int) (0)][(int) (3)].hora_inicio = (int) (9);
RDebugUtils.currentLine=2490400;
 //BA.debugLineNum = 2490400;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490401;
 //BA.debugLineNum = 2490401;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_actividadsecuencia[(int) (0)][(int) (3)].hora_fin = (int) (14);
RDebugUtils.currentLine=2490402;
 //BA.debugLineNum = 2490402;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_fin = (int) (0);
RDebugUtils.currentLine=2490403;
 //BA.debugLineNum = 2490403;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (0)][(int) (3)].Pictograma = "colegio";
RDebugUtils.currentLine=2490404;
 //BA.debugLineNum = 2490404;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (0)][(int) (3)].Descripcion = "Cole";
RDebugUtils.currentLine=2490406;
 //BA.debugLineNum = 2490406;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_actividadsecuencia[(int) (0)][(int) (4)].hora_inicio = (int) (14);
RDebugUtils.currentLine=2490407;
 //BA.debugLineNum = 2490407;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490408;
 //BA.debugLineNum = 2490408;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_actividadsecuencia[(int) (0)][(int) (4)].hora_fin = (int) (15);
RDebugUtils.currentLine=2490409;
 //BA.debugLineNum = 2490409;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_fin = (int) (0);
RDebugUtils.currentLine=2490410;
 //BA.debugLineNum = 2490410;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Pictograma = "comer";
RDebugUtils.currentLine=2490411;
 //BA.debugLineNum = 2490411;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Descripcion = "Comer";
RDebugUtils.currentLine=2490413;
 //BA.debugLineNum = 2490413;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_actividadsecuencia[(int) (0)][(int) (5)].hora_inicio = (int) (15);
RDebugUtils.currentLine=2490414;
 //BA.debugLineNum = 2490414;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490415;
 //BA.debugLineNum = 2490415;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (5)].hora_fin = (int) (20);
RDebugUtils.currentLine=2490416;
 //BA.debugLineNum = 2490416;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_fin = (int) (0);
RDebugUtils.currentLine=2490417;
 //BA.debugLineNum = 2490417;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_actividadsecuencia[(int) (0)][(int) (5)].Pictograma = "juguete";
RDebugUtils.currentLine=2490418;
 //BA.debugLineNum = 2490418;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_actividadsecuencia[(int) (0)][(int) (5)].Descripcion = "Jugar";
RDebugUtils.currentLine=2490420;
 //BA.debugLineNum = 2490420;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_inicio = (int) (20);
RDebugUtils.currentLine=2490421;
 //BA.debugLineNum = 2490421;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490422;
 //BA.debugLineNum = 2490422;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_fin = (int) (20);
RDebugUtils.currentLine=2490423;
 //BA.debugLineNum = 2490423;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_fin = (int) (30);
RDebugUtils.currentLine=2490424;
 //BA.debugLineNum = 2490424;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Pictograma = "ban_arse";
RDebugUtils.currentLine=2490425;
 //BA.debugLineNum = 2490425;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Descripcion = "Bañarse";
RDebugUtils.currentLine=2490427;
 //BA.debugLineNum = 2490427;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (7)].hora_inicio = (int) (20);
RDebugUtils.currentLine=2490428;
 //BA.debugLineNum = 2490428;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
RDebugUtils.currentLine=2490429;
 //BA.debugLineNum = 2490429;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (7)].hora_fin = (int) (21);
RDebugUtils.currentLine=2490430;
 //BA.debugLineNum = 2490430;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_fin = (int) (0);
RDebugUtils.currentLine=2490431;
 //BA.debugLineNum = 2490431;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_actividadsecuencia[(int) (0)][(int) (7)].Pictograma = "cenar_2";
RDebugUtils.currentLine=2490432;
 //BA.debugLineNum = 2490432;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_actividadsecuencia[(int) (0)][(int) (7)].Descripcion = "Cenar";
RDebugUtils.currentLine=2490434;
 //BA.debugLineNum = 2490434;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_inicio = (int) (21);
RDebugUtils.currentLine=2490435;
 //BA.debugLineNum = 2490435;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
RDebugUtils.currentLine=2490436;
 //BA.debugLineNum = 2490436;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_fin = (int) (21);
RDebugUtils.currentLine=2490437;
 //BA.debugLineNum = 2490437;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_fin = (int) (30);
RDebugUtils.currentLine=2490438;
 //BA.debugLineNum = 2490438;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_actividadsecuencia[(int) (0)][(int) (8)].Pictograma = "dormir_1";
RDebugUtils.currentLine=2490439;
 //BA.debugLineNum = 2490439;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_actividadsecuencia[(int) (0)][(int) (8)].Descripcion = "Acostarse";
RDebugUtils.currentLine=2490441;
 //BA.debugLineNum = 2490441;BA.debugLine="Secuencia(0).tablero.tipo=2";
_secuencia[(int) (0)].tablero.tipo = (int) (2);
RDebugUtils.currentLine=2490442;
 //BA.debugLineNum = 2490442;BA.debugLine="Secuencia(0).tablero.indicar_hora=3";
_secuencia[(int) (0)].tablero.indicar_hora = (int) (3);
RDebugUtils.currentLine=2490443;
 //BA.debugLineNum = 2490443;BA.debugLine="Secuencia(0).tablero.tam_icono=10";
_secuencia[(int) (0)].tablero.tam_icono = (int) (10);
RDebugUtils.currentLine=2490445;
 //BA.debugLineNum = 2490445;BA.debugLine="Secuencia(0).pictograma=\"colegio\"";
_secuencia[(int) (0)].pictograma = "colegio";
RDebugUtils.currentLine=2490446;
 //BA.debugLineNum = 2490446;BA.debugLine="Secuencia(0).descripcion=\"Secuencia de ejemplo\"";
_secuencia[(int) (0)].Descripcion = "Secuencia de ejemplo";
RDebugUtils.currentLine=2490448;
 //BA.debugLineNum = 2490448;BA.debugLine="End Sub";
return "";
}
public static String  _intercambiaratividades(int _act1,int _act2) throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "intercambiaratividades"))
	return (String) Debug.delegate(processBA, "intercambiaratividades", new Object[] {_act1,_act2});
javi.prieto.pictorario.starter._actividad _actint = null;
RDebugUtils.currentLine=4587520;
 //BA.debugLineNum = 4587520;BA.debugLine="Sub IntercambiarAtividades (Act1 As Int, Act2 As I";
RDebugUtils.currentLine=4587521;
 //BA.debugLineNum = 4587521;BA.debugLine="Dim ActInt As Actividad";
_actint = new javi.prieto.pictorario.starter._actividad();
RDebugUtils.currentLine=4587522;
 //BA.debugLineNum = 4587522;BA.debugLine="ActInt=ActividadSecuencia(MaxSecuencias,Act1)";
_actint = _actividadsecuencia[_maxsecuencias][_act1];
RDebugUtils.currentLine=4587523;
 //BA.debugLineNum = 4587523;BA.debugLine="ActividadSecuencia(MaxSecuencias,Act2)=ActividadS";
_actividadsecuencia[_maxsecuencias][_act2] = _actividadsecuencia[_maxsecuencias][_act1];
RDebugUtils.currentLine=4587524;
 //BA.debugLineNum = 4587524;BA.debugLine="ActividadSecuencia(MaxSecuencias,Act1)=ActInt";
_actividadsecuencia[_maxsecuencias][_act1] = _actint;
RDebugUtils.currentLine=4587525;
 //BA.debugLineNum = 4587525;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "service_create"))
	return (String) Debug.delegate(processBA, "service_create", null);
RDebugUtils.currentLine=2293760;
 //BA.debugLineNum = 2293760;BA.debugLine="Sub Service_Create";
RDebugUtils.currentLine=2293764;
 //BA.debugLineNum = 2293764;BA.debugLine="NumSecuencias=0";
_numsecuencias = (int) (0);
RDebugUtils.currentLine=2293765;
 //BA.debugLineNum = 2293765;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_kvs._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
RDebugUtils.currentLine=2293767;
 //BA.debugLineNum = 2293767;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
RDebugUtils.currentLine=2293771;
 //BA.debugLineNum = 2293771;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "service_destroy"))
	return (String) Debug.delegate(processBA, "service_destroy", null);
RDebugUtils.currentLine=2752512;
 //BA.debugLineNum = 2752512;BA.debugLine="Sub Service_Destroy";
RDebugUtils.currentLine=2752514;
 //BA.debugLineNum = 2752514;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "service_start"))
	return (String) Debug.delegate(processBA, "service_start", new Object[] {_startingintent});
RDebugUtils.currentLine=2555904;
 //BA.debugLineNum = 2555904;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
RDebugUtils.currentLine=2555906;
 //BA.debugLineNum = 2555906;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
RDebugUtils.currentModule="starter";
if (Debug.shouldDelegate(processBA, "service_taskremoved"))
	return (String) Debug.delegate(processBA, "service_taskremoved", null);
RDebugUtils.currentLine=2621440;
 //BA.debugLineNum = 2621440;BA.debugLine="Sub Service_TaskRemoved";
RDebugUtils.currentLine=2621442;
 //BA.debugLineNum = 2621442;BA.debugLine="End Sub";
return "";
}
}