package com.parchedmod.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.util.Identifier;

/**
 * Uses the standard skeleton model but with the parched texture.
 * Parched shares the exact same skeleton geometry in vanilla 1.21.11.
 *
 * Texture location: src/main/resources/assets/parched/textures/entity/parched.png
 * See README for instructions on obtaining the texture.
 */
public class ParchedEntityRenderer extends SkeletonEntityRenderer {

    private static final Identifier TEXTURE =
            Identifier.of("parched", "textures/entity/parched.png");

    public ParchedEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SkeletonEntity entity) {
        return TEXTURE;
    }
}
