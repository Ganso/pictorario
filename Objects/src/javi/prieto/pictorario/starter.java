package javi.prieto.pictorario;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
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
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
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
public static int _maxsecuencias = 0;
public static int _maxactividades = 0;
public static int _numsecuencias = 0;
public static int _secuenciaactiva = 0;
public static javi.prieto.pictorario.starter._actividad[][] _actividadsecuencia = null;
public static String[] _descripcionsecuencia = null;
public static int[] _numactividades = null;
public static javi.prieto.pictorario.starter._tablero[] _tablerosecuencia = null;
public static String[] _pictogramasecuencia = null;
public static int[] _colores = null;
public static int _maxcolores = 0;
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
public int color;
public void Initialize() {
IsInitialized = true;
hora_inicio = 0;
minuto_inicio = 0;
hora_fin = 0;
minuto_fin = 0;
Pictograma = "";
descripcion = "";
color = 0;
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
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 192;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 17;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 28;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_maxsecuencias = (int) (10);
 //BA.debugLineNum = 29;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_maxactividades = (int) (20);
 //BA.debugLineNum = 31;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_numsecuencias = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim SecuenciaActiva As Int";
_secuenciaactiva = 0;
 //BA.debugLineNum = 34;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias,MaxActividad";
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
 //BA.debugLineNum = 35;BA.debugLine="Dim DescripcionSecuencia(MaxSecuencias) As String";
_descripcionsecuencia = new String[_maxsecuencias];
java.util.Arrays.fill(_descripcionsecuencia,"");
 //BA.debugLineNum = 36;BA.debugLine="Dim NumActividades(MaxSecuencias) As Int 'Número";
_numactividades = new int[_maxsecuencias];
;
 //BA.debugLineNum = 37;BA.debugLine="Dim TableroSecuencia(MaxSecuencias) As Tablero 'T";
_tablerosecuencia = new javi.prieto.pictorario.starter._tablero[_maxsecuencias];
{
int d0 = _tablerosecuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
_tablerosecuencia[i0] = new javi.prieto.pictorario.starter._tablero();
}
}
;
 //BA.debugLineNum = 38;BA.debugLine="Dim PictogramaSecuencia(MaxSecuencias) As String";
_pictogramasecuencia = new String[_maxsecuencias];
java.util.Arrays.fill(_pictogramasecuencia,"");
 //BA.debugLineNum = 40;BA.debugLine="Dim Colores() As Int 'Colores para las áreas";
_colores = new int[(int) (0)];
;
 //BA.debugLineNum = 41;BA.debugLine="Dim MaxColores=11 As Int";
_maxcolores = (int) (11);
 //BA.debugLineNum = 42;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_colores = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1)};
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 51;BA.debugLine="NumSecuencias=2";
_numsecuencias = (int) (2);
 //BA.debugLineNum = 54;BA.debugLine="NumActividades(0)=9";
_numactividades[(int) (0)] = (int) (9);
 //BA.debugLineNum = 56;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 57;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 58;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 59;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_actividadsecuencia[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 60;BA.debugLine="ActividadSecuencia(0,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (0)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 61;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (0)][(int) (0)].descripcion = "Despertarse";
 //BA.debugLineNum = 62;BA.debugLine="ActividadSecuencia(0,0).color=Colores(0)";
_actividadsecuencia[(int) (0)][(int) (0)].color = _colores[(int) (0)];
 //BA.debugLineNum = 64;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 65;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 66;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_actividadsecuencia[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 67;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 68;BA.debugLine="ActividadSecuencia(0,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 69;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (0)][(int) (1)].descripcion = "Vestirse";
 //BA.debugLineNum = 70;BA.debugLine="ActividadSecuencia(0,1).color=Colores(1)";
_actividadsecuencia[(int) (0)][(int) (1)].color = _colores[(int) (1)];
 //BA.debugLineNum = 72;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_actividadsecuencia[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 73;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 74;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_actividadsecuencia[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 75;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 76;BA.debugLine="ActividadSecuencia(0,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 77;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (0)][(int) (2)].descripcion = "Desayunar";
 //BA.debugLineNum = 78;BA.debugLine="ActividadSecuencia(0,2).color=Colores(2)";
_actividadsecuencia[(int) (0)][(int) (2)].color = _colores[(int) (2)];
 //BA.debugLineNum = 80;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_actividadsecuencia[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 81;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 82;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_actividadsecuencia[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 83;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 84;BA.debugLine="ActividadSecuencia(0,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (0)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 85;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (0)][(int) (3)].descripcion = "Cole";
 //BA.debugLineNum = 86;BA.debugLine="ActividadSecuencia(0,3).color=Colores(3)";
_actividadsecuencia[(int) (0)][(int) (3)].color = _colores[(int) (3)];
 //BA.debugLineNum = 88;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_actividadsecuencia[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 89;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 90;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_actividadsecuencia[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 91;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="ActividadSecuencia(0,4).pictograma=\"comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].Pictograma = "comer";
 //BA.debugLineNum = 93;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_actividadsecuencia[(int) (0)][(int) (4)].descripcion = "Comer";
 //BA.debugLineNum = 94;BA.debugLine="ActividadSecuencia(0,4).color=Colores(4)";
_actividadsecuencia[(int) (0)][(int) (4)].color = _colores[(int) (4)];
 //BA.debugLineNum = 96;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_actividadsecuencia[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 97;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 98;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 99;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 100;BA.debugLine="ActividadSecuencia(0,5).pictograma=\"juguete\"";
_actividadsecuencia[(int) (0)][(int) (5)].Pictograma = "juguete";
 //BA.debugLineNum = 101;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_actividadsecuencia[(int) (0)][(int) (5)].descripcion = "Jugar";
 //BA.debugLineNum = 102;BA.debugLine="ActividadSecuencia(0,5).color=Colores(5)";
_actividadsecuencia[(int) (0)][(int) (5)].color = _colores[(int) (5)];
 //BA.debugLineNum = 104;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 105;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 106;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_actividadsecuencia[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 107;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 108;BA.debugLine="ActividadSecuencia(0,6).pictograma=\"ban_arse\"";
_actividadsecuencia[(int) (0)][(int) (6)].Pictograma = "ban_arse";
 //BA.debugLineNum = 109;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_actividadsecuencia[(int) (0)][(int) (6)].descripcion = "Bañarse";
 //BA.debugLineNum = 110;BA.debugLine="ActividadSecuencia(0,6).color=Colores(6)";
_actividadsecuencia[(int) (0)][(int) (6)].color = _colores[(int) (6)];
 //BA.debugLineNum = 112;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_actividadsecuencia[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 113;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 114;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 115;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_actividadsecuencia[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 116;BA.debugLine="ActividadSecuencia(0,7).pictograma=\"cenar_2\"";
_actividadsecuencia[(int) (0)][(int) (7)].Pictograma = "cenar_2";
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_actividadsecuencia[(int) (0)][(int) (7)].descripcion = "Cenar";
 //BA.debugLineNum = 118;BA.debugLine="ActividadSecuencia(0,7).color=Colores(7)";
_actividadsecuencia[(int) (0)][(int) (7)].color = _colores[(int) (7)];
 //BA.debugLineNum = 120;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 121;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 122;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_actividadsecuencia[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 123;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_actividadsecuencia[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 124;BA.debugLine="ActividadSecuencia(0,8).pictograma=\"dormir_1\"";
_actividadsecuencia[(int) (0)][(int) (8)].Pictograma = "dormir_1";
 //BA.debugLineNum = 125;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_actividadsecuencia[(int) (0)][(int) (8)].descripcion = "Acostarse";
 //BA.debugLineNum = 126;BA.debugLine="ActividadSecuencia(0,8).color=Colores(8)";
_actividadsecuencia[(int) (0)][(int) (8)].color = _colores[(int) (8)];
 //BA.debugLineNum = 128;BA.debugLine="TableroSecuencia(0).tipo=2";
_tablerosecuencia[(int) (0)].tipo = (int) (2);
 //BA.debugLineNum = 129;BA.debugLine="TableroSecuencia(0).indicar_hora=3";
_tablerosecuencia[(int) (0)].indicar_hora = (int) (3);
 //BA.debugLineNum = 130;BA.debugLine="TableroSecuencia(0).tam_icono=10";
_tablerosecuencia[(int) (0)].tam_icono = (int) (10);
 //BA.debugLineNum = 132;BA.debugLine="PictogramaSecuencia(0)=\"colegio\"";
_pictogramasecuencia[(int) (0)] = "colegio";
 //BA.debugLineNum = 134;BA.debugLine="DescripcionSecuencia(0)=\"Secuencia de prueba de d";
_descripcionsecuencia[(int) (0)] = "Secuencia de prueba de día completo";
 //BA.debugLineNum = 138;BA.debugLine="NumActividades(1)=4";
_numactividades[(int) (1)] = (int) (4);
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_actividadsecuencia[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(1,0).hora_fin=8";
_actividadsecuencia[(int) (1)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=15";
_actividadsecuencia[(int) (1)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(1,0).pictograma=\"despertar_1\"";
_actividadsecuencia[(int) (1)][(int) (0)].Pictograma = "despertar_1";
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Despertarse\"";
_actividadsecuencia[(int) (1)][(int) (0)].descripcion = "Despertarse";
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(1,0).color=Colores(0)";
_actividadsecuencia[(int) (1)][(int) (0)].color = _colores[(int) (0)];
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=15";
_actividadsecuencia[(int) (1)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(1,1).hora_fin=8";
_actividadsecuencia[(int) (1)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=30";
_actividadsecuencia[(int) (1)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(1,1).pictograma=\"vestirse\"";
_actividadsecuencia[(int) (1)][(int) (1)].Pictograma = "vestirse";
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Vestirse\"";
_actividadsecuencia[(int) (1)][(int) (1)].descripcion = "Vestirse";
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(1,1).color=Colores(1)";
_actividadsecuencia[(int) (1)][(int) (1)].color = _colores[(int) (1)];
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=8";
_actividadsecuencia[(int) (1)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=30";
_actividadsecuencia[(int) (1)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(1,2).hora_fin=9";
_actividadsecuencia[(int) (1)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=0";
_actividadsecuencia[(int) (1)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(1,2).pictograma=\"desayunar\"";
_actividadsecuencia[(int) (1)][(int) (2)].Pictograma = "desayunar";
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Desayunar\"";
_actividadsecuencia[(int) (1)][(int) (2)].descripcion = "Desayunar";
 //BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(1,2).color=Colores(2)";
_actividadsecuencia[(int) (1)][(int) (2)].color = _colores[(int) (2)];
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=9";
_actividadsecuencia[(int) (1)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=0";
_actividadsecuencia[(int) (1)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(1,3).hora_fin=14";
_actividadsecuencia[(int) (1)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=0";
_actividadsecuencia[(int) (1)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(1,3).pictograma=\"colegio\"";
_actividadsecuencia[(int) (1)][(int) (3)].Pictograma = "colegio";
 //BA.debugLineNum = 169;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Cole\"";
_actividadsecuencia[(int) (1)][(int) (3)].descripcion = "Cole";
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(1,3).color=Colores(3)";
_actividadsecuencia[(int) (1)][(int) (3)].color = _colores[(int) (3)];
 //BA.debugLineNum = 172;BA.debugLine="TableroSecuencia(1).tipo=3";
_tablerosecuencia[(int) (1)].tipo = (int) (3);
 //BA.debugLineNum = 173;BA.debugLine="TableroSecuencia(1).indicar_hora=3";
_tablerosecuencia[(int) (1)].indicar_hora = (int) (3);
 //BA.debugLineNum = 174;BA.debugLine="TableroSecuencia(1).tam_icono=20";
_tablerosecuencia[(int) (1)].tam_icono = (int) (20);
 //BA.debugLineNum = 176;BA.debugLine="PictogramaSecuencia(1)=\"despertar_1\"";
_pictogramasecuencia[(int) (1)] = "despertar_1";
 //BA.debugLineNum = 178;BA.debugLine="DescripcionSecuencia(1)=\"Mañana de día laborable\"";
_descripcionsecuencia[(int) (1)] = "Mañana de día laborable";
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 195;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
}
