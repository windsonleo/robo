package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecsoluction.robo.controller.MainController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GerenciadorFiltro implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	final static Logger logger = LoggerFactory.getLogger(GerenciadorFiltro.class);

	
	private List<Registro> registros = new ArrayList<Registro>();
	
	private List<Detento> detentos =  new ArrayList<Detento>();
	
	private List<Filtro> filtros =  new ArrayList<Filtro>();
	
	private List<Object> excluidos =  new ArrayList<Object>();;
	
	private Field atributo;
	
	private Object object = null; 
	 
	private  Class<?> classDef;
	
	private String valor;
	
	private String chave;
	
	
	
	public GerenciadorFiltro() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	public GerenciadorFiltro(List<Filtro> filts , List<Registro> regs , List<Detento> dets) {
		
		this.detentos = dets;
		this.filtros = filts;
		this.registros = regs;
		
		InitBusca(filtros);
	
	
	}
	
	public GerenciadorFiltro(List<Filtro> filtss , List<Registro> regss ) {
		
//		this.detentos = dets;
		this.filtros = filtss;
		this.registros = regss;
		
		InitBusca(filtros);
	
	
	}
	
	
//	public GerenciadorFiltro(List<Filtro> filts , List<Detento> dets ) {
//		
//		this.detentos = dets;
//		this.filtros = filts;
////		this.registros = regs;
//		
//		InitBusca(filtros);
//	
//	
//	}
	
	

	
	public void InitBusca(List<Filtro> filtros2) {

		
		for(Filtro filtro : filtros2){
		
		
			IdentificarClasse(filtro);
	}
		
	}



	
	private void IdentificarClasse(Filtro filtro){
		
		 chave = null;
		 chave = filtro.getObjeto();
		String clase = null;
		clase = "com.tecsoluction.robo.entidade." + chave;
		classDef = null;
		object = null;
		String atrib = null;
		valor = null;
		
		try {
			classDef =	Class.forName(clase);
			  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			object = classDef.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		atrib = filtro.getChave();
		
//		try {
//			atributo = object.getClass().getField(atrib);
//		} catch (NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	//	IdentificarAtributo(atributo);
		
		 valor = filtro.getValor().toUpperCase();
		 
		 ProcurarValorNoAtributo(chave,atrib);
			
	}
	
	
	private void ProcurarValorNoAtributo(String chave2, String atrib){
		
		
		
		if(chave2.equals("Detento")){
			
			try {
				
				//atributo.setAccessible(true);
				atributo = object.getClass().getField(atrib);
				atributo.setAccessible(true);
				LocalizarValorDetentos(detentos,atributo);
				
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		if(chave2.equals("Violacao")){
			
			try {
				
				atributo = object.getClass().getField(atrib);
				atributo.setAccessible(true);
				LocalizarValorViolacao(detentos,atributo);
				
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		if(chave2.equals("Registro")){
			
			try {
				
				atributo = object.getClass().getField(atrib);
				atributo.setAccessible(true);
				LocalizarValorRegistro(registros,atributo);
				
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}


	private void LocalizarValorRegistro(List<Registro> registros2, Field atributo2) {

		Object ValorAtributo="";
//		Object ValorAtributo2="";
		atributo2.setAccessible(true);
		String nomecampo = "descricao";
		String nomecampo2 = "caracteristica";
		String nomecampo3 = "Estabelecimento";

		
		Iterator<Registro> it = registros2.iterator();

		while(it.hasNext()){
						
			Registro item = it.next();
			
			try {
				ValorAtributo =	atributo2.get(item);
//				ValorAtributo2 =atributo2.get(item);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		            if(nomecampo.equals(atributo2.getName())){
		            	
		            	
						if(ValorAtributo.toString().toUpperCase().contains(valor.toUpperCase())){                                
					           
			            	excluidos.add(item);
//			            	registros.remove(item);
			            	it.remove();
			            
			            }
		            	
		            }
		            
		            
		            
		            else if (nomecampo2.equals(atributo2.getName())) {
		            	
//		            	System.out.println("atrib valor: " + ValorAtributo);
//		            	System.out.println(" valor: " + valor);
		            	
		            	
		            	
		            	if(ValorAtributo!= null){
		            		
			            	if(ValorAtributo.toString().toUpperCase().contains(valor.toUpperCase())){
			            		
				            	
				            	excluidos.add(item);
//				            	registros.remove(item);
				            	it.remove();
			            	
			            	}
		            		
		            		
		            	}
		            	

		            	
		            	
		            	
		            	
		            }
		            
		            else if (nomecampo3.equals(atributo2.getName())) {
		            	
//		            	System.out.println("atrib valor: " + ValorAtributo);
//		            	System.out.println(" valor: " + valor);
		            	
		            	
		            	
		            	if(ValorAtributo!= null){
		            		
			            	if(ValorAtributo.toString().toUpperCase().equalsIgnoreCase(valor.toUpperCase())){
			            		
				            	
				            	excluidos.add(item);
//				            	registros.remove(item);
				            	it.remove();
			            	
			            	}
		            		
		            		
		            	}
		            	

		            	
		            	
		            	
		            	
		            }
		            
		            else {
		            	
		            	
		            	if(ValorAtributo!= null){
		            		
							if(valor.trim().equalsIgnoreCase(ValorAtributo.toString().trim())){                                
						           
				            	excluidos.add(item);
//				            	registros.remove(item);
				            	it.remove();
				            
				            }

			
		            	}
		            	
			

					
		        }
		
		
	}
		
		
		
	}


	private void LocalizarValorViolacao(List<Detento> detentos2, Field atributo2) {

		Object ValorAtributo="";
		atributo2.setAccessible(true);
		
		
		Iterator<Detento> iti = detentos2.iterator();
		
		

		while(iti.hasNext()){
			
//			Object ValorAtributo="";
			
			Detento item = iti.next();
			
			
			
			Iterator<Violacao> itv = item.getViolacoes().iterator();
			
			while(itv.hasNext()){
				
				Violacao vio = itv.next();
				
				try {
					ValorAtributo =	atributo2.get(vio);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 if(valor.equals(ValorAtributo)){                                
			           
		            	excluidos.add(vio);
		            	
		            	itv.remove();
		            
		            }
				
			}
			
			

		
	}
		
		
		
	}


	private void LocalizarValorDetentos(List<Detento> detentos22, Field atributo2) {

		Object ValorAtributo="";
		atributo2.setAccessible(true);
		
		Iterator<Detento> itt = detentos22.iterator();
		
		

		while(itt.hasNext()){
			
			
			Detento item = itt.next();
			
			
			try {
				ValorAtributo =	atributo2.get(item);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		            if(valor.equals(ValorAtributo)){                                
		           
		            	itt.remove();
		            	excluidos.add(item);
		            //	detentos.remove(item);
		            	
		            
		            }
		        }
		
		
	
	}
	
	
	
	public List<Registro> FiltroInclusivo(List<Registro> registros2, Field atributo2,String valor) {

		Object ValorAtributo="";
		atributo2.setAccessible(true);
		String nomecampo2 = "caracteristica";
		
		Iterator<Registro> it = registros2.iterator();

		while(it.hasNext()){
						
			Registro item = it.next();
			
			try {
				ValorAtributo =	atributo2.get(item);
//				ValorAtributo2 =atributo2.get(item);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		            if(nomecampo2.equals(atributo2.getName())){
		            	
		            	
		            	if(ValorAtributo!= null){
		            		
		            		
						if(ValorAtributo.toString().toUpperCase().contains(valor.toUpperCase())){                                
					           

				            
			            }else{
			            	
			            	
			            	excluidos.add(item);
//				            	registros.remove(item);
			            	it.remove();
			            	
			            }
		            		
		            		
		            		
		            		
		            	}else {
		            		
		            		
			            	excluidos.add(item);
//			            	registros.remove(item);
		            	it.remove();
		            		
		            		
		            	}
		            	

		            	
		            }

		            
		            else {
			
			
					if(!valor.equals(ValorAtributo)){                                
		           
		            	excluidos.add(item);
//		            	registros.remove(item);
		            	it.remove();
		            
		            }
					
		        }
		
		
	}
		
		return registros2;
		
	}

}
