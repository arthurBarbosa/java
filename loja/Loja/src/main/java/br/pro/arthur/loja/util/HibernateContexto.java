package br.pro.arthur.loja.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContexto implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.getFabricaDeSessoes().close();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.getFabricaDeSessoes().openSession();
	}

}
