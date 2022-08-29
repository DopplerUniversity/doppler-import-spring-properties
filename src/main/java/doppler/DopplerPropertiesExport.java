package doppler;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DopplerPropertiesExport {

    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("\n# Doppler Spring Properties Export script\n#");
            System.out.println("# Make:   make export-properties FILE=\"./sample-application.properties\" > doppler-secrets.json");
            System.out.println("# Maven:  ./mvnw compile exec:java --quiet -Dexec.args=\"./sample-application.properties\" > doppler-secrets.json");
            System.out.println("# Gradle: ./gradlew run --quiet --args=\"./sample-application.properties\" > doppler-secrets.json\n#\n");
            System.exit(0);
        }
        String propertiesFile = args[0];
        HashMap<String, String> secrets = new HashMap<>();

        try {
            Properties properties = DopplerPropertiesExport.readPropertiesFile(propertiesFile);
            properties.forEach((key, val) -> {
                String envKey = convertPropertyToEnv((String) key);
                String value = (String) val;
                value = value.replaceAll("\\$\\{", "\\\\\\$\\\\{");
                secrets.put(envKey, value);

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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert propsFile != null;
            propsFile.close();
        }
        return props;

    }

    public static String convertPropertyToEnv(String name) {
        List<String> parts = new ArrayList<>();
        for (String key : name.split("\\.")) {
            parts.add((key.replaceAll("-", "")).toUpperCase());
        }

        return String.join("_", parts);
    }
}