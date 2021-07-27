package br.com.candidate.candidateCIandT.candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

    @NotNull @NotEmpty
    private String fullName;
    private String description;
    @NotNull @NotEmpty
    private Level level;
    @NotNull @NotEmpty
    private int proficiency;
    private String socialLinks;

}
