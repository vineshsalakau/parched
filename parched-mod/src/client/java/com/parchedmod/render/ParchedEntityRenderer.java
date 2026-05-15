package com.parchedmod.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.util.Identifier;

public class ParchedEntityRenderer extends SkeletonEntityRenderer {

    private static final Identifier TEXTURE =
            Identifier.of("parched", "textures/entity/parched.png");

    public ParchedEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(SkeletonEntity entity) {
        return TEXTURE;
    }
}
