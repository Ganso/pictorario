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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.main");
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



public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (configurarsecuencia.mostCurrent != null);
vis = vis | (visualizacion.mostCurrent != null);
vis = vis | (acercade.mostCurrent != null);
return vis;}

private static BA killProgramHelper(BA ba) {
    if (ba == null)
        return null;
    anywheresoftware.b4a.BA.SharedProcessBA sharedProcessBA = ba.sharedProcessBA;
    if (sharedProcessBA == null || sharedProcessBA.activityBA == null)
        return null;
    return sharedProcessBA.activityBA.get();
}
public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(main.mostCurrent == null ? null : main.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (configurarsecuencia.previousOne != null) {
				__a = configurarsecuencia.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(configurarsecuencia.mostCurrent == null ? null : configurarsecuencia.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

BA.applicationContext.stopService(new android.content.Intent(BA.applicationContext, starter.class));
 {
            Activity __a = null;
            if (visualizacion.previousOne != null) {
				__a = visualizacion.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(visualizacion.mostCurrent == null ? null : visualizacion.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (acercade.previousOne != null) {
				__a = acercade.previousOne.get();
			}
            else {
                BA ba = killProgramHelper(acercade.mostCurrent == null ? null : acercade.mostCurrent.processBA);
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

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
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime});
RDebugUtils.currentLine=131072;
 //BA.debugLineNum = 131072;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=131074;
 //BA.debugLineNum = 131074;BA.debugLine="DibujarPortada";
_dibujarportada();
RDebugUtils.currentLine=131075;
 //BA.debugLineNum = 131075;BA.debugLine="End Sub";
return "";
}
public static String  _dibujarportada() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "dibujarportada"))
	return (String) Debug.delegate(mostCurrent.activityBA, "dibujarportada", null);
int _act = 0;
RDebugUtils.currentLine=196608;
 //BA.debugLineNum = 196608;BA.debugLine="Sub DibujarPortada";
RDebugUtils.currentLine=196610;
 //BA.debugLineNum = 196610;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
RDebugUtils.currentLine=196611;
 //BA.debugLineNum = 196611;BA.debugLine="Activity.LoadLayout(\"Portada\")";
mostCurrent._activity.LoadLayout("Portada",mostCurrent.activityBA);
RDebugUtils.currentLine=196613;
 //BA.debugLineNum = 196613;BA.debugLine="Dim Act As Int";
_act = 0;
RDebugUtils.currentLine=196614;
 //BA.debugLineNum = 196614;BA.debugLine="For Act=0 To Starter.NumSecuencias-1";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._starter._numsecuencias-1);
_act = (int) (0) ;
for (;(step4 > 0 && _act <= limit4) || (step4 < 0 && _act >= limit4) ;_act = ((int)(0 + _act + step4))  ) {
RDebugUtils.currentLine=196616;
 //BA.debugLineNum = 196616;BA.debugLine="PictogramaSecuencia(Act).Initialize(\"BotonPictog";
mostCurrent._pictogramasecuencia[_act].Initialize(mostCurrent.activityBA,"BotonPictograma");
RDebugUtils.currentLine=196617;
 //BA.debugLineNum = 196617;BA.debugLine="PictogramaSecuencia(Act).Tag=Act";
mostCurrent._pictogramasecuencia[_act].setTag((Object)(_act));
RDebugUtils.currentLine=196618;
 //BA.debugLineNum = 196618;BA.debugLine="PictogramaSecuencia(Act).Color=Colors.Transparen";
mostCurrent._pictogramasecuencia[_act].setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
RDebugUtils.currentLine=196619;
 //BA.debugLineNum = 196619;BA.debugLine="PictogramaSecuencia(Act).SetBackgroundImage(Load";
mostCurrent._pictogramasecuencia[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._starter._secuencia[_act].pictograma+".png").getObject()));
RDebugUtils.currentLine=196620;
 //BA.debugLineNum = 196620;BA.debugLine="PanelScroll.Panel.AddView(PictogramaSecuencia(Ac";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._pictogramasecuencia[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=196622;
 //BA.debugLineNum = 196622;BA.debugLine="EtiquetaSecuencia(Act).Initialize(\"TextoSecuenci";
mostCurrent._etiquetasecuencia[_act].Initialize(mostCurrent.activityBA,"TextoSecuencia");
RDebugUtils.currentLine=196623;
 //BA.debugLineNum = 196623;BA.debugLine="EtiquetaSecuencia(Act).Tag=Act";
mostCurrent._etiquetasecuencia[_act].setTag((Object)(_act));
RDebugUtils.currentLine=196624;
 //BA.debugLineNum = 196624;BA.debugLine="EtiquetaSecuencia(Act).Text=Starter.Secuencia(Ac";
mostCurrent._etiquetasecuencia[_act].setText(BA.ObjectToCharSequence(mostCurrent._starter._secuencia[_act].Descripcion));
RDebugUtils.currentLine=196625;
 //BA.debugLineNum = 196625;BA.debugLine="EtiquetaSecuencia(Act).TextColor=Colors.Black";
mostCurrent._etiquetasecuencia[_act].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=196626;
 //BA.debugLineNum = 196626;BA.debugLine="EtiquetaSecuencia(Act).TextSize=16";
mostCurrent._etiquetasecuencia[_act].setTextSize((float) (16));
RDebugUtils.currentLine=196627;
 //BA.debugLineNum = 196627;BA.debugLine="EtiquetaSecuencia(Act).Gravity=Bit.Or(Gravity.CE";
mostCurrent._etiquetasecuencia[_act].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.LEFT));
RDebugUtils.currentLine=196628;
 //BA.debugLineNum = 196628;BA.debugLine="PanelScroll.Panel.AddView(EtiquetaSecuencia(Act)";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._etiquetasecuencia[_act].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (110)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=196630;
 //BA.debugLineNum = 196630;BA.debugLine="EditarSecuencia(Act).Initialize(\"BotonEditar\")";
mostCurrent._editarsecuencia[_act].Initialize(mostCurrent.activityBA,"BotonEditar");
RDebugUtils.currentLine=196631;
 //BA.debugLineNum = 196631;BA.debugLine="EditarSecuencia(Act).Tag=Act";
mostCurrent._editarsecuencia[_act].setTag((Object)(_act));
RDebugUtils.currentLine=196632;
 //BA.debugLineNum = 196632;BA.debugLine="EditarSecuencia(Act).SetBackgroundImage(LoadBitm";
mostCurrent._editarsecuencia[_act].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"engranaje.png").getObject()));
RDebugUtils.currentLine=196633;
 //BA.debugLineNum = 196633;BA.debugLine="PanelScroll.Panel.AddView(EditarSecuencia(Act),1";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._editarsecuencia[_act].getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*_act),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 }
};
RDebugUtils.currentLine=196637;
 //BA.debugLineNum = 196637;BA.debugLine="BotonCrear.Initialize(\"BotonCrear\")";
mostCurrent._botoncrear.Initialize(mostCurrent.activityBA,"BotonCrear");
RDebugUtils.currentLine=196638;
 //BA.debugLineNum = 196638;BA.debugLine="BotonCrear.Text=\"Crear Secuencia\"";
mostCurrent._botoncrear.setText(BA.ObjectToCharSequence("Crear Secuencia"));
RDebugUtils.currentLine=196639;
 //BA.debugLineNum = 196639;BA.debugLine="BotonCrear.TextSize=20";
mostCurrent._botoncrear.setTextSize((float) (20));
RDebugUtils.currentLine=196640;
 //BA.debugLineNum = 196640;BA.debugLine="BotonCrear.Gravity=Bit.Or(Gravity.CENTER_VERTICAL";
mostCurrent._botoncrear.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=196641;
 //BA.debugLineNum = 196641;BA.debugLine="BotonCrear.TextColor=Colors.Black";
mostCurrent._botoncrear.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=196642;
 //BA.debugLineNum = 196642;BA.debugLine="PanelScroll.Panel.AddView(BotonCrear,5dip,90dip*S";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._botoncrear.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*mostCurrent._starter._numsecuencias),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=196644;
 //BA.debugLineNum = 196644;BA.debugLine="BotonAcercaDe.Initialize(\"BotonAcercaDe\")";
mostCurrent._botonacercade.Initialize(mostCurrent.activityBA,"BotonAcercaDe");
RDebugUtils.currentLine=196645;
 //BA.debugLineNum = 196645;BA.debugLine="BotonAcercaDe.Text=\"Acerca de Pictorario\"";
mostCurrent._botonacercade.setText(BA.ObjectToCharSequence("Acerca de Pictorario"));
RDebugUtils.currentLine=196646;
 //BA.debugLineNum = 196646;BA.debugLine="BotonAcercaDe.TextSize=20";
mostCurrent._botonacercade.setTextSize((float) (20));
RDebugUtils.currentLine=196647;
 //BA.debugLineNum = 196647;BA.debugLine="BotonAcercaDe.Gravity=Bit.Or(Gravity.CENTER_VERTI";
mostCurrent._botonacercade.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
RDebugUtils.currentLine=196648;
 //BA.debugLineNum = 196648;BA.debugLine="BotonAcercaDe.TextColor=Colors.Black";
mostCurrent._botonacercade.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=196649;
 //BA.debugLineNum = 196649;BA.debugLine="PanelScroll.Panel.AddView(BotonAcercaDe,5dip,90di";
mostCurrent._panelscroll.getPanel().AddView((android.view.View)(mostCurrent._botonacercade.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*(mostCurrent._starter._numsecuencias+1)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=196651;
 //BA.debugLineNum = 196651;BA.debugLine="PanelScroll.Panel.Height=90dip*(Starter.NumSecuen";
mostCurrent._panelscroll.getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))*(mostCurrent._starter._numsecuencias+1)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
RDebugUtils.currentLine=196653;
 //BA.debugLineNum = 196653;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="main";
RDebugUtils.currentLine=524288;
 //BA.debugLineNum = 524288;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=524290;
 //BA.debugLineNum = 524290;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null);
RDebugUtils.currentLine=458752;
 //BA.debugLineNum = 458752;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=458753;
 //BA.debugLineNum = 458753;BA.debugLine="DibujarPortada";
_dibujarportada();
RDebugUtils.currentLine=458754;
 //BA.debugLineNum = 458754;BA.debugLine="End Sub";
return "";
}
public static String  _botonacercade_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "botonacercade_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "botonacercade_click", null);
RDebugUtils.currentLine=655360;
 //BA.debugLineNum = 655360;BA.debugLine="Sub BotonAcercaDe_Click";
RDebugUtils.currentLine=655361;
 //BA.debugLineNum = 655361;BA.debugLine="StartActivity(AcercaDe)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._acercade.getObject()));
RDebugUtils.currentLine=655362;
 //BA.debugLineNum = 655362;BA.debugLine="End Sub";
return "";
}
public static String  _botoncrear_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "botoncrear_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "botoncrear_click", null);
RDebugUtils.currentLine=720896;
 //BA.debugLineNum = 720896;BA.debugLine="Sub BotonCrear_Click";
RDebugUtils.currentLine=720897;
 //BA.debugLineNum = 720897;BA.debugLine="Starter.SecuenciaActiva=Starter.MaxSecuencias";
mostCurrent._starter._secuenciaactiva = mostCurrent._starter._maxsecuencias;
RDebugUtils.currentLine=720898;
 //BA.debugLineNum = 720898;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._configurarsecuencia.getObject()));
RDebugUtils.currentLine=720899;
 //BA.debugLineNum = 720899;BA.debugLine="End Sub";
return "";
}
public static String  _botoneditar_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "botoneditar_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "botoneditar_click", null);
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
anywheresoftware.b4a.objects.collections.List _opciones = null;
int _resultado = 0;
int _sec = 0;
RDebugUtils.currentLine=327680;
 //BA.debugLineNum = 327680;BA.debugLine="Sub BotonEditar_click";
RDebugUtils.currentLine=327681;
 //BA.debugLineNum = 327681;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
RDebugUtils.currentLine=327682;
 //BA.debugLineNum = 327682;BA.debugLine="Dim Opciones As List";
_opciones = new anywheresoftware.b4a.objects.collections.List();
RDebugUtils.currentLine=327683;
 //BA.debugLineNum = 327683;BA.debugLine="Dim resultado As Int";
_resultado = 0;
RDebugUtils.currentLine=327685;
 //BA.debugLineNum = 327685;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=327686;
 //BA.debugLineNum = 327686;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
RDebugUtils.currentLine=327687;
 //BA.debugLineNum = 327687;BA.debugLine="Opciones.Initialize2(Array As String(\"Editar secu";
_opciones.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Editar secuencia","Borrar secuencia","CANCELAR"}));
RDebugUtils.currentLine=327688;
 //BA.debugLineNum = 327688;BA.debugLine="resultado=InputList(Opciones,\"Acción\",-1)";
_resultado = anywheresoftware.b4a.keywords.Common.InputList(_opciones,BA.ObjectToCharSequence("Acción"),(int) (-1),mostCurrent.activityBA);
RDebugUtils.currentLine=327689;
 //BA.debugLineNum = 327689;BA.debugLine="Select resultado";
switch (_resultado) {
case 0: {
RDebugUtils.currentLine=327691;
 //BA.debugLineNum = 327691;BA.debugLine="StartActivity(ConfigurarSecuencia)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._configurarsecuencia.getObject()));
 break; }
case 1: {
RDebugUtils.currentLine=327693;
 //BA.debugLineNum = 327693;BA.debugLine="If Msgbox2(\"¿Está seguro de que quiere borrar l";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro de que quiere borrar la secuencia \""+mostCurrent._starter._secuencia[mostCurrent._starter._secuenciaactiva].Descripcion+"\"?"),BA.ObjectToCharSequence("Borrar secuencia"),"Sí","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
RDebugUtils.currentLine=327694;
 //BA.debugLineNum = 327694;BA.debugLine="For Sec=Starter.SecuenciaActiva To Starter.Num";
{
final int step13 = 1;
final int limit13 = (int) (mostCurrent._starter._numsecuencias-2);
_sec = mostCurrent._starter._secuenciaactiva ;
for (;(step13 > 0 && _sec <= limit13) || (step13 < 0 && _sec >= limit13) ;_sec = ((int)(0 + _sec + step13))  ) {
RDebugUtils.currentLine=327696;
 //BA.debugLineNum = 327696;BA.debugLine="CallSub3(Starter,\"CopiarSecuencias\",Sec+1,Sec";
anywheresoftware.b4a.keywords.Common.CallSubDebug3(processBA,(Object)(mostCurrent._starter.getObject()),"CopiarSecuencias",(Object)(_sec+1),(Object)(_sec));
 }
};
RDebugUtils.currentLine=327699;
 //BA.debugLineNum = 327699;BA.debugLine="Starter.NumSecuencias=Starter.NumSecuencias-1";
mostCurrent._starter._numsecuencias = (int) (mostCurrent._starter._numsecuencias-1);
RDebugUtils.currentLine=327700;
 //BA.debugLineNum = 327700;BA.debugLine="CallSub(Starter,\"Guardar_Configuracion\")";
anywheresoftware.b4a.keywords.Common.CallSubDebug(processBA,(Object)(mostCurrent._starter.getObject()),"Guardar_Configuracion");
RDebugUtils.currentLine=327701;
 //BA.debugLineNum = 327701;BA.debugLine="DibujarPortada";
_dibujarportada();
 };
 break; }
}
;
RDebugUtils.currentLine=327704;
 //BA.debugLineNum = 327704;BA.debugLine="End Sub";
return "";
}
public static String  _botonpictograma_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "botonpictograma_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "botonpictograma_click", null);
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
RDebugUtils.currentLine=262144;
 //BA.debugLineNum = 262144;BA.debugLine="Sub BotonPictograma_click";
RDebugUtils.currentLine=262145;
 //BA.debugLineNum = 262145;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
RDebugUtils.currentLine=262146;
 //BA.debugLineNum = 262146;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=262147;
 //BA.debugLineNum = 262147;BA.debugLine="Starter.SecuenciaActiva=BotonPulsado.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
RDebugUtils.currentLine=262148;
 //BA.debugLineNum = 262148;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
RDebugUtils.currentLine=262149;
 //BA.debugLineNum = 262149;BA.debugLine="End Sub";
return "";
}
public static String  _textosecuencia_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "textosecuencia_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "textosecuencia_click", null);
anywheresoftware.b4a.objects.LabelWrapper _etiquetapulsada = null;
RDebugUtils.currentLine=393216;
 //BA.debugLineNum = 393216;BA.debugLine="Sub TextoSecuencia_click";
RDebugUtils.currentLine=393217;
 //BA.debugLineNum = 393217;BA.debugLine="Dim EtiquetaPulsada As Label";
_etiquetapulsada = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=393218;
 //BA.debugLineNum = 393218;BA.debugLine="EtiquetaPulsada=Sender";
_etiquetapulsada.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=393219;
 //BA.debugLineNum = 393219;BA.debugLine="Starter.SecuenciaActiva=EtiquetaPulsada.Tag";
mostCurrent._starter._secuenciaactiva = (int)(BA.ObjectToNumber(_etiquetapulsada.getTag()));
RDebugUtils.currentLine=393220;
 //BA.debugLineNum = 393220;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
RDebugUtils.currentLine=393221;
 //BA.debugLineNum = 393221;BA.debugLine="End Sub";
return "";
}
public static String  _versecuencias_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(mostCurrent.activityBA, "versecuencias_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "versecuencias_click", null);
RDebugUtils.currentLine=589824;
 //BA.debugLineNum = 589824;BA.debugLine="Sub VerSecuencias_Click";
RDebugUtils.currentLine=589825;
 //BA.debugLineNum = 589825;BA.debugLine="Starter.SecuenciaActiva=1";
mostCurrent._starter._secuenciaactiva = (int) (1);
RDebugUtils.currentLine=589826;
 //BA.debugLineNum = 589826;BA.debugLine="StartActivity(Visualizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._visualizacion.getObject()));
RDebugUtils.currentLine=589827;
 //BA.debugLineNum = 589827;BA.debugLine="End Sub";
return "";
}
}