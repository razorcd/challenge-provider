package com.challenge.provider.challengeprovider.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor  // to make lombok happy
abstract public class ChallengeBase {

    /**
     * The project id that corresponds to the name of the project folder.
     */
    ChallengeId challengeId;

    /**
     * The datetime when the resource was created.
     */
    LocalDateTime createTimestamp;
}
