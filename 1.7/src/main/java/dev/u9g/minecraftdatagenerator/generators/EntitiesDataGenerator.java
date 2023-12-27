package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.accessor.EntityTypeAccessor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.Projectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EntitiesDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "entities";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        for (Class<? extends Entity> entityType : Registries.ENTITY_TYPES) {
            resultArray.add(generateEntity(entityType));
        }
        return resultArray;
    }

    public static JsonObject generateEntity(Class<? extends Entity> entityClass) {
        JsonObject entityDesc = new JsonObject();
        String registryKey = Registries.ENTITY_TYPES.getId(entityClass);
        int entityRawId = Registries.ENTITY_TYPES.getRawId(entityClass);
        @Nullable Entity entity = makeEntity(entityClass);
        // FIXME: ENTITY ID IS WRONG
        int id = entityId(entity);
        entityDesc.addProperty("id", id);
        entityDesc.addProperty("internalId", id);
        entityDesc.addProperty("name", Objects.requireNonNull(registryKey));
        String displayName = entity != null ? entity.getTranslatedName() : null;
        if (displayName != null && !displayName.startsWith("entity.")) {
            entityDesc.addProperty("displayName", displayName);
        }
        entityDesc.addProperty("width", entity == null ? 0 : entity.width);
        entityDesc.addProperty("height", entity == null ? 0 : entity.height);

        String entityTypeString = "UNKNOWN";
        entityTypeString = getEntityTypeForClass(entityClass);
        entityDesc.addProperty("type", entityTypeString);
        entityDesc.addProperty("category", getCategoryFrom(entityClass));

        return entityDesc;
    }

    private static Entity makeEntity(Class<? extends Entity> type) {
        String name = EntityTypeAccessor.CLASS_NAME_MAP().get(type);
        return EntityType.createInstanceFromName(name, DGU.getWorld());
    }

    private static String getCategoryFrom(@NotNull Class<?> entityClass) {
        if (entityClass == PlayerEntity.class) return "other"; // fail early for player entities
        String packageName = entityClass.getPackage().getName();
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
        if (PathAwareEntity.class.isAssignableFrom(entityClass)) {
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

    private static int entityId(Entity entity) {
        if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.7")) {
            throw new Error("These ids were gotten manually for 1.7, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        }
        int rawId = Registries.ENTITY_TYPES.getRawId(entity.getClass());
        if (rawId == -1) { // see TrackedEntityInstance
            if (entity instanceof ItemEntity) {
                return 2;
            } else if (entity instanceof FishingBobberEntity) {
                return 90;
            } else {
                throw new Error("unable to find rawId for entity: " + entity.getClass().getName());
            }
        }
        return rawId;
    }
}
