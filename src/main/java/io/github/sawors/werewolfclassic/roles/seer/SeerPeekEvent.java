package io.github.sawors.werewolfclassic.roles.seer;

import io.github.sawors.werewolfgame.LinkedUser;
import io.github.sawors.werewolfgame.Main;
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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SeerPeekEvent extends GenericVote implements RoleEvent {

    public SeerPeekEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {
        Main.logAdmin("Starting seer peek");
        // this isn't necessary, but I prefer (re)defining votemessage here as in a future update I plan on removing its "inherited" field
        this.votechannel = manager.getRoleChannel(getRole());
        final String rolename = getRole().getRoleName();

        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<LinkedUser> votepool = manager.defaultVotePool();
        votepool.removeIf(us -> manager.getPlayerRoles().get(us.getId()) == null || manager.getPlayerRoles().get(us.getId()).getMainRole().equals(getRole()) || !manager.getPlayerRoles().get(us.getId()).isAlive());
        this.votepool = votepool;
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<UserId> voters = manager.getRealPlayers();
        voters.removeIf(us -> manager.getPlayerRoles().get(us) == null ||!(manager.getPlayerRoles().get(us).getMainRole().equals(getRole())) || !manager.getPlayerRoles().get(us).isAlive());
        voters.add(UserId.fromString("sawors01"));
        this.voters = voters;
        // this votemessage pattern can be copy/pasted, it adds the title+description+thumbnail for the role's vote based on the general vote message template
        // (please note that this pattern may only be used for single-player roles, for teams please use the plural form of the role name to name the vote category)
        this.votemessage = new EmbedBuilder();
        TranslatableText texts = new TranslatableText(getExtension().getTranslator(), manager.getLanguage());
        votemessage.setTitle(texts.get("votes.seer.title"));
        votemessage.setDescription(texts.get("votes.seer.description"));
        votemessage.setThumbnail(texts.get("roles.seer.thumbnail"));

        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundStartAnnouncement(manager.getLanguage())).queue();
        Main.logAdmin("Started seer peek");
        votechannel.sendMessage("yo").queue();
        start(manager, votemessage,false);
    }

    @Override
    public void onVote(UserId voter, UserId voted) {

        closeVote();
        
        TranslatableText texts = new TranslatableText(getExtension().getTranslator(), manager.getLanguage());

        String name = "";
        String role = "";
        if(manager.getPlayerRoles().containsKey(voted)){
            name = voted.toString();
            role = manager.getPlayerRoles().get(voted).getMainRole() != null ? new TranslatableText(manager.getPlayerRoles().get(voted).getMainRole().getExtension().getTranslator(), manager.getLanguage()).get("roles."+manager.getPlayerRoles().get(voted).getMainRole().getRoleName()+".name") : "??????";
        }
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
        votechannel.sendMessage(texts.get("votes.seer.end").replaceAll("%user%", name).replaceAll("%role%", role)).queueAfter(1, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> manager.nextEvent(), 2, TimeUnit.SECONDS);
    }

    @Override
    public PlayerRole getRole() {
        return new Seer(extension);
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
