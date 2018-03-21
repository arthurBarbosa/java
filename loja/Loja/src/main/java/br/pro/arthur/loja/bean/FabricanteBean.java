package br.pro.arthur.loja.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.pro.arthur.loja.dao.FabricanteDAO;
import br.pro.arthur.loja.domain.Fabricante;

@ManagedBean
@ViewScoped
public class FabricanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fabricante fabricante;

	public List<Fabricante> fabricantes;

	FabricanteDAO dao;

	@PostConstruct
	public void init() {
		/*
		 * FabricanteDAO dao = new FabricanteDAO(); fabricantes = dao.listar();
		 */

		Client cliente = ClientBuilder.newClient();
		WebTarget caminho = cliente.target("http://127.0.0.1:8080/LojaOn/rest/fabricante");
		String json = caminho.request().get(String.class);

		Gson gson = new Gson();
		Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

		fabricantes = Arrays.asList(vetor);
	}

	public void salvar() {
		try {
			/*
			 * FabricanteDAO dao = new FabricanteDAO(); dao.salvar(fabricante);
			 */

			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/LojaOn/rest/fabricante");

			Gson gson = new Gson();

			String json = gson.toJson(fabricante);
			caminho.request().post(Entity.json(json));

			fabricante = new Fabricante();

			json = caminho.request().get(String.class);
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);
			fabricantes = Arrays.asList(vetor);

			// novo();

			// postProcess();

			printMessage("Salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar um fabricante");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

		Client cliente = ClientBuilder.newClient();
		WebTarget caminho = cliente.target("http://127.0.0.1:8080/LojaOn/rest/fabricante");

		WebTarget caminhoExcluir = caminho.path("{codigo}").resolveTemplate("codigo", fabricante.getCodigo());
		Gson gson = new Gson();

		String json = gson.toJson(fabricante);
		caminhoExcluir.request().delete();

		fabricante = new Fabricante();

		json = caminho.request().get(String.class);
		Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

		fabricantes = Arrays.asList(vetor);

		printMessage("Fabricante removido com sucesso.");
	}

	public void editar(ActionEvent evento) {
		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
	}

	private void printMessage(String message) {
		Messages.addGlobalInfo(message);
	}

	public void novo() {
		fabricante = new Fabricante();

	}

	private void postProcess() {
		FabricanteDAO dao = new FabricanteDAO();
		fabricantes = dao.listar();

	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

}
