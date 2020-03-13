package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacao implements Serializable {
	
		
		/**
	 * 
	 */
		public static final long serialVersionUID = 1L;
		
		
		public String id;
		
		 public String	descricao;
	    
	    public String	usuario;
	    
	    public Violacao violacao = new Violacao();
	    
	    public boolean issubstituto = false;
	    
	    public boolean isremove=false;
	    

	    
	    public Notificacao() {
	    	
	    	

	    
	    }
	    
	    
	    
	    public Notificacao(Registro reg) {
	    	
	    	this.id = reg.getIdnotitificacao();
	    	this.descricao = reg.getDescricao();
	    	this.usuario = reg.getUsuario();
	    	this.issubstituto = reg.isIssubstituto();
	    	this.isremove = reg.isIsremove();
	    	violacao = new Violacao();
	    	
		    
	    }  
	    

		@Override
	    public String toString() {

	    	return id;
	    }
		
		
		
		 @Override
		    public boolean equals(Object obj) {
		        if (!(obj instanceof Notificacao)) {
		            return false;
		        }
		        final Notificacao other = (Notificacao) obj;
		        return this.id.equals(other.getId());
		    }
		    
		    
		    
		    @Override
		    public int hashCode() {
		    	// TODO Auto-generated method stub
		    	return super.hashCode();
		    }
	    
	    
	  
}
