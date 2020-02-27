package net.haizor.hypixelfishinglog.data;

import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Drop {
    public int amount;
    public int meta;
    public String registryName;
    public String nbt;

    public static Drop fromItemStack(ItemStack i) {
        Drop drop = new Drop();
        drop.amount = i.stackSize;
        drop.meta = i.getMetadata();
        drop.registryName = i.getItem().getRegistryName();
        drop.nbt = i.getTagCompound().toString();
        return drop;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = GameRegistry.makeItemStack(registryName, meta, amount, nbt);
        return itemStack;
    }

    public static class SkullInfo {
        public String id;
        public String texture;
    }
}
