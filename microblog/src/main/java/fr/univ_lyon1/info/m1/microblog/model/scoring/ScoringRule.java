package fr.univ_lyon1.info.m1.microblog.model.scoring;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;

import java.util.Map;

/**
 * Interface representing a scoring rule for messages.
 * Implementing classes should provide their own logic for computing scores
 * based on the given messages and their associated data.
 */
public interface ScoringRule {

    /**
     * Computes the scores for the given messages based on the specific scoring rule implementation.
     *
     * @param messages a map of messages to their associated message data,
     *                 where the key is the message and the value is the corresponding MessageData.
     */
    void computeScores(Map<Message, MessageData> messages);
}
