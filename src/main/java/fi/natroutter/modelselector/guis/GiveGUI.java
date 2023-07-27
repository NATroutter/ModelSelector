package fi.natroutter.modelselector.guis;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.handlers.ModelHandler;
import fi.natroutter.modelselector.object.Model;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveGUI extends GUIFrame {

    ModelHandler modelHandler = ModelSelector.getModelHandler();

    public GiveGUI() {
        super(Theme.prefix("Give") + Theme.separator(" <page>/<max_page>"), Rows.row6);
        this.setClickSound(Sound.UI_BUTTON_CLICK, 100, 1f);
        this.setCloseSound(Sound.BLOCK_CHEST_CLOSE, 100, 1f);
        this.setOpenSound(Sound.BLOCK_CHEST_OPEN, 100, 1f);
        this.setFiller(Buttons.filler());
        this.setMaxPages(3);
    }

    @Override
    protected boolean onShow(Player player, GUI gui, List<Object> args) {
        String category = args.get(0).toString();

        if (modelHandler.hasModel(category)) {
            Model model = modelHandler.getModel(category);
            List<Button> buttons = model.getOverrides().stream().map(m->
                    Buttons.give(category, m)
            ).toList();

            gui.paginateButtons(buttons);
            gui.setButton(Buttons.back(), Rows.row6, 4);
            gui.addNavigator(
                    new Navigator(Buttons.previous(), Rows.row6, 3),
                    new Navigator(Buttons.next(), Rows.row6, 5)
            );

        } else {
            Bukkit.getConsoleSender().sendMessage("§4Invalid model: " + category);
        }


        if (gui.getMaxPages() > 1) {
            gui.setRawTitle(Theme.prefix(category.toUpperCase()) + Theme.separator(" <page>/<max_page>"));
        } else {
            gui.setRawTitle(Theme.prefix(category.toUpperCase()));
        }
        return true;
    }
}
