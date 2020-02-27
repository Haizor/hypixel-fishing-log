package net.haizor.hypixelfishinglog.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button extends GuiButton {
    protected Minecraft mc;

    protected int baseX;
    protected int baseY;

    protected boolean defaultDraw = true;
    protected boolean playSound = true;

    public Button(GuiSeaCreatureLog log, int baseX, int baseY, String buttonText) {
        this(log, baseX, baseY, 20, 20, buttonText);
        mc = Minecraft.getMinecraft();
    }

    public Button(GuiSeaCreatureLog log, int baseX, int baseY, int width, int height, String buttonText) {
        super(log.currButton, baseX, baseY, buttonText);
        this.baseX = baseX;
        this.baseY = baseY;
        this.width = width;
        this.height = height;
        log.currButton++;
    }

    public void onActivated() {

    }

    public void doActivateCheck(int mouseX, int mouseY) {
        hovered = mouseX >= xPosition && mouseX <= xPosition + width &&
                mouseY >= yPosition && mouseY <= yPosition + height;
        if (hovered) {
            if (GuiSeaCreatureLog.mouseClicked) {
                onActivated();
                if (playSound) {
                    this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
                }
            }

        }

    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        doActivateCheck(mouseX, mouseY);
        if (defaultDraw) super.drawButton(mc, mouseX, mouseY);
    }

    public void drawButton(int x, int y, int mouseX, int mouseY, float partialTicks) {
        this.xPosition = baseX + x;
        this.yPosition = baseY + y;
        this.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }
}