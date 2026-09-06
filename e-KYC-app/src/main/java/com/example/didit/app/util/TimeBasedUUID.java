package com.example.didit.app.util;

import java.util.UUID;

public class TimeBasedUUID {
    public static void main(String[] args) {
        // Generate a random UUID (Version 4)
        UUID uuid = UUID.randomUUID();
        System.out.println("Random UUID: " + uuid);

        // Generate a time-based UUID (Version 1) using timestamp
        UUID timeBasedUUID = generateTimeBasedUUID();
        System.out.println("Time-Based UUID: " + timeBasedUUID);
    }

    public static UUID generateTimeBasedUUID() {
        long currentTimeMillis = System.currentTimeMillis();

        // UUID Version 1 format: time_low - time_mid - time_hi_and_version - clock_seq - node
        long timeLow = (currentTimeMillis & 0xFFFFFFFFL) << 32;
        long timeMid = ((currentTimeMillis >> 32) & 0xFFFFL) << 16;
        long timeHiAndVersion = ((currentTimeMillis >> 48) & 0x0FFFL) | 0x1000L; // Version 1

        long mostSigBits = timeLow | timeMid | timeHiAndVersion;

        // Random clock sequence and node
        long leastSigBits = (long) (Math.random() * Long.MAX_VALUE);
        leastSigBits = (leastSigBits & 0x3FFFFFFFFFFFFFFFL) | 0x8000000000000000L; // Variant bits

        return new UUID(mostSigBits, leastSigBits);
    }
}