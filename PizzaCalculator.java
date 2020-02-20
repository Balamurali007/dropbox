import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class PizzaCalculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader((System.in))));
        long maxPeople = in.nextInt();
        long availableTypes = in.nextInt();
        String line;
        do {
            line = in.nextLine();
        } while (line.isEmpty());
        String[] split = line.split(" ");
        long[] slices = Arrays.stream(split).mapToLong(Integer::parseInt).toArray();
        calculateSlices(maxPeople, availableTypes, slices);
    }

    private static void calculateSlices(long maxPeople, long availableTypes, long[] slices) {
        List<Integer> dummyList = null;
        Collection lastResult = new Collection(0, dummyList, true, true);
        Collection currentResult;
        for (long startIndex = slices.length - 1; startIndex >= 0; startIndex--) {
            currentResult = iterator(slices, maxPeople, startIndex);
            if (currentResult.notExceeded && currentResult.notEqualled && lastResult.count < currentResult.count) {
                lastResult = currentResult;
            }
            if (!currentResult.notEqualled) {
                lastResult = currentResult;
                break;
            }
            slices = Arrays.copyOf(slices, slices.length - 1);
        }
        Collections.reverse(lastResult.indexes);
        System.out.println(lastResult.indexes.size());
        String sol = lastResult.indexes.stream().map(Object::toString).collect(Collectors.joining(" "));
        System.out.print(sol);
    }

    public static Collection iterator(long[] arr, long maxPeople, long startIndex) {
        ArrayList<Integer> indexOfPizza = new ArrayList<>();
        long count = 0;
        boolean notEqualled = true;
        boolean notExceeded = true;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (notEqualled && notExceeded) {
                count = count + arr[i];
                if (count < maxPeople) {
                    indexOfPizza.add(i);
                } else if (count == maxPeople) {
                    indexOfPizza.add(i);
                    notEqualled = false;
                    return new Collection(count, indexOfPizza, notEqualled, notExceeded);
                } else {
                    count = count - arr[i];
                }
            } else if (!notExceeded) {
                count = count - arr[i];
                notExceeded = true;
            }
        }
        return new Collection(count, indexOfPizza, notEqualled, notExceeded);
    }

    public static class Collection {
        long count;
        List<Integer> indexes;
        boolean notEqualled;
        boolean notExceeded;

        public Collection(long count, List<Integer> indexes, boolean notEqualled, boolean notExceeded) {
            this.count = count;
            this.indexes = indexes;
            this.notEqualled = notEqualled;
            this.notExceeded = notExceeded;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public List<Integer> getIndexes() {
            return indexes;
        }

        public void setIndexes(List<Integer> indexes) {
            this.indexes = indexes;
        }

        public boolean isNotEqualled() {
            return notEqualled;
        }

        public void setNotEqualled(boolean notEqualled) {
            this.notEqualled = notEqualled;
        }

        public boolean isNotExceeded() {
            return notExceeded;
        }

        public void setNotExceeded(boolean notExceeded) {
            this.notExceeded = notExceeded;
        }
    }
}