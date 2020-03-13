package com.tecsoluction.robo.entidade;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacaoDetento {
	
	
	
	
	private Detento detento;
	
	private boolean isvalido=false;
	
	private List<Violacao> violacoes;
	
	private List<Notificacao> notificações;
	
	
	
	
	
	
	
	public ValidacaoDetento() {


	
	}
	
	
	
	public ValidacaoDetento(Detento det) {
		
		this.detento = det;
		

	}



public void PreencherVaraComCaracteristica(Detento det){
		
		
		
	    String varaex = null;
	    
	    
	    int cont =0;
	    
		for (Violacao vio : det.getViolacoes()) {
	    	
//	    	builder.append("\r\n" + vio.getAlarme() + "\r\n");
//	    	alarme = alarme +"," + vio.getAlarme();
	    	
	    	if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){
	    		
	    		cont = cont +1;
	    		
	    		varaex = vio.getCaracteristica();
	    		det.setVara(varaex);
	    		
	    		
	    		
	    	}
	    	
	    	 if(varaex == null || varaex ==""){
	 	    	
	 	    	varaex="vazia";
	 	    	
	 	    	det.setStatusdoc("ERROR");
	 	    	det.getErros().add("SEM VARA PREENCHIDA(CARACTERISTICA)");
	 	    	
	 	    	//detentoserros.add(det);
	 	    	
	 	    	
	 	    }else {
	 	    	
	 	    //	det.setStatusdoc("VALIDADO");
	 	    //	detentosucesso.add(det);
	 	    	
	 	    	
	 	    }
	    	

			
		}
		
		
	}
	
private boolean validaDetentoEmail(Detento detento){
	
	boolean valido = true;
	
	String nulo = null;
	String branco = "";
	
//	detento.PreencheVara();
			
		//String nome = detento.getNome();
	
//	if((jcbemail.isSelected())) {
		
	if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){
			
		valido = false;
		
		detento.addErros("Nome");
		
//		PreencherValidacaoNome(detento, valido);
		
//		ValidaDetentoInformacao("Nome Detento Branco ou Nulo");
			
		}else {
			
//			PreencherValidacaoNome(detento, valido);
			
			
		}
		
	if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){
			
		valido = false;
		detento.addErros("Email");
		
//		ValidaDetentoInformacao("Email Detento Branco ou Nulo");
			
		}else {
			
//			PreencherValidacaoEmail(detento, valido);
			
			
		}
		
		//String pronto = detento.getProntuario();
		
	 if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){
			
			valido = false;
			detento.addErros("Prontuario");
				
//			ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");
				
			}
			
		if((detento.getProcesso()==null)&&(detento.getVep()==null) ){
				
			valido = false;
			detento.addErros("Processo/VEP NULOS");
			
//			ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			
//			PreencherValidacaoProcesso(detento, valido);
//			PreencherValidacaoVep(detento, valido);
				
			}else {
				
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
				
				
			}
			
		 if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){
				
			valido = false;
			detento.addErros("Violações");
			
//			ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
				
			}
		 
		 if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
				
				valido = false;
				detento.addErros("CARACTRISTICA");
				
//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
					
				}
		 
		 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
				
				valido = false;
				detento.addErros("Estabelecimento");
				
//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
					
				}
		 
		 
		 if((detento.getVara() == null || detento.getVara().equals(branco)) ){
				
				valido = false;
				detento.addErros("Vara");
				
//				PreencherValidacaoVara(detento, valido);
				
//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
					
				}else {
					
//					PreencherValidacaoVara(detento, valido);
					
				}
			 
			 
//			 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
//					
//				valido = false;
//				detento.addErros("Vara");
//				
////				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//					
//				}
			 
			 
//			 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
//					
//					valido = false;
//					detento.addErros("Equip");
//					
////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//						
//					}
		 
			if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){
				
				
				valido = false;
				detento.addErros("Processo/VEP VAZIO");
				
//				ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
					
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
					
				}else {
					
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
					
					
				}
			
			
			if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
				
				
				valido = false;
				detento.addErros("Processo/VEP PREENCHIDOS");
				
//				ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
					
			
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
					
				}else {
					
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
					
					
				}
	 
	 

	

		
		
			
//		 if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){
//				
//			valido = false;
//			detento.addErros("Prontuario");
//				
////			ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");
//				
//			}
//			
//		if((detento.getProcesso()==null)&&(detento.getVep()==null) ){
//				
//			valido = false;
//			detento.addErros("Processo/VEP NULOS");
//			
//			PreencherValidacaoProcesso(detento, valido);
//			PreencherValidacaoVep(detento, valido);
//				
//			}else {
//				
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
//				
//				
//			}
//			
//		 if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){
//				
//			valido = false;
//			detento.addErros("Violações");
//			
////			ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//				
//			}
//		 
//		 if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
//				
//				valido = false;
//				detento.addErros("CARACTRISTICA");
//				
////				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//					
//				}
//		 
//		 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
//				
//				valido = false;
//				detento.addErros("Estabelecimento");
//				
////				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//					
//				}
//		 
//		 
//		 if((detento.getVara() == null || detento.getVara().equals(branco)) ){
//				
//				valido = false;
//				detento.addErros("Vara");
//				
//				PreencherValidacaoVara(detento, valido);
//			
//		 
//		 }else {
//				
//				PreencherValidacaoVara(detento, valido);
//				
//			}
//			 
//			 
////			 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
////					
////				valido = false;
////				detento.addErros("Vara");
////				
//////				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
////					
////				}
//			 
//			 
////			 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
////					
////					valido = false;
////					detento.addErros("Equip");
////					
//////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
////						
////					}
//		 
//			if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){
//				
//				
//				valido = false;
//				detento.addErros("Processo/VEP VAZIO");
//				
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
//					
//				}else {
//					
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
//					
//					
//				}
//			
//			
//			if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
//				
//				
//				valido = false;
//				detento.addErros("Processo/VEP PREENCHIDOS");
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
//					
//				}else {
//					
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
//					
//					
//				}
		
		
		if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){
			
			valido = false;
			
			detento.addErros("Nome");
			
//			PreencherValidacaoNome(detento, valido);
			
//			ValidaDetentoInformacao("Nome Detento Branco ou Nulo");
				
			}else {
				
//				PreencherValidacaoNome(detento, valido);
				
				
			}
			
		if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){
				
			valido = false;
			detento.addErros("Email");
			
//			ValidaDetentoInformacao("Email Detento Branco ou Nulo");
			
//			PreencherValidacaoEmail(detento, valido);
				
			}else {
				
//				PreencherValidacaoEmail(detento, valido);
				
				
			}
			
			//String pronto = detento.getProntuario();
			
		 if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){
				
				valido = false;
				detento.addErros("Prontuario");
					
//				ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");
					
				}
				
			if((detento.getProcesso()==null)&&(detento.getVep()==null) ){
					
				valido = false;
				detento.addErros("Processo/VEP NULOS");
				
//				ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
				
//				PreencherValidacaoProcesso(detento, valido);
//				PreencherValidacaoVep(detento, valido);
					
				}else {
					
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
					
					
				}
				
			 if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){
					
				valido = false;
				detento.addErros("Violações");
				
//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
					
				}
			 
			 if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
					
					valido = false;
					detento.addErros("CARACTRISTICA");
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}
			 
			 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
					
					valido = false;
					detento.addErros("Estabelecimento");
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}
			 
			 
			 if((detento.getVara() == null || detento.getVara().equals(branco)) ){
					
					valido = false;
					detento.addErros("Vara");
					
//					PreencherValidacaoVara(detento, valido);
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}else {
						
//						PreencherValidacaoVara(detento, valido);
						
					}
				 
				 
//				 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
//						
//					valido = false;
//					detento.addErros("Vara");
//					
////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//						
//					}
				 
				 
//				 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
//						
//						valido = false;
//						detento.addErros("Equip");
//						
////						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//							
//						}
			 
				if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){
					
					
					valido = false;
					detento.addErros("Processo/VEP VAZIO");
					
//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
						
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
						
					}else {
						
//						PreencherValidacaoProcesso(detento, valido);
//						PreencherValidacaoVep(detento, valido);
						
						
					}
				
				
				if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
					
					
					valido = false;
					detento.addErros("Processo/VEP PREENCHIDOS");
					
//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
						
				
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
						
					}else {
						
//						PreencherValidacaoProcesso(detento, valido);
//						PreencherValidacaoVep(detento, valido);
						
						
					}
			
			
	
	
	
	return valido;
	
}
	
	

}
