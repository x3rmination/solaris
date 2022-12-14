package com.github.x3rmination.solaris.common.item.FlamingFlamberge;

import com.github.x3rmination.solaris.common.helper.ParticleHelper;
import com.github.x3rmination.solaris.common.item.SolarisWeapon;
import com.github.x3rmination.solaris.common.registry.ItemRegistry;
import com.github.x3rmination.solaris.common.scheduler.ClientScheduler;
import com.github.x3rmination.solaris.common.scheduler.Executable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.Skill;

public class FlamingFlambergeItem extends SwordItem implements SolarisWeapon {

    public FlamingFlambergeItem(Properties pProperties) {
        super(new ForgeTier(0, 1000, 2.0F, 0.0F, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)), 9, -3F, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.setRemainingFireTicks(Math.max(100, pTarget.getRemainingFireTicks()));
        pTarget.hurt(DamageSource.ON_FIRE, 1);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void clientAttack(LocalPlayer localPlayer, Skill skill) throws NoSuchMethodException {
        if(skill.equals(Skills.GIANT_WHIRLWIND)) {
            ClientScheduler.schedule(new Executable(
                    this,
                    this.getClass().getDeclaredMethod("flamingFlambergeClient", LocalPlayer.class),
                    new Object[]{localPlayer}, 35));
        }
    }

    public void flamingFlambergeClient(LocalPlayer localPlayer) {
        ParticleHelper particleHelper = new ParticleHelper(localPlayer.level, ParticleTypes.FLAME, localPlayer.position().add(0, 1, 0));
        for(int i = 0; i < 10; i++) {
            particleHelper.spawnCircle(2 + (i * 0.1), (int) (32 + (Math.random() * 4)));
        }
    }
}
