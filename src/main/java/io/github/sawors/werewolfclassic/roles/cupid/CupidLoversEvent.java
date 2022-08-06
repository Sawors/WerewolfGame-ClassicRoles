package io.github.sawors.werewolfclassic.roles.cupid;

import io.github.sawors.werewolfclassic.roles.lover.Lover;
import io.github.sawors.werewolfclassic.roles.lover.LoverWakeUpEvent;
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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CupidLoversEvent extends GenericVote implements RoleEvent {
    
    List<UserId> targets = new ArrayList<>();
    
    public CupidLoversEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {
    
    
        Main.logAdmin("Starting cupid select");
        // this isn't necessary, but I prefer (re)defining votemessage here as in a future update I plan on removing its "inherited" field
        this.votechannel = manager.getRoleChannel(getRole());
    
        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<LinkedUser> votepool = manager.defaultVotePool();
        votepool.removeIf(us -> manager.getPlayerRoles().get(us.getId()) == null || !manager.getPlayerRoles().get(us.getId()).isAlive());
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
        votemessage.setTitle(texts.get("votes.cupid.title"));
        votemessage.setDescription(texts.get("votes.cupid.description"));
        votemessage.setThumbnail(texts.get("roles.cupid.thumbnail"));
    
        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundStartAnnouncement(manager.getLanguage())).queue();
        start(manager, votemessage,false);
    }
    
    @Override
    public PlayerRole getRole() {
        return new Cupid(extension);
    }
    
    @Override
    public void validate(boolean force, boolean wait) {
        onValidationAttempt(force,wait);
        if(targets.size() == 2){
            onValidationSuccess(force);
            List<UserId> lovers = new ArrayList<>(targets);
            votechannel.sendMessage(new TranslatableText(getExtension().getTranslator(),manager.getLanguage()).get("votes.cupid.end").replaceAll("%user1%", lovers.get(0).toString()).replaceAll("%user2%", lovers.get(1).toString())).queue();
            // create a new "lovers" team if players are from different teams, otherwise it keeps them in the same team (and they can win with their team)
            if(!Objects.equals(manager.getPlayerTeam(lovers.get(0)),manager.getPlayerTeam(lovers.get(1)))){
                String teamname = "lovers";
                Main.logAdmin("using lover team");
                manager.registerNewTeam(teamname);
                for(UserId uid : lovers){
                    manager.removePlayerFromTeam(manager.getPlayerTeam(uid),uid);
                    manager.addPlayerToTeam(teamname,uid);
                }
            }
    
            for(UserId uid : lovers){
                manager.addRoleToPlayer(uid, new Lover(getExtension()));
            }
            
            closeVote();
            
            manager.overwriteCurrentEvent(new LoverWakeUpEvent(getExtension()));
            manager.getCurrentEvent().start(manager);
        } else {
            onValidationFail();
        }
    }
    
    @Override
    public void onVote(UserId voter, UserId voted) {
        targets.add(voted);
        votechannel.sendMessage(new TranslatableText(getExtension().getTranslator(),manager.getLanguage()).get("votes.cupid.arrow").replaceAll("%user%", voted.toString())).queue();
        for(Message msg : buttonmessage){
            List<ActionRow> rows = new ArrayList<>();
            for(ActionRow row : msg.getActionRows()){
                List<Button> disabled = new ArrayList<>();
                for(Button button : row.getButtons()){
                    if(button.getId() != null && button.getId().contains(voted.toString())){
                        disabled.add(button.asDisabled().withStyle(ButtonStyle.SECONDARY));
                    } else {
                        disabled.add(button);
                    }
                }
                rows.add(ActionRow.of(disabled));
            }
            msg.editMessage(msg).setActionRows(rows).queue();
        }
    }
    
    @Override
    public void onVoteNew(UserId voter, UserId voted) {}
    @Override
    public void onVoteChanged(UserId voter, UserId voted) {}
}
