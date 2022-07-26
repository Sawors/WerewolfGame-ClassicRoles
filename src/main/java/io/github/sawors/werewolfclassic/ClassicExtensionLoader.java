package io.github.sawors.werewolfclassic;

import io.github.sawors.werewolfclassic.roles.cupid.Cupid;
import io.github.sawors.werewolfclassic.roles.hunter.Hunter;
import io.github.sawors.werewolfclassic.roles.littlegirl.LittleGirl;
import io.github.sawors.werewolfclassic.roles.littlegirl.LittleGirlListenEvent;
import io.github.sawors.werewolfclassic.roles.lover.Lover;
import io.github.sawors.werewolfclassic.roles.seer.Seer;
import io.github.sawors.werewolfclassic.roles.witch.Witch;
import io.github.sawors.werewolfgame.extensionsloader.WerewolfExtension;

public class ClassicExtensionLoader extends WerewolfExtension
{
    @Override
    public void onLoad() {
        registerNewRoles(
                new Cupid(this),
                new Hunter(this),
                new LittleGirl(this),
                new Lover(this),
                new Seer(this),
                new Witch(this)
        );
        
        registerBackgroundEvents(
                new LittleGirlListenEvent(this)
        );
        
        addBundledLocale(
                "en_UK",
                "fr_FR"
        );
    }
}
