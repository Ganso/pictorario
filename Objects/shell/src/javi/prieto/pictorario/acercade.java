
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

public class acercade implements IRemote{
	public static acercade mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public acercade() {
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
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("acercade"), "javi.prieto.pictorario.acercade");
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
		pcBA = new PCBA(this, acercade.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _volver = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _logotipo = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _pictogramas = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _pictorario = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _programador = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _textoarasaac = RemoteObject.declareNull("anywheresoftware.b4a.objects.WebViewWrapper");
public static RemoteObject _textoautor = RemoteObject.declareNull("anywheresoftware.b4a.objects.WebViewWrapper");
public static javi.prieto.pictorario.main _main = null;
public static javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public static javi.prieto.pictorario.starter _starter = null;
public static javi.prieto.pictorario.visualizacion _visualizacion = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",acercade.mostCurrent._activity,"ConfigurarSecuencia",Debug.moduleToString(javi.prieto.pictorario.configurarsecuencia.class),"Logotipo",acercade.mostCurrent._logotipo,"Main",Debug.moduleToString(javi.prieto.pictorario.main.class),"Pictogramas",acercade.mostCurrent._pictogramas,"Pictorario",acercade.mostCurrent._pictorario,"Programador",acercade.mostCurrent._programador,"Starter",Debug.moduleToString(javi.prieto.pictorario.starter.class),"TextoArasaac",acercade.mostCurrent._textoarasaac,"TextoAutor",acercade.mostCurrent._textoautor,"Visualizacion",Debug.moduleToString(javi.prieto.pictorario.visualizacion.class),"Volver",acercade.mostCurrent._volver};
}
}