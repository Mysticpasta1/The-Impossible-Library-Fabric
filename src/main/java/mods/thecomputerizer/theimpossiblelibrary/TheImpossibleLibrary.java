package mods.thecomputerizer.theimpossiblelibrary;

import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.Nullable;

public class TheImpossibleLibrary implements ModInitializer {

    @Override
    public void onInitialize() {
        //generate config txt file for early loading stuff;
    }

    public static void logInfo(String message) {
        Constants.LOGGER.info(message);
    }

    public static void logWarning(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.warn(message, throwable);
        Constants.LOGGER.warn(message);
    }

    public static void logError(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.error(message, throwable);
        Constants.LOGGER.warn(message);
    }

    public static void logFatal(String message, @Nullable Throwable throwable) {
        if(throwable!=null) Constants.LOGGER.fatal(message, throwable);
        Constants.LOGGER.warn(message);
    }
}
