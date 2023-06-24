package dev.oxydien.mbtym.entity;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.SlideEntity;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class ModEntities {
	public static final EntityType<SlideEntity> SLIDER = registerEntity("slider", QuiltEntityTypeBuilder.create(SpawnGroup.MISC, SlideEntity::new).setDimensions(new EntityDimensions(0.6F, 0.8F, false)).build());

	public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entityType) {
		final EntityType<T> newEntity = Registry.register(Registries.ENTITY_TYPE, new Identifier(InitMod.MOD_ID, name), entityType);
		// FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) newEntity,
		// 	 SlideEntity.createAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
		// );
		return newEntity;
	}

	public static void registerModEntities() {
		InitMod.LOGGER.info("Registering entities!");
	}
}


