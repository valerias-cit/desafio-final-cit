package br.com.candidate.candidateCIandT.candidate;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/candidate")
@Api(value = "Candidates endpoints")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody @Valid CandidateRequest candidateRequest, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        Candidate candidateToSave = Candidate.builder()
                .fullName(candidateRequest.getFullName())
                .description(candidateRequest.getDescription())
                .level(candidateRequest.getLevel())
                .proficiency(candidateRequest.getProficiency())
                .socialLinks(candidateRequest.getSocialLinks())
                .createdAt(LocalDateTime.now())
                .build();

        try {
            candidateService.saveCustomer(candidateToSave);
            return ResponseEntity.ok().body("Candidato cadastrado com sucesso!");
        } catch (Exception exception) {
            throw exception;
        }
    }
}
