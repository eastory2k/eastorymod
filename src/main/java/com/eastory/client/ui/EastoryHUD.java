package com.eastory.client.ui;

import com.eastory.client.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import java.text.SimpleDateFormat;
import java.util.*;

public class EastoryHUD {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final TextRenderer f = mc.textRenderer;
    private static final SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

    private static final int BG = 0xCC0D0D0D, BORDER = 0xFF2A2A2A, ACCENT = 0xFF6BB5FF;
    private static final int WHITE = 0xFFFFFFFF, GRAY = 0xFFAAAAAA, GREEN = 0xFF55FF55, RED = 0xFFFF5555;

    private static float fps = 0;
    private static long last = 0;
    private static int idx = 0;
    private static final float[] buf = new float[10];

    private static final List<PlayerEntity> players = new ArrayList<>();
    private static final List<Module> modules = EastoryClient.INSTANCE.modules.all();

    public void render(DrawContext c, float d) {
        if (mc.player == null || mc.world == null || mc.options.hudHidden) return;

        int w = mc.getWindow().getScaledWidth();
        int h = mc.getWindow().getScaledHeight();
        long now = System.currentTimeMillis();

        // FPS
        if (last > 0) {
            float cf = 1000f / (now - last);
            buf[idx++ % buf.length] = cf;
            float s = 0; for (float v : buf) s += v;
            fps = s / buf.length;
        }
        last = now;

        // Левая панель
        int lw = 100, lh = 44 + modules.size() * 13;
        c.fill(6, 6, 6 + lw, 6 + lh, BG);
        c.fill(6, 6, 6 + lw, 8, ACCENT);
        box(c, 6, 6, lw, lh, BORDER);

        int y = 14;
        c.drawTextWithShadow(f, "EASTORY", 6 + lw / 2 - f.getWidth("EASTORY") / 2, y, ACCENT); y += 13;
        for (Module m : modules) {
            boolean on = m.on;
            c.drawTextWithShadow(f, on ? "●" : "○", 14, y, on ? GREEN : RED);
            c.drawTextWithShadow(f, m.name, 26, y, on ? WHITE : GRAY);
            y += 13;
        }

        // Правая панель
        players.clear();
        for (var e : mc.world.getEntities())
            if (e instanceof PlayerEntity pl && pl != mc.player) players.add(pl);
        players.sort((a, b) -> Float.compare(mc.player.distanceTo(a), mc.player.distanceTo(b)));

        if (!players.isEmpty()) {
            int rx = w - 136, ry = 6, rw = 130, max = Math.min(players.size(), 8), rh = 24 + max * 18;
            c.fill(rx, ry, rx + rw, ry + rh, BG);
            box(c, rx, ry, rw, rh, BORDER);

            int py = ry + 8;
            c.drawTextWithShadow(f, "PLAYERS", rx + rw / 2 - f.getWidth("PLAYERS") / 2, py, ACCENT); py += 13;

            for (int i = 0; i < max; i++) {
                var pl = players.get(i);
                float hp = (pl.getHealth() + pl.getAbsorptionAmount()) / pl.getMaxHealth();
                int col = hp > 0.6f ? GREEN : hp > 0.3f ? 0xFFFFFF55 : RED;
                String n = pl.getName().getString();
                if (n.length() > 13) n = n.substring(0, 10) + "..";

                c.drawTextWithShadow(f, n, rx + 6, py, col);
                c.drawTextWithShadow(f, (int)(hp * 100) + "%", rx + rw - 6 - f.getWidth((int)(hp * 100) + "%"), py, col);
                c.fill(rx + 6, py + 11, rx + 6 + (int)((rw - 12) * hp), py + 14, col);
                py += 18;
            }
        }

        // Статус-бар
        int sy = h - 14;
        c.fill(0, sy, w, sy + 14, 0xEE0A0A0A);
        c.fill(0, sy, w, sy + 2, ACCENT);
        String bar = "Eastory  |  " + (int)fps + " FPS  |  " + getPing() + "ms  |  " + getServer();
        c.drawTextWithShadow(f, bar, 6, sy + 3, WHITE);
        String time = tf.format(new Date());
        c.drawTextWithShadow(f, time, w - 6 - f.getWidth(time), sy + 3, ACCENT);
    } 

private static void box(DrawContext c, int x, int y, int w, int h, int col) {
        c.fill(x, y, x + w, y + 1, col);
        c.fill(x, y + h - 1, x + w, y + h, col);
        c.fill(x, y, x + 1, y + h, col);
        c.fill(x + w - 1, y, x + w, y + h, col);
    }

    private static int getPing() {
        if (mc.getNetworkHandler() != null && mc.player != null) {
            PlayerListEntry e = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            if (e != null) return e.getLatency();
        }
        return 0;
    }

    private static String getServer() {
        return mc.getCurrentServerEntry() != null ? mc.getCurrentServerEntry().address : "SP";
    }
}
