//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.fx.DocktailorUtility;
import com.enosistudio.docktailor.fx.FxAction;
import com.enosistudio.docktailor.fx.FxMenuBar;
import com.enosistudio.docktailor.fx.LocalSettings;
import com.enosistudio.docktailor.fxdock.FxDockWindow;
import com.enosistudio.docktailor.fxdock.internal.DocktailorService;
import com.enosistudio.docktailor.other.PopupSaveUI;
import com.enosistudio.docktailor.sample.mvc.MainApp;
import com.enosistudio.docktailor.sample.mvc.controller.PersonDockPane;
import com.enosistudio.docktailor.sample.mvc.controller.TestDockPane;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.paint.Color;
import lombok.Getter;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.CustomCaption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TemplateDockWindow extends FxDockWindow {
    private static final String FILE_1 = Path.of(DocktailorService.getDocktailorSaveFolder(), "docktailor_1.ui").toString();

    @Getter
    private final PopupSaveUI popupSaveUI = new PopupSaveUI();

    private static final Logger log = LoggerFactory.getLogger(TemplateDockWindow.class);
    public final FxAction windowCheckAction = new FxAction();

    public TemplateDockWindow() {
        super("TemplateDockWindow");

        this.getIcons().add(MainApp.IMAGE);
        FxMenuBar fxMenuBar = this.createMenu();
        this.getScene().widthProperty().addListener((obs, oldVal, newVal) -> fxMenuBar.setMaxWidth(newVal.doubleValue() - 138.0));
        this.setTop(fxMenuBar);
        this.setTitle("DockTailor example");
        LocalSettings.get(this).add("CHECKBOX_MENU", this.windowCheckAction);
        Platform.runLater(() -> CustomCaption.useForStage(this, (new CaptionConfiguration()).setCaptionDragRegion(fxMenuBar).setControlBackgroundColor(Color.rgb(60, 63, 65))));

        this.getOnDocktailorEvent().addListener(this::showPopup);

        this.getPopupSaveUI().setOnSave(e -> {
            actionSaveSettings(FILE_1);
            PopupSaveUI.hides();
        });
    }

    private void showPopup() {
        popupSaveUI.show(this.getParentStackPane());
    }

    protected FxMenuBar createMenu() {
        FxMenuBar fxMenuBar = new FxMenuBar();
        Menu menuApplication = new Menu("Application");
        fxMenuBar.add(menuApplication);
        Platform.runLater(() -> {
            MenuItem menuItemOpenConf = new MenuItem("\uD83D\uDCC2 Ouvrir l'explorateur de fichiers");
            menuItemOpenConf.setOnAction(e -> openFolder(DocktailorService.getDocktailorSaveFolder()));
            menuApplication.getItems().add(menuItemOpenConf);

            MenuItem menuItemSaveLayout = new MenuItem("\uD83D\uDCBE Sauvegarder la configuration");
            menuItemSaveLayout.setOnAction(e -> actionSaveSettings(FILE_1));
            menuApplication.getItems().add(menuItemSaveLayout);

            MenuItem menuItemLoadLayout = new MenuItem("Recharger la configuration");
            menuItemLoadLayout.setOnAction(e -> actionLoadSettings(FILE_1));
            menuApplication.getItems().add(menuItemLoadLayout);

            MenuItem menuItemDefaultConf = new MenuItem("Charger la configuration par défaut");
            menuItemDefaultConf.setOnAction(e -> actionLoadSettings(DocktailorService.getDefaultUiFile()));
            menuApplication.getItems().add(menuItemDefaultConf);

            menuApplication.getItems().add(new SeparatorMenuItem());

            MenuItem menuLeaveApp = new MenuItem("❌ Quitter l'application");
            menuLeaveApp.setOnAction(e -> DocktailorUtility.exit());
            menuApplication.getItems().add(menuLeaveApp);
        });
        Menu menuWindows = new Menu("Windows");

        DocktailorService.getInstance().setAll(PersonDockPane.class, TestDockPane.class);

        menuWindows.getItems().setAll(DocktailorService.getInstance().createMenuItems(this));
        fxMenuBar.add(menuWindows);
        return fxMenuBar;
    }


    /**
     * Ouvre l’explorateur de fichiers Windows à l’emplacement du fichier de configuration.
     */
    private void openFolder(String folderString) {
        try {
            File file = new File(folderString);
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

    protected static void actionLoadSettings(String fileName) {
        log.info("Docktailor : Load default interface configuration : {}", fileName);
        GlobalSettings.getInstance().setFileProvider(fileName);
        TemplateDockSchema templateDockSchema = new TemplateDockSchema();
        DocktailorUtility.openDockSystemConf(templateDockSchema);
        DocktailorService.getInstance().setLastUIConfigUsed(fileName);
        DocktailorService.getInstance().getConfigDocktailor().save();
    }

    /**
     * Use GlobalSettings.FILE...
     *
     * @param fileName :
     */
    protected static void actionSaveSettings(String fileName) {
        log.info("Docktailor : Save current interface configuration in {}", fileName);
        DocktailorUtility.storeLayout(fileName);
        DocktailorService.getInstance().setLastUIConfigUsed(fileName);
        DocktailorService.getInstance().getConfigDocktailor().save();
    }
}
