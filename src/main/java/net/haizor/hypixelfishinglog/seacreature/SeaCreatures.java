package net.haizor.hypixelfishinglog.seacreature;

import net.minecraft.util.IChatComponent;

import java.util.*;

public class SeaCreatures {
    public static final String CHAT_FORMATTER = "\\u00A7";

    public static SeaCreature SQUID;
    public static SeaCreature SEA_WALKER;
    public static SeaCreature NIGHT_SQUID;
    public static SeaCreature FROZEN_STEVE;
    public static SeaCreature SEA_GUARDIAN;
    public static SeaCreature FROSTY;
    public static SeaCreature SEA_WITCH;
    public static SeaCreature SEA_ARCHER;
    public static SeaCreature MONSTER_OF_THE_DEEP;
    public static SeaCreature GRINCH;
    public static SeaCreature CATFISH;
    public static SeaCreature CARROT_KING;
    public static SeaCreature SEA_LEECH;
    public static SeaCreature GUARDIAN_DEFENDER;
    public static SeaCreature DEEP_SEA_PROTECTOR;
    public static SeaCreature WATER_HYDRA;
    public static SeaCreature SEA_EMPEROR;
    public static SeaCreature YETI;

    public static List<SeaCreature> seaCreatures = new ArrayList<>();

    public static SeaCreature getSeaCreatureFromMessage(IChatComponent comp) {
        String message = comp.getUnformattedText();
        for (SeaCreature c : seaCreatures) {
            if (c.getCatchText().equals(message)) return c;
        }
        return null;
    }

    public static SeaCreature getSeaCreatureFromName(IChatComponent name) {
        String unformattedText = name.getUnformattedText();

        try {
            String nameStripped = unformattedText.split("\\u00A7c")[1].split("\\u00A7")[0];
            nameStripped = nameStripped.substring(0, nameStripped.length() - 1);

            for (SeaCreature c : seaCreatures) {
                if (nameStripped.equals(c.getMobName())) {
                    return c;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return null;
    }

    static {
        SQUID = new SeaCreature("SQUID", SeaCreature.Rarity.COMMON);
        SEA_WALKER = new SeaCreature("SEA_WALKER", SeaCreature.Rarity.COMMON);
        NIGHT_SQUID = new SeaCreature("NIGHT_SQUID", SeaCreature.Rarity.COMMON);
        FROZEN_STEVE = new SeaCreature("FROZEN_STEVE", SeaCreature.Rarity.COMMON);
        SEA_GUARDIAN = new SeaCreature("SEA_GUARDIAN", SeaCreature.Rarity.COMMON);
        FROSTY = new SeaCreature("FROSTY", SeaCreature.Rarity.COMMON);
        SEA_WITCH = new SeaCreature("SEA_WITCH", SeaCreature.Rarity.UNCOMMON);
        SEA_ARCHER = new SeaCreature("SEA_ARCHER", SeaCreature.Rarity.UNCOMMON);
        MONSTER_OF_THE_DEEP = new SeaCreature("MONSTER_OF_THE_DEEP", SeaCreature.Rarity.UNCOMMON);
        GRINCH = new SeaCreature("GRINCH", SeaCreature.Rarity.UNCOMMON);
        CATFISH = new SeaCreature("CATFISH", SeaCreature.Rarity.RARE);
        CARROT_KING = new SeaCreature("CARROT_KING", SeaCreature.Rarity.RARE);
        SEA_LEECH = new SeaCreature("SEA_LEECH", SeaCreature.Rarity.RARE);
        GUARDIAN_DEFENDER = new SeaCreature("GUARDIAN_DEFENDER", SeaCreature.Rarity.EPIC);
        DEEP_SEA_PROTECTOR = new SeaCreature("DEEP_SEA_PROTECTOR", SeaCreature.Rarity.EPIC);
        WATER_HYDRA = new SeaCreature("WATER_HYDRA", SeaCreature.Rarity.LEGENDARY);
        SEA_EMPEROR = new SeaCreature("SEA_EMPEROR", SeaCreature.Rarity.LEGENDARY);
        YETI = new SeaCreature("YETI", SeaCreature.Rarity.LEGENDARY);
    }
}
