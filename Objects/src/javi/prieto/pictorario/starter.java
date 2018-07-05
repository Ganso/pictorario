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
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
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
public static boolean _vvv2 = false;
public static int _vvv3 = 0;
public static String _vvv4 = "";
public static int[] _vvv5 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv2 = null;
public javi.prieto.pictorario.main _vvvvv5 = null;
public javi.prieto.pictorario.visualizacion _vvvv3 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvv2 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvv3 = null;
public javi.prieto.pictorario.acercade _vvvv1 = null;
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
 //BA.debugLineNum = 296;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 297;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return false;
}
public static String  _borrarpictogramas() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _i = 0;
String _nomfich = "";
 //BA.debugLineNum = 337;BA.debugLine="Sub BorrarPictogramas 'Borra todos los pictogramas";
 //BA.debugLineNum = 338;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 339;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 340;BA.debugLine="Dim NomFich As String";
_nomfich = "";
 //BA.debugLineNum = 343;BA.debugLine="fileList=File.ListFiles(DirPictogramas)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(_vvv4);
 //BA.debugLineNum = 344;BA.debugLine="For i=0 To fileList.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (_filelist.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 345;BA.debugLine="NomFich=fileList.Get(i)";
_nomfich = BA.ObjectToString(_filelist.Get(_i));
 //BA.debugLineNum = 346;BA.debugLine="File.Delete(DirPictogramas,NomFich)";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvv4,_nomfich);
 }
};
 //BA.debugLineNum = 350;BA.debugLine="CopiarPictogramasIniciales";
_vvvvv4();
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 92;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 93;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 94;BA.debugLine="DetectadaVersionAntigua=False";
_vvv2 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 95;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv5 = (int)(BA.ObjectToNumber(_v6._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 96;BA.debugLine="If NumSecuencias==0 Then";
if (_vv5==0) { 
 //BA.debugLineNum = 97;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 98;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 100;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv5-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 101;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vv7[_i] = (javi.prieto.pictorario.starter._secuencia)(_v6._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 102;BA.debugLine="If IsNumber(Secuencia(i).Pictograma)==False The";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vv7[_i].pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 103;BA.debugLine="Secuencia(i).Pictograma=IdPictogramaPorDefecto";
_vv7[_i].pictograma = BA.NumberToString(_vvv3);
 //BA.debugLineNum = 104;BA.debugLine="DetectadaVersionAntigua=True";
_vvv2 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 106;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step14 = 1;
final int limit14 = (int) (_vv7[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit14 ;_j = _j + step14 ) {
 //BA.debugLineNum = 107;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vv0[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v6._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 //BA.debugLineNum = 108;BA.debugLine="If IsNumber(ActividadSecuencia(i,j).Pictograma";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vv0[_i][_j].Pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 109;BA.debugLine="ActividadSecuencia(i,j).Pictograma=IdPictogra";
_vv0[_i][_j].Pictograma = BA.NumberToString(_vvv3);
 //BA.debugLineNum = 110;BA.debugLine="DetectadaVersionAntigua=True";
_vvv2 = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 115;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv1 = (int)(BA.ObjectToNumber(_v6._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvv4() throws Exception{
int _i = 0;
String _nombrefich = "";
 //BA.debugLineNum = 319;BA.debugLine="Sub CopiarPictogramasIniciales 'Copia los pictogra";
 //BA.debugLineNum = 320;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 321;BA.debugLine="Dim NombreFich As String";
_nombrefich = "";
 //BA.debugLineNum = 324;BA.debugLine="If File.IsDirectory(File.DirInternal, \"pictograma";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 325;BA.debugLine="File.MakeDir(File.DirInternal, \"pictogramas\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas");
 };
 //BA.debugLineNum = 328;BA.debugLine="For i=0 To PictogramasIniciales.Length-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvv5.length-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 329;BA.debugLine="NombreFich=PictogramasIniciales(i)&\".png\"";
_nombrefich = BA.NumberToString(_vvv5[_i])+".png";
 //BA.debugLineNum = 330;BA.debugLine="If File.Exists(DirPictogramas,NombreFich)==False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_vvv4,_nombrefich)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 332;BA.debugLine="File.Copy(File.DirAssets,NombreFich,DirPictogra";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_nombrefich,_vvv4,_nombrefich);
 };
 }
};
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 304;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 306;BA.debugLine="Secuencia(Seq2).Initialize";
_vv7[_seq2].Initialize();
 //BA.debugLineNum = 307;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vv7[_seq2].Descripcion = _vv7[_seq1].Descripcion;
 //BA.debugLineNum = 308;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vv7[_seq2].num_actividades = _vv7[_seq1].num_actividades;
 //BA.debugLineNum = 309;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vv7[_seq2].pictograma = _vv7[_seq1].pictograma;
 //BA.debugLineNum = 310;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vv7[_seq2].tablero.Initialize();
 //BA.debugLineNum = 311;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vv7[_seq2].tablero.tipo = _vv7[_seq1].tablero.tipo;
 //BA.debugLineNum = 312;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vv7[_seq2].tablero.tam_icono = _vv7[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 313;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vv7[_seq2].tablero.indicar_hora = _vv7[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 314;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step9 = 1;
final int limit9 = (int) (_vv7[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 315;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_vv0[_seq2][_i] = _vv0[_seq1][_i];
 }
};
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 80;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 81;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 82;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v6._put("NumSecuencias",(Object)(_vv5));
 //BA.debugLineNum = 83;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step3 = 1;
final int limit3 = (int) (_vv5-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 84;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v6._put("Secuencia."+BA.NumberToString(_i),(Object)(_vv7[_i]));
 //BA.debugLineNum = 85;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step5 = 1;
final int limit5 = (int) (_vv7[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit5 ;_j = _j + step5 ) {
 //BA.debugLineNum = 86;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v6._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vv0[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 89;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v6._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 120;BA.debugLine="NumSecuencias=3";
_vv5 = (int) (3);
 //BA.debugLineNum = 124;BA.debugLine="Secuencia(0).Initialize";
_vv7[(int) (0)].Initialize();
 //BA.debugLineNum = 125;BA.debugLine="Secuencia(0).num_actividades=9";
_vv7[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 126;BA.debugLine="Secuencia(0).tablero.tipo=2";
_vv7[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 127;BA.debugLine="Secuencia(0).tablero.indicar_hora=1";
_vv7[(int) (0)].tablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 128;BA.debugLine="Secuencia(0).tablero.tam_icono=14";
_vv7[(int) (0)].tablero.tam_icono = (int) (14);
 //BA.debugLineNum = 129;BA.debugLine="Secuencia(0).pictograma=26799";
_vv7[(int) (0)].pictograma = BA.NumberToString(26799);
 //BA.debugLineNum = 130;BA.debugLine="Secuencia(0).descripcion=\"Ejemplo de día completo";
_vv7[(int) (0)].Descripcion = "Ejemplo de día completo";
 //BA.debugLineNum = 132;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vv0[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 133;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vv0[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 134;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vv0[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vv0[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,0).pictograma=31857";
_vv0[(int) (0)][(int) (0)].Pictograma = BA.NumberToString(31857);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vv0[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vv0[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vv0[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 141;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vv0[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vv0[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,1).pictograma=2781";
_vv0[(int) (0)][(int) (1)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vv0[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vv0[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vv0[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vv0[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vv0[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,2).pictograma=28667";
_vv0[(int) (0)][(int) (2)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vv0[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vv0[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vv0[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vv0[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vv0[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,3).pictograma=3082";
_vv0[(int) (0)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vv0[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vv0[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vv0[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vv0[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vv0[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,4).pictograma=28206";
_vv0[(int) (0)][(int) (4)].Pictograma = BA.NumberToString(28206);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vv0[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vv0[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vv0[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 169;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vv0[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vv0[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 171;BA.debugLine="ActividadSecuencia(0,5).pictograma=9813";
_vv0[(int) (0)][(int) (5)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vv0[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vv0[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vv0[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 176;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vv0[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 177;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vv0[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 178;BA.debugLine="ActividadSecuencia(0,6).pictograma=2271";
_vv0[(int) (0)][(int) (6)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 179;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vv0[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 181;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vv0[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 182;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vv0[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 183;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vv0[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 184;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vv0[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 185;BA.debugLine="ActividadSecuencia(0,7).pictograma=28675";
_vv0[(int) (0)][(int) (7)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 186;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vv0[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 188;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vv0[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 189;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vv0[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 190;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vv0[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 191;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vv0[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 192;BA.debugLine="ActividadSecuencia(0,8).pictograma=2369";
_vv0[(int) (0)][(int) (8)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 193;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vv0[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 197;BA.debugLine="Secuencia(1).Initialize";
_vv7[(int) (1)].Initialize();
 //BA.debugLineNum = 198;BA.debugLine="Secuencia(1).num_actividades=6";
_vv7[(int) (1)].num_actividades = (int) (6);
 //BA.debugLineNum = 199;BA.debugLine="Secuencia(1).tablero.tipo=1";
_vv7[(int) (1)].tablero.tipo = (int) (1);
 //BA.debugLineNum = 200;BA.debugLine="Secuencia(1).tablero.indicar_hora=3";
_vv7[(int) (1)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 201;BA.debugLine="Secuencia(1).tablero.tam_icono=17";
_vv7[(int) (1)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 202;BA.debugLine="Secuencia(1).pictograma=9813";
_vv7[(int) (1)].pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 203;BA.debugLine="Secuencia(1).descripcion=\"Tarde después del cole\"";
_vv7[(int) (1)].Descripcion = "Tarde después del cole";
 //BA.debugLineNum = 205;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=15";
_vv0[(int) (1)][(int) (0)].hora_inicio = (int) (15);
 //BA.debugLineNum = 206;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_vv0[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 207;BA.debugLine="ActividadSecuencia(1,0).hora_fin=17";
_vv0[(int) (1)][(int) (0)].hora_fin = (int) (17);
 //BA.debugLineNum = 208;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=0";
_vv0[(int) (1)][(int) (0)].minuto_fin = (int) (0);
 //BA.debugLineNum = 209;BA.debugLine="ActividadSecuencia(1,0).pictograma=9813";
_vv0[(int) (1)][(int) (0)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 210;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Jugar\"";
_vv0[(int) (1)][(int) (0)].Descripcion = "Jugar";
 //BA.debugLineNum = 212;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=17";
_vv0[(int) (1)][(int) (1)].hora_inicio = (int) (17);
 //BA.debugLineNum = 213;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=0";
_vv0[(int) (1)][(int) (1)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 214;BA.debugLine="ActividadSecuencia(1,1).hora_fin=18";
_vv0[(int) (1)][(int) (1)].hora_fin = (int) (18);
 //BA.debugLineNum = 215;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=0";
_vv0[(int) (1)][(int) (1)].minuto_fin = (int) (0);
 //BA.debugLineNum = 216;BA.debugLine="ActividadSecuencia(1,1).pictograma=32556";
_vv0[(int) (1)][(int) (1)].Pictograma = BA.NumberToString(32556);
 //BA.debugLineNum = 217;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Hacer los de";
_vv0[(int) (1)][(int) (1)].Descripcion = "Hacer los deberes";
 //BA.debugLineNum = 219;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=18";
_vv0[(int) (1)][(int) (2)].hora_inicio = (int) (18);
 //BA.debugLineNum = 220;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=0";
_vv0[(int) (1)][(int) (2)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 221;BA.debugLine="ActividadSecuencia(1,2).hora_fin=20";
_vv0[(int) (1)][(int) (2)].hora_fin = (int) (20);
 //BA.debugLineNum = 222;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=30";
_vv0[(int) (1)][(int) (2)].minuto_fin = (int) (30);
 //BA.debugLineNum = 223;BA.debugLine="ActividadSecuencia(1,2).pictograma=9813";
_vv0[(int) (1)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 224;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Jugar\"";
_vv0[(int) (1)][(int) (2)].Descripcion = "Jugar";
 //BA.debugLineNum = 226;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=20";
_vv0[(int) (1)][(int) (3)].hora_inicio = (int) (20);
 //BA.debugLineNum = 227;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=30";
_vv0[(int) (1)][(int) (3)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 228;BA.debugLine="ActividadSecuencia(1,3).hora_fin=21";
_vv0[(int) (1)][(int) (3)].hora_fin = (int) (21);
 //BA.debugLineNum = 229;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=00";
_vv0[(int) (1)][(int) (3)].minuto_fin = (int) (00);
 //BA.debugLineNum = 230;BA.debugLine="ActividadSecuencia(1,3).pictograma=2271";
_vv0[(int) (1)][(int) (3)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 231;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Bañarse\"";
_vv0[(int) (1)][(int) (3)].Descripcion = "Bañarse";
 //BA.debugLineNum = 233;BA.debugLine="ActividadSecuencia(1,4).hora_inicio=21";
_vv0[(int) (1)][(int) (4)].hora_inicio = (int) (21);
 //BA.debugLineNum = 234;BA.debugLine="ActividadSecuencia(1,4).minuto_inicio=0";
_vv0[(int) (1)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 235;BA.debugLine="ActividadSecuencia(1,4).hora_fin=22";
_vv0[(int) (1)][(int) (4)].hora_fin = (int) (22);
 //BA.debugLineNum = 236;BA.debugLine="ActividadSecuencia(1,4).minuto_fin=0";
_vv0[(int) (1)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 237;BA.debugLine="ActividadSecuencia(1,4).pictograma=28675";
_vv0[(int) (1)][(int) (4)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 238;BA.debugLine="ActividadSecuencia(1,4).descripcion=\"Cenar\"";
_vv0[(int) (1)][(int) (4)].Descripcion = "Cenar";
 //BA.debugLineNum = 240;BA.debugLine="ActividadSecuencia(1,5).hora_inicio=22";
_vv0[(int) (1)][(int) (5)].hora_inicio = (int) (22);
 //BA.debugLineNum = 241;BA.debugLine="ActividadSecuencia(1,5).minuto_inicio=0";
_vv0[(int) (1)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 242;BA.debugLine="ActividadSecuencia(1,5).hora_fin=22";
_vv0[(int) (1)][(int) (5)].hora_fin = (int) (22);
 //BA.debugLineNum = 243;BA.debugLine="ActividadSecuencia(1,5).minuto_fin=30";
_vv0[(int) (1)][(int) (5)].minuto_fin = (int) (30);
 //BA.debugLineNum = 244;BA.debugLine="ActividadSecuencia(1,5).pictograma=2369";
_vv0[(int) (1)][(int) (5)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 245;BA.debugLine="ActividadSecuencia(1,5).descripcion=\"Acostarse\"";
_vv0[(int) (1)][(int) (5)].Descripcion = "Acostarse";
 //BA.debugLineNum = 249;BA.debugLine="Secuencia(2).Initialize";
_vv7[(int) (2)].Initialize();
 //BA.debugLineNum = 250;BA.debugLine="Secuencia(2).num_actividades=4";
_vv7[(int) (2)].num_actividades = (int) (4);
 //BA.debugLineNum = 251;BA.debugLine="Secuencia(2).tablero.tipo=3";
_vv7[(int) (2)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 252;BA.debugLine="Secuencia(2).tablero.indicar_hora=0";
_vv7[(int) (2)].tablero.indicar_hora = (int) (0);
 //BA.debugLineNum = 253;BA.debugLine="Secuencia(2).tablero.tam_icono=17";
_vv7[(int) (2)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 254;BA.debugLine="Secuencia(2).pictograma=3082";
_vv7[(int) (2)].pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 255;BA.debugLine="Secuencia(2).descripcion=\"Antes de ir al cole\"";
_vv7[(int) (2)].Descripcion = "Antes de ir al cole";
 //BA.debugLineNum = 257;BA.debugLine="ActividadSecuencia(2,0).hora_inicio=8";
_vv0[(int) (2)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 258;BA.debugLine="ActividadSecuencia(2,0).minuto_inicio=0";
_vv0[(int) (2)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 259;BA.debugLine="ActividadSecuencia(2,0).hora_fin=8";
_vv0[(int) (2)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 260;BA.debugLine="ActividadSecuencia(2,0).minuto_fin=15";
_vv0[(int) (2)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 261;BA.debugLine="ActividadSecuencia(2,0).pictograma=2781";
_vv0[(int) (2)][(int) (0)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 262;BA.debugLine="ActividadSecuencia(2,0).descripcion=\"Vestirse\"";
_vv0[(int) (2)][(int) (0)].Descripcion = "Vestirse";
 //BA.debugLineNum = 264;BA.debugLine="ActividadSecuencia(2,1).hora_inicio=8";
_vv0[(int) (2)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 265;BA.debugLine="ActividadSecuencia(2,1).minuto_inicio=15";
_vv0[(int) (2)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 266;BA.debugLine="ActividadSecuencia(2,1).hora_fin=8";
_vv0[(int) (2)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 267;BA.debugLine="ActividadSecuencia(2,1).minuto_fin=30";
_vv0[(int) (2)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 268;BA.debugLine="ActividadSecuencia(2,1).pictograma=28667";
_vv0[(int) (2)][(int) (1)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 269;BA.debugLine="ActividadSecuencia(2,1).descripcion=\"Desayunar\"";
_vv0[(int) (2)][(int) (1)].Descripcion = "Desayunar";
 //BA.debugLineNum = 271;BA.debugLine="ActividadSecuencia(2,2).hora_inicio=8";
_vv0[(int) (2)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 272;BA.debugLine="ActividadSecuencia(2,2).minuto_inicio=30";
_vv0[(int) (2)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 273;BA.debugLine="ActividadSecuencia(2,2).hora_fin=8";
_vv0[(int) (2)][(int) (2)].hora_fin = (int) (8);
 //BA.debugLineNum = 274;BA.debugLine="ActividadSecuencia(2,2).minuto_fin=35";
_vv0[(int) (2)][(int) (2)].minuto_fin = (int) (35);
 //BA.debugLineNum = 275;BA.debugLine="ActividadSecuencia(2,2).pictograma=9813";
_vv0[(int) (2)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 276;BA.debugLine="ActividadSecuencia(2,2).descripcion=\"Coger un jug";
_vv0[(int) (2)][(int) (2)].Descripcion = "Coger un juguete";
 //BA.debugLineNum = 278;BA.debugLine="ActividadSecuencia(2,3).hora_inicio=8";
_vv0[(int) (2)][(int) (3)].hora_inicio = (int) (8);
 //BA.debugLineNum = 279;BA.debugLine="ActividadSecuencia(2,3).minuto_inicio=35";
_vv0[(int) (2)][(int) (3)].minuto_inicio = (int) (35);
 //BA.debugLineNum = 280;BA.debugLine="ActividadSecuencia(2,3).hora_fin=9";
_vv0[(int) (2)][(int) (3)].hora_fin = (int) (9);
 //BA.debugLineNum = 281;BA.debugLine="ActividadSecuencia(2,3).minuto_fin=0";
_vv0[(int) (2)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 282;BA.debugLine="ActividadSecuencia(2,3).pictograma=3082";
_vv0[(int) (2)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 283;BA.debugLine="ActividadSecuencia(2,3).descripcion=\"Ir andando a";
_vv0[(int) (2)][(int) (3)].Descripcion = "Ir andando al cole";
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim CambiosVersion As String";
_v5 = "";
 //BA.debugLineNum = 9;BA.debugLine="CambiosVersion= _ 	\"- Actualizada la dirección de";
_v5 = BA.__b (new byte[] {2,35,48,6,125,53,34,13,127,104,93,16,84,43,8,25,104,38,57,8,127,106,90,82,-16,-72,71,92,49,60,56,92,106,-117,-114,87,67,121,72,26,35,98,61,13,124,106,85,95,4,60}, 199977)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {2,35,53,-17,100,48,34,-25,108,115,20,-13,80,103,76,-8,-117,-15,60,-16,125,102,17,-66,70,110,79,-21,48,121,37,-65,120,45,71,-16,84,115,16,-14,53,43,36,-21,107,110,66,-16,15,119,93,-63,43,48,30,-80,97,33}, 214531)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {2,33,106,-23,110,39,102,-21,101,48,10,-5,64,122,71,-8,59,96,98,-15,58,103,15,-2,69,96,13,-20,52,55,106,-29,125,41,29,21,-107,122,31,-1,35,96,103,-19,107,100,1,-10,18,48,71,-18,98,35,101,-29,103,100,5,-66,64,125,17,-1,43,125,127,-23,102,121,69,-67,82,122,21,-19,41,110,119,-92,124,113,24,-19,69,113,77,-17,59,58,116,-27,57,113,5,-8}, 972587);
 //BA.debugLineNum = 14;BA.debugLine="Dim kvs As KeyValueStore";
_v6 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 18;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 20;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 31;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 35;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_v7 = (int) (10);
 //BA.debugLineNum = 36;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_v0 = (int) (20);
 //BA.debugLineNum = 40;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv1 = new String[(int) (4)];
java.util.Arrays.fill(_vv1,"");
 //BA.debugLineNum = 41;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv1 = new String[]{BA.__b (new byte[] {125,103,-55,119,99,97,-13,121,54,34,-38,97,21,34,-35,100,-117,-14,-27,105,123,33}, 736996),BA.__b (new byte[] {125,102,-116,57,99,96,-74,55,54,35,-97,47,21,35,-127,42,58,38,-92,96}, 402353),BA.__b (new byte[] {125,103,40,-67,99,97,18,-77,54,33,61,-85}, 568144),BA.__b (new byte[] {110,112,66,-114,41,37,118,-59,101,118,15,-123,80,100,87,-107,41}, 516294)};
 //BA.debugLineNum = 43;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv2 = new String[(int) (4)];
java.util.Arrays.fill(_vv2,"");
 //BA.debugLineNum = 44;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv2 = new String[]{BA.__b (new byte[] {124,106,85,64,96,46,109,13,117,115,21,24,-10,-72,64}, 103247),BA.__b (new byte[] {102,108,-77,19,106,32,-105,94,126,124,-24,10}, 827253),BA.__b (new byte[] {102,109,-37,52,106,33,-1,121,126,125,-128,45,21,114,-118,45,33,44,-21,54,117,122}, 325254),BA.__b (new byte[] {102,108,40,86,106,32,12,27,126,124,115,79,25,42,52,75,38,54,25,79,105,40,125,65,64,111,115,83,59,60,6,85}, 581271)};
 //BA.debugLineNum = 48;BA.debugLine="Dim MaxColores=20 As Int";
_vv3 = (int) (20);
 //BA.debugLineNum = 49;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv4 = new int[_vv3];
;
 //BA.debugLineNum = 50;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_vv4 = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 54;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv5 = 0;
 //BA.debugLineNum = 55;BA.debugLine="Dim SecuenciaActiva As Int";
_vv6 = 0;
 //BA.debugLineNum = 56;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vv7 = new javi.prieto.pictorario.starter._secuencia[(int) (_v7+1)];
{
int d0 = _vv7.length;
for (int i0 = 0;i0 < d0;i0++) {
_vv7[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 57;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
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
 //BA.debugLineNum = 58;BA.debugLine="Dim VersionInstalada As Int";
_vvv1 = 0;
 //BA.debugLineNum = 59;BA.debugLine="Dim DetectadaVersionAntigua As Boolean";
_vvv2 = false;
 //BA.debugLineNum = 63;BA.debugLine="Dim IdPictogramaPorDefecto As Int 'Imagen por def";
_vvv3 = 0;
 //BA.debugLineNum = 64;BA.debugLine="Dim DirPictogramas As String 'Directorio de traba";
_vvv4 = "";
 //BA.debugLineNum = 65;BA.debugLine="Dim PictogramasIniciales(12) As Int 'Listado de p";
_vvv5 = new int[(int) (12)];
;
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 70;BA.debugLine="NumSecuencias=0";
_vv5 = (int) (0);
 //BA.debugLineNum = 71;BA.debugLine="DirPictogramas=File.Combine(File.DirInternal,\"/pi";
_vvv4 = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"/pictogramas");
 //BA.debugLineNum = 72;BA.debugLine="IdPictogramaPorDefecto=\"7229\" 'El pictograma por";
_vvv3 = (int)(Double.parseDouble("7229"));
 //BA.debugLineNum = 73;BA.debugLine="PictogramasIniciales = Array As Int (31857,2781,2";
_vvv5 = new int[]{(int) (31857),(int) (2781),(int) (28667),(int) (3082),(int) (28206),(int) (9813),(int) (2271),(int) (28675),(int) (2369),(int) (7229),(int) (26799),(int) (32556)};
 //BA.debugLineNum = 74;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v6._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 76;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 77;BA.debugLine="CopiarPictogramasIniciales";
_vvvvv4();
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 300;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 287;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 291;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
}
