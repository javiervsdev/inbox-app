package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.email.Email;
import dev.javiervs.inboxapp.email.EmailService;
import dev.javiervs.inboxapp.emaillist.EmailListItemKey;
import dev.javiervs.inboxapp.emaillist.EmailListItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class EmailViewController {

    private final EmailService emailService;
    private final EmailListItemService emailListItemService;

    @GetMapping("email/{id}")
    public String emailView(@PathVariable UUID id,
                            @RequestParam String folder,
                            @AuthenticationPrincipal OAuth2User principal,
                            Model model) {
        Optional<Email> optionalEmail = emailService.findById(id);

        if (optionalEmail
                .filter(email -> !email.hasUserAccess(principal.getAttribute("login")))
                .isPresent()) {
            return "redirect:/";
        }

        model.addAttribute("email", optionalEmail.get());
        EmailListItemKey key = new EmailListItemKey(
                principal.getAttribute("login"),
                folder, id);
        emailListItemService.read(key);

        return "email-page";
    }
}
