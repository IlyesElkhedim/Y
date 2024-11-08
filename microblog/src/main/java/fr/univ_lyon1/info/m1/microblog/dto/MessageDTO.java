package fr.univ_lyon1.info.m1.microblog.dto;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for representing a message.
 * This class is used to transfer message data between different layers of the application.
 */
public class MessageDTO {
    private final String id;
    private final String publisherId;
    private final String content;
    private final Date date;

    /**
     * Constructs a MessageDTO with the specified publisher ID, message ID, content, and date.
     *
     * @param publisherId the ID of the user who published the message
     * @param id the unique identifier of the message
     * @param content the content of the message
     * @param date the date when the message was published
     */
    public MessageDTO(final String publisherId,
                      final String id,
                      final String content,
                      final Date date) {
        this.id = id;
        this.publisherId = publisherId;
        this.content = content;
        this.date = date;
    }

    /**
     * Gets the unique identifier of the message.
     *
     * @return the message ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the ID of the user who published the message.
     *
     * @return the publisher ID
     */
    public String getPublisherId() {
        return publisherId;
    }

    /**
     * Gets the content of the message.
     *
     * @return the message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the date when the message was published.
     *
     * @return the publication date
     */
    public Date getDate() {
        return date;
    }

}

