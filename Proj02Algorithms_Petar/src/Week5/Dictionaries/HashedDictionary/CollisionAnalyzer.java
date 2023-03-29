package Week5.Dictionaries.HashedDictionary;

import java.util.Arrays;

/**
 * Calculates detailed statistics about collision counts during probing.
 */
public class CollisionAnalyzer {
    private int[] stats;
    private int size;
    private int index;

    /**
     * Constructs an empty array with specified size.
     * @param size size of integer array
     */
    public CollisionAnalyzer(int size) {
        stats = new int[size];
        this.size = size;
        index = 0;
    }

    /**
     * Sets index at the beginning of the array.
     */
    public void reset() {
        index = 0;
    }

    /**
     * Adds number of collisions to the array and increments index each time.
     * @param value number of collisions
     * @throws  IndexOutOfBoundsException() if array is full throw exception
     */
    public void add(int value) {
        if (index < size) {
            stats[index++] = value;
        }
        else {
            throw new IndexOutOfBoundsException("Error: Array is full.");
        }
    } // end add

    /**
     * Returns the average of all number of collisions
     * that were populated in the array so far.
     * Runtime is O(n).
     * If index is less than or equal 0 it returns 0.0.
     * @return sum/index
     */
    public double getAverage() {
        double sum = 0.0;
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                sum += stats[i];
            }
            return sum / index;
        }
        else {
            return 0.0;
        }
    } // end getAverage

    /**
     * Returns the standard deviation of all number of collisions
     * that were populated in the array so far based on calculated average.
     * Runtime is O(n).
     * @return Math.sqrt(standardDeviation/index)
     */
    public double getStandardDeviation()
    {
        double mean = getAverage();
        double standardDeviation = 0.0;

        for(int i = 0; i < index; i++) {
            standardDeviation += Math.pow(stats[i] - mean, 2);
        }

        return Math.sqrt(standardDeviation/index);
    }

    /**
     * Sorts the array and calculates the percentile index
     * and returns the value at that index.
     * Runtime is O(n).
     * @param percent number of data points at or below the value divided by index
     * @return sorted[percentileIndex]
     */
    public double getPercentile(double percent) {
        int[] sorted = new int[index];
        for (int i = 0; i < index; i++) {
            sorted[i] = stats[i];
        }
        Arrays.sort(sorted);
        if (index >= (int)(100.00/(100.00 - percent))) {
            int percentileIndex = (int) (index * percent / 100.0);
            return sorted[percentileIndex];
        }
        else {
            System.out.println("ATTENTION: " + String.format("%.2f", percent) + " Percentile is not accurate because there are not enough samples: " + index);
            return sorted[index-1];
        }
    }

    /**
     * Returns the data point at the 90th percentile.
     * @return getPercentile(90.0);
     */
    public double getP90() {
        return getPercentile(90.0);
    }

    /**
     * Returns the data point at the 99th percentile.
     * @return getPercentile(99.0);
     */
    public double getP99() {
        return getPercentile(99.0);
    }

    /**
     * Returns the median value.
     * Runtime is O(n).
     * @return sorted[index/2] if index is odd, otherwise (sorted[index/2] + sorted[index/2-1])/2.0
     */
    public double getMedian() {
        int[] sorted = new int[index];
        for (int i = 0; i < index; i++) {
            sorted[i] = stats[i];
        }
        Arrays.sort(sorted);
        if (index % 2 == 0) {
            return (sorted[index/2] + sorted[index/2-1])/2.0;
        }
        else {
            return sorted[index/2];
        }
    }

    /**
     * Returns the minimum value.
     * Runtime is O(n).
     * @return min
     */
    public int getMin() {
        int min = stats[0];
        for(int i = 1; i < index; i++) {
            if(min > stats[i]) {
                min = stats[i];
            }
        }
        return min;
    }

    /**
     * Returns the maximum value.
     * Runtime is O(n).
     * @return max
     */
    public int getMax() {
        int max = stats[0];
        for(int i = 1; i < index; i++) {
            if(max < stats[i]) {
                max = stats[i];
            }
        }
        return max;
    }

    /**
     * Returns string representation of all calculated stats.
     * @return str
     */
    public String toString() {
        String str = "";
        str += "==== Collisions Stats ====\n";
        str += "Total samples:  " + index + "\n";
        str += "Minimum value:  " + getMin() + "\n";
        str += "Median value:   " + getMedian() + "\n";
        str += "Average value:  " + String.format("%.3f", getAverage()) + "\n";
        str += "75 Percentile:  " + getPercentile(75.0) + "\n";
        str += "90 Percentile:  " + getP90() + "\n";
        str += "99 Percentile:  " + getP99() + "\n";
        str += "Maximum value:  " + getMax() + "\n";
        str += "Standard Dev:   " + String.format("%.3f", getStandardDeviation()) + "\n";
        str += "==========================\n";
        return str;
    }
}
