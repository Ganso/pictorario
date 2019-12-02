package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_acercade{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[AcercaDe/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Volver.Width=100%X"[AcercaDe/General script]
views.get("volver").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 4;BA.debugLine="Volver.Height=60dip"[AcercaDe/General script]
views.get("volver").vw.setHeight((int)((60d * scale)));
//BA.debugLineNum = 5;BA.debugLine="Volver.Left=0"[AcercaDe/General script]
views.get("volver").vw.setLeft((int)(0d));
//BA.debugLineNum = 6;BA.debugLine="Volver.Top=100%Y-10dip-Volver.Height"[AcercaDe/General script]
views.get("volver").vw.setTop((int)((100d / 100 * height)-(10d * scale)-(views.get("volver").vw.getHeight())));
//BA.debugLineNum = 7;BA.debugLine="VerVideo.Width=100%X"[AcercaDe/General script]
views.get("vervideo").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 8;BA.debugLine="VerVideo.Height=60dip"[AcercaDe/General script]
views.get("vervideo").vw.setHeight((int)((60d * scale)));
//BA.debugLineNum = 9;BA.debugLine="VerVideo.Left=0"[AcercaDe/General script]
views.get("vervideo").vw.setLeft((int)(0d));
//BA.debugLineNum = 10;BA.debugLine="VerVideo.Top=Volver.Top-60dip"[AcercaDe/General script]
views.get("vervideo").vw.setTop((int)((views.get("volver").vw.getTop())-(60d * scale)));
//BA.debugLineNum = 11;BA.debugLine="Logotipo.Left=10dip"[AcercaDe/General script]
views.get("logotipo").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 12;BA.debugLine="Logotipo.Top=10dip"[AcercaDe/General script]
views.get("logotipo").vw.setTop((int)((10d * scale)));
//BA.debugLineNum = 13;BA.debugLine="Logotipo.Width=100dip"[AcercaDe/General script]
views.get("logotipo").vw.setWidth((int)((100d * scale)));
//BA.debugLineNum = 14;BA.debugLine="Logotipo.Height=100dip"[AcercaDe/General script]
views.get("logotipo").vw.setHeight((int)((100d * scale)));
//BA.debugLineNum = 15;BA.debugLine="Programador.Top=Logotipo.Bottom"[AcercaDe/General script]
views.get("programador").vw.setTop((int)((views.get("logotipo").vw.getTop() + views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 16;BA.debugLine="Programador.Left=10dip"[AcercaDe/General script]
views.get("programador").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 17;BA.debugLine="Programador.Width=Logotipo.Width"[AcercaDe/General script]
views.get("programador").vw.setWidth((int)((views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 18;BA.debugLine="Programador.Height=Logotipo.Height"[AcercaDe/General script]
views.get("programador").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 19;BA.debugLine="Pictogramas.Top=Programador.Bottom"[AcercaDe/General script]
views.get("pictogramas").vw.setTop((int)((views.get("programador").vw.getTop() + views.get("programador").vw.getHeight())));
//BA.debugLineNum = 20;BA.debugLine="Pictogramas.Left=10dip"[AcercaDe/General script]
views.get("pictogramas").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 21;BA.debugLine="Pictogramas.Width=Logotipo.Width"[AcercaDe/General script]
views.get("pictogramas").vw.setWidth((int)((views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 22;BA.debugLine="Pictogramas.Height=Logotipo.Height"[AcercaDe/General script]
views.get("pictogramas").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 23;BA.debugLine="Pictorario.Top=Logotipo.Top"[AcercaDe/General script]
views.get("pictorario").vw.setTop((int)((views.get("logotipo").vw.getTop())));
//BA.debugLineNum = 24;BA.debugLine="Pictorario.Left=Logotipo.Right"[AcercaDe/General script]
views.get("pictorario").vw.setLeft((int)((views.get("logotipo").vw.getLeft() + views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 25;BA.debugLine="Pictorario.Width=100%X-Pictorario.Left-10dip"[AcercaDe/General script]
views.get("pictorario").vw.setWidth((int)((100d / 100 * width)-(views.get("pictorario").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 26;BA.debugLine="Pictorario.Height=Logotipo.Height"[AcercaDe/General script]
views.get("pictorario").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 27;BA.debugLine="TextoAutor.Top=Programador.Top"[AcercaDe/General script]
views.get("textoautor").vw.setTop((int)((views.get("programador").vw.getTop())));
//BA.debugLineNum = 28;BA.debugLine="TextoAutor.Left=Programador.Right+10dip"[AcercaDe/General script]
views.get("textoautor").vw.setLeft((int)((views.get("programador").vw.getLeft() + views.get("programador").vw.getWidth())+(10d * scale)));
//BA.debugLineNum = 29;BA.debugLine="TextoAutor.Width=100%X-TextoAutor.Left-10dip"[AcercaDe/General script]
views.get("textoautor").vw.setWidth((int)((100d / 100 * width)-(views.get("textoautor").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 30;BA.debugLine="TextoAutor.Height=100dip"[AcercaDe/General script]
views.get("textoautor").vw.setHeight((int)((100d * scale)));
//BA.debugLineNum = 31;BA.debugLine="TextoArasaac.Top=Pictogramas.Top"[AcercaDe/General script]
views.get("textoarasaac").vw.setTop((int)((views.get("pictogramas").vw.getTop())));
//BA.debugLineNum = 32;BA.debugLine="TextoArasaac.Left=TextoAutor.Left"[AcercaDe/General script]
views.get("textoarasaac").vw.setLeft((int)((views.get("textoautor").vw.getLeft())));
//BA.debugLineNum = 33;BA.debugLine="TextoArasaac.Width=100%X-TextoArasaac.Left-10dip"[AcercaDe/General script]
views.get("textoarasaac").vw.setWidth((int)((100d / 100 * width)-(views.get("textoarasaac").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 34;BA.debugLine="TextoArasaac.Height=120dip"[AcercaDe/General script]
views.get("textoarasaac").vw.setHeight((int)((120d * scale)));
//BA.debugLineNum = 35;BA.debugLine="PulsaLosIconos.Top=TextoArasaac.Bottom"[AcercaDe/General script]
views.get("pulsalosiconos").vw.setTop((int)((views.get("textoarasaac").vw.getTop() + views.get("textoarasaac").vw.getHeight())));
//BA.debugLineNum = 36;BA.debugLine="PulsaLosIconos.Height=40dip"[AcercaDe/General script]
views.get("pulsalosiconos").vw.setHeight((int)((40d * scale)));
//BA.debugLineNum = 37;BA.debugLine="PulsaLosIconos.Width=100%X"[AcercaDe/General script]
views.get("pulsalosiconos").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="PulsaLosIconos.Left=0"[AcercaDe/General script]
views.get("pulsalosiconos").vw.setLeft((int)(0d));
//BA.debugLineNum = 39;BA.debugLine="ParaTeo.Top=PulsaLosIconos.Bottom"[AcercaDe/General script]
views.get("parateo").vw.setTop((int)((views.get("pulsalosiconos").vw.getTop() + views.get("pulsalosiconos").vw.getHeight())));
//BA.debugLineNum = 40;BA.debugLine="ParaTeo.Height=VerVideo.Top-ParaTeo.Top"[AcercaDe/General script]
views.get("parateo").vw.setHeight((int)((views.get("vervideo").vw.getTop())-(views.get("parateo").vw.getTop())));
//BA.debugLineNum = 41;BA.debugLine="ParaTeo.Left=0"[AcercaDe/General script]
views.get("parateo").vw.setLeft((int)(0d));
//BA.debugLineNum = 42;BA.debugLine="ParaTeo.Width=100%X"[AcercaDe/General script]
views.get("parateo").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 43;BA.debugLine="VersionApp.Top=0"[AcercaDe/General script]
views.get("versionapp").vw.setTop((int)(0d));
//BA.debugLineNum = 44;BA.debugLine="VersionApp.Width=80dip"[AcercaDe/General script]
views.get("versionapp").vw.setWidth((int)((80d * scale)));
//BA.debugLineNum = 45;BA.debugLine="VersionApp.Height=100dip"[AcercaDe/General script]
views.get("versionapp").vw.setHeight((int)((100d * scale)));
//BA.debugLineNum = 46;BA.debugLine="VersionApp.Left=100%X-VersionApp.Width"[AcercaDe/General script]
views.get("versionapp").vw.setLeft((int)((100d / 100 * width)-(views.get("versionapp").vw.getWidth())));

}
}