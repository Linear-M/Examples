package ms.aurora.aiocutter;

import ms.aurora.aiocutter.action.BankAction;
import ms.aurora.aiocutter.action.ChopAction;
import ms.aurora.aiocutter.action.DropAction;
import ms.aurora.aiocutter.action.PositioningAction;
import ms.aurora.aiocutter.config.Configuration;
import ms.aurora.aiocutter.config.impl.EastVarrockConfiguration;
import ms.aurora.aiocutter.event.MessageEvent;
import ms.aurora.aiocutter.task.MessageListenerTask;
import ms.aurora.api.script.ActionScript;
import ms.aurora.api.script.ScriptManifest;
import ms.aurora.api.script.task.EventBus;
import ms.aurora.event.listeners.PaintListener;

import java.awt.*;

/**
 * Script main class. Registers the actions, tasks and event listeners
 * TODO: GUI for selecting the configuration to use
 * @author _override
 */
@ScriptManifest(name = "AIOCutter", author = "_override", version = 1.0)
public class AIOCutter extends ActionScript implements PaintListener {
    public static Configuration configuration = new EastVarrockConfiguration();
    public static boolean banking = true;

    public static int logsCount = 0;
    public static int levelsGained = 0;

    @Override
    public void onStart() {
        getQueue().add(new MessageListenerTask());

        submit(new PositioningAction());
        submit(new ChopAction());
        if (banking) {
            submit(new BankAction());
        } else {
            submit(new DropAction());
        }
        getEventBus().register(this);
    }

    @EventBus.EventHandler
    public void onMessageEvent(MessageEvent event) {
        if(event.getMessage().matches("You get some (.*?) logs")) {
            logsCount++;
        } else if(event.getMessage().contains("advanced a Woodcutting level")) {
            levelsGained++;
            info("Gained a level! Yeah!");
        }

        info("Received server message: " + event.getMessage());
    }

    @Override
    public void onRepaint(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.drawString("Configuration: " + configuration.getClass().getSimpleName(), 10, 30);
        graphics.drawString("Logs cut: " + logsCount, 10, 50);
        graphics.drawString("Levels gained: " + levelsGained, 10, 70);
    }
}
