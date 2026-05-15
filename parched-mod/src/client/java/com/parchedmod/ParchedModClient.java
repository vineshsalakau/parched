package com.parchedmod;

import com.parchedmod.init.ModEntities;
import com.parchedmod.render.ParchedEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ParchedModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.PARCHED, ParchedEntityRenderer::new);
    }
}
