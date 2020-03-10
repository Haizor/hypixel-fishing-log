package net.haizor.hypixelfishinglog.data;

import com.google.gson.Gson;
import net.haizor.hypixelfishinglog.seacreature.SeaCreature;
import net.haizor.hypixelfishinglog.seacreature.SeaCreatures;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    public static final String localDataDir = System.getProperty("user.dir") + "\\data";
    public static final String catchDataFile = "\\hypixelskyblockfishing.json";

    public static File dataFile;
    public static Data data;
    private static Gson gson = new Gson();

    public static void init() {
        File directory = new File(localDataDir);

        dataFile = new File(localDataDir + catchDataFile);
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            } else {
                data = gson.fromJson(new FileReader(dataFile), Data.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data == null) {
            data = Data.initEmpty();
            saveJSON();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveJSON();
        }));
    }



    public static void saveJSON() {
        try {
            Writer writer = new FileWriter(dataFile);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SeaCreature.Data getDataStruct(SeaCreature creature) {
        if (!data.creatures.containsKey(creature.id)) {
            data.creatures.put(creature.id, new SeaCreature.Data());
        }
        return data.creatures.get(creature.id);
    }

    public static class Data {
        public Map<String, SeaCreature.Data> creatures = new HashMap<>();
        public Map<String, Drop> goodCatches = new HashMap<>();
        public Map<String, Drop> greatCatches = new HashMap<>();

        public void addToMap(Map<String, Drop> map, ItemStack stack) {
            String name = stack.getDisplayName();
            if (map.containsKey(name)) {
                map.get(name).amount += stack.stackSize;
            } else {
                map.put(name, Drop.fromItemStack(stack));
            }
        }

        public void addToMap(Map<String, Drop> map, Collection<ItemStack> stacks) {
            for (ItemStack i : stacks) {
                addToMap(map, i);
            }
        }

        public static Data initEmpty() {
            Data data = new Data();
            Map<String, SeaCreature.Data> map = new HashMap<String, SeaCreature.Data>();
            for (SeaCreature c : SeaCreatures.seaCreatures) {
                map.put(c.id, new SeaCreature.Data());
            }
            data.creatures = map;
            return data;
        }
    }
}
