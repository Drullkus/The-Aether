package com.gildedgames.aether.common.item.tools.zanite;

import com.gildedgames.aether.common.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;

public class ZaniteHoeItem extends HoeItem implements IZaniteToolItem
{
    public ZaniteHoeItem() {
        super(AetherItemTiers.ZANITE, -2, -1.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return calculateIncrease(stack, super.getDestroySpeed(stack, state));
    }
}
