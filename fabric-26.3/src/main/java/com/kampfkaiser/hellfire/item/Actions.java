package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.HellfireConfig;
import com.kampfkaiser.hellfire.entity.Env;
import com.kampfkaiser.hellfire.entity.FlareEntity;
import com.kampfkaiser.hellfire.entity.MissileEntity;
import com.kampfkaiser.hellfire.entity.NuclearChargeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class Actions {
    private Actions() {}

    public static InteractionResult missileStrike(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        BlockPos pos = ctx.getClickedPos();
        Vec3 target = Vec3.atCenterOf(pos);
        if (!Env.client(level)) {
            double ox = (level.getRandom().nextDouble() - 0.5) * 20.0;
            double oz = (level.getRandom().nextDouble() - 0.5) * 20.0;
            if (ox * ox + oz * oz < 49.0) ox = 16.0;
            Vec3 from = target.add(ox, 34.0, oz);
            Vec3 velocity = target.subtract(from).normalize().scale(1.45);
            MissileEntity.spawn(level, from, velocity, target, false);
            FlareEntity.spawn(level, target.add(0.0, 0.15, 0.0), 90, FlareEntity.RED);
            Env.sound(level, target.x, target.y, target.z, "launch", 2.0F, 0.85F);
            Env.message(player, "message.hellfire.missile");
        }
        Env.cooldown(player, ctx.getItemInHand().getItem(), 40);
        return Env.ok(level);
    }

    public static InteractionResult nuclearStrike(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Vec3 target = Vec3.atCenterOf(ctx.getClickedPos()).add(0.0, 1.0, 0.0);
        if (!Env.client(level)) {
            NuclearChargeEntity.spawn(level, target);
            FlareEntity.spawn(level, target, HellfireConfig.fuseTicks() + 30, FlareEntity.YELLOW);
            Env.message(player, "message.hellfire.nuclear");
        }
        Env.cooldown(player, ctx.getItemInHand().getItem(), 40);
        return Env.ok(level);
    }
}
