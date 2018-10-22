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
public anywheresoftware.b4a.objects.PanelWrapper _paneldescripcion = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreloj = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagenpictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondopictograma = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelagujas = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvv6 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvvv3 = null;
public static float _vvvvvvv1 = 0f;
public static float _vvvvvvv2 = 0f;
public static float _vvvvvvv7 = 0f;
public static int _vvvvvvvv4 = 0;
public static int _vvvvvvvv5 = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[] _vvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cambiarvista = null;
public anywheresoftware.b4a.objects.LabelWrapper _descripcionpictograma = null;
public anywheresoftware.b4a.objects.ButtonWrapper _volver = null;
public static float[] _vvvvvvv3 = null;
public static float[] _vvvvvvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _relojdigital = null;
public static int _vvvvvvvv1 = 0;
public static int _vvvvvvvv2 = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv6 = null;
public b4a.example.dateutils _vvvvv7 = null;
public javi.prieto.pictorario.main _vvvvvvvv7 = null;
public javi.prieto.pictorario.configurarsecuencia _vvvv4 = null;
public javi.prieto.pictorario.seleccionpictogramas _vvvvv0 = null;
public javi.prieto.pictorario.acercade _vvvv3 = null;
public javi.prieto.pictorario.arranqueautomatico _vvvvvv1 = null;
public javi.prieto.pictorario.avisos _vvvvvv2 = null;
public javi.prieto.pictorario.starter _vvvv2 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _vvvvvv3(int _i) throws Exception{
 //BA.debugLineNum = 416;BA.debugLine="Sub ActivarBoton(i As Int)";
 //BA.debugLineNum = 417;BA.debugLine="ImagenPictograma.Bitmap=LoadBitmap(Starter.DirPic";
mostCurrent._imagenpictograma.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvv2._vvv7,mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].Pictograma+".png").getObject()));
 //BA.debugLineNum = 418;BA.debugLine="TextoPictograma.Text=Starter.ActividadSecuencia(S";
mostCurrent._textopictograma.setText(BA.ObjectToCharSequence(mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].Descripcion.toUpperCase()));
 //BA.debugLineNum = 419;BA.debugLine="DescripcionPictograma.Text=\"De \"&Hora24a12(Starte";
mostCurrent._descripcionpictograma.setText(BA.ObjectToCharSequence("De "+BA.NumberToString(_vvvvvv4(mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].hora_inicio))+_vvvvvv5(mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].minuto_inicio)+" a "+BA.NumberToString(_vvvvvv4(mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].hora_fin))+_vvvvvv5(mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_i].minuto_fin)));
 //BA.debugLineNum = 420;BA.debugLine="FondoPictograma.Color=Starter.Colores(i)";
mostCurrent._fondopictograma.setColor(mostCurrent._vvvv2._vv7[_i]);
 //BA.debugLineNum = 421;BA.debugLine="Boton(i).BringToFront()";
mostCurrent._vvvvvv6[_i].BringToFront();
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 49;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="DibujarTablero";
_vvvvvv7();
 //BA.debugLineNum = 51;BA.debugLine="Temporizador.Initialize(\"Temporizador\",1000)";
_v5.Initialize(processBA,"Temporizador",(long) (1000));
 //BA.debugLineNum = 52;BA.debugLine="Temporizador.Enabled=True";
_v5.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 456;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 457;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 353;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public static String  _avisoactividad() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneVibrate _vibracion = null;
anywheresoftware.b4a.phone.RingtoneManagerWrapper _sonido = null;
String _texto = "";
anywheresoftware.b4a.objects.NotificationWrapper _n = null;
 //BA.debugLineNum = 424;BA.debugLine="Sub AvisoActividad";
 //BA.debugLineNum = 427;BA.debugLine="Dim Vibracion As PhoneVibrate";
_vibracion = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 428;BA.debugLine="Vibracion.Vibrate(1000)";
_vibracion.Vibrate(processBA,(long) (1000));
 //BA.debugLineNum = 429;BA.debugLine="Dim Sonido As RingtoneManager";
_sonido = new anywheresoftware.b4a.phone.RingtoneManagerWrapper();
 //BA.debugLineNum = 430;BA.debugLine="Sonido.Play(Sonido.GetDefault(Sonido.TYPE_NOTIFIC";
_sonido.Play(processBA,_sonido.GetDefault(_sonido.TYPE_NOTIFICATION));
 //BA.debugLineNum = 432;BA.debugLine="Dim Texto=Starter.Secuencia(Starter.ProximaAlarma";
_texto = mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._v0].Descripcion+" ➞ "+mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._v0][mostCurrent._vvvv2._vv1].Descripcion;
 //BA.debugLineNum = 434;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 435;BA.debugLine="n.Initialize2(n.IMPORTANCE_HIGH)";
_n.Initialize2(_n.IMPORTANCE_HIGH);
 //BA.debugLineNum = 436;BA.debugLine="n.OnGoingEvent = False";
_n.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 437;BA.debugLine="n.Sound = True";
_n.setSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="n.Vibrate = True";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 439;BA.debugLine="n.Light = True";
_n.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 440;BA.debugLine="n.Insistent = True";
_n.setInsistent(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="n.AutoCancel = True";
_n.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 442;BA.debugLine="n.Icon = \"iconw\"";
_n.setIcon("iconw");
 //BA.debugLineNum = 443;BA.debugLine="n.SetInfo(\"AVISO DE INICIO DE ACTIVIDAD\" ,Texto,";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence("AVISO DE INICIO DE ACTIVIDAD"),BA.ObjectToCharSequence(_texto),(Object)(""));
 //BA.debugLineNum = 444;BA.debugLine="n.Notify(2)";
_n.Notify((int) (2));
 //BA.debugLineNum = 445;BA.debugLine="Msgbox2(Texto,\"Aviso de inicio de actividad\",\"Ace";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_texto),BA.ObjectToCharSequence("Aviso de inicio de actividad"),"Aceptar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvv2._vvv7,mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._v0][mostCurrent._vvvv2._vv1].Pictograma+".png").getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 447;BA.debugLine="n.Cancel(2)";
_n.Cancel((int) (2));
 //BA.debugLineNum = 450;BA.debugLine="CallSub(Starter,\"CalcularProximaAlarma\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvv2.getObject()),"CalcularProximaAlarma");
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return "";
}
public static String  _botonactividad_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
 //BA.debugLineNum = 343;BA.debugLine="Sub BotonActividad_click";
 //BA.debugLineNum = 344;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 345;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 346;BA.debugLine="ActivarBoton(BotonPulsado.Tag)";
_vvvvvv3((int)(BA.ObjectToNumber(_botonpulsado.getTag())));
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_click() throws Exception{
 //BA.debugLineNum = 362;BA.debugLine="Sub CambiarVista_Click";
 //BA.debugLineNum = 363;BA.debugLine="Starter.Secuencia(Starter.SecuenciaActiva).tabler";
mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo = (int) (((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo)+1)%4);
 //BA.debugLineNum = 364;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 366;BA.debugLine="ToastMessageShow(\"Cambiado tipo de tablero a \"&St";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cambiado tipo de tablero a "+mostCurrent._vvvv2._vv4[mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo]),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 367;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 368;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 369;BA.debugLine="DibujarTablero";
_vvvvvv7();
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvv0(int _numactividad) throws Exception{
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
float _distanciaadibujar = 0f;
 //BA.debugLineNum = 203;BA.debugLine="Sub DibujarActividad(NumActividad As Int)";
 //BA.debugLineNum = 206;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo>1 || (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==1 && mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_fin>11) || (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==0 && mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 209;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_inicio;
 //BA.debugLineNum = 210;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].minuto_inicio;
 //BA.debugLineNum = 211;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_fin;
 //BA.debugLineNum = 212;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].minuto_fin;
 //BA.debugLineNum = 213;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 214;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 215;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 217;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 218;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 222;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 223;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 226;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 227;BA.debugLine="Dim DistanciaADibujar=2 As Float 'Distancia a la";
_distanciaadibujar = (float) (2);
 //BA.debugLineNum = 228;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvv1,_vvvvvvv2);
 //BA.debugLineNum = 229;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
_recorte.LineTo(_horaminuto_x((float) (_horainicio),(float) (_mininicio),_distanciaadibujar),_horaminuto_y((float) (_horainicio),(float) (_mininicio),_distanciaadibujar));
 //BA.debugLineNum = 230;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
_recorte.LineTo(_horaminuto_x(_horamitad,_minutomitad,_distanciaadibujar),_horaminuto_y(_horamitad,_minutomitad,_distanciaadibujar));
 //BA.debugLineNum = 231;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,Dist";
_recorte.LineTo(_horaminuto_x((float) (_horafin),(float) (_minfin),_distanciaadibujar),_horaminuto_y((float) (_horafin),(float) (_minfin),_distanciaadibujar));
 //BA.debugLineNum = 232;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
_recorte.LineTo(_vvvvvvv1,_vvvvvvv2);
 //BA.debugLineNum = 234;BA.debugLine="AnguloInicio(NumActividad)=NormalizarAngulo(ATan";
_vvvvvvv3[_numactividad] = _vvvvvvv4((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horainicio),(float) (_mininicio),_distanciaadibujar)-_vvvvvvv2,_horaminuto_x((float) (_horainicio),(float) (_mininicio),_distanciaadibujar)-_vvvvvvv1)%360));
 //BA.debugLineNum = 235;BA.debugLine="AnguloFin(NumActividad)=NormalizarAngulo(ATan2D(";
_vvvvvvv5[_numactividad] = _vvvvvvv4((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horafin),(float) (_minfin),_distanciaadibujar)-_vvvvvvv2,_horaminuto_x((float) (_horafin),(float) (_minfin),_distanciaadibujar)-_vvvvvvv1)%360));
 //BA.debugLineNum = 236;BA.debugLine="If AnguloFin(NumActividad)=0 Then";
if (_vvvvvvv5[_numactividad]==0) { 
 //BA.debugLineNum = 237;BA.debugLine="AnguloFin(NumActividad)=360";
_vvvvvvv5[_numactividad] = (float) (360);
 };
 //BA.debugLineNum = 241;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvv6.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 242;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,St";
mostCurrent._vvvvvvv6.DrawCircle(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv7*0.7),mostCurrent._vvvv2._vv7[_numactividad],anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 243;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvv6.RemoveClip();
 };
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv0(int _numactividad) throws Exception{
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
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _bordeboton = null;
 //BA.debugLineNum = 249;BA.debugLine="Sub DibujarBoton(NumActividad As Int)";
 //BA.debugLineNum = 252;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo>1 || (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==1 && mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_fin>11) || (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==0 && mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 255;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_inicio;
 //BA.debugLineNum = 256;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].minuto_inicio;
 //BA.debugLineNum = 257;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].hora_fin;
 //BA.debugLineNum = 258;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].minuto_fin;
 //BA.debugLineNum = 259;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 260;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 261;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 263;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 264;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 268;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 269;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 272;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
mostCurrent._vvvvvv6[_numactividad].Initialize(mostCurrent.activityBA,"BotonActividad");
 //BA.debugLineNum = 273;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
mostCurrent._vvvvvv6[_numactividad].setTag((Object)(_numactividad));
 //BA.debugLineNum = 274;BA.debugLine="Dim DistanciaBoton=0.3+0.1*(NumActividad Mod 3)";
_distanciaboton = (float) (0.3+0.1*(_numactividad%3));
 //BA.debugLineNum = 275;BA.debugLine="Dim TamañoIcono=Starter.Secuencia(Starter.Secuen";
_tamañoicono = (float) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tam_icono*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA));
 //BA.debugLineNum = 276;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
_botonx = (float) (_horaminuto_x(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 277;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
_botony = (float) (_horaminuto_y(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 278;BA.debugLine="Dim BordeBoton As Rect";
_bordeboton = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 279;BA.debugLine="BordeBoton.Initialize(BotonX-1dip,BotonY-1dip,Bo";
_bordeboton.Initialize((int) (_botonx-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(int) (_botony-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(int) (_botonx+_tamañoicono+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (_botony+_tamañoicono+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 280;BA.debugLine="Pantalla.DrawRect(BordeBoton,0x80FFFFFF,True,0)";
mostCurrent._vvvvvvv6.DrawRect((android.graphics.Rect)(_bordeboton.getObject()),(int) (0x80ffffff),anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 281;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Boto";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvv6[_numactividad].getObject()),(int) (_botonx),(int) (_botony),(int) (_tamañoicono),(int) (_tamañoicono));
 //BA.debugLineNum = 282;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitma";
mostCurrent._vvvvvv6[_numactividad].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvv2._vvv7,mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][_numactividad].Pictograma+".png").getObject()));
 //BA.debugLineNum = 285;BA.debugLine="HoraActual=DateTime.GetHour(DateTime.Now)";
_vvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 286;BA.debugLine="MinutoActual=DateTime.GetMinute(DateTime.Now)";
_vvvvvvvv2 = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 287;BA.debugLine="If (  HoraActual*60+MinutoActual>=HoraInicio*60+";
if ((_vvvvvvvv1*60+_vvvvvvvv2>=_horainicio*60+_mininicio && _vvvvvvvv1*60+_vvvvvvvv2<_horafin*60+_minfin)) { 
 //BA.debugLineNum = 288;BA.debugLine="ActivarBoton(NumActividad)";
_vvvvvv3(_numactividad);
 };
 };
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvv7() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
int _hora = 0;
float _x = 0f;
float _y = 0f;
anywheresoftware.b4a.objects.LabelWrapper _numerohora = null;
int _nactividad = 0;
 //BA.debugLineNum = 56;BA.debugLine="Sub DibujarTablero()";
 //BA.debugLineNum = 58;BA.debugLine="CentroX=50%x";
_vvvvvvv1 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 59;BA.debugLine="CentroY=60%x";
_vvvvvvv2 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 60;BA.debugLine="Radio=45%x";
_vvvvvvv7 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 62;BA.debugLine="Pantalla.Initialize(PanelReloj)";
mostCurrent._vvvvvvv6.Initialize((android.view.View)(mostCurrent._panelreloj.getObject()));
 //BA.debugLineNum = 63;BA.debugLine="PantallaAgujas.Initialize(PanelAgujas)";
mostCurrent._vvvvvvvv3.Initialize((android.view.View)(mostCurrent._panelagujas.getObject()));
 //BA.debugLineNum = 66;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo<2) { 
 //BA.debugLineNum = 67;BA.debugLine="MinHora=1";
_vvvvvvvv4 = (int) (1);
 //BA.debugLineNum = 68;BA.debugLine="MaxHora=12";
_vvvvvvvv5 = (int) (12);
 }else if(mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 70;BA.debugLine="MinHora=1";
_vvvvvvvv4 = (int) (1);
 //BA.debugLineNum = 71;BA.debugLine="MaxHora=24";
_vvvvvvvv5 = (int) (24);
 }else {
 //BA.debugLineNum = 73;BA.debugLine="MinHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvv4 = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][(int) (0)].hora_inicio;
 //BA.debugLineNum = 74;BA.debugLine="MaxHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvv5 = mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][(int) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 75;BA.debugLine="If (Starter.ActividadSecuencia(Starter.Secuencia";
if ((mostCurrent._vvvv2._vvv3[mostCurrent._vvvv2._vvv1][(int) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].num_actividades-1)].minuto_fin!=0)) { 
 //BA.debugLineNum = 76;BA.debugLine="MaxHora=MaxHora+1";
_vvvvvvvv5 = (int) (_vvvvvvvv5+1);
 };
 };
 //BA.debugLineNum = 81;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo<3) { 
 //BA.debugLineNum = 83;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvv6.DrawCircle(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv7*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 84;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvv6.DrawCircle(_vvvvvvv1,_vvvvvvv2,_vvvvvvv7,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 }else {
 //BA.debugLineNum = 86;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
_recorte.Initialize(_vvvvvvv1,(float) (_vvvvvvv2+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)));
 //BA.debugLineNum = 89;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(114)*_vvvvvvv7*3)+_vvvvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(114)*_vvvvvvv7*3)+_vvvvvvv2));
 //BA.debugLineNum = 90;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 91;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 92;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 93;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 94;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(81)*_vvvvvvv7*3)+_vvvvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(81)*_vvvvvvv7)+_vvvvvvv2));
 //BA.debugLineNum = 95;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvv6.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 96;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvv6.DrawCircle(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv7*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 97;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvv6.RemoveClip();
 //BA.debugLineNum = 99;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvv1,_vvvvvvv2);
 //BA.debugLineNum = 100;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(116)*_vvvvvvv7*3)+_vvvvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(116)*_vvvvvvv7*3)+_vvvvvvv2));
 //BA.debugLineNum = 101;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 102;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 103;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 104;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 105;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(80)*_vvvvvvv7*3)+_vvvvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(80)*_vvvvvvv7)+_vvvvvvv2));
 //BA.debugLineNum = 106;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvv6.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 107;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvv6.DrawCircle(_vvvvvvv1,_vvvvvvv2,_vvvvvvv7,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 108;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvv6.RemoveClip();
 };
 //BA.debugLineNum = 111;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
{
final int step45 = 1;
final int limit45 = _vvvvvvvv5;
_hora = _vvvvvvvv4 ;
for (;_hora <= limit45 ;_hora = _hora + step45 ) {
 //BA.debugLineNum = 113;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 114;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 116;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
mostCurrent._vvvvvvv6.DrawCircle(_x,_y,(float) (_vvvvvvv7*0.02),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 117;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 118;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 119;BA.debugLine="Dim NumeroHora As Label";
_numerohora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 120;BA.debugLine="NumeroHora.Initialize(\"\")";
_numerohora.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 121;BA.debugLine="NumeroHora.Text=(Hora) Mod 24";
_numerohora.setText(BA.ObjectToCharSequence((_hora)%24));
 //BA.debugLineNum = 122;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
_numerohora.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 123;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
_numerohora.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 124;BA.debugLine="NumeroHora.TextSize=15";
_numerohora.setTextSize((float) (15));
 //BA.debugLineNum = 125;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
mostCurrent._activity.AddView((android.view.View)(_numerohora.getObject()),(int) (_x-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (_y-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 129;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo,(int) (0),(int) (1),(int) (2))) {
case 0: {
 //BA.debugLineNum = 131;BA.debugLine="MinHora=0";
_vvvvvvvv4 = (int) (0);
 //BA.debugLineNum = 132;BA.debugLine="MaxHora=11";
_vvvvvvvv5 = (int) (11);
 break; }
case 1: {
 //BA.debugLineNum = 134;BA.debugLine="MinHora=12";
_vvvvvvvv4 = (int) (12);
 //BA.debugLineNum = 135;BA.debugLine="MaxHora=23";
_vvvvvvvv5 = (int) (23);
 break; }
case 2: {
 //BA.debugLineNum = 137;BA.debugLine="MinHora=0";
_vvvvvvvv4 = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="MaxHora=23";
_vvvvvvvv5 = (int) (23);
 break; }
}
;
 //BA.debugLineNum = 142;BA.debugLine="For NActividad=0 To Starter.MaxActividades-1";
{
final int step70 = 1;
final int limit70 = (int) (mostCurrent._vvvv2._vv3-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit70 ;_nactividad = _nactividad + step70 ) {
 //BA.debugLineNum = 143;BA.debugLine="AnguloInicio(NActividad)=-1";
_vvvvvvv3[_nactividad] = (float) (-1);
 //BA.debugLineNum = 144;BA.debugLine="AnguloFin(NActividad)=-1";
_vvvvvvv5[_nactividad] = (float) (-1);
 }
};
 //BA.debugLineNum = 148;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step74 = 1;
final int limit74 = (int) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].num_actividades-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit74 ;_nactividad = _nactividad + step74 ) {
 //BA.debugLineNum = 149;BA.debugLine="DibujarActividad(NActividad)";
_vvvvvv0(_nactividad);
 }
};
 //BA.debugLineNum = 153;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step77 = 1;
final int limit77 = (int) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].num_actividades-1);
_nactividad = (int) (0) ;
for (;_nactividad <= limit77 ;_nactividad = _nactividad + step77 ) {
 //BA.debugLineNum = 154;BA.debugLine="DibujarBoton(NActividad)";
_vvvvvvv0(_nactividad);
 }
};
 //BA.debugLineNum = 158;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo,(int) (0),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 160;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(File";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"manana.png").getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 162;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(File";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tarde.png").getObject()));
 break; }
case 2: {
 //BA.debugLineNum = 164;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(File";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dia.png").getObject()));
 break; }
case 3: {
 //BA.debugLineNum = 166;BA.debugLine="CambiarVista.SetBackgroundImage(LoadBitmap(File";
mostCurrent._cambiarvista.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fila.png").getObject()));
 break; }
}
;
 //BA.debugLineNum = 169;BA.debugLine="DibujasAgujas";
_vvvvvvvv6();
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvv6() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rectangulovacio = null;
int _segundoactual = 0;
float _angulominuto = 0f;
float _angulosegundo = 0f;
 //BA.debugLineNum = 173;BA.debugLine="Sub DibujasAgujas";
 //BA.debugLineNum = 174;BA.debugLine="Dim RectanguloVacio As Rect";
_rectangulovacio = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
_segundoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 177;BA.debugLine="HoraActual=DateTime.GetHour(DateTime.Now)";
_vvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 178;BA.debugLine="MinutoActual=DateTime.GetMinute(DateTime.Now)";
_vvvvvvvv2 = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 180;BA.debugLine="RectanguloVacio.Initialize(0,0,PanelAgujas.Width,";
_rectangulovacio.Initialize((int) (0),(int) (0),mostCurrent._panelagujas.getWidth(),mostCurrent._panelagujas.getHeight());
 //BA.debugLineNum = 181;BA.debugLine="PantallaAgujas.DrawRect(RectanguloVacio, Colors.T";
mostCurrent._vvvvvvvv3.DrawRect((android.graphics.Rect)(_rectangulovacio.getObject()),anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 183;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).ta";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.indicar_hora>0 && _vvvvvvvv1>=_vvvvvvvv4 && _vvvvvvvv1<=_vvvvvvvv5)) { 
 //BA.debugLineNum = 184;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==3 || mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.indicar_hora==1)) { 
 //BA.debugLineNum = 185;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvv3.DrawLine(_vvvvvvv1,_vvvvvvv2,_horaminuto_x((float) (_vvvvvvvv1),(float) (_vvvvvvvv2),(float) (0.8)),_horaminuto_y((float) (_vvvvvvvv1),(float) (_vvvvvvvv2),(float) (0.8)),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 }else {
 //BA.debugLineNum = 187;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvv3.DrawLine(_vvvvvvv1,_vvvvvvv2,_horaminuto_x((float) (_vvvvvvvv1),(float) (_vvvvvvvv2),(float) (0.6)),_horaminuto_y((float) (_vvvvvvvv1),(float) (_vvvvvvvv2),(float) (0.6)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 188;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.indicar_hora>1)) { 
 //BA.debugLineNum = 189;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
_angulominuto = (float) (270+(_vvvvvvvv2*6));
 //BA.debugLineNum = 190;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centro";
mostCurrent._vvvvvvvv3.DrawLine(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv1+anywheresoftware.b4a.keywords.Common.CosD(_angulominuto)*_vvvvvvv7*0.8),(float) (_vvvvvvv2+anywheresoftware.b4a.keywords.Common.SinD(_angulominuto)*_vvvvvvv7*0.75),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6))));
 //BA.debugLineNum = 191;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva)";
if ((mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.indicar_hora>2)) { 
 //BA.debugLineNum = 192;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
_angulosegundo = (float) (270+(_segundoactual*6));
 //BA.debugLineNum = 193;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centr";
mostCurrent._vvvvvvvv3.DrawLine(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv1+anywheresoftware.b4a.keywords.Common.CosD(_angulosegundo)*_vvvvvvv7*0.9),(float) (_vvvvvvv2+anywheresoftware.b4a.keywords.Common.SinD(_angulosegundo)*_vvvvvvv7*0.9),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))));
 };
 };
 };
 };
 //BA.debugLineNum = 200;BA.debugLine="PantallaAgujas.DrawCircle(CentroX,CentroY,Radio*0";
mostCurrent._vvvvvvvv3.DrawCircle(_vvvvvvv1,_vvvvvvv2,(float) (_vvvvvvv7*0.1),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private PanelDescripcion As Panel";
mostCurrent._paneldescripcion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private PanelReloj As Panel";
mostCurrent._panelreloj = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private ImagenPictograma As ImageView";
mostCurrent._imagenpictograma = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private FondoPictograma As Panel";
mostCurrent._fondopictograma = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private TextoPictograma As Label";
mostCurrent._textopictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private PanelAgujas As Panel";
mostCurrent._panelagujas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja la";
mostCurrent._vvvvvvv6 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim PantallaAgujas As Canvas";
mostCurrent._vvvvvvvv3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
_vvvvvvv1 = 0f;
_vvvvvvv2 = 0f;
_vvvvvvv7 = 0f;
 //BA.debugLineNum = 26;BA.debugLine="Dim MinHora,MaxHora As Int";
_vvvvvvvv4 = 0;
_vvvvvvvv5 = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim Boton(Starter.MaxActividades) As Button";
mostCurrent._vvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._vvvv2._vv3];
{
int d0 = mostCurrent._vvvvvv6.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvv6[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 31;BA.debugLine="Private CambiarVista As Button";
mostCurrent._cambiarvista = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private DescripcionPictograma As Label";
mostCurrent._descripcionpictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Volver As Button";
mostCurrent._volver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim AnguloInicio(Starter.MaxActividades) As Float";
_vvvvvvv3 = new float[mostCurrent._vvvv2._vv3];
;
 //BA.debugLineNum = 38;BA.debugLine="Dim AnguloFin(Starter.MaxActividades) As Float";
_vvvvvvv5 = new float[mostCurrent._vvvv2._vv3];
;
 //BA.debugLineNum = 41;BA.debugLine="Private RelojDigital As Label";
mostCurrent._relojdigital = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim HoraActual As Int";
_vvvvvvvv1 = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim MinutoActual As Int";
_vvvvvvvv2 = 0;
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvv4(int _hora24) throws Exception{
 //BA.debugLineNum = 321;BA.debugLine="Sub Hora24a12 (Hora24 As Int) As Int";
 //BA.debugLineNum = 322;BA.debugLine="If (Hora24==0 Or Hora24==12) Then";
if ((_hora24==0 || _hora24==12)) { 
 //BA.debugLineNum = 323;BA.debugLine="Return 12";
if (true) return (int) (12);
 }else if((_hora24>11)) { 
 //BA.debugLineNum = 325;BA.debugLine="Return Hora24-12";
if (true) return (int) (_hora24-12);
 }else {
 //BA.debugLineNum = 327;BA.debugLine="Return Hora24";
if (true) return _hora24;
 };
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return 0;
}
public static float  _horaminuto_x(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 295;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 296;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==3) { 
 //BA.debugLineNum = 297;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvv4)*300/(double)(_vvvvvvvv5-_vvvvvvvv4));
 //BA.debugLineNum = 298;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD(_angulo)*_vvvvvvv7*_distancia)+_vvvvvvv1);
 }else {
 //BA.debugLineNum = 300;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 301;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 302;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 304;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvv7*_distancia)+_vvvvvvv1);
 };
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return 0f;
}
public static float  _horaminuto_y(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 308;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 309;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==3) { 
 //BA.debugLineNum = 310;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvv4)*300/(double)(_vvvvvvvv5-_vvvvvvvv4));
 //BA.debugLineNum = 311;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD(_angulo)*_vvvvvvv7*_distancia)+_vvvvvvv2);
 }else {
 //BA.debugLineNum = 313;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.tipo==2) { 
 //BA.debugLineNum = 314;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 315;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 317;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvv7*_distancia)+_vvvvvvv2);
 };
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return 0f;
}
public static String  _vvvvvv5(int _minuto) throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Sub MinutoLegible (Minuto As Int) As String";
 //BA.debugLineNum = 332;BA.debugLine="If (Minuto==0) Then";
if ((_minuto==0)) { 
 //BA.debugLineNum = 333;BA.debugLine="Return \"\"";
if (true) return "";
 }else if((_minuto==15)) { 
 //BA.debugLineNum = 335;BA.debugLine="Return \" y cuarto\"";
if (true) return " y cuarto";
 }else if((_minuto==30)) { 
 //BA.debugLineNum = 337;BA.debugLine="Return \" y media\"";
if (true) return " y media";
 }else {
 //BA.debugLineNum = 339;BA.debugLine="Return \":\"&NumberFormat(Minuto,2,0)";
if (true) return ":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minuto,(int) (2),(int) (0));
 };
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static float  _vvvvvvv4(float _angulo) throws Exception{
 //BA.debugLineNum = 407;BA.debugLine="Sub NormalizarAngulo(Angulo As Float) As Float";
 //BA.debugLineNum = 408;BA.debugLine="Angulo=Angulo+90 'Pone el 0 arriba";
_angulo = (float) (_angulo+90);
 //BA.debugLineNum = 409;BA.debugLine="If Angulo<0 Then";
if (_angulo<0) { 
 //BA.debugLineNum = 410;BA.debugLine="Return Angulo+360";
if (true) return (float) (_angulo+360);
 }else {
 //BA.debugLineNum = 412;BA.debugLine="Return Angulo";
if (true) return _angulo;
 };
 //BA.debugLineNum = 414;BA.debugLine="End Sub";
return 0f;
}
public static boolean  _panelagujas_touch(int _accion,float _x,float _y) throws Exception{
float _angulo = 0f;
int _i = 0;
 //BA.debugLineNum = 393;BA.debugLine="Sub PanelAgujas_Touch(Accion As Int, x As Float, y";
 //BA.debugLineNum = 394;BA.debugLine="Dim Angulo As Float";
_angulo = 0f;
 //BA.debugLineNum = 395;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 396;BA.debugLine="If Accion = Activity.ACTION_DOWN Then";
if (_accion==mostCurrent._activity.ACTION_DOWN) { 
 //BA.debugLineNum = 397;BA.debugLine="Angulo=NormalizarAngulo(ATan2D(y-CentroY,x-Centr";
_angulo = _vvvvvvv4((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_y-_vvvvvvv2,_x-_vvvvvvv1)));
 //BA.debugLineNum = 398;BA.debugLine="For i=0 To Starter.Secuencia(Starter.SecuenciaAc";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].num_actividades-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 399;BA.debugLine="If Angulo>=AnguloInicio(i) And Angulo<=AnguloFi";
if (_angulo>=_vvvvvvv3[_i] && _angulo<=_vvvvvvv5[_i]) { 
 //BA.debugLineNum = 400;BA.debugLine="ActivarBoton(i)";
_vvvvvv3(_i);
 };
 }
};
 };
 //BA.debugLineNum = 404;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
return false;
}
public static String  _panelreloj_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 358;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 360;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 376;BA.debugLine="Sub Temporizador_Tick";
 //BA.debugLineNum = 377;BA.debugLine="DibujasAgujas";
_vvvvvvvv6();
 //BA.debugLineNum = 378;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvvv2._vvv2[mostCurrent._vvvv2._vvv1].tablero.indicar_hora>0) { 
 //BA.debugLineNum = 379;BA.debugLine="RelojDigital.Text=NumberFormat(Hora24a12(HoraAct";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvv4(_vvvvvvvv1),(int) (2),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvv2,(int) (2),(int) (0))));
 //BA.debugLineNum = 380;BA.debugLine="If HoraActual=0 Then";
if (_vvvvvvvv1==0) { 
 //BA.debugLineNum = 381;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" de la noc";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" de la noche"));
 }else if(_vvvvvvvv1==12) { 
 //BA.debugLineNum = 383;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" del medio";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" del mediodía"));
 }else if((_vvvvvvvv1>12)) { 
 //BA.debugLineNum = 385;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" p.m.\"";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" p.m."));
 }else {
 //BA.debugLineNum = 387;BA.debugLine="RelojDigital.Text=RelojDigital.Text&\" a.m.\"";
mostCurrent._relojdigital.setText(BA.ObjectToCharSequence(mostCurrent._relojdigital.getText()+" a.m."));
 };
 };
 //BA.debugLineNum = 390;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static String  _volver_click() throws Exception{
 //BA.debugLineNum = 372;BA.debugLine="Sub Volver_Click";
 //BA.debugLineNum = 373;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
}
