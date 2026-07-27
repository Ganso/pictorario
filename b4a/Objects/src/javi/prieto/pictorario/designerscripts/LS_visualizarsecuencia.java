package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_visualizarsecuencia{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panelreloj").vw.setHeight((int)((130d / 100 * width)));
views.get("cambiarvista").vw.setWidth((int)((60d * scale)));
views.get("cambiarvista").vw.setHeight((int)((60d * scale)));
views.get("cambiarvista").vw.setTop((int)((views.get("panelreloj").vw.getTop() + views.get("panelreloj").vw.getHeight())-(views.get("volver").vw.getHeight())-(30d * scale)));
views.get("cambiarvista").vw.setLeft((int)((100d / 100 * width)-(views.get("cambiarvista").vw.getWidth())-(10d * scale)));
views.get("volver").vw.setLeft((int)((10d * scale)));
views.get("volver").vw.setWidth((int)((views.get("cambiarvista").vw.getLeft())-(20d * scale)));
views.get("volver").vw.setHeight((int)((60d * scale)));
views.get("volver").vw.setTop((int)((views.get("cambiarvista").vw.getTop())));
views.get("panelagujas").vw.setTop((int)((views.get("panelreloj").vw.getTop())));
views.get("panelagujas").vw.setLeft((int)((views.get("panelreloj").vw.getLeft())));
views.get("panelagujas").vw.setWidth((int)((views.get("panelreloj").vw.getWidth())));
views.get("panelagujas").vw.setHeight((int)((views.get("panelreloj").vw.getHeight())));
views.get("relojdigital").vw.setTop((int)((10d * scale)));
views.get("relojdigital").vw.setLeft((int)(0d));
views.get("relojdigital").vw.setWidth((int)((100d / 100 * width)));
views.get("relojdigital").vw.setHeight((int)((60d * scale)));
views.get("navegadoractividades").vw.setTop((int)((views.get("cambiarvista").vw.getTop() + views.get("cambiarvista").vw.getHeight())+(5d * scale)));
views.get("navegadoractividades").vw.setHeight((int)((100d / 100 * height)-(views.get("navegadoractividades").vw.getTop())));
views.get("progresoactividad").vw.setTop((int)((views.get("navegadoractividades").vw.getTop())-(10d * scale)));
views.get("progresoactividad").vw.setLeft((int)((10d / 100 * width)));
views.get("progresoactividad").vw.setWidth((int)((80d / 100 * width)));
views.get("progresoactividad").vw.setHeight((int)((20d * scale)));

}
}