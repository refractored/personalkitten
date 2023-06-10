package net.refractored.personalkitten.runnables;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.refractored.personalkitten.PersonalKitten;
import net.refractored.personalkitten.commands.Command;
import net.refractored.personalkitten.commands.LegacyCommand;
import net.refractored.personalkitten.commands.PrivateChannelCommand;
import net.refractored.personalkitten.commands.PublicChannelCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class RegisterCommands extends EndableRunnable {
    private final static Logger logger = LogManager.getLogger(RegisterCommands.class);

    private static HashMap<CommandData, Command> commands = new HashMap<>();
    private static HashMap<String, LegacyCommand> legacyCommands = new HashMap<>();

    @Override
    public void run() {
        if (isRunning()) {
            logger.info("Registering commands...");
            commands.put(new PublicChannelCommand().getCommandData(), new PublicChannelCommand());
            commands.put(new PrivateChannelCommand().getCommandData(), new PrivateChannelCommand());

            for (CommandData commandData : commands.keySet()) {
                PersonalKitten.getJda().getShards().forEach(jda -> {
                    jda.upsertCommand(commandData).queue();
                });
                logger.trace("Registered command: " + commandData.getName());
            }

            logger.info("Registered commands.");

            logger.info("Registering legacy commands...");
            logger.info("Registered legacy commands.");
        }
        stopped = true;
    }

    @Override
    public String getName() {
        return "RegisterCommands";
    }

    @Nullable
    public static Command getCommand(CommandData commandData) {
        return commands.get(commandData);
    }

    @Nullable
    public static Command getCommand(String name) {
        for (CommandData commandData : commands.keySet()) {
            if (commandData.getName().equals(name)) {
                return commands.get(commandData);
            }
        }
        return null;
    }

    public static HashMap<CommandData, Command> getCommands() {
        return commands;
    }

    @Nullable
    public static LegacyCommand getLegacyCommand(String name) {
        return legacyCommands.get(name);
    }

    public static HashMap<String, LegacyCommand> getLegacyCommands() {
        return legacyCommands;
    }
}
