package dev.javiervs.inboxapp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.javiervs.inboxapp.enums.DefaultFolder.*;
import static dev.javiervs.inboxapp.enums.DefaultFolder.IMPORTANT;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<Folder> fetchDefaultFolders(String userId) {
        return List.of(
                new Folder(userId, INBOX.getName(), INBOX.getColor()),
                new Folder(userId, SENT.getName(), SENT.getColor()),
                new Folder(userId, IMPORTANT.getName(), IMPORTANT.getColor())
        );
    }

    public List<Folder> findAllById(String id) {
        return folderRepository.findAllById(id);
    }
}
