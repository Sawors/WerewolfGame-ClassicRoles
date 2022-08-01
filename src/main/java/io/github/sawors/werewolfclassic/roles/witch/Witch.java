package io.github.sawors.werewolfclassic.roles.witch;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GamePhase;
import io.github.sawors.werewolfgame.game.roles.PrimaryRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.LoadedLocale;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import javax.annotation.Nullable;
import java.util.Collection;

public class Witch extends PrimaryRole implements TextRole {
    
    public Witch(WerewolfExtension extension) {
        super(extension);
        addEvent(new WitchChooseActionEvent(getExtension()), GamePhase.NIGHT_POSTWOLVES);
    }

    @Override
    public Integer priority() {
        return 10;
    }
    
    @Override
    public void onLoad() {
    
    }
    
    @Override
    public String getChannelName(@Nullable LoadedLocale language) {
        return new TranslatableText(getExtension().getTranslator(), language).get("roles.witch.channel");
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
                        .setTitle(textpool.get("roles.witch.name",true))
                        .setDescription(textpool.get("roles.witch.text",true))
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.role-description"), textpool.get("roles.witch.role-description"),false)
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.win-condition"), textpool.get("roles.witch.win-condition"),false)
                        .setThumbnail(textpool.get("roles.witch.thumbnail", true))
                        .build();
    }
    
    @Override
    public String getAnnouncementMessage(LoadedLocale locale) {
        return new TranslatableText(getExtension().getTranslator(), locale).get("roles."+getRoleName()+".announcement");
    }
    
    @Override
    public String getIntroMessage(LoadedLocale lang) {
        return null;
    }
    
    @Override
    public void onMessageSent(GenericMessageEvent event) {
    
    }
    
    @Override
    public void onReactionAdded(GenericMessageEvent event) {
    
    }
    
    @Override
    public void onReactionRemoved(GenericMessageEvent event) {
    
    }
}
