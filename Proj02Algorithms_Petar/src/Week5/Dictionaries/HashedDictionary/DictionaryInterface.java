package Week5.Dictionaries.HashedDictionary;

import java.util.Iterator;

public interface DictionaryInterface<K, V> {
    /**
     * Adds key value pair to the hashTable and returns value.
     * @param key   used to find index
     * @param value value associated with key
     * @return oldValue
     */
    V add(K key, V value);

    /**
     * Removes key value pair and returns removed value.
     * @param key   used to find index
     * @return removedValue
     */
    V remove(K key);

    /**
     * Returns value with specified key.
     * @param key   used to find index
     * @return result
     */
    V getValue(K key);

    /**
     * Checks if value with specified key exists.
     * @param key   used to find index
     * @return getValue(key) != null
     */
    boolean contains(K key);
    /**
     * Returns number of entries in the hash table.
     * @return numberOfEntries
     */
    int getSize();

    /** Clears hash table. */
    void clear();

    /**
     * Returns new key iterator.
     * @return new KeyIterator()
     */
    Iterator<K> getKeyIterator();
    /**
     * Returns new value iterator.
     * @return new getValueIterator()
     */
    Iterator<V> getValueIterator();
}
