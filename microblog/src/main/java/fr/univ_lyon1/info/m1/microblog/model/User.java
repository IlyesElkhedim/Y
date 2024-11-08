package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;

/**
 * Represents a user of the application.
 * Each user has a unique identifier and can bookmark messages.
 */
public class User {
    private final String id;
    private final LinkedHashMap<String, MessageData> messagesData;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }

    /**
     * Default constructor for User.
     *
     * @param id must be a unique identifier.
     */
    public User(final String id) {
        this.id = id;
        this.messagesData = new LinkedHashMap<>();
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return the ID of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Bookmarks a message by its ID.
     *
     * @param messageId the ID of the message to be bookmarked.
     * @return true if the message was successfully bookmarked, false otherwise.
     */
    public boolean bookmarkMessage(final String messageId) {
        if (this.messagesData.containsKey(messageId)) {
            this.messagesData.get(messageId).setBookmarked(true);
            return true;
        }
        return false;

    }

    /**
     * Retrieves a collection of IDs of all bookmarked messages.
     *
     * @return a collection of message IDs that are bookmarked.
     */
    public Collection<String> getBookmarkedMessage() {
        return messagesData.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isBookmarked())
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Removes the bookmark from a message by its ID.
     *
     * @param messageId the ID of the message to unbookmark.
     * @return true if the message was successfully unbookmarked, false otherwise.
     */
    public boolean removeBookmarkedMessage(final String messageId) {
        if (this.messagesData.containsKey(messageId)) {
            this.messagesData.get(messageId).setBookmarked(false);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the messages data associated with the user.
     *
     * @return a LinkedHashMap of message IDs and their corresponding MessageData.
     */
    public LinkedHashMap<String, MessageData> getMessagesData() {
        return messagesData;
    }

    /**
     * Adds a new message to the user's data by its ID.
     *
     * @param messageId the ID of the new message.
     */
    public void addNewMessage(final String messageId) {
        this.messagesData.put(messageId, new MessageData());
    }

    /**
     * Updates the user's messages data with new data.
     *
     * @param updatedMessages a LinkedHashMap containing updated message data.
     */
    public void updateMessages(final LinkedHashMap<String, MessageData> updatedMessages) {
        this.messagesData.clear();
        this.messagesData.putAll(updatedMessages);
    }

    /**
     * Sorts the user's messages based on score and bookmark status.
     */
    public void sortMessages() {
        LinkedHashMap<String, MessageData> sortedMessageList = new LinkedHashMap<>();
        this.messagesData.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getScore() > 1)
                .sorted(Collections.reverseOrder((Entry<String, MessageData> left,
                                                  Entry<String, MessageData> right) ->
                        left.getValue().compare(right.getValue())))
                .sorted(Collections.reverseOrder((Entry<String, MessageData> left,
                                                  Entry<String, MessageData> right) ->
                        Boolean.compare(left.getValue().isBookmarked(),
                                                          right.getValue().isBookmarked())))
                .forEach((Entry<String, MessageData> e) ->
                        sortedMessageList.put(e.getKey(), e.getValue()));
        this.messagesData.clear();
        this.messagesData.putAll(sortedMessageList);
    }

}
