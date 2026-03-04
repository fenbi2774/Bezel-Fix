package com.PojavOutlineFix.integration;

import com.PojavOutlineFix.config.ModConfig;
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
                    .setTitle(Component.translatable("pojavoutlinefix.config.title"));

            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("pojavoutlinefix.config.category.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("pojavoutlinefix.config.enabled"), ModConfig.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.enabled.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.enabled = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("pojavoutlinefix.config.sideOnly"), ModConfig.sideOnly)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.sideOnly.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.sideOnly = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("pojavoutlinefix.config.fill"), ModConfig.fill)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.fill.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.fill = newValue)
                    .build());

            general.addEntry(entryBuilder.startAlphaColorField(Component.translatable("pojavoutlinefix.config.color"), ModConfig.color)
                    .setDefaultValue(0x464475FF)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.color.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.color = newValue)
                    .build());

            general.addEntry(entryBuilder.startAlphaColorField(Component.translatable("pojavoutlinefix.config.outlineColor"), ModConfig.outlineColor)
                    .setDefaultValue(0x964475FF)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.outlineColor.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.outlineColor = newValue)
                    .build());

            general.addEntry(entryBuilder.startFloatField(Component.translatable("pojavoutlinefix.config.thickness"), ModConfig.thickness)
                    .setDefaultValue(2.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.thickness.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.thickness = newValue)
                    .build());

            ConfigCategory cursor = builder.getOrCreateCategory(Component.translatable("pojavoutlinefix.config.category.cursor"));

            cursor.addEntry(entryBuilder.startFloatField(Component.translatable("pojavoutlinefix.config.cursorSize"), ModConfig.cursorSize)
                    .setDefaultValue(1.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.cursorSize.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.cursorSize = newValue)
                    .build());

            cursor.addEntry(entryBuilder.startFloatField(Component.translatable("pojavoutlinefix.config.cursorThickness"), ModConfig.cursorThickness)
                    .setDefaultValue(1.0f)
                    .setMin(0.1f)
                    .setMax(10.0f)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.cursorThickness.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.cursorThickness = newValue)
                    .build());

            ConfigCategory slide = builder.getOrCreateCategory(Component.translatable("pojavoutlinefix.config.category.animation"));
            
            slide.addEntry(entryBuilder.startBooleanToggle(Component.translatable("pojavoutlinefix.config.slide.enabled"), ModConfig.Slide.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.slide.enabled.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.enabled = newValue)
                    .build());

            slide.addEntry(entryBuilder.startIntField(Component.translatable("pojavoutlinefix.config.slide.time"), ModConfig.Slide.time)
                    .setDefaultValue(150)
                    .setMin(1)
                    .setMax(1000)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.slide.time.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.time = newValue)
                    .build());

            slide.addEntry(entryBuilder.startStringDropdownMenu(Component.translatable("pojavoutlinefix.config.slide.easing"), ModConfig.Slide.easing)
                    .setDefaultValue("LINEAR")
                    .setSelections(java.util.Arrays.asList("LINEAR", "QUADIN", "QUADOUT", "QUADINOUT"))
                    .setTooltip(Component.translatable("pojavoutlinefix.config.slide.easing.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.Slide.easing = newValue)
                    .build());

            ConfigCategory debug = builder.getOrCreateCategory(Component.translatable("pojavoutlinefix.config.category.debug"));

            debug.addEntry(entryBuilder.startBooleanToggle(Component.translatable("pojavoutlinefix.config.debug"), ModConfig.debug)
                    .setDefaultValue(false)
                    .setTooltip(Component.translatable("pojavoutlinefix.config.debug.tooltip"))
                    .setSaveConsumer(newValue -> ModConfig.debug = newValue)
                    .build());

            builder.setSavingRunnable(ModConfig::save);

            return builder.build();
        };
    }
}
