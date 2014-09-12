package net.famzangl.minecraft.minebot.ai.task;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.strategy.TaskOperations;
import net.famzangl.minecraft.minebot.ai.task.error.StringTaskError;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

public abstract class PutItemInContainerTask extends AITask {

	private int slotToPlace = 0;
	private boolean placed = false;
	private boolean isFull;
	
	@Override
	public boolean isFinished(AIHelper h) {
		final GuiContainer screen = (GuiContainer) h.getMinecraft().currentScreen;
		return screen != null
				&& placed
				&& (slotToPlace < 0 || isFull || !screen.inventorySlots.getSlot(
						slotToPlace).getHasStack());
	}

	@Override
	public void runTick(AIHelper h, TaskOperations o) {
		final GuiContainer screen = (GuiContainer) h.getMinecraft().currentScreen;
		if (screen == null) {
			o.desync(new StringTaskError("Expected container to be open"));
			return;
		}
		slotToPlace = getStackToPut(h);
		placed = true;
		if (slotToPlace < 0) {
			System.out.println("No item to put.");
			o.desync(new StringTaskError("No itemto put in that slot."));
		} else {
			System.out.println("Moving from slot: " + slotToPlace);
			Slot slot = screen.inventorySlots.getSlot(slotToPlace);
			int oldContent, newContent = getSlotContentCount(slot);
			do {
				oldContent = newContent;
				h.getMinecraft().playerController.windowClick(
						screen.inventorySlots.windowId, slotToPlace, 0, 1,
						h.getMinecraft().thePlayer);
				newContent = getSlotContentCount(slot);
			} while (newContent != oldContent);
			if (newContent > 0) {
				containerIsFull();
			}
		}
	}

	protected void containerIsFull() {
		isFull = true;
	}

	private int getSlotContentCount(Slot slot) {
		return slot.getHasStack() ? slot.getStack().stackSize : 0;
	}

	protected abstract int getStackToPut(AIHelper h);

}