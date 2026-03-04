package com.PojavOutlineFix;

import com.PojavOutlineFix.config.ModConfig;
import com.PojavOutlineFix.render.DebugCursorRenderer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojavOutlineFix implements ClientModInitializer {
	public static final String MOD_ID = "PojavOutlineFix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing PojavOutlineFix...");
		ModConfig.load();
		DebugCursorRenderer.init();
	}
}