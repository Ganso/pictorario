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
 //BA.debugLineNum = 299;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 300;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return false;
}
public static String  _borrarpictogramas() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _i = 0;
String _nomfich = "";
 //BA.debugLineNum = 340;BA.debugLine="Sub BorrarPictogramas 'Borra todos los pictogramas";
 //BA.debugLineNum = 341;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 342;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 343;BA.debugLine="Dim NomFich As String";
_nomfich = "";
 //BA.debugLineNum = 346;BA.debugLine="fileList=File.ListFiles(DirPictogramas)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(_vvv4);
 //BA.debugLineNum = 347;BA.debugLine="For i=0 To fileList.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (_filelist.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 348;BA.debugLine="NomFich=fileList.Get(i)";
_nomfich = BA.ObjectToString(_filelist.Get(_i));
 //BA.debugLineNum = 349;BA.debugLine="File.Delete(DirPictogramas,NomFich)";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvv4,_nomfich);
 }
};
 //BA.debugLineNum = 353;BA.debugLine="CopiarPictogramasIniciales";
_vvvvv4();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 95;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 96;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 97;BA.debugLine="DetectadaVersionAntigua=False";
_vvv2 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 98;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv5 = (int)(BA.ObjectToNumber(_v6._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 99;BA.debugLine="If NumSecuencias==0 Then";
if (_vv5==0) { 
 //BA.debugLineNum = 100;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 101;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 103;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv5-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 104;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vv7[_i] = (javi.prieto.pictorario.starter._secuencia)(_v6._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 105;BA.debugLine="If IsNumber(Secuencia(i).Pictograma)==False The";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vv7[_i].pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 106;BA.debugLine="Secuencia(i).Pictograma=IdPictogramaPorDefecto";
_vv7[_i].pictograma = BA.NumberToString(_vvv3);
 //BA.debugLineNum = 107;BA.debugLine="DetectadaVersionAntigua=True";
_vvv2 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 109;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step14 = 1;
final int limit14 = (int) (_vv7[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit14 ;_j = _j + step14 ) {
 //BA.debugLineNum = 110;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vv0[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v6._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 //BA.debugLineNum = 111;BA.debugLine="If IsNumber(ActividadSecuencia(i,j).Pictograma";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vv0[_i][_j].Pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 112;BA.debugLine="ActividadSecuencia(i,j).Pictograma=IdPictogra";
_vv0[_i][_j].Pictograma = BA.NumberToString(_vvv3);
 //BA.debugLineNum = 113;BA.debugLine="DetectadaVersionAntigua=True";
_vvv2 = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 118;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv1 = (int)(BA.ObjectToNumber(_v6._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvv4() throws Exception{
int _i = 0;
String _nombrefich = "";
 //BA.debugLineNum = 322;BA.debugLine="Sub CopiarPictogramasIniciales 'Copia los pictogra";
 //BA.debugLineNum = 323;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 324;BA.debugLine="Dim NombreFich As String";
_nombrefich = "";
 //BA.debugLineNum = 327;BA.debugLine="If File.IsDirectory(File.DirInternal, \"pictograma";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 328;BA.debugLine="File.MakeDir(File.DirInternal, \"pictogramas\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas");
 };
 //BA.debugLineNum = 331;BA.debugLine="For i=0 To PictogramasIniciales.Length-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvv5.length-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 332;BA.debugLine="NombreFich=PictogramasIniciales(i)&\".png\"";
_nombrefich = BA.NumberToString(_vvv5[_i])+".png";
 //BA.debugLineNum = 333;BA.debugLine="If File.Exists(DirPictogramas,NombreFich)==False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_vvv4,_nombrefich)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 335;BA.debugLine="File.Copy(File.DirAssets,NombreFich,DirPictogra";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_nombrefich,_vvv4,_nombrefich);
 };
 }
};
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 307;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 309;BA.debugLine="Secuencia(Seq2).Initialize";
_vv7[_seq2].Initialize();
 //BA.debugLineNum = 310;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vv7[_seq2].Descripcion = _vv7[_seq1].Descripcion;
 //BA.debugLineNum = 311;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vv7[_seq2].num_actividades = _vv7[_seq1].num_actividades;
 //BA.debugLineNum = 312;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vv7[_seq2].pictograma = _vv7[_seq1].pictograma;
 //BA.debugLineNum = 313;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vv7[_seq2].tablero.Initialize();
 //BA.debugLineNum = 314;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vv7[_seq2].tablero.tipo = _vv7[_seq1].tablero.tipo;
 //BA.debugLineNum = 315;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vv7[_seq2].tablero.tam_icono = _vv7[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 316;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vv7[_seq2].tablero.indicar_hora = _vv7[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 317;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step9 = 1;
final int limit9 = (int) (_vv7[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 318;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_vv0[_seq2][_i] = _vv0[_seq1][_i];
 }
};
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 83;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 84;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 85;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v6._put("NumSecuencias",(Object)(_vv5));
 //BA.debugLineNum = 86;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step3 = 1;
final int limit3 = (int) (_vv5-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 87;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v6._put("Secuencia."+BA.NumberToString(_i),(Object)(_vv7[_i]));
 //BA.debugLineNum = 88;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step5 = 1;
final int limit5 = (int) (_vv7[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit5 ;_j = _j + step5 ) {
 //BA.debugLineNum = 89;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v6._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vv0[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 92;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v6._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 123;BA.debugLine="NumSecuencias=3";
_vv5 = (int) (3);
 //BA.debugLineNum = 127;BA.debugLine="Secuencia(0).Initialize";
_vv7[(int) (0)].Initialize();
 //BA.debugLineNum = 128;BA.debugLine="Secuencia(0).num_actividades=9";
_vv7[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 129;BA.debugLine="Secuencia(0).tablero.tipo=2";
_vv7[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 130;BA.debugLine="Secuencia(0).tablero.indicar_hora=1";
_vv7[(int) (0)].tablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 131;BA.debugLine="Secuencia(0).tablero.tam_icono=14";
_vv7[(int) (0)].tablero.tam_icono = (int) (14);
 //BA.debugLineNum = 132;BA.debugLine="Secuencia(0).pictograma=26799";
_vv7[(int) (0)].pictograma = BA.NumberToString(26799);
 //BA.debugLineNum = 133;BA.debugLine="Secuencia(0).descripcion=\"Ejemplo de día completo";
_vv7[(int) (0)].Descripcion = "Ejemplo de día completo";
 //BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vv0[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 136;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vv0[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vv0[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 138;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vv0[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 139;BA.debugLine="ActividadSecuencia(0,0).pictograma=31857";
_vv0[(int) (0)][(int) (0)].Pictograma = BA.NumberToString(31857);
 //BA.debugLineNum = 140;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vv0[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 142;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vv0[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 143;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vv0[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vv0[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vv0[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,1).pictograma=2781";
_vv0[(int) (0)][(int) (1)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vv0[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vv0[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 150;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vv0[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vv0[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vv0[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,2).pictograma=28667";
_vv0[(int) (0)][(int) (2)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vv0[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vv0[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 157;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vv0[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vv0[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vv0[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,3).pictograma=3082";
_vv0[(int) (0)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vv0[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vv0[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 164;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vv0[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vv0[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vv0[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,4).pictograma=28206";
_vv0[(int) (0)][(int) (4)].Pictograma = BA.NumberToString(28206);
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vv0[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vv0[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 171;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vv0[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vv0[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 173;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vv0[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(0,5).pictograma=9813";
_vv0[(int) (0)][(int) (5)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vv0[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 177;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vv0[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 178;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vv0[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 179;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vv0[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 180;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vv0[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 181;BA.debugLine="ActividadSecuencia(0,6).pictograma=2271";
_vv0[(int) (0)][(int) (6)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 182;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vv0[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 184;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vv0[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 185;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vv0[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 186;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vv0[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 187;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vv0[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 188;BA.debugLine="ActividadSecuencia(0,7).pictograma=28675";
_vv0[(int) (0)][(int) (7)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 189;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vv0[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 191;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vv0[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 192;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vv0[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 193;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vv0[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 194;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vv0[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 195;BA.debugLine="ActividadSecuencia(0,8).pictograma=2369";
_vv0[(int) (0)][(int) (8)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 196;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vv0[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 200;BA.debugLine="Secuencia(1).Initialize";
_vv7[(int) (1)].Initialize();
 //BA.debugLineNum = 201;BA.debugLine="Secuencia(1).num_actividades=6";
_vv7[(int) (1)].num_actividades = (int) (6);
 //BA.debugLineNum = 202;BA.debugLine="Secuencia(1).tablero.tipo=1";
_vv7[(int) (1)].tablero.tipo = (int) (1);
 //BA.debugLineNum = 203;BA.debugLine="Secuencia(1).tablero.indicar_hora=3";
_vv7[(int) (1)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 204;BA.debugLine="Secuencia(1).tablero.tam_icono=17";
_vv7[(int) (1)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 205;BA.debugLine="Secuencia(1).pictograma=9813";
_vv7[(int) (1)].pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 206;BA.debugLine="Secuencia(1).descripcion=\"Tarde después del cole\"";
_vv7[(int) (1)].Descripcion = "Tarde después del cole";
 //BA.debugLineNum = 208;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=15";
_vv0[(int) (1)][(int) (0)].hora_inicio = (int) (15);
 //BA.debugLineNum = 209;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_vv0[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 210;BA.debugLine="ActividadSecuencia(1,0).hora_fin=17";
_vv0[(int) (1)][(int) (0)].hora_fin = (int) (17);
 //BA.debugLineNum = 211;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=0";
_vv0[(int) (1)][(int) (0)].minuto_fin = (int) (0);
 //BA.debugLineNum = 212;BA.debugLine="ActividadSecuencia(1,0).pictograma=9813";
_vv0[(int) (1)][(int) (0)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 213;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Jugar\"";
_vv0[(int) (1)][(int) (0)].Descripcion = "Jugar";
 //BA.debugLineNum = 215;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=17";
_vv0[(int) (1)][(int) (1)].hora_inicio = (int) (17);
 //BA.debugLineNum = 216;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=0";
_vv0[(int) (1)][(int) (1)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 217;BA.debugLine="ActividadSecuencia(1,1).hora_fin=18";
_vv0[(int) (1)][(int) (1)].hora_fin = (int) (18);
 //BA.debugLineNum = 218;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=0";
_vv0[(int) (1)][(int) (1)].minuto_fin = (int) (0);
 //BA.debugLineNum = 219;BA.debugLine="ActividadSecuencia(1,1).pictograma=32556";
_vv0[(int) (1)][(int) (1)].Pictograma = BA.NumberToString(32556);
 //BA.debugLineNum = 220;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Hacer los de";
_vv0[(int) (1)][(int) (1)].Descripcion = "Hacer los deberes";
 //BA.debugLineNum = 222;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=18";
_vv0[(int) (1)][(int) (2)].hora_inicio = (int) (18);
 //BA.debugLineNum = 223;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=0";
_vv0[(int) (1)][(int) (2)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 224;BA.debugLine="ActividadSecuencia(1,2).hora_fin=20";
_vv0[(int) (1)][(int) (2)].hora_fin = (int) (20);
 //BA.debugLineNum = 225;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=30";
_vv0[(int) (1)][(int) (2)].minuto_fin = (int) (30);
 //BA.debugLineNum = 226;BA.debugLine="ActividadSecuencia(1,2).pictograma=9813";
_vv0[(int) (1)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 227;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Jugar\"";
_vv0[(int) (1)][(int) (2)].Descripcion = "Jugar";
 //BA.debugLineNum = 229;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=20";
_vv0[(int) (1)][(int) (3)].hora_inicio = (int) (20);
 //BA.debugLineNum = 230;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=30";
_vv0[(int) (1)][(int) (3)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 231;BA.debugLine="ActividadSecuencia(1,3).hora_fin=21";
_vv0[(int) (1)][(int) (3)].hora_fin = (int) (21);
 //BA.debugLineNum = 232;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=00";
_vv0[(int) (1)][(int) (3)].minuto_fin = (int) (00);
 //BA.debugLineNum = 233;BA.debugLine="ActividadSecuencia(1,3).pictograma=2271";
_vv0[(int) (1)][(int) (3)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 234;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Bañarse\"";
_vv0[(int) (1)][(int) (3)].Descripcion = "Bañarse";
 //BA.debugLineNum = 236;BA.debugLine="ActividadSecuencia(1,4).hora_inicio=21";
_vv0[(int) (1)][(int) (4)].hora_inicio = (int) (21);
 //BA.debugLineNum = 237;BA.debugLine="ActividadSecuencia(1,4).minuto_inicio=0";
_vv0[(int) (1)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 238;BA.debugLine="ActividadSecuencia(1,4).hora_fin=22";
_vv0[(int) (1)][(int) (4)].hora_fin = (int) (22);
 //BA.debugLineNum = 239;BA.debugLine="ActividadSecuencia(1,4).minuto_fin=0";
_vv0[(int) (1)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 240;BA.debugLine="ActividadSecuencia(1,4).pictograma=28675";
_vv0[(int) (1)][(int) (4)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 241;BA.debugLine="ActividadSecuencia(1,4).descripcion=\"Cenar\"";
_vv0[(int) (1)][(int) (4)].Descripcion = "Cenar";
 //BA.debugLineNum = 243;BA.debugLine="ActividadSecuencia(1,5).hora_inicio=22";
_vv0[(int) (1)][(int) (5)].hora_inicio = (int) (22);
 //BA.debugLineNum = 244;BA.debugLine="ActividadSecuencia(1,5).minuto_inicio=0";
_vv0[(int) (1)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 245;BA.debugLine="ActividadSecuencia(1,5).hora_fin=22";
_vv0[(int) (1)][(int) (5)].hora_fin = (int) (22);
 //BA.debugLineNum = 246;BA.debugLine="ActividadSecuencia(1,5).minuto_fin=30";
_vv0[(int) (1)][(int) (5)].minuto_fin = (int) (30);
 //BA.debugLineNum = 247;BA.debugLine="ActividadSecuencia(1,5).pictograma=2369";
_vv0[(int) (1)][(int) (5)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 248;BA.debugLine="ActividadSecuencia(1,5).descripcion=\"Acostarse\"";
_vv0[(int) (1)][(int) (5)].Descripcion = "Acostarse";
 //BA.debugLineNum = 252;BA.debugLine="Secuencia(2).Initialize";
_vv7[(int) (2)].Initialize();
 //BA.debugLineNum = 253;BA.debugLine="Secuencia(2).num_actividades=4";
_vv7[(int) (2)].num_actividades = (int) (4);
 //BA.debugLineNum = 254;BA.debugLine="Secuencia(2).tablero.tipo=3";
_vv7[(int) (2)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 255;BA.debugLine="Secuencia(2).tablero.indicar_hora=0";
_vv7[(int) (2)].tablero.indicar_hora = (int) (0);
 //BA.debugLineNum = 256;BA.debugLine="Secuencia(2).tablero.tam_icono=17";
_vv7[(int) (2)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 257;BA.debugLine="Secuencia(2).pictograma=3082";
_vv7[(int) (2)].pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 258;BA.debugLine="Secuencia(2).descripcion=\"Antes de ir al cole\"";
_vv7[(int) (2)].Descripcion = "Antes de ir al cole";
 //BA.debugLineNum = 260;BA.debugLine="ActividadSecuencia(2,0).hora_inicio=8";
_vv0[(int) (2)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 261;BA.debugLine="ActividadSecuencia(2,0).minuto_inicio=0";
_vv0[(int) (2)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 262;BA.debugLine="ActividadSecuencia(2,0).hora_fin=8";
_vv0[(int) (2)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 263;BA.debugLine="ActividadSecuencia(2,0).minuto_fin=15";
_vv0[(int) (2)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 264;BA.debugLine="ActividadSecuencia(2,0).pictograma=2781";
_vv0[(int) (2)][(int) (0)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 265;BA.debugLine="ActividadSecuencia(2,0).descripcion=\"Vestirse\"";
_vv0[(int) (2)][(int) (0)].Descripcion = "Vestirse";
 //BA.debugLineNum = 267;BA.debugLine="ActividadSecuencia(2,1).hora_inicio=8";
_vv0[(int) (2)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 268;BA.debugLine="ActividadSecuencia(2,1).minuto_inicio=15";
_vv0[(int) (2)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 269;BA.debugLine="ActividadSecuencia(2,1).hora_fin=8";
_vv0[(int) (2)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 270;BA.debugLine="ActividadSecuencia(2,1).minuto_fin=30";
_vv0[(int) (2)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 271;BA.debugLine="ActividadSecuencia(2,1).pictograma=28667";
_vv0[(int) (2)][(int) (1)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 272;BA.debugLine="ActividadSecuencia(2,1).descripcion=\"Desayunar\"";
_vv0[(int) (2)][(int) (1)].Descripcion = "Desayunar";
 //BA.debugLineNum = 274;BA.debugLine="ActividadSecuencia(2,2).hora_inicio=8";
_vv0[(int) (2)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 275;BA.debugLine="ActividadSecuencia(2,2).minuto_inicio=30";
_vv0[(int) (2)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 276;BA.debugLine="ActividadSecuencia(2,2).hora_fin=8";
_vv0[(int) (2)][(int) (2)].hora_fin = (int) (8);
 //BA.debugLineNum = 277;BA.debugLine="ActividadSecuencia(2,2).minuto_fin=35";
_vv0[(int) (2)][(int) (2)].minuto_fin = (int) (35);
 //BA.debugLineNum = 278;BA.debugLine="ActividadSecuencia(2,2).pictograma=9813";
_vv0[(int) (2)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 279;BA.debugLine="ActividadSecuencia(2,2).descripcion=\"Coger un jug";
_vv0[(int) (2)][(int) (2)].Descripcion = "Coger un juguete";
 //BA.debugLineNum = 281;BA.debugLine="ActividadSecuencia(2,3).hora_inicio=8";
_vv0[(int) (2)][(int) (3)].hora_inicio = (int) (8);
 //BA.debugLineNum = 282;BA.debugLine="ActividadSecuencia(2,3).minuto_inicio=35";
_vv0[(int) (2)][(int) (3)].minuto_inicio = (int) (35);
 //BA.debugLineNum = 283;BA.debugLine="ActividadSecuencia(2,3).hora_fin=9";
_vv0[(int) (2)][(int) (3)].hora_fin = (int) (9);
 //BA.debugLineNum = 284;BA.debugLine="ActividadSecuencia(2,3).minuto_fin=0";
_vv0[(int) (2)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 285;BA.debugLine="ActividadSecuencia(2,3).pictograma=3082";
_vv0[(int) (2)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 286;BA.debugLine="ActividadSecuencia(2,3).descripcion=\"Ir andando a";
_vv0[(int) (2)][(int) (3)].Descripcion = "Ir andando al cole";
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim CambiosVersion As String";
_v5 = "";
 //BA.debugLineNum = 12;BA.debugLine="CambiosVersion= _ 	\"- Actualizada la dirección de";
_v5 = BA.__b (new byte[] {2,33,106,-30,125,55,120,-23,127,106,7,-12,84,41,82,-3,104,36,99,-20,127,104,0,-74,-16,-70,29,-72,49,62,98,-72,106,-119,-44,-77,67,123,18,-2,35,96,103,-23,124,104,15,-69,4,62}, 992292)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {2,35,-66,113,100,48,-87,121,108,115,-97,109,80,103,-57,102,-117,-15,-73,110,125,102,-102,32,70,110,-60,117,48,121,-82,33,120,45,-52,110,84,115,-101,108,53,43,-81,117,107,110,-55,110,15,119,-42,95,43,48,-107,46,97,33}, 434150)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {2,34,28,-13,110,36,16,-15,101,51,124,-31,64,121,49,-30,59,99,20,-21,58,100,121,-28,69,99,123,-10,52,52,28,-7,125,42,107,15,-107,121,105,-27,35,99,17,-9,107,103,119,-20,18,51,49,-12,98,32,19,-7,103,103,115,-92,64,126,103,-27,43,126,9,-13,102,122,51,-89,82,121,99,-9,41,109,1,-66,124,114,110,-9,69,114,59,-11,59,57,2,-1,57,114,115,-30}, 603478);
 //BA.debugLineNum = 17;BA.debugLine="Dim kvs As KeyValueStore";
_v6 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 21;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 23;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 34;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 38;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_v7 = (int) (10);
 //BA.debugLineNum = 39;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_v0 = (int) (20);
 //BA.debugLineNum = 43;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv1 = new String[(int) (4)];
java.util.Arrays.fill(_vv1,"");
 //BA.debugLineNum = 44;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv1 = new String[]{BA.__b (new byte[] {125,103,-122,31,99,97,-68,17,54,34,-107,9,21,34,-110,12,-117,-14,-86,1,123,33}, 879296),BA.__b (new byte[] {125,103,-58,87,99,97,-4,89,54,34,-43,65,21,34,-53,68,58,39,-18,14}, 764100),BA.__b (new byte[] {125,102,-106,-68,99,96,-84,-78,54,32,-125,-86}, 449862),BA.__b (new byte[] {110,112,12,-115,41,37,56,-58,101,118,65,-122,80,100,25,-106,41}, 641725)};
 //BA.debugLineNum = 46;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv2 = new String[(int) (4)];
java.util.Arrays.fill(_vv2,"");
 //BA.debugLineNum = 47;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv2 = new String[]{BA.__b (new byte[] {124,106,53,-19,96,46,13,-96,117,115,117,-75,-10,-72,32}, 161174),BA.__b (new byte[] {102,109,26,-7,106,33,62,-76,126,125,65,-32}, 212897),BA.__b (new byte[] {102,108,22,-59,106,32,50,-120,126,124,77,-36,21,115,71,-36,33,45,38,-57,117,123}, 664504),BA.__b (new byte[] {102,109,-104,29,106,33,-68,80,126,125,-61,4,25,43,-124,0,38,55,-87,4,105,41,-51,10,64,110,-61,24,59,61,-74,30}, 438436)};
 //BA.debugLineNum = 51;BA.debugLine="Dim MaxColores=20 As Int";
_vv3 = (int) (20);
 //BA.debugLineNum = 52;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv4 = new int[_vv3];
;
 //BA.debugLineNum = 53;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_vv4 = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 57;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv5 = 0;
 //BA.debugLineNum = 58;BA.debugLine="Dim SecuenciaActiva As Int";
_vv6 = 0;
 //BA.debugLineNum = 59;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vv7 = new javi.prieto.pictorario.starter._secuencia[(int) (_v7+1)];
{
int d0 = _vv7.length;
for (int i0 = 0;i0 < d0;i0++) {
_vv7[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 60;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
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
 //BA.debugLineNum = 61;BA.debugLine="Dim VersionInstalada As Int";
_vvv1 = 0;
 //BA.debugLineNum = 62;BA.debugLine="Dim DetectadaVersionAntigua As Boolean";
_vvv2 = false;
 //BA.debugLineNum = 66;BA.debugLine="Dim IdPictogramaPorDefecto As Int 'Imagen por def";
_vvv3 = 0;
 //BA.debugLineNum = 67;BA.debugLine="Dim DirPictogramas As String 'Directorio de traba";
_vvv4 = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim PictogramasIniciales(12) As Int 'Listado de p";
_vvv5 = new int[(int) (12)];
;
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 73;BA.debugLine="NumSecuencias=0";
_vv5 = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="DirPictogramas=File.Combine(File.DirInternal,\"/pi";
_vvv4 = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"/pictogramas");
 //BA.debugLineNum = 75;BA.debugLine="IdPictogramaPorDefecto=\"7229\" 'El pictograma por";
_vvv3 = (int)(Double.parseDouble("7229"));
 //BA.debugLineNum = 76;BA.debugLine="PictogramasIniciales = Array As Int (31857,2781,2";
_vvv5 = new int[]{(int) (31857),(int) (2781),(int) (28667),(int) (3082),(int) (28206),(int) (9813),(int) (2271),(int) (28675),(int) (2369),(int) (7229),(int) (26799),(int) (32556)};
 //BA.debugLineNum = 77;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v6._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 79;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 80;BA.debugLine="CopiarPictogramasIniciales";
_vvvvv4();
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
}
