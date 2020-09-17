package br.jzp.base;

import io.restassured.http.ContentType;

public interface Constantes {
	
	String APP_BASE_URL = "https://reqres.in";
	Integer APP_PORT = 443;		// http -> 80
	String APP_BASE_PATH = "";
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT = 5000L; 	// 5 SEGUNDOOS DE ESPERA

}