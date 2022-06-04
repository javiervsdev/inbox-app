package dev.javiervs.inboxapp.folder;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.*;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.COUNTER;

@Data
@Table(value = "unread_email_stats")
public class UnreadEmailStats {

    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "label", ordinal = 1, type = CLUSTERED)
    private String label;

    @CassandraType(type = COUNTER)
    private int unreadCount;
}
