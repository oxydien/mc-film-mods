package dev.oxydien.mbtym.block.entity;


import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;


public class ModBlockEntities {
	public static BlockEntityType<CoffeeMachineEntity> COFFEE_MACHINE_ENTITY;

	public static void registerBlockEntities() {
		InitMod.Log("Registering block entities!");
		COFFEE_MACHINE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE
		,new Identifier(InitMod.MOD_ID, "coffee_machine_entity"),
			QuiltBlockEntityTypeBuilder.create(CoffeeMachineEntity::new,
				ModBlocks.COFFEE_MACHINE_BLOCK).build());
	}
}
