package com.challenge.provider.challengeprovider.repository;

import com.challenge.provider.challengeprovider.config.StorageParams;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.utils.FileHelper;
import com.challenge.provider.challengeprovider.utils.JsonStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository
public class ChallengeSolutionRepository {

    private Path mainStorageFolder;
    private String solutionFilename;
    private FileHelper fileHelper;
    private JsonStringConverter jsonStringConverter;
    private Clock clock;

    /**
     * Repository to create and read challenge solutions.
     *
     * @param storageParams the object that defines the parameters for storing the files
     * @param fileHelper the datasource that persists the files.
     * @param jsonStringConverter the serializer/deserializer used to save/read files.
     * @param clock the system clock
     */
    @Autowired
    public ChallengeSolutionRepository(StorageParams storageParams,
                                       FileHelper fileHelper,
                                       JsonStringConverter jsonStringConverter,
                                       Clock clock) {
        this.mainStorageFolder = Paths.get(storageParams.getMainStorageFolder());
        this.solutionFilename = storageParams.getSolutionFilename();
        this.fileHelper = fileHelper;
        this.jsonStringConverter = jsonStringConverter;
        this.clock = clock;

        createMainSourceFolderIfNeeded();
    }

    /**
     * Get a challenge source by it's ID.
     *
     * @param challengeId the id of the challenge solution to search by.
     * @return [ChallengeSource] if exists or null
     */
    public ChallengeSolution getChallengeSolutionById(ChallengeId challengeId) {
        Path projectPath = mainStorageFolder.resolve(challengeId.getId());

        if (!fileHelper.fileExists(projectPath.resolve(solutionFilename))) return null;

        String jsonChallengeSolution = new String(fileHelper.readFile(projectPath.resolve(solutionFilename)));

        return jsonStringConverter.convertJsonStringToData(jsonChallengeSolution, ChallengeSolution.class);
    }

    /**
     * Persist a challenge solution without attached file. The solution must not already exist.
     *
     * @param challengeSolution the challenge solution to persist
     */
    public void createChallengeSolution(ChallengeSolution challengeSolution) {
        Path projectPath = mainStorageFolder.resolve(challengeSolution.getChallengeId().getId());
        fileHelper.createDirectoryIfDoesNotExist(projectPath);

        challengeSolution.setCreateTimestamp(LocalDateTime.now(clock));
        String jsonChallengeSolution = jsonStringConverter.convertDataToJsonString(challengeSolution);
        fileHelper.createFile(projectPath.resolve(solutionFilename), jsonChallengeSolution.getBytes());
    }

    /**
     * Persist a challenge solution with attached file. The solution must not already exist.
     *
     * @param challengeSolution the challenge solution to persist
     * @param multipartFile the multipart file to persist
     */
    public void createChallengeSolution(ChallengeSolution challengeSolution, @NonNull MultipartFile multipartFile) {
        Path projectPath = mainStorageFolder.resolve(challengeSolution.getChallengeId().getId());

        challengeSolution.setUploadedFileName(multipartFile.getOriginalFilename());
        createChallengeSolution(challengeSolution);

        fileHelper.copyMultipartFile(multipartFile, projectPath);
    }

    private void createMainSourceFolderIfNeeded() {
        fileHelper.createDirectoryIfDoesNotExist(mainStorageFolder);
    }
}
