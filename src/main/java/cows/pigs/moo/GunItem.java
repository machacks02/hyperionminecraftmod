package cows.pigs.moo;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GunItem extends Item{

    public GunItem(Settings settings) {
        super(settings);
        //TODO Auto-generated constructor stub
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent
		/*
		user.getItemCooldownManager().set(this, 5);
		Optionally, you can add a cooldown to your item's right-click use, similar to Ender Pearls.
		*/
		if (!world.isClient) {
			bullet bulletEntity = new bullet(world, user);
			bulletEntity.setItem(itemStack);
			bulletEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 5F, 0F);
			world.spawnEntity(bulletEntity); // spawns entity
		}
 
		user.incrementStat(Stats.USED.getOrCreateStat(this));
 
		return TypedActionResult.success(itemStack, world.isClient());
	}
    @Override
        public void appendTooltip(ItemStack itemStack, net.minecraft.world.World world, List<Text> tooltip, TooltipContext tooltipContext) {
            tooltip.add( new TranslatableText("Officer I dropkicked that").formatted(Formatting.BLUE).formatted(Formatting.ITALIC) );
            tooltip.add( new TranslatableText("toddler in self defense.").formatted(Formatting.BLUE).formatted(Formatting.ITALIC));
            tooltip.add( new TranslatableText(" ").formatted(Formatting.DARK_GRAY));
            tooltip.add( new TranslatableText(" ").formatted(Formatting.DARK_GRAY));
            tooltip.add( new TranslatableText("Right click this weapon to shoot a dagger.").formatted(Formatting.DARK_GRAY));
        }
}
