package io.github.sawors.werewolfclassic.roles.witch;

import io.github.sawors.werewolfgame.DatabaseManager;
import io.github.sawors.werewolfgame.LinkedUser;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.events.GenericVote;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Set;

public class WitchKillEvent extends GenericVote implements RoleEvent {
    public WitchKillEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager gamemanager) {
        this.manager = gamemanager;
        this.votechannel = manager.getRoleChannel(getRole());
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<UserId> voters = manager.getRealPlayers();
        voters.removeIf(us -> manager.getPlayerRoles().get(us) == null ||!(manager.getPlayerRoles().get(us).getMainRole().equals(getRole())) || !manager.getPlayerRoles().get(us).isAlive() || manager.getPendingDeath().contains(us));
        voters.add(UserId.fromString("sawors01"));
        this.voters = voters;
    
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<LinkedUser> votepool = manager.defaultVotePool();
        votepool.removeIf(us -> manager.getPlayerRoles().get(us.getId()) == null || manager.getPlayerRoles().get(us.getId()).getMainRole().equals(getRole()) || !manager.getPlayerRoles().get(us.getId()).isAlive());
        this.votepool = votepool;
    
        // this votemessage pattern can be copy/pasted, it adds the title+description+thumbnail for the role's vote based on the general vote message template
        // (please note that this pattern may only be used for single-player roles, for teams please use the plural form of the role name to name the vote category)
        this.votemessage = new EmbedBuilder();
        TranslatableText texts = new TranslatableText(getExtension().getTranslator(), manager.getLanguage());
        votemessage.setTitle(texts.get("votes.witch-kill.title"));
        votemessage.setDescription(texts.get("votes.witch-kill.description"));
        votemessage.setThumbnail(texts.get("roles.witch.thumbnail"));
        
        start(gamemanager, votemessage,false);
    }
    
    @Override
    public PlayerRole getRole() {
        return new Witch(getExtension());
    }
    
    @Override
    public void onVote(UserId voter, UserId voted) {
        manager.killUser(voted);
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
        String victimname = DatabaseManager.getName(voted);
        votechannel.sendMessage(new TranslatableText(getExtension().getTranslator(), manager.getLanguage()).get("votes.witch-kill.end").replaceAll("%user%",victimname)).queue();
        closeVote();
        manager.nextEvent();
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
