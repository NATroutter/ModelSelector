package fi.natroutter.modelselector.guis;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.object.Color;
import fi.natroutter.modelselector.object.ColorTypes;
import fi.natroutter.modelselector.object.Items;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.GUI;
import fi.natroutter.natlibs.handlers.guibuilder.GUIFrame;
import fi.natroutter.natlibs.handlers.guibuilder.Rows;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ColorGUI extends GUIFrame {

    public static ConcurrentHashMap<UUID, Color> selectedColor = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, Boolean> enabled = new ConcurrentHashMap<>();

    public ColorGUI() {
        super(Theme.prefix("Color Selector"), Rows.row6);
    }

    @Override
    protected boolean onShow(Player player, GUI gui, List<Object> args) {

        gui.setButton(Buttons.color(ColorTypes.DARK_RED), Rows.row2, 1);
        gui.setButton(Buttons.color(ColorTypes.RED), Rows.row2, 2);
        gui.setButton(Buttons.color(ColorTypes.GOLD), Rows.row2, 3);
        gui.setButton(Buttons.color(ColorTypes.YELLOW), Rows.row2, 4);
        gui.setButton(Buttons.color(ColorTypes.DARK_GREEN), Rows.row2, 5);
        gui.setButton(Buttons.color(ColorTypes.GREEN), Rows.row2, 6);
        gui.setButton(Buttons.color(ColorTypes.AQUA), Rows.row2, 7);

        gui.setButton(Buttons.color(ColorTypes.DARK_AQUA), Rows.row3, 1);
        gui.setButton(Buttons.color(ColorTypes.DARK_BLUE), Rows.row3, 2);
        gui.setButton(Buttons.color(ColorTypes.BLUE), Rows.row3, 3);
        gui.setButton(Buttons.color(ColorTypes.LIGHT_PURPLE), Rows.row3, 4);
        gui.setButton(Buttons.color(ColorTypes.DARK_PURPLE), Rows.row3, 5);
        gui.setButton(Buttons.color(ColorTypes.WHITE), Rows.row3, 6);
        gui.setButton(Buttons.color(ColorTypes.GRAY), Rows.row3, 7);

        gui.setButton(Buttons.color(ColorTypes.DARK_GRAY), Rows.row4, 1);

        Color color = selectedColor.getOrDefault(player.getUniqueId(), new Color(0,0,0));
        gui.setItem(Items.colorDisplay(color,true), Rows.row6, 4);
        gui.setButton(Buttons.customColor(), Rows.row6, 5);

        gui.setButton(Buttons.toggle(player), Rows.row5, 4);

        gui.setButton(Buttons.color(ColorTypes.BLACK), Rows.row4, 7);

        gui.setButton(Buttons.back(ModelSelector.getCategoryGUI()), Rows.row6, 3);


        return true;
    }
}
