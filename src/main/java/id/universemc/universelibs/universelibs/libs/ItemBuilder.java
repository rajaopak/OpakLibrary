package id.universemc.universelibs.universelibs.libs;

import com.cryptomorin.xseries.SkullUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

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
            throw new IllegalArgumentException("The type " + item.getType() + " doesn't support item meta");
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

    public ItemBuilder setType(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public ItemBuilder setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemBuilder setMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "item=" + item.toString() +
                ", meta=" + meta.toString() +
                '}';
    }

    @NotNull
    @Override
    public ItemBuilder clone(){
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
            Common.log("&cFailed to clone ItemBuilder!");
            throw new Error(e);
        }
    }

    public ItemBuilder setData(int data) {
        return setDurability((short) data);
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

    public ItemBuilder setEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        this.meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchants) {
        for (Map.Entry<Enchantment, Integer> enchantment : enchants.entrySet()) {
            setEnchant(enchantment.getKey(), enchantment.getValue());
        }
        return this;
    }

    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchants, boolean ignoreLevelRestriction) {
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
        this.meta.setDisplayName(Common.color(name));
        return this;
    }

    public ItemBuilder setLore(String lore) {
        return setLore(Collections.singletonList(lore));
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        this.meta.setLore(Common.color(lore));
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

    public ItemBuilder customModelData(int data){
        this.meta.setCustomModelData(data);
        return this;
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

    public ItemBuilder pdc(NamespacedKey key, String s){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, s);
        return this;
    }

    public ItemBuilder pdc(NamespacedKey key, Double d){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, d);
        return this;
    }

    public ItemBuilder pdc(NamespacedKey key, Float f){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.FLOAT, f);
        return this;
    }

    public ItemBuilder pdc(NamespacedKey key, Integer i){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
        return this;
    }

    public ItemBuilder pdc(NamespacedKey key, Long l){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, l);
        return this;
    }

    public ItemBuilder pdc(NamespacedKey key, Byte b){
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, b);
        return this;
    }

    public ItemBuilder setSkull(String identifier){
        SkullUtils.applySkin(this.meta, identifier);
        return this;
    }

    public ItemBuilder setSkull(OfflinePlayer identifier){
        SkullUtils.applySkin(this.meta, identifier);
        return this;
    }

    public ItemBuilder setSkull(UUID identifier) {
        SkullUtils.applySkin(this.meta, identifier);
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
