package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.entity.AaPlatformEntity;
import com.kampfkaiser.hellfire.entity.Env;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AaPlatformItem extends Item {
    public AaPlatformItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        BlockPos place = ctx.getClickedPos().relative(ctx.getClickedFace());
        if (!level.getBlockState(place).canBeReplaced()) return InteractionResult.FAIL;
        if (!level.isClientSide()) {
            AaPlatformEntity.spawn(level, Vec3.atCenterOf(place), player.getUUID());
            if (!Env.creative(player)) ctx.getItemInHand().shrink(1);
        }
        return Env.ok(level);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.hellfire.aa_platform.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
