package dev.unethicalite.scripts.kebabs;

import dev.hoot.bot.script.BotScript;
import dev.hoot.bot.script.ScriptMeta;
import dev.hoot.bot.script.ScriptThread;
import dev.unethicalite.scripts.kebabs.tasks.BankKebabs;
import dev.unethicalite.scripts.kebabs.tasks.BuyKebabs;
import dev.unethicalite.scripts.kebabs.tasks.ScriptTask;

// This annotation is required in order for the client to detect it as a script.
@ScriptMeta(
        value = "kebab-buyer",
        version = "1.0.0"
)
public class KebabBuyer extends BotScript {
    private static final ScriptTask[] TASKS = new ScriptTask[] {
            new BankKebabs(),
            new BuyKebabs()
    };

    /**
     * Gets executed whenever a script starts.
     * Can be used to for example initialize script settings, or perform tasks before starting the loop logic.
     * @param args any script arguments passed to the script, separated by spaces.
     */
    @Override
    protected void onStart(String... args) {

    }

    /**
     * Any logic passed inside this method will be repeatedly executed by an internal loop that calls this method.
     * See {@link ScriptThread#run()} for more information.
     * @return the amount of milliseconds to sleep after each loop iteration.
     */
    @Override
    protected int loop() {
        // Here I use task-based logic. You can also just write the entire script logic
        for (ScriptTask task : TASKS) {
            if (task.validate()) {
                // Perform the task and store the sleep value
                int sleep = task.execute();
                // If this task blocks the next task, return the sleep value and the internal loop will sleep for this amount of time
                if (task.blocking()) {
                    return sleep;
                }
            }
        }

        return 1000;
    }
}
