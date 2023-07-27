package fi.natroutter.modelselector.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class ResourceKey {

    String namespace;
    String name;

    public String getFull() {
        return namespace + ":" + name;
    }
}
