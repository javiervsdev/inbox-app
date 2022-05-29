package dev.javiervs.inboxapp.email;

import dev.javiervs.inboxapp.emaillist.EmailListItem;
import dev.javiervs.inboxapp.emaillist.EmailListItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static dev.javiervs.inboxapp.enums.DefaultFolder.INBOX;
import static dev.javiervs.inboxapp.enums.DefaultFolder.SENT;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailListItemRepository emailListItemRepository;

    public Optional<Email> findById(UUID id) {
        return emailRepository.findById(id);
    }

    public void sendEmail(EmailDto emailDto) {
        Email email = Email.create(emailDto);
        emailRepository.save(email);

        email.getTo().forEach(to -> {
            EmailListItem receivedItemEntry = EmailListItem.create(email, to, INBOX.getName());
            emailListItemRepository.save(receivedItemEntry);
        });

        EmailListItem sentItemEntry = EmailListItem.create(email, email.getFrom(), SENT.getName());
        emailListItemRepository.save(sentItemEntry);
    }
}
