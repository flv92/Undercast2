package undercast.client;

import java.util.HashMap;
import net.minecraft.client.settings.KeyBinding;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public interface IKeyRegistry {

    public HashMap<KeyBinding, Boolean> registerKeyBindings();
}
