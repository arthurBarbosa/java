package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import br.pro.arthur.loja.dao.CaixaDAO;
import br.pro.arthur.loja.dao.FuncionarioDAO;
import br.pro.arthur.loja.domain.Caixa;
import br.pro.arthur.loja.domain.Funcionario;

@ManagedBean
@ViewScoped
public class CaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Caixa caixa;
	
	private ScheduleModel listagemCaixas;

	private Funcionario funcionario;
	private List<Funcionario> funcionarios;

	@PostConstruct
	public void listar() {
		listagemCaixas = new DefaultScheduleModel();
		
		
	}
	
	public void novo(SelectEvent evento) {
		caixa = new Caixa();
		
		caixa.setDataDeAbertura((Date)evento.getObject());
		
		
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		funcionarios = funcionarioDAO.listar();
	}
	
	public void salvar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(caixa.getDataDeAbertura());
		calendar.add(Calendar.DATE, 1);
		caixa.setDataDeAbertura(calendar.getTime());
		
		CaixaDAO caixaDAO = new CaixaDAO();
		caixaDAO.salvar(caixa);
		printMessageSuccess("Caixa aberto com sucesso.");
		
	}

	private void printMessageSuccess(String message) {
		Messages.addGlobalInfo(message);
		
	}
	private void printMessageError(String message) {
		Messages.addGlobalError(message);
		
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public ScheduleModel getListagemCaixas() {
		return listagemCaixas;
	}

	public void setListagemCaixas(ScheduleModel listagemCaixas) {
		this.listagemCaixas = listagemCaixas;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
}
