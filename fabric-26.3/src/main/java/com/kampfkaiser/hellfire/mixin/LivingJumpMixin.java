package com.kampfkaiser.hellfire.mixin;

import com.kampfkaiser.hellfire.entity.PilotPlaneEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingJumpMixin {
    @Shadow protected boolean jumping;

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void hellfireJumpOff(CallbackInfo ci) {
        if (!((Object) this instanceof Player player)) return;
        if (this.jumping && player.getVehicle() instanceof PilotPlaneEntity) player.stopRiding();
    }
}
