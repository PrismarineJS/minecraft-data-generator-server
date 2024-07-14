package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataGenerators {
    private static final List<IDataGenerator> GENERATORS = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DataGenerators.class);

    static {
        List<Class<IDataGenerator>> generators;
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("dev.u9g.minecraftdatagenerator.generators")
                .overrideClassLoaders(IDataGenerator.class.getClassLoader())
                .enableClassInfo().scan()) {
            generators = scanResult
                    .getClassesImplementing(IDataGenerator.class)
                    .loadClasses(IDataGenerator.class);
        }

        for (Class<IDataGenerator> generatorClass : generators) {
            try {
                GENERATORS.add(generatorClass.getDeclaredConstructor().newInstance());
            } catch (ReflectiveOperationException e) {
                LOGGER.error("Failed to instantiate data generator {}", generatorClass.getName(), e);
            }
        }
    }

    public static boolean runDataGenerators(Path outputDirectory) {
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            LOGGER.error("Failed to create data generator output directory at {}", outputDirectory, e);
            return false;
        }

        int generatorsFailed = 0;
        LOGGER.info("Running minecraft data generators, output at {}", outputDirectory);

        for (IDataGenerator dataGenerator : GENERATORS) {
            if (!dataGenerator.isEnabled()) {
                LOGGER.info("Skipping disabled generator {}", dataGenerator.getDataName());
                continue;
            }

            LOGGER.info("Running generator {}", dataGenerator.getDataName());
            try {
                String outputFileName = String.format("%s.json", dataGenerator.getDataName());
                JsonElement outputElement = dataGenerator.generateDataJson();
                Path outputFilePath = outputDirectory.resolve(outputFileName);

                try (Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    JsonWriter jsonWriter = new JsonWriter(writer);
                    jsonWriter.setIndent("  ");
                    Streams.write(outputElement, jsonWriter);
                }

                LOGGER.info("Generator: {} -> {}", dataGenerator.getDataName(), outputFileName);
            } catch (Throwable e) {
                LOGGER.error("Failed to run data generator {}", dataGenerator.getDataName(), e);
                generatorsFailed++;
            }
        }

        LOGGER.info("Finishing running data generators");
        return generatorsFailed == 0;
    }
}
