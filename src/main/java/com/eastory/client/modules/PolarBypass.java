package com.eastory.client.bypass;

import com.eastory.client.*;

public class PolarBypass extends Module {

    private int tickCounter;

    public PolarBypass() { super("PolarBypass"); }

    @Override
    public void tick() {
        if (EastoryClient.mc.player == null) return;
        tickCounter++;
        if (tickCounter % 20 == 0) {
            float yaw = EastoryClient.mc.player.getYaw();
            EastoryClient.mc.player.setYaw(yaw + (float)(Math.random() - 0.5) * 0.05f);
        }
    }
}
