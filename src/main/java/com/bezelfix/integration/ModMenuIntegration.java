package com.bezelfix.integration;

import com.bezelfix.config.ModConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("bezel-fix.config.title"));

            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("bezel-fix.config.category.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("bezel-fix.config.enabled"), ModConfig.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("bezel-fix.config.enabled.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.enabled = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("bezel-fix.config.sideOnly"), ModConfig.sideOnly)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("bezel-fix.config.sideOnly.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.sideOnly = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("bezel-fix.config.fill"), ModConfig.fill)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("bezel-fix.config.fill.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.fill = newValue)
                    .build());

            general.addEntry(entryBuilder.startAlphaColorField(Component.translatable("bezel-fix.config.color"), ModConfig.color)
                    .setDefaultValue(0x464475FF)
                    .setTooltip(Component.translatable("bezel-fix.config.color.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.color = newValue)
                    .build());

            general.addEntry(entryBuilder.startAlphaColorField(Component.translatable("bezel-fix.config.outlineColor"), ModConfig.outlineColor)
                    .setDefaultValue(0x964475FF)
                    .setTooltip(Component.translatable("bezel-fix.config.outlineColor.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.outlineColor = newValue)
                    .build());

            general.addEntry(entryBuilder.startFloatField(Component.translatable("bezel-fix.config.thickness"), ModConfig.thickness)
                    .setDefaultValue(2.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("bezel-fix.config.thickness.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.thickness = newValue)
                    .build());

            ConfigCategory cursor = builder.getOrCreateCategory(Component.translatable("bezel-fix.config.category.cursor"));

            cursor.addEntry(entryBuilder.startFloatField(Component.translatable("bezel-fix.config.cursorSize"), ModConfig.cursorSize)
                    .setDefaultValue(1.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("bezel-fix.config.cursorSize.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.cursorSize = newValue)
                    .build());

            cursor.addEntry(entryBuilder.startFloatField(Component.translatable("bezel-fix.config.cursorThickness"), ModConfig.cursorThickness)
                    .setDefaultValue(1.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("bezel-fix.config.cursorThickness.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.cursorThickness = newValue)
                    .build());

            ConfigCategory slide = builder.getOrCreateCategory(Component.translatable("bezel-fix.config.category.animation"));
            
            slide.addEntry(entryBuilder.startBooleanToggle(Component.translatable("bezel-fix.config.slide.enabled"), ModConfig.Slide.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("bezel-fix.config.slide.enabled.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.enabled = newValue)
                    .build());

            slide.addEntry(entryBuilder.startIntField(Component.translatable("bezel-fix.config.slide.time"), ModConfig.Slide.time)
                    .setDefaultValue(150)
                    .setMin(1)
                    .setMax(1000)
                    .setTooltip(Component.translatable("bezel-fix.config.slide.time.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.time = newValue)
                    .build());

            slide.addEntry(entryBuilder.startStringDropdownMenu(Component.translatable("bezel-fix.config.slide.easing"), ModConfig.Slide.easing)
                    .setDefaultValue("LINEAR")
                    .setSelections(java.util.Arrays.asList("LINEAR", "QUADIN", "QUADOUT", "QUADINOUT"))
                    .setTooltip(Component.translatable("bezel-fix.config.slide.easing.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.easing = newValue)
                    .build());

            ConfigCategory debug = builder.getOrCreateCategory(Component.translatable("bezel-fix.config.category.debug"));

            debug.addEntry(entryBuilder.startBooleanToggle(Component.translatable("bezel-fix.config.debug"), ModConfig.debug)
                    .setDefaultValue(false)
                    .setTooltip(Component.translatable("bezel-fix.config.debug.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.debug = newValue)
                    .build());

            builder.setSavingRunnable(ModConfig::save);

            return builder.build();
        };
    }
}
