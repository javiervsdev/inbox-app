package dev.javiervs.inboxapp.controllers;

import dev.javiervs.inboxapp.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonController {

    private final FolderService folderService;

    @ModelAttribute
    public void addAttributes(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) return;

        String id = principal.getAttribute("login");
        model.addAttribute("defaultFolders", folderService.fetchDefaultFolders(id));
        model.addAttribute("userFolders", folderService.findAllById(id));
        model.addAttribute("stats", folderService.mapCountToLabels(id));
    }
}
