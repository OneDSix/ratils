package net.onedsix.ratils.trimap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractTriList<D, K, V> implements TriMap<D, K, V> {
    public List<D> dividers;
    public List<K> keys;
    public List<V> values;
    public D preferredDivider;
    
    protected AbstractTriList(List<D> dividers, List<K> keys, List<V> values, D preferredDivider) {
        this.dividers = Collections.synchronizedList(dividers);
        this.keys = Collections.synchronizedList(keys);
        this.values = Collections.synchronizedList(values);
        this.preferredDivider = preferredDivider;
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
    public int totalSize() {
        return values.size();
    }
    
    @Override
    public boolean containsDivider(D supposedDivider) {
        return false;
    }
    
    @Override
    public boolean containsKey(K supposedKey) {
        return false;
    }
    
    @Override
    public boolean containsKey(D divider, K supposedKey) {
        return false;
    }
    
    @Override
    public boolean containsValue(V supposedValue) {
        return false;
    }
    
    @Override
    public boolean containsValue(D divider, V supposedValue) {
        return false;
    }
    
    @Override
    public V get(K key) {
        return get(this.preferredDivider, key);
    }
    
    @Override
    public V get(D divider, K key) {
        for (int i = 0; i < dividers.size(); i++) {
            if (dividers.get(i) == divider && keys.get(i) == key) {
                return values.get(i);
            }
        }
        return null;
    }
    
    @Override
    public Map<K, V> getInternalMap(D divider) {
        return null;
    }
    
    @Override
    public Map<K, V> getInternalMap() {
        return getInternalMap(this.preferredDivider);
    }
    
    @Override
    public V put(K key, V value) {
        return put(this.preferredDivider, key, value);
    }
    
    @Override
    public V put(D divider, K key, V value) {
        for (int i = 0; i < totalSize(); i++) {
            if (dividers.get(i) == divider && keys.get(i) == key) {
                values.set(i, value);
                return value;
            }
        }
        
        dividers.add(divider);
        keys.add(key);
        values.add(value);
        return value;
    }
    
    @Override
    public void putAll(D divider, Map<K, V> map) {
        map.forEach((key, value) -> AbstractTriList.this.put(divider, key, value));
    }
    
    @Override
    public boolean remove(D divider, K key) {
        return false;
    }
    
    @Override
    public Map<K, V> remove(D divider) {
        return null;
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
