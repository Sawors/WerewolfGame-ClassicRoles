package io.github.sawors.werewolfclassic.roles.lover;

import io.github.sawors.werewolfgame.DatabaseManager;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.GameEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class LoverWakeUpEvent extends GameEvent implements RoleEvent {
    public LoverWakeUpEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundStartAnnouncement(manager.getLanguage())).queue();
        TextChannel chan = manager.getRoleChannel(getRole());
        if(chan != null){
            for(UserId id : manager.getUsersWithRole(getRole())){
                User u = manager.getDiscordUser(id);
                chan.sendMessage(DatabaseManager.getName(id)).queue();
                if(u != null){
                    chan.getPermissionContainer().getManager().putMemberPermissionOverride(u.getIdLong(), List.of(Permission.MESSAGE_SEND,Permission.VIEW_CHANNEL),List.of(Permission.UNKNOWN)).queue();
                }
            }
        }
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
        
        
        manager.nextEvent();
    }
    
    @Override
    public PlayerRole getRole() {
        return new Lover(getExtension());
    }
}
