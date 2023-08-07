package fi.natroutter.modelselector.guis;


import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.handlers.ModelHandler;
import fi.natroutter.modelselector.object.Items;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CategoryGUI extends GUIFrame {

    private static ModelHandler modelHandler = ModelSelector.getModelHandler();

    public CategoryGUI() {
        super(Theme.prefix("ModelSelector") + Theme.separator(" <page>/<max_page>"), Rows.row6);
        this.setClickSound(Sound.UI_BUTTON_CLICK, 100, 1f);
        this.setCloseSound(Sound.BLOCK_CHEST_CLOSE, 100, 1f);
        this.setOpenSound(Sound.BLOCK_CHEST_OPEN, 100, 1f);
        this.setFiller(Buttons.filler());
    }

    @Override
    protected boolean onShow(Player player, GUI gui, List<Object> args) {

        List<Button> categoryButtons = new ArrayList<>();

        modelHandler.getModels((name, model) ->{
            categoryButtons.add(Buttons.category(name, modelHandler.getModelCount(name)));
        });

        gui.paginateButtons(categoryButtons);
        gui.addNavigator(
                new Navigator(Buttons.previous(), Rows.row6, 3),
                new Navigator(Buttons.next(), Rows.row6, 5)
        );

        gui.setButton(new SwitchButton(Items.editColor(), ModelSelector.getColorGUI()), Rows.row6, 0);

        if (gui.getMaxPages() > 1) {
            gui.setRawTitle(Theme.prefix("ModelSelector") + Theme.separator(" <page>/<max_page>"));
        } else {
            gui.setRawTitle(Theme.prefix("ModelSelector"));
        }
        return true;
    }
}
