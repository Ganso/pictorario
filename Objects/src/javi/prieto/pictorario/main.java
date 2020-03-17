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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _panelscroll = null;
public anywheresoftware.b4a.objects.LabelWrapper _pictorario = null;
public static int _vvvvvvvvvv6 = 0;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv4 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv5 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv6 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvv7 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvv0 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.starter _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvvv3 = null;
public javi.prieto.pictorario.httputils2service _vvvvvvvvvv4 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (seleccionpictogramas.mostCurrent != null);
vis = vis | (visualizacion.mostCurrent != null);
vis = vis | (acercade.mostCurrent != null);
vis = vis | (configuracion.mostCurrent != null);
vis = vis | (configurarsecuencia.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _resultado = 0;
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 52;BA.debugLine="DibujarPortada";
_vvvvvvvvvv5();
 //BA.debugLineNum = 55;BA.debugLine="If (Starter.DetectadaVersionAntigua==True) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv5 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 56;BA.debugLine="resultado=Msgbox2( _ 			\"Se han hecho cambios en";
_resultado = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se han hecho cambios en la aplicación que han podido afectar a las secuencias ya creadas."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Se recomienda comenzar con una configuración limpia."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Disculpe las molestias."),BA.ObjectToCharSequence("Actualización de datos"),"Reestablecer configuración (RECOMENDADO)","","Importar datos anteriores",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 61;BA.debugLine="If resultado==DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 62;BA.debugLine="CallSub(Starter,\"Inicializar_Con_Ejemplo\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Inicializar_Con_Ejemplo");
 //BA.debugLineNum = 63;BA.debugLine="CallSub(Starter,\"BorrarPictogramas\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"BorrarPictogramas");
 //BA.debugLineNum = 64;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 };
 };
 //BA.debugLineNum = 69;BA.debugLine="If (Starter.VersionInstalada<>-1 And Starter.Vers";
if ((mostCurrent._vvvvvvvvvv2._vvv4 /*int*/ !=-1 && mostCurrent._vvvvvvvvvv2._vvv4 /*int*/ !=anywheresoftware.b4a.keywords.Common.Application.getVersionCode())) { 
 //BA.debugLineNum = 70;BA.debugLine="Msgbox2(\"Novedades de la versión \"&Application.V";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Novedades de la versión "+anywheresoftware.b4a.keywords.Common.Application.getVersionName()+":"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._vvvvvvvvvv2._v6 /*String*/ ),BA.ObjectToCharSequence("APLICACIÓN ACTUALIZADA"),"Continuar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 71;BA.debugLine="Starter.VersionInstalada=Application.VersionCode";
mostCurrent._vvvvvvvvvv2._vvv4 /*int*/  = anywheresoftware.b4a.keywords.Common.Application.getVersionCode();
 //BA.debugLineNum = 72;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 };
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.main parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.main parent;
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
 //BA.debugLineNum = 294;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 295;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 237;BA.debugLine="DibujarPortada";
_vvvvvvvvvv5();
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _botonacercade_click() throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Sub BotonAcercaDe_Click";
 //BA.debugLineNum = 249;BA.debugLine="StartActivity(AcercaDe)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv6.getObject()));
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
public static String  _botoncandado_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
 //BA.debugLineNum = 275;BA.debugLine="Sub BotonCandado_Click";
 //BA.debugLineNum = 276;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 277;BA.debugLine="Vibracion.Vibrate(100)";
_vibracion.Vibrate(processBA,(long) (100));
 //BA.debugLineNum = 278;BA.debugLine="ContadorCandado=ContadorCandado+1";
_vvvvvvvvvv6 = (int) (_vvvvvvvvvv6+1);
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _botoncandado_longclick() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
 //BA.debugLineNum = 281;BA.debugLine="Sub BotonCandado_LongClick";
 //BA.debugLineNum = 282;BA.debugLine="If ( ContadorCandado>0 ) Then";
if ((_vvvvvvvvvv6>0)) { 
 //BA.debugLineNum = 283;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 284;BA.debugLine="Vibracion.Vibrate(300)";
_vibracion.Vibrate(processBA,(long) (300));
 //BA.debugLineNum = 285;BA.debugLine="Starter.AplicacionProtegida=False";
mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 286;BA.debugLine="Msgbox(\"Se puede reactivar desde el menú de conf";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Se puede reactivar desde el menú de configuración."),BA.ObjectToCharSequence("Protección desactivada"),mostCurrent.activityBA);
 //BA.debugLineNum = 287;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 288;BA.debugLine="DibujarPortada";
_vvvvvvvvvv5();
 };
 //BA.debugLineNum = 290;BA.debugLine="ContadorCandado=0";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _botonconfiguracion_click() throws Exception{
 //BA.debugLineNum = 244;BA.debugLine="Sub BotonConfiguracion_Click";
 //BA.debugLineNum = 245;BA.debugLine="StartActivity(Configuracion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv7.getObject()));
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public static String  _botoncrear_click() throws Exception{
 //BA.debugLineNum = 252;BA.debugLine="Sub BotonCrear_Click";
 //BA.debugLineNum = 253;BA.debugLine="Starter.SecuenciaActiva=Starter.MaxSecuencias";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = mostCurrent._vvvvvvvvvv2._vv2 /*int*/ ;
 //BA.debugLineNum = 254;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()));
 //BA.debugLineNum = 255;BA.debugLine="End Sub";
return "";
}
public static String  _botoneditar_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
anywheresoftware.b4a.objects.collections.List _opciones = null;
int _resultado = 0;
int _sec = 0;
 //BA.debugLineNum = 184;BA.debugLine="Sub BotonEditar_click";
 //BA.debugLineNum = 185;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 186;BA.debugLine="Dim Opciones As List";
_opciones = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 187;BA.debugLine="Dim resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 189;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 190;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 191;BA.debugLine="Opciones.Initialize2(Array As String(\"Editar secu";
_opciones.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Editar secuencia","Borrar secuencia","Duplicar secuencia","CANCELAR"}));
 //BA.debugLineNum = 192;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_opciones,BA.ObjectToCharSequence("Acción"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 193;BA.debugLine="Select resultado";
switch (_resultado) {
case 0: {
 //BA.debugLineNum = 195;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 197;BA.debugLine="If Msgbox2(\"¿Está seguro de que quiere borrar l";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro de que quiere borrar la secuencia \""+mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [mostCurrent._vvvvvvvvvv2._vvv1 /*int*/ ].Descripcion /*String*/ +"\"?"),BA.ObjectToCharSequence("Borrar secuencia"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 198;BA.debugLine="For Sec=Starter.SecuenciaActiva To Starter.Num";
{
final int step13 = 1;
final int limit13 = (int) (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ -2);
_sec = mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  ;
for (;_sec <= limit13 ;_sec = _sec + step13 ) {
 //BA.debugLineNum = 200;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Sec+1,Sec";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"CopiarSecuencias",(Object)(_sec+1),(Object)(_sec));
 }
};
 //BA.debugLineNum = 203;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias-1";
mostCurrent._vvvvvvvvvv2._vv0 /*int*/  = (int) (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ -1);
 //BA.debugLineNum = 204;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 205;BA.debugLine="DibujarPortada";
_vvvvvvvvvv5();
 };
 break; }
case 2: {
 //BA.debugLineNum = 208;BA.debugLine="If Starter.NumSecuencias==Starter.MaxSecuencias";
if (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ==mostCurrent._vvvvvvvvvv2._vv2 /*int*/ ) { 
 //BA.debugLineNum = 209;BA.debugLine="Msgbox(\"No es posible: Se ha alcanzado el máxi";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No es posible: Se ha alcanzado el máximo de secuencias soportadas por la aplicación."),BA.ObjectToCharSequence("Error al duplicar secuencia"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 212;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Starter.Se";
anywheresoftware.b4a.keywords.Common.CallSubNew3(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"CopiarSecuencias",(Object)(mostCurrent._vvvvvvvvvv2._vvv1 /*int*/ ),(Object)(mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ));
 //BA.debugLineNum = 213;BA.debugLine="Starter.Secuencia(Starter.NumSecuencias).Descr";
mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ].Descripcion /*String*/  = mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ].Descripcion /*String*/ +" (copia)";
 //BA.debugLineNum = 215;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias+1";
mostCurrent._vvvvvvvvvv2._vv0 /*int*/  = (int) (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ +1);
 //BA.debugLineNum = 216;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 217;BA.debugLine="DibujarPortada";
_vvvvvvvvvv5();
 };
 break; }
}
;
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static String  _botonpictograma_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _botonpulsado = null;
 //BA.debugLineNum = 222;BA.debugLine="Sub BotonPictograma_click";
 //BA.debugLineNum = 223;BA.debugLine="Dim BotonPulsado As Label";
_botonpulsado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 224;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 225;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 226;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv5.getObject()));
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _botonsalir_click() throws Exception{
 //BA.debugLineNum = 257;BA.debugLine="Sub BotonSalir_Click";
 //BA.debugLineNum = 258;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv5() throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
int _act = 0;
int _ultimalinea = 0;
String _textomanana = "";
 //BA.debugLineNum = 76;BA.debugLine="Sub DibujarPortada";
 //BA.debugLineNum = 78;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 79;BA.debugLine="Activity.LoadLayout(\"Portada\")";
mostCurrent._activity.LoadLayout("Portada",mostCurrent.activityBA);
 //BA.debugLineNum = 82;BA.debugLine="Dim SU As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 83;BA.debugLine="Do While SU.MeasureMultilineTextHeight(Pictorario";
while (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._pictorario.getObject()),BA.ObjectToCharSequence(mostCurrent._pictorario.getText()))>mostCurrent._pictorario.getHeight()) {
 //BA.debugLineNum = 84;BA.debugLine="Pictorario.TextSize=Pictorario.TextSize-1";
mostCurrent._pictorario.setTextSize((float) (mostCurrent._pictorario.getTextSize()-1));
 }
;
 //BA.debugLineNum = 87;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 88;BA.debugLine="Dim UltimaLinea=0 As Int";
_ultimalinea = (int) (0);
 //BA.debugLineNum = 90;BA.debugLine="For Act=0 To Starter.NumSecuencias-1";
{
final int step9 = 1;
final int limit9 = (int) (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ -1);
_act = (int) (0) ;
for (;_act <= limit9 ;_act = _act + step9 ) {
 //BA.debugLineNum = 92;BA.debugLine="PictogramaSecuencia(Act).Initialize(\"BotonPictog";
mostCurrent._vvvvvvvvvv7[_act].Initialize(mostCurrent.activityBA,"BotonPictograma");
 //BA.debugLineNum = 93;BA.debugLine="PictogramaSecuencia(Act).Tag=Act";
mostCurrent._vvvvvvvvvv7[_act].setTag((Object)(_act));
 //BA.debugLineNum = 94;BA.debugLine="PictogramaSecuencia(Act).Color=Colors.Transparen";
mostCurrent._vvvvvvvvvv7[_act].setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 95;BA.debugLine="PictogramaSecuencia(Act).SetBackgroundImage(Load";
mostCurrent._vvvvvvvvvv7[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [_act].pictograma /*String*/ +".png").getObject()));
 //BA.debugLineNum = 96;BA.debugLine="PanelScroll.Panel.AddView(PictogramaSecuencia(Ac";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvv7[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 98;BA.debugLine="EtiquetaSecuencia(Act).Initialize(\"TextoSecuenci";
mostCurrent._vvvvvvvvvv0[_act].Initialize(mostCurrent.activityBA,"TextoSecuencia");
 //BA.debugLineNum = 99;BA.debugLine="EtiquetaSecuencia(Act).Tag=Act";
mostCurrent._vvvvvvvvvv0[_act].setTag((Object)(_act));
 //BA.debugLineNum = 100;BA.debugLine="EtiquetaSecuencia(Act).Text=Starter.Secuencia(Ac";
mostCurrent._vvvvvvvvvv0[_act].setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [_act].Descripcion /*String*/ ));
 //BA.debugLineNum = 101;BA.debugLine="EtiquetaSecuencia(Act).TextColor=Colors.Black";
mostCurrent._vvvvvvvvvv0[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 102;BA.debugLine="EtiquetaSecuencia(Act).TextSize=16";
mostCurrent._vvvvvvvvvv0[_act].setTextSize((float) (16));
 //BA.debugLineNum = 103;BA.debugLine="EtiquetaSecuencia(Act).Gravity=Bit.Or(Gravity.CE";
mostCurrent._vvvvvvvvvv0[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.LEFT));
 //BA.debugLineNum = 104;BA.debugLine="PanelScroll.Panel.AddView(EtiquetaSecuencia(Act)";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvv0[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 106;BA.debugLine="If ( Starter.AplicacionProtegida==False ) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 107;BA.debugLine="EditarSecuencia(Act).Initialize(\"BotonEditar\")";
mostCurrent._vvvvvvvvvvv1[_act].Initialize(mostCurrent.activityBA,"BotonEditar");
 //BA.debugLineNum = 108;BA.debugLine="EditarSecuencia(Act).Tag=Act";
mostCurrent._vvvvvvvvvvv1[_act].setTag((Object)(_act));
 //BA.debugLineNum = 109;BA.debugLine="EditarSecuencia(Act).SetBackgroundImage(LoadBit";
mostCurrent._vvvvvvvvvvv1[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 110;BA.debugLine="PanelScroll.Panel.AddView(EditarSecuencia(Act),";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv1[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 113;BA.debugLine="UltimaLinea=PictogramaSecuencia(Act).Top+Pictogr";
_ultimalinea = (int) (mostCurrent._vvvvvvvvvv7[_act].getTop()+mostCurrent._vvvvvvvvvv7[_act].getHeight());
 }
};
 //BA.debugLineNum = 116;BA.debugLine="If Starter.ProximaAlarmaSeq>=0 Then 'Si hay una p";
if (mostCurrent._vvvvvvvvvv2._v0 /*int*/ >=0) { 
 //BA.debugLineNum = 117;BA.debugLine="ProximaAlarmaPict.Initialize(\"ProximaAlarmaPict\"";
mostCurrent._vvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ProximaAlarmaPict");
 //BA.debugLineNum = 118;BA.debugLine="ProximaAlarmaPict.SetBackgroundImage(LoadBitmap(";
mostCurrent._vvvvvvvvvvv2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].Pictograma /*String*/ +".png").getObject()));
 //BA.debugLineNum = 119;BA.debugLine="PanelScroll.Panel.AddView(ProximaAlarmaPict,100%";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv2.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 121;BA.debugLine="ProximaAlarma.Initialize(\"ProximaAlarma\")";
mostCurrent._vvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"ProximaAlarma");
 //BA.debugLineNum = 122;BA.debugLine="Dim TextoManana=\"\" As String";
_textomanana = "";
 //BA.debugLineNum = 123;BA.debugLine="If (Starter.ActividadSecuencia(Starter.ProximaAl";
if ((mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].hora_inicio /*int*/ *60+mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].minuto_inicio /*int*/ )<(anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow())*60+anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))) { 
 //BA.debugLineNum = 124;BA.debugLine="TextoManana=\"Mañana a las \"";
_textomanana = "Mañana a las ";
 }else {
 //BA.debugLineNum = 126;BA.debugLine="TextoManana=\"Hoy a las \"";
_textomanana = "Hoy a las ";
 };
 //BA.debugLineNum = 128;BA.debugLine="ProximaAlarma.Text=\"Próxima alarma:\"&CRLF&TextoM";
mostCurrent._vvvvvvvvvvv3.setText(BA.ObjectToCharSequence("Próxima alarma:"+anywheresoftware.b4a.keywords.Common.CRLF+_textomanana+_vvvvvvvvvvv4(mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].hora_inicio /*int*/ ,mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].minuto_inicio /*int*/ )+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._vvvvvvvvvv2._vvv2 /*javi.prieto.pictorario.starter._secuencia[]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ].Descripcion /*String*/ +" ➞ "+mostCurrent._vvvvvvvvvv2._vvv3 /*javi.prieto.pictorario.starter._actividad[][]*/ [mostCurrent._vvvvvvvvvv2._v0 /*int*/ ][mostCurrent._vvvvvvvvvv2._vv1 /*int*/ ].Descripcion /*String*/ ));
 //BA.debugLineNum = 129;BA.debugLine="ProximaAlarma.TextColor=Colors.Black";
mostCurrent._vvvvvvvvvvv3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 130;BA.debugLine="ProximaAlarma.TextSize=16";
mostCurrent._vvvvvvvvvvv3.setTextSize((float) (16));
 //BA.debugLineNum = 131;BA.debugLine="ProximaAlarma.Typeface=Typeface.CreateNew(Typefa";
mostCurrent._vvvvvvvvvvv3.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.CreateNew(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,anywheresoftware.b4a.keywords.Common.Typeface.STYLE_ITALIC));
 //BA.debugLineNum = 132;BA.debugLine="ProximaAlarma.Gravity=Bit.Or(Gravity.CENTER_VERT";
mostCurrent._vvvvvvvvvvv3.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.RIGHT));
 //BA.debugLineNum = 133;BA.debugLine="PanelScroll.Panel.AddView(ProximaAlarma,10dip,Pr";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),mostCurrent._vvvvvvvvvvv2.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 135;BA.debugLine="UltimaLinea=ProximaAlarma.Top+ProximaAlarma.Heig";
_ultimalinea = (int) (mostCurrent._vvvvvvvvvvv3.getTop()+mostCurrent._vvvvvvvvvvv3.getHeight());
 };
 //BA.debugLineNum = 138;BA.debugLine="If ( Starter.AplicacionProtegida==False ) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 139;BA.debugLine="BotonCrear.Initialize(\"BotonCrear\")";
mostCurrent._vvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"BotonCrear");
 //BA.debugLineNum = 140;BA.debugLine="BotonCrear.Text=\"Crear Secuencia\"";
mostCurrent._vvvvvvvvvvv5.setText(BA.ObjectToCharSequence("Crear Secuencia"));
 //BA.debugLineNum = 141;BA.debugLine="BotonCrear.TextSize=20";
mostCurrent._vvvvvvvvvvv5.setTextSize((float) (20));
 //BA.debugLineNum = 142;BA.debugLine="BotonCrear.Gravity=Bit.Or(Gravity.CENTER_VERTICA";
mostCurrent._vvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 143;BA.debugLine="BotonCrear.TextColor=Colors.Black";
mostCurrent._vvvvvvvvvvv5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 144;BA.debugLine="PanelScroll.Panel.AddView(BotonCrear,5dip,Ultima";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (_ultimalinea+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 146;BA.debugLine="If Starter.NumSecuencias==Starter.MaxSecuencias";
if (mostCurrent._vvvvvvvvvv2._vv0 /*int*/ ==mostCurrent._vvvvvvvvvv2._vv2 /*int*/ ) { 
 //BA.debugLineNum = 147;BA.debugLine="BotonCrear.Enabled=False 'Si llegamos al máximo";
mostCurrent._vvvvvvvvvvv5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 150;BA.debugLine="BotonConfiguracion.Initialize(\"BotonConfiguracio";
mostCurrent._vvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"BotonConfiguracion");
 //BA.debugLineNum = 151;BA.debugLine="BotonConfiguracion.Text=\"Configuración\"";
mostCurrent._vvvvvvvvvvv6.setText(BA.ObjectToCharSequence("Configuración"));
 //BA.debugLineNum = 152;BA.debugLine="BotonConfiguracion.TextSize=20";
mostCurrent._vvvvvvvvvvv6.setTextSize((float) (20));
 //BA.debugLineNum = 153;BA.debugLine="BotonConfiguracion.Gravity=Bit.Or(Gravity.CENTER";
mostCurrent._vvvvvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 154;BA.debugLine="BotonConfiguracion.TextColor=Colors.Black";
mostCurrent._vvvvvvvvvvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 155;BA.debugLine="PanelScroll.Panel.AddView(BotonConfiguracion,5di";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (mostCurrent._vvvvvvvvvvv5.getTop()+mostCurrent._vvvvvvvvvvv5.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 157;BA.debugLine="BotonAcercaDe.Initialize(\"BotonAcercaDe\")";
mostCurrent._vvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"BotonAcercaDe");
 //BA.debugLineNum = 158;BA.debugLine="BotonAcercaDe.Text=\"Acerca de Pictorario\"";
mostCurrent._vvvvvvvvvvv7.setText(BA.ObjectToCharSequence("Acerca de Pictorario"));
 //BA.debugLineNum = 159;BA.debugLine="BotonAcercaDe.TextSize=20";
mostCurrent._vvvvvvvvvvv7.setTextSize((float) (20));
 //BA.debugLineNum = 160;BA.debugLine="BotonAcercaDe.Gravity=Bit.Or(Gravity.CENTER_VERT";
mostCurrent._vvvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 161;BA.debugLine="BotonAcercaDe.TextColor=Colors.Black";
mostCurrent._vvvvvvvvvvv7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 162;BA.debugLine="PanelScroll.Panel.AddView(BotonAcercaDe,5dip,Bot";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (mostCurrent._vvvvvvvvvvv6.getTop()+mostCurrent._vvvvvvvvvvv6.getHeight()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 164;BA.debugLine="UltimaLinea=BotonAcercaDe.Top+BotonConfiguracion";
_ultimalinea = (int) (mostCurrent._vvvvvvvvvvv7.getTop()+mostCurrent._vvvvvvvvvvv6.getHeight());
 };
 //BA.debugLineNum = 167;BA.debugLine="BotonSalir.Initialize(\"BotonSalir\")";
mostCurrent._vvvvvvvvvvv0.Initialize(mostCurrent.activityBA,"BotonSalir");
 //BA.debugLineNum = 168;BA.debugLine="BotonSalir.Text=\"Salir\"";
mostCurrent._vvvvvvvvvvv0.setText(BA.ObjectToCharSequence("Salir"));
 //BA.debugLineNum = 169;BA.debugLine="BotonSalir.TextSize=20";
mostCurrent._vvvvvvvvvvv0.setTextSize((float) (20));
 //BA.debugLineNum = 170;BA.debugLine="BotonSalir.Gravity=Bit.Or(Gravity.CENTER_VERTICAL";
mostCurrent._vvvvvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 171;BA.debugLine="BotonSalir.TextColor=Colors.Black";
mostCurrent._vvvvvvvvvvv0.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 172;BA.debugLine="PanelScroll.Panel.AddView(BotonSalir,5dip,UltimaL";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvv0.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_ultimalinea,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 174;BA.debugLine="PanelScroll.Panel.Height=BotonSalir.Top+BotonSali";
mostCurrent._panelscroll.getPanel().setHeight((int) (mostCurrent._vvvvvvvvvvv0.getTop()+mostCurrent._vvvvvvvvvvv0.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 176;BA.debugLine="If ( Starter.AplicacionProtegida==True ) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 177;BA.debugLine="BotonCandado.Initialize(\"BotonCandado\")";
mostCurrent._vvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"BotonCandado");
 //BA.debugLineNum = 178;BA.debugLine="BotonCandado.SetBackgroundImage(LoadBitmap(File.";
mostCurrent._vvvvvvvvvvvv1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"candado.png").getObject()));
 //BA.debugLineNum = 179;BA.debugLine="Activity.AddView(BotonCandado,100%X-50dip,100%Y-";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvv1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv4(int _hora,int _minutos) throws Exception{
String _salida = "";
int _horamodificada = 0;
 //BA.debugLineNum = 299;BA.debugLine="Sub EscribirHora(Hora As Int, Minutos As Int) As S";
 //BA.debugLineNum = 300;BA.debugLine="Dim Salida As String";
_salida = "";
 //BA.debugLineNum = 301;BA.debugLine="Dim HoraModificada As Int";
_horamodificada = 0;
 //BA.debugLineNum = 302;BA.debugLine="If (Starter.Formato24h==False And Hora>11) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False && _hora>11)) { 
 //BA.debugLineNum = 303;BA.debugLine="HoraModificada=Hora-12";
_horamodificada = (int) (_hora-12);
 }else {
 //BA.debugLineNum = 305;BA.debugLine="HoraModificada=Hora";
_horamodificada = _hora;
 };
 //BA.debugLineNum = 307;BA.debugLine="Salida=HoraModificada&\":\"&NumberFormat(Minutos,2,";
_salida = BA.NumberToString(_horamodificada)+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutos,(int) (2),(int) (0));
 //BA.debugLineNum = 308;BA.debugLine="If Starter.Formato24h==False Then";
if (mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 309;BA.debugLine="If Hora<12 Then";
if (_hora<12) { 
 //BA.debugLineNum = 310;BA.debugLine="Salida=Salida&\" a.m.\"";
_salida = _salida+" a.m.";
 }else {
 //BA.debugLineNum = 312;BA.debugLine="Salida=Salida&\" p.m.\"";
_salida = _salida+" p.m.";
 };
 };
 //BA.debugLineNum = 315;BA.debugLine="Return Salida";
if (true) return _salida;
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 28;BA.debugLine="Dim PictogramaSecuencia(Starter.MaxSecuencias) As";
mostCurrent._vvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv2._vv2 /*int*/ ];
{
int d0 = mostCurrent._vvvvvvvvvv7.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvv7[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 29;BA.debugLine="Dim EditarSecuencia(Starter.MaxSecuencias) As Lab";
mostCurrent._vvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv2._vv2 /*int*/ ];
{
int d0 = mostCurrent._vvvvvvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 30;BA.debugLine="Dim EtiquetaSecuencia(Starter.MaxActividades) As";
mostCurrent._vvvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv2._vv3 /*int*/ ];
{
int d0 = mostCurrent._vvvvvvvvvv0.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvv0[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 32;BA.debugLine="Dim ProximaAlarma As Label";
mostCurrent._vvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim ProximaAlarmaPict As Label";
mostCurrent._vvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim BotonAcercaDe As Button";
mostCurrent._vvvvvvvvvvv7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim BotonCrear As Button";
mostCurrent._vvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim BotonSalir As Button";
mostCurrent._vvvvvvvvvvv0 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim BotonConfiguracion As Button";
mostCurrent._vvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim BotonCandado As Button";
mostCurrent._vvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private PanelScroll As ScrollView";
mostCurrent._panelscroll = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Pictorario As Label";
mostCurrent._pictorario = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim ContadorCandado=0 As Int";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
b4a.example.versione06._process_globals();
main._process_globals();
seleccionpictogramas._process_globals();
visualizacion._process_globals();
acercade._process_globals();
configuracion._process_globals();
arranqueautomatico._process_globals();
avisos._process_globals();
starter._process_globals();
configurarsecuencia._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _proximaalarma_click() throws Exception{
 //BA.debugLineNum = 261;BA.debugLine="Sub ProximaAlarma_Click";
 //BA.debugLineNum = 262;BA.debugLine="If ( Starter.AplicacionProtegida==False ) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 263;BA.debugLine="Starter.SecuenciaActiva=Starter.ProximaAlarmaSeq";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = mostCurrent._vvvvvvvvvv2._v0 /*int*/ ;
 //BA.debugLineNum = 264;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()));
 };
 //BA.debugLineNum = 266;BA.debugLine="End Sub";
return "";
}
public static String  _proximaalarmapict_click() throws Exception{
 //BA.debugLineNum = 268;BA.debugLine="Sub ProximaAlarmaPict_Click";
 //BA.debugLineNum = 269;BA.debugLine="If ( Starter.AplicacionProtegida==False ) Then";
if ((mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 270;BA.debugLine="Starter.SecuenciaActiva=Starter.ProximaAlarmaSeq";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = mostCurrent._vvvvvvvvvv2._v0 /*int*/ ;
 //BA.debugLineNum = 271;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()));
 };
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _textosecuencia_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _etiquetapulsada = null;
 //BA.debugLineNum = 229;BA.debugLine="Sub TextoSecuencia_click";
 //BA.debugLineNum = 230;BA.debugLine="Dim EtiquetaPulsada As Label";
_etiquetapulsada = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 231;BA.debugLine="EtiquetaPulsada=Sender";
_etiquetapulsada.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 232;BA.debugLine="Starter.SecuenciaActiva=EtiquetaPulsada.Tag";
mostCurrent._vvvvvvvvvv2._vvv1 /*int*/  = (int)(BA.ObjectToNumber(_etiquetapulsada.getTag()));
 //BA.debugLineNum = 233;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv5.getObject()));
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
}
