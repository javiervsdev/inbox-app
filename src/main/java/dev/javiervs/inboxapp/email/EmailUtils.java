package dev.javiervs.inboxapp.email;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class EmailUtils {

    public static Stream<String> getUniqueIdsStream(String to) {
        if (!StringUtils.hasText(to)) {
            return Stream.empty();
        }

        return Arrays.stream(to.split(","))
                .map(StringUtils::trimAllWhitespace)
                .filter(StringUtils::hasText)
                .distinct();
    }
}
