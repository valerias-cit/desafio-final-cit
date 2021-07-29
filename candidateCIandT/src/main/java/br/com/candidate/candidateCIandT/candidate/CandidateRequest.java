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
    @NotNull
    private Level level;
    @NotNull
    private Integer proficiency;
    private String socialLinks;
    @NotNull
    private Status status;

}
