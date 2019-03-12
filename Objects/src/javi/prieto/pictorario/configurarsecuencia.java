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
public static int _vvvvvvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvvv4 = 0;
public static int _vvvvvvvvvvvvvv0 = 0;
public anywheresoftware.b4a.objects.ScrollViewWrapper _parametrossecuencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _vvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _vvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.EditTextWrapper[] _vvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncancelar = null;
public static boolean _vvvvvvvvvvvvv4 = false;
public static int _vvvvvvvvvvvvv0 = 0;
public static String _vvvvvvvvvvvv1 = "";
public static String _vvvvvvvvvvvv3 = "";
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv5 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv6 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv7 = null;
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
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).Initiali";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].Initialize();
 //BA.debugLineNum = 61;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero.";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.Initialize();
 //BA.debugLineNum = 63;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvvvvvvvvv3._vvv1==mostCurrent._vvvvvvvvvv3._vv2)) { 
 //BA.debugLineNum = 64;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades = (int) (0);
 //BA.debugLineNum = 65;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].Descripcion = mostCurrent._vvvvvvvvvvvv1;
 //BA.debugLineNum = 66;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].pictograma = BA.NumberToString(7229);
 //BA.debugLineNum = 67;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.tipo = (int) (3);
 //BA.debugLineNum = 68;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.tam_icono = (int) (0);
 //BA.debugLineNum = 69;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.indicar_hora = (int) (1);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Secu";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvvvvvvvvv3._vvv1),(Object)(mostCurrent._vvvvvvvvvv3._vv2));
 };
 //BA.debugLineNum = 74;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 672;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 673;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 675;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 399;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 395;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return "";
}
public static String  _botonaceptar_click() throws Exception{
 //BA.debugLineNum = 383;BA.debugLine="Sub BotonAceptar_Click";
 //BA.debugLineNum = 384;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvvvvvvvvv3._vvv1==mostCurrent._vvvvvvvvvv3._vv2)) { 
 //BA.debugLineNum = 385;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
mostCurrent._vvvvvvvvvv3._vv0 = (int) (mostCurrent._vvvvvvvvvv3._vv0+1);
 //BA.debugLineNum = 386;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvvvvvvvvv3._vv2),(Object)(mostCurrent._vvvvvvvvvv3._vv0-1));
 }else {
 //BA.debugLineNum = 388;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvvvvvvvvv3._vv2),(Object)(mostCurrent._vvvvvvvvvv3._vvv1));
 };
 //BA.debugLineNum = 390;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 391;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv3.getObject()));
 //BA.debugLineNum = 392;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _botonanadiractividad_click() throws Exception{
int _sumahoras = 0;
 //BA.debugLineNum = 640;BA.debugLine="Sub BotonAnadirActividad_Click";
 //BA.debugLineNum = 641;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].Descripcion = mostCurrent._vvvvvvvvvvvv3;
 //BA.debugLineNum = 643;BA.debugLine="If (Starter.Secuencia(Starter.MaxSecuencias).num_";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades>0)) { 
 //BA.debugLineNum = 644;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].hora_inicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 645;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].minuto_inicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1)].minuto_fin;
 }else {
 //BA.debugLineNum = 647;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].hora_inicio = (int) (8);
 //BA.debugLineNum = 648;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].minuto_inicio = (int) (0);
 };
 //BA.debugLineNum = 651;BA.debugLine="Dim SumaHoras = SumarHoras(Starter.ActividadSecue";
_sumahoras = _vvvvvvvvvvvv4(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].minuto_inicio,(int) (0),(int) (30));
 //BA.debugLineNum = 652;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].hora_fin = _vvvvvvvvvvvv5(_sumahoras);
 //BA.debugLineNum = 653;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].minuto_fin = _vvvvvvvvvvvv6(_sumahoras);
 //BA.debugLineNum = 655;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades].Pictograma = BA.NumberToString(9813);
 //BA.debugLineNum = 656;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_acti";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades+1);
 //BA.debugLineNum = 657;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 //BA.debugLineNum = 658;BA.debugLine="End Sub";
return "";
}
public static String  _botoncancelar_click() throws Exception{
 //BA.debugLineNum = 372;BA.debugLine="Sub BotonCancelar_Click";
 //BA.debugLineNum = 373;BA.debugLine="SalidaConfigurarSecuencia";
_vvvvvvvvvvvv7();
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvv0(int _hora1,int _minuto1,int _hora2,int _minuto2) throws Exception{
int _hm1 = 0;
int _hm2 = 0;
 //BA.debugLineNum = 110;BA.debugLine="Sub ComparaHoras(Hora1 As Int, Minuto1 As Int,Hora";
 //BA.debugLineNum = 114;BA.debugLine="Dim HM1=MinutosDia(Hora1,Minuto1) As Int";
_hm1 = _vvvvvvvvvvvvv1(_hora1,_minuto1);
 //BA.debugLineNum = 115;BA.debugLine="Dim HM2=MinutosDia(Hora2,Minuto2) As Int";
_hm2 = _vvvvvvvvvvvvv1(_hora2,_minuto2);
 //BA.debugLineNum = 116;BA.debugLine="If HM1==HM2 Then";
if (_hm1==_hm2) { 
 //BA.debugLineNum = 117;BA.debugLine="Return 0";
if (true) return (int) (0);
 }else if(_hm1<_hm2) { 
 //BA.debugLineNum = 119;BA.debugLine="Return -1";
if (true) return (int) (-1);
 }else {
 //BA.debugLineNum = 121;BA.debugLine="Return 1";
if (true) return (int) (1);
 };
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return 0;
}
public static String  _configdescripcion_focuschanged(boolean _tienefoco) throws Exception{
 //BA.debugLineNum = 627;BA.debugLine="Sub ConfigDescripcion_FocusChanged (TieneFoco As B";
 //BA.debugLineNum = 628;BA.debugLine="If TieneFoco==True And ConfigDescripcion.Text==De";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True && (mostCurrent._vvvvvvvvvvvvv2.getText()).equals(mostCurrent._vvvvvvvvvvvv1)) { 
 //BA.debugLineNum = 629;BA.debugLine="ConfigDescripcion.Text=\"\"";
mostCurrent._vvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 630;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].Descripcion = "";
 //BA.debugLineNum = 631;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 633;BA.debugLine="If TieneFoco==False And ConfigDescripcion.Text==\"";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.False && (mostCurrent._vvvvvvvvvvvvv2.getText()).equals("")) { 
 //BA.debugLineNum = 634;BA.debugLine="ConfigDescripcion.Text=DescripcionSecuenciaPorDe";
mostCurrent._vvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvvvvvv1));
 //BA.debugLineNum = 635;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].Descripcion = mostCurrent._vvvvvvvvvvvv1;
 //BA.debugLineNum = 636;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 638;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 623;BA.debugLine="Sub ConfigDescripcion_TextChanged (Old As String,";
 //BA.debugLineNum = 624;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descripc";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].Descripcion = _new;
 //BA.debugLineNum = 625;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_focuschanged(boolean _tienefoco) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 589;BA.debugLine="Sub ConfigDescripcionAct_FocusChanged (TieneFoco A";
 //BA.debugLineNum = 590;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 591;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 593;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 594;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 596;BA.debugLine="If TieneFoco==True Then";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 597;BA.debugLine="If ConfigDescripcionAct(Act).Text==DescripcionAc";
if ((mostCurrent._vvvvvvvvvvvvv3[_act].getText()).equals(mostCurrent._vvvvvvvvvvvv3)) { 
 //BA.debugLineNum = 598;BA.debugLine="ConfigDescripcionAct(Act).Text=\"\"";
mostCurrent._vvvvvvvvvvvvv3[_act].setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 599;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].Descripcion = "";
 //BA.debugLineNum = 600;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 }else {
 //BA.debugLineNum = 603;BA.debugLine="If ConfigDescripcionAct(Act).Text==\"\" Then";
if ((mostCurrent._vvvvvvvvvvvvv3[_act].getText()).equals("")) { 
 //BA.debugLineNum = 604;BA.debugLine="ConfigDescripcionAct(Act).Text=DescripcionActiv";
mostCurrent._vvvvvvvvvvvvv3[_act].setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvvvvvv3));
 //BA.debugLineNum = 605;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].Descripcion = mostCurrent._vvvvvvvvvvvv3;
 //BA.debugLineNum = 606;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 };
 //BA.debugLineNum = 609;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_textchanged(String _old,String _new) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 612;BA.debugLine="Sub ConfigDescripcionAct_TextChanged (Old As Strin";
 //BA.debugLineNum = 613;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 614;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 616;BA.debugLine="If Inicializando==False Then";
if (_vvvvvvvvvvvvv4==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 617;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 618;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 619;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].Descripcion = _new;
 };
 //BA.debugLineNum = 621;BA.debugLine="End Sub";
return "";
}
public static String  _confighorafinalact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 558;BA.debugLine="Sub ConfigHoraFinalAct_Click";
 //BA.debugLineNum = 559;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 560;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 561;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 562;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 564;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 565;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 567;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_fin);
 //BA.debugLineNum = 568;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_fin);
 //BA.debugLineNum = 569;BA.debugLine="DialogoTiempo.Is24Hours=Starter.Formato24h";
_dialogotiempo.setIs24Hours(mostCurrent._vvvvvvvvvv3._vvv0);
 //BA.debugLineNum = 570;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora fina";
_resultado = _dialogotiempo.Show("Indica la hora final de la actividad","Hora final","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 572;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 573;BA.debugLine="If ComparaHoras (DialogoTiempo.Hour,DialogoTiemp";
if (_vvvvvvvvvvvv0(_dialogotiempo.getHour(),_dialogotiempo.getMinute(),mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_inicio)<0) { 
 //BA.debugLineNum = 576;BA.debugLine="ToastMessageShow(\"La hora de finalización de un";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("La hora de finalización de una actividad no puede ser anterior a la de inicio."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 578;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_fin = _dialogotiempo.getHour();
 //BA.debugLineNum = 579;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_fin = _dialogotiempo.getMinute();
 //BA.debugLineNum = 580;BA.debugLine="If QuitarSolapes==True Then";
if (_vvvvvvvvvvvvv5()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 582;BA.debugLine="ToastMessageShow(\"Se ha corregido la hora fina";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se ha corregido la hora final de la actividad para evitar solapes."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 584;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 };
 };
 //BA.debugLineNum = 587;BA.debugLine="End Sub";
return "";
}
public static String  _confighorainicioact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
int _sumahora = 0;
 //BA.debugLineNum = 480;BA.debugLine="Sub ConfigHoraInicioAct_Click";
 //BA.debugLineNum = 481;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 482;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 483;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 484;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 486;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 487;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 489;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio);
 //BA.debugLineNum = 490;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_inicio);
 //BA.debugLineNum = 491;BA.debugLine="DialogoTiempo.Is24Hours=Starter.Formato24h";
_dialogotiempo.setIs24Hours(mostCurrent._vvvvvvvvvv3._vvv0);
 //BA.debugLineNum = 492;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora inic";
_resultado = _dialogotiempo.Show("Indica la hora inicial de la actividad","Hora inicial","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 494;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 495;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio = _dialogotiempo.getHour();
 //BA.debugLineNum = 496;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_inicio = _dialogotiempo.getMinute();
 //BA.debugLineNum = 497;BA.debugLine="If ComparaHoras(DialogoTiempo.Hour,DialogoTiempo";
if (_vvvvvvvvvvvv0(_dialogotiempo.getHour(),_dialogotiempo.getMinute(),mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_fin,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio)>0) { 
 //BA.debugLineNum = 500;BA.debugLine="Dim SumaHora = SumarHoras(Starter.ActividadSecu";
_sumahora = _vvvvvvvvvvvv4(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_inicio,(int) (0),(int) (30));
 //BA.debugLineNum = 501;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].hora_fin = _vvvvvvvvvvvv5(_sumahora);
 //BA.debugLineNum = 502;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_fin = _vvvvvvvvvvvv6(_sumahora);
 };
 //BA.debugLineNum = 506;BA.debugLine="If OrdenarActividades==True Then";
if (_vvvvvvvvvvvvv6()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 508;BA.debugLine="ToastMessageShow(\"Se ha colocado la actividad e";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se ha colocado la actividad en su posición correcta."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 510;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 };
 //BA.debugLineNum = 512;BA.debugLine="End Sub";
return "";
}
public static String  _configindicarhora_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tiposindicador = null;
int _resultado = 0;
 //BA.debugLineNum = 352;BA.debugLine="Sub ConfigIndicarHora_Click";
 //BA.debugLineNum = 353;BA.debugLine="Dim TiposIndicador As List";
_tiposindicador = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 354;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 356;BA.debugLine="TiposIndicador.Initialize";
_tiposindicador.Initialize();
 //BA.debugLineNum = 357;BA.debugLine="TiposIndicador.AddAll(Starter.DescripcionMinutero";
_tiposindicador.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvvvvvvvvv3._vv5));
 //BA.debugLineNum = 359;BA.debugLine="resultado=InputList(TiposIndicador,\"Indicar hora";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tiposindicador,BA.ObjectToCharSequence("Indicar hora actual"),mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.indicar_hora,mostCurrent.activityBA);
 //BA.debugLineNum = 360;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 361;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.indicar_hora = _resultado;
 //BA.debugLineNum = 362;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 };
 //BA.debugLineNum = 364;BA.debugLine="End Sub";
return "";
}
public static String  _confignotificaciones_click() throws Exception{
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _casilla = null;
 //BA.debugLineNum = 343;BA.debugLine="Sub ConfigNotificaciones_Click";
 //BA.debugLineNum = 344;BA.debugLine="Dim Casilla As CheckBox";
_casilla = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 345;BA.debugLine="Casilla=Sender";
_casilla.setObject((android.widget.CheckBox)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 346;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).notifica";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].notificaciones = _casilla.getChecked();
 //BA.debugLineNum = 347;BA.debugLine="If Casilla.Checked==True And Starter.AlarmasActiv";
if (_casilla.getChecked()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._vvvvvvvvvv3._vvv6==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 348;BA.debugLine="ToastMessageShow(\"Para que se lance la notificac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para que se lance la notificación a la hora indicada es necesario activar las alarmas en la configuración."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _configopcionesact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _act = 0;
anywheresoftware.b4a.objects.collections.List _opciones = null;
int _resultado = 0;
int _nact = 0;
 //BA.debugLineNum = 421;BA.debugLine="Sub ConfigOpcionesAct_Click";
 //BA.debugLineNum = 422;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 423;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 424;BA.debugLine="Dim Opciones As List";
_opciones = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 425;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 427;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 428;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 430;BA.debugLine="Opciones.Initialize2(Array As String(\"Borrar acti";
_opciones.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Borrar actividad","CANCELAR"}));
 //BA.debugLineNum = 431;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_opciones,BA.ObjectToCharSequence("Acción"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 433;BA.debugLine="If resultado=0 Then";
if (_resultado==0) { 
 //BA.debugLineNum = 434;BA.debugLine="For nAct=Act To Starter.Secuencia(Starter.MaxSec";
{
final int step10 = 1;
final int limit10 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1);
_nact = _act ;
for (;_nact <= limit10 ;_nact = _nact + step10 ) {
 //BA.debugLineNum = 437;BA.debugLine="CopiarActividad(Starter.MaxSecuencias,nAct+1,St";
_vvvvvvvvvvvvv7(mostCurrent._vvvvvvvvvv3._vv2,(int) (_nact+1),mostCurrent._vvvvvvvvvv3._vv2,_nact);
 }
};
 //BA.debugLineNum = 439;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1);
 };
 //BA.debugLineNum = 441;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 //BA.debugLineNum = 442;BA.debugLine="End Sub";
return "";
}
public static String  _configpictograma_click() throws Exception{
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 403;BA.debugLine="Sub ConfigPictograma_Click";
 //BA.debugLineNum = 404;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 405;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 406;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 407;BA.debugLine="PictogramaEditado=-1";
_vvvvvvvvvvvvv0 = (int) (-1);
 //BA.debugLineNum = 408;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv5.getObject()));
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
return "";
}
public static String  _configpictogramaact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
anywheresoftware.b4a.objects.IME _im = null;
 //BA.debugLineNum = 411;BA.debugLine="Sub ConfigPictogramaAct_Click";
 //BA.debugLineNum = 412;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 413;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 414;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 415;BA.debugLine="im.HideKeyboard";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 416;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 417;BA.debugLine="PictogramaEditado=BotonPulsado.Tag";
_vvvvvvvvvvvvv0 = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 418;BA.debugLine="StartActivity(SeleccionPictogramas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv5.getObject()));
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public static String  _configtamicono_valuechanged(int _valor,boolean _cambio) throws Exception{
 //BA.debugLineNum = 366;BA.debugLine="Sub ConfigTamIcono_ValueChanged (Valor As Int, Cam";
 //BA.debugLineNum = 367;BA.debugLine="If (Cambio==True) Then";
if ((_cambio==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 368;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.tam_icono = _valor;
 };
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _configtipotablero_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tipostablero = null;
int _resultado = 0;
 //BA.debugLineNum = 329;BA.debugLine="Sub ConfigTipoTablero_Click";
 //BA.debugLineNum = 330;BA.debugLine="Dim TiposTablero As List";
_tipostablero = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 331;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 333;BA.debugLine="TiposTablero.Initialize";
_tipostablero.Initialize();
 //BA.debugLineNum = 334;BA.debugLine="TiposTablero.AddAll(Starter.DescripcionTablero)";
_tipostablero.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvvvvvvvvv3._vv4));
 //BA.debugLineNum = 336;BA.debugLine="resultado=InputList(TiposTablero,\"Tipo de tablero";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tipostablero,BA.ObjectToCharSequence("Tipo de tablero"),mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.tipo,mostCurrent.activityBA);
 //BA.debugLineNum = 337;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 338;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].tablero.tipo = _resultado;
 //BA.debugLineNum = 339;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 };
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv7(int _seq1,int _act1,int _seq2,int _act2) throws Exception{
 //BA.debugLineNum = 444;BA.debugLine="Sub CopiarActividad(Seq1 As Int, Act1 As Int, Seq2";
 //BA.debugLineNum = 446;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).Descripcion";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Descripcion = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Descripcion;
 //BA.debugLineNum = 447;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).hora_fin=St";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_fin;
 //BA.debugLineNum = 448;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).hora_inicio";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_inicio;
 //BA.debugLineNum = 449;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).minuto_fin=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_fin;
 //BA.debugLineNum = 450;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).minuto_inic";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_inicio;
 //BA.debugLineNum = 451;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).Pictograma=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Pictograma = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Pictograma;
 //BA.debugLineNum = 452;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvvvvvv2() throws Exception{
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
int step89;
int limit89;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 137;BA.debugLine="Dim Posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 139;BA.debugLine="If ParametrosSecuencia.IsInitialized==True Then";
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
 //BA.debugLineNum = 140;BA.debugLine="Posicion=ParametrosSecuencia.ScrollPosition";
_posicion = parent.mostCurrent._parametrossecuencia.getScrollPosition();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 142;BA.debugLine="Posicion=0";
_posicion = (int) (0);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 145;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 146;BA.debugLine="Activity.LoadLayout(\"ConfigurarSecuencia\")";
parent.mostCurrent._activity.LoadLayout("ConfigurarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 148;BA.debugLine="Inicializando=True 'Para evitar que se lancen pro";
parent._vvvvvvvvvvvvv4 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 150;BA.debugLine="EtiquetaInicial.Initialize(\"EtiquetaInicial\")";
parent.mostCurrent._vvvvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"EtiquetaInicial");
 //BA.debugLineNum = 151;BA.debugLine="EtiquetaInicial.Text=\"Crear nueva secuencia\"";
parent.mostCurrent._vvvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("Crear nueva secuencia"));
 //BA.debugLineNum = 152;BA.debugLine="If (Starter.SecuenciaActiva<Starter.MaxSecuencias";
if (true) break;

case 7:
//if
this.state = 10;
if ((parent.mostCurrent._vvvvvvvvvv3._vvv1<parent.mostCurrent._vvvvvvvvvv3._vv2)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 153;BA.debugLine="EtiquetaInicial.Text=\"Editar secuencia\"";
parent.mostCurrent._vvvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("Editar secuencia"));
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 155;BA.debugLine="EtiquetaInicial.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvv1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 156;BA.debugLine="EtiquetaInicial.TextSize=24";
parent.mostCurrent._vvvvvvvvvvvvvv1.setTextSize((float) (24));
 //BA.debugLineNum = 157;BA.debugLine="EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvvvvvvv1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 158;BA.debugLine="EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 159;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiquetaInicial";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvv1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 161;BA.debugLine="ConfigPictograma.Initialize(\"ConfigPictograma\")";
parent.mostCurrent._vvvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ConfigPictograma");
 //BA.debugLineNum = 162;BA.debugLine="ConfigPictograma.SetBackgroundImage(LoadBitmap(St";
parent.mostCurrent._vvvvvvvvvvvvvv2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvvvvvvvvv3._vvvv5,parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].pictograma+".png").getObject()));
 //BA.debugLineNum = 163;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogram";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvv2.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv3-parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvvvvvvv1.getHeight()+parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 165;BA.debugLine="ConfigDescripcion.Initialize(\"ConfigDescripcion\")";
parent.mostCurrent._vvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ConfigDescripcion");
 //BA.debugLineNum = 166;BA.debugLine="ConfigDescripcion.Text=Starter.Secuencia(Starter.";
parent.mostCurrent._vvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].Descripcion));
 //BA.debugLineNum = 167;BA.debugLine="ConfigDescripcion.TextColor=Colors.White";
parent.mostCurrent._vvvvvvvvvvvvv2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 168;BA.debugLine="ConfigDescripcion.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvv2.setTextSize((float) (16));
 //BA.debugLineNum = 169;BA.debugLine="ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvvvvvv2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 170;BA.debugLine="ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 171;BA.debugLine="ConfigDescripcion.Color=Colors.DarkGray";
parent.mostCurrent._vvvvvvvvvvvvv2.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 172;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripci";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvv2.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvvvvvvv1.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvvv2.getLeft()-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 174;BA.debugLine="EtiqTipoTablero.Initialize(\"EtiqTipoTablero\")";
parent.mostCurrent._vvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"EtiqTipoTablero");
 //BA.debugLineNum = 175;BA.debugLine="EtiqTipoTablero.Text=\"Tipo de tablero:\"";
parent.mostCurrent._vvvvvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Tipo de tablero:"));
 //BA.debugLineNum = 176;BA.debugLine="EtiqTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 177;BA.debugLine="EtiqTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 178;BA.debugLine="EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 179;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTipoTablero";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvv5.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvvvvv2.getHeight()+parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv6,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 181;BA.debugLine="ConfigTipoTablero.Initialize(\"ConfigTipoTablero\")";
parent.mostCurrent._vvvvvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"ConfigTipoTablero");
 //BA.debugLineNum = 182;BA.debugLine="ConfigTipoTablero.Text=Starter.DescripcionTablero";
parent.mostCurrent._vvvvvvvvvvvvvv7.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvvvvvvvvv3._vv4[parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].tablero.tipo]));
 //BA.debugLineNum = 183;BA.debugLine="ConfigTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 184;BA.debugLine="ConfigTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvv7.setTextSize((float) (16));
 //BA.debugLineNum = 185;BA.debugLine="ConfigTipoTablero.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvv7.setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 186;BA.debugLine="ConfigTipoTablero.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 187;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTipoTable";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvv7.getObject()),(int) (parent._vvvvvvvvvvvvvv6+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvvvvv2.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv6-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 189;BA.debugLine="EtiqIndicarHora.Initialize(\"EtiqIndicarHora\")";
parent.mostCurrent._vvvvvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"EtiqIndicarHora");
 //BA.debugLineNum = 190;BA.debugLine="EtiqIndicarHora.Text=\"Indicar hora actual:\"";
parent.mostCurrent._vvvvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("Indicar hora actual:"));
 //BA.debugLineNum = 191;BA.debugLine="EtiqIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 192;BA.debugLine="EtiqIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvv1.setTextSize((float) (16));
 //BA.debugLineNum = 193;BA.debugLine="EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 194;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqIndicarHora";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv1.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvvvvvvv7.getHeight()+parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv6,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 196;BA.debugLine="ConfigIndicarHora.Initialize(\"ConfigIndicarHora\")";
parent.mostCurrent._vvvvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ConfigIndicarHora");
 //BA.debugLineNum = 197;BA.debugLine="ConfigIndicarHora.Text=Starter.DescripcionMinuter";
parent.mostCurrent._vvvvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvvvvvvvvv3._vv5[parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].tablero.indicar_hora]));
 //BA.debugLineNum = 198;BA.debugLine="ConfigIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 199;BA.debugLine="ConfigIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvv2.setTextSize((float) (16));
 //BA.debugLineNum = 200;BA.debugLine="ConfigIndicarHora.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvvv2.setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 201;BA.debugLine="ConfigIndicarHora.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 202;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigIndicarHo";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv2.getObject()),(int) (parent._vvvvvvvvvvvvvv6+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvvvvvvv7.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv6-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 204;BA.debugLine="EtiqTamIcono.Initialize(\"EtiqTamIcono\")";
parent.mostCurrent._vvvvvvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"EtiqTamIcono");
 //BA.debugLineNum = 205;BA.debugLine="EtiqTamIcono.Text=\"Tamaño de los iconos:\"";
parent.mostCurrent._vvvvvvvvvvvvvvv3.setText(BA.ObjectToCharSequence("Tamaño de los iconos:"));
 //BA.debugLineNum = 206;BA.debugLine="EtiqTamIcono.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 207;BA.debugLine="EtiqTamIcono.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvv3.setTextSize((float) (16));
 //BA.debugLineNum = 208;BA.debugLine="EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvvvvvvvvv3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 209;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTamIcono,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv3.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv2.getHeight()+parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv6,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 211;BA.debugLine="ConfigTamIcono.Initialize(\"ConfigTamIcono\")";
parent.mostCurrent._vvvvvvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"ConfigTamIcono");
 //BA.debugLineNum = 212;BA.debugLine="ConfigTamIcono.Value=Starter.Secuencia(Starter.Ma";
parent.mostCurrent._vvvvvvvvvvvvvvv4.setValue(parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].tablero.tam_icono);
 //BA.debugLineNum = 213;BA.debugLine="ConfigTamIcono.Max=30";
parent.mostCurrent._vvvvvvvvvvvvvvv4.setMax((int) (30));
 //BA.debugLineNum = 214;BA.debugLine="ConfigTamIcono.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvvv4.setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 215;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTamIcono,";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv4.getObject()),(int) (parent._vvvvvvvvvvvvvv6+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv2.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv6-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 217;BA.debugLine="EtiqNotificaciones.Initialize(\"EtiqNotificaciones";
parent.mostCurrent._vvvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"EtiqNotificaciones");
 //BA.debugLineNum = 218;BA.debugLine="EtiqNotificaciones.Text=\"Activar alarmas:\"";
parent.mostCurrent._vvvvvvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Activar alarmas:"));
 //BA.debugLineNum = 219;BA.debugLine="EtiqNotificaciones.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 220;BA.debugLine="EtiqNotificaciones.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 221;BA.debugLine="EtiqNotificaciones.Gravity=Gravity.CENTER_VERTICA";
parent.mostCurrent._vvvvvvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 222;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqNotificacio";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv5.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv4.getHeight()+parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv6,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 224;BA.debugLine="ConfigNotificaciones.Initialize(\"ConfigNotificaci";
parent.mostCurrent._vvvvvvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"ConfigNotificaciones");
 //BA.debugLineNum = 225;BA.debugLine="ConfigNotificaciones.Checked=Starter.Secuencia(St";
parent.mostCurrent._vvvvvvvvvvvvvvv6.setChecked(parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].notificaciones);
 //BA.debugLineNum = 226;BA.debugLine="ConfigNotificaciones.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvvv6.setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 227;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigNotificac";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv6.getObject()),(int) (parent._vvvvvvvvvvvvvv6+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv4.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv6-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 229;BA.debugLine="EtiqActividades.Initialize(\"EtiqActividades\")";
parent.mostCurrent._vvvvvvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"EtiqActividades");
 //BA.debugLineNum = 230;BA.debugLine="EtiqActividades.Text=\"Actividades\"";
parent.mostCurrent._vvvvvvvvvvvvvvv7.setText(BA.ObjectToCharSequence("Actividades"));
 //BA.debugLineNum = 231;BA.debugLine="EtiqActividades.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 232;BA.debugLine="EtiqActividades.TextSize=24";
parent.mostCurrent._vvvvvvvvvvvvvvv7.setTextSize((float) (24));
 //BA.debugLineNum = 233;BA.debugLine="EtiqActividades.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvvvvvvvv7.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 234;BA.debugLine="EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 235;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqActividades";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv7.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvvv6.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv6.getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 237;BA.debugLine="Dim InicioVertical As Int";
_iniciovertical = 0;
 //BA.debugLineNum = 238;BA.debugLine="Dim FinVertical As Int";
_finvertical = 0;
 //BA.debugLineNum = 240;BA.debugLine="FinVertical=EtiqActividades.Top+EtiqActividades.H";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv7.getHeight());
 //BA.debugLineNum = 241;BA.debugLine="InicioVertical=FinVertical+SeparacionCasillas";
_iniciovertical = (int) (_finvertical+parent._vvvvvvvvvvvvvv4);
 //BA.debugLineNum = 243;BA.debugLine="For Act=0 To Starter.Secuencia(Starter.MaxSecuenc";
if (true) break;

case 11:
//for
this.state = 18;
step89 = 1;
limit89 = (int) (parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1);
_act = (int) (0) ;
this.state = 27;
if (true) break;

case 27:
//C
this.state = 18;
if ((step89 > 0 && _act <= limit89) || (step89 < 0 && _act >= limit89)) this.state = 13;
if (true) break;

case 28:
//C
this.state = 27;
_act = ((int)(0 + _act + step89)) ;
if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 245;BA.debugLine="If (Act>0) Then";
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
 //BA.debugLineNum = 246;BA.debugLine="InicioVertical=ConfigHoraInicioAct(Act-1).Top+C";
_iniciovertical = (int) (parent.mostCurrent._vvvvvvvvvvvvvvv0[(int) (_act-1)].getTop()+parent.mostCurrent._vvvvvvvvvvvvvvv0[(int) (_act-1)].getHeight()+4*parent._vvvvvvvvvvvvvv4);
 if (true) break;

case 17:
//C
this.state = 28;
;
 //BA.debugLineNum = 249;BA.debugLine="ConfigPictogramaAct(Act).Initialize(\"ConfigPicto";
parent.mostCurrent._vvvvvvvvvvvvvvvv1[_act].Initialize(mostCurrent.activityBA,"ConfigPictogramaAct");
 //BA.debugLineNum = 250;BA.debugLine="ConfigPictogramaAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvvvvvvv1[_act].setTag((Object)(_act));
 //BA.debugLineNum = 251;BA.debugLine="ConfigPictogramaAct(Act).SetBackgroundImage(Load";
parent.mostCurrent._vvvvvvvvvvvvvvvv1[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(parent.mostCurrent._vvvvvvvvvv3._vvvv5,parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].Pictograma+".png").getObject()));
 //BA.debugLineNum = 252;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogra";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvvv1[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv3-parent._vvvvvvvvvvvvvv4),_iniciovertical,parent._vvvvvvvvvvvvvv3,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 254;BA.debugLine="ConfigDescripcionAct(Act).Initialize(\"ConfigDesc";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].Initialize(mostCurrent.activityBA,"ConfigDescripcionAct");
 //BA.debugLineNum = 255;BA.debugLine="ConfigDescripcionAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setTag((Object)(_act));
 //BA.debugLineNum = 256;BA.debugLine="ConfigDescripcionAct(Act).Text=Starter.Actividad";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setText(BA.ObjectToCharSequence(parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].Descripcion));
 //BA.debugLineNum = 257;BA.debugLine="ConfigDescripcionAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 258;BA.debugLine="ConfigDescripcionAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setTextSize((float) (16));
 //BA.debugLineNum = 259;BA.debugLine="ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 260;BA.debugLine="ConfigDescripcionAct(Act).Color=Starter.Colores(";
parent.mostCurrent._vvvvvvvvvvvvv3[_act].setColor(parent.mostCurrent._vvvvvvvvvv3._vv7[_act]);
 //BA.debugLineNum = 261;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripc";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvv3[_act].getObject()),parent._vvvvvvvvvvvvvv4,_iniciovertical,(int) (parent.mostCurrent._vvvvvvvvvvvvvvvv1[_act].getLeft()-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 263;BA.debugLine="ConfigHoraInicioAct(Act).Initialize(\"ConfigHoraI";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].Initialize(mostCurrent.activityBA,"ConfigHoraInicioAct");
 //BA.debugLineNum = 264;BA.debugLine="ConfigHoraInicioAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setTag((Object)(_act));
 //BA.debugLineNum = 265;BA.debugLine="ConfigHoraInicioAct(Act).Text=\"Desde\"&CRLF&Escri";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setText(BA.ObjectToCharSequence("Desde"+anywheresoftware.b4a.keywords.Common.CRLF+_vvvvvvvvvvvvvvvv2(parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].hora_inicio,parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_inicio)));
 //BA.debugLineNum = 267;BA.debugLine="ConfigHoraInicioAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 268;BA.debugLine="ConfigHoraInicioAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setTextSize((float) (16));
 //BA.debugLineNum = 269;BA.debugLine="ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 270;BA.debugLine="ConfigHoraInicioAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 271;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraInic";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].getObject()),(int) (3*parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvv3[_act].getTop()+parent.mostCurrent._vvvvvvvvvvvvv3[_act].getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvvvvvvv4-parent._vvvvvvvvvvvvvv3/(double)2),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 273;BA.debugLine="ConfigHoraFinalAct(Act).Initialize(\"ConfigHoraFi";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].Initialize(mostCurrent.activityBA,"ConfigHoraFinalAct");
 //BA.debugLineNum = 274;BA.debugLine="ConfigHoraFinalAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setTag((Object)(_act));
 //BA.debugLineNum = 275;BA.debugLine="ConfigHoraFinalAct(Act).Text=\"Desde\"&CRLF&Escrib";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setText(BA.ObjectToCharSequence("Desde"+anywheresoftware.b4a.keywords.Common.CRLF+_vvvvvvvvvvvvvvvv2(parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].hora_fin,parent.mostCurrent._vvvvvvvvvv3._vvv3[parent.mostCurrent._vvvvvvvvvv3._vv2][_act].minuto_fin)));
 //BA.debugLineNum = 276;BA.debugLine="ConfigHoraFinalAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 277;BA.debugLine="ConfigHoraFinalAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setTextSize((float) (16));
 //BA.debugLineNum = 278;BA.debugLine="ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.C";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 279;BA.debugLine="ConfigHoraFinalAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].setColor(parent._vvvvvvvvvvvvvv0);
 //BA.debugLineNum = 280;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraFina";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].getObject()),(int) (parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].getLeft()+parent.mostCurrent._vvvvvvvvvvvvvvv0[_act].getWidth()+parent._vvvvvvvvvvvvvv4),(int) (parent.mostCurrent._vvvvvvvvvvvvv3[_act].getTop()+parent.mostCurrent._vvvvvvvvvvvvv3[_act].getHeight()+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvvvvvvvv4-parent._vvvvvvvvvvvvvv3/(double)2),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 282;BA.debugLine="ConfigOpcionesAct(Act).Initialize(\"ConfigOpcione";
parent.mostCurrent._vvvvvvvvvvvvvvvv4[_act].Initialize(mostCurrent.activityBA,"ConfigOpcionesAct");
 //BA.debugLineNum = 283;BA.debugLine="ConfigOpcionesAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvvvvvvvvv4[_act].setTag((Object)(_act));
 //BA.debugLineNum = 284;BA.debugLine="ConfigOpcionesAct(Act).SetBackgroundImage(LoadBi";
parent.mostCurrent._vvvvvvvvvvvvvvvv4[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 285;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigOpciones";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvvv4[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvvvvvvvv3-parent._vvvvvvvvvvvvvv4),parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].getTop(),parent._vvvvvvvvvvvvvv3,parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 287;BA.debugLine="FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHo";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].getTop()+parent.mostCurrent._vvvvvvvvvvvvvvvv3[_act].getHeight());
 if (true) break;
if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 291;BA.debugLine="BotonAnadirActividad.Initialize(\"BotonAnadirActiv";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"BotonAnadirActividad");
 //BA.debugLineNum = 292;BA.debugLine="BotonAnadirActividad.Text=\"Añadir Actividad\"";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Añadir Actividad"));
 //BA.debugLineNum = 293;BA.debugLine="BotonAnadirActividad.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.setTextSize((float) (16));
 //BA.debugLineNum = 294;BA.debugLine="BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTE";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 295;BA.debugLine="BotonAnadirActividad.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 296;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 19:
//if
this.state = 22;
if (parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].num_actividades==parent.mostCurrent._vvvvvvvvvv3._vv3) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 297;BA.debugLine="BotonAnadirActividad.Enabled=False";
parent.mostCurrent._vvvvvvvvvvvvvvvv5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 22:
//C
this.state = 23;
;
 //BA.debugLineNum = 299;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAnadirActi";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvvv5.getObject()),parent._vvvvvvvvvvvvvv4,(int) (_finvertical+parent._vvvvvvvvvvvvvv4),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 301;BA.debugLine="BotonAceptar.Initialize(\"BotonAceptar\")";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"BotonAceptar");
 //BA.debugLineNum = 302;BA.debugLine="BotonAceptar.Text=\"Aceptar\"";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.setText(BA.ObjectToCharSequence("Aceptar"));
 //BA.debugLineNum = 303;BA.debugLine="BotonAceptar.TextSize=16";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.setTextSize((float) (16));
 //BA.debugLineNum = 304;BA.debugLine="BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTIC";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 305;BA.debugLine="BotonAceptar.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 306;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAceptar,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvvvvvvvv6.getObject()),parent._vvvvvvvvvvvvvv4,(int) (parent.mostCurrent._vvvvvvvvvvvvvvvv5.getTop()+parent.mostCurrent._vvvvvvvvvvvvvvvv5.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 308;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 23:
//if
this.state = 26;
if (parent.mostCurrent._vvvvvvvvvv3._vvv2[parent.mostCurrent._vvvvvvvvvv3._vv2].num_actividades==0) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 309;BA.debugLine="BotonAceptar.Enabled=False";
parent.mostCurrent._vvvvvvvvvvvvvvvv6.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 26:
//C
this.state = -1;
;
 //BA.debugLineNum = 312;BA.debugLine="BotonCancelar.Initialize(\"BotonCancelar\")";
parent.mostCurrent._botoncancelar.Initialize(mostCurrent.activityBA,"BotonCancelar");
 //BA.debugLineNum = 313;BA.debugLine="BotonCancelar.Text=\"Cancelar\"";
parent.mostCurrent._botoncancelar.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 314;BA.debugLine="BotonCancelar.TextSize=16";
parent.mostCurrent._botoncancelar.setTextSize((float) (16));
 //BA.debugLineNum = 315;BA.debugLine="BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTI";
parent.mostCurrent._botoncancelar.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 316;BA.debugLine="BotonCancelar.TextColor=Colors.Black";
parent.mostCurrent._botoncancelar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 317;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonCancelar,5";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._botoncancelar.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+parent._vvvvvvvvvvvvvv4),parent.mostCurrent._vvvvvvvvvvvvvvvv6.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvvvvvvvv4),parent._vvvvvvvvvvvvvv3);
 //BA.debugLineNum = 319;BA.debugLine="ParametrosSecuencia.Panel.Height=BotonCancelar.To";
parent.mostCurrent._parametrossecuencia.getPanel().setHeight((int) (parent.mostCurrent._botoncancelar.getTop()+parent.mostCurrent._botoncancelar.getHeight()+parent._vvvvvvvvvvvvvv4));
 //BA.debugLineNum = 321;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 322;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 29;
return;
case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 323;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 325;BA.debugLine="Inicializando=False";
parent._vvvvvvvvvvvvv4 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _vvvvvvvvvvvvvvvv2(int _hora,int _minutos) throws Exception{
String _salida = "";
int _horamodificada = 0;
 //BA.debugLineNum = 78;BA.debugLine="Sub EscribirHora(Hora As Int, Minutos As Int) As S";
 //BA.debugLineNum = 79;BA.debugLine="Dim Salida As String";
_salida = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim HoraModificada As Int";
_horamodificada = 0;
 //BA.debugLineNum = 81;BA.debugLine="If (Starter.Formato24h==False And Hora>11) Then";
if ((mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.False && _hora>11)) { 
 //BA.debugLineNum = 82;BA.debugLine="HoraModificada=Hora-12";
_horamodificada = (int) (_hora-12);
 }else {
 //BA.debugLineNum = 84;BA.debugLine="HoraModificada=Hora";
_horamodificada = _hora;
 };
 //BA.debugLineNum = 86;BA.debugLine="Salida=NumberFormat(HoraModificada,2,0)&\":\"&Numbe";
_salida = anywheresoftware.b4a.keywords.Common.NumberFormat(_horamodificada,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutos,(int) (2),(int) (0));
 //BA.debugLineNum = 87;BA.debugLine="If Starter.Formato24h==False Then";
if (mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 88;BA.debugLine="If Hora<12 Then";
if (_hora<12) { 
 //BA.debugLineNum = 89;BA.debugLine="Salida=Salida&\" a.m.\"";
_salida = _salida+" a.m.";
 }else {
 //BA.debugLineNum = 91;BA.debugLine="Salida=Salida&\" p.m.\"";
_salida = _salida+" p.m.";
 };
 };
 //BA.debugLineNum = 94;BA.debugLine="Return Salida";
if (true) return _salida;
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim SeparacionHorizontal=25%X As Int  'Separación";
_vvvvvvvvvvvvvv6 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA);
 //BA.debugLineNum = 14;BA.debugLine="Dim TamCasilla=60dip As Int 'Tamaño vertical de l";
_vvvvvvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 15;BA.debugLine="Dim SeparacionCasillas=5dip As Int 'Separación ve";
_vvvvvvvvvvvvvv4 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 17;BA.debugLine="Dim ColorDeFondo=0xFFF0FFFF As Int";
_vvvvvvvvvvvvvv0 = (int) (0xfff0ffff);
 //BA.debugLineNum = 19;BA.debugLine="Private ParametrosSecuencia As ScrollView";
mostCurrent._parametrossecuencia = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim EtiquetaInicial As Label";
mostCurrent._vvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ConfigDescripcion As EditText";
mostCurrent._vvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim ConfigPictograma As Label";
mostCurrent._vvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim EtiqTipoTablero As Label";
mostCurrent._vvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim ConfigTipoTablero As Label";
mostCurrent._vvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim EtiqIndicarHora As Label";
mostCurrent._vvvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim ConfigIndicarHora As Label";
mostCurrent._vvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim EtiqNotificaciones As Label";
mostCurrent._vvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim ConfigNotificaciones As CheckBox";
mostCurrent._vvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim EtiqTamIcono As Label";
mostCurrent._vvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim ConfigTamIcono As SeekBar";
mostCurrent._vvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim EtiqActividades As Label";
mostCurrent._vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim ConfigDescripcionAct(Starter.MaxActividades)";
mostCurrent._vvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.EditTextWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.EditTextWrapper();
}
}
;
 //BA.debugLineNum = 36;BA.debugLine="Dim ConfigHoraInicioAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvv0.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvv0[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 37;BA.debugLine="Dim ConfigHoraFinalAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 38;BA.debugLine="Dim ConfigPictogramaAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 39;BA.debugLine="Dim ConfigOpcionesAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvv4[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 41;BA.debugLine="Dim BotonAnadirActividad As Button";
mostCurrent._vvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim BotonAceptar As Button";
mostCurrent._vvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim BotonCancelar As Button";
mostCurrent._botoncancelar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim Inicializando As Boolean";
_vvvvvvvvvvvvv4 = false;
 //BA.debugLineNum = 47;BA.debugLine="Dim PictogramaEditado As Int";
_vvvvvvvvvvvvv0 = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim DescripcionSecuenciaPorDefecto=\"Pulsa aquí pa";
mostCurrent._vvvvvvvvvvvv1 = "Pulsa aquí para poner un nombre de secuencia";
 //BA.debugLineNum = 52;BA.debugLine="Dim DescripcionActividadPorDefecto=\"Nombre de la";
mostCurrent._vvvvvvvvvvvv3 = "Nombre de la nueva actividad";
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvv5(int _minutos) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub HoraDesdeMinutosDia(Minutos As Int) As Int";
 //BA.debugLineNum = 103;BA.debugLine="Return Minutos/60";
if (true) return (int) (_minutos/(double)60);
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return 0;
}
public static String  _vvvvvvvvvvvvvvvv7(int _seq1,int _act1,int _seq2,int _act2) throws Exception{
int _hora_inicio = 0;
int _minuto_inicio = 0;
int _hora_fin = 0;
int _minuto_fin = 0;
String _descripcion = "";
String _pictograma = "";
 //BA.debugLineNum = 454;BA.debugLine="Sub IntercambiarActividades(Seq1 As Int,Act1 As In";
 //BA.debugLineNum = 455;BA.debugLine="Dim hora_inicio,minuto_inicio,hora_fin,minuto_fin";
_hora_inicio = 0;
_minuto_inicio = 0;
_hora_fin = 0;
_minuto_fin = 0;
 //BA.debugLineNum = 456;BA.debugLine="Dim Descripcion,Pictograma As String";
_descripcion = "";
_pictograma = "";
 //BA.debugLineNum = 458;BA.debugLine="Descripcion=Starter.ActividadSecuencia(Seq1,Act1)";
_descripcion = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Descripcion;
 //BA.debugLineNum = 459;BA.debugLine="hora_fin=Starter.ActividadSecuencia(Seq1,Act1).ho";
_hora_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_fin;
 //BA.debugLineNum = 460;BA.debugLine="hora_inicio=Starter.ActividadSecuencia(Seq1,Act1)";
_hora_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_inicio;
 //BA.debugLineNum = 461;BA.debugLine="minuto_fin=Starter.ActividadSecuencia(Seq1,Act1).";
_minuto_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_fin;
 //BA.debugLineNum = 462;BA.debugLine="minuto_inicio=Starter.ActividadSecuencia(Seq1,Act";
_minuto_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_inicio;
 //BA.debugLineNum = 463;BA.debugLine="Pictograma=Starter.ActividadSecuencia(Seq1,Act1).";
_pictograma = mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Pictograma;
 //BA.debugLineNum = 465;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).Descripcion";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Descripcion = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Descripcion;
 //BA.debugLineNum = 466;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).hora_fin=St";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_fin;
 //BA.debugLineNum = 467;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).hora_inicio";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].hora_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_inicio;
 //BA.debugLineNum = 468;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).minuto_fin=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_fin = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_fin;
 //BA.debugLineNum = 469;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).minuto_inic";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].minuto_inicio = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_inicio;
 //BA.debugLineNum = 470;BA.debugLine="Starter.ActividadSecuencia(Seq1,Act1).Pictograma=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq1][_act1].Pictograma = mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Pictograma;
 //BA.debugLineNum = 472;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).Descripcion";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Descripcion = _descripcion;
 //BA.debugLineNum = 473;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).hora_fin=ho";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_fin = _hora_fin;
 //BA.debugLineNum = 474;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).hora_inicio";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].hora_inicio = _hora_inicio;
 //BA.debugLineNum = 475;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).minuto_fin=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_fin = _minuto_fin;
 //BA.debugLineNum = 476;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).minuto_inic";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].minuto_inicio = _minuto_inicio;
 //BA.debugLineNum = 477;BA.debugLine="Starter.ActividadSecuencia(Seq2,Act2).Pictograma=";
mostCurrent._vvvvvvvvvv3._vvv3[_seq2][_act2].Pictograma = _pictograma;
 //BA.debugLineNum = 478;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvv6(int _minutos) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub MinutosDesdeMinutosdia(Minutos As Int) As Int";
 //BA.debugLineNum = 107;BA.debugLine="Return Minutos Mod 60";
if (true) return (int) (_minutos%60);
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return 0;
}
public static int  _vvvvvvvvvvvvv1(int _hora,int _minutos) throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub MinutosDia(Hora As Int, Minutos As Int) As Int";
 //BA.debugLineNum = 99;BA.debugLine="Return(Hora*60+Minutos)";
if (true) return (int) ((_hora*60+_minutos));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return 0;
}
public static boolean  _vvvvvvvvvvvvv6() throws Exception{
int _i = 0;
int _j = 0;
boolean _intercambiorealizado = false;
 //BA.debugLineNum = 514;BA.debugLine="Sub OrdenarActividades As Boolean";
 //BA.debugLineNum = 515;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 516;BA.debugLine="Dim IntercambioRealizado As Boolean";
_intercambiorealizado = false;
 //BA.debugLineNum = 518;BA.debugLine="IntercambioRealizado=False";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 521;BA.debugLine="For i=1 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-1);
_i = (int) (1) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 522;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuenci";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit5 ;_j = _j + step5 ) {
 //BA.debugLineNum = 523;BA.debugLine="If ComparaHoras( Starter.ActividadSecuencia(Sta";
if (_vvvvvvvvvvvv0(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].minuto_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].minuto_inicio)>0) { 
 //BA.debugLineNum = 524;BA.debugLine="IntercambiarActividades(Starter.MaxSecuencias,";
_vvvvvvvvvvvvvvvv7(mostCurrent._vvvvvvvvvv3._vv2,_j,mostCurrent._vvvvvvvvvv3._vv2,(int) (_j+1));
 //BA.debugLineNum = 529;BA.debugLine="IntercambioRealizado=True";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 //BA.debugLineNum = 535;BA.debugLine="QuitarSolapes";
_vvvvvvvvvvvvv5();
 //BA.debugLineNum = 537;BA.debugLine="Return IntercambioRealizado";
if (true) return _intercambiorealizado;
 //BA.debugLineNum = 538;BA.debugLine="End Sub";
return false;
}
public static String  _pictogramaelegido(int _id) throws Exception{
 //BA.debugLineNum = 660;BA.debugLine="Sub PictogramaElegido(Id As Int)";
 //BA.debugLineNum = 661;BA.debugLine="If Id<>-1 Then 'Si no se ha pulsado en \"Cancelar\"";
if (_id!=-1) { 
 //BA.debugLineNum = 662;BA.debugLine="If PictogramaEditado==-1 Then 'Pictograma de la";
if (_vvvvvvvvvvvvv0==-1) { 
 //BA.debugLineNum = 663;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictog";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].pictograma = BA.NumberToString(_id);
 }else {
 //BA.debugLineNum = 665;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_vvvvvvvvvvvvv0].Pictograma = BA.NumberToString(_id);
 };
 //BA.debugLineNum = 667;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvvvvvvvv2();
 };
 //BA.debugLineNum = 669;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvvvvvv5() throws Exception{
int _j = 0;
boolean _resultado = false;
 //BA.debugLineNum = 540;BA.debugLine="Sub QuitarSolapes As Boolean";
 //BA.debugLineNum = 542;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 543;BA.debugLine="Dim resultado As Boolean";
_resultado = false;
 //BA.debugLineNum = 545;BA.debugLine="resultado=False";
_resultado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 547;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vv2].num_actividades-2);
_j = (int) (0) ;
for (;_j <= limit4 ;_j = _j + step4 ) {
 //BA.debugLineNum = 548;BA.debugLine="If ComparaHoras(Starter.ActividadSecuencia(Start";
if (_vvvvvvvvvvvv0(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].hora_fin,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].minuto_fin,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].minuto_inicio)>0) { 
 //BA.debugLineNum = 549;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].hora_fin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].hora_inicio;
 //BA.debugLineNum = 550;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][_j].minuto_fin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vv2][(int) (_j+1)].minuto_inicio;
 //BA.debugLineNum = 551;BA.debugLine="resultado=True";
_resultado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 555;BA.debugLine="Return resultado";
if (true) return _resultado;
 //BA.debugLineNum = 556;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvv7() throws Exception{
 //BA.debugLineNum = 376;BA.debugLine="Sub SalidaConfigurarSecuencia";
 //BA.debugLineNum = 377;BA.debugLine="If Msgbox2(\"Se perderán todos los cambios realiza";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se perderán todos los cambios realizados."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"¿Está seguro de que desea salir sin guardarlos?"),BA.ObjectToCharSequence("Cancelar cambios"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 378;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv3.getObject()));
 //BA.debugLineNum = 379;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvv4(int _hora1,int _minuto1,int _hora2,int _minuto2) throws Exception{
int _resultado = 0;
 //BA.debugLineNum = 125;BA.debugLine="Sub SumarHoras(Hora1 As Int, Minuto1 As Int,Hora2";
 //BA.debugLineNum = 128;BA.debugLine="Dim Resultado = MinutosDia(Hora1,Minuto1)+Minutos";
_resultado = (int) (_vvvvvvvvvvvvv1(_hora1,_minuto1)+_vvvvvvvvvvvvv1(_hora2,_minuto2));
 //BA.debugLineNum = 129;BA.debugLine="If Resultado > MinutosDia(23,59) Then";
if (_resultado>_vvvvvvvvvvvvv1((int) (23),(int) (59))) { 
 //BA.debugLineNum = 130;BA.debugLine="Resultado=MinutosDia(23,59)";
_resultado = _vvvvvvvvvvvvv1((int) (23),(int) (59));
 };
 //BA.debugLineNum = 132;BA.debugLine="Return Resultado";
if (true) return _resultado;
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return 0;
}
}
