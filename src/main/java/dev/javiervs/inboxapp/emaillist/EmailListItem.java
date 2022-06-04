package dev.javiervs.inboxapp.emaillist;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import dev.javiervs.inboxapp.email.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.*;

@Data
@Builder
@AllArgsConstructor
@Table(value = "messages_by_user_folder")
public class EmailListItem {

    @PrimaryKey
    private EmailListItemKey key;

    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> to;

    @CassandraType(type = TEXT)
    private String subject;

    @CassandraType(type = BOOLEAN)
    private boolean isUnread;

    public static EmailListItem create(Email email, String owner, String folder, boolean isUnread) {
        return EmailListItem.builder()
                .key(EmailListItemKey.create(email, owner, folder))
                .to(email.getTo())
                .subject(email.getSubject())
                .isUnread(isUnread)
                .build();
    }

    public String getVerboseTimeAgo() {
        Date emailDateTime = new Date(Uuids.unixTimestamp(key.getTimeUUID()));
        return new PrettyTime().format(emailDateTime);
    }

    public String getFolderLabel() {
        return key.getLabel();
    }
}
