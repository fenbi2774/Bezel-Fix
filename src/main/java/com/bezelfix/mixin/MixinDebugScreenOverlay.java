package com.bezelfix.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public class MixinDebugScreenOverlay {
    @Inject(method = "render3dCrosshair", at = @At("HEAD"), cancellable = true)
    private void cancel3dCrosshair(Camera camera, CallbackInfo ci) {
        if (com.bezelfix.config.ModConfig.enabled) {
            com.bezelfix.render.DebugCursorRenderer.render(camera);
            ci.cancel();
        }
    }
}
