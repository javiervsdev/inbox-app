package dev.javiervs.inboxapp.configuration;

import dev.javiervs.inboxapp.email.EmailDto;
import dev.javiervs.inboxapp.email.EmailService;
import dev.javiervs.inboxapp.folder.Folder;
import dev.javiervs.inboxapp.folder.FolderRepository;
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
    private final EmailService emailService;

    public static final String DEFAULT_USER_ID = "javiervsdev";
    public static final String DEFAULT_TO_MESSAGE = String.join(",", List.of(DEFAULT_USER_ID, "foo", "bar"));

    @PostConstruct
    public void init() {
        folderRepository.save(
                        new Folder(DEFAULT_USER_ID,
                                DEFAULT_USER_ID,
                                INBOX.getColor()));
        Stream.iterate(0, i -> i + 1).limit(10)
                .forEach(i -> emailService.sendEmail(createEmailDto(i)));
    }

    private EmailDto createEmailDto(Integer index) {
        return EmailDto.builder()
                .from(DEFAULT_USER_ID)
                .to(DEFAULT_TO_MESSAGE)
                .subject("Subject " + index)
                .body("Body " + index)
                .build();
    }
}
