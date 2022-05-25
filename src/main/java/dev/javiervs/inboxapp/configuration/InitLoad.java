package dev.javiervs.inboxapp.configuration;

import dev.javiervs.inboxapp.folder.Folder;
import dev.javiervs.inboxapp.folder.FolderRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class InitLoad {

    private final FolderRepository folderRepository;

    @PostConstruct
    public void init() {
        folderRepository.saveAll(
                List.of(
                        new Folder("javiervsdev", "Inbox", "blue"),
                        new Folder("javiervsdev", "Sent", "green"),
                        new Folder("javiervsdev", "Important", "yellow")
                )
        );
    }
}
