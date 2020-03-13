package com.tecsoluction.robo.entidade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ManipuladorArquivo {
	
	final static Logger logger = LoggerFactory.getLogger(ManipuladorArquivo.class);

	
	private FileWriter  filewriter;
	
	private FileReader  fileread;
	
	private File  file;
	
	private File  filefiltro;
	
	private String  conteudo = new String();
	
	private String  conteudofiltro = new String();
	
	private BufferedWriter  buferedwriter;
	
	private BufferedReader buferedread;
	
	private boolean aux =false;
	
	private boolean auxfilter =false;
	
	private HashMap<Integer, String> lines= new HashMap<Integer, String>();
	
	private HashMap<Integer, Filtro> linesfiter= new HashMap<Integer, Filtro>();
	
	
	public ManipuladorArquivo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ManipuladorArquivo(File fil,File fil2) {

	this.file = fil;
	this.filefiltro = fil2;
	this.lines= new HashMap<Integer, String>();
	this.linesfiter= new HashMap<Integer, Filtro>();
	this.conteudo = new String();
	this.conteudofiltro = new String();
	
	
	
	Init();
	
	}
	
	
	public ManipuladorArquivo(File fil) {

	this.file = fil;
//	this.filefiltro = fil2;
	this.lines= new HashMap<Integer, String>();
	this.linesfiter= new HashMap<Integer, Filtro>();
	this.conteudo = new String();
	this.conteudofiltro = new String();
	
	
	
	Init();
	
	}
	
	private void Init(){
		
		aux = Verficar(file);
		
		if(aux){
			
			Ler(file);
			
			
		}else {
			
			CriarArquivo(file);
			
		}
		
		
		
		
		auxfilter = Verficar(filefiltro);
		
		if(auxfilter){
			
			LerFilter(filefiltro);
			
		}else {
			
			CriarArquivoFiltro(filefiltro);
			
		}
		
		
		
	}
	
	
	public void Escrever(String path,String content){
		
		this.conteudo = conteudo + content + "\n";
		
		
		 try {
			filewriter= new FileWriter(file.getAbsoluteFile());
			buferedwriter= new BufferedWriter(filewriter);
			buferedwriter.write(conteudo);
			buferedwriter.close();
		}catch (Exception e) {
			// TODO: handle exception
	
		}
		 
		 
		 
	}
	
	
	public void EscreverFilter(String path,Filtro content){
		
		
		
		this.conteudofiltro = conteudofiltro + content.toString() + "\n";
		
		
		
		
		 try {
			filewriter= new FileWriter(filefiltro.getAbsoluteFile());
			buferedwriter= new BufferedWriter(filewriter);
			buferedwriter.write(conteudofiltro);
			buferedwriter.close();
		}catch (Exception e) {
			// TODO: handle exception
	
		}
		 
		 
		 
	}
	
	public String Ler(File path){
		
		String result = new String();
		result="";
		
		
//		lines = new HashMap<Integer, String>();
		int lineNumber = 0;
		
		 try {
			fileread = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          buferedread = new BufferedReader(fileread);  
          String linha;
          try {
			while( (linha = buferedread.readLine()) != null ){
			     
				System.out.println(linha);
				lines.put(lineNumber, linha);
			      result = result + linha;
			      lineNumber++;
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	
	public String LerFilter(File path){
		
		String result = new String();
		result="";
		
		
//		lines = new HashMap<Integer, String>();
		int lineNumber = 0;
		
		 try {
			fileread = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          buferedread = new BufferedReader(fileread);  
          String linha;
          try {
			while( (linha = buferedread.readLine()) != null ){
			     
				System.out.println(linha);
				linesfiter.put(lineNumber, new Filtro(linha));
			      result = result + linha;
			      lineNumber++;
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	
	private boolean Verficar(File file){
		
		
		boolean existe = false;
		
		
		
		existe = file.exists();
		
		
		return existe;
		
		
		
	}
	
	
	
	private File CriarArquivo(File file){
		
		
		boolean criado = false;
		String s = file.getPath().replace(file.getName(),"");
		
		File f = new File (s);
		
		if( f.mkdirs() ){
		    System.out.println("Diretório criado");
		  //  oficiook = true;
		    
		}else{
		    System.out.println("Diretório não criado");
		   // oficiook = false;
		}
		
		try {
			criado = file.createNewFile();
			 System.out.println("arquivo criado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(criado){
			
			this.file = file;
			
		}else {
			
			
			
			
		}
		
		
		return file;
		
		
		
	}
	
	private File CriarArquivoFiltro(File file){
		
		
		boolean criado = false;
		String s = file.getPath().replace(file.getName(),"");
		
		File f = new File (s);
		
		if( f.mkdirs() ){
		    System.out.println("Diretório filtro criado");
		  //  oficiook = true;
		    
		}else{
		    System.out.println("Diretório filtro não criado");
		   // oficiook = false;
		}
		
		try {
			criado = file.createNewFile();
			 System.out.println("arquivo filtro criado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(criado){
			
			this.filefiltro = file;
			
		}else {
			
			
			
			
		}
		
		
		return file;
	}

	
	 public static void copy(File origem, File destino) throws IOException {
	        Date date = new Date();
	        InputStream in = new FileInputStream(origem);
	        OutputStream out = new FileOutputStream(destino);          
	        // Transferindo bytes de entrada para saída
	        byte[] buffer = new byte[1024];
	        int lenght;
	        while ((lenght= in.read(buffer)) > 0) {
	            out.write(buffer, 0, lenght);
	        }
	        in.close();
	        out.close();
	        Long time = new Date().getTime() - date.getTime();
	        System.out.println("Saiu copy"+time);
	    }
	

	 
	 
	 
	 public static void copy(File origem, File destino, boolean overwrite) throws IOException{
	        Date date = new Date();
	       if (destino.exists() && !overwrite){
	          System.err.println(destino.getName()+" já existe, ignorando...");
	          return;
	       }
	       FileInputStream fisOrigem = new FileInputStream(origem);
	       FileOutputStream fisDestino = new FileOutputStream(destino);
	       FileChannel fcOrigem = fisOrigem.getChannel();  
	       FileChannel fcDestino = fisDestino.getChannel();  
	       fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);  
	       fisOrigem.close();  
	       fisDestino.close();
	       Long time = new Date().getTime() - date.getTime();
	       System.out.println("Saiu copy"+time);
	    }

}
