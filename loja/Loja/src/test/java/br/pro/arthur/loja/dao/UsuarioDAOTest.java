package br.pro.arthur.loja.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.pro.arthur.enumeracao.TipoUsuario;
import br.pro.arthur.loja.dao.PessoaDAO;
import br.pro.arthur.loja.dao.UsuarioDAO;
import br.pro.arthur.loja.domain.Pessoa;
import br.pro.arthur.loja.domain.Usuario;

public class UsuarioDAOTest {
	
	@Test
	
	public void salvar(){
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(3L);
		
		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());
		
		
		
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("qwe");
		
		SimpleHash hash = new SimpleHash("md5",usuario.getSenhaSemCriptografia());
		usuario.setSenha(hash.toHex());
		
		usuario.setTipoUsuario(TipoUsuario.BALCONISTA);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		System.out.println("Usuário salvo com sucesso.");
	}
	
	@Test
	@Ignore
	public void autenticar() {
		String email = "diva@gmail.com";
		String senha = "q1w2e3r4";
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.autenticar(email, senha);
		
		System.out.println("usuario autenticado" + usuario);
	}
}	
