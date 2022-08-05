package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.roles.PrimaryRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.LoadedLocale;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class Hunter extends PrimaryRole implements TextRole {
    public Hunter(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public Integer priority() {
        return null;
    }
    
    
    
    @Override
    public void onLoad() {
    
    }

    @Override
    public String getChannelName(@Nullable LoadedLocale loadedLocale) {
        return new TranslatableText(getExtension().getTranslator(), loadedLocale).get("roles."+getRoleName()+".channel");
    }
    
    @Override
    public Collection<Permission> getChannelAllow(){
        return getExtension().getDefaultRoleChannelAllow();
    }
    @Override
    public Collection<Permission> getChannelDeny(){
        return getExtension().getDefaultRoleChannelDeny();
    }
    
    @Override
    public MessageEmbed getHelpMessageEmbed(LoadedLocale lang) {
        TranslatableText textpool = new TranslatableText(getExtension().getTranslator(), lang);
        return
                new EmbedBuilder()
                        .setTitle(textpool.get("roles."+getRoleName()+".name",true))
                        .setDescription(textpool.get("roles."+getRoleName()+".text",true))
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.role-description"), textpool.get("roles."+getRoleName()+".role-description"),false)
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.win-condition"), textpool.get("roles."+getRoleName()+".win-condition"),false)
                        .setThumbnail(textpool.get("roles."+getRoleName()+".thumbnail", true))
                        .build();
    }
    
    @Override
    public String getIntroMessage(LoadedLocale lang) {
        return null;
    }

    @Override
    public String getRoundStartAnnouncement(LoadedLocale locale) {
        return new TranslatableText(getExtension().getTranslator(), locale).get("roles."+getRoleName()+".round-start");
    }

    @Override
    public String getRoundEndAnnouncement(LoadedLocale locale) {
        return new TranslatableText(getExtension().getTranslator(), locale).get("roles."+getRoleName()+".round-end");
    }

    @Override
    public void onMessageSent(GenericMessageEvent genericMessageEvent) {
    
    }
    
    @Override
    public void onReactionAdded(GenericMessageEvent genericMessageEvent) {
    
    }
    
    @Override
    public void onReactionRemoved(GenericMessageEvent genericMessageEvent) {
    
    }
}
