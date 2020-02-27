package net.haizor.hypixelfishinglog.gui.pages;

import net.haizor.hypixelfishinglog.data.Drop;
import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageTreasureCatches extends Page {
    public Map<String, Drop> catches;

    private static final int dropSpacingX = 32;
    private static final int dropSpacingY = 32;

    public int yScroll;
    public int yScrollMin;
    public int yScrollMax;
    public int yScrollInterval = 32;

    public int minItemY = 20;
    public int maxItemY = 150;

    public PageTreasureCatches(GuiSeaCreatureLog parent, Map<String, Drop> catches, String title) {
        super(parent);
        this.title = title;
        this.catches = catches;
    }

    @Override
    public void handleMouseInput() {
        int scroll = Integer.signum(Mouse.getEventDWheel());
        scroll -= scroll * 2;
        yScroll += scroll * yScrollInterval;

        if (yScroll > yScrollMax) yScroll = yScrollMax;
        if (yScroll < yScrollMin) yScroll = yScrollMin;
    }

    @Override
    public void drawPage(int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.drawPage(x, y, mouseX, mouseY, partialTicks);

        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(GuiSeaCreatureLog.bookGuiTextures);

        List<Drop> drops = new ArrayList<>(catches.values());

        yScrollMax = yScrollInterval * Math.round(drops.size() / 4f) - (yScrollInterval * 4);

        if (drops.size() > 16) {
            log.drawTexturedModalRect(x + 150, y + 30, 146, 9, 8, 137);
            log.drawTexturedModalRect(x + 151, y + 31 + ((float)yScroll / (float)yScrollMax) * 126, 147, 0, 6, 9);
        }

        for (int i = 0; i < drops.size(); i++) {
            int currX = (dropSpacingX * i);
            int currY = 0;

            int pageWidth = (dropSpacingX * 4);

            while (currX >= pageWidth) {
                currX -= (dropSpacingX * 4);
                currY += dropSpacingY;
            }

            ItemStack stack = drops.get(i).toItemStack();
            drawItem(stack, x, y, currX + 35, currY + 40 - yScroll, mouseX, mouseY);
        }
    }

    public void drawItem(ItemStack stack, int baseX, int baseY, int x, int y, int mouseX, int mouseY) {
        RenderItem renderItem = log.getRenderItem();
        if (y <= maxItemY && y >= minItemY) {
            renderItem.zLevel = -1;
            RenderHelper.enableGUIStandardItemLighting();
            renderItem.renderItemAndEffectIntoGUI(stack, baseX + x, baseY + y);

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();

            log.drawCenteredString(log.getFontRenderer(), stack.stackSize + "", baseX + (x + 8), baseY + (y + 16), GuiSeaCreatureLog.baseTextColor);
            if (mouseX >= baseX + x && mouseX <= baseX + x + 16 && mouseY >= baseY + y && mouseY <= baseY + y + 16) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    if (GuiSeaCreatureLog.mouseClicked) {
                        catches.remove(stack.getDisplayName());
                    }
                    List<String> tooltip = new ArrayList<>();
                    tooltip.add("Shift + left click to remove this entry.");
                    log.displayTooltip = tooltip;
                } else {
                    log.hoveredItemStack = stack;
                }

            }

            RenderHelper.disableStandardItemLighting();
        }
    }
}
