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
public static int _vvvvvvvvvv1 = 0;
public static int _vvvvvvvvv6 = 0;
public static int _vvvvvvvvv7 = 0;
public static int _vvvvvvvvvv3 = 0;
public anywheresoftware.b4a.objects.ScrollViewWrapper _parametrossecuencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _vvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.EditTextWrapper[] _vvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncancelar = null;
public static boolean _vvvvvvvv0 = false;
public static int _vvvvvvvvv3 = 0;
public static String _vvvvvvvv2 = "";
public static String _vvvvvvvv4 = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv2 = null;
public javi.prieto.pictorario.main _vvvvv5 = null;
public javi.prieto.pictorario.starter _vvv0 = null;
public javi.prieto.pictorario.visualizacion _vvvv3 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvv3 = null;
public javi.prieto.pictorario.acercade _vvvv1 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).Initiali";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].Initialize();
 //BA.debugLineNum = 56;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero.";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.Initialize();
 //BA.debugLineNum = 58;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv0._vv6==mostCurrent._vvv0._v7)) { 
 //BA.debugLineNum = 59;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades = (int) (0);
 //BA.debugLineNum = 60;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].Descripcion = mostCurrent._vvvvvvvv2;
 //BA.debugLineNum = 61;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].pictograma = BA.NumberToString(7229);
 //BA.debugLineNum = 62;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.tipo = (int) (3);
 //BA.debugLineNum = 63;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.tam_icono = (int) (20);
 //BA.debugLineNum = 64;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.indicar_hora = (int) (1);
 }else {
 //BA.debugLineNum = 66;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Secu";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv0.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv0._vv6),(Object)(mostCurrent._vvv0._v7));
 };
 //BA.debugLineNum = 69;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 557;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 558;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 560;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 315;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 311;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _botonaceptar_click() throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub BotonAceptar_Click";
 //BA.debugLineNum = 300;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv0._vv6==mostCurrent._vvv0._v7)) { 
 //BA.debugLineNum = 301;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
mostCurrent._vvv0._vv5 = (int) (mostCurrent._vvv0._vv5+1);
 //BA.debugLineNum = 302;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv0.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv0._v7),(Object)(mostCurrent._vvv0._vv5-1));
 }else {
 //BA.debugLineNum = 304;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv0.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv0._v7),(Object)(mostCurrent._vvv0._vv6));
 };
 //BA.debugLineNum = 306;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvv0.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 307;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv5.getObject()));
 //BA.debugLineNum = 308;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _botonanadiractividad_click() throws Exception{
 //BA.debugLineNum = 517;BA.debugLine="Sub BotonAnadirActividad_Click";
 //BA.debugLineNum = 518;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].Descripcion = mostCurrent._vvvvvvvv4;
 //BA.debugLineNum = 520;BA.debugLine="If (Starter.Secuencia(Starter.MaxSecuencias).num_";
if ((mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades>0)) { 
 //BA.debugLineNum = 521;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_inicio = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 522;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_inicio = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-1)].minuto_fin;
 }else {
 //BA.debugLineNum = 524;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_inicio = (int) (8);
 //BA.debugLineNum = 525;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_inicio = (int) (0);
 };
 //BA.debugLineNum = 528;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_fin = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_inicio;
 //BA.debugLineNum = 529;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_inicio+30);
 //BA.debugLineNum = 530;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin>59) { 
 //BA.debugLineNum = 531;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin-60);
 //BA.debugLineNum = 532;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_fin = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_fin+1);
 };
 //BA.debugLineNum = 535;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_fin*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin>(23*60+59)) { 
 //BA.debugLineNum = 536;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].hora_fin = (int) (23);
 //BA.debugLineNum = 537;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].minuto_fin = (int) (59);
 };
 //BA.debugLineNum = 540;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 541;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_acti";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades+1);
 //BA.debugLineNum = 542;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 //BA.debugLineNum = 543;BA.debugLine="End Sub";
return "";
}
public static String  _botoncancelar_click() throws Exception{
 //BA.debugLineNum = 288;BA.debugLine="Sub BotonCancelar_Click";
 //BA.debugLineNum = 289;BA.debugLine="SalidaConfigurarSecuencia";
_vvvvvvvv5();
 //BA.debugLineNum = 290;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_focuschanged(boolean _tienefoco) throws Exception{
 //BA.debugLineNum = 504;BA.debugLine="Sub ConfigDescripcion_FocusChanged (TieneFoco As B";
 //BA.debugLineNum = 505;BA.debugLine="If TieneFoco==True And ConfigDescripcion.Text==De";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True && (mostCurrent._vvvvvvvv6.getText()).equals(mostCurrent._vvvvvvvv2)) { 
 //BA.debugLineNum = 506;BA.debugLine="ConfigDescripcion.Text=\"\"";
mostCurrent._vvvvvvvv6.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 507;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].Descripcion = "";
 //BA.debugLineNum = 508;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 510;BA.debugLine="If TieneFoco==False And ConfigDescripcion.Text==\"";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.False && (mostCurrent._vvvvvvvv6.getText()).equals("")) { 
 //BA.debugLineNum = 511;BA.debugLine="ConfigDescripcion.Text=DescripcionSecuenciaPorDe";
mostCurrent._vvvvvvvv6.setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvv2));
 //BA.debugLineNum = 512;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].Descripcion = mostCurrent._vvvvvvvv2;
 //BA.debugLineNum = 513;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 500;BA.debugLine="Sub ConfigDescripcion_TextChanged (Old As String,";
 //BA.debugLineNum = 501;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descripc";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].Descripcion = _new;
 //BA.debugLineNum = 502;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_focuschanged(boolean _tienefoco) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 466;BA.debugLine="Sub ConfigDescripcionAct_FocusChanged (TieneFoco A";
 //BA.debugLineNum = 467;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 468;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 470;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 471;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 473;BA.debugLine="If TieneFoco==True Then";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 474;BA.debugLine="If ConfigDescripcionAct(Act).Text==DescripcionAc";
if ((mostCurrent._vvvvvvvv7[_act].getText()).equals(mostCurrent._vvvvvvvv4)) { 
 //BA.debugLineNum = 475;BA.debugLine="ConfigDescripcionAct(Act).Text=\"\"";
mostCurrent._vvvvvvvv7[_act].setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 476;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].Descripcion = "";
 //BA.debugLineNum = 477;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 }else {
 //BA.debugLineNum = 480;BA.debugLine="If ConfigDescripcionAct(Act).Text==\"\" Then";
if ((mostCurrent._vvvvvvvv7[_act].getText()).equals("")) { 
 //BA.debugLineNum = 481;BA.debugLine="ConfigDescripcionAct(Act).Text=DescripcionActiv";
mostCurrent._vvvvvvvv7[_act].setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvv4));
 //BA.debugLineNum = 482;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].Descripcion = mostCurrent._vvvvvvvv4;
 //BA.debugLineNum = 483;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 };
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_textchanged(String _old,String _new) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 489;BA.debugLine="Sub ConfigDescripcionAct_TextChanged (Old As Strin";
 //BA.debugLineNum = 490;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 491;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 493;BA.debugLine="If Inicializando==False Then";
if (_vvvvvvvv0==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 494;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 495;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 496;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].Descripcion = _new;
 };
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return "";
}
public static String  _confighorafinalact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 437;BA.debugLine="Sub ConfigHoraFinalAct_Click";
 //BA.debugLineNum = 438;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 439;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 440;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 441;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 443;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 444;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 446;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_fin);
 //BA.debugLineNum = 447;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_fin);
 //BA.debugLineNum = 448;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 449;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora fina";
_resultado = _dialogotiempo.Show("Indica la hora final de la actividad","Hora final","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 451;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 452;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute<St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()<mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_inicio*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_inicio) { 
 //BA.debugLineNum = 454;BA.debugLine="Msgbox(\"La hora de finalización de una activida";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La hora de finalización de una actividad no puede ser anterior a la de inicio."),BA.ObjectToCharSequence("Hora inválida"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 456;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_fin = _dialogotiempo.getHour();
 //BA.debugLineNum = 457;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_fin = _dialogotiempo.getMinute();
 //BA.debugLineNum = 458;BA.debugLine="If QuitarSolapes==True Then";
if (_vvvvvvvvv1()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 459;BA.debugLine="Msgbox(\"Se ha corregido la hora final de la ac";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha corregido la hora final de la actividad para evitar solapes."),BA.ObjectToCharSequence("Hora final corregida"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 461;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 };
 };
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _confighorainicioact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 359;BA.debugLine="Sub ConfigHoraInicioAct_Click";
 //BA.debugLineNum = 360;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 361;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 362;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 363;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 365;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 366;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 368;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_inicio);
 //BA.debugLineNum = 369;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_inicio);
 //BA.debugLineNum = 370;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 371;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora inic";
_resultado = _dialogotiempo.Show("Indica la hora inicial de la actividad","Hora inicial","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 373;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 374;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_inicio = _dialogotiempo.getHour();
 //BA.debugLineNum = 375;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_inicio = _dialogotiempo.getMinute();
 //BA.debugLineNum = 376;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute>St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()>mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_fin*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_fin) { 
 //BA.debugLineNum = 379;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_fin = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].hora_inicio;
 //BA.debugLineNum = 380;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_fin = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_act].minuto_inicio;
 };
 //BA.debugLineNum = 382;BA.debugLine="If OrdenarActividades==True Then";
if (_vvvvvvvvv2()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 383;BA.debugLine="Msgbox(\"Se ha colocado la actividad en su posic";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha colocado la actividad en su posición correcta."),BA.ObjectToCharSequence("Actividades reorganizadas"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 385;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 };
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _configindicarhora_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tiposindicador = null;
int _resultado = 0;
 //BA.debugLineNum = 268;BA.debugLine="Sub ConfigIndicarHora_Click";
 //BA.debugLineNum = 269;BA.debugLine="Dim TiposIndicador As List";
_tiposindicador = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 270;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 272;BA.debugLine="TiposIndicador.Initialize";
_tiposindicador.Initialize();
 //BA.debugLineNum = 273;BA.debugLine="TiposIndicador.AddAll(Starter.DescripcionMinutero";
_tiposindicador.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv0._vv2));
 //BA.debugLineNum = 275;BA.debugLine="resultado=InputList(TiposIndicador,\"Indicar hora";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tiposindicador,BA.ObjectToCharSequence("Indicar hora actual"),mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.indicar_hora,mostCurrent.activityBA);
 //BA.debugLineNum = 276;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 277;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.indicar_hora = _resultado;
 //BA.debugLineNum = 278;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 };
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _configopcionesact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _act = 0;
anywheresoftware.b4a.objects.collections.List _opciones = null;
int _resultado = 0;
int _nact = 0;
 //BA.debugLineNum = 337;BA.debugLine="Sub ConfigOpcionesAct_Click";
 //BA.debugLineNum = 338;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 339;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 340;BA.debugLine="Dim Opciones As List";
_opciones = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 341;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 343;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 344;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 346;BA.debugLine="Opciones.Initialize2(Array As String(\"Borrar acti";
_opciones.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Borrar actividad","CANCELAR"}));
 //BA.debugLineNum = 347;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_opciones,BA.ObjectToCharSequence("Acción"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 349;BA.debugLine="If resultado=0 Then";
if (_resultado==0) { 
 //BA.debugLineNum = 350;BA.debugLine="For nAct=Act To Starter.Secuencia(Starter.MaxSec";
{
final int step10 = 1;
final int limit10 = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-2);
_nact = _act ;
for (;_nact <= limit10 ;_nact = _nact + step10 ) {
 //BA.debugLineNum = 352;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_nact] = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_nact+1)];
 }
};
 //BA.debugLineNum = 354;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-1);
 };
 //BA.debugLineNum = 356;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _configpictograma_click() throws Exception{
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 319;BA.debugLine="Sub ConfigPictograma_Click";
 //BA.debugLineNum = 320;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 321;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 322;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 323;BA.debugLine="PictogramaEditado=-1";
_vvvvvvvvv3 = (int) (-1);
 //BA.debugLineNum = 324;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv3.getObject()));
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _configpictogramaact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 327;BA.debugLine="Sub ConfigPictogramaAct_Click";
 //BA.debugLineNum = 328;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 329;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 330;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 331;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 332;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 333;BA.debugLine="PictogramaEditado=BotonPulsado.Tag";
_vvvvvvvvv3 = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 334;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv3.getObject()));
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static String  _configtamicono_valuechanged(int _valor,boolean _cambio) throws Exception{
 //BA.debugLineNum = 282;BA.debugLine="Sub ConfigTamIcono_ValueChanged (Valor As Int, Cam";
 //BA.debugLineNum = 283;BA.debugLine="If (Cambio==True) Then";
if ((_cambio==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 284;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.tam_icono = _valor;
 };
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return "";
}
public static String  _configtipotablero_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tipostablero = null;
int _resultado = 0;
 //BA.debugLineNum = 254;BA.debugLine="Sub ConfigTipoTablero_Click";
 //BA.debugLineNum = 255;BA.debugLine="Dim TiposTablero As List";
_tipostablero = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 256;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 258;BA.debugLine="TiposTablero.Initialize";
_tipostablero.Initialize();
 //BA.debugLineNum = 259;BA.debugLine="TiposTablero.AddAll(Starter.DescripcionTablero)";
_tipostablero.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv0._vv1));
 //BA.debugLineNum = 261;BA.debugLine="resultado=InputList(TiposTablero,\"Tipo de tablero";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tipostablero,BA.ObjectToCharSequence("Tipo de tablero"),mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.tipo,mostCurrent.activityBA);
 //BA.debugLineNum = 262;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 263;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].tablero.tipo = _resultado;
 //BA.debugLineNum = 264;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 };
 //BA.debugLineNum = 266;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvv3() throws Exception{
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
 //BA.debugLineNum = 75;BA.debugLine="Dim Posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 77;BA.debugLine="If ParametrosSecuencia.IsInitialized==True Then";
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
 //BA.debugLineNum = 78;BA.debugLine="Posicion=ParametrosSecuencia.ScrollPosition";
_posicion = parent.mostCurrent._parametrossecuencia.getScrollPosition();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 80;BA.debugLine="Posicion=0";
_posicion = (int) (0);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 83;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 84;BA.debugLine="Activity.LoadLayout(\"ConfigurarSecuencia\")";
parent.mostCurrent._activity.LoadLayout("ConfigurarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 86;BA.debugLine="Inicializando=True 'Para evitar que se lancen pro";
parent._vvvvvvvv0 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 88;BA.debugLine="EtiquetaInicial.Initialize(\"EtiquetaInicial\")";
parent.mostCurrent._vvvvvvvvv4.Initialize(mostCurrent.activityBA,"EtiquetaInicial");
 //BA.debugLineNum = 89;BA.debugLine="EtiquetaInicial.Text=\"Crear nueva secuencia\"";
parent.mostCurrent._vvvvvvvvv4.setText(BA.ObjectToCharSequence("Crear nueva secuencia"));
 //BA.debugLineNum = 90;BA.debugLine="If (Starter.SecuenciaActiva<Starter.MaxSecuencias";
if (true) break;

case 7:
//if
this.state = 10;
if ((parent.mostCurrent._vvv0._vv6<parent.mostCurrent._vvv0._v7)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 91;BA.debugLine="EtiquetaInicial.Text=\"Editar secuencia\"";
parent.mostCurrent._vvvvvvvvv4.setText(BA.ObjectToCharSequence("Editar secuencia"));
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 93;BA.debugLine="EtiquetaInicial.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 94;BA.debugLine="EtiquetaInicial.TextSize=24";
parent.mostCurrent._vvvvvvvvv4.setTextSize((float) (24));
 //BA.debugLineNum = 95;BA.debugLine="EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvv4.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 96;BA.debugLine="EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 97;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiquetaInicial";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 99;BA.debugLine="ConfigPictograma.Initialize(\"ConfigPictograma\")";
parent.mostCurrent._vvvvvvvvv5.Initialize(mostCurrent.activityBA,"ConfigPictograma");
 //BA.debugLineNum = 100;BA.debugLine="ConfigPictograma.SetBackgroundImage(LoadBitmap(St";
parent.mostCurrent._vvvvvvvvv5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvv0._vvv4,parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].pictograma+".png").getObject()));
 //BA.debugLineNum = 101;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogram";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv5.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv6-parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvv4.getHeight()+parent._vvvvvvvvv7),parent._vvvvvvvvv6,parent._vvvvvvvvv6);
 //BA.debugLineNum = 103;BA.debugLine="ConfigDescripcion.Initialize(\"ConfigDescripcion\")";
parent.mostCurrent._vvvvvvvv6.Initialize(mostCurrent.activityBA,"ConfigDescripcion");
 //BA.debugLineNum = 104;BA.debugLine="ConfigDescripcion.Text=Starter.Secuencia(Starter.";
parent.mostCurrent._vvvvvvvv6.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].Descripcion));
 //BA.debugLineNum = 105;BA.debugLine="ConfigDescripcion.TextColor=Colors.White";
parent.mostCurrent._vvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 106;BA.debugLine="ConfigDescripcion.TextSize=16";
parent.mostCurrent._vvvvvvvv6.setTextSize((float) (16));
 //BA.debugLineNum = 107;BA.debugLine="ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvv6.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 108;BA.debugLine="ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 109;BA.debugLine="ConfigDescripcion.Color=Colors.DarkGray";
parent.mostCurrent._vvvvvvvv6.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 110;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripci";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv6.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvv4.getHeight()+parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvvv5.getLeft()-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 112;BA.debugLine="EtiqTipoTablero.Initialize(\"EtiqTipoTablero\")";
parent.mostCurrent._vvvvvvvvv0.Initialize(mostCurrent.activityBA,"EtiqTipoTablero");
 //BA.debugLineNum = 113;BA.debugLine="EtiqTipoTablero.Text=\"Tipo de tablero:\"";
parent.mostCurrent._vvvvvvvvv0.setText(BA.ObjectToCharSequence("Tipo de tablero:"));
 //BA.debugLineNum = 114;BA.debugLine="EtiqTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 115;BA.debugLine="EtiqTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvv0.setTextSize((float) (16));
 //BA.debugLineNum = 116;BA.debugLine="EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 117;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTipoTablero";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv0.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvv6.getTop()+parent.mostCurrent._vvvvvvvv6.getHeight()+parent._vvvvvvvvv7),parent._vvvvvvvvvv1,parent._vvvvvvvvv6);
 //BA.debugLineNum = 119;BA.debugLine="ConfigTipoTablero.Initialize(\"ConfigTipoTablero\")";
parent.mostCurrent._vvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ConfigTipoTablero");
 //BA.debugLineNum = 120;BA.debugLine="ConfigTipoTablero.Text=Starter.DescripcionTablero";
parent.mostCurrent._vvvvvvvvvv2.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv0._vv1[parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].tablero.tipo]));
 //BA.debugLineNum = 121;BA.debugLine="ConfigTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 122;BA.debugLine="ConfigTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvvv2.setTextSize((float) (16));
 //BA.debugLineNum = 123;BA.debugLine="ConfigTipoTablero.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv2.setColor(parent._vvvvvvvvvv3);
 //BA.debugLineNum = 124;BA.debugLine="ConfigTipoTablero.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 125;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTipoTable";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv2.getObject()),(int) (parent._vvvvvvvvvv1+parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvv6.getTop()+parent.mostCurrent._vvvvvvvv6.getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvv1-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 127;BA.debugLine="EtiqIndicarHora.Initialize(\"EtiqIndicarHora\")";
parent.mostCurrent._vvvvvvvvvv4.Initialize(mostCurrent.activityBA,"EtiqIndicarHora");
 //BA.debugLineNum = 128;BA.debugLine="EtiqIndicarHora.Text=\"Indicar hora actual:\"";
parent.mostCurrent._vvvvvvvvvv4.setText(BA.ObjectToCharSequence("Indicar hora actual:"));
 //BA.debugLineNum = 129;BA.debugLine="EtiqIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 130;BA.debugLine="EtiqIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvv4.setTextSize((float) (16));
 //BA.debugLineNum = 131;BA.debugLine="EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 132;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqIndicarHora";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv4.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvv2.getHeight()+parent._vvvvvvvvv7),parent._vvvvvvvvvv1,parent._vvvvvvvvv6);
 //BA.debugLineNum = 134;BA.debugLine="ConfigIndicarHora.Initialize(\"ConfigIndicarHora\")";
parent.mostCurrent._vvvvvvvvvv5.Initialize(mostCurrent.activityBA,"ConfigIndicarHora");
 //BA.debugLineNum = 135;BA.debugLine="ConfigIndicarHora.Text=Starter.DescripcionMinuter";
parent.mostCurrent._vvvvvvvvvv5.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv0._vv2[parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].tablero.indicar_hora]));
 //BA.debugLineNum = 136;BA.debugLine="ConfigIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 137;BA.debugLine="ConfigIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 138;BA.debugLine="ConfigIndicarHora.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv5.setColor(parent._vvvvvvvvvv3);
 //BA.debugLineNum = 139;BA.debugLine="ConfigIndicarHora.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 140;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigIndicarHo";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv5.getObject()),(int) (parent._vvvvvvvvvv1+parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvv2.getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvv1-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 142;BA.debugLine="EtiqTamIcono.Initialize(\"EtiqTamIcono\")";
parent.mostCurrent._vvvvvvvvvv6.Initialize(mostCurrent.activityBA,"EtiqTamIcono");
 //BA.debugLineNum = 143;BA.debugLine="EtiqTamIcono.Text=\"Tamaño de los iconos:\"";
parent.mostCurrent._vvvvvvvvvv6.setText(BA.ObjectToCharSequence("Tamaño de los iconos:"));
 //BA.debugLineNum = 144;BA.debugLine="EtiqTamIcono.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 145;BA.debugLine="EtiqTamIcono.TextSize=16";
parent.mostCurrent._vvvvvvvvvv6.setTextSize((float) (16));
 //BA.debugLineNum = 146;BA.debugLine="EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 147;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTamIcono,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv6.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvvvv5.getHeight()+parent._vvvvvvvvv7),parent._vvvvvvvvvv1,parent._vvvvvvvvv6);
 //BA.debugLineNum = 149;BA.debugLine="ConfigTamIcono.Initialize(\"ConfigTamIcono\")";
parent.mostCurrent._vvvvvvvvvv7.Initialize(mostCurrent.activityBA,"ConfigTamIcono");
 //BA.debugLineNum = 150;BA.debugLine="ConfigTamIcono.Value=Starter.Secuencia(Starter.Ma";
parent.mostCurrent._vvvvvvvvvv7.setValue(parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].tablero.tam_icono);
 //BA.debugLineNum = 151;BA.debugLine="ConfigTamIcono.Max=30";
parent.mostCurrent._vvvvvvvvvv7.setMax((int) (30));
 //BA.debugLineNum = 152;BA.debugLine="ConfigTamIcono.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvv7.setColor(parent._vvvvvvvvvv3);
 //BA.debugLineNum = 153;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTamIcono,";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv7.getObject()),(int) (parent._vvvvvvvvvv1+parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvvvv5.getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvv1-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 155;BA.debugLine="EtiqActividades.Initialize(\"EtiqActividades\")";
parent.mostCurrent._vvvvvvvvvv0.Initialize(mostCurrent.activityBA,"EtiqActividades");
 //BA.debugLineNum = 156;BA.debugLine="EtiqActividades.Text=\"Actividades\"";
parent.mostCurrent._vvvvvvvvvv0.setText(BA.ObjectToCharSequence("Actividades"));
 //BA.debugLineNum = 157;BA.debugLine="EtiqActividades.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 158;BA.debugLine="EtiqActividades.TextSize=24";
parent.mostCurrent._vvvvvvvvvv0.setTextSize((float) (24));
 //BA.debugLineNum = 159;BA.debugLine="EtiqActividades.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvvv0.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 160;BA.debugLine="EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 161;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqActividades";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv0.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvvv7.getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 163;BA.debugLine="Dim InicioVertical As Int";
_iniciovertical = 0;
 //BA.debugLineNum = 164;BA.debugLine="Dim FinVertical As Int";
_finvertical = 0;
 //BA.debugLineNum = 166;BA.debugLine="FinVertical=EtiqActividades.Top+EtiqActividades.H";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvv0.getTop()+parent.mostCurrent._vvvvvvvvvv0.getHeight());
 //BA.debugLineNum = 167;BA.debugLine="InicioVertical=FinVertical+SeparacionCasillas";
_iniciovertical = (int) (_finvertical+parent._vvvvvvvvv7);
 //BA.debugLineNum = 169;BA.debugLine="For Act=0 To Starter.Secuencia(Starter.MaxSecuenc";
if (true) break;

case 11:
//for
this.state = 18;
step79 = 1;
limit79 = (int) (parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].num_actividades-1);
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
 //BA.debugLineNum = 171;BA.debugLine="If (Act>0) Then";
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
 //BA.debugLineNum = 172;BA.debugLine="InicioVertical=ConfigHoraInicioAct(Act-1).Top+C";
_iniciovertical = (int) (parent.mostCurrent._vvvvvvvvvvv1[(int) (_act-1)].getTop()+parent.mostCurrent._vvvvvvvvvvv1[(int) (_act-1)].getHeight()+4*parent._vvvvvvvvv7);
 if (true) break;

case 17:
//C
this.state = 28;
;
 //BA.debugLineNum = 175;BA.debugLine="ConfigPictogramaAct(Act).Initialize(\"ConfigPicto";
parent.mostCurrent._vvvvvvvvvvv2[_act].Initialize(mostCurrent.activityBA,"ConfigPictogramaAct");
 //BA.debugLineNum = 176;BA.debugLine="ConfigPictogramaAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv2[_act].setTag((Object)(_act));
 //BA.debugLineNum = 177;BA.debugLine="ConfigPictogramaAct(Act).SetBackgroundImage(Load";
parent.mostCurrent._vvvvvvvvvvv2[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvv0._vvv4,parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].Pictograma+".png").getObject()));
 //BA.debugLineNum = 178;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogra";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv2[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv6-parent._vvvvvvvvv7),_iniciovertical,parent._vvvvvvvvv6,parent._vvvvvvvvv6);
 //BA.debugLineNum = 180;BA.debugLine="ConfigDescripcionAct(Act).Initialize(\"ConfigDesc";
parent.mostCurrent._vvvvvvvv7[_act].Initialize(mostCurrent.activityBA,"ConfigDescripcionAct");
 //BA.debugLineNum = 181;BA.debugLine="ConfigDescripcionAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvv7[_act].setTag((Object)(_act));
 //BA.debugLineNum = 182;BA.debugLine="ConfigDescripcionAct(Act).Text=Starter.Actividad";
parent.mostCurrent._vvvvvvvv7[_act].setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].Descripcion));
 //BA.debugLineNum = 183;BA.debugLine="ConfigDescripcionAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv7[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 184;BA.debugLine="ConfigDescripcionAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvv7[_act].setTextSize((float) (16));
 //BA.debugLineNum = 185;BA.debugLine="ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity";
parent.mostCurrent._vvvvvvvv7[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 186;BA.debugLine="ConfigDescripcionAct(Act).Color=Starter.Colores(";
parent.mostCurrent._vvvvvvvv7[_act].setColor(parent.mostCurrent._vvv0._vv4[_act]);
 //BA.debugLineNum = 187;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripc";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv7[_act].getObject()),parent._vvvvvvvvv7,_iniciovertical,(int) (parent.mostCurrent._vvvvvvvvvvv2[_act].getLeft()-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 189;BA.debugLine="ConfigHoraInicioAct(Act).Initialize(\"ConfigHoraI";
parent.mostCurrent._vvvvvvvvvvv1[_act].Initialize(mostCurrent.activityBA,"ConfigHoraInicioAct");
 //BA.debugLineNum = 190;BA.debugLine="ConfigHoraInicioAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv1[_act].setTag((Object)(_act));
 //BA.debugLineNum = 191;BA.debugLine="ConfigHoraInicioAct(Act).Text=\"Desde\"&CRLF&Numbe";
parent.mostCurrent._vvvvvvvvvvv1[_act].setText(BA.ObjectToCharSequence("Desde"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].hora_inicio,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].minuto_inicio,(int) (2),(int) (0))));
 //BA.debugLineNum = 192;BA.debugLine="ConfigHoraInicioAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv1[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 193;BA.debugLine="ConfigHoraInicioAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvv1[_act].setTextSize((float) (16));
 //BA.debugLineNum = 194;BA.debugLine="ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.";
parent.mostCurrent._vvvvvvvvvvv1[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 195;BA.debugLine="ConfigHoraInicioAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvv1[_act].setColor(parent._vvvvvvvvvv3);
 //BA.debugLineNum = 196;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraInic";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv1[_act].getObject()),(int) (3*parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvv7[_act].getTop()+parent.mostCurrent._vvvvvvvv7[_act].getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvv7-parent._vvvvvvvvv6/(double)2),parent._vvvvvvvvv6);
 //BA.debugLineNum = 198;BA.debugLine="ConfigHoraFinalAct(Act).Initialize(\"ConfigHoraFi";
parent.mostCurrent._vvvvvvvvvvv3[_act].Initialize(mostCurrent.activityBA,"ConfigHoraFinalAct");
 //BA.debugLineNum = 199;BA.debugLine="ConfigHoraFinalAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv3[_act].setTag((Object)(_act));
 //BA.debugLineNum = 200;BA.debugLine="ConfigHoraFinalAct(Act).Text=\"Hasta\"&CRLF&Number";
parent.mostCurrent._vvvvvvvvvvv3[_act].setText(BA.ObjectToCharSequence("Hasta"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].hora_fin,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv0._vv0[parent.mostCurrent._vvv0._v7][_act].minuto_fin,(int) (2),(int) (0))));
 //BA.debugLineNum = 201;BA.debugLine="ConfigHoraFinalAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv3[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 202;BA.debugLine="ConfigHoraFinalAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvv3[_act].setTextSize((float) (16));
 //BA.debugLineNum = 203;BA.debugLine="ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.C";
parent.mostCurrent._vvvvvvvvvvv3[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 204;BA.debugLine="ConfigHoraFinalAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvv3[_act].setColor(parent._vvvvvvvvvv3);
 //BA.debugLineNum = 205;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraFina";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv3[_act].getObject()),(int) (parent.mostCurrent._vvvvvvvvvvv1[_act].getLeft()+parent.mostCurrent._vvvvvvvvvvv1[_act].getWidth()+parent._vvvvvvvvv7),(int) (parent.mostCurrent._vvvvvvvv7[_act].getTop()+parent.mostCurrent._vvvvvvvv7[_act].getHeight()+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvv7-parent._vvvvvvvvv6/(double)2),parent._vvvvvvvvv6);
 //BA.debugLineNum = 207;BA.debugLine="ConfigOpcionesAct(Act).Initialize(\"ConfigOpcione";
parent.mostCurrent._vvvvvvvvvvv4[_act].Initialize(mostCurrent.activityBA,"ConfigOpcionesAct");
 //BA.debugLineNum = 208;BA.debugLine="ConfigOpcionesAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvv4[_act].setTag((Object)(_act));
 //BA.debugLineNum = 209;BA.debugLine="ConfigOpcionesAct(Act).SetBackgroundImage(LoadBi";
parent.mostCurrent._vvvvvvvvvvv4[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 210;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigOpciones";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv4[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvv6-parent._vvvvvvvvv7),parent.mostCurrent._vvvvvvvvvvv3[_act].getTop(),parent._vvvvvvvvv6,parent._vvvvvvvvv6);
 //BA.debugLineNum = 212;BA.debugLine="FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHo";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvvv3[_act].getTop()+parent.mostCurrent._vvvvvvvvvvv3[_act].getHeight());
 if (true) break;
if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 216;BA.debugLine="BotonAnadirActividad.Initialize(\"BotonAnadirActiv";
parent.mostCurrent._vvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"BotonAnadirActividad");
 //BA.debugLineNum = 217;BA.debugLine="BotonAnadirActividad.Text=\"Añadir Actividad\"";
parent.mostCurrent._vvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Añadir Actividad"));
 //BA.debugLineNum = 218;BA.debugLine="BotonAnadirActividad.TextSize=16";
parent.mostCurrent._vvvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 219;BA.debugLine="BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTE";
parent.mostCurrent._vvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 220;BA.debugLine="BotonAnadirActividad.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 221;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 19:
//if
this.state = 22;
if (parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].num_actividades==parent.mostCurrent._vvv0._v0) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 222;BA.debugLine="BotonAnadirActividad.Enabled=False";
parent.mostCurrent._vvvvvvvvvvv5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 22:
//C
this.state = 23;
;
 //BA.debugLineNum = 224;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAnadirActi";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv5.getObject()),parent._vvvvvvvvv7,(int) (_finvertical+parent._vvvvvvvvv7),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 226;BA.debugLine="BotonAceptar.Initialize(\"BotonAceptar\")";
parent.mostCurrent._vvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"BotonAceptar");
 //BA.debugLineNum = 227;BA.debugLine="BotonAceptar.Text=\"Aceptar\"";
parent.mostCurrent._vvvvvvvvvvv6.setText(BA.ObjectToCharSequence("Aceptar"));
 //BA.debugLineNum = 228;BA.debugLine="BotonAceptar.TextSize=16";
parent.mostCurrent._vvvvvvvvvvv6.setTextSize((float) (16));
 //BA.debugLineNum = 229;BA.debugLine="BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTIC";
parent.mostCurrent._vvvvvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 230;BA.debugLine="BotonAceptar.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 231;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAceptar,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvv6.getObject()),parent._vvvvvvvvv7,(int) (parent.mostCurrent._vvvvvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvvvvv5.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 233;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 23:
//if
this.state = 26;
if (parent.mostCurrent._vvv0._vv7[parent.mostCurrent._vvv0._v7].num_actividades==0) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 234;BA.debugLine="BotonAceptar.Enabled=False";
parent.mostCurrent._vvvvvvvvvvv6.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 26:
//C
this.state = -1;
;
 //BA.debugLineNum = 237;BA.debugLine="BotonCancelar.Initialize(\"BotonCancelar\")";
parent.mostCurrent._botoncancelar.Initialize(mostCurrent.activityBA,"BotonCancelar");
 //BA.debugLineNum = 238;BA.debugLine="BotonCancelar.Text=\"Cancelar\"";
parent.mostCurrent._botoncancelar.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 239;BA.debugLine="BotonCancelar.TextSize=16";
parent.mostCurrent._botoncancelar.setTextSize((float) (16));
 //BA.debugLineNum = 240;BA.debugLine="BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTI";
parent.mostCurrent._botoncancelar.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 241;BA.debugLine="BotonCancelar.TextColor=Colors.Black";
parent.mostCurrent._botoncancelar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 242;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonCancelar,5";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._botoncancelar.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+parent._vvvvvvvvv7),parent.mostCurrent._vvvvvvvvvvv6.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvv7),parent._vvvvvvvvv6);
 //BA.debugLineNum = 244;BA.debugLine="ParametrosSecuencia.Panel.Height=BotonCancelar.To";
parent.mostCurrent._parametrossecuencia.getPanel().setHeight((int) (parent.mostCurrent._botoncancelar.getTop()+parent.mostCurrent._botoncancelar.getHeight()+parent._vvvvvvvvv7));
 //BA.debugLineNum = 246;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 247;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 29;
return;
case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 248;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 250;BA.debugLine="Inicializando=False";
parent._vvvvvvvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim SeparacionHorizontal=25%X As Int  'Separación";
_vvvvvvvvvv1 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA);
 //BA.debugLineNum = 11;BA.debugLine="Dim TamCasilla=60dip As Int 'Tamaño vertical de l";
_vvvvvvvvv6 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 12;BA.debugLine="Dim SeparacionCasillas=5dip As Int 'Separación ve";
_vvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 14;BA.debugLine="Dim ColorDeFondo=0xFFF0FFFF As Int";
_vvvvvvvvvv3 = (int) (0xfff0ffff);
 //BA.debugLineNum = 16;BA.debugLine="Private ParametrosSecuencia As ScrollView";
mostCurrent._parametrossecuencia = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim EtiquetaInicial As Label";
mostCurrent._vvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim ConfigDescripcion As EditText";
mostCurrent._vvvvvvvv6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim ConfigPictograma As Label";
mostCurrent._vvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim EtiqTipoTablero As Label";
mostCurrent._vvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ConfigTipoTablero As Label";
mostCurrent._vvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim EtiqIndicarHora As Label";
mostCurrent._vvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim ConfigIndicarHora As Label";
mostCurrent._vvvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim EtiqTamIcono As Label";
mostCurrent._vvvvvvvvvv6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ConfigTamIcono As SeekBar";
mostCurrent._vvvvvvvvvv7 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim EtiqActividades As Label";
mostCurrent._vvvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ConfigDescripcionAct(Starter.MaxActividades)";
mostCurrent._vvvvvvvv7 = new anywheresoftware.b4a.objects.EditTextWrapper[mostCurrent._vvv0._v0];
{
int d0 = mostCurrent._vvvvvvvv7.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvv7[i0] = new anywheresoftware.b4a.objects.EditTextWrapper();
}
}
;
 //BA.debugLineNum = 31;BA.debugLine="Dim ConfigHoraInicioAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv0._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 32;BA.debugLine="Dim ConfigHoraFinalAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv0._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 33;BA.debugLine="Dim ConfigPictogramaAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv0._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv2.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv2[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 34;BA.debugLine="Dim ConfigOpcionesAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv0._v0];
{
int d0 = mostCurrent._vvvvvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv4[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 36;BA.debugLine="Dim BotonAnadirActividad As Button";
mostCurrent._vvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim BotonAceptar As Button";
mostCurrent._vvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim BotonCancelar As Button";
mostCurrent._botoncancelar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim Inicializando As Boolean";
_vvvvvvvv0 = false;
 //BA.debugLineNum = 42;BA.debugLine="Dim PictogramaEditado As Int";
_vvvvvvvvv3 = 0;
 //BA.debugLineNum = 46;BA.debugLine="Dim DescripcionSecuenciaPorDefecto=\"Pulsa aquí pa";
mostCurrent._vvvvvvvv2 = "Pulsa aquí para poner un nombre de secuencia";
 //BA.debugLineNum = 47;BA.debugLine="Dim DescripcionActividadPorDefecto=\"Nombre de la";
mostCurrent._vvvvvvvv4 = "Nombre de la nueva actividad";
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvv2() throws Exception{
int _i = 0;
int _j = 0;
int _hm_1 = 0;
int _hm_2 = 0;
javi.prieto.pictorario.starter._actividad _actint = null;
boolean _intercambiorealizado = false;
 //BA.debugLineNum = 389;BA.debugLine="Sub OrdenarActividades As Boolean";
 //BA.debugLineNum = 390;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 391;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 392;BA.debugLine="Dim ActInt As Actividad";
_actint = new javi.prieto.pictorario.starter._actividad();
 //BA.debugLineNum = 393;BA.debugLine="Dim IntercambioRealizado As Boolean";
_intercambiorealizado = false;
 //BA.debugLineNum = 395;BA.debugLine="IntercambioRealizado=False";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 398;BA.debugLine="For i=1 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-1);
_i = (int) (1) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 399;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuenci";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit7 ;_j = _j + step7 ) {
 //BA.debugLineNum = 400;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_1 = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].hora_inicio*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].minuto_inicio);
 //BA.debugLineNum = 401;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_2 = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 402;BA.debugLine="If hm_1>hm_2 Then";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 403;BA.debugLine="ActInt=Starter.ActividadSecuencia(Starter.MaxS";
_actint = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j];
 //BA.debugLineNum = 404;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j] = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)];
 //BA.debugLineNum = 405;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)] = _actint;
 //BA.debugLineNum = 406;BA.debugLine="IntercambioRealizado=True";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 //BA.debugLineNum = 412;BA.debugLine="QuitarSolapes";
_vvvvvvvvv1();
 //BA.debugLineNum = 414;BA.debugLine="Return IntercambioRealizado";
if (true) return _intercambiorealizado;
 //BA.debugLineNum = 415;BA.debugLine="End Sub";
return false;
}
public static String  _pictogramaelegido(int _id) throws Exception{
 //BA.debugLineNum = 545;BA.debugLine="Sub PictogramaElegido(Id As Int)";
 //BA.debugLineNum = 546;BA.debugLine="If Id<>-1 Then 'Si no se ha pulsado en \"Cancelar\"";
if (_id!=-1) { 
 //BA.debugLineNum = 547;BA.debugLine="If PictogramaEditado==-1 Then 'Pictograma de la";
if (_vvvvvvvvv3==-1) { 
 //BA.debugLineNum = 548;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictog";
mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].pictograma = BA.NumberToString(_id);
 }else {
 //BA.debugLineNum = 550;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_vvvvvvvvv3].Pictograma = BA.NumberToString(_id);
 };
 //BA.debugLineNum = 552;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvv3();
 };
 //BA.debugLineNum = 554;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvv1() throws Exception{
int _hm_1 = 0;
int _hm_2 = 0;
int _j = 0;
boolean _resultado = false;
 //BA.debugLineNum = 417;BA.debugLine="Sub QuitarSolapes As Boolean";
 //BA.debugLineNum = 418;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 419;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 420;BA.debugLine="Dim resultado As Boolean";
_resultado = false;
 //BA.debugLineNum = 422;BA.debugLine="resultado=False";
_resultado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 424;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvv0._vv7[mostCurrent._vvv0._v7].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit5 ;_j = _j + step5 ) {
 //BA.debugLineNum = 425;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_1 = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].hora_fin*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].minuto_fin);
 //BA.debugLineNum = 426;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_2 = (int) (mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 427;BA.debugLine="If hm_1>hm_2 Then 'Si es así, las iguala";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 428;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].hora_fin = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].hora_inicio;
 //BA.debugLineNum = 429;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][_j].minuto_fin = mostCurrent._vvv0._vv0[mostCurrent._vvv0._v7][(int) (_j+1)].minuto_inicio;
 //BA.debugLineNum = 430;BA.debugLine="resultado=True";
_resultado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 434;BA.debugLine="Return resultado";
if (true) return _resultado;
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvv5() throws Exception{
 //BA.debugLineNum = 292;BA.debugLine="Sub SalidaConfigurarSecuencia";
 //BA.debugLineNum = 293;BA.debugLine="If Msgbox2(\"Se perderán todos los cambios realiza";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se perderán todos los cambios realizados."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"¿Está seguro de que desea salir sin guardarlos?"),BA.ObjectToCharSequence("Cancelar cambios"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 294;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvv5.getObject()));
 //BA.debugLineNum = 295;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
}
