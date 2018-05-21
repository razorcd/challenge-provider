package com.challenge.provider.challengeprovider.controller;

import com.challenge.provider.challengeprovider.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    @Qualifier("temp-folder")
    private Path tempFolder;

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public String createProject(@RequestParam(value = "projectFile", required = false) MultipartFile file,
                                @RequestParam(value = "description", required = true) String description,
                                RedirectAttributes redirectAttributes) {
        Path uploadedFile = moveUploadToTempFolder(file);

        projectService.createProject(uploadedFile, description);

        redirectAttributes.addFlashAttribute("success_message","Project successfully created.");

        return "redirect:/projects";
    }

    private Path moveUploadToTempFolder(MultipartFile file) {
        try {
            Path newTempFile = Files.createTempFile(tempFolder, null, null);
            file.transferTo(newTempFile.toFile());
            return newTempFile;
        } catch(IOException e) {
            throw new RuntimeException("IO error during moving uploaded file to temp folder. " + e.getMessage());
        }
    }
}
