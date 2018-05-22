package com.challenge.provider.challengeprovider.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity representing the solution of a challenge.
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChallengeSolution extends ChallengeBase {

    /**
     * Create challenge solution with all args.
     *
     * @param challengeId the id of the challenge.
     * @param solversName The name of the person who resolves the challenge.
     * @param solversEmail The email of the person who resolves the challenge.
     * @param description Description added by the person who resolves the challenge.
     * @param uploadedFileName Path of the uploaded file representing project solution.
     */
    public ChallengeSolution(ChallengeId challengeId, String solversName, String solversEmail, String description, String uploadedFileName) {
        this.challengeId = challengeId;
        this.solversName = solversName;
        this.solversEmail = solversEmail;
        this.description = description;
        this.uploadedFileName = uploadedFileName;
    }

    /**
     * The name of the person who resolves the challenge.
     */
    @NotBlank
    @Size(max = 32)
    String solversName;

    /**
     * The email of the person who resolves the challenge.
     */
    @NotBlank
    @Email
    @Size(max = 32)
    String solversEmail;

    /**
     * Description added by the person who resolves the challenge.
     */
    @NotBlank
    @Size(max = 1024000)
    String description;

    /**
     * Path of the uploaded file representing project solution.
     */
    @Size(max = 32)
    String uploadedFileName;
}
