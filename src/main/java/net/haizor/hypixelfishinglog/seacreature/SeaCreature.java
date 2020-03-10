package net.haizor.hypixelfishinglog.seacreature;

import net.haizor.hypixelfishinglog.data.Drop;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SeaCreature {
    public String id;
    public Rarity rarity;

    public SeaCreature(String id, Rarity rarity) {
        this.id = id;
        this.rarity = rarity;

        SeaCreatures.seaCreatures.add(this);
    }

    public String getDisplayName() {
        return I18n.format(String.format("seacreature.%s.display_name", id.toLowerCase()));
    }

    public String getCatchText() {
        return I18n.format(String.format("seacreature.%s.catch_text", id.toLowerCase()));
    }

    public String getMobName() {
        return I18n.format(String.format("seacreature.%s.mob_name", id.toLowerCase()));
    }

    public static class Data {
        public int catchCount = 0;
        public Map<String, Drop> drops = new HashMap<>();

        public Data() {}

        public Data(int catchCount) {
            this.catchCount = catchCount;
        }

        public void addDrop(ItemStack stack) {
            String name = stack.getDisplayName();
            if (drops.containsKey(name)) {
                drops.get(name).amount += stack.stackSize;
            } else {
                drops.put(name, Drop.fromItemStack(stack));
            }
        }

        public void addDrops(Collection<ItemStack> stacks) {
            for (ItemStack i : stacks) {
                addDrop(i);
            }
        }

        public void removeDrop(ItemStack stack) {
            String name = stack.getDisplayName();
            drops.remove(name);
        }
    }

    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY
    }
}
