package com.PojavOutlineFix.render;

import com.PojavOutlineFix.config.ModConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import java.util.Objects;

public class DebugCursorRenderer {

    public static void init() {
        // 改由 Mixin 触发
    }

    public static void render(Camera camera) {
        if (!ModConfig.enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (!mc.debugEntries.isCurrentlyEnabled(DebugScreenEntries.THREE_DIMENSIONAL_CROSSHAIR)) return;

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        
        // 1.21.11 原生逻辑：平移、旋转、缩放
        modelViewStack.translate(0.0F, 0.0F, -1.0F);
        modelViewStack.rotateX(camera.xRot() * (float) (Math.PI / 180.0));
        modelViewStack.rotateY(camera.yRot() * (float) (Math.PI / 180.0));
        
        float scale = 0.01F * (float) mc.getWindow().getGuiScale();
        modelViewStack.scale(-scale, scale, -scale);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        float size = 20.0f * ModConfig.cursorSize; // 再次增加长度 (15 -> 20)
        float t = 1.0f * ModConfig.cursorThickness; // 再次增加半厚度 (0.75 -> 1.0)

        // X 轴 (红色)
        drawBox(modelViewStack, bufferBuilder, 0, -t, -t, size, t, t, 1, 0, 0, 1);
        // Y 轴 (绿色)
        drawBox(modelViewStack, bufferBuilder, -t, 0, -t, t, size, t, 0, 1, 0, 1);
        // Z 轴 (蓝色)
        drawBox(modelViewStack, bufferBuilder, -t, -t, 0, t, t, size, 0, 0, 1, 1);

        try (MeshData meshData = bufferBuilder.buildOrThrow()) {
            RenderTypes.debugQuads().draw(meshData);
        }
        
        modelViewStack.popMatrix();
    }

    private static void drawBox(Matrix4f matrix, VertexConsumer buffer, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
        Matrix4f m = Objects.requireNonNull(matrix);
        // 6 faces
        // Down
        buffer.addVertex(m, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y1, z2).setColor(r, g, b, a);
        // Up
        buffer.addVertex(m, x1, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z1).setColor(r, g, b, a);
        // North
        buffer.addVertex(m, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y1, z1).setColor(r, g, b, a);
        // South
        buffer.addVertex(m, x1, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y2, z2).setColor(r, g, b, a);
        // West
        buffer.addVertex(m, x1, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y1, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x1, y2, z1).setColor(r, g, b, a);
        // East
        buffer.addVertex(m, x2, y1, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z1).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y2, z2).setColor(r, g, b, a);
        buffer.addVertex(m, x2, y1, z2).setColor(r, g, b, a);
    }
}
