package fi.natroutter.modelselector.commands;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.files.Config;
import fi.natroutter.modelselector.guis.CategoryGUI;
import fi.natroutter.modelselector.guis.ColorGUI;
import fi.natroutter.modelselector.guis.GiveGUI;
import fi.natroutter.modelselector.handlers.ModelHandler;
import fi.natroutter.modelselector.handlers.PackHandler;
import fi.natroutter.modelselector.object.Color;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ModelSelectorCMD extends Command {

    private ModelHandler modelHandler = ModelSelector.getModelHandler();
    private PackHandler packHandler = ModelSelector.getPackHandler();
    private CategoryGUI categoryGUI = ModelSelector.getCategoryGUI();
    private GiveGUI giveGUI = ModelSelector.getGiveGUI();

    public ModelSelectorCMD() {
        super("ModelSelector");
        this.setAliases(Arrays.asList("ms", "models"));
    }


    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String lable, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Theme.withPrefix("This command can only be used ingame!"));
            return false;
        }
        if (!p.hasPermission("modelselector.use")) {
            p.sendMessage(Theme.withPrefix("You don't have permission to use this!"));
            return false;
        }

        if (args.length == 0) {
            if (modelHandler.getCachedSize() > 0) {
                categoryGUI.show(p);

                return true;
            } else {
                p.sendMessage(Theme.withPrefix("No models installed!"));
                return false;
            }
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    p.sendMessage(Theme.headerC());
                    p.sendMessage(Theme.help("/" + lable, " Opens the category selection gui"));
                    p.sendMessage(Theme.help("/"+ lable +  " open <category>", "Opens specified category gui"));
                    p.sendMessage(Theme.help("/"+ lable +  " help", "Shows this help page"));
                    p.sendMessage(Theme.help("/"+ lable +  " reinstall", "Reinstall all models"));
                    p.sendMessage(Theme.help("/"+ lable +  " reload", "Reloads configuration"));
                    p.sendMessage(Theme.help("/"+ lable + " color <r,g,b>", "Set dyeable model color"));
                    p.sendMessage(Theme.headerC());
                    break;
                case "reload":
                    Config.UseOverride.reloadFile();
                    p.sendMessage(Theme.withPrefix("Configuration reloaded!"));
                    break;
                case "reinstall":
                    if (packHandler.reinstall()) {
                        modelHandler.loadToCache();
                        p.sendMessage(Theme.withPrefix("Reinstalled all models!"));
                    } else {
                        p.sendMessage(Theme.withPrefix("Failed to reinstall models, see console for more info!"));
                    }
                    break;
                default:
                    p.sendMessage(Theme.withPrefix("Usage: " + Theme.highlight("/"+lable+" help")));
                    break;
            }
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "color":
                    if (!args[1].contains(",")) {
                        p.sendMessage(Theme.withPrefix("Invalid color!"));
                        return false;
                    }
                    String[] color = args[1].split(",");
                    if (color.length != 3) {
                        p.sendMessage(Theme.withPrefix("Invalid color!"));
                        return false;
                    }
                    try {
                        int r = Integer.parseInt(color[0]);
                        int g = Integer.parseInt(color[1]);
                        int b = Integer.parseInt(color[2]);
                        if ((r > 255 || g > 255 || b > 255) || (r < 0 || g < 0 || b < 0)) {
                            p.sendMessage(Theme.withPrefix("Invalid color!"));
                            return false;
                        }
                        ColorGUI.selectedColor.put(p.getUniqueId(), new Color(r, g, b));
                        p.sendMessage(Theme.withPrefix("Color set to: &c" + r + "&7, &a" + g + "&7, &b" + b));
                    } catch (Exception e) {
                        p.sendMessage(Theme.withPrefix("Invalid color!"));
                        return false;
                    }
                    break;

                case "open":
                    if (modelHandler.hasModel(args[1].toLowerCase())) {
                        giveGUI.show(p, List.of(args[1].toLowerCase()));
                        p.sendMessage(Theme.withPrefix("Opened category: " + Theme.highlight(args[1].toLowerCase())));
                    } else {
                        p.sendMessage(Theme.withPrefix("Category not found!"));
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (!sender.hasPermission("modelselector.use")) {
            return Utilities.emptyTab();
        }

        if (args.length == 1) {
            return Utilities.getCompletes(sender, args[0], Arrays.asList("help", "open", "reinstall", "reload", "color"));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("open")) {
                return Utilities.getCompletes(sender, args[1], modelHandler.getModelNames());
            } else if (args[0].equalsIgnoreCase("color")) {
                if (sender instanceof Player p) {
                    Color c = ColorGUI.selectedColor.getOrDefault(p.getUniqueId(), new Color(0,0,0));
                    return Utilities.getCompletes(sender, args[1], List.of(c.getR() +"," + c.getG() + "," + c.getB()));
                }
            }
        }

        return Utilities.emptyTab();
    }
}
