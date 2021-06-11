package cows.pigs.moo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrapplingHook extends Item {
 
    public GrapplingHook(Settings settings) {
        super(settings);
    }
 
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        double rotationx = playerEntity.getRotationVector().getX();
        double rotationy = playerEntity.getRotationVector().getY();
        double rotationz = playerEntity.getRotationVector().getZ();
        playerEntity.addVelocity(rotationx, rotationy*2, rotationz);

        return TypedActionResult.success(playerEntity.getStackInHand(hand));

        
    }
}
