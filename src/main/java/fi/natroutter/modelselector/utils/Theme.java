package fi.natroutter.modelselector.utils;

import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import java.util.Arrays;

public class Theme {


    public static Component withPrefix(Object value) {
        return Component.join(JoinConfiguration.noSeparators(), Arrays.asList(
                Utilities.translateColors(prefix() ),
                Utilities.translateColors(separator(" » ")),
                Utilities.translateColors(text(value))
        ));
    }

    public static String header() {
        return "<bold><color:#545454><st>━━━━━━━━━━━━</st>| <gradient:#f2272a:#f3393d>ModelSelector</gradient> <color:#545454>|<st>━━━━━━━━━━━━</st></bold>";
    }
    public static String prefix() {
        return prefix("ModelSelector");
    }
    public static String prefix(String value) {
        return "<bold><gradient:#f2272a:#f2ab27>"+value+"</gradient><bold>";
    }
    public static String text(Object value) {
        return "<color:#D3D3D3>"+value+"</color>";
    }
    public static String separator(Object value) {
        return "<bold><color:#666666>" + value + "</color></bold>";
    }
    public static String main(Object value) {
        return "<color:#f2272a>"+value+"</color>";
    }
    public static String highlight(Object value) {
        return "<color:#f76e71>"+value+"</color>";
    }

    //-----------------------------------------------------------------------------------------------------------------

    public static Component help(String command, String description) {
        return Utilities.translateColors(
                Theme.separator("» ") + Theme.highlight(command) + Theme.separator(" | ") + Theme.text(description)
        );
    }

    public static Component compile(String value) {
        return Utilities.translateColors(value);
    }

    public static Component headerC() {
        return Utilities.translateColors(header());
    }
    public static Component prefixC() {
        return Utilities.translateColors(prefix());
    }
    public static Component textC(Object value) {
        return Utilities.translateColors(text(value));
    }
    public static Component separatorC(Object value) {
        return Utilities.translateColors(separator(value));
    }
    public static Component mainC(Object value) {
        return Utilities.translateColors(main(value));
    }
    public static Component highlightC(Object value) {
        return Utilities.translateColors(highlight(value));
    }

}
