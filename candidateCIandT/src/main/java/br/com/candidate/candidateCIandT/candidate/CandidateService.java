package br.com.candidate.candidateCIandT.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void saveCustomer(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public List<Candidate> findByStatus(Status status) {
        return candidateRepository.findByStatus(status);
    }
}
