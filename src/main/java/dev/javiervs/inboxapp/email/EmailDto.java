package dev.javiervs.inboxapp.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String from;
    private String to;
    private String subject;
    private String body;
}