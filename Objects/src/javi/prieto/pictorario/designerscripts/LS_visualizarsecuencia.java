package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_visualizarsecuencia{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("paneldescripcion").vw.setTop((int)((100d / 100 * height)-(33.333d / 100 * width)));
views.get("paneldescripcion").vw.setHeight((int)((33.333d / 100 * width)));
views.get("panelreloj").vw.setHeight((int)((100d / 100 * height)-(33.333d / 100 * width)));
views.get("textopictograma").vw.setHeight((int)((33.333d / 100 * width)));
views.get("fondopictograma").vw.setHeight((int)((33.333d / 100 * width)));
views.get("imagenpictograma").vw.setHeight((int)((33.333d / 100 * width)-(20d * scale)));
views.get("bordepictograma").vw.setHeight((int)((33.333d / 100 * width)-(10d * scale)));
views.get("fondopictograma").vw.setWidth((int)((views.get("fondopictograma").vw.getHeight())));
views.get("imagenpictograma").vw.setWidth((int)((views.get("imagenpictograma").vw.getHeight())));
views.get("bordepictograma").vw.setWidth((int)((views.get("bordepictograma").vw.getHeight())));
views.get("textopictograma").vw.setHeight((int)((views.get("paneldescripcion").vw.getHeight())/2d));
views.get("textopictograma").vw.setLeft((int)((views.get("fondopictograma").vw.getLeft())+(views.get("fondopictograma").vw.getWidth())));
views.get("textopictograma").vw.setWidth((int)((100d / 100 * width)-(views.get("textopictograma").vw.getLeft())));
views.get("descripcionpictograma").vw.setTop((int)((views.get("textopictograma").vw.getTop() + views.get("textopictograma").vw.getHeight())));
views.get("descripcionpictograma").vw.setWidth((int)((views.get("textopictograma").vw.getWidth())));
views.get("descripcionpictograma").vw.setHeight((int)((views.get("textopictograma").vw.getHeight())));
views.get("descripcionpictograma").vw.setLeft((int)((views.get("textopictograma").vw.getLeft())));
views.get("cambiarvista").vw.setWidth((int)((60d * scale)));
views.get("cambiarvista").vw.setHeight((int)((60d * scale)));
views.get("cambiarvista").vw.setTop((int)((views.get("paneldescripcion").vw.getTop())-(views.get("volver").vw.getHeight())-(30d * scale)));
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

}
}