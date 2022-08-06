package io.github.sawors.werewolfclassic.roles.lover;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.BackgroundEvent;

public class LoverEventBackground extends BackgroundEvent {
    
    GameManager manager;
    
    public LoverEventBackground(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void initialize(GameManager gamemanager) {
        this.manager = gamemanager;
        
    }
}
