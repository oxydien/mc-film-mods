package dev.oxydien.mbtym.entity.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class SlideEntity extends PathAwareEntity {

	private static final float MOVEMENT_SPEED = 0.35F;
	public SlideEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}
}
