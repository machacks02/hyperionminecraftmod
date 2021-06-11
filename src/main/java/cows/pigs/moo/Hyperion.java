package cows.pigs.moo;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Hyperion extends SwordItem {
    
    public Hyperion(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        
        toolMaterial = new PogiumToolMaterial();
        settings = new Item.Settings().group(ItemGroup.COMBAT);}
        public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
            playerEntity.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F);
            playerEntity.getItemCooldownManager().set(this, 50);
            bullet bulletEntity = new bullet(world, playerEntity);
            world.spawnEntity(bulletEntity);
            double x = playerEntity.getX();
            double y = playerEntity.getY();
            double z = playerEntity.getZ();
            playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1*5, 10));
            bulletEntity.explode();
            bulletEntity.remove();
            playerEntity.teleport(x, y, z);
            return TypedActionResult.success(playerEntity.getStackInHand(hand));
        }

        @Override
        public void appendTooltip(ItemStack itemStack, net.minecraft.world.World world, List<Text> tooltip, TooltipContext tooltipContext) {
            tooltip.add( new TranslatableText("Sun Tzu, The Art of War:").formatted(Formatting.DARK_GRAY) );
            tooltip.add( new TranslatableText("If you know your enemy and you know yourself ").formatted(Formatting.DARK_GRAY));
            tooltip.add( new TranslatableText("you need not fear the result of 100 battles.").formatted(Formatting.DARK_GRAY));
            tooltip.add( new TranslatableText(" "));
            tooltip.add( new TranslatableText(" "));
            tooltip.add(new TranslatableText("Special Ability: Poggers: ").formatted(Formatting.GOLD).formatted(Formatting.BOLD));
            tooltip.add(new TranslatableText("Trigger an explosion to damage all those around you!").formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
        }
        
{

    }
    
}
