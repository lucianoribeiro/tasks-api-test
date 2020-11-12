package br.lucianopinheiro.tasks.apitest;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

import jdk.jfr.ContentType;

@SuppressWarnings("unused")
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
		.contentType(io.restassured.http.ContentType.JSON)
		
		.when()
			.post("/todo")
		.then().assertThat()	
			.log().all()
			.statusCode(equalTo(201))
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		RestAssured.given()
		.body("{ \"task\": \"Testes via API\",	\"dueDate\": \"2010-12-30\"}")
		.contentType(io.restassured.http.ContentType.JSON)
		.when()
			.post("/todo")
		.then().assertThat()	
			.log().all()
			.statusCode(equalTo(400))
			.body("message",CoreMatchers.is("Due date must not be in past"))
			;
	}


}