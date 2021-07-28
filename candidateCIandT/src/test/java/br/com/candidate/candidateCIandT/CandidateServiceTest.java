package br.com.candidate.candidateCIandT;

import br.com.candidate.candidateCIandT.candidate.Candidate;
import br.com.candidate.candidateCIandT.candidate.CandidateController;
import br.com.candidate.candidateCIandT.candidate.CandidateRepository;
import br.com.candidate.candidateCIandT.candidate.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static br.com.candidate.candidateCIandT.candidate.Level.Junior;

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

        Mockito.verify(candidateRepository,Mockito.times(1)).save(candidateCaptor.capture());
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

        Mockito.verify(candidateRepository,Mockito.times(2)).save(candidateCaptor.capture());
    }

}

