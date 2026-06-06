package com.eastory.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class Module {
    public String name;
    public boolean on = true;
    public int key = -1;
    public Module(String name) { this.name = name; }
    public void tick() {}
    public void render(WorldRenderContext ctx) {}
}
