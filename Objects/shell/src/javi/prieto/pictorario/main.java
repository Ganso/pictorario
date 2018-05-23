
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

public class main implements IRemote{
	public static main mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public main() {
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
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("main"), "javi.prieto.pictorario.main");
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
		pcBA = new PCBA(this, main.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _pictogramasecuencia = null;
public static RemoteObject _editarsecuencia = null;
public static RemoteObject _etiquetasecuencia = null;
public static RemoteObject _botonacercade = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _botoncrear = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _panelscroll = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public static javi.prieto.pictorario.starter _starter = null;
public static javi.prieto.pictorario.visualizacion _visualizacion = null;
public static javi.prieto.pictorario.acercade _acercade = null;
  public Object[] GetGlobals() {
		return new Object[] {"AcercaDe",Debug.moduleToString(javi.prieto.pictorario.acercade.class),"Activity",main.mostCurrent._activity,"BotonAcercaDe",main.mostCurrent._botonacercade,"BotonCrear",main.mostCurrent._botoncrear,"ConfigurarSecuencia",Debug.moduleToString(javi.prieto.pictorario.configurarsecuencia.class),"EditarSecuencia",main.mostCurrent._editarsecuencia,"EtiquetaSecuencia",main.mostCurrent._etiquetasecuencia,"PanelScroll",main.mostCurrent._panelscroll,"PictogramaSecuencia",main.mostCurrent._pictogramasecuencia,"Starter",Debug.moduleToString(javi.prieto.pictorario.starter.class),"Visualizacion",Debug.moduleToString(javi.prieto.pictorario.visualizacion.class)};
}
}