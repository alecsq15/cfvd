package com.eritro.mod1;

import org.slf4j.Logger;

import com.eritro.mod1.config.BrutalRaidConfig;
import com.eritro.mod1.event.BrutalRaidEventHandler;
import com.eritro.mod1.item.ModItems;
import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Moddeprueba.MODID)
public class Moddeprueba {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "moddeprueba";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Moddeprueba(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        ModItems.ITEMS.register(modEventBus);

        NeoForge.EVENT_BUS.register(new BrutalRaidEventHandler());

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, BrutalRaidConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Brutal raids enabled: Bad Omen {}, max waves {}",
                BrutalRaidConfig.requiredBadOmenLevel(),
                BrutalRaidConfig.maxWaves());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.BRUTAL_OMEN_BOTTLE);
        }
    }
}
