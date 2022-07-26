package io.github.sawors.werewolfclassic.roles.seer;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.GamePhase;
import io.github.sawors.werewolfgame.game.events.GameEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;

public class SeerPeekEvent extends GameEvent implements RoleEvent {
    
    public SeerPeekEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {
    
    }
    
    @Override
    public GamePhase getPhase() {
        return GamePhase.NIGHT_PREWOLVES;
    }
    
    @Override
    public PlayerRole getRole() {
        return new Seer(extension);
    }
}
