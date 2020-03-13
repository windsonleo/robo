package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class Filtro extends Registro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String chave;
	
	private String valor;
	
	private String objeto;
	
	
	public Filtro() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Filtro(String linha) {

	this.valor = linha;
	
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Filtro)) {
			return false;
		}
		final Filtro other = (Filtro) obj;
		return this.valor.equals(other.getValor());
	}
	
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return chave + "," + valor + "," + objeto;
	}

}
