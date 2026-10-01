package com.kampfkaiser.hellfire.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

public class MissileStrikeItem extends Item {
    public MissileStrikeItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) { return Actions.missileStrike(context); }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("item.hellfire.missile_strike.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
