package fr.univ_lyon1.info.m1.microblog.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;



import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringManager;
import fr.univ_lyon1.info.m1.microblog.observer.Observer;


/**
 * Toplevel class for the Y microblogging application's model.
 * This class manages users, messages, and observers in the application.
 */
public class Y {
    private final List<User> users = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private final ScoringManager scoringManager = new ScoringManager();

    /**
     * Adds a new view (observer) to the list of observers and updates it.
     *
     * @param observer the observer to be added
     */
    public void addView(final Observer observer) {
        this.observers.add(observer);
        observer.update();
    }

    /**
     * Notifies all registered observers about changes in the model.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Creates a new user with the given ID and adds it to the user's registry.
     *
     * @param id the ID of the new user
     */
    public void createUser(final String id) {
        User u = new User(id);
        users.add(u);
        notifyObservers();
    }

    /**
     * Creates an example set of users and messages for testing purposes.
     */
    public void createExampleMessages() {
        createUser("foo");
        createUser("bar");
        Message m1 = new Message("foo", "Hello, world!");
        add(m1);
        Message m2 = new Message("foo", "What is this message?");
        add(m2);
        add(new Message("bar", "Good bye, world!"));
        add(new Message("bar", "Hello, you!"));
        add(new Message("bar", "Hello hello, world world world."));
        notifyObservers();
    }

    /**
     * Retrieves all users in the registry.
     *
     * @return a collection of users
     */
    public Collection<User> getUsers() {
        return users;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if not found
     */
    public User getUserById(final String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param id the ID of the message to retrieve
     * @return the message with the specified ID, or null if not found
     */
    public Message getMessageById(final String id) {
        return messages.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Posts a new message and notifies all users and observers.
     *
     * @param message the message to post
     */
    public void add(final Message message) {
        this.messages.add(message);
        for (User user : users) {
            user.addNewMessage(message.getId());
        }
        notifyObservers();
    }

    /**
     * Delete the message by its Id and notifies all users and observers.
     *
     * @param msgId the Id of the message to delete
     */
    public void deleteMessage(final String msgId) {
        this.messages.remove(getMessageById(msgId));
        for (User user : users) {
            user.getMessagesData().remove(msgId);
        }
        notifyObservers();
    }
    
    /**
     * Sets the words associated with each message in the provided map of messages data.
     *
     * @param messagesData a map where each key is a {@link Message}
     *                     and its corresponding value is {@link MessageData}.
     *                     This method extracts the words from each message's content,
     *                     processes them, and stores
     *                     them in the associated {@link MessageData}.
     */
    public void setWordsForMessagesData(final Map<Message, MessageData> messagesData) {
        Set<Message> keyMessages = messagesData.keySet();
        keyMessages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            String[] w = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>(Arrays.asList(w));
            d.setWords(words);
        });
    }

    /**
     * Applies the scoring rules to the provided map of messages data.
     * This method first sets the words for each message data and initializes their scores to zero.
     * Then, it applies all the scoring rules
     * defined in the scoring manager to calculate the final scores.
     *
     * @param messagesData a map where each key is a {@link Message}
     *                     and its corresponding value is {@link MessageData}.
     *                     This map is used to compute
     *                     and update the scores based on the scoring rules.
     */
    public void applyScoringRules(final Map<Message, MessageData> messagesData) {
        setWordsForMessagesData(messagesData);
        messagesData.values().forEach(data -> data.setScore(0));
        scoringManager.applyScoringRules(messagesData);
    }



}
