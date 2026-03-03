package com.bezelfix.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.LevelRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @Inject(method = "renderBlockOutline", at = @At("HEAD"), cancellable = true)
    private void cancelBlockOutline(MultiBufferSource.BufferSource immediate, PoseStack matrices, boolean renderBlockOutline, LevelRenderState renderStates, CallbackInfo ci) {
        if (com.bezelfix.config.ModConfig.enabled) {
            ci.cancel();
        }
    }
}
