package io.github.sawors.werewolfclassic.roles.littlegirl;

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

public class LittleGirl extends PrimaryRole implements TextRole {
    public LittleGirl(WerewolfExtension extension) {
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
    public String getChannelName(@Nullable LoadedLocale language) {
        return new TranslatableText(getExtension().getTranslator(), language).get("roles.littlegirl.channel");
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
                        .setTitle(textpool.get("roles.littlegirl.name",true))
                        .setDescription(textpool.get("roles.littlegirl.text",true))
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.role-description"), textpool.get("roles.littlegirl.role-description"),false)
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.win-condition"), textpool.get("roles.littlegirl.win-condition"),false)
                        .setThumbnail(textpool.get("roles.littlegirl.thumbnail", true))
                        .build();
    }
    
    @Override
    public String getIntroMessage(LoadedLocale lang) {
        return null;
    }
    
    @Override
    public String getAnnouncementMessage(LoadedLocale loadedLocale) {
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
