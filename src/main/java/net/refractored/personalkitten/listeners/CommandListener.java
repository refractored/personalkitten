package net.refractored.personalkitten.listeners;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.refractored.personalkitten.runnables.RegisterCommands;
import net.refractored.personalkitten.commands.AutoCompleteableCommand;
import net.refractored.personalkitten.commands.Command;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (RegisterCommands.getCommand(event.getInteraction().getName()) == null) {
            event.reply("Unable to find command, try again later or contact support.").setEphemeral(true).queue();
        } else {
            RegisterCommands.getCommand(event.getInteraction().getName()).run(event);
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Command command = RegisterCommands.getCommand(event.getInteraction().getName());
        if (RegisterCommands.getCommand(event.getInteraction().getName()) == null) { return; }
        AutoCompleteableCommand autoCompleteableCommand = (AutoCompleteableCommand) command;

        autoCompleteableCommand.runAutoComplete(event);
    }
}
