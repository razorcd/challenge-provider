package com.challenge.provider.challengeprovider.service;

import com.challenge.provider.challengeprovider.domain.Challenge;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.repository.ChallengeSolutionRepository;
import com.challenge.provider.challengeprovider.repository.ChallengeSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private ChallengeSourceRepository challengeSourceRepository;
    private ChallengeSolutionRepository challengeSolutionRepository;
    private Clock clock;

    @Autowired
    public ChallengeService(ChallengeSourceRepository challengeSourceRepository,
                            ChallengeSolutionRepository challengeSolutionRepository,
                            Clock clock) {
        this.challengeSourceRepository = challengeSourceRepository;
        this.challengeSolutionRepository = challengeSolutionRepository;
        this.clock = clock;
    }

    /**
     * Get all challenges.
     *
     * @return [{@code List<Challenge>}]
     */
    public List<Challenge> getChallengeList() {
        List<ChallengeSource> challengeSources = challengeSourceRepository.getChallengeSourceList();
        return challengeSources
                .parallelStream().map((challengeSource) -> {
                    ChallengeSolution challengeSolution = challengeSolutionRepository.getChallengeSolutionById(challengeSource.getChallengeId()); //TODO: eager load
                  return new Challenge(challengeSource, challengeSolution);
                }).collect(Collectors.toList());
    }

    /**
     * Get a challenge by it's id.
     *
     * @param id the id of the challenge
     * @return [Challenge] or null
     */
    public Challenge getChallengeById(ChallengeId id) {
        ChallengeSource challengeSource = challengeSourceRepository.getChallengeSourceById(id);
        if (Objects.isNull(challengeSource)) return null;

        ChallengeSolution challengeSolution = challengeSolutionRepository.getChallengeSolutionById(id);

        return new Challenge(challengeSource, challengeSolution);
    }

    /**
     * Start challenge by id.
     *
     * @param id the id of the challenge.
     */
    public void startChallengeById(ChallengeId id) {
       ChallengeSource challengeSource = challengeSourceRepository.getChallengeSourceById(id);
       if (challengeSource.getStartedTimestamp() != null) throw new RuntimeException("Can not start challenge that was already started.");
       challengeSource.setStartedTimestamp(LocalDateTime.now(clock));
       challengeSourceRepository.overwriteChallengeSource(challengeSource);
    }
}
