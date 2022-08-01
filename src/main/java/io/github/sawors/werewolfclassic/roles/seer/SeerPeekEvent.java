package io.github.sawors.werewolfclassic.roles.seer;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.GameEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;

public class SeerPeekEvent extends GameEvent implements RoleEvent {
    
    public SeerPeekEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {
    
    
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getAnnouncementMessage(manager.getLanguage())).queue();
        manager.nextEvent();
    }
    
    @Override
    public PlayerRole getRole() {
        return new Seer(extension);
    }
}
