package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.BackgroundEvent;

public class HunterEventBackground extends BackgroundEvent {
    
    GameManager manager;
    
    public HunterEventBackground(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void initialize(GameManager gameManager) {
        this.manager = gameManager;
    }
}
