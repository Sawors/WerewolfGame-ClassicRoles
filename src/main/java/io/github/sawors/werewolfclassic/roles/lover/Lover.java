package io.github.sawors.werewolfclassic.roles.lover;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.roles.DefaultRoleType;
import io.github.sawors.werewolfgame.game.roles.FirstNightRole;
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

public class Lover extends PlayerRole implements FirstNightRole, TextRole {
    
    public Lover(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public String toString() {
        return DefaultRoleType.LOVER.toString();
    }

    @Override
    public Integer priority() {
        return -20;
    }

    @Override
    public void onDeathAction() {
        //TODO : Lover action (kill)
    }
    
    @Override
    public void doFirstNightAction() {
        //TODO : Let them know each other on the first night
    }
    
    @Override
    public String getChannelName(@Nullable LoadedLocale loadedLocale) {
        return new TranslatableText(getExtension().getTranslator(), loadedLocale).get("roles.lover.channel");
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
                        .setTitle(textpool.get("roles.lover.name",true))
                        .setDescription(textpool.get("roles.lover.text",true))
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.supplementary.title"),new TranslatableText(Main.getTranslator(), lang).get("roles.supplementary.description"),false)
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.role-description"), textpool.get("roles.lover.role-description"),false)
                        .addField(new TranslatableText(Main.getTranslator(), lang).get("roles.generic.win-condition"), textpool.get("roles.lover.win-condition"),false)
                        .setThumbnail(textpool.get("roles.lover.thumbnail", true))
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
    public void onReactionAdded(GenericMessageEvent genericMessageEvent) {
    
    }
    
    @Override
    public void onReactionRemoved(GenericMessageEvent genericMessageEvent) {
    
    }
}
