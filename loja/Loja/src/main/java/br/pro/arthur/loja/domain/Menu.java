package br.pro.arthur.loja.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Menu extends GenericDomain {
	
	private static final long serialVersionUID = 1L;
	
	@Column(length = 50, nullable = false)
	private String rotulo;
	
	@Column(length = 50)
	private String caminho;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "codigo")
	@Fetch(FetchMode.SUBSELECT)
	private List<Menu> menus;

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	

}
