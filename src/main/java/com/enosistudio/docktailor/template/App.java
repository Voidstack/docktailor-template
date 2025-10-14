package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.fx.FxFramework;
import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {
    @Getter
    private static final List<String> ARGS = new ArrayList<>();

    String defaultUiFile = App.class.getResource("/com/enosistudio/docktailor/template/template_default.conf").getFile();

    public static void main(String[] args) {
        ARGS.addAll(Arrays.stream(args).toList());
        ServiceDocktailor.IS_DEBUG = ARGS.contains("-debug");
        ServiceDocktailor.setDocktailorSaveFolder(Path.of(System.getenv("APPDATA"), "enosistudio", "docktailor-template").toString());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // setup css
        Application.setUserAgentStylesheet(ServiceDocktailor.getDocktailorCss().getAbsoluteURL().toExternalForm());

        // setup default Ui save
        ServiceDocktailor.setDefaultUiFile(defaultUiFile);

        // set file to use to load the app (the last config saved here)
        GlobalSettings.getInstance().setFileProvider(ServiceDocktailor.getInstance().getLastUIConfigUsed());

        FxFramework.openDockSystemConf(new TemplateDockSchema());
    }
}
