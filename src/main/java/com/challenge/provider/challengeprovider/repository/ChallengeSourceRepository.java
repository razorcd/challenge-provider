package com.challenge.provider.challengeprovider.repository;

import com.challenge.provider.challengeprovider.config.StorageParams;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.utils.FileHelper;
import com.challenge.provider.challengeprovider.utils.JsonStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ChallengeSourceRepository {

    private Path mainStorageFolder;
    private String sourceFilename;
    private FileHelper fileHelper;
    private JsonStringConverter jsonStringConverter;
    private Clock clock;

    /**
     * Repository to create and read challenge sources.
     *
     * @param storageParams the object that defines the parameters for storing the files
     * @param fileHelper the data source that persists the files.
     * @param jsonStringConverter the serializer/deserializer used to save/read files.
     * @param clock the system clock
     */
    @Autowired
    public ChallengeSourceRepository(StorageParams storageParams,
                                     FileHelper fileHelper,
                                     JsonStringConverter jsonStringConverter,
                                     Clock clock) {
        this.mainStorageFolder = Paths.get(storageParams.getMainStorageFolder());
        this.sourceFilename = storageParams.getSourceFilename();
        this.fileHelper = fileHelper;
        this.jsonStringConverter = jsonStringConverter;
        this.clock = clock;

        createMainSourceFolderIfNeeded();
    }

    /**
     * Get a challenge source by it's ID.
     *
     * @param challengeId the id of the challenge source to search by.
     * @return [ChallengeSource] if exists or null
     */
    public ChallengeSource getChallengeSourceById(ChallengeId challengeId) {
        Path projectPath = mainStorageFolder.resolve(challengeId.getId());

        if (!fileHelper.fileExists(projectPath.resolve(sourceFilename))) return null;

        String jsonChallengeSource = new String(fileHelper.readFile(projectPath.resolve(sourceFilename)));

        return jsonStringConverter.convertJsonStringToData(jsonChallengeSource, ChallengeSource.class);
    }

    /**
     * Persist a challenge source. The challenge should have a new ID.
     *
     * @param challengeSource the challenge source to persist
     */
    public void createChallengeSource(ChallengeSource challengeSource) {
        //TODO: ensure ID is unique
        Path projectPath = mainStorageFolder.resolve(challengeSource.getChallengeId().getId());
        fileHelper.createDirectory(projectPath);

        challengeSource.setCreateTimestamp(LocalDateTime.now(clock));
        String jsonChallengeSource = jsonStringConverter.convertDataToJsonString(challengeSource);
        fileHelper.createFile(projectPath.resolve(sourceFilename), jsonChallengeSource.getBytes());
    }

    /**
     * Overwrites a challenge source. The challenge should have an ID.
     *
     * @param challengeSource the challenge source to overwrite
     */
    public void overwriteChallengeSource(ChallengeSource challengeSource) {
        Path projectPath = mainStorageFolder.resolve(challengeSource.getChallengeId().getId());

        ChallengeSource existingChallengeSource = getChallengeSourceById(challengeSource.getChallengeId());

        if (Objects.isNull(existingChallengeSource))
            throw new RuntimeException("Challenge source with id '"+challengeSource.getChallengeId()+"' not found.");

        challengeSource.setCreateTimestamp(existingChallengeSource.getCreateTimestamp());

        String jsonChallengeSource = jsonStringConverter.convertDataToJsonString(challengeSource);

        fileHelper.overwriteFile(projectPath.resolve(sourceFilename), jsonChallengeSource.getBytes());

    }

    private void createMainSourceFolderIfNeeded() {
        fileHelper.createDirectoryIfDoesNotExist(mainStorageFolder);
    }

    /**
     * Get all existing challenge sources.
     *
     * @return [{@Code List<ChallengeSource>}]
     */
    public List<ChallengeSource> getChallengeSourceList() {
        List<String> challengeIds = fileHelper.getAllDirectories(mainStorageFolder);
        return challengeIds.parallelStream()
                .map(id -> getChallengeSourceById(new ChallengeId(id)))
                .collect(Collectors.toList());
    }
}
