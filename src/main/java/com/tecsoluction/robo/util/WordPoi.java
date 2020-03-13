package com.tecsoluction.robo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WordPoi {
	
		private POIFSFileSystem fs = null;  
	    private FileInputStream fis = null;  
	    private static HWPFDocument doc = null;  
	      
	    private String arquivoEntrada ;// origem do arquivo  
	    private String arquivoSaida ;   // pasta de saida do arquivo   
	      
	  

		/** 
	     * Cria o arquivo na pasta indicada 
	     * @param arquivoEntrada - endereco do arquivo de entrada (modelo) 
	     * @param arquivoSaida - endereco do arquivo de saida, onde vai ser salvo o relatório 
	     */  
      //construtor
	    public WordPoi(String arquivoEntrada, String arquivoSaida){  
	          
	        this.arquivoEntrada = arquivoEntrada;  
	        this.arquivoSaida = arquivoSaida;  
	          
	        try {  
	              
	            fis = new FileInputStream(arquivoEntrada);  
	            fs = new POIFSFileSystem(fis);  
	            doc = new HWPFDocument(fs);  
	              
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    
	    public WordPoi() {
			// TODO Auto-generated constructor stub
		}
	    
	    //getters and setters
	    
	    public String getArquivoEntrada() {
			return arquivoEntrada;
		}


		public void setArquivoEntrada(String arquivoEntrada) {
			this.arquivoEntrada = arquivoEntrada;
		}


		public String getArquivoSaida() {
			return arquivoSaida;
		}


		public void setArquivoSaida(String arquivoSaida) {
			this.arquivoSaida = arquivoSaida;
		}

	      
	      
	    /** 
	     * Metodo responsavel por criar o arquivo na pasta informada como parametro na criacao do objeto 
	     */  
	      
	    public void write(){  
	          
	        FileOutputStream fos;  
	        try {  
	            fos = new FileOutputStream(arquivoSaida);  
	            doc.write(fos);  
	            fis.close();
	            fos.close();  
	            System.out.println("Arquivo Gerado com Sucesso!!");  
	              
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	      
	  
	    /** 
	     * METODO USADO PARA SUBSTITUIR UMA TAG POR UMA STRING QUE O PROGRAMADOR DESEJAR 
	     * @param tag 
	     * @param novaPalavra 
	     */  
	    public void replaceTag(String tag, String novaPalavra){  
	          
	            Range range = doc.getRange();  
	            range.replaceText(tag,novaPalavra);  
	              
	  
	    }     

	    
	    public byte[] returnBytes(){  
	    	
	    	byte[] dados =doc.getDataStream();
	          
	    		return dados;
              
  
    }
	    
	    public static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
	        Range r1 = doc.getRange(); 

	        for (int i = 0; i < r1.numSections(); ++i ) { 
	            Section s = r1.getSection(i); 
	            for (int x = 0; x < s.numParagraphs(); x++) { 
	                Paragraph p = s.getParagraph(x); 
	                for (int z = 0; z < p.numCharacterRuns(); z++) { 
	                    CharacterRun run = p.getCharacterRun(z); 
	                    String text = run.text();
	                    if(text.contains(findText)) {
	                        run.replaceText(findText, replaceText);
	                       // replaceTag(doc, findText);
	                    } 
	                }
	            }
	        } 
	        return doc;
	    }
	    
	    
	    public static HWPFDocument replaceTag(HWPFDocument doc,Map<String, String> maps){
	        Range r1 = doc.getRange(); 

	        for (int i = 0; i < r1.numSections(); ++i ) { 
	            Section s = r1.getSection(i); 
	            for (int x = 0; x < s.numParagraphs(); x++) { 
	                Paragraph p = s.getParagraph(x); 
	                for (int z = 0; z < p.numCharacterRuns(); z++) { 
	                    CharacterRun run = p.getCharacterRun(z); 
	                    String text = run.text();
	                    Set keys = maps.keySet();
	                   
	                    for(Object key : keys) {
	                    	
	                    	String value = maps.get(key); 
	                    	String chave = key.toString(); 
		                    if(text.contains(chave)) {
		                        run.replaceText(chave, value);
		                   
		                    } 
	                    	
	                    
	                    }
	                    

	                }
	            }
	        } 
	        return doc;
	    }
	    
	    public static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
	        FileOutputStream out = null;
	     //   File file = new File(filePath);
	     //   file.setReadOnly();
	     //   file.setWritable(false,true);
	        
	        try{
	            out = new FileOutputStream(filePath);
//	            file.setReadOnly();
	            doc.write(out);
	           
	            
	        }
	        finally{
	            out.close();
	        }
	    }
}
