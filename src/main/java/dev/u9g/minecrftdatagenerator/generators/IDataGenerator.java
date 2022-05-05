package dev.u9g.minecrftdatagenerator.generators;

import com.google.gson.JsonElement;

public interface IDataGenerator {

    String getDataName();

    JsonElement generateDataJson();
}
