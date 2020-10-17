package util;

import static util.Constants.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author josh.isc@hotmail.com
 */
public class PropertiesReader {
    
    private File file;
    private Map<String, String> properties;
    
    public PropertiesReader(String filePath) {
        file = new File(filePath);
        properties = null;
    }
    
    public double getDouble(String property) {
        if(properties == null || !properties.containsKey(property)) {
            throw new IllegalArgumentException("Property "+property+" not found.");
        }
        
        try {
            return Double.parseDouble(properties.get(property));
        } catch(Exception e) {
            throw new IllegalArgumentException("Property "+property+" cannot be read as a double value.", e);
        }
    }
    
    public int getInt(String property) {
        if(properties == null || !properties.containsKey(property)) {
            throw new IllegalArgumentException("Property "+property+" not found.");
        }
        
        try {
            return Integer.parseInt(properties.get(property));
        } catch(Exception e) {
            throw new IllegalArgumentException("Property "+property+" cannot be read as an integer value.", e);
        }
    }
    
    public String getString(String property) {
        if(properties == null || !properties.containsKey(property)) {
            throw new IllegalArgumentException("Property "+property+" not found.");
        }
        
        return properties.get(property);
    }

    public void loadProperties() throws Exception {
        properties = new HashMap<>();
        String line;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.startsWith(COMMENT)) {
                    continue;
                }
                String[] property = line.split(EQUALS);
                if(property.length != 2) {
                    continue;
                }
                properties.put(property[0].trim(), property[1].trim());
            }
        } catch(Exception e) {
            throw new Exception("Properties file "+file.getPath()+" could not be parsed.", e);
        }
    }
}
