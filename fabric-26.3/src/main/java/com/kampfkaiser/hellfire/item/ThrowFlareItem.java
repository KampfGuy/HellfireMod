package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.entity.Env;
import com.kampfkaiser.hellfire.entity.ThrownFlareEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ThrowFlareItem extends Item {
    private final int kind;
    private final String tip;
    private final int cooldown;

    public ThrowFlareItem(Properties properties, int kind, String tip, int cooldown) {
        super(properties);
        this.kind = kind;
        this.tip = tip;
        this.cooldown = cooldown;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) { return 72000; }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) { return ItemUseAnimation.BOW; }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity user, int remaining) {
        if (!(user instanceof Player player)) return false;
        float power = Math.min(1.0F, (72000 - remaining) / 20.0F);
        if (power < 0.12F) return false;
        if (!level.isClientSide()) ThrownFlareEntity.spawn(level, player, this.kind, power);
        Env.cooldown(player, stack.getItem(), this.cooldown);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.tip).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable("item.hellfire.throw_hint").withStyle(ChatFormatting.DARK_GRAY));
    }
}
