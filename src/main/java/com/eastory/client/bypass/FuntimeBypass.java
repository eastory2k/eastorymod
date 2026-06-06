package com.eastory.client.bypass;

import com.eastory.client.*;

public class FuntimeBypass extends Module {

    private int tickCounter;

    public FuntimeBypass() { super("FuntimeBypass"); }

    @Override
    public void tick() {
        if (EastoryClient.mc.player == null) return;
        tickCounter++;
        if (tickCounter % 15 == 0) {
            float pitch = EastoryClient.mc.player.getPitch();
            EastoryClient.mc.player.setPitch(pitch + (float)(Math.random() - 0.5) * 0.03f);
        }
    }
}
