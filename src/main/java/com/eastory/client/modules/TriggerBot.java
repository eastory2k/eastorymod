package com.eastory.client.modules;

import com.eastory.client.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {

    private int cd;

    public TriggerBot() { super("TriggerBot"); }

    @Override
    public void tick() {
        var p = EastoryClient.mc.player;
        var im = EastoryClient.mc.interactionManager;
        if (p == null || im == null || p.isDead()) return;
        if (cd > 0) { cd--; return; }
        var t = get();
        if (t == null) return;
        im.attackEntity(p, t);
        p.swingHand(Hand.MAIN_HAND);
        cd = 2;
    }

    private LivingEntity get() {
        var h = EastoryClient.mc.crosshairTarget;
        if (h instanceof EntityHitResult e) {
            if (e.getEntity() instanceof LivingEntity t && t.isAlive() && t != EastoryClient.mc.player) {
                return EastoryClient.mc.player.distanceTo(t) <= 3.2f ? t : null;
            }
        }
        return null;
    }
}
