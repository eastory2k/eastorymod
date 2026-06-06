package com.eastory.client.bypass;

import com.eastory.client.*;

public class SlothBypass extends Module {

    private int tickCounter;

    public SlothBypass() { super("SlothBypass"); }

    @Override
    public void tick() {
        if (EastoryClient.mc.player == null) return;
        tickCounter++;
        if (tickCounter % 10 == 0) {
            float yaw = EastoryClient.mc.player.getYaw();
            EastoryClient.mc.player.setYaw(yaw + (float)(Math.random() - 0.5) * 0.1f);
        }
    }
}
