package dev.u9g.minecraftdatagenerator;

import dev.u9g.minecraftdatagenerator.generators.DataGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

public class MinecraftDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinecraftDataGenerator.class);

    public static void start(String versionName, Path serverRootDirectory) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
        MinecraftDataGenerator.LOGGER.info("Starting data generation!");
        Path dataDumpDirectory = serverRootDirectory.resolve("minecraft-data").resolve(versionName);
        if (DataGenerators.runDataGenerators(dataDumpDirectory)) {
            MinecraftDataGenerator.LOGGER.info("Data generation successful!");
        } else {
            MinecraftDataGenerator.LOGGER.error("Data generation failed!");
        }
        Runtime.getRuntime().halt(0);
    }
}
