package com.example.helloworld;

import android.content.Context;
import android.provider.Settings;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class DeviceInfoUtils {

    public DeviceInfoUtils() {

    }

    public String generateUniqueIdentifier(Context context) {
        // Get the Android ID
        String androidId = getAndroidId(context);

        // Generate random bytes
        byte[] randomBytes = generateRandomBytes();

        // Combine Android ID and random bytes
        byte[] combinedBytes = combineByteArrays(androidId.getBytes(), randomBytes);

        // Convert to a UUID (or any other format you prefer)
        UUID uuid = bytesToUUID(combinedBytes);

        return uuid.toString();
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static byte[] generateRandomBytes() {
        byte[] randomBytes = new byte[16]; // You can adjust the length as needed
        new SecureRandom().nextBytes(randomBytes);
        return randomBytes;
    }

    public static byte[] combineByteArrays(byte[] array1, byte[] array2) {
        byte[] combined = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, combined, 0, array1.length);
        System.arraycopy(array2, 0, combined, array1.length, array2.length);
        return combined;
    }

    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}

