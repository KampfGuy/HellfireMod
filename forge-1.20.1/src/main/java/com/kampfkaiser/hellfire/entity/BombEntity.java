package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BombEntity extends TrackedEntity {
    private int age;

    public BombEntity(EntityType<? extends BombEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, Vec3 velocity) {
        if (Env.client(level)) return;
        BombEntity bomb = new BombEntity(ModEntities.bombType(), level);
        bomb.setPos(pos.x, pos.y, pos.z);
        bomb.setDeltaMovement(velocity);
        level.addFreshEntity(bomb);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.age++;
        if (getY() < world().getMinBuildHeight() - 8) {
            discard();
            return;
        }
        setDeltaMovement(getDeltaMovement().add(0.0, -0.045, 0.0));
        move(MoverType.SELF, getDeltaMovement());
        faceMotion();
        if (this.age > 4 && (grounded() || this.age > 200)) {
            Blasts.bomb(world(), position());
            discard();
        }
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.age = Nbt.getInt(tag, "Age", 0);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Age", this.age);
    }
}
