package undercast.client.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import undercast.client.UndercastClient;
import undercast.client.interfaces.IModule;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastConfig {

    public ArrayList<ConfigKey> entryes = new ArrayList();
    private File configFile;
    private String currentCategoryName;

    public UndercastConfig(File f) {
        configFile = f;
    }

    public void loadConfig() throws IOException, Exception {
        entryes.clear();
        currentCategoryName = "root";
        if (!configFile.exists()) {
            configFile.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line1 = reader.readLine();
        if (line1 == null) {
            //File is Empty, adding the top comment
            FileWriter writer = new FileWriter(configFile);
            writer.write("###########################\n");
            writer.write("####UndercastMod Config####\n");
            writer.write("###########################\n");
            writer.close();
        }
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#")) {
                //Getting the current category
                if (line.startsWith("[ ") && line.contains(" ]")) {
                    //The line define a category
                    String categoryName = line.substring(2, line.lastIndexOf(" ]"));
                    if (categoryName.contains(" ")) {
                        throw new Exception("You can't have a space in your category name");
                    } else {
                        currentCategoryName = categoryName;
                    }
                }
                line = this.removeComments(line);
                if (line.contains(" = ")) {
                    String key = line.substring(0, line.indexOf(" = "));
                    String value = line.substring(line.indexOf(" = ") + 3, line.length());

                    //Detect if the value is an int, a double, a boolean ...
                    Integer valueInt = null;
                    Double valueDouble = null;
                    Boolean valueBoolean = null;
                    try {
                        valueInt = Integer.parseInt(value);
                    } catch (Exception e) {
                        //This is not an int
                        try {
                            valueDouble = Double.parseDouble(value);
                        } catch (Exception e2) {
                            //This is either a boolean or a string
                            if (value.equals("false")) {
                                valueBoolean = false;
                            } else if (value.equals("true")) {
                                valueBoolean = true;
                            }
                        }
                    }

                    if (valueInt != null) {
                        entryes.add(new ConfigKey(currentCategoryName, key, valueInt));
                    } else if (valueDouble != null) {
                        entryes.add(new ConfigKey(currentCategoryName, key, valueDouble));
                    } else if (valueBoolean != null) {
                        entryes.add(new ConfigKey(currentCategoryName, key, valueBoolean));
                    } else {
                        entryes.add(new ConfigKey(currentCategoryName, key, value));
                    }
                }
            }
        }
        reader.close();
    }

    public void setValue(IModule category, String name, String stringValue) throws FileNotFoundException, IOException, Exception {
        File tempFile = new File(configFile.getParent() + "/temporaryFile." + name + ".cfg");
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        FileWriter writer = new FileWriter(tempFile);
        String line;
        String currentCategory = "root";
        boolean lineSet = false;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#")) {
                //Getting the current category
                if (line.startsWith("[ ") && line.contains(" ]")) {
                    //The line define a category
                    String categoryName = line.substring(2, line.lastIndexOf(" ]"));
                    if (categoryName.contains(" ")) {
                        throw new Exception("You can't have a space in your category name");
                    } else {
                        if (currentCategory.equals(category.getName()) && !lineSet) {
                            writer.write(name + " = " + stringValue + "\n");
                            lineSet = true;
                        }
                        currentCategory = categoryName;
                        writer.write("\n" + line + "\n");
                    }
                } else if (line.startsWith(name + " ") && currentCategory.equals(category.getName())) {
                    writer.write(name + " = " + stringValue + "\n");
                    lineSet = true;
                } else if (!line.isEmpty()) {
                    writer.write(line + "\n");
                }
            } else {
                writer.write(line + "\n");
            }
        }
        if (!lineSet && !currentCategory.equals(category.getName())) {
            writer.write("\n[ " + category.getName() + " ]\n");
            writer.write(name + " = " + stringValue + "\n");
        } else if (!lineSet) {
            writer.write(name + " = " + stringValue + "\n");
        }
        writer.close();
        reader.close();
        configFile.delete();
        tempFile.renameTo(configFile);
    }

    public void setValue(IModule category, String name, Boolean boolValue) throws FileNotFoundException, IOException, Exception {
        String value = boolValue.toString();
        this.setValue(category, name, value);
    }

    public void setValue(IModule category, String name, Integer intValue) throws FileNotFoundException, IOException, Exception {
        String value = intValue.toString();
        this.setValue(category, name, value);
    }

    public void setValue(IModule category, String name, Double doubleValue) throws FileNotFoundException, IOException, Exception {
        String value = doubleValue.toString();
        this.setValue(category, name, value);
    }

    public String getValue(IModule category, String name, String defaultValue) throws Exception {
        boolean valueFound = false;
        String value = defaultValue;
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        String currentCategory = "root";
        while ((line = reader.readLine()) != null) {
            if (!valueFound) {
                if (!line.startsWith("#")) {
                    //Getting the current category
                    if (line.startsWith("[ ") && line.contains(" ]")) {
                        //The line define a category
                        String categoryName = line.substring(2, line.lastIndexOf(" ]"));
                        if (categoryName.contains(" ")) {
                            throw new Exception("You can't have a space in your category name");
                        } else {
                            currentCategory = categoryName;
                        }
                    } else {
                        line = removeComments(line);
                        if (line.startsWith(name) && currentCategory.equals(category.getName()) && line.contains(" = ")) {
                            value = line.split(" = ")[1];
                            valueFound = true;
                        }
                    }
                }
            }
        }
        if (!valueFound) {
            this.setValue(category, name, value);
        }
        return value;
    }

    public Integer getValue(IModule category, String name, Integer defaultValue) {
        String retour;
        try {
            retour = this.getValue(category, name, defaultValue.toString());
        } catch (Exception e) {
            return -1;
        }
        try {
            Integer i = Integer.parseInt(retour);
            return i;
        } catch (Exception e) {
            System.out.println("The value " + name + " is not an Integer");
        }
        return Integer.MIN_VALUE;
    }

    public Double getValue(IModule category, String name, Double defaultValue) {
        String retour;
        try {
            retour = this.getValue(category, name, defaultValue.toString());
        } catch (Exception e) {
            return -1D;
        }
        try {
            Double i = Double.parseDouble(retour);
            return i;
        } catch (Exception e) {
            System.out.println("The value " + name + " is not a Double");
        }
        return Double.NaN;
    }

    public Boolean getValue(IModule category, String name, Boolean defaultValue) {
        String retour;
        try {
            retour = this.getValue(category, name, defaultValue.toString());
        } catch (Exception e) {
            return false;
        }
        try {
            Boolean i = Boolean.parseBoolean(retour);
            return i;
        } catch (Exception e) {
            System.out.println("The value " + name + " is not a Boolean");
        }
        return Boolean.FALSE;
    }

    public String removeComments(String s) {
        if (s.contains("#")) {
            s = s.substring(0, s.indexOf("#"));
        }
        while (s.endsWith(" ")) {
            s = s.substring(0, s.lastIndexOf(" "));
        }
        return s;
    }

    public void cleanConfigFile() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        File tempFile = new File(configFile.getParent() + "/temporaryFile.clean.cfg");
        FileWriter writer = new FileWriter(tempFile);
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty() && !(line.startsWith("[ ") && line.contains(" ]"))) {
                writer.write(line + "\n");
            } else if (line.startsWith("[ ") && line.contains(" ]")) {
                writer.write("\n" + line + "\n");
            }
        }
        reader.close();
        writer.close();
        configFile.delete();
        tempFile.renameTo(configFile);
    }
}
