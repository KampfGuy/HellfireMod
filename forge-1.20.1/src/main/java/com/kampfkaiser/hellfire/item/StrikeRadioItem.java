
package com.kampfkaiser.hellfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class StrikeRadioItem extends Item {
    public StrikeRadioItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) { return Actions.strike(context); }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.hellfire.strike_radio.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
