import java.util.*;

public class Shift {


    static int roll(int n, int shift, int numbits) {
        int mask = (1 << numbits) - 1;
        int a = (n << shift);
        return (a & mask) | (a >> numbits);
    }

    static int reverse(int x, int bits) {
        int mask = (1 << bits) - 1;
        x = ((x & 0x55555555) << 1) | ((x & 0xAAAAAAAA) >> 1);
        x = ((x & 0x33333333) << 2) | ((x & 0xCCCCCCCC) >> 2);
        x = ((x & 0x0F0F0F0F) << 4) | ((x & 0xF0F0F0F0) >> 4);
        x = ((x & 0x00FF00FF) << 8) | ((x & 0xFF00FF00) >> 8);
        x = ((x & 0x0000FFFF) << 16) | ((x & 0xFFFF0000) >> 16);
        return mask & (x >> (32 - bits));
    }


    static class Mut {
        public final int n;
        public final int rot;
        public final boolean mirror;

        public Mut(int n, int rot, boolean mirror) {
            this.n = n;
            this.rot = rot;
            this.mirror = mirror;
        }

        @Override
        public String toString() {
            return "Mut{" +
                    "n = " + n +
                    ", rot = " + rot +
                    ", mirror = " + mirror +
                    '}';
        }
    }

    public static void main(String[] args) {

        HashMap<Integer, List<Mut>> numbers = new HashMap<>();

        for (int i = 0; i < (1 << 6); ++i) {
            System.out.println(">> " + i + " " + Integer.toBinaryString(i));
            int s = 0;
            for (; s < 6; ++s) {
                int m1 = roll(i, s, 6);
                if (numbers.containsKey(m1)) {
                    numbers.get(m1).add(new Mut(i, s, false));
                    break;
                }
            }
            if (s == 6) {
                for (s = 0; s < 6; ++s) {
                    int m2 = reverse(roll(i, s, 6), 6);
                    if (numbers.containsKey(m2)) {
                        numbers.get(m2).add(new Mut(i, s, true));
                        break;
                    }
                }
            }
            if (s == 6) {
                numbers.put(i, new ArrayList<>(Collections.singleton(new Mut(i, 0, false))));
            }
        }

        System.out.println(numbers.size());
        for (Map.Entry<Integer, List<Mut>> entry : numbers.entrySet()) {
            System.out.println(Integer.toBinaryString(entry.getKey()) + " " + entry);
        }
        for (Map.Entry<Integer, List<Mut>> entry : numbers.entrySet()) {
            System.out.println(Integer.toBinaryString(entry.getKey()) + " " + entry.getKey());
        }
    }
}
