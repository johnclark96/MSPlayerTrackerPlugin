package rocks.milspecsg.msplayertracker;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;


@Plugin(
        id = "msplayertracker",
        name = "MSPlayerTracker"
)
public class MSPlayerTracker {

    @Inject
    public Logger logger;

    private static MSPlayerTracker instance = null;
    public static MSPlayerTracker getInstance(){return instance;}
    public Commands commandMgr;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        instance = this;
        logger.info("[V1.0.0] MSPlayerTracker is loading...");
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent event) {
        logger.info("[V1.0.0] MSPlayerTracker is loaded!");
    }

    @Listener
    public void onStarting(GameStartingServerEvent event) {
        logger.info("[MSPTRACK] Registered Commands.");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        registerCommands();
    }


    public void registerCommands()
    {
        this.commandMgr = new Commands(instance);

        commandMgr.init();
    }
}
