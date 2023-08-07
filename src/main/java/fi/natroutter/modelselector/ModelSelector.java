package fi.natroutter.modelselector;

import fi.natroutter.modelselector.commands.ModelSelectorCMD;
import fi.natroutter.modelselector.files.Config;
import fi.natroutter.modelselector.guis.CategoryGUI;
import fi.natroutter.modelselector.guis.ColorGUI;
import fi.natroutter.modelselector.guis.CustomColorGUI;
import fi.natroutter.modelselector.guis.GiveGUI;
import fi.natroutter.modelselector.handlers.ModelHandler;
import fi.natroutter.modelselector.handlers.PackHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModelSelector extends JavaPlugin {

    @Getter
    private static ModelSelector instance;

    @Getter
    private static ModelHandler modelHandler;

    @Getter
    private static PackHandler packHandler;

    @Getter private static CategoryGUI categoryGUI;
    @Getter private static GiveGUI GiveGUI;
    @Getter private static ColorGUI colorGUI;
    @Getter private static CustomColorGUI customColorGUI;



    @Override
    public void onEnable() {
        instance = this;
        Config.UseOverride.reloadFile();

        packHandler = new PackHandler();

        modelHandler = new ModelHandler(this);

        customColorGUI = new CustomColorGUI();
        colorGUI = new ColorGUI();
        categoryGUI = new CategoryGUI();
        GiveGUI = new GiveGUI();

        CommandMap map = Bukkit.getCommandMap();
        map.register("modelselector", new ModelSelectorCMD());

    }

    @Override
    public void onDisable() {
    }


}
