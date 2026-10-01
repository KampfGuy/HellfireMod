package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.HellfireConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * In-game blasts only. Radii are Minecraft blocks, capped so a survival world stays playable.
 * This is not a description of any real weapon.
 */
public final class Blasts {
    private Blasts() {}

    public static void bomb(Level level, Vec3 pos) {
        if (!(level instanceof ServerLevel server)) return;
        Env.boom(server, pos.x, pos.y, pos.z, HellfireConfig.bombPower(), false);
        Env.sound(server, pos.x, pos.y, pos.z, "explode", 1.4F, 1.05F);
    }

    public static void missile(Level level, Vec3 pos, boolean fire) {
        if (!(level instanceof ServerLevel server)) return;
        Env.boom(server, pos.x, pos.y, pos.z, HellfireConfig.missilePower(), fire);
        Env.sound(server, pos.x, pos.y, pos.z, "explode", 2.4F, 0.85F);
    }

    public static void nuclear(Level level, Vec3 pos) {
        if (!(level instanceof ServerLevel server)) return;
        int radius = HellfireConfig.nuclearRadius();
        float power = HellfireConfig.explosionPower();
        int gap = Math.max(6, radius / 3);
        int[][] offsets = {{0, 0}, {gap, 0}, {-gap, 0}, {0, gap}, {0, -gap}, {gap, gap}, {-gap, -gap}};
        for (int[] offset : offsets) {
            Env.boom(server, pos.x + offset[0], pos.y + 0.5, pos.z + offset[1], power, false);
        }
        Env.sound(server, pos.x, pos.y, pos.z, "boom", 4.0F, 0.55F);
        BlockPos center = BlockPos.containing(pos);
        carve(server, center, radius);
        shock(server, pos, radius);
        fireRing(server, center, radius);
        MushroomCloudEntity.spawn(server, pos, radius);
        RadiationZoneEntity.spawn(server, pos, Math.max(10, (int) (radius * 0.75)), 1200);
    }

    private static void carve(ServerLevel level, BlockPos center, int radius) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int vertical = Math.max(4, radius / 2);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -vertical; dy <= radius / 3; dy++) {
                    double nx = dx / (double) radius;
                    double nz = dz / (double) radius;
                    double ny = dy / (double) vertical;
                    if (nx * nx + nz * nz + ny * ny > 1.0) continue;
                    cursor.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    BlockState state = level.getBlockState(cursor);
                    if (!terrain(state, level, cursor)) continue;
                    level.setBlock(cursor, Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
    }

    private static boolean terrain(BlockState state, Level level, BlockPos pos) {
        if (state.isAir() || state.is(Blocks.BEDROCK)) return false;
        if (state.getDestroySpeed(level, pos) < 0.0F) return false;
        return state.is(BlockTags.DIRT)
                || state.is(BlockTags.SAND)
                || state.is(BlockTags.BASE_STONE_OVERWORLD)
                || state.is(BlockTags.BASE_STONE_NETHER)
                || state.is(BlockTags.STONE_ORE_REPLACEABLES)
                || state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                || state.is(Blocks.GRAVEL)
                || state.is(Blocks.CLAY)
                || state.is(Blocks.MUD)
                || state.is(Blocks.SANDSTONE)
                || state.is(Blocks.RED_SANDSTONE)
                || state.is(Blocks.TUFF)
                || state.is(Blocks.CALCITE)
                || state.is(Blocks.NETHERRACK)
                || state.is(Blocks.END_STONE)
                || state.is(Blocks.BLACKSTONE)
                || state.is(Blocks.COBBLESTONE)
                || state.is(Blocks.MOSSY_COBBLESTONE)
                || state.is(Blocks.COBBLED_DEEPSLATE)
                || state.is(Blocks.DEEPSLATE)
                || state.is(Blocks.SNOW)
                || state.is(Blocks.SNOW_BLOCK)
                || state.is(Blocks.STONE)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.ANDESITE)
                || state.is(Blocks.DIORITE)
                || state.is(Blocks.GRANITE);
    }

    private static void shock(ServerLevel level, Vec3 pos, int radius) {
        AABB box = new AABB(pos, pos).inflate(radius);
        for (Entity entity : level.getEntities((Entity) null, box, entity -> true)) {
            if (entity instanceof MushroomCloudEntity || entity instanceof NuclearChargeEntity) continue;
            Vec3 away = entity.position().subtract(pos);
            double distance = away.length();
            if (distance < 0.05 || distance > radius) continue;
            double strength = (1.0 - distance / radius) * 2.4;
            Vec3 push = away.normalize().scale(strength).add(0.0, 0.55, 0.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(push));
            Env.impulse(entity);
        }
    }

    private static void fireRing(ServerLevel level, BlockPos center, int radius) {
        double ring = radius * 0.72;
        for (int i = 0; i < 24; i++) {
            double angle = i * Math.PI * 2.0 / 24.0;
            int x = center.getX() + (int) Math.round(Math.cos(angle) * ring);
            int z = center.getZ() + (int) Math.round(Math.sin(angle) * ring);
            BlockPos ground = findGround(level, x, center.getY(), z);
            if (ground == null) continue;
            BlockPos firePos = ground.above();
            if (level.getBlockState(firePos).isAir() && Blocks.FIRE.defaultBlockState().canSurvive(level, firePos)) {
                level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 2);
            }
        }
    }

    private static BlockPos findGround(ServerLevel level, int x, int y, int z) {
        for (int dy = 4; dy >= -8; dy--) {
            BlockPos pos = new BlockPos(x, y + dy, z);
            if (!level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir()) {
                return pos;
            }
        }
        return null;
    }
}
