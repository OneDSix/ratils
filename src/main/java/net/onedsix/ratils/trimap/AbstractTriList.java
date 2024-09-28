package net.onedsix.ratils.trimap;

import java.util.List;
import java.util.Map;

public abstract class AbstractTriList<D, K, V> implements TriMap<D, K, V> {
    public List<D> dividers;
    public List<K> keys;
    public List<V> values;
    public D preferredDivider;
    
    protected AbstractTriList(List<D> dividers, List<K> keys, List<V> values, D preferredDivider) {
        this.dividers = dividers;
        this.keys = keys;
        this.values = values;
        this.preferredDivider = preferredDivider;
    }
    @Override
    public int dividerSize() {
        return 0;
    }
    
    @Override
    public int largestInternalSize() {
        return 0;
    }
    
    @Override
    public int totalSize() {
        return 0;
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
        return null;
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
        return null;
    }
    
    @Override
    public V put(K key, V value) {
        return null;
    }
    
    @Override
    public V put(D divider, K key, V value) {
        return null;
    }
    
    @Override
    public void putAll(D divider, Map<K, V> map) {
    
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
    
    }
    
    @Override
    public D setPreferredDivider(D divider) {
        return null;
    }
    
    @Override
    public D getPreferredDivider() {
        return preferredDivider;
    }
}
