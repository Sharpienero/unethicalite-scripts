package dev.unethicalite.scripts.kebabs.tasks;

import dev.hoot.api.entities.NPCs;
import dev.hoot.api.items.Inventory;
import dev.hoot.api.movement.Movement;
import dev.hoot.api.movement.Reachable;
import dev.hoot.api.widgets.Dialog;
import dev.hoot.api.widgets.DialogOption;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;

public class BuyKebabs implements ScriptTask {
    @Override
    public boolean validate() {
        return !Inventory.isFull() && Inventory.contains(ItemID.COINS_995);
    }

    @Override
    public int execute() {
        if (Movement.isWalking()) {
            return 1000;
        }

        NPC karim = NPCs.getNearest("Karim");
        if (karim == null) {
            Movement.walkTo(3274, 3181, 0);
            return 1000;
        }

        if (!Reachable.isInteractable(karim)) {
            Movement.walkTo(karim);
            return 1000;
        }

        Dialog.quickProcess(
                DialogOption.NPC_CONTINUE,
                DialogOption.CHAT_OPTION_TWO,
                DialogOption.PLAYER_CONTINUE
        );

        karim.interact("Talk-to");
        return 300;
    }
}
