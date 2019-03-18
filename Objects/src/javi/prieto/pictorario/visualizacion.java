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

public class visualizacion extends Activity implements B4AActivity{
	public static visualizacion mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.visualizacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (visualizacion).");
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
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.visualizacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.visualizacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (visualizacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (visualizacion) Resume **");
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
		return visualizacion.class;
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
        BA.LogInfo("** Activity (visualizacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            visualizacion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (visualizacion) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _v5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreloj = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelagujas = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvvvvvvvvvvvvvvv2 = null;
public static float _vvvvvvvvvvvvvvvvvv0 = 0f;
public static float _vvvvvvvvvvvvvvvvvvv1 = 0f;
public static float _vvvvvvvvvvvvvvvvvvv6 = 0f;
public static int _vvvvvvvvvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvvvvvvvvv4 = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[] _vvvvvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cambiarvista = null;
public anywheresoftware.b4a.objects.ButtonWrapper _volver = null;
public static float[] _vvvvvvvvvvvvvvvvvvv2 = null;
public static float[] _vvvvvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _relojdigital = null;
public static int _vvvvvvvvvvvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvvvvvvvvv6 = 0;
public b4a.example.panelnavigator _navegadoractividades = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progresoactividad = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondopictogramacentral = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _pictogramacentral = null;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvvvvvvv4 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvvvvvv5 = null;
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
public static String  _vvvvvvvvvvvvvvvvvv2(int _i) throws Exception{
 //BA.debugLineNum = 564;BA.debugLine="Sub ActivarBoton(i As Int)";
 //BA.debugLineNum = 572;BA.debugLine="Boton(i).BringToFront()";
mostCurrent._vvvvvvvvvvvvvvvvvv3[_i].BringToFront();
 //BA.debugLineNum = 573;BA.debugLine="DibujarProgreso(i)";
_vvvvvvvvvvvvvvvvvv4(_i);
 //BA.debugLineNum = 574;BA.debugLine="NavegadorActividades.SetSelectPanel(i)";
mostCurrent._navegadoractividades._vvvv1(_i);
 //BA.debugLineNum = 575;BA.debugLine="NavegadorActividades.SetVisible(True)";
mostCurrent._navegadoractividades._vvvv3(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 577;BA.debugLine="PictogramaCentral.Bitmap=LoadBitmap(Starter.DirPi";
mostCurrent._pictogramacentral.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv3._vvvv5,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_i].Pictograma+".png").getObject()));
 //BA.debugLineNum = 578;BA.debugLine="FondoPictogramaCentral.Visible=True";
mostCurrent._fondopictogramacentral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 579;BA.debugLine="PictogramaCentral.Visible=True";
mostCurrent._pictogramacentral.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 580;BA.debugLine="FondoPictogramaCentral.BringToFront";
mostCurrent._fondopictogramacentral.BringToFront();
 //BA.debugLineNum = 581;BA.debugLine="PictogramaCentral.BringToFront";
mostCurrent._pictogramacentral.BringToFront();
 //BA.debugLineNum = 626;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 71;BA.debugLine="DibujarTablero";
_vvvvvvvvvvvvvvvvvv5();
 //BA.debugLineNum = 72;BA.debugLine="Temporizador.Initialize(\"Temporizador\",500)";
_v5.Initialize(processBA,"Temporizador",(long) (500));
 //BA.debugLineNum = 73;BA.debugLine="Temporizador.Enabled=True";
_v5.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.visualizacion parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.visualizacion parent;
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
 //BA.debugLineNum = 682;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 683;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 685;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 472;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 474;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 468;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 470;BA.debugLine="End Sub";
return "";
}
public static String  _avisoactividad() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
anywheresoftware.b4a.phone.RingtoneManagerWrapper _sonido = null;
String _texto = "";
anywheresoftware.b4a.objects.NotificationWrapper _n = null;
 //BA.debugLineNum = 650;BA.debugLine="Sub AvisoActividad";
 //BA.debugLineNum = 653;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 654;BA.debugLine="Vibracion.Vibrate(1000)";
_vibracion.Vibrate(processBA,(long) (1000));
 //BA.debugLineNum = 655;BA.debugLine="Dim Sonido As RingtoneManager";
_sonido = new anywheresoftware.b4a.phone.RingtoneManagerWrapper();
 //BA.debugLineNum = 656;BA.debugLine="Sonido.Play(Sonido.GetDefault(Sonido.TYPE_NOTIFIC";
_sonido.Play(processBA,_sonido.GetDefault(_sonido.TYPE_NOTIFICATION));
 //BA.debugLineNum = 658;BA.debugLine="Dim Texto=Starter.Secuencia(Starter.ProximaAlarma";
_texto = mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._v0].Descripcion+" ➞ "+mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._v0][mostCurrent._vvvvvvvvvv3._vv1].Descripcion;
 //BA.debugLineNum = 660;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 661;BA.debugLine="n.Initialize2(n.IMPORTANCE_HIGH)";
_n.Initialize2(_n.IMPORTANCE_HIGH);
 //BA.debugLineNum = 662;BA.debugLine="n.OnGoingEvent = False";
_n.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 663;BA.debugLine="n.Sound = True";
_n.setSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 664;BA.debugLine="n.Vibrate = True";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 665;BA.debugLine="n.Light = True";
_n.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 666;BA.debugLine="n.Insistent = True";
_n.setInsistent(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 667;BA.debugLine="n.AutoCancel = True";
_n.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 668;BA.debugLine="n.Icon = \"iconw\"";
_n.setIcon("iconw");
 //BA.debugLineNum = 669;BA.debugLine="n.SetInfo(\"AVISO DE INICIO DE ACTIVIDAD\" ,Texto,";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence("AVISO DE INICIO DE ACTIVIDAD"),BA.ObjectToCharSequence(_texto),(Object)(""));
 //BA.debugLineNum = 670;BA.debugLine="n.Notify(2)";
_n.Notify((int) (2));
 //BA.debugLineNum = 671;BA.debugLine="Msgbox2(Texto,\"Aviso de inicio de actividad\",\"Ace";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_texto),BA.ObjectToCharSequence("Aviso de inicio de actividad"),"Aceptar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv3._vvvv5,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._v0][mostCurrent._vvvvvvvvvv3._vv1].Pictograma+".png").getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 673;BA.debugLine="n.Cancel(2)";
_n.Cancel((int) (2));
 //BA.debugLineNum = 676;BA.debugLine="CallSub(Starter,\"CalcularProximaAlarma\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"CalcularProximaAlarma");
 //BA.debugLineNum = 679;BA.debugLine="End Sub";
return "";
}
public static String  _botonactividad_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
int _seleccion = 0;
 //BA.debugLineNum = 453;BA.debugLine="Sub BotonActividad_click";
 //BA.debugLineNum = 454;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 455;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 457;BA.debugLine="Dim Seleccion As Int";
_seleccion = 0;
 //BA.debugLineNum = 458;BA.debugLine="Seleccion=BotonPulsado.Tag";
_seleccion = (int)(BA.ObjectToNumber(_botonpulsado.getTag()));
 //BA.debugLineNum = 460;BA.debugLine="If (Seleccion<>BotonSeleccionado And BotonSelecci";
if ((_seleccion!=_vvvvvvvvvvvvvvvvvv6 && _vvvvvvvvvvvvvvvvvv6>-1)) { 
 //BA.debugLineNum = 461;BA.debugLine="DibujarActividad(BotonSeleccionado,True)";
_vvvvvvvvvvvvvvvvvv7(_vvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 464;BA.debugLine="BotonSeleccionado=Seleccion";
_vvvvvvvvvvvvvvvvvv6 = _seleccion;
 //BA.debugLineNum = 465;BA.debugLine="ActivarBoton(BotonSeleccionado)";
_vvvvvvvvvvvvvvvvvv2(_vvvvvvvvvvvvvvvvvv6);
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
 //BA.debugLineNum = 481;BA.debugLine="Sub CambiarVista_Click";
 //BA.debugLineNum = 482;BA.debugLine="If Starter.AplicacionProtegida==True Then";
if (mostCurrent._vvvvvvvvvv3._vvv7==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 484;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 485;BA.debugLine="Vibracion.Vibrate(100)";
_vibracion.Vibrate(processBA,(long) (100));
 //BA.debugLineNum = 486;BA.debugLine="ContadorCandado=ContadorCandado+1";
_vvvvvvvvvv6 = (int) (_vvvvvvvvvv6+1);
 }else {
 //BA.debugLineNum = 488;BA.debugLine="Starter.Secuencia(Starter.SecuenciaActiva).table";
mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo = (int) (((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo)+1)%4);
 //BA.debugLineNum = 489;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 491;BA.debugLine="ToastMessageShow(\"Cambiado tipo de tablero a \"&S";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cambiado tipo de tablero a "+mostCurrent._vvvvvvvvvv3._vv4[mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo]),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 492;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 493;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 494;BA.debugLine="BotonSeleccionado=-1";
_vvvvvvvvvvvvvvvvvv6 = (int) (-1);
 //BA.debugLineNum = 495;BA.debugLine="DibujarTablero";
_vvvvvvvvvvvvvvvvvv5();
 };
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_longclick() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
 //BA.debugLineNum = 499;BA.debugLine="Sub CambiarVista_LongClick";
 //BA.debugLineNum = 500;BA.debugLine="If ( Starter.AplicacionProtegida==True And Contad";
if ((mostCurrent._vvvvvvvvvv3._vvv7==anywheresoftware.b4a.keywords.Common.True && _vvvvvvvvvv6>0)) { 
 //BA.debugLineNum = 501;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 502;BA.debugLine="Vibracion.Vibrate(300)";
_vibracion.Vibrate(processBA,(long) (300));
 //BA.debugLineNum = 503;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 505;BA.debugLine="ContadorCandado=0";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 506;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv7(int _numactividad,boolean _deseleccionar) throws Exception{
float _radioactividad = 0f;
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
float _distanciaadibujar = 0f;
 //BA.debugLineNum = 276;BA.debugLine="Sub DibujarActividad(NumActividad As Int,Deselecci";
 //BA.debugLineNum = 277;BA.debugLine="Dim RadioActividad=0.7 As Float";
_radioactividad = (float) (0.7);
 //BA.debugLineNum = 280;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo>1 || (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==1 && mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_fin>11) || (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==0 && mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 283;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_inicio;
 //BA.debugLineNum = 284;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].minuto_inicio;
 //BA.debugLineNum = 285;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_fin;
 //BA.debugLineNum = 286;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].minuto_fin;
 //BA.debugLineNum = 287;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 288;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 289;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 291;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 292;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 296;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 297;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 300;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 301;BA.debugLine="Dim DistanciaADibujar=2 As Float 'Distancia a la";
_distanciaadibujar = (float) (2);
 //BA.debugLineNum = 302;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1);
 //BA.debugLineNum = 303;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
_recorte.LineTo(_horaminuto_x((float) (_horainicio),(float) (_mininicio),_distanciaadibujar),_horaminuto_y((float) (_horainicio),(float) (_mininicio),_distanciaadibujar));
 //BA.debugLineNum = 304;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
_recorte.LineTo(_horaminuto_x(_horamitad,_minutomitad,_distanciaadibujar),_horaminuto_y(_horamitad,_minutomitad,_distanciaadibujar));
 //BA.debugLineNum = 305;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,Dist";
_recorte.LineTo(_horaminuto_x((float) (_horafin),(float) (_minfin),_distanciaadibujar),_horaminuto_y((float) (_horafin),(float) (_minfin),_distanciaadibujar));
 //BA.debugLineNum = 306;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
_recorte.LineTo(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1);
 //BA.debugLineNum = 308;BA.debugLine="AnguloInicio(NumActividad)=NormalizarAngulo(ATan";
_vvvvvvvvvvvvvvvvvvv2[_numactividad] = _vvvvvvvvvvvvvvvvvvv3((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horainicio),(float) (_mininicio),_distanciaadibujar)-_vvvvvvvvvvvvvvvvvvv1,_horaminuto_x((float) (_horainicio),(float) (_mininicio),_distanciaadibujar)-_vvvvvvvvvvvvvvvvvv0)%360));
 //BA.debugLineNum = 309;BA.debugLine="AnguloFin(NumActividad)=NormalizarAngulo(ATan2D(";
_vvvvvvvvvvvvvvvvvvv4[_numactividad] = _vvvvvvvvvvvvvvvvvvv3((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horafin),(float) (_minfin),_distanciaadibujar)-_vvvvvvvvvvvvvvvvvvv1,_horaminuto_x((float) (_horafin),(float) (_minfin),_distanciaadibujar)-_vvvvvvvvvvvvvvvvvv0)%360));
 //BA.debugLineNum = 310;BA.debugLine="If AnguloFin(NumActividad)=0 Then";
if (_vvvvvvvvvvvvvvvvvvv4[_numactividad]==0) { 
 //BA.debugLineNum = 311;BA.debugLine="AnguloFin(NumActividad)=360";
_vvvvvvvvvvvvvvvvvvv4[_numactividad] = (float) (360);
 };
 //BA.debugLineNum = 315;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 316;BA.debugLine="If Deseleccionar==True Then";
if (_deseleccionar==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 317;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*Radio";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*_radioactividad),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.False,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 319;BA.debugLine="If BotonSeleccionado==NumActividad And Deselecci";
if (_vvvvvvvvvvvvvvvvvv6==_numactividad && _deseleccionar==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 320;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*Radio";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*_radioactividad),mostCurrent._vvvvvvvvvv3._vv7[_numactividad],anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 321;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*Radio";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*_radioactividad),anywheresoftware.b4a.keywords.Common.Colors.Red,anywheresoftware.b4a.keywords.Common.False,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else {
 //BA.debugLineNum = 323;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*Radio";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*_radioactividad),mostCurrent._vvvvvvvvvv3._vv7[_numactividad],anywheresoftware.b4a.keywords.Common.True,(float) (0));
 };
 //BA.debugLineNum = 325;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.RemoveClip();
 };
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvv7() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rectangulovacio = null;
int _segundoactual = 0;
float _angulominuto = 0f;
float _angulosegundo = 0f;
 //BA.debugLineNum = 242;BA.debugLine="Sub DibujarAgujas";
 //BA.debugLineNum = 243;BA.debugLine="Dim RectanguloVacio As Rect";
_rectangulovacio = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 244;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
_segundoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 246;BA.debugLine="HoraActual=DateTime.GetHour(DateTime.Now)";
_vvvvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 247;BA.debugLine="MinutoActual=DateTime.GetMinute(DateTime.Now)";
_vvvvvvvvvvvvvvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 249;BA.debugLine="If BotonSeleccionado>-1 Then";
if (_vvvvvvvvvvvvvvvvvv6>-1) { 
 //BA.debugLineNum = 250;BA.debugLine="DibujarActividad(BotonSeleccionado,False)";
_vvvvvvvvvvvvvvvvvv7(_vvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 253;BA.debugLine="RectanguloVacio.Initialize(0,0,PanelAgujas.Width,";
_rectangulovacio.Initialize((int) (0),(int) (0),mostCurrent._panelagujas.getWidth(),mostCurrent._panelagujas.getHeight());
 //BA.debugLineNum = 254;BA.debugLine="PantallaAgujas.DrawRect(RectanguloVacio, Colors.T";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawRect((android.graphics.Rect)(_rectangulovacio.getObject()),anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 256;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).ta";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.indicar_hora>0 && _vvvvvvvvvvvvvvvvvvv0>=_vvvvvvvvvvvvvvvvvvvv3 && _vvvvvvvvvvvvvvvvvvv0<=_vvvvvvvvvvvvvvvvvvvv4)) { 
 //BA.debugLineNum = 257;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==3 || mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.indicar_hora==1)) { 
 //BA.debugLineNum = 258;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawLine(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,_horaminuto_x((float) (_vvvvvvvvvvvvvvvvvvv0),(float) (_vvvvvvvvvvvvvvvvvvvv1),(float) (0.8)),_horaminuto_y((float) (_vvvvvvvvvvvvvvvvvvv0),(float) (_vvvvvvvvvvvvvvvvvvvv1),(float) (0.8)),mostCurrent._vvvvvvvvvv3._vvvv3,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 }else {
 //BA.debugLineNum = 260;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawLine(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,_horaminuto_x((float) (_vvvvvvvvvvvvvvvvvvv0),(float) (_vvvvvvvvvvvvvvvvvvvv1),(float) (0.7)),_horaminuto_y((float) (_vvvvvvvvvvvvvvvvvvv0),(float) (_vvvvvvvvvvvvvvvvvvvv1),(float) (0.6)),mostCurrent._vvvvvvvvvv3._vvvv1,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 261;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.indicar_hora>1)) { 
 //BA.debugLineNum = 262;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
_angulominuto = (float) (270+(_vvvvvvvvvvvvvvvvvvvv1*6));
 //BA.debugLineNum = 263;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centro";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawLine(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvv0+anywheresoftware.b4a.keywords.Common.CosD(_angulominuto)*_vvvvvvvvvvvvvvvvvvv6*0.8),(float) (_vvvvvvvvvvvvvvvvvvv1+anywheresoftware.b4a.keywords.Common.SinD(_angulominuto)*_vvvvvvvvvvvvvvvvvvv6*0.75),mostCurrent._vvvvvvvvvv3._vvvv2,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6))));
 //BA.debugLineNum = 264;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva)";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.indicar_hora>2)) { 
 //BA.debugLineNum = 265;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
_angulosegundo = (float) (270+(_segundoactual*6));
 //BA.debugLineNum = 266;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centr";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawLine(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvv0+anywheresoftware.b4a.keywords.Common.CosD(_angulosegundo)*_vvvvvvvvvvvvvvvvvvv6*0.9),(float) (_vvvvvvvvvvvvvvvvvvv1+anywheresoftware.b4a.keywords.Common.SinD(_angulosegundo)*_vvvvvvvvvvvvvvvvvvv6*0.9),mostCurrent._vvvvvvvvvv3._vvvv3,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))));
 };
 };
 };
 };
 //BA.debugLineNum = 273;BA.debugLine="PantallaAgujas.DrawCircle(CentroX,CentroY,Radio*0";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*0.1),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvv5(int _numactividad) throws Exception{
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
float _distanciaboton = 0f;
float _tamañoicono = 0f;
float _botonx = 0f;
float _botony = 0f;
 //BA.debugLineNum = 331;BA.debugLine="Sub DibujarBoton(NumActividad As Int)";
 //BA.debugLineNum = 334;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo>1 || (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==1 && mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_fin>11) || (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==0 && mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 337;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_inicio;
 //BA.debugLineNum = 338;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].minuto_inicio;
 //BA.debugLineNum = 339;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].hora_fin;
 //BA.debugLineNum = 340;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].minuto_fin;
 //BA.debugLineNum = 341;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 342;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 343;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 345;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 346;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 350;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 351;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 354;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
mostCurrent._vvvvvvvvvvvvvvvvvv3[_numactividad].Initialize(mostCurrent.activityBA,"BotonActividad");
 //BA.debugLineNum = 355;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
mostCurrent._vvvvvvvvvvvvvvvvvv3[_numactividad].setTag((Object)(_numactividad));
 //BA.debugLineNum = 356;BA.debugLine="Dim DistanciaBoton=0.4+0.1*(NumActividad Mod 3)";
_distanciaboton = (float) (0.4+0.1*(_numactividad%3));
 //BA.debugLineNum = 357;BA.debugLine="Dim TamañoIcono=Starter.Secuencia(Starter.Secuen";
_tamañoicono = (float) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tam_icono*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA));
 //BA.debugLineNum = 358;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
_botonx = (float) (_horaminuto_x(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 359;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
_botony = (float) (_horaminuto_y(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 365;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tam_icono>0)) { 
 //BA.debugLineNum = 366;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Bot";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvvv3[_numactividad].getObject()),(int) (_botonx),(int) (_botony),(int) (_tamañoicono),(int) (_tamañoicono));
 //BA.debugLineNum = 367;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitm";
mostCurrent._vvvvvvvvvvvvvvvvvv3[_numactividad].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv3._vvvv5,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_numactividad].Pictograma+".png").getObject()));
 };
 //BA.debugLineNum = 371;BA.debugLine="HoraActual=DateTime.GetHour(DateTime.Now)";
_vvvvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 372;BA.debugLine="MinutoActual=DateTime.GetMinute(DateTime.Now)";
_vvvvvvvvvvvvvvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 373;BA.debugLine="If (  HoraActual*60+MinutoActual>=HoraInicio*60+";
if ((_vvvvvvvvvvvvvvvvvvv0*60+_vvvvvvvvvvvvvvvvvvvv1>=_horainicio*60+_mininicio && _vvvvvvvvvvvvvvvvvvv0*60+_vvvvvvvvvvvvvvvvvvvv1<_horafin*60+_minfin)) { 
 //BA.debugLineNum = 374;BA.debugLine="BotonSeleccionado=NumActividad";
_vvvvvvvvvvvvvvvvvv6 = _numactividad;
 //BA.debugLineNum = 375;BA.debugLine="ActivarBoton(NumActividad)";
_vvvvvvvvvvvvvvvvvv2(_numactividad);
 };
 };
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv4(int _i) throws Exception{
float _minhoraprogreso = 0f;
float _horaactualprogreso = 0f;
float _maxhoraprogreso = 0f;
float _progreso = 0f;
 //BA.debugLineNum = 628;BA.debugLine="Sub DibujarProgreso(i As Int)";
 //BA.debugLineNum = 629;BA.debugLine="Dim MinHoraProgreso,HoraActualProgreso,MaxHoraPro";
_minhoraprogreso = 0f;
_horaactualprogreso = 0f;
_maxhoraprogreso = 0f;
_progreso = 0f;
 //BA.debugLineNum = 631;BA.debugLine="If (i>-1) Then";
if ((_i>-1)) { 
 //BA.debugLineNum = 632;BA.debugLine="MinHoraProgreso=(Starter.ActividadSecuencia(Star";
_minhoraprogreso = (float) ((mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_i].hora_inicio*60+mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_i].minuto_inicio)*60);
 //BA.debugLineNum = 633;BA.debugLine="HoraActualProgreso=DateTime.GetHour(DateTime.Now";
_horaactualprogreso = (float) (anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow())*60*60+anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow())*60+anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 634;BA.debugLine="MaxHoraProgreso=(Starter.ActividadSecuencia(Star";
_maxhoraprogreso = (float) ((mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_i].hora_fin*60+mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_i].minuto_fin)*60);
 //BA.debugLineNum = 635;BA.debugLine="Progreso=100*(HoraActualProgreso-MinHoraProgreso";
_progreso = (float) (100*(_horaactualprogreso-_minhoraprogreso)/(double)(_maxhoraprogreso-_minhoraprogreso));
 //BA.debugLineNum = 636;BA.debugLine="ProgresoActividad.Progress=Progreso";
mostCurrent._progresoactividad.setProgress((int) (_progreso));
 //BA.debugLineNum = 637;BA.debugLine="If (Progreso>=0 And Progreso<=100) Then";
if ((_progreso>=0 && _progreso<=100)) { 
 //BA.debugLineNum = 638;BA.debugLine="ProgresoActividad.Visible=True";
mostCurrent._progresoactividad.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 639;BA.debugLine="ProgresoActividad.BringToFront";
mostCurrent._progresoactividad.BringToFront();
 }else {
 //BA.debugLineNum = 641;BA.debugLine="ProgresoActividad.Visible=False";
mostCurrent._progresoactividad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 644;BA.debugLine="ProgresoActividad.Visible=False";
mostCurrent._progresoactividad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv5() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
int _hora = 0;
float _x = 0f;
float _y = 0f;
anywheresoftware.b4a.objects.LabelWrapper _numerohora = null;
int _nactividad = 0;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 77;BA.debugLine="Sub DibujarTablero()";
 //BA.debugLineNum = 79;BA.debugLine="CentroX=50%x";
_vvvvvvvvvvvvvvvvvv0 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 80;BA.debugLine="CentroY=60%x";
_vvvvvvvvvvvvvvvvvvv1 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 81;BA.debugLine="Radio=45%x";
_vvvvvvvvvvvvvvvvvvv6 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 83;BA.debugLine="Pantalla.Initialize(PanelReloj)";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.Initialize((android.view.View)(mostCurrent._panelreloj.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="PantallaAgujas.Initialize(PanelAgujas)";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2.Initialize((android.view.View)(mostCurrent._panelagujas.getObject()));
 //BA.debugLineNum = 87;BA.debugLine="FondoPictogramaCentral.Left=CentroX-40dip";
mostCurrent._fondopictogramacentral.setLeft((int) (_vvvvvvvvvvvvvvvvvv0-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))));
 //BA.debugLineNum = 88;BA.debugLine="FondoPictogramaCentral.Top=CentroY-40dip";
mostCurrent._fondopictogramacentral.setTop((int) (_vvvvvvvvvvvvvvvvvvv1-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))));
 //BA.debugLineNum = 89;BA.debugLine="FondoPictogramaCentral.Width=80dip";
mostCurrent._fondopictogramacentral.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 90;BA.debugLine="FondoPictogramaCentral.Height=80dip";
mostCurrent._fondopictogramacentral.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 91;BA.debugLine="PictogramaCentral.Left=0";
mostCurrent._pictogramacentral.setLeft((int) (0));
 //BA.debugLineNum = 92;BA.debugLine="PictogramaCentral.Top=0";
mostCurrent._pictogramacentral.setTop((int) (0));
 //BA.debugLineNum = 93;BA.debugLine="PictogramaCentral.Width=80dip";
mostCurrent._pictogramacentral.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 94;BA.debugLine="PictogramaCentral.Height=80dip";
mostCurrent._pictogramacentral.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 97;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo<2) { 
 //BA.debugLineNum = 98;BA.debugLine="MinHora=1";
_vvvvvvvvvvvvvvvvvvvv3 = (int) (1);
 //BA.debugLineNum = 99;BA.debugLine="MaxHora=12";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (12);
 }else if(mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 101;BA.debugLine="MinHora=1";
_vvvvvvvvvvvvvvvvvvvv3 = (int) (1);
 //BA.debugLineNum = 102;BA.debugLine="MaxHora=24";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (24);
 }else {
 //BA.debugLineNum = 104;BA.debugLine="MinHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvvvvvvvvvvvvvv3 = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][(int) (0)].hora_inicio;
 //BA.debugLineNum = 105;BA.debugLine="MaxHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvvvvvvvvvvvvvv4 = mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][(int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 106;BA.debugLine="If (Starter.ActividadSecuencia(Starter.Secuencia";
if ((mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][(int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1)].minuto_fin!=0)) { 
 //BA.debugLineNum = 107;BA.debugLine="MaxHora=MaxHora+1";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (_vvvvvvvvvvvvvvvvvvvv4+1);
 };
 };
 //BA.debugLineNum = 112;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo<3) { 
 //BA.debugLineNum = 114;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 115;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,_vvvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 }else {
 //BA.debugLineNum = 117;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
_recorte.Initialize(_vvvvvvvvvvvvvvvvvv0,(float) (_vvvvvvvvvvvvvvvvvvv1+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)));
 //BA.debugLineNum = 120;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(114)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvv0),(float) ((anywheresoftware.b4a.keywords.Common.SinD(114)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvvv1));
 //BA.debugLineNum = 121;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 122;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 123;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 124;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 125;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(81)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvv0),(float) ((anywheresoftware.b4a.keywords.Common.SinD(81)*_vvvvvvvvvvvvvvvvvvv6)+_vvvvvvvvvvvvvvvvvvv1));
 //BA.debugLineNum = 126;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 127;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,(float) (_vvvvvvvvvvvvvvvvvvv6*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 128;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.RemoveClip();
 //BA.debugLineNum = 130;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1);
 //BA.debugLineNum = 131;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(116)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvv0),(float) ((anywheresoftware.b4a.keywords.Common.SinD(116)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvvv1));
 //BA.debugLineNum = 132;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 133;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 134;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 135;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 136;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(80)*_vvvvvvvvvvvvvvvvvvv6*3)+_vvvvvvvvvvvvvvvvvv0),(float) ((anywheresoftware.b4a.keywords.Common.SinD(80)*_vvvvvvvvvvvvvvvvvvv6)+_vvvvvvvvvvvvvvvvvvv1));
 //BA.debugLineNum = 137;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 138;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_vvvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvvv1,_vvvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 139;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.RemoveClip();
 };
 //BA.debugLineNum = 142;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
{
final int step53 = 1;
final int limit53 = _vvvvvvvvvvvvvvvvvvvv4;
_hora = _vvvvvvvvvvvvvvvvvvvv3 ;
for (;_hora <= limit53 ;_hora = _hora + step53 ) {
 //BA.debugLineNum = 144;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 145;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 147;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
mostCurrent._vvvvvvvvvvvvvvvvvvv5.DrawCircle(_x,_y,(float) (_vvvvvvvvvvvvvvvvvvv6*0.02),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 148;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 149;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 150;BA.debugLine="Dim NumeroHora As Label";
_numerohora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 151;BA.debugLine="NumeroHora.Initialize(\"\")";
_numerohora.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 152;BA.debugLine="NumeroHora.Text=Hora24a12((Hora) Mod 24)";
_numerohora.setText(BA.ObjectToCharSequence(_vvvvvvvvvvvvvvvvvvvv6((int) ((_hora)%24))));
 //BA.debugLineNum = 153;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
_numerohora.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 154;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
_numerohora.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 155;BA.debugLine="NumeroHora.TextSize=15";
_numerohora.setTextSize((float) (15));
 //BA.debugLineNum = 156;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
mostCurrent._activity.AddView((android.view.View)(_numerohora.getObject()),(int) (_x-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (_y-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 160;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo,(int) (0),(int) (1),(int) (2))) {
case 0: {
 //BA.debugLineNum = 162;BA.debugLine="MinHora=0";
_vvvvvvvvvvvvvvvvvvvv3 = (int) (0);
 //BA.debugLineNum = 163;BA.debugLine="MaxHora=11";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (11);
 break; }
case 1: {
 //BA.debugLineNum = 165;BA.debugLine="MinHora=12";
_vvvvvvvvvvvvvvvvvvvv3 = (int) (12);
 //BA.debugLineNum = 166;BA.debugLine="MaxHora=23";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (23);
 break; }
case 2: {
 //BA.debugLineNum = 168;BA.debugLine="MinHora=0";
_vvvvvvvvvvvvvvvvvvvv3 = (int) (0);
 //BA.debugLineNum = 169;BA.debugLine="MaxHora=23";
_vvvvvvvvvvvvvvvvvvvv4 = (int) (23);
 break; }
}
;
 //BA.debugLineNum = 173;BA.debugLine="For NActividad=0 To Starter.MaxActividades-1";
{
final int step78 = 1;
final int limit78 = (int) (mostCurrent._vvvvvvvvvv3._vv3-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit78 ;_nactividad = _nactividad + step78 ) {
 //BA.debugLineNum = 174;BA.debugLine="AnguloInicio(NActividad)=-1";
_vvvvvvvvvvvvvvvvvvv2[_nactividad] = (float) (-1);
 //BA.debugLineNum = 175;BA.debugLine="AnguloFin(NActividad)=-1";
_vvvvvvvvvvvvvvvvvvv4[_nactividad] = (float) (-1);
 }
};
 //BA.debugLineNum = 179;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step82 = 1;
final int limit82 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit82 ;_nactividad = _nactividad + step82 ) {
 //BA.debugLineNum = 180;BA.debugLine="DibujarActividad(NActividad,False)";
_vvvvvvvvvvvvvvvvvv7(_nactividad,anywheresoftware.b4a.keywords.Common.False);
 }
};
 //BA.debugLineNum = 184;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step85 = 1;
final int limit85 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit85 ;_nactividad = _nactividad + step85 ) {
 //BA.debugLineNum = 185;BA.debugLine="DibujarBoton(NActividad)";
_vvvvvvvvvvvvvvvvvvvv5(_nactividad);
 }
};
 //BA.debugLineNum = 194;BA.debugLine="If ( Starter.AplicacionProtegida==True ) Then";
if ((mostCurrent._vvvvvvvvvv3._vvv7==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 196;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(File.";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"candado.png").getObject()));
 //BA.debugLineNum = 197;BA.debugLine="Volver.Visible=False";
mostCurrent._volver.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 199;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva";
switch (BA.switchObjectToInt(mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo,(int) (0),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 201;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(Fil";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"manana.png").getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 203;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(Fil";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tarde.png").getObject()));
 break; }
case 2: {
 //BA.debugLineNum = 205;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(Fil";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dia.png").getObject()));
 break; }
case 3: {
 //BA.debugLineNum = 207;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(Fil";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fila.png").getObject()));
 break; }
}
;
 };
 //BA.debugLineNum = 211;BA.debugLine="DibujarAgujas";
_vvvvvvvvvvvvvvvvvvv7();
 //BA.debugLineNum = 212;BA.debugLine="Dim cs As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 214;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step105 = 1;
final int limit105 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit105 ;_nactividad = _nactividad + step105 ) {
 //BA.debugLineNum = 215;BA.debugLine="NavegadorActividades.Add(NActividad,Starter.Colo";
mostCurrent._navegadoractividades._v6(BA.NumberToString(_nactividad),mostCurrent._vvvvvvvvvv3._vv7[_nactividad],anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv3._vvvv5,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].Pictograma+".png"));
 //BA.debugLineNum = 216;BA.debugLine="DescripcionesActividades(NActividad).Initialize(";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 217;BA.debugLine="DescripcionesActividades(NActividad).Gravity=Gra";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 218;BA.debugLine="DescripcionesActividades(NActividad).Text=Starte";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].Descripcion));
 //BA.debugLineNum = 219;BA.debugLine="DescripcionesActividades(NActividad).TextColor=C";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 220;BA.debugLine="DescripcionesActividades(NActividad).Typeface=Ty";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 221;BA.debugLine="DescripcionesActividades(NActividad).TextSize=20";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].setTextSize((float) (20));
 //BA.debugLineNum = 222;BA.debugLine="NavegadorActividades.GetPanel(NActividad).AddVie";
mostCurrent._navegadoractividades._vv5(_nactividad).AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvvvvv7[_nactividad].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._navegadoractividades._vv5(_nactividad).getHeight()/(double)2));
 //BA.debugLineNum = 223;BA.debugLine="HorasActividades(NActividad).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 224;BA.debugLine="HorasActividades(NActividad).Gravity=Gravity.CEN";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 228;BA.debugLine="HorasActividades(NActividad).Text=cs.Initialize.";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].setText(BA.ObjectToCharSequence(_cs.Initialize().Append(BA.ObjectToCharSequence("desde ")).Bold().Append(BA.ObjectToCharSequence(_vvvvvvvvvvv4(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].hora_inicio,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].minuto_inicio))).Pop().Append(BA.ObjectToCharSequence("           hasta ")).Bold().Append(BA.ObjectToCharSequence(_vvvvvvvvvvv4(mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].hora_fin,mostCurrent._vvvvvvvvvv3._vvv3[mostCurrent._vvvvvvvvvv3._vvv1][_nactividad].minuto_fin))).Pop().getObject()));
 //BA.debugLineNum = 230;BA.debugLine="HorasActividades(NActividad).TextColor=Colors.Bl";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 231;BA.debugLine="HorasActividades(NActividad).TextSize=16";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].setTextSize((float) (16));
 //BA.debugLineNum = 232;BA.debugLine="NavegadorActividades.GetPanel(NActividad).AddVie";
mostCurrent._navegadoractividades._vv5(_nactividad).AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvvvvvv0[_nactividad].getObject()),(int) (0),(int) (mostCurrent._navegadoractividades._vv5(_nactividad).getHeight()/(double)2),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._navegadoractividades._vv5(_nactividad).getHeight()/(double)2));
 //BA.debugLineNum = 234;BA.debugLine="ProgresoActividad.Top=CambiarVista.Top+CambiarVi";
mostCurrent._progresoactividad.setTop((int) (mostCurrent._cambiarvista.getTop()+mostCurrent._cambiarvista.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))+mostCurrent._navegadoractividades._vv5(_nactividad).getHeight()/(double)2));
 //BA.debugLineNum = 235;BA.debugLine="ProgresoActividad.Height=10dip";
mostCurrent._progresoactividad.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 236;BA.debugLine="ProgresoActividad.Visible=False";
mostCurrent._progresoactividad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }
};
 //BA.debugLineNum = 238;BA.debugLine="NavegadorActividades.SetVisible(False)";
mostCurrent._navegadoractividades._vvvv3(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv4(int _hora,int _minutos) throws Exception{
String _salida = "";
int _horamodificada = 0;
 //BA.debugLineNum = 434;BA.debugLine="Sub EscribirHora(Hora As Int, Minutos As Int) As S";
 //BA.debugLineNum = 435;BA.debugLine="Dim Salida As String";
_salida = "";
 //BA.debugLineNum = 436;BA.debugLine="Dim HoraModificada As Int";
_horamodificada = 0;
 //BA.debugLineNum = 437;BA.debugLine="If (Starter.Formato24h==False And Hora>11) Then";
if ((mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.False && _hora>11)) { 
 //BA.debugLineNum = 438;BA.debugLine="HoraModificada=Hora-12";
_horamodificada = (int) (_hora-12);
 }else {
 //BA.debugLineNum = 440;BA.debugLine="HoraModificada=Hora";
_horamodificada = _hora;
 };
 //BA.debugLineNum = 442;BA.debugLine="Salida=NumberFormat(HoraModificada,2,0)&\":\"&Numbe";
_salida = anywheresoftware.b4a.keywords.Common.NumberFormat(_horamodificada,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutos,(int) (2),(int) (0));
 //BA.debugLineNum = 443;BA.debugLine="If Starter.Formato24h==False Then";
if (mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 444;BA.debugLine="If Hora<12 Then";
if (_hora<12) { 
 //BA.debugLineNum = 445;BA.debugLine="Salida=Salida&\" a.m.\"";
_salida = _salida+" a.m.";
 }else {
 //BA.debugLineNum = 447;BA.debugLine="Salida=Salida&\" p.m.\"";
_salida = _salida+" p.m.";
 };
 };
 //BA.debugLineNum = 450;BA.debugLine="Return Salida";
if (true) return _salida;
 //BA.debugLineNum = 451;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private PanelReloj As Panel";
mostCurrent._panelreloj = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private PanelAgujas As Panel";
mostCurrent._panelagujas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja la";
mostCurrent._vvvvvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim PantallaAgujas As Canvas";
mostCurrent._vvvvvvvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
_vvvvvvvvvvvvvvvvvv0 = 0f;
_vvvvvvvvvvvvvvvvvvv1 = 0f;
_vvvvvvvvvvvvvvvvvvv6 = 0f;
 //BA.debugLineNum = 33;BA.debugLine="Dim MinHora,MaxHora As Int";
_vvvvvvvvvvvvvvvvvvvv3 = 0;
_vvvvvvvvvvvvvvvvvvvv4 = 0;
 //BA.debugLineNum = 36;BA.debugLine="Dim Boton(Starter.MaxActividades) As Button";
mostCurrent._vvvvvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvvvv3.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvvvv3[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 38;BA.debugLine="Private CambiarVista As Button";
mostCurrent._cambiarvista = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Volver As Button";
mostCurrent._volver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim AnguloInicio(Starter.MaxActividades) As Float";
_vvvvvvvvvvvvvvvvvvv2 = new float[mostCurrent._vvvvvvvvvv3._vv3];
;
 //BA.debugLineNum = 45;BA.debugLine="Dim AnguloFin(Starter.MaxActividades) As Float";
_vvvvvvvvvvvvvvvvvvv4 = new float[mostCurrent._vvvvvvvvvv3._vv3];
;
 //BA.debugLineNum = 48;BA.debugLine="Private RelojDigital As Label";
mostCurrent._relojdigital = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim HoraActual As Int";
_vvvvvvvvvvvvvvvvvvv0 = 0;
 //BA.debugLineNum = 50;BA.debugLine="Dim MinutoActual As Int";
_vvvvvvvvvvvvvvvvvvvv1 = 0;
 //BA.debugLineNum = 53;BA.debugLine="Dim ContadorCandado=0 As Int";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 56;BA.debugLine="Dim BotonSeleccionado=-1 As Int";
_vvvvvvvvvvvvvvvvvv6 = (int) (-1);
 //BA.debugLineNum = 59;BA.debugLine="Private NavegadorActividades As PanelNavigator";
mostCurrent._navegadoractividades = new b4a.example.panelnavigator();
 //BA.debugLineNum = 60;BA.debugLine="Private DescripcionesActividades(Starter.MaxActiv";
mostCurrent._vvvvvvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvvvvvv7.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvvvvvv7[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 61;BA.debugLine="Private HorasActividades(Starter.MaxActividades)";
mostCurrent._vvvvvvvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.LabelWrapper[mostCurrent._vvvvvvvvvv3._vv3];
{
int d0 = mostCurrent._vvvvvvvvvvvvvvvvvvvv0.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvvvvvvvvv0[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 62;BA.debugLine="Private ProgresoActividad As ProgressBar";
mostCurrent._progresoactividad = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private FondoPictogramaCentral As Panel";
mostCurrent._fondopictogramacentral = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private PictogramaCentral As ImageView";
mostCurrent._pictogramacentral = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvvvvvvvvvv6(int _hora24) throws Exception{
 //BA.debugLineNum = 408;BA.debugLine="Sub Hora24a12 (Hora24 As Int) As Int";
 //BA.debugLineNum = 409;BA.debugLine="If Starter.Formato24h==True Then";
if (mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 410;BA.debugLine="Return Hora24";
if (true) return _hora24;
 }else {
 //BA.debugLineNum = 412;BA.debugLine="If (Hora24==0 Or Hora24==12) Then";
if ((_hora24==0 || _hora24==12)) { 
 //BA.debugLineNum = 413;BA.debugLine="Return 12";
if (true) return (int) (12);
 }else if((_hora24>11)) { 
 //BA.debugLineNum = 415;BA.debugLine="Return Hora24-12";
if (true) return (int) (_hora24-12);
 }else {
 //BA.debugLineNum = 417;BA.debugLine="Return Hora24";
if (true) return _hora24;
 };
 };
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return 0;
}
public static float  _horaminuto_x(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 382;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 383;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==3) { 
 //BA.debugLineNum = 384;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvvvvvvvvvvvvvv3)*300/(double)(_vvvvvvvvvvvvvvvvvvvv4-_vvvvvvvvvvvvvvvvvvvv3));
 //BA.debugLineNum = 385;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD(_angulo)*_vvvvvvvvvvvvvvvvvvv6*_distancia)+_vvvvvvvvvvvvvvvvvv0);
 }else {
 //BA.debugLineNum = 387;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 388;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 389;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 391;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvvvvvvvvvvvvvv6*_distancia)+_vvvvvvvvvvvvvvvvvv0);
 };
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return 0f;
}
public static float  _horaminuto_y(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 395;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 396;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==3) { 
 //BA.debugLineNum = 397;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvvvvvvvvvvvvvv3)*300/(double)(_vvvvvvvvvvvvvvvvvvvv4-_vvvvvvvvvvvvvvvvvvvv3));
 //BA.debugLineNum = 398;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD(_angulo)*_vvvvvvvvvvvvvvvvvvv6*_distancia)+_vvvvvvvvvvvvvvvvvvv1);
 }else {
 //BA.debugLineNum = 400;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 401;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 402;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 404;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvvvvvvvvvvvvvv6*_distancia)+_vvvvvvvvvvvvvvvvvvv1);
 };
 //BA.debugLineNum = 406;BA.debugLine="End Sub";
return 0f;
}
public static String  _navegadoractividades_changepanel(int _npanel) throws Exception{
 //BA.debugLineNum = 708;BA.debugLine="Sub NavegadorActividades_Changepanel(NPanel As Int";
 //BA.debugLineNum = 709;BA.debugLine="If (NPanel<>BotonSeleccionado And BotonSelecciona";
if ((_npanel!=_vvvvvvvvvvvvvvvvvv6 && _vvvvvvvvvvvvvvvvvv6>-1)) { 
 //BA.debugLineNum = 710;BA.debugLine="DibujarActividad(BotonSeleccionado,True)";
_vvvvvvvvvvvvvvvvvv7(_vvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 712;BA.debugLine="BotonSeleccionado=NPanel";
_vvvvvvvvvvvvvvvvvv6 = _npanel;
 //BA.debugLineNum = 713;BA.debugLine="ActivarBoton(BotonSeleccionado)";
_vvvvvvvvvvvvvvvvvv2(_vvvvvvvvvvvvvvvvvv6);
 //BA.debugLineNum = 714;BA.debugLine="End Sub";
return "";
}
public static float  _vvvvvvvvvvvvvvvvvvv3(float _angulo) throws Exception{
 //BA.debugLineNum = 555;BA.debugLine="Sub NormalizarAngulo(Angulo As Float) As Float";
 //BA.debugLineNum = 556;BA.debugLine="Angulo=Angulo+90 'Pone el 0 arriba";
_angulo = (float) (_angulo+90);
 //BA.debugLineNum = 557;BA.debugLine="If Angulo<0 Then";
if (_angulo<0) { 
 //BA.debugLineNum = 558;BA.debugLine="Return Angulo+360";
if (true) return (float) (_angulo+360);
 }else {
 //BA.debugLineNum = 560;BA.debugLine="Return Angulo";
if (true) return _angulo;
 };
 //BA.debugLineNum = 562;BA.debugLine="End Sub";
return 0f;
}
public static boolean  _panelagujas_touch(int _accion,float _x,float _y) throws Exception{
float _angulo = 0f;
int _i = 0;
 //BA.debugLineNum = 537;BA.debugLine="Sub PanelAgujas_Touch(Accion As Int, x As Float, y";
 //BA.debugLineNum = 538;BA.debugLine="Dim Angulo As Float";
_angulo = 0f;
 //BA.debugLineNum = 539;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 540;BA.debugLine="If Accion = Activity.ACTION_DOWN Then";
if (_accion==mostCurrent._activity.ACTION_DOWN) { 
 //BA.debugLineNum = 541;BA.debugLine="Angulo=NormalizarAngulo(ATan2D(y-CentroY,x-Centr";
_angulo = _vvvvvvvvvvvvvvvvvvv3((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_y-_vvvvvvvvvvvvvvvvvvv1,_x-_vvvvvvvvvvvvvvvvvv0)));
 //BA.debugLineNum = 542;BA.debugLine="For i=0 To Starter.Secuencia(Starter.SecuenciaAc";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 543;BA.debugLine="If Angulo>=AnguloInicio(i) And Angulo<=AnguloFi";
if (_angulo>=_vvvvvvvvvvvvvvvvvvv2[_i] && _angulo<=_vvvvvvvvvvvvvvvvvvv4[_i]) { 
 //BA.debugLineNum = 544;BA.debugLine="If (BotonSeleccionado<>i And BotonSeleccionado";
if ((_vvvvvvvvvvvvvvvvvv6!=_i && _vvvvvvvvvvvvvvvvvv6>-1)) { 
 //BA.debugLineNum = 545;BA.debugLine="DibujarActividad(BotonSeleccionado,True)";
_vvvvvvvvvvvvvvvvvv7(_vvvvvvvvvvvvvvvvvv6,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 547;BA.debugLine="ActivarBoton(i)";
_vvvvvvvvvvvvvvvvvv2(_i);
 //BA.debugLineNum = 548;BA.debugLine="BotonSeleccionado=i";
_vvvvvvvvvvvvvvvvvv6 = _i;
 };
 }
};
 };
 //BA.debugLineNum = 552;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return false;
}
public static String  _panelreloj_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 477;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 479;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim Temporizador As Timer";
_v5 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _temporizador_tick() throws Exception{
 //BA.debugLineNum = 512;BA.debugLine="Sub Temporizador_Tick";
 //BA.debugLineNum = 513;BA.debugLine="DibujarAgujas";
_vvvvvvvvvvvvvvvvvvv7();
 //BA.debugLineNum = 514;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvvvvvvvv3._vvv2[mostCurrent._vvvvvvvvvv3._vvv1].tablero.indicar_hora>0) { 
 //BA.debugLineNum = 515;BA.debugLine="If Starter.Formato24h==False Then";
if (mostCurrent._vvvvvvvvvv3._vvv0==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 516;BA.debugLine="RelojDigital.Text=NumberFormat(Hora24a12(HoraAc";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvvvvvvvvvvvvv6(_vvvvvvvvvvvvvvvvvvv0),(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvvvvvvvvvvvvv1,(int) (2),(int) (0))));
 //BA.debugLineNum = 517;BA.debugLine="If HoraActual=0 Then";
if (_vvvvvvvvvvvvvvvvvvv0==0) { 
 //BA.debugLineNum = 518;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" de la no";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" de la noche"));
 }else if(_vvvvvvvvvvvvvvvvvvv0==12) { 
 //BA.debugLineNum = 520;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" del medi";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" del mediodía"));
 }else if((_vvvvvvvvvvvvvvvvvvv0>12)) { 
 //BA.debugLineNum = 522;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" p.m.\"";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" p.m."));
 }else {
 //BA.debugLineNum = 524;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" a.m.\"";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" a.m."));
 };
 }else {
 //BA.debugLineNum = 527;BA.debugLine="RelojDigital.Text=NumberFormat(HoraActual,2,0)&";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvvvvvvvvvvvv0,(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvvvvvvvvvvvvv1,(int) (2),(int) (0))));
 };
 };
 //BA.debugLineNum = 530;BA.debugLine="If BotonSeleccionado>-1 Then";
if (_vvvvvvvvvvvvvvvvvv6>-1) { 
 //BA.debugLineNum = 531;BA.debugLine="NavegadorActividades.SetSelectPanel(BotonSelecci";
mostCurrent._navegadoractividades._vvvv1(_vvvvvvvvvvvvvvvvvv6);
 //BA.debugLineNum = 532;BA.debugLine="DibujarProgreso(BotonSeleccionado)";
_vvvvvvvvvvvvvvvvvv4(_vvvvvvvvvvvvvvvvvv6);
 };
 //BA.debugLineNum = 534;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 535;BA.debugLine="End Sub";
return "";
}
public static String  _volver_click() throws Exception{
 //BA.debugLineNum = 508;BA.debugLine="Sub Volver_Click";
 //BA.debugLineNum = 509;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 510;BA.debugLine="End Sub";
return "";
}
}
