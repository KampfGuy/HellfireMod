package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.entity.Env;
import com.kampfkaiser.hellfire.entity.PilotPlaneEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class RadioItem extends Item {
    public RadioItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getVehicle() instanceof PilotPlaneEntity plane) {
            if (!level.isClientSide()) {
                if (player.isShiftKeyDown()) plane.cycle(player);
                else plane.drop(player);
            }
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        }
        if (!level.isClientSide()) {
            PilotPlaneEntity.spawnAndRide(level, player);
            Env.message(player, "message.hellfire.airborne");
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.hellfire.radio.tooltip").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.hellfire.radio.tooltip2").withStyle(ChatFormatting.DARK_GRAY));
    }
}
