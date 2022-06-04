package dev.javiervs.inboxapp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.javiervs.inboxapp.enums.DefaultFolder.*;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final UnreadEmailStatsRepository unreadEmailStatsRepository;

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

    public Map<String, Integer> mapCountToLabels(String id) {
        List<UnreadEmailStats> stats = unreadEmailStatsRepository.findAllById(id);
        return stats.stream()
                .collect(Collectors.toMap(
                        UnreadEmailStats::getLabel,
                        UnreadEmailStats::getUnreadCount));
    }
}
