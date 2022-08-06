package io.github.sawors.werewolfclassic.roles.lover;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.BackgroundEvent;
import io.github.sawors.werewolfgame.game.events.DeathValidateEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;

import java.util.ArrayList;
import java.util.List;

public class LoverEventBackground extends BackgroundEvent implements RoleEvent {
    
    GameManager manager;
    
    public LoverEventBackground(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void initialize(GameManager gamemanager) {
        this.manager = gamemanager;
    }
    
    @Override
    public void onDeathConfirmed(UserId victim) {
        List<UserId> killed = new ArrayList<>(manager.getUsersWithRole(getRole()));
        
        if(manager.getUsersWithRole(getRole()).contains(victim)){
            killed.remove(victim);
            for(UserId id : killed){
                if(!manager.getAlivePlayers().contains(id)){
                    return;
                }
            }
            Main.logAdmin("Lovers die now");
            manager.getMainTextChannel().sendMessage("ono jemeure").queue();
            for(UserId collateral : killed){
                manager.getMainTextChannel().sendMessage("collatéral : "+collateral).queue();
                manager.killUser(collateral);
            }
            manager.overwriteCurrentEvent(new DeathValidateEvent(Main.getRootExtensionn(), false));
            manager.getCurrentEvent().start(manager);
        }
    }
    
    @Override
    public PlayerRole getRole() {
        return new Lover(extension);
    }
}
