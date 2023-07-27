package fi.natroutter.modelselector.object;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class Model {

    private String parent;
    private Map<String, String> textures;
    private List<Override> overrides;

    @Getter @Setter
    public static class Override {
        private Predicate predicate;
        private String model;

        public String getName() {
            if (model.contains(":")) {
                model = model.substring(model.indexOf(":") + 1);
            }
            return model.substring(model.lastIndexOf("/") + 1).replace("_", " ");
        }

    }

    @Getter @Setter
    public static class Predicate {
        private int custom_model_data;
    }


}
