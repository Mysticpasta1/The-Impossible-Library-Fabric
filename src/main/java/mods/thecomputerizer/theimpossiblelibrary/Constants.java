package mods.thecomputerizer.theimpossiblelibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Random;

@SuppressWarnings("unused")
public class Constants {
    public static final String MODID = "theimpossiblelibrary";
    public static final String NAME = "The Impossible Library";
    public static final String VERSION = "1.17.1-0.1";
    public static final String DEPENDENCIES = "";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final Random RANDOM = new Random();
    public static final File DATA_DIRECTORY = new File("./impossible_data");
}
