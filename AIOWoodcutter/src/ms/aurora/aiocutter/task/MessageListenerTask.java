package ms.aurora.aiocutter.task;

import ms.aurora.aiocutter.event.MessageEvent;
import ms.aurora.api.Context;
import ms.aurora.api.methods.Widgets;
import ms.aurora.api.script.task.PassiveTask;
import ms.aurora.api.wrappers.RSWidget;
import ms.aurora.api.wrappers.RSWidgetGroup;

/**
 * Checks the chat box widget for new messages.
 * This task only runs when a player is logged in, at an interval of 75ms.
 * @author _override
 */
public class MessageListenerTask extends PassiveTask {
    private static final int CHATBOX_GROUP_ID = 137;
    private String lastMessage = "";

    private String getLatestMessage() {
        RSWidgetGroup group = Widgets.getWidgetGroup(CHATBOX_GROUP_ID);
        String lastText = "";
        for(RSWidget widget : group.getWidgets()) {
            if(widget.getText() != null && widget.getText().length() > 0) {
                lastText = widget.getText();
            }
        }
        return lastText;
    }

    @Override
    public boolean canRun() {
        return Context.isLoggedIn();
    }

    @Override
    public int execute() {
        String latest = getLatestMessage();
        if(!latest.equals(lastMessage)) {
            lastMessage = latest;

            queue.getEventBus().submit(new MessageEvent(latest));
        }
        return 75;
    }

}
