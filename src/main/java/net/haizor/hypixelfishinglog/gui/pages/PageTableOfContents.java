package net.haizor.hypixelfishinglog.gui.pages;

import net.haizor.hypixelfishinglog.gui.Button;
import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;

import java.util.List;

public class PageTableOfContents extends Page {
    public int currYScroll;
    public int minYScroll = 0;
    public int displayCount = 10;
    public int maxYScroll;
    public int linkSpacing = 12;

    public PageTableOfContents(GuiSeaCreatureLog parent, List<Page> pages) {
        super(parent);

        title = "Table of Contents";

        for (int i = 0; i < pages.size(); i++) {
            Page currPage = pages.get(i);

            int pageNum = i + 1;
            pageNum = pageNum % 2 == 0 ? pageNum : pageNum - 1;

            addButton(new LinkButton(this, parent, currPage, 35,  25 + i * linkSpacing, pageNum));
        }

        maxYScroll = (pages.size() - displayCount) * linkSpacing;
    }

    @Override
    public void drawPage(int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.drawPage(x, y, mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() {
        int scroll = Integer.signum(Mouse.getEventDWheel());
        scroll -= scroll * 2;
        currYScroll += scroll * linkSpacing;

        if (currYScroll > maxYScroll) currYScroll = maxYScroll;
        if (currYScroll < minYScroll) currYScroll = minYScroll;
    }

    public static class LinkButton extends Button {
        Page page;
        PageTableOfContents parent;
        int pageNumber;
        public LinkButton(PageTableOfContents parent, GuiSeaCreatureLog log, Page page, int baseX, int baseY, int pageNumber) {
            super(log, baseX, baseY, page.title);
            this.page = page;
            this.parent = parent;
            this.pageNumber = pageNumber;
            this.width = mc.fontRendererObj.getStringWidth(page.title);
            this.height = mc.fontRendererObj.FONT_HEIGHT;
            this.defaultDraw = false;
        }

        @Override
        public void onActivated() {
            super.onActivated();

            GuiSeaCreatureLog.currPage = pageNumber;
        }

        @Override
        public void drawButton(int x, int y, int mouseX, int mouseY, float partialTicks) {
            this.xPosition = baseX + x;
            this.yPosition = baseY + y - parent.currYScroll;

            int color = hovered ? 0xFFFF88 : 0xFFFFFF;

            if (this.yPosition < y + 20 + (parent.linkSpacing * parent.displayCount) && this.yPosition > y + 20) {
                doActivateCheck(mouseX, mouseY);
                mc.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + page.title, xPosition, yPosition, color, true);
            }



            //super.drawButton(mc, mouseX, mouseY);
        }
    }
}
