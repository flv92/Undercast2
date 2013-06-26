package undercast.client.forge;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastConnectionHandler implements IConnectionHandler {
    
    public UndercastConnectionHandler() {
    }
    
    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
    }
    
    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        return null;
    }

    /*
     * Fired when connecting to a REMOTE server
     * Client-side only
     */
    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
        UndercastClient.getInstance().clientConnect(((NetClientHandler)netClientHandler).getNetManager().getSocketAddress().toString());
    }

    /*
     * Fired when connecting to a LOCAL server
     * Client-side only
     */
    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
    }
    
    @Override
    public void connectionClosed(INetworkManager manager) {
        UndercastClient.getInstance().clientDisconnect(manager.getSocketAddress().toString());
    }
    
    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
    }
}
