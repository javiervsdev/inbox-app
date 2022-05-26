package dev.javiervs.inboxapp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<Folder> fetchDefaultFolders(String userId) {
        return List.of(
                new Folder(userId, "Inbox", "blue"),
                new Folder(userId, "Sent", "green"),
                new Folder(userId, "Important", "yellow")
        );
    }

    public List<Folder> findAllById(String id) {
        return folderRepository.findAllById(id);
    }
}
