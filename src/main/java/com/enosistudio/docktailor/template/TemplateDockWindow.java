//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.fx.FxAction;
import com.enosistudio.docktailor.fx.FxFramework;
import com.enosistudio.docktailor.fx.FxMenuBar;
import com.enosistudio.docktailor.fx.LocalSettings;
import com.enosistudio.docktailor.fxdock.FxDockWindow;
import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import com.enosistudio.docktailor.sample.mvc.MainApp;
import com.enosistudio.docktailor.sample.mvc.controller.PersonDockPane;
import com.enosistudio.docktailor.sample.mvc.controller.TestDockPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.CustomCaption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class TemplateDockWindow extends FxDockWindow {
    private static final Logger log = LoggerFactory.getLogger(TemplateDockWindow.class);
    public final FxAction windowCheckAction = new FxAction();

    public TemplateDockWindow() {
        super("TemplateDockWindow");

        this.getIcons().add(MainApp.IMAGE);
        FxMenuBar fxMenuBar = this.createMenu();
        this.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            fxMenuBar.setMaxWidth(newVal.doubleValue() - 138.0);
        });
        this.setTop(fxMenuBar);
        this.setTitle("DockTailor example");
        LocalSettings.get(this).add("CHECKBOX_MENU", this.windowCheckAction);
        Platform.runLater(() -> {
            CustomCaption.useForStage(this, (new CaptionConfiguration()).setCaptionDragRegion(fxMenuBar).setControlBackgroundColor(Color.rgb(60, 63, 65)));
        });
    }

    protected static void actionLoadSettings(String fileName) {
        log.info("Docktailor : Load default interface configuration : {}", fileName);
        FxFramework.openDockSystemConf(fileName);
        ServiceDocktailor.getInstance().setLastUIConfigUsed(fileName);
        ServiceDocktailor.getInstance().getConfigDocktailor().save();
    }

    protected static void actionSaveSettings(String fileName) {
        log.info("Docktailor : Save current interface configuration in {}", fileName);
        FxFramework.storeLayout(fileName);
        ServiceDocktailor.getInstance().setLastUIConfigUsed(fileName);
        ServiceDocktailor.getInstance().getConfigDocktailor().save();
    }

    protected FxMenuBar createMenu() {
        FxMenuBar fxMenuBar = new FxMenuBar();
        Menu menuApplication = new Menu("Application");
        fxMenuBar.add(menuApplication);
        Platform.runLater(() -> {
            MenuItem menuItemOpenConf = new MenuItem("Ouvrir l'explorateur de fichiers");
            menuItemOpenConf.setOnAction(e -> TemplateDockSettings.getInstance().openInExplorer());
            menuApplication.getItems().add(menuItemOpenConf);

            MenuItem menuItemSaveLayout = new MenuItem("Sauvegarder la configuration");
            menuItemSaveLayout.setOnAction(e -> TemplateDockSettings.getInstance().saveCurrentConf());
            menuApplication.getItems().add(menuItemSaveLayout);

            MenuItem menuItemLoadLayout = new MenuItem("Recharger une configuration sauvegardée");
            menuItemLoadLayout.setOnAction(e -> {
                TemplateDockSettings.getInstance().loadCurrentSavedConf();
            });
            menuApplication.getItems().add(menuItemLoadLayout);

            MenuItem menuItemDefaultConf = new MenuItem("Charger la configuration par défaut");
            menuItemDefaultConf.setOnAction(e -> TemplateDockSettings.getInstance().loadDefault());
            menuApplication.getItems().add(menuItemDefaultConf);
            MenuItem menuLeaveApp = new MenuItem("Quitter l'application");
            menuLeaveApp.setOnAction(e -> FxFramework.exit());
            menuApplication.getItems().add(menuLeaveApp);
        });
        Menu menuWindows = new Menu("Windows");

        ServiceDocktailor.getInstance().setAll(PersonDockPane.class, TestDockPane.class);

        menuWindows.getItems().setAll(ServiceDocktailor.getInstance().createMenuItems(this));
        fxMenuBar.add(menuWindows);
        return fxMenuBar;
    }

    private CustomMenuItem addCustomConfiguration(String strLabel, String fileName) {
        HBox hbox = new HBox();
        CustomMenuItem menuCustomSave1 = new CustomMenuItem(hbox);
        menuCustomSave1.setHideOnClick(false);
        hbox.setSpacing(3.0);
        hbox.setPadding(Insets.EMPTY);
        hbox.setAlignment(Pos.CENTER);
        Label label = new Label(strLabel);
        Region separator = new Region();
        HBox.setHgrow(separator, Priority.ALWAYS);
        hbox.getChildren().addAll(label, separator);
        Button btnSave = new Button("Sauvegarder");
        btnSave.setOnAction(event -> actionSaveSettings(fileName));
        hbox.getChildren().add(btnSave);
        if (Files.exists(Path.of(fileName))) {
            Button btnLoad = new Button("Charger");
            btnLoad.setOnAction(event -> actionLoadSettings(fileName));
            hbox.getChildren().add(btnLoad);
        }

        return menuCustomSave1;
    }
}
