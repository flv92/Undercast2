package undercast.client;

import java.util.HashMap;
import net.minecraft.client.settings.KeyBinding;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public interface IModule {

    public void load();
    
    public void setState(boolean newState);

    /**
     * Returns the boolean value of enable or disable
     * @return boolean enable=true disable=false
     */
    public boolean getState();
}
