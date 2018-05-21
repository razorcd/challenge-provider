package com.challenge.provider.challengeprovider.controller;

import com.challenge.provider.challengeprovider.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Create new project.
     *
     * @param file project file.
     * @param description project description.
     * @return [String] redirect path
     */
    @PostMapping
    public String createProject(@RequestParam(value = "projectFile", required = false) MultipartFile file,
                                @RequestParam(value = "description", required = true) String description,
                                RedirectAttributes redirectAttributes) {

        projectService.createProject(file, description);

        redirectAttributes.addFlashAttribute("success_message","ChallengeSource successfully created.");

        return "redirect:/projects";
    }
}
