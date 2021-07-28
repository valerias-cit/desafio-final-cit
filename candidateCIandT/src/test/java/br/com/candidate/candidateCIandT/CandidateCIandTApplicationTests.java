package br.com.candidate.candidateCIandT;

import br.com.candidate.candidateCIandT.candidate.CandidateController;
import br.com.candidate.candidateCIandT.candidate.CandidateRequest;
import br.com.candidate.candidateCIandT.candidate.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class CandidateCIandTApplicationTests {

	@InjectMocks
	private CandidateController candidateController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	CandidateRequest candidateRequest = new CandidateRequest("Rafaela Candido", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn");

	@Test
	void deveSalvarUmCandidato() throws Exception {
		CandidateRequest candidateRequest = new CandidateRequest("Rafaela Candido", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn");
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
		CandidateRequest candidateRequest = new CandidateRequest("", "software engineer", Level.Junior, 80, "rafaelacandido.linkedIn");
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

	public String geradorJson(CandidateRequest candidateRequest) throws JsonProcessingException {
		return objectMapper.writeValueAsString(candidateRequest);
	}
}
