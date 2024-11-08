package fr.univ_lyon1.info.m1.microblog.dto;

/**
 * Data Transfer Object (DTO) for representing the data associated with a message.
 * This class is used to encapsulate the message's score and bookmark status.
 */
public class MessageDataDTO {
    private final String originalMessageId;
    private int score;
    private boolean isBookmarked;

    /**
     * Constructs a MessageDataDTO with the original message ID, score, and bookmark status.
     *
     * @param originalMessageId the ID of the original message
     * @param score the score of the message
     * @param isBookmarked the bookmark status of the message
     */
    public MessageDataDTO(final String originalMessageId,
                          final int score,
                          final boolean isBookmarked) {
        this.originalMessageId = originalMessageId;
        this.score = score;
        this.isBookmarked = isBookmarked;
    }

    /**
     * Gets the ID of the original message.
     *
     * @return the original message ID
     */
    public String getId() {
        return originalMessageId;
    }

    /**
     * Gets the score of the message.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Checks if the message is bookmarked.
     *
     * @return true if the message is bookmarked, false otherwise
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Sets the score of the message.
     *
     * @param score the new score to set
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Sets the bookmark status of the message.
     *
     * @param bookmarked the new bookmark status
     */
    public void setBookmarked(final boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
