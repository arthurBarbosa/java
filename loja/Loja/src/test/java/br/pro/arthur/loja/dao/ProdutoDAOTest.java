package br.pro.arthur.loja.dao;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

import br.pro.arthur.loja.dao.FabricanteDAO;
import br.pro.arthur.loja.dao.ProdutoDAO;
import br.pro.arthur.loja.domain.Fabricante;
import br.pro.arthur.loja.domain.Produto;

public class ProdutoDAOTest {
	@Test
	@Ignore
	public void salvar(){
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(new Long("3"));
		
		Produto produto = new Produto();
		produto.setDescricao("Cataflan 50mg com 20 Comprimidos");
		produto.setFabricante(fabricante);
		produto.setPreco(new BigDecimal("13.70"));
		produto.setQuantidade(new Short("7"));
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.salvar(produto);
		
		System.out.println("Produto salvo com sucesso");
	}
}
