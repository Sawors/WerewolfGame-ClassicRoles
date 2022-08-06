package io.github.sawors.werewolfclassic.roles.witch;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.GenericVote;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;

import java.util.Set;

public class WitchKillEvent extends GenericVote implements RoleEvent {
    public WitchKillEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager gameManager) {
        this.manager = gameManager;
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<UserId> voters = manager.getRealPlayers();
        voters.removeIf(us -> manager.getPlayerRoles().get(us) == null ||!(manager.getPlayerRoles().get(us).getMainRole().equals(getRole())) || !manager.getPlayerRoles().get(us).isAlive());
        voters.add(UserId.fromString("sawors01"));
        this.voters = voters;
        Main.logAdmin("witch should kill");
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
        manager.nextEvent();
    }
    
    @Override
    public PlayerRole getRole() {
        return new Witch(getExtension());
    }
    
    
}
