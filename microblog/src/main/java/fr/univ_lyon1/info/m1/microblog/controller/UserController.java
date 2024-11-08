package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.dto.MessageDataDTO;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Y;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controller for managing user-related operations in the microblogging application.
 */
public class UserController {
    private final Y y;

    /**
     * Constructs a UserController with the specified Y instance.
     *
     * @param y the instance of the application's model
     */
    public UserController(final Y y) {
        this.y = y;
    }

    /**
     * Retrieves the IDs of all users in the system.
     *
     * @return a collection of user IDs
     */
    public Collection<String> getUserIds() {
        Collection<User> users = y.getUsers();
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    /**
     * Retrieves the messages data for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of MessageDataDTO objects associated with the user, null if user doesn't exist
     */
    public List<MessageDataDTO> getMessagesDataForUser(final String userId) {
        User user = y.getUserById(userId);
        if (user == null) {
            return null;
        }
        LinkedHashMap<String, MessageData> allMessages = user.getMessagesData(); // Global messages
        List<MessageDataDTO> messageDataDTOs = new ArrayList<>();

        for (Map.Entry<String, MessageData> entry : allMessages.entrySet()) {
            String messageId = entry.getKey();
            MessageData msgData = entry.getValue();
            MessageDataDTO msgDataDTO = new MessageDataDTO(
                    messageId,
                    msgData.getScore(),
                    msgData.isBookmarked()
            );
            messageDataDTOs.add(msgDataDTO);
        }

        return messageDataDTOs;
    }

    /**
     * Bookmarks a message for a user.
     *
     * @param userId the ID of the user
     * @param messageId the ID of the message to bookmark
     * @return true if the message was successfully bookmarked, false otherwise
     */
    public boolean bookMarkMessage(final String userId, final String messageId) {
        User user = y.getUserById(userId);
        if (user == null) {
            return false;
        }
        return user.bookmarkMessage(messageId);
    }

    /**
     * Unbookmarks a message for a user.
     *
     * @param userId the ID of the user
     * @param messageId the ID of the message to unbookmark
     * @return true if the message was successfully unbookmarked, false otherwise
     */
    public boolean unBookMarkMessage(final String userId, final String messageId) {
        User user = y.getUserById(userId);
        if (user == null) {
            return false;
        }
        return user.removeBookmarkedMessage(messageId);
    }

    /**
     * Updates the user's messages with the provided updated messages data.
     *
     * @param userId the ID of the user
     * @param updatedMessages a collection of updated message data
     */
    public void updateUserMsg(final String userId,
                              final Collection<MessageDataDTO> updatedMessages) {
        User user = y.getUserById(userId);
        if (user == null) {
            return;
        }
        LinkedHashMap<String, MessageData> mappedMsg = updatedMessages.stream()
                .collect(Collectors.toMap(
                        MessageDataDTO::getId,  // Uses the DTO's ID as key
                        // Transforms DTO to MessageData
                        dto -> new MessageData(dto.isBookmarked(), dto.getScore()),
                        (existing, replacement) -> existing, // Keeps existing if duplicates exist
                        LinkedHashMap::new  // Collects into a LinkedHashMap
                ));
        user.updateMessages(mappedMsg);
    }

    /**
     * Sorts the messages of a user based on their scores and bookmark status.
     *
     * @param userId the ID of the user
     */
    public void sortMessages(final String userId) {
        User user = y.getUserById(userId);
        if (user == null) {
            return;
        }
        user.sortMessages();
    }
}
