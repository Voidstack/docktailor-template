package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.DocktailorService;
import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.template.dock.*;
import com.enosistudio.docktailor.template.generated.R;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class App extends Application {

    private final String saveFolder = Path.of(System.getenv("APPDATA"), "enosistudio", "docktailor-template").toString();

    private final Map<String, String> predefinedUiFiles = Map.of(
            "Configuration #1", Path.of(saveFolder, "docktailor_1.ui").toString(),
            "Configuration #2", Path.of(saveFolder, "docktailor_2.ui").toString(),
            "Configuration #3", Path.of(saveFolder, "docktailor_3.ui").toString()
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        log.info("MainApp : Application start");

        // setup css
        Application.setUserAgentStylesheet(R.com.enosistudio.docktailor.template.css.modena.mainCss.toExternalForm());

        GlobalSettings docktailorSettings = DocktailorService.getInstance()
                .setConfigFile(Path.of(saveFolder, "docktailor.conf").toString())
                .setDefaultUiFile(R.com.enosistudio.docktailor.template.docktailorDefaultUi.getURL().getFile())
                .setPredefinedUiFiles(predefinedUiFiles)
                .setDraggableTab(PersonDockPane.class, TestDockPane.class, RedDockPane.class, BlueDockPane.class, GreenDockPane.class)
                .init();
        TemplateDockSchema demoDockSchema = new TemplateDockSchema(docktailorSettings);
        DocktailorService.openDockSystemConf(demoDockSchema);
    }
}
