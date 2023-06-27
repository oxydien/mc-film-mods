package dev.oxydien.mbtym.entity;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModEntities {
	public static final EntityType<TestCarEntity> TEST_CAR = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(InitMod.MOD_ID,"test_car"),
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TestCarEntity::new)
			.dimensions(EntityDimensions.fixed(2.3f,1.8f)).build());

	public static void registerModEntities() {
		InitMod.LOGGER.info("Registering entities!");
	}
}


