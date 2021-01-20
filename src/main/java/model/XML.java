package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class XML {
	private String rutaAbsoluta = "src/main/resources/ficheros/Equipos.xml";
	static SAXBuilder builder = new SAXBuilder();

	public static String listarEquipos(String ruta) {
		String contenido = "";
		try {
			
			Document documentJDOM = builder.build(ruta);
			Element raiz = documentJDOM.getRootElement();
			List<Element> hijosRaiz = raiz.getChildren();

			for (Element hijo : hijosRaiz) {
				contenido += "EQUIPO: " + hijo.getAttributeValue("nomEquipo") + "  |  COPAS GANADAS:" + hijo.getAttributeValue("copasGanadas") + "\n" + "COD-LIGA:" + hijo.getChildText("codLiga") + "\n";
//				System.out.println("EQUIPO: " + hijo.getAttributeValue("nomEquipo") + "  |  COPAS GANADAS:"
//						+ hijo.getAttributeValue("copasGanadas"));
//				System.out.println("COD-LIGA:" + hijo.getChildText("codLiga"));
				List<Element> etiquetasHijas = hijo.getChildren("Contratos");
				for (Element element : etiquetasHijas) {
					contenido += element.getName() + "\n";
//					System.out.println(element.getName());
					List<Element> futbolistas = element.getChildren();
					for (Element element2 : futbolistas) {
						contenido += element2.getValue() + " |  fechaInicio: " + element2.getAttributeValue("fechaInicio")
						+ "  _  fechaFin: " + element2.getAttributeValue("fechaFin") + "\n";
//						System.out.println(
//								element2.getValue() + " |  fechaInicio: " + element2.getAttributeValue("fechaInicio")
//										+ "  _  fechaFin: " + element2.getAttributeValue("fechaFin"));
					}
				}
				contenido+="\n";
			}
			
			System.out.println(contenido);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contenido;
	}

	public static void cambiarCopasGanadas(String ruta, String equipo, String nuevasCopas) {
		try {

			Document documentJDOM = builder.build(ruta);

			String query = "//*[@nomEquipo= '" + equipo + "']";
			XPathExpression<Element> xpe = XPathFactory.instance().compile(query, Filters.element());
			for (Element urle : xpe.evaluate(documentJDOM)) {
				urle.getAttribute("copasGanadas").setValue(nuevasCopas);
			}

			XMLOutputter xmlOutput = new XMLOutputter();


			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(documentJDOM, new FileWriter(ruta));
			

			System.out.println("File updated!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void eliminarUnEquipo(String ruta, String equipo) {
		try {
			Document documentJDOM = builder.build(new FileInputStream(ruta));

			String query = "//*[@nomEquipo= '" + equipo + "']";
			XPathExpression<Element> xpe = XPathFactory.instance().compile(query, Filters.element());
			for (Element urle : xpe.evaluate(documentJDOM)) {
				Element raiz = documentJDOM.getRootElement();
				raiz.removeContent(urle);
			}

			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(documentJDOM, new FileWriter(ruta));

			// xmlOutput.output(doc, System.out);

			System.out.println("File updated!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void insertarContrato(String ruta, String nomEquipo, String nomFutbolista, String fechaInicio, String fechaFin) {
		
		try{
			Document documentJDOM = builder.build(new FileInputStream(ruta));

			Element raiz = documentJDOM.getRootElement();
			List<Element> hijosRaiz = raiz.getChildren();

			for (Element hijo : hijosRaiz) {
				String id = hijo.getAttributeValue("nomEquipo");
				if (id != null) {
					if (id.equals(nomEquipo)) {
						Element etiquetaNueva = new Element("Futbolista");
						etiquetaNueva.setAttribute("fechaInicio", fechaInicio);
						etiquetaNueva.setAttribute("fechaFin", fechaFin);
						etiquetaNueva.setText(nomFutbolista);
						Element etiquetaHijo = (Element) hijo.getChild("Contratos");
						etiquetaHijo.addContent(etiquetaNueva);
					}
				}
			}

			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(documentJDOM, new FileWriter(ruta));
			System.out.println("File updated!");
		}catch(Exception ex) {
			
		}
		
	}

	public static void guardarEnRuta(String ruta, String rutaNueva) {
		try {
			Document documentJDOM = builder.build(new FileInputStream(ruta));
			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(documentJDOM, new FileWriter(rutaNueva));


			System.out.println("File updated!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}
