package fi.natroutter.modelselector.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Setter @Getter
public class Color {
    private int r;
    private int g;
    private int b;

    public org.bukkit.Color getColor() {
        return org.bukkit.Color.fromRGB(r, g, b);
    }

}