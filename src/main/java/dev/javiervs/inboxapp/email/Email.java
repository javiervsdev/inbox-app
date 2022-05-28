package dev.javiervs.inboxapp.email;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.*;

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

    public String getVerboseTo() {
        return String.join(", ", to);
    }
}
