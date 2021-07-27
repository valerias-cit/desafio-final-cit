package br.com.candidate.candidateCIandT.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    public void saveCustomer(Candidate candidate) {
        this.candidateRepository.save(candidate);
    }
}
