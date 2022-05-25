package dev.javiervs.inboxapp.folder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TEXT;

@Data
@AllArgsConstructor
@Table(value = "folders_by_user")
public class Folder {

    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "label", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private String label;

    @CassandraType(type = TEXT)
    private String color;

}