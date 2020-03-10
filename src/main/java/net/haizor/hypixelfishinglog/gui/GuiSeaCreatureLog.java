package net.haizor.hypixelfishinglog.gui;

import net.haizor.hypixelfishinglog.FishingLog;
import net.haizor.hypixelfishinglog.data.DataManager;
import net.haizor.hypixelfishinglog.gui.pages.*;
import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.haizor.hypixelfishinglog.seacreature.SeaCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GuiSeaCreatureLog extends GuiScreen {
    public static final ResourceLocation bookGuiTextures = new ResourceLocation(FishingLog.MODID, "textures/gui/book.png");
    private static final ResourceLocation bookGuiTexturesB = new ResourceLocation(FishingLog.MODID, "textures/gui/book2.png");

    public static final int bookImageWidth = 192;
    public static final int bookImageHeight = 192;

    public static final int baseTextColor = 0xFFFFFF;

    public static int bookPageWidth = 146;
    public static int bookPageHeight = 180;

    int width;
    int height;

    int x;
    int y;

    private ScaledResolution scaled;

    public static int currPage;

    public ItemStack hoveredItemStack;
    public List<String> displayTooltip;
    public List<Page> pages = new ArrayList<>();
    public static boolean mouseClicked = false;

    public int currButton = 0;

    public GuiSeaCreatureLog(Minecraft mc) {
        this.mc = mc;
        scaled = new ScaledResolution(mc);

        width = scaled.getScaledWidth();
        height = scaled.getScaledHeight();

        x = (width - bookImageWidth) / 2;
        y = (height - bookImageHeight) / 2;

        pages.add(new PageSeaCreature(this, SeaCreatures.SQUID));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_WALKER));
        pages.add(new PageSeaCreature(this, SeaCreatures.NIGHT_SQUID));
        pages.add(new PageSeaCreature(this, SeaCreatures.FROZEN_STEVE));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_GUARDIAN));
        pages.add(new PageSeaCreature(this, SeaCreatures.FROSTY));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_WITCH));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_ARCHER));
        pages.add(new PageSeaCreature(this, SeaCreatures.MONSTER_OF_THE_DEEP));
        pages.add(new PageSeaCreature(this, SeaCreatures.GRINCH));
        pages.add(new PageSeaCreature(this, SeaCreatures.CATFISH));
        pages.add(new PageSeaCreature(this, SeaCreatures.CARROT_KING));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_LEECH));
        pages.add(new PageSeaCreature(this, SeaCreatures.GUARDIAN_DEFENDER));
        pages.add(new PageSeaCreature(this, SeaCreatures.DEEP_SEA_PROTECTOR));
        pages.add(new PageSeaCreature(this, SeaCreatures.WATER_HYDRA));
        pages.add(new PageSeaCreature(this, SeaCreatures.SEA_EMPEROR));
        pages.add(new PageSeaCreature(this, SeaCreatures.YETI));
        pages.add(new PageTreasureCatches(this, DataManager.data.goodCatches, I18n.format(PageTreasureCatches.GOOD_CATCH_TITLE_KEY)));
        pages.add(new PageTreasureCatches(this, DataManager.data.greatCatches, I18n.format(PageTreasureCatches.GREAT_CATCH_TITLE_KEY)));
        pages.add(new PageSettings(this));

        pages.add(0, new PageTableOfContents(this, pages));
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new PaginationButton(this, x + 112, y + bookPageHeight + 4, PaginationButton.Type.FORWARD));
        buttonList.add(new PaginationButton(this, x + 56, y + bookPageHeight + 4, PaginationButton.Type.BACKWARD));
        buttonList.add(new FirstPageButton(this, x + 26, y + bookPageHeight + 4));
        buttonList.add(new LastPageButton(this, x + 142, y + bookPageHeight + 4));
        buttonList.add(new TogglePercentButton(this, x + 84, y + bookPageHeight));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        mouseClicked = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        width = scaled.getScaledWidth();
        height = scaled.getScaledHeight();

        x = (width - bookImageWidth) / 2;
        y = (height - bookImageHeight) / 2;

        Page pageLeft = null;
        Page pageRight = null;

        try {
            pageLeft = pages.get(currPage);
            pageRight = pages.get(currPage + 1);
        } catch (IndexOutOfBoundsException e) { }

        drawPage(pageLeft, x - (bookPageWidth / 2), y, mouseX, mouseY, partialTicks, currPage);
        drawPage(pageRight, x + (bookPageWidth / 2), y, mouseX, mouseY, partialTicks, currPage + 1);

        GlStateManager.color(1, 1, 1);

        mc.getTextureManager().bindTexture(bookGuiTexturesB);
        drawTexturedModalRect(x + 14, y + 174, 0, 180, 158, 32);

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (hoveredItemStack != null) {
            renderTooltip(hoveredItemStack, mouseX, mouseY);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }

        if (displayTooltip != null) {
            this.drawHoveringText(displayTooltip, mouseX, mouseY);
        }

        hoveredItemStack = null;
        mouseClicked = false;
        displayTooltip = null;
    }

    public void drawPage(Page p, int x, int y, int mouseX, int mouseY, float partialTicks, int pageNumber) {
        zLevel = -999;

        GlStateManager.color(1.0f, 1.0f, 1.0f);

        if (pageNumber % 2 == 0) {
            mc.getTextureManager().bindTexture(bookGuiTexturesB);
        } else {
            mc.getTextureManager().bindTexture(bookGuiTextures);
        }

        drawTexturedModalRect(x + 20, y, 0, 0, bookPageWidth, bookPageHeight);
        mc.getTextureManager().bindTexture(bookGuiTextures);
        if (p != null) {
            p.drawPage(x, y, mouseX, mouseY, partialTicks);
        }
    }

    public void drawCenteredStringWithoutShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawString(text, (x - fontRenderer.getStringWidth(text) / 2), y, color);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.isCreated()) {
            try {
                Page pageLeft = pages.get(currPage);
                Page pageRight = pages.get(currPage + 1);

                int mouseX = Mouse.getEventX();

                //TODO: completely arbitrary. fix this
                if (mouseX > mc.displayWidth / 2) {
                    pageRight.handleMouseInput();
                } else {
                    pageLeft.handleMouseInput();
                }
            } catch (IndexOutOfBoundsException e) {}
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void renderTooltip(ItemStack i, int x, int y) {
        zLevel = 300;
        this.renderToolTip(i, x, y);
        zLevel = -1;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public RenderItem getRenderItem() {
        return this.itemRender;
    }

    public int getMaxPage() {
        return pages.size() % 2 == 0 ? pages.size() - 2 : pages.size() - 1;
    }

    public static class PaginationButton extends Button {
        GuiSeaCreatureLog parent;
        Type type;
        public PaginationButton(GuiSeaCreatureLog log, int x, int y, Type type) {
            super(log, x, y, "");
            parent = log;
            this.type = type;
            this.width = 18;
            this.height = 10;
            this.defaultDraw = false;
            this.playSound = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            super.drawButton(mc, mouseX, mouseY);

            mc.renderEngine.bindTexture(bookGuiTextures);
            drawTexturedModalRect(xPosition, yPosition, getTextureX(), getTextureY(), width, height);
        }

        @Override
        public void onActivated() {
            super.onActivated();

            switch(type) {
                case FORWARD:
                    if (parent.pages.size() > currPage + 2) currPage += 2;
                    break;
                case BACKWARD:
                    if (0 <= currPage - 2) currPage -= 2;
                    break;
            }
        }

        public int getTextureX() {
            if (hovered) return 26; else return 3;
        }

        public int getTextureY() {
            int y = 194;
            if (type == Type.BACKWARD) y += 13;
            return y;
        }

        public enum Type {
            FORWARD,
            BACKWARD
        }
    }

    public static class FirstPageButton extends Button {
        GuiSeaCreatureLog parent;

        public FirstPageButton(GuiSeaCreatureLog log, int x, int y) {
            super(log, x, y, "");
            parent = log;
            this.width = 18;
            this.height = 10;
            this.defaultDraw = false;
            this.playSound = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            super.drawButton(mc, mouseX, mouseY);

            mc.renderEngine.bindTexture(bookGuiTextures);
            drawTexturedModalRect(xPosition, yPosition, getTextureX(), 241, width, height);
        }

        public int getTextureX() {
            if (hovered) return 26; else return 3;
        }

        @Override
        public void onActivated() {
            super.onActivated();
            GuiSeaCreatureLog.currPage = 0;
        }
    }

    public static class LastPageButton extends Button {
        GuiSeaCreatureLog parent;

        public LastPageButton(GuiSeaCreatureLog log, int x, int y) {
            super(log, x, y, "");
            parent = log;
            this.width = 18;
            this.height = 10;
            this.defaultDraw = false;
            this.playSound = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            super.drawButton(mc, mouseX, mouseY);

            mc.renderEngine.bindTexture(bookGuiTextures);
            drawTexturedModalRect(xPosition, yPosition, getTextureX(), 241, width, height);
        }

        public int getTextureX() {
            if (hovered) return 71; else return 48;
        }

        @Override
        public void onActivated() {
            super.onActivated();
            GuiSeaCreatureLog.currPage = parent.getMaxPage();
        }
    }

    public static class TogglePercentButton extends Button {
        public TogglePercentButton(GuiSeaCreatureLog log, int baseX, int baseY) {
            super(log, baseX, baseY, 18, 18, "");
            this.defaultDraw = false;
            this.playSound = false;
        }

        @Override
        public void onActivated() {
            super.onActivated();
            PageSeaCreature.showDropPercents = !PageSeaCreature.showDropPercents;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            super.drawButton(mc, mouseX, mouseY);

            mc.renderEngine.bindTexture(GuiSeaCreatureLog.bookGuiTextures);
            drawTexturedModalRect(xPosition, yPosition, getTextureX(), 220, width, height);
        }

        public int getTextureX() {
            if (hovered) {
                return 26;
            } else {
                return 3;
            }
        }
    }
}
