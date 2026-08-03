package me.apeiros.alchimiavitae;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.apeiros.alchimiavitae.util.CustomItemStack;

import me.apeiros.alchimiavitae.setup.AlchimiaItems;

/**
 * Holds utility classes and methods of {@link AlchimiaVitae}
 */
public final class AlchimiaUtils {

    private AlchimiaUtils() {}

    public static NamespacedKey createKey(String key) {
        return new NamespacedKey(AlchimiaVitae.i(), key);
    }

    // {{{ Item groups
    /**
     * Holds {@link AlchimiaVitae}'s {@link ItemGroup}s
     */
    public static final class ItemGroups {

        private ItemGroups() {}

        public static final NestedItemGroup MAIN = new NestedItemGroup(
                createKey("alchimia_vitae"),
                new CustomItemStack(Material.TOTEM_OF_UNDYING, "&6Alchimia Vitae"));

        public static final SubItemGroup GENERAL = new SubItemGroup(
                createKey("av_general"),
                MAIN,
                new CustomItemStack(Material.ENCHANTED_BOOK, "&6Alchimia Vitae &7- &2General"));

        public static final SubItemGroup ALTAR_RECIPES = new SubItemGroup(
                createKey("av_altar_recipes"),
                MAIN,
                new CustomItemStack(Material.ENCHANTING_TABLE, "&6Alchimia Vitae &7- &5Transmutation"));

        public static final SubItemGroup INFUSIONS = new SubItemGroup(
                createKey("av_infusions"),
                MAIN,
                new CustomItemStack(Material.NETHER_STAR, "&6Alchimia Vitae &7- &dInfusion"));

    }
    // }}}

    // {{{ Recipe types
    /**
     * Holds {@link AlchimiaVitae}'s {@link RecipeType}s
     */
    public static final class RecipeTypes {

        private RecipeTypes() {}

        public static final RecipeType SOUL_COLLECTOR = new RecipeType(
                createKey("soul_collector_type"), AlchimiaItems.SOUL_COLLECTOR,
                "", "&b&oExtract using the Soul Collector");

        public static final RecipeType PLANT_INFUSION_CHAMBER = new RecipeType(
                createKey("plant_infusion_chamber_type"), AlchimiaItems.PLANT_INFUSION_CHAMBER,
                "", "&b&oInfuse using the Plant Infusion Chamber");

        public static final RecipeType EXP_CRYSTALLIZER = new RecipeType(
                createKey("exp_crystallizer_type"), AlchimiaItems.EXP_CRYSTALLIZER,
                "", "&b&oCrystallize using the Experience Crystallizer");

        public static final RecipeType DIVINE_ALTAR = new RecipeType(
                createKey("divine_altar_type"), AlchimiaItems.DIVINE_ALTAR,
                "", "&b&oFabricate using the Divine Altar");

        public static final RecipeType COSMIC_CAULDRON = new RecipeType(
                createKey("cosmic_cauldron_type"), AlchimiaItems.COSMIC_CAULDRON,
                "", "&b&oBrew using the Cosmic Cauldron");

        public static final RecipeType INFUSION_ALTAR = new RecipeType(
                createKey("infusion_altar_type"), AlchimiaItems.ALTAR_OF_INFUSION,
                "", "&b&oInfuse using the Altar of Infusion");

    }
    // }}}

    // {{{ MiniMessage
    private static final MiniMessage MM = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.reset())
                    .build())
            .build();

    // Serializer
    private static final LegacyComponentSerializer LCS = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    public static String format(String s) {
        return LCS.serialize(MM.deserialize(s));
    }

    public static String itemType(String type) {
        return LCS.serialize(MM.deserialize("<blue>" + type + "<blue> (<italic>AlchimiaVitae<blue>)"));
    }
    // }}}

    // {{{ Methods for making potions
    public static SlimefunItemStack makePotion(
            String id,
            String name,
            Color color,
            Collection<PotionEffect> effects,
            boolean splash,
            String... lore) {

        ItemStack potion = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        if (potionMeta != null) {
            potionMeta.setDisplayName(name);
            potionMeta.setColor(color);

            for (PotionEffect e : effects) {
                potionMeta.addCustomEffect(e, true);
            }

            potion.setItemMeta(potionMeta);
        }

        return new SlimefunItemStack(id, potion, name, lore);
    }

    public static SlimefunItemStack makePotion(
            String id,
            String name,
            Color color,
            Map<PotionEffectType, int[]> effects,
            boolean splash,
            String... lore) {

        List<PotionEffect> new_effects = new LinkedList<PotionEffect>();

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            new_effects.add(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true));
        }

        return makePotion(id, name, color, new_effects, splash, lore);
    }
    // }}}

    // {{{ Other utility methods
    public static boolean equalsAny(Object base, Object... comparisons) {
        if (base == null || comparisons == null || comparisons.length == 0) return false;
        boolean first = base.equals(comparisons[0]);

        for (int i = 1; i < comparisons.length; i++) {
            if (first) return first;
            first = first || base.equals(comparisons[i]);
        }

        return first;
    }

    public static boolean equalsAll(Object base, Object... comparisons) {
        if (base == null || comparisons == null || comparisons.length == 0) return false;
        boolean first = base.equals(comparisons[0]);

        for (int i = 1; i < comparisons.length; i++) {
            if (!first) return first;
            first = first && base.equals(comparisons[i]);
        }

        return first;
    }
    // }}}

}
