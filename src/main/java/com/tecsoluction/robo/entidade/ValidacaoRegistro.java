package com.tecsoluction.robo.entidade;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecsoluction.robo.controller.MainController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacaoRegistro {
	
	final static Logger logger = LoggerFactory.getLogger(ValidacaoRegistro.class);

	private Registro registro;
	
	
//	private Detento detento;
	
//	private boolean isvalido=false;
	
//	private List<Violacao> violacoes;
	
//	private List<Notificacao> notificações;
	
	
	
	
	
	
	
	public ValidacaoRegistro() {


	
	}
	
	
	
	public ValidacaoRegistro(Registro det) {
		
		this.registro = det;
		

	}



	
public boolean validaRegistro(Registro detento){
	
	boolean valido = true;
	
	String nulo = null;
	String branco = "";
	

		
		
		if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){
			
			valido = false;
			
			detento.addErros("Nome");
			
//			PreencherValidacaoNome(detento, valido);
			
//			ValidaDetentoInformacao("Nome Detento Branco ou Nulo");
				
			}else {
				
//				PreencherValidacaoNome(detento, valido);
				
				
			}
			
		if((detento.getEmail()==null)|| (detento.getEmail().equals(branco)) ){
				
			valido = false;
			detento.addErros("Email nulo");
			
//			ValidaDetentoInformacao("Email Detento Branco ou Nulo");
			
//			PreencherValidacaoEmail(detento, valido);
				
			}else {
				
//				PreencherValidacaoEmail(detento, valido);
//				boolean val = VerificarPadraoEmail(detento);
				//valido = true;
//				valido = VerificarPadraoEmail(detento);
				
//				if(valido && val){
//					
//					valido = true;
//					
//				}else {
//					
//					valido = false;
//				
//
//					detento.addErros("Padrão Email");
//					
//				}
				
				
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
				
			 if((detento.getIdnotitificacao()==null)||(detento.getIdnotitificacao().toString().equals(branco))){
					
				valido = false;
				detento.addErros("notificação");
				
//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
					
				}else {
					
					
				boolean	valid = VerificarPadraoIdNotificao(detento);
					
					if(valid && valido){
						
						valido = true;
						
					//	detento.addErros("Padrão notificação1");
						
					} else if (valid && !valido) {
						
						valido = false;
//						detento.addErros("Padrão notificação");
						
					}	
					
					else if (!valid && valido) {
						
						valido = false;
						detento.addErros("Padrão notificação");
						
					}else if (!valid && !valido) {
						
						valido = false;
						detento.addErros("Padrão notificação");
						
					}		
						
						
						
					
					}
					
				
			 
			 if((detento.getIdviolacao()==null) || (detento.getIdviolacao().equals(branco)) ){
					
					valido = false;
					detento.addErros("violacao");
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}else {
						
						
						boolean	valid = VerificarPadraoIdViolacao(detento);
						
						if(valid && valido){
							
							valido = true;	
						//	detento.addErros("Padrão violacao");
							
						}else if (valid && !valido) {
							
							valido = false;
//							detento.addErros("Padrão violacao");
							
						}	
						
						else if (!valid && valido) {
							
							valido = false;
							detento.addErros("Padrão violacao");
							
						}else if (!valid && !valido) {
							
							valido = false;
							detento.addErros("Padrão violacao");
							
						}	
						
						
					}
			 
			 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
					
					valido = false;
					detento.addErros("Estabelecimento");
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}
			 
			 
			 
			 
			 if((detento.getCaracteristica()==null)||(detento.getCaracteristica().equals(branco)) ){
					
					valido = false;
					detento.addErros("caracterista");
					
//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
						
					}else {
						
						
						detento.setVara(detento.getCaracteristica());
						
						
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
				
				
				
				if( (detento.getDescricao()==null) || (detento.getDescricao().equals(branco)) ){
					
					
					valido = false;
					detento.addErros("Descricao");
					
//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
						
				
//					PreencherValidacaoProcesso(detento, valido);
//					PreencherValidacaoVep(detento, valido);
						
					}else {
						
//						PreencherValidacaoProcesso(detento, valido);
//						PreencherValidacaoVep(detento, valido);
						
						
					}
			
	
	
	
	return valido;
	
}



private boolean VerificarPadraoIdViolacao(Registro detento) {

	
	 boolean isViolaValid = false;
	String idnot = detento.getIdviolacao();
	
	if((idnot.startsWith("V")) && (idnot.length()>0)){
		
		isViolaValid =true;
		
	}else{
		
		isViolaValid = false;
		
	}
	
	return isViolaValid;
	
	
}



private boolean VerificarPadraoIdNotificao(Registro detento) {

	 boolean isNotiValid = false;
	String idnot = detento.getIdnotitificacao();
	
	if((idnot.startsWith("N")) && (idnot.length()>0)){
		
		isNotiValid =true;
		
	}else{
		
		isNotiValid = false;
		
	}
	
	return isNotiValid;
}



public boolean VerificarPadraoEmail(Registro detento) {
	
	boolean isEmailIdValid = false;
	
	 String email = detento.getEmail();
	 
     if (email != null && email.length() > 0) {
         String expression = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?$";
         Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(email);
         if (matcher.matches()) {
             isEmailIdValid = true;
         }
         
         if (matcher.find()) {
        	    System.out.println("Full match: " + matcher.group(0));
        	    for (int i = 1; i <= matcher.groupCount(); i++) {
        	        System.out.println("Group " + i + ": " + matcher.group(i));
        	    }
         
     }
     
}

     return isEmailIdValid;	
}	

}
