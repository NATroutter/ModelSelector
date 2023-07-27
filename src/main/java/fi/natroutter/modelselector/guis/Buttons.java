package fi.natroutter.modelselector.guis;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.object.Model;
import fi.natroutter.modelselector.object.ResourceKey;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.Button;
import fi.natroutter.natlibs.handlers.guibuilder.SwitchButton;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.Parser;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Buttons {

    public static BaseItem filler() {
        BaseItem btn = new BaseItem(Material.BLACK_STAINED_GLASS_PANE);
        btn.name(" ");
        return btn;
    }

    public static Button next() {
        Button btn = new Button(Material.ARROW, (e, gui) -> gui.nextPage());
        btn.name(Theme.main("<bold>Next</bold>"));
        btn.lore(Theme.text("Go to next page"));
        return btn;
    }

    public static Button previous() {
        Button btn = new Button(Material.ARROW, (e, gui) -> gui.previousPage());
        btn.name(Theme.main("<bold>Previous</bold>"));
        btn.lore(Theme.text("Go back to previous page"));
        return btn;
    }

    public static Button category(String name, int size) {
        SwitchButton btn = new SwitchButton(
                Parser.material(name, Material.PAPER),
                ModelSelector.getGiveGUI(),
                Arrays.asList(name)
        );
        btn.name(Theme.main("<bold>" + name + "</bold>"));
        btn.lore(
                Theme.text("Go to " + Theme.highlight(name) + " category"),
                Theme.text("Models loaded: " + Theme.highlight(size))
        );
        return btn;
    }

    public static Button back() {
        SwitchButton btn = new SwitchButton(Material.BARRIER, ModelSelector.getCategoryGUI());
        btn.name(Theme.main("<bold>Back</bold>"));
        btn.lore(Theme.text("Go back to previous menu"));
        return btn;
    }

    public static Button give(String category, Model.Override override) {
        BaseItem item = new BaseItem(Parser.material(category, Material.PAPER));
        item.name(Theme.mainC(override.getName()));
        item.lore(
                Theme.text("CustomModelData: ") + Theme.highlight(override.getPredicate().getCustom_model_data()),
                Theme.text("Item: ") + Theme.highlight(item.getType())
        );
        Button btn = new Button(item, (e, gui) -> {
            BaseItem giveItem = new BaseItem(Parser.material(category, Material.PAPER));
            giveItem.setCustomModelData(override.getPredicate().getCustom_model_data());
            e.getPlayer().getInventory().addItem(giveItem);
        });
        btn.setCustomModelData(override.getPredicate().getCustom_model_data());
        return btn;

    }

}
