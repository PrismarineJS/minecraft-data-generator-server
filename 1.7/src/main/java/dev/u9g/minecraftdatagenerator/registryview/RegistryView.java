package dev.u9g.minecraftdatagenerator.registryview;

public interface RegistryView<K, V> extends Iterable<V> {
    int getRawId(V value);

    K getId(V value);

    V getByRawId(int rawId);

    V get(K id);
}
