package dev.oxydien.mbtym.entity;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
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

	public static void registerModEntities() {
		InitMod.Log("Registering entities!");
	}
}


