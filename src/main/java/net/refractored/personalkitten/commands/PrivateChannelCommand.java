package net.refractored.personalkitten.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.EnumSet;

public class PrivateChannelCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent event) {
        if (!(event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            event.reply("You don't have permission to do that.").queue();
            return;
        }

        GuildChannel channel = event.getOption("channel").getAsChannel();
        EnumSet<Permission> allowed = channel.getPermissionContainer().getPermissionOverride(event.getGuild().getPublicRole()).getAllowed();
        allowed.remove(Permission.VIEW_CHANNEL);
        allowed.remove(Permission.VOICE_CONNECT);
        allowed.remove(Permission.VOICE_SPEAK);
        allowed.remove(Permission.VOICE_STREAM);
        allowed.remove(Permission.VOICE_USE_VAD);
        allowed.remove(Permission.MESSAGE_SEND);

        EnumSet<Permission> denied = channel.getPermissionContainer().getPermissionOverride(event.getGuild().getPublicRole()).getDenied();
        denied.add(Permission.VIEW_CHANNEL);
        denied.add(Permission.VOICE_CONNECT);
        denied.add(Permission.VOICE_SPEAK);
        denied.add(Permission.VOICE_STREAM);
        denied.add(Permission.VOICE_USE_VAD);
        denied.add(Permission.MESSAGE_SEND);

        channel.getPermissionContainer().getManager().putPermissionOverride(event.getGuild().getPublicRole(), Permission.getRaw(allowed), Permission.getRaw(denied)).queue();
        event.reply("done mommy").queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("privatechannel", "Makes a channel private.")
                .addOption(OptionType.CHANNEL, "channel", "The channel to make private.", true);
    }

    @Override
    public String getCategory() {
        return "admin";
    }
}
