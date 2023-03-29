package Week5.Dictionaries.HashedDictionary;

public class Main {
    public static void main(String[] args) {
        /**
         * Keep all constant parameters in one central place.
         */
        final int RANDOM_STRING_KEY_SIZE = 6;
        final int KEY_SET_SIZE_A = 10000;
        final int KEY_SET_SIZE_B = 10000;
        final int KEY_BUCKET_SIZE_A = 1000;
        final int KEY_BUCKET_SIZE_B = 1000;
        final int HASHTABLE_INIT_SIZE = 2000;
        final int COLLISION_STATS_TABLE_SIZE = 1000;
        final int STATS_SAMPLES = 1000;

        /**
         * For single hashing use id A:
         * Create kvSet object with all key pair values.
         * Select unique subset of key pair values randomly.
         * Display populated kvSubset.
         */
        KeyValueGenerator kvSetA = new KeyValueGenerator(KEY_SET_SIZE_A,"A", RANDOM_STRING_KEY_SIZE);
        String[][] kvSubsetA = new String[2][KEY_BUCKET_SIZE_A];
        kvSetA.getKVSubset(kvSubsetA);
        //displayKVSet(kvSubsetA);

        /**
         * For single hashing use id B:
         * Create kvSet object with all key pair values.
         * Select unique subset of key pair values randomly.
         * Display populated kvSubset.
         */
        KeyValueGenerator kvSetB = new KeyValueGenerator(KEY_SET_SIZE_B,"B", RANDOM_STRING_KEY_SIZE);
        String[][] kvSubsetB = new String[2][KEY_BUCKET_SIZE_B];
        kvSetB.getKVSubset(kvSubsetB);
        //displayKVSet(kvSubsetB);

        System.out.println("\n******** Linear Probing ***********\n");
        /**
         * Create hashTableA object for single hashing.
         * Fill hashTableA with key value subset that is less than table size.
         * Display populated hashTable.
         */
        HashedDictionary<String, String> hashTableA = new HashedDictionary(HASHTABLE_INIT_SIZE);
        fillHashTable(hashTableA,kvSubsetA);
        System.out.println("Hash Table Size:             " + hashTableA.getTableSize());
        //hashTableA.displayHashTable();

        /**
         * Single Hashing:
         * Get collision counts for add method.
         * Reset collision counts.
         * Try to find keys that don't exist.
         * Display collisionCount for getValue method.
         */
        System.out.println("collisionCount for add:      " + hashTableA.getCollisionCount());
        hashTableA.resetCollisionCount();
        System.out.println("Expected collisionCount: 0   " + hashTableA.getCollisionCount());
        System.out.println("Expected null:               " + hashTableA.getValue("blablabla"));
        System.out.println("Expected false :             " + findKeys(hashTableA,kvSubsetB));
        System.out.println("collisionCount for getValue: " + hashTableA.getCollisionCount() + "\n");

        // Required testing starts here.
        /**
         * Create CollisionAnalyzer object for collecting and analyzing collision stats for getValue method.
         */
        CollisionAnalyzer stats = new CollisionAnalyzer(COLLISION_STATS_TABLE_SIZE);


        /**
         * Collect desired number of test samples and display collision counts stats for single hashing.
         */
        for(int i = 0; i < STATS_SAMPLES; i++) {
            kvSetA.getKVSubset(kvSubsetA);
            hashTableA.clear();
            fillHashTable(hashTableA,kvSubsetA);
            hashTableA.resetCollisionCount();
            kvSetB.getKVSubset(kvSubsetB);
            findKeys(hashTableA,kvSubsetB);
            stats.add(hashTableA.getCollisionCount());
            //System.out.println(hashTableA.toString());
        }
        System.out.println(stats.toString());
        stats.reset(); // Reset collision analyze table to start from the beginning.
        System.out.println("Hash Table Size:     " + hashTableA.getTableSize());
        System.out.println("Current Load Factor: " + String.format("%.3f", hashTableA.getCurrentLoadFactor()) + "\n");

        // Required testing - double hashing
        System.out.println("\n******** Double Hashing ***********\n");
        /**
         * Create hashTableB object for double hashing.
         * Fill hashTableB with key value subset that is less than table size.
         * Get table size and hash prime number for hash2 function.
         * Display populated hashTable.
         */
        DoubleHashedDictionary<String, String> hashTableB = new DoubleHashedDictionary(HASHTABLE_INIT_SIZE);
        fillHashTable(hashTableB,kvSubsetB);
        System.out.println("Hash Table Size:             " + hashTableB.getTableSize());
        System.out.println("Hash Prime Number:           " + hashTableB.getHashPrimeNumber());
        //hashTableB.displayHashTable();

        /**
         * Double Hashing:
         * Get collision counts for add method.
         * Reset collision counts.
         * Try to find keys that don't exist.
         * Display collisionCount for getValue method.
         */
        System.out.println("collisionCount for add:      " + hashTableB.getCollisionCount());
        hashTableB.resetCollisionCount();
        System.out.println("Expected collisionCount: 0   " + hashTableB.getCollisionCount());
        System.out.println("Expected null:               " + hashTableB.getValue("blablabla"));
        System.out.println("Expected false :             " + findKeys(hashTableB,kvSubsetA));
        System.out.println("collisionCount for getValue: " + hashTableB.getCollisionCount() + "\n");


        CollisionAnalyzer statsB = new CollisionAnalyzer(COLLISION_STATS_TABLE_SIZE);

        /**
         * Collect desired number of test samples and display collision counts stats for double hashing.
         */
        for(int i = 0; i < STATS_SAMPLES; i++) {
            kvSetB.getKVSubset(kvSubsetB);
            hashTableB.clear();
            fillHashTable(hashTableB,kvSubsetB);
            hashTableB.resetCollisionCount();
            kvSetA.getKVSubset(kvSubsetA);
            findKeys(hashTableB,kvSubsetA);
            statsB.add(hashTableB.getCollisionCount());
            //System.out.println(hashTableB.toString());
        }
        System.out.println(statsB.toString());
        statsB.reset(); // Reset collision analyze table to start from the beginning.
        System.out.println("Hash Table Size:     " + hashTableB.getTableSize());
        System.out.println("Current Load Factor: " + String.format("%.3f", hashTableB.getCurrentLoadFactor()) + "\n");

        // Testing original HashedDictionary class methods
//        HashedDictionary dictionary = new HashedDictionary();
//
//        dictionary.add("key-123", 7);
//        System.out.println("Expected 7: " + dictionary.getValue("key-123"));
//        dictionary.add("key-abc", 13);
//        System.out.println("Expected 13: " + dictionary.getValue("key-abc"));
//
//        System.out.println("\nDisplay hash table content:");
//        dictionary.displayHashTable();
//
//        dictionary.remove("key-123");
//        System.out.println("Expected: available - removed state:");
//        dictionary.displayHashTable();
//
//        dictionary.add("Boban", 51);
//        System.out.println("Expected: 51 " + dictionary.getValue("Boban"));
//        dictionary.displayHashTable();
//
//        System.out.print("Expected: false ");
//        System.out.println(dictionary.contains(2));
//        System.out.print("Expected: false ");
//        System.out.println(dictionary.isEmpty());
//        dictionary.clear();
//        System.out.print("Expected: true ");
//        System.out.println(dictionary.isEmpty());
//        System.out.print("Expected: 0 ");
//        System.out.println(dictionary.getSize());

    }

    /**
     * Displays key value set.
     * Run time is O(n).
     * @param kvTable 2 dimensional String array with key value subset
     */
    public static void displayKVSet(String[][] kvTable) {
        for (int i=0; i < kvTable[0].length; i++) {
            System.out.println(kvTable[0][i] + " : " + kvTable[1][i]);
            //System.out.println(array1[0][i]);
        }
    }

    /**
     * Fills hash table with key value subset for linear probing.
     * @param hashTable HashedDictionary object
     * @param bucket    2 dimensional String array with key value subset
     */
    public static void fillHashTable(HashedDictionary<String, String> hashTable, String[][] bucket) {
        // Precondition: bucket size should be no more than 50% of table size.
        if (bucket[0].length > 0.8 * hashTable.getTableSize()) {
            throw new IllegalArgumentException("Error: Bucket size is too large, more than 80% of hashtable size.");
        }
        for (int i = 0; i < bucket[0].length; i++) {
            hashTable.add(bucket[0][i], bucket[1][i]);
        }
    }

    /**
     * Fills hash table with key value subset for double hash probing.
     * @param hashTable HashedDictionary object
     * @param bucket    2 dimensional String array with key value subset
     */
    public static void fillHashTable(DoubleHashedDictionary<String, String> hashTable, String[][] bucket) {
        // Precondition: bucket size should be no more than 50% of table size.
        if (bucket[0].length > 0.8 * hashTable.getTableSize()) {
            throw new IllegalArgumentException("Error: Bucket size is too large, more than 80% of hashtable size.");
        }
        for (int i = 0; i < bucket[0].length; i++) {
            hashTable.add(bucket[0][i], bucket[1][i]);
        }
    }

    /**
     * Searches for all key value pairs from the bucket for linear probing.
     * @param hashTable HashedDictionary object
     * @param bucket    2 dimensional String array with key value subset
     * @return          Returns false if none of the keys are found
     */
    public static boolean findKeys(HashedDictionary<String, String> hashTable, String[][] bucket) {
        boolean found = false;
        for(int i = 0; i < bucket[0].length; i++) {
            if (hashTable.getValue(bucket[0][i]) != null) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Searches for all key value pairs from the bucket for double hash probing.
     * @param hashTable HashedDictionary object
     * @param bucket    2 dimensional String array with key value subset
     * @return          Returns false if none of the keys are found
     */
    public static boolean findKeys(DoubleHashedDictionary<String, String> hashTable, String[][] bucket) {
        boolean found = false;
        for(int i = 0; i < bucket[0].length; i++) {
            if (hashTable.getValue(bucket[0][i]) != null) {
                found = true;
            }
        }
        return found;
    }
}

// ========================= Required Testing =======================
// 1,000 samples with 100 keys for both, linear and double hashing.
// ==================================================================
// Conclusions:
// 1. Double hashing has smaller clusters than linear probing.
// 2. Double hashing has slightly average collision counts.
// 3. Standard deviation for double hashing is about 50% smaller.
// 4. Percentile stats are better for double hashing.
// ==================================================================
// Double hashing formula:
// index = (hash1(key) + j * (hashPrime - (hash1(key)/2) % hashPrime)) % tableSize
// ==================================================================
//final int RANDOM_STRING_KEY_SIZE = 6;
//    final int KEY_SET_SIZE_A = 1000;
//    final int KEY_SET_SIZE_B = 10000;
//    final int KEY_BUCKET_SIZE_A = 100;
//    final int KEY_BUCKET_SIZE_B = 100;
//    final int HASHTABLE_INIT_SIZE = 197;
//    final int COLLISION_STATS_TABLE_SIZE = 1000;
//    final int STATS_SAMPLES = 1000;
//
//******** Linear Probing ***********
//
//        Hash Table Size:             197
//        collisionCount for add:      58
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 140
//
//        ==== Collisions Stats ====
//        Total samples:  1000
//        Minimum value:  73
//        Median value:   143.0
//        Average value:  149.938
//        75 Percentile:  170.0
//        90 Percentile:  197.0
//        99 Percentile:  278.0
//        Maximum value:  555
//        Standard Dev:   39.264
//        ==========================
//
//        Hash Table Size:     197
//        Current Load Factor: 0.508
//
//
//        ******** Double Hashing ***********
//
//        Hash Table Size:             197
//        Hash Prime Number:           193
//        collisionCount for add:      41
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 137
//
//        ==== Collisions Stats ====
//        Total samples:  1000
//        Minimum value:  87
//        Median value:   148.0
//        Average value:  148.281
//        75 Percentile:  163.0
//        90 Percentile:  179.0
//        99 Percentile:  199.0
//        Maximum value:  212
//        Standard Dev:   22.325
//        ==========================
//
//        Hash Table Size:     197
//        Current Load Factor: 0.508


// ========================= Required Testing ========================
// 1,000 samples with 1,000 keys for both, linear and double hashing.
// ===================================================================
//    final int RANDOM_STRING_KEY_SIZE = 6;
//    final int KEY_SET_SIZE_A = 10000;
//    final int KEY_SET_SIZE_B = 10000;
//    final int KEY_BUCKET_SIZE_A = 1000;
//    final int KEY_BUCKET_SIZE_B = 1000;
//    final int HASHTABLE_INIT_SIZE = 2000;
//    final int COLLISION_STATS_TABLE_SIZE = 1000;
//    final int STATS_SAMPLES = 1000;
//
//******** Linear Probing ***********
//
//        Hash Table Size:             2003
//        collisionCount for add:      503
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 1444
//
//        ==== Collisions Stats ====
//        Total samples:  1000
//        Minimum value:  1136
//        Median value:   1464.5
//        Average value:  1483.470
//        75 Percentile:  1564.0
//        90 Percentile:  1654.0
//        99 Percentile:  1861.0
//        Maximum value:  2069
//        Standard Dev:   130.968
//        ==========================
//
//        Hash Table Size:     2003
//        Current Load Factor: 0.499
//
//
//        ******** Double Hashing ***********
//
//        Hash Table Size:             2003
//        Hash Prime Number:           1999
//        collisionCount for add:      505
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 1449
//
//        ==== Collisions Stats ====
//        Total samples:  1000
//        Minimum value:  1227
//        Median value:   1434.0
//        Average value:  1436.701
//        75 Percentile:  1481.0
//        90 Percentile:  1528.0
//        99 Percentile:  1599.0
//        Maximum value:  1652
//        Standard Dev:   68.334
//        ==========================
//
//        Hash Table Size:     2003
//        Current Load Factor: 0.499


// ====================================================================
// Testing original HashedDictionary methods.
// ====================================================================
//        Expected 7: 7
//        Expected 13: 13
//
//        Display hash table content:
//        key-abc 13
//        key-123 7
//        null
//        null
//        null
//
//        Expected: available - removed state:
//        key-abc 13
//        available - removed state
//        null
//        null
//        null
//
//        Expected: 51 51
//        key-abc 13
//        available - removed state
//        Boban 51
//        null
//        null
//
//        Expected: false false
//        Expected: false false
//        Expected: true true
//        Expected: 0 0

// ========================== Beyond and Above ========================
// 10,000 samples with 1,000 keys for both, linear and double hashing.
// ====================================================================
//final int RANDOM_STRING_KEY_SIZE = 6;
//    final int KEY_SET_SIZE_A = 10000;
//    final int KEY_SET_SIZE_B = 10000;
//    final int KEY_BUCKET_SIZE_A = 1000;
//    final int KEY_BUCKET_SIZE_B = 1000;
//    final int HASHTABLE_INIT_SIZE = 2000;
//    final int COLLISION_STATS_TABLE_SIZE = 10000;
//    final int STATS_SAMPLES = 10000;
//
//******** Linear Probing ***********
//
//        Hash Table Size:             2003
//        collisionCount for add:      501
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 1359
//
//        ==== Collisions Stats ====
//        Total samples:  10000
//        Minimum value:  1108
//        Median value:   1480.0
//        Average value:  1492.755
//        75 Percentile:  1573.0
//        90 Percentile:  1664.0
//        99 Percentile:  1859.0
//        Maximum value:  2324
//        Standard Dev:   132.130
//        ==========================
//
//        Hash Table Size:     2003
//        Current Load Factor: 0.499
//
//
//        ******** Double Hashing ***********
//
//        Hash Table Size:             2003
//        Hash Prime Number:           1999
//        collisionCount for add:      481
//        Expected collisionCount: 0   0
//        Expected null:               null
//        Expected false :             false
//        collisionCount for getValue: 1400
//
//        ==== Collisions Stats ====
//        Total samples:  10000
//        Minimum value:  1169
//        Median value:   1440.0
//        Average value:  1441.223
//        75 Percentile:  1488.0
//        90 Percentile:  1531.0
//        99 Percentile:  1610.0
//        Maximum value:  1732
//        Standard Dev:   69.546
//        ==========================
//
//        Hash Table Size:     2003
//        Current Load Factor: 0.499


// ========================== Beyond and Above ========================
// Display visual clustering in hash tables.
// ====================================================================
//            System.out.println(hashTableB.toString());
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____ key key key key____________________ key________________ key key____ key____ key________________ key key key key key key____ key____ key key____ key key key____________________ key key key____________ key key____________ key key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key____ key____________ key____ key________________________ key____________ key key____ key____ key key key key key key____________ key____ key____ key________ key key____ key____________ key________ key____ key key key key key key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ____ key____ key key________ key key____ key________________ key key key key____ key key________ key____________________ key____ key key____________ key____ key____________________ key key key key____ key key key____ key____ key key key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key key key____ key____ key key____________ key________ key____________ key key____________ key____ key key________ key____ key____ key________ key key________ key____ key________ key key key____________ key key key____ key____ key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____________ key________ key key____ key key____ key key________ key key key________________ key________ key key____ key________ key____ key____________ key key____ key________ key________ key key key key key key____________ key key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____ key____ key____ key key key________ key key key____________ key key____ key____ key key key________ key key________ key____________________ key key________________ key____________ key key key________ key____ key____ key key key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ________ key________________ key key________ key key____ key____________ key key________________________ key key key____ key________ key key____ key key key key key key key key________ key key key key____________ key key________ key key________
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____________ key key key key key________ key key key____ key________ key key________ key____ key____ key________ key____ key key key key____ key____ key____ key____ key____ key key________ key________________________________ key key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key key____ key________________ key key key key____ key key key____ key________ key key____ key key____ key____ key____________ key key________ key____________ key key________________ key key____ key key____________ key________ key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ____ key key key________________________ key key key________ key________ key____________ key key________ key key key key key________ key key____________ key____ key key________ key____ key key key____ key key________________ key key key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ________ key key________ key key key________ key key________ key________ key key____ key key key key____ key key____ key________________ key____ key____________ key________ key____ key________ key key key key key key____________ key key________
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ____ key____ key key key____ key key key____ key____ key____________ key key key____ key key____ key____ key____________ key key key____________ key________ key________________________ key key____________ key____ key key key key____ key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key key key key________________ key key key________ key key key key____ key key key____________ key________________________ key____ key____ key key key____ key____ key____ key____ key key________ key____________ key________ key key________
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ____ key____________________ key key key________ key____ key key key____ key____________ key key________ key key____ key____ key________ key key____ key key key____________ key key key key____ key____ key____________ key________ key key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key____ key key key________________ key________ key____ key____ key key key____________________ key key key________________ key key key________ key key____ key____ key key____________________ key key________ key____ key____ key key key key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key________ key____ key________ key____ key key____ key____ key key____________ key________ key____ key key key key key________________ key____ key____________ key________ key____ key key________ key key____ key________ key key key____ key
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key key________ key key key____ key________________ key key____ key____________ key________ key key key key key key____ key____ key____________________________ key____ key key____ key____ key key key____ key key________________ key key key____
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           ________________ key key____ key____ key____________ key____ key____ key key key____ key key key____ key____ key____________ key____ key key key key key key key key________ key____ key____ key key____ key____ key________________ key____________
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____________ key key key key____________ key key key____ key key key____________ key key____ key____ key________________ key____________________ key key key____________ key________ key key key key____ key key____ key key key key____________
//
//           0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47  48  49  50  51  52  53  54  55  56  57  58  59  60
//           key____ key key____ key____ key________ key________ key key________ key____ key____ key key key________________________________ key key key key key________ key____ key key________________ key key key____ key________ key key____ key key____ key

// Sample key value pairs:
//        keyA-sbYwUH : valueA-WrNusb
//        keyA-1li7YM : valueA-7YOnUd
//        keyA-KEnjst : valueA-Ja6FOt
//        keyA-st6bxD : valueA-76NTv5
//        keyA-4jWsp0 : valueA-FmfZ4M
//        keyA-Yohx1H : valueA-3rxewl
//        keyA-mOvAeP : valueA-lpUArF
//        keyA-zpVU6M : valueA-ujnneC
//        keyA-xKxtWs : valueA-RkppnT
//        keyA-Qj3rjJ : valueA-XJtkpP
//        keyA-0iLga1 : valueA-i0K20B