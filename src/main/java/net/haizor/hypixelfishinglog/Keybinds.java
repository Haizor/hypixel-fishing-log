package net.haizor.hypixelfishinglog;

import net.haizor.hypixelfishinglog.gui.GuiSeaCreatureLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static String KEYBIND_CATEGORY = "key.categories.sea_creatures";

    public static KeyBinding openSeaCreatureLog;

    public static void register() {
        openSeaCreatureLog = new KeyBinding("key.open_sea_creature_log", Keyboard.KEY_B, KEYBIND_CATEGORY);
        ClientRegistry.registerKeyBinding(openSeaCreatureLog);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (openSeaCreatureLog.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSeaCreatureLog(Minecraft.getMinecraft()));
        }
    }
}
