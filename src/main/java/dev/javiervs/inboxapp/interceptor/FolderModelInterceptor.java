package dev.javiervs.inboxapp.interceptor;

import dev.javiervs.inboxapp.folder.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class FolderModelInterceptor implements HandlerInterceptor {
    private final FolderService folderService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        OAuth2AuthenticationToken userPrincipal = (OAuth2AuthenticationToken) request.getUserPrincipal();
        if (modelAndView != null && userPrincipal != null) {
            String id = userPrincipal.getPrincipal().getAttribute("login");
            modelAndView.getModelMap()
                    .addAttribute("defaultFolders", folderService.fetchDefaultFolders(id))
                    .addAttribute("userFolders", folderService.findAllById(id))
                    .addAttribute("stats", folderService.mapCountToLabels(id));
        }
    }
}
