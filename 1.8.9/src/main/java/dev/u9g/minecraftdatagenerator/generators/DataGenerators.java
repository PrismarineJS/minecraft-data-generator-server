package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import dev.u9g.minecraftdatagenerator.Main;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataGenerators {

    private static final List<IDataGenerator> GENERATORS = new ArrayList<>();
    private static final Logger logger = Main.LOGGER;

    public static void register(IDataGenerator generator) {
        GENERATORS.add(generator);
    }

    public static boolean runDataGenerators(Path outputDirectory) {
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException exception) {
            logger.log(Level.INFO, "Failed to create data generator output directory at " + outputDirectory);
            exception.printStackTrace();
            return false;
        }

        int generatorsFailed = 0;
        logger.log(Level.INFO, MessageFormat.format("Running minecraft data generators, output at {0}", outputDirectory));

        for (IDataGenerator dataGenerator : GENERATORS) {
            logger.log(Level.INFO, MessageFormat.format("Running generator {0}", dataGenerator.getDataName()));
            try {
                String outputFileName = String.format("%s.json", dataGenerator.getDataName());
                JsonElement outputElement = dataGenerator.generateDataJson();
                Path outputFilePath = outputDirectory.resolve(outputFileName);

                try (Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    JsonWriter jsonWriter = new JsonWriter(writer);
                    jsonWriter.setIndent("  ");
                    Streams.write(outputElement, jsonWriter);
                }
                logger.log(Level.INFO, MessageFormat.format("Generator: {0} -> {1}", dataGenerator.getDataName(), outputFileName));

            } catch (Throwable exception) {
                logger.log(Level.INFO, MessageFormat.format("Failed to run data generator {0}", dataGenerator.getDataName()));
                exception.printStackTrace();
                generatorsFailed++;
            }
        }

        logger.log(Level.INFO, "Finishing running data generators");
        return generatorsFailed == 0;
    }

    static {
        register(new BiomesDataGenerator());
        register(new BlockCollisionShapesDataGenerator());
        register(new BlocksDataGenerator());
        register(new EffectsDataGenerator());
        register(new EnchantmentsDataGenerator());
        register(new EntitiesDataGenerator());
        register(new FoodsDataGenerator());
        register(new ItemsDataGenerator());
        register(new ParticlesDataGenerator());
        register(new TintsDataGenerator());
        register(new LanguageDataGenerator());
        register(new InstrumentsDataGenerator());
        register(new AttributesDataGenerator());
    }
}
