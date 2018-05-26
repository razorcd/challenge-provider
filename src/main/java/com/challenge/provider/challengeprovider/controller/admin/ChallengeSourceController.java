package com.challenge.provider.challengeprovider.controller.admin;

import com.challenge.provider.challengeprovider.domain.Challenge;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.repository.ChallengeSourceRepository;
import com.challenge.provider.challengeprovider.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Challenge source admin controller.
 */
@Controller
@RequestMapping("/admin/challengeSources")
public class ChallengeSourceController {

    private ChallengeSourceRepository challengeSourceRepository;

    private ChallengeService challengeService;

    /**
     * Creates controller to handle challenge sources for administrator.
     *
     * @param challengeService the challenge service
     * @param challengeSourceRepository the challenge source repository
     */
    @Autowired
    public ChallengeSourceController(ChallengeService challengeService,
                                     ChallengeSourceRepository challengeSourceRepository) {
        this.challengeService = challengeService;
        this.challengeSourceRepository = challengeSourceRepository;
    }

    /**
     * Get the list of all challenge sources.
     *
     * @param modelMap spring UI model map
     * @return [ModelAndView]
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getChallengeSourceList(ModelMap modelMap) {
        List<Challenge> challenges = challengeService.getChallengeList();
        return new ModelAndView("challenges", "challenges", challenges)
                .addAllObjects(modelMap);
    }

    /**
     * Get view to create new challenge source.
     *
     * @return [ModelAndView]
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newChallengeSourceView() {
        return new ModelAndView("newChallengeSourceView", "newChallengeSource", new ChallengeSource());
    }

    /**
     * Create new challenge source.
     *
     * @param newChallengeSource the new challenge source object to create
     * @param bindingResult spring's binding result to handle JSR303 validations
     * @param redirectAttributes spring's injected redirect attributes to handle flash messages
     * @param model spring's injected UI model
     * @return [ModelAndView]
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createChallengeSource(@Valid @ModelAttribute("newChallengeSource") ChallengeSource newChallengeSource,
                                              BindingResult bindingResult,
                                              RedirectAttributes redirectAttributes,
                                              ModelMap model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flashMessageError","Failed to create challenge.");
            return new ModelAndView("newChallengeSourceView", "newChallengeSource", newChallengeSource);
        }

        newChallengeSource.setChallengeId(new ChallengeId(UUID.randomUUID().toString()));
        challengeSourceRepository.createChallengeSource(newChallengeSource);

        redirectAttributes.addFlashAttribute("flashMessageSuccess","Challenge successfully created.");
        return new ModelAndView("redirect:/challengeSources/"+newChallengeSource.getChallengeId().getId());
    }

}
