package com.tecsoluction.robo.util;

import java.io.File;
import java.net.MalformedURLException;

import com.itextpdf.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
	
	private File arquivoLoc ;
	
	
	
	
	public HeaderFooterPageEvent() {
		// TODO Auto-generated constructor stub
	}
	
	
	public HeaderFooterPageEvent(File f) {

	this.arquivoLoc = f;
	
	}
	
	
	
	 public void onStartPage(PdfWriter writer, Document document) {
	      
		 String  img = arquivoLoc.getPath().replace(arquivoLoc.getName(), "") + "img1.png";;
		    Image image = null;
		    
			 Font tabheader = new Font(Font.FontFamily.COURIER, 9,
	                 Font.BOLD);
			 tabheader.setColor(BaseColor.BLACK);
		    
		    try {
		        try {
					image = Image.getInstance(img);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        image.setAlignment(Element.ALIGN_CENTER);
		        image.setAbsolutePosition(190, 770);
//		        image.scalePercent(7.5f, 7.5f);
		        writer.getDirectContent().addImage(image, true);
		    } catch (IOException | DocumentException e) {
		        System.out.println("L'image logo-tp-50x50.png a provoqué une erreur." + e);
		    }
		 
		 
		 
		 
		 	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("SECRETARIA DE JUSTIÇA E DIREITOS HUMANOS",tabheader), 300, 770, 0);
		 	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("SECRETARIA EXECUTIVA DE RESSOCIALIZAÇÃO",tabheader), 300, 760, 0);
		 	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("CENTRO DE MONITORAMENTO ELETRÔNICO DE REEDUCANDOS – CEMER",tabheader), 300, 750, 0);

//			  try {
//				document.add(Chunk.NEWLINE);
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			  try {
//				document.add(Chunk.NEWLINE);
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			  try {
//				document.add(Chunk.NEWLINE);
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	 }

	    public void onEndPage(PdfWriter writer, Document document) {
	    	
			 Font tabheader = new Font(Font.FontFamily.COURIER, 9,
	                 Font.ITALIC);
			 tabheader.setColor(BaseColor.GRAY);
	      
			 String  img = arquivoLoc.getPath().replace(arquivoLoc.getName(), "") + "img2.png";;
			    Image image = null;
			    try {
			        try {
						image = Image.getInstance(img);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (java.io.IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        image.setAlignment(Element.ALIGN_LEFT);
			        image.setAbsolutePosition(30, 10);
//			        image.scalePercent(7.5f, 7.5f);
			        writer.getDirectContent().addImage(image, true);
			    } catch (IOException | DocumentException e) {
			        System.out.println("L'image logo-tp-50x50.png a provoqué une erreur." + e);
			    }
	    	
	    	
	    	
	    	
	    	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(" Gervásio Pires,850–Recife/PE cemer@seres.pe.gov.br-Fone:81 3184-2191",tabheader), 280, 10, 0);
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Página " + document.getPageNumber(),tabheader), 530, 10, 0);
	    }
	

}
