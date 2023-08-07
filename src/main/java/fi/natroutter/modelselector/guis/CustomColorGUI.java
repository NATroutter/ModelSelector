package fi.natroutter.modelselector.guis;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.object.*;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.Button;
import fi.natroutter.natlibs.handlers.guibuilder.GUI;
import fi.natroutter.natlibs.handlers.guibuilder.GUIFrame;
import fi.natroutter.natlibs.handlers.guibuilder.Rows;
import fi.natroutter.natlibs.utilities.Parser;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomColorGUI extends GUIFrame {

    public CustomColorGUI() {
        super(Theme.prefix("Custom Color"), Rows.row3);
    }

    @Override
    protected boolean onShow(Player player, GUI gui, List<Object> args) {

        //back
        gui.setButton(Buttons.back(ModelSelector.getColorGUI()),Rows.row2,1);

        gui.setButton(Buttons.changeColor(RGB.RED,Dire.UP), Rows.row1, 3);
        gui.setButton(Buttons.changeColor(RGB.GREEN,Dire.UP), Rows.row1, 4);
        gui.setButton(Buttons.changeColor(RGB.BLUE, Dire.UP), Rows.row1, 5);

        Color c = ColorGUI.selectedColor.getOrDefault(player.getUniqueId(), new Color(0,0,0));

        gui.setButton(Buttons.rgbDisplay(RGB.RED, c.getR()), Rows.row2,3);
        gui.setButton(Buttons.rgbDisplay(RGB.GREEN, c.getG()), Rows.row2,4);
        gui.setButton(Buttons.rgbDisplay(RGB.BLUE, c.getB()), Rows.row2,5);

        gui.setItem(Items.colorDisplay(c,true),Rows.row2,7);

        gui.setButton(Buttons.changeColor(RGB.RED,Dire.DOWN), Rows.row3, 3);
        gui.setButton(Buttons.changeColor(RGB.GREEN,Dire.DOWN), Rows.row3, 4);
        gui.setButton(Buttons.changeColor(RGB.BLUE,Dire.DOWN), Rows.row3, 5);

        return true;
    }
}
