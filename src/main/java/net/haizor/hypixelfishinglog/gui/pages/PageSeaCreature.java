package net.haizor.hypixelfishinglog.gui.pages;

import net.haizor.hypixelfishinglog.data.DataManager;
import net.haizor.hypixelfishinglog.Helper;
import net.haizor.hypixelfishinglog.data.Drop;
import net.haizor.hypixelfishinglog.gui.Button;
import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageSeaCreature extends Page {
    public static boolean showDropPercents = false;

    private SeaCreature creature;
    private SeaCreature.Data scData;

    private static final int dropSpacingX = 32;
    private static final int dropSpacingY = 32;

    public PageSeaCreature(GuiSeaCreatureLog parent, SeaCreature creature) {
        super(parent);
        this.creature = creature;
        this.scData = DataManager.getDataStruct(creature);
        this.title = creature.displayName;
        this.titleColor = Helper.colorFromRarity(creature.rarity);
    }

    @Override
    public void drawPage(int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.drawPage(x, y, mouseX, mouseY, partialTicks);

        int centerX = x + GuiSeaCreatureLog.bookPageWidth / 2 + centeringOffset;
        RenderItem renderItem = log.getRenderItem();

        Gui.drawRect(0, 0, 300,  300, 0);

        String dropsSectionText = "Total Drops";

        if (showDropPercents) {
            dropsSectionText = "Average Drops/Kill";
        }

        log.drawCenteredStringWithoutShadow(log.getFontRenderer(), "Times Caught: " + scData.catchCount, centerX, y + 20, GuiSeaCreatureLog.baseTextColor);
        log.drawCenteredStringWithoutShadow(log.getFontRenderer(), EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + dropsSectionText, centerX, y + 40, GuiSeaCreatureLog.baseTextColor);

        List<Drop> drops = new ArrayList<>(scData.drops.values());

        for (int i = 0; i < drops.size(); i++) {
            int currX = x + (dropSpacingX * i);
            int currY = y;

            int pageWidth = x + (dropSpacingX * 4);

            while (currX >= pageWidth) {
                currX -= (dropSpacingX * 4);
                currY += dropSpacingY;
            }

            ItemStack stack = drops.get(i).toItemStack();
            drawItem(stack, renderItem, currX + 35, currY + 50, mouseX, mouseY);
        }
    }

    public void drawItem(ItemStack stack, RenderItem renderItem, int x, int y, int mouseX, int mouseY) {
        renderItem.zLevel = -1;
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(stack, x, y);

        String drawString = stack.stackSize + "";
        if (showDropPercents) {
            DecimalFormat df = new DecimalFormat("#.##");
            drawString = df.format(((float)stack.stackSize / (float)scData.catchCount)) + "";
        }
        log.drawCenteredString(log.getFontRenderer(), drawString, (x + 8), (y + 16), GuiSeaCreatureLog.baseTextColor);
        //renderItem.renderItemOverlays(log.getFontRenderer(), stack, x, y);
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();

        if (mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (GuiSeaCreatureLog.mouseClicked) {
                    scData.removeDrop(stack);
                }
                List<String> tooltip = new ArrayList<>();
                tooltip.add("Shift + left click to remove this entry.");
                log.displayTooltip = tooltip;
            } else {
                log.hoveredItemStack = stack;
            }

        }
        RenderHelper.disableStandardItemLighting();
        //log.drawCenteredString(log.getFontRenderer(), stack.stackSize + "", x + 10, y + 10, 0xFFFFFF);
    }
}
