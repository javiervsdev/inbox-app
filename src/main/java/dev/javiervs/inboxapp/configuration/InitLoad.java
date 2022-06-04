package dev.javiervs.inboxapp.configuration;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import dev.javiervs.inboxapp.email.Email;
import dev.javiervs.inboxapp.email.EmailRepository;
import dev.javiervs.inboxapp.emaillist.EmailListItem;
import dev.javiervs.inboxapp.emaillist.EmailListItemKey;
import dev.javiervs.inboxapp.emaillist.EmailListItemRepository;
import dev.javiervs.inboxapp.folder.Folder;
import dev.javiervs.inboxapp.folder.FolderRepository;
import dev.javiervs.inboxapp.folder.UnreadEmailStatsRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static dev.javiervs.inboxapp.enums.DefaultFolder.*;

@Component
@AllArgsConstructor
public class InitLoad {

    private final FolderRepository folderRepository;
    private final EmailListItemRepository emailListItemRepository;
    private final EmailRepository emailRepository;

    private final UnreadEmailStatsRepository unreadEmailStatsRepository;

    public static final String DEFAULT_USER_ID = "javiervsdev";

    @PostConstruct
    public void init() {
        folderRepository.saveAll(
                List.of(
                        new Folder(DEFAULT_USER_ID, INBOX.getName(), INBOX.getColor()),
                        new Folder(DEFAULT_USER_ID, SENT.getName(), SENT.getColor()),
                        new Folder(DEFAULT_USER_ID, IMPORTANT.getName(), IMPORTANT.getColor())
                )
        );
        Stream.iterate(0, i -> i + 1).limit(10)
                .forEach(i -> {
                            EmailListItem emailListItem = createEmailListItem(i);
                            Email email = createEmail(emailListItem);
                            emailListItemRepository.save(emailListItem);
                            unreadEmailStatsRepository.incrementUnreadCount(DEFAULT_USER_ID,
                                    emailListItem.getFolderLabel());
                            emailRepository.save(email);
                        }
                );
    }

    private EmailListItem createEmailListItem(Integer i) {
        return EmailListItem.builder()
                .key(getDefaultEmailListItemKey())
                .to(List.of(DEFAULT_USER_ID, "foo", "bar"))
                .subject("Subject " + i)
                .isUnread(i % 2 == 0)
                .build();
    }

    private Email createEmail(EmailListItem emailListItem) {
        return Email.builder()
                .id(emailListItem.getKey().getTimeUUID())
                .from(DEFAULT_USER_ID)
                .to(emailListItem.getTo())
                .subject(emailListItem.getSubject())
                .body("Body " + emailListItem.getKey().getTimeUUID())
                .build();
    }

    private EmailListItemKey getDefaultEmailListItemKey() {
        return EmailListItemKey.builder()
                .id(DEFAULT_USER_ID)
                .label(getRandomFolder().getName())
                .timeUUID(Uuids.timeBased())
                .build();
    }

}
