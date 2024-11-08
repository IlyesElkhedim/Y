package fr.univ_lyon1.info.m1.microblog.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the data associated with a message for a particular user.
 */
public class MessageData {
    /**
     * Indicates whether the message is bookmarked.
     */
    private boolean isBookmarked = false;

    /**
     * The score of the message.
     */
    private int score = -1;

    /**
     * The set of words associated with the message.
     */
    private Set<String> words = new HashSet<>();

    /**
     * Default constructor for MessageData.
     */
    public MessageData() { }

    /**
     * Constructs a MessageData object with the specified bookmark status and score.
     *
     * @param isBookmarked Indicates whether the message is bookmarked.
     * @param score The score of the message.
     */
    public MessageData(final boolean isBookmarked, final int score) {
        this.isBookmarked = isBookmarked;
        this.score = score;
    }

    /**
     * Returns the set of words associated with the message.
     *
     * @return the set of words
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Sets the set of words associated with the message.
     *
     * @param words the set of words
     */
    public void setWords(final Set<String> words) {
        this.words = words;
    }

    /**
     * Returns the score of the message.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the message.
     *
     * @param score the score
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Returns whether the message is bookmarked or not.
     *
     * @return true if the message is bookmarked, false otherwise
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Sets whether the message is bookmarked or not.
     *
     * @param bookmarked true if the message is bookmarked, false otherwise
     */
    public void setBookmarked(final boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    /**
     * Compares two MessageData objects. Suitable for sorting based on their scores.
     *
     * @param rightData The MessageData object to compare with.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    public int compare(final MessageData rightData) {
        int scoreLeft = getScore();
        int scoreRight = rightData.getScore();
        return Integer.compare(scoreLeft, scoreRight);
    }
}
