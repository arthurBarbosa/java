package br.pro.arthur.loja.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.UsuarioDAO;
import br.pro.arthur.loja.domain.Pessoa;
import br.pro.arthur.loja.domain.Usuario;

@SessionScoped
@ManagedBean
public class AutenticacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		UsernamePasswordToken token  = new UsernamePasswordToken();
		
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getEmail(), usuario.getSenha());
			if (usuarioLogado == null) {
				printMessageError("Usuário ou senha estão incorretos");
				return;
			}

			Faces.redirect("./pages/index.xhtml");
		} catch (IOException erro) {
			printMessageError("erro.printStackTrace()");
			erro.printStackTrace();
		}
	}

	public boolean possuiPermissao(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipoUsuario().equals(permissao)) {
				return true;
			}
		}
		return false;
	}

	public boolean temPermissoes(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipoUsuario().equals(permissao)) {
				return true;
			}
		}

		return false;
	}
	
	public void sair() {
		HttpSession autentica = Faces.getSession();
		autentica.invalidate();
		usuarioLogado = new Usuario();
		 Faces.navigate("/pages/autentica.xhtml?faces-redirect=true");
		 
	}
	
	
	/*public void permissao(List<String> permissoes) {
		for(String permissao : permissoes) {
			
			UsernamePasswordToken token  = new UsernamePasswordToken();
			token.setRememberMe(true);
			
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.checkPermissions(permissao);	
		}
		
		
	}*/
	

	private void printMessageError(String message) {
		Messages.addGlobalError(message);

	}

	private void printMessageSucess(String message) {
		Messages.addGlobalInfo(message);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	
}
