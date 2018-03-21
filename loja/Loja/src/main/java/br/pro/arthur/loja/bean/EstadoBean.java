package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.EstadoDAO;
import br.pro.arthur.loja.domain.Estado;

@ManagedBean //
@ViewScoped
public class EstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Estado estado;

	private List<Estado> estados;

	@PostConstruct
	public void init() {
		EstadoDAO dao = new EstadoDAO();
		estados = dao.listar();
	}

	public void novo() {
		estado = new Estado();
		postProcess();
	}

	public void salvar() {
		try {
			EstadoDAO dao = new EstadoDAO();
			dao.salvar(estado);

			novo();
			postProcess();
			printMessage("Salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar o estado");
			erro.printStackTrace();
		}
	}

	// excluir
	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO dao = new EstadoDAO();
			dao.excluir(estado);
			printMessage("Excluído com sucesso!");
			postProcess();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir um estado!");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO dao = new EstadoDAO();
			dao.editar(estado);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um estado");
		}
	}

	private void postProcess() {
		EstadoDAO dao = new EstadoDAO();
		estados = dao.listar();

	}

	private void printMessage(String message) {
		Messages.addGlobalInfo(message);

	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
