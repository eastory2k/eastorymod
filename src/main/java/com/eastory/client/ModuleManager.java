package com.eastory.client;

import com.eastory.client.modules.*;
import com.eastory.client.bypass.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import java.util.*;

public class ModuleManager {
    public final List<Module> list = new ArrayList<>();

    public ModuleManager() {
        list.add(new com.eastory.client.modules.AimAssist());
        list.add(new com.eastory.client.modules.TriggerBot());
        list.add(new com.eastory.client.modules.Spider());
        list.add(new PolarBypass());
        list.add(new FuntimeBypass());
        list.add(new SlothBypass());
    }

    public void tick() {
        for (Module m : list) if (m.on) m.tick();
    }

    public void onWorldRender(WorldRenderContext ctx) {
        for (Module m : list) if (m.on) m.render(ctx);
    }

    public List<Module> all() { return list; }
    public int enabled() {
        int n = 0;
        for (Module m : list) if (m.on) n++;
        return n;
    }
}
