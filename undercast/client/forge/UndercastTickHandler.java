package undercast.client.forge;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastTickHandler implements ITickHandler {

    public UndercastTickHandler() {
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        //onTickInGame ML method is called if the tickType is Render or Client and if the current world exists
        if((type.equals(EnumSet.of(TickType.CLIENT)) || type.equals(EnumSet.of(TickType.RENDER)))
                && Minecraft.getMinecraft().theWorld != null){
            UndercastClient.getInstance().onTickInGame(0.0F, Minecraft.getMinecraft());
        }
        
        //onTickInGui ML method is different.
        //It is called when the tickType is Render OR (if the tickType is Client and the world exists)
        //Basically it is called on the mainMenu and in game if there is a world
        if((type.equals(EnumSet.of(TickType.RENDER))) ||
                (type.equals(EnumSet.of(TickType.CLIENT)) && Minecraft.getMinecraft().theWorld != null)) {
            UndercastClient.getInstance().onTickInGUI(0.0F, Minecraft.getMinecraft(), Minecraft.getMinecraft().currentScreen);
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        EnumSet<TickType> e = EnumSet.of(TickType.CLIENT);
        e.add(TickType.RENDER);
        return e;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
