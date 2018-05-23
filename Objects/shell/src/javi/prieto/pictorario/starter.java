
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

public class starter implements IRemote{
	public static starter mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public starter() {
		mostCurrent = this;
	}
    public RemoteObject getRemoteMe() {
        return remoteMe;    
    }
    
public boolean isSingleton() {
		return true;
	}
    static {
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("starter"), "javi.prieto.pictorario.starter");
	}
     public static RemoteObject getObject() {
		return myClass;
	 }
	public RemoteObject _service;
    private PCBA pcBA;

	public PCBA create(Object[] args) throws ClassNotFoundException{
		processBA = (RemoteObject) args[1];
        _service = (RemoteObject) args[2];
        remoteMe = RemoteObject.declareNull("javi.prieto.pictorario.starter");
        anywheresoftware.b4a.keywords.Common.Density = (Float)args[3];
		pcBA = new PCBA(this, starter.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _kvs = RemoteObject.declareNull("b4a.example3.keyvaluestore");
public static RemoteObject _maxsecuencias = RemoteObject.createImmutable(0);
public static RemoteObject _maxactividades = RemoteObject.createImmutable(0);
public static RemoteObject _descripciontablero = null;
public static RemoteObject _descripcionminutero = null;
public static RemoteObject _maxcolores = RemoteObject.createImmutable(0);
public static RemoteObject _colores = null;
public static RemoteObject _numsecuencias = RemoteObject.createImmutable(0);
public static RemoteObject _secuenciaactiva = RemoteObject.createImmutable(0);
public static RemoteObject _secuencia = null;
public static RemoteObject _actividadsecuencia = null;
public static javi.prieto.pictorario.main _main = null;
public static javi.prieto.pictorario.configurarsecuencia _configurarsecuencia = null;
public static javi.prieto.pictorario.visualizacion _visualizacion = null;
public static javi.prieto.pictorario.acercade _acercade = null;
  public Object[] GetGlobals() {
		return new Object[] {"AcercaDe",Debug.moduleToString(javi.prieto.pictorario.acercade.class),"ActividadSecuencia",starter._actividadsecuencia,"Colores",starter._colores,"ConfigurarSecuencia",Debug.moduleToString(javi.prieto.pictorario.configurarsecuencia.class),"DescripcionMinutero",starter._descripcionminutero,"DescripcionTablero",starter._descripciontablero,"kvs",starter._kvs,"Main",Debug.moduleToString(javi.prieto.pictorario.main.class),"MaxActividades",starter._maxactividades,"MaxColores",starter._maxcolores,"MaxSecuencias",starter._maxsecuencias,"NumSecuencias",starter._numsecuencias,"Secuencia",starter._secuencia,"SecuenciaActiva",starter._secuenciaactiva,"Service",starter.mostCurrent._service,"Visualizacion",Debug.moduleToString(javi.prieto.pictorario.visualizacion.class)};
}
}