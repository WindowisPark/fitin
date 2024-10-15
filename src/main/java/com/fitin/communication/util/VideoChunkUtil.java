package com.fitin.communication.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VideoChunkUtil {
    private static final int DEFAULT_CHUNK_SIZE = 1024 * 1024; // 1MB

    public static List<byte[]> splitVideoIntoChunks(byte[] videoData) {
        return splitVideoIntoChunks(videoData, DEFAULT_CHUNK_SIZE);
    }

    public static List<byte[]> splitVideoIntoChunks(byte[] videoData, int chunkSize) {
        List<byte[]> chunks = new ArrayList<>();
        for (int i = 0; i < videoData.length; i += chunkSize) {
            int length = Math.min(chunkSize, videoData.length - i);
            byte[] chunk = new byte[length];
            System.arraycopy(videoData, i, chunk, 0, length);
            chunks.add(chunk);
        }
        return chunks;
    }

    public static byte[] mergeChunks(List<byte[]> chunks) {
        int totalSize = chunks.stream().mapToInt(chunk -> chunk.length).sum();
        byte[] mergedData = new byte[totalSize];
        int currentPosition = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, mergedData, currentPosition, chunk.length);
            currentPosition += chunk.length;
        }
        return mergedData;
    }

    public static void saveVideoToFile(byte[] videoData, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, videoData);
    }

    public static byte[] readVideoFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}