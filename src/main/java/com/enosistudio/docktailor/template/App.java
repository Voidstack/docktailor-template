package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import com.enosistudio.generated.R;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {
    public static final List<String> ARGS = new ArrayList<>();

    public static final Image IMAGE = new Image(R.com.enosistudio.docktailor.icons.logoPng.getResourcePath());

//    public static final Image IMAGE = R.loadImage("docktailor/icons/logo.png");
    public static final String TITLE = "DockTailor example";

    public static void main(String[] args) {
        ARGS.addAll(Arrays.stream(args).toList());
        ServiceDocktailor.IS_DEBUG = ARGS.contains("-debug");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // setup css
        Application.setUserAgentStylesheet(ServiceDocktailor.getDocktailorCss().getAbsoluteURL().toExternalForm());

        ServiceDocktailor.getInstance().setDocktailorSaveFolder("template");
        TemplateDockSettings.getInstance().open();
    }
}
