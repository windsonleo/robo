package com.tecsoluction.robo.entidade;

import java.io.Serializable;

//import javax.persistence.Entity;
//import javax.persistence.Table;

//import com.cemeer.mala.mala.framework.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name="USUARIO")
public class Usuario implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private String foto;
	
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username;
	}
	
	public String PegarNomeClasse(){
		
		
		return this.getClass().getSimpleName();
	}
	

}
