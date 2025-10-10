//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.*;
import com.enosistudio.docktailor.fx.FxFramework;
import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Setter
@Singleton
public class TemplateDockSettings extends AGlobalSettings {
    private SettingsProviderBase provider;
    private static TemplateDockSettings instance;

    private final URL defaultConfURL = getClass().getResource("/com/enosistudio/docktailor/template/template_default.conf");

    // Le chemin du fichier dans le dossier AppData
    private final Path targetPath = Path.of(System.getenv("APPDATA"), "enosistudio", "docktailor", "docktailor.conf");

    @Getter
    private final File savedConfFile;

    private TemplateDockSettings() throws IOException {
        // créer le dossier s’il n’existe pas
        Files.createDirectories(targetPath.getParent());

        // créer/copier le fichier seulement s’il n’existe pas
        if (Files.notExists(targetPath)) {
            try (InputStream is = defaultConfURL.openStream()) {
                Files.copy(is, targetPath);
            }
        }
        savedConfFile = targetPath.toFile();
        setDefaultFileProvider();
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

    private void setDefaultFileProvider() throws IOException {
        try (InputStream is = defaultConfURL.openStream()) {
            Files.copy(is, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        FileSettingsProvider p = new FileSettingsProvider(savedConfFile);
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

    public void saveCurrentConf() {
        FxFramework.storeLayout(savedConfFile.getAbsolutePath());
        ServiceDocktailor.getInstance().setLastUIConfigUsed(savedConfFile.getAbsolutePath());
        ServiceDocktailor.getInstance().getConfigDocktailor().save();
    }

    public void save(String fileName) {
        this.provider.save(fileName);
    }

    public void resetRuntime() {
        this.provider.resetRuntime();
    }

    public void loadDefault() {
        open();
    }

    /**
     * Ouvre l’explorateur de fichiers Windows à l’emplacement du fichier de configuration.
     */
    public void openInExplorer() {
        try {
            File file = targetPath.toFile();
            if (file.exists()) {
                // Ouvre l’explorateur sur le fichier sélectionné
                new ProcessBuilder("explorer.exe", "/select,", file.getAbsolutePath()).start();
            } else {
                // Si le fichier n’existe pas encore, ouvre simplement le dossier
                new ProcessBuilder("explorer.exe", file.getParentFile().getAbsolutePath()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible d’ouvrir l’explorateur de fichiers", e);
        }
    }

    public void open() {
        TemplateDockSchema templateDockSchema = new TemplateDockSchema(this);
        FxFramework.openDockSystemConf(templateDockSchema);
    }

    public void loadCurrentSavedConf() {
//         FxFramework.openDockSystemConf(savedConfFile.getPath());

        setProvider(new FileSettingsProvider(savedConfFile));

        TemplateDockSchema newSchema = new TemplateDockSchema(this);
        if (FxFramework.getSchema() != null) {
            FxFramework.getSchema().closeLayout();
        }

        FxFramework.setSchema(newSchema);
        FxFramework.getSchema().openLayout();
    }
}
