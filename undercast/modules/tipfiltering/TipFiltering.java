package undercast.client.modules.tipfiltering;

import net.minecraft.util.EnumChatFormatting;
import undercast.client.IChatModifier;
import undercast.client.IModule;
import undercast.client.annotations.UndercastModule;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
@UndercastModule(name = "TipFiltering", version = "1.0")
public class TipFiltering implements IModule, IChatModifier {

    private boolean state = true;

    @Override
    public void load() {
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean newState) {
        this.state = newState;
    }

    @Override
    public String messageReceived(String message, String strippedMessage) {
        if(message.contains("§7§o[§b§l§oTip§r§7§o]§7")){
            message = null;
        }
        return message;
    }
}
