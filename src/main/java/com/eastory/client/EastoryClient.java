package com.eastory.client;

import com.eastory.client.modules.*;
import com.eastory.client.ui.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class EastoryClient implements ClientModInitializer {

    public static EastoryClient INSTANCE;
    public static MinecraftClient mc;
    public ModuleManager modules;
    public EastoryHUD hud;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        mc = MinecraftClient.getInstance();
        modules = new ModuleManager();
        hud = new EastoryHUD();

        ClientTickEvents.START_CLIENT_TICK.register(this::onTick);
        HudRenderCallback.EVENT.register(hud::render);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(modules::onWorldRender);
    }

    private void onTick(MinecraftClient c) {
        if (c.player == null || c.world == null) return;
        modules.tick();
    }
}
