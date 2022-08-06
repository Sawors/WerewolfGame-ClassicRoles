package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.BackgroundEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;

public class HunterEventBackground extends BackgroundEvent implements RoleEvent {
    
    GameManager manager;
    
    public HunterEventBackground(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void initialize(GameManager gameManager) {
        this.manager = gameManager;
    }
    
    @Override
    public void onDeathConfirmed(UserId victim) {
        if(manager.getUsersWithRole(getRole()).contains(victim)){
            Main.logAdmin("Hunter kills now");
            manager.getMainTextChannel().sendMessage("agrou chasseur pakontan").queue();
            manager.overwriteCurrentEvent(new HunterDieEvent(this.extension));
            manager.getCurrentEvent().start(manager);
        }
    }
    
    @Override
    public PlayerRole getRole() {
        return new Hunter(extension);
    }
}
