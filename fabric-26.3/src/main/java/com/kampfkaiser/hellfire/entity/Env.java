
package com.kampfkaiser.hellfire.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class Env {
    private Env() {}

    public static boolean client(Level level) { return level.isClientSide(); }

    public static InteractionResult ok(Level level) { return InteractionResult.SUCCESS; }

    public static void cooldown(Player player, Item item, int ticks) {
        player.getCooldowns().addCooldown(new ItemStack(item), ticks);
    }

    public static boolean creative(Player player) { return player.getAbilities().instabuild; }

    public static void message(Player player, String key) {
        player.sendOverlayMessage(Component.translatable(key));
    }

    public static void impulse(Entity entity) { entity.needsSync = true; }

    public static void boom(ServerLevel level, double x, double y, double z, float power, boolean fire) {
        level.explode(null, x, y, z, power, fire, Level.ExplosionInteraction.TNT);
    }

    public static void sound(Level level, double x, double y, double z, String kind, float volume, float pitch) {
        SoundEvent event = switch (kind) {
            case "launch" -> SoundEvents.FIREWORK_ROCKET_LAUNCH;
            case "fuse" -> SoundEvents.TNT_PRIMED;
            default -> SoundEvents.GENERIC_EXPLODE.value();
        };
        level.playSound(null, x, y, z, event, SoundSource.BLOCKS, volume, pitch);
    }

    public static float forward(net.minecraft.world.entity.player.Player player) {
        if (player instanceof net.minecraft.server.level.ServerPlayer server) {
            var input = server.getLastClientInput();
            return (input.forward() ? 1.0F : 0.0F) - (input.backward() ? 1.0F : 0.0F);
        }
        return player.zza;
    }

    public static float strafe(net.minecraft.world.entity.player.Player player) {
        if (player instanceof net.minecraft.server.level.ServerPlayer server) {
            var input = server.getLastClientInput();
            return (input.left() ? 1.0F : 0.0F) - (input.right() ? 1.0F : 0.0F);
        }
        return player.xxa;
    }

    public static void harm(net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.LivingEntity entity, float amount) {
        entity.hurtServer(level, level.damageSources().wither(), amount);
    }

    public static void cushion(net.minecraft.world.entity.player.Player player) {
        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOW_FALLING, 260));
    }

}
