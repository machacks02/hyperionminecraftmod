package cows.pigs.moo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class bullet extends ThrownItemEntity {
	public bullet(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
        //TODO Auto-generated constructor stub
    }

    public bullet(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public bullet(World world, LivingEntity owner) {
		super(modded.BulletType, owner, world); // null will be changed later
	}
 
	public bullet(World world, double x, double y, double z) {
		super(modded.BulletType, x, y, z, world); // null will be changed later
	}
 
	@Override
	protected Item getDefaultItem() {
		return modded.GUN; // We will configure this later, once we have created the ProjectileItem.
	}
 
	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.CRIT : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}
 
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
 
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
 
	}
	public void explode() {
		this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 4, DestructionType.NONE);
	}
 
	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		int i = entity instanceof BlazeEntity ? 10 : 6; // sets i to 3 if the Entity instance is an instance of BlazeEntity
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)i); // deals damage
 
		if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
			entity.playSound(SoundEvents.AMBIENT_CAVE, 2F, 1F); // plays a sound for the entity hit only
		}
	}
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
			this.world.sendEntityStatus(this, (byte)3); // particle?
			this.remove(); // kills the projectile
		}
 
	}
    @Override
	public Packet createSpawnPacket() {
		return EntitySpawnPacket.create(this, modded.PacketID);
	}
}