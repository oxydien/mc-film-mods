package dev.oxydien.mbtym.entity;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder.*;


public class ModEntities {
	public static final EntityType<TestCarEntity> TEST_CAR = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(InitMod.MOD_ID,"test_car"),
		create(SpawnGroup.CREATURE, TestCarEntity::new)
			.dimensions(EntityDimensions.changing(1.8f,1.3f)).build());
	public static  final  EntityType<SimpleCarEntity> WHITE_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"white_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static  final  EntityType<SimpleCarEntity> BLUE_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"blue_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static  final  EntityType<SimpleCarEntity> BLUE_LONG_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"blue_long_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static  final  EntityType<SimpleCarEntity> RED_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"red_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static  final  EntityType<SimpleCarEntity> RED_LONG_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"red_long_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static  final  EntityType<SimpleCarEntity> ORANGE_LONG_CAR =
		Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID,"orange_long_car"),
			create(SpawnGroup.CREATURE,SimpleCarEntity::new).dimensions(EntityDimensions.fixed(1.2f,1.2f)).build());
	public static void registerModEntities() {
		InitMod.Log("Registering entities!");
		FabricDefaultAttributeRegistry.register(ModEntities.TEST_CAR, TestCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.WHITE_CAR, SimpleCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BLUE_CAR, SimpleCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BLUE_LONG_CAR, SimpleCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RED_CAR, SimpleCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RED_LONG_CAR, SimpleCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ORANGE_LONG_CAR, SimpleCarEntity.setAttributes());
	}
}


