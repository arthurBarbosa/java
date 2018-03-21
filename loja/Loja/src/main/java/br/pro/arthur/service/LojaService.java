package br.pro.arthur.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
//http:localhost:8080/Loja/rest/loja

@Path("loja")
public class LojaService {
	
	@GET
	public String exibir() {
		return "Curso de java";
	}
}
