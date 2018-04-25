package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_relojactividades{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("paneldescripcion").vw.setTop((int)((100d / 100 * height)-(33.333d / 100 * width)));
views.get("paneldescripcion").vw.setHeight((int)((33.333d / 100 * width)));
views.get("panelreloj").vw.setHeight((int)((100d / 100 * height)-(33.333d / 100 * width)));
views.get("textopictograma").vw.setHeight((int)((33.333d / 100 * width)));
views.get("fondopictograma").vw.setHeight((int)((33.333d / 100 * width)));
views.get("imagenpictograma").vw.setHeight((int)((33.333d / 100 * width)-(20d * scale)));
views.get("fondopictograma").vw.setWidth((int)((views.get("fondopictograma").vw.getHeight())));
views.get("imagenpictograma").vw.setWidth((int)((views.get("imagenpictograma").vw.getHeight())));
views.get("textopictograma").vw.setHeight((int)((views.get("paneldescripcion").vw.getHeight())/2d));
views.get("textopictograma").vw.setLeft((int)((views.get("fondopictograma").vw.getLeft())+(views.get("fondopictograma").vw.getWidth())));
views.get("textopictograma").vw.setWidth((int)((100d / 100 * width)-(views.get("textopictograma").vw.getLeft())));
views.get("descripcionpictograma").vw.setTop((int)((views.get("textopictograma").vw.getTop() + views.get("textopictograma").vw.getHeight())));
views.get("descripcionpictograma").vw.setWidth((int)((views.get("textopictograma").vw.getWidth())));
views.get("descripcionpictograma").vw.setHeight((int)((views.get("textopictograma").vw.getHeight())));
views.get("descripcionpictograma").vw.setLeft((int)((views.get("textopictograma").vw.getLeft())));
views.get("cambiarvista").vw.setWidth((int)((35d / 100 * width)));
views.get("cambiarvista").vw.setHeight((int)((10d / 100 * width)));
views.get("cambiarvista").vw.setTop((int)((10d * scale)));
views.get("cambiarvista").vw.setLeft((int)((100d / 100 * width)-(views.get("cambiarvista").vw.getWidth())-(10d * scale)));

}
}