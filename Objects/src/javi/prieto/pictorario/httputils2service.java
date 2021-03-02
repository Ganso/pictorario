package javi.prieto.pictorario;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class httputils2service extends  android.app.Service{
	public static class httputils2service_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (httputils2service) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, httputils2service.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static httputils2service mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return httputils2service.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.httputils2service");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.httputils2service", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (httputils2service) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
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
                    BA.LogInfo("** Service (httputils2service) Create **");
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
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (httputils2service) Start **");
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
        if (false) {
            BA.LogInfo("** Service (httputils2service) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (httputils2service) Destroy **");
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
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _vvvvvvvvvvvvvvvvvvvvvvv1 = null;
public static anywheresoftware.b4a.objects.collections.Map _vvvvvvvvvvvvvvvvvvvvvv0 = null;
public static String _vvvvv2 = "";
public static int _vvvvvvvvvvvvvvvvvvvvvvv2 = 0;
public b4a.example.dateutils _vvvvvvvvv4 = null;
public b4a.example.versione06 _vvvvvvvvv5 = null;
public javi.prieto.pictorario.main _vvvvvvvvv6 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv7 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv0 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvvv3 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv4 = null;
public javi.prieto.pictorario.starter _vvvvvvvvvv5 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvvv6 = null;
public static String  _vvvvvvvvvvvvvvvvvvvvvv7(int _taskid,boolean _success,String _errormessage) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
 //BA.debugLineNum = 115;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean,";
 //BA.debugLineNum = 119;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 120;BA.debugLine="TaskIdToJob.Remove(TaskId)";
_vvvvvvvvvvvvvvvvvvvvvv0.Remove((Object)(_taskid));
 //BA.debugLineNum = 121;BA.debugLine="job.success = success";
_job._vvvvvvvv3 /*boolean*/  = _success;
 //BA.debugLineNum = 122;BA.debugLine="job.errorMessage = errorMessage";
_job._vvvvvvvv6 /*String*/  = _errormessage;
 //BA.debugLineNum = 124;BA.debugLine="job.Complete(TaskId)";
_job._complete /*String*/ (_taskid);
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
 //BA.debugLineNum = 90;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
 //BA.debugLineNum = 91;BA.debugLine="Log($\"ResponseError. Reason: ${Reason}, Response:";
anywheresoftware.b4a.keywords.Common.LogImpl("71769473",("ResponseError. Reason: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_reason))+", Response: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_response.getErrorResponse()))+""),0);
 //BA.debugLineNum = 92;BA.debugLine="Response.Release";
_response.Release();
 //BA.debugLineNum = 93;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 94;BA.debugLine="job.Response = Response";
_job._vvvvvvvvv2 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse*/  = _response;
 //BA.debugLineNum = 95;BA.debugLine="If Response.ErrorResponse <> \"\" Then";
if ((_response.getErrorResponse()).equals("") == false) { 
 //BA.debugLineNum = 96;BA.debugLine="CompleteJob(TaskId, False, Response.ErrorRespons";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,anywheresoftware.b4a.keywords.Common.False,_response.getErrorResponse());
 }else {
 //BA.debugLineNum = 98;BA.debugLine="CompleteJob(TaskId, False, Reason)";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 };
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 71;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
 //BA.debugLineNum = 72;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 73;BA.debugLine="job.Response = Response";
_job._vvvvvvvvv2 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse*/  = _response;
 //BA.debugLineNum = 74;BA.debugLine="Dim out As OutputStream = File.OpenOutput(TempFol";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(_vvvvv2,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="Response.GetAsynchronously(\"response\", out , _";
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(_out.getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private hc As OkHttpClient";
_vvvvvvvvvvvvvvvvvvvvvvv1 = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private TaskIdToJob As Map";
_vvvvvvvvvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 19;BA.debugLine="Public TempFolder As String";
_vvvvv2 = "";
 //BA.debugLineNum = 20;BA.debugLine="Private taskCounter As Int";
_vvvvvvvvvvvvvvvvvvvvvvv2 = 0;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Private Sub Response_StreamFinish (Success As Bool";
 //BA.debugLineNum = 83;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 84;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,_success,"");
 }else {
 //BA.debugLineNum = 86;BA.debugLine="CompleteJob(TaskId, Success, LastException.Messa";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 25;BA.debugLine="TempFolder = File.DirInternalCache";
_vvvvv2 = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 //BA.debugLineNum = 26;BA.debugLine="Try";
try { //BA.debugLineNum = 27;BA.debugLine="File.WriteString(TempFolder, \"~test.test\", \"test";
anywheresoftware.b4a.keywords.Common.File.WriteString(_vvvvv2,"~test.test","test");
 //BA.debugLineNum = 28;BA.debugLine="File.Delete(TempFolder, \"~test.test\")";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvvvv2,"~test.test");
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 30;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("71376263",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(processBA)),0);
 //BA.debugLineNum = 31;BA.debugLine="Log(\"Switching to File.DirInternal\")";
anywheresoftware.b4a.keywords.Common.LogImpl("71376264","Switching to File.DirInternal",0);
 //BA.debugLineNum = 32;BA.debugLine="TempFolder = File.DirInternal";
_vvvvv2 = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 };
 //BA.debugLineNum = 37;BA.debugLine="If hc.IsInitialized = False Then";
if (_vvvvvvvvvvvvvvvvvvvvvvv1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 42;BA.debugLine="hc.Initialize(\"hc\")";
_vvvvvvvvvvvvvvvvvvvvvvv1.Initialize("hc");
 };
 //BA.debugLineNum = 45;BA.debugLine="TaskIdToJob.Initialize";
_vvvvvvvvvvvvvvvvvvvvvv0.Initialize();
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 49;BA.debugLine="Service.StopAutomaticForeground";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _submitjob(javi.prieto.pictorario.httpjob _job) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Public Sub SubmitJob(job As HttpJob)";
 //BA.debugLineNum = 59;BA.debugLine="If TaskIdToJob.IsInitialized = False Then Service";
if (_vvvvvvvvvvvvvvvvvvvvvv0.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_service_create();};
 //BA.debugLineNum = 60;BA.debugLine="taskCounter = taskCounter + 1";
_vvvvvvvvvvvvvvvvvvvvvvv2 = (int) (_vvvvvvvvvvvvvvvvvvvvvvv2+1);
 //BA.debugLineNum = 61;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
_vvvvvvvvvvvvvvvvvvvvvv0.Put((Object)(_vvvvvvvvvvvvvvvvvvvvvvv2),(Object)(_job));
 //BA.debugLineNum = 62;BA.debugLine="If job.Username <> \"\" And job.Password <> \"\" Then";
if ((_job._vvvvvvvv4 /*String*/ ).equals("") == false && (_job._vvvvvvvv5 /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 63;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounte";
_vvvvvvvvvvvvvvvvvvvvvvv1.ExecuteCredentials(processBA,_job._vvvvvv4 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_vvvvvvvvvvvvvvvvvvvvvvv2,_job._vvvvvvvv4 /*String*/ ,_job._vvvvvvvv5 /*String*/ );
 }else {
 //BA.debugLineNum = 65;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
_vvvvvvvvvvvvvvvvvvvvvvv1.Execute(processBA,_job._vvvvvv4 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_vvvvvvvvvvvvvvvvvvvvvvv2);
 };
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
}
