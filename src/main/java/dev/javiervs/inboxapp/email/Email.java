package dev.javiervs.inboxapp.email;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static dev.javiervs.inboxapp.email.EmailUtils.getUniqueIdsStream;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.LIST;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TEXT;

@Data
@Builder
@Table(value = "messages_by_id")
public class Email {

    @Id
    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PARTITIONED)
    private UUID id;

    @CassandraType(type = TEXT)
    private String from;

    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> to;

    @CassandraType(type = TEXT)
    private String subject;

    @CassandraType(type = TEXT)
    private String body;

    public static Email create(EmailDto emailDto) {
        if (emailDto == null
                || !StringUtils.hasText(emailDto.getFrom())
                || !StringUtils.hasText(emailDto.getTo())) {
            throw new IllegalArgumentException("EmailDto cannot be created");
        }

        return Email.builder()
                .id(Uuids.timeBased())
                .from(emailDto.getFrom())
                .to(getUniqueIdsStream(emailDto.getTo()).toList())
                .subject(emailDto.getSubject())
                .body(emailDto.getBody())
                .build();
    }

    public String getVerboseTo() {
        return String.join(", ", to);
    }

    public String getReplySubject() {
        return "Re: " + subject;
    }

    public String getReplyBody() {
        return String.format(
                "\n\n\n----------------------------------\n" +
                        "From: %s\n" +
                        "To: %s\n\n" +
                        "%s",
                from, getVerboseTo(), body);
    }

    public boolean hasUserAccess(String userId) {
        return to.contains(userId) || from.equals(userId);
    }
}
