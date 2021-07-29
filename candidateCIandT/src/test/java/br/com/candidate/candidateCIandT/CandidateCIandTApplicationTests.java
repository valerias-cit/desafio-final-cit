package br.com.candidate.candidateCIandT;

import br.com.candidate.candidateCIandT.candidate.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.candidate.candidateCIandT.candidate.Level.Junior;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class CandidateCIandTApplicationTests {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CandidateService candidateService;

	CandidateRequest candidateRequest = new CandidateRequest("Rafaela Candido", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn", Status.A);

	@Test
	void deveSalvarUmCandidato() throws Exception {
		CandidateRequest candidateRequest = new CandidateRequest("Rafaela Candido", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn", Status.A);
		String json = geradorJson(candidateRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/candidate")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(json))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString().equals("Candidato cadastrado com sucesso!");

	}

	@Test
	void naoDeveSalvarCandidatoComNomeVazio() throws Exception {
		CandidateRequest candidateRequest = new CandidateRequest("", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn", Status.A);
		String json = geradorJson(candidateRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/candidate")
				.contentType(MediaType.APPLICATION_JSON)
						.header("Accept-Language", "pt-BR")
				.characterEncoding("UTF-8")
				.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$[0].defaultMessage").value("não deve estar vazio"))
				.andExpect(jsonPath("$[0].objectName").value("candidateRequest"))
				.andExpect(jsonPath("$[0].field").value("fullName"));
	}

	@Test
	void listarCandidatosAtivos() throws Exception {
		//Dado que exista uma lista de candidatos
		List<Candidate> listaEsperada = Lists.list(Candidate.builder()
						.id(100L)
						.fullName("Mario")
						.description("software engineer")
						.level(Junior)
						.proficiency(80)
						.socialLinks("mario.linkedIn.com")
						.createdAt(LocalDateTime.now())
						.status(Status.A)
						.build(),
				Candidate.builder()
						.id(200L)
						.fullName("João")
						.description("architect")
						.level(Junior)
						.proficiency(90)
						.socialLinks("joao.linkedIn.com")
						.createdAt(LocalDateTime.now())
						.status(Status.A)
						.build());

		//E o service.findByStatus retorne esta lista
		Mockito.when(candidateService.findByStatus(Mockito.any())).thenReturn(listaEsperada);

		//Quando o controller.findCandidates for chamado
		mockMvc.perform(MockMvcRequestBuilders.get("/candidate")
						.header("Accept-Language", "pt-BR")
						.characterEncoding("UTF-8"))
						.andExpect(status().isOk()) // então a resposta deve ser 200ok
						.andExpect(jsonPath("$").value(hasSize(2))) // e a lista deve ter 2 items
						.andExpect(jsonPath("$[0].fullName").value("Mario")) //e o primeiro item deve ter o nome Mario
						.andExpect(jsonPath("$[1].fullName").value("João")); //e o segundo item deve ter o nome João
	}

	@Test
	void listaVazia() throws Exception {
		//Dado que exista uma lista vazia de candidatos
		List<Candidate> listaEsperada = Lists.emptyList();

		//E o service.findByStatus retorne esta lista
		Mockito.when(candidateService.findByStatus(Mockito.any())).thenReturn(listaEsperada);

		//Quando o controller.findCandidates for chamado
		mockMvc.perform(MockMvcRequestBuilders.get("/candidate")
						.header("Accept-Language", "pt-BR")
						.characterEncoding("UTF-8"))
						.andExpect(status().isOk()) // então a resposta deve ser 200ok
						.andExpect(jsonPath("$").value(hasSize(0))); //e a lista deve estar vazia
	}

	@Test
	void deveExluirCandidato() throws Exception {

		candidateRepository.save(Candidate.builder()
				.fullName("Mario")
				.description("tafsdakh")
				.level(Junior)
				.proficiency(80)
				.socialLinks("dhfbczdlk")
				.createdAt(LocalDateTime.now())
				.build());

		mockMvc.perform(MockMvcRequestBuilders.delete("/candidate/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk());

	}

	@Test

	void deveRetornarErroQuandoNaoEncontrarOCandidatoCadastrado() throws Exception {
		candidateRepository.deleteAll();
		mockMvc.perform(MockMvcRequestBuilders.delete("/candidate/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString().equals("Candidato não encontrado");
	}


	public String geradorJson(CandidateRequest candidateRequest) throws JsonProcessingException {
		return objectMapper.writeValueAsString(candidateRequest);
	}

}
