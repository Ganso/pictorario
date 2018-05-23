
package javi.prieto.pictorario;

import java.io.IOException;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.PCBA;
import anywheresoftware.b4a.pc.RDebug;
import anywheresoftware.b4a.pc.RemoteObject;
import anywheresoftware.b4a.pc.RDebug.IRemote;
import anywheresoftware.b4a.pc.Debug;
import anywheresoftware.b4a.pc.B4XTypes.B4XClass;
import anywheresoftware.b4a.pc.B4XTypes.DeviceClass;

public class visualizacion implements IRemote{
	public static visualizacion mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public visualizacion() {
		mostCurrent = this;
	}
    public RemoteObject getRemoteMe() {
        return remoteMe;    
    }
    
	public static void main (String[] args) throws Exception {
		new RDebug(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
		RDebug.INSTANCE.waitForTask();

	}
    static {
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("visualizacion"), "javi.prieto.pictorario.visualizacion");
	}

public boolean isSingleton() {
		return true;
	}
     public static RemoteObject getObject() {
		return myClass;
	 }

	public RemoteObject activityBA;
	public RemoteObject _activity;
    private PCBA pcBA;

	public PCBA create(Object[] args) throws ClassNotFoundException{
		processBA = (RemoteObject) args[1];
		activityBA = (RemoteObject) args[2];
		_activity = (RemoteObject) args[3];
        anywheresoftware.b4a.keywords.Common.Density = (Float)args[4];
        remoteMe = (RemoteObject) args[5];
		pcBA = new PCBA(this, visualizacion.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _paneldescripcion = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _panelreloj = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _imagenpictograma = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _fondopictograma = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _textopictograma = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _pantalla = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper");
public static RemoteObject _centrox = RemoteObject.createImmutable(0f);
public static RemoteObject _centroy = RemoteObject.createImmutable(0f);
public static RemoteObject _radio = RemoteObject.createImmutable(0f);
public static RemoteObject _minhora = RemoteObject.createImmutable(0);
public static RemoteObject _maxhora = RemoteObject.createImmutable(0);
public static RemoteObject _boton = null;
public static RemoteObject _cambiarvista = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _descripcionpictograma = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static javi.prieto.pictorario.main _main = null;
public static javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public static javi.prieto.pictorario.starter _starter = null;
public static javi.prieto.pictorario.acercade _acercade = null;
  public Object[] GetGlobals() {
		return new Object[] {"AcercaDe",Debug.moduleToString(javi.prieto.pictorario.acercade.class),"Activity",visualizacion.mostCurrent._activity,"Boton",visualizacion.mostCurrent._boton,"CambiarVista",visualizacion.mostCurrent._cambiarvista,"CentroX",visualizacion._centrox,"CentroY",visualizacion._centroy,"ConfigurarSecuencia",Debug.moduleToString(javi.prieto.pictorario.configurarsecuencia.class),"DescripcionPictograma",visualizacion.mostCurrent._descripcionpictograma,"FondoPictograma",visualizacion.mostCurrent._fondopictograma,"ImagenPictograma",visualizacion.mostCurrent._imagenpictograma,"Main",Debug.moduleToString(javi.prieto.pictorario.main.class),"MaxHora",visualizacion._maxhora,"MinHora",visualizacion._minhora,"PanelDescripcion",visualizacion.mostCurrent._paneldescripcion,"PanelReloj",visualizacion.mostCurrent._panelreloj,"Pantalla",visualizacion.mostCurrent._pantalla,"Radio",visualizacion._radio,"Starter",Debug.moduleToString(javi.prieto.pictorario.starter.class),"TextoPictograma",visualizacion.mostCurrent._textopictograma};
}
}