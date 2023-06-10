package net.refractored.personalkitten.listeners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.refractored.personalkitten.runnables.RegisterCommands;
import org.jetbrains.annotations.NotNull;
import net.refractored.personalkitten.PersonalKitten;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        PersonalKitten.getThreadManager().add(new RegisterCommands());
    }
}
