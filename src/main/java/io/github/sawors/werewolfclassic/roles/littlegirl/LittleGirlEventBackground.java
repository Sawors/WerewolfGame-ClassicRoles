package io.github.sawors.werewolfclassic.roles.littlegirl;

import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.BackgroundEvent;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;

public class LittleGirlEventBackground extends BackgroundEvent implements RoleEvent {
    
    GameManager manager;
    
    public LittleGirlEventBackground(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void initialize(GameManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void onMessageSent(MessageReceivedEvent event) {
        if(!event.getAuthor().isSystem() && !event.getAuthor().isBot() && this.manager != null && this.manager.getWolfChannel() != null && event.getChannel().getId().equals(this.manager.getWolfChannel().getId())){
            for(Map.Entry<TextChannel, TextRole> rolechan : manager.getRoleChannels().entrySet()){
                if(rolechan.getValue().equals(getRole())){
                    String msg = event.getMessage().getContentRaw();
                    String format = "*";
                    if(msg.substring(0,4).equalsIgnoreCase("http")){
                        format = "";
                    }
                    rolechan.getKey().sendMessage("**Loups** : "+format+msg+format).queue();
                }
            }
        }
    }
    
    @Override
    public PlayerRole getRole() {
        return new LittleGirl(extension);
    }
}
