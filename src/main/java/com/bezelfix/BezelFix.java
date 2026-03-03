package com.bezelfix;

import com.bezelfix.config.ModConfig;
import com.bezelfix.render.BlockOutlineRenderer;
import com.bezelfix.render.DebugCursorRenderer;
import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BezelFix implements ClientModInitializer {
	public static final String MOD_ID = "bezel-fix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing BezelFix...");
		ModConfig.load();
		BlockOutlineRenderer.init();
		DebugCursorRenderer.init();
	}
}