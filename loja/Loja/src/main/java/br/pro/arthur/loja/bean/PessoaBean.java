package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.CidadeDAO;
import br.pro.arthur.loja.dao.EstadoDAO;
import br.pro.arthur.loja.dao.PessoaDAO;
import br.pro.arthur.loja.domain.Cidade;
import br.pro.arthur.loja.domain.Estado;
import br.pro.arthur.loja.domain.Pessoa;

@ViewScoped
@ManagedBean
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa;

	private List<Pessoa> pessoas;

	private List<Cidade> cidades;

	private Estado estado;
	
	private List<Estado> estados;

	@PostConstruct
	public void init() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new Pessoa();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();

			estados = estadoDAO.listar("nome");

			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException error) {
			messageError("Erro ao listar os estados.");
			error.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);

			pessoa = new Pessoa();

			pessoas = pessoaDAO.listar();
			novo();
			message("Pessoa salva com sucesso!");
		} catch (RuntimeException error) {
			messageError("Erro ao salvar a pessoa.");
			error.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);
			pessoas = pessoaDAO.listar();
			message("Pessoa removido com sucesso.");

		} catch (RuntimeException error) {
			messageError("Erro ao remover a pessoa.");
			error.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			estado = pessoa.getCidade().getEstado();
			
			EstadoDAO estadoDAO = new EstadoDAO();

			estados = estadoDAO.listar("nome");
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			

		} catch (RuntimeException error) {
			messageError("Erro ao alterar a pessoa.");
			error.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
				System.out.println("total: " + cidades.size());
			} else {
				cidades = new ArrayList<Cidade>();
			}
		} catch (RuntimeException error) {
			messageError("Erro ao alterar o pessoa.");
			error.printStackTrace();
		}

	}

	private void message(String message) {
		Messages.addGlobalInfo(message);
	}

	private void messageError(String message) {
		Messages.addFlashGlobalError(message);
		;

	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
