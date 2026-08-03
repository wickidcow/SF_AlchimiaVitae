package me.apeiros.alchimiavitae.setup.items.electric;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public class PlantInfusionChamber extends AContainer implements RecipeDisplayItem {

    public PlantInfusionChamber(ItemGroup ig) {
        super(ig, AlchimiaItems.PLANT_INFUSION_CHAMBER, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.ELECTRIC_PRESS.item(), SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.ELECTRIC_PRESS.item(),
                SlimefunItems.HARDENED_GLASS.item(), SlimefunItems.HEATED_PRESSURE_CHAMBER_2.item(), SlimefunItems.HARDENED_GLASS.item(),
                SlimefunItems.HEATING_COIL.item(), SlimefunItems.ANCIENT_ALTAR.item(), SlimefunItems.HEATING_COIL.item()
        });

        this.setProcessingSpeed(1).setCapacity(128).setEnergyConsumption(32);
    }

    // {{{ Register recipes
    public void registerDefaultRecipes() {
        this.registerRecipe(60,
            new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING),
                SlimefunItems.MAGIC_LUMP_3.item()
            },

            new ItemStack[] {
                AlchimiaItems.LIGHT_MAGIC_PLANT.item()
            }
        );

        this.registerRecipe(60,
            new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING),
                AlchimiaItems.CONDENSED_SOUL.item()
            },

            new ItemStack[] {
                AlchimiaItems.DARK_MAGIC_PLANT.item()
            }
        );
    }
    // }}}

    // {{{ Other
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.SHORT_GRASS);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "AV_PLANT_INFUSION_CHAMBER";
    }
    // }}}

}
