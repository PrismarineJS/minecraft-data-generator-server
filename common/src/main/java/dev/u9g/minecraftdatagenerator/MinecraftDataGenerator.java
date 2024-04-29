package dev.u9g.minecraftdatagenerator;

import dev.u9g.minecraftdatagenerator.generators.DataGenerators;

import java.nio.file.Path;
import java.util.logging.Logger;

public class MinecraftDataGenerator {
    public static final Logger LOGGER = Logger.getLogger("mc-data-gen-serv");

    public static void start(ClassLoader classLoader, String versionName, Path serverRootDirectory) {
        MinecraftDataGenerator.LOGGER.info("Starting data generation!");
        Path dataDumpDirectory = serverRootDirectory.resolve("minecraft-data").resolve(versionName);
        boolean success = DataGenerators.runDataGenerators(dataDumpDirectory);
        MinecraftDataGenerator.LOGGER.info("Done data generation!");
        Runtime.getRuntime().halt(success ? 0 : 1);
    }
}
