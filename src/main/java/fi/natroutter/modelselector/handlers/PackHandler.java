package fi.natroutter.modelselector.handlers;

import fi.natroutter.modelselector.ModelSelector;
import fi.natroutter.modelselector.files.Config;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackHandler {

    private static File dataFolder = ModelSelector.getInstance().getDataFolder();
    private static String packURL;
    @Getter
    private static File destination = new File(dataFolder, "/resourcepack/");
    private static File destinationFile = new File(destination, "pack.zip");

    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public PackHandler() {
        if (destination.exists()) {
            console.sendMessage("§4[ModelSelector] §cPack folder already exists! (skipping install)");
        } else {
            reinstall();
        }
    }

    public boolean reinstall() {
        if (Config.UseOverride.asBoolean()) {
            console.sendMessage("§4[ModelSelector] §cUsing override url! : " + Config.OverrideURL.asString());
            packURL = Config.OverrideURL.asString();
        } else {
            packURL = Bukkit.getServer().getResourcePack();
            if (packURL.isBlank()) {
                console.sendMessage("§4[ModelSelector] §cResourcepack is not installed in server.properties!");
                return false;
            }
        }
        console.sendMessage("§4[ModelSelector] §cInstalling resourcepack!");
        downloadPack(destinationFile);
        unzipPack(destinationFile, destination);
        console.sendMessage("§4[ModelSelector] §cResourcepack installed!");
        return true;
    }

    @SneakyThrows
    private void downloadPack(File destinationFile) {

        if (destination.isDirectory() && destination.exists()) {
            FileUtils.cleanDirectory(destination);
            FileUtils.deleteDirectory(destination);
            console.sendMessage("§4[ModelSelector] §cDeleted old pack!");
        }

        destination.mkdirs();
        console.sendMessage("§4[ModelSelector] §cCreated new pack folder!");

        if (packURL.startsWith("file:///")) {
            console.sendMessage("§4[ModelSelector] §cCopying pack...");

            packURL = packURL.replace("file:///", "");
            File pack = new File(packURL);
            if (pack.exists()) {
                if (destinationFile.exists()) {
                    console.sendMessage("§4[ModelSelector] §cCleaning old files...");
                    destinationFile.delete();
                }

                Files.copy(pack.toPath(), destinationFile.toPath());
            } else {
                console.sendMessage("§4[ModelSelector] §cPack not found!");
            }

            console.sendMessage("§4[ModelSelector] §cPack Copied!");

        } else {
            console.sendMessage("§4[ModelSelector] §cDownloading pack...");
            Connection connection = Jsoup.connect(packURL);
            connection.timeout(5000);
            Connection.Response resultImageResponse = connection.ignoreContentType(true).maxBodySize(0).execute();

            FileOutputStream out = new FileOutputStream(destinationFile);
            out.write(resultImageResponse.bodyAsBytes());
            out.close();
            console.sendMessage("§4[ModelSelector] §cPack downloaded!");
        }
    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    @SneakyThrows
    private void unzipPack(File destinationFile, File destination) {
        console.sendMessage("§4[ModelSelector] §cUnzipping pack...");
        try (ZipFile zipFile = new ZipFile(destinationFile)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(destination,  entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out2 = new FileOutputStream(entryDestination)) {
                        out2.write(in.readAllBytes());
                    }
                }
            }
        }
        console.sendMessage("§4[ModelSelector] §cPack unzipped!");
    }


}
