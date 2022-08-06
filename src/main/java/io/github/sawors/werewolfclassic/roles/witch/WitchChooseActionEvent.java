package io.github.sawors.werewolfclassic.roles.witch;

import io.github.sawors.werewolfgame.Main;
import io.github.sawors.werewolfgame.database.UserId;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;
import io.github.sawors.werewolfgame.game.GameManager;
import io.github.sawors.werewolfgame.game.WerewolfPlayer;
import io.github.sawors.werewolfgame.game.events.GenericVote;
import io.github.sawors.werewolfgame.game.events.RoleEvent;
import io.github.sawors.werewolfgame.game.roles.PlayerRole;
import io.github.sawors.werewolfgame.game.roles.TextRole;
import io.github.sawors.werewolfgame.localization.TranslatableText;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WitchChooseActionEvent extends GenericVote implements RoleEvent {
    
    private static final String killaction = "witchkill";
    private static final String saveaction = "witchsave";
    private static final String ignoreaction = "witchignore";
    private static final String nohealtag = "witchnoheal";
    private static final String nokilltag = "witchnokill";
    
    private String victimname = "";
    
    public WitchChooseActionEvent(WerewolfExtension extension) {
        super(extension);
    }
    
    @Override
    public void start(GameManager manager) {

        manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundStartAnnouncement(manager.getLanguage())).queue();

        // this check can be copy/pasted, it removes users if : they do not have any role OR have the role of this RoleEvent OR are dead (validated)
        Set<UserId> voters = manager.getRealPlayers();
        voters.removeIf(us -> manager.getPlayerRoles().get(us) == null ||!(manager.getPlayerRoles().get(us).getMainRole().equals(getRole())) || !manager.getPlayerRoles().get(us).isAlive());
        voters.add(UserId.fromString("sawors01"));
        this.voters = voters;

        start(manager, new EmbedBuilder());
    }
    
    @Override
    public PlayerRole getRole() {
        return new Witch(extension);
    }
    
    @Override
    public void start(GameManager manager, EmbedBuilder embed){
        
        this.manager = manager;
        votechannel = manager.getRoleChannel(getRole());

        
        TranslatableText texts = new TranslatableText(getExtension().getTranslator(), manager.getLanguage());
        
        embed
            .setThumbnail(texts.get("roles.witch.thumbnail"))
            .setTitle(texts.get("votes.witch-action.title"))
            .setDescription(texts.get("votes.witch-action.description"));
        
        
        Button killbutton = Button.primary("vote:"+manager.getId()+"#"+killaction,texts.get("votes.witch-action.kill-label"));
        Button savebutton = Button.primary("vote:"+manager.getId()+"#"+saveaction,texts.get("votes.witch-action.save-label"));
        Button ignorebutton = Button.primary("vote:"+manager.getId()+"#"+ignoreaction,texts.get("votes.witch-action.ignore-label"));
        
        if(manager.getPendingDeath().size() > 0){
            User w = manager.getDiscordUser(new ArrayList<>(manager.getPendingDeath()).get(0));
            victimname = w != null ? w.getName() : new ArrayList<>(manager.getPendingDeath()).get(0).toString();
            votechannel.sendMessage(texts.get("votes.witch-action.victim-announcement").replaceAll("%user%",victimname)).queue();
        } else {
            killbutton = killbutton.asDisabled();
            votechannel.sendMessage(texts.get("votes.witch-action.no-victim-announcement")).queue();
        }
        List<UserId> witchuser = manager.getUsersWithRole(getRole());
        if(witchuser.size() >= 1){
            WerewolfPlayer witchplayer = manager.getPlayerRoles().get(witchuser.get(0));
            if(witchplayer != null){
                if(witchplayer.getTags().contains(nokilltag)){
                    killbutton = killbutton.asDisabled();
                }
                if(witchplayer.getTags().contains(nohealtag)){
                    savebutton = savebutton.asDisabled();
                }
            }
        }
        
        
        ActionRow votebuttons = ActionRow.of(List.of(killbutton,ignorebutton,savebutton));
        if(manager.getOptions().useVoteTimer()){
            TranslatableText textpool = new TranslatableText(Main.getTranslator(),manager.getLanguage());
            embed.addField(textpool.get("votes.generic.time.title"), textpool.get("votes.generic.time.display").replaceAll("%time%",String.valueOf(votetime)), false);
        }
        votechannel.sendMessageEmbeds(embed.build()).setActionRows(votebuttons).queue(msg -> {
            this.buttonmessage.add(msg);
        });
    }
    
    @Override
    public void validate(boolean force, boolean wait) {}

    @Override
    public void doAction(UserId user, String action) {
        switch (action) {
            case killaction -> {
                Main.logAdmin("Witch kill");
                manager.overwriteCurrentEvent(new WitchKillEvent(getExtension()));
                WerewolfPlayer witchplayer = manager.getPlayerRoles().get(user);
                if(witchplayer != null){
                    witchplayer.addTag(nokilltag);
                }
                manager.getCurrentEvent().start(manager);
            }
            case saveaction -> {
                Main.logAdmin("Witch save");
                manager.resurrectUser(new ArrayList<>(manager.getPendingDeath()).get(0));
                WerewolfPlayer witchplayer = manager.getPlayerRoles().get(user);
                if(witchplayer != null){
                    witchplayer.addTag(nohealtag);
                }
                closeVote();
                votechannel.sendMessage(new TranslatableText(getExtension().getTranslator(), manager.getLanguage()).get("votes.witch-action.confirm").replaceAll("%user%",victimname)).queue();
                manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
                manager.nextEvent();
            }
            case ignoreaction -> {
                Main.logAdmin("Witch ignore");
                votechannel.sendMessage(new TranslatableText(getExtension().getTranslator(), manager.getLanguage()).get("votes.witch-action.ignore-confirm")).queue();
                closeVote();
                manager.getMainTextChannel().sendMessage(((TextRole)getRole()).getRoundEndAnnouncement(manager.getLanguage())).queue();
                manager.nextEvent();
            }
        }
    }
}
