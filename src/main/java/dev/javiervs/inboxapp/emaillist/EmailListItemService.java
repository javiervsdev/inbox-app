package dev.javiervs.inboxapp.emaillist;

import dev.javiervs.inboxapp.folder.UnreadEmailStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailListItemService {

    private final EmailListItemRepository emailListItemRepository;
    private final UnreadEmailStatsRepository unreadEmailStatsRepository;

    public List<EmailListItem> findAllById(String id, String label) {
        return emailListItemRepository.findAllByKey_IdAndKey_Label(id, label);
    }

    public void read(EmailListItemKey key) {
        emailListItemRepository.findById(key)
                .ifPresent(emailListItem -> {
                    if (emailListItem.isUnread()) {
                        emailListItem.setUnread(false);
                        emailListItemRepository.save(emailListItem);
                        unreadEmailStatsRepository.decrementUnreadCount(key.getId(), key.getLabel());
                    }
                });
    }
}
