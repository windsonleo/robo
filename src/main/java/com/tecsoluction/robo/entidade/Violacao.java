package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Violacao implements Serializable {
	
		
		/**
	 * 
	 */
		public static final long serialVersionUID = 1L;
		
		
		
		public String id;
		
		public List<Notificacao> notificacoes= new ArrayList<Notificacao>();

		public String datainicio;
	    
		public String datafinalizacao;
	    
	    public String	duracao;
	    
	    public String	tipoviolacao;

	    public Detento detento;
	    
	    public String alarme;
	    
	    public String duracaoalarme;
	    
	    
	    public String dataviolacao;
	    
	    public String caracteristica;
	    
	    public boolean isremove=false;


	    
	    public Violacao() {

	    	 notificacoes = new ArrayList<Notificacao>();
	    }
	    
	    
	    
	    public Violacao(Registro reg) {
	    	
	    	
	    this.id = reg.getIdviolacao();
	    this.datainicio = reg.getDatainicio();
	    this.datafinalizacao=reg.getDatafinalizacao();
	    this.duracao =reg.getDuracao();
	    this.tipoviolacao = reg.getTipoviolacao();
	    this.alarme = reg.getAlarme();
	    this.duracaoalarme = reg.getDuracaoalarme();
	    this.dataviolacao = reg.getDataviolacao();
	    this.caracteristica = reg.getCaracteristica();
	    this.isremove = reg.isremove;
	    
		    
	    }  
	    
		@Override
	    public String toString() {

	    	return id;
	    }
	    
	    

	    
	    public void addNotificao(Notificacao not){
	    	
	    	
	    	this.getNotificacoes().add(not);
	    	
	    }


	    public void removeNotificacao(Notificacao not){
	    	
	    	this.getNotificacoes().remove(not);
	    	
	    }
	    
	    
	    @Override
	    public boolean equals(Object obj) {
	        if (!(obj instanceof Violacao)) {
	            return false;
	        }
	        final Violacao other = (Violacao) obj;
	        return this.id.equals(other.getId());
	    }
	    
	    
	    
	    @Override
	    public int hashCode() {
	    	// TODO Auto-generated method stub
	    	return super.hashCode();
	    }
	    
	    


}
