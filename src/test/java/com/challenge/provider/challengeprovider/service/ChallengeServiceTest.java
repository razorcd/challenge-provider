package com.challenge.provider.challengeprovider.service;

import com.challenge.provider.challengeprovider.domain.Challenge;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.repository.ChallengeSolutionRepository;
import com.challenge.provider.challengeprovider.repository.ChallengeSourceRepository;
import org.hamcrest.collection.IsArrayContaining;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeServiceTest {

    @Mock
    private ChallengeSourceRepository challengeSourceRepositoryMock;

    @Mock
    private ChallengeSolutionRepository challengeSolutionRepositoryMock;

    private Clock clock;
    private ChallengeService challengeService;

    @Before
    public void before() {
        clock = Clock.fixed(Instant.ofEpochMilli(9000), ZoneId.systemDefault());
        challengeService = new ChallengeService(challengeSourceRepositoryMock, challengeSolutionRepositoryMock, clock);
    }

    @Test
    public void getChallengeList() {
        ChallengeSource challengeSource1 = generateChallengeSource("id-1");
        ChallengeSolution challengeSolution1 = generateChallengeSolution("id-1");
        ChallengeSource challengeSource2 = generateChallengeSource("id-2");

        when(challengeSourceRepositoryMock.getChallengeSourceList()).thenReturn(Arrays.asList(challengeSource1, challengeSource2));
        when(challengeSolutionRepositoryMock.getChallengeSolutionById(new ChallengeId("id-1"))).thenReturn(challengeSolution1);
        when(challengeSolutionRepositoryMock.getChallengeSolutionById(new ChallengeId("id-2"))).thenReturn(null);

        List<Challenge> returnedChallenges = challengeService.getChallengeList();

        assertThat("Should contain a challenge and solution.", returnedChallenges.toArray(), IsArrayContaining.hasItemInArray(new Challenge(challengeSource1, challengeSolution1)));
        assertThat("Should contain a challenge without a solution.", returnedChallenges.toArray(), IsArrayContaining.hasItemInArray(new Challenge(challengeSource2, null)));
    }

    @Test
    public void getChallengeByIdWhenSourceExists() {
        ChallengeSource challengeSource1 = generateChallengeSource("id-1");
        ChallengeSolution challengeSolution1 = generateChallengeSolution("id-1");

        when(challengeSourceRepositoryMock.getChallengeSourceById(new ChallengeId("id-1"))).thenReturn(challengeSource1);
        when(challengeSolutionRepositoryMock.getChallengeSolutionById(new ChallengeId("id-1"))).thenReturn(challengeSolution1);

        Challenge returnedChallenge = challengeService.getChallengeById(new ChallengeId("id-1"));

        assertEquals("Should return the challenge with specified id.", returnedChallenge, new Challenge(challengeSource1, challengeSolution1));
    }


    @Test
    public void startChallengeById() {
        ChallengeSource challengeSource = generateChallengeSource("idStartChallenge-1");
        when(challengeSourceRepositoryMock.getChallengeSourceById(new ChallengeId("idStartChallenge-1"))).thenReturn(challengeSource);

        challengeService.startChallengeById(challengeSource.getChallengeId());

        ArgumentCaptor<ChallengeSource> capturedChallengeSource = ArgumentCaptor.forClass(ChallengeSource.class);
        verify(challengeSourceRepositoryMock, times(1)).overwriteChallengeSource(capturedChallengeSource.capture());
        assertEquals("Overwrites with a challenge source that has a start datetime.", LocalDateTime.now(clock), capturedChallengeSource.getValue().getStartedTimestamp());
    }

    @Test(expected = RuntimeException.class)
    public void startChallengeByIdWhenAlreadyStarted() {
        ChallengeSource challengeSource = generateChallengeSource("idStartChallenge-1");
        challengeSource.setStartedTimestamp(LocalDateTime.now(clock));
        when(challengeSourceRepositoryMock.getChallengeSourceById(new ChallengeId("idStartChallenge-1"))).thenReturn(challengeSource);

        challengeService.startChallengeById(challengeSource.getChallengeId());
    }

    private ChallengeSource generateChallengeSource(String challengeId) {
        return new ChallengeSource(
                new ChallengeId(challengeId),
                "Project Name " + new Random().nextInt(),
                "description " + new Random().nextInt(),
                "Solver Name " + new Random().nextInt(),
                "solverName" + new Random().nextInt() + "@example.com",
                9000L
        );
    }


    private ChallengeSolution generateChallengeSolution(String challengeId) {
        return new ChallengeSolution( // TODO: extract to data generator
                new ChallengeId(challengeId),
                "Solver Name " + new Random().nextInt(),
                "solverName" + new Random().nextInt() + "@example.com",
                "comments " + new Random().nextInt(),
                null
        );
    }
}