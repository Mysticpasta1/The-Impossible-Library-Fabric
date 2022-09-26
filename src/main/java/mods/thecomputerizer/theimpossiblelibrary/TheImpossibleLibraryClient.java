package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.client.visual.GIF;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.MP4;
import mods.thecomputerizer.theimpossiblelibrary.client.visual.Renderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class TheImpossibleLibraryClient implements ClientModInitializer {
    public static final KeyBinding TEST_KEYBIND = new KeyBinding("key.test", InputUtil.GLFW_KEY_I, "key.categories.theimpossiblelibrary");
    public static GIF testGif = null;
    public static MP4 testMp4 = null;

    @Override
    public void onInitializeClient() {
        if (TEST_KEYBIND.isPressed()) {
            //render testing
            if(testMp4!=null && testMp4.isFinished) testMp4 = null;
            if(testMp4==null) testMp4 = Renderer.initializeMp4(new Identifier(Constants.MODID,"test.mp4"));
            else Renderer.renderAnyAcceptedFileTypeToBackground(testMp4,"center","center",0,0,4f,4f,10000);
        }
    }
}
