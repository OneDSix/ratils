package net.onedsix.ratils.trimap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;
import java.util.WeakHashMap;

/** An extension of {@link TriMap} that uses memory efficient arrays instead of nested maps or lists.
 * This object also gives a bit more access to manage memory yourself, as you need to add a capacity to create the object,
 * and running {@link TriArray#put(D,K,V)} may cause {@link ArrayIndexOutOfBoundsException} if not taken care of.<br>
 * @see TriMap */
@SuppressWarnings("unused")
public class TriArray<D, K, V> implements TriMap<D, K, V> {
    /** Internal data variable holding the dividers, acting as the lower-level map.
     * Don't mess with this outside this class unless you know what you're doing
     * @see TriArray#keys
     * @see TriArray#values
     * */
    public Object[] dividers;
    /** Internal data variable holding the keys, acting as part of the upper-level map.
     * Don't mess with this outside this class unless you know what you're doing
     * @see TriArray#dividers
     * @see TriArray#values
     * */
    public Object[] keys;
    /** Internal data variable holding the values, acting as part of the upper-level map.
     * Don't mess with this outside this class unless you know what you're doing
     * @see TriArray#dividers
     * @see TriArray#keys
     * */
    public Object[] values;
    public int size;
    public D preferredDivider;
    /** The capacity of the arrays.<br>
     * There is no way to change the target capacity after construction. */
    public final int capacity;
    
    public TriArray(D preferredDivider, int capacity) {
        this.dividers = new Object[capacity];
        this.keys = new Object[capacity];
        this.values = new Object[capacity];
        this.preferredDivider = preferredDivider;
        this.size = 0;
        this.capacity = capacity;
    }
    
    @Override
    public int dividerSize() {
        throw new NotImplementedException();
    }
    
    @Override
    public int largestInternalSize() {
        throw new NotImplementedException();
    }
    
    @Override
    public int totalSize() {
        return size;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public int spaceRemaining() {
        return capacity - size;
    }
    
    @Override
    public boolean containsDivider(D supposedDivider) {
        for (int i = 0; i < size; i++) {
            if (dividers[i].equals(supposedDivider)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(K supposedKey) {
        throw new NotImplementedException();
    }
    
    @Override
    public boolean containsKey(D divider, K supposedKey) {
        throw new NotImplementedException();
    }
    
    @Override
    public boolean containsValue(V supposedValue) {
        throw new NotImplementedException();
    }
    
    @Override
    public boolean containsValue(D divider, V supposedValue) {
        throw new NotImplementedException();
    }
    
    @Override
    public V get(K key) {
        return get(preferredDivider,key);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public V get(D divider, K key) {
        for (int i = 0; i < size; i++) {
            if (dividers[i].equals(divider) && keys[i].equals(key)) {
                return (V) values[i];
            }
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Map<K, V> getInternalMap(D divider) {
        Map<K, V> map = new WeakHashMap<>();
        for (int i = 0; i < size; i++) {
            if (dividers[i].equals(divider)) {
                map.put((K) keys[i], (V) values[i]);
            }
        }
        return map;
    }
    
    @Override
    public Map<K, V> getInternalMap() {
        return getInternalMap(preferredDivider);
    }
    
    @Override
    public V put(K key, V value) throws ArrayIndexOutOfBoundsException {
        return put(preferredDivider, key, value);
    }
    
    @Override
    public V put(D divider, K key, V value) throws ArrayIndexOutOfBoundsException {
        if (capacity == size)
            throw new ArrayIndexOutOfBoundsException("This TriArray has reached capacity!");
        
        // Check if the entry already exists
        for (int i = 0; i < size; i++) {
            if (dividers[i].equals(divider) && keys[i].equals(key)) {
                values[i] = value;
                return value;
            }
        }
        // If not, add new entry
        dividers[size] = divider;
        keys[size] = key;
        values[size] = value;
        size++;
        return value;
    }
    
    @Override
    public void putAll(D divider, Map<K, V> map) {
        map.forEach((key, value) -> put(divider, key, value));
    }
    
    @Override
    public boolean remove(D divider, K key) {
        throw new NotImplementedException();
    }
    
    @Override
    public Map<K, V> remove(D divider) {
        throw new NotImplementedException();
    }
    
    @Override
    public void clear() {
        dividers = new Object[capacity];
        keys = new Object[capacity];
        values = new Object[capacity];
    }
    
    @Override
    public D setPreferredDivider(D divider) {
        preferredDivider = divider;
        return preferredDivider;
    }
    
    @Override
    public D getPreferredDivider() {
        return preferredDivider;
    }
}
