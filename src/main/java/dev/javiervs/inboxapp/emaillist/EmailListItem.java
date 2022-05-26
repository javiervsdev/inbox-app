package dev.javiervs.inboxapp.emaillist;

import com.datastax.oss.driver.api.core.uuid.Uuids;
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

    public String getVerboseTimeAgo() {
        Date emailDateTime = new Date(Uuids.unixTimestamp(key.getTimeUUID()));
        return new PrettyTime().format(emailDateTime);
    }
}
