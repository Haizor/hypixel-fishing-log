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

    public static Map<String, SeaCreature> seaCreatures = new HashMap<String, SeaCreature>();

    public static SeaCreature getSeaCreatureFromMessage(IChatComponent comp) {
        String message = comp.getUnformattedText();
        return seaCreatures.get(message);
    }

    public static SeaCreature getSeaCreatureFromName(IChatComponent name) {
        String unformattedText = name.getUnformattedText();

        try {
            String nameStripped = unformattedText.split("\\u00A7c")[1].split("\\u00A7")[0];
            nameStripped = nameStripped.substring(0, nameStripped.length() - 1);

            for (SeaCreature c : seaCreatures.values()) {
                if (nameStripped.equals(c.displayName)) {
                    return c;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return null;
    }

    static {
        SQUID = new SeaCreature("SQUID", "You caught a lowly Squid.", "Squid", SeaCreature.Rarity.COMMON);
        SEA_WALKER = new SeaCreature("SEA_WALKER", "From the depths of the waters, you've reeled in a Sea Walker.", "Sea Walker", SeaCreature.Rarity.COMMON);
        NIGHT_SQUID = new SeaCreature("NIGHT_SQUID", "Pitch darkness reveals you've caught a Night Squid.", "Night Squid", SeaCreature.Rarity.COMMON);
        FROZEN_STEVE = new SeaCreature("FROZEN_STEVE", "Frozen Steve fell into the pond long ago, never to resurface...until now!", "Frozen Steve", SeaCreature.Rarity.COMMON);
        SEA_GUARDIAN = new SeaCreature("SEA_GUARDIAN", "You've stumbled upon a patrolling Sea Guardian.", "Sea Guardian", SeaCreature.Rarity.COMMON);
        FROSTY = new SeaCreature("FROSTY", "It's a snowman! He looks harmless", "Frosty", SeaCreature.Rarity.COMMON);
        SEA_WITCH = new SeaCreature("SEA_WITCH", "It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious!", "Sea Witch", SeaCreature.Rarity.UNCOMMON);
        SEA_ARCHER = new SeaCreature("SEA_ARCHER", "From the depths of the waters, you've reeled in a Sea Archer.", "Sea Archer", SeaCreature.Rarity.UNCOMMON);
        MONSTER_OF_THE_DEEP = new SeaCreature("MONSTER_OF_THE_DEEP", "The Monster of the Deep emerges from the dark depths...", "Rider of the Deep", SeaCreature.Rarity.UNCOMMON);
        GRINCH = new SeaCreature("GRINCH", "The Grinch stole Jerry's Gifts...get them back!", "Grinch", SeaCreature.Rarity.UNCOMMON);
        CATFISH = new SeaCreature("CATFISH", "You have found a Catfish, don't let it steal your catches!", "Catfish", SeaCreature.Rarity.RARE);
        CARROT_KING = new SeaCreature("CARROT_KING", "Is this even a fish? It's the Carrot King!", "Carrot King", SeaCreature.Rarity.RARE);
        SEA_LEECH = new SeaCreature("SEA_LEECH", "Gross! A Sea Leech!", "Sea Leech", SeaCreature.Rarity.RARE);
        GUARDIAN_DEFENDER = new SeaCreature("GUARDIAN_DEFENDER", "You've discovered a Guardian Defender of the sea.", "Guardian Defender", SeaCreature.Rarity.EPIC);
        DEEP_SEA_PROTECTOR = new SeaCreature("DEEP_SEA_PROTECTOR", "You have awoken the Deep Sea Protector, prepare for a battle!", "Deep Sea Protector", SeaCreature.Rarity.EPIC);
        WATER_HYDRA = new SeaCreature("WATER_HYDRA", "The Water Hydra has come to test your strength.", "Water Hydra", SeaCreature.Rarity.LEGENDARY);
        SEA_EMPEROR = new SeaCreature("SEA_EMPEROR", "The Sea Emperor arises from the depths...", "The Emperor", SeaCreature.Rarity.LEGENDARY);
        YETI = new SeaCreature("YETI", "What is this creature!?", "Yeti", SeaCreature.Rarity.LEGENDARY);
    }
}
