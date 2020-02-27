package net.haizor.hypixelfishinglog.gui.pages;

import net.haizor.hypixelfishinglog.gui.Button;
import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public class Page {
    protected GuiSeaCreatureLog log;

    public String title = "Page";
    public int titleColor = 0xFFFFFF;
    public int centeringOffset = 18;

    public List<Button> buttonList = new ArrayList<>();

    public Page(GuiSeaCreatureLog parent) {
        this.log = parent;
    }

    public void drawPage(int x, int y, int mouseX, int mouseY, float partialTicks) {
        int centerX = x + GuiSeaCreatureLog.bookPageWidth / 2 + centeringOffset;
        log.drawCenteredString(log.getFontRenderer(), title, centerX, y + 10, titleColor);
        GlStateManager.color(1, 1, 1);

        for (Button b : buttonList) {
            b.drawButton(x, y, mouseX, mouseY, partialTicks);
        }
    }

    public void addButton(Button button) {
        buttonList.add(button);
    }

    public void handleMouseInput() {}
}
