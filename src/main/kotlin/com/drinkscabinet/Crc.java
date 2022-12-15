package com.drinkscabinet;

import java.util.HashMap;
import java.util.zip.CRC32;

public class Crc {

    public static void main(String[] args) {

        System.out.println(crc("jamesckxadb"));
        System.out.println(crc("jameszzm"));
        System.out.println(crc("secret/troweprice/foo/bar/dabaab"));
        System.out.println(crc("secret/troweprice/foo/bar/zgl"));
//        System.exit(0);

//        getIntermediateCollision("james");
        getIntermediateCollision("secret/troweprice/foo/bar/");
    }

    private static String crc(String d) {
        CRC32 crc32 = new CRC32();
        crc32.update(d.getBytes());
        return Long.toHexString(crc32.getValue());
    }

    private static String getCollision(String root) {
        CRC32 crc32 = new CRC32();
        crc32.update(root.getBytes());
        long target = crc32.getValue();
        System.out.println("Target is " + target);

        HashMap<Long, String> seenHashes = new HashMap<>(10000000);

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String postfix = calcPostfix(i);
            if( i % 1000 == 0 ) {
                System.out.println("" + i + " " + postfix);
            }
            // Calculate postfix

            crc32.reset();
            crc32.update(root.getBytes());
            crc32.update(postfix.getBytes());
            long c = crc32.getValue();
            if( c == target ) {
                return root + postfix;
            }
        }
        return null;
    }

    private static void getIntermediateCollision(String root) {
        CRC32 crc32 = new CRC32();
        crc32.update(root.getBytes());
        long target = crc32.getValue();
        System.out.println("Target is " + target);

        HashMap<Long, String> seenHashes = new HashMap<>(10000000);

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String postfix = calcPostfix(i);
            if( i % 1000 == 0 ) {
                System.out.println("" + i + " " + postfix);
            }
            // Calculate postfix

            crc32.reset();
            crc32.update(root.getBytes());
            crc32.update(postfix.getBytes());
            long c = crc32.getValue();
            if( seenHashes.containsKey(c)) {
                System.out.println("Found collision between " + postfix + " and " + seenHashes.get(c));
                System.out.println("Iterations: " + i);
                return;
            }
            else {
                seenHashes.put(c, postfix);
            }
        }
    }

    private static String calcPostfix(int i) {
        StringBuilder builder = new StringBuilder(10);

        while( i > 0 ) {
            int x = i % 26;
            builder.append((char)('a' + x));
            i /= 26;
        }
        return builder.toString();
    }
}
