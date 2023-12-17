package dev.rajaopak.opaklibrary.libs;

import com.cryptomorin.xseries.SkullUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * Simple {@link ItemStack} builder
 *
 * @author MrMicky
 */
public class ItemBuilder implements Cloneable {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(ItemStack item) {
        this.item = Objects.requireNonNull(item, "item");
        this.meta = item.getItemMeta();

        if (this.meta == null) {
            throw new IllegalArgumentException(
                    "The type " + item.getType() + " doesn't support item meta");
        }
    }

    @NotNull
    public static ItemBuilder from(@NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    @NotNull
    public static ItemBuilder from(@NotNull Material material) {
        return new ItemBuilder(material);
    }

    public static ItemStack fromConfig(ConfigurationSection config) {
        return fromConfig(null, config);
    }


    /**
     * @param material the material, set to null if you want to get the material from config
     * @param config the configuration section of the item data
     * @return item stack, return null if material in config section is null or not match any material.
     */
    public static ItemStack fromConfig(Material material, ConfigurationSection config) {
        if (material == null && config.getString("material") == null) return null;

        if (material == null) material = Material.matchMaterial(config.getString("material"));

        if (material == Material.AIR) return new ItemStack(Material.AIR);

        ItemBuilder builder = new ItemBuilder(material);

        if (config.getString("name") != null) {
            builder.setName(config.getString("name"));
        }

        if (config.getInt("custom-model-data") != 0) {
            builder.customModelData(config.getInt("custom-model-data"));
        }

        if (config.getBoolean("unbreakable")) {
            builder.setUnbreakable(true);
        }

        if (config.getBoolean("glow")) {
            builder.setGlowing();
        }

        if (config.getString("texture") != null) {
            if (builder.getItem().getType() == Material.PLAYER_HEAD) {
                builder.setSkullByTexture(config.getString("texture"));
            }
        }

        builder.setLore(config.getStringList("lore"));

        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        config.getStringList("enchantments").forEach(s -> {
            String[] args = s.split(";");

            Enchantment enchant = Enchantment.getByKey(NamespacedKey.fromString(args[0].toLowerCase()));
            int level = Integer.parseInt(args[1]);

            if (enchant == null) return;

            enchants.put(enchant, level);
        });

        builder.setEnchants(enchants, true);

        config.getStringList("flags").forEach(s -> {
            try {
                builder.setFlags(ItemFlag.valueOf(s));
            } catch (IllegalArgumentException ignore) {
            }
        });

        return builder.build();
    }

    public ItemBuilder setType(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemBuilder setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public ItemBuilder setMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" + "item=" + item.toString() + ", meta=" + meta.toString() + '}';
    }

    @NotNull
    @Override
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();

            if (this.item != null) {
                clone.item = this.item.clone();
            }

            if (this.meta != null) {
                clone.meta = this.meta.clone();
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            Debug.warn("&cFailed to clone ItemBuilder!");
            throw new RuntimeException(e);
        }
    }

    public ItemBuilder setData(byte data) {
        item = new ItemStack(item.getType(), item.getAmount(), item.getDurability(), data);
        meta = meta.clone();
        return this;
    }

    @Deprecated
    public ItemBuilder setDurability(short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setEnchant(Enchantment enchantment) {
        return setEnchant(enchantment, 1);
    }

    public ItemBuilder setEnchant(Enchantment enchantment, int level) {
        return setEnchant(enchantment, level, false);
    }

    public ItemBuilder setEnchant(
            Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        this.meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchants) {
        for (Map.Entry<Enchantment, Integer> enchantment : enchants.entrySet()) {
            setEnchant(enchantment.getKey(), enchantment.getValue());
        }
        return this;
    }

    public ItemBuilder setEnchants(
            Map<Enchantment, Integer> enchants, boolean ignoreLevelRestriction) {
        for (Map.Entry<Enchantment, Integer> enchantment : enchants.entrySet()) {
            setEnchant(enchantment.getKey(), enchantment.getValue(), ignoreLevelRestriction);
        }
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        this.meta.getEnchants().keySet().forEach(this.meta::removeEnchant);
        return this;
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.meta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(this.meta)) {
            metaConsumer.accept(metaClass.cast(this.meta));
        }
        return this;
    }

    public ItemBuilder setName(String name) {
        this.meta.setDisplayName(ChatUtil.color(name));
        return this;
    }

    public String getName() {
        return this.meta.getDisplayName();
    }

    public List<String> getLore() {
        return this.meta.getLore();
    }

    public ItemBuilder setLore(String lore) {
        return setLore(Collections.singletonList(lore));
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        this.meta.setLore(ChatUtil.color(lore));
        return this;
    }

    public ItemBuilder addLore(String line) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return setLore(line);
        }

        lore.add(line);
        return setLore(lore);
    }

    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return setLore(lines);
        }

        lore.addAll(lines);
        return setLore(lore);
    }

    public ItemBuilder removeLore(String line) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return this;
        }

        lore.remove(line);

        return setLore(lore);
    }

    public ItemBuilder removeLore(int index) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return this;
        }

        lore.remove(index);

        return setLore(lore);
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setFlags() {
        return setFlags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public ItemBuilder armorColor(Color color) {
        return meta(LeatherArmorMeta.class, m -> m.setColor(color));
    }

    public ItemBuilder customModelData(int data) {
        if (data == -1) return this;

        this.meta.setCustomModelData(data);
        return this;
    }

    public int getCustomModelData() {
        if (this.meta.hasCustomModelData()) {
            return this.meta.getCustomModelData();
        }

        return -1;
    }

    public boolean isUnbreakable() {
        return this.meta.isUnbreakable();
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        return setUnbreakable(true);
    }

    public ItemBuilder removeUnbreakable() {
        if (this.meta.isUnbreakable()) {
            this.meta.setUnbreakable(false);
        }
        return this;
    }

    public ItemBuilder setGlowing() {
        return setGlowing(true);
    }

    public ItemBuilder setGlowing(boolean glowing) {
        if (glowing) {
            setEnchant(Enchantment.LURE, 1);
            setFlags(ItemFlag.HIDE_ENCHANTS);
            return this;
        }

        for (final Enchantment enchantment : meta.getEnchants().keySet()) {
            meta.removeEnchant(enchantment);
        }

        return this;
    }

    public boolean hasPDC(NamespacedKey key, PersistentDataType<?, ?> dataType) {
        return this.meta.getPersistentDataContainer().has(key, dataType);
    }

    public <N> N getPDC(NamespacedKey key, PersistentDataType<?, N> dataType) {
        return this.meta.getPersistentDataContainer().get(key, dataType);
    }

    public ItemBuilder removePDC(NamespacedKey key) {
        this.meta.getPersistentDataContainer().remove(key);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, String s) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, s);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, Double d) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, d);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, Float f) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.FLOAT, f);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, Integer i) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, Long l) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, l);
        return this;
    }

    public ItemBuilder setPDC(NamespacedKey key, Byte b) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, b);
        return this;
    }

    public ItemBuilder setSkullByTexture(String texture) {
        if (texture == null || texture.isEmpty()) return this;
        if (this.item.getType() != Material.PLAYER_HEAD) return this;

        SkullMeta headMeta = (SkullMeta) this.getMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field profileField = Objects.requireNonNull(headMeta).getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }

        this.setMeta(headMeta);
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
