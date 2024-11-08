package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a message in the application.
 * Each message has a unique identifier, a publisher, content, and a timestamp.
 */
public class Message {
    private final String id;
    private final String publisherId;
    private final String content;
    private final Date date;

    /**
     * Retrieves the unique identifier of the message.
     *
     * @return the ID of the message.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the date and time when the message was created.
     *
     * @return the date of the message.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return the content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the ID of the user who published the message.
     *
     * @return the publisher's ID.
     */
    public String getPublisherId() {
        return publisherId;
    }

    /**
     * Constructs a Message object with a publisher ID and content.
     * A unique ID is automatically generated for the message,
     * and the creation date is set to the current date and time.
     *
     * @param publisherId the ID of the user who publishes the message.
     * @param content     the content of the message.
     */
    public Message(final String publisherId, final String content) {
        this.id = UUID.randomUUID().toString();
        this.publisherId = publisherId;
        this.content = content;
        this.date = new Date();
    }

}
