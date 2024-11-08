package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.dto.MessageDTO;
import fr.univ_lyon1.info.m1.microblog.dto.MessageDataDTO;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.Y;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for managing messages in the microblogging application.
 */
public class MessageController {
    private final Y y;

    /**
     * Constructs a MessageController with the specified Y instance.
     *
     * @param y the instance of the application's model
     */
    public MessageController(final Y y) {
        this.y = y;
    }

    /**
     * Adds a new message to the system.
     *
     * @param publisherId the ID of the message publisher
     * @param content     the content of the message
     */
    public void addMessage(final String publisherId, final String content) {
        Message realMsg = new Message(publisherId, content);
        y.add(realMsg);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param msgId the ID of the message
     * @return a MessageDTO representing the message
     * @throws Exception if the message does not exist
     */
    public MessageDTO getMessageById(final String msgId) throws Exception {
        Message msg = y.getMessageById(msgId);
        if (msg != null) {
            return new MessageDTO(msg.getPublisherId(), msgId, msg.getContent(), msg.getDate());
        }
        throw new Exception("This message does not exist");
    }

    /**
     * Computes scores for a collection of messages based on their bookmark status.
     *
     * @param messages a collection of MessageDataDTO representing messages to score
     */
    public void computeScores(final Collection<MessageDataDTO> messages) {
        Map<Message, MessageData> mappedMsgs = messages.stream()
                .collect(Collectors.toMap(
                        // The key: a new Message object from the DTO
                        dto -> y.getMessageById(dto.getId()),
                        // The value: a new MessageData object
                        dto -> new MessageData(dto.isBookmarked(), dto.getScore())
                ));

        y.applyScoringRules(mappedMsgs);

        for (Map.Entry<Message, MessageData> entry : mappedMsgs.entrySet()) {
            String messageId = entry.getKey().getId();
            MessageData newData = entry.getValue();

            // Searches the original DTO collection and updates the data
            for (MessageDataDTO dto : messages) {
                if (dto.getId().equals(messageId)) {
                    dto.setScore(newData.getScore());
                }
            }
        }
    }

    /**
     * Delete the message by its Id.
     *
     * @param msgId the Id of the message to delete
     */
    public void deleteMessageById(final String msgId) {
        y.deleteMessage(msgId);
    }    

}
