package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.PessoaDAO;
import br.pro.arthur.loja.dao.UsuarioDAO;
import br.pro.arthur.loja.domain.Pessoa;
import br.pro.arthur.loja.domain.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private List<Usuario> usuarios;

	private Pessoa pessoa;

	private List<Pessoa> pessoas;

	@PostConstruct
	public void init() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarios = usuarioDAO.listar("tipoUsuario");
	}

	public void novo() {
		try {
			usuario = new Usuario();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");

		} catch (RuntimeException error) {
			printMessageError("Erro ao listar as pessoas");
		}

	}

	public void salvar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.merge(usuario);
			usuario = new Usuario();
			usuarios = usuarioDAO.listar();
			
			printMessage("Usuário salvo com sucesso.");
		} catch (Exception e) {
			printMessageError("Erro ao tentar salvar o Usuário");
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);
			usuarios = usuarioDAO.listar("nome");
			printMessage("Usuario removido com sucesso.");
		} catch (Exception e) {
			printMessageError("Erro ao remover o usuario");
		}
	}

	private void printMessage(String message) {
		Messages.addGlobalInfo(message);
	}

	private void printMessageError(String message) {
		Messages.addFlashGlobalError(message);

	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
