package net.distortsm.example;

import org.schema.game.client.controller.GameMainMenuController;
import org.schema.game.client.view.gui.DialogPanel;

public class ExampleDialogPanel extends DialogPanel {
    public ExampleDialogPanel(GameMainMenuController controller) {
        super("Hello", controller, 600, 800, "Hello Space", "Spaaaaaccceeeee");
    }

    @Override
    public final void onDeactivate() {
    }

    @Override
    public final void pressedOK() {
        this.deactivate();
    }
}