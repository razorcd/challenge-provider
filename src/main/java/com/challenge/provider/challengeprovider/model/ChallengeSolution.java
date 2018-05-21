package com.challenge.provider.challengeprovider.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity representing the solution of a challenge.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChallengeSolution extends ChallengeBase {

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
     * Commends added by the person who resolves the challenge.
     */
    @Max(1024000)
    String solversComments;

    /**
     * Path of the uploaded file representing project solution.
     */
    @Size(max = 32)
    String uploadedFileName;
}
