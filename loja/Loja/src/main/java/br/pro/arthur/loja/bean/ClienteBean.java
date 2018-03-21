package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.ClienteDAO;
import br.pro.arthur.loja.dao.PessoaDAO;
import br.pro.arthur.loja.domain.Cliente;
import br.pro.arthur.loja.domain.Pessoa;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private List<Cliente> clientes;

	private List<Pessoa> pessoas;

	@PostConstruct
	public void init() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("dataCadastro");
		} catch (Exception error) {
			Messages.addFlashGlobalError("Erro ao listar os clientes.");
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (Exception error) {
			Messages.addFlashGlobalError("Erro ao listar os clientes.");
			error.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			Messages.addGlobalInfo("Cliente salvo com sucesso.");
			clientes = clienteDAO.listar("dataCadastro");;
			novo();
		} catch (Exception error) {
			Messages.addFlashGlobalError("Erro ao listar os clientes.");
			error.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);
			Messages.addGlobalInfo("Removido com sucesso.");
			clientes = clienteDAO.listar();
			novo();
		} catch (Exception error) {
			Messages.addFlashGlobalError("Erro ao remover o cliente.");
			error.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (Exception error) {
			Messages.addFlashGlobalError("Erro ao alterar o cliente.");
			error.printStackTrace();
		}
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
