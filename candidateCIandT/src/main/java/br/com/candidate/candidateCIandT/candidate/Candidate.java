package br.com.candidate.candidateCIandT.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = IDENTITY.AUTO)
    private Long id;
    private String fullName;
    private String description;
    private Level level;
    private int proficiency;
    private String socialLinks;
    private LocalDateTime createdAt;
}
