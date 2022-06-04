package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class EmailViewController {

    private final EmailService emailService;

    @GetMapping("email/{id}")
    public String emailView(@PathVariable UUID id,
                            @AuthenticationPrincipal OAuth2User principal,
                            Model model) {
        try {
            model.addAttribute("email", emailService.findById(id));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return "redirect:/";
        }
        return "email-page";
    }
}
