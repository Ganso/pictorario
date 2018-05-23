package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_acercade{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[AcercaDe/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Volver.Width=66%X"[AcercaDe/General script]
views.get("volver").vw.setWidth((int)((66d / 100 * width)));
//BA.debugLineNum = 4;BA.debugLine="Volver.Height=60dip"[AcercaDe/General script]
views.get("volver").vw.setHeight((int)((60d * scale)));
//BA.debugLineNum = 5;BA.debugLine="Volver.Left=50%X-Volver.Width/2"[AcercaDe/General script]
views.get("volver").vw.setLeft((int)((50d / 100 * width)-(views.get("volver").vw.getWidth())/2d));
//BA.debugLineNum = 6;BA.debugLine="Volver.Top=100%Y-10dip-Volver.Height"[AcercaDe/General script]
views.get("volver").vw.setTop((int)((100d / 100 * height)-(10d * scale)-(views.get("volver").vw.getHeight())));
//BA.debugLineNum = 7;BA.debugLine="Logotipo.Left=10dip"[AcercaDe/General script]
views.get("logotipo").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 8;BA.debugLine="Logotipo.Top=10dip"[AcercaDe/General script]
views.get("logotipo").vw.setTop((int)((10d * scale)));
//BA.debugLineNum = 9;BA.debugLine="Logotipo.Width=100dip"[AcercaDe/General script]
views.get("logotipo").vw.setWidth((int)((100d * scale)));
//BA.debugLineNum = 10;BA.debugLine="Logotipo.Height=100dip"[AcercaDe/General script]
views.get("logotipo").vw.setHeight((int)((100d * scale)));
//BA.debugLineNum = 11;BA.debugLine="Programador.Top=Logotipo.Bottom+40dip"[AcercaDe/General script]
views.get("programador").vw.setTop((int)((views.get("logotipo").vw.getTop() + views.get("logotipo").vw.getHeight())+(40d * scale)));
//BA.debugLineNum = 12;BA.debugLine="Programador.Left=10dip"[AcercaDe/General script]
views.get("programador").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 13;BA.debugLine="Programador.Width=Logotipo.Width"[AcercaDe/General script]
views.get("programador").vw.setWidth((int)((views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 14;BA.debugLine="Programador.Height=Logotipo.Height"[AcercaDe/General script]
views.get("programador").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 15;BA.debugLine="Pictogramas.Top=Programador.Bottom+40dip"[AcercaDe/General script]
views.get("pictogramas").vw.setTop((int)((views.get("programador").vw.getTop() + views.get("programador").vw.getHeight())+(40d * scale)));
//BA.debugLineNum = 16;BA.debugLine="Pictogramas.Left=10dip"[AcercaDe/General script]
views.get("pictogramas").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 17;BA.debugLine="Pictogramas.Width=Logotipo.Width"[AcercaDe/General script]
views.get("pictogramas").vw.setWidth((int)((views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 18;BA.debugLine="Pictogramas.Height=Logotipo.Height"[AcercaDe/General script]
views.get("pictogramas").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 19;BA.debugLine="Pictorario.Top=Logotipo.Top"[AcercaDe/General script]
views.get("pictorario").vw.setTop((int)((views.get("logotipo").vw.getTop())));
//BA.debugLineNum = 20;BA.debugLine="Pictorario.Left=Logotipo.Right"[AcercaDe/General script]
views.get("pictorario").vw.setLeft((int)((views.get("logotipo").vw.getLeft() + views.get("logotipo").vw.getWidth())));
//BA.debugLineNum = 21;BA.debugLine="Pictorario.Width=100%X-Pictorario.Left-10dip"[AcercaDe/General script]
views.get("pictorario").vw.setWidth((int)((100d / 100 * width)-(views.get("pictorario").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 22;BA.debugLine="Pictorario.Height=Logotipo.Height"[AcercaDe/General script]
views.get("pictorario").vw.setHeight((int)((views.get("logotipo").vw.getHeight())));
//BA.debugLineNum = 23;BA.debugLine="TextoAutor.Top=Programador.Top"[AcercaDe/General script]
views.get("textoautor").vw.setTop((int)((views.get("programador").vw.getTop())));
//BA.debugLineNum = 24;BA.debugLine="TextoAutor.Left=Programador.Right"[AcercaDe/General script]
views.get("textoautor").vw.setLeft((int)((views.get("programador").vw.getLeft() + views.get("programador").vw.getWidth())));
//BA.debugLineNum = 25;BA.debugLine="TextoAutor.Width=100%X-TextoAutor.Left-10dip"[AcercaDe/General script]
views.get("textoautor").vw.setWidth((int)((100d / 100 * width)-(views.get("textoautor").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 26;BA.debugLine="TextoAutor.Height=140dip"[AcercaDe/General script]
views.get("textoautor").vw.setHeight((int)((140d * scale)));
//BA.debugLineNum = 27;BA.debugLine="TextoArasaac.Top=Pictogramas.Top"[AcercaDe/General script]
views.get("textoarasaac").vw.setTop((int)((views.get("pictogramas").vw.getTop())));
//BA.debugLineNum = 28;BA.debugLine="TextoArasaac.Left=Pictogramas.Right"[AcercaDe/General script]
views.get("textoarasaac").vw.setLeft((int)((views.get("pictogramas").vw.getLeft() + views.get("pictogramas").vw.getWidth())));
//BA.debugLineNum = 29;BA.debugLine="TextoArasaac.Width=100%X-TextoArasaac.Left-10dip"[AcercaDe/General script]
views.get("textoarasaac").vw.setWidth((int)((100d / 100 * width)-(views.get("textoarasaac").vw.getLeft())-(10d * scale)));
//BA.debugLineNum = 30;BA.debugLine="TextoArasaac.Height=Volver.Top-Pictogramas.Top"[AcercaDe/General script]
views.get("textoarasaac").vw.setHeight((int)((views.get("volver").vw.getTop())-(views.get("pictogramas").vw.getTop())));

}
}