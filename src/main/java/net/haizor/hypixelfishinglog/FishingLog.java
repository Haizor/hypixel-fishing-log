package net.haizor.hypixelfishinglog;

import net.haizor.hypixelfishinglog.data.DataManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FishingLog.MODID, version = FishingLog.VERSION, clientSideOnly = true)
public class FishingLog
{
    public static final String MODID = "hypixelfishinglog";
    public static final String VERSION = "1.0.0";

    public static FishingLog INSTANCE;

    public FishingEventHandler eventHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventHandler = new FishingEventHandler();
        DataManager.init();
        Keybinds.register();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(new Keybinds());
    }
}
