package com.springboot.tfg.app.util;

import java.awt.Color;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.AlquilaDetalles;
import com.springboot.tfg.app.models.entity.Cliente;
import com.springboot.tfg.app.models.entity.Deporte;

@Component("mis_productos")
public class ProductoPDF extends AbstractPdfView{
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Cliente cliente = (Cliente) model.get("cliente");
		String fecha = (String) model.get("fecha");
		
		if(fecha.equals("nada")) {
	
			Deporte deporte = (Deporte) model.get("deporte");		
			
			Image img1 = Image.getInstance("src/main/resources/static/images/nuevo/logos/logo.png");
			Image img2 = Image.getInstance("src/main/resources/static/images/clases/" + deporte.getNombre() + ".png");
			
			PdfPCell cell = null;
			PdfPCell cell1 = null;
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(50);
			cell = new PdfPCell();
			cell.setBorderColor(new Color(255,255,255));
			cell.setBorderColorTop(new Color(0,0,0));
			cell.setPaddingBottom(20f);
			cell.addElement(img2);
			table.addCell(cell);
			document.add(table);
				
			PdfPTable tabla = new PdfPTable(1);
			tabla.setSpacingAfter(20);
				
			cell = new PdfPCell(new Phrase("Datos del cliente"));
			//cell.setBackgroundColor(new Color(184, 218, 255));
			cell.setPaddingBottom(8f);
			cell.setBorderColor(new Color(255,255,255));
			tabla.addCell(cell);			
				
			tabla.addCell(cliente.getNombre() + " " + cliente.getApellidos());
			tabla.addCell(cliente.getEmail());
			
				
			PdfPTable tabla2 = new PdfPTable(2);
			tabla2.setSpacingAfter(20);			
				
			cell = new PdfPCell(new Phrase("Datos de la clase"));
			//cell.setBackgroundColor(new Color(195, 230, 203));
			cell.setPaddingBottom(8f);
			cell.setBorderColor(new Color(255,255,255));
			cell.setColspan(2);
				
			tabla2.addCell(cell);
			tabla2.addCell("Nombre: ");
			tabla2.addCell(deporte.getNombre().toUpperCase());
			tabla2.addCell("Dias: ");
			tabla2.addCell(dias(deporte.getDias().toString()));
			tabla2.addCell("Hora: ");
			tabla2.addCell(deporte.getHora() + " horas");
			tabla2.addCell("Aforo: ");
			tabla2.addCell(String.valueOf(deporte.getAforo()) + " personas");
				
			document.add(tabla);
			document.add(tabla2);
				
			PdfPTable tabla3 = new PdfPTable(4);
			tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
			
			String precioBase = String.format("%.2f", deporte.getPrecio() - (deporte.getPrecio() * 0.21));
			cell = new PdfPCell(new Phrase("Precio base: "));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell);
			cell1 = new PdfPCell(new Phrase(precioBase));
			cell1.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell1);
			
			String precioIva = String.format("%.2f", deporte.getPrecio() * 0.21);
			cell = new PdfPCell(new Phrase("IVA 21%: "));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell);
			cell1 = new PdfPCell(new Phrase(precioIva));
			cell1.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell1);
			
			cell = new PdfPCell(new Phrase("Precio total"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tabla3.addCell(cell);
			tabla3.addCell(String.valueOf(deporte.getPrecio() + "€"));
			    
			document.add(tabla3);
			
			PdfPTable table5 = new PdfPTable(1);
			table5.setWidthPercentage(50);
			table5.setSpacingBefore(8f);
			cell = new PdfPCell();
			cell.setBorderColor(new Color(255,255,255));
			cell.addElement(img1);
			table5.addCell(cell);
			
			cell1 = new PdfPCell(new Phrase("©2021. Todos los derechos reservados"));
			cell1.setBorderColor(new Color(255,255,255));
			table5.addCell(cell1);
			document.add(table5);
			
		}else {
			
			AlquilaDetalles alquila = (AlquilaDetalles) model.get("alquila");		
			String nombrePista = (String) model.get("nombrePista");
			double precio = (double) model.get("precio");
			
			String tarjetaNumero = String.valueOf(alquila.getTarjeta());
			tarjetaNumero = tarjetaNumero.substring(tarjetaNumero.length() - 4, tarjetaNumero.length());
			tarjetaNumero = "*****"+tarjetaNumero;
			
			Image img1 = Image.getInstance("src/main/resources/static/images/nuevo/logos/logo.png");
			Image img2 = Image.getInstance("src/main/resources/static/images/alquilar/" + nombrePista + ".jpg");
			
			PdfPCell cell = null;
			PdfPCell cell1 = null;
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(50);
			cell = new PdfPCell();
			cell.setBorderColor(new Color(255,255,255));
			cell.setBorderColorTop(new Color(0,0,0));
			cell.setPaddingBottom(20f);
			cell.addElement(img2);
			table.addCell(cell);
			document.add(table);
				
			PdfPTable tabla = new PdfPTable(1);
			tabla.setSpacingAfter(20);
				
			cell = new PdfPCell(new Phrase("Datos del cliente"));
			//cell.setBackgroundColor(new Color(184, 218, 255));
			cell.setPaddingBottom(8f);
			cell.setBorderColor(new Color(255,255,255));
			tabla.addCell(cell);			
				
			tabla.addCell(cliente.getNombre() + " " + cliente.getApellidos());
			tabla.addCell(cliente.getEmail());
			tabla.addCell("Tarjeta: " + tarjetaNumero);
				
			PdfPTable tabla2 = new PdfPTable(2);
			tabla2.setSpacingAfter(20);			
				
			cell = new PdfPCell(new Phrase("Datos del alquiler"));
			//cell.setBackgroundColor(new Color(195, 230, 203));
			cell.setPaddingBottom(8f);
			cell.setBorderColor(new Color(255,255,255));
			cell.setColspan(2);
				
			tabla2.addCell(cell);
			tabla2.addCell("Nombre: ");
			tabla2.addCell(nombrePista.toUpperCase());
			tabla2.addCell("Hora inicio: ");
			tabla2.addCell(alquila.getHora_inicio());
			tabla2.addCell("Hora final: ");
			tabla2.addCell(alquila.getHora_final());
			tabla2.addCell("Fecha: ");
			tabla2.addCell(fecha);
				
			document.add(tabla);
			document.add(tabla2);
				
			PdfPTable tabla3 = new PdfPTable(4);
			tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
			
			cell = new PdfPCell(new Phrase("Precio base: "));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell);
			cell1 = new PdfPCell(new Phrase(String.valueOf(precio - (precio * 0.21) + "€")));
			cell1.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell1);
				
			cell = new PdfPCell(new Phrase("IVA 21%: "));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell);
			cell1 = new PdfPCell(new Phrase(String.valueOf(precio * 0.21 + "€")));
			cell1.setBorderColor(new Color(255,255,255));
			tabla3.addCell(cell1);
			
			cell = new PdfPCell(new Phrase("Precio total"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tabla3.addCell(cell);
			tabla3.addCell(String.valueOf(precio + "€"));
			    
			document.add(tabla3);
			
			PdfPTable table5 = new PdfPTable(1);
			table5.setWidthPercentage(50);
			cell = new PdfPCell();
			cell.setBorderColor(new Color(255,255,255));
			table5.setSpacingBefore(8f);
			cell.addElement(img1);
			table5.addCell(cell);
			
			cell1 = new PdfPCell(new Phrase("©2021. Todos los derechos reservados"));
			cell1.setBorderColor(new Color(255,255,255));
			table5.addCell(cell1);
			document.add(table5);
		}	
		
	}
	
	public String dias(String dia) {
		
		String devolver = null;
		
		if(dia.equals("M-J")) {
			devolver = "Martes y jueves";
		}else if(dia.equals("L-J")) {
			devolver = "Lunes y jueves";
		}else if(dia.equals("L-X")) {
			devolver = "Lunes y miércoles";
		}else if(dia.equals("X-V")) {
			devolver = "Miércoles y viernes";
		}
		
		return devolver;
	}

}
