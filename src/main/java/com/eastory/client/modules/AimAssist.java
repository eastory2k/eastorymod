package com.eastory.client.modules;

import com.eastory.client.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class AimAssist extends Module {

    private static final float SMOOTH = 0.25f;
    private static final float FOV = 90f;
    private static final float RANGE = 5f;

    public AimAssist() { super("AimAssist"); }

    @Override
    public void tick() {
        var p = EastoryClient.mc.player;
        if (p == null || p.isDead()) return;
        var t = find();
        if (t == null) return;
        var tp = t.getPos().add(0, t.getHeight() * 0.8, 0);
        var ep = p.getEyePos();
        double dx = tp.x - ep.x, dy = tp.y - ep.y, dz = tp.z - ep.z;
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
        p.setYaw(p.getYaw() + MathHelper.wrapDegrees(yaw - p.getYaw()) * SMOOTH);
        p.setPitch(MathHelper.clamp(p.getPitch() + (pitch - p.getPitch()) * SMOOTH, -90, 90));
    }

    private LivingEntity find() {
        var p = EastoryClient.mc.player;
        var best = (LivingEntity) null;
        var bestD = Double.MAX_VALUE;
        for (var e : EastoryClient.mc.world.getEntities()) {
            if (!(e instanceof LivingEntity t) || t == p || !t.isAlive()) continue;
            double d = p.distanceTo(t);
            if (d > RANGE) continue;
            var a = p.getRotationVector();
            var b = t.getPos().add(0, t.getHeight() / 2, 0).subtract(p.getEyePos()).normalize();
            if (Math.toDegrees(Math.acos(MathHelper.clamp(a.dotProduct(b), -1, 1))) > FOV) continue;
            if (d < bestD) { bestD = d; best = t; }
        }
        return best;
    }
}
