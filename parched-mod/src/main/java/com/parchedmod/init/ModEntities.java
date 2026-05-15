package com.parchedmod.init;

import com.parchedmod.entity.ParchedEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final RegistryKey<EntityType<?>> PARCHED_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of("parched", "parched")
    );

    public static final EntityType<ParchedEntity> PARCHED = Registry.register(
            Registries.ENTITY_TYPE,
            PARCHED_KEY,
            EntityType.Builder.create(ParchedEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.99F)
                    .maxTrackingRange(8)
                    .build(PARCHED_KEY)
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(PARCHED, ParchedEntity.createAttributes());
    }
}
