package com.challenge.provider.challengeprovider.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity representing the source of the challenge that needs to be resolved.
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChallengeSource extends ChallengeBase {

    /**
     * Create source challenge with all args.
     *
     * @param challengeId the id of the challenge.
     * @param projectName the name of the project.
     * @param description description of the challenge of the project.
     * @param expectedSolversName the name of the person who is expected to resolve the challenge.
     * @param expectedSolversEmail the email of the person who is expected to resolve the challenge.
     * @param allowedDurationMin the allowed duration of the challenge in minutes.
     */
    public ChallengeSource(ChallengeId challengeId, String projectName, String description, String expectedSolversName,
                           String expectedSolversEmail, Long allowedDurationMin) {
        this.challengeId = challengeId;
        this.projectName = projectName;
        this.description = description;
        this.expectedSolversName = expectedSolversName;
        this.expectedSolversEmail = expectedSolversEmail;
        this.allowedDurationMin = allowedDurationMin;
    }

    /**
     * The name of the project.
     */
    @NotBlank
    @Size(max = 32)
    private String projectName;

    /**
     * Description of the challenge of the project.
     */
    @NotBlank
    @Size(max = 1024000)
    private String description;

    /**
     * The name of the person who is expected to resolve the challenge.
     */
    @NotBlank
    @Size(max = 32)
    private String expectedSolversName;

    /**
     * The email of the person who is expected to resolve the challenge.
     */
    @NotBlank
    @Email
    @Size(max = 32)
    private String expectedSolversEmail;

    /**
     * The allowed duration of the challenge in minutes.
     */
    @NotNull
    private Long allowedDurationMin;

    /**
     * The datetime this challenge started.
     */
    private LocalDateTime startedTimestamp;

    /**
     * If challenge is started.
     *
     * @return [boolean]
     */
    @JsonIgnore
    public boolean isStarted() {
        return this.startedTimestamp != null;
    }
}
