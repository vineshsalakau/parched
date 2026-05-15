package com.parchedmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IronGolemEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ParchedEntity extends SkeletonEntity {

    public ParchedEntity(EntityType<? extends ParchedEntity> type, World world) {
        super(type, world);
    }

    // -------------------------------------------------------------------
    // Attributes: 16 HP (8 hearts), same speed as skeleton
    // -------------------------------------------------------------------
    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)   // 8 hearts
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0);
    }

    // -------------------------------------------------------------------
    // Goals: same as skeleton but WITHOUT AvoidSunlightGoal,
    //         and bow fires every 70 ticks (3.5s) instead of 20 (1s)
    // -------------------------------------------------------------------
    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.0));
        // Priority 4: bow attack — 70 ticks between shots = 3.5 seconds
        this.goalSelector.add(4, new BowAttackGoal<>(this, 1.0, 70, 15.0F));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        // Parched also tramples turtle eggs, just like husks
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, TurtleEntity.class,
                10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }

    // -------------------------------------------------------------------
    // Sunlight immunity — parched does NOT burn in daylight
    // -------------------------------------------------------------------
    @Override
    public boolean isBurningInDaylight() {
        return false;
    }

    // -------------------------------------------------------------------
    // Ranged attack — fires Arrows of Weakness (30s duration, level 0)
    // -------------------------------------------------------------------
    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        World world = this.getWorld();

        // Create the arrow projectile from a plain arrow ItemStack
        ItemStack arrowStack = new ItemStack(Items.ARROW);
        PersistentProjectileEntity projectile = ProjectileUtil.createArrowProjectile(this, arrowStack, pullProgress, null);

        // Apply Weakness for 30 seconds (600 ticks), amplifier 0
        if (projectile instanceof ArrowEntity arrow) {
            arrow.addEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 0));
        }

        // Aim toward target
        double dx = target.getX() - this.getX();
        double dy = target.getBodyY(0.3333333333333333) - projectile.getY();
        double dz = target.getZ() - this.getZ();
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        // Inaccuracy: lower on Hard, higher on Easy (same formula as vanilla skeleton)
        float inaccuracy = (float) (14 - world.getDifficulty().getId() * 4);

        projectile.setVelocity(dx, dy + horizontalDist * 0.2, dz, 1.6F, inaccuracy);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F,
                1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        world.spawnEntity(projectile);
    }
}
