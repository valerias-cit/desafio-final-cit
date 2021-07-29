package br.com.candidate.candidateCIandT.candidate;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/candidate")
@Api(value = "Candidates endpoints")
public class CandidateController {

    private final CandidateService candidateService;
    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateController(CandidateService candidateService, CandidateRepository candidateRepository) {
        this.candidateService = candidateService;
        this.candidateRepository = candidateRepository;
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
                .status(candidateRequest.getStatus())
                .build();

        try {
            candidateService.saveCustomer(candidateToSave);
            return ResponseEntity.ok().body("Candidato cadastrado com sucesso!");
        } catch (Exception exception) {
            throw exception;
        }
    }

    @GetMapping
    public List<Candidate> findCandidates() {
        return candidateService.findByStatus(Status.A);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCandidate(@PathVariable Long id) {

        if (candidateService.deleteCandidate(id)){
            return ResponseEntity.ok().build();
        } else
        return new ResponseEntity<>("Candidato não encontrado", HttpStatus.NOT_FOUND);
    }
}
