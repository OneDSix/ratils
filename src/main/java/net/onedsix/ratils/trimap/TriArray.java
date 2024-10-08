package net.onedsix.ratils.trimap;

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
    public int currentSize;
    public D preferredDivider;
    /** The capacity of the arrays.<br>
     * There is no way to change the target capacity after construction. */
    public final int maximumCapacity;
    
    public TriArray(D preferredDivider, int capacity) {
        this.dividers = new Object[capacity];
        this.keys = new Object[capacity];
        this.values = new Object[capacity];
        this.preferredDivider = preferredDivider;
        this.currentSize = 0;
        this.maximumCapacity = capacity;
    }
    
    /** Returns the location of the given divider-key pair.
     * @return The location of the specified divider-key pair, or {@code -1} if not found */
    public int getLocation(D divider, K key) {
        for (int i = 0; i < currentSize; i++) {
            if (dividers[i].equals(divider) && keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public int dividerSize() {
        return dividers.length;
    }
    
    @Override
    public int largestInternalSize() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriArray does not support largestInternalSize()");
    }
    
    @Override
    public int size() {
        return currentSize;
    }
    
    public int getCapacity() {
        return maximumCapacity;
    }
    
    public int spaceRemaining() {
        return maximumCapacity - currentSize;
    }
    
    @Override
    public boolean containsDivider(D supposedDivider) {
        for (int i = 0; i < currentSize; i++) {
            if (dividers[i].equals(supposedDivider)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(K supposedKey) {
        return containsKey(this.preferredDivider, supposedKey);
    }
    
    @Override
    public boolean containsKey(D divider, K supposedKey) {
        return getLocation(divider, supposedKey) >= 0; // -1 means not found
    }
    
    @Override
    public boolean containsValue(V supposedValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriArray does not support containsValue()");
    }
    
    @Override
    public boolean containsValue(D divider, V supposedValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriArray does not support containsValue()");
    }
    
    @Override
    public V get(K key) {
        return get(preferredDivider,key);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public V get(D divider, K key) {
        int position = getLocation(divider, key);
        if (position != -1) {
            return (V) values[position];
        } else {
            return null;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Map<K, V> getSubmap(D divider) {
        Map<K, V> map = new WeakHashMap<>();
        for (int i = 0; i < currentSize; i++) {
            if (dividers[i].equals(divider)) {
                map.put((K) keys[i], (V) values[i]);
            }
        }
        return map;
    }
    
    @Override
    public Map<K, V> getSubmap() {
        return getSubmap(preferredDivider);
    }
    
    @Override
    public V put(K key, V value) throws ArrayIndexOutOfBoundsException {
        return put(this.preferredDivider, key, value);
    }
    
    @Override
    public V put(D divider, K key, V value) throws ArrayIndexOutOfBoundsException {
        if (maximumCapacity == currentSize)
            throw new ArrayIndexOutOfBoundsException("This TriArray has reached capacity!");
        
        // Check if the entry already exists
        int position = getLocation(divider, key);
        if (position != -1) {
            values[position] = value;
            return value;
        }
        // If not, add new entry
        dividers[currentSize] = divider;
        keys[currentSize] = key;
        values[currentSize] = value;
        currentSize++;
        return value;
    }
    
    @Override
    public void putAll(D divider, Map<K, V> map) {
        map.forEach((key, value) -> put(divider, key, value));
    }
    
    @Override
    public V removeEntry(D divider, K key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriArray does not support removal");
    }
    
    @Override
    public Map<K, V> removeSubmap(D divider) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("TriArray does not support removal");
    }
    
    @Override
    public void clear() {
        dividers = new Object[maximumCapacity];
        keys = new Object[maximumCapacity];
        values = new Object[maximumCapacity];
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
