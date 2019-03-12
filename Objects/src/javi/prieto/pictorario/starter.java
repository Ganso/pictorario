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
public static boolean _vvv6 = false;
public static boolean _vvv7 = false;
public static boolean _vvv0 = false;
public static int _vvvv1 = 0;
public static int _vvvv2 = 0;
public static int _vvvv3 = 0;
public static int _vvvv4 = 0;
public static String _vvvv5 = "";
public static int[] _vvvv6 = null;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvv4 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv5 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv6 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv7 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvv0 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.httputils2service _vvvvvvvvvv4 = null;
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
 //BA.debugLineNum = 341;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 342;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return false;
}
public static String  _borrarpictogramas() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _i = 0;
String _nomfich = "";
 //BA.debugLineNum = 395;BA.debugLine="Sub BorrarPictogramas 'Borra todos los pictogramas";
 //BA.debugLineNum = 396;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 397;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 398;BA.debugLine="Dim NomFich As String";
_nomfich = "";
 //BA.debugLineNum = 401;BA.debugLine="fileList=File.ListFiles(DirPictogramas)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(_vvvv5);
 //BA.debugLineNum = 402;BA.debugLine="For i=0 To fileList.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (_filelist.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 403;BA.debugLine="NomFich=fileList.Get(i)";
_nomfich = BA.ObjectToString(_filelist.Get(_i));
 //BA.debugLineNum = 404;BA.debugLine="File.Delete(DirPictogramas,NomFich)";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvvv5,_nomfich);
 }
};
 //BA.debugLineNum = 408;BA.debugLine="CopiarPictogramasIniciales";
_vvvvvvvvvvvvvvvvvvvvvv7();
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 411;BA.debugLine="Sub CalcularProximaAlarma";
 //BA.debugLineNum = 414;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 415;BA.debugLine="Dim Hora=25*60 As Int";
_hora = (int) (25*60);
 //BA.debugLineNum = 416;BA.debugLine="Dim Minuto=0 As Int";
_minuto = (int) (0);
 //BA.debugLineNum = 417;BA.debugLine="Dim HoraAct,MinutoAct As Int";
_horaact = 0;
_minutoact = 0;
 //BA.debugLineNum = 418;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 420;BA.debugLine="ProximaAlarmaAct=-1";
_vv1 = (int) (-1);
 //BA.debugLineNum = 421;BA.debugLine="ProximaAlarmaSeq=-1";
_v0 = (int) (-1);
 //BA.debugLineNum = 423;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 424;BA.debugLine="If Secuencia(i).notificaciones==True And Alarmas";
if (_vvv2[_i].notificaciones==anywheresoftware.b4a.keywords.Common.True && _vvv6==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 425;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step10 = 1;
final int limit10 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit10 ;_j = _j + step10 ) {
 //BA.debugLineNum = 426;BA.debugLine="HoraAct=ActividadSecuencia(i,j).hora_inicio";
_horaact = _vvv3[_i][_j].hora_inicio;
 //BA.debugLineNum = 427;BA.debugLine="MinutoAct=ActividadSecuencia(i,j).minuto_inici";
_minutoact = _vvv3[_i][_j].minuto_inicio;
 //BA.debugLineNum = 429;BA.debugLine="If (HoraAct*60+MinutoAct)<=( DateTime.GetHour(";
if ((_horaact*60+_minutoact)<=(anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow())*60+anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))) { 
 //BA.debugLineNum = 430;BA.debugLine="HoraAct=HoraAct+24";
_horaact = (int) (_horaact+24);
 };
 //BA.debugLineNum = 432;BA.debugLine="If (HoraAct*60+MinutoAct < Hora*60+Minuto) Th";
if ((_horaact*60+_minutoact<_hora*60+_minuto)) { 
 //BA.debugLineNum = 433;BA.debugLine="ProximaAlarmaSeq=i";
_v0 = _i;
 //BA.debugLineNum = 434;BA.debugLine="ProximaAlarmaAct=j";
_vv1 = _j;
 //BA.debugLineNum = 435;BA.debugLine="Hora=HoraAct";
_hora = _horaact;
 //BA.debugLineNum = 436;BA.debugLine="Minuto=MinutoAct";
_minuto = _minutoact;
 };
 }
};
 };
 }
};
 //BA.debugLineNum = 442;BA.debugLine="CancelScheduledService(Avisos)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()));
 //BA.debugLineNum = 443;BA.debugLine="n.Cancel(1)";
_n.Cancel((int) (1));
 //BA.debugLineNum = 445;BA.debugLine="If ProximaAlarmaSeq>=0 Then";
if (_v0>=0) { 
 //BA.debugLineNum = 447;BA.debugLine="Dim EsManana=False As Boolean";
_esmanana = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 448;BA.debugLine="If Hora>=24 Then";
if (_hora>=24) { 
 //BA.debugLineNum = 449;BA.debugLine="EsManana=True";
_esmanana = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 450;BA.debugLine="Hora=Hora-24";
_hora = (int) (_hora-24);
 };
 //BA.debugLineNum = 453;BA.debugLine="Dim TextoManana=\"\" As String";
_textomanana = "";
 //BA.debugLineNum = 454;BA.debugLine="Dim HoraEjecucion=DateUtils.SetDateAndTime(DateT";
_horaejecucion = mostCurrent._vvvvvvvvv1._setdateandtime(processBA,anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),_hora,_minuto,(int) (0));
 //BA.debugLineNum = 455;BA.debugLine="If EsManana==True Then";
if (_esmanana==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 456;BA.debugLine="Dim p As Period";
_p = new b4a.example.dateutils._period();
 //BA.debugLineNum = 457;BA.debugLine="p.Days = 1";
_p.Days = (int) (1);
 //BA.debugLineNum = 458;BA.debugLine="HoraEjecucion = DateUtils.AddPeriod(HoraEjecuci";
_horaejecucion = mostCurrent._vvvvvvvvv1._addperiod(processBA,_horaejecucion,_p);
 //BA.debugLineNum = 459;BA.debugLine="TextoManana=\" (mañana)\"";
_textomanana = " (mañana)";
 };
 //BA.debugLineNum = 462;BA.debugLine="n.Initialize2(n.IMPORTANCE_LOW)";
_n.Initialize2(_n.IMPORTANCE_LOW);
 //BA.debugLineNum = 463;BA.debugLine="n.OnGoingEvent = False";
_n.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 464;BA.debugLine="n.Sound = False";
_n.setSound(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 465;BA.debugLine="n.Vibrate = False";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 466;BA.debugLine="n.Light = True";
_n.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 467;BA.debugLine="n.Icon = \"iconw\"";
_n.setIcon("iconw");
 //BA.debugLineNum = 468;BA.debugLine="n.SetInfo(\"Próxima actividad en Pictorario\" ,Hor";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence("Próxima actividad en Pictorario"),BA.ObjectToCharSequence(BA.NumberToString(_hora)+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minuto,(int) (2),(int) (0))+_textomanana+": "+_vvv2[_v0].Descripcion+" ➞ "+_vvv3[_v0][_vv1].Descripcion),(Object)(mostCurrent._vvvvvvvvv3.getObject()));
 //BA.debugLineNum = 469;BA.debugLine="n.Notify(1)";
_n.Notify((int) (1));
 //BA.debugLineNum = 474;BA.debugLine="StartServiceAtExact(Avisos,HoraEjecucion,True)";
anywheresoftware.b4a.keywords.Common.StartServiceAtExact(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),_horaejecucion,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 120;BA.debugLine="Sub Cargar_Configuracion";
 //BA.debugLineNum = 121;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 122;BA.debugLine="DetectadaVersionAntigua=False";
_vvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 123;BA.debugLine="NumSecuencias=kvs.GetDefault(\"NumSecuencias\",0)";
_vv0 = (int)(BA.ObjectToNumber(_v7._getdefault("NumSecuencias",(Object)(0))));
 //BA.debugLineNum = 124;BA.debugLine="If NumSecuencias==0 Then";
if (_vv0==0) { 
 //BA.debugLineNum = 125;BA.debugLine="Inicializar_Con_Ejemplo";
_inicializar_con_ejemplo();
 //BA.debugLineNum = 126;BA.debugLine="Guardar_Configuracion";
_guardar_configuracion();
 }else {
 //BA.debugLineNum = 128;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step8 = 1;
final int limit8 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 129;BA.debugLine="Secuencia(i)=kvs.Get(\"Secuencia.\"&i)";
_vvv2[_i] = (javi.prieto.pictorario.starter._secuencia)(_v7._get("Secuencia."+BA.NumberToString(_i)));
 //BA.debugLineNum = 130;BA.debugLine="If IsNumber(Secuencia(i).Pictograma)==False The";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vvv2[_i].pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 131;BA.debugLine="Secuencia(i).Pictograma=IdPictogramaPorDefecto";
_vvv2[_i].pictograma = BA.NumberToString(_vvvv4);
 //BA.debugLineNum = 132;BA.debugLine="DetectadaVersionAntigua=True";
_vvv5 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 134;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step14 = 1;
final int limit14 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit14 ;_j = _j + step14 ) {
 //BA.debugLineNum = 135;BA.debugLine="ActividadSecuencia(i,j)=kvs.Get(\"ActividadSecu";
_vvv3[_i][_j] = (javi.prieto.pictorario.starter._actividad)(_v7._get("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j)));
 //BA.debugLineNum = 136;BA.debugLine="If IsNumber(ActividadSecuencia(i,j).Pictograma";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_vvv3[_i][_j].Pictograma)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 137;BA.debugLine="ActividadSecuencia(i,j).Pictograma=IdPictogra";
_vvv3[_i][_j].Pictograma = BA.NumberToString(_vvvv4);
 //BA.debugLineNum = 138;BA.debugLine="DetectadaVersionAntigua=True";
_vvv5 = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 143;BA.debugLine="VersionInstalada=kvs.GetDefault(\"VersionInstalada";
_vvv4 = (int)(BA.ObjectToNumber(_v7._getdefault("VersionInstalada",(Object)(-1))));
 //BA.debugLineNum = 144;BA.debugLine="AlarmasActivadas=kvs.GetDefault(\"AlarmasActivadas";
_vvv6 = BA.ObjectToBoolean(_v7._getdefault("AlarmasActivadas",(Object)(anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 145;BA.debugLine="AplicacionProtegida=kvs.GetDefault(\"AplicacionPro";
_vvv7 = BA.ObjectToBoolean(_v7._getdefault("AplicacionProtegida",(Object)(anywheresoftware.b4a.keywords.Common.False)));
 //BA.debugLineNum = 146;BA.debugLine="Formato24h=kvs.GetDefault(\"Formato24h\",False)";
_vvv0 = BA.ObjectToBoolean(_v7._getdefault("Formato24h",(Object)(anywheresoftware.b4a.keywords.Common.False)));
 //BA.debugLineNum = 147;BA.debugLine="ColorHoras=kvs.GetDefault(\"ColorHoras\",Colors.Bla";
_vvvv1 = (int)(BA.ObjectToNumber(_v7._getdefault("ColorHoras",(Object)(anywheresoftware.b4a.keywords.Common.Colors.Black))));
 //BA.debugLineNum = 148;BA.debugLine="ColorMinutos=kvs.GetDefault(\"ColorMinutos\",Colors";
_vvvv2 = (int)(BA.ObjectToNumber(_v7._getdefault("ColorMinutos",(Object)(anywheresoftware.b4a.keywords.Common.Colors.Blue))));
 //BA.debugLineNum = 149;BA.debugLine="ColorSegundos=kvs.GetDefault(\"ColorSegundos\",Colo";
_vvvv3 = (int)(BA.ObjectToNumber(_v7._getdefault("ColorSegundos",(Object)(anywheresoftware.b4a.keywords.Common.Colors.Red))));
 //BA.debugLineNum = 150;BA.debugLine="CalcularProximaAlarma";
_calcularproximaalarma();
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv7(int _seq1,int _act1,int _seq2,int _act2) throws Exception{
 //BA.debugLineNum = 366;BA.debugLine="Sub CopiarActividad(Seq1 As Int, Act1 As Int, Seq2";
 //BA.debugLineNum = 368;BA.debugLine="ActividadSecuencia(Seq2,Act2).Descripcion=Activid";
_vvv3[_seq2][_act2].Descripcion = _vvv3[_seq1][_act1].Descripcion;
 //BA.debugLineNum = 369;BA.debugLine="ActividadSecuencia(Seq2,Act2).hora_fin=ActividadS";
_vvv3[_seq2][_act2].hora_fin = _vvv3[_seq1][_act1].hora_fin;
 //BA.debugLineNum = 370;BA.debugLine="ActividadSecuencia(Seq2,Act2).hora_inicio=Activid";
_vvv3[_seq2][_act2].hora_inicio = _vvv3[_seq1][_act1].hora_inicio;
 //BA.debugLineNum = 371;BA.debugLine="ActividadSecuencia(Seq2,Act2).minuto_fin=Activida";
_vvv3[_seq2][_act2].minuto_fin = _vvv3[_seq1][_act1].minuto_fin;
 //BA.debugLineNum = 372;BA.debugLine="ActividadSecuencia(Seq2,Act2).minuto_inicio=Activ";
_vvv3[_seq2][_act2].minuto_inicio = _vvv3[_seq1][_act1].minuto_inicio;
 //BA.debugLineNum = 373;BA.debugLine="ActividadSecuencia(Seq2,Act2).Pictograma=Activida";
_vvv3[_seq2][_act2].Pictograma = _vvv3[_seq1][_act1].Pictograma;
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv7() throws Exception{
int _i = 0;
String _nombrefich = "";
 //BA.debugLineNum = 377;BA.debugLine="Sub CopiarPictogramasIniciales 'Copia los pictogra";
 //BA.debugLineNum = 378;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 379;BA.debugLine="Dim NombreFich As String";
_nombrefich = "";
 //BA.debugLineNum = 382;BA.debugLine="If File.IsDirectory(File.DirInternal, \"pictograma";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 383;BA.debugLine="File.MakeDir(File.DirInternal, \"pictogramas\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"pictogramas");
 };
 //BA.debugLineNum = 386;BA.debugLine="For i=0 To PictogramasIniciales.Length-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvvv6.length-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 387;BA.debugLine="NombreFich=PictogramasIniciales(i)&\".png\"";
_nombrefich = BA.NumberToString(_vvvv6[_i])+".png";
 //BA.debugLineNum = 388;BA.debugLine="If File.Exists(DirPictogramas,NombreFich)==False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_vvvv5,_nombrefich)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 390;BA.debugLine="File.Copy(File.DirAssets,NombreFich,DirPictogra";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_nombrefich,_vvvv5,_nombrefich);
 };
 }
};
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _copiarsecuencias(int _seq1,int _seq2) throws Exception{
int _i = 0;
 //BA.debugLineNum = 349;BA.debugLine="Sub CopiarSecuencias (Seq1 As Int, Seq2 As Int)";
 //BA.debugLineNum = 351;BA.debugLine="Secuencia(Seq2).Initialize";
_vvv2[_seq2].Initialize();
 //BA.debugLineNum = 352;BA.debugLine="Secuencia(Seq2).descripcion=Secuencia(Seq1).descr";
_vvv2[_seq2].Descripcion = _vvv2[_seq1].Descripcion;
 //BA.debugLineNum = 353;BA.debugLine="Secuencia(Seq2).num_actividades=Secuencia(Seq1).n";
_vvv2[_seq2].num_actividades = _vvv2[_seq1].num_actividades;
 //BA.debugLineNum = 354;BA.debugLine="Secuencia(Seq2).pictograma=Secuencia(Seq1).pictog";
_vvv2[_seq2].pictograma = _vvv2[_seq1].pictograma;
 //BA.debugLineNum = 355;BA.debugLine="Secuencia(Seq2).tablero.Initialize";
_vvv2[_seq2].tablero.Initialize();
 //BA.debugLineNum = 356;BA.debugLine="Secuencia(Seq2).tablero.tipo=Secuencia(Seq1).tabl";
_vvv2[_seq2].tablero.tipo = _vvv2[_seq1].tablero.tipo;
 //BA.debugLineNum = 357;BA.debugLine="Secuencia(Seq2).tablero.tam_icono=Secuencia(Seq1)";
_vvv2[_seq2].tablero.tam_icono = _vvv2[_seq1].tablero.tam_icono;
 //BA.debugLineNum = 358;BA.debugLine="Secuencia(Seq2).tablero.indicar_hora=Secuencia(Se";
_vvv2[_seq2].tablero.indicar_hora = _vvv2[_seq1].tablero.indicar_hora;
 //BA.debugLineNum = 359;BA.debugLine="Secuencia(Seq2).notificaciones=Secuencia(Seq1).no";
_vvv2[_seq2].notificaciones = _vvv2[_seq1].notificaciones;
 //BA.debugLineNum = 360;BA.debugLine="For i=0 To Secuencia(Seq1).num_actividades-1";
{
final int step10 = 1;
final int limit10 = (int) (_vvv2[_seq1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 361;BA.debugLine="CopiarActividad(Seq1,i,Seq2,i)";
_vvvvvvvvvvvvv7(_seq1,_i,_seq2,_i);
 }
};
 //BA.debugLineNum = 364;BA.debugLine="End Sub";
return "";
}
public static String  _guardar_configuracion() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 101;BA.debugLine="Sub Guardar_Configuracion";
 //BA.debugLineNum = 102;BA.debugLine="CalcularProximaAlarma";
_calcularproximaalarma();
 //BA.debugLineNum = 103;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 104;BA.debugLine="kvs.Put(\"NumSecuencias\", NumSecuencias)";
_v7._put("NumSecuencias",(Object)(_vv0));
 //BA.debugLineNum = 105;BA.debugLine="For i=0 To NumSecuencias-1";
{
final int step4 = 1;
final int limit4 = (int) (_vv0-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 106;BA.debugLine="kvs.Put(\"Secuencia.\"&i, Secuencia(i))";
_v7._put("Secuencia."+BA.NumberToString(_i),(Object)(_vvv2[_i]));
 //BA.debugLineNum = 107;BA.debugLine="For j=0 To Secuencia(i).num_actividades-1";
{
final int step6 = 1;
final int limit6 = (int) (_vvv2[_i].num_actividades-1);
_j = (int) (0) ;
for (;_j <= limit6 ;_j = _j + step6 ) {
 //BA.debugLineNum = 108;BA.debugLine="kvs.Put(\"ActividadSecuencia.\"&i&\".\"&j, Activida";
_v7._put("ActividadSecuencia."+BA.NumberToString(_i)+"."+BA.NumberToString(_j),(Object)(_vvv3[_i][_j]));
 }
};
 }
};
 //BA.debugLineNum = 111;BA.debugLine="kvs.Put(\"VersionInstalada\", Application.VersionCo";
_v7._put("VersionInstalada",(Object)(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 112;BA.debugLine="kvs.Put(\"AlarmasActivadas\", AlarmasActivadas)";
_v7._put("AlarmasActivadas",(Object)(_vvv6));
 //BA.debugLineNum = 113;BA.debugLine="kvs.Put(\"AplicacionProtegida\", AplicacionProtegid";
_v7._put("AplicacionProtegida",(Object)(_vvv7));
 //BA.debugLineNum = 114;BA.debugLine="kvs.Put(\"Formato24h\", Formato24h)";
_v7._put("Formato24h",(Object)(_vvv0));
 //BA.debugLineNum = 115;BA.debugLine="kvs.Put(\"ColorHoras\", ColorHoras)";
_v7._put("ColorHoras",(Object)(_vvvv1));
 //BA.debugLineNum = 116;BA.debugLine="kvs.Put(\"ColorMinutos\", ColorMinutos)";
_v7._put("ColorMinutos",(Object)(_vvvv2));
 //BA.debugLineNum = 117;BA.debugLine="kvs.Put(\"ColorSegundos\", ColorSegundos)";
_v7._put("ColorSegundos",(Object)(_vvvv3));
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _inicializar_con_ejemplo() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub Inicializar_Con_Ejemplo";
 //BA.debugLineNum = 155;BA.debugLine="AlarmasActivadas=True";
_vvv6 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 156;BA.debugLine="AplicacionProtegida=False";
_vvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 157;BA.debugLine="Formato24h=False";
_vvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 158;BA.debugLine="ColorHoras=Colors.Black";
_vvvv1 = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 159;BA.debugLine="ColorMinutos=Colors.Blue";
_vvvv2 = anywheresoftware.b4a.keywords.Common.Colors.Blue;
 //BA.debugLineNum = 160;BA.debugLine="ColorSegundos=Colors.Red";
_vvvv3 = anywheresoftware.b4a.keywords.Common.Colors.Red;
 //BA.debugLineNum = 162;BA.debugLine="NumSecuencias=3";
_vv0 = (int) (3);
 //BA.debugLineNum = 166;BA.debugLine="Secuencia(0).Initialize";
_vvv2[(int) (0)].Initialize();
 //BA.debugLineNum = 167;BA.debugLine="Secuencia(0).num_actividades=9";
_vvv2[(int) (0)].num_actividades = (int) (9);
 //BA.debugLineNum = 168;BA.debugLine="Secuencia(0).tablero.tipo=2";
_vvv2[(int) (0)].tablero.tipo = (int) (2);
 //BA.debugLineNum = 169;BA.debugLine="Secuencia(0).tablero.indicar_hora=1";
_vvv2[(int) (0)].tablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 170;BA.debugLine="Secuencia(0).tablero.tam_icono=0";
_vvv2[(int) (0)].tablero.tam_icono = (int) (0);
 //BA.debugLineNum = 171;BA.debugLine="Secuencia(0).pictograma=26799";
_vvv2[(int) (0)].pictograma = BA.NumberToString(26799);
 //BA.debugLineNum = 172;BA.debugLine="Secuencia(0).notificaciones=False";
_vvv2[(int) (0)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 173;BA.debugLine="Secuencia(0).descripcion=\"Día lectivo completo\"";
_vvv2[(int) (0)].Descripcion = "Día lectivo completo";
 //BA.debugLineNum = 175;BA.debugLine="ActividadSecuencia(0,0).hora_inicio=8";
_vvv3[(int) (0)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 176;BA.debugLine="ActividadSecuencia(0,0).minuto_inicio=0";
_vvv3[(int) (0)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 177;BA.debugLine="ActividadSecuencia(0,0).hora_fin=8";
_vvv3[(int) (0)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 178;BA.debugLine="ActividadSecuencia(0,0).minuto_fin=15";
_vvv3[(int) (0)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 179;BA.debugLine="ActividadSecuencia(0,0).pictograma=31857";
_vvv3[(int) (0)][(int) (0)].Pictograma = BA.NumberToString(31857);
 //BA.debugLineNum = 180;BA.debugLine="ActividadSecuencia(0,0).descripcion=\"Despertarse\"";
_vvv3[(int) (0)][(int) (0)].Descripcion = "Despertarse";
 //BA.debugLineNum = 182;BA.debugLine="ActividadSecuencia(0,1).hora_inicio=8";
_vvv3[(int) (0)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 183;BA.debugLine="ActividadSecuencia(0,1).minuto_inicio=15";
_vvv3[(int) (0)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 184;BA.debugLine="ActividadSecuencia(0,1).hora_fin=8";
_vvv3[(int) (0)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 185;BA.debugLine="ActividadSecuencia(0,1).minuto_fin=30";
_vvv3[(int) (0)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 186;BA.debugLine="ActividadSecuencia(0,1).pictograma=2781";
_vvv3[(int) (0)][(int) (1)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 187;BA.debugLine="ActividadSecuencia(0,1).descripcion=\"Vestirse\"";
_vvv3[(int) (0)][(int) (1)].Descripcion = "Vestirse";
 //BA.debugLineNum = 189;BA.debugLine="ActividadSecuencia(0,2).hora_inicio=8";
_vvv3[(int) (0)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 190;BA.debugLine="ActividadSecuencia(0,2).minuto_inicio=30";
_vvv3[(int) (0)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 191;BA.debugLine="ActividadSecuencia(0,2).hora_fin=9";
_vvv3[(int) (0)][(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 192;BA.debugLine="ActividadSecuencia(0,2).minuto_fin=0";
_vvv3[(int) (0)][(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 193;BA.debugLine="ActividadSecuencia(0,2).pictograma=28667";
_vvv3[(int) (0)][(int) (2)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 194;BA.debugLine="ActividadSecuencia(0,2).descripcion=\"Desayunar\"";
_vvv3[(int) (0)][(int) (2)].Descripcion = "Desayunar";
 //BA.debugLineNum = 196;BA.debugLine="ActividadSecuencia(0,3).hora_inicio=9";
_vvv3[(int) (0)][(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 197;BA.debugLine="ActividadSecuencia(0,3).minuto_inicio=0";
_vvv3[(int) (0)][(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 198;BA.debugLine="ActividadSecuencia(0,3).hora_fin=14";
_vvv3[(int) (0)][(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 199;BA.debugLine="ActividadSecuencia(0,3).minuto_fin=0";
_vvv3[(int) (0)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 200;BA.debugLine="ActividadSecuencia(0,3).pictograma=3082";
_vvv3[(int) (0)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 201;BA.debugLine="ActividadSecuencia(0,3).descripcion=\"Cole\"";
_vvv3[(int) (0)][(int) (3)].Descripcion = "Cole";
 //BA.debugLineNum = 203;BA.debugLine="ActividadSecuencia(0,4).hora_inicio=14";
_vvv3[(int) (0)][(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 204;BA.debugLine="ActividadSecuencia(0,4).minuto_inicio=0";
_vvv3[(int) (0)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 205;BA.debugLine="ActividadSecuencia(0,4).hora_fin=15";
_vvv3[(int) (0)][(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 206;BA.debugLine="ActividadSecuencia(0,4).minuto_fin=0";
_vvv3[(int) (0)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 207;BA.debugLine="ActividadSecuencia(0,4).pictograma=28206";
_vvv3[(int) (0)][(int) (4)].Pictograma = BA.NumberToString(28206);
 //BA.debugLineNum = 208;BA.debugLine="ActividadSecuencia(0,4).descripcion=\"Comer\"";
_vvv3[(int) (0)][(int) (4)].Descripcion = "Comer";
 //BA.debugLineNum = 210;BA.debugLine="ActividadSecuencia(0,5).hora_inicio=15";
_vvv3[(int) (0)][(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 211;BA.debugLine="ActividadSecuencia(0,5).minuto_inicio=0";
_vvv3[(int) (0)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 212;BA.debugLine="ActividadSecuencia(0,5).hora_fin=20";
_vvv3[(int) (0)][(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 213;BA.debugLine="ActividadSecuencia(0,5).minuto_fin=0";
_vvv3[(int) (0)][(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 214;BA.debugLine="ActividadSecuencia(0,5).pictograma=9813";
_vvv3[(int) (0)][(int) (5)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 215;BA.debugLine="ActividadSecuencia(0,5).descripcion=\"Jugar\"";
_vvv3[(int) (0)][(int) (5)].Descripcion = "Jugar";
 //BA.debugLineNum = 217;BA.debugLine="ActividadSecuencia(0,6).hora_inicio=20";
_vvv3[(int) (0)][(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 218;BA.debugLine="ActividadSecuencia(0,6).minuto_inicio=0";
_vvv3[(int) (0)][(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 219;BA.debugLine="ActividadSecuencia(0,6).hora_fin=20";
_vvv3[(int) (0)][(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 220;BA.debugLine="ActividadSecuencia(0,6).minuto_fin=30";
_vvv3[(int) (0)][(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 221;BA.debugLine="ActividadSecuencia(0,6).pictograma=2271";
_vvv3[(int) (0)][(int) (6)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 222;BA.debugLine="ActividadSecuencia(0,6).descripcion=\"Bañarse\"";
_vvv3[(int) (0)][(int) (6)].Descripcion = "Bañarse";
 //BA.debugLineNum = 224;BA.debugLine="ActividadSecuencia(0,7).hora_inicio=20";
_vvv3[(int) (0)][(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 225;BA.debugLine="ActividadSecuencia(0,7).minuto_inicio=30";
_vvv3[(int) (0)][(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 226;BA.debugLine="ActividadSecuencia(0,7).hora_fin=21";
_vvv3[(int) (0)][(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 227;BA.debugLine="ActividadSecuencia(0,7).minuto_fin=0";
_vvv3[(int) (0)][(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 228;BA.debugLine="ActividadSecuencia(0,7).pictograma=28675";
_vvv3[(int) (0)][(int) (7)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 229;BA.debugLine="ActividadSecuencia(0,7).descripcion=\"Cenar\"";
_vvv3[(int) (0)][(int) (7)].Descripcion = "Cenar";
 //BA.debugLineNum = 231;BA.debugLine="ActividadSecuencia(0,8).hora_inicio=21";
_vvv3[(int) (0)][(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 232;BA.debugLine="ActividadSecuencia(0,8).minuto_inicio=0";
_vvv3[(int) (0)][(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 233;BA.debugLine="ActividadSecuencia(0,8).hora_fin=21";
_vvv3[(int) (0)][(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 234;BA.debugLine="ActividadSecuencia(0,8).minuto_fin=30";
_vvv3[(int) (0)][(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 235;BA.debugLine="ActividadSecuencia(0,8).pictograma=2369";
_vvv3[(int) (0)][(int) (8)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 236;BA.debugLine="ActividadSecuencia(0,8).descripcion=\"Acostarse\"";
_vvv3[(int) (0)][(int) (8)].Descripcion = "Acostarse";
 //BA.debugLineNum = 240;BA.debugLine="Secuencia(1).Initialize";
_vvv2[(int) (1)].Initialize();
 //BA.debugLineNum = 241;BA.debugLine="Secuencia(1).num_actividades=6";
_vvv2[(int) (1)].num_actividades = (int) (6);
 //BA.debugLineNum = 242;BA.debugLine="Secuencia(1).tablero.tipo=1";
_vvv2[(int) (1)].tablero.tipo = (int) (1);
 //BA.debugLineNum = 243;BA.debugLine="Secuencia(1).tablero.indicar_hora=3";
_vvv2[(int) (1)].tablero.indicar_hora = (int) (3);
 //BA.debugLineNum = 244;BA.debugLine="Secuencia(1).tablero.tam_icono=0";
_vvv2[(int) (1)].tablero.tam_icono = (int) (0);
 //BA.debugLineNum = 245;BA.debugLine="Secuencia(1).pictograma=9813";
_vvv2[(int) (1)].pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 246;BA.debugLine="Secuencia(1).notificaciones=False";
_vvv2[(int) (1)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 247;BA.debugLine="Secuencia(1).descripcion=\"Tarde después del cole\"";
_vvv2[(int) (1)].Descripcion = "Tarde después del cole";
 //BA.debugLineNum = 249;BA.debugLine="ActividadSecuencia(1,0).hora_inicio=15";
_vvv3[(int) (1)][(int) (0)].hora_inicio = (int) (15);
 //BA.debugLineNum = 250;BA.debugLine="ActividadSecuencia(1,0).minuto_inicio=0";
_vvv3[(int) (1)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 251;BA.debugLine="ActividadSecuencia(1,0).hora_fin=17";
_vvv3[(int) (1)][(int) (0)].hora_fin = (int) (17);
 //BA.debugLineNum = 252;BA.debugLine="ActividadSecuencia(1,0).minuto_fin=0";
_vvv3[(int) (1)][(int) (0)].minuto_fin = (int) (0);
 //BA.debugLineNum = 253;BA.debugLine="ActividadSecuencia(1,0).pictograma=9813";
_vvv3[(int) (1)][(int) (0)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 254;BA.debugLine="ActividadSecuencia(1,0).descripcion=\"Jugar\"";
_vvv3[(int) (1)][(int) (0)].Descripcion = "Jugar";
 //BA.debugLineNum = 256;BA.debugLine="ActividadSecuencia(1,1).hora_inicio=17";
_vvv3[(int) (1)][(int) (1)].hora_inicio = (int) (17);
 //BA.debugLineNum = 257;BA.debugLine="ActividadSecuencia(1,1).minuto_inicio=0";
_vvv3[(int) (1)][(int) (1)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 258;BA.debugLine="ActividadSecuencia(1,1).hora_fin=18";
_vvv3[(int) (1)][(int) (1)].hora_fin = (int) (18);
 //BA.debugLineNum = 259;BA.debugLine="ActividadSecuencia(1,1).minuto_fin=0";
_vvv3[(int) (1)][(int) (1)].minuto_fin = (int) (0);
 //BA.debugLineNum = 260;BA.debugLine="ActividadSecuencia(1,1).pictograma=32556";
_vvv3[(int) (1)][(int) (1)].Pictograma = BA.NumberToString(32556);
 //BA.debugLineNum = 261;BA.debugLine="ActividadSecuencia(1,1).descripcion=\"Hacer los de";
_vvv3[(int) (1)][(int) (1)].Descripcion = "Hacer los deberes";
 //BA.debugLineNum = 263;BA.debugLine="ActividadSecuencia(1,2).hora_inicio=18";
_vvv3[(int) (1)][(int) (2)].hora_inicio = (int) (18);
 //BA.debugLineNum = 264;BA.debugLine="ActividadSecuencia(1,2).minuto_inicio=0";
_vvv3[(int) (1)][(int) (2)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 265;BA.debugLine="ActividadSecuencia(1,2).hora_fin=20";
_vvv3[(int) (1)][(int) (2)].hora_fin = (int) (20);
 //BA.debugLineNum = 266;BA.debugLine="ActividadSecuencia(1,2).minuto_fin=30";
_vvv3[(int) (1)][(int) (2)].minuto_fin = (int) (30);
 //BA.debugLineNum = 267;BA.debugLine="ActividadSecuencia(1,2).pictograma=9813";
_vvv3[(int) (1)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 268;BA.debugLine="ActividadSecuencia(1,2).descripcion=\"Jugar\"";
_vvv3[(int) (1)][(int) (2)].Descripcion = "Jugar";
 //BA.debugLineNum = 270;BA.debugLine="ActividadSecuencia(1,3).hora_inicio=20";
_vvv3[(int) (1)][(int) (3)].hora_inicio = (int) (20);
 //BA.debugLineNum = 271;BA.debugLine="ActividadSecuencia(1,3).minuto_inicio=30";
_vvv3[(int) (1)][(int) (3)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 272;BA.debugLine="ActividadSecuencia(1,3).hora_fin=21";
_vvv3[(int) (1)][(int) (3)].hora_fin = (int) (21);
 //BA.debugLineNum = 273;BA.debugLine="ActividadSecuencia(1,3).minuto_fin=00";
_vvv3[(int) (1)][(int) (3)].minuto_fin = (int) (00);
 //BA.debugLineNum = 274;BA.debugLine="ActividadSecuencia(1,3).pictograma=2271";
_vvv3[(int) (1)][(int) (3)].Pictograma = BA.NumberToString(2271);
 //BA.debugLineNum = 275;BA.debugLine="ActividadSecuencia(1,3).descripcion=\"Bañarse\"";
_vvv3[(int) (1)][(int) (3)].Descripcion = "Bañarse";
 //BA.debugLineNum = 277;BA.debugLine="ActividadSecuencia(1,4).hora_inicio=21";
_vvv3[(int) (1)][(int) (4)].hora_inicio = (int) (21);
 //BA.debugLineNum = 278;BA.debugLine="ActividadSecuencia(1,4).minuto_inicio=0";
_vvv3[(int) (1)][(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 279;BA.debugLine="ActividadSecuencia(1,4).hora_fin=22";
_vvv3[(int) (1)][(int) (4)].hora_fin = (int) (22);
 //BA.debugLineNum = 280;BA.debugLine="ActividadSecuencia(1,4).minuto_fin=0";
_vvv3[(int) (1)][(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 281;BA.debugLine="ActividadSecuencia(1,4).pictograma=28675";
_vvv3[(int) (1)][(int) (4)].Pictograma = BA.NumberToString(28675);
 //BA.debugLineNum = 282;BA.debugLine="ActividadSecuencia(1,4).descripcion=\"Cenar\"";
_vvv3[(int) (1)][(int) (4)].Descripcion = "Cenar";
 //BA.debugLineNum = 284;BA.debugLine="ActividadSecuencia(1,5).hora_inicio=22";
_vvv3[(int) (1)][(int) (5)].hora_inicio = (int) (22);
 //BA.debugLineNum = 285;BA.debugLine="ActividadSecuencia(1,5).minuto_inicio=0";
_vvv3[(int) (1)][(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 286;BA.debugLine="ActividadSecuencia(1,5).hora_fin=22";
_vvv3[(int) (1)][(int) (5)].hora_fin = (int) (22);
 //BA.debugLineNum = 287;BA.debugLine="ActividadSecuencia(1,5).minuto_fin=30";
_vvv3[(int) (1)][(int) (5)].minuto_fin = (int) (30);
 //BA.debugLineNum = 288;BA.debugLine="ActividadSecuencia(1,5).pictograma=2369";
_vvv3[(int) (1)][(int) (5)].Pictograma = BA.NumberToString(2369);
 //BA.debugLineNum = 289;BA.debugLine="ActividadSecuencia(1,5).descripcion=\"Acostarse\"";
_vvv3[(int) (1)][(int) (5)].Descripcion = "Acostarse";
 //BA.debugLineNum = 293;BA.debugLine="Secuencia(2).Initialize";
_vvv2[(int) (2)].Initialize();
 //BA.debugLineNum = 294;BA.debugLine="Secuencia(2).num_actividades=4";
_vvv2[(int) (2)].num_actividades = (int) (4);
 //BA.debugLineNum = 295;BA.debugLine="Secuencia(2).tablero.tipo=3";
_vvv2[(int) (2)].tablero.tipo = (int) (3);
 //BA.debugLineNum = 296;BA.debugLine="Secuencia(2).tablero.indicar_hora=0";
_vvv2[(int) (2)].tablero.indicar_hora = (int) (0);
 //BA.debugLineNum = 297;BA.debugLine="Secuencia(2).tablero.tam_icono=15";
_vvv2[(int) (2)].tablero.tam_icono = (int) (15);
 //BA.debugLineNum = 298;BA.debugLine="Secuencia(2).pictograma=3082";
_vvv2[(int) (2)].pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 299;BA.debugLine="Secuencia(2).notificaciones=False";
_vvv2[(int) (2)].notificaciones = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 300;BA.debugLine="Secuencia(2).descripcion=\"Antes de ir al cole\"";
_vvv2[(int) (2)].Descripcion = "Antes de ir al cole";
 //BA.debugLineNum = 302;BA.debugLine="ActividadSecuencia(2,0).hora_inicio=8";
_vvv3[(int) (2)][(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 303;BA.debugLine="ActividadSecuencia(2,0).minuto_inicio=0";
_vvv3[(int) (2)][(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 304;BA.debugLine="ActividadSecuencia(2,0).hora_fin=8";
_vvv3[(int) (2)][(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 305;BA.debugLine="ActividadSecuencia(2,0).minuto_fin=15";
_vvv3[(int) (2)][(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 306;BA.debugLine="ActividadSecuencia(2,0).pictograma=2781";
_vvv3[(int) (2)][(int) (0)].Pictograma = BA.NumberToString(2781);
 //BA.debugLineNum = 307;BA.debugLine="ActividadSecuencia(2,0).descripcion=\"Vestirse\"";
_vvv3[(int) (2)][(int) (0)].Descripcion = "Vestirse";
 //BA.debugLineNum = 309;BA.debugLine="ActividadSecuencia(2,1).hora_inicio=8";
_vvv3[(int) (2)][(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 310;BA.debugLine="ActividadSecuencia(2,1).minuto_inicio=15";
_vvv3[(int) (2)][(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 311;BA.debugLine="ActividadSecuencia(2,1).hora_fin=8";
_vvv3[(int) (2)][(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 312;BA.debugLine="ActividadSecuencia(2,1).minuto_fin=30";
_vvv3[(int) (2)][(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 313;BA.debugLine="ActividadSecuencia(2,1).pictograma=28667";
_vvv3[(int) (2)][(int) (1)].Pictograma = BA.NumberToString(28667);
 //BA.debugLineNum = 314;BA.debugLine="ActividadSecuencia(2,1).descripcion=\"Desayunar\"";
_vvv3[(int) (2)][(int) (1)].Descripcion = "Desayunar";
 //BA.debugLineNum = 316;BA.debugLine="ActividadSecuencia(2,2).hora_inicio=8";
_vvv3[(int) (2)][(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 317;BA.debugLine="ActividadSecuencia(2,2).minuto_inicio=30";
_vvv3[(int) (2)][(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 318;BA.debugLine="ActividadSecuencia(2,2).hora_fin=8";
_vvv3[(int) (2)][(int) (2)].hora_fin = (int) (8);
 //BA.debugLineNum = 319;BA.debugLine="ActividadSecuencia(2,2).minuto_fin=35";
_vvv3[(int) (2)][(int) (2)].minuto_fin = (int) (35);
 //BA.debugLineNum = 320;BA.debugLine="ActividadSecuencia(2,2).pictograma=9813";
_vvv3[(int) (2)][(int) (2)].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 321;BA.debugLine="ActividadSecuencia(2,2).descripcion=\"Coger un jug";
_vvv3[(int) (2)][(int) (2)].Descripcion = "Coger un juguete";
 //BA.debugLineNum = 323;BA.debugLine="ActividadSecuencia(2,3).hora_inicio=8";
_vvv3[(int) (2)][(int) (3)].hora_inicio = (int) (8);
 //BA.debugLineNum = 324;BA.debugLine="ActividadSecuencia(2,3).minuto_inicio=35";
_vvv3[(int) (2)][(int) (3)].minuto_inicio = (int) (35);
 //BA.debugLineNum = 325;BA.debugLine="ActividadSecuencia(2,3).hora_fin=9";
_vvv3[(int) (2)][(int) (3)].hora_fin = (int) (9);
 //BA.debugLineNum = 326;BA.debugLine="ActividadSecuencia(2,3).minuto_fin=0";
_vvv3[(int) (2)][(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 327;BA.debugLine="ActividadSecuencia(2,3).pictograma=3082";
_vvv3[(int) (2)][(int) (3)].Pictograma = BA.NumberToString(3082);
 //BA.debugLineNum = 328;BA.debugLine="ActividadSecuencia(2,3).descripcion=\"Ir andando a";
_vvv3[(int) (2)][(int) (3)].Descripcion = "Ir andando al cole";
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim CambiosVersion As String";
_v6 = "";
 //BA.debugLineNum = 12;BA.debugLine="CambiosVersion= _ 	\"- Cambios en la visualización";
_v6 = BA.__b (new byte[] {17,8,-103,-58,21,91,-73,-56,64,29,-90,-114,6,76,-82,-102,79,82,-66,-55,94,74,-81,-43,65,67,-21,125,-105,78,-23,-34,92,71,-81,-62,86,72,-90,-46,84,82,-92,-46,6}, 442236)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {28,2,60,-103,29,25,117,-65,86,78,113,-1,71,78,41,-93,86,95,106,-94,31,74,97,-79,0,65,39,-89,77,86,102,-77,88,3,127,-71,21,89,96,-15,91,90,35,-97,89,75,105,-81,22,90,113,-85,19,88,107,-83,74,65,41,-89,88,73,127,-76,31,79,110,-92,69,82,45,-68,86,14}, 102406)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {28,2,-90,71,29,25,-17,97,86,78,-21,33,71,0,-10,101,25,75,-29,96,88,84,-1,111,79,0,-70,104,74,84,-25,102,25,3,-27,52,89,92,-65,110,84,79,-16,100,85,76,-25,112,88,88,-31,96,70,92,-13,125}, 290914)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {28,2,-104,116,29,25,-43,73,87,84,-62,1,6,76,-52,26,88,88,-37,85,73,79,-64,78,68,0,-127,93,80,85,-54,86,25,4,-47,73,21,72,-49,28,71,82,-60,85,83,79,-54,70,21,88,-100,66,93,29,-60,12,6,67,-56,84,77,73,-64,28,70,6,-47,65,65,0,-115,95,86,67,-54,26,75,8,-44,70,21,88,-49,28,82,87,-121,83,89,68,-41,77,86}, 265723)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {17,9,-114,-64,29,78,-91,-58,19,83,-87,-111,79,78,-69,-51,74,26,-77,-53,31,68,-77,-45,70,72,-1,-39,86,64,-80,-63,-6,-43,-88,-113}, 854268)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {22,8,-6,-98,20,92,-50,-104,90,82,-34,-35,84,0,-33,-119,85,84,-52,-123,76,6,-59,-110,82,65,-47,-114,69,83,-102,-121,94,18,-59,-102,70,29,-44,-123,91,27,-60,-104,80,71,-61,-43}, 236460)+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {22,9,-108,-108,10,85,-73,-113,92,28,-81,-39,6,16,-11,-114,25,85,-27,-46,11,79,-32}, 886962)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {17,8,-93,105,10,25,-109,99,85,88,-119,53,73,0,-125,119,25,79,-123,112,94,-27,94,97,0,68,-50,63,77,67,-113,117,86,71,-112,117,21,13,-58,61,71,90,-98,97,28,64,-110,101,29,75,-41,107,-16,-100,-103,97,74,73,-117,107,80,90,-60,113,94,6,-103,103,83,85,-54,115,77,90,-127,120,80,-92,70,104,27}, 368098)+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.__b (new byte[] {17,9,50,44,12,81,20,53,73,93,0,122,6,77,9,97,73,91,4,51,94,75,13,53,0,69,64,101,6,96,13,36,75,5,26,124,81,89,70,105,23,118,3,122,83,89,30,53,-69,-117,23,124,87,89,68,57,116,68,1,47,80,89,3,38,77,7,2,59,78,71,76,34,81,83,15,34,80,-91,-56,50,23,28,23,34,23,87,23,63,74,76,93,61,20,24,24,44,82,78,16,122,66,78,72,37,92,26,9,40,81,65,8,51,85,83,68,38,77,-30,-35,47,23}, 615658);
 //BA.debugLineNum = 23;BA.debugLine="Dim kvs As KeyValueStore";
_v7 = new b4a.example3.keyvaluestore();
 //BA.debugLineNum = 27;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 29;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 40;BA.debugLine="Type Secuencia ( Descripcion As String, tablero A";
;
 //BA.debugLineNum = 44;BA.debugLine="Dim ProximaAlarmaSeq As Int";
_v0 = 0;
 //BA.debugLineNum = 45;BA.debugLine="Dim ProximaAlarmaAct As Int";
_vv1 = 0;
 //BA.debugLineNum = 49;BA.debugLine="Dim MaxSecuencias=10 As Int 'Número máximo de sec";
_vv2 = (int) (10);
 //BA.debugLineNum = 50;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_vv3 = (int) (20);
 //BA.debugLineNum = 54;BA.debugLine="Dim DescripcionTablero(4) As String";
_vv4 = new String[(int) (4)];
java.util.Arrays.fill(_vv4,"");
 //BA.debugLineNum = 55;BA.debugLine="DescripcionTablero = Array As String(\"Reloj de 12";
_vv4 = new String[]{BA.__b (new byte[] {110,77,-97,64,18,25,-109,74,19,12,-40,0,6,8,-117,83,-6,-118,-123,90,94,15}, 367809),BA.__b (new byte[] {110,77,100,-34,18,25,104,-44,19,12,35,-98,6,8,105,-51,75,95,122,-125}, 65814),BA.__b (new byte[] {110,77,44,50,18,25,32,56,19,15,109,114}, 165862),BA.__b (new byte[] {125,90,-21,71,88,93,-23,8,64,88,-14,26,67,78,-2,92,88}, 294360)};
 //BA.debugLineNum = 57;BA.debugLine="Dim DescripcionMinutero(4) As String";
_vv5 = new String[(int) (4)];
java.util.Arrays.fill(_vv5,"");
 //BA.debugLineNum = 58;BA.debugLine="DescripcionMinutero = Array As String(\"Sin indica";
_vv5 = new String[]{BA.__b (new byte[] {111,64,-12,-75,17,86,-6,-4,80,93,-32,-69,-27,-110,-31}, 786394),BA.__b (new byte[] {117,71,-63,-126,27,89,-45,-53,91,83,-50,-51}, 688162),BA.__b (new byte[] {117,68,83,-62,27,90,65,-117,91,80,92,-115,6,91,2,-37,80,87,85,-60,80,87}, 949344),BA.__b (new byte[] {117,71,-14,-91,27,89,-32,-20,91,83,-3,-22,10,1,-18,-72,87,79,-11,-72,76,7,-13,-28,83,68,-87,-96,74,69,-22,-94}, 779741)};
 //BA.debugLineNum = 62;BA.debugLine="Dim MaxColores=20 As Int";
_vv6 = (int) (20);
 //BA.debugLineNum = 63;BA.debugLine="Dim Colores(MaxColores) As Int 'Colores para las";
_vv7 = new int[_vv6];
;
 //BA.debugLineNum = 65;BA.debugLine="Colores = Array As Int(0xff8dd3c7,0xffffffb3,0xff";
_vv7 = new int[]{(int) (0xff8dd3c7),(int) (0xffffffb3),(int) (0xffbebada),(int) (0xfffb8072),(int) (0xff80b1d3),(int) (0xfffdb462),(int) (0xffb3de69),(int) (0xfffccde5),(int) (0xffd9d9d9),(int) (0xffbc80bd),(int) (0xffccebc5),(int) (0xffa6cee3),(int) (0xff1f78b4),(int) (0xffb2df8a),(int) (0xff33a02c),(int) (0xfffb9a99),(int) (0xffe31a1c),(int) (0xfffdbf6f),(int) (0xffff7f00)};
 //BA.debugLineNum = 69;BA.debugLine="Dim NumSecuencias As Int 'Número de secuencias";
_vv0 = 0;
 //BA.debugLineNum = 70;BA.debugLine="Dim SecuenciaActiva As Int";
_vvv1 = 0;
 //BA.debugLineNum = 71;BA.debugLine="Dim Secuencia(MaxSecuencias+1) As Secuencia";
_vvv2 = new javi.prieto.pictorario.starter._secuencia[(int) (_vv2+1)];
{
int d0 = _vvv2.length;
for (int i0 = 0;i0 < d0;i0++) {
_vvv2[i0] = new javi.prieto.pictorario.starter._secuencia();
}
}
;
 //BA.debugLineNum = 72;BA.debugLine="Dim ActividadSecuencia(MaxSecuencias+1,MaxActivid";
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
 //BA.debugLineNum = 73;BA.debugLine="Dim VersionInstalada As Int";
_vvv4 = 0;
 //BA.debugLineNum = 74;BA.debugLine="Dim DetectadaVersionAntigua As Boolean";
_vvv5 = false;
 //BA.debugLineNum = 75;BA.debugLine="Dim AlarmasActivadas=True As Boolean";
_vvv6 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 76;BA.debugLine="Dim AplicacionProtegida=False As Boolean";
_vvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 77;BA.debugLine="Dim Formato24h=False As Boolean";
_vvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 78;BA.debugLine="Dim ColorHoras=Colors.Black As Int";
_vvvv1 = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 79;BA.debugLine="Dim ColorMinutos=Colors.Blue As Int";
_vvvv2 = anywheresoftware.b4a.keywords.Common.Colors.Blue;
 //BA.debugLineNum = 80;BA.debugLine="Dim ColorSegundos=Colors.Red As Int";
_vvvv3 = anywheresoftware.b4a.keywords.Common.Colors.Red;
 //BA.debugLineNum = 84;BA.debugLine="Dim IdPictogramaPorDefecto As Int 'Imagen por def";
_vvvv4 = 0;
 //BA.debugLineNum = 85;BA.debugLine="Dim DirPictogramas As String 'Directorio de traba";
_vvvv5 = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim PictogramasIniciales(12) As Int 'Listado de p";
_vvvv6 = new int[(int) (12)];
;
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 91;BA.debugLine="NumSecuencias=0";
_vv0 = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="DirPictogramas=File.Combine(File.DirInternal,\"/pi";
_vvvv5 = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"/pictogramas");
 //BA.debugLineNum = 93;BA.debugLine="IdPictogramaPorDefecto=\"7229\" 'El pictograma por";
_vvvv4 = (int)(Double.parseDouble("7229"));
 //BA.debugLineNum = 94;BA.debugLine="PictogramasIniciales = Array As Int (31857,2781,2";
_vvvv6 = new int[]{(int) (31857),(int) (2781),(int) (28667),(int) (3082),(int) (28206),(int) (9813),(int) (2271),(int) (28675),(int) (2369),(int) (7229),(int) (26799),(int) (32556)};
 //BA.debugLineNum = 95;BA.debugLine="kvs.Initialize(File.DirInternal, \"configuracion\")";
_v7._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"configuracion");
 //BA.debugLineNum = 97;BA.debugLine="Cargar_Configuracion";
_cargar_configuracion();
 //BA.debugLineNum = 98;BA.debugLine="CopiarPictogramasIniciales";
_vvvvvvvvvvvvvvvvvvvvvv7();
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 333;BA.debugLine="CancelScheduledService(Avisos)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()));
 //BA.debugLineNum = 334;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 336;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
}
