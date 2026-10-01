
package com.kampfkaiser.hellfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class TacticalMissileItem extends Item {
    public TacticalMissileItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Actions.launch(level, player, hand);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.hellfire.tactical_missile.tooltip").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(Incendiary.flag(stack) ? "message.hellfire.incendiary_on" : "message.hellfire.incendiary_off").withStyle(ChatFormatting.DARK_GRAY));
    }
}
