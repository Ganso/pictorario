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
public static String _vvvv7 = "";
public static int _vvvvvvvvvvvvvvvvvvvvvvv2 = 0;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv4 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv5 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv6 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvv7 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvv0 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.starter _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvvv3 = null;
public static String  _vvvvvvvvvvvvvvvvvvvvvv7(int _taskid,boolean _success,String _errormessage) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
 //BA.debugLineNum = 93;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean,";
 //BA.debugLineNum = 97;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 98;BA.debugLine="TaskIdToJob.Remove(TaskId)";
_vvvvvvvvvvvvvvvvvvvvvv0.Remove((Object)(_taskid));
 //BA.debugLineNum = 99;BA.debugLine="job.success = success";
_job._vvvvvvv0 /*boolean*/  = _success;
 //BA.debugLineNum = 100;BA.debugLine="job.errorMessage = errorMessage";
_job._vvvvvvvv3 /*String*/  = _errormessage;
 //BA.debugLineNum = 102;BA.debugLine="job.Complete(TaskId)";
_job._complete /*String*/ (_taskid);
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
 //BA.debugLineNum = 70;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
 //BA.debugLineNum = 71;BA.debugLine="Log($\"ResponseError. Reason: ${Reason}, Response:";
anywheresoftware.b4a.keywords.Common.LogImpl("89895937",("ResponseError. Reason: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_reason))+", Response: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_response.getErrorResponse()))+""),0);
 //BA.debugLineNum = 72;BA.debugLine="Response.Release";
_response.Release();
 //BA.debugLineNum = 73;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 74;BA.debugLine="job.Response = Response";
_job._vvvvvvvv7 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse*/  = _response;
 //BA.debugLineNum = 75;BA.debugLine="If Response.ErrorResponse <> \"\" Then";
if ((_response.getErrorResponse()).equals("") == false) { 
 //BA.debugLineNum = 76;BA.debugLine="CompleteJob(TaskId, False, Response.ErrorRespons";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,anywheresoftware.b4a.keywords.Common.False,_response.getErrorResponse());
 }else {
 //BA.debugLineNum = 78;BA.debugLine="CompleteJob(TaskId, False, Reason)";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 };
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
javi.prieto.pictorario.httpjob _job = null;
 //BA.debugLineNum = 55;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
 //BA.debugLineNum = 56;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (javi.prieto.pictorario.httpjob)(_vvvvvvvvvvvvvvvvvvvvvv0.Get((Object)(_taskid)));
 //BA.debugLineNum = 57;BA.debugLine="job.Response = Response";
_job._vvvvvvvv7 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse*/  = _response;
 //BA.debugLineNum = 58;BA.debugLine="Response.GetAsynchronously(\"response\", File.OpenO";
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_vvvv7,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False).getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private hc As OkHttpClient";
_vvvvvvvvvvvvvvvvvvvvvvv1 = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private TaskIdToJob As Map";
_vvvvvvvvvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 9;BA.debugLine="Public TempFolder As String";
_vvvv7 = "";
 //BA.debugLineNum = 10;BA.debugLine="Private taskCounter As Int";
_vvvvvvvvvvvvvvvvvvvvvvv2 = 0;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Private Sub Response_StreamFinish (Success As Bool";
 //BA.debugLineNum = 63;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 64;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,_success,"");
 }else {
 //BA.debugLineNum = 66;BA.debugLine="CompleteJob(TaskId, Success, LastException.Messa";
_vvvvvvvvvvvvvvvvvvvvvv7(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 16;BA.debugLine="TempFolder = File.DirInternalCache";
_vvvv7 = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 //BA.debugLineNum = 17;BA.debugLine="Try";
try { //BA.debugLineNum = 18;BA.debugLine="File.WriteString(TempFolder, \"~test.test\", \"test";
anywheresoftware.b4a.keywords.Common.File.WriteString(_vvvv7,"~test.test","test");
 //BA.debugLineNum = 19;BA.debugLine="File.Delete(TempFolder, \"~test.test\")";
anywheresoftware.b4a.keywords.Common.File.Delete(_vvvv7,"~test.test");
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 21;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("89502728",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(processBA)),0);
 //BA.debugLineNum = 22;BA.debugLine="Log(\"Switching to File.DirInternal\")";
anywheresoftware.b4a.keywords.Common.LogImpl("89502729","Switching to File.DirInternal",0);
 //BA.debugLineNum = 23;BA.debugLine="TempFolder = File.DirInternal";
_vvvv7 = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 };
 //BA.debugLineNum = 28;BA.debugLine="hc.Initialize(\"hc\")";
_vvvvvvvvvvvvvvvvvvvvvvv1.Initialize("hc");
 //BA.debugLineNum = 29;BA.debugLine="TaskIdToJob.Initialize";
_vvvvvvvvvvvvvvvvvvvvvv0.Initialize();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 33;BA.debugLine="Service.StopAutomaticForeground";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _submitjob(javi.prieto.pictorario.httpjob _job) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Public Sub SubmitJob(job As HttpJob)";
 //BA.debugLineNum = 43;BA.debugLine="If hc.IsInitialized = False Then Service_Create";
if (_vvvvvvvvvvvvvvvvvvvvvvv1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_service_create();};
 //BA.debugLineNum = 44;BA.debugLine="taskCounter = taskCounter + 1";
_vvvvvvvvvvvvvvvvvvvvvvv2 = (int) (_vvvvvvvvvvvvvvvvvvvvvvv2+1);
 //BA.debugLineNum = 45;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
_vvvvvvvvvvvvvvvvvvvvvv0.Put((Object)(_vvvvvvvvvvvvvvvvvvvvvvv2),(Object)(_job));
 //BA.debugLineNum = 46;BA.debugLine="If job.Username <> \"\" And job.Password <> \"\" Then";
if ((_job._vvvvvvvv1 /*String*/ ).equals("") == false && (_job._vvvvvvvv2 /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 47;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounte";
_vvvvvvvvvvvvvvvvvvvvvvv1.ExecuteCredentials(processBA,_job._vvvvvv1 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_vvvvvvvvvvvvvvvvvvvvvvv2,_job._vvvvvvvv1 /*String*/ ,_job._vvvvvvvv2 /*String*/ );
 }else {
 //BA.debugLineNum = 49;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
_vvvvvvvvvvvvvvvvvvvvvvv1.Execute(processBA,_job._vvvvvv1 /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ (),_vvvvvvvvvvvvvvvvvvvvvvv2);
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
}
