package cows.pigs.moo;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {
 
    public static final ArmorMaterial CUSTOM_ARMOR_MATERIAL = new CustomArmorMaterial();
    // If you made a new material, this is where you would note it.
    public static final Item VIBRANIUM_HELMET = new ArmorItem(CUSTOM_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item VIBRANIUM_CHESTPLATE = new ArmorItem(CUSTOM_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item VIBRANIUM_LEGGINGS = new ArmorItem(CUSTOM_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item VIBRANIUM_BOOTS = new ArmorItem(CUSTOM_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    
    public static void register() {
        Registry.register(Registry.ITEM, new Identifier("modded", "vibranium_helmet"), VIBRANIUM_HELMET);
        Registry.register(Registry.ITEM, new Identifier("modded", "vibranium_chestplate"), VIBRANIUM_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("modded", "vibranium_leggings"), VIBRANIUM_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("modded", "vibranium_boots"), VIBRANIUM_BOOTS);
    }
}
