package doppler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.HashMap;
import java.util.Properties;
import java.lang.System;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class DopplerPropertiesExport {

    public static void main(String... args) {
        if (args.length == 0) {
            System.err.println("usage: bash ImportSecrets.java /absolute/path/to/application.properties");
            System.exit(1);
        }
        String propertiesFile = args[0];
        HashMap<String, String> secrets = new HashMap<>();

        try {
            Properties properties = DopplerPropertiesExport.readPropertiesFile(propertiesFile);
            properties.forEach((key, val) -> {
                String envKey = convertPropertyToEnv((String) key);
                String value = (String) val;
                value = value.replaceAll("\\$\\{", "\\\\\\$\\\\{");
                secrets.put(envKey, (String) value);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        System.out.print(gson.toJson(secrets));
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream propsFile = null;
        Properties props = new Properties();
        try {
            propsFile = new FileInputStream(fileName);
            props = new Properties();
            props.load(propsFile);
            propsFile.close();
            return props;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            propsFile.close();
        }
        return props;

    }

    public static String convertPropertyToEnv(String name) {
        List<String> parts = new ArrayList<String>();
        for (String key : name.split("\\.")) {
            parts.add((key.replaceAll("-", "")).toUpperCase());
        }

        return String.join("_", parts);
    }
}