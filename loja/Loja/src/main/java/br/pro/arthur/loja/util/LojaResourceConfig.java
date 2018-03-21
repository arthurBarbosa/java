package br.pro.arthur.loja.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class LojaResourceConfig extends ResourceConfig{

	public LojaResourceConfig() {
		packages("br.pro.arthur.service");
	}
}
