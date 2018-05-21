package com.challenge.provider.challengeprovider.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity representing the source of the challenge that needs to be resolved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChallengeSource extends ChallengeBase {

    /**
     * The project id that corresponds to the name of the project folder.
     */
    @NotBlank
    @Size(max = 32)
    String projectId;

    /**
     * The name of the project.
     */
    @NotBlank
    @Size(max = 32)
    String projectName;

    /**
     * Description of the challenge of the project.
     */
    @NotBlank
    @Max(1024000)
    String description;

    /**
     * The name of the person who is expected to resolve the challenge.
     */
    @NotBlank
    @Size(max = 32)
    String expectedSolversName;

    /**
     * The email of the person who is expected to resolve the challenge.
     */
    @NotBlank
    @Email
    @Size(max = 32)
    String expectedSolversEmail;
}
