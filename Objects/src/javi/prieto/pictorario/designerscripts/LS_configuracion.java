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
views.get("activaralarmaslabel").vw.setLeft((int)((10d * scale)));
views.get("activaralarmaslabel").vw.setTop((int)((views.get("titulo").vw.getTop() + views.get("titulo").vw.getHeight())));
views.get("activaralarmaslabel").vw.setHeight((int)(Double.parseDouble(_altura)));
views.get("activaralarmaslabel").vw.setWidth((int)((100d / 100 * width)-2d*Double.parseDouble(_altura)-4d*Double.parseDouble(_separador)));
views.get("activaralarmasicono").vw.setTop((int)((views.get("activaralarmaslabel").vw.getTop())));
views.get("activaralarmasicono").vw.setLeft((int)((views.get("activaralarmaslabel").vw.getLeft() + views.get("activaralarmaslabel").vw.getWidth())+Double.parseDouble(_separador)));
views.get("activaralarmasicono").vw.setWidth((int)(Double.parseDouble(_altura)));
views.get("activaralarmasicono").vw.setHeight((int)(Double.parseDouble(_altura)));
views.get("activaralarmascheck").vw.setTop((int)((views.get("activaralarmasicono").vw.getTop())));
views.get("activaralarmascheck").vw.setLeft((int)((views.get("activaralarmasicono").vw.getLeft() + views.get("activaralarmasicono").vw.getWidth())+Double.parseDouble(_separador)));
views.get("activaralarmascheck").vw.setWidth((int)(Double.parseDouble(_altura)));
views.get("activaralarmascheck").vw.setHeight((int)(Double.parseDouble(_altura)));
views.get("protegervisualizacionlabel").vw.setLeft((int)((views.get("activaralarmaslabel").vw.getLeft())));
views.get("protegervisualizacionlabel").vw.setTop((int)((views.get("activaralarmaslabel").vw.getTop() + views.get("activaralarmaslabel").vw.getHeight())+Double.parseDouble(_separador)));
views.get("protegervisualizacionlabel").vw.setHeight((int)((views.get("activaralarmaslabel").vw.getHeight())));
views.get("protegervisualizacionlabel").vw.setWidth((int)((views.get("activaralarmaslabel").vw.getWidth())));
views.get("protegervisualizacionicono").vw.setTop((int)((views.get("protegervisualizacionlabel").vw.getTop())));
views.get("protegervisualizacionicono").vw.setLeft((int)((views.get("protegervisualizacionlabel").vw.getLeft() + views.get("protegervisualizacionlabel").vw.getWidth())+Double.parseDouble(_separador)));
views.get("protegervisualizacionicono").vw.setWidth((int)(Double.parseDouble(_altura)));
views.get("protegervisualizacionicono").vw.setHeight((int)(Double.parseDouble(_altura)));
views.get("protegervisualizacioncheck").vw.setLeft((int)((views.get("protegervisualizacionicono").vw.getLeft() + views.get("protegervisualizacionicono").vw.getWidth())+Double.parseDouble(_separador)));
views.get("protegervisualizacioncheck").vw.setTop((int)((views.get("protegervisualizacionlabel").vw.getTop())));
views.get("protegervisualizacioncheck").vw.setHeight((int)((views.get("protegervisualizacionlabel").vw.getHeight())));
views.get("protegervisualizacioncheck").vw.setWidth((int)(Double.parseDouble(_altura)));
views.get("botonvolver").vw.setHeight((int)(Double.parseDouble(_altura)));
views.get("botonvolver").vw.setTop((int)((100d / 100 * height)-Double.parseDouble(_altura)-Double.parseDouble(_separador)));
views.get("botonvolver").vw.setLeft((int)(Double.parseDouble(_separador)));
views.get("botonvolver").vw.setWidth((int)((100d / 100 * width)-2d*Double.parseDouble(_separador)));

}
}