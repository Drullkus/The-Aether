package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.common.entity.monster.WhirlwindEntity;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PassiveWhirlyParticle extends TextureSheetParticle {
    WhirlwindEntity whirlwind;
    SpriteSet animatedSprite;
    public PassiveWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, SpriteSet sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.animatedSprite = sprite;
        whirlwind = worldIn.getNearestEntity(WhirlwindEntity.class, TargetingConditions.DEFAULT, null, xCoordIn, yCoordIn, zCoordIn, new AABB(x, y, z, x + 1, y + 1, z + 1));
        this.setPos(whirlwind.getX(), whirlwind.getY(), whirlwind.getZ());
        this.xd = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.yd = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.zd = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.quadSize = this.random.nextFloat() * this.random.nextFloat() * 0.5F /* * 6.0F + 1.0F*/;
        this.lifetime = (int)(16.0D / ((double)this.random.nextFloat() * 0.8D + 0.2D)) + 2;
        int color = whirlwind.getColorData();
        this.setColor((((color >> 16) & 0xFF) / 255F), (((color >> 8) & 0xFF) / 255F), ((color & 0xFF) / 255F));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        this.setSpriteFromAge(animatedSprite);

        if(whirlwind.isAlive()) {
            float f = (float)(whirlwind.getX() - this.x);
            float f1 = (float)(whirlwind.getY() - this.y);
            float f2 = (float)(whirlwind.getZ() - this.z);
            float d16 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);

            double d18 = this.getBoundingBox().minY - this.y;
            double d21 = Math.atan2(whirlwind.getX() - this.x, whirlwind.getZ() - this.z) / 0.01745329424738884D;
            d21 += 160D;
            this.xd = -Math.cos(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.zd = Math.sin(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.yd = 0.11500000208616257D;
        }
        this.yd += 0.004D;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.8999999761581421D;
        this.yd *= 0.8999999761581421D;
        this.zd *= 0.8999999761581421D;
        if (this.onGround) {
            this.xd *= 0.699999988079071D;
            this.zd *= 0.699999988079071D;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PassiveWhirlyParticle whirlyParticle = new PassiveWhirlyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            whirlyParticle.pickSprite(this.spriteSet);
            return whirlyParticle;
        }
    }
}
