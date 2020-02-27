package net.haizor.hypixelfishinglog;

import net.haizor.hypixelfishinglog.data.DataManager;
import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.haizor.hypixelfishinglog.seacreature.SeaCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class FishingEventHandler {
    public ItemStack[] prevInventoryState;
    public SeaCreature lastKilledSeaCreature;
    public boolean goodCatch;
    public boolean greatCatch;
    public int pickupTime = 6;
    public int pickupTimer = 0;

    @SubscribeEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event) {
        String unformatted = event.message.getUnformattedText();
        SeaCreature creature = SeaCreatures.getSeaCreatureFromMessage(event.message);

        if (creature != null) {
            DataManager.getDataStruct(creature).catchCount++;
        } else if (unformatted.contains("GOOD CATCH!")) {
            pickupTimer = 0;
            goodCatch = true;
        } else if (unformatted.contains("GREAT CATCH!")) {
            pickupTimer = 0;
            greatCatch = true;
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        DataManager.saveJSON();
    }

    public boolean ifPlayerKilled(LivingDeathEvent deathEvent) {
        MovingObjectPosition mouseOver = Minecraft.getMinecraft().objectMouseOver;
        boolean isMouseOver = (mouseOver != null &&
                mouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY &&
                mouseOver.entityHit != null &&
                mouseOver.entityHit.getEntityId() == deathEvent.entity.getEntityId());

        AxisAlignedBB box = deathEvent.entity.getEntityBoundingBox();

        boolean isHookKill = false;

        World w = Minecraft.getMinecraft().theWorld;
        List<EntityFishHook> fishHooks = w.getEntitiesWithinAABB(EntityFishHook.class, box.expand(5, 5, 5));
        for (EntityFishHook hook : fishHooks) {
            if (hook.angler.equals(Minecraft.getMinecraft().thePlayer)) {
                isHookKill = true;
            }
        }

        return isHookKill || isMouseOver;
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent deathEvent) {
        if (ifPlayerKilled(deathEvent)) {
            World w = Minecraft.getMinecraft().theWorld;

            //TODO: nail this down
            AxisAlignedBB box = deathEvent.entity.getEntityBoundingBox().expand(5, 50,5);

            if (box != null) {
                List<EntityArmorStand> armorStands = w.getEntitiesWithinAABB(EntityArmorStand.class, box);

                if (armorStands.size() > 0) {
                    for (EntityArmorStand stand : armorStands) {
                        SeaCreature creature = SeaCreatures.getSeaCreatureFromName(stand.getDisplayName());
                        if (creature != null) {
                            lastKilledSeaCreature = creature;
                            if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.inventory != null)
                                Minecraft.getMinecraft().thePlayer.inventory.inventoryChanged = false;

                            pickupTimer = 0;
                            break;
                        }
                    }

                } else {
                    System.out.println("UH OH STINKY :((((");
                }
            }
        }
    }

    @SubscribeEvent
    public void onPostTick(TickEvent.ClientTickEvent tick) {
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.inventory != null) {
            if (tick.phase == TickEvent.Phase.END) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if (pickupTimer < pickupTime) {
                    if (player.inventory.inventoryChanged && prevInventoryState != null) {
                        List<ItemStack> difference = Helper.getNewItemsStacks(Arrays.asList(player.inventory.mainInventory), Arrays.asList(prevInventoryState));
                        if (difference.size() > 0) {
                            if (lastKilledSeaCreature != null) {
                                SeaCreature.Data data = DataManager.getDataStruct(lastKilledSeaCreature);
                                data.addDrops(difference);
                            } else if (goodCatch) {
                                DataManager.data.addToMap(DataManager.data.goodCatches, difference);
                            } else if (greatCatch) {
                                DataManager.data.addToMap(DataManager.data.greatCatches, difference);
                            }
                        }
                    }
                    pickupTimer++;
                } else if (pickupTimer == pickupTime) {
                    DataManager.saveJSON();
                    lastKilledSeaCreature = null;
                    goodCatch = false;
                    greatCatch = false;
                }
                prevInventoryState = (ItemStack[]) Arrays.copyOf(player.inventory.mainInventory, player.inventory.mainInventory.length, ItemStack[].class);
            }
        }
    }
}
