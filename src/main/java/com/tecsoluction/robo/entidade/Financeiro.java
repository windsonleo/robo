package com.tecsoluction.robo.entidade;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode(callSuper = false)
//@Entity
//@Table(name = "VIOLACAO")
public class Financeiro implements Serializable {
	
		
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;

		private String datainst;
		
		private String datadesinst;
		
		private String valor;
		
	    private Detento detento;
	    
	 
	    
	    
	    
	    public Financeiro() {

	    
	    }
	    
	    public Financeiro(RegistroFinanceiro reg) {
	    	
	    this.datainst=reg.getDatainst();
	    this.datadesinst = reg.getDatadesinst();
	    this.valor=reg.getValor();
	    this.detento=reg.getDetento();
//	    this.duracao =reg.getDuracao();
//	    this.notificacao = reg.getNotificacao();
//	    this.caracteristica = reg.getCaracteristica();
	    
	    
	    
		    
	    }  
	    
	    @Override
	    public String toString() {

	    	return valor;
	    }

}
