package com.PojavOutlineFix.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.LevelRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.PojavOutlineFix.render.BlockOutlineRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @Inject(method = "renderBlockOutline", at = @At("HEAD"), cancellable = true)
    private void onRenderBlockOutline(MultiBufferSource.BufferSource immediate, PoseStack matrices, boolean renderBlockOutline, LevelRenderState renderStates, CallbackInfo ci) {
        if (com.PojavOutlineFix.config.ModConfig.enabled) {
            // Only fire the custom renderer when rendering the second pass (translucent) to avoid duplicate rendering
            if (renderBlockOutline) {
                BlockOutlineRenderer.render(matrices, immediate);
            }
            ci.cancel();
        }
    }
}
