package net.haizor.hypixelfishinglog.gui.pages;

import net.haizor.hypixelfishinglog.data.DataManager;
import net.haizor.hypixelfishinglog.gui.Button;
import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class PageSettings extends Page {
    private static final String TITLE_KEY = "gui.seacreaturelog.page.settings.title";

    public PageSettings(GuiSeaCreatureLog parent) {
        super(parent);
        this.title = I18n.format(TITLE_KEY);
        this.addButton(new WipeDataButton(parent, 42, 30));
    }

    public static class WipeDataButton extends Button {
        public List<String> tooltip;
        GuiSeaCreatureLog log;

        public WipeDataButton(GuiSeaCreatureLog log, int baseX, int baseY) {
            super(log, baseX, baseY, 100, 20, "Wipe Data");
            this.log = log;
            tooltip = new ArrayList<>();
            tooltip.add("Shift-click if you really want to do this.");
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (hovered) {
                log.displayTooltip = tooltip;
            }
            super.drawButton(mc, mouseX, mouseY);
        }

        @Override
        public void onActivated() {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                DataManager.data = DataManager.Data.initEmpty();
                DataManager.saveJSON();

                Minecraft.getMinecraft().displayGuiScreen(new GuiSeaCreatureLog(Minecraft.getMinecraft()));
            }
        }
    }
}
