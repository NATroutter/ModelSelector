package fi.natroutter.modelselector.object;

import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.SkullCreator;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class Items {

    public static BaseItem colorDisplay(Color color, boolean display) {
        return colorDisplay("&8(&c"+color.getR()+"&7, &a"+color.getG()+"&7, &b"+color.getB()+"&8)", color, display);
    }
    public static BaseItem colorDisplay(ColorTypes color, boolean display) {
        return colorDisplay(color.getNameString(), color.getColor(),display);
    }
    public static BaseItem colorDisplay(String lore, Color color, boolean display) {
        BaseItem item = new BaseItem(Material.LEATHER_CHESTPLATE);
        item.name(Theme.main(display ? "<bold>Current color</bold>" : "<bold>Color preset</bold>"));

        item.lore(
                display ? lore : Theme.text("Color: ") + Theme.highlight(lore)
        );
        item.addItemFlags(ItemFlag.values());
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        for (int i = 0; i < 5; i++) {
            meta.setColor(color.getColor());
        }
        item.setItemMeta(meta);
        return item;
    }



    public static BaseItem editColor() {
        BaseItem item = SkullCreator.create(" ", Textures.COLOR);
        item.name(Theme.main("<bold>Color Selector</bold>"));
        item.lore(
                Theme.text("Some models has possibility"),
                Theme.text("to be dyed to different colors"),
                Theme.text("Here you can change the color"),
                " ",
                Theme.main("<bold>Dyeable models:</bold>"),
                Theme.separator("  - ") + Theme.highlight("Leather horse armor"),
                Theme.separator("  - ") + Theme.highlight("Leather armors")
        );
        item.addItemFlags(ItemFlag.values());
        return item;
    }

    public static BaseItem red(int value) {
        BaseItem item = new BaseItem(Material.RED_CONCRETE);
        item.name("&c&l" + value);
        item.lore(
                Theme.text("Amount of red color"),
                " ",
                Theme.text(Theme.highlight("Left-Click") + Theme.separator(" | ") + "to max this color"),
                Theme.text(Theme.highlight("Right-Click") + Theme.separator(" | ") + "to reset this color")
        );
        item.addItemFlags(ItemFlag.values());
        return item;
    }

    public static BaseItem green(int value) {
        BaseItem item = new BaseItem(Material.GREEN_CONCRETE);
        item.name("&a&l" + value);
        item.lore(
                Theme.text("Amount of green color"),
                " ",
                Theme.text(Theme.highlight("Left-Click") + Theme.separator(" | ") + "to max this color"),
                Theme.text(Theme.highlight("Right-Click") + Theme.separator(" | ") + "to reset this color")
        );
        item.addItemFlags(ItemFlag.values());
        return item;
    }

    public static BaseItem blue(int value) {
        BaseItem item = new BaseItem(Material.BLUE_CONCRETE);
        item.name("&b&l" + value);
        item.lore(
                Theme.text("Amount of blue color"),
                " ",
                Theme.text(Theme.highlight("Left-Click") + Theme.separator(" | ") + "to max this color"),
                Theme.text(Theme.highlight("Right-Click") + Theme.separator(" | ") + "to reset this color")
        );
        item.addItemFlags(ItemFlag.values());
        return item;
    }

}
