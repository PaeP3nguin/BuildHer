package com.wic.buildher;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

/**
 * Class representing an update
 */
@ParseClassName("Announcement")
public class Update extends ParseObject {
    public Update() {
        super();
    }

    public String getSubject() {
        return getString("subject");
    }

    public String getMessage() {
        return getString("message");
    }

    public LocalDateTime getTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(getCreatedAt().getTime()),
                ZoneId.of("UTC-5"));
    }
}
