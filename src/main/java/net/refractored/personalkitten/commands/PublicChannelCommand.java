package net.refractored.personalkitten.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.EnumSet;

public class PublicChannelCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent event) {
        if (!(event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            event.reply("You don't have permission to do that.").queue();
            return;
        }

        GuildChannel channel = event.getOption("channel").getAsChannel();
        EnumSet<Permission> allowed = channel.getPermissionContainer().getPermissionOverride(event.getGuild().getPublicRole()).getAllowed();
        allowed.add(Permission.VIEW_CHANNEL);
        allowed.add(Permission.VOICE_CONNECT);
        allowed.add(Permission.VOICE_SPEAK);
        allowed.add(Permission.VOICE_STREAM);
        allowed.add(Permission.VOICE_USE_VAD);
        allowed.add(Permission.MESSAGE_SEND);

        EnumSet<Permission> denied = channel.getPermissionContainer().getPermissionOverride(event.getGuild().getPublicRole()).getDenied();
        denied.remove(Permission.VIEW_CHANNEL);
        denied.remove(Permission.VOICE_CONNECT);
        denied.remove(Permission.VOICE_SPEAK);
        denied.remove(Permission.VOICE_STREAM);
        denied.remove(Permission.VOICE_USE_VAD);
        denied.remove(Permission.MESSAGE_SEND);

        channel.getPermissionContainer().getManager().putPermissionOverride(event.getGuild().getPublicRole(), Permission.getRaw(allowed), Permission.getRaw(denied)).queue();
        event.reply("done mommy").queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("publicchannel", "Makes a channel public.")
                .addOption(OptionType.CHANNEL, "channel", "The channel to make public.", true);
    }

    @Override
    public String getCategory() {
        return "admin";
    }
}
