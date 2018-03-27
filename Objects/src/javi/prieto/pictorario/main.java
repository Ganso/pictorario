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
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
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
        if (_activity == null) //workaround for emulator bug (Issue 2423)
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
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _paneldescripcion = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreloj = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagenpictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondopictograma = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopictograma = null;
public static int _maxactividades = 0;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _pantalla = null;
public static float _centrox = 0f;
public static float _centroy = 0f;
public static float _radio = 0f;
public static int _minhora = 0;
public static int _maxhora = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[] _boton = null;
public javi.prieto.pictorario.main._actividad[] _misactividades = null;
public javi.prieto.pictorario.main._tablero _mitablero = null;
public static int _totalactividades = 0;
public static int[] _colorespastel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cambiarvista = null;
public javi.prieto.pictorario.starter _starter = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static class _actividad{
public boolean IsInitialized;
public int hora_inicio;
public int minuto_inicio;
public int hora_fin;
public int minuto_fin;
public String pictograma;
public String descripcion;
public int color;
public void Initialize() {
IsInitialized = true;
hora_inicio = 0;
minuto_inicio = 0;
hora_fin = 0;
minuto_fin = 0;
pictograma = "";
descripcion = "";
color = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _tablero{
public boolean IsInitialized;
public int tipo;
public int indicar_hora;
public float tam_icono;
public void Initialize() {
IsInitialized = true;
tipo = 0;
indicar_hora = 0;
tam_icono = 0f;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 65;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
mostCurrent._activity.LoadLayout("RelojActividades",mostCurrent.activityBA);
 //BA.debugLineNum = 67;BA.debugLine="MiTablero.Initialize";
mostCurrent._mitablero.Initialize();
 //BA.debugLineNum = 68;BA.debugLine="MiTablero.tipo=3";
mostCurrent._mitablero.tipo = (int) (3);
 //BA.debugLineNum = 69;BA.debugLine="MiTablero.indicar_hora=1";
mostCurrent._mitablero.indicar_hora = (int) (1);
 //BA.debugLineNum = 70;BA.debugLine="MiTablero.tam_icono=10%x";
mostCurrent._mitablero.tam_icono = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 72;BA.debugLine="MisActividades(0).hora_inicio=8";
mostCurrent._misactividades[(int) (0)].hora_inicio = (int) (8);
 //BA.debugLineNum = 73;BA.debugLine="MisActividades(0).minuto_inicio=0";
mostCurrent._misactividades[(int) (0)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="MisActividades(0).hora_fin=8";
mostCurrent._misactividades[(int) (0)].hora_fin = (int) (8);
 //BA.debugLineNum = 75;BA.debugLine="MisActividades(0).minuto_fin=15";
mostCurrent._misactividades[(int) (0)].minuto_fin = (int) (15);
 //BA.debugLineNum = 76;BA.debugLine="MisActividades(0).pictograma=\"despertar_1\"";
mostCurrent._misactividades[(int) (0)].pictograma = "despertar_1";
 //BA.debugLineNum = 77;BA.debugLine="MisActividades(0).descripcion=\"Despertarse\"";
mostCurrent._misactividades[(int) (0)].descripcion = "Despertarse";
 //BA.debugLineNum = 78;BA.debugLine="MisActividades(0).color=ColoresPastel(0)";
mostCurrent._misactividades[(int) (0)].color = _colorespastel[(int) (0)];
 //BA.debugLineNum = 80;BA.debugLine="MisActividades(1).hora_inicio=8";
mostCurrent._misactividades[(int) (1)].hora_inicio = (int) (8);
 //BA.debugLineNum = 81;BA.debugLine="MisActividades(1).minuto_inicio=15";
mostCurrent._misactividades[(int) (1)].minuto_inicio = (int) (15);
 //BA.debugLineNum = 82;BA.debugLine="MisActividades(1).hora_fin=8";
mostCurrent._misactividades[(int) (1)].hora_fin = (int) (8);
 //BA.debugLineNum = 83;BA.debugLine="MisActividades(1).minuto_fin=30";
mostCurrent._misactividades[(int) (1)].minuto_fin = (int) (30);
 //BA.debugLineNum = 84;BA.debugLine="MisActividades(1).pictograma=\"vestirse\"";
mostCurrent._misactividades[(int) (1)].pictograma = "vestirse";
 //BA.debugLineNum = 85;BA.debugLine="MisActividades(1).descripcion=\"Vestirse\"";
mostCurrent._misactividades[(int) (1)].descripcion = "Vestirse";
 //BA.debugLineNum = 86;BA.debugLine="MisActividades(1).color=ColoresPastel(1)";
mostCurrent._misactividades[(int) (1)].color = _colorespastel[(int) (1)];
 //BA.debugLineNum = 88;BA.debugLine="MisActividades(2).hora_inicio=8";
mostCurrent._misactividades[(int) (2)].hora_inicio = (int) (8);
 //BA.debugLineNum = 89;BA.debugLine="MisActividades(2).minuto_inicio=30";
mostCurrent._misactividades[(int) (2)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 90;BA.debugLine="MisActividades(2).hora_fin=9";
mostCurrent._misactividades[(int) (2)].hora_fin = (int) (9);
 //BA.debugLineNum = 91;BA.debugLine="MisActividades(2).minuto_fin=0";
mostCurrent._misactividades[(int) (2)].minuto_fin = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="MisActividades(2).pictograma=\"desayunar\"";
mostCurrent._misactividades[(int) (2)].pictograma = "desayunar";
 //BA.debugLineNum = 93;BA.debugLine="MisActividades(2).descripcion=\"Desayunar\"";
mostCurrent._misactividades[(int) (2)].descripcion = "Desayunar";
 //BA.debugLineNum = 94;BA.debugLine="MisActividades(2).color=ColoresPastel(2)";
mostCurrent._misactividades[(int) (2)].color = _colorespastel[(int) (2)];
 //BA.debugLineNum = 96;BA.debugLine="MisActividades(3).hora_inicio=9";
mostCurrent._misactividades[(int) (3)].hora_inicio = (int) (9);
 //BA.debugLineNum = 97;BA.debugLine="MisActividades(3).minuto_inicio=0";
mostCurrent._misactividades[(int) (3)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 98;BA.debugLine="MisActividades(3).hora_fin=14";
mostCurrent._misactividades[(int) (3)].hora_fin = (int) (14);
 //BA.debugLineNum = 99;BA.debugLine="MisActividades(3).minuto_fin=0";
mostCurrent._misactividades[(int) (3)].minuto_fin = (int) (0);
 //BA.debugLineNum = 100;BA.debugLine="MisActividades(3).pictograma=\"colegio\"";
mostCurrent._misactividades[(int) (3)].pictograma = "colegio";
 //BA.debugLineNum = 101;BA.debugLine="MisActividades(3).descripcion=\"Cole\"";
mostCurrent._misactividades[(int) (3)].descripcion = "Cole";
 //BA.debugLineNum = 102;BA.debugLine="MisActividades(3).color=ColoresPastel(3)";
mostCurrent._misactividades[(int) (3)].color = _colorespastel[(int) (3)];
 //BA.debugLineNum = 104;BA.debugLine="MisActividades(4).hora_inicio=14";
mostCurrent._misactividades[(int) (4)].hora_inicio = (int) (14);
 //BA.debugLineNum = 105;BA.debugLine="MisActividades(4).minuto_inicio=0";
mostCurrent._misactividades[(int) (4)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 106;BA.debugLine="MisActividades(4).hora_fin=15";
mostCurrent._misactividades[(int) (4)].hora_fin = (int) (15);
 //BA.debugLineNum = 107;BA.debugLine="MisActividades(4).minuto_fin=0";
mostCurrent._misactividades[(int) (4)].minuto_fin = (int) (0);
 //BA.debugLineNum = 108;BA.debugLine="MisActividades(4).pictograma=\"comer\"";
mostCurrent._misactividades[(int) (4)].pictograma = "comer";
 //BA.debugLineNum = 109;BA.debugLine="MisActividades(4).descripcion=\"Comer\"";
mostCurrent._misactividades[(int) (4)].descripcion = "Comer";
 //BA.debugLineNum = 110;BA.debugLine="MisActividades(4).color=ColoresPastel(4)";
mostCurrent._misactividades[(int) (4)].color = _colorespastel[(int) (4)];
 //BA.debugLineNum = 112;BA.debugLine="MisActividades(5).hora_inicio=15";
mostCurrent._misactividades[(int) (5)].hora_inicio = (int) (15);
 //BA.debugLineNum = 113;BA.debugLine="MisActividades(5).minuto_inicio=0";
mostCurrent._misactividades[(int) (5)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 114;BA.debugLine="MisActividades(5).hora_fin=20";
mostCurrent._misactividades[(int) (5)].hora_fin = (int) (20);
 //BA.debugLineNum = 115;BA.debugLine="MisActividades(5).minuto_fin=0";
mostCurrent._misactividades[(int) (5)].minuto_fin = (int) (0);
 //BA.debugLineNum = 116;BA.debugLine="MisActividades(5).pictograma=\"juguete\"";
mostCurrent._misactividades[(int) (5)].pictograma = "juguete";
 //BA.debugLineNum = 117;BA.debugLine="MisActividades(5).descripcion=\"Jugar\"";
mostCurrent._misactividades[(int) (5)].descripcion = "Jugar";
 //BA.debugLineNum = 118;BA.debugLine="MisActividades(5).color=ColoresPastel(5)";
mostCurrent._misactividades[(int) (5)].color = _colorespastel[(int) (5)];
 //BA.debugLineNum = 120;BA.debugLine="MisActividades(6).hora_inicio=20";
mostCurrent._misactividades[(int) (6)].hora_inicio = (int) (20);
 //BA.debugLineNum = 121;BA.debugLine="MisActividades(6).minuto_inicio=0";
mostCurrent._misactividades[(int) (6)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 122;BA.debugLine="MisActividades(6).hora_fin=20";
mostCurrent._misactividades[(int) (6)].hora_fin = (int) (20);
 //BA.debugLineNum = 123;BA.debugLine="MisActividades(6).minuto_fin=30";
mostCurrent._misactividades[(int) (6)].minuto_fin = (int) (30);
 //BA.debugLineNum = 124;BA.debugLine="MisActividades(6).pictograma=\"ban_arse\"";
mostCurrent._misactividades[(int) (6)].pictograma = "ban_arse";
 //BA.debugLineNum = 125;BA.debugLine="MisActividades(6).descripcion=\"Bañarse\"";
mostCurrent._misactividades[(int) (6)].descripcion = "Bañarse";
 //BA.debugLineNum = 126;BA.debugLine="MisActividades(6).color=ColoresPastel(6)";
mostCurrent._misactividades[(int) (6)].color = _colorespastel[(int) (6)];
 //BA.debugLineNum = 128;BA.debugLine="MisActividades(7).hora_inicio=20";
mostCurrent._misactividades[(int) (7)].hora_inicio = (int) (20);
 //BA.debugLineNum = 129;BA.debugLine="MisActividades(7).minuto_inicio=30";
mostCurrent._misactividades[(int) (7)].minuto_inicio = (int) (30);
 //BA.debugLineNum = 130;BA.debugLine="MisActividades(7).hora_fin=21";
mostCurrent._misactividades[(int) (7)].hora_fin = (int) (21);
 //BA.debugLineNum = 131;BA.debugLine="MisActividades(7).minuto_fin=0";
mostCurrent._misactividades[(int) (7)].minuto_fin = (int) (0);
 //BA.debugLineNum = 132;BA.debugLine="MisActividades(7).pictograma=\"cenar_2\"";
mostCurrent._misactividades[(int) (7)].pictograma = "cenar_2";
 //BA.debugLineNum = 133;BA.debugLine="MisActividades(7).descripcion=\"Cenar\"";
mostCurrent._misactividades[(int) (7)].descripcion = "Cenar";
 //BA.debugLineNum = 134;BA.debugLine="MisActividades(7).color=ColoresPastel(7)";
mostCurrent._misactividades[(int) (7)].color = _colorespastel[(int) (7)];
 //BA.debugLineNum = 136;BA.debugLine="MisActividades(8).hora_inicio=21";
mostCurrent._misactividades[(int) (8)].hora_inicio = (int) (21);
 //BA.debugLineNum = 137;BA.debugLine="MisActividades(8).minuto_inicio=0";
mostCurrent._misactividades[(int) (8)].minuto_inicio = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="MisActividades(8).hora_fin=21";
mostCurrent._misactividades[(int) (8)].hora_fin = (int) (21);
 //BA.debugLineNum = 139;BA.debugLine="MisActividades(8).minuto_fin=30";
mostCurrent._misactividades[(int) (8)].minuto_fin = (int) (30);
 //BA.debugLineNum = 140;BA.debugLine="MisActividades(8).pictograma=\"dormir_1\"";
mostCurrent._misactividades[(int) (8)].pictograma = "dormir_1";
 //BA.debugLineNum = 141;BA.debugLine="MisActividades(8).descripcion=\"Acostarse\"";
mostCurrent._misactividades[(int) (8)].descripcion = "Acostarse";
 //BA.debugLineNum = 142;BA.debugLine="MisActividades(8).color=ColoresPastel(8)";
mostCurrent._misactividades[(int) (8)].color = _colorespastel[(int) (8)];
 //BA.debugLineNum = 144;BA.debugLine="TotalActividades=9";
_totalactividades = (int) (9);
 //BA.debugLineNum = 146;BA.debugLine="DibujarTablero";
_dibujartablero();
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 356;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _botonactividad_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
javi.prieto.pictorario.main._actividad _actividadpulsada = null;
 //BA.debugLineNum = 345;BA.debugLine="Sub BotonActividad_click";
 //BA.debugLineNum = 346;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 347;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 348;BA.debugLine="Dim ActividadPulsada As Actividad";
_actividadpulsada = new javi.prieto.pictorario.main._actividad();
 //BA.debugLineNum = 349;BA.debugLine="ActividadPulsada=MisActividades(BotonPulsado.Tag)";
_actividadpulsada = mostCurrent._misactividades[(int)(BA.ObjectToNumber(_botonpulsado.getTag()))];
 //BA.debugLineNum = 350;BA.debugLine="ImagenPictograma.Bitmap=LoadBitmap(File.DirAssets";
mostCurrent._imagenpictograma.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._misactividades[(int)(BA.ObjectToNumber(_botonpulsado.getTag()))].pictograma+".png").getObject()));
 //BA.debugLineNum = 351;BA.debugLine="TextoPictograma.Text=ActividadPulsada.descripcion";
mostCurrent._textopictograma.setText(BA.ObjectToCharSequence(_actividadpulsada.descripcion.toUpperCase()+anywheresoftware.b4a.keywords.Common.CRLF+"de "+BA.NumberToString(_actividadpulsada.hora_inicio)+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_actividadpulsada.minuto_inicio,(int) (2),(int) (0))+" a "+BA.NumberToString(_actividadpulsada.hora_fin)+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_actividadpulsada.minuto_fin,(int) (2),(int) (0))));
 //BA.debugLineNum = 352;BA.debugLine="FondoPictograma.Color=MisActividades(BotonPulsado";
mostCurrent._fondopictograma.setColor(mostCurrent._misactividades[(int)(BA.ObjectToNumber(_botonpulsado.getTag()))].color);
 //BA.debugLineNum = 353;BA.debugLine="BotonPulsado.BringToFront()";
_botonpulsado.BringToFront();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_click() throws Exception{
 //BA.debugLineNum = 369;BA.debugLine="Sub CambiarVista_Click";
 //BA.debugLineNum = 370;BA.debugLine="MiTablero.tipo=((MiTablero.tipo)+1) Mod 4";
mostCurrent._mitablero.tipo = (int) (((mostCurrent._mitablero.tipo)+1)%4);
 //BA.debugLineNum = 371;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 372;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 373;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
mostCurrent._activity.LoadLayout("RelojActividades",mostCurrent.activityBA);
 //BA.debugLineNum = 374;BA.debugLine="DibujarTablero";
_dibujartablero();
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return "";
}
public static String  _dibujaractividad(int _numactividad) throws Exception{
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
float _distanciaboton = 0f;
float _botonx = 0f;
float _botony = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _bordeboton = null;
 //BA.debugLineNum = 269;BA.debugLine="Sub DibujarActividad(NumActividad As Int)";
 //BA.debugLineNum = 272;BA.debugLine="If ( MiTablero.tipo>1 Or (MiTablero.tipo==1 And M";
if ((mostCurrent._mitablero.tipo>1 || (mostCurrent._mitablero.tipo==1 && mostCurrent._misactividades[_numactividad].hora_fin>11) || (mostCurrent._mitablero.tipo==0 && mostCurrent._misactividades[_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 275;BA.debugLine="Dim HoraInicio=MisActividades(NumActividad).hora";
_horainicio = mostCurrent._misactividades[_numactividad].hora_inicio;
 //BA.debugLineNum = 276;BA.debugLine="Dim MinInicio=MisActividades(NumActividad).minut";
_mininicio = mostCurrent._misactividades[_numactividad].minuto_inicio;
 //BA.debugLineNum = 277;BA.debugLine="Dim HoraFin=MisActividades(NumActividad).hora_fi";
_horafin = mostCurrent._misactividades[_numactividad].hora_fin;
 //BA.debugLineNum = 278;BA.debugLine="Dim MinFin=MisActividades(NumActividad).minuto_f";
_minfin = mostCurrent._misactividades[_numactividad].minuto_fin;
 //BA.debugLineNum = 279;BA.debugLine="If (MiTablero.tipo==0 And HoraFin>11) Then";
if ((mostCurrent._mitablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 280;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 281;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._mitablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 283;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 284;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 288;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 289;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 292;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 293;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_centrox,_centroy);
 //BA.debugLineNum = 294;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
_recorte.LineTo(_horaminuto_x((float) (_horainicio),(float) (_mininicio),(float) (1.5)),_horaminuto_y((float) (_horainicio),(float) (_mininicio),(float) (1.5)));
 //BA.debugLineNum = 295;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
_recorte.LineTo(_horaminuto_x(_horamitad,_minutomitad,(float) (1.5)),_horaminuto_y(_horamitad,_minutomitad,(float) (1.5)));
 //BA.debugLineNum = 296;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,1.5)";
_recorte.LineTo(_horaminuto_x((float) (_horafin),(float) (_minfin),(float) (1.5)),_horaminuto_y((float) (_horafin),(float) (_minfin),(float) (1.5)));
 //BA.debugLineNum = 297;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
_recorte.LineTo(_centrox,_centroy);
 //BA.debugLineNum = 300;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._pantalla.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 301;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,Mi";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,(float) (_radio*0.7),mostCurrent._misactividades[_numactividad].color,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 302;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._pantalla.RemoveClip();
 //BA.debugLineNum = 305;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
mostCurrent._boton[_numactividad].Initialize(mostCurrent.activityBA,"BotonActividad");
 //BA.debugLineNum = 306;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
mostCurrent._boton[_numactividad].setTag((Object)(_numactividad));
 //BA.debugLineNum = 307;BA.debugLine="Dim DistanciaBoton=0.3+0.1*(NumActividad Mod 3)";
_distanciaboton = (float) (0.3+0.1*(_numactividad%3));
 //BA.debugLineNum = 308;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
_botonx = (float) (_horaminuto_x(_horamitad,_minutomitad,_distanciaboton)-mostCurrent._mitablero.tam_icono/(double)2);
 //BA.debugLineNum = 309;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
_botony = (float) (_horaminuto_y(_horamitad,_minutomitad,_distanciaboton)-mostCurrent._mitablero.tam_icono/(double)2);
 //BA.debugLineNum = 310;BA.debugLine="Dim BordeBoton As Rect";
_bordeboton = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 311;BA.debugLine="BordeBoton.Initialize(BotonX-1dip,BotonY-1dip,Bo";
_bordeboton.Initialize((int) (_botonx-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(int) (_botony-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(int) (_botonx+mostCurrent._mitablero.tam_icono+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (_botony+mostCurrent._mitablero.tam_icono+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 312;BA.debugLine="Pantalla.DrawRect(BordeBoton,Colors.Black,True,0";
mostCurrent._pantalla.DrawRect((android.graphics.Rect)(_bordeboton.getObject()),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 313;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Boto";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._boton[_numactividad].getObject()),(int) (_botonx),(int) (_botony),(int) (mostCurrent._mitablero.tam_icono),(int) (mostCurrent._mitablero.tam_icono));
 //BA.debugLineNum = 314;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitma";
mostCurrent._boton[_numactividad].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._misactividades[_numactividad].pictograma+".png").getObject()));
 };
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public static String  _dibujartablero() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
int _hora = 0;
float _x = 0f;
float _y = 0f;
anywheresoftware.b4a.objects.LabelWrapper _numerohora = null;
int _nactividad = 0;
int _horaactual = 0;
int _minutoactual = 0;
int _segundoactual = 0;
float _angulominuto = 0f;
float _angulosegundo = 0f;
 //BA.debugLineNum = 150;BA.debugLine="Sub DibujarTablero()";
 //BA.debugLineNum = 152;BA.debugLine="CentroX=50%x";
_centrox = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 153;BA.debugLine="CentroY=60%x";
_centroy = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 154;BA.debugLine="Radio=45%x";
_radio = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 156;BA.debugLine="Pantalla.Initialize(PanelReloj)";
mostCurrent._pantalla.Initialize((android.view.View)(mostCurrent._panelreloj.getObject()));
 //BA.debugLineNum = 159;BA.debugLine="If MiTablero.tipo<2 Then 'Reloj 12h";
if (mostCurrent._mitablero.tipo<2) { 
 //BA.debugLineNum = 160;BA.debugLine="MinHora=1";
_minhora = (int) (1);
 //BA.debugLineNum = 161;BA.debugLine="MaxHora=12";
_maxhora = (int) (12);
 }else if(mostCurrent._mitablero.tipo==2) { 
 //BA.debugLineNum = 163;BA.debugLine="MinHora=1";
_minhora = (int) (1);
 //BA.debugLineNum = 164;BA.debugLine="MaxHora=24";
_maxhora = (int) (24);
 }else {
 //BA.debugLineNum = 166;BA.debugLine="MinHora=MisActividades(0).hora_inicio";
_minhora = mostCurrent._misactividades[(int) (0)].hora_inicio;
 //BA.debugLineNum = 167;BA.debugLine="MaxHora=MisActividades(TotalActividades-1).hora_";
_maxhora = mostCurrent._misactividades[(int) (_totalactividades-1)].hora_fin;
 //BA.debugLineNum = 168;BA.debugLine="If (MisActividades(TotalActividades-1).minuto_fi";
if ((mostCurrent._misactividades[(int) (_totalactividades-1)].minuto_fin!=0)) { 
 //BA.debugLineNum = 169;BA.debugLine="MaxHora=MaxHora+1";
_maxhora = (int) (_maxhora+1);
 };
 };
 //BA.debugLineNum = 174;BA.debugLine="If MiTablero.tipo<3 Then 'Reloj";
if (mostCurrent._mitablero.tipo<3) { 
 //BA.debugLineNum = 176;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,(float) (_radio*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 177;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,_radio,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 }else {
 //BA.debugLineNum = 179;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
_recorte.Initialize(_centrox,(float) (_centroy+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)));
 //BA.debugLineNum = 182;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(114)*_radio*3)+_centrox),(float) ((anywheresoftware.b4a.keywords.Common.SinD(114)*_radio*3)+_centroy));
 //BA.debugLineNum = 183;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 184;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 185;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 186;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 187;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(81)*_radio*3)+_centrox),(float) ((anywheresoftware.b4a.keywords.Common.SinD(81)*_radio)+_centroy));
 //BA.debugLineNum = 188;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._pantalla.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 189;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,(float) (_radio*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 190;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._pantalla.RemoveClip();
 //BA.debugLineNum = 192;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_centrox,_centroy);
 //BA.debugLineNum = 193;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(116)*_radio*3)+_centrox),(float) ((anywheresoftware.b4a.keywords.Common.SinD(116)*_radio*3)+_centroy));
 //BA.debugLineNum = 194;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 195;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 196;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 197;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 198;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(80)*_radio*3)+_centrox),(float) ((anywheresoftware.b4a.keywords.Common.SinD(80)*_radio)+_centroy));
 //BA.debugLineNum = 199;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._pantalla.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 200;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,_radio,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 201;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._pantalla.RemoveClip();
 };
 //BA.debugLineNum = 204;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
{
final int step44 = (int) (1);
final int limit44 = _maxhora;
_hora = _minhora ;
for (;(step44 > 0 && _hora <= limit44) || (step44 < 0 && _hora >= limit44) ;_hora = ((int)(0 + _hora + step44))  ) {
 //BA.debugLineNum = 206;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 207;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 209;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
mostCurrent._pantalla.DrawCircle(_x,_y,(float) (_radio*0.02),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 210;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 211;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 212;BA.debugLine="Dim NumeroHora As Label";
_numerohora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 213;BA.debugLine="NumeroHora.Initialize(\"\")";
_numerohora.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 214;BA.debugLine="NumeroHora.Text=(Hora) Mod 24";
_numerohora.setText(BA.ObjectToCharSequence((_hora)%24));
 //BA.debugLineNum = 215;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
_numerohora.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 216;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
_numerohora.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 217;BA.debugLine="NumeroHora.TextSize=15";
_numerohora.setTextSize((float) (15));
 //BA.debugLineNum = 218;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
mostCurrent._activity.AddView((android.view.View)(_numerohora.getObject()),(int) (_x-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (_y-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 221;BA.debugLine="If MiTablero.tipo=1 Then 'Si es 12h PM, pasamos a";
if (mostCurrent._mitablero.tipo==1) { 
 //BA.debugLineNum = 222;BA.debugLine="MinHora=MinHora+12";
_minhora = (int) (_minhora+12);
 //BA.debugLineNum = 223;BA.debugLine="MaxHora=MaxHora+12";
_maxhora = (int) (_maxhora+12);
 };
 //BA.debugLineNum = 227;BA.debugLine="For NActividad=0 To TotalActividades-1 Step 1";
{
final int step62 = (int) (1);
final int limit62 = (int) (_totalactividades-1);
_nactividad = (int) (0) ;
for (;(step62 > 0 && _nactividad <= limit62) || (step62 < 0 && _nactividad >= limit62) ;_nactividad = ((int)(0 + _nactividad + step62))  ) {
 //BA.debugLineNum = 228;BA.debugLine="DibujarActividad(NActividad)";
_dibujaractividad(_nactividad);
 }
};
 //BA.debugLineNum = 232;BA.debugLine="Dim HoraActual=DateTime.GetHour(DateTime.Now) As";
_horaactual = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 233;BA.debugLine="Dim MinutoActual=DateTime.GetMinute(DateTime.Now)";
_minutoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 234;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
_segundoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 235;BA.debugLine="If (MiTablero.indicar_hora>0 And HoraActual>=MinH";
if ((mostCurrent._mitablero.indicar_hora>0 && _horaactual>=_minhora && _horaactual<_maxhora)) { 
 //BA.debugLineNum = 236;BA.debugLine="If (MiTablero.tipo=3 Or MiTablero.indicar_hora==";
if ((mostCurrent._mitablero.tipo==3 || mostCurrent._mitablero.indicar_hora==1)) { 
 //BA.debugLineNum = 237;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
mostCurrent._pantalla.DrawLine(_centrox,_centroy,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 }else {
 //BA.debugLineNum = 239;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
mostCurrent._pantalla.DrawLine(_centrox,_centroy,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 240;BA.debugLine="If (MiTablero.indicar_hora>1) Then";
if ((mostCurrent._mitablero.indicar_hora>1)) { 
 //BA.debugLineNum = 241;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
_angulominuto = (float) (270+(_minutoactual*6));
 //BA.debugLineNum = 242;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+CosD";
mostCurrent._pantalla.DrawLine(_centrox,_centroy,(float) (_centrox+anywheresoftware.b4a.keywords.Common.CosD(_angulominuto)*_radio*0.8),(float) (_centroy+anywheresoftware.b4a.keywords.Common.SinD(_angulominuto)*_radio*0.75),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6))));
 //BA.debugLineNum = 243;BA.debugLine="If (MiTablero.indicar_hora>2) Then";
if ((mostCurrent._mitablero.indicar_hora>2)) { 
 //BA.debugLineNum = 244;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
_angulosegundo = (float) (270+(_segundoactual*6));
 //BA.debugLineNum = 245;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+Cos";
mostCurrent._pantalla.DrawLine(_centrox,_centroy,(float) (_centrox+anywheresoftware.b4a.keywords.Common.CosD(_angulosegundo)*_radio*0.9),(float) (_centroy+anywheresoftware.b4a.keywords.Common.SinD(_angulosegundo)*_radio*0.9),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))));
 };
 };
 };
 };
 //BA.debugLineNum = 252;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.1,Col";
mostCurrent._pantalla.DrawCircle(_centrox,_centroy,(float) (_radio*0.1),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 255;BA.debugLine="CambiarVista.Text=MiTablero.tipo";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence(mostCurrent._mitablero.tipo));
 //BA.debugLineNum = 256;BA.debugLine="Select MiTablero.tipo";
switch (BA.switchObjectToInt(mostCurrent._mitablero.tipo,(int) (0),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 258;BA.debugLine="CambiarVista.Text=\"12hAM\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("12hAM"));
 break; }
case 1: {
 //BA.debugLineNum = 260;BA.debugLine="CambiarVista.Text=\"12hPM\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("12hPM"));
 break; }
case 2: {
 //BA.debugLineNum = 262;BA.debugLine="CambiarVista.Text=\"24h\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("24h"));
 break; }
case 3: {
 //BA.debugLineNum = 264;BA.debugLine="CambiarVista.Text=\"Arco\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Arco"));
 break; }
}
;
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 36;BA.debugLine="Private PanelDescripcion As Panel";
mostCurrent._paneldescripcion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private PanelReloj As Panel";
mostCurrent._panelreloj = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ImagenPictograma As ImageView";
mostCurrent._imagenpictograma = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private FondoPictograma As Panel";
mostCurrent._fondopictograma = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private TextoPictograma As Label";
mostCurrent._textopictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim MaxActividades=20 As Int 'Número máximo de ac";
_maxactividades = (int) (20);
 //BA.debugLineNum = 44;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja el";
mostCurrent._pantalla = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
_centrox = 0f;
_centroy = 0f;
_radio = 0f;
 //BA.debugLineNum = 48;BA.debugLine="Dim MinHora,MaxHora As Int";
_minhora = 0;
_maxhora = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim Boton(MaxActividades) As Button";
mostCurrent._boton = new anywheresoftware.b4a.objects.ButtonWrapper[_maxactividades];
{
int d0 = mostCurrent._boton.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._boton[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 53;BA.debugLine="Dim MisActividades(MaxActividades) As Actividad";
mostCurrent._misactividades = new javi.prieto.pictorario.main._actividad[_maxactividades];
{
int d0 = mostCurrent._misactividades.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._misactividades[i0] = new javi.prieto.pictorario.main._actividad();
}
}
;
 //BA.debugLineNum = 54;BA.debugLine="Dim MiTablero As Tablero";
mostCurrent._mitablero = new javi.prieto.pictorario.main._tablero();
 //BA.debugLineNum = 55;BA.debugLine="Dim TotalActividades As Int";
_totalactividades = 0;
 //BA.debugLineNum = 57;BA.debugLine="Dim ColoresPastel() As Int";
_colorespastel = new int[(int) (0)];
;
 //BA.debugLineNum = 58;BA.debugLine="ColoresPastel = Array As Int(0xFFffb3ba,0xFFffdfb";
_colorespastel = new int[]{(int) (0xffffb3ba),(int) (0xffffdfba),(int) (0xffffffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffbaff),(int) (0xffdfffba),(int) (0xffbaffc9),(int) (0xffbae1ff),(int) (0xffffe1b1),(int) (0xffbaffe1)};
 //BA.debugLineNum = 60;BA.debugLine="Private CambiarVista As Button";
mostCurrent._cambiarvista = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static float  _horaminuto_x(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 319;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 320;BA.debugLine="If MiTablero.tipo==3 Then";
if (mostCurrent._mitablero.tipo==3) { 
 //BA.debugLineNum = 321;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_minhora)*300/(double)(_maxhora-_minhora));
 //BA.debugLineNum = 322;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD(_angulo)*_radio*_distancia)+_centrox);
 }else {
 //BA.debugLineNum = 324;BA.debugLine="If MiTablero.tipo==2 Then";
if (mostCurrent._mitablero.tipo==2) { 
 //BA.debugLineNum = 325;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 326;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 328;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_radio*_distancia)+_centrox);
 };
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return 0f;
}
public static float  _horaminuto_y(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 332;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 333;BA.debugLine="If MiTablero.tipo==3 Then";
if (mostCurrent._mitablero.tipo==3) { 
 //BA.debugLineNum = 334;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_minhora)*300/(double)(_maxhora-_minhora));
 //BA.debugLineNum = 335;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD(_angulo)*_radio*_distancia)+_centroy);
 }else {
 //BA.debugLineNum = 337;BA.debugLine="If MiTablero.tipo==2 Then";
if (mostCurrent._mitablero.tipo==2) { 
 //BA.debugLineNum = 338;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 339;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 341;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_radio*_distancia)+_centroy);
 };
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return 0f;
}
public static String  _panelreloj_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 365;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Type Actividad ( hora_inicio As Int, minuto_inici";
;
 //BA.debugLineNum = 21;BA.debugLine="Type Tablero ( tipo As Int, indicar_hora As Int,";
;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
}
