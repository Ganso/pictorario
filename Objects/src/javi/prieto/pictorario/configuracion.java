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

public class configuracion extends Activity implements B4AActivity{
	public static configuracion mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.configuracion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (configuracion).");
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
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.configuracion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.configuracion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (configuracion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (configuracion) Resume **");
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
		return configuracion.class;
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
        BA.LogInfo("** Activity (configuracion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            configuracion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (configuracion) Resume **");
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
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _vvvvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _vvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _titulo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _panelscroll = null;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv4 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv5 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv6 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvvvvv0 = null;
public javi.prieto.pictorario.avisos _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.starter _vvvvvvvvvv2 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvvv3 = null;
public javi.prieto.pictorario.httputils2service _vvvvvvvvvv4 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activaralarmascheck_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub ActivarAlarmasCheck_CheckedChange(Checked As B";
 //BA.debugLineNum = 192;BA.debugLine="Starter.AlarmasActivadas=ActivarAlarmasCheck.Chec";
mostCurrent._vvvvvvvvvv2._vvv6 /*boolean*/  = mostCurrent._vvvvvvvvvvvvvvvv2.getChecked();
 //BA.debugLineNum = 193;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _separador = 0;
int _altura = 0;
int _tambotonescolores = 0;
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 36;BA.debugLine="Activity.LoadLayout(\"Configuracion\")";
mostCurrent._activity.LoadLayout("Configuracion",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="Dim Separador=10dip,Altura=80dip As Int";
_separador = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
_altura = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80));
 //BA.debugLineNum = 40;BA.debugLine="ActivarAlarmasCheck.Initialize(\"ActivarAlarmasChe";
mostCurrent._vvvvvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ActivarAlarmasCheck");
 //BA.debugLineNum = 41;BA.debugLine="ActivarAlarmasIcono.Initialize(\"ActivarAlarmasIco";
mostCurrent._vvvvvvvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"ActivarAlarmasIcono");
 //BA.debugLineNum = 42;BA.debugLine="ActivarAlarmasLabel.Initialize(\"ActivarAlarmasLab";
mostCurrent._vvvvvvvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"ActivarAlarmasLabel");
 //BA.debugLineNum = 43;BA.debugLine="ProtegerVisualizacionCheck.Initialize(\"ProtegerVi";
mostCurrent._vvvvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"ProtegerVisualizacionCheck");
 //BA.debugLineNum = 44;BA.debugLine="ProtegerVisualizacionIcono.Initialize(\"ProtegerVi";
mostCurrent._vvvvvvvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"ProtegerVisualizacionIcono");
 //BA.debugLineNum = 45;BA.debugLine="ProtegerVisualizacionLabel.Initialize(\"ProtegerVi";
mostCurrent._vvvvvvvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"ProtegerVisualizacionLabel");
 //BA.debugLineNum = 46;BA.debugLine="FormatoHorasButton.Initialize(\"FormatoHorasButton";
mostCurrent._vvvvvvvvvvvvvvvv0.Initialize(mostCurrent.activityBA,"FormatoHorasButton");
 //BA.debugLineNum = 47;BA.debugLine="FormatoHorasLabel.Initialize(\"FormatoHorasLabel\")";
mostCurrent._vvvvvvvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"FormatoHorasLabel");
 //BA.debugLineNum = 48;BA.debugLine="ColoresRelojLabel.Initialize(\"ColoresRelojLabel\")";
mostCurrent._vvvvvvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"ColoresRelojLabel");
 //BA.debugLineNum = 49;BA.debugLine="BotonHoras.Initialize(\"BotonHoras\")";
mostCurrent._vvvvvvvvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"BotonHoras");
 //BA.debugLineNum = 50;BA.debugLine="BotonMinutos.Initialize(\"BotonMinutos\")";
mostCurrent._vvvvvvvvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"BotonMinutos");
 //BA.debugLineNum = 51;BA.debugLine="BotonSegundos.Initialize(\"BotonSegundos\")";
mostCurrent._vvvvvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"BotonSegundos");
 //BA.debugLineNum = 52;BA.debugLine="BotonReiniciar.Initialize(\"BotonReiniciar\")";
mostCurrent._vvvvvvvvvvvvvvvvv6.Initialize(mostCurrent.activityBA,"BotonReiniciar");
 //BA.debugLineNum = 53;BA.debugLine="BotonVolver.Initialize(\"BotonVolver\")";
mostCurrent._vvvvvvvvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"BotonVolver");
 //BA.debugLineNum = 55;BA.debugLine="ActivarAlarmasLabel.Text=\"Activar alarmas\"";
mostCurrent._vvvvvvvvvvvvvvvv4.setText(BA.ObjectToCharSequence("Activar alarmas"));
 //BA.debugLineNum = 56;BA.debugLine="ActivarAlarmasLabel.TextSize=16";
mostCurrent._vvvvvvvvvvvvvvvv4.setTextSize((float) (16));
 //BA.debugLineNum = 57;BA.debugLine="ActivarAlarmasLabel.Gravity=Gravity.CENTER_VERTIC";
mostCurrent._vvvvvvvvvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 59;BA.debugLine="ProtegerVisualizacionLabel.Text=\"Proteger aplicac";
mostCurrent._vvvvvvvvvvvvvvvv7.setText(BA.ObjectToCharSequence("Proteger aplicación"));
 //BA.debugLineNum = 60;BA.debugLine="ProtegerVisualizacionLabel.TextSize=16";
mostCurrent._vvvvvvvvvvvvvvvv7.setTextSize((float) (16));
 //BA.debugLineNum = 61;BA.debugLine="ProtegerVisualizacionLabel.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 63;BA.debugLine="FormatoHorasLabel.Text=\"Formato horario\"";
mostCurrent._vvvvvvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("Formato horario"));
 //BA.debugLineNum = 64;BA.debugLine="FormatoHorasLabel.TextSize=16";
mostCurrent._vvvvvvvvvvvvvvvvv1.setTextSize((float) (16));
 //BA.debugLineNum = 65;BA.debugLine="FormatoHorasLabel.Gravity=Gravity.CENTER_VERTICAL";
mostCurrent._vvvvvvvvvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 67;BA.debugLine="ActivarAlarmasCheck.Checked=Starter.AlarmasActiva";
mostCurrent._vvvvvvvvvvvvvvvv2.setChecked(mostCurrent._vvvvvvvvvv2._vvv6 /*boolean*/ );
 //BA.debugLineNum = 68;BA.debugLine="ActivarAlarmasCheck.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 70;BA.debugLine="ProtegerVisualizacionCheck.Checked=Starter.Aplica";
mostCurrent._vvvvvvvvvvvvvvvv5.setChecked(mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ );
 //BA.debugLineNum = 71;BA.debugLine="ProtegerVisualizacionCheck.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvv5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 73;BA.debugLine="ColoresRelojLabel.Text=\"Colores del reloj (horari";
mostCurrent._vvvvvvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence("Colores del reloj (horario, minutero y segundero)"));
 //BA.debugLineNum = 74;BA.debugLine="ColoresRelojLabel.TextSize=16";
mostCurrent._vvvvvvvvvvvvvvvvv2.setTextSize((float) (16));
 //BA.debugLineNum = 75;BA.debugLine="ColoresRelojLabel.Gravity=Gravity.CENTER_VERTICAL";
mostCurrent._vvvvvvvvvvvvvvvvv2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 77;BA.debugLine="BotonHoras.Color=Starter.ColorHoras";
mostCurrent._vvvvvvvvvvvvvvvvv3.setColor(mostCurrent._vvvvvvvvvv2._vvvv1 /*int*/ );
 //BA.debugLineNum = 78;BA.debugLine="BotonMinutos.Color=Starter.ColorMinutos";
mostCurrent._vvvvvvvvvvvvvvvvv4.setColor(mostCurrent._vvvvvvvvvv2._vvvv2 /*int*/ );
 //BA.debugLineNum = 79;BA.debugLine="BotonSegundos.Color=Starter.ColorSegundos";
mostCurrent._vvvvvvvvvvvvvvvvv5.setColor(mostCurrent._vvvvvvvvvv2._vvvv3 /*int*/ );
 //BA.debugLineNum = 81;BA.debugLine="If Starter.Formato24h==True Then";
if (mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 82;BA.debugLine="FormatoHorasButton.Text=\"24 horas\"";
mostCurrent._vvvvvvvvvvvvvvvv0.setText(BA.ObjectToCharSequence("24 horas"));
 }else {
 //BA.debugLineNum = 84;BA.debugLine="FormatoHorasButton.Text=\"12 horas\"";
mostCurrent._vvvvvvvvvvvvvvvv0.setText(BA.ObjectToCharSequence("12 horas"));
 };
 //BA.debugLineNum = 86;BA.debugLine="FormatoHorasButton.TextSize=20";
mostCurrent._vvvvvvvvvvvvvvvv0.setTextSize((float) (20));
 //BA.debugLineNum = 87;BA.debugLine="FormatoHorasButton.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvv0.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 89;BA.debugLine="BotonReiniciar.Text=\"Reiniciar configuración\"";
mostCurrent._vvvvvvvvvvvvvvvvv6.setText(BA.ObjectToCharSequence("Reiniciar configuración"));
 //BA.debugLineNum = 90;BA.debugLine="BotonReiniciar.TextSize=20";
mostCurrent._vvvvvvvvvvvvvvvvv6.setTextSize((float) (20));
 //BA.debugLineNum = 91;BA.debugLine="BotonReiniciar.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvvv6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 93;BA.debugLine="BotonVolver.Text=\"Volver a la portada\"";
mostCurrent._vvvvvvvvvvvvvvvvv7.setText(BA.ObjectToCharSequence("Volver a la portada"));
 //BA.debugLineNum = 94;BA.debugLine="BotonVolver.TextSize=20";
mostCurrent._vvvvvvvvvvvvvvvvv7.setTextSize((float) (20));
 //BA.debugLineNum = 95;BA.debugLine="BotonVolver.Gravity=Gravity.CENTER";
mostCurrent._vvvvvvvvvvvvvvvvv7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 97;BA.debugLine="ActivarAlarmasIcono.SetBackgroundImage(LoadBitmap";
mostCurrent._vvvvvvvvvvvvvvvv3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"alarma.png").getObject()));
 //BA.debugLineNum = 98;BA.debugLine="ProtegerVisualizacionIcono.SetBackgroundImage(Loa";
mostCurrent._vvvvvvvvvvvvvvvv6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"llave.png").getObject()));
 //BA.debugLineNum = 100;BA.debugLine="PanelScroll.Panel.AddView(ActivarAlarmasLabel,10d";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*_altura-4*_separador),_altura);
 //BA.debugLineNum = 101;BA.debugLine="PanelScroll.Panel.AddView(ActivarAlarmasIcono,Act";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv3.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvv4.getLeft()+mostCurrent._vvvvvvvvvvvvvvvv4.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvv4.getTop(),_altura,_altura);
 //BA.debugLineNum = 102;BA.debugLine="PanelScroll.Panel.AddView(ActivarAlarmasCheck,Act";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv2.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvv3.getLeft()+mostCurrent._vvvvvvvvvvvvvvvv3.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvv3.getTop(),_altura,_altura);
 //BA.debugLineNum = 104;BA.debugLine="PanelScroll.Panel.AddView(ProtegerVisualizacionLa";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv7.getObject()),mostCurrent._vvvvvvvvvvvvvvvv4.getLeft(),(int) (mostCurrent._vvvvvvvvvvvvvvvv4.getTop()+mostCurrent._vvvvvvvvvvvvvvvv4.getHeight()+_separador),mostCurrent._vvvvvvvvvvvvvvvv4.getWidth(),mostCurrent._vvvvvvvvvvvvvvvv4.getHeight());
 //BA.debugLineNum = 105;BA.debugLine="PanelScroll.Panel.AddView(ProtegerVisualizacionIc";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv6.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvv7.getLeft()+mostCurrent._vvvvvvvvvvvvvvvv7.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvv7.getTop(),_altura,_altura);
 //BA.debugLineNum = 106;BA.debugLine="PanelScroll.Panel.AddView(ProtegerVisualizacionCh";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv5.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvv6.getLeft()+mostCurrent._vvvvvvvvvvvvvvvv6.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvv7.getTop(),_altura,mostCurrent._vvvvvvvvvvvvvvvv7.getHeight());
 //BA.debugLineNum = 108;BA.debugLine="PanelScroll.Panel.AddView(FormatoHorasLabel,Prote";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv1.getObject()),mostCurrent._vvvvvvvvvvvvvvvv7.getLeft(),(int) (mostCurrent._vvvvvvvvvvvvvvvv7.getTop()+mostCurrent._vvvvvvvvvvvvvvvv7.getHeight()+_separador),mostCurrent._vvvvvvvvvvvvvvvv7.getWidth(),mostCurrent._vvvvvvvvvvvvvvvv7.getHeight());
 //BA.debugLineNum = 109;BA.debugLine="PanelScroll.Panel.AddView(FormatoHorasButton,Prot";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv0.getObject()),mostCurrent._vvvvvvvvvvvvvvvv6.getLeft(),mostCurrent._vvvvvvvvvvvvvvvvv1.getTop(),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._vvvvvvvvvvvvvvvv6.getLeft()-_separador),mostCurrent._vvvvvvvvvvvvvvvvv1.getHeight());
 //BA.debugLineNum = 111;BA.debugLine="PanelScroll.Panel.AddView(ColoresRelojLabel,Forma";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv2.getObject()),mostCurrent._vvvvvvvvvvvvvvvvv1.getLeft(),(int) (mostCurrent._vvvvvvvvvvvvvvvvv1.getTop()+mostCurrent._vvvvvvvvvvvvvvvvv1.getHeight()+_separador),mostCurrent._vvvvvvvvvvvvvvvvv1.getWidth(),mostCurrent._vvvvvvvvvvvvvvvvv1.getHeight());
 //BA.debugLineNum = 113;BA.debugLine="Dim TamBotonesColores=(100%X-ColoresRelojLabel.Le";
_tambotonescolores = (int) ((anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._vvvvvvvvvvvvvvvvv2.getLeft()-mostCurrent._vvvvvvvvvvvvvvvvv2.getWidth()-4*_separador)/(double)3);
 //BA.debugLineNum = 114;BA.debugLine="PanelScroll.Panel.AddView(BotonHoras,ColoresReloj";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv3.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvvv2.getLeft()+mostCurrent._vvvvvvvvvvvvvvvvv2.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvvv2.getTop(),_tambotonescolores,_tambotonescolores);
 //BA.debugLineNum = 115;BA.debugLine="PanelScroll.Panel.AddView(BotonMinutos,BotonHoras";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv4.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvvv3.getLeft()+mostCurrent._vvvvvvvvvvvvvvvvv3.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvvv2.getTop(),_tambotonescolores,_tambotonescolores);
 //BA.debugLineNum = 116;BA.debugLine="PanelScroll.Panel.AddView(BotonSegundos,BotonMinu";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv5.getObject()),(int) (mostCurrent._vvvvvvvvvvvvvvvvv4.getLeft()+mostCurrent._vvvvvvvvvvvvvvvvv4.getWidth()+_separador),mostCurrent._vvvvvvvvvvvvvvvvv2.getTop(),_tambotonescolores,_tambotonescolores);
 //BA.debugLineNum = 118;BA.debugLine="PanelScroll.Panel.AddView(BotonReiniciar,Separado";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv6.getObject()),_separador,(int) (mostCurrent._vvvvvvvvvvvvvvvvv2.getTop()+mostCurrent._vvvvvvvvvvvvvvvvv2.getHeight()+_separador),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*_separador),_altura);
 //BA.debugLineNum = 120;BA.debugLine="PanelScroll.Panel.AddView(BotonVolver,Separador,B";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvv7.getObject()),_separador,(int) (mostCurrent._vvvvvvvvvvvvvvvvv6.getTop()+mostCurrent._vvvvvvvvvvvvvvvvv6.getHeight()+_separador),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-2*_separador),_altura);
 //BA.debugLineNum = 122;BA.debugLine="PanelScroll.Panel.Height=BotonVolver.Top+BotonVol";
mostCurrent._panelscroll.getPanel().setHeight((int) (mostCurrent._vvvvvvvvvvvvvvvvv7.getTop()+mostCurrent._vvvvvvvvvvvvvvvvv7.getHeight()+_separador));
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.configuracion parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.configuracion parent;
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
 //BA.debugLineNum = 135;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 136;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _botonhoras_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog _dialogocolor = null;
int _resultado = 0;
 //BA.debugLineNum = 148;BA.debugLine="Sub BotonHoras_Click";
 //BA.debugLineNum = 149;BA.debugLine="Dim DialogoColor As ColorPickerDialog";
_dialogocolor = new anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog();
 //BA.debugLineNum = 150;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 152;BA.debugLine="DialogoColor.RGB=Starter.ColorHoras";
_dialogocolor.setRGB(mostCurrent._vvvvvvvvvv2._vvvv1 /*int*/ );
 //BA.debugLineNum = 153;BA.debugLine="Resultado=DialogoColor.Show(\"Color del horario\",\"";
_resultado = _dialogocolor.Show("Color del horario","Seleccionar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 154;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 155;BA.debugLine="Starter.ColorHoras=DialogoColor.RGB";
mostCurrent._vvvvvvvvvv2._vvvv1 /*int*/  = _dialogocolor.getRGB();
 //BA.debugLineNum = 156;BA.debugLine="BotonHoras.Color=DialogoColor.RGB";
mostCurrent._vvvvvvvvvvvvvvvvv3.setColor(_dialogocolor.getRGB());
 //BA.debugLineNum = 157;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 };
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _botonminutos_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog _dialogocolor = null;
int _resultado = 0;
 //BA.debugLineNum = 161;BA.debugLine="Sub BotonMinutos_Click";
 //BA.debugLineNum = 162;BA.debugLine="Dim DialogoColor As ColorPickerDialog";
_dialogocolor = new anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog();
 //BA.debugLineNum = 163;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 165;BA.debugLine="DialogoColor.RGB=Starter.ColorMinutos";
_dialogocolor.setRGB(mostCurrent._vvvvvvvvvv2._vvvv2 /*int*/ );
 //BA.debugLineNum = 166;BA.debugLine="Resultado=DialogoColor.Show(\"Color del minutero\",";
_resultado = _dialogocolor.Show("Color del minutero","Seleccionar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 167;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 168;BA.debugLine="Starter.ColorMinutos=DialogoColor.RGB";
mostCurrent._vvvvvvvvvv2._vvvv2 /*int*/  = _dialogocolor.getRGB();
 //BA.debugLineNum = 169;BA.debugLine="BotonMinutos.Color=DialogoColor.RGB";
mostCurrent._vvvvvvvvvvvvvvvvv4.setColor(_dialogocolor.getRGB());
 //BA.debugLineNum = 170;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 };
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _botonreiniciar_click() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Sub BotonReiniciar_Click";
 //BA.debugLineNum = 207;BA.debugLine="If Msgbox2(\"Se borrarán todas las secuencias crea";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se borrarán todas las secuencias creadas y se dejará solo la de ejemplo, y se reiniciará toda la configuración."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"¿Está seguro de que desea hacer esto?"),BA.ObjectToCharSequence("Borrar toda la configuración"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 209;BA.debugLine="CallSub(Starter,\"Inicializar_Con_Ejemplo\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Inicializar_Con_Ejemplo");
 //BA.debugLineNum = 210;BA.debugLine="CallSub(Starter,\"BorrarPictogramas\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"BorrarPictogramas");
 //BA.debugLineNum = 211;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 212;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _botonsegundos_click() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog _dialogocolor = null;
int _resultado = 0;
 //BA.debugLineNum = 174;BA.debugLine="Sub BotonSegundos_Click";
 //BA.debugLineNum = 175;BA.debugLine="Dim DialogoColor As ColorPickerDialog";
_dialogocolor = new anywheresoftware.b4a.agraham.dialogs.InputDialog.ColorPickerDialog();
 //BA.debugLineNum = 176;BA.debugLine="Dim Resultado As Int";
_resultado = 0;
 //BA.debugLineNum = 178;BA.debugLine="DialogoColor.RGB=Starter.ColorSegundos";
_dialogocolor.setRGB(mostCurrent._vvvvvvvvvv2._vvvv3 /*int*/ );
 //BA.debugLineNum = 179;BA.debugLine="Resultado=DialogoColor.Show(\"Color del segundero\"";
_resultado = _dialogocolor.Show("Color del segundero","Seleccionar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 180;BA.debugLine="If Resultado=DialogResponse.POSITIVE Then";
if (_resultado==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 181;BA.debugLine="Starter.ColorSegundos=DialogoColor.RGB";
mostCurrent._vvvvvvvvvv2._vvvv3 /*int*/  = _dialogocolor.getRGB();
 //BA.debugLineNum = 182;BA.debugLine="BotonSegundos.Color=DialogoColor.RGB";
mostCurrent._vvvvvvvvvvvvvvvvv5.setColor(_dialogocolor.getRGB());
 //BA.debugLineNum = 183;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 };
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _botonvolver_click() throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub BotonVolver_Click";
 //BA.debugLineNum = 188;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _formatohorasbutton_click() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub FormatoHorasButton_Click";
 //BA.debugLineNum = 197;BA.debugLine="Starter.Formato24h=Not(Starter.Formato24h)";
mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/  = anywheresoftware.b4a.keywords.Common.Not(mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/ );
 //BA.debugLineNum = 198;BA.debugLine="If Starter.Formato24h==True Then";
if (mostCurrent._vvvvvvvvvv2._vvv0 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 199;BA.debugLine="FormatoHorasButton.Text=\"24 horas\"";
mostCurrent._vvvvvvvvvvvvvvvv0.setText(BA.ObjectToCharSequence("24 horas"));
 }else {
 //BA.debugLineNum = 201;BA.debugLine="FormatoHorasButton.Text=\"12 horas\"";
mostCurrent._vvvvvvvvvvvvvvvv0.setText(BA.ObjectToCharSequence("12 horas"));
 };
 //BA.debugLineNum = 203;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private ActivarAlarmasCheck As CheckBox";
mostCurrent._vvvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private ActivarAlarmasIcono As Label";
mostCurrent._vvvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private ActivarAlarmasLabel As Label";
mostCurrent._vvvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private ProtegerVisualizacionCheck As CheckBox";
mostCurrent._vvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private ProtegerVisualizacionIcono As Label";
mostCurrent._vvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private ProtegerVisualizacionLabel As Label";
mostCurrent._vvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private Titulo As Label";
mostCurrent._titulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private FormatoHorasButton As Button";
mostCurrent._vvvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private FormatoHorasLabel As Label";
mostCurrent._vvvvvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private ColoresRelojLabel As Label";
mostCurrent._vvvvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private BotonHoras As Button";
mostCurrent._vvvvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private BotonMinutos As Button";
mostCurrent._vvvvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private BotonSegundos As Button";
mostCurrent._vvvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private BotonReiniciar As Button";
mostCurrent._vvvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private BotonVolver As Button";
mostCurrent._vvvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private PanelScroll As ScrollView";
mostCurrent._panelscroll = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _protegervisualizacioncheck_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub ProtegerVisualizacionCheck_CheckedChange(Check";
 //BA.debugLineNum = 141;BA.debugLine="Starter.AplicacionProtegida=ProtegerVisualizacion";
mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/  = mostCurrent._vvvvvvvvvvvvvvvv5.getChecked();
 //BA.debugLineNum = 142;BA.debugLine="If Starter.AplicacionProtegida==True Then";
if (mostCurrent._vvvvvvvvvv2._vvv7 /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 143;BA.debugLine="Msgbox2(\"Para liberar la protección, hacer:\"&CRL";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Para liberar la protección, hacer:"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"UNA PULSACIÓN CORTA"+anywheresoftware.b4a.keywords.Common.CRLF+"  seguida de"+anywheresoftware.b4a.keywords.Common.CRLF+"UNA PULSACIÓN LARGA"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Sobre el icono del candado del resto de pantallas."),BA.ObjectToCharSequence("IMPORTANTE:"+anywheresoftware.b4a.keywords.Common.CRLF+"Aplicación protegida"),"Aceptar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"candado.png").getObject()),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 145;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv2.getObject()),"Guardar_Configuracion");
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
}
