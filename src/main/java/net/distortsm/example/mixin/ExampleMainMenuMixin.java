package net.distortsm.example.mixin;

import org.schema.common.GraphicsInterface;
import org.schema.game.client.controller.GameMainMenuController;
import org.schema.game.client.event.MouseEvent;
import org.schema.game.client.view.GuiNode;
import org.schema.game.client.view.gui.Callable;
import org.schema.game.client.view.gui.DialogPanel;
import org.schema.game.client.view.gui.GUIAnchor;
import org.schema.game.client.view.mainmenu.MainMenuGUI;
import org.schema.schine.graphicsengine.core.GLFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.distortsm.example.ExampleDialogPanel;

@Mixin(value = MainMenuGUI.class)
public abstract class ExampleMainMenuMixin extends GUIAnchor {
	public ExampleMainMenuMixin(GraphicsInterface a) {
		super(a);
	}

	public ExampleMainMenuMixin(GraphicsInterface a, float width, float height) {
		super(a, width, height);
	}

	@Shadow
	private void addButton(String string, Callable callable, Object object) { }

	@Shadow
	private static void showDialog(MainMenuGUI parent) {}

	@Inject(
		method = "onInit()V",
		at = @At(
			value = "INVOKE",
			target = "Lorg/schema/game/client/view/mainmenu/MainMenuGUI;addButton(Ljava/lang/String;Lorg/schema/game/client/view/gui/Callable;Ljava/lang/Object;)V",
			ordinal = 3,
			remap = true),
		remap = false)
	public void onInit(CallbackInfo info) {
		final MainMenuGUI parent = (MainMenuGUI)(Object)this;
		addButton("Spaaaaaaaaaaaaaaaace", new Callable() {
			@Override
			public boolean isOccluded() {
				return !parent.isActive();
			}

			@Override
			public void callback(GuiNode node, MouseEvent mouseEvent) {
				if (mouseEvent.leftDown()) {
					showDialog(parent);
					GameMainMenuController controller = (GameMainMenuController)parent.getState();
					DialogPanel hello = new ExampleDialogPanel(controller);
					hello.getInputPanel().attachToParent(false);
					hello.getInputPanel().onInit();
					hello.getInputPanel().window.setPos(470.0F, 35.0F, 0.0F);
					hello.getInputPanel().window.setWidth((float)(GLFrame.getWidth() - 435));
					hello.getInputPanel().window.setHeight((float)(GLFrame.getHeight() - 400));
					hello.activate();
				}
			}
		}, null);
	}
}