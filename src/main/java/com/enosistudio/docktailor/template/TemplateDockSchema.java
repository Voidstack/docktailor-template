package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.AGlobalSettings;
import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.fxdock.FxDockPane;
import com.enosistudio.docktailor.fxdock.FxDockSchema;
import com.enosistudio.docktailor.fxdock.internal.IDockPane;
import com.enosistudio.docktailor.fxdock.internal.ServiceDocktailor;
import javafx.stage.Stage;

import java.util.Iterator;

public class TemplateDockSchema  extends FxDockSchema {
    protected TemplateDockSchema(AGlobalSettings s) {
        super(s);
    }

    public TemplateDockSchema() {
        super(GlobalSettings.getInstance());
    }

    public FxDockPane createPane(String id) throws IllegalArgumentException {
        Iterator<IDockPane> var2 = ServiceDocktailor.getInstance().getNewInstances().iterator();

        IDockPane newInstance;
        do {
            if (!var2.hasNext()) {
                throw new IllegalArgumentException("Le fichier de configuration pour docktailor est corrompue");
            }

            newInstance = var2.next();
        } while(!id.equals(newInstance.getTabName()));

        return newInstance.createDockPane();
    }

    public Stage createWindow(String name) {
        return new TemplateDockWindow();
    }

    public Stage createDefaultWindow() {
        return new TemplateDockWindow();
    }
}
