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

public class configurarsecuencia extends Activity implements B4AActivity{
	public static configurarsecuencia mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.configurarsecuencia");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (configurarsecuencia).");
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
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
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
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.configurarsecuencia");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.configurarsecuencia", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (configurarsecuencia) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (configurarsecuencia) Resume **");
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
		return configurarsecuencia.class;
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
        BA.LogInfo("** Activity (configurarsecuencia) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            configurarsecuencia mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (configurarsecuencia) Resume **");
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
public static int _vvvvvvvvv0 = 0;
public static int _vvvvvvvvv5 = 0;
public static int _vvvvvvvvv6 = 0;
public static int _vvvvvvvvvv2 = 0;
public anywheresoftware.b4a.objects.ScrollViewWrapper _parametrossecuencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _vvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.EditTextWrapper[] _vvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncancelar = null;
public static boolean _vvvvvvvv7 = false;
public static int _vvvvvvvvv2 = 0;
public static String _vvvvvvvv1 = "";
public static String _vvvvvvvv3 = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv1 = null;
public javi.prieto.pictorario.main _vvvvv4 = null;
public javi.prieto.pictorario.starter _vvv7 = null;
public javi.prieto.pictorario.visualizacion _vvvv2 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvv2 = null;
public javi.prieto.pictorario.acercade _vvv0 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).Initiali";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].Initialize();
 //BA.debugLineNum = 61;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero.";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.Initialize();
 //BA.debugLineNum = 63;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv7._vv6==mostCurrent._vvv7._v7)) { 
 //BA.debugLineNum = 64;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades = (int) (0);
 //BA.debugLineNum = 65;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].Descripcion = mostCurrent._vvvvvvvv1;
 //BA.debugLineNum = 66;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].pictograma = BA.NumberToString(7229);
 //BA.debugLineNum = 67;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.tipo = (int) (3);
 //BA.debugLineNum = 68;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.tam_icono = (int) (20);
 //BA.debugLineNum = 69;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.indicar_hora = (int) (1);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Secu";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv7.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv7._vv6),(Object)(mostCurrent._vvv7._v7));
 };
 //BA.debugLineNum = 74;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.configurarsecuencia parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.configurarsecuencia parent;
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
 //BA.debugLineNum = 604;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 605;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 607;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 321;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
public static String  _botonaceptar_click() throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Sub BotonAceptar_Click";
 //BA.debugLineNum = 306;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv7._vv6==mostCurrent._vvv7._v7)) { 
 //BA.debugLineNum = 307;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
mostCurrent._vvv7._vv5 = (int) (mostCurrent._vvv7._vv5+1);
 //BA.debugLineNum = 308;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv7.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv7._v7),(Object)(mostCurrent._vvv7._vv5-1));
 }else {
 //BA.debugLineNum = 310;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv7.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv7._v7),(Object)(mostCurrent._vvv7._vv6));
 };
 //BA.debugLineNum = 312;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvv7.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 313;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv4.getObject()));
 //BA.debugLineNum = 314;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static String  _botonanadiractividad_click() throws Exception{
 //BA.debugLineNum = 564;BA.debugLine="Sub BotonAnadirActividad_Click";
 //BA.debugLineNum = 565;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].Descripcion = mostCurrent._vvvvvvvv3;
 //BA.debugLineNum = 567;BA.debugLine="If (Starter.Secuencia(Starter.MaxSecuencias).num_";
if ((mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades>0)) { 
 //BA.debugLineNum = 568;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_inicio = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 569;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_inicio = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-1)].minuto_fin;
 }else {
 //BA.debugLineNum = 571;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_inicio = (int) (8);
 //BA.debugLineNum = 572;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_inicio = (int) (0);
 };
 //BA.debugLineNum = 575;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_fin = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_inicio;
 //BA.debugLineNum = 576;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_inicio+30);
 //BA.debugLineNum = 577;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin>59) { 
 //BA.debugLineNum = 578;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin-60);
 //BA.debugLineNum = 579;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_fin = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_fin+1);
 };
 //BA.debugLineNum = 582;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_fin*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin>(23*60+59)) { 
 //BA.debugLineNum = 583;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].hora_fin = (int) (23);
 //BA.debugLineNum = 584;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].minuto_fin = (int) (59);
 };
 //BA.debugLineNum = 587;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 588;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_acti";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades+1);
 //BA.debugLineNum = 589;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 //BA.debugLineNum = 590;BA.debugLine="End Sub";
return "";
}
public static String  _botoncancelar_click() throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Sub BotonCancelar_Click";
 //BA.debugLineNum = 295;BA.debugLine="SalidaConfigurarSecuencia";
_vvvvvvvv4();
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_focuschanged(boolean _tienefoco) throws Exception{
 //BA.debugLineNum = 551;BA.debugLine="Sub ConfigDescripcion_FocusChanged (TieneFoco As B";
 //BA.debugLineNum = 552;BA.debugLine="If TieneFoco==True And ConfigDescripcion.Text==De";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True && (mostCurrent._vvvvvvvv5.getText()).equals(mostCurrent._vvvvvvvv1)) { 
 //BA.debugLineNum = 553;BA.debugLine="ConfigDescripcion.Text=\"\"";
mostCurrent._vvvvvvvv5.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 554;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].Descripcion = "";
 //BA.debugLineNum = 555;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 557;BA.debugLine="If TieneFoco==False And ConfigDescripcion.Text==\"";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.False && (mostCurrent._vvvvvvvv5.getText()).equals("")) { 
 //BA.debugLineNum = 558;BA.debugLine="ConfigDescripcion.Text=DescripcionSecuenciaPorDe";
mostCurrent._vvvvvvvv5.setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvv1));
 //BA.debugLineNum = 559;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].Descripcion = mostCurrent._vvvvvvvv1;
 //BA.debugLineNum = 560;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 562;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 547;BA.debugLine="Sub ConfigDescripcion_TextChanged (Old As String,";
 //BA.debugLineNum = 548;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descripc";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].Descripcion = _new;
 //BA.debugLineNum = 549;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_focuschanged(boolean _tienefoco) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 513;BA.debugLine="Sub ConfigDescripcionAct_FocusChanged (TieneFoco A";
 //BA.debugLineNum = 514;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 515;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 517;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 518;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 520;BA.debugLine="If TieneFoco==True Then";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 521;BA.debugLine="If ConfigDescripcionAct(Act).Text==DescripcionAc";
if ((mostCurrent._vvvvvvvv6[_act].getText()).equals(mostCurrent._vvvvvvvv3)) { 
 //BA.debugLineNum = 522;BA.debugLine="ConfigDescripcionAct(Act).Text=\"\"";
mostCurrent._vvvvvvvv6[_act].setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 523;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].Descripcion = "";
 //BA.debugLineNum = 524;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 }else {
 //BA.debugLineNum = 527;BA.debugLine="If ConfigDescripcionAct(Act).Text==\"\" Then";
if ((mostCurrent._vvvvvvvv6[_act].getText()).equals("")) { 
 //BA.debugLineNum = 528;BA.debugLine="ConfigDescripcionAct(Act).Text=DescripcionActiv";
mostCurrent._vvvvvvvv6[_act].setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvv3));
 //BA.debugLineNum = 529;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].Descripcion = mostCurrent._vvvvvvvv3;
 //BA.debugLineNum = 530;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 };
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_textchanged(String _old,String _new) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 536;BA.debugLine="Sub ConfigDescripcionAct_TextChanged (Old As Strin";
 //BA.debugLineNum = 537;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 538;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 540;BA.debugLine="If Inicializando==False Then";
if (_vvvvvvvv7==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 541;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 542;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 543;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].Descripcion = _new;
 };
 //BA.debugLineNum = 545;BA.debugLine="End Sub";
return "";
}
public static String  _confighorafinalact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 443;BA.debugLine="Sub ConfigHoraFinalAct_Click";
 //BA.debugLineNum = 444;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 445;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 446;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 447;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 449;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 450;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 452;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_fin);
 //BA.debugLineNum = 453;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_fin);
 //BA.debugLineNum = 454;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 455;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora fina";
_resultado = _dialogotiempo.Show("Indica la hora final de la actividad","Hora final","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 457;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 458;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute<St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()<mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_inicio*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_inicio) { 
 //BA.debugLineNum = 460;BA.debugLine="Msgbox(\"La hora de finalización de una activida";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La hora de finalización de una actividad no puede ser anterior a la de inicio."),BA.ObjectToCharSequence("Hora inválida"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 462;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_fin = _dialogotiempo.getHour();
 //BA.debugLineNum = 463;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_fin = _dialogotiempo.getMinute();
 //BA.debugLineNum = 464;BA.debugLine="If QuitarSolapes==True Then";
if (_vvvvvvvv0()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 465;BA.debugLine="Msgbox(\"Se ha corregido la hora final de la ac";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha corregido la hora final de la actividad para evitar solapes."),BA.ObjectToCharSequence("Hora final corregida"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 467;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 };
 };
 //BA.debugLineNum = 470;BA.debugLine="End Sub";
return "";
}
public static String  _confighorainicioact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 365;BA.debugLine="Sub ConfigHoraInicioAct_Click";
 //BA.debugLineNum = 366;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 367;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 368;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 369;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 371;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 372;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 374;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_inicio);
 //BA.debugLineNum = 375;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_inicio);
 //BA.debugLineNum = 376;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 377;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora inic";
_resultado = _dialogotiempo.Show("Indica la hora inicial de la actividad","Hora inicial","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 379;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 380;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_inicio = _dialogotiempo.getHour();
 //BA.debugLineNum = 381;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_inicio = _dialogotiempo.getMinute();
 //BA.debugLineNum = 382;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute>St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()>mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_fin*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_fin) { 
 //BA.debugLineNum = 385;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_fin = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].hora_inicio;
 //BA.debugLineNum = 386;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_fin = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_act].minuto_inicio;
 };
 //BA.debugLineNum = 388;BA.debugLine="If OrdenarActividades==True Then";
if (_vvvvvvvvv1()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 389;BA.debugLine="Msgbox(\"Se ha colocado la actividad en su posic";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha colocado la actividad en su posición correcta."),BA.ObjectToCharSequence("Actividades reorganizadas"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 391;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 };
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _configindicarhora_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tiposindicador = null;
int _resultado = 0;
 //BA.debugLineNum = 274;BA.debugLine="Sub ConfigIndicarHora_Click";
 //BA.debugLineNum = 275;BA.debugLine="Dim TiposIndicador As List";
_tiposindicador = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 276;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 278;BA.debugLine="TiposIndicador.Initialize";
_tiposindicador.Initialize();
 //BA.debugLineNum = 279;BA.debugLine="TiposIndicador.AddAll(Starter.DescripcionMinutero";
_tiposindicador.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv7._vv2));
 //BA.debugLineNum = 281;BA.debugLine="resultado=InputList(TiposIndicador,\"Indicar hora";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tiposindicador,BA.ObjectToCharSequence("Indicar hora actual"),mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.indicar_hora,mostCurrent.activityBA);
 //BA.debugLineNum = 282;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 283;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.indicar_hora = _resultado;
 //BA.debugLineNum = 284;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 };
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return "";
}
public static String  _configopcionesact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _act = 0;
anywheresoftware.b4a.objects.collections.List _opciones = null;
int _resultado = 0;
int _nact = 0;
 //BA.debugLineNum = 343;BA.debugLine="Sub ConfigOpcionesAct_Click";
 //BA.debugLineNum = 344;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 345;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 346;BA.debugLine="Dim Opciones As List";
_opciones = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 347;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 349;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 350;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 352;BA.debugLine="Opciones.Initialize2(Array As String(\"Borrar acti";
_opciones.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Borrar actividad","CANCELAR"}));
 //BA.debugLineNum = 353;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_opciones,BA.ObjectToCharSequence("Acción"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 355;BA.debugLine="If resultado=0 Then";
if (_resultado==0) { 
 //BA.debugLineNum = 356;BA.debugLine="For nAct=Act To Starter.Secuencia(Starter.MaxSec";
{
final int step10 = 1;
final int limit10 = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-2);
_nact = _act ;
for (;_nact <= limit10 ;_nact = _nact + step10 ) {
 //BA.debugLineNum = 358;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_nact] = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_nact+1)];
 }
};
 //BA.debugLineNum = 360;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-1);
 };
 //BA.debugLineNum = 362;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _configpictograma_click() throws Exception{
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 325;BA.debugLine="Sub ConfigPictograma_Click";
 //BA.debugLineNum = 326;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 327;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 328;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 329;BA.debugLine="PictogramaEditado=-1";
_vvvvvvvvv2 = (int) (-1);
 //BA.debugLineNum = 330;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv2.getObject()));
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _configpictogramaact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 333;BA.debugLine="Sub ConfigPictogramaAct_Click";
 //BA.debugLineNum = 334;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 335;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 336;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 337;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 338;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 339;BA.debugLine="PictogramaEditado=BotonPulsado.Tag";
_vvvvvvvvv2 = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 340;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv2.getObject()));
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static String  _configtamicono_valuechanged(int _valor,boolean _cambio) throws Exception{
 //BA.debugLineNum = 288;BA.debugLine="Sub ConfigTamIcono_ValueChanged (Valor As Int, Cam";
 //BA.debugLineNum = 289;BA.debugLine="If (Cambio==True) Then";
if ((_cambio==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 290;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.tam_icono = _valor;
 };
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _configtipotablero_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tipostablero = null;
int _resultado = 0;
 //BA.debugLineNum = 260;BA.debugLine="Sub ConfigTipoTablero_Click";
 //BA.debugLineNum = 261;BA.debugLine="Dim TiposTablero As List";
_tipostablero = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 262;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 264;BA.debugLine="TiposTablero.Initialize";
_tipostablero.Initialize();
 //BA.debugLineNum = 265;BA.debugLine="TiposTablero.AddAll(Starter.DescripcionTablero)";
_tipostablero.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv7._vv1));
 //BA.debugLineNum = 267;BA.debugLine="resultado=InputList(TiposTablero,\"Tipo de tablero";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tipostablero,BA.ObjectToCharSequence("Tipo de tablero"),mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.tipo,mostCurrent.activityBA);
 //BA.debugLineNum = 268;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 269;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].tablero.tipo = _resultado;
 //BA.debugLineNum = 270;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 };
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvv2() throws Exception{
ResumableSub_DibujarConfigurarSecuencia rsub = new ResumableSub_DibujarConfigurarSecuencia(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_DibujarConfigurarSecuencia extends BA.ResumableSub {
public ResumableSub_DibujarConfigurarSecuencia(javi.prieto.pictorario.configurarsecuencia parent) {
this.parent = parent;
}
javi.prieto.pictorario.configurarsecuencia parent;
int _posicion = 0;
int _iniciovertical = 0;
int _finvertical = 0;
int _act = 0;
int step79;
int limit79;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 81;BA.debugLine="Dim Posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 83;BA.debugLine="If ParametrosSecuencia.IsInitialized==True Then";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.mostCurrent._parametrossecuencia.IsInitialized()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 84;BA.debugLine="Posicion=ParametrosSecuencia.ScrollPosition";
_posicion = parent.mostCurrent._parametrossecuencia.getScrollPosition();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 86;BA.debugLine="Posicion=0";
_posicion = (int) (0);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 89;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 90;BA.debugLine="Activity.LoadLayout(\"ConfigurarSecuencia\")";
parent.mostCurrent._activity.LoadLayout("ConfigurarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 92;BA.debugLine="Inicializando=True 'Para evitar que se lancen pro";
parent._vvvvvvvv7 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 94;BA.debugLine="EtiquetaInicial.Initialize(\"EtiquetaInicial\")";
parent.mostCurrent._vvvvvvvvv3.Initialize(mostCurrent.activityBA,"EtiquetaInicial");
 //BA.debugLineNum = 95;BA.debugLine="EtiquetaInicial.Text=\"Crear nueva secuencia\"";
parent.mostCurrent._vvvvvvvvv3.setText(BA.ObjectToCharSequence("Crear nueva secuencia"));
 //BA.debugLineNum = 96;BA.debugLine="If (Starter.SecuenciaActiva<Starter.MaxSecuencias";
if (true) break;

case 7:
//if
this.state = 10;
if ((parent.mostCurrent._vvv7._vv6<parent.mostCurrent._vvv7._v7)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 97;BA.debugLine="EtiquetaInicial.Text=\"Editar secuencia\"";
parent.mostCurrent._vvvvvvvvv3.setText(BA.ObjectToCharSequence("Editar secuencia"));
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 99;BA.debugLine="EtiquetaInicial.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 100;BA.debugLine="EtiquetaInicial.TextSize=24";
parent.mostCurrent._vvvvvvvvv3.setTextSize((float) (24));
 //BA.debugLineNum = 101;BA.debugLine="EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvv3.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 102;BA.debugLine="EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvv3.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 103;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiquetaInicial";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 105;BA.debugLine="ConfigPictograma.Initialize(\"ConfigPictograma\")";
parent.mostCurrent._vvvvvvvvv4.Initialize(mostCurrent.activityBA,"ConfigPictograma");
 //BA.debugLineNum = 106;BA.debugLine="ConfigPictograma.SetBackgroundImage(LoadBitmap(St";
parent.mostCurrent._vvvvvvvvv4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvv7._vvv4,parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].pictograma+".png").getObject()));
 //BA.debugLineNum = 107;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogram";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv4.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv5-parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvvv3.getTop()+parent.mostCurrent._vvvvvvvvv3.getHeight()+parent._vvvvvvvvv6),parent._vvvvvvvvv5,parent._vvvvvvvvv5);
 //BA.debugLineNum = 109;BA.debugLine="ConfigDescripcion.Initialize(\"ConfigDescripcion\")";
parent.mostCurrent._vvvvvvvv5.Initialize(mostCurrent.activityBA,"ConfigDescripcion");
 //BA.debugLineNum = 110;BA.debugLine="ConfigDescripcion.Text=Starter.Secuencia(Starter.";
parent.mostCurrent._vvvvvvvv5.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].Descripcion));
 //BA.debugLineNum = 111;BA.debugLine="ConfigDescripcion.TextColor=Colors.White";
parent.mostCurrent._vvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 112;BA.debugLine="ConfigDescripcion.TextSize=16";
parent.mostCurrent._vvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 113;BA.debugLine="ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvv5.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 114;BA.debugLine="ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 115;BA.debugLine="ConfigDescripcion.Color=Colors.DarkGray";
parent.mostCurrent._vvvvvvvv5.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 116;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripci";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv5.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvvv3.getTop()+parent.mostCurrent._vvvvvvvvv3.getHeight()+parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvvv4.getLeft()-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 118;BA.debugLine="EtiqTipoTablero.Initialize(\"EtiqTipoTablero\")";
parent.mostCurrent._vvvvvvvvv7.Initialize(mostCurrent.activityBA,"EtiqTipoTablero");
 //BA.debugLineNum = 119;BA.debugLine="EtiqTipoTablero.Text=\"Tipo de tablero:\"";
parent.mostCurrent._vvvvvvvvv7.setText(BA.ObjectToCharSequence("Tipo de tablero:"));
 //BA.debugLineNum = 120;BA.debugLine="EtiqTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 121;BA.debugLine="EtiqTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvv7.setTextSize((float) (16));
 //BA.debugLineNum = 122;BA.debugLine="EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 123;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTipoTablero";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv7.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvv5.getHeight()+parent._vvvvvvvvv6),parent._vvvvvvvvv0,parent._vvvvvvvvv5);
 //BA.debugLineNum = 125;BA.debugLine="ConfigTipoTablero.Initialize(\"ConfigTipoTablero\")";
parent.mostCurrent._vvvvvvvvvv1.Initialize(mostCurrent.activityBA,"ConfigTipoTablero");
 //BA.debugLineNum = 126;BA.debugLine="ConfigTipoTablero.Text=Starter.DescripcionTablero";
parent.mostCurrent._vvvvvvvvvv1.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv7._vv1[parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].tablero.tipo]));
 //BA.debugLineNum = 127;BA.debugLine="ConfigTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 128;BA.debugLine="ConfigTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvvv1.setTextSize((float) (16));
 //BA.debugLineNum = 129;BA.debugLine="ConfigTipoTablero.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv1.setColor(parent._vvvvvvvvvv2);
 //BA.debugLineNum = 130;BA.debugLine="ConfigTipoTablero.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 131;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTipoTable";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv1.getObject()),(int) (parent._vvvvvvvvv0+parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvv5.getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv0-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 133;BA.debugLine="EtiqIndicarHora.Initialize(\"EtiqIndicarHora\")";
parent.mostCurrent._vvvvvvvvvv3.Initialize(mostCurrent.activityBA,"EtiqIndicarHora");
 //BA.debugLineNum = 134;BA.debugLine="EtiqIndicarHora.Text=\"Indicar hora actual:\"";
parent.mostCurrent._vvvvvvvvvv3.setText(BA.ObjectToCharSequence("Indicar hora actual:"));
 //BA.debugLineNum = 135;BA.debugLine="EtiqIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 136;BA.debugLine="EtiqIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvv3.setTextSize((float) (16));
 //BA.debugLineNum = 137;BA.debugLine="EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvv3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 138;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqIndicarHora";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv3.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvvv1.getHeight()+parent._vvvvvvvvv6),parent._vvvvvvvvv0,parent._vvvvvvvvv5);
 //BA.debugLineNum = 140;BA.debugLine="ConfigIndicarHora.Initialize(\"ConfigIndicarHora\")";
parent.mostCurrent._vvvvvvvvvv4.Initialize(mostCurrent.activityBA,"ConfigIndicarHora");
 //BA.debugLineNum = 141;BA.debugLine="ConfigIndicarHora.Text=Starter.DescripcionMinuter";
parent.mostCurrent._vvvvvvvvvv4.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv7._vv2[parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].tablero.indicar_hora]));
 //BA.debugLineNum = 142;BA.debugLine="ConfigIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 143;BA.debugLine="ConfigIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvv4.setTextSize((float) (16));
 //BA.debugLineNum = 144;BA.debugLine="ConfigIndicarHora.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv4.setColor(parent._vvvvvvvvvv2);
 //BA.debugLineNum = 145;BA.debugLine="ConfigIndicarHora.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 146;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigIndicarHo";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv4.getObject()),(int) (parent._vvvvvvvvv0+parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvvv1.getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv0-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 148;BA.debugLine="EtiqTamIcono.Initialize(\"EtiqTamIcono\")";
parent.mostCurrent._vvvvvvvvvv5.Initialize(mostCurrent.activityBA,"EtiqTamIcono");
 //BA.debugLineNum = 149;BA.debugLine="EtiqTamIcono.Text=\"Tamaño de los iconos:\"";
parent.mostCurrent._vvvvvvvvvv5.setText(BA.ObjectToCharSequence("Tamaño de los iconos:"));
 //BA.debugLineNum = 150;BA.debugLine="EtiqTamIcono.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 151;BA.debugLine="EtiqTamIcono.TextSize=16";
parent.mostCurrent._vvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 152;BA.debugLine="EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 153;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTamIcono,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv5.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvvv4.getHeight()+parent._vvvvvvvvv6),parent._vvvvvvvvv0,parent._vvvvvvvvv5);
 //BA.debugLineNum = 155;BA.debugLine="ConfigTamIcono.Initialize(\"ConfigTamIcono\")";
parent.mostCurrent._vvvvvvvvvv6.Initialize(mostCurrent.activityBA,"ConfigTamIcono");
 //BA.debugLineNum = 156;BA.debugLine="ConfigTamIcono.Value=Starter.Secuencia(Starter.Ma";
parent.mostCurrent._vvvvvvvvvv6.setValue(parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].tablero.tam_icono);
 //BA.debugLineNum = 157;BA.debugLine="ConfigTamIcono.Max=30";
parent.mostCurrent._vvvvvvvvvv6.setMax((int) (30));
 //BA.debugLineNum = 158;BA.debugLine="ConfigTamIcono.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv6.setColor(parent._vvvvvvvvvv2);
 //BA.debugLineNum = 159;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTamIcono,";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv6.getObject()),(int) (parent._vvvvvvvvv0+parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvvv4.getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv0-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 161;BA.debugLine="EtiqActividades.Initialize(\"EtiqActividades\")";
parent.mostCurrent._vvvvvvvvvv7.Initialize(mostCurrent.activityBA,"EtiqActividades");
 //BA.debugLineNum = 162;BA.debugLine="EtiqActividades.Text=\"Actividades\"";
parent.mostCurrent._vvvvvvvvvv7.setText(BA.ObjectToCharSequence("Actividades"));
 //BA.debugLineNum = 163;BA.debugLine="EtiqActividades.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 164;BA.debugLine="EtiqActividades.TextSize=24";
parent.mostCurrent._vvvvvvvvvv7.setTextSize((float) (24));
 //BA.debugLineNum = 165;BA.debugLine="EtiqActividades.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvvv7.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 166;BA.debugLine="EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 167;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqActividades";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv7.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvvvv6.getTop()+parent.mostCurrent._vvvvvvvvvv6.getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 169;BA.debugLine="Dim InicioVertical As Int";
_iniciovertical = 0;
 //BA.debugLineNum = 170;BA.debugLine="Dim FinVertical As Int";
_finvertical = 0;
 //BA.debugLineNum = 172;BA.debugLine="FinVertical=EtiqActividades.Top+EtiqActividades.H";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvvv7.getHeight());
 //BA.debugLineNum = 173;BA.debugLine="InicioVertical=FinVertical+SeparacionCasillas";
_iniciovertical = (int) (_finvertical+parent._vvvvvvvvv6);
 //BA.debugLineNum = 175;BA.debugLine="For Act=0 To Starter.Secuencia(Starter.MaxSecuenc";
if (true) break;

case 11:
//for
this.state = 18;
step79 = 1;
limit79 = (int) (parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].num_actividades-1);
_act = (int) (0) ;
this.state = 27;
if (true) break;

case 27:
//C
this.state = 18;
if ((step79 > 0 && _act <= limit79) || (step79 < 0 && _act >= limit79)) this.state = 13;
if (true) break;

case 28:
//C
this.state = 27;
_act = ((int)(0 + _act + step79)) ;
if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 177;BA.debugLine="If (Act>0) Then";
if (true) break;

case 14:
//if
this.state = 17;
if ((_act>0)) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 178;BA.debugLine="InicioVertical=ConfigHoraInicioAct(Act-1).Top+C";
_iniciovertical = (int) (parent.mostCurrent._vvvvvvvvvv0[(int) (_act-1)].getTop()+parent.mostCurrent._vvvvvvvvvv0[(int) (_act-1)].getHeight()+4*parent._vvvvvvvvv6);
 if (true) break;

case 17:
//C
this.state = 28;
;
 //BA.debugLineNum = 181;BA.debugLine="ConfigPictogramaAct(Act).Initialize(\"ConfigPicto";
parent.mostCurrent._vvvvvvvvvvv1[_act].Initialize(mostCurrent.activityBA,"ConfigPictogramaAct");
 //BA.debugLineNum = 182;BA.debugLine="ConfigPictogramaAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv1[_act].setTag((Object)(_act));
 //BA.debugLineNum = 183;BA.debugLine="ConfigPictogramaAct(Act).SetBackgroundImage(Load";
parent.mostCurrent._vvvvvvvvvvv1[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvv7._vvv4,parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].Pictograma+".png").getObject()));
 //BA.debugLineNum = 184;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogra";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv1[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv5-parent._vvvvvvvvv6),_iniciovertical,parent._vvvvvvvvv5,parent._vvvvvvvvv5);
 //BA.debugLineNum = 186;BA.debugLine="ConfigDescripcionAct(Act).Initialize(\"ConfigDesc";
parent.mostCurrent._vvvvvvvv6[_act].Initialize(mostCurrent.activityBA,"ConfigDescripcionAct");
 //BA.debugLineNum = 187;BA.debugLine="ConfigDescripcionAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvv6[_act].setTag((Object)(_act));
 //BA.debugLineNum = 188;BA.debugLine="ConfigDescripcionAct(Act).Text=Starter.Actividad";
parent.mostCurrent._vvvvvvvv6[_act].setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].Descripcion));
 //BA.debugLineNum = 189;BA.debugLine="ConfigDescripcionAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv6[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 190;BA.debugLine="ConfigDescripcionAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvv6[_act].setTextSize((float) (16));
 //BA.debugLineNum = 191;BA.debugLine="ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity";
parent.mostCurrent._vvvvvvvv6[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 192;BA.debugLine="ConfigDescripcionAct(Act).Color=Starter.Colores(";
parent.mostCurrent._vvvvvvvv6[_act].setColor(parent.mostCurrent._vvv7._vv4[_act]);
 //BA.debugLineNum = 193;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripc";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv6[_act].getObject()),parent._vvvvvvvvv6,_iniciovertical,(int) (parent.mostCurrent._vvvvvvvvvvv1[_act].getLeft()-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 195;BA.debugLine="ConfigHoraInicioAct(Act).Initialize(\"ConfigHoraI";
parent.mostCurrent._vvvvvvvvvv0[_act].Initialize(mostCurrent.activityBA,"ConfigHoraInicioAct");
 //BA.debugLineNum = 196;BA.debugLine="ConfigHoraInicioAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvv0[_act].setTag((Object)(_act));
 //BA.debugLineNum = 197;BA.debugLine="ConfigHoraInicioAct(Act).Text=\"Desde\"&CRLF&Numbe";
parent.mostCurrent._vvvvvvvvvv0[_act].setText(BA.ObjectToCharSequence("Desde"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].hora_inicio,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].minuto_inicio,(int) (2),(int) (0))));
 //BA.debugLineNum = 198;BA.debugLine="ConfigHoraInicioAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv0[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 199;BA.debugLine="ConfigHoraInicioAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvv0[_act].setTextSize((float) (16));
 //BA.debugLineNum = 200;BA.debugLine="ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.";
parent.mostCurrent._vvvvvvvvvv0[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 201;BA.debugLine="ConfigHoraInicioAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv0[_act].setColor(parent._vvvvvvvvvv2);
 //BA.debugLineNum = 202;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraInic";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv0[_act].getObject()),(int) (3*parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvv6[_act].getTop()+parent.mostCurrent._vvvvvvvv6[_act].getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvv6-parent._vvvvvvvvv5/(double)2),parent._vvvvvvvvv5);
 //BA.debugLineNum = 204;BA.debugLine="ConfigHoraFinalAct(Act).Initialize(\"ConfigHoraFi";
parent.mostCurrent._vvvvvvvvvvv2[_act].Initialize(mostCurrent.activityBA,"ConfigHoraFinalAct");
 //BA.debugLineNum = 205;BA.debugLine="ConfigHoraFinalAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv2[_act].setTag((Object)(_act));
 //BA.debugLineNum = 206;BA.debugLine="ConfigHoraFinalAct(Act).Text=\"Hasta\"&CRLF&Number";
parent.mostCurrent._vvvvvvvvvvv2[_act].setText(BA.ObjectToCharSequence("Hasta"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].hora_fin,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv7._vv0[parent.mostCurrent._vvv7._v7][_act].minuto_fin,(int) (2),(int) (0))));
 //BA.debugLineNum = 207;BA.debugLine="ConfigHoraFinalAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv2[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 208;BA.debugLine="ConfigHoraFinalAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvv2[_act].setTextSize((float) (16));
 //BA.debugLineNum = 209;BA.debugLine="ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.C";
parent.mostCurrent._vvvvvvvvvvv2[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 210;BA.debugLine="ConfigHoraFinalAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvv2[_act].setColor(parent._vvvvvvvvvv2);
 //BA.debugLineNum = 211;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraFina";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv2[_act].getObject()),(int) (parent.mostCurrent._vvvvvvvvvv0[_act].getLeft()+parent.mostCurrent._vvvvvvvvvv0[_act].getWidth()+parent._vvvvvvvvv6),(int) (parent.mostCurrent._vvvvvvvv6[_act].getTop()+parent.mostCurrent._vvvvvvvv6[_act].getHeight()+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvv6-parent._vvvvvvvvv5/(double)2),parent._vvvvvvvvv5);
 //BA.debugLineNum = 213;BA.debugLine="ConfigOpcionesAct(Act).Initialize(\"ConfigOpcione";
parent.mostCurrent._vvvvvvvvvvv3[_act].Initialize(mostCurrent.activityBA,"ConfigOpcionesAct");
 //BA.debugLineNum = 214;BA.debugLine="ConfigOpcionesAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv3[_act].setTag((Object)(_act));
 //BA.debugLineNum = 215;BA.debugLine="ConfigOpcionesAct(Act).SetBackgroundImage(LoadBi";
parent.mostCurrent._vvvvvvvvvvv3[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 216;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigOpciones";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv3[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv5-parent._vvvvvvvvv6),parent.mostCurrent._vvvvvvvvvvv2[_act].getTop(),parent._vvvvvvvvv5,parent._vvvvvvvvv5);
 //BA.debugLineNum = 218;BA.debugLine="FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHo";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvvv2[_act].getTop()+parent.mostCurrent._vvvvvvvvvvv2[_act].getHeight());
 if (true) break;
if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 222;BA.debugLine="BotonAnadirActividad.Initialize(\"BotonAnadirActiv";
parent.mostCurrent._vvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"BotonAnadirActividad");
 //BA.debugLineNum = 223;BA.debugLine="BotonAnadirActividad.Text=\"Añadir Actividad\"";
parent.mostCurrent._vvvvvvvvvvv4.setText(BA.ObjectToCharSequence("Añadir Actividad"));
 //BA.debugLineNum = 224;BA.debugLine="BotonAnadirActividad.TextSize=16";
parent.mostCurrent._vvvvvvvvvvv4.setTextSize((float) (16));
 //BA.debugLineNum = 225;BA.debugLine="BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTE";
parent.mostCurrent._vvvvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 226;BA.debugLine="BotonAnadirActividad.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 227;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 19:
//if
this.state = 22;
if (parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].num_actividades==parent.mostCurrent._vvv7._v0) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 228;BA.debugLine="BotonAnadirActividad.Enabled=False";
parent.mostCurrent._vvvvvvvvvvv4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 22:
//C
this.state = 23;
;
 //BA.debugLineNum = 230;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAnadirActi";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv4.getObject()),parent._vvvvvvvvv6,(int) (_finvertical+parent._vvvvvvvvv6),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 232;BA.debugLine="BotonAceptar.Initialize(\"BotonAceptar\")";
parent.mostCurrent._vvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"BotonAceptar");
 //BA.debugLineNum = 233;BA.debugLine="BotonAceptar.Text=\"Aceptar\"";
parent.mostCurrent._vvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Aceptar"));
 //BA.debugLineNum = 234;BA.debugLine="BotonAceptar.TextSize=16";
parent.mostCurrent._vvvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 235;BA.debugLine="BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTIC";
parent.mostCurrent._vvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 236;BA.debugLine="BotonAceptar.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 237;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAceptar,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv5.getObject()),parent._vvvvvvvvv6,(int) (parent.mostCurrent._vvvvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvvvv4.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 239;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 23:
//if
this.state = 26;
if (parent.mostCurrent._vvv7._vv7[parent.mostCurrent._vvv7._v7].num_actividades==0) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 240;BA.debugLine="BotonAceptar.Enabled=False";
parent.mostCurrent._vvvvvvvvvvv5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 26:
//C
this.state = -1;
;
 //BA.debugLineNum = 243;BA.debugLine="BotonCancelar.Initialize(\"BotonCancelar\")";
parent.mostCurrent._botoncancelar.Initialize(mostCurrent.activityBA,"BotonCancelar");
 //BA.debugLineNum = 244;BA.debugLine="BotonCancelar.Text=\"Cancelar\"";
parent.mostCurrent._botoncancelar.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 245;BA.debugLine="BotonCancelar.TextSize=16";
parent.mostCurrent._botoncancelar.setTextSize((float) (16));
 //BA.debugLineNum = 246;BA.debugLine="BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTI";
parent.mostCurrent._botoncancelar.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 247;BA.debugLine="BotonCancelar.TextColor=Colors.Black";
parent.mostCurrent._botoncancelar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 248;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonCancelar,5";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._botoncancelar.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+parent._vvvvvvvvv6),parent.mostCurrent._vvvvvvvvvvv5.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvv6),parent._vvvvvvvvv5);
 //BA.debugLineNum = 250;BA.debugLine="ParametrosSecuencia.Panel.Height=BotonCancelar.To";
parent.mostCurrent._parametrossecuencia.getPanel().setHeight((int) (parent.mostCurrent._botoncancelar.getTop()+parent.mostCurrent._botoncancelar.getHeight()+parent._vvvvvvvvv6));
 //BA.debugLineNum = 252;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 253;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 29;
return;
case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 254;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 256;BA.debugLine="Inicializando=False";
parent._vvvvvvvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim SeparacionHorizontal=25%X As Int  'Separación";
_vvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA);
 //BA.debugLineNum = 11;BA.debugLine="Dim TamCasilla=60dip As Int 'Tamaño vertical de l";
_vvvvvvvvv5 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 12;BA.debugLine="Dim SeparacionCasillas=5dip As Int 'Separación ve";
_vvvvvvvvv6 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 14;BA.debugLine="Dim ColorDeFondo=0xFFF0FFFF As Int";
_vvvvvvvvvv2 = (int) (0xfff0ffff);
 //BA.debugLineNum = 16;BA.debugLine="Private ParametrosSecuencia As ScrollView";
mostCurrent._parametrossecuencia = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim EtiquetaInicial As Label";
mostCurrent._vvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim ConfigDescripcion As EditText";
mostCurrent._vvvvvvvv5 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim ConfigPictograma As Label";
mostCurrent._vvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim EtiqTipoTablero As Label";
mostCurrent._vvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ConfigTipoTablero As Label";
mostCurrent._vvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim EtiqIndicarHora As Label";
mostCurrent._vvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim ConfigIndicarHora As Label";
mostCurrent._vvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim EtiqTamIcono As Label";
mostCurrent._vvvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ConfigTamIcono As SeekBar";
mostCurrent._vvvvvvvvvv6 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim EtiqActividades As Label";
mostCurrent._vvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ConfigDescripcionAct(Starter.MaxActividades)";
mostCurrent._vvvvvvvv6 = new anywheresoftware.b4a.objects.EditTextWrapper[mostCurrent._vvv7._v0];
{
int d0 = mostCurrent._vvvvvvvv6.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvv6[i0] = new anywheresoftware.b4a.objects.EditTextWrapper();
}
}
;
 //BA.debugLineNum = 31;BA.debugLine="Dim ConfigHoraInicioAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv7._v0];
{
int d0 = mostCurrent._vvvvvvvvvv0.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvv0[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 32;BA.debugLine="Dim ConfigHoraFinalAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv7._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv2.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv2[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 33;BA.debugLine="Dim ConfigPictogramaAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv7._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 34;BA.debugLine="Dim ConfigOpcionesAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv7._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 36;BA.debugLine="Dim BotonAnadirActividad As Button";
mostCurrent._vvvvvvvvvvv4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim BotonAceptar As Button";
mostCurrent._vvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim BotonCancelar As Button";
mostCurrent._botoncancelar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim Inicializando As Boolean";
_vvvvvvvv7 = false;
 //BA.debugLineNum = 47;BA.debugLine="Dim PictogramaEditado As Int";
_vvvvvvvvv2 = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim DescripcionSecuenciaPorDefecto=\"Pulsa aquí pa";
mostCurrent._vvvvvvvv1 = "Pulsa aquí para poner un nombre de secuencia";
 //BA.debugLineNum = 52;BA.debugLine="Dim DescripcionActividadPorDefecto=\"Nombre de la";
mostCurrent._vvvvvvvv3 = "Nombre de la nueva actividad";
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvv1() throws Exception{
int _i = 0;
int _j = 0;
int _hm_1 = 0;
int _hm_2 = 0;
javi.prieto.pictorario.starter._actividad _actint = null;
boolean _intercambiorealizado = false;
 //BA.debugLineNum = 395;BA.debugLine="Sub OrdenarActividades As Boolean";
 //BA.debugLineNum = 396;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 397;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 398;BA.debugLine="Dim ActInt As Actividad";
_actint = new javi.prieto.pictorario.starter._actividad();
 //BA.debugLineNum = 399;BA.debugLine="Dim IntercambioRealizado As Boolean";
_intercambiorealizado = false;
 //BA.debugLineNum = 401;BA.debugLine="IntercambioRealizado=False";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 404;BA.debugLine="For i=1 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-1);
_i = (int) (1) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 405;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuenci";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit7 ;_j = _j + step7 ) {
 //BA.debugLineNum = 406;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_1 = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].hora_inicio*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].minuto_inicio);
 //BA.debugLineNum = 407;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_2 = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 408;BA.debugLine="If hm_1>hm_2 Then";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 409;BA.debugLine="ActInt=Starter.ActividadSecuencia(Starter.MaxS";
_actint = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j];
 //BA.debugLineNum = 410;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j] = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)];
 //BA.debugLineNum = 411;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)] = _actint;
 //BA.debugLineNum = 412;BA.debugLine="IntercambioRealizado=True";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 //BA.debugLineNum = 418;BA.debugLine="QuitarSolapes";
_vvvvvvvv0();
 //BA.debugLineNum = 420;BA.debugLine="Return IntercambioRealizado";
if (true) return _intercambiorealizado;
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return false;
}
public static String  _pictogramaelegido(int _id) throws Exception{
 //BA.debugLineNum = 592;BA.debugLine="Sub PictogramaElegido(Id As Int)";
 //BA.debugLineNum = 593;BA.debugLine="If Id<>-1 Then 'Si no se ha pulsado en \"Cancelar\"";
if (_id!=-1) { 
 //BA.debugLineNum = 594;BA.debugLine="If PictogramaEditado==-1 Then 'Pictograma de la";
if (_vvvvvvvvv2==-1) { 
 //BA.debugLineNum = 595;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictog";
mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].pictograma = BA.NumberToString(_id);
 }else {
 //BA.debugLineNum = 597;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_vvvvvvvvv2].Pictograma = BA.NumberToString(_id);
 };
 //BA.debugLineNum = 599;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv2();
 };
 //BA.debugLineNum = 601;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvv0() throws Exception{
int _hm_1 = 0;
int _hm_2 = 0;
int _j = 0;
boolean _resultado = false;
 //BA.debugLineNum = 423;BA.debugLine="Sub QuitarSolapes As Boolean";
 //BA.debugLineNum = 424;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 425;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 426;BA.debugLine="Dim resultado As Boolean";
_resultado = false;
 //BA.debugLineNum = 428;BA.debugLine="resultado=False";
_resultado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 430;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvv7._vv7[mostCurrent._vvv7._v7].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit5 ;_j = _j + step5 ) {
 //BA.debugLineNum = 431;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_1 = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].hora_fin*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].minuto_fin);
 //BA.debugLineNum = 432;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_2 = (int) (mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 433;BA.debugLine="If hm_1>hm_2 Then 'Si es así, las iguala";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 434;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].hora_fin = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].hora_inicio;
 //BA.debugLineNum = 435;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][_j].minuto_fin = mostCurrent._vvv7._vv0[mostCurrent._vvv7._v7][(int) (_j+1)].minuto_inicio;
 //BA.debugLineNum = 436;BA.debugLine="resultado=True";
_resultado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 440;BA.debugLine="Return resultado";
if (true) return _resultado;
 //BA.debugLineNum = 441;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvv4() throws Exception{
 //BA.debugLineNum = 298;BA.debugLine="Sub SalidaConfigurarSecuencia";
 //BA.debugLineNum = 299;BA.debugLine="If Msgbox2(\"Se perderán todos los cambios realiza";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se perderán todos los cambios realizados."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"¿Está seguro de que desea salir sin guardarlos?"),BA.ObjectToCharSequence("Cancelar cambios"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 300;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv4.getObject()));
 //BA.debugLineNum = 301;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
}
