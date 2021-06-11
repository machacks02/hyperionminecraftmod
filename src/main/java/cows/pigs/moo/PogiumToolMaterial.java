package cows.pigs.moo;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PogiumToolMaterial implements ToolMaterial{

    public static final PogiumToolMaterial INSTANCE = new PogiumToolMaterial();

    @Override
    public float getAttackDamage() {
        // TODO Auto-generated method stub
        return 9;
    }

    @Override
    public int getDurability() {
        // TODO Auto-generated method stub
        return 1000;
    }

    @Override
    public int getEnchantability() {
        // TODO Auto-generated method stub
        return 20;
    }

    @Override
    public int getMiningLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        // TODO Auto-generated method stub
        return Ingredient.ofItems(Items.NETHERITE_BLOCK);
    }
    
}
