package fr.univ_lyon1.info.m1.microblog.model.scoring.scoringRules;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Responsible for scoring messages based on bookmarks.
 * The scoring is calculated based on the words in the message content
 * that are also present in bookmarked messages.
 */
public class BookmarkScoring implements ScoringRule {

    /**
     * Computes the score for all messages in the provided map.
     * The score is determined by the number of unique words in a message
     * that are also found in other bookmarked messages.
     *
     * @param messagesData a map of messages to their corresponding metadata,
     *                     including bookmark status and score.
     */
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Set<Message> messages = messagesData.keySet();
        Set<String> bookmarkedWords = new HashSet<>();

        // First pass: collect all words from bookmarked messages
        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            String[] w = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>(Arrays.asList(w));
            d.setWords(words);
            if (d.isBookmarked()) {
                bookmarkedWords.addAll(words);
            }
        });

        // Second pass: compute the score based on bookmarked words
        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            int score = d.getScore();
            for (String w : d.getWords()) {
                if (bookmarkedWords.contains(w)) {
                    score++;
                }
            }
            d.setScore(score);
        });

    }

}
