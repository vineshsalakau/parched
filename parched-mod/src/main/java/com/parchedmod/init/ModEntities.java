package com.parchedmod.init;

import com.parchedmod.entity.ParchedEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<ParchedEntity> PARCHED = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("parched", "parched"),
            EntityType.Builder.create(ParchedEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.99F)      // Same hitbox as skeleton
                    .maxTrackingRange(8)
                    .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(PARCHED, ParchedEntity.createAttributes());
    }
}
