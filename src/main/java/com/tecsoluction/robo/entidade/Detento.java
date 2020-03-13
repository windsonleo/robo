package com.tecsoluction.robo.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Detento implements Serializable {

	public static final long serialVersionUID = 1L;

	public String Estabelecimento;
	public String perfil;
	public String tipoperfil;
	public String prontuario;
	public String nome;
	public String statusenvio;
	public String statusdoc;

	public String email;

	public List<Violacao> violacoes = new ArrayList<Violacao>();

	public String vep;

	public List<String> erros = new ArrayList<String>();

	public String processo;

	public String vara;
	
	public boolean matrix = false;

	// CONSTRUTOR PADRAO
	public Detento() {

		violacoes = new ArrayList<Violacao>();
		erros = new ArrayList<String>();

	}

	public Detento(String nome) {
		this.nome = nome;

	}

	public Detento(Registro registro) {

		this.nome = registro.getNome();
		this.prontuario = registro.getProntuario();
		this.Estabelecimento = registro.getEstabelecimento();
		this.perfil = registro.getPerfil();
		this.tipoperfil = registro.getTipoperfil();

		this.statusenvio = "PENDENTE";
		this.statusdoc = "PENDENTE";

		this.vep = registro.getVep();

		this.violacoes = new ArrayList<Violacao>();

		this.email = registro.getEmail();

		this.processo = registro.getProcesso();
		this.vara = registro.getVara();

	}

	public void addViolacao(Violacao violacao) {

		this.getViolacoes().add(violacao);

	}

	public void removeViolacao(Violacao violacao) {

		this.getViolacoes().remove(violacao);

	}

	public void addErros(String err) {

		this.getErros().add(err);

	}

	public void removeErros(String err) {

		this.getErros().remove(err);

	}

	@Override
	public String toString() {
		return nome.toUpperCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Detento)) {
			return false;
		}
		final Detento other = (Detento) obj;
		return this.prontuario.equals(other.getProntuario());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
