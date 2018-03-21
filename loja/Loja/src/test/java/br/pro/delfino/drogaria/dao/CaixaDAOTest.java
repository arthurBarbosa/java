package br.pro.delfino.drogaria.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.pro.arthur.loja.dao.CaixaDAO;
import br.pro.arthur.loja.domain.Caixa;

public class CaixaDAOTest {
	@Test
	@Ignore
	public void salvar() throws ParseException {
		Caixa caixa = new Caixa();
		caixa.setDataDeAbertura(new SimpleDateFormat("dd/MM/yyyy").parse("14/01/2018"));
		caixa.setValorAbertura(new Double(100.00));

		CaixaDAO caixaDAO = new CaixaDAO();
		caixaDAO.salvar(caixa);
	}

	@Test
	public void buscar() throws ParseException {
		CaixaDAO caixaDAO = new CaixaDAO();
		Caixa caixa = caixaDAO.buscar(new SimpleDateFormat("dd/MM/yyyy").parse("1/01/2018"));
		
		System.out.println(caixa);
		
		Assert.assertNull(caixa);
	}
}
