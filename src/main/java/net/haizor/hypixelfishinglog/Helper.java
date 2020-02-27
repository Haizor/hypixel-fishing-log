package net.haizor.hypixelfishinglog;

import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;
import java.util.stream.Collectors;

public class Helper {
    public static <T> Set<T> difference(T[] p1, T[] p2) {
        Set<T> setA = new HashSet<T>(Arrays.asList(p1));
        setA.removeAll(Arrays.asList(p2));

        return setA;
    }

    public static boolean itemsRoughEqual(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;
        return a.getItem().equals(b.getItem()) && a.getDisplayName().equals(b.getDisplayName());
    }

    public static List<ItemStack> getNewItemsStacks(Collection<ItemStack> cInv, Collection<ItemStack> pInv) {
        List<ItemStack> currInv = concatItemStacks(cInv);
        List<ItemStack> prevInv = concatItemStacks(pInv);

        List<ItemStack> newItems = new ArrayList<>();

        for (ItemStack currItem : currInv) {
            Optional<ItemStack> equalStack = prevInv.stream().filter(item -> itemsRoughEqual(currItem, item)).findFirst();
            if (equalStack.isPresent()) {
                ItemStack stackB = equalStack.get();
                int stackSize = Math.max(stackB.stackSize, currItem.stackSize) - Math.min(currItem.stackSize, stackB.stackSize);
                if (stackSize != 0) {
                    stackB.stackSize = stackSize;
                    prevInv.remove(stackB);
                    newItems.add(stackB);
                }
            } else {
                newItems.add(currItem);
            }
        }

        for (ItemStack currItem : prevInv) {
            Optional<ItemStack> equalStack = currInv.stream().filter(item -> itemsRoughEqual(currItem, item)).findFirst();
            if (equalStack.isPresent()) {
                ItemStack stackB = equalStack.get();
                int stackSize = Math.max(stackB.stackSize, currItem.stackSize) - Math.min(currItem.stackSize, stackB.stackSize);
                if (stackSize != 0) {
                    ItemStack newStack = stackB.copy();
                    newStack.stackSize = stackSize;
                    newItems.add(newStack);
                }
            } else {
                newItems.add(currItem);
            }
        }

        newItems = newItems.stream().filter(item -> item != null).collect(Collectors.toList());

        return newItems;
    }

    public static List<ItemStack> concatItemStacks(Collection<ItemStack> inv) {
        ArrayList<ItemStack> newItems = new ArrayList<>();

        for (ItemStack i : inv) {
            if (i != null) {
                Optional<ItemStack> equalStack = newItems.stream().filter(item -> itemsRoughEqual(i, item)).findFirst();
                if (equalStack.isPresent()) {
                    equalStack.get().stackSize += i.stackSize;
                } else {
                    newItems.add(i.copy());
                }
            }
        }

        return newItems;
    }

    public static int colorFromRarity(SeaCreature.Rarity rarity) {
        switch(rarity) {
            case COMMON:
                return 0xFFFFFF;
            case UNCOMMON:
                return 0x55FF55;
            case RARE:
                return 0x5555FF;
            case EPIC:
                return 0xAA00AA;
            case LEGENDARY:
                return 0xFFAA00;
        }
        return 0;
    }
}
