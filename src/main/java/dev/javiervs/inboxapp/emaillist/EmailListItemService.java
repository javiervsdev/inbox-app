package dev.javiervs.inboxapp.emaillist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailListItemService {

    private final EmailListItemRepository emailListItemRepository;

    public List<EmailListItem> findAllById(String id, String label) {
        return emailListItemRepository.findAllByKey_IdAndKey_Label(id, label);
    }
}
