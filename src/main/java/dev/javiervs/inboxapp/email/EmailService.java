package dev.javiervs.inboxapp.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    public Optional<Email> findById(UUID id) {
        return emailRepository.findById(id);
    }
}
