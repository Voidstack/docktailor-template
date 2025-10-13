package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.AGlobalSettings;
import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.fx.FxFramework;
import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {
    @Getter
    private static final List<String> ARGS = new ArrayList<>();

    public static void main(String[] args) {
        ARGS.addAll(Arrays.stream(args).toList());
        ServiceDocktailor.IS_DEBUG = ARGS.contains("-debug");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // setup css
        Application.setUserAgentStylesheet(ServiceDocktailor.getDocktailorCss().getAbsoluteURL().toExternalForm());

        ServiceDocktailor.setDocktailorSaveFolder(Path.of(System.getenv("APPDATA"), "enosistudio", "docktailor-template").toString());

        String url = App.class.getResource("/com/enosistudio/docktailor/template/template_default.conf").getFile();
        ServiceDocktailor.setDefaultUiFile(url);

        GlobalSettings.getInstance().setFileProvider(ServiceDocktailor.getInstance().getLastUIConfigUsed());
        AGlobalSettings store = GlobalSettings.getInstance();
        TemplateDockSchema templateDockSchema = new TemplateDockSchema(store);
        FxFramework.openDockSystemConf(templateDockSchema);
    }
}
