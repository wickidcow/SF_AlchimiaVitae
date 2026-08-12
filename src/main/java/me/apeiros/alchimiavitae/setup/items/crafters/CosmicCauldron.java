package me.apeiros.alchimiavitae.setup.items.crafters;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.util.AlchimiaScheduler;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/** Cosmic Cauldron. */
public class CosmicCauldron extends AbstractCrafter<SlimefunItemStack> {

    public CosmicCauldron(ItemGroup ig, DivineAltar divineAltar) {
        super(ig, AlchimiaItems.COSMIC_CAULDRON, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL.item(), SlimefunItems.AUTO_BREWER.item(), AlchimiaItems.EXP_CRYSTAL.item(),
                AlchimiaItems.DARKSTEEL.item(), AlchimiaItems.DIVINE_ALTAR.item(), AlchimiaItems.ILLUMIUM.item(),
                SlimefunItems.BLISTERING_INGOT_3.item(), SlimefunItems.FLUID_PUMP.item(), SlimefunItems.BLISTERING_INGOT_3.item()
        });

        divineAltar.newRecipe(AlchimiaItems.COSMIC_CAULDRON, getRecipe());
    }

    @Override
    protected void newInstanceEffects(World w, Location l) {
        w.spawnParticle(Particle.TOTEM_OF_UNDYING, l, 100, 3, 3, 3);
        w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1F, 1F);
    }

    @Override
    protected void addDefaultRecipes() {}

    @Override
    protected void finish(World w, Location l, BlockMenu menu, SlimefunItemStack item) {
        Location anchor = l.clone();

        Runnable first = () -> {
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            w.spawnParticle(Particle.WITCH, anchor, 2, 1, 1, 1);
        };

        Runnable middle = () -> {
            w.playSound(anchor, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
            w.playSound(anchor, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
            w.spawnParticle(Particle.ENCHANTED_HIT, anchor, 200, 1, 1, 1);
        };

        Runnable finish = () -> {
            ItemStack newItem = item.item().clone();
            Location dropLocation = anchor.clone().add(0, 0.5, 0);

            if (menu.fits(newItem, OUT_SLOTS)) {
                menu.pushItem(newItem, OUT_SLOTS);
            } else {
                w.dropItemNaturally(dropLocation, newItem);
            }

            w.playSound(anchor, Sound.ITEM_BOTTLE_FILL, 1, 1);
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.5F, 1);
            w.playSound(anchor, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            w.playSound(anchor, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
            w.playSound(anchor, Sound.ITEM_LODESTONE_COMPASS_LOCK, 2, 1);
            w.spawnParticle(Particle.FLASH, anchor, 1, 0.1, 0.1, 0.1);
            w.spawnParticle(Particle.END_ROD, anchor, 200, 0.1, 4, 0.1);
        };

        AlchimiaScheduler.runPhases(anchor, 30L, first, middle, middle, middle, finish);
    }
}
