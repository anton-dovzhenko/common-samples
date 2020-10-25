package com.gd.snippet.externalsort;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anton on 17/4/17.
 */
public class ExternalIntSort {

    private final File input;
    private final int maxInMemNumbers;

    private final List<File> chunks = new ArrayList<>();

    public ExternalIntSort(File input, int maxInMemNumbers) {
        this.input = input;
        this.maxInMemNumbers = maxInMemNumbers;
    }

    public File sort() throws IOException {
        createSortedChunks();
        File output = mergeSortedChunks();
        deleteChunks();
        return output;
    }

    private void createSortedChunks() throws IOException {
        try (FileInputStream fis = new FileInputStream(input)
             ; BufferedInputStream bis = new BufferedInputStream(fis)
             ; DataInputStream dis = new DataInputStream(bis)) {
            int[] array = new int[maxInMemNumbers];
            int count = 0;
            while (dis.available() > 0) {
                int i = dis.readInt();
                array[count] = i;
                if (++count == maxInMemNumbers) {
                    sortAndWriteToFile(array, count);
                    count = 0;
                }
            }
            if (count > 0) {
                sortAndWriteToFile(array, count);
            }
        }
    }

    private File mergeSortedChunks() throws IOException {
        File output = new File("sorted_" + input.getName());
        int chunkCount = chunks.size();
        DataInputStream[] streams = new DataInputStream[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            streams[i] = new DataInputStream(new BufferedInputStream(new FileInputStream(chunks.get(i))));
        }
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));

        int[] cache = new int[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            cache[i] = streams[i].readInt();
        }
        while (true) {
            int minIndex = -1;
            int minVal = Integer.MAX_VALUE;
            for (int i = 0; i < chunkCount; i++) {
                if (cache[i] != Integer.MAX_VALUE && cache[i] < minVal) {
                    minVal = cache[i];
                    minIndex = i;
                }
            }
            if (minVal == Integer.MAX_VALUE) {
                break;
            } else {
                dos.writeInt(minVal);
                cache[minIndex] = streams[minIndex].available() > 0 ? streams[minIndex].readInt() : Integer.MAX_VALUE;
            }
        }

        return output;
    }

    private void sortAndWriteToFile(int[] array, int count) throws IOException {
        Arrays.sort(array, 0, count);
        File chunk = writeToFile("_chunk_" + chunks.size() + "_" + input.getName(), array, count);
        chunks.add(chunk);
    }

    private File writeToFile(String filename, int[] array, int count) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)
             ; BufferedOutputStream bos = new BufferedOutputStream(fos)
             ; DataOutputStream dos = new DataOutputStream(bos)) {
            for (int i = 0; i < count; i++) {
                dos.writeInt(array[i]);
            }
        }
        return new File(filename);
    }

    private void deleteChunks() {
        for (File chunk : chunks) {
            boolean deleted = chunk.delete();
            if (!deleted) {
                System.out.println(String.format("Chunk %s can not be deleted", chunk.getAbsolutePath()));
            }
        }
        chunks.clear();
    }

}
