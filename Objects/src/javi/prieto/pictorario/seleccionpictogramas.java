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

public class seleccionpictogramas extends Activity implements B4AActivity{
	public static seleccionpictogramas mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "javi.prieto.pictorario", "javi.prieto.pictorario.seleccionpictogramas");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (seleccionpictogramas).");
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
		activityBA = new BA(this, layout, processBA, "javi.prieto.pictorario", "javi.prieto.pictorario.seleccionpictogramas");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "javi.prieto.pictorario.seleccionpictogramas", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (seleccionpictogramas) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (seleccionpictogramas) Resume **");
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
		return seleccionpictogramas.class;
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
        BA.LogInfo("** Activity (seleccionpictogramas) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            seleccionpictogramas mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (seleccionpictogramas) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _barrabusqueda = null;
public anywheresoftware.b4a.objects.LabelWrapper _botonbuscar = null;
public anywheresoftware.b4a.objects.PanelWrapper _buscar = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _listadopictogramas = null;
public anywheresoftware.b4a.objects.LabelWrapper _titulo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _botoncancelar = null;
public static String _vvvvvvvvvvvv2 = "";
public static int _vvvvvvvvvvvv7 = 0;
public static int _vvvvvvvvvvvv0 = 0;
public anywheresoftware.b4a.objects.ButtonWrapper[][] _vvvvvvvvvvvvv1 = null;
public static int _vvvvvvvvvvvv3 = 0;
public b4a.example.dateutils _vvvvvvvvv1 = null;
public b4a.example.versione06 _vvvvvvvvv2 = null;
public javi.prieto.pictorario.main _vvvvvvvvv3 = null;
public javi.prieto.pictorario.visualizacion _vvvvvvvvv5 = null;
public javi.prieto.pictorario.acercade _vvvvvvvvv6 = null;
public javi.prieto.pictorario.configuracion _vvvvvvvvv7 = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"SeleccionarPictograma\")";
mostCurrent._activity.LoadLayout("SeleccionarPictograma",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="BotonBuscar.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._botonbuscar.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"buscar.png").getObject()));
 //BA.debugLineNum = 39;BA.debugLine="BarraBusqueda.Text=TextoBuscarPorDefecto";
mostCurrent._barrabusqueda.setText(BA.ObjectToCharSequence(mostCurrent._vvvvvvvvvvvv2));
 //BA.debugLineNum = 40;BA.debugLine="TamIcono=ListadoPictogramas.Width/3";
_vvvvvvvvvvvv3 = (int) (mostCurrent._listadopictogramas.getWidth()/(double)3);
 //BA.debugLineNum = 41;BA.debugLine="DibujaIconos";
_vvvvvvvvvvvv4();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static void  _activity_keypress(int _keycode) throws Exception{
ResumableSub_Activity_KeyPress rsub = new ResumableSub_Activity_KeyPress(null,_keycode);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_KeyPress extends BA.ResumableSub {
public ResumableSub_Activity_KeyPress(javi.prieto.pictorario.seleccionpictogramas parent,int _keycode) {
this.parent = parent;
this._keycode = _keycode;
}
javi.prieto.pictorario.seleccionpictogramas parent;
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
 //BA.debugLineNum = 253;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then 'Al pulsa";
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
 //BA.debugLineNum = 254;BA.debugLine="Sleep(0) 'No hace nada";
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
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _barrabusqueda_enterpressed() throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub BarraBusqueda_EnterPressed";
 //BA.debugLineNum = 72;BA.debugLine="BuscarTexto";
_vvvvvvvvvvvv5();
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _barrabusqueda_focuschanged(boolean _tienefoco) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub BarraBusqueda_FocusChanged (TieneFoco As Boole";
 //BA.debugLineNum = 65;BA.debugLine="If TieneFoco==True And BarraBusqueda.Text==TextoB";
if (_tienefoco==anywheresoftware.b4a.keywords.Common.True && (mostCurrent._barrabusqueda.getText()).equals(mostCurrent._vvvvvvvvvvvv2)) { 
 //BA.debugLineNum = 66;BA.debugLine="BarraBusqueda.Text=\"\"";
mostCurrent._barrabusqueda.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 67;BA.debugLine="Activity.Invalidate";
mostCurrent._activity.Invalidate();
 };
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _botonbuscar_click() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub BotonBuscar_Click";
 //BA.debugLineNum = 60;BA.debugLine="BuscarTexto";
_vvvvvvvvvvvv5();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _botoncancelar_click() throws Exception{
 //BA.debugLineNum = 199;BA.debugLine="Sub BotonCancelar_Click";
 //BA.debugLineNum = 200;BA.debugLine="CallSubDelayed2(ConfigurarSecuencia,\"PictogramaEl";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"PictogramaElegido",(Object)(-1));
 //BA.debugLineNum = 201;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _botonicono_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _botonpulsado = null;
 //BA.debugLineNum = 52;BA.debugLine="Sub BotonIcono_Click";
 //BA.debugLineNum = 53;BA.debugLine="Dim BotonPulsado As Button";
_botonpulsado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="BotonPulsado=Sender";
_botonpulsado.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 55;BA.debugLine="CallSubDelayed2(ConfigurarSecuencia,\"PictogramaEl";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._vvvvvvvvvv3.getObject()),"PictogramaElegido",_botonpulsado.getTag());
 //BA.debugLineNum = 56;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvvvvvv5() throws Exception{
ResumableSub_BuscarTexto rsub = new ResumableSub_BuscarTexto(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_BuscarTexto extends BA.ResumableSub {
public ResumableSub_BuscarTexto(javi.prieto.pictorario.seleccionpictogramas parent) {
this.parent = parent;
}
javi.prieto.pictorario.seleccionpictogramas parent;
int[] _id = null;
String[] _url = null;
int _i = 0;
int _x = 0;
int _y = 0;
anywheresoftware.b4a.objects.IME _im = null;
javi.prieto.pictorario.httpjob _web = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
javi.prieto.pictorario.httpjob _j = null;
int _encontrados = 0;
anywheresoftware.b4a.objects.collections.JSONParser _jp = null;
anywheresoftware.b4a.objects.collections.List _pictogramasencontrados = null;
anywheresoftware.b4a.objects.collections.Map _pictograma = null;
int _resultado = 0;
anywheresoftware.b4a.BA.IterableList group19;
int index19;
int groupLen19;
int step29;
int limit29;
int step34;
int limit34;
int step40;
int limit40;
int step41;
int limit41;
int step47;
int limit47;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 76;BA.debugLine="Dim id(100) As Int";
_id = new int[(int) (100)];
;
 //BA.debugLineNum = 77;BA.debugLine="Dim URL(100) As String";
_url = new String[(int) (100)];
java.util.Arrays.fill(_url,"");
 //BA.debugLineNum = 79;BA.debugLine="Dim i,x,y As Int";
_i = 0;
_x = 0;
_y = 0;
 //BA.debugLineNum = 81;BA.debugLine="If BarraBusqueda.Text<>TextoBuscarPorDefecto Then";
if (true) break;

case 1:
//if
this.state = 49;
if ((parent.mostCurrent._barrabusqueda.getText()).equals(parent.mostCurrent._vvvvvvvvvvvv2) == false) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 82;BA.debugLine="Dim im As IME";
_im = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 83;BA.debugLine="im.Initialize(\"\")";
_im.Initialize("");
 //BA.debugLineNum = 84;BA.debugLine="im.HideKeyboard 'Ocultamos el teclado";
_im.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 86;BA.debugLine="ProgressDialogShow(\"Buscando pictogramas\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando pictogramas"));
 //BA.debugLineNum = 89;BA.debugLine="Dim Web As HttpJob";
_web = new javi.prieto.pictorario.httpjob();
 //BA.debugLineNum = 90;BA.debugLine="Web.Initialize(\"buscapictogramas\",Me)";
_web._initialize /*String*/ (processBA,"buscapictogramas",seleccionpictogramas.getObject());
 //BA.debugLineNum = 91;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 92;BA.debugLine="Web.Download(\"https://api.arasaac.org/api/pictog";
_web._vvvvv2 /*String*/ ("https://api.arasaac.org/api/pictograms/es/search/"+_su.EncodeUrl(parent.mostCurrent._barrabusqueda.getText(),"UTF8").replace("+","%20"));
 //BA.debugLineNum = 93;BA.debugLine="Wait For (Web) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_web));
this.state = 50;
return;
case 50:
//C
this.state = 4;
_j = (javi.prieto.pictorario.httpjob) result[0];
;
 //BA.debugLineNum = 94;BA.debugLine="Dim encontrados As Int = 0";
_encontrados = (int) (0);
 //BA.debugLineNum = 95;BA.debugLine="If j.Success Then";
if (true) break;

case 4:
//if
this.state = 13;
if (_j._vvvvvvv0 /*boolean*/ ) { 
this.state = 6;
}else {
this.state = 12;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 96;BA.debugLine="Dim jp As JSONParser";
_jp = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 97;BA.debugLine="jp.Initialize(j.GetString)";
_jp.Initialize(_j._vvvvvv2 /*String*/ ());
 //BA.debugLineNum = 98;BA.debugLine="Dim PictogramasEncontrados As List = jp.NextArr";
_pictogramasencontrados = new anywheresoftware.b4a.objects.collections.List();
_pictogramasencontrados = _jp.NextArray();
 //BA.debugLineNum = 99;BA.debugLine="For Each Pictograma As Map In PictogramasEncont";
if (true) break;

case 7:
//for
this.state = 10;
_pictograma = new anywheresoftware.b4a.objects.collections.Map();
group19 = _pictogramasencontrados;
index19 = 0;
groupLen19 = group19.getSize();
this.state = 51;
if (true) break;

case 51:
//C
this.state = 10;
if (index19 < groupLen19) {
this.state = 9;
_pictograma.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group19.Get(index19)));}
if (true) break;

case 52:
//C
this.state = 51;
index19++;
if (true) break;

case 9:
//C
this.state = 52;
 //BA.debugLineNum = 100;BA.debugLine="id(encontrados)=Pictograma.Get(\"_id\")";
_id[_encontrados] = (int)(BA.ObjectToNumber(_pictograma.Get((Object)("_id"))));
 //BA.debugLineNum = 101;BA.debugLine="encontrados=encontrados+1";
_encontrados = (int) (_encontrados+1);
 if (true) break;
if (true) break;

case 10:
//C
this.state = 13;
;
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 104;BA.debugLine="Msgbox(\"La búsqueda no ha producido resultados.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La búsqueda no ha producido resultados."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Pruebe otros términos, y compruebe que la conexión a Internet está activa."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Las búsquedas no funcionan si no hay conexión a la red."),BA.ObjectToCharSequence("NO SE ENCUENTRAN PICTOGRAMAS"),mostCurrent.activityBA);
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 106;BA.debugLine="j.Release";
_j._vvvvvvv6 /*String*/ ();
 //BA.debugLineNum = 107;BA.debugLine="Web.Release";
_web._vvvvvvv6 /*String*/ ();
 //BA.debugLineNum = 109;BA.debugLine="ProgressDialogShow(\"Descargando imágenes\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Descargando imágenes"));
 //BA.debugLineNum = 113;BA.debugLine="For i=0 To encontrados-1";
if (true) break;

case 14:
//for
this.state = 21;
step29 = 1;
limit29 = (int) (_encontrados-1);
_i = (int) (0) ;
this.state = 53;
if (true) break;

case 53:
//C
this.state = 21;
if ((step29 > 0 && _i <= limit29) || (step29 < 0 && _i >= limit29)) this.state = 16;
if (true) break;

case 54:
//C
this.state = 53;
_i = ((int)(0 + _i + step29)) ;
if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 114;BA.debugLine="If File.Exists(Starter.DirPictogramas,id(i)&\".p";
if (true) break;

case 17:
//if
this.state = 20;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,BA.NumberToString(_id[_i])+".png")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 118;BA.debugLine="URL(i)=\"https://static.arasaac.org/pictograms/";
_url[_i] = "https://static.arasaac.org/pictograms/"+BA.NumberToString(_id[_i])+"/"+BA.NumberToString(_id[_i])+"_500.png";
 if (true) break;

case 20:
//C
this.state = 54;
;
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 122;BA.debugLine="For i=0 To encontrados-1";

case 21:
//for
this.state = 28;
step34 = 1;
limit34 = (int) (_encontrados-1);
_i = (int) (0) ;
this.state = 55;
if (true) break;

case 55:
//C
this.state = 28;
if ((step34 > 0 && _i <= limit34) || (step34 < 0 && _i >= limit34)) this.state = 23;
if (true) break;

case 56:
//C
this.state = 55;
_i = ((int)(0 + _i + step34)) ;
if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 123;BA.debugLine="If File.Exists(Starter.DirPictogramas,id(i)&\".p";
if (true) break;

case 24:
//if
this.state = 27;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,BA.NumberToString(_id[_i])+".png")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 26;
}if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 124;BA.debugLine="Wait For (DescargarPictograma(id(i),URL(i)))";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _vvvvvvvvvvvv6(_id[_i],_url[_i]));
this.state = 57;
return;
case 57:
//C
this.state = 27;
_resultado = (Integer) result[0];
;
 if (true) break;

case 27:
//C
this.state = 56;
;
 if (true) break;
if (true) break;

case 28:
//C
this.state = 29;
;
 //BA.debugLineNum = 128;BA.debugLine="ProgressDialogShow(\"Preparando listado\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparando listado"));
 //BA.debugLineNum = 130;BA.debugLine="For x=0 To NumColumnas-1";
if (true) break;

case 29:
//for
this.state = 36;
step40 = 1;
limit40 = (int) (parent._vvvvvvvvvvvv7-1);
_x = (int) (0) ;
this.state = 58;
if (true) break;

case 58:
//C
this.state = 36;
if ((step40 > 0 && _x <= limit40) || (step40 < 0 && _x >= limit40)) this.state = 31;
if (true) break;

case 59:
//C
this.state = 58;
_x = ((int)(0 + _x + step40)) ;
if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 131;BA.debugLine="For y=0 To NumLineas-1";
if (true) break;

case 32:
//for
this.state = 35;
step41 = 1;
limit41 = (int) (parent._vvvvvvvvvvvv0-1);
_y = (int) (0) ;
this.state = 60;
if (true) break;

case 60:
//C
this.state = 35;
if ((step41 > 0 && _y <= limit41) || (step41 < 0 && _y >= limit41)) this.state = 34;
if (true) break;

case 61:
//C
this.state = 60;
_y = ((int)(0 + _y + step41)) ;
if (true) break;

case 34:
//C
this.state = 61;
 //BA.debugLineNum = 132;BA.debugLine="BotonIcono(y,x).Visible=False 'Oculta todos lo";
parent.mostCurrent._vvvvvvvvvvvvv1[_y][_x].setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
if (true) break;

case 35:
//C
this.state = 59;
;
 if (true) break;
if (true) break;

case 36:
//C
this.state = 37;
;
 //BA.debugLineNum = 136;BA.debugLine="x=0";
_x = (int) (0);
 //BA.debugLineNum = 137;BA.debugLine="y=0";
_y = (int) (0);
 //BA.debugLineNum = 138;BA.debugLine="For i=0 To encontrados-1 'Muestra los nuevos ico";
if (true) break;

case 37:
//for
this.state = 48;
step47 = 1;
limit47 = (int) (_encontrados-1);
_i = (int) (0) ;
this.state = 62;
if (true) break;

case 62:
//C
this.state = 48;
if ((step47 > 0 && _i <= limit47) || (step47 < 0 && _i >= limit47)) this.state = 39;
if (true) break;

case 63:
//C
this.state = 62;
_i = ((int)(0 + _i + step47)) ;
if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 139;BA.debugLine="If y<NumLineas Then";
if (true) break;

case 40:
//if
this.state = 47;
if (_y<parent._vvvvvvvvvvvv0) { 
this.state = 42;
}if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 140;BA.debugLine="BotonIcono(y,x).SetBackgroundImage(LoadBitmap(";
parent.mostCurrent._vvvvvvvvvvvvv1[_y][_x].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"/pictogramas"),BA.NumberToString(_id[_i])+".png").getObject()));
 //BA.debugLineNum = 141;BA.debugLine="BotonIcono(y,x).Tag=id(i)";
parent.mostCurrent._vvvvvvvvvvvvv1[_y][_x].setTag((Object)(_id[_i]));
 //BA.debugLineNum = 142;BA.debugLine="BotonIcono(y,x).Visible=True";
parent.mostCurrent._vvvvvvvvvvvvv1[_y][_x].setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 143;BA.debugLine="ListadoPictogramas.Panel.Height=(y+1)*TamIcono";
parent.mostCurrent._listadopictogramas.getPanel().setHeight((int) ((_y+1)*parent._vvvvvvvvvvvv3));
 //BA.debugLineNum = 144;BA.debugLine="x=x+1";
_x = (int) (_x+1);
 //BA.debugLineNum = 145;BA.debugLine="If (x=NumColumnas) Then";
if (true) break;

case 43:
//if
this.state = 46;
if ((_x==parent._vvvvvvvvvvvv7)) { 
this.state = 45;
}if (true) break;

case 45:
//C
this.state = 46;
 //BA.debugLineNum = 146;BA.debugLine="x=0";
_x = (int) (0);
 //BA.debugLineNum = 147;BA.debugLine="y=y+1";
_y = (int) (_y+1);
 if (true) break;

case 46:
//C
this.state = 47;
;
 if (true) break;

case 47:
//C
this.state = 63;
;
 if (true) break;
if (true) break;

case 48:
//C
this.state = 49;
;
 //BA.debugLineNum = 152;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 49:
//C
this.state = -1;
;
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(javi.prieto.pictorario.httpjob _j) throws Exception{
}
public static void  _complete(int _resultado) throws Exception{
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _vvvvvvvvvvvv6(int _id,String _url) throws Exception{
ResumableSub_DescargarPictograma rsub = new ResumableSub_DescargarPictograma(null,_id,_url);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_DescargarPictograma extends BA.ResumableSub {
public ResumableSub_DescargarPictograma(javi.prieto.pictorario.seleccionpictogramas parent,int _id,String _url) {
this.parent = parent;
this._id = _id;
this._url = _url;
}
javi.prieto.pictorario.seleccionpictogramas parent;
int _id;
String _url;
javi.prieto.pictorario.httpjob _web3 = null;
javi.prieto.pictorario.httpjob _j3 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _fichero = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 159;BA.debugLine="Dim Web3 As HttpJob";
_web3 = new javi.prieto.pictorario.httpjob();
 //BA.debugLineNum = 160;BA.debugLine="Web3.Initialize(\"bajapictograma\",Me)";
_web3._initialize /*String*/ (processBA,"bajapictograma",seleccionpictogramas.getObject());
 //BA.debugLineNum = 161;BA.debugLine="Web3.Download(URL)";
_web3._vvvvv2 /*String*/ (_url);
 //BA.debugLineNum = 163;BA.debugLine="Wait For (Web3) JobDone(j3 As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_web3));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_j3 = (javi.prieto.pictorario.httpjob) result[0];
;
 //BA.debugLineNum = 164;BA.debugLine="If j3.Success Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_j3._vvvvvvv0 /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 165;BA.debugLine="Dim Fichero As OutputStream";
_fichero = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Fichero = File.OpenOutput(Starter.DirPictogramas";
_fichero = anywheresoftware.b4a.keywords.Common.File.OpenOutput(parent.mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,BA.NumberToString(_id)+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="File.Copy2(Web3.GetInputStream, Fichero)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_web3._vvvvv0 /*anywheresoftware.b4a.objects.streams.File.InputStreamWrapper*/ ().getObject()),(java.io.OutputStream)(_fichero.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="Fichero.Close";
_fichero.Close();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 170;BA.debugLine="j3.Release";
_j3._vvvvvvv6 /*String*/ ();
 //BA.debugLineNum = 171;BA.debugLine="Web3.Release";
_web3._vvvvvvv6 /*String*/ ();
 //BA.debugLineNum = 172;BA.debugLine="Return 0";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(0));return;};
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _vvvvvvvvvvvv4() throws Exception{
int _x = 0;
int _y = 0;
 //BA.debugLineNum = 204;BA.debugLine="Sub DibujaIconos";
 //BA.debugLineNum = 205;BA.debugLine="Dim x,y As Int";
_x = 0;
_y = 0;
 //BA.debugLineNum = 207;BA.debugLine="For x=0 To NumColumnas-1";
{
final int step2 = 1;
final int limit2 = (int) (_vvvvvvvvvvvv7-1);
_x = (int) (0) ;
for (;_x <= limit2 ;_x = _x + step2 ) {
 //BA.debugLineNum = 208;BA.debugLine="For y=0 To NumLineas-1";
{
final int step3 = 1;
final int limit3 = (int) (_vvvvvvvvvvvv0-1);
_y = (int) (0) ;
for (;_y <= limit3 ;_y = _y + step3 ) {
 //BA.debugLineNum = 209;BA.debugLine="BotonIcono(y,x).Initialize(\"BotonIcono\")";
mostCurrent._vvvvvvvvvvvvv1[_y][_x].Initialize(mostCurrent.activityBA,"BotonIcono");
 //BA.debugLineNum = 210;BA.debugLine="ListadoPictogramas.Panel.AddView(BotonIcono(y,x";
mostCurrent._listadopictogramas.getPanel().AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv1[_y][_x].getObject()),(int) (_x*_vvvvvvvvvvvv3),(int) (_y*_vvvvvvvvvvvv3),_vvvvvvvvvvvv3,_vvvvvvvvvvvv3);
 //BA.debugLineNum = 211;BA.debugLine="BotonIcono(y,x).Visible=False";
mostCurrent._vvvvvvvvvvvvv1[_y][_x].setVisible(anywheresoftware.b4a.keywords.Common.False);
 }
};
 }
};
 //BA.debugLineNum = 214;BA.debugLine="ListadoPictogramas.Panel.Height=0 'Ningún icono v";
mostCurrent._listadopictogramas.getPanel().setHeight((int) (0));
 //BA.debugLineNum = 216;BA.debugLine="RellenarIconos";
_vvvvvvvvvvvvv2();
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private BarraBusqueda As EditText";
mostCurrent._barrabusqueda = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private BotonBuscar As Label";
mostCurrent._botonbuscar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Buscar As Panel";
mostCurrent._buscar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private ListadoPictogramas As ScrollView";
mostCurrent._listadopictogramas = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Titulo As Label";
mostCurrent._titulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private BotonCancelar As Button";
mostCurrent._botoncancelar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim TextoBuscarPorDefecto=\"Buscar pictograma por";
mostCurrent._vvvvvvvvvvvv2 = "Buscar pictograma por texto";
 //BA.debugLineNum = 25;BA.debugLine="Dim NumColumnas As Int = 3";
_vvvvvvvvvvvv7 = (int) (3);
 //BA.debugLineNum = 26;BA.debugLine="Dim NumLineas As Int = 20";
_vvvvvvvvvvvv0 = (int) (20);
 //BA.debugLineNum = 29;BA.debugLine="Dim BotonIcono(NumLineas,NumColumnas) As Button";
mostCurrent._vvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.ButtonWrapper[_vvvvvvvvvvvv0][];
{
int d0 = mostCurrent._vvvvvvvvvvvvv1.length;
int d1 = _vvvvvvvvvvvv7;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.ButtonWrapper[d1];
for (int i1 = 0;i1 < d1;i1++) {
mostCurrent._vvvvvvvvvvvvv1[i0][i1] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
}
;
 //BA.debugLineNum = 32;BA.debugLine="Dim TamIcono As Int";
_vvvvvvvvvvvv3 = 0;
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv2() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _i = 0;
int _x = 0;
int _y = 0;
int _numficheros = 0;
int _id = 0;
String _nombrefich = "";
 //BA.debugLineNum = 219;BA.debugLine="Sub RellenarIconos";
 //BA.debugLineNum = 221;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 222;BA.debugLine="Dim i,x,y,NumFicheros,id As Int";
_i = 0;
_x = 0;
_y = 0;
_numficheros = 0;
_id = 0;
 //BA.debugLineNum = 223;BA.debugLine="Dim NombreFich As String";
_nombrefich = "";
 //BA.debugLineNum = 225;BA.debugLine="fileList = File.ListFiles(Starter.DirPictogramas)";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ );
 //BA.debugLineNum = 226;BA.debugLine="fileList.Sort(False)";
_filelist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 228;BA.debugLine="NumFicheros=fileList.Size";
_numficheros = _filelist.getSize();
 //BA.debugLineNum = 229;BA.debugLine="If NumFicheros>(NumLineas*NumColumnas) Then";
if (_numficheros>(_vvvvvvvvvvvv0*_vvvvvvvvvvvv7)) { 
 //BA.debugLineNum = 230;BA.debugLine="NumFicheros=NumLineas*NumColumnas";
_numficheros = (int) (_vvvvvvvvvvvv0*_vvvvvvvvvvvv7);
 };
 //BA.debugLineNum = 233;BA.debugLine="x=0";
_x = (int) (0);
 //BA.debugLineNum = 234;BA.debugLine="y=0";
_y = (int) (0);
 //BA.debugLineNum = 235;BA.debugLine="For i=0 To NumFicheros-1";
{
final int step12 = 1;
final int limit12 = (int) (_numficheros-1);
_i = (int) (0) ;
for (;_i <= limit12 ;_i = _i + step12 ) {
 //BA.debugLineNum = 236;BA.debugLine="If y<NumLineas Then";
if (_y<_vvvvvvvvvvvv0) { 
 //BA.debugLineNum = 237;BA.debugLine="NombreFich=fileList.Get(i)";
_nombrefich = BA.ObjectToString(_filelist.Get(_i));
 //BA.debugLineNum = 238;BA.debugLine="BotonIcono(y,x).SetBackgroundImage(LoadBitmap(S";
mostCurrent._vvvvvvvvvvvvv1[_y][_x].SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._vvvvvvvvvv2._vvvv5 /*String*/ ,_nombrefich).getObject()));
 //BA.debugLineNum = 239;BA.debugLine="id=Regex.Replace(\"[^0-9]\",NombreFich,\"\")";
_id = (int)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.Regex.Replace("[^0-9]",_nombrefich,"")));
 //BA.debugLineNum = 240;BA.debugLine="BotonIcono(y,x).Tag=id";
mostCurrent._vvvvvvvvvvvvv1[_y][_x].setTag((Object)(_id));
 //BA.debugLineNum = 241;BA.debugLine="BotonIcono(y,x).Visible=True";
mostCurrent._vvvvvvvvvvvvv1[_y][_x].setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 242;BA.debugLine="ListadoPictogramas.Panel.Height=(y+1)*TamIcono";
mostCurrent._listadopictogramas.getPanel().setHeight((int) ((_y+1)*_vvvvvvvvvvvv3));
 //BA.debugLineNum = 243;BA.debugLine="x=x+1";
_x = (int) (_x+1);
 //BA.debugLineNum = 244;BA.debugLine="If (x=NumColumnas) Then";
if ((_x==_vvvvvvvvvvvv7)) { 
 //BA.debugLineNum = 245;BA.debugLine="x=0";
_x = (int) (0);
 //BA.debugLineNum = 246;BA.debugLine="y=y+1";
_y = (int) (_y+1);
 };
 };
 }
};
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
}
