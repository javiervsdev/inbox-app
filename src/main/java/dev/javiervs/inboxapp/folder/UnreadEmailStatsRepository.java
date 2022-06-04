package dev.javiervs.inboxapp.folder;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnreadEmailStatsRepository extends CassandraRepository<UnreadEmailStats, String> {

    List<UnreadEmailStats> findAllById(String id);

    @Query("update unread_email_stats " +
            "set unreadcount = unreadcount + 1 " +
            "where user_id = :id " +
            "   and label = :label")
    void incrementUnreadCount(String id, String label);

    @Query("update unread_email_stats " +
            "set unreadcount = unreadcount - 1 " +
            "where user_id = :id " +
            "   and label = :label")
    void decrementUnreadCount(String id, String label);
}
