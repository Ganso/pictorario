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
public anywheresoftware.b4a.objects.ButtonWrapper[] _pictogramasecuencia = null;
public anywheresoftware.b4a.objects.ButtonWrapper[] _editarsecuencia = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _etiquetasecuencia = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botonacercade = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncrear = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _panelscroll = null;
public javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public javi.prieto.pictorario.starter _starter = null;
public javi.prieto.pictorario.visualizacion _visualizacion = null;
public javi.prieto.pictorario.acercade _acercade = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (configurarsecuencia.mostCurrent != null);
vis = vis | (visualizacion.mostCurrent != null);
vis = vis | (acercade.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="DibujarPortada";
_dibujarportada();
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 110;BA.debugLine="DibujarPortada";
_dibujarportada();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _botonacercade_click() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub BotonAcercaDe_Click";
 //BA.debugLineNum = 124;BA.debugLine="StartActivity(AcercaDe)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._acercade.getObject()));
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _botoncrear_click() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub BotonCrear_Click";
 //BA.debugLineNum = 128;BA.debugLine="Starter.SecuenciaActiva=Starter.MaxSecuencias";
mostCurrent._starter._secuenciaactiva = mostCurrent._starter._maxsecuencias;
 //BA.debugLineNum = 129;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._configurarsecuencia.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _botoneditar_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
 //BA.debugLineNum = 95;BA.debugLine="Sub BotonEditar_click";
 //BA.debugLineNum = 96;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 97;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 98;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 99;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._configurarsecuencia.getObject()));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _botonpictograma_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
 //BA.debugLineNum = 88;BA.debugLine="Sub BotonPictograma_click";
 //BA.debugLineNum = 89;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 90;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 91;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 92;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _dibujarportada() throws Exception{
int _act = 0;
 //BA.debugLineNum = 41;BA.debugLine="Sub DibujarPortada";
 //BA.debugLineNum = 43;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"Portada\")";
mostCurrent._activity.LoadLayout("Portada",mostCurrent.activityBA);
 //BA.debugLineNum = 46;BA.debugLine="Dim Act As Int";
_act = 0;
 //BA.debugLineNum = 47;BA.debugLine="For Act=0 To Starter.NumSecuencias-1";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._starter._numsecuencias-1);
_act = (int) (0) ;
for (;(step4 > 0 && _act <= limit4) || (step4 < 0 && _act >= limit4) ;_act = ((int)(0 + _act + step4))  ) {
 //BA.debugLineNum = 49;BA.debugLine="PictogramaSecuencia(Act).Initialize(\"BotonPictog";
mostCurrent._pictogramasecuencia[_act].Initialize(mostCurrent.activityBA,"BotonPictograma");
 //BA.debugLineNum = 50;BA.debugLine="PictogramaSecuencia(Act).Tag=Act";
mostCurrent._pictogramasecuencia[_act].setTag((Object)(_act));
 //BA.debugLineNum = 51;BA.debugLine="PictogramaSecuencia(Act).Color=Colors.Transparen";
mostCurrent._pictogramasecuencia[_act].setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 52;BA.debugLine="PictogramaSecuencia(Act).SetBackgroundImage(Load";
mostCurrent._pictogramasecuencia[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._starter._secuencia[_act].pictograma+".png").getObject()));
 //BA.debugLineNum = 53;BA.debugLine="PanelScroll.Panel.AddView(PictogramaSecuencia(Ac";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._pictogramasecuencia[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 55;BA.debugLine="EtiquetaSecuencia(Act).Initialize(\"TextoSecuenci";
mostCurrent._etiquetasecuencia[_act].Initialize(mostCurrent.activityBA,"TextoSecuencia");
 //BA.debugLineNum = 56;BA.debugLine="EtiquetaSecuencia(Act).Tag=Act";
mostCurrent._etiquetasecuencia[_act].setTag((Object)(_act));
 //BA.debugLineNum = 57;BA.debugLine="EtiquetaSecuencia(Act).Text=Starter.Secuencia(Ac";
mostCurrent._etiquetasecuencia[_act].setText(BA.ObjectToCharSequence(mostCurrent._starter._secuencia[_act].Descripcion));
 //BA.debugLineNum = 58;BA.debugLine="EtiquetaSecuencia(Act).TextColor=Colors.Black";
mostCurrent._etiquetasecuencia[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 59;BA.debugLine="EtiquetaSecuencia(Act).TextSize=16";
mostCurrent._etiquetasecuencia[_act].setTextSize((float) (16));
 //BA.debugLineNum = 60;BA.debugLine="EtiquetaSecuencia(Act).Gravity=Bit.Or(Gravity.CE";
mostCurrent._etiquetasecuencia[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.LEFT));
 //BA.debugLineNum = 61;BA.debugLine="PanelScroll.Panel.AddView(EtiquetaSecuencia(Act)";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._etiquetasecuencia[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 63;BA.debugLine="EditarSecuencia(Act).Initialize(\"BotonEditar\")";
mostCurrent._editarsecuencia[_act].Initialize(mostCurrent.activityBA,"BotonEditar");
 //BA.debugLineNum = 64;BA.debugLine="EditarSecuencia(Act).Tag=Act";
mostCurrent._editarsecuencia[_act].setTag((Object)(_act));
 //BA.debugLineNum = 65;BA.debugLine="EditarSecuencia(Act).SetBackgroundImage(LoadBitm";
mostCurrent._editarsecuencia[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
 //BA.debugLineNum = 66;BA.debugLine="PanelScroll.Panel.AddView(EditarSecuencia(Act),1";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._editarsecuencia[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 }
};
 //BA.debugLineNum = 70;BA.debugLine="BotonCrear.Initialize(\"BotonCrear\")";
mostCurrent._botoncrear.Initialize(mostCurrent.activityBA,"BotonCrear");
 //BA.debugLineNum = 71;BA.debugLine="BotonCrear.Text=\"Crear Secuencia\"";
mostCurrent._botoncrear.setText(BA.ObjectToCharSequence("Crear Secuencia"));
 //BA.debugLineNum = 72;BA.debugLine="BotonCrear.TextSize=20";
mostCurrent._botoncrear.setTextSize((float) (20));
 //BA.debugLineNum = 73;BA.debugLine="BotonCrear.Gravity=Bit.Or(Gravity.CENTER_VERTICAL";
mostCurrent._botoncrear.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 74;BA.debugLine="BotonCrear.TextColor=Colors.Black";
mostCurrent._botoncrear.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 75;BA.debugLine="PanelScroll.Panel.AddView(BotonCrear,5dip,90dip*S";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._botoncrear.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*mostCurrent._starter._numsecuencias),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 77;BA.debugLine="BotonAcercaDe.Initialize(\"BotonAcercaDe\")";
mostCurrent._botonacercade.Initialize(mostCurrent.activityBA,"BotonAcercaDe");
 //BA.debugLineNum = 78;BA.debugLine="BotonAcercaDe.Text=\"Acerca de Pictorario\"";
mostCurrent._botonacercade.setText(BA.ObjectToCharSequence("Acerca de Pictorario"));
 //BA.debugLineNum = 79;BA.debugLine="BotonAcercaDe.TextSize=20";
mostCurrent._botonacercade.setTextSize((float) (20));
 //BA.debugLineNum = 80;BA.debugLine="BotonAcercaDe.Gravity=Bit.Or(Gravity.CENTER_VERTI";
mostCurrent._botonacercade.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 81;BA.debugLine="BotonAcercaDe.TextColor=Colors.Black";
mostCurrent._botonacercade.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 82;BA.debugLine="PanelScroll.Panel.AddView(BotonAcercaDe,5dip,90di";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._botonacercade.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*(mostCurrent._starter._numsecuencias+1)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 84;BA.debugLine="PanelScroll.Panel.Height=90dip*(Starter.NumSecuen";
mostCurrent._panelscroll.getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*(mostCurrent._starter._numsecuencias+1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 25;BA.debugLine="Dim PictogramaSecuencia(Starter.MaxSecuencias) As";
mostCurrent._pictogramasecuencia = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._starter._maxsecuencias];
{
int d0 = mostCurrent._pictogramasecuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._pictogramasecuencia[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 26;BA.debugLine="Dim EditarSecuencia(Starter.MaxSecuencias) As But";
mostCurrent._editarsecuencia = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._starter._maxsecuencias];
{
int d0 = mostCurrent._editarsecuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._editarsecuencia[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 27;BA.debugLine="Dim EtiquetaSecuencia(Starter.MaxActividades) As";
mostCurrent._etiquetasecuencia = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._starter._maxactividades];
{
int d0 = mostCurrent._etiquetasecuencia.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._etiquetasecuencia[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 29;BA.debugLine="Dim BotonAcercaDe As Button";
mostCurrent._botonacercade = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim BotonCrear As Button";
mostCurrent._botoncrear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private PanelScroll As ScrollView";
mostCurrent._panelscroll = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
configurarsecuencia._process_globals();
starter._process_globals();
visualizacion._process_globals();
acercade._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _textosecuencia_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _etiquetapulsada = null;
 //BA.debugLineNum = 102;BA.debugLine="Sub TextoSecuencia_click";
 //BA.debugLineNum = 103;BA.debugLine="Dim EtiquetaPulsada As Label";
_etiquetapulsada = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="EtiquetaPulsada=Sender";
_etiquetapulsada.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 105;BA.debugLine="Starter.SecuenciaActiva=EtiquetaPulsada.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_etiquetapulsada.getTag()));
 //BA.debugLineNum = 106;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _versecuencias_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub VerSecuencias_Click";
 //BA.debugLineNum = 119;BA.debugLine="Starter.SecuenciaActiva=1";
mostCurrent._starter._secuenciaactiva = (int) (1);
 //BA.debugLineNum = 120;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
}
