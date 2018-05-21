package com.challenge.provider.challengeprovider.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
abstract public class ChallengeBase {

    /**
     * The project id that corresponds to the name of the project folder.
     */
    @NotBlank
    @Size(max = 32)
    String projectId;
}
