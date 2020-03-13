package com.tecsoluction.robo.entidade;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistroFinanceiro implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    
    private	String prontuario;
    
    private	String nome;
    

    private String ativo;
    
	private String datainst;
	
	private String datadesinst;
	
	private String valor;
	
	private Detento detento;

    
    
    public RegistroFinanceiro() {

    
    
    }
    
    
    @Override
    public String toString() {

    	return prontuario.toUpperCase();
    }
	

}
