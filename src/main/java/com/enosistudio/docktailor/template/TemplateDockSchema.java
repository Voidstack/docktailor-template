package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.common.GlobalSettings;
import com.enosistudio.docktailor.fxdock.FxDockPane;
import com.enosistudio.docktailor.fxdock.FxDockSchema;
import com.enosistudio.docktailor.fxdock.internal.DocktailorService;
import com.enosistudio.docktailor.fxdock.internal.IDockPane;

import java.util.Iterator;

/**
 * TemplateDockSchema is a subclass of FxDockSchema that provides methods
 * to create dock panes and dock windows. It uses the global settings
 * and interacts with the ServiceDocktailor to manage dock components.
 */
public class TemplateDockSchema extends FxDockSchema {

    /**
     * Constructs a TemplateDockSchema instance using the global settings.
     */
    public TemplateDockSchema() {
        super(GlobalSettings.getInstance());
    }

    /**
     * Creates a new FxDockPane instance based on the provided ID.
     *
     * @param id The unique identifier of the dock pane to create.
     * @return A new FxDockPane instance matching the given ID.
     * @throws IllegalArgumentException If no matching dock pane is found
     *                                  or if the configuration file is corrupted.
     */
    public FxDockPane createPane(String id) throws IllegalArgumentException {
        Iterator<IDockPane> var2 = DocktailorService.getInstance().getNewInstances().iterator();

        IDockPane newInstance;
        do {
            if (!var2.hasNext()) {
                throw new IllegalArgumentException("Le fichier de configuration pour docktailor est corrompue");
            }

            newInstance = var2.next();
        } while (!id.equals(newInstance.getTabName()));

        return newInstance.createDockPane();
    }

    /**
     * Creates a new TemplateDockWindow instance with the specified name.
     *
     * @param name The name of the dock window to create.
     * @return A new TemplateDockWindow instance.
     */
    public TemplateDockWindow createWindow(String name) {
        return new TemplateDockWindow();
    }

    /**
     * Creates a new default TemplateDockWindow instance.
     *
     * @return A new TemplateDockWindow instance.
     */
    public TemplateDockWindow createDefaultWindow() {
        return new TemplateDockWindow();
    }
}