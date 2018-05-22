package com.challenge.provider.challengeprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Represents and Id value object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeId {

    @NotBlank
    @Size(max = 32)
    private String id;
}
