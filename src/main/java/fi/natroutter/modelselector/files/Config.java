package fi.natroutter.modelselector.files;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.natlibs.config.IConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public enum Config implements IConfig {

    UseOverride("UseOverride"),
    OverrideURL("OverrideURL"),

    ;

    @Getter
    private String path;

    @Override
    public JavaPlugin getPlugin() {
        return ModelSelector.getInstance();
    }
}
