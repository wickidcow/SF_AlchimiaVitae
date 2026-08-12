package me.apeiros.alchimiavitae.setup.items.crafters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;
import me.apeiros.alchimiavitae.util.AlchimiaScheduler;
import me.apeiros.alchimiavitae.util.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class AltarOfInfusion extends AbstractCrafter<Infusion> {

    private static final int TOOL_SLOT = 10;

    public AltarOfInfusion(ItemGroup ig, DivineAltar divineAltar) {
        super(ig, AlchimiaItems.ALTAR_OF_INFUSION, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.WITHER_PROOF_GLASS.item(), AlchimiaItems.EXP_CRYSTAL.item(),
                SlimefunItems.REINFORCED_PLATE.item(), new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE.item(),
                SlimefunItems.BLISTERING_INGOT_3.item(), AlchimiaItems.DIVINE_ALTAR.item(), SlimefunItems.BLISTERING_INGOT_3.item()
        });

        divineAltar.newRecipe(AlchimiaItems.ALTAR_OF_INFUSION, getRecipe());
    }

    @Override
    protected void newInstanceEffects(World w, Location l) {
        w.spawnParticle(Particle.END_ROD, l, 100, 0.5, 0.5, 0.5);
    }

    @Override
    protected void addDefaultRecipes() {
        AlchimiaVitae instance = AlchimiaVitae.i();
        Configuration cfg = instance.getConfig();

        boolean destructiveCritsEnabled = cfg.getBoolean("options.infusions.infusion-destructivecrits");
        boolean phantomCritsEnabled = cfg.getBoolean("options.infusions.infusion-phantomcrits");
        boolean trueAimEnabled = cfg.getBoolean("options.infusions.infusion-trueaim");
        boolean forcefulEnabled = cfg.getBoolean("options.infusions.infusion-forceful");
        boolean volatileEnabled = cfg.getBoolean("options.infusions.infusion-volatile");
        boolean healingEnabled = cfg.getBoolean("options.infusions.infusion-healing");
        boolean autoReplantEnabled = cfg.getBoolean("options.infusions.infusion-autoreplant");
        boolean totemStorageEnabled = cfg.getBoolean("options.infusions.infusion-totemstorage");
        boolean knockbackEnabled = cfg.getBoolean("options.infusions.infusion-knockback");

        CustomItemStack validMelee = new CustomItemStack(
                Material.DIAMOND_SWORD, "&a&oA gold, iron, diamond,", "&a&oor netherite axe or sword");
        CustomItemStack validRanged = new CustomItemStack(Material.BOW, "&a&oA bow or crossbow");
        CustomItemStack validHoe = new CustomItemStack(
                Material.DIAMOND_HOE, "&a&oA gold, iron, diamond,", "&a&oor netherite hoe");
        CustomItemStack validChestplate = new CustomItemStack(
                Material.DIAMOND_CHESTPLATE, "&a&oA gold, iron, diamond, or", "&a&onetherite chestplate");
        CustomItemStack validFishingRod = new CustomItemStack(Material.FISHING_ROD, "&a&oA fishing rod");

        ItemGroup ig = AlchimiaUtils.ItemGroups.INFUSIONS;
        RecipeType rt = AlchimiaUtils.RecipeTypes.INFUSION_ALTAR;

        if (destructiveCritsEnabled) {
            newRecipe(ig, rt, Infusion.DESTRUCTIVE_CRITS, new ItemStack[] {
                    new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE.item(), new ItemStack(Material.STONECUTTER),
                    AlchimiaItems.DARKSTEEL.item(), validMelee, SlimefunItems.WITHER_PROOF_OBSIDIAN.item(),
                    new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN.item(), new ItemStack(Material.TNT)
            });
        }

        if (phantomCritsEnabled) {
            newRecipe(ig, rt, Infusion.PHANTOM_CRITS, new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS.item(), new ItemStack(Material.PHANTOM_MEMBRANE),
                    AlchimiaItems.DARKSTEEL.item(), validMelee, SlimefunItems.HARDENED_GLASS.item(),
                    new ItemStack(Material.PHANTOM_MEMBRANE), AlchimiaItems.CONDENSED_SOUL.item(), new ItemStack(Material.PHANTOM_MEMBRANE)
            });
        }

        if (forcefulEnabled) {
            newRecipe(ig, rt, Infusion.FORCEFUL, new ItemStack[] {
                    SlimefunItems.ELECTRO_MAGNET.item(), new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND.item(),
                    SlimefunItems.INFUSED_MAGNET.item(), validRanged, SlimefunItems.STEEL_THRUSTER.item(),
                    SlimefunItems.ELECTRO_MAGNET.item(), new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER.item()
            });
        }

        if (healingEnabled) {
            newRecipe(ig, rt, Infusion.HEALING, new ItemStack[] {
                    AlchimiaItems.BENEVOLENT_BREW.item(), SlimefunItems.MEDICINE.item(), SlimefunItems.VITAMINS.item(),
                    AlchimiaItems.ILLUMIUM.item(), validRanged, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE.item(), SlimefunItems.MAGIC_SUGAR.item()
            });
        }

        if (trueAimEnabled) {
            newRecipe(ig, rt, Infusion.TRUE_AIM, new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL.item(), SlimefunItems.INFUSED_MAGNET.item(), SlimefunItems.STAFF_WIND.item(),
                    AlchimiaItems.DARKSTEEL.item(), validRanged, AlchimiaItems.EXP_CRYSTAL.item(),
                    new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA.item(), SlimefunItems.STEEL_THRUSTER.item()
            });
        }

        if (volatileEnabled) {
            newRecipe(ig, rt, Infusion.VOLATILITY, new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE.item(), SlimefunItems.TALISMAN_FIRE.item(),
                    AlchimiaItems.DARKSTEEL.item(), validRanged, SlimefunItems.LAVA_GENERATOR_2.item(),
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND.item(), SlimefunItems.LAVA_CRYSTAL.item()
            });
        }

        if (totemStorageEnabled) {
            newRecipe(ig, rt, Infusion.TOTEM_BATTERY, new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL.item(), AlchimiaItems.CONDENSED_SOUL.item(), AlchimiaItems.BENEVOLENT_BREW.item(),
                    AlchimiaItems.ILLUMIUM.item(), validChestplate, AlchimiaItems.EXP_CRYSTAL.item(),
                    SlimefunItems.ESSENCE_OF_AFTERLIFE.item(), SlimefunItems.ENERGIZED_CAPACITOR.item(), SlimefunItems.ESSENCE_OF_AFTERLIFE.item()
            });
        }

        if (knockbackEnabled) {
            newRecipe(ig, rt, Infusion.KNOCKBACK, new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND.item(), new ItemStack(Material.STICKY_PISTON), AlchimiaItems.EXP_CRYSTAL.item(),
                    SlimefunItems.GRANDPAS_WALKING_STICK.item(), validFishingRod, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK.item(), SlimefunItems.TALISMAN_WHIRLWIND.item()
            });
        }

        if (autoReplantEnabled) {
            newRecipe(ig, rt, Infusion.AUTO_REPLANT, new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), AlchimiaItems.LIGHT_ESSENCE.item(), new ItemStack(Material.WATER_BUCKET),
                    AlchimiaItems.ILLUMIUM.item(), validHoe, SlimefunItems.FLUID_PUMP.item(),
                    new ItemStack(Material.BONE_BLOCK), AlchimiaItems.LIGHT_MAGIC_PLANT.item(), new ItemStack(Material.GRINDSTONE)
            });
        }
    }

    @Override
    public void newRecipe(
            @Nonnull ItemGroup ig,
            @Nonnull RecipeType rt,
            @Nonnull Infusion output,
            @Nonnull ItemStack... input) {
        ItemStack[] newInput = new ItemStack[8];

        int inputIndex = 0;
        int outerIndex = 0;
        for (ItemStack stack : input) {
            if (inputIndex != 4) {
                newInput[outerIndex++] = stack;
            }
            inputIndex++;
        }

        recipes.put(output, newInput);
        new SlimefunItem(ig, output.guideItem(), rt, input).register(AlchimiaVitae.i());
    }

    @Override
    protected void craft(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull Player p) {
        ItemStack[] input = new ItemStack[8];

        int index = 0;
        for (int slot : IN_SLOTS) {
            if (slot != TOOL_SLOT) {
                input[index++] = menu.getItemInSlot(slot);
            }
        }

        Infusion infusion = recipes.get(input);
        if (infusion == null) {
            p.sendMessage(AlchimiaUtils.format("<red>That recipe is invalid!"));
            return;
        }

        ItemStack tool = menu.getItemInSlot(TOOL_SLOT);
        if (tool == null || tool.getType().isAir()) {
            p.sendMessage(AlchimiaUtils.format("<red>There is nothing to infuse!"));
            return;
        }

        ItemMeta meta = tool.getItemMeta();
        if (meta == null) {
            p.sendMessage(AlchimiaUtils.format("<red>There is nothing to infuse!"));
            return;
        }

        if (!Infusion.ANY.canApply(tool)) {
            p.sendMessage(AlchimiaUtils.format("<red>You cannot infuse that item!"));
            return;
        }

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (Infusion.ANY.has(pdc)) {
            p.sendMessage(AlchimiaUtils.format("<red>This item already has an infusion!"));
            return;
        }

        if (!infusion.canApply(tool)) {
            p.sendMessage(AlchimiaUtils.format("<red>You cannot apply that infusion to this item!"));
            return;
        }

        infusion.apply(pdc);

        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(AlchimiaUtils.format("<gray>Infusion:"));
        lore.add(AlchimiaUtils.format("<dark_gray>› " + infusion.lore()));
        meta.setLore(lore);
        tool.setItemMeta(meta);

        finish(b.getWorld(), b.getLocation().add(0.5, 0.5, 0.5), menu, infusion);
    }

    @Override
    protected void finish(World w, Location l, BlockMenu menu, Infusion infusion) {
        ItemStack current = menu.getItemInSlot(TOOL_SLOT);
        if (current == null || current.getType().isAir()) {
            return;
        }

        ItemStack tool = current.clone();
        for (int slot : IN_SLOTS) {
            menu.consumeItem(slot, 1);
        }

        Location anchor = l.clone();

        Runnable first = () -> {
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1, 1);
            w.playSound(anchor, Sound.BLOCK_BEACON_POWER_SELECT, 1.5F, 1);
            w.spawnParticle(Particle.FLASH, anchor, 2, 0.1, 0.1, 0.1);
        };

        Runnable second = () -> {
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
            w.playSound(anchor, Sound.BLOCK_CONDUIT_ATTACK_TARGET, 0.5F, 1);
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            w.playSound(anchor, Sound.ITEM_TOTEM_USE, 0.1F, 1);
            w.playSound(anchor, Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
            w.playSound(anchor, Sound.BLOCK_LODESTONE_PLACE, 1.5F, 1);
            w.spawnParticle(Particle.FLASH, anchor, 2, 0.1, 0.1, 0.1);
        };

        Runnable third = () -> {
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            w.playSound(anchor, Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1.5F, 1);
            w.playSound(anchor, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
            w.playSound(anchor, Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
            w.playSound(anchor, Sound.ITEM_TOTEM_USE, 0.3F, 1);
            w.spawnParticle(Particle.FLASH, anchor, 2, 0.1, 0.1, 0.1);
        };

        Runnable finish = () -> {
            Location effectLocation = anchor.clone().add(0, 0.5, 0);

            if (menu.fits(tool, OUT_SLOTS)) {
                menu.pushItem(tool, OUT_SLOTS);
            } else {
                w.dropItemNaturally(effectLocation, tool);
            }

            w.strikeLightningEffect(effectLocation);
            w.playSound(anchor, Sound.ITEM_TRIDENT_THUNDER, 0.5F, 1);
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            w.playSound(anchor, Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
            w.playSound(anchor, Sound.ITEM_TOTEM_USE, 0.5F, 1);
            w.spawnParticle(Particle.END_ROD, anchor, 5, 0, 8, 0);
            w.spawnParticle(Particle.PORTAL, anchor, 300, 2, 2, 2);
        };

        AlchimiaScheduler.runPhases(anchor, 30L, first, second, third, finish);
    }

    public enum Infusion {
        DESTRUCTIVE_CRITS(
                "infusion_destructivecrits",
                "<red><bold>Destructive Criticals",
                new SlimefunItemStack(
                        "AV_DESTRUCTIVE_CRITS_INFUSION",
                        Material.TNT,
                        "&c&lDestructive Criticals",
                        "&4Grants a small chance to give your opponent",
                        "&4negative status effects on a critical hit,",
                        "&4as well as deal additional damage to armor.")),

        PHANTOM_CRITS(
                "infusion_phantomcrits",
                "<aqua>Phantom Criticals",
                new SlimefunItemStack(
                        "AV_PHANTOM_CRITS_INFUSION",
                        Material.PHANTOM_MEMBRANE,
                        "&bPhantom Criticals",
                        "&7Grants a small chance to deal extra damage",
                        "&7on a critical hit, which bypasses armor")),

        FORCEFUL(
                "infusion_forceful",
                "<dark_green>Forceful",
                new SlimefunItemStack(
                        "AV_FORCEFUL_INFUSION",
                        Material.PISTON,
                        "&2Forceful",
                        "&2Grants the ability to fire arrows that",
                        "&2travel further and deal more damage")),

        HEALING(
                "infusion_healing",
                "<red>Healing",
                new SlimefunItemStack(
                        "AV_HEALING_INFUSION",
                        Material.REDSTONE,
                        "&cHealing",
                        "&cGrants the ability to heal hit",
                        "&ctargets instead of harming them")),

        TRUE_AIM(
                "infusion_trueaim",
                "<light_purple>True Aim",
                new SlimefunItemStack(
                        "AV_TRUE_AIM_INFUSION",
                        Material.SHULKER_SHELL,
                        "&dTrue Aim",
                        "&5Grants the ability to fire arrows",
                        "&5that are not affected by gravity")),

        VOLATILITY(
                "infusion_volatile",
                "<dark_red><bold>Volatility",
                new SlimefunItemStack(
                        "AV_VOLATILE_INFUSION",
                        Material.FIRE_CHARGE,
                        "&4&lVolatility",
                        "&cGrants the ability to shoot fireballs")),

        TOTEM_BATTERY(
                "infusion_totemstorage",
                "<gold><bold>Battery of Totems",
                new SlimefunItemStack(
                        "AV_TOTEM_BATTERY_INFUSION",
                        Material.TOTEM_OF_UNDYING,
                        "&6&lTotem Battery",
                        "&6Stores up to 8 Totems of Undying which will resurrect you",
                        "&eStore a totem by &7&lShift-Right-Clicking &ewhile holding",
                        "&eone and while an infused chestplate is worn")),

        KNOCKBACK(
                "infusion_knockback",
                "<green>Knockback",
                new SlimefunItemStack(
                        "AV_KNOCKBACK_INFUSION",
                        Material.SLIME_BALL,
                        "&aKnockback",
                        "&aPushes targets away instead",
                        "&aof pulling them towards you")),

        AUTO_REPLANT(
                "infusion_autoreplant",
                "<green>Automatic Re-plant",
                new SlimefunItemStack(
                        "AV_AUTO_REPLANT_INFUSION",
                        Material.WHEAT,
                        "&aAutomatic Re-plant",
                        "&2Grants the ability to automatically replant",
                        "&2fully grown crops when harvesting them")),

        ANY("infusion_dummy_any", "", null);

        private final NamespacedKey key;
        private final String lore;
        private final SlimefunItemStack guideItem;

        Infusion(String key, String lore, SlimefunItemStack guideItem) {
            this.key = AlchimiaUtils.createKey(key);
            this.lore = lore;
            this.guideItem = guideItem;
        }

        public NamespacedKey key() {
            return key;
        }

        public String lore() {
            return lore;
        }

        public SlimefunItemStack guideItem() {
            return guideItem;
        }

        public boolean canApply(@Nonnull ItemStack tool) {
            Material mat = tool.getType();

            return switch (mat) {
                case GOLDEN_AXE,
                        IRON_AXE,
                        DIAMOND_AXE,
                        NETHERITE_AXE,
                        GOLDEN_SWORD,
                        IRON_SWORD,
                        DIAMOND_SWORD,
                        NETHERITE_SWORD -> AlchimiaUtils.equalsAny(this, ANY, DESTRUCTIVE_CRITS, PHANTOM_CRITS);

                case BOW, CROSSBOW -> AlchimiaUtils.equalsAny(this, ANY, FORCEFUL, HEALING, TRUE_AIM, VOLATILITY);

                case GOLDEN_CHESTPLATE,
                        IRON_CHESTPLATE,
                        DIAMOND_CHESTPLATE,
                        NETHERITE_CHESTPLATE -> AlchimiaUtils.equalsAny(this, ANY, TOTEM_BATTERY);

                case FISHING_ROD -> AlchimiaUtils.equalsAny(this, ANY, KNOCKBACK);

                case GOLDEN_HOE,
                        IRON_HOE,
                        DIAMOND_HOE,
                        NETHERITE_HOE -> AlchimiaUtils.equalsAny(this, ANY, AUTO_REPLANT);

                default -> false;
            };
        }

        public boolean has(@Nonnull PersistentDataContainer pdc) {
            if (this == TOTEM_BATTERY) {
                return pdc.has(key(), PersistentDataType.INTEGER);
            }
            return pdc.has(key(), PersistentDataType.BYTE);
        }

        public void apply(@Nonnull PersistentDataContainer pdc) {
            if (this == TOTEM_BATTERY) {
                pdc.set(key(), PersistentDataType.INTEGER, 0);
            } else {
                pdc.set(key(), PersistentDataType.BYTE, (byte) 1);
            }
        }

        public int getTotems(@Nonnull PersistentDataContainer pdc) {
            if (this != TOTEM_BATTERY) {
                return -1;
            }

            Integer totems = pdc.get(key(), PersistentDataType.INTEGER);
            return totems != null ? totems : 0;
        }

        public void setTotems(@Nonnull PersistentDataContainer pdc, int n) {
            if (this == TOTEM_BATTERY) {
                pdc.set(key(), PersistentDataType.INTEGER, n);
            }
        }
    }
}
