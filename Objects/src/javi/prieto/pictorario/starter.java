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
public static String _v6 = "";
public static b4a.example3.keyvaluestore _v7 = null;
public static int _v0 = 0;
public static int _vv1 = 0;
public static int _vv2 = 0;
public static int _vv3 = 0;
public static String[] _vv4 = null;
public static String[] _vv5 = null;
public static int _vv6 = 0;
public static int[] _vv7 = null;
public static int _vv0 = 0;
public static int _vvv1 = 0;
public static javi.prieto.pictorario.starter._secuencia[] _vvv2 = null;
public static javi.prieto.pictorario.starter._actividad[][] _vvv3 = null;
public static int _vvv4 = 0;
public static boolean _vvv5 = false;
public static int _vvv6 = 0;
public static String _vvv7 = "";
public static int[] _vvv0 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv6 = null;
public b4a.example.dateutils _vvvvv7 = null;
public javi.prieto.pictorario.main _vvvvvvvv7 = null;
public javi.prieto.pictorario.visualizacion _vvvv5 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvv4 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvv0 = null;
public javi.prieto.pictorario.acercade _vvvv3 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvv1 = null;
public javi.prieto.pictorario.avisos _vvvvvv2 = null;
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
public boolean notificaciones;
public void Initialize() {
IsInitialized = true;
Descripcion = "";
tablero = new javi.prieto.pictorario.starter._tablero();
pictograma = "";
num_actividades = 0;
notificaciones = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 310;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 311;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return false;
}
public static String  _borrarpictogramas() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _i = 0;
String _nomfich = "";
 //BA.debugLineNum = 352;BA.debugLine="Sub BorrarPictogramas 'Borra todos los pictogramas";
 //BA.debugLineNum = 353;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 354;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 355;BA.debugLine="Dim NomFich As String";
_nomfich = "";
 //BA.debugLineNum = 358;BA.debugLine="fileList=File.ListFiles(DirPictogramas)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(_vvv7);
 //BA.debugLineNum = 359;BA.debugLine="For i=0 To fileList.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (_filelist.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 360;BA.debugLine="NomFich=fileList.Get(i)";
_nomfich = BA.ObjectToString(_filelist.Get(_i));
 //BA.debugLineNum = 361;BA.debugLine="File.Delete(DirPictogramas,NomFich)";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvv7,_nomfich);
 }
};
 //BA.debugLineNum = 365;BA.debugLine="CopiarPictogramasIniciales";
_vvvvvvvvvvvvvv6();
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _calcularproximaalarma() throws Exception{
int _i = 0;
int _j = 0;
int _hora = 0;
int _minuto = 0;
int _horaact = 0;
int _minutoact = 0;
anywheresoftware.b4a.objects.NotificationWrapper _n = null;
boolean _esmanana = false;
String _textomanana = "";
long _horaejecucion = 0L;
b4a.example.dateutils._period _p = null;
 //BA.debugLineNum = 368;BA.debugLine="Sub CalcularProximaAlarma";
 //BA.debugLineNum = 371;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 372;BA.debugLine="Dim Hora=25*60 As Int";
_hora = (int) (25*60);
 //BA.debugLineNum = 373;BA.debugLine="Dim Minuto=0 As Int";
_minuto = (int) (0);
 //BA.debugLineNum = 374;BA.debugLine="Dim HoraAct,MinutoAct As Int";
_horaact = 0;
_minutoact = 0;
 //BA.debugLineNum = 375;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 377;BA.debugLine="ProximaAlarmaAct=-1";
_vv1 = (int) (-1);
 //BA.debugLineNum = 378;BA.debugLine="ProximaAlarmaSeq=-1";
_v0 = (int) (-1);
 //BA.debugLineNum = 380;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 381;BA.debugLine="If Secuencia(i).notificaciones==True Then";
if (_vvv2[_i].notificaciones==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 382;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step10 = 1;
final int limit10 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit10 ;_j = _j + step10 ) {
 //BA.debugLineNum = 383;BA.debugLine="HoraAct=ActividadSecuencia(i,j).hora_inicio";
_horaact = _vvv3[_i][_j].hora_inicio;
 //BA.debugLineNum = 384;BA.debugLine="MinutoAct=ActividadSecuencia(i,j).minuto_inici";
_minutoact = _vvv3[_i][_j].minuto_inicio;
 //BA.debugLineNum = 386;BA.debugLine="If (HoraAct*60+MinutoAct)<=( DateTime.GetHour(";
if ((_horaact*60+_minutoact)<=(anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow())*60+anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))) { 
 //BA.debugLineNum = 387;BA.debugLine="HoraAct=HoraAct+24";
_horaact = (int) (_horaact+24);
 };
 //BA.debugLineNum = 389;BA.debugLine="If (HoraAct*60+MinutoAct < Hora*60+Minuto) Th";
if ((_horaact*60+_minutoact<_hora*60+_minuto)) { 
 //BA.debugLineNum = 390;BA.debugLine="ProximaAlarmaSeq=i";
_v0 = _i;
 //BA.debugLineNum = 391;BA.debugLine="ProximaAlarmaAct=j";
_vv1 = _j;
 //BA.debugLineNum = 392;BA.debugLine="Hora=HoraAct";
_hora = _horaact;
 //BA.debugLineNum = 393;BA.debugLine="Minuto=MinutoAct";
_minuto = _minutoact;
 };
 }
};
 };
 }
};
 //BA.debugLineNum = 399;BA.debugLine="CancelScheduledService(Avisos)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 400;BA.debugLine="n.Cancel(1)";
_n.Cancel((int) (1));
 //BA.debugLineNum = 402;BA.debugLine="If ProximaAlarmaSeq>=0 Then";
if (_v0>=0) { 
 //BA.debugLineNum = 404;BA.debugLine="Dim EsManana=False As Boolean";
_esmanana = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 405;BA.debugLine="If Hora>=24 Then";
if (_hora>=24) { 
 //BA.debugLineNum = 406;BA.debugLine="EsManana=True";
_esmanana = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 407;BA.debugLine="Hora=Hora-24";
_hora = (int) (_hora-24);
 };
 //BA.debugLineNum = 410;BA.debugLine="Dim TextoManana=\"\" As String";
_textomanana = "";
 //BA.debugLineNum = 411;BA.debugLine="Dim HoraEjecucion=DateUtils.SetDateAndTime(DateT";
_horaejecucion = mostCurrent._vvvvv7._setdateandtime(processBA,anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),_hora,_minuto,(int) (0));
 //BA.debugLineNum = 412;BA.debugLine="If EsManana==True Then";
if (_esmanana==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 413;BA.debugLine="Dim p As Period";
_p = new b4a.example.dateutils._period();
 //BA.debugLineNum = 414;BA.debugLine="p.Days = 1";
_p.Days = (int) (1);
 //BA.debugLineNum = 415;BA.debugLine="HoraEjecucion = DateUtils.AddPeriod(HoraEjecuci";
_horaejecucion = mostCurrent._vvvvv7._addperiod(processBA,_horaejecucion,_p);
 //BA.debugLineNum = 416;BA.debugLine="TextoManana=\" (mañana)\"";
_textomanana = " (mañana)";
 };
 //BA.debugLineNum = 419;BA.debugLine="n.Initialize2(n.IMPORTANCE_LOW)";
_n.Initialize2(_n.IMPORTANCE_LOW);
 //BA.debugLineNum = 420;BA.debugLine="n.OnGoingEvent = False";
_n.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="n.Sound = False";
_n.setSound(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 422;BA.debugLine="n.Vibrate = False";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 423;BA.debugLine="n.Light = True";
_n.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 424;BA.debugLine="n.Icon = \"iconw\"";
_n.setIcon("iconw");
 //BA.debugLineNum = 425;BA.debugLine="n.SetInfo(\"Próxima actividad en Pictorario\" ,Hor";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence("Próxima actividad en Pictorario"),BA.ObjectToCharSequence(BA.NumberToString(_hora)+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minuto,(int) (2),(int) (0))+_textomanana+": "+_vvv2[_v0].Descripcion+" ➞ "+_vvv3[_v0][_vv1].Descripcion),(Object)(mostCurrent._vvvvvvvv7.getObject()));
 //BA.debugLineNum = 426;BA.debugLine="n.Notify(1)";
_n.Notify((int) (1));
 //BA.debugLineNum = 431;BA.debugLine="StartServiceAtExact(Avisos,HoraEjecucion,True)";
anywheresoftware.b4a.keywords.Common.StartServiceAtExact(processBA,(Object)(mostCurrent._vvvvvv2.getObject()),_horaejecucion,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 438;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 102;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 103;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 104;BA.debugLine="DetectadaVersionAntigua=False";
_vvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 105;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv0 = (int)(BA.ObjectToNumber(_v7._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 106;BA.debugLine="If NumSecuencias==0 Then";
if (_vv0==0) { 
 //BA.debugLineNum = 107;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 108;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 110;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 111;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vvv2[_i] = (javi.prieto.pictorario.starter._secuencia)(_v7._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 112;BA.debugLine="If IsNumber(Secuencia(i).Pictograma)==False The";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vvv2[_i].pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 113;BA.debugLine="Secuencia(i).Pictograma=IdPictogramaPorDefecto";
_vvv2[_i].pictograma = BA.NumberToString(_vvv6);
 //BA.debugLineNum = 114;BA.debugLine="DetectadaVersionAntigua=True";
_vvv5 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 116;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step14 = 1;
final int limit14 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit14 ;_j = _j + step14 ) {
 //BA.debugLineNum = 117;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vvv3[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v7._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 //BA.debugLineNum = 118;BA.debugLine="If IsNumber(ActividadSecuencia(i,j).Pictograma";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vvv3[_i][_j].Pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 119;BA.debugLine="ActividadSecuencia(i,j).Pictograma=IdPictogra";
_vvv3[_i][_j].Pictograma = BA.NumberToString(_vvv6);
 //BA.debugLineNum = 120;BA.debugLine="DetectadaVersionAntigua=True";
_vvv5 = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 125;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv4 = (int)(BA.ObjectToNumber(_v7._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 126;BA.debugLine="CalcularProximaAlarma";
_calcularproximaalarma();
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvv6() throws Exception{
int _i = 0;
String _nombrefich = "";
 //BA.debugLineNum = 334;BA.debugLine="Sub CopiarPictogramasIniciales 'Copia los pictogra";
 //BA.debugLineNum = 335;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 336;BA.debugLine="Dim NombreFich As String";
_nombrefich = "";
 //BA.debugLineNum = 339;BA.debugLine="If File.IsDirectory(File.DirInternal, \"pictograma";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 340;BA.debugLine="File.MakeDir(File.DirInternal, \"pictogramas\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas");
 };
 //BA.debugLineNum = 343;BA.debugLine="For i=0 To PictogramasIniciales.Length-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvv0.length-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 344;BA.debugLine="NombreFich=PictogramasIniciales(i)&\".png\"";
_nombrefich = BA.NumberToString(_vvv0[_i])+".png";
 //BA.debugLineNum = 345;BA.debugLine="If File.Exists(DirPictogramas,NombreFich)==False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_vvv7,_nombrefich)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 347;BA.debugLine="File.Copy(File.DirAssets,NombreFich,DirPictogra";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_nombrefich,_vvv7,_nombrefich);
 };
 }
};
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 318;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 320;BA.debugLine="Secuencia(Seq2).Initialize";
_vvv2[_seq2].Initialize();
 //BA.debugLineNum = 321;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vvv2[_seq2].Descripcion = _vvv2[_seq1].Descripcion;
 //BA.debugLineNum = 322;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vvv2[_seq2].num_actividades = _vvv2[_seq1].num_actividades;
 //BA.debugLineNum = 323;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vvv2[_seq2].pictograma = _vvv2[_seq1].pictograma;
 //BA.debugLineNum = 324;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vvv2[_seq2].tablero.Initialize();
 //BA.debugLineNum = 325;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vvv2[_seq2].tablero.tipo = _vvv2[_seq1].tablero.tipo;
 //BA.debugLineNum = 326;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vvv2[_seq2].tablero.tam_icono = _vvv2[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 327;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vvv2[_seq2].tablero.indicar_hora = _vvv2[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 328;BA.debugLine="Secuencia(Seq2).notificaciones=Secuencia(Seq1).no";
_vvv2[_seq2].notificaciones = _vvv2[_seq1].notificaciones;
 //BA.debugLineNum = 329;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step10 = 1;
final int limit10 = (int) (_vvv2[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 330;BA.debugLine="ActividadSecuencia(Seq2,i)=ActividadSecuencia(Se";
_vvv3[_seq2][_i] = _vvv3[_seq1][_i];
 }
};
 //BA.debugLineNum = 332;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 89;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 90;BA.debugLine="CalcularProximaAlarma";
_calcularproximaalarma();
 //BA.debugLineNum = 91;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 92;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v7._put("NumSecuencias",(Object)(_vv0));
 //BA.debugLineNum = 93;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step4 = 1;
final int limit4 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 94;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v7._put("Secuencia."+BA.NumberToString(_i),(Object)(_vvv2[_i]));
 //BA.debugLineNum = 95;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit6 ;_j = _j + step6 ) {
 //BA.debugLineNum = 96;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v7._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vvv3[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 99;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v7._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 131;BA.debugLine="NumSecuencias=3";
_vv0 = (int) (3);
 //BA.debugLineNum = 135;BA.debugLine="Secuencia(0).Initialize";
_vvv2[(int) (0)].Initialize();
 //BA.debugLineNum = 136;BA.debugLine="Secuencia(0).num_actividades=9";
_vvv2[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 137;BA.debugLine="Secuencia(0).tablero.tipo=2";
_vvv2[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 138;BA.debugLine="Secuencia(0).tablero.indicar_hora=1";
_vvv2[(int) (0)].tablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 139;BA.debugLine="Secuencia(0).tablero.tam_icono=14";
_vvv2[(int) (0)].tablero.tam_icono = (int) (14);
 //BA.debugLineNum = 140;BA.debugLine="Secuencia(0).pictograma=26799";
_vvv2[(int) (0)].pictograma = BA.NumberToString(26799);
 //BA.debugLineNum = 141;BA.debugLine="Secuencia(0).notificaciones=False";
_vvv2[(int) (0)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 142;BA.debugLine="Secuencia(0).descripcion=\"Día lectivo completo\"";
_vvv2[(int) (0)].Descripcion = "Día lectivo completo";
 //BA.debugLineNum = 144;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vvv3[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 145;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vvv3[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 146;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vvv3[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 147;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vvv3[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 148;BA.debugLine="ActividadSecuencia(0,0).pictograma=31857";
_vvv3[(int) (0)][(int) (0)].Pictograma = BA.NumberToString(31857);
 //BA.debugLineNum = 149;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vvv3[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 151;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vvv3[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 152;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vvv3[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 153;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vvv3[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 154;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vvv3[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 155;BA.debugLine="ActividadSecuencia(0,1).pictograma=2781";
_vvv3[(int) (0)][(int) (1)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 156;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vvv3[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 158;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vvv3[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 159;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vvv3[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 160;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vvv3[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 161;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vvv3[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 162;BA.debugLine="ActividadSecuencia(0,2).pictograma=28667";
_vvv3[(int) (0)][(int) (2)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 163;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vvv3[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 165;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vvv3[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 166;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vvv3[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 167;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vvv3[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 168;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vvv3[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 169;BA.debugLine="ActividadSecuencia(0,3).pictograma=3082";
_vvv3[(int) (0)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 170;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vvv3[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 172;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vvv3[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 173;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vvv3[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 174;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vvv3[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vvv3[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 176;BA.debugLine="ActividadSecuencia(0,4).pictograma=28206";
_vvv3[(int) (0)][(int) (4)].Pictograma = BA.NumberToString(28206);
 //BA.debugLineNum = 177;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vvv3[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 179;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vvv3[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 180;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vvv3[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 181;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vvv3[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 182;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vvv3[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 183;BA.debugLine="ActividadSecuencia(0,5).pictograma=9813";
_vvv3[(int) (0)][(int) (5)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 184;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vvv3[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 186;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vvv3[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 187;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vvv3[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 188;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vvv3[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 189;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vvv3[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 190;BA.debugLine="ActividadSecuencia(0,6).pictograma=2271";
_vvv3[(int) (0)][(int) (6)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 191;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vvv3[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 193;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vvv3[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 194;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vvv3[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 195;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vvv3[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 196;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vvv3[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 197;BA.debugLine="ActividadSecuencia(0,7).pictograma=28675";
_vvv3[(int) (0)][(int) (7)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 198;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vvv3[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 200;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vvv3[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 201;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vvv3[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 202;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vvv3[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 203;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vvv3[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 204;BA.debugLine="ActividadSecuencia(0,8).pictograma=2369";
_vvv3[(int) (0)][(int) (8)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 205;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vvv3[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 209;BA.debugLine="Secuencia(1).Initialize";
_vvv2[(int) (1)].Initialize();
 //BA.debugLineNum = 210;BA.debugLine="Secuencia(1).num_actividades=6";
_vvv2[(int) (1)].num_actividades = (int) (6);
 //BA.debugLineNum = 211;BA.debugLine="Secuencia(1).tablero.tipo=1";
_vvv2[(int) (1)].tablero.tipo = (int) (1);
 //BA.debugLineNum = 212;BA.debugLine="Secuencia(1).tablero.indicar_hora=3";
_vvv2[(int) (1)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 213;BA.debugLine="Secuencia(1).tablero.tam_icono=17";
_vvv2[(int) (1)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 214;BA.debugLine="Secuencia(1).pictograma=9813";
_vvv2[(int) (1)].pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 215;BA.debugLine="Secuencia(1).notificaciones=False";
_vvv2[(int) (1)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 216;BA.debugLine="Secuencia(1).descripcion=\"Tarde después del cole\"";
_vvv2[(int) (1)].Descripcion = "Tarde después del cole";
 //BA.debugLineNum = 218;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=15";
_vvv3[(int) (1)][(int) (0)].hora_inicio = (int) (15);
 //BA.debugLineNum = 219;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_vvv3[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 220;BA.debugLine="ActividadSecuencia(1,0).hora_fin=17";
_vvv3[(int) (1)][(int) (0)].hora_fin = (int) (17);
 //BA.debugLineNum = 221;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=0";
_vvv3[(int) (1)][(int) (0)].minuto_fin = (int) (0);
 //BA.debugLineNum = 222;BA.debugLine="ActividadSecuencia(1,0).pictograma=9813";
_vvv3[(int) (1)][(int) (0)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 223;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Jugar\"";
_vvv3[(int) (1)][(int) (0)].Descripcion = "Jugar";
 //BA.debugLineNum = 225;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=17";
_vvv3[(int) (1)][(int) (1)].hora_inicio = (int) (17);
 //BA.debugLineNum = 226;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=0";
_vvv3[(int) (1)][(int) (1)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 227;BA.debugLine="ActividadSecuencia(1,1).hora_fin=18";
_vvv3[(int) (1)][(int) (1)].hora_fin = (int) (18);
 //BA.debugLineNum = 228;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=0";
_vvv3[(int) (1)][(int) (1)].minuto_fin = (int) (0);
 //BA.debugLineNum = 229;BA.debugLine="ActividadSecuencia(1,1).pictograma=32556";
_vvv3[(int) (1)][(int) (1)].Pictograma = BA.NumberToString(32556);
 //BA.debugLineNum = 230;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Hacer los de";
_vvv3[(int) (1)][(int) (1)].Descripcion = "Hacer los deberes";
 //BA.debugLineNum = 232;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=18";
_vvv3[(int) (1)][(int) (2)].hora_inicio = (int) (18);
 //BA.debugLineNum = 233;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=0";
_vvv3[(int) (1)][(int) (2)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 234;BA.debugLine="ActividadSecuencia(1,2).hora_fin=20";
_vvv3[(int) (1)][(int) (2)].hora_fin = (int) (20);
 //BA.debugLineNum = 235;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=30";
_vvv3[(int) (1)][(int) (2)].minuto_fin = (int) (30);
 //BA.debugLineNum = 236;BA.debugLine="ActividadSecuencia(1,2).pictograma=9813";
_vvv3[(int) (1)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 237;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Jugar\"";
_vvv3[(int) (1)][(int) (2)].Descripcion = "Jugar";
 //BA.debugLineNum = 239;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=20";
_vvv3[(int) (1)][(int) (3)].hora_inicio = (int) (20);
 //BA.debugLineNum = 240;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=30";
_vvv3[(int) (1)][(int) (3)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 241;BA.debugLine="ActividadSecuencia(1,3).hora_fin=21";
_vvv3[(int) (1)][(int) (3)].hora_fin = (int) (21);
 //BA.debugLineNum = 242;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=00";
_vvv3[(int) (1)][(int) (3)].minuto_fin = (int) (00);
 //BA.debugLineNum = 243;BA.debugLine="ActividadSecuencia(1,3).pictograma=2271";
_vvv3[(int) (1)][(int) (3)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 244;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Bañarse\"";
_vvv3[(int) (1)][(int) (3)].Descripcion = "Bañarse";
 //BA.debugLineNum = 246;BA.debugLine="ActividadSecuencia(1,4).hora_inicio=21";
_vvv3[(int) (1)][(int) (4)].hora_inicio = (int) (21);
 //BA.debugLineNum = 247;BA.debugLine="ActividadSecuencia(1,4).minuto_inicio=0";
_vvv3[(int) (1)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 248;BA.debugLine="ActividadSecuencia(1,4).hora_fin=22";
_vvv3[(int) (1)][(int) (4)].hora_fin = (int) (22);
 //BA.debugLineNum = 249;BA.debugLine="ActividadSecuencia(1,4).minuto_fin=0";
_vvv3[(int) (1)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 250;BA.debugLine="ActividadSecuencia(1,4).pictograma=28675";
_vvv3[(int) (1)][(int) (4)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 251;BA.debugLine="ActividadSecuencia(1,4).descripcion=\"Cenar\"";
_vvv3[(int) (1)][(int) (4)].Descripcion = "Cenar";
 //BA.debugLineNum = 253;BA.debugLine="ActividadSecuencia(1,5).hora_inicio=22";
_vvv3[(int) (1)][(int) (5)].hora_inicio = (int) (22);
 //BA.debugLineNum = 254;BA.debugLine="ActividadSecuencia(1,5).minuto_inicio=0";
_vvv3[(int) (1)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 255;BA.debugLine="ActividadSecuencia(1,5).hora_fin=22";
_vvv3[(int) (1)][(int) (5)].hora_fin = (int) (22);
 //BA.debugLineNum = 256;BA.debugLine="ActividadSecuencia(1,5).minuto_fin=30";
_vvv3[(int) (1)][(int) (5)].minuto_fin = (int) (30);
 //BA.debugLineNum = 257;BA.debugLine="ActividadSecuencia(1,5).pictograma=2369";
_vvv3[(int) (1)][(int) (5)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 258;BA.debugLine="ActividadSecuencia(1,5).descripcion=\"Acostarse\"";
_vvv3[(int) (1)][(int) (5)].Descripcion = "Acostarse";
 //BA.debugLineNum = 262;BA.debugLine="Secuencia(2).Initialize";
_vvv2[(int) (2)].Initialize();
 //BA.debugLineNum = 263;BA.debugLine="Secuencia(2).num_actividades=4";
_vvv2[(int) (2)].num_actividades = (int) (4);
 //BA.debugLineNum = 264;BA.debugLine="Secuencia(2).tablero.tipo=3";
_vvv2[(int) (2)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 265;BA.debugLine="Secuencia(2).tablero.indicar_hora=0";
_vvv2[(int) (2)].tablero.indicar_hora = (int) (0);
 //BA.debugLineNum = 266;BA.debugLine="Secuencia(2).tablero.tam_icono=17";
_vvv2[(int) (2)].tablero.tam_icono = (int) (17);
 //BA.debugLineNum = 267;BA.debugLine="Secuencia(2).pictograma=3082";
_vvv2[(int) (2)].pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 268;BA.debugLine="Secuencia(2).notificaciones=False";
_vvv2[(int) (2)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 269;BA.debugLine="Secuencia(2).descripcion=\"Antes de ir al cole\"";
_vvv2[(int) (2)].Descripcion = "Antes de ir al cole";
 //BA.debugLineNum = 271;BA.debugLine="ActividadSecuencia(2,0).hora_inicio=8";
_vvv3[(int) (2)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 272;BA.debugLine="ActividadSecuencia(2,0).minuto_inicio=0";
_vvv3[(int) (2)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 273;BA.debugLine="ActividadSecuencia(2,0).hora_fin=8";
_vvv3[(int) (2)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 274;BA.debugLine="ActividadSecuencia(2,0).minuto_fin=15";
_vvv3[(int) (2)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 275;BA.debugLine="ActividadSecuencia(2,0).pictograma=2781";
_vvv3[(int) (2)][(int) (0)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 276;BA.debugLine="ActividadSecuencia(2,0).descripcion=\"Vestirse\"";
_vvv3[(int) (2)][(int) (0)].Descripcion = "Vestirse";
 //BA.debugLineNum = 278;BA.debugLine="ActividadSecuencia(2,1).hora_inicio=8";
_vvv3[(int) (2)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 279;BA.debugLine="ActividadSecuencia(2,1).minuto_inicio=15";
_vvv3[(int) (2)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 280;BA.debugLine="ActividadSecuencia(2,1).hora_fin=8";
_vvv3[(int) (2)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 281;BA.debugLine="ActividadSecuencia(2,1).minuto_fin=30";
_vvv3[(int) (2)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 282;BA.debugLine="ActividadSecuencia(2,1).pictograma=28667";
_vvv3[(int) (2)][(int) (1)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 283;BA.debugLine="ActividadSecuencia(2,1).descripcion=\"Desayunar\"";
_vvv3[(int) (2)][(int) (1)].Descripcion = "Desayunar";
 //BA.debugLineNum = 285;BA.debugLine="ActividadSecuencia(2,2).hora_inicio=8";
_vvv3[(int) (2)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 286;BA.debugLine="ActividadSecuencia(2,2).minuto_inicio=30";
_vvv3[(int) (2)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 287;BA.debugLine="ActividadSecuencia(2,2).hora_fin=8";
_vvv3[(int) (2)][(int) (2)].hora_fin = (int) (8);
 //BA.debugLineNum = 288;BA.debugLine="ActividadSecuencia(2,2).minuto_fin=35";
_vvv3[(int) (2)][(int) (2)].minuto_fin = (int) (35);
 //BA.debugLineNum = 289;BA.debugLine="ActividadSecuencia(2,2).pictograma=9813";
_vvv3[(int) (2)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 290;BA.debugLine="ActividadSecuencia(2,2).descripcion=\"Coger un jug";
_vvv3[(int) (2)][(int) (2)].Descripcion = "Coger un juguete";
 //BA.debugLineNum = 292;BA.debugLine="ActividadSecuencia(2,3).hora_inicio=8";
_vvv3[(int) (2)][(int) (3)].hora_inicio = (int) (8);
 //BA.debugLineNum = 293;BA.debugLine="ActividadSecuencia(2,3).minuto_inicio=35";
_vvv3[(int) (2)][(int) (3)].minuto_inicio = (int) (35);
 //BA.debugLineNum = 294;BA.debugLine="ActividadSecuencia(2,3).hora_fin=9";
_vvv3[(int) (2)][(int) (3)].hora_fin = (int) (9);
 //BA.debugLineNum = 295;BA.debugLine="ActividadSecuencia(2,3).minuto_fin=0";
_vvv3[(int) (2)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 296;BA.debugLine="ActividadSecuencia(2,3).pictograma=3082";
_vvv3[(int) (2)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 297;BA.debugLine="ActividadSecuencia(2,3).descripcion=\"Ir andando a";
_vvv3[(int) (2)][(int) (3)].Descripcion = "Ir andando al cole";
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim CambiosVersion As String";
_v6 = "";
 //BA.debugLineNum = 12;BA.debugLine="CambiosVersion= _ 	\"- Soporte de ALERTAS y NOTIFI";
_v6 = BA.__b (new byte[] {18,9,127,94,11,87,90,69,85,28,81,19,5,96,117,105,104,110,122,121,28,94,16,119,108,117,61,110,110,98,126,111,115,41,100,116,101,18}, 934038)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {111,75,33,-94,91,90,52,-73,89,73,43,-10,73,77,106,-2,79,77,45,-76,85,94,46,-71,3,78,106,-6,73,87,37,-88,91,69,58,-77,85,86,-119,107,90,25,110,-124,92,94,58,-75,26,73,119,-94,92,94,56,-23,68,81,100,-2,94,92,42,-84,78,75,111,-81,70,2,103,-69,7,65,47,-80,92,12,50,-74,68,94,41,-79,-9,-118,34,-27,91,79,115,-80,30,88,34,-90,94,92,35,-27,86,12}, 654394)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {18,10,-6,105,91,90,-62,100,89,92,-50,44,76,-31,16,123,26,64,-64,51,82,75,-118,101,80,86,45,-80,7,71,-53,53,92,4,-61,109,22,93,-54,103,85,23}, 715701)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {18,10,92,56,91,83,123,43,81,31,108,112,5,71,105,100,72,92,107,45,86,4,104,56,68,75,60,33,75,2,112,33,26,12,120,61,95,92,104,98,87,86,97,127,82,-23,-79,42,91,88,120,56,66,86,109,127,65,12}, 540414)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {18,11,47,-71,15,83,9,-96,74,95,26,-25,74,77,16,-89,22,24,26,-73,86,74,14,-96,80,3,93,-66,7,79,18,-12,95,23,18,-88,84,87,21,-69,80,89,27,-17,70,11,5,-89,91,86,5,-23,87,91,10,-6,76,-32,-58,-70,26,92,18,-14,84,74,14,-96,80,3,93,-66,7,79,18,-89,26,5,5,-67,95,72,16,-74,85,92,26,-68,17}, 111348);
 //BA.debugLineNum = 18;BA.debugLine="Dim kvs As KeyValueStore";
_v7 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 22;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 24;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 35;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 39;BA.debugLine="Dim ProximaAlarmaSeq As Int";
_v0 = 0;
 //BA.debugLineNum = 40;BA.debugLine="Dim ProximaAlarmaAct As Int";
_vv1 = 0;
 //BA.debugLineNum = 44;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_vv2 = (int) (10);
 //BA.debugLineNum = 45;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_vv3 = (int) (20);
 //BA.debugLineNum = 49;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv4 = new String[(int) (4)];
java.util.Arrays.fill(_vv4,"");
 //BA.debugLineNum = 50;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv4 = new String[]{BA.__b (new byte[] {109,79,-10,-22,17,27,-6,-32,16,14,-79,-86,5,10,-30,-7,-7,-120,-20,-16,93,13}, 781131),BA.__b (new byte[] {109,78,127,49,17,26,115,59,16,15,56,113,5,11,114,34,72,92,97,108}, 79869),BA.__b (new byte[] {109,78,-62,11,17,26,-50,1,16,12,-125,75}, 242870),BA.__b (new byte[] {126,88,6,-125,91,95,4,-52,67,90,31,-34,64,76,19,-104,91}, 578814)};
 //BA.debugLineNum = 52;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv5 = new String[(int) (4)];
java.util.Arrays.fill(_vv5,"");
 //BA.debugLineNum = 53;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv5 = new String[]{BA.__b (new byte[] {108,67,-34,102,18,85,-48,47,83,94,-54,104,-26,-111,-53}, 712612),BA.__b (new byte[] {118,69,-21,-30,24,91,-7,-85,88,81,-28,-83}, 302652),BA.__b (new byte[] {118,69,-10,-126,24,91,-28,-53,88,81,-7,-51,5,90,-89,-101,83,86,-16,-124,83,86}, 308253),BA.__b (new byte[] {118,68,-14,112,24,90,-32,57,88,80,-3,63,9,2,-18,109,84,76,-11,109,79,4,-13,49,80,71,-87,117,73,70,-22,119}, 772926)};
 //BA.debugLineNum = 57;BA.debugLine="Dim MaxColores=20 As Int";
_vv6 = (int) (20);
 //BA.debugLineNum = 58;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv7 = new int[_vv6];
;
 //BA.debugLineNum = 59;BA.debugLine="Colores = Array As Int(0xFFffb3ba,0xFFffdfba,0xFF";
_vv7 = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1),(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff)};
 //BA.debugLineNum = 63;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv0 = 0;
 //BA.debugLineNum = 64;BA.debugLine="Dim SecuenciaActiva As Int";
_vvv1 = 0;
 //BA.debugLineNum = 65;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vvv2 = new javi.prieto.pictorario.starter._secuencia[(int) (_vv2+1)];
{
int d0 = _vvv2.length;
for (int i0 = 0;i0 < d0;i0++) {
_vvv2[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 66;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
_vvv3 = new javi.prieto.pictorario.starter._actividad[(int) (_vv2+1)][];
{
int d0 = _vvv3.length;
int d1 = _vv3;
for (int i0 = 0;i0 < d0;i0++) {
_vvv3[i0] = new javi.prieto.pictorario.starter._actividad[d1];
for (int i1 = 0;i1 < d1;i1++) {
_vvv3[i0][i1] = new javi.prieto.pictorario.starter._actividad();
}
}
}
;
 //BA.debugLineNum = 67;BA.debugLine="Dim VersionInstalada As Int";
_vvv4 = 0;
 //BA.debugLineNum = 68;BA.debugLine="Dim DetectadaVersionAntigua As Boolean";
_vvv5 = false;
 //BA.debugLineNum = 72;BA.debugLine="Dim IdPictogramaPorDefecto As Int 'Imagen por def";
_vvv6 = 0;
 //BA.debugLineNum = 73;BA.debugLine="Dim DirPictogramas As String 'Directorio de traba";
_vvv7 = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim PictogramasIniciales(12) As Int 'Listado de p";
_vvv0 = new int[(int) (12)];
;
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 79;BA.debugLine="NumSecuencias=0";
_vv0 = (int) (0);
 //BA.debugLineNum = 80;BA.debugLine="DirPictogramas=File.Combine(File.DirInternal,\"/pi";
_vvv7 = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"/pictogramas");
 //BA.debugLineNum = 81;BA.debugLine="IdPictogramaPorDefecto=\"7229\" 'El pictograma por";
_vvv6 = (int)(Double.parseDouble("7229"));
 //BA.debugLineNum = 82;BA.debugLine="PictogramasIniciales = Array As Int (31857,2781,2";
_vvv0 = new int[]{(int) (31857),(int) (2781),(int) (28667),(int) (3082),(int) (28206),(int) (9813),(int) (2271),(int) (28675),(int) (2369),(int) (7229),(int) (26799),(int) (32556)};
 //BA.debugLineNum = 83;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v7._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 85;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 86;BA.debugLine="CopiarPictogramasIniciales";
_vvvvvvvvvvvvvv6();
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 314;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 302;BA.debugLine="CancelScheduledService(Avisos)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 307;BA.debugLine="End Sub";
return "";
}
}
