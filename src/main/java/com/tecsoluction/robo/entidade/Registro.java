package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Registro implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
 
    
    public String  Estabelecimento	;
    public String perfil;
    public String tipoperfil;
    public	String prontuario;
    public String nome;
    
    public String datainicio;
    public String datafinalizacao;
    public String	duracao;
    public String tipoviolacao;
    
    public String descricao;
    public String idnotitificacao;
    public String idviolacao;
    
    public String	vep;
    public String	usuario;
    
//    public String artigos;
//    public String ativo;
    public String alarme;
    public String duracaoalarme;
//	
	public String dataviolacao;
//	
//    
//    public String	notificacao;
    public String	caracteristica;
    public String	email;
    public String	processo;
//    
    public String	vara;
//    
//    public String	mae_pai;
//    
//    public String	telefone;
//    
//    public String	equipamentos;
//    
//    public String	ult_posicao;
//    
//    public String	alarme_posicao;
//    
//    public String idviolacao;
//    
//    public String idnotificacao;
//    
    public boolean isremove = false;
    
    public boolean issubstituto = false;
    
	public List<String> erros = new ArrayList<String>();
    
    
    public Registro() {

    
    
    }
    
    
    @Override
    public String toString() {

    	return idnotitificacao.toUpperCase();
    }
    
    
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Registro)) {
			return false;
		}
		final Registro other = (Registro) obj;
		return this.idnotitificacao.equals(other.getIdnotitificacao());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	public void addErros(String err) {

		this.getErros().add(err);

	}

	public void removeErros(String err) {

		this.getErros().remove(err);

	}

}
