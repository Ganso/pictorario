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
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (visualizacion) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _vvv2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _paneldescripcion = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreloj = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagenpictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondopictograma = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelagujas = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvvvvvvv5 = null;
public static float _vvvvvvvvv5 = 0f;
public static float _vvvvvvvvv6 = 0f;
public static float _vvvvvvvvvv3 = 0f;
public static int _vvvvvvvvvv6 = 0;
public static int _vvvvvvvvvv7 = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[] _vvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cambiarvista = null;
public anywheresoftware.b4a.objects.LabelWrapper _descripcionpictograma = null;
public anywheresoftware.b4a.objects.ButtonWrapper _volver = null;
public static float[] _vvvvvvvvv7 = null;
public static float[] _vvvvvvvvvv1 = null;
public javi.prieto.pictorario.main _vvvvvvvv6 = null;
public javi.prieto.pictorario.configurarsecuencia _vvv6 = null;
public javi.prieto.pictorario.starter _vvv4 = null;
public javi.prieto.pictorario.acercade _vvv5 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _vvvvvvvv7(int _i) throws Exception{
 //BA.debugLineNum = 388;BA.debugLine="Sub ActivarBoton(i As Int)";
 //BA.debugLineNum = 389;BA.debugLine="ImagenPictograma.Bitmap=LoadBitmap(File.DirAssets";
mostCurrent._imagenpictograma.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].Pictograma+".png").getObject()));
 //BA.debugLineNum = 390;BA.debugLine="TextoPictograma.Text=Starter.ActividadSecuencia(S";
mostCurrent._textopictograma.setText(BA.ObjectToCharSequence(mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].Descripcion.toUpperCase()));
 //BA.debugLineNum = 391;BA.debugLine="DescripcionPictograma.Text=\"De \"&Hora24a12(Starte";
mostCurrent._descripcionpictograma.setText(BA.ObjectToCharSequence("De "+BA.NumberToString(_vvvvvvvv0(mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].hora_inicio))+_vvvvvvvvv1(mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].minuto_inicio)+" a "+BA.NumberToString(_vvvvvvvv0(mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].hora_fin))+_vvvvvvvvv1(mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_i].minuto_fin)));
 //BA.debugLineNum = 392;BA.debugLine="FondoPictograma.Color=Starter.Colores(i)";
mostCurrent._fondopictograma.setColor(mostCurrent._vvv4._vv4[_i]);
 //BA.debugLineNum = 393;BA.debugLine="Boton(i).BringToFront()";
mostCurrent._vvvvvvvvv2[_i].BringToFront();
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 48;BA.debugLine="DibujarTablero";
_vvvvvvvvv3();
 //BA.debugLineNum = 49;BA.debugLine="Temporizador.Initialize(\"Temporizador\",1000)";
_vvv2.Initialize(processBA,"Temporizador",(long) (1000));
 //BA.debugLineNum = 50;BA.debugLine="Temporizador.Enabled=True";
_vvv2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 339;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 335;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 337;BA.debugLine="End Sub";
return "";
}
public static String  _botonactividad_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
 //BA.debugLineNum = 329;BA.debugLine="Sub BotonActividad_click";
 //BA.debugLineNum = 330;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 331;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 332;BA.debugLine="ActivarBoton(BotonPulsado.Tag)";
_vvvvvvvv7((int)(BA.ObjectToNumber(_botonpulsado.getTag())));
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_click() throws Exception{
 //BA.debugLineNum = 348;BA.debugLine="Sub CambiarVista_Click";
 //BA.debugLineNum = 349;BA.debugLine="Starter.Secuencia(Starter.SecuenciaActiva).tabler";
mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo = (int) (((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo)+1)%4);
 //BA.debugLineNum = 350;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 351;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 352;BA.debugLine="Activity.LoadLayout(\"VisualizarSecuencia\")";
mostCurrent._activity.LoadLayout("VisualizarSecuencia",mostCurrent.activityBA);
 //BA.debugLineNum = 353;BA.debugLine="DibujarTablero";
_vvvvvvvvv3();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv4(int _numactividad) throws Exception{
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
 //BA.debugLineNum = 200;BA.debugLine="Sub DibujarActividad(NumActividad As Int)";
 //BA.debugLineNum = 203;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo>1 || (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==1 && mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_fin>11) || (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==0 && mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 206;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_inicio;
 //BA.debugLineNum = 207;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].minuto_inicio;
 //BA.debugLineNum = 208;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_fin;
 //BA.debugLineNum = 209;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].minuto_fin;
 //BA.debugLineNum = 210;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 211;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 212;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 214;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 215;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 219;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 220;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 223;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 224;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvvvv5,_vvvvvvvvv6);
 //BA.debugLineNum = 225;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
_recorte.LineTo(_horaminuto_x((float) (_horainicio),(float) (_mininicio),(float) (1.5)),_horaminuto_y((float) (_horainicio),(float) (_mininicio),(float) (1.5)));
 //BA.debugLineNum = 226;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
_recorte.LineTo(_horaminuto_x(_horamitad,_minutomitad,(float) (1.5)),_horaminuto_y(_horamitad,_minutomitad,(float) (1.5)));
 //BA.debugLineNum = 227;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,1.5)";
_recorte.LineTo(_horaminuto_x((float) (_horafin),(float) (_minfin),(float) (1.5)),_horaminuto_y((float) (_horafin),(float) (_minfin),(float) (1.5)));
 //BA.debugLineNum = 228;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
_recorte.LineTo(_vvvvvvvvv5,_vvvvvvvvv6);
 //BA.debugLineNum = 230;BA.debugLine="AnguloInicio(NumActividad)=NormalizarAngulo(ATan";
_vvvvvvvvv7[_numactividad] = _vvvvvvvvv0((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horainicio),(float) (_mininicio),(float) (1.5))-_vvvvvvvvv6,_horaminuto_x((float) (_horainicio),(float) (_mininicio),(float) (1.5))-_vvvvvvvvv5)%360));
 //BA.debugLineNum = 231;BA.debugLine="AnguloFin(NumActividad)=NormalizarAngulo(ATan2D(";
_vvvvvvvvvv1[_numactividad] = _vvvvvvvvv0((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_horaminuto_y((float) (_horafin),(float) (_minfin),(float) (1.5))-_vvvvvvvvv6,_horaminuto_x((float) (_horafin),(float) (_minfin),(float) (1.5))-_vvvvvvvvv5)%360));
 //BA.debugLineNum = 232;BA.debugLine="If AnguloFin(NumActividad)=0 Then";
if (_vvvvvvvvvv1[_numactividad]==0) { 
 //BA.debugLineNum = 233;BA.debugLine="AnguloFin(NumActividad)=360";
_vvvvvvvvvv1[_numactividad] = (float) (360);
 };
 //BA.debugLineNum = 237;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvv2.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 238;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,St";
mostCurrent._vvvvvvvvvv2.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvvv3*0.7),mostCurrent._vvv4._vv4[_numactividad],anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 239;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvv2.RemoveClip();
 };
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv4(int _numactividad) throws Exception{
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
 //BA.debugLineNum = 245;BA.debugLine="Sub DibujarBoton(NumActividad As Int)";
 //BA.debugLineNum = 248;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo>1 || (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==1 && mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_fin>11) || (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==0 && mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 251;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_inicio;
 //BA.debugLineNum = 252;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].minuto_inicio;
 //BA.debugLineNum = 253;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].hora_fin;
 //BA.debugLineNum = 254;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].minuto_fin;
 //BA.debugLineNum = 255;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 256;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 257;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 259;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 260;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 264;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 265;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 268;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
mostCurrent._vvvvvvvvv2[_numactividad].Initialize(mostCurrent.activityBA,"BotonActividad");
 //BA.debugLineNum = 269;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
mostCurrent._vvvvvvvvv2[_numactividad].setTag((Object)(_numactividad));
 //BA.debugLineNum = 270;BA.debugLine="Dim DistanciaBoton=0.3+0.1*(NumActividad Mod 3)";
_distanciaboton = (float) (0.3+0.1*(_numactividad%3));
 //BA.debugLineNum = 271;BA.debugLine="Dim TamañoIcono=Starter.Secuencia(Starter.Secuen";
_tamañoicono = (float) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tam_icono*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA));
 //BA.debugLineNum = 272;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
_botonx = (float) (_horaminuto_x(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 273;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
_botony = (float) (_horaminuto_y(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 277;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Boto";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvv2[_numactividad].getObject()),(int) (_botonx),(int) (_botony),(int) (_tamañoicono),(int) (_tamañoicono));
 //BA.debugLineNum = 278;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitma";
mostCurrent._vvvvvvvvv2[_numactividad].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][_numactividad].Pictograma+".png").getObject()));
 };
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv3() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
int _hora = 0;
float _x = 0f;
float _y = 0f;
anywheresoftware.b4a.objects.LabelWrapper _numerohora = null;
int _nactividad = 0;
 //BA.debugLineNum = 54;BA.debugLine="Sub DibujarTablero()";
 //BA.debugLineNum = 56;BA.debugLine="CentroX=50%x";
_vvvvvvvvv5 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 57;BA.debugLine="CentroY=60%x";
_vvvvvvvvv6 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 58;BA.debugLine="Radio=45%x";
_vvvvvvvvvv3 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 60;BA.debugLine="Pantalla.Initialize(PanelReloj)";
mostCurrent._vvvvvvvvvv2.Initialize((android.view.View)(mostCurrent._panelreloj.getObject()));
 //BA.debugLineNum = 61;BA.debugLine="PantallaAgujas.Initialize(PanelAgujas)";
mostCurrent._vvvvvvvvvv5.Initialize((android.view.View)(mostCurrent._panelagujas.getObject()));
 //BA.debugLineNum = 64;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo<2) { 
 //BA.debugLineNum = 65;BA.debugLine="MinHora=1";
_vvvvvvvvvv6 = (int) (1);
 //BA.debugLineNum = 66;BA.debugLine="MaxHora=12";
_vvvvvvvvvv7 = (int) (12);
 }else if(mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 68;BA.debugLine="MinHora=1";
_vvvvvvvvvv6 = (int) (1);
 //BA.debugLineNum = 69;BA.debugLine="MaxHora=24";
_vvvvvvvvvv7 = (int) (24);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="MinHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvvvv6 = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][(int) (0)].hora_inicio;
 //BA.debugLineNum = 72;BA.debugLine="MaxHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvvvvvvv7 = mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][(int) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 73;BA.debugLine="If (Starter.ActividadSecuencia(Starter.Secuencia";
if ((mostCurrent._vvv4._vv0[mostCurrent._vvv4._vv6][(int) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].num_actividades-1)].minuto_fin!=0)) { 
 //BA.debugLineNum = 74;BA.debugLine="MaxHora=MaxHora+1";
_vvvvvvvvvv7 = (int) (_vvvvvvvvvv7+1);
 };
 };
 //BA.debugLineNum = 79;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo<3) { 
 //BA.debugLineNum = 81;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvvvvv2.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvvv3*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 82;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvvvvv2.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,_vvvvvvvvvv3,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 }else {
 //BA.debugLineNum = 84;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
_recorte.Initialize(_vvvvvvvvv5,(float) (_vvvvvvvvv6+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)));
 //BA.debugLineNum = 87;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(114)*_vvvvvvvvvv3*3)+_vvvvvvvvv5),(float) ((anywheresoftware.b4a.keywords.Common.SinD(114)*_vvvvvvvvvv3*3)+_vvvvvvvvv6));
 //BA.debugLineNum = 88;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 89;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 90;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 91;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 92;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(81)*_vvvvvvvvvv3*3)+_vvvvvvvvv5),(float) ((anywheresoftware.b4a.keywords.Common.SinD(81)*_vvvvvvvvvv3)+_vvvvvvvvv6));
 //BA.debugLineNum = 93;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvv2.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 94;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvvvvvvv2.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvvv3*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 95;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvv2.RemoveClip();
 //BA.debugLineNum = 97;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvvvvvv5,_vvvvvvvvv6);
 //BA.debugLineNum = 98;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(116)*_vvvvvvvvvv3*3)+_vvvvvvvvv5),(float) ((anywheresoftware.b4a.keywords.Common.SinD(116)*_vvvvvvvvvv3*3)+_vvvvvvvvv6));
 //BA.debugLineNum = 99;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 100;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 101;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 102;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 103;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(80)*_vvvvvvvvvv3*3)+_vvvvvvvvv5),(float) ((anywheresoftware.b4a.keywords.Common.SinD(80)*_vvvvvvvvvv3)+_vvvvvvvvv6));
 //BA.debugLineNum = 104;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvvvvvvv2.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvvvvvvv2.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,_vvvvvvvvvv3,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 106;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvvvvvvv2.RemoveClip();
 };
 //BA.debugLineNum = 109;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
{
final int step45 = (int) (1);
final int limit45 = _vvvvvvvvvv7;
_hora = _vvvvvvvvvv6 ;
for (;(step45 > 0 && _hora <= limit45) || (step45 < 0 && _hora >= limit45) ;_hora = ((int)(0 + _hora + step45))  ) {
 //BA.debugLineNum = 111;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 112;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 114;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
mostCurrent._vvvvvvvvvv2.DrawCircle(_x,_y,(float) (_vvvvvvvvvv3*0.02),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 115;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 116;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 117;BA.debugLine="Dim NumeroHora As Label";
_numerohora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 118;BA.debugLine="NumeroHora.Initialize(\"\")";
_numerohora.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 119;BA.debugLine="NumeroHora.Text=(Hora) Mod 24";
_numerohora.setText(BA.ObjectToCharSequence((_hora)%24));
 //BA.debugLineNum = 120;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
_numerohora.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 121;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
_numerohora.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 122;BA.debugLine="NumeroHora.TextSize=15";
_numerohora.setTextSize((float) (15));
 //BA.debugLineNum = 123;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
mostCurrent._activity.AddView((android.view.View)(_numerohora.getObject()),(int) (_x-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (_y-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 127;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo,(int) (0),(int) (1),(int) (2))) {
case 0: {
 //BA.debugLineNum = 129;BA.debugLine="MinHora=0";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 130;BA.debugLine="MaxHora=11";
_vvvvvvvvvv7 = (int) (11);
 break; }
case 1: {
 //BA.debugLineNum = 132;BA.debugLine="MinHora=12";
_vvvvvvvvvv6 = (int) (12);
 //BA.debugLineNum = 133;BA.debugLine="MaxHora=23";
_vvvvvvvvvv7 = (int) (23);
 break; }
case 2: {
 //BA.debugLineNum = 135;BA.debugLine="MinHora=0";
_vvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 136;BA.debugLine="MaxHora=23";
_vvvvvvvvvv7 = (int) (23);
 break; }
}
;
 //BA.debugLineNum = 140;BA.debugLine="For NActividad=0 To Starter.MaxActividades-1";
{
final int step70 = 1;
final int limit70 = (int) (mostCurrent._vvv4._v0-1);
_nactividad = (int) (0) ;
for (;(step70 > 0 && _nactividad <= limit70) || (step70 < 0 && _nactividad >= limit70) ;_nactividad = ((int)(0 + _nactividad + step70))  ) {
 //BA.debugLineNum = 141;BA.debugLine="AnguloInicio(NActividad)=-1";
_vvvvvvvvv7[_nactividad] = (float) (-1);
 //BA.debugLineNum = 142;BA.debugLine="AnguloFin(NActividad)=-1";
_vvvvvvvvvv1[_nactividad] = (float) (-1);
 }
};
 //BA.debugLineNum = 146;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step74 = (int) (1);
final int limit74 = (int) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].num_actividades-1);
_nactividad = (int) (0) ;
for (;(step74 > 0 && _nactividad <= limit74) || (step74 < 0 && _nactividad >= limit74) ;_nactividad = ((int)(0 + _nactividad + step74))  ) {
 //BA.debugLineNum = 147;BA.debugLine="DibujarActividad(NActividad)";
_vvvvvvvvv4(_nactividad);
 }
};
 //BA.debugLineNum = 151;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step77 = (int) (1);
final int limit77 = (int) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].num_actividades-1);
_nactividad = (int) (0) ;
for (;(step77 > 0 && _nactividad <= limit77) || (step77 < 0 && _nactividad >= limit77) ;_nactividad = ((int)(0 + _nactividad + step77))  ) {
 //BA.debugLineNum = 152;BA.debugLine="DibujarBoton(NActividad)";
_vvvvvvvvvv4(_nactividad);
 }
};
 //BA.debugLineNum = 156;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo,(int) (0),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 158;BA.debugLine="CambiarVista.Text=\"Mañana\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Mañana"));
 break; }
case 1: {
 //BA.debugLineNum = 160;BA.debugLine="CambiarVista.Text=\"Tarde\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Tarde"));
 break; }
case 2: {
 //BA.debugLineNum = 162;BA.debugLine="CambiarVista.Text=\"Día\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Día"));
 break; }
case 3: {
 //BA.debugLineNum = 164;BA.debugLine="CambiarVista.Text=\"Secuencia\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Secuencia"));
 break; }
}
;
 //BA.debugLineNum = 167;BA.debugLine="DibujasAgujas";
_vvvvvvvvvv0();
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv0() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rectangulovacio = null;
int _horaactual = 0;
int _minutoactual = 0;
int _segundoactual = 0;
float _angulominuto = 0f;
float _angulosegundo = 0f;
 //BA.debugLineNum = 171;BA.debugLine="Sub DibujasAgujas";
 //BA.debugLineNum = 172;BA.debugLine="Dim RectanguloVacio As Rect";
_rectangulovacio = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Dim HoraActual=DateTime.GetHour(DateTime.Now) As";
_horaactual = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 174;BA.debugLine="Dim MinutoActual=DateTime.GetMinute(DateTime.Now)";
_minutoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 175;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
_segundoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 177;BA.debugLine="RectanguloVacio.Initialize(0,0,PanelAgujas.Width,";
_rectangulovacio.Initialize((int) (0),(int) (0),mostCurrent._panelagujas.getWidth(),mostCurrent._panelagujas.getHeight());
 //BA.debugLineNum = 178;BA.debugLine="PantallaAgujas.DrawRect(RectanguloVacio, Colors.T";
mostCurrent._vvvvvvvvvv5.DrawRect((android.graphics.Rect)(_rectangulovacio.getObject()),anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 180;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).ta";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.indicar_hora>0 && _horaactual>=_vvvvvvvvvv6 && _horaactual<_vvvvvvvvvv7)) { 
 //BA.debugLineNum = 181;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==3 || mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.indicar_hora==1)) { 
 //BA.debugLineNum = 182;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvvvv5.DrawLine(_vvvvvvvvv5,_vvvvvvvvv6,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 }else {
 //BA.debugLineNum = 184;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,HoraMin";
mostCurrent._vvvvvvvvvv5.DrawLine(_vvvvvvvvv5,_vvvvvvvvv6,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 185;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.indicar_hora>1)) { 
 //BA.debugLineNum = 186;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
_angulominuto = (float) (270+(_minutoactual*6));
 //BA.debugLineNum = 187;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centro";
mostCurrent._vvvvvvvvvv5.DrawLine(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvv5+anywheresoftware.b4a.keywords.Common.CosD(_angulominuto)*_vvvvvvvvvv3*0.8),(float) (_vvvvvvvvv6+anywheresoftware.b4a.keywords.Common.SinD(_angulominuto)*_vvvvvvvvvv3*0.75),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6))));
 //BA.debugLineNum = 188;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva)";
if ((mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.indicar_hora>2)) { 
 //BA.debugLineNum = 189;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
_angulosegundo = (float) (270+(_segundoactual*6));
 //BA.debugLineNum = 190;BA.debugLine="PantallaAgujas.DrawLine(CentroX,CentroY,Centr";
mostCurrent._vvvvvvvvvv5.DrawLine(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvv5+anywheresoftware.b4a.keywords.Common.CosD(_angulosegundo)*_vvvvvvvvvv3*0.9),(float) (_vvvvvvvvv6+anywheresoftware.b4a.keywords.Common.SinD(_angulosegundo)*_vvvvvvvvvv3*0.9),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))));
 };
 };
 };
 };
 //BA.debugLineNum = 197;BA.debugLine="PantallaAgujas.DrawCircle(CentroX,CentroY,Radio*0";
mostCurrent._vvvvvvvvvv5.DrawCircle(_vvvvvvvvv5,_vvvvvvvvv6,(float) (_vvvvvvvvvv3*0.1),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private PanelDescripcion As Panel";
mostCurrent._paneldescripcion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private PanelReloj As Panel";
mostCurrent._panelreloj = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private ImagenPictograma As ImageView";
mostCurrent._imagenpictograma = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private FondoPictograma As Panel";
mostCurrent._fondopictograma = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private TextoPictograma As Label";
mostCurrent._textopictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private PanelAgujas As Panel";
mostCurrent._panelagujas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja la";
mostCurrent._vvvvvvvvvv2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim PantallaAgujas As Canvas";
mostCurrent._vvvvvvvvvv5 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
_vvvvvvvvv5 = 0f;
_vvvvvvvvv6 = 0f;
_vvvvvvvvvv3 = 0f;
 //BA.debugLineNum = 29;BA.debugLine="Dim MinHora,MaxHora As Int";
_vvvvvvvvvv6 = 0;
_vvvvvvvvvv7 = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim Boton(Starter.MaxActividades) As Button";
mostCurrent._vvvvvvvvv2 = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._vvv4._v0];
{
int d0 = mostCurrent._vvvvvvvvv2.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvv2[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 34;BA.debugLine="Private CambiarVista As Button";
mostCurrent._cambiarvista = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private DescripcionPictograma As Label";
mostCurrent._descripcionpictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Volver As Button";
mostCurrent._volver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim AnguloInicio(Starter.MaxActividades) As Float";
_vvvvvvvvv7 = new float[mostCurrent._vvv4._v0];
;
 //BA.debugLineNum = 41;BA.debugLine="Dim AnguloFin(Starter.MaxActividades) As Float";
_vvvvvvvvvv1 = new float[mostCurrent._vvv4._v0];
;
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvv0(int _hora24) throws Exception{
 //BA.debugLineNum = 309;BA.debugLine="Sub Hora24a12 (Hora24 As Int) As Int";
 //BA.debugLineNum = 310;BA.debugLine="If (Hora24>11) Then";
if ((_hora24>11)) { 
 //BA.debugLineNum = 311;BA.debugLine="Return Hora24-12";
if (true) return (int) (_hora24-12);
 }else {
 //BA.debugLineNum = 313;BA.debugLine="Return Hora24";
if (true) return _hora24;
 };
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return 0;
}
public static float  _horaminuto_x(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 283;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 284;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==3) { 
 //BA.debugLineNum = 285;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvvvv6)*300/(double)(_vvvvvvvvvv7-_vvvvvvvvvv6));
 //BA.debugLineNum = 286;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD(_angulo)*_vvvvvvvvvv3*_distancia)+_vvvvvvvvv5);
 }else {
 //BA.debugLineNum = 288;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 289;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 290;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 292;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvvvvv3*_distancia)+_vvvvvvvvv5);
 };
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return 0f;
}
public static float  _horaminuto_y(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 296;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 297;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==3) { 
 //BA.debugLineNum = 298;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvvvvvvv6)*300/(double)(_vvvvvvvvvv7-_vvvvvvvvvv6));
 //BA.debugLineNum = 299;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD(_angulo)*_vvvvvvvvvv3*_distancia)+_vvvvvvvvv6);
 }else {
 //BA.debugLineNum = 301;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 302;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 303;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 305;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvvvvvvv3*_distancia)+_vvvvvvvvv6);
 };
 //BA.debugLineNum = 307;BA.debugLine="End Sub";
return 0f;
}
public static String  _vvvvvvvvv1(int _minuto) throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Sub MinutoLegible (Minuto As Int) As String";
 //BA.debugLineNum = 318;BA.debugLine="If (Minuto==0) Then";
if ((_minuto==0)) { 
 //BA.debugLineNum = 319;BA.debugLine="Return \"\"";
if (true) return "";
 }else if((_minuto==15)) { 
 //BA.debugLineNum = 321;BA.debugLine="Return \" y cuarto\"";
if (true) return " y cuarto";
 }else if((_minuto==30)) { 
 //BA.debugLineNum = 323;BA.debugLine="Return \" y media\"";
if (true) return " y media";
 }else {
 //BA.debugLineNum = 325;BA.debugLine="Return \":\"&NumberFormat(Minuto,2,0)";
if (true) return ":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minuto,(int) (2),(int) (0));
 };
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static float  _vvvvvvvvv0(float _angulo) throws Exception{
 //BA.debugLineNum = 379;BA.debugLine="Sub NormalizarAngulo(Angulo As Float) As Float";
 //BA.debugLineNum = 380;BA.debugLine="Angulo=Angulo+90 'Pone el 0 arriba";
_angulo = (float) (_angulo+90);
 //BA.debugLineNum = 381;BA.debugLine="If Angulo<0 Then";
if (_angulo<0) { 
 //BA.debugLineNum = 382;BA.debugLine="Return Angulo+360";
if (true) return (float) (_angulo+360);
 }else {
 //BA.debugLineNum = 384;BA.debugLine="Return Angulo";
if (true) return _angulo;
 };
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return 0f;
}
public static boolean  _panelagujas_touch(int _accion,float _x,float _y) throws Exception{
float _angulo = 0f;
int _i = 0;
 //BA.debugLineNum = 365;BA.debugLine="Sub PanelAgujas_Touch(Accion As Int, x As Float, y";
 //BA.debugLineNum = 366;BA.debugLine="Dim Angulo As Float";
_angulo = 0f;
 //BA.debugLineNum = 367;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 368;BA.debugLine="If Accion = Activity.ACTION_DOWN Then";
if (_accion==mostCurrent._activity.ACTION_DOWN) { 
 //BA.debugLineNum = 369;BA.debugLine="Angulo=NormalizarAngulo(ATan2D(y-CentroY,x-Centr";
_angulo = _vvvvvvvvv0((float) (anywheresoftware.b4a.keywords.Common.ATan2D(_y-_vvvvvvvvv6,_x-_vvvvvvvvv5)));
 //BA.debugLineNum = 370;BA.debugLine="For i=0 To Starter.Secuencia(Starter.SecuenciaAc";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvv4._vv7[mostCurrent._vvv4._vv6].num_actividades-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 371;BA.debugLine="If Angulo>=AnguloInicio(i) And Angulo<=AnguloFi";
if (_angulo>=_vvvvvvvvv7[_i] && _angulo<=_vvvvvvvvvv1[_i]) { 
 //BA.debugLineNum = 372;BA.debugLine="ActivarBoton(i)";
_vvvvvvvv7(_i);
 };
 }
};
 };
 //BA.debugLineNum = 376;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 377;BA.debugLine="End Sub";
return false;
}
public static String  _panelreloj_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 344;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Temporizador As Timer";
_vvv2 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _temporizador_tick() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub Temporizador_Tick";
 //BA.debugLineNum = 361;BA.debugLine="DibujasAgujas";
_vvvvvvvvvv0();
 //BA.debugLineNum = 362;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _volver_click() throws Exception{
 //BA.debugLineNum = 356;BA.debugLine="Sub Volver_Click";
 //BA.debugLineNum = 357;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
}
