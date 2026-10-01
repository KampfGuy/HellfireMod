package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.entity.Env;
import com.kampfkaiser.hellfire.entity.FlareEntity;
import com.kampfkaiser.hellfire.entity.MissileEntity;
import com.kampfkaiser.hellfire.entity.NuclearChargeEntity;
import com.kampfkaiser.hellfire.entity.StrikePlaneEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class Actions {
    private Actions() {}

    public static InteractionResult strike(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Vec3 target;
        if (player.isShiftKeyDown()) {
            FlareEntity flare = FlareEntity.nearest(level, player.position(), 64.0);
            if (flare == null) {
                if (!Env.client(level)) Env.message(player, "message.hellfire.no_flare");
                return InteractionResult.FAIL;
            }
            target = flare.position();
        } else {
            BlockPos pos = ctx.getClickedPos();
            target = Vec3.atCenterOf(pos).add(0.0, 1.0, 0.0);
        }
        if (!Env.client(level)) {
            StrikePlaneEntity.spawn(level, target, player.getLookAngle());
            FlareEntity.spawn(level, target, 200);
            Env.sound(level, target.x, target.y, target.z, "launch", 2.0F, 1.1F);
            Env.message(player, "message.hellfire.strike");
        }
        Env.cooldown(player, ctx.getItemInHand().getItem(), 80);
        return Env.ok(level);
    }

    public static void launch(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (!Env.client(level)) {
                Incendiary.toggle(stack);
                Env.message(player, Incendiary.flag(stack) ? "message.hellfire.incendiary_on" : "message.hellfire.incendiary_off");
            }
            return;
        }
        if (!Env.client(level)) {
            Vec3 eye = player.getEyePosition();
            Vec3 look = player.getLookAngle();
            Vec3 end = eye.add(look.scale(90.0));
            BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            Vec3 target = hit.getLocation();
            MissileEntity.spawn(level, eye.add(look.scale(1.4)), look.scale(1.05), target, Incendiary.enabled(stack));
            Env.sound(level, eye.x, eye.y, eye.z, "launch", 1.0F, 0.8F);
            if (!Env.creative(player)) stack.shrink(1);
        }
        Env.cooldown(player, stack.getItem(), 20);
    }

    public static InteractionResult placeCharge(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        BlockPos place = ctx.getClickedPos().relative(ctx.getClickedFace());
        if (!level.getBlockState(place).canBeReplaced()) return InteractionResult.FAIL;
        if (!Env.client(level)) {
            NuclearChargeEntity.spawn(level, Vec3.atCenterOf(place));
            if (!Env.creative(player)) ctx.getItemInHand().shrink(1);
        }
        return Env.ok(level);
    }

    public static InteractionResult placeFlare(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        BlockPos place = ctx.getClickedPos().relative(ctx.getClickedFace());
        if (!level.getBlockState(place).canBeReplaced()) return InteractionResult.FAIL;
        if (!Env.client(level)) {
            FlareEntity.spawn(level, Vec3.atCenterOf(place), 600);
            if (!Env.creative(player)) ctx.getItemInHand().shrink(1);
        }
        return Env.ok(level);
    }
}
