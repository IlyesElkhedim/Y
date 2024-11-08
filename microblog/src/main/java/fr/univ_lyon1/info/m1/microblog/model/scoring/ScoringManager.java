package fr.univ_lyon1.info.m1.microblog.model.scoring;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Scoring rules manager class.
 *
 * This class is responsible for loading and applying scoring rules
 * to a set of messages and their associated data. It uses Reflections to
 * dynamically discover the scoring rule classes present in a specified package.
 */
public class ScoringManager {
    private final Set<ScoringRule> scoringRules = new HashSet<>();

    /**
     * Constructor for ScoringManager.
     *
     * This constructor initializes the scoring manager and loads the scoring rules
     * defined in the specified package.
     */
    public ScoringManager() {
        loadScoringRules();
    }

    /**
     * Dynamically loads scoring rules from the specified package.
     *
     * This method uses the Reflections library to scan the package
     * containing subclasses of ScoringRule. The found classes are instantiated
     * and added to the collection of scoring rules.
     */
    private void loadScoringRules() {
        Reflections reflections = new Reflections(
                "fr.univ_lyon1.info.m1.microblog.model.scoring.scoringRules");
        Set<Class<? extends ScoringRule>> ruleClasses =
                reflections.getSubTypesOf(ScoringRule.class);

        for (Class<? extends ScoringRule> ruleClass : ruleClasses) {
            try {
                ScoringRule rule = ruleClass.getDeclaredConstructor().newInstance();
                scoringRules.add(rule);
            } catch (Exception e) {
                System.out.println("Error instantiating class " + ruleClass.getName());
            }
        }
    }

    /**
     * Applies all scoring rules to the provided message data.
     *
     * @param messagesData A mapping between messages and their associated data.
     *                     Each scoring rule is applied to modify the scores of the
     *                     messages based on the defined criteria.
     */
    public void applyScoringRules(final Map<Message, MessageData> messagesData) {
        for (ScoringRule rule : scoringRules) {
            rule.computeScores(messagesData);
        }
    }
}
