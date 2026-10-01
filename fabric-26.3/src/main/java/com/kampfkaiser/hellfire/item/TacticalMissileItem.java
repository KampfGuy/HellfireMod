
package com.kampfkaiser.hellfire.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class TacticalMissileItem extends Item {
    public TacticalMissileItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Actions.launch(level, player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("item.hellfire.tactical_missile.tooltip").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(Incendiary.flag(stack) ? "message.hellfire.incendiary_on" : "message.hellfire.incendiary_off").withStyle(ChatFormatting.DARK_GRAY));
    }
}
