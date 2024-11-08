package fr.univ_lyon1.info.m1.microblog.model.scoring.scoringRules;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringRule;

import java.util.Map;
import java.util.Set;
import java.util.Date;

import java.util.concurrent.TimeUnit;

/**
 * Responsible for scoring messages based on the date of the messages.
 * +1 if < 7 days
 * Another +1 if < 24h
 */
public class RecentMessagesScoring implements ScoringRule {

    /**
     * Computes the score for all messages in the provided map.
     * The score is determined by date of the message
     *
     * @param messagesData a map of messages to their corresponding metadata,
     *                     including bookmark status and score.
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Set<Message> messages = messagesData.keySet();

        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            int bonus = 0;
            int score = d.getScore();
            long currentTime = new Date().getTime();
            long messageTime = m.getDate().getTime();
            long diff = currentTime - messageTime;
            long difference =  TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);

            if (difference < 24) {
                bonus += 1;
            }
            if (difference < 168) {
                bonus += 1;
            }

            score += bonus;

            d.setScore(score);
        });
    }
}
