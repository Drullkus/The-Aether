package com.gildedgames.aether.common.item.accessories.miscellaneous;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.item.accessories.abilities.ISlowFallAccessory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class GoldenFeatherItem extends AccessoryItem implements ISlowFallAccessory
{
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        handleSlowFall(livingEntity);
    }
}
