package net.refractored.personalkitten.commands;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;

public interface AutoCompleteableCommand extends Command {
    public void runAutoComplete(CommandAutoCompleteInteractionEvent event);
}
