package io.github.sawors.werewolfclassic.roles.lover;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GamePhase;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.LoadedLocale;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class Lover extends PlayerRole implements TextRole {
    
    public Lover(WerewolfExtension extension) {
        super(extension);
        addEvent(new LoverWakeUpEvent(getExtension()), GamePhase.NIGHT_PREWOLVES);
    }

    @Override
    public Integer priority() {
        return -20;
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
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.supplementary.title"),new TranslatableText(Main.getTranslator(), lang).get("roles.supplementary.description"),false)
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
    public void onMessageSent(GenericMessageEvent genericMessageEvent) {
    
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
    public void onReactionAdded(GenericMessageEvent genericMessageEvent) {
    
    }
    
    @Override
    public void onReactionRemoved(GenericMessageEvent genericMessageEvent) {
    
    }
}
