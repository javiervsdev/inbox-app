package dev.javiervs.inboxapp.email;

import dev.javiervs.inboxapp.emaillist.EmailListItem;
import dev.javiervs.inboxapp.emaillist.EmailListItemRepository;
import dev.javiervs.inboxapp.folder.UnreadEmailStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.javiervs.inboxapp.enums.DefaultFolder.INBOX;
import static dev.javiervs.inboxapp.enums.DefaultFolder.SENT;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailListItemRepository emailListItemRepository;
    private final UnreadEmailStatsRepository unreadEmailStatsRepository;

    public Email findById(UUID id) {
        return emailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));
    }

    public void sendEmail(EmailDto emailDto) {
        Email email = Email.create(emailDto);
        emailRepository.save(email);

        email.getTo().forEach(to -> {
            String folderLabel = INBOX.getName();
            EmailListItem receivedItemEntry = EmailListItem.create(email, to, folderLabel, true);
            emailListItemRepository.save(receivedItemEntry);
            unreadEmailStatsRepository.incrementUnreadCount(to, folderLabel);
        });

        EmailListItem sentItemEntry = EmailListItem.create(email, email.getFrom(), SENT.getName(), false);
        emailListItemRepository.save(sentItemEntry);
    }
}
