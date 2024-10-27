package dev.u9g.minecraftdatagenerator.registryview;

import com.google.common.collect.HashBasedTable;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class TableBackedRegistryView<K, V> implements RegistryView<K, V> {
    private final HashBasedTable<K, Integer, V> table;

    protected TableBackedRegistryView(HashBasedTable<K, Integer, V> table) {
        this.table = table;
    }

    @Override
    public int getRawId(V value) {
        for (Map.Entry<K, Map<Integer, V>> row : table.rowMap().entrySet()) {
            Map.Entry<Integer, V> entry = row.getValue().entrySet().iterator().next();
            if (Objects.equals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("value not in registry.");
    }

    @Override
    public K getId(V value) {
        for (Map.Entry<K, Map<Integer, V>> row : table.rowMap().entrySet()) {
            Map.Entry<Integer, V> entry = row.getValue().entrySet().iterator().next();
            if (Objects.equals(entry.getValue(), value)) {
                return row.getKey();
            }
        }
        throw new IllegalStateException("value not in registry.");
    }

    @Override
    public V getByRawId(int rawId) {
        return table.columnMap().get(rawId).entrySet().iterator().next().getValue();
    }

    @Override
    public V get(K id) {
        return table.row(id).entrySet().iterator().next().getValue();
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return table.values().iterator();
    }

    @Override
    public void forEach(Consumer action) {
        for (V v : this) {
            action.accept(v);
        }
    }

    public static class Builder<K, V> {
        private final HashBasedTable<K, Integer, V> table = HashBasedTable.create();

        public Builder<K, V> add(K key, int rawId, V value) {
            table.put(key, rawId, value);
            return this;
        }

        public TableBackedRegistryView<K, V> build() {
            return new TableBackedRegistryView<>(table);
        }
    }
}
