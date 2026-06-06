package com.eastory.client.modules;

import com.eastory.client.*;

public class Spider extends Module {

    public Spider() { super("Spider"); }

    @Override
    public void tick() {
        var p = EastoryClient.mc.player;
        if (p == null || p.isDead()) return;
        if (!p.horizontalCollision) return;
        p.setVelocity(p.getVelocity().x, 0.2, p.getVelocity().z);
    }
}
