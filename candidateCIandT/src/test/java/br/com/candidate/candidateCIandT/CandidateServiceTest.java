package br.com.candidate.candidateCIandT;

import br.com.candidate.candidateCIandT.candidate.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.LifecycleState;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.candidate.candidateCIandT.candidate.Level.Junior;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    public void deveSalvarOCandidato() {
        Candidate candidateToSave = Candidate.builder()
                .fullName("Mario")
                .description("tafsdakh")
                .level(Junior)
                .proficiency(80)
                .socialLinks("dhfbczdlk")
                .createdAt(LocalDateTime.now())
                .build();

        final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);

        candidateService.saveCustomer(candidateToSave);

        Mockito.verify(candidateRepository, Mockito.times(1)).save(candidateCaptor.capture());
    }

    @Test
    public void naoVaiSalvarOCandidato() {
        Candidate candidateToSave = Candidate.builder()
                .fullName("Mario")
                .description("tafsdakh")
                .level(Junior)
                .proficiency(80)
                .socialLinks("dhfbczdlk")
                .createdAt(LocalDateTime.now())
                .build();

        final ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);

        candidateService.saveCustomer(candidateToSave);

        Mockito.verify(candidateRepository, Mockito.times(1)).save(candidateCaptor.capture());
    }

    @Test
    public void listaDeCandidatos() {
        //Dado que exista uma lista de candidatos
        List<Candidate> listaEsperada = Lists.list(Candidate.builder()
                        .fullName("Mario")
                        .description("software engineer")
                        .level(Junior)
                        .proficiency(80)
                        .socialLinks("mario.linkedIn.com")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Candidate.builder()
                        .fullName("João")
                        .description("architect")
                        .level(Junior)
                        .proficiency(90)
                        .socialLinks("joao.linkedIn.com")
                        .createdAt(LocalDateTime.now())
                        .build());

        //E o repository.findByStatus retorne esta lista
        Mockito.when(candidateRepository.findByStatus(Mockito.any())).thenReturn(listaEsperada);

        //Quando o service.findByStatus for chamado
        List<Candidate> result = candidateService.findByStatus(Status.A);

        //Então a lista de retorno não deve estar vazia
        assertEquals(2, result.size());
    }

    @Test
    public void listaVaziaDeCandidatos() {
        //Dado que exista uma lista vazia de candidatos
        List<Candidate> listaEsperada = Lists.emptyList();

        //E o repository.findByStatus retorne esta lista
        Mockito.when(candidateRepository.findByStatus(Mockito.any())).thenReturn(listaEsperada);

        //Quando o service.findByStatus for chamado
        List<Candidate> result = candidateService.findByStatus(Status.A);

        //Então a lista de retorno deve estar vazia
        assertEquals(0, result.size());
    }
}

