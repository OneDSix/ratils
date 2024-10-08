package net.onedsix.ratils.trimap;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTriList<D, K, V> implements TriMap<D, K, V> {
    public final List<D> dividers;
    public final List<K> keys;
    public final List<V> values;
    public D preferredDivider;
    
    public AbstractTriList(List<D> dividers, List<K> keys, List<V> values, D preferredDivider) {
        this.dividers = Collections.synchronizedList(dividers);
        this.keys = Collections.synchronizedList(keys);
        this.values = Collections.synchronizedList(values);
        this.preferredDivider = preferredDivider;
    }
    
    /** Returns the location of the given divider-key pair.
     * @return The location of the specified divider-key pair, or {@code -1} if not found */
    public int getLocation(D divider, K key) {
        for (int i = 0; i < values.size(); i++) {
            if (dividers.get(i).equals(divider) && keys.get(i).equals(key)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public int dividerSize() {
        return dividers.size();
    }
    
    @Override
    public int largestInternalSize() {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public int size() {
        return values.size();
    }
    
    @Override
    public boolean containsDivider(D supposedDivider) {
        for (D divider : dividers) {
            if (divider.equals(supposedDivider))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean containsKey(K supposedKey) {
        return containsKey(this.preferredDivider, supposedKey);
    }
    
    @Override
    public boolean containsKey(D divider, K supposedKey) {
        for (int i = 0; i < dividers.size(); i++) {
            if (dividers.get(i).equals(divider) && keys.get(i).equals(supposedKey))
                    return true;
        }
        return false;
    }
    
    @Override
    public boolean containsValue(V supposedValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriList does not support ");
    }
    
    @Override
    public boolean containsValue(D divider, V supposedValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriList does not support ");
    }
    
    @Override
    public V get(K key) {
        return get(this.preferredDivider, key);
    }
    
    @Override
    public V get(D divider, K key) {
        int position = getLocation(divider, key);
        if (position != -1) {
            return values.get(position);
        } else {
            return null;
        }
    }
    
    @Override
    public Map<K, V> getSubmap(D divider) {
        Map<K, V> returns = new HashMap<>();
        for (int i = 0; i < dividers.size(); i++) {
            if (dividers.get(i).equals(divider)) {
                returns.put(keys.get(i), values.get(i));
            }
        }
        return returns;
    }
    
    @Override
    public Map<K, V> getSubmap() {
        return getSubmap(this.preferredDivider);
    }
    
    @Override
    public V put(K key, V value) {
        return put(this.preferredDivider, key, value);
    }
    
    @Override
    public V put(D divider, K key, V value) {
        // Check if the entry already exists
        int position = getLocation(divider, key);
        if (position != -1) {
            values.set(position, value);
            return value;
        }
        // If not, add new entry
        dividers.add(divider);
        keys.add(key);
        values.add(value);
        return value;
    }
    
    @Override
    public void putAll(D divider, @NotNull Map<K, V> map) {
        map.forEach((key, value) -> AbstractTriList.this.put(divider, key, value));
    }
    
    @Override
    public V removeEntry(D divider, K key) {
        int position = getLocation(divider, key);
        if (position != -1) {
            dividers.remove(position);
            keys.remove(position);
            return values.remove(position);
        }
        return null;
    }
    
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    @Override
    public Map<K, V> removeSubmap(D divider) {
        // get the map before modifying
        Map<K, V> returns = getSubmap(divider);
        // map is now freely modifiable
        for (int i = 0; i < values.size(); i++) {
            if (dividers.get(i).equals(divider)) {
                // "Suspicious 'List.remove()' in loop" - intellij
                // should be fine?
                dividers.remove(i);
                keys.remove(i);
                values.remove(i);
            }
        }
        return returns;
    }
    
    @Override
    public void clear() {
        dividers.clear();
        keys.clear();
        values.clear();
    }
    
    @Override
    public D setPreferredDivider(D divider) {
        return this.preferredDivider = divider;
    }
    
    @Override
    public D getPreferredDivider() {
        return preferredDivider;
    }
}
