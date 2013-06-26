package undercast.client.forge;

import cpw.mods.fml.common.network.IChatListener;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet3Chat;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastChatListener implements IChatListener {

    public UndercastChatListener() {
    }

    @Override
    public Packet3Chat serverChat(NetHandler handler, Packet3Chat packet) {
        return packet;
    }

    @Override
    public Packet3Chat clientChat(NetHandler handler, Packet3Chat packet) {
        UndercastClient instance = UndercastClient.getInstance();
        instance.chatMessageReceived(packet.message);
        if (instance.shouldUseForgeNewMessage) {
            packet.message = instance.forgeNewMessage;
            instance.forgeNewMessage = null;
            instance.shouldUseForgeNewMessage = false;
        }
        return packet;
    }
}
