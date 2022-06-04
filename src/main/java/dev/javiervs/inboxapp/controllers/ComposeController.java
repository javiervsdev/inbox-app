package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.email.EmailDto;
import dev.javiervs.inboxapp.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static dev.javiervs.inboxapp.email.EmailUtils.getUniqueIdsStream;
import static java.util.stream.Collectors.joining;

@Controller
@RequiredArgsConstructor
public class ComposeController {

    private final EmailService emailService;

    @GetMapping("/compose")
    public String getComposePage(@RequestParam(required = false) String to, Model model) {
        model.addAttribute("emailDto",
                EmailDto.builder()
                        .to(getUniqueIdsStream(to)
                                .collect(joining(", ")))
                        .build());

        return "compose-page";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@ModelAttribute EmailDto emailDto,
                            @AuthenticationPrincipal OAuth2User principal) {
        emailDto.setFrom(principal.getAttribute("login"));
        emailService.sendEmail(emailDto);
        return "redirect:/";
    }
}
