package me.apeiros.alchimiavitae.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CustomItemStack extends ItemStack {

    private static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static Component legacyComponent(String text) {
        return text.indexOf('§') >= 0 ? SECTION.deserialize(text) : AMPERSAND.deserialize(text);
    }

    private static List<Component> legacyLore(String... lore) {
        return Arrays.stream(lore).map(CustomItemStack::legacyComponent).toList();
    }

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
                meta.displayName(legacyComponent(name));
            }
            if (lore != null && lore.length > 0) {
                meta.lore(legacyLore(lore));
            }
            setItemMeta(meta);
        }
    }

    public CustomItemStack(ItemStack item, String name, String... lore) {
        this(item);
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            if (name != null) {
                meta.displayName(legacyComponent(name));
            }
            if (lore != null && lore.length > 0) {
                meta.lore(legacyLore(lore));
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
