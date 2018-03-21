package br.pro.arthur.loja.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.pro.arthur.enumeracao.TipoUsuario;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain {

	@Column(length = 32, nullable = false)
	private String senha;

	@Transient
	private String senhaSemCriptografia;

	/*
	 * @Column(nullable = false) private Character tipo;
	 */
	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/*
	 * public Character getTipo() { return tipo; }
	 * 
	 * @Transient public String getTipoFormatado(){ String tipoFormatado = null;
	 * if(tipo == 'A'){ tipoFormatado = "Administrador"; }else if(tipo == 'B'){
	 * tipoFormatado = "Balconista"; }else if(tipo == 'G'){ tipoFormatado =
	 * "Gerente"; } return tipoFormatado; }
	 */
	/*
	 * public void setTipo(Character tipo) { this.tipo = tipo; }
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Transient
	public String getAtivoFormatado() {
		String ativoFormatado = "Não";
		if (ativo) {
			ativoFormatado = "Sim";
		}
		return ativoFormatado;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
