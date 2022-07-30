package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.events.GameEvent;
import io.github.sawors.werewolfgame.game.roles.DefaultRoleType;
import io.github.sawors.werewolfgame.game.roles.PrimaryRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.LoadedLocale;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public class Hunter extends PrimaryRole implements TextRole {
    public Hunter(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public String toString() {
        return DefaultRoleType.HUNTER.toString();
    }
    
    @Override
    public Integer priority() {
        return null;
    }
    
    @Override
    public void onDeathAction() {
        //TODO : Hunter action
    }
    
    @Override
    public void onLoad() {
    
    }
    
    @Override
    public Set<GameEvent> getEvents() {
        return Set.of();
    }
    
    @Override
    public String getChannelName(@Nullable LoadedLocale loadedLocale) {
        return new TranslatableText(getExtension().getTranslator(), loadedLocale).get("roles.hunter.channel");
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
        return null;
    }
    
    @Override
    public String getIntroMessage(LoadedLocale lang) {
        return "You are the Hunter";
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
