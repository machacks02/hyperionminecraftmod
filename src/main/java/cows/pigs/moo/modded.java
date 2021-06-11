package cows.pigs.moo;
import java.util.UUID;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.entity.player.PlayerEntity;

public class modded implements ModInitializer {

	public static final Item GRAPPLING_HOOK = new GrapplingHook(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final Block VIBRANIUM = new vibranium(Settings.copy(Blocks.ANCIENT_DEBRIS));

	private static ConfiguredFeature<?, ?> VIBRANIUM_OVERWORLD = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, VIBRANIUM.getDefaultState(), 9))
    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 240))).spreadHorizontally().repeat(1);

	public static final ItemGroup MODDED_GROUP = FabricItemGroupBuilder.create(
            new Identifier("modded", "modded_group"))
            .build();
	public static final Item VIBRANIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static VibraniumSword VIBRNAIUM_SWORD = new VibraniumSword(VibraniumToolMaterial.INSTANCE, 3, -3.2F, new Item.Settings().group(ItemGroup.COMBAT).rarity(net.minecraft.util.Rarity.EPIC).fireproof());
	public static Hyperion HYPERION = new Hyperion(PogiumToolMaterial.INSTANCE, 3, -3F, new Item.Settings().group(ItemGroup.COMBAT).rarity(net.minecraft.util.Rarity.EPIC).fireproof());
	public static final Item GUN = new GunItem(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1).rarity(net.minecraft.util.Rarity.EPIC));
	public static final String ModID = "modded"; // This is just so we can refer to our ModID easier.
	public static final Identifier PacketID = new Identifier(ModID, "spawn_packet");
	public static final EntityType<bullet> BulletType = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "bullet"),
			FabricEntityTypeBuilder.<bullet>create(SpawnGroup.MISC, bullet::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);

	public void receiveEntityPacket() {
		ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.getTaskQueue().execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.pitch = pitch;
				e.yaw = yaw;
				e.setEntityId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}



	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(ModID, "gun"), GUN);
		Registry.register(Registry.ITEM, new Identifier(ModID, "hyperion"), HYPERION);


		RegistryKey<ConfiguredFeature<?,?>> vibraniumOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier("modded","vibranium"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, vibraniumOverworld.getValue(), VIBRANIUM_OVERWORLD);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, vibraniumOverworld);
		EntityRendererRegistry.INSTANCE.register(BulletType, (dispatcher, context) ->
				new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
		receiveEntityPacket();
		
	}
}
