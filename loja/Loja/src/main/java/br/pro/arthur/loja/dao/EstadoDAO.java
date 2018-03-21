package br.pro.arthur.loja.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.pro.arthur.loja.domain.Cidade;
import br.pro.arthur.loja.domain.Estado;
import br.pro.arthur.loja.util.HibernateUtil;

public class EstadoDAO extends GenericDAO<Estado> {

	public List<Estado> buscarPorCidade(Long cidadeCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("cidade.codigo", cidadeCodigo));
			consulta.addOrder(Order.asc("nome"));
			List<Estado> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
