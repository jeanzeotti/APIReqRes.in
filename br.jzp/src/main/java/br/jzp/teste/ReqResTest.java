package br.jzp.teste;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.jzp.base.BaseTest;


public class ReqResTest extends BaseTest {
	
	private Usuario getNameJob() {
		Usuario usuario = new Usuario();
		usuario.setName("Jean");
		usuario.setJob("Teste");
		return usuario;
	}
	
	private Usuario getEmail() {
		Usuario usuario = new Usuario();
		usuario.setEmail("eve.holt@reqres.in");
		return usuario;
	}
	
	private Usuario getEmailPassword() {
		Usuario usuario = getEmail();
		usuario.setPassword("cityslicka");
		return usuario;
	}
	
	@Test
	public void obterUsuariosDaLista() {
		given()
		.when()
			.get("/api/users?page=2")
		.then()
			.statusCode(200)
			.body("page", is(2))
			.body("per_page", is(6))
			.body("total_pages", is(2))
			.body("data.id[0]", is(7))
			.body("data.id", hasSize(6))
		;
	}

	@Test
	public void obterUmUsuario() {
		given()
		.when()
			.get("/api/users/2")
		.then()
			.statusCode(200)
			.body("data.id", is(2))
			.body("data.email", is("janet.weaver@reqres.in"))
			.body("data.first_name", is("Janet"))
			.body("data.last_name", is("Weaver"))
		;
	}
	
	@Test
	public void naoDeveListarNenhumUsuario() {
		String resposta = given()
		.when()
			.get("/api/users/23")
		.then()
			.statusCode(404)
			.extract().asString();
		;
		assertEquals("{}", resposta);
	}
	
	@Test
	public void criarUsuario() {
		Usuario usuario = getNameJob();
//		Map<String, String> usuario = new HashMap<>();
//		usuario.put("name", "Jean");
//		usuario.put("job", "Teste");
		
		given()
			.body(usuario)
			
			
		.when()
			.post("/api/users")
		.then()
		.log().all()
			.statusCode(201)
			.body("name", is(usuario.getName()))
			.body("job", is(usuario.getJob()))
		;
		
		System.out.println("------------------" + usuario);

	}
	
	@Test
	public void atualizarUmUsuario() {
		Usuario usuario = getNameJob();
		
		given()
			.body(usuario)
		.when()
			.put("/api/users/2")
		.then()
			.statusCode(200)
			.body("name", is( "Jean"))
			.body("job", is("Teste"))
		;
	}
	
	@Test
	public void deletarUmUsuario() {
		given()
		.when()
			.delete("/api/users/2")
		.then()
			.statusCode(204)
			
		;
	}
	
	@Test
	public void registroSemSucesso() {
		Usuario email = getEmail();
		
		given()
			.body(email)
		.when()
			.post("/api/register")
		.then()
			.statusCode(400)
			.body("error", is("Missing password"))
		;
	}
	
	@Test
	public void loginComSucesso() {
		Usuario usuario = getEmailPassword();
		
		given()
		.log().all()
			.body(usuario)
		.when()
			.post("/api/login")
		.then()
		.log().all()
			.statusCode(200)
			.body("token", is("QpwL5tke4Pnpja7X4"))
		;
	}
	
	@Test
	public void loginSemSucesso() {
		Usuario usuario = getEmail();
		
		given()
			.body(usuario)
		.when()
			.post("/api/login")
		.then()
		.log().all()
			.statusCode(400)
			.body("error", is("Missing password"))
		;
	}
	
	@Test
	public void obterUsuariosDaListaComDelay3Segundos() {
		given()
		.when()
			.get("/api/users?delay=3")
		.then()
			.statusCode(200)
			.body("page", is(1))
			.body("per_page", is(6))
			.body("total", is(12))
			.body("total_pages", is(2))
			.body("data.id[0]", is(1))
			.body("data.id", hasSize(6))
		;
	}
}
