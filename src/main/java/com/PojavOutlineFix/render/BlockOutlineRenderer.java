package com.PojavOutlineFix.render;

import com.PojavOutlineFix.PojavOutlineFix;
import com.PojavOutlineFix.config.ModConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.joml.Matrix4fc;

public class BlockOutlineRenderer {

    private static AABB currentPosition = null;
    private static AABB previousPosition = null;
    private static long lastChange = 0L;

    public static void render(PoseStack matrices, MultiBufferSource consumers) {
        if (!ModConfig.enabled) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        LocalPlayer player = mc.player;
        HitResult target = mc.hitResult;

        if (world == null || player == null || !(target instanceof BlockHitResult) || target.getType() == HitResult.Type.MISS) {
            resetPositions();
            return;
        }

        BlockHitResult blockHit = (BlockHitResult) target;
        BlockPos blockPos = blockHit.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.isAir() || !world.getWorldBorder().isWithinBounds(blockPos)) {
            resetPositions();
            return;
        }

        Entity cameraEntity = mc.getCameraEntity() != null ? mc.getCameraEntity() : player;
        VoxelShape shape = blockState.getShape(world, blockPos, CollisionContext.of(Objects.requireNonNull(cameraEntity)));
        if (shape.isEmpty()) {
            resetPositions();
            return;
        }

        AABB bounds = shape.bounds();
        double minX = bounds.minX + blockPos.getX();
        double minY = bounds.minY + blockPos.getY();
        double minZ = bounds.minZ + blockPos.getZ();
        double maxX = bounds.maxX + blockPos.getX();
        double maxY = bounds.maxY + blockPos.getY();
        double maxZ = bounds.maxZ + blockPos.getZ();

        if (currentPosition == null || 
            currentPosition.minX != minX || currentPosition.minY != minY || currentPosition.minZ != minZ ||
            currentPosition.maxX != maxX || currentPosition.maxY != maxY || currentPosition.maxZ != maxZ) {
            
            if (ModConfig.debug) {
                PojavOutlineFix.LOGGER.info("Block selection changed to: {}", blockPos);
            }

            previousPosition = currentPosition;
            currentPosition = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
            lastChange = System.currentTimeMillis();
        }

        if (previousPosition != null && ModConfig.Slide.enabled) {
            float factor = getEasingFactor(lastChange, System.currentTimeMillis(), ModConfig.Slide.time);
            minX = Mth.lerp(factor, previousPosition.minX, minX);
            minY = Mth.lerp(factor, previousPosition.minY, minY);
            minZ = Mth.lerp(factor, previousPosition.minZ, minZ);
            maxX = Mth.lerp(factor, previousPosition.maxX, maxX);
            maxY = Mth.lerp(factor, previousPosition.maxY, maxY);
            maxZ = Mth.lerp(factor, previousPosition.maxZ, maxZ);
        }

        renderOutline(matrices, consumers, minX, minY, minZ, maxX, maxY, maxZ);
    }

    private static void renderOutline(PoseStack matrices, MultiBufferSource consumers, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (matrices == null || consumers == null) return;

        Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().position();
        double x1 = minX - camPos.x;
        double y1 = minY - camPos.y;
        double z1 = minZ - camPos.z;
        double x2 = maxX - camPos.x;
        double y2 = maxY - camPos.y;
        double z2 = maxZ - camPos.z;

        if (ModConfig.debug) {
            PojavOutlineFix.LOGGER.info("Rendering at: [{}, {}, {}] to [{}, {}, {}]", x1, y1, z1, x2, y2, z2);
        }

        if (ModConfig.fill) {
            int fillColor = ModConfig.color;
            float fr = (float) (fillColor >> 16 & 255) / 255.0f;
            float fg = (float) (fillColor >> 8 & 255) / 255.0f;
            float fb = (float) (fillColor & 255) / 255.0f;
            float fa = (float) (fillColor >> 24 & 255) / 255.0f;
            
            VertexConsumer fillBuffer = consumers.getBuffer(RenderTypes.debugFilledBox());
            drawBoxFaces(matrices.last().pose(), fillBuffer, x1, y1, z1, x2, y2, z2, fr, fg, fb, fa);
        }

        int outlineColor = ModConfig.outlineColor;
        float r = (float) (outlineColor >> 16 & 255) / 255.0f;
        float g = (float) (outlineColor >> 8 & 255) / 255.0f;
        float b = (float) (outlineColor & 255) / 255.0f;
        float a = (float) (outlineColor >> 24 & 255) / 255.0f;

        VertexConsumer buffer = consumers.getBuffer(RenderTypes.debugFilledBox());
        drawBoxLines(matrices.last().pose(), buffer, x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    private static float getEasingFactor(long startTime, long currentTime, int duration) {
        if (duration <= 0) return 1.0f;
        float x = (float) (currentTime - startTime) / duration;
        if (x >= 1.0f) return 1.0f;
        if (x <= 0.0f) return 0.0f;

        String easing = ModConfig.Slide.easing.toUpperCase();
        switch (easing) {
            case "QUADIN": return x * x;
            case "QUADOUT": return 1 - (1 - x) * (1 - x);
            case "QUADINOUT": return x < 0.5f ? 2 * x * x : 1 - (float) Math.pow(-2 * x + 2, 2) / 2;
            default: return x; // Linear
        }
    }

    private static void resetPositions() {
        currentPosition = null;
        previousPosition = null;
    }

    private static void drawBoxFaces(@NonNull Matrix4fc matrix, VertexConsumer buffer, double x1d, double y1d, double z1d, double x2d, double y2d, double z2d, float r, float g, float b, float a) {
        float x1 = (float) x1d;
        float y1 = (float) y1d;
        float z1 = (float) z1d;
        float x2 = (float) x2d;
        float y2 = (float) y2d;
        float z2 = (float) z2d;

        buffer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, a);

        buffer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, a);

        buffer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, a);

        buffer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, a);

        buffer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, a);

        buffer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, a);
    }

    private static void drawBoxLines(@NonNull Matrix4fc matrix, VertexConsumer buffer, double x1d, double y1d, double z1d, double x2d, double y2d, double z2d, float r, float g, float b, float a) {
        float x1 = (float) x1d;
        float y1 = (float) y1d;
        float z1 = (float) z1d;
        float x2 = (float) x2d;
        float y2 = (float) y2d;
        float z2 = (float) z2d;
        float t = ModConfig.thickness / 200.0f; // half thickness, default 2.0 / 200 = 0.01

        // 12 edges, each drawn as a thin box (quads)
        // Bottom 4 edges
        drawEdge(matrix, buffer, x1, y1, z1, x2, y1, z1, t, r, g, b, a); // x1 to x2 (z1)
        drawEdge(matrix, buffer, x1, y1, z2, x2, y1, z2, t, r, g, b, a); // x1 to x2 (z2)
        drawEdge(matrix, buffer, x1, y1, z1, x1, y1, z2, t, r, g, b, a); // z1 to z2 (x1)
        drawEdge(matrix, buffer, x2, y1, z1, x2, y1, z2, t, r, g, b, a); // z1 to z2 (x2)

        // Top 4 edges
        drawEdge(matrix, buffer, x1, y2, z1, x2, y2, z1, t, r, g, b, a);
        drawEdge(matrix, buffer, x1, y2, z2, x2, y2, z2, t, r, g, b, a);
        drawEdge(matrix, buffer, x1, y2, z1, x1, y2, z2, t, r, g, b, a);
        drawEdge(matrix, buffer, x2, y2, z1, x2, y2, z2, t, r, g, b, a);

        // Vertical 4 edges
        drawEdge(matrix, buffer, x1, y1, z1, x1, y2, z1, t, r, g, b, a);
        drawEdge(matrix, buffer, x2, y1, z1, x2, y2, z1, t, r, g, b, a);
        drawEdge(matrix, buffer, x1, y1, z2, x1, y2, z2, t, r, g, b, a);
        drawEdge(matrix, buffer, x2, y1, z2, x2, y2, z2, t, r, g, b, a);
    }

    private static void drawEdge(@NonNull Matrix4fc matrix, VertexConsumer buffer, float x1, float y1, float z1, float x2, float y2, float z2, float t, float r, float g, float b, float a) {
        // Draw each edge as a thin box (6 faces) to avoid using RenderTypes.lines() which crashes in some environments.
        drawBoxFaces(matrix, buffer, x1 - t, y1 - t, z1 - t, x2 + t, y2 + t, z2 + t, r, g, b, a);
    }
}
