package me.apeiros.alchimiavitae.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItemStack extends ItemStack {

    public CustomItemStack(ItemStack item) {
        super(item.getType(), item.getAmount());
        copyMeta(item);
    }

    public CustomItemStack(Material type) {
        super(type);
    }

    public CustomItemStack(Material type, String name, String... lore) {
        this(type, 1, name, lore);
    }

    public CustomItemStack(Material type, int amount, String name, String... lore) {
        super(type, amount);
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            if (name != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }
            if (lore != null && lore.length > 0) {
                List<String> list = new ArrayList<>();
                for (String line : lore) {
                    list.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                meta.setLore(list);
            }
            setItemMeta(meta);
        }
    }

    public CustomItemStack(ItemStack item, String name, String... lore) {
        this(item);
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            if (name != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }
            if (lore != null && lore.length > 0) {
                List<String> list = new ArrayList<>();
                for (String line : lore) {
                    list.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                meta.setLore(list);
            }
            setItemMeta(meta);
        }
    }

    public CustomItemStack(ItemStack item, int amount) {
        this(item);
        setAmount(amount);
    }

    public CustomItemStack(SlimefunItemStack item, int amount) {
        this((ItemStack) item);
        setAmount(amount);
    }

    public CustomItemStack(SlimefunItemStack item) {
        this((ItemStack) item);
    }

    private void copyMeta(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                setItemMeta(meta);
            }
        }
    }
}
