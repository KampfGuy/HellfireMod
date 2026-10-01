package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.entity.Env;
import com.kampfkaiser.hellfire.entity.PilotPlaneEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class RadioItem extends Item {
    public RadioItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.getVehicle() instanceof PilotPlaneEntity plane) {
            if (!level.isClientSide()) {
                if (player.isShiftKeyDown()) plane.cycle(player);
                else plane.drop(player);
            }
            return InteractionResult.CONSUME;
        }
        if (!level.isClientSide()) {
            PilotPlaneEntity.spawnAndRide(level, player);
            Env.message(player, "message.hellfire.airborne");
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("item.hellfire.radio.tooltip").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable("item.hellfire.radio.tooltip2").withStyle(ChatFormatting.DARK_GRAY));
    }
}
