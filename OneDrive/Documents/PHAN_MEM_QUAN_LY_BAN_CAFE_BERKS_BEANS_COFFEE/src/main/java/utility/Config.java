/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.io.File;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 *
 * @author hoang
 */
public class Config {

    public static boolean createDefaultConfigFile() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                writeDefaultConfig();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String isConfigFileCreated() {
        File configFile = new File(CONFIG_FILE_PATH);
        return configFile.getAbsolutePath();
    }
    private static final String CONFIG_FILE_PATH = "config.yaml";

    private static void writeDefaultConfig() throws IOException {
        Map<String, Object> defaultConfig = new HashMap<>();
        defaultConfig.put("server", "localhost");
        defaultConfig.put("password", "1234");
        defaultConfig.put("databaseName", "PHANMEMQUANLYBANCAFEBERKSBEANSCOFFEE");
        defaultConfig.put("port", "1433");
        defaultConfig.put("username", "sa");

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowReadOnlyProperties(true);

        Representer representer = new Representer();
        representer.addClassTag(defaultConfig.getClass(), Tag.MAP);

        Yaml yaml = new Yaml(representer, options);
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            yaml.dump(defaultConfig, writer);
        }
    }

    public static Map<String, String> getConfig() {
        Map<String, String> data = null;
        try {
            FileInputStream input = new FileInputStream(CONFIG_FILE_PATH);
            Yaml yaml = new Yaml();
            data = yaml.load(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.exit(0);
        }
        return data;
    }
}
