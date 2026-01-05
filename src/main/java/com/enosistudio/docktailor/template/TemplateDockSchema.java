package com.enosistudio.docktailor.template;

import com.enosistudio.docktailor.DocktailorService;
import com.enosistudio.docktailor.common.AGlobalSettings;
import com.enosistudio.docktailor.fx.fxdock.FxDockPane;
import com.enosistudio.docktailor.fx.fxdock.FxDockSchema;
import com.enosistudio.docktailor.fx.fxdock.internal.IDockPane;

/**
 * TemplateDockSchema is a subclass of FxDockSchema that provides methods
 * to create dock panes and dock windows. It uses the global settings
 * and interacts with the ServiceDocktailor to manage dock components.
 */
public class TemplateDockSchema extends FxDockSchema {

    /**
     * Constructs a TemplateDockSchema instance using the global settings.
     */
    public TemplateDockSchema(AGlobalSettings globalSettings) {
        super(globalSettings);
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
        for (IDockPane newInstance : DocktailorService.getInstance().getNewInstances()) {
            if (id.equals(newInstance.getTabName())) {
                return newInstance.createDockPane();
            }
        }

        throw new IllegalArgumentException("Docktailor configuration file is corrupted");
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