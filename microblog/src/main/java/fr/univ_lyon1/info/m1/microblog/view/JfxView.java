package fr.univ_lyon1.info.m1.microblog.view;

import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;

import fr.univ_lyon1.info.m1.microblog.controller.MessageController;
import fr.univ_lyon1.info.m1.microblog.controller.UserController;
import fr.univ_lyon1.info.m1.microblog.dto.MessageDTO;
import fr.univ_lyon1.info.m1.microblog.dto.MessageDataDTO;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.observer.Observer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView implements Observer {
    private final HBox users;
    private final MessageController messageController;
    private final UserController userController;

    /**
     * Main view of the application.
     *
     * @param y      Main model of the application.
     * @param stage  Main window of the interface.
     * @param width  Width of the window.
     * @param height Height of the window.
     */
    public JfxView(final Y y, final Stage stage,
                       final int width, final int height) {
        stage.setTitle("Y Microblogging");

        this.messageController = new MessageController(y);
        this.userController = new UserController(y);
        final VBox root = new VBox(10);

        // final Pane search = createSearchWidget();
        // root.getChildren().add(search);

        users = new HBox(10);
        root.getChildren().add(users);
        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates the user interface to display messages.
     */
    public void update() {
        Collection<String> userIds = this.userController.getUserIds();
        if (userIds.size() > this.users.getChildren().size()) {
            createUsersPanes();
        }
        for (String uid: userIds) {
            sortMessages(uid);
        }

    }

    /**
     * Creates user panels.
     */
    public void createUsersPanes() {
        users.getChildren().clear();
        for (String uid : userController.getUserIds()) {
            ScrollPane p = new ScrollPane();
            VBox userBox = new VBox();
            p.setMinWidth(300);
            p.setContent(userBox);
            users.getChildren().add(p);

            VBox userMsgBox = new VBox();
            
            Label userID = new Label(uid);

            Pane textBox = createInputWidget(uid);
            userBox.getChildren().addAll(userID, userMsgBox, textBox);
        }
    }

    /**
     * Handles bookmarking a message.
     *
     * @param userId The ID of the user.
     * @param msgId  The ID of the message.
     * @return true if the message was successfully bookmarked, false otherwise.
     */
    public boolean bookmarkMessage(final String userId, final String msgId) {
        return this.userController.bookMarkMessage(userId, msgId);
    }

    /**
     * Handles unbookmarking a message.
     *
     * @param userId The ID of the user.
     * @param msgId  The ID of the message.
     * @return true if the message was successfully unbookmarked, false otherwise.
     */
    public boolean unBookmarkMessage(final String userId, final String msgId) {
        return this.userController.unBookMarkMessage(userId, msgId);
    }

    /**
     * Reloads the messages for a specific user.
     *
     * @param dbUserId The ID of the user whose messages should be reloaded.
     */
    void reloadMessages(final String dbUserId) {
    Collection<MessageDataDTO> messagesData = this.userController.getMessagesDataForUser(dbUserId);
    if (messagesData == null) {
        return;
    }
    for (Node u : users.getChildren()) {
        ScrollPane scroll = (ScrollPane) u;
        VBox userBox = (VBox) scroll.getContent();
        Label userID = (Label) userBox.getChildren().get(0);
        if (userID.getText().equals(dbUserId)) {
            VBox userMsg = (VBox) userBox.getChildren().get(1);
            userMsg.getChildren().clear();
            for (MessageDataDTO msgData : messagesData) {
                VBox messageWidget = createMessageWidget(msgData);
                
                HBox buttonWidget = new HBox();
                Button bookButton = createBookButton(dbUserId, msgData);
                Button deleteButton = createDeleteButton(dbUserId, msgData);
                buttonWidget.getChildren().add(bookButton);
                buttonWidget.getChildren().add(deleteButton);
                buttonWidget.setAlignment(Pos.BASELINE_RIGHT);

                messageWidget.getChildren().add(0, buttonWidget); 
                userMsg.getChildren().add(messageWidget);
            }
            break;
        }
    }
}

    static final String MSG_STYLE = "-fx-background-color: white; "
            + "-fx-border-color: black; -fx-border-width: 1;"
            + "-fx-border-radius: 10px;"
            + "-fx-background-radius: 10px;"
            + "-fx-padding: 8px; "
            + "-fx-margin: 5px; ";


    /**
     * Creates a message widget.
     *
     * @param msgData The data of the message to display.
     * @return A VBox containing the message widget.
     */
    private VBox createMessageWidget(
            final MessageDataDTO msgData) {
        VBox msgBox = new VBox();

        try {
            MessageDTO msg = this.messageController.getMessageById(msgData.getId());
            final Label label = new Label(msg.getContent());
            msgBox.getChildren().add(label);

            Date messageDate = msg.getDate();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formatedDate = dateformat.format(messageDate);
            final Label date = new Label(formatedDate);
            msgBox.getChildren().add(date);

            final Label score = new Label("Score: " + msgData.getScore());
            score.setTextFill(Color.LIGHTGRAY);
            msgBox.getChildren().add(score);

            msgBox.setStyle(MSG_STYLE);
        } catch (Exception e) {
            final Label label = new Label("Can't load this message");
            msgBox.getChildren().add(label);
        }
        return msgBox;
    }

    /**
     * Creates a bookmark button for a message.
     *
     * @param userId  The ID of the user.
     * @param msgData The data of the message.
     * @return A button to bookmark or unbookmark the message.
     */
    private Button createBookButton(final String userId, final MessageDataDTO msgData) {
        String bookmarkText;
        boolean bookmarked = msgData.isBookmarked();
        String msgId = msgData.getId();
        if (bookmarked) {
            bookmarkText = "⭐";
        } else {
            bookmarkText = "Click to bookmark";
        }
        Button bookButton = new Button(bookmarkText);
        bookButton.setOnAction(e -> {
            if (bookButton.getText().equals("Click to bookmark")) {
                if (bookmarkMessage(userId, msgId)) {
                    bookButton.setText("⭐");
                }
            } else {
                if (unBookmarkMessage(userId, msgId)) {
                    bookButton.setText("Click to bookmark");

                }
            }
            sortMessages(userId);
        });
        return bookButton;
    }

    /**
     * Creates a delete button for a message.
     *
     * @param userId  The ID of the user.
     * @return A button to delete the message.
     */
    private Button createDeleteButton(final String userId, final MessageDataDTO msgData) {
        String msgId = msgData.getId();
        Button deleteButton = new Button("x");
        deleteButton.setOnAction(e -> {
            this.messageController.deleteMessageById(msgId);
            sortMessages(userId);
        });
        return deleteButton;
    }

    /**
     * Creates an input widget for publishing messages.
     *
     * @param userId The ID of the user.
     * @return A Pane containing the input widget.
     */
    private Pane createInputWidget(final String userId) {
        final Pane input = new HBox();
        TextArea area = new TextArea();
        area.setMaxSize(200, 150);
        area.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
                publish(userId, area);
                area.clear();
            }
        });
        Button publishButton = new Button("Publish");
        publishButton.setOnAction(event -> {
            publish(userId, area);
            area.clear();
        });
        input.getChildren().addAll(area, publishButton);
        return input;
    }

    /**
     * Adds a message for all users.
     *
     * @param publisherId The ID of the user publishing the message.
     * @param content     The content of the message.
     */
    public void addMessage(final String publisherId, final String content) {
        this.messageController.addMessage(publisherId, content);
    }

    /**
     * Publishes a message.
     *
     * @param publisherId The ID of the user publishing the message.
     * @param t           The text area containing the message to publish.
     */
    private void publish(final String publisherId, final TextArea t) {
        addMessage(publisherId, t.getText());
    }

    /**
     * Sorts the messages for a user.
     *
     * @param uid The ID of the user whose messages should be sorted.
     */
    private void sortMessages(final String uid) {
            Collection<MessageDataDTO> userMsg = this.userController.getMessagesDataForUser(uid);
            if (userMsg == null) {
                return;
            }
            this.messageController.computeScores(userMsg);
            this.userController.updateUserMsg(uid, userMsg);
            this.userController.sortMessages(uid);
            reloadMessages(uid);
    }
}
