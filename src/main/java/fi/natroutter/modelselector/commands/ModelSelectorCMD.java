package fi.natroutter.modelselector.commands;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.guis.CategoryGUI;
import fi.natroutter.modelselector.guis.GiveGUI;
import fi.natroutter.modelselector.handlers.ModelHandler;
import fi.natroutter.modelselector.handlers.PackHandler;
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
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
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
                    p.sendMessage(Theme.help("/ModelSelector", "Opens the category selection gui"));
                    p.sendMessage(Theme.help("/ModelSelector open <category>", "Opens specified category gui"));
                    p.sendMessage(Theme.help("/ModelSelector help", "Shows this help page"));
                    p.sendMessage(Theme.help("/ModelSelector reinstall", "Reinstall all models"));
                    p.sendMessage(Theme.headerC());
                    break;
                case "reinstall":
                    packHandler.reinstall();
                    modelHandler.loadToCache();
                    p.sendMessage(Theme.withPrefix("Reinstalled all models!"));
                    break;
                default:
                    p.sendMessage(Theme.withPrefix("Usage: " + Theme.highlight("/ModelSelector help")));
                    break;
            }
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "open":
                    if (modelHandler.hasModel(args[1].toLowerCase())) {
                        giveGUI.show(p, Arrays.asList(args[1].toLowerCase()));
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
            return Utilities.getCompletes(sender, args[0], Arrays.asList("help", "open", "reinstall"));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("open")) {
                return Utilities.getCompletes(sender, args[1], modelHandler.getModelNames());
            }
        }

        return Utilities.emptyTab();
    }
}
