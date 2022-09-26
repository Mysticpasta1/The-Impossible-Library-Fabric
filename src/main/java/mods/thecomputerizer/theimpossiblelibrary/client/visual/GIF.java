package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sksamuel.scrimage.nio.AnimatedGif;
import com.sksamuel.scrimage.nio.AnimatedGifReader;
import com.sksamuel.scrimage.nio.ImageSource;
import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class GIF {

    private final AnimatedGif status;
    private final long delay;
    private final Identifier source;
    private final int glTextureId;
    private long milliCounter;
    private int frame;
    private long milliStatus;
    private String horizontal;
    private String vertical;
    private int x;
    private int y;
    private float scaleX;
    private float scaleY;
    private long millis;
    public boolean isFinished;

    public GIF(Identifier location) throws IOException {
        this.status = AnimatedGifReader.read(ImageSource.of(MinecraftClient.getInstance().getResourceManager().getResource(location).getInputStream()));
        this.delay = this.status.getDelay(0).toMillis();
        this.frame = 0;
        this.milliStatus = 0;
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.isFinished = false;
        this.source = location;
        this.glTextureId = TextureUtil.generateTextureId();
    }

    public boolean checkMilli(long millis) {
        boolean status = true;
        for(int i=0;i<millis;i++) {
            if (this.milliCounter <= 0 && status) status = incrementFrame();
            if (this.milliCounter>0) {
                this.milliCounter--;
                this.milliStatus++;
            }
        }
        return status;
    }

    public boolean incrementFrame() {
        this.frame++;
        this.milliCounter = this.delay;
        if(millis<=0) return this.frame<this.status.getFrameCount();
        else if (this.frame>=this.status.getFrameCount()) this.frame = 0;
        return this.milliStatus<=this.millis;
    }

    public void loadCurrentFrame() {
        try {
            TextureUtil.releaseTextureId(this.glTextureId);
            RenderSystem.bindTexture(this.glTextureId);
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Something went wrong when trying to render a frame of the gif at location "+this.source,e);
        }
    }

    public long getDelay() {
        return delay;
    }

    public int getScaledWidth() {
        return (int)(this.status.getDimensions().width*this.scaleX);
    }

    public int getScaledHeight() {
        return (int)(this.status.getDimensions().height*this.scaleY);
    }

    public float getWidthRatio() {
        return (float) getScaledWidth()/(float) getScaledHeight();
    }

    public float getHeightRatio() {
        return (float) getScaledHeight()/(float) getScaledWidth();
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getHorizontal() {
        return this.horizontal;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getVertical() {
        return this.vertical;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }
}
