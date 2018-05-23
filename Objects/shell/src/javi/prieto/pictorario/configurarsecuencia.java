
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

public class configurarsecuencia implements IRemote{
	public static configurarsecuencia mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public configurarsecuencia() {
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
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("configurarsecuencia"), "javi.prieto.pictorario.configurarsecuencia");
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
		pcBA = new PCBA(this, configurarsecuencia.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _separacionhorizontal = RemoteObject.createImmutable(0);
public static RemoteObject _tamcasilla = RemoteObject.createImmutable(0);
public static RemoteObject _separacioncasillas = RemoteObject.createImmutable(0);
public static RemoteObject _colordefondo = RemoteObject.createImmutable(0);
public static RemoteObject _parametrossecuencia = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static RemoteObject _etiquetainicial = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _configdescripcion = RemoteObject.declareNull("anywheresoftware.b4a.objects.EditTextWrapper");
public static RemoteObject _configpictograma = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _etiqtipotablero = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _configtipotablero = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _etiqindicarhora = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _configindicarhora = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _etiqtamicono = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _configtamicono = RemoteObject.declareNull("anywheresoftware.b4a.objects.SeekBarWrapper");
public static RemoteObject _etiqactividades = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _configdescripcionact = null;
public static RemoteObject _confighorainicioact = null;
public static RemoteObject _confighorafinalact = null;
public static RemoteObject _configpictogramaact = null;
public static RemoteObject _botonanadiractividad = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _botonaceptar = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _botoncancelar = RemoteObject.declareNull("anywheresoftware.b4a.objects.ButtonWrapper");
public static RemoteObject _inicializando = RemoteObject.createImmutable(false);
public static RemoteObject _listapictogramas = RemoteObject.declareNull("anywheresoftware.b4a.objects.ListViewWrapper");
public static RemoteObject _pictogramaeditado = RemoteObject.createImmutable(0);
public static javi.prieto.pictorario.main _main = null;
public static javi.prieto.pictorario.starter _starter = null;
public static javi.prieto.pictorario.visualizacion _visualizacion = null;
public static javi.prieto.pictorario.acercade _acercade = null;
  public Object[] GetGlobals() {
		return new Object[] {"AcercaDe",Debug.moduleToString(javi.prieto.pictorario.acercade.class),"Activity",configurarsecuencia.mostCurrent._activity,"BotonAceptar",configurarsecuencia.mostCurrent._botonaceptar,"BotonAnadirActividad",configurarsecuencia.mostCurrent._botonanadiractividad,"BotonCancelar",configurarsecuencia.mostCurrent._botoncancelar,"ColorDeFondo",configurarsecuencia._colordefondo,"ConfigDescripcion",configurarsecuencia.mostCurrent._configdescripcion,"ConfigDescripcionAct",configurarsecuencia.mostCurrent._configdescripcionact,"ConfigHoraFinalAct",configurarsecuencia.mostCurrent._confighorafinalact,"ConfigHoraInicioAct",configurarsecuencia.mostCurrent._confighorainicioact,"ConfigIndicarHora",configurarsecuencia.mostCurrent._configindicarhora,"ConfigPictograma",configurarsecuencia.mostCurrent._configpictograma,"ConfigPictogramaAct",configurarsecuencia.mostCurrent._configpictogramaact,"ConfigTamIcono",configurarsecuencia.mostCurrent._configtamicono,"ConfigTipoTablero",configurarsecuencia.mostCurrent._configtipotablero,"EtiqActividades",configurarsecuencia.mostCurrent._etiqactividades,"EtiqIndicarHora",configurarsecuencia.mostCurrent._etiqindicarhora,"EtiqTamIcono",configurarsecuencia.mostCurrent._etiqtamicono,"EtiqTipoTablero",configurarsecuencia.mostCurrent._etiqtipotablero,"EtiquetaInicial",configurarsecuencia.mostCurrent._etiquetainicial,"Inicializando",configurarsecuencia._inicializando,"ListaPictogramas",configurarsecuencia.mostCurrent._listapictogramas,"Main",Debug.moduleToString(javi.prieto.pictorario.main.class),"ParametrosSecuencia",configurarsecuencia.mostCurrent._parametrossecuencia,"PictogramaEditado",configurarsecuencia._pictogramaeditado,"SeparacionCasillas",configurarsecuencia._separacioncasillas,"SeparacionHorizontal",configurarsecuencia._separacionhorizontal,"Starter",Debug.moduleToString(javi.prieto.pictorario.starter.class),"TamCasilla",configurarsecuencia._tamcasilla,"Visualizacion",Debug.moduleToString(javi.prieto.pictorario.visualizacion.class)};
}
}