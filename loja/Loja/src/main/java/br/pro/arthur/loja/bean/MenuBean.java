package br.pro.arthur.loja.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.pro.arthur.loja.dao.MenuDAO;
import br.pro.arthur.loja.domain.Menu;

@ManagedBean
@SessionScoped
public class MenuBean {
	
	private MenuModel modeloMenu;

	public MenuModel getModeloMenu() {
		return modeloMenu;
	}

	public void setModeloMenu(MenuModel modeloMenu) {
		this.modeloMenu = modeloMenu;
	}
	
	
	public void init(){
		MenuDAO menuDAO = new MenuDAO();
		
		List<Menu> lista = 	menuDAO.listar();
		
		for(Menu menu : lista) {
			if(menu.getCaminho() == null) {
				DefaultSubMenu subMenu = new DefaultSubMenu(menu.getRotulo());
				
				for(Menu item :menu.getMenus()) {
					DefaultMenuItem menuItem = new DefaultMenuItem(item.getRotulo());
					menuItem.setUrl(item.getCaminho());
					
					subMenu.addElement(menuItem);
				}
				modeloMenu.addElement(subMenu);
			}
		
		}
	}
	
	@PostConstruct
	public void iniciar(){
		MenuDAO menuDAO = new MenuDAO();
		List<Menu> lista = menuDAO.listar();
		
		modeloMenu = new DefaultMenuModel();

		for (Menu menu : lista) {
			if (menu.getCaminho() == null) {
				DefaultSubMenu subMenu = new DefaultSubMenu(menu.getRotulo());
				
				for (Menu item : menu.getMenus()){
					DefaultMenuItem menuItem = new DefaultMenuItem(item.getRotulo());
					menuItem.setUrl(item.getCaminho());
					
					subMenu.addElement(menuItem);
				}
				
				modeloMenu.addElement(subMenu);
			}
		}
	}
}
