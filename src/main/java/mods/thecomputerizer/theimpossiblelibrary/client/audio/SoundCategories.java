package mods.thecomputerizer.theimpossiblelibrary.client.audio;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import net.minecraft.sound.SoundCategory;

public class SoundCategories {

    public static SoundCategory createSoundCategory(String name) {
        try {
            if (SoundCategory.valueOf(name) != null) {
                TheImpossibleLibrary.logWarning("Sound category with name " + name + " already exists!", null);
            }
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Failed to create new sound catrgory with name "+name,e);
        }
        return null;
    }
}
