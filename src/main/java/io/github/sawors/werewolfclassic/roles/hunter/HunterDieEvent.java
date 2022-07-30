package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.GamePhase;
import io.github.sawors.werewolfgame.game.events.GameEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;

public class HunterDieEvent extends GameEvent implements RoleEvent {
    public HunterDieEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager gameManager) {
    
    }
    
    @Override
    public GamePhase getPhase() {
        return null;
    }
    
    @Override
    public PlayerRole getRole() {
        return new Hunter(extension);
    }
}
