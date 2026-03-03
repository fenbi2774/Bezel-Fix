package com.bezelfix.config;

import com.bezelfix.BezelFix;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ModConfig {
    public static boolean enabled = true;
    public static boolean sideOnly = true;
    public static boolean fill = true;
    public static int color = new Color(68, 117, 255, 70).getRGB();
    public static int outlineColor = new Color(68, 117, 255, 150).getRGB();
    public static float thickness = 2.0f;
    public static float cursorSize = 1.0f;
    public static float cursorThickness = 1.0f;
    public static boolean debug = false;

    public static class Slide {
        public static boolean enabled = true;
        public static int time = 150;
        public static String easing = "LINEAR";
    }

    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), BezelFix.MOD_ID + ".json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null) {
                enabled = data.enabled;
                sideOnly = data.sideOnly;
                fill = data.fill;
                color = data.color;
                outlineColor = data.outlineColor;
                thickness = data.thickness;
                cursorSize = data.cursorSize;
                cursorThickness = data.cursorThickness;
                debug = data.debug;
                Slide.enabled = data.slideEnabled;
                Slide.time = data.slideTime;
                Slide.easing = data.slideEasing;
            }
        } catch (Exception e) {
            BezelFix.LOGGER.error("Failed to load config", e);
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            ConfigData data = new ConfigData();
            data.enabled = enabled;
            data.sideOnly = sideOnly;
            data.fill = fill;
            data.color = color;
            data.outlineColor = outlineColor;
            data.thickness = thickness;
            data.cursorSize = cursorSize;
            data.cursorThickness = cursorThickness;
            data.debug = debug;
            data.slideEnabled = Slide.enabled;
            data.slideTime = Slide.time;
            data.slideEasing = Slide.easing;
            GSON.toJson(data, writer);
        } catch (Exception e) {
            BezelFix.LOGGER.error("Failed to save config", e);
        }
    }

    private static class ConfigData {
        boolean enabled = true;
        boolean sideOnly = true;
        boolean fill = true;
        int color = new Color(68, 117, 255, 70).getRGB();
        int outlineColor = new Color(68, 117, 255, 150).getRGB();
        float thickness = 2.0f;
        float cursorSize = 4.0f;
        float cursorThickness = 4.0f;
        boolean debug = false;
        boolean slideEnabled = true;
        int slideTime = 150;
        String slideEasing = "LINEAR";
    }
}
