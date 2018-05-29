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
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (configurarsecuencia) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
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
public static int _vvvvvvvv3 = 0;
public static int _vvvvvvv0 = 0;
public static int _vvvvvvvv1 = 0;
public static int _vvvvvvvv5 = 0;
public anywheresoftware.b4a.objects.ScrollViewWrapper _parametrossecuencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvv6 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvv0 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _vvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper[] _vvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvv1 = null;
public static boolean _vvvvvvv2 = false;
public anywheresoftware.b4a.objects.ListViewWrapper _vvvvvv5 = null;
public static boolean _vvvvvv4 = false;
public static int _vvvvvvv5 = 0;
public static String _vvvvvv2 = "";
public static String _vvvvvv7 = "";
public javi.prieto.pictorario.main _vvvvvv1 = null;
public javi.prieto.pictorario.visualizacion _vvv6 = null;
public javi.prieto.pictorario.starter _vvv3 = null;
public javi.prieto.pictorario.acercade _vvv4 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 66;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).Initiali";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].Initialize();
 //BA.debugLineNum = 67;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero.";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.Initialize();
 //BA.debugLineNum = 69;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv3._vv6==mostCurrent._vvv3._v7)) { 
 //BA.debugLineNum = 70;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades = (int) (0);
 //BA.debugLineNum = 71;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].Descripcion = mostCurrent._vvvvvv2;
 //BA.debugLineNum = 72;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].pictograma = "reloj_6";
 //BA.debugLineNum = 73;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.tipo = (int) (3);
 //BA.debugLineNum = 74;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.tam_icono = (int) (20);
 //BA.debugLineNum = 75;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.indicar_hora = (int) (0);
 }else {
 //BA.debugLineNum = 77;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Secu";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv3._vv6),(Object)(mostCurrent._vvv3._v7));
 };
 //BA.debugLineNum = 80;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 //BA.debugLineNum = 81;BA.debugLine="Inicializar_Lista_Pictogramas";
_inicializar_lista_pictogramas();
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 590;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 591;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 592;BA.debugLine="If ListaPictogramasVisible==True Then 'Si está a";
if (_vvvvvv4==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 593;BA.debugLine="ListaPictogramas.RemoveView";
mostCurrent._vvvvvv5.RemoveView();
 //BA.debugLineNum = 594;BA.debugLine="ListaPictogramasVisible=False";
_vvvvvv4 = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 596;BA.debugLine="SalidaConfigurarSecuencia";
_vvvvvv6();
 };
 };
 //BA.debugLineNum = 599;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 600;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 325;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 321;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _botonaceptar_click() throws Exception{
 //BA.debugLineNum = 310;BA.debugLine="Sub BotonAceptar_Click";
 //BA.debugLineNum = 311;BA.debugLine="If (Starter.SecuenciaActiva==Starter.MaxSecuencia";
if ((mostCurrent._vvv3._vv6==mostCurrent._vvv3._v7)) { 
 //BA.debugLineNum = 312;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
mostCurrent._vvv3._vv5 = (int) (mostCurrent._vvv3._vv5+1);
 //BA.debugLineNum = 313;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv3._v7),(Object)(mostCurrent._vvv3._vv5-1));
 }else {
 //BA.debugLineNum = 315;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.MaxS";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvv3.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvv3._v7),(Object)(mostCurrent._vvv3._vv6));
 };
 //BA.debugLineNum = 317;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvv3.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 318;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
public static String  _botonanadiractividad_click() throws Exception{
 //BA.debugLineNum = 562;BA.debugLine="Sub BotonAnadirActividad_Click";
 //BA.debugLineNum = 563;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].Descripcion = mostCurrent._vvvvvv7;
 //BA.debugLineNum = 565;BA.debugLine="If (Starter.Secuencia(Starter.MaxSecuencias).num_";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades>0)) { 
 //BA.debugLineNum = 566;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_inicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 567;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_inicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-1)].minuto_fin;
 }else {
 //BA.debugLineNum = 569;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_inicio = (int) (8);
 //BA.debugLineNum = 570;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_inicio = (int) (0);
 };
 //BA.debugLineNum = 573;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_fin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_inicio;
 //BA.debugLineNum = 574;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_inicio+30);
 //BA.debugLineNum = 575;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin>59) { 
 //BA.debugLineNum = 576;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin-60);
 //BA.debugLineNum = 577;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_fin = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_fin+1);
 };
 //BA.debugLineNum = 580;BA.debugLine="If Starter.ActividadSecuencia(Starter.MaxSecuenci";
if (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_fin*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin>(23*60+59)) { 
 //BA.debugLineNum = 581;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].hora_fin = (int) (23);
 //BA.debugLineNum = 582;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].minuto_fin = (int) (59);
 };
 //BA.debugLineNum = 585;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias,";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades].Pictograma = "jugar";
 //BA.debugLineNum = 586;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_acti";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades+1);
 //BA.debugLineNum = 587;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 //BA.debugLineNum = 588;BA.debugLine="End Sub";
return "";
}
public static String  _botoncancelar_click() throws Exception{
 //BA.debugLineNum = 300;BA.debugLine="Sub BotonCancelar_Click";
 //BA.debugLineNum = 301;BA.debugLine="SalidaConfigurarSecuencia";
_vvvvvv6();
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_focuschanged(boolean _tienefoco) throws Exception{
 //BA.debugLineNum = 554;BA.debugLine="Sub ConfigDescripcion_FocusChanged (TieneFoco As B";
 //BA.debugLineNum = 555;BA.debugLine="If TieneFoco==True And ConfigDescripcion.Text==De";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True && (mostCurrent._vvvvvv0.getText()).equals(mostCurrent._vvvvvv2)) { 
 //BA.debugLineNum = 556;BA.debugLine="ConfigDescripcion.Text=\"\"";
mostCurrent._vvvvvv0.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 557;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descrip";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].Descripcion = "";
 //BA.debugLineNum = 558;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 560;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcion_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 550;BA.debugLine="Sub ConfigDescripcion_TextChanged (Old As String,";
 //BA.debugLineNum = 551;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).descripc";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].Descripcion = _new;
 //BA.debugLineNum = 552;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_focuschanged(boolean _tienefoco) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 523;BA.debugLine="Sub ConfigDescripcionAct_FocusChanged (TieneFoco A";
 //BA.debugLineNum = 524;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 525;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 527;BA.debugLine="If TieneFoco==True Then";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 528;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 529;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 530;BA.debugLine="If ConfigDescripcionAct(Act).Text==DescripcionAc";
if ((mostCurrent._vvvvvvv1[_act].getText()).equals(mostCurrent._vvvvvv7)) { 
 //BA.debugLineNum = 531;BA.debugLine="ConfigDescripcionAct(Act).Text=\"\"";
mostCurrent._vvvvvvv1[_act].setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 532;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].Descripcion = "";
 //BA.debugLineNum = 533;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 };
 //BA.debugLineNum = 536;BA.debugLine="End Sub";
return "";
}
public static String  _configdescripcionact_textchanged(String _old,String _new) throws Exception{
anywheresoftware.b4a.objects.EditTextWrapper _botonpulsado = null;
int _act = 0;
 //BA.debugLineNum = 539;BA.debugLine="Sub ConfigDescripcionAct_TextChanged (Old As Strin";
 //BA.debugLineNum = 540;BA.debugLine="Dim BotonPulsado As EditText";
_botonpulsado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 541;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 543;BA.debugLine="If Inicializando==False Then";
if (_vvvvvvv2==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 544;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.EditText)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 545;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 546;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].Descripcion = _new;
 };
 //BA.debugLineNum = 548;BA.debugLine="End Sub";
return "";
}
public static String  _confighorafinalact_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog _dialogotiempo = null;
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
int _resultado = 0;
int _act = 0;
 //BA.debugLineNum = 442;BA.debugLine="Sub ConfigHoraFinalAct_Click";
 //BA.debugLineNum = 443;BA.debugLine="Dim DialogoTiempo As TimeDialog";
_dialogotiempo = new anywheresoftware.b4a.agraham.dialogs.InputDialog.TimeDialog();
 //BA.debugLineNum = 444;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 445;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 446;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 448;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 449;BA.debugLine="Act=BotonPulsado.Tag";
_act = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 451;BA.debugLine="DialogoTiempo.Hour=Starter.ActividadSecuencia(Sta";
_dialogotiempo.setHour(mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_fin);
 //BA.debugLineNum = 452;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_fin);
 //BA.debugLineNum = 453;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora fina";
_resultado = _dialogotiempo.Show("Indica la hora final de la actividad","Hora final","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 456;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 457;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute<St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()<mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_inicio*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_inicio) { 
 //BA.debugLineNum = 459;BA.debugLine="Msgbox(\"La hora de finalización de una activida";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La hora de finalización de una actividad no puede ser anterior a la de inicio."),BA.ObjectToCharSequence("Hora inválida"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 461;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_fin = _dialogotiempo.getHour();
 //BA.debugLineNum = 462;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_fin = _dialogotiempo.getMinute();
 //BA.debugLineNum = 463;BA.debugLine="If QuitarSolapes==True Then";
if (_vvvvvvv3()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 464;BA.debugLine="Msgbox(\"Se ha corregido la hora final de la ac";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha corregido la hora final de la actividad para evitar solapes."),BA.ObjectToCharSequence("Hora final corregida"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 466;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 };
 };
 //BA.debugLineNum = 469;BA.debugLine="End Sub";
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
_dialogotiempo.setHour(mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_inicio);
 //BA.debugLineNum = 375;BA.debugLine="DialogoTiempo.Minute=Starter.ActividadSecuencia(S";
_dialogotiempo.setMinute(mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_inicio);
 //BA.debugLineNum = 376;BA.debugLine="DialogoTiempo.Is24Hours=False";
_dialogotiempo.setIs24Hours(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 377;BA.debugLine="Resultado=DialogoTiempo.Show(\"Indica la hora inic";
_resultado = _dialogotiempo.Show("Indica la hora inicial de la actividad","Hora inicial","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 379;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 380;BA.debugLine="If DialogoTiempo.Hour*60+DialogoTiempo.Minute>St";
if (_dialogotiempo.getHour()*60+_dialogotiempo.getMinute()>mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_fin*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_fin) { 
 //BA.debugLineNum = 382;BA.debugLine="Msgbox(\"La hora de inicio de una actividad no pu";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La hora de inicio de una actividad no puede ser posterior a la de finalización."),BA.ObjectToCharSequence("Hora inválida"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 384;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].hora_inicio = _dialogotiempo.getHour();
 //BA.debugLineNum = 385;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_act].minuto_inicio = _dialogotiempo.getMinute();
 //BA.debugLineNum = 386;BA.debugLine="If OrdenarActividades==True Then";
if (_vvvvvvv4()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 387;BA.debugLine="Msgbox(\"Se ha colocado la actividad en su posi";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se ha colocado la actividad en su posición correcta."),BA.ObjectToCharSequence("Actividades reorganizadas"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 389;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 };
 };
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return "";
}
public static String  _configindicarhora_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tiposindicador = null;
int _resultado = 0;
 //BA.debugLineNum = 280;BA.debugLine="Sub ConfigIndicarHora_Click";
 //BA.debugLineNum = 281;BA.debugLine="Dim TiposIndicador As List";
_tiposindicador = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 282;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 284;BA.debugLine="TiposIndicador.Initialize";
_tiposindicador.Initialize();
 //BA.debugLineNum = 285;BA.debugLine="TiposIndicador.AddAll(Starter.DescripcionMinutero";
_tiposindicador.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv3._vv2));
 //BA.debugLineNum = 287;BA.debugLine="resultado=InputList(TiposIndicador,\"Indicar hora";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tiposindicador,BA.ObjectToCharSequence("Indicar hora actual"),mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.indicar_hora,mostCurrent.activityBA);
 //BA.debugLineNum = 288;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 289;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.indicar_hora = _resultado;
 //BA.debugLineNum = 290;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 };
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
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
final int limit10 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-2);
_nact = _act ;
for (;(step10 > 0 && _nact <= limit10) || (step10 < 0 && _nact >= limit10) ;_nact = ((int)(0 + _nact + step10))  ) {
 //BA.debugLineNum = 358;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_nact] = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_nact+1)];
 }
};
 //BA.debugLineNum = 360;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).num_act";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-1);
 };
 //BA.debugLineNum = 362;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _configpictograma_click() throws Exception{
 //BA.debugLineNum = 329;BA.debugLine="Sub ConfigPictograma_Click";
 //BA.debugLineNum = 330;BA.debugLine="PictogramaEditado=-1";
_vvvvvvv5 = (int) (-1);
 //BA.debugLineNum = 331;BA.debugLine="Activity.AddView(ListaPictogramas, 5dip, 5dip, 10";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvv5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 332;BA.debugLine="ListaPictogramasVisible=True";
_vvvvvv4 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _configpictogramaact_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
 //BA.debugLineNum = 335;BA.debugLine="Sub ConfigPictogramaAct_Click";
 //BA.debugLineNum = 336;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 337;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 338;BA.debugLine="PictogramaEditado=BotonPulsado.Tag";
_vvvvvvv5 = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 339;BA.debugLine="Activity.AddView(ListaPictogramas, 5dip, 5dip, 10";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvv5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 340;BA.debugLine="ListaPictogramasVisible=True";
_vvvvvv4 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static String  _configtamicono_valuechanged(int _valor,boolean _cambio) throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Sub ConfigTamIcono_ValueChanged (Valor As Int, Cam";
 //BA.debugLineNum = 295;BA.debugLine="If (Cambio==True) Then";
if ((_cambio==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 296;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.tam_icono = _valor;
 };
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public static String  _configtipotablero_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _tipostablero = null;
int _resultado = 0;
 //BA.debugLineNum = 266;BA.debugLine="Sub ConfigTipoTablero_Click";
 //BA.debugLineNum = 267;BA.debugLine="Dim TiposTablero As List";
_tipostablero = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 268;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 270;BA.debugLine="TiposTablero.Initialize";
_tipostablero.Initialize();
 //BA.debugLineNum = 271;BA.debugLine="TiposTablero.AddAll(Starter.DescripcionTablero)";
_tipostablero.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(mostCurrent._vvv3._vv1));
 //BA.debugLineNum = 273;BA.debugLine="resultado=InputList(TiposTablero,\"Tipo de tablero";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_tipostablero,BA.ObjectToCharSequence("Tipo de tablero"),mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.tipo,mostCurrent.activityBA);
 //BA.debugLineNum = 274;BA.debugLine="If (resultado>=0) Then";
if ((_resultado>=0)) { 
 //BA.debugLineNum = 275;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).tablero";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].tablero.tipo = _resultado;
 //BA.debugLineNum = 276;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 };
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvv3() throws Exception{
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
 //BA.debugLineNum = 87;BA.debugLine="Dim Posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 89;BA.debugLine="If ParametrosSecuencia.IsInitialized==True Then";
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
 //BA.debugLineNum = 90;BA.debugLine="Posicion=ParametrosSecuencia.ScrollPosition";
_posicion = parent.mostCurrent._parametrossecuencia.getScrollPosition();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 92;BA.debugLine="Posicion=0";
_posicion = (int) (0);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 95;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 96;BA.debugLine="Activity.LoadLayout(\"ConfigurarSecuencia\")";
parent.mostCurrent._activity.LoadLayout("ConfigurarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 98;BA.debugLine="Inicializando=True 'Para evitar que se lancen pro";
parent._vvvvvvv2 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 100;BA.debugLine="EtiquetaInicial.Initialize(\"EtiquetaInicial\")";
parent.mostCurrent._vvvvvvv6.Initialize(mostCurrent.activityBA,"EtiquetaInicial");
 //BA.debugLineNum = 101;BA.debugLine="EtiquetaInicial.Text=\"Crear nueva secuencia\"";
parent.mostCurrent._vvvvvvv6.setText(BA.ObjectToCharSequence("Crear nueva secuencia"));
 //BA.debugLineNum = 102;BA.debugLine="If (Starter.SecuenciaActiva<Starter.MaxSecuencias";
if (true) break;

case 7:
//if
this.state = 10;
if ((parent.mostCurrent._vvv3._vv6<parent.mostCurrent._vvv3._v7)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 103;BA.debugLine="EtiquetaInicial.Text=\"Editar secuencia\"";
parent.mostCurrent._vvvvvvv6.setText(BA.ObjectToCharSequence("Editar secuencia"));
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 105;BA.debugLine="EtiquetaInicial.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 106;BA.debugLine="EtiquetaInicial.TextSize=24";
parent.mostCurrent._vvvvvvv6.setTextSize((float) (24));
 //BA.debugLineNum = 107;BA.debugLine="EtiquetaInicial.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvv6.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 108;BA.debugLine="EtiquetaInicial.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 109;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiquetaInicial";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvv6.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 111;BA.debugLine="ConfigPictograma.Initialize(\"ConfigPictograma\")";
parent.mostCurrent._vvvvvvv7.Initialize(mostCurrent.activityBA,"ConfigPictograma");
 //BA.debugLineNum = 112;BA.debugLine="ConfigPictograma.SetBackgroundImage(LoadBitmap(Fi";
parent.mostCurrent._vvvvvvv7.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].pictograma+".png").getObject()));
 //BA.debugLineNum = 113;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogram";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvv7.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvv0-parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvv6.getTop()+parent.mostCurrent._vvvvvvv6.getHeight()+parent._vvvvvvvv1),parent._vvvvvvv0,parent._vvvvvvv0);
 //BA.debugLineNum = 115;BA.debugLine="ConfigDescripcion.Initialize(\"ConfigDescripcion\")";
parent.mostCurrent._vvvvvv0.Initialize(mostCurrent.activityBA,"ConfigDescripcion");
 //BA.debugLineNum = 116;BA.debugLine="ConfigDescripcion.Text=Starter.Secuencia(Starter.";
parent.mostCurrent._vvvvvv0.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].Descripcion));
 //BA.debugLineNum = 117;BA.debugLine="ConfigDescripcion.TextColor=Colors.White";
parent.mostCurrent._vvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 118;BA.debugLine="ConfigDescripcion.TextSize=16";
parent.mostCurrent._vvvvvv0.setTextSize((float) (16));
 //BA.debugLineNum = 119;BA.debugLine="ConfigDescripcion.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvv0.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 120;BA.debugLine="ConfigDescripcion.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 121;BA.debugLine="ConfigDescripcion.Color=Colors.DarkGray";
parent.mostCurrent._vvvvvv0.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 122;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripci";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvv0.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvvv6.getTop()+parent.mostCurrent._vvvvvvv6.getHeight()+parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvv7.getLeft()-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 124;BA.debugLine="EtiqTipoTablero.Initialize(\"EtiqTipoTablero\")";
parent.mostCurrent._vvvvvvvv2.Initialize(mostCurrent.activityBA,"EtiqTipoTablero");
 //BA.debugLineNum = 125;BA.debugLine="EtiqTipoTablero.Text=\"Tipo de tablero:\"";
parent.mostCurrent._vvvvvvvv2.setText(BA.ObjectToCharSequence("Tipo de tablero:"));
 //BA.debugLineNum = 126;BA.debugLine="EtiqTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 127;BA.debugLine="EtiqTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvv2.setTextSize((float) (16));
 //BA.debugLineNum = 128;BA.debugLine="EtiqTipoTablero.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 129;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTipoTablero";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv2.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvv0.getTop()+parent.mostCurrent._vvvvvv0.getHeight()+parent._vvvvvvvv1),parent._vvvvvvvv3,parent._vvvvvvv0);
 //BA.debugLineNum = 131;BA.debugLine="ConfigTipoTablero.Initialize(\"ConfigTipoTablero\")";
parent.mostCurrent._vvvvvvvv4.Initialize(mostCurrent.activityBA,"ConfigTipoTablero");
 //BA.debugLineNum = 132;BA.debugLine="ConfigTipoTablero.Text=Starter.DescripcionTablero";
parent.mostCurrent._vvvvvvvv4.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv3._vv1[parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].tablero.tipo]));
 //BA.debugLineNum = 133;BA.debugLine="ConfigTipoTablero.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 134;BA.debugLine="ConfigTipoTablero.TextSize=16";
parent.mostCurrent._vvvvvvvv4.setTextSize((float) (16));
 //BA.debugLineNum = 135;BA.debugLine="ConfigTipoTablero.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvv4.setColor(parent._vvvvvvvv5);
 //BA.debugLineNum = 136;BA.debugLine="ConfigTipoTablero.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 137;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTipoTable";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv4.getObject()),(int) (parent._vvvvvvvv3+parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvv0.getTop()+parent.mostCurrent._vvvvvv0.getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvv3-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 139;BA.debugLine="EtiqIndicarHora.Initialize(\"EtiqIndicarHora\")";
parent.mostCurrent._vvvvvvvv6.Initialize(mostCurrent.activityBA,"EtiqIndicarHora");
 //BA.debugLineNum = 140;BA.debugLine="EtiqIndicarHora.Text=\"Indicar hora actual:\"";
parent.mostCurrent._vvvvvvvv6.setText(BA.ObjectToCharSequence("Indicar hora actual:"));
 //BA.debugLineNum = 141;BA.debugLine="EtiqIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 142;BA.debugLine="EtiqIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvv6.setTextSize((float) (16));
 //BA.debugLineNum = 143;BA.debugLine="EtiqIndicarHora.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 144;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqIndicarHora";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv6.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvv4.getHeight()+parent._vvvvvvvv1),parent._vvvvvvvv3,parent._vvvvvvv0);
 //BA.debugLineNum = 146;BA.debugLine="ConfigIndicarHora.Initialize(\"ConfigIndicarHora\")";
parent.mostCurrent._vvvvvvvv7.Initialize(mostCurrent.activityBA,"ConfigIndicarHora");
 //BA.debugLineNum = 147;BA.debugLine="ConfigIndicarHora.Text=Starter.DescripcionMinuter";
parent.mostCurrent._vvvvvvvv7.setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv3._vv2[parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].tablero.indicar_hora]));
 //BA.debugLineNum = 148;BA.debugLine="ConfigIndicarHora.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 149;BA.debugLine="ConfigIndicarHora.TextSize=16";
parent.mostCurrent._vvvvvvvv7.setTextSize((float) (16));
 //BA.debugLineNum = 150;BA.debugLine="ConfigIndicarHora.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvv7.setColor(parent._vvvvvvvv5);
 //BA.debugLineNum = 151;BA.debugLine="ConfigIndicarHora.Gravity=Bit.Or(Gravity.CENTER_V";
parent.mostCurrent._vvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 152;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigIndicarHo";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv7.getObject()),(int) (parent._vvvvvvvv3+parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvvv4.getTop()+parent.mostCurrent._vvvvvvvv4.getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvv3-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 154;BA.debugLine="EtiqTamIcono.Initialize(\"EtiqTamIcono\")";
parent.mostCurrent._vvvvvvvv0.Initialize(mostCurrent.activityBA,"EtiqTamIcono");
 //BA.debugLineNum = 155;BA.debugLine="EtiqTamIcono.Text=\"Tamaño de los iconos:\"";
parent.mostCurrent._vvvvvvvv0.setText(BA.ObjectToCharSequence("Tamaño de los iconos:"));
 //BA.debugLineNum = 156;BA.debugLine="EtiqTamIcono.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 157;BA.debugLine="EtiqTamIcono.TextSize=16";
parent.mostCurrent._vvvvvvvv0.setTextSize((float) (16));
 //BA.debugLineNum = 158;BA.debugLine="EtiqTamIcono.Gravity=Gravity.CENTER_VERTICAL";
parent.mostCurrent._vvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 159;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqTamIcono,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvv0.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvv7.getHeight()+parent._vvvvvvvv1),parent._vvvvvvvv3,parent._vvvvvvv0);
 //BA.debugLineNum = 161;BA.debugLine="ConfigTamIcono.Initialize(\"ConfigTamIcono\")";
parent.mostCurrent._vvvvvvvvv1.Initialize(mostCurrent.activityBA,"ConfigTamIcono");
 //BA.debugLineNum = 162;BA.debugLine="ConfigTamIcono.Value=Starter.Secuencia(Starter.Ma";
parent.mostCurrent._vvvvvvvvv1.setValue(parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].tablero.tam_icono);
 //BA.debugLineNum = 163;BA.debugLine="ConfigTamIcono.Max=30";
parent.mostCurrent._vvvvvvvvv1.setMax((int) (30));
 //BA.debugLineNum = 164;BA.debugLine="ConfigTamIcono.Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvv1.setColor(parent._vvvvvvvv5);
 //BA.debugLineNum = 165;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigTamIcono,";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv1.getObject()),(int) (parent._vvvvvvvv3+parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvv7.getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvvv3-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 167;BA.debugLine="EtiqActividades.Initialize(\"EtiqActividades\")";
parent.mostCurrent._vvvvvvvvv2.Initialize(mostCurrent.activityBA,"EtiqActividades");
 //BA.debugLineNum = 168;BA.debugLine="EtiqActividades.Text=\"Actividades\"";
parent.mostCurrent._vvvvvvvvv2.setText(BA.ObjectToCharSequence("Actividades"));
 //BA.debugLineNum = 169;BA.debugLine="EtiqActividades.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 170;BA.debugLine="EtiqActividades.TextSize=24";
parent.mostCurrent._vvvvvvvvv2.setTextSize((float) (24));
 //BA.debugLineNum = 171;BA.debugLine="EtiqActividades.Typeface=Typeface.DEFAULT_BOLD";
parent.mostCurrent._vvvvvvvvv2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 172;BA.debugLine="EtiqActividades.Gravity=Bit.Or(Gravity.CENTER_VER";
parent.mostCurrent._vvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 173;BA.debugLine="ParametrosSecuencia.Panel.AddView(EtiqActividades";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv2.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvv1.getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 175;BA.debugLine="Dim InicioVertical As Int";
_iniciovertical = 0;
 //BA.debugLineNum = 176;BA.debugLine="Dim FinVertical As Int";
_finvertical = 0;
 //BA.debugLineNum = 178;BA.debugLine="FinVertical=EtiqActividades.Top+EtiqActividades.H";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvv2.getTop()+parent.mostCurrent._vvvvvvvvv2.getHeight());
 //BA.debugLineNum = 179;BA.debugLine="InicioVertical=FinVertical+SeparacionCasillas";
_iniciovertical = (int) (_finvertical+parent._vvvvvvvv1);
 //BA.debugLineNum = 181;BA.debugLine="For Act=0 To Starter.Secuencia(Starter.MaxSecuenc";
if (true) break;

case 11:
//for
this.state = 18;
step79 = 1;
limit79 = (int) (parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].num_actividades-1);
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
 //BA.debugLineNum = 183;BA.debugLine="If (Act>0) Then";
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
 //BA.debugLineNum = 184;BA.debugLine="InicioVertical=ConfigHoraInicioAct(Act-1).Top+C";
_iniciovertical = (int) (parent.mostCurrent._vvvvvvvvv3[(int) (_act-1)].getTop()+parent.mostCurrent._vvvvvvvvv3[(int) (_act-1)].getHeight()+4*parent._vvvvvvvv1);
 if (true) break;

case 17:
//C
this.state = 28;
;
 //BA.debugLineNum = 187;BA.debugLine="ConfigPictogramaAct(Act).Initialize(\"ConfigPicto";
parent.mostCurrent._vvvvvvvvv4[_act].Initialize(mostCurrent.activityBA,"ConfigPictogramaAct");
 //BA.debugLineNum = 188;BA.debugLine="ConfigPictogramaAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvv4[_act].setTag((Object)(_act));
 //BA.debugLineNum = 189;BA.debugLine="ConfigPictogramaAct(Act).SetBackgroundImage(Load";
parent.mostCurrent._vvvvvvvvv4[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].Pictograma+".png").getObject()));
 //BA.debugLineNum = 190;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigPictogra";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv4[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvv0-parent._vvvvvvvv1),_iniciovertical,parent._vvvvvvv0,parent._vvvvvvv0);
 //BA.debugLineNum = 192;BA.debugLine="ConfigDescripcionAct(Act).Initialize(\"ConfigDesc";
parent.mostCurrent._vvvvvvv1[_act].Initialize(mostCurrent.activityBA,"ConfigDescripcionAct");
 //BA.debugLineNum = 193;BA.debugLine="ConfigDescripcionAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvv1[_act].setTag((Object)(_act));
 //BA.debugLineNum = 194;BA.debugLine="ConfigDescripcionAct(Act).Text=Starter.Actividad";
parent.mostCurrent._vvvvvvv1[_act].setText(BA.ObjectToCharSequence(parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].Descripcion));
 //BA.debugLineNum = 195;BA.debugLine="ConfigDescripcionAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvv1[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 196;BA.debugLine="ConfigDescripcionAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvv1[_act].setTextSize((float) (16));
 //BA.debugLineNum = 197;BA.debugLine="ConfigDescripcionAct(Act).Gravity=Bit.Or(Gravity";
parent.mostCurrent._vvvvvvv1[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 198;BA.debugLine="ConfigDescripcionAct(Act).Color=Starter.Colores(";
parent.mostCurrent._vvvvvvv1[_act].setColor(parent.mostCurrent._vvv3._vv4[_act]);
 //BA.debugLineNum = 199;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigDescripc";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvv1[_act].getObject()),parent._vvvvvvvv1,_iniciovertical,(int) (parent.mostCurrent._vvvvvvvvv4[_act].getLeft()-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 201;BA.debugLine="ConfigHoraInicioAct(Act).Initialize(\"ConfigHoraI";
parent.mostCurrent._vvvvvvvvv3[_act].Initialize(mostCurrent.activityBA,"ConfigHoraInicioAct");
 //BA.debugLineNum = 202;BA.debugLine="ConfigHoraInicioAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvv3[_act].setTag((Object)(_act));
 //BA.debugLineNum = 203;BA.debugLine="ConfigHoraInicioAct(Act).Text=\"Desde\"&CRLF&Numbe";
parent.mostCurrent._vvvvvvvvv3[_act].setText(BA.ObjectToCharSequence("Desde"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].hora_inicio,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].minuto_inicio,(int) (2),(int) (0))));
 //BA.debugLineNum = 204;BA.debugLine="ConfigHoraInicioAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv3[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 205;BA.debugLine="ConfigHoraInicioAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvv3[_act].setTextSize((float) (16));
 //BA.debugLineNum = 206;BA.debugLine="ConfigHoraInicioAct(Act).Gravity=Bit.Or(Gravity.";
parent.mostCurrent._vvvvvvvvv3[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 207;BA.debugLine="ConfigHoraInicioAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvv3[_act].setColor(parent._vvvvvvvv5);
 //BA.debugLineNum = 208;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraInic";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv3[_act].getObject()),(int) (3*parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvv1[_act].getTop()+parent.mostCurrent._vvvvvvv1[_act].getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvv1-parent._vvvvvvv0/(double)2),parent._vvvvvvv0);
 //BA.debugLineNum = 210;BA.debugLine="ConfigHoraFinalAct(Act).Initialize(\"ConfigHoraFi";
parent.mostCurrent._vvvvvvvvv5[_act].Initialize(mostCurrent.activityBA,"ConfigHoraFinalAct");
 //BA.debugLineNum = 211;BA.debugLine="ConfigHoraFinalAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvv5[_act].setTag((Object)(_act));
 //BA.debugLineNum = 212;BA.debugLine="ConfigHoraFinalAct(Act).Text=\"Hasta\"&CRLF&Number";
parent.mostCurrent._vvvvvvvvv5[_act].setText(BA.ObjectToCharSequence("Hasta"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].hora_fin,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(parent.mostCurrent._vvv3._vv0[parent.mostCurrent._vvv3._v7][_act].minuto_fin,(int) (2),(int) (0))));
 //BA.debugLineNum = 213;BA.debugLine="ConfigHoraFinalAct(Act).TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv5[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 214;BA.debugLine="ConfigHoraFinalAct(Act).TextSize=16";
parent.mostCurrent._vvvvvvvvv5[_act].setTextSize((float) (16));
 //BA.debugLineNum = 215;BA.debugLine="ConfigHoraFinalAct(Act).Gravity=Bit.Or(Gravity.C";
parent.mostCurrent._vvvvvvvvv5[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 216;BA.debugLine="ConfigHoraFinalAct(Act).Color=ColorDeFondo";
parent.mostCurrent._vvvvvvvvv5[_act].setColor(parent._vvvvvvvv5);
 //BA.debugLineNum = 217;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigHoraFina";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv5[_act].getObject()),(int) (parent.mostCurrent._vvvvvvvvv3[_act].getLeft()+parent.mostCurrent._vvvvvvvvv3[_act].getWidth()+parent._vvvvvvvv1),(int) (parent.mostCurrent._vvvvvvv1[_act].getTop()+parent.mostCurrent._vvvvvvv1[_act].getHeight()+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-3*parent._vvvvvvvv1-parent._vvvvvvv0/(double)2),parent._vvvvvvv0);
 //BA.debugLineNum = 219;BA.debugLine="ConfigOpcionesAct(Act).Initialize(\"ConfigOpcione";
parent.mostCurrent._vvvvvvvvv6[_act].Initialize(mostCurrent.activityBA,"ConfigOpcionesAct");
 //BA.debugLineNum = 220;BA.debugLine="ConfigOpcionesAct(Act).Tag=Act";
parent.mostCurrent._vvvvvvvvv6[_act].setTag((Object)(_act));
 //BA.debugLineNum = 221;BA.debugLine="ConfigOpcionesAct(Act).SetBackgroundImage(LoadBi";
parent.mostCurrent._vvvvvvvvv6[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 222;BA.debugLine="ParametrosSecuencia.Panel.AddView(ConfigOpciones";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv6[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-parent._vvvvvvv0-parent._vvvvvvvv1),parent.mostCurrent._vvvvvvvvv5[_act].getTop(),parent._vvvvvvv0,parent._vvvvvvv0);
 //BA.debugLineNum = 224;BA.debugLine="FinVertical=ConfigHoraFinalAct(Act).Top+ConfigHo";
_finvertical = (int) (parent.mostCurrent._vvvvvvvvv5[_act].getTop()+parent.mostCurrent._vvvvvvvvv5[_act].getHeight());
 if (true) break;
if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 228;BA.debugLine="BotonAnadirActividad.Initialize(\"BotonAnadirActiv";
parent.mostCurrent._vvvvvvvvv7.Initialize(mostCurrent.activityBA,"BotonAnadirActividad");
 //BA.debugLineNum = 229;BA.debugLine="BotonAnadirActividad.Text=\"Añadir Actividad\"";
parent.mostCurrent._vvvvvvvvv7.setText(BA.ObjectToCharSequence("Añadir Actividad"));
 //BA.debugLineNum = 230;BA.debugLine="BotonAnadirActividad.TextSize=16";
parent.mostCurrent._vvvvvvvvv7.setTextSize((float) (16));
 //BA.debugLineNum = 231;BA.debugLine="BotonAnadirActividad.Gravity=Bit.Or(Gravity.CENTE";
parent.mostCurrent._vvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 232;BA.debugLine="BotonAnadirActividad.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 233;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 19:
//if
this.state = 22;
if (parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].num_actividades==parent.mostCurrent._vvv3._v0) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 234;BA.debugLine="BotonAnadirActividad.Enabled=False";
parent.mostCurrent._vvvvvvvvv7.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 22:
//C
this.state = 23;
;
 //BA.debugLineNum = 236;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAnadirActi";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv7.getObject()),parent._vvvvvvvv1,(int) (_finvertical+parent._vvvvvvvv1),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 238;BA.debugLine="BotonAceptar.Initialize(\"BotonAceptar\")";
parent.mostCurrent._vvvvvvvvv0.Initialize(mostCurrent.activityBA,"BotonAceptar");
 //BA.debugLineNum = 239;BA.debugLine="BotonAceptar.Text=\"Aceptar\"";
parent.mostCurrent._vvvvvvvvv0.setText(BA.ObjectToCharSequence("Aceptar"));
 //BA.debugLineNum = 240;BA.debugLine="BotonAceptar.TextSize=16";
parent.mostCurrent._vvvvvvvvv0.setTextSize((float) (16));
 //BA.debugLineNum = 241;BA.debugLine="BotonAceptar.Gravity=Bit.Or(Gravity.CENTER_VERTIC";
parent.mostCurrent._vvvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 242;BA.debugLine="BotonAceptar.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 243;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonAceptar,Se";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvv0.getObject()),parent._vvvvvvvv1,(int) (parent.mostCurrent._vvvvvvvvv7.getTop()+parent.mostCurrent._vvvvvvvvv7.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 245;BA.debugLine="If Starter.Secuencia(Starter.MaxSecuencias).num_a";
if (true) break;

case 23:
//if
this.state = 26;
if (parent.mostCurrent._vvv3._vv7[parent.mostCurrent._vvv3._v7].num_actividades==0) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 246;BA.debugLine="BotonAceptar.Enabled=False";
parent.mostCurrent._vvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 26:
//C
this.state = -1;
;
 //BA.debugLineNum = 249;BA.debugLine="BotonCancelar.Initialize(\"BotonCancelar\")";
parent.mostCurrent._vvvvvvvvvv1.Initialize(mostCurrent.activityBA,"BotonCancelar");
 //BA.debugLineNum = 250;BA.debugLine="BotonCancelar.Text=\"Cancelar\"";
parent.mostCurrent._vvvvvvvvvv1.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 251;BA.debugLine="BotonCancelar.TextSize=16";
parent.mostCurrent._vvvvvvvvvv1.setTextSize((float) (16));
 //BA.debugLineNum = 252;BA.debugLine="BotonCancelar.Gravity=Bit.Or(Gravity.CENTER_VERTI";
parent.mostCurrent._vvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 253;BA.debugLine="BotonCancelar.TextColor=Colors.Black";
parent.mostCurrent._vvvvvvvvvv1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 254;BA.debugLine="ParametrosSecuencia.Panel.AddView(BotonCancelar,5";
parent.mostCurrent._parametrossecuencia.getPanel().AddView((android.view.View)(parent.mostCurrent._vvvvvvvvvv1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+parent._vvvvvvvv1),parent.mostCurrent._vvvvvvvvv0.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-2*parent._vvvvvvvv1),parent._vvvvvvv0);
 //BA.debugLineNum = 256;BA.debugLine="ParametrosSecuencia.Panel.Height=BotonCancelar.To";
parent.mostCurrent._parametrossecuencia.getPanel().setHeight((int) (parent.mostCurrent._vvvvvvvvvv1.getTop()+parent.mostCurrent._vvvvvvvvvv1.getHeight()+parent._vvvvvvvv1));
 //BA.debugLineNum = 258;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 259;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 29;
return;
case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 260;BA.debugLine="ParametrosSecuencia.ScrollPosition=Posicion";
parent.mostCurrent._parametrossecuencia.setScrollPosition(_posicion);
 //BA.debugLineNum = 262;BA.debugLine="Inicializando=False";
parent._vvvvvvv2 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim SeparacionHorizontal=25%X As Int  'Separación";
_vvvvvvvv3 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA);
 //BA.debugLineNum = 17;BA.debugLine="Dim TamCasilla=60dip As Int 'Tamaño vertical de l";
_vvvvvvv0 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 18;BA.debugLine="Dim SeparacionCasillas=5dip As Int 'Separación ve";
_vvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 20;BA.debugLine="Dim ColorDeFondo=0xFFF0FFFF As Int";
_vvvvvvvv5 = (int) (0xfff0ffff);
 //BA.debugLineNum = 22;BA.debugLine="Private ParametrosSecuencia As ScrollView";
mostCurrent._parametrossecuencia = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim EtiquetaInicial As Label";
mostCurrent._vvvvvvv6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim ConfigDescripcion As EditText";
mostCurrent._vvvvvv0 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ConfigPictograma As Label";
mostCurrent._vvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim EtiqTipoTablero As Label";
mostCurrent._vvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim ConfigTipoTablero As Label";
mostCurrent._vvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim EtiqIndicarHora As Label";
mostCurrent._vvvvvvvv6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ConfigIndicarHora As Label";
mostCurrent._vvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim EtiqTamIcono As Label";
mostCurrent._vvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim ConfigTamIcono As SeekBar";
mostCurrent._vvvvvvvvv1 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim EtiqActividades As Label";
mostCurrent._vvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim ConfigDescripcionAct(Starter.MaxActividades)";
mostCurrent._vvvvvvv1 = new anywheresoftware.b4a.objects.EditTextWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvv1[i0] = new anywheresoftware.b4a.objects.EditTextWrapper();
}
}
;
 //BA.debugLineNum = 37;BA.debugLine="Dim ConfigHoraInicioAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 38;BA.debugLine="Dim ConfigHoraFinalAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvv5 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvvvvvv5.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvv5[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 39;BA.debugLine="Dim ConfigPictogramaAct(Starter.MaxActividades) A";
mostCurrent._vvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvv4[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 40;BA.debugLine="Dim ConfigOpcionesAct(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvv6 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvvvvvv6.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvv6[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 42;BA.debugLine="Dim BotonAnadirActividad As Button";
mostCurrent._vvvvvvvvv7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim BotonAceptar As Button";
mostCurrent._vvvvvvvvv0 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim BotonCancelar As Button";
mostCurrent._vvvvvvvvvv1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim Inicializando As Boolean";
_vvvvvvv2 = false;
 //BA.debugLineNum = 50;BA.debugLine="Dim ListaPictogramas As ListView";
mostCurrent._vvvvvv5 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim ListaPictogramasVisible As Boolean";
_vvvvvv4 = false;
 //BA.debugLineNum = 53;BA.debugLine="Dim PictogramaEditado As Int";
_vvvvvvv5 = 0;
 //BA.debugLineNum = 57;BA.debugLine="Dim DescripcionSecuenciaPorDefecto=\"Pulsa aquí pa";
mostCurrent._vvvvvv2 = "Pulsa aquí para poner un nombre de secuencia";
 //BA.debugLineNum = 58;BA.debugLine="Dim DescripcionActividadPorDefecto=\"Nombre de la";
mostCurrent._vvvvvv7 = "Nombre de la nueva actividad";
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static void  _inicializar_lista_pictogramas() throws Exception{
ResumableSub_Inicializar_Lista_Pictogramas rsub = new ResumableSub_Inicializar_Lista_Pictogramas(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Inicializar_Lista_Pictogramas extends BA.ResumableSub {
public ResumableSub_Inicializar_Lista_Pictogramas(javi.prieto.pictorario.configurarsecuencia parent) {
this.parent = parent;
}
javi.prieto.pictorario.configurarsecuencia parent;
anywheresoftware.b4a.objects.collections.List _filelist = null;
String _file1 = "";
String _file2 = "";
int _n = 0;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitmap1 = null;
int step17;
int limit17;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 483;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 484;BA.debugLine="Dim file1 As String";
_file1 = "";
 //BA.debugLineNum = 485;BA.debugLine="Dim file2 As String";
_file2 = "";
 //BA.debugLineNum = 486;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 488;BA.debugLine="If ListaPictogramas.IsInitialized=False Then";
if (true) break;

case 1:
//if
this.state = 12;
if (parent.mostCurrent._vvvvvv5.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 490;BA.debugLine="ProgressDialogShow(\"Inicializando lista de picto";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Inicializando lista de pictogramas"));
 //BA.debugLineNum = 492;BA.debugLine="ListaPictogramas.Initialize(\"ListaPictogramas\")";
parent.mostCurrent._vvvvvv5.Initialize(mostCurrent.activityBA,"ListaPictogramas");
 //BA.debugLineNum = 493;BA.debugLine="ListaPictogramas.Color=Colors.LightGray";
parent.mostCurrent._vvvvvv5.setColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 494;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.Label.TextCol";
parent.mostCurrent._vvvvvv5.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 495;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.SecondLabel.V";
parent.mostCurrent._vvvvvv5.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 496;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.ImageView.Wid";
parent.mostCurrent._vvvvvv5.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 497;BA.debugLine="ListaPictogramas.TwoLinesAndBitmap.ImageView.Hei";
parent.mostCurrent._vvvvvv5.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 498;BA.debugLine="ListaPictogramas.Padding=Array As Int(5dip,5dip,";
parent.mostCurrent._vvvvvv5.setPadding(new int[]{anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))});
 //BA.debugLineNum = 499;BA.debugLine="ListaPictogramas.FastScrollEnabled = True";
parent.mostCurrent._vvvvvv5.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 501;BA.debugLine="fileList = File.ListFiles(File.DirAssets)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirAssets());
 //BA.debugLineNum = 502;BA.debugLine="fileList.Sort(True)";
_filelist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 504;BA.debugLine="For n = 0 To fileList.Size-1";
if (true) break;

case 4:
//for
this.state = 11;
step17 = 1;
limit17 = (int) (_filelist.getSize()-1);
_n = (int) (0) ;
this.state = 13;
if (true) break;

case 13:
//C
this.state = 11;
if ((step17 > 0 && _n <= limit17) || (step17 < 0 && _n >= limit17)) this.state = 6;
if (true) break;

case 14:
//C
this.state = 13;
_n = ((int)(0 + _n + step17)) ;
if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 505;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 15;
return;
case 15:
//C
this.state = 7;
;
 //BA.debugLineNum = 506;BA.debugLine="file1 = fileList.Get(n)";
_file1 = BA.ObjectToString(_filelist.Get(_n));
 //BA.debugLineNum = 507;BA.debugLine="If file1.Contains(\".png\") Then";
if (true) break;

case 7:
//if
this.state = 10;
if (_file1.contains(".png")) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 508;BA.debugLine="file2=file1.Replace(\".png\",\"\")";
_file2 = _file1.replace(".png","");
 //BA.debugLineNum = 509;BA.debugLine="Dim Bitmap1 As Bitmap";
_bitmap1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 510;BA.debugLine="Bitmap1.InitializeSample(File.DirAssets, file1";
_bitmap1.InitializeSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_file1,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 511;BA.debugLine="ListaPictogramas.AddTwoLinesAndBitmap(file2,\"\"";
parent.mostCurrent._vvvvvv5.AddTwoLinesAndBitmap(BA.ObjectToCharSequence(_file2),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(_bitmap1.getObject()));
 if (true) break;

case 10:
//C
this.state = 14;
;
 if (true) break;
if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 514;BA.debugLine="ListaPictogramasVisible=False";
parent._vvvvvv4 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 516;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 521;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _listapictogramas_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 471;BA.debugLine="Sub ListaPictogramas_ItemClick (Position As Int, V";
 //BA.debugLineNum = 472;BA.debugLine="ListaPictogramas.RemoveView";
mostCurrent._vvvvvv5.RemoveView();
 //BA.debugLineNum = 473;BA.debugLine="ListaPictogramasVisible=False";
_vvvvvv4 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 474;BA.debugLine="If PictogramaEditado==-1 Then";
if (_vvvvvvv5==-1) { 
 //BA.debugLineNum = 475;BA.debugLine="Starter.Secuencia(Starter.MaxSecuencias).pictogr";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].pictograma = BA.ObjectToString(_value);
 }else {
 //BA.debugLineNum = 477;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencias";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_vvvvvvv5].Pictograma = BA.ObjectToString(_value);
 };
 //BA.debugLineNum = 479;BA.debugLine="DibujarConfigurarSecuencia";
_vvvvvv3();
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvv4() throws Exception{
int _i = 0;
int _j = 0;
int _hm_1 = 0;
int _hm_2 = 0;
javi.prieto.pictorario.starter._actividad _actint = null;
boolean _intercambiorealizado = false;
 //BA.debugLineNum = 394;BA.debugLine="Sub OrdenarActividades As Boolean";
 //BA.debugLineNum = 395;BA.debugLine="Dim i,j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 396;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 397;BA.debugLine="Dim ActInt As Actividad";
_actint = new javi.prieto.pictorario.starter._actividad();
 //BA.debugLineNum = 398;BA.debugLine="Dim IntercambioRealizado As Boolean";
_intercambiorealizado = false;
 //BA.debugLineNum = 400;BA.debugLine="IntercambioRealizado=False";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 403;BA.debugLine="For i=1 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-1);
_i = (int) (1) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 404;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuenci";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-2);
_j = (int) (0) ;
for (;(step7 > 0 && _j <= limit7) || (step7 < 0 && _j >= limit7) ;_j = ((int)(0 + _j + step7))  ) {
 //BA.debugLineNum = 405;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_1 = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].hora_inicio*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].minuto_inicio);
 //BA.debugLineNum = 406;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecu";
_hm_2 = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 407;BA.debugLine="If hm_1>hm_2 Then";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 408;BA.debugLine="ActInt=Starter.ActividadSecuencia(Starter.MaxS";
_actint = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j];
 //BA.debugLineNum = 409;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j] = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)];
 //BA.debugLineNum = 410;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuenci";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)] = _actint;
 //BA.debugLineNum = 411;BA.debugLine="IntercambioRealizado=True";
_intercambiorealizado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 }
};
 //BA.debugLineNum = 417;BA.debugLine="QuitarSolapes";
_vvvvvvv3();
 //BA.debugLineNum = 419;BA.debugLine="Return IntercambioRealizado";
if (true) return _intercambiorealizado;
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvv3() throws Exception{
int _hm_1 = 0;
int _hm_2 = 0;
int _j = 0;
boolean _resultado = false;
 //BA.debugLineNum = 422;BA.debugLine="Sub QuitarSolapes As Boolean";
 //BA.debugLineNum = 423;BA.debugLine="Dim hm_1,hm_2 As Int";
_hm_1 = 0;
_hm_2 = 0;
 //BA.debugLineNum = 424;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 425;BA.debugLine="Dim resultado As Boolean";
_resultado = false;
 //BA.debugLineNum = 427;BA.debugLine="resultado=False";
_resultado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 429;BA.debugLine="For j=0 To Starter.Secuencia(Starter.MaxSecuencia";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._v7].num_actividades-2);
_j = (int) (0) ;
for (;(step5 > 0 && _j <= limit5) || (step5 < 0 && _j >= limit5) ;_j = ((int)(0 + _j + step5))  ) {
 //BA.debugLineNum = 430;BA.debugLine="hm_1=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_1 = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].hora_fin*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].minuto_fin);
 //BA.debugLineNum = 431;BA.debugLine="hm_2=Starter.ActividadSecuencia(Starter.MaxSecue";
_hm_2 = (int) (mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].hora_inicio*60+mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].minuto_inicio);
 //BA.debugLineNum = 432;BA.debugLine="If hm_1>hm_2 Then 'Si es así, las iguala";
if (_hm_1>_hm_2) { 
 //BA.debugLineNum = 433;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].hora_fin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].hora_inicio;
 //BA.debugLineNum = 434;BA.debugLine="Starter.ActividadSecuencia(Starter.MaxSecuencia";
mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][_j].minuto_fin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._v7][(int) (_j+1)].minuto_inicio;
 //BA.debugLineNum = 435;BA.debugLine="resultado=True";
_resultado = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 439;BA.debugLine="Return resultado";
if (true) return _resultado;
 //BA.debugLineNum = 440;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvv6() throws Exception{
 //BA.debugLineNum = 304;BA.debugLine="Sub SalidaConfigurarSecuencia";
 //BA.debugLineNum = 305;BA.debugLine="If Msgbox2(\"Se perderán todos los cambios realiza";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se perderán todos los cambios realizados."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"¿Está seguro de que desea salir sin guardarlos?"),BA.ObjectToCharSequence("Cancelar cambios"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 306;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 308;BA.debugLine="End Sub";
return "";
}
}
