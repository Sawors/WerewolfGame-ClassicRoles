package io.github.sawors.werewolfclassic.roles.hunter;

import io.github.sawors.werewolfgame.LinkedUser;
import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.DeathValidateEvent;
import io.github.sawors.werewolfgame.game.events.GenericVote;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Set;

public class HunterDieEvent extends GenericVote implements RoleEvent {
    
    GameManager manager;
    
    public HunterDieEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager gamemanager) {
        this.manager = gamemanager;
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundStartAnnouncement(manager.getLanguage())).queue();
        this.votechannel = manager.getMainTextChannel();
        
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<LinkedUser> votepool = manager.defaultVotePool();
        votepool.removeIf(us -> manager.getPlayerRoles().get(us.getId()) == null || manager.getPlayerRoles().get(us.getId()).getMainRole().equals(getRole()) || !manager.getPlayerRoles().get(us.getId()).isAlive());
        this.votepool = votepool;
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<UserId> voters = manager.getRealPlayers();
        voters.removeIf(us -> manager.getPlayerRoles().get(us) == null ||!(manager.getPlayerRoles().get(us).getMainRole().equals(getRole())) || !manager.getPlayerRoles().get(us).isAlive());
        voters.add(UserId.fromString("sawors01"));
        this.voters = voters;
        
        
        this.votemessage = new EmbedBuilder();
        TranslatableText texts = new TranslatableText(getExtension().getTranslator(), manager.getLanguage());
        votemessage.setTitle(texts.get("votes.hunter.title"));
        votemessage.setDescription(texts.get("votes.hunter.description"));
        votemessage.setThumbnail(texts.get("roles.hunter.thumbnail"));
        
        start(manager,votemessage,false);
    }
    
    @Override
    public PlayerRole getRole() {
        return new Hunter(extension);
    }
    
    @Override
    public void onVote(UserId voter, UserId voted) {
        closeVote();
        manager.killUser(voted);
        manager.overwriteCurrentEvent(new DeathValidateEvent(Main.getRootExtensionn(),false));
        manager.getCurrentEvent().start(manager);
    }
    
    // These 3 override can be copy/pasted if you want to disable default vote behaviour (typically for single-player roles) but still trigger events (YOU SHOULD NEVER REMOVE EVENT TRIGGERING)
    @Override
    public void validate(boolean force, boolean wait) {
        onValidationAttempt(force,wait);
        onValidationSuccess(force);
    }
    @Override
    public void onVoteNew(UserId voter, UserId voted) {}
    @Override
    public void onVoteChanged(UserId voter, UserId voted) {}
}
