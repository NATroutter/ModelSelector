package fi.natroutter.modelselector.guis;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.object.*;
import fi.natroutter.modelselector.object.Color;
import fi.natroutter.modelselector.utils.Theme;
import fi.natroutter.natlibs.handlers.guibuilder.Button;
import fi.natroutter.natlibs.handlers.guibuilder.GUIFrame;
import fi.natroutter.natlibs.handlers.guibuilder.SwitchButton;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.Parser;
import fi.natroutter.natlibs.utilities.SkullCreator;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public static BaseItem filler() {
        BaseItem btn = new BaseItem(Material.BLACK_STAINED_GLASS_PANE);
        btn.name(" ");
        return btn;
    }

    public static Button color(ColorTypes type) {
        return new Button(Items.colorDisplay(type,false), (e, g)->{
            ColorGUI.selectedColor.put(e.getPlayer().getUniqueId(), type.getColor());
        });
    }

    private static Color colorUp(Color color, RGB rgb, int increment) {
        switch (rgb) {
            case RED:
                int r = color.getR() + increment;
                if (r >= 255) {r=255;}
                color.setR(r);
            case GREEN:
                int g = color.getG() + increment;
                if (g >= 255) {g=255;}
                color.setG(g);
            case BLUE:
                int b = color.getB() + increment;
                if (b >= 255) {b=255;}
                color.setB(b);
        }
        return color;
    }

    private static Color colorDown(Color color, RGB rgb, int increment) {
        switch (rgb) {
            case RED:
                int r = color.getR() - increment;
                if (r <= 0) {r=0;}
                color.setR(r);
            case GREEN:
                int g = color.getG() - increment;
                if (g <= 0) {g=0;}
                color.setG(g);
            case BLUE:
                int b = color.getB() - increment;
                if (b <= 0) {b=0;}
                color.setB(b);
        }
        return color;
    }

    public static Button changeColor(RGB rgb, Dire dire) {
        BaseItem item = SkullCreator.create(" ", dire == Dire.UP ? Textures.UP : Textures.DOWN);
        String what = dire == Dire.UP ? "increase" : "decrease";
        item.name(Theme.main("<bold>"+ Utilities.toTitleCase(what)+"</bold>"));
        item.lore(
                Theme.text(Theme.highlight("Left-Click")+Theme.separator(" | ")+"to "+what+" color by 1"),
                Theme.text(Theme.highlight("Right-Click")+Theme.separator(" | ")+"to "+what+" color by 10")
        );
        item.addItemFlags(ItemFlag.values());

        return new Button(item, (e,g)->{
            ClickType click = e.getClickType();
            if (click.equals(ClickType.LEFT) || click.equals(ClickType.RIGHT)) {
                int increment = click == ClickType.LEFT ? 1 : 10;
                Color color = ColorGUI.selectedColor.getOrDefault(e.getPlayer().getUniqueId(), new Color(0,0,0));
                switch (dire) {
                    case UP -> ColorGUI.selectedColor.put(e.getPlayer().getUniqueId(), colorUp(color, rgb, increment));
                    case DOWN -> ColorGUI.selectedColor.put(e.getPlayer().getUniqueId(), colorDown(color, rgb, increment));
                }
            }
        });
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
        SwitchButton btn = new SwitchButton(Parser.material(name, Material.PAPER),ModelSelector.getGiveGUI(), List.of(name));

        btn.name(Theme.main("<bold>" + name + "</bold>"));
        btn.lore(
                Theme.text("Go to " + Theme.highlight(name) + " category"),
                Theme.text("Models loaded: " + Theme.highlight(size))
        );
        return btn;
    }

    public static Button rgbDisplay(RGB rgb, int value) {
        BaseItem item = switch (rgb) {
            case RED -> Items.red(value);
            case GREEN -> Items.green(value);
            case BLUE -> Items.blue(value);
        };
        return new Button(item, (e, gui) -> {
            Color color = ColorGUI.selectedColor.getOrDefault(e.getPlayer().getUniqueId(), new Color(0,0,0));

           if (e.getClickType().isLeftClick()) {
               Color c = switch (rgb) {
                    case RED -> new Color(255, color.getG(), color.getB());
                    case GREEN -> new Color(color.getR(), 255, color.getB());
                    case BLUE -> new Color(color.getR(), color.getG(), 255);
               };
               ColorGUI.selectedColor.put(e.getPlayer().getUniqueId(), c);
           } else if (e.getClickType().isRightClick()) {
               Color c = switch (rgb) {
                   case RED -> new Color(0, color.getG(), color.getB());
                   case GREEN -> new Color(color.getR(), 0, color.getB());
                   case BLUE -> new Color(color.getR(), color.getG(), 0);
               };
               ColorGUI.selectedColor.put(e.getPlayer().getUniqueId(), c);
           }
        });
    }

    public static Button toggle(Player p) {
        boolean state = ColorGUI.enabled.getOrDefault(p.getUniqueId(), false);
        BaseItem item = SkullCreator.create(
                "<bold>"+(state ? Theme.main("Enabled") : Theme.main("Disabled"))+"</bold>",
                state ? Textures.ENABLED : Textures.DISABLED
        );
        item.lore(
                Theme.text("Should the color be applied to models?")
        );

        return new Button(item, (e, gui) -> ColorGUI.enabled.put(e.getPlayer().getUniqueId(), !state));
    }

    public static Button customColor() {
        SwitchButton btn = new SwitchButton(SkullCreator.create(" ", Textures.RAINBOW), ModelSelector.getCustomColorGUI());
        btn.name(Theme.main("<bold>Custom Color</bold>"));
        btn.lore(Theme.text("Choose custom rgb color"));
        return btn;
    }

    public static SwitchButton back(GUIFrame gui) {
        SwitchButton btn = new SwitchButton(SkullCreator.create(" ", Textures.BACK), gui);
        btn.name(Theme.main("<bold>Back</bold>"));
        btn.lore(Theme.text("Go back to previous menu"));
        return btn;
    }

    public static Button give(Player p, String category, Model.Override override) {
        List<String> lore = new ArrayList<>();
        BaseItem item = new BaseItem(Parser.material(category, Material.PAPER));
        item.name(Theme.mainC(override.getName()));

        lore.add(Theme.text("CustomModelData: ") + Theme.highlight(override.getPredicate().getCustom_model_data()));
        lore.add(Theme.text("Item: ") + Theme.highlight(item.getType()));

        if (ColorGUI.enabled.getOrDefault(p.getUniqueId(), false)) {
            if (item.getItemMeta() instanceof LeatherArmorMeta meta) {
                Color color = ColorGUI.selectedColor.getOrDefault(p.getUniqueId(), new Color(0,0,0));
                meta.setColor(color.getColor());
                lore.add(Theme.text("Color: ") + "&8(&c"+color.getR()+"&7, &a"+color.getG()+"&7, &b"+color.getB()+"&8)");
                item.setItemMeta(meta);

            }
        }
        item.lore(lore);

        Button btn = new Button(item, (e, gui) -> {
            BaseItem giveItem = new BaseItem(Parser.material(category, Material.PAPER));
            giveItem.setCustomModelData(override.getPredicate().getCustom_model_data());
            if (ColorGUI.enabled.getOrDefault(p.getUniqueId(), false)) {
                if ((giveItem.getItemMeta() != null && giveItem.getItemMeta() instanceof LeatherArmorMeta giveMeta)  && (item.getItemMeta() != null && item.getItemMeta() instanceof LeatherArmorMeta meta)) {
                    giveMeta.setColor(meta.getColor());
                    giveItem.setItemMeta(giveMeta);
                }
            }
            e.getPlayer().getInventory().addItem(giveItem);
        });
        btn.setCustomModelData(override.getPredicate().getCustom_model_data());
        return btn;

    }

}
