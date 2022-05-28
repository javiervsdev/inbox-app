package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ComposeController {

    private final FolderService folderService;

    @GetMapping("/compose")
    public String getComposePage(@RequestParam(required = false) String to,
                                 @AuthenticationPrincipal OAuth2User principal,
                                 Model model) {
        String userId = principal.getAttribute("login");
        model.addAttribute("defaultFolders",
                folderService.fetchDefaultFolders(userId));
        model.addAttribute("userFolders",
                folderService.findAllById(userId));

        if (StringUtils.hasText(to)) {
            String uniqueIds = Arrays.stream(to.split(","))
                    .map(StringUtils::trimAllWhitespace)
                    .filter(StringUtils::hasText)
                    .distinct()
                    .collect(Collectors.joining(", "));
            model.addAttribute("toIds",
                    String.join(", ", uniqueIds));
        }

        return "compose-page";
    }

    @PostMapping
    public String post() {
        return null;
    }
}
