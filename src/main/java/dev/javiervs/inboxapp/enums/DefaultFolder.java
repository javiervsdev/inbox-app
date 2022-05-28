package dev.javiervs.inboxapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultFolder {
    INBOX("Inbox", "blue"),
    SENT("Sent", "green"),
    IMPORTANT("Important", "yellow");

    private final String name;
    private final String color;

    public static DefaultFolder getRandomFolder() {
        return values()[(int) (Math.random() * values().length)];
    }
}
