package undercast.client.interfaces;

import undercast.client.config.ConfigKey;
import undercast.client.config.UndercastConfig;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public interface IModule {

    /**
     * This is where you should use the config
     */
    public void load(UndercastConfig config);

    public void setState(boolean newState);
    
    public String getConfigStringForKeyAndState(ConfigKey config, boolean enabled);

    /**
     * Returns the boolean value of enable or disable
     *
     * @return boolean enable=true disable=false
     */
    public boolean getState();
    
    public String getName();
}
