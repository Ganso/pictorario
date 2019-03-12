package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_configuracion{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _separador="";
String _altura="";
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
_separador = BA.NumberToString((10d * scale));
_altura = BA.NumberToString((60d * scale));
views.get("titulo").vw.setWidth((int)((100d / 100 * width)));
views.get("titulo").vw.setHeight((int)(2d*Double.parseDouble(_altura)));
views.get("panelscroll").vw.setTop((int)((views.get("titulo").vw.getTop() + views.get("titulo").vw.getHeight())));
views.get("panelscroll").vw.setLeft((int)(0d));
views.get("panelscroll").vw.setWidth((int)((100d / 100 * width)));
views.get("panelscroll").vw.setHeight((int)((100d / 100 * height)-(views.get("titulo").vw.getTop() + views.get("titulo").vw.getHeight())));

}
}