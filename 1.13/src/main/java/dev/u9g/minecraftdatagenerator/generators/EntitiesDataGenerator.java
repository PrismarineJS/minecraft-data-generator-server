package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.Projectile;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class EntitiesDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "entities";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        Registry<EntityType<?>> entityTypeRegistry = Registry.ENTITY_TYPE;
        for (EntityType<?> entityType : (Iterable<EntityType<?>>) entityTypeRegistry) {
            resultArray.add(generateEntity(entityTypeRegistry, entityType));
        }
        return resultArray;
    }

    public static JsonObject generateEntity(Registry<EntityType<?>> entityRegistry, EntityType<?> entityType) {
        JsonObject entityDesc = new JsonObject();
        Identifier registryKey = entityRegistry.getId(entityType);
        int entityRawId = entityRegistry.getRawId(entityType);
        Class<? extends Entity> entityClass = getEntityClass(entityType);
        @Nullable Entity entity = makeEntity(entityType);

        entityDesc.addProperty("id", entityRawId);
        entityDesc.addProperty("internalId", entityRawId);
        entityDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());

        entityDesc.addProperty("displayName", DGU.translateText(entityType.getTranslationKey()));
        entityDesc.addProperty("width", entity == null ? 0 : entity.width);
        entityDesc.addProperty("height", entity == null ? 0 : entity.height);

        String entityTypeString = "UNKNOWN";
        entityTypeString = getEntityTypeForClass(entityClass);
        entityDesc.addProperty("type", entityTypeString);
        entityDesc.addProperty("category", getCategoryFrom(entityType));

        return entityDesc;
    }

    private static Entity makeEntity(EntityType<?> type) {
        Entity entity;
        try {
            entity = type.spawn(DGU.getWorld());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }

    private static Class<? extends Entity> getEntityClass(EntityType<?> entityType) {
        Class<? extends Entity> entityClazz = null;
        try {
            for (Field field : EntityType.class.getFields())
                if (entityType == field.get(EntityType.class))
                    entityClazz = (Class<? extends Entity>)((ParameterizedType) TypeToken.get(field.getGenericType()).getType()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (entityClazz == null) throw new RuntimeException("Shouldn't be null...");
        return entityClazz;
    }

    private static String getCategoryFrom(@NotNull EntityType<?> entityType) {
        if (entityType == EntityType.PLAYER) return "other"; // fail early for player entities
        Class<? extends Entity> entityClazz = getEntityClass(entityType);
        String packageName = entityClazz.getPackage().getName();
        String category = null;
        switch (packageName) {
            case "net.minecraft.entity.decoration":
            case "net.minecraft.entity.decoration.painting":
                category = "Immobile";
                break;
            case "net.minecraft.entity.boss":
            case "net.minecraft.entity.mob":
            case "net.minecraft.entity.boss.dragon":
                category = "Hostile mobs";
                break;
            case "net.minecraft.entity.projectile":
            case "net.minecraft.entity.thrown":
                category = "Projectiles";
                break;
            case "net.minecraft.entity.passive":
                category = "Passive mobs";
                break;
            case "net.minecraft.entity.vehicle":
                category = "Vehicles";
                break;
            case "net.minecraft.entity":
                category = "other";
                break;
            default:
                throw new Error("Unexpected entity type: " + packageName);
        };
        return category;
    }

    //Honestly, both "type" and "category" fields in the schema and examples do not contain any useful information
    //Since category is optional, I will just leave it out, and for type I will assume general entity classification
    //by the Entity class hierarchy (which has some weirdness too by the way)
    private static String getEntityTypeForClass(Class<? extends Entity> entityClass) {
        //Top-level classifications
        if (WaterCreatureEntity.class.isAssignableFrom(entityClass)) {
            return "water_creature";
        }
        if (AnimalEntity.class.isAssignableFrom(entityClass)) {
            return "animal";
        }
        if (HostileEntity.class.isAssignableFrom(entityClass)) {
            return "hostile";
        }
        if (AmbientEntity.class.isAssignableFrom(entityClass)) {
            return "ambient";
        }

        //Second level classifications. PathAwareEntity is not included because it
        //doesn't really make much sense to categorize by it
        if (PassiveEntity.class.isAssignableFrom(entityClass)) {
            return "passive";
        }
        if (MobEntity.class.isAssignableFrom(entityClass)) {
            return "mob";
        }

        //Other classifications only include living entities and projectiles. everything else is categorized as other
        if (LivingEntity.class.isAssignableFrom(entityClass)) {
            return "living";
        }
        if (Projectile.class.isAssignableFrom(entityClass)) {
            return "projectile";
        }
        return "other";
    }
}
