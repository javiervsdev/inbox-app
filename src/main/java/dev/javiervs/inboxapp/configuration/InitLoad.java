package dev.javiervs.inboxapp.configuration;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import dev.javiervs.inboxapp.emaillist.EmailListItem;
import dev.javiervs.inboxapp.emaillist.EmailListItemKey;
import dev.javiervs.inboxapp.emaillist.EmailListItemRepository;
import dev.javiervs.inboxapp.folder.Folder;
import dev.javiervs.inboxapp.folder.FolderRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class InitLoad {

    private final FolderRepository folderRepository;
    private final EmailListItemRepository emailListItemRepository;

    @PostConstruct
    public void init() {
        folderRepository.saveAll(
                List.of(
                        new Folder("javiervsdev", "Inbox", "blue"),
                        new Folder("javiervsdev", "Sent", "green"),
                        new Folder("javiervsdev", "Important", "yellow")
                )
        );
        emailListItemRepository.saveAll(
                Stream.iterate(0, i -> i + 1).limit(10)
                        .map(i ->
                                EmailListItem.builder()
                                        .key(getDefaultEmailListItemKey())
                                        .to(List.of("javiervsdev"))
                                        .subject("Subject " + i)
                                        .isUnread(i % 2 == 0)
                                        .build()
                        ).toList());
    }

    private EmailListItemKey getDefaultEmailListItemKey() {
        return EmailListItemKey.builder()
                .id("javiervsdev")
                .label("Inbox")
                .timeUUID(Uuids.timeBased())
                .build();
    }

}
