package br.lucianoribeiro.tasks.apitest;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
	RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")	
		.then().assertThat()
			.log().all()
			.statusCode(equalTo(200))
		;
		
		}
	@Test
	public void deveAdicionarTarefacomSucesso() {
		
		RestAssured.given()
		.body("{ \"task\": \"Testes via API\",	\"dueDate\": \"2020-12-30\"}")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then().assertThat()	
			.log().all()
			.statusCode(equalTo(201))
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvlida() {
		
		RestAssured.given()
		.body("{ \"task\": \"Testes via API\",	\"dueDate\": \"2010-12-30\"}")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then().assertThat()	
			.log().all()
			.statusCode(equalTo(400))
			.body("message",CoreMatchers.is("Due date must not be in past"))
			;
	}


}
