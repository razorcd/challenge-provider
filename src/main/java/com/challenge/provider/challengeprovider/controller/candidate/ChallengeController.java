package com.challenge.provider.challengeprovider.controller.candidate;

import com.challenge.provider.challengeprovider.domain.Challenge;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.repository.ChallengeSolutionRepository;
import com.challenge.provider.challengeprovider.repository.ChallengeSourceRepository;
import com.challenge.provider.challengeprovider.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Challenge source and solution canditate controller
 */
@Controller
@RequestMapping("/challengeSources")
public class ChallengeController {

    // TODO: use DTOs
    // TODO: replace repository with service
    private ChallengeSourceRepository challengeSourceRepository;
    private ChallengeSolutionRepository challengeSolutionRepository;

    private ChallengeService challengeService;

    /**
     * Creates controller to handle challenge solutions for candidates.
     * @param challengeService the challenge service
     * @param challengeSourceRepository the challenge source repository
     * @param challengeSolutionRepository the challenge solution repository
     */
    @Autowired
    public ChallengeController(ChallengeService challengeService,
                               ChallengeSourceRepository challengeSourceRepository,
                               ChallengeSolutionRepository challengeSolutionRepository) {
        this.challengeService = challengeService;
        this.challengeSourceRepository = challengeSourceRepository;
        this.challengeSolutionRepository = challengeSolutionRepository;
    }


    /**
     * Get one challenge source by it's id
     *
     * @param id the challenge id to search by
     * @param modelMap spring's injected IO model map
     * @return [ModelAndView]
     */
    @RequestMapping(value = "/{id}")
    public ModelAndView getChallengeSource(@PathVariable String id, ModelMap modelMap) {
        Challenge challenge = challengeService.getChallengeById(new ChallengeId(id));

        return new ModelAndView("challenge", "challenge", challenge)
                .addObject("newChallengeSolution", new ChallengeSolution());
    }

    /**
     * Create a solution to a challenge by it's id.
     *
     * @param id the id of the challenge
     * @param solutionFile the optional multipart solution file
     * @param newChallengeSolution the solution object to create
     * @param bindingResult spring's binding result to handle JSR303 validations
     * @param redirectAttributes spring's injected redirect attributes to handle flash messages
     * @param model spring's injected UI model
     * @return [ModelAndView]
     */
    @RequestMapping(value = "/{id}/solution", method = RequestMethod.POST)
    public ModelAndView createChallengeSolution(@PathVariable String id,
                                                @RequestParam("solutionFile") MultipartFile solutionFile,
                                                @Valid @ModelAttribute("newChallengeSolution") ChallengeSolution newChallengeSolution,
                                                BindingResult bindingResult,
                                                RedirectAttributes redirectAttributes,
                                                ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("flashMessageError","Failed to add challenge solution");
            Challenge challenge = challengeService.getChallengeById(new ChallengeId(id));
            return new ModelAndView("challenge", "newChallengeSolution", newChallengeSolution)
                    .addObject("challenge", challenge);
        }

        newChallengeSolution.setChallengeId(new ChallengeId(id));
        if (solutionFile.isEmpty())
            challengeSolutionRepository.createChallengeSolution(newChallengeSolution);
        else
            challengeSolutionRepository.createChallengeSolution(newChallengeSolution, solutionFile);

        redirectAttributes.addFlashAttribute("flashMessageSuccess","Thank you for uploading your solution");
        return new ModelAndView("redirect:/challengeSources/"+id);
    }

    /**
     * Start timer for a challenge.
     *
     * @param id of the challenge
     * @param redirectAttributes spring's injected redirect attributes to handle flash messages
     * @return [ModelAndView]
     */
    @RequestMapping(value = "/{id}/startChallenge")
    public ModelAndView startChallenge(@PathVariable String id, RedirectAttributes redirectAttributes) {
        challengeService.startChallengeById(new ChallengeId(id));

        redirectAttributes.addFlashAttribute("flashMessageSuccess","Challenge has started. Good luck!");
        return new ModelAndView("redirect:/challengeSources/"+id);
    }

}
