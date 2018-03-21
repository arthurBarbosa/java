package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.HistoricoDAO;
import br.pro.arthur.loja.dao.ProdutoDAO;
import br.pro.arthur.loja.domain.Historico;
import br.pro.arthur.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Boolean exibePainelDeDados;
	private Historico historico;

	
	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public Boolean getExibePainelDeDados() {
		return exibePainelDeDados;
	}

	public void setExibePainelDeDados(Boolean exibePainelDeDados) {
		this.exibePainelDeDados = exibePainelDeDados;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@PostConstruct
	public void init() {
		historico = new Historico();
		produto = new Produto();
		exibePainelDeDados = false;
	}

	public void buscar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());
			if (resultado == null) {
				exibePainelDeDados = false;
				Messages.addGlobalWarn("Não existe produto cadastrado para o código informado");
			} else {
				produto = resultado;
				exibePainelDeDados = true;
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao buscar o produto");
		}
	}
	
	public void salvar() {
		try {
			historico.setHorario(new Date());
			historico.setProduto(produto);
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.salvar(historico);
			
			messageSuccess("Histórico salvo com sucesso");
		}catch(RuntimeException erro) {
			messageError("Erro ao salvar o histórico");
			erro.printStackTrace();
		}
	}
	
	private String messageSuccess(String message) {
		Messages.addGlobalInfo(message);
		return message;
	}
	
	private String messageError(String message) {
		Messages.addGlobalError(message);
		return message;
	}
	
}
