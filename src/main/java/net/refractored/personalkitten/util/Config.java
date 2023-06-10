package net.refractored.personalkitten.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static Config config;
    private static final Logger logger = LogManager.getLogger(Config.class);
    protected static GsonBuilder jsonBuilder = new GsonBuilder();
    protected static Gson json = jsonBuilder.create();

    // Bot
    private String token  = "";


    public static synchronized Config getConfig() {
        String sPath = System.getProperty("user.dir") + "/config.json";
        Path path = Path.of(sPath);
        boolean fileExists = Files.exists(path);
        try {
            if (!fileExists) {
                new FileOutputStream(sPath, true).close();
            }
            byte[] dataByte = Files.newInputStream(path).readAllBytes();
            StringBuilder dataBuilder = new StringBuilder();
            for (byte b : dataByte) {
                dataBuilder.append((char) b);
            }
            String data = dataBuilder.toString();

            if (data.length() == 0 || data.equals("null")) {
                 config = new Config();
                 saveConfig();
                 logger.fatal("Missing config, please fill out config.json and restart the bot.");
                 System.exit(1);
                 return null;
            }

            config = json.fromJson(data, Config.class);

            if (!checkConfiguration()) {
                System.exit(1);
                return null;
            }

            return config;
        } catch (IOException e) {
            logger.fatal("Failed to load config.json.");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static synchronized void saveConfig() {
        try {
            OutputStream out = Files.newOutputStream(Path.of(System.getProperty("user.dir") + "/config.json"));
            out.write(json.toJson(config).getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.fatal("Failed to save config.json.");
            e.printStackTrace();
        }
    }

    public static synchronized boolean checkConfiguration() {
        if (config.getToken().length() == 0) {
            logger.fatal("Missing token, please fill out config.json and restart the bot.");
            return false;
        }
        return true;
    }

    public synchronized String getToken() {
        return token;
    }
}
