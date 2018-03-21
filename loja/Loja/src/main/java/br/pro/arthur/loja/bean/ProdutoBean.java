package br.pro.arthur.loja.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.pro.arthur.loja.dao.FabricanteDAO;
import br.pro.arthur.loja.dao.ProdutoDAO;
import br.pro.arthur.loja.domain.Fabricante;
import br.pro.arthur.loja.domain.Produto;
import br.pro.arthur.loja.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@ViewScoped
@ManagedBean
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;

	private List<Produto> produtos;

	private List<Fabricante> fabricantes;

	private StreamedContent foto;

	@PostConstruct
	public void init() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as cidades");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {

			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

		} catch (Exception e) {
			messageError("Ocorreu um erro ao listar os fabricantes.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (produto.getCaminho() == null) {
				message("O campo foto é obrigatório");
				return;
			}
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto produtoRetorno = produtoDAO.merge(produto);

			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("C:/Desenvolvimento/Uploads/" + produtoRetorno.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			produto = new Produto();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			produtos = produtoDAO.listar();
			message("Produto salvo com sucesso!");
		} catch (RuntimeException | IOException error) {
			messageError("Erro ao salvar o Produto.");
			error.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);
			Path arquivo = Paths.get("C:/Desenvolvimento/Uploads/" + produto.getCodigo() + ".png");
			Files.deleteIfExists(arquivo);
			produtos = produtoDAO.listar();
			message("Produto removido com sucesso.");

		} catch (RuntimeException | IOException error) {
			messageError("Erro ao remover o produto.");
			error.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			produto.setCaminho("C:/Desenvolvimento/Uploads/" + produto.getCodigo() + ".png");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

		} catch (RuntimeException error) {
			messageError("Erro ao alterar o produto.");
			error.printStackTrace();
		}
	}

	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			produto.setCaminho(arquivoTemp.toString());

			message("Upload de foto realizado com sucesso.");
		} catch (IOException erro) {
			messageError("Ocorreu um erro ao tentar realizar o upload do arquivo");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			String caminho = Faces.getRealPath("/reports/produtos.jasper");

			Map<String, Object> parametros = new HashMap<>();

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);

		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}

	public void download(ActionEvent evento) {

		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			InputStream stream = new FileInputStream("C:/Desenvolvimento/Uploads/" + produto.getCodigo() + ".png");
			foto = new DefaultStreamedContent(stream, "image/png", produto.getCodigo() + ".png");
		} catch (FileNotFoundException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download da foto");
			e.printStackTrace();
		}

	}

	private void message(String message) {
		Messages.addGlobalInfo(message);
	}

	private void messageError(String message) {
		Messages.addFlashGlobalError(message);

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
