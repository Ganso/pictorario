package javi.prieto.pictorario.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_seleccionarpictograma{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _separador="";
String _tamvertical="";
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
_separador = BA.NumberToString((10d * scale));
_tamvertical = BA.NumberToString((60d * scale));
views.get("titulo").vw.setTop((int)(Double.parseDouble(_separador)));
views.get("titulo").vw.setLeft((int)(Double.parseDouble(_separador)));
views.get("titulo").vw.setWidth((int)((100d / 100 * width)-2d*Double.parseDouble(_separador)));
views.get("titulo").vw.setHeight((int)(Double.parseDouble(_tamvertical)));
views.get("buscar").vw.setLeft((int)(Double.parseDouble(_separador)));
views.get("buscar").vw.setTop((int)((100d / 100 * height)-Double.parseDouble(_separador)-2d*Double.parseDouble(_tamvertical)));
views.get("buscar").vw.setWidth((int)((100d / 100 * width)-2d*Double.parseDouble(_separador)));
views.get("buscar").vw.setHeight((int)(2d*Double.parseDouble(_tamvertical)));
views.get("listadopictogramas").vw.setLeft((int)(Double.parseDouble(_separador)));
views.get("listadopictogramas").vw.setTop((int)((views.get("titulo").vw.getTop())+(views.get("titulo").vw.getHeight())));
views.get("listadopictogramas").vw.setWidth((int)((100d / 100 * width)-2d*Double.parseDouble(_separador)));
views.get("listadopictogramas").vw.setHeight((int)((views.get("buscar").vw.getTop())-(views.get("titulo").vw.getTop())-(views.get("titulo").vw.getHeight())));
views.get("barrabusqueda").vw.setTop((int)(0d));
views.get("barrabusqueda").vw.setLeft((int)(0d));
views.get("barrabusqueda").vw.setWidth((int)((views.get("buscar").vw.getWidth())-Double.parseDouble(_tamvertical)));
views.get("barrabusqueda").vw.setHeight((int)(Double.parseDouble(_tamvertical)));
views.get("botonbuscar").vw.setTop((int)(0d));
views.get("botonbuscar").vw.setLeft((int)((views.get("barrabusqueda").vw.getWidth())));
views.get("botonbuscar").vw.setWidth((int)(Double.parseDouble(_tamvertical)));
views.get("botonbuscar").vw.setHeight((int)(Double.parseDouble(_tamvertical)));
views.get("botoncancelar").vw.setTop((int)(Double.parseDouble(_tamvertical)));
views.get("botoncancelar").vw.setLeft((int)(0d));
views.get("botoncancelar").vw.setWidth((int)((views.get("buscar").vw.getWidth())));
views.get("botoncancelar").vw.setHeight((int)(Double.parseDouble(_tamvertical)));

}
}