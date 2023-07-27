package fi.natroutter.modelselector.handlers;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.object.Model;
import fi.natroutter.modelselector.object.ResourceKey;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ModelHandler {

    private Plugin instance;
    private Gson gson = new Gson();

    private PackHandler packHandler = ModelSelector.getPackHandler();

    private ConcurrentHashMap<String, Model> modelCache = new ConcurrentHashMap<>();

    public ModelHandler(Plugin instance) {
        this.instance = instance;
        loadToCache();
    }


    public void loadToCache() {
        if (!packHandler.getDestination().exists()) {
            console("Failed to load models, pack folder does not exist!");
        }
        modelCache.clear();

        getModelFiles((name, file) -> {
            console("Loading model: " + name);
            try {
                JsonReader jsonReader = new JsonReader(new FileReader(file));
                Model model = gson.fromJson(jsonReader, Model.class);
                if (model != null) {

                    String modelName = Files.getNameWithoutExtension(file.getName());
                    modelCache.put(modelName, model);

                    console("Loaded model: §e" + name + "§c to cache");
                } else {
                    console("Failed to read model file: §e" + name);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        console("Loaded " + modelCache.size() + " models");
    }



    private void getModelFiles(BiConsumer<String, File> consumer) {
        File modelFolder = new File(packHandler.getDestination().getAbsolutePath(), "assets/minecraft/models/item");
        if (!modelFolder.isDirectory()) return;
        if (modelFolder == null || !modelFolder.exists()) return;

        for (File modelFile : modelFolder.listFiles()) {

            if (modelFile == null || !modelFile.exists()) continue;

            if (modelFile.getName().endsWith(".json")) {
                consumer.accept(modelFile.getName(), modelFile);
            }
        }
    }

    public List<String> getModelNames() {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Model> cached : modelCache.entrySet()) {
            names.add(cached.getKey());
        }
        return names;
    }

    public int getCachedSize() {
        return modelCache.size();
    }

    public int getModelCount(String name) {
        for (Map.Entry<String, Model> cached : modelCache.entrySet()) {
            if (!cached.getKey().equalsIgnoreCase(name)) continue;
            return cached.getValue().getOverrides().size();
        }
        return 0;
    }

    public void getModels(BiConsumer<String, Model> consumer) {
        for (Map.Entry<String, Model> cached : modelCache.entrySet()) {
            consumer.accept(cached.getKey(), cached.getValue());
        }
    }

    public boolean hasModel(String name) {
        return modelCache.containsKey(name);
    }

    public Model getModel(String name) {
        for (Map.Entry<String, Model> cached : modelCache.entrySet()) {
            if (!cached.getKey().equalsIgnoreCase(name)) continue;
            return cached.getValue();
        }
        return null;
    }

    private void console(String msg) {
        Bukkit.getConsoleSender().sendMessage("§4[ModelSelector] §c" + msg);
    }


}
