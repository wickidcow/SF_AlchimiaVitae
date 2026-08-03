package me.apeiros.alchimiavitae.setup;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.util.CustomItemStack;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.DyeListener;
import me.apeiros.alchimiavitae.listeners.infusion.FishingRodListener;
import me.apeiros.alchimiavitae.listeners.infusion.HoeListener;
import me.apeiros.alchimiavitae.listeners.infusion.MeleeWeaponListener;
import me.apeiros.alchimiavitae.listeners.infusion.RangedWeaponListener;
import me.apeiros.alchimiavitae.listeners.infusion.TotemListener;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.MoltenMysteryMetal;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.setup.items.potions.BenevolentBrew;
import me.apeiros.alchimiavitae.setup.items.potions.MalevolentConcoction;
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfOsmosis;

/**
 * Sets up items, {@link Listener}s, and {@link Research}es
 */
public class Setup {

    // {{{ Main
    public static void setup(AlchimiaVitae instance) {
        // Register main item group
        AlchimiaUtils.ItemGroups.MAIN.register(instance);

        // Items
        setupItems(instance);

        // Listeners
        setupListeners(instance);

        // Researches
        setupResearches(instance);
    }
    // }}}

    // {{{ Items
    private static void setupItems(AlchimiaVitae instance) {
        // Items
        // {{{ Soul Collector & Condensed Soul
        new SoulCollector(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        CustomItemStack condensedSoulRecipeItem = new CustomItemStack(
                Material.DROWNED_SPAWN_EGG,
                "&bAny Mob",
                "&7Wither Skeletons and",
                "&7Withers have a chance",
                "&7to drop more souls...");

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.CONDENSED_SOUL, AlchimiaUtils.RecipeTypes.SOUL_COLLECTOR, new ItemStack[] {
                null, null, null,
                null, condensedSoulRecipeItem, null,
                null, null, null
        }).register(instance);
        // }}}

        // {{{ Plants
        // Machine
        new PlantInfusionChamber(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        // Plants
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.LIGHT_MAGIC_PLANT, AlchimiaUtils.RecipeTypes.PLANT_INFUSION_CHAMBER, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), SlimefunItems.MAGIC_LUMP_3.item(), null,
                null, null, null,
                null, null, null
        }).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARK_MAGIC_PLANT, AlchimiaUtils.RecipeTypes.PLANT_INFUSION_CHAMBER, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), AlchimiaItems.CONDENSED_SOUL.item(), null,
                null, null, null,
                null, null, null
        }).register(instance);

        // Essence
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.LIGHT_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                AlchimiaItems.LIGHT_MAGIC_PLANT.item(), null, null,
                null, null, null,
                null, null, null
        }, new CustomItemStack(AlchimiaItems.LIGHT_ESSENCE, 4)).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARK_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                AlchimiaItems.DARK_MAGIC_PLANT.item(), null, null,
                null, null, null,
                null, null, null
        }, new CustomItemStack(AlchimiaItems.DARK_ESSENCE, 4)).register(instance);
        // }}}

        // {{{ EXP Crystallizer
        new EXPCrystallizer(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.EXP_CRYSTAL, AlchimiaUtils.RecipeTypes.EXP_CRYSTALLIZER, new ItemStack[] {
                new CustomItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 4), null, null,
                null, null, null,
                null, null, null
        }).register(instance);
        // }}}

        // {{{ Ingots
        // Illumium
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.ILLUMIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1.item(), AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.MAGIC_LUMP_1.item(),
                AlchimiaItems.LIGHT_ESSENCE.item(), SlimefunItems.STEEL_INGOT.item(), AlchimiaItems.LIGHT_ESSENCE.item(),
                SlimefunItems.MAGIC_LUMP_1.item(), AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.MAGIC_LUMP_1.item()
        }, new CustomItemStack(AlchimiaItems.ILLUMIUM, 4)).register(instance);

        // Darksteel
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARKSTEEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1.item(), AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.ENDER_LUMP_1.item(),
                AlchimiaItems.DARK_ESSENCE.item(), SlimefunItems.STEEL_INGOT.item(), AlchimiaItems.DARK_ESSENCE.item(),
                SlimefunItems.ENDER_LUMP_1.item(), AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.ENDER_LUMP_1.item()
        }, new CustomItemStack(AlchimiaItems.DARKSTEEL, 4)).register(instance);
        // }}}

        // {{{ Divine Altar
        DivineAltar divineAltar = new DivineAltar(AlchimiaUtils.ItemGroups.GENERAL);
        divineAltar.register(instance);

        new MoltenMysteryMetal(AlchimiaUtils.ItemGroups.GENERAL, divineAltar).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.MYSTERY_METAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                AlchimiaItems.MOLTEN_MYSTERY_METAL.item(), null, null,
                null, null, null,
                null, null, null
        }, new CustomItemStack(AlchimiaItems.MYSTERY_METAL, 16)).register(instance);
        // }}}

        // {{{ Cosmic Cauldron
        CosmicCauldron cauldron = new CosmicCauldron(AlchimiaUtils.ItemGroups.GENERAL, divineAltar);
        cauldron.register(instance);

        new PotionOfOsmosis(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        new BenevolentBrew(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        new MalevolentConcoction(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        // }}}

        // {{{ Altar of Infusion
        new AltarOfInfusion(AlchimiaUtils.ItemGroups.INFUSIONS, divineAltar).register(instance);
        // }}}
    }
    // }}}

    // {{{ Listeners
    private static void setupListeners(AlchimiaVitae instance) {
        new DyeListener(instance);

        // Infusion listeners
        new MeleeWeaponListener(instance);
        new RangedWeaponListener(instance);
        new HoeListener(instance);
        new TotemListener(instance);
        new FishingRodListener(instance);
    }
    // }}}

    // {{{ Researches
    private static void setupResearches(AlchimiaVitae instance) {
        new Research(AlchimiaUtils.createKey("soul"), 131072,
                "Manipulation of life force", 15)
                .addItems(AlchimiaItems.CONDENSED_SOUL.item(), AlchimiaItems.SOUL_COLLECTOR.item())
                .register();

        new Research(AlchimiaUtils.createKey("magic_plants"), 131073,
                "Powerful plants", 20)
                .addItems(AlchimiaItems.PLANT_INFUSION_CHAMBER.item(), AlchimiaItems.LIGHT_MAGIC_PLANT.item(), AlchimiaItems.DARK_MAGIC_PLANT.item())
                .register();

        new Research(AlchimiaUtils.createKey("magic_essence"), 131074,
                "Powerful powder", 10)
                .addItems(AlchimiaItems.LIGHT_ESSENCE.item(), AlchimiaItems.DARK_ESSENCE.item())
                .register();

        new Research(AlchimiaUtils.createKey("exp_crystals"), 131075,
                "Pure crystalline energy", 12)
                .addItems(AlchimiaItems.EXP_CRYSTALLIZER.item(), AlchimiaItems.EXP_CRYSTAL.item())
                .register();

        new Research(AlchimiaUtils.createKey("magic_steel"), 131076,
                "Mystical metals", 16)
                .addItems(AlchimiaItems.DARKSTEEL.item(), AlchimiaItems.ILLUMIUM.item())
                .register();

        new Research(AlchimiaUtils.createKey("divine_altar"), 131077,
                "The Ancient Altar's lost cousin", 36)
                .addItems(AlchimiaItems.DIVINE_ALTAR.item())
                .register();

        new Research(AlchimiaUtils.createKey("metal_amalgamation"), 131078,
                "Amalgam", 19)
                .addItems(AlchimiaItems.MOLTEN_MYSTERY_METAL.item(), AlchimiaItems.MYSTERY_METAL.item())
                .register();

        new Research(AlchimiaUtils.createKey("cosmic_cauldron"), 131079,
                "Advanced brewery", 36)
                .addItems(AlchimiaItems.COSMIC_CAULDRON.item())
                .register();

        new Research(AlchimiaUtils.createKey("potion_of_osmosis"), 131080,
                "Absorbing and reflecting", 30)
                .addItems(AlchimiaItems.POTION_OF_OSMOSIS.item())
                .register();

        new Research(AlchimiaUtils.createKey("benevolent_brew"), 131081,
                "A blessing from Gaia herself", 20)
                .addItems(AlchimiaItems.BENEVOLENT_BREW.item())
                .register();

        new Research(AlchimiaUtils.createKey("malevolent_concoction"), 131082,
                "A demonic liquid", 20)
                .addItems(AlchimiaItems.MALEVOLENT_CONCOCTION.item())
                .register();

        new Research(AlchimiaUtils.createKey("altar_of_infusion"), 131083,
                "Infusion", 36)
                .addItems(AlchimiaItems.ALTAR_OF_INFUSION.item())
                .register();
    }
    // }}}

}
