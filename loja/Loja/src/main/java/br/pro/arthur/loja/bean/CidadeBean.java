package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.CidadeDAO;
import br.pro.arthur.loja.dao.EstadoDAO;
import br.pro.arthur.loja.domain.Cidade;
import br.pro.arthur.loja.domain.Estado;

@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cidade cidade;

	private List<Cidade> cidades;

	private List<Estado> estados;

	@PostConstruct
	public void init() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as cidades");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {

			cidade = new Cidade();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();

		} catch (Exception e) {
			messageError("Ocorreu um erro ao listar os estados.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);

			cidade = new Cidade();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();

			cidades = cidadeDAO.listar();
			message("Cidade salva com sucesso!");
		} catch (RuntimeException error) {
			messageError("Erro ao salvar a cidade.");
			error.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);
			cidades = cidadeDAO.listar();
			message("Cidade removida com sucesso!");
		} catch (RuntimeException error) {
			messageError("Erro ao tentar remover a Cidade");
			error.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		try {

			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();

		} catch (Exception e) {
			messageError("Ocorreu um erro ao listar os estados.");
			e.printStackTrace();
		}

	}

	public void message(String message) {
		Messages.addFlashGlobalInfo(message);
	}

	public void messageError(String message) {
		Messages.addFlashGlobalError(message);
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Estado> getEstados() {
		return estados;
	}
}
