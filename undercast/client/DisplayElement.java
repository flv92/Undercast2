
package undercast.client;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class DisplayElement {

    public String message;
    public int color;
    
    public DisplayElement(String s,  int i){
        message = s;
        color = i;
    }
    public DisplayElement(String s){
        this(s, 16777215);
    }
}
