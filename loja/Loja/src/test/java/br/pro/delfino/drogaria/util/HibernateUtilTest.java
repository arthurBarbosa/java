package br.pro.delfino.drogaria.util;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import br.pro.arthur.loja.util.HibernateUtil;

public class HibernateUtilTest {
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
