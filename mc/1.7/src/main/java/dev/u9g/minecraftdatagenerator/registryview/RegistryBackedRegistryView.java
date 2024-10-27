package dev.u9g.minecraftdatagenerator.registryview;

import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public class RegistryBackedRegistryView<V> implements RegistryView<String, V> {
    private final SimpleRegistry registry;

    public RegistryBackedRegistryView(SimpleRegistry registry) {
        this.registry = registry;
    }

    @Override
    public int getRawId(V value) {
        return registry.getRawId(value);
    }

    @Override
    public String getId(V value) {
        return registry.getId(value);
    }

    @Override
    public V getByRawId(int rawId) {
        return (V) registry.getByRawId(rawId);
    }

    @Override
    public V get(String id) {
        return (V) registry.get(id);
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return (Iterator<V>) registry.iterator();
    }

    @Override
    public void forEach(Consumer<? super V> action) {
        registry.forEach(value -> action.accept((V) value));
    }
}
