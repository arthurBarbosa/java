package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.arthur.loja.dao.ClienteDAO;
import br.pro.arthur.loja.dao.FuncionarioDAO;
import br.pro.arthur.loja.dao.ProdutoDAO;
import br.pro.arthur.loja.dao.VendaDAO;
import br.pro.arthur.loja.domain.Cliente;
import br.pro.arthur.loja.domain.Funcionario;
import br.pro.arthur.loja.domain.ItemVenda;
import br.pro.arthur.loja.domain.Produto;
import br.pro.arthur.loja.domain.Venda;

@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Venda venda;
	private List<Produto> produtos;
	private List<ItemVenda> itensVendas;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Venda> vendas;

	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensVendas = new ArrayList<>();

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			e.printStackTrace();
		}
	}

	public void listar() {
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listar("horario");
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVendas.size(); posicao++) {
			if (itensVendas.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}
		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVendas.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVendas.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}

		calcular();

	}

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVendas.size(); posicao++) {
			if (itensVendas.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			itensVendas.remove(achou);
		}

		calcular();

	}

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));
		for (int posicao = 0; posicao < itensVendas.size(); posicao++) {
			ItemVenda itemVenda = itensVendas.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}

	}

	public void finalizar() {
		try {
			venda.setHorario(new Date());

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar finalizar a venda");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getPrecoTotal().signum() == 0) {
				Messages.addGlobalError("Adicione ao menos um item na cesta de compras.");
			}
			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.salvar(venda, itensVendas);

			novo();

			printMessage("Venda salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar a venda.");
		}
	}
	
	public void atualizarPrecoParcial() {
		for(ItemVenda itemVenda : itensVendas) {
			itemVenda.setPrecoParcial(itemVenda.getProduto().getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		this.calcular();
	}

	private String printMessage(String message) {
		Messages.addGlobalInfo(message);
		return message;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVendas() {
		return itensVendas;
	}

	public void setItensVendas(List<ItemVenda> itensVendas) {
		this.itensVendas = itensVendas;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

}
