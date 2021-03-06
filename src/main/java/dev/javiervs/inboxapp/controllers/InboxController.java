package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.emaillist.EmailListItemService;
import dev.javiervs.inboxapp.enums.DefaultFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class InboxController {

    private final EmailListItemService emailListItemService;

    @GetMapping("/")
    public String homePage(@RequestParam(required = false) String folder,
                           @AuthenticationPrincipal OAuth2User principal,
                           Model model) {
        if (principal == null
                || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }

        String id = principal.getAttribute("login");
        String folderLabel = StringUtils.hasText(folder) ? folder: DefaultFolder.INBOX.getName();
        model.addAttribute("emailList", emailListItemService.findAllById(id, folderLabel));
        model.addAttribute("folderName", folderLabel);

        return "inbox-page";
    }
}
