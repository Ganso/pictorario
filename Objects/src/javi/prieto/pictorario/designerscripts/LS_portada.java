package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_portada{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("logotipo").vw.setWidth((int)((33d / 100 * width)));
views.get("logotipo").vw.setLeft((int)((50d / 100 * width)-(views.get("logotipo").vw.getWidth())/2d));
views.get("logotipo").vw.setHeight((int)((views.get("logotipo").vw.getWidth())));
views.get("pictorario").vw.setTop((int)((views.get("logotipo").vw.getTop() + views.get("logotipo").vw.getHeight())));
views.get("pictorario").vw.setLeft((int)((100d / 100 * width) - (views.get("pictorario").vw.getWidth())));
views.get("panelscroll").vw.setTop((int)((views.get("pictorario").vw.getTop() + views.get("pictorario").vw.getHeight())+(10d * scale)));
views.get("panelscroll").vw.setHeight((int)((100d / 100 * height)-(views.get("panelscroll").vw.getTop())));
views.get("panelscroll").vw.setWidth((int)((100d / 100 * width)));

}
}