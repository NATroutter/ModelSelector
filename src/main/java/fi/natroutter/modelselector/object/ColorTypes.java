package fi.natroutter.modelselector.object;

import fi.natroutter.natlibs.utilities.Utilities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum ColorTypes {

    DARK_RED(new Color(170, 0, 0)),
    RED(new Color(255,85,85)),
    GOLD(new Color(255,170,0)),
    YELLOW(new Color(255,255,85)),
    DARK_GREEN(new Color(0,170,0)),
    GREEN(new Color(85,255,85)),
    AQUA(new Color(85,255,255)),
    DARK_AQUA(new Color(0,170,170)),
    DARK_BLUE(new Color(0,0,170)),
    BLUE(new Color(85,85,255)),
    LIGHT_PURPLE(new Color(255,85,255)),
    DARK_PURPLE(new Color(170,0,170)),
    WHITE(new Color(255,255,255)),
    GRAY(new Color(170,170,170)),
    DARK_GRAY(new Color(85,85,85)),
    BLACK(new Color(0,0,0)),
    CUSTOM(new Color(0,0,0));

    @Getter
    private Color color;

    public Component getName() {
        return Utilities.translateColors(getNameString());
    }

    public String getNameString() {
        if (this.equals(CUSTOM)) {
            return "&8(&c"+color.getR()+"&7, &a"+color.getG()+"&7, &b"+color.getB()+"&8)";
        }
        return "<color:"+this.name().toLowerCase()+">" + Utilities.toTitleCase(this.name().replace("_", " ")) + "</color>";
    }
}
