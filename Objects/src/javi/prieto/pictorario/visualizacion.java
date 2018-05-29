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
public anywheresoftware.b4a.objects.PanelWrapper _paneldescripcion = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelreloj = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagenpictograma = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondopictograma = null;
public anywheresoftware.b4a.objects.LabelWrapper _textopictograma = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _vvvvv3 = null;
public static float _vvvvv1 = 0f;
public static float _vvvvv2 = 0f;
public static float _vvvvv4 = 0f;
public static int _vvvvv7 = 0;
public static int _vvvvv0 = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[] _vvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cambiarvista = null;
public anywheresoftware.b4a.objects.LabelWrapper _descripcionpictograma = null;
public anywheresoftware.b4a.objects.ButtonWrapper _volver = null;
public javi.prieto.pictorario.main _vvvvvv1 = null;
public javi.prieto.pictorario.configurarsecuencia _vvv5 = null;
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
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
mostCurrent._activity.LoadLayout("RelojActividades",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="DibujarTablero";
_vvvv5();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 307;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _botonactividad_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
javi.prieto.pictorario.starter._actividad _actividadpulsada = null;
 //BA.debugLineNum = 291;BA.debugLine="Sub BotonActividad_click";
 //BA.debugLineNum = 292;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 293;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 294;BA.debugLine="Dim ActividadPulsada As Actividad";
_actividadpulsada = new javi.prieto.pictorario.starter._actividad();
 //BA.debugLineNum = 295;BA.debugLine="ActividadPulsada=Starter.ActividadSecuencia(Start";
_actividadpulsada = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][(int)(BA.ObjectToNumber(_botonpulsado.getTag()))];
 //BA.debugLineNum = 296;BA.debugLine="ImagenPictograma.Bitmap=LoadBitmap(File.DirAssets";
mostCurrent._imagenpictograma.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][(int)(BA.ObjectToNumber(_botonpulsado.getTag()))].Pictograma+".png").getObject()));
 //BA.debugLineNum = 297;BA.debugLine="TextoPictograma.Text=ActividadPulsada.descripcion";
mostCurrent._textopictograma.setText(BA.ObjectToCharSequence(_actividadpulsada.Descripcion.toUpperCase()));
 //BA.debugLineNum = 298;BA.debugLine="DescripcionPictograma.Text=\"De \"&Hora24a12(Activi";
mostCurrent._descripcionpictograma.setText(BA.ObjectToCharSequence("De "+BA.NumberToString(_vvvv6(_actividadpulsada.hora_inicio))+_vvvv7(_actividadpulsada.minuto_inicio)+" a "+BA.NumberToString(_vvvv6(_actividadpulsada.hora_fin))+_vvvv7(_actividadpulsada.minuto_fin)));
 //BA.debugLineNum = 299;BA.debugLine="FondoPictograma.Color=Starter.Colores(BotonPulsad";
mostCurrent._fondopictograma.setColor(mostCurrent._vvv3._vv4[(int)(BA.ObjectToNumber(_botonpulsado.getTag()))]);
 //BA.debugLineNum = 300;BA.debugLine="BotonPulsado.BringToFront()";
_botonpulsado.BringToFront();
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarvista_click() throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Sub CambiarVista_Click";
 //BA.debugLineNum = 317;BA.debugLine="Starter.Secuencia(Starter.SecuenciaActiva).tabler";
mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo = (int) (((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo)+1)%4);
 //BA.debugLineNum = 318;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 319;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 //BA.debugLineNum = 320;BA.debugLine="Activity.LoadLayout(\"RelojActividades\")";
mostCurrent._activity.LoadLayout("RelojActividades",mostCurrent.activityBA);
 //BA.debugLineNum = 321;BA.debugLine="DibujarTablero";
_vvvv5();
 //BA.debugLineNum = 322;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv0(int _numactividad) throws Exception{
int _horainicio = 0;
int _mininicio = 0;
int _horafin = 0;
int _minfin = 0;
float _horamitad = 0f;
float _minutomitad = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _recorte = null;
 //BA.debugLineNum = 168;BA.debugLine="Sub DibujarActividad(NumActividad As Int)";
 //BA.debugLineNum = 171;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo>1 || (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==1 && mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_fin>11) || (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==0 && mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 174;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_inicio;
 //BA.debugLineNum = 175;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].minuto_inicio;
 //BA.debugLineNum = 176;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_fin;
 //BA.debugLineNum = 177;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].minuto_fin;
 //BA.debugLineNum = 178;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 179;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 180;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 182;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 183;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 187;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 188;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 191;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 192;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvv1,_vvvvv2);
 //BA.debugLineNum = 193;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraInicio,MinInici";
_recorte.LineTo(_horaminuto_x((float) (_horainicio),(float) (_mininicio),(float) (1.5)),_horaminuto_y((float) (_horainicio),(float) (_mininicio),(float) (1.5)));
 //BA.debugLineNum = 194;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraMitad,MinutoMit";
_recorte.LineTo(_horaminuto_x(_horamitad,_minutomitad,(float) (1.5)),_horaminuto_y(_horamitad,_minutomitad,(float) (1.5)));
 //BA.debugLineNum = 195;BA.debugLine="Recorte.LineTo( HoraMinuto_X(HoraFin,MinFin,1.5)";
_recorte.LineTo(_horaminuto_x((float) (_horafin),(float) (_minfin),(float) (1.5)),_horaminuto_y((float) (_horafin),(float) (_minfin),(float) (1.5)));
 //BA.debugLineNum = 196;BA.debugLine="Recorte.LineTo(CentroX,CentroY)";
_recorte.LineTo(_vvvvv1,_vvvvv2);
 //BA.debugLineNum = 199;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvv3.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 200;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.7,St";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,(float) (_vvvvv4*0.7),mostCurrent._vvv3._vv4[_numactividad],anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 201;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvv3.RemoveClip();
 };
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvv5(int _numactividad) throws Exception{
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
 //BA.debugLineNum = 207;BA.debugLine="Sub DibujarBoton(NumActividad As Int)";
 //BA.debugLineNum = 210;BA.debugLine="If ( Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo>1 || (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==1 && mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_fin>11) || (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==0 && mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_inicio<12))) { 
 //BA.debugLineNum = 213;BA.debugLine="Dim HoraInicio=Starter.ActividadSecuencia(Starte";
_horainicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_inicio;
 //BA.debugLineNum = 214;BA.debugLine="Dim MinInicio=Starter.ActividadSecuencia(Starter";
_mininicio = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].minuto_inicio;
 //BA.debugLineNum = 215;BA.debugLine="Dim HoraFin=Starter.ActividadSecuencia(Starter.S";
_horafin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].hora_fin;
 //BA.debugLineNum = 216;BA.debugLine="Dim MinFin=Starter.ActividadSecuencia(Starter.Se";
_minfin = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].minuto_fin;
 //BA.debugLineNum = 217;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==0 && _horafin>11)) { 
 //BA.debugLineNum = 218;BA.debugLine="HoraFin=12";
_horafin = (int) (12);
 //BA.debugLineNum = 219;BA.debugLine="MinFin=0";
_minfin = (int) (0);
 }else if((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==1 && _horainicio<12)) { 
 //BA.debugLineNum = 221;BA.debugLine="HoraInicio=12";
_horainicio = (int) (12);
 //BA.debugLineNum = 222;BA.debugLine="MinInicio=0";
_mininicio = (int) (0);
 };
 //BA.debugLineNum = 226;BA.debugLine="Dim HoraMitad=(HoraInicio+HoraFin)/2 As Float";
_horamitad = (float) ((_horainicio+_horafin)/(double)2);
 //BA.debugLineNum = 227;BA.debugLine="Dim MinutoMitad=(MinInicio+MinFin)/2 As Float";
_minutomitad = (float) ((_mininicio+_minfin)/(double)2);
 //BA.debugLineNum = 230;BA.debugLine="Boton(NumActividad).Initialize(\"BotonActividad\")";
mostCurrent._vvvvv6[_numactividad].Initialize(mostCurrent.activityBA,"BotonActividad");
 //BA.debugLineNum = 231;BA.debugLine="Boton(NumActividad).Tag=NumActividad";
mostCurrent._vvvvv6[_numactividad].setTag((Object)(_numactividad));
 //BA.debugLineNum = 232;BA.debugLine="Dim DistanciaBoton=0.5+0.1*(NumActividad Mod 3)";
_distanciaboton = (float) (0.5+0.1*(_numactividad%3));
 //BA.debugLineNum = 233;BA.debugLine="Dim TamañoIcono=Starter.Secuencia(Starter.Secuen";
_tamañoicono = (float) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tam_icono*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA));
 //BA.debugLineNum = 234;BA.debugLine="Dim BotonX=HoraMinuto_X(HoraMitad,MinutoMitad,Di";
_botonx = (float) (_horaminuto_x(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 235;BA.debugLine="Dim BotonY=HoraMinuto_Y(HoraMitad,MinutoMitad,Di";
_botony = (float) (_horaminuto_y(_horamitad,_minutomitad,_distanciaboton)-_tamañoicono/(double)2);
 //BA.debugLineNum = 239;BA.debugLine="Activity.AddView(Boton(NumActividad),BotonX,Boto";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvv6[_numactividad].getObject()),(int) (_botonx),(int) (_botony),(int) (_tamañoicono),(int) (_tamañoicono));
 //BA.debugLineNum = 240;BA.debugLine="Boton(NumActividad).SetBackgroundImage(LoadBitma";
mostCurrent._vvvvv6[_numactividad].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][_numactividad].Pictograma+".png").getObject()));
 };
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv5() throws Exception{
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
 //BA.debugLineNum = 43;BA.debugLine="Sub DibujarTablero()";
 //BA.debugLineNum = 45;BA.debugLine="CentroX=50%x";
_vvvvv1 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 46;BA.debugLine="CentroY=60%x";
_vvvvv2 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 47;BA.debugLine="Radio=45%x";
_vvvvv4 = (float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 49;BA.debugLine="Pantalla.Initialize(PanelReloj)";
mostCurrent._vvvvv3.Initialize((android.view.View)(mostCurrent._panelreloj.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo<2) { 
 //BA.debugLineNum = 53;BA.debugLine="MinHora=1";
_vvvvv7 = (int) (1);
 //BA.debugLineNum = 54;BA.debugLine="MaxHora=12";
_vvvvv0 = (int) (12);
 }else if(mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 56;BA.debugLine="MinHora=1";
_vvvvv7 = (int) (1);
 //BA.debugLineNum = 57;BA.debugLine="MaxHora=24";
_vvvvv0 = (int) (24);
 }else {
 //BA.debugLineNum = 59;BA.debugLine="MinHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvv7 = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][(int) (0)].hora_inicio;
 //BA.debugLineNum = 61;BA.debugLine="MaxHora=Starter.ActividadSecuencia(Starter.Secue";
_vvvvv0 = mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][(int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].num_actividades-1)].hora_fin;
 //BA.debugLineNum = 62;BA.debugLine="If (Starter.ActividadSecuencia(Starter.Secuencia";
if ((mostCurrent._vvv3._vv0[mostCurrent._vvv3._vv6][(int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].num_actividades-1)].minuto_fin!=0)) { 
 //BA.debugLineNum = 63;BA.debugLine="MaxHora=MaxHora+1";
_vvvvv0 = (int) (_vvvvv0+1);
 };
 };
 //BA.debugLineNum = 68;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo<3) { 
 //BA.debugLineNum = 70;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,(float) (_vvvvv4*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 71;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,_vvvvv4,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 }else {
 //BA.debugLineNum = 73;BA.debugLine="Dim Recorte As Path";
_recorte = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Recorte.Initialize(CentroX,CentroY+3%Y)";
_recorte.Initialize(_vvvvv1,(float) (_vvvvv2+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA)));
 //BA.debugLineNum = 76;BA.debugLine="Recorte.LineTo( (CosD(114)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(114)*_vvvvv4*3)+_vvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(114)*_vvvvv4*3)+_vvvvv2));
 //BA.debugLineNum = 77;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 78;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 79;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 80;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 81;BA.debugLine="Recorte.LineTo( (CosD(81)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(81)*_vvvvv4*3)+_vvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(81)*_vvvvv4)+_vvvvv2));
 //BA.debugLineNum = 82;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvv3.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 83;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*1.05,C";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,(float) (_vvvvv4*1.05),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 84;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvv3.RemoveClip();
 //BA.debugLineNum = 86;BA.debugLine="Recorte.Initialize(CentroX,CentroY)";
_recorte.Initialize(_vvvvv1,_vvvvv2);
 //BA.debugLineNum = 87;BA.debugLine="Recorte.LineTo( (CosD(116)*Radio*3)+CentroX, (Si";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(116)*_vvvvv4*3)+_vvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(116)*_vvvvv4*3)+_vvvvv2));
 //BA.debugLineNum = 88;BA.debugLine="Recorte.LineTo(0,100%Y)";
_recorte.LineTo((float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 89;BA.debugLine="Recorte.LineTo(0,0)";
_recorte.LineTo((float) (0),(float) (0));
 //BA.debugLineNum = 90;BA.debugLine="Recorte.LineTo(100%X,0)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 91;BA.debugLine="Recorte.LineTo(100%X,100%Y)";
_recorte.LineTo((float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 92;BA.debugLine="Recorte.LineTo( (CosD(80)*Radio*3)+CentroX, (Sin";
_recorte.LineTo((float) ((anywheresoftware.b4a.keywords.Common.CosD(80)*_vvvvv4*3)+_vvvvv1),(float) ((anywheresoftware.b4a.keywords.Common.SinD(80)*_vvvvv4)+_vvvvv2));
 //BA.debugLineNum = 93;BA.debugLine="Pantalla.ClipPath(Recorte)";
mostCurrent._vvvvv3.ClipPath((android.graphics.Path)(_recorte.getObject()));
 //BA.debugLineNum = 94;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio,Colors";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,_vvvvv4,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 95;BA.debugLine="Pantalla.RemoveClip";
mostCurrent._vvvvv3.RemoveClip();
 };
 //BA.debugLineNum = 98;BA.debugLine="For Hora=MinHora To MaxHora Step 1";
{
final int step44 = (int) (1);
final int limit44 = _vvvvv0;
_hora = _vvvvv7 ;
for (;(step44 > 0 && _hora <= limit44) || (step44 < 0 && _hora >= limit44) ;_hora = ((int)(0 + _hora + step44))  ) {
 //BA.debugLineNum = 100;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.95) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 101;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.95) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.95));
 //BA.debugLineNum = 103;BA.debugLine="Pantalla.DrawCircle(X,Y,Radio*0.02,Colors.LightG";
mostCurrent._vvvvv3.DrawCircle(_x,_y,(float) (_vvvvv4*0.02),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 104;BA.debugLine="Dim X=HoraMinuto_X(Hora,0,0.85) As Float";
_x = _horaminuto_x((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 105;BA.debugLine="Dim Y=HoraMinuto_Y(Hora,0,0.85) As Float";
_y = _horaminuto_y((float) (_hora),(float) (0),(float) (0.85));
 //BA.debugLineNum = 106;BA.debugLine="Dim NumeroHora As Label";
_numerohora = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 107;BA.debugLine="NumeroHora.Initialize(\"\")";
_numerohora.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 108;BA.debugLine="NumeroHora.Text=(Hora) Mod 24";
_numerohora.setText(BA.ObjectToCharSequence((_hora)%24));
 //BA.debugLineNum = 109;BA.debugLine="NumeroHora.TextColor=Colors.DarkGray";
_numerohora.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 110;BA.debugLine="NumeroHora.Gravity=Gravity.CENTER";
_numerohora.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 111;BA.debugLine="NumeroHora.TextSize=15";
_numerohora.setTextSize((float) (15));
 //BA.debugLineNum = 112;BA.debugLine="Activity.AddView(NumeroHora,X-15dip,Y-15dip,30di";
mostCurrent._activity.AddView((android.view.View)(_numerohora.getObject()),(int) (_x-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (_y-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 }
};
 //BA.debugLineNum = 115;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==1) { 
 //BA.debugLineNum = 116;BA.debugLine="MinHora=MinHora+12";
_vvvvv7 = (int) (_vvvvv7+12);
 //BA.debugLineNum = 117;BA.debugLine="MaxHora=MaxHora+12";
_vvvvv0 = (int) (_vvvvv0+12);
 };
 //BA.debugLineNum = 121;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step62 = (int) (1);
final int limit62 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].num_actividades-1);
_nactividad = (int) (0) ;
for (;(step62 > 0 && _nactividad <= limit62) || (step62 < 0 && _nactividad >= limit62) ;_nactividad = ((int)(0 + _nactividad + step62))  ) {
 //BA.debugLineNum = 122;BA.debugLine="DibujarActividad(NActividad)";
_vvvv0(_nactividad);
 }
};
 //BA.debugLineNum = 126;BA.debugLine="For NActividad=0 To Starter.Secuencia(Starter.Sec";
{
final int step65 = (int) (1);
final int limit65 = (int) (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].num_actividades-1);
_nactividad = (int) (0) ;
for (;(step65 > 0 && _nactividad <= limit65) || (step65 < 0 && _nactividad >= limit65) ;_nactividad = ((int)(0 + _nactividad + step65))  ) {
 //BA.debugLineNum = 127;BA.debugLine="DibujarBoton(NActividad)";
_vvvvv5(_nactividad);
 }
};
 //BA.debugLineNum = 131;BA.debugLine="Dim HoraActual=DateTime.GetHour(DateTime.Now) As";
_horaactual = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 132;BA.debugLine="Dim MinutoActual=DateTime.GetMinute(DateTime.Now)";
_minutoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 133;BA.debugLine="Dim SegundoActual=DateTime.GetSecond(DateTime.Now";
_segundoactual = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 134;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).ta";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.indicar_hora>0 && _horaactual>=_vvvvv7 && _horaactual<_vvvvv0)) { 
 //BA.debugLineNum = 135;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).t";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==3 || mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.indicar_hora==1)) { 
 //BA.debugLineNum = 136;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
mostCurrent._vvvvv3.DrawLine(_vvvvv1,_vvvvv2,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.8)),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 }else {
 //BA.debugLineNum = 138;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,HoraMinuto_X(";
mostCurrent._vvvvv3.DrawLine(_vvvvv1,_vvvvv2,_horaminuto_x((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),_horaminuto_y((float) (_horaactual),(float) (_minutoactual),(float) (0.6)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 139;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva).";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.indicar_hora>1)) { 
 //BA.debugLineNum = 140;BA.debugLine="Dim AnguloMinuto=270+(MinutoActual*6) As Float";
_angulominuto = (float) (270+(_minutoactual*6));
 //BA.debugLineNum = 141;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+CosD";
mostCurrent._vvvvv3.DrawLine(_vvvvv1,_vvvvv2,(float) (_vvvvv1+anywheresoftware.b4a.keywords.Common.CosD(_angulominuto)*_vvvvv4*0.8),(float) (_vvvvv2+anywheresoftware.b4a.keywords.Common.SinD(_angulominuto)*_vvvvv4*0.75),anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6))));
 //BA.debugLineNum = 142;BA.debugLine="If (Starter.Secuencia(Starter.SecuenciaActiva)";
if ((mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.indicar_hora>2)) { 
 //BA.debugLineNum = 143;BA.debugLine="Dim AnguloSegundo=270+(SegundoActual*6) As Fl";
_angulosegundo = (float) (270+(_segundoactual*6));
 //BA.debugLineNum = 144;BA.debugLine="Pantalla.DrawLine(CentroX,CentroY,CentroX+Cos";
mostCurrent._vvvvv3.DrawLine(_vvvvv1,_vvvvv2,(float) (_vvvvv1+anywheresoftware.b4a.keywords.Common.CosD(_angulosegundo)*_vvvvv4*0.9),(float) (_vvvvv2+anywheresoftware.b4a.keywords.Common.SinD(_angulosegundo)*_vvvvv4*0.9),anywheresoftware.b4a.keywords.Common.Colors.Red,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))));
 };
 };
 };
 };
 //BA.debugLineNum = 151;BA.debugLine="Pantalla.DrawCircle(CentroX,CentroY,Radio*0.1,Col";
mostCurrent._vvvvv3.DrawCircle(_vvvvv1,_vvvvv2,(float) (_vvvvv4*0.1),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 155;BA.debugLine="Select Starter.Secuencia(Starter.SecuenciaActiva)";
switch (BA.switchObjectToInt(mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo,(int) (0),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 157;BA.debugLine="CambiarVista.Text=\"Mañana\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Mañana"));
 break; }
case 1: {
 //BA.debugLineNum = 159;BA.debugLine="CambiarVista.Text=\"Tarde\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Tarde"));
 break; }
case 2: {
 //BA.debugLineNum = 161;BA.debugLine="CambiarVista.Text=\"Día\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Día"));
 break; }
case 3: {
 //BA.debugLineNum = 163;BA.debugLine="CambiarVista.Text=\"Secuencia\"";
mostCurrent._cambiarvista.setText(BA.ObjectToCharSequence("Secuencia"));
 break; }
}
;
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private PanelDescripcion As Panel";
mostCurrent._paneldescripcion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private PanelReloj As Panel";
mostCurrent._panelreloj = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private ImagenPictograma As ImageView";
mostCurrent._imagenpictograma = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private FondoPictograma As Panel";
mostCurrent._fondopictograma = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private TextoPictograma As Label";
mostCurrent._textopictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim Pantalla As Canvas 'Región donde se dibuja el";
mostCurrent._vvvvv3 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim CentroX, CentroY, Radio As Float";
_vvvvv1 = 0f;
_vvvvv2 = 0f;
_vvvvv4 = 0f;
 //BA.debugLineNum = 25;BA.debugLine="Dim MinHora,MaxHora As Int";
_vvvvv7 = 0;
_vvvvv0 = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim Boton(Starter.MaxActividades) As Button";
mostCurrent._vvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper[mostCurrent._vvv3._v0];
{
int d0 = mostCurrent._vvvvv6.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvv6[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 30;BA.debugLine="Private CambiarVista As Button";
mostCurrent._cambiarvista = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private DescripcionPictograma As Label";
mostCurrent._descripcionpictograma = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Volver As Button";
mostCurrent._volver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static int  _vvvv6(int _hora24) throws Exception{
 //BA.debugLineNum = 271;BA.debugLine="Sub Hora24a12 (Hora24 As Int) As Int";
 //BA.debugLineNum = 272;BA.debugLine="If (Hora24>11) Then";
if ((_hora24>11)) { 
 //BA.debugLineNum = 273;BA.debugLine="Return Hora24-12";
if (true) return (int) (_hora24-12);
 }else {
 //BA.debugLineNum = 275;BA.debugLine="Return Hora24";
if (true) return _hora24;
 };
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return 0;
}
public static float  _horaminuto_x(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 245;BA.debugLine="Sub HoraMinuto_X(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 246;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==3) { 
 //BA.debugLineNum = 247;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvv7)*300/(double)(_vvvvv0-_vvvvv7));
 //BA.debugLineNum = 248;BA.debugLine="Return (CosD(Angulo)*Radio*Distancia)+CentroX";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD(_angulo)*_vvvvv4*_distancia)+_vvvvv1);
 }else {
 //BA.debugLineNum = 250;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 251;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 252;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 254;BA.debugLine="Return (CosD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.CosD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvv4*_distancia)+_vvvvv1);
 };
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return 0f;
}
public static float  _horaminuto_y(float _hora,float _minuto,float _distancia) throws Exception{
float _angulo = 0f;
 //BA.debugLineNum = 258;BA.debugLine="Sub HoraMinuto_Y(Hora As Float,Minuto As Float,Dis";
 //BA.debugLineNum = 259;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).tab";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==3) { 
 //BA.debugLineNum = 260;BA.debugLine="Dim Angulo=120+(Hora+Minuto/60-MinHora)*300/(Max";
_angulo = (float) (120+(_hora+_minuto/(double)60-_vvvvv7)*300/(double)(_vvvvv0-_vvvvv7));
 //BA.debugLineNum = 261;BA.debugLine="Return (SinD(Angulo)*Radio*Distancia)+CentroY";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD(_angulo)*_vvvvv4*_distancia)+_vvvvv2);
 }else {
 //BA.debugLineNum = 263;BA.debugLine="If Starter.Secuencia(Starter.SecuenciaActiva).ta";
if (mostCurrent._vvv3._vv7[mostCurrent._vvv3._vv6].tablero.tipo==2) { 
 //BA.debugLineNum = 264;BA.debugLine="Hora=Hora/2";
_hora = (float) (_hora/(double)2);
 //BA.debugLineNum = 265;BA.debugLine="Minuto=Minuto/2";
_minuto = (float) (_minuto/(double)2);
 };
 //BA.debugLineNum = 267;BA.debugLine="Return (SinD((Hora+Minuto/60)*(360/12)+270)*Radi";
if (true) return (float) ((anywheresoftware.b4a.keywords.Common.SinD((_hora+_minuto/(double)60)*(360/(double)12)+270)*_vvvvv4*_distancia)+_vvvvv2);
 };
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return 0f;
}
public static String  _vvvv7(int _minuto) throws Exception{
 //BA.debugLineNum = 279;BA.debugLine="Sub MinutoLegible (Minuto As Int) As String";
 //BA.debugLineNum = 280;BA.debugLine="If (Minuto==0) Then";
if ((_minuto==0)) { 
 //BA.debugLineNum = 281;BA.debugLine="Return \"\"";
if (true) return "";
 }else if((_minuto==15)) { 
 //BA.debugLineNum = 283;BA.debugLine="Return \" y cuarto\"";
if (true) return " y cuarto";
 }else if((_minuto==30)) { 
 //BA.debugLineNum = 285;BA.debugLine="Return \" y media\"";
if (true) return " y media";
 }else {
 //BA.debugLineNum = 287;BA.debugLine="Return \":\"&NumberFormat(Minuto,2,0)";
if (true) return ":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minuto,(int) (2),(int) (0));
 };
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _panelreloj_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Sub PanelReloj_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 314;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _volver_click() throws Exception{
 //BA.debugLineNum = 324;BA.debugLine="Sub Volver_Click";
 //BA.debugLineNum = 325;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
}
