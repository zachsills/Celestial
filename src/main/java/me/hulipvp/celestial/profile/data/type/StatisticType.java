package me.hulipvp.celestial.profile.data.type;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;

public enum StatisticType {

    COAL_MINED("coalMined", Material.COAL_ORE),
    GOLD_MINED("goldMined", Material.GOLD_ORE),
    IRON_MINED("ironMined", Material.IRON_ORE),
    LAPIS_MINED("lapisMined", Material.LAPIS_ORE),
    DIAMONDS_MINED("diamondsMined", Material.DIAMOND_ORE),
    EMERALDS_MINED("emeraldsMined", Material.EMERALD_ORE),
    REDSTONE_MINED("redstoneMined", Material.REDSTONE_ORE);

    @Getter private final String key;
    @Getter private final Material material;

    StatisticType(final String key, final Material material) {
        this.key = key;
        this.material = material;
    }

    public static StatisticType getByKey(final String key) {
        return Arrays.stream(StatisticType.values())
                .filter(type -> type.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public static StatisticType getByMaterial(final Material material) {
        return Arrays.stream(StatisticType.values())
                .filter(type -> type.getMaterial() == material)
                .findFirst()
                .orElse(null);
    }
}
