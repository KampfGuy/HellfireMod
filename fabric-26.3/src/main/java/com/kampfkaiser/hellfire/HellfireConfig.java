
package com.kampfkaiser.hellfire;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public final class HellfireConfig {
    public static int nuclearRadius = 18;
    public static double explosionPower = 3.2;
    public static double missilePower = 5.5;
    public static double bombPower = 3.0;
    public static boolean missileStartsFires = false;
    public static int fuseSeconds = 10;

    private HellfireConfig() {}

    public static void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("hellfire.properties");
        Properties properties = new Properties();
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                properties.load(reader);
            } catch (IOException ignored) {
            }
        } else {
            properties.setProperty("nuclearRadius", "18");
            properties.setProperty("explosionPower", "3.2");
            properties.setProperty("missilePower", "5.5");
            properties.setProperty("bombPower", "3.0");
            properties.setProperty("missileStartsFires", "false");
            properties.setProperty("fuseSeconds", "10");
            try {
                Files.createDirectories(path.getParent());
                try (Writer writer = Files.newBufferedWriter(path)) {
                    properties.store(writer, "Hellfire in-game blast settings. Radii are Minecraft blocks, not real weapons.");
                }
            } catch (IOException ignored) {
            }
        }
        nuclearRadius = clamp(parseInt(properties.getProperty("nuclearRadius"), 18), 8, 22);
        explosionPower = clampD(parseDouble(properties.getProperty("explosionPower"), 3.2), 1.0, 6.0);
        missilePower = clampD(parseDouble(properties.getProperty("missilePower"), 5.5), 1.0, 6.0);
        bombPower = clampD(parseDouble(properties.getProperty("bombPower"), 3.0), 1.0, 5.0);
        missileStartsFires = Boolean.parseBoolean(properties.getProperty("missileStartsFires", "false"));
        fuseSeconds = clamp(parseInt(properties.getProperty("fuseSeconds"), 10), 3, 30);
    }

    public static int nuclearRadius() { return nuclearRadius; }
    public static float explosionPower() { return (float) explosionPower; }
    public static float missilePower() { return (float) missilePower; }
    public static float bombPower() { return (float) bombPower; }
    public static boolean missileStartsFires() { return missileStartsFires; }
    public static int fuseTicks() { return fuseSeconds * 20; }

    private static int parseInt(String raw, int fallback) {
        try { return Integer.parseInt(raw.trim()); } catch (Exception e) { return fallback; }
    }
    private static double parseDouble(String raw, double fallback) {
        try { return Double.parseDouble(raw.trim()); } catch (Exception e) { return fallback; }
    }
    private static int clamp(int v, int min, int max) { return Math.max(min, Math.min(max, v)); }
    private static double clampD(double v, double min, double max) { return Math.max(min, Math.min(max, v)); }
}
