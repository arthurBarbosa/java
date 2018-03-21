package br.pro.arthur.loja.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Caixa extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	@Temporal(TemporalType.DATE)
	private Date dataDeAbertura;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataDeFechamento;

	@Column(nullable = false, precision = 7, scale = 2)
	private Double valorAbertura;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataDeAbertura() {
		return dataDeAbertura;
	}

	public void setDataDeAbertura(Date dataDeAbertura) {
		this.dataDeAbertura = dataDeAbertura;
	}

	public Date getDataDeFechamento() {
		return dataDeFechamento;
	}

	public void setDataDeFechamento(Date dataDeFechamento) {
		this.dataDeFechamento = dataDeFechamento;
	}

	public Double getValorAbertura() {
		return valorAbertura;
	}

	public void setValorAbertura(Double valorAbertura) {
		this.valorAbertura = valorAbertura;
	}

}
