package com.challenge.provider.challengeprovider.domain;

import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {

    /**
     * The source details of the challenge.
     */
    private ChallengeSource challengeSource;

    /**
     * The solution of the challenge.
     */
    private ChallengeSolution challengeSolution;

    /**
     * If current challenged is resolved.
     * @return [boolean] if resolved
     */
    public boolean hasSolution() {
        return !Objects.isNull(challengeSolution);
    }
}
