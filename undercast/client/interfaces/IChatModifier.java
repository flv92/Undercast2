package undercast.client.interfaces;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public interface IChatModifier {

    
    /**
     * Called on every new chat message
     * @param message The content of the message
     * @param strippedMessage The content of the message without color/formatting codes
     * @return The new message to be displayed
     */
    public String messageReceived(String message, String strippedMessage);
}
