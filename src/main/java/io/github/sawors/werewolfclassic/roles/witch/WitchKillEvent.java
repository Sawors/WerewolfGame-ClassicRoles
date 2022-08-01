package io.github.sawors.werewolfclassic.roles.witch;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.GenericVote;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;

public class WitchKillEvent extends GenericVote implements RoleEvent {
    public WitchKillEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager gameManager) {
        Main.logAdmin("witch should kill");
        gameManager.nextEvent();
    }
    
    @Override
    public PlayerRole getRole() {
        return new Witch(getExtension());
    }
    
    
}
