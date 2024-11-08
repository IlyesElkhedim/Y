package fr.univ_lyon1.info.m1.microblog.model.scoring.scoringRules;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringRule;

import java.util.Map;
import java.util.Set;

/**
 * Responsible for scoring messages based on a set of forbidden words.
 * The scoring is calculated based on the words in the message content
 * that are also present in forbidden words.
 */
public class ForbiddenWordsScoring implements ScoringRule {
    private final Set<String> forbiddenWords = Set.of("hello");

    /**
     * Computes the score for all messages in the provided map.
     * The score is determined by the number of unique words in a message
     * that are also found in the forbidden words set
     *
     * @param messagesData a map of messages to their corresponding metadata,
     *                     including bookmark status and score.
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        messagesData.forEach((message, data) -> {
            long penalty = data.getWords().stream()
                    .map(String::toLowerCase)
                    .filter(forbiddenWords::contains)
                    .count();
            data.setScore(data.getScore() - (int) penalty * 5); // Applique un malus
        });
    }
}
