//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.enosistudio.docktailor.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enosistudio.docktailor.common.*;
import lombok.Setter;

@Setter
@Singleton
public class TemplateDockSettings extends AGlobalSettings {
    private SettingsProviderBase provider;
    private static TemplateDockSettings instance;

    // Le fichier par défaut est copié depuis les ressources vers le dossier AppData
    private final InputStream in = getClass().getResourceAsStream("/com/enosistudio/docktailor/template/template_default.conf");
    // Le chemin du fichier dans le dossier AppData
    private final Path targetPath = Path.of(System.getenv("APPDATA"), "enosistudio", "docktailor", "docktailor.conf");

    private final File CURRENT_SETTINGS;

    private TemplateDockSettings() throws IOException {
        // créer le dossier s’il n’existe pas
        Files.createDirectories(targetPath.getParent());

        // créer/copier le fichier seulement s’il n’existe pas
        if (Files.notExists(targetPath)) {
            Files.copy(in, targetPath);
        }

        CURRENT_SETTINGS = targetPath.toFile();
        setFileProvider(CURRENT_SETTINGS);
    }

    public static TemplateDockSettings getInstance() {
        if (instance == null) {
            try {
                instance = new TemplateDockSettings();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public void setDefaultFileProvider() throws IOException {
        Files.copy(in, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        this.setFileProvider(CURRENT_SETTINGS);
    }

    private void setFileProvider(File f) {
        FileSettingsProvider p = new FileSettingsProvider(f);
        p.loadQuiet();
        this.setProvider(p);
    }

    public void setStream(String key, SStream stream) {
        this.provider.setStream(key, stream);
    }

    public SStream getStream(String key) {
        return this.provider.getStream(key);
    }

    public void setString(String key, String val) {
        this.provider.setString(key, val);
    }

    public String getString(String key) {
        return this.provider.getString(key);
    }

    public void save() {
        this.provider.save();
    }

    public void save(String fileName) {
        this.provider.save(fileName);
    }

    public void resetRuntime() {
        this.provider.resetRuntime();
    }

}
