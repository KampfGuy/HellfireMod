package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** A plane the player flies. Look and WASD steer it. Sneak descends. */
public class PilotPlaneEntity extends DurableEntity {
    public static final int BOMBS = 0;
    public static final int NAPALM = 1;
    public static final int MISSILE = 2;
    public static final int NUCLEAR = 3;
    private static final String[] NAMES = {
            "message.hellfire.ordnance_bombs",
            "message.hellfire.ordnance_napalm",
            "message.hellfire.ordnance_missile",
            "message.hellfire.ordnance_nuclear"
    };

    private int ordnance;
    private int nukeCooldown;
    private int emptyTicks;

    public PilotPlaneEntity(EntityType<? extends PilotPlaneEntity> type, Level level) {
        super(type, level);
    }

    public static void spawnAndRide(Level level, Player player) {
        if (Env.client(level)) return;
        PilotPlaneEntity plane = new PilotPlaneEntity(com.kampfkaiser.hellfire.registry.ModEntities.pilotType(), level);
        plane.setPos(player.getX(), player.getY() + 0.2, player.getZ());
        plane.markGhost();
        plane.snapYaw(player.getYRot());
        level.addFreshEntity(plane);
        player.startRiding(plane);
    }

    public void cycle(Player player) {
        this.ordnance = (this.ordnance + 1) % 4;
        Env.message(player, NAMES[this.ordnance]);
    }

    public void drop(Player player) {
        if (this.ordnance == NUCLEAR && this.nukeCooldown > 0) {
            Env.message(player, "message.hellfire.nuke_cooling");
            return;
        }
        Vec3 look = player.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0, look.z);
        if (flat.lengthSqr() < 1.0E-4) flat = new Vec3(0.0, 0.0, 1.0);
        flat = flat.normalize();
        Vec3 pos = position().add(0.0, -1.2, 0.0);
        Vec3 velocity = flat.scale(0.35).add(0.0, -0.2, 0.0);
        switch (this.ordnance) {
            case NAPALM -> NapalmFieldEntity.spawn(world(), pos, 600);
            case MISSILE -> {
                Vec3 target = eyeTarget(player);
                MissileEntity.spawn(world(), position().add(look.scale(1.5)), look.scale(1.2), target, false);
            }
            case NUCLEAR -> {
                BombEntity.spawnNuclear(world(), pos, velocity);
                this.nukeCooldown = 600;
                Env.message(player, "message.hellfire.nuke_dropped");
            }
            default -> BombEntity.spawn(world(), pos, velocity);
        }
        Env.sound(world(), getX(), getY(), getZ(), "launch", 0.8F, 1.2F);
    }

    private Vec3 eyeTarget(Player player) {
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(80.0));
        var hit = world().clip(new net.minecraft.world.level.ClipContext(eye, end, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, player));
        return hit.getLocation();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.nukeCooldown > 0) this.nukeCooldown--;
        if (client()) return;
        Entity rider = getFirstPassenger();
        if (!(rider instanceof Player player) || !player.getMainHandItem().is(ModItems.radioItem())) {
            this.emptyTicks++;
            if (!(rider instanceof Player) || this.emptyTicks > 2) {
                if (rider instanceof Player still) still.stopRiding();
                discard();
            }
            return;
        }
        this.emptyTicks = 0;
        float yaw = player.getYRot();
        float pitch = player.getXRot();
        snapYaw(yaw);
        setXRot(pitch * 0.65F);
        float forward = Env.forward(player);
        float strafe = Env.strafe(player);
        Vec3 look = Vec3.directionFromRotation(0.0F, yaw);
        Vec3 side = new Vec3(-look.z, 0.0, look.x);
        double climb = -pitch / 90.0 * 0.62;
        if (player.isShiftKeyDown()) climb -= 0.42;
        Vec3 motion = look.scale(forward * 0.92).add(side.scale(-strafe * 0.55)).add(0.0, climb, 0.0);
        if (motion.length() > 1.2) motion = motion.normalize().scale(1.2);
        setDeltaMovement(motion);
        setPos(getX() + motion.x, getY() + motion.y, getZ() + motion.z);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (client() || !(passenger instanceof Player player)) return;
        int x = net.minecraft.util.Mth.floor(getX());
        int z = net.minecraft.util.Mth.floor(getZ());
        int y = net.minecraft.util.Mth.floor(getY());
        Integer land = null;
        for (int dy = 0; dy <= 96; dy++) {
            BlockPos pos = new BlockPos(x, y - dy, z);
            if (!world().getBlockState(pos).isAir() && world().getBlockState(pos.above()).isAir()) {
                land = pos.getY() + 1;
                break;
            }
        }
        if (land != null) player.teleportTo(getX(), land, getZ());
        else Env.cushion(player);
        player.resetFallDistance();
        player.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public net.minecraft.world.entity.LivingEntity getControllingPassenger() {
        Entity first = getFirstPassenger();
        return first instanceof net.minecraft.world.entity.LivingEntity living ? living : null;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.ordnance = Nbt.getInt(tag, "Ordnance", 0);
        this.nukeCooldown = Nbt.getInt(tag, "NukeWait", 0);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Ordnance", this.ordnance);
        Nbt.putInt(tag, "NukeWait", this.nukeCooldown);
    }
}
