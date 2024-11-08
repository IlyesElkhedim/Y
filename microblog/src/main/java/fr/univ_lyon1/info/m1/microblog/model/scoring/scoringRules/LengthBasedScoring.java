package fr.univ_lyon1.info.m1.microblog.model.scoring.scoringRules;


import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringRule;

import java.util.Map;

/**
 * Responsible for scoring messages based on the length of the message.
 */
public class LengthBasedScoring implements ScoringRule {

    /**
     * Computes the score for all messages in the provided map.
     * The score is determined by the number of characters
     * (including spaces) in the message
     *
     * @param messagesData a map of messages to their corresponding metadata,
     *                     including bookmark status and score.
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        messagesData.forEach((message, data) -> {
            int length = message.getContent().length();
            int bonus = length > 100 ? 2 : 0;
            data.setScore(data.getScore() + bonus);
        });
    }
}
