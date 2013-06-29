
package undercast.client.config;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class ConfigKey {

    public String category;
    public String name;
    public Object value;
    
    public ConfigKey(String c, String n, Object v){
        category = c;
        name = n;
        value = v;
    }
    
    public String toString(){
        return category + ":" + name;
    }
}
