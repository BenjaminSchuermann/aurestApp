package aurestApp.Tools;

import javafx.animation.AnimationTimer;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MessageConsumer extends AnimationTimer {
    private final BlockingQueue<String> messageQueue;
    private final TextArea textArea;
    private final int numMessages;
    private int messagesReceived = 0;

    public MessageConsumer(BlockingQueue<String> messageQueue, TextArea textArea, int numMessages) {
        this.messageQueue = messageQueue;
        this.textArea = textArea;
        this.numMessages = numMessages;
    }

    @Override
    public void handle(long now) {
        List<String> messages = new ArrayList<>();
        messagesReceived += messageQueue.drainTo(messages);
        messages.forEach(msg -> textArea.appendText("\n" + msg));
        if (messagesReceived >= numMessages) {
            stop();
        }
    }
}
