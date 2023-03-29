package Week5.Dictionaries.HashedDictionary;

import java.util.Random;

/**
 * Generates key value pairs and that will be used to fill up hash tables.
 */
public class KeyValueGenerator {
    private String[][] kvTable;

    /**
     * Constructs an empty key value 2D array with specified size.
     * @param size length of the array
     * @param id   array id
     * @param len  random alphanumeric string length
     */
    public KeyValueGenerator(int size, String id, int len) {
        kvTable = new String[2][size];
        generateKVTable(id,len);
    }

    /**
     * Generates master key value table by generating random strings.
     * Run time is O(n).
     * @param id string identifier
     * @param len length of string
     */
    public void generateKVTable(String id, int len) {
        int n = kvTable[0].length;
        for (int i = 0; i < n; i++) {
            String key = "key" + id + "-" + randomString(len);
            kvTable[0][i] = key;
            String value = "value" + id + "-" + randomString(len);
            kvTable[1][i] = value;
        }
    }

    /**
     * Generates random string with specified length.
     * Run time is O(n).
     * @param len length of string
     */
    public String randomString(int len) {
        final String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567";
        int n = base.length();
        Random rand = new Random();

        String genStr = "";
        for (int i = 0; i < len; i++) {
            int j = rand.nextInt(n);
            genStr += base.substring(j, j+1);
        }
        return genStr;
    }

    /**
     * Populates 2D kvSubset table with key value pairs from master table.
     * Run time is O(n).
     * @param kvSubset takes sample of the larger array
     * @throws IllegalArgumentException() if subset is larger than 50%
     */
    public void getKVSubset(String[][] kvSubset) {
        int size = kvSubset[0].length;
        int n = kvTable[0].length;
        if(size > n/2) {
            throw new IllegalArgumentException("Error: Subset is more than 50% large. Pick smaller subset.");
        }
        boolean[] isUnique = new boolean[n];
        Random rand = new Random();
        int index;
        for (int i = 0; i < size; i++) {
            do {
                index = rand.nextInt(n);
            }
            while (isUnique[index]);
            isUnique[index] = true;
            kvSubset[0][i] = kvTable[0][index]; // Get key
            kvSubset[1][i] = kvTable[1][index]; // Get value
        }
    }
}
