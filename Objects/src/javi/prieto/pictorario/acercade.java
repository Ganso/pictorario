package javi.prieto.pictorario;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class acercade extends Activity implements B4AActivity{
	public static acercade mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.acercade");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (acercade).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.acercade");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.acercade", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (acercade) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (acercade) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return acercade.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (acercade) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            acercade mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (acercade) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ButtonWrapper _volver = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _logotipo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _pictogramas = null;
public anywheresoftware.b4a.objects.LabelWrapper _pictorario = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _programador = null;
public anywheresoftware.b4a.objects.LabelWrapper _textoarasaac = null;
public anywheresoftware.b4a.objects.LabelWrapper _textoautor = null;
public anywheresoftware.b4a.objects.LabelWrapper _parateo = null;
public anywheresoftware.b4a.objects.LabelWrapper _versionapp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vervideo = null;
public anywheresoftware.b4a.objects.LabelWrapper _pulsalosiconos = null;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvv4 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv5 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv6 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvv0 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.starter _vvvvvvvvvv3 = null;
public javi.prieto.pictorario.httputils2service _vvvvvvvvvv4 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cs1 = null;
anywheresoftware.b4a.objects.CSBuilder _cs2 = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Dim cs1,cs2 As CSBuilder";
_cs1 = new anywheresoftware.b4a.objects.CSBuilder();
_cs2 = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 29;BA.debugLine="Activity.LoadLayout(\"AcercaDe\")";
mostCurrent._activity.LoadLayout("AcercaDe",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="TextoAutor.Text=cs1.Initialize.Bold.Append(\"Aplic";
mostCurrent._textoautor.setText(BA.ObjectToCharSequence(_cs1.Initialize().Bold().Append(BA.ObjectToCharSequence("Aplicación: ")).Pop().Append(BA.ObjectToCharSequence("Javier Prieto Martínez (www.ganso.org)")).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF)).Bold().Append(BA.ObjectToCharSequence("Licencia: ")).Pop().Append(BA.ObjectToCharSequence("CC (BY-NC-SA)")).getObject()));
 //BA.debugLineNum = 32;BA.debugLine="TextoArasaac.Text=cs2.Initialize.Bold.Append(\"Pic";
mostCurrent._textoarasaac.setText(BA.ObjectToCharSequence(_cs2.Initialize().Bold().Append(BA.ObjectToCharSequence("Pictogramas: ")).Pop().Append(BA.ObjectToCharSequence("Sergio Palao")).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF)).Bold().Append(BA.ObjectToCharSequence("Procedencia: ")).Pop().Append(BA.ObjectToCharSequence("ARASAAC (www.arasaac.org)")).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF)).Bold().Append(BA.ObjectToCharSequence("Licencia: ")).Pop().Append(BA.ObjectToCharSequence("CC (BY-NC-SA)")).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF)).Bold().Append(BA.ObjectToCharSequence("Propiedad: ")).Pop().Append(BA.ObjectToCharSequence("Gobierno de Aragón")).getObject()));
 //BA.debugLineNum = 35;BA.debugLine="Dim SU As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 36;BA.debugLine="Do While SU.MeasureMultilineTextHeight(Pictorario";
while (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._pictorario.getObject()),BA.ObjectToCharSequence(mostCurrent._pictorario.getText()))>mostCurrent._pictorario.getHeight()) {
 //BA.debugLineNum = 37;BA.debugLine="Pictorario.TextSize=Pictorario.TextSize-1";
mostCurrent._pictorario.setTextSize((float) (mostCurrent._pictorario.getTextSize()-1));
 }
;
 //BA.debugLineNum = 39;BA.debugLine="Do While SU.MeasureMultilineTextHeight(TextoAutor";
while (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._textoautor.getObject()),BA.ObjectToCharSequence(mostCurrent._textoautor.getText()))>mostCurrent._textoautor.getHeight()) {
 //BA.debugLineNum = 40;BA.debugLine="TextoAutor.TextSize=TextoAutor.TextSize-1";
mostCurrent._textoautor.setTextSize((float) (mostCurrent._textoautor.getTextSize()-1));
 }
;
 //BA.debugLineNum = 42;BA.debugLine="Do While SU.MeasureMultilineTextHeight(TextoArasa";
while (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._textoarasaac.getObject()),BA.ObjectToCharSequence(mostCurrent._textoarasaac.getText()))>mostCurrent._textoarasaac.getHeight()) {
 //BA.debugLineNum = 43;BA.debugLine="TextoArasaac.TextSize=TextoArasaac.TextSize-1";
mostCurrent._textoarasaac.setTextSize((float) (mostCurrent._textoarasaac.getTextSize()-1));
 }
;
 //BA.debugLineNum = 45;BA.debugLine="Do While SU.MeasureMultilineTextHeight(PulsaLosIc";
while (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._pulsalosiconos.getObject()),BA.ObjectToCharSequence(mostCurrent._pulsalosiconos.getText()))>mostCurrent._pulsalosiconos.getHeight()) {
 //BA.debugLineNum = 46;BA.debugLine="PulsaLosIconos.TextSize=PulsaLosIconos.TextSize-";
mostCurrent._pulsalosiconos.setTextSize((float) (mostCurrent._pulsalosiconos.getTextSize()-1));
 }
;
 //BA.debugLineNum = 53;BA.debugLine="VersionApp.Text=Application.VersionName";
mostCurrent._versionapp.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getVersionName()));
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.acercade parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.acercade parent;
int _keycode;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 96;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
if (true) break;

case 1:
//if
this.state = 4;
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 97;BA.debugLine="Sleep(0) 'No hace nada";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 5;
return;
case 5:
//C
this.state = 4;
;
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private Volver As Button";
mostCurrent._volver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private Logotipo As ImageView";
mostCurrent._logotipo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Pictogramas As ImageView";
mostCurrent._pictogramas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Pictorario As Label";
mostCurrent._pictorario = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Programador As ImageView";
mostCurrent._programador = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private TextoArasaac As Label";
mostCurrent._textoarasaac = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private TextoAutor As Label";
mostCurrent._textoautor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private ParaTeo As Label";
mostCurrent._parateo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private VersionApp As Label";
mostCurrent._versionapp = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private VerVideo As Button";
mostCurrent._vervideo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private PulsaLosIconos As Label";
mostCurrent._pulsalosiconos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _logotipo_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub Logotipo_Click";
 //BA.debugLineNum = 81;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 82;BA.debugLine="StartActivity(p.OpenBrowser(\"http://blog.ganso.or";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://blog.ganso.org/proyectos/pictorario")));
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _pictogramas_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 75;BA.debugLine="Sub Pictogramas_Click";
 //BA.debugLineNum = 76;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 77;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.arasaac.o";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://www.arasaac.org")));
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _programador_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 70;BA.debugLine="Sub Programador_Click";
 //BA.debugLineNum = 71;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 72;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.ganso.org";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://www.ganso.org")));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _versionapp_click() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub VersionApp_Click";
 //BA.debugLineNum = 92;BA.debugLine="Msgbox2(\"Novedades de la versión:\"&CRLF&CRLF&Star";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Novedades de la versión:"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._vvvvvvvvvv3._v6 /*String*/ ),BA.ObjectToCharSequence("Versión "+anywheresoftware.b4a.keywords.Common.Application.getVersionName()),"Continuar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _vervideo_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 85;BA.debugLine="Sub VerVideo_Click";
 //BA.debugLineNum = 86;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 88;BA.debugLine="StartActivity(p.OpenBrowser(\"http://Bit.ly/VideoP";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("http://Bit.ly/VideoPictorario")));
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _volver_click() throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Volver_Click";
 //BA.debugLineNum = 67;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
}
