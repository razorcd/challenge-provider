package com.challenge.provider.challengeprovider.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorageParams {
    private String mainStorageFolder;
    private String sourceFilename;
    private String solutionFilename;
}
