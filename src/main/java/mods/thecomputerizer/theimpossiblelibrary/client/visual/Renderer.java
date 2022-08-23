package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Renderer {

    private static final Map<GIF, Long> renderableGifs = new HashMap<>();
    private static final ArrayList<MP4> renderableMp4s = new ArrayList<>();

    public static MP4 initializeMp4(ResourceLocation location) {
        try {
            return new MP4(location);
        } catch (IOException ex) {
            TheImpossibleLibrary.logError("Failed to initialize mp4 at resource location "+location,ex);
        }
        return null;
    }

    public static GIF initializeGif(ResourceLocation location) {
        try {
            return new GIF(location);
        } catch (IOException ex) {
            TheImpossibleLibrary.logError("Failed to initialize gif at resource location "+location,ex);
        }
        return null;
    }

    /*
        horizontal accepts left, center, and right
        vertical accepts bottom, center, and top
        x and y are additional horizontal and vertical translations
        scalex and scale y are percentage scales
        set millis to 0 or less to only render the mp4 for 1 cycle
     */
    public static void renderMP4ToBackground(MP4 mp4, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderableMp4s.contains(mp4)) {
            mp4.setHorizontal(horizontal);
            mp4.setVertical(vertical);
            mp4.setX(x);
            mp4.setY(y);
            mp4.setScaleX(scaleX);
            mp4.setScaleY(scaleY);
            mp4.setMillis(millis);
            renderableMp4s.add(mp4);
        }
    }

    /*
        horizontal accepts left, center, and right
        vertical accepts bottom, center, and top
        x and y are additional horizontal and vertical translations
        scalex and scale y are percentage scales
        set millis to 0 or less to only render the gif for 1 cycle
     */
    public static void renderGifToBackground(GIF gif, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderableGifs.containsKey(gif)) {
            gif.setHorizontal(horizontal);
            gif.setVertical(vertical);
            gif.setX(x);
            gif.setY(y);
            gif.setScaleX(scaleX);
            gif.setScaleY(scaleY);
            gif.setMillis(millis);
            renderableGifs.put(gif, gif.getDelay());
        }
    }

    public static void stopRenderingAllGifs() {
        renderableGifs.clear();
    }

    public static void stopRenderingAllMp4s() {
        renderableMp4s.clear();
    }

    public static void stopRenderingAllFileTypes() {
        stopRenderingAllGifs();
        stopRenderingAllMp4s();
    }

    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()== RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution res = e.getResolution();
            int x = res.getScaledWidth();
            int y = res.getScaledHeight();
            Vector4f color = new Vector4f(1, 1, 1, 1);
            for(GIF gif : renderableGifs.keySet()) renderGif(gif,color,x,y);
            for(MP4 mp4 : renderableMp4s) renderMp4(mp4,color,x,y);
            renderableGifs.entrySet().removeIf(entry -> {
                if(!entry.getKey().checkMilli((long)(50f*e.getPartialTicks())))
                    entry.getKey().isFinished = true;
                return entry.getKey().isFinished;
            });
            renderableMp4s.removeIf(mp4 -> {
                if(!mp4.checkMilli((long)(50f*e.getPartialTicks())))
                    mp4.isFinished = true;
                return mp4.isFinished;
            });
        }
    }

    public static void renderGif(GIF gif, Vector4f color, int resolutionX, int resolutionY) {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        gif.loadCurrentFrame(false,false);
        GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*gif.getScaleX();
        float scaleY = 0.25f*gif.getScaleY();
        if(gif.getWidthRatio()>=1f) scaleX*=gif.getWidthRatio();
        if(gif.getHeightRatio()>=1f) scaleY*=gif.getHeightRatio();
        GlStateManager.scale(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(gif.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(gif.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(gif.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(gif.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+gif.getX();
        float posY = (yOffset*(1/scaleY))+gif.getY();
        GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,resolutionX,resolutionY,resolutionX,resolutionY,resolutionX,resolutionY);
        GlStateManager.color(1F, 1F, 1F, 1);
        GlStateManager.popMatrix();
    }

    public static void renderMp4(MP4 mp4, Vector4f color, int resolutionX, int resolutionY) {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        mp4.loadCurrentFrame(false,false);
        GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*mp4.getScaleX();
        float scaleY = 0.25f*mp4.getScaleY();
        if(mp4.getWidthRatio()>=1f) scaleX*=mp4.getWidthRatio();
        if(mp4.getHeightRatio()>=1f) scaleY*=mp4.getHeightRatio();
        GlStateManager.scale(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(mp4.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(mp4.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(mp4.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(mp4.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+mp4.getX();
        float posY = (yOffset*(1/scaleY))+mp4.getY();
        GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,resolutionX,resolutionY,resolutionX,resolutionY,resolutionX,resolutionY);
        GlStateManager.color(1F, 1F, 1F, 1);
        GlStateManager.popMatrix();
    }
}
