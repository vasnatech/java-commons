package com.vasnatech.commons.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ReaderCharSequence implements CharSequence {

    final Reader reader;

    int offset;
    char[] leftBuffer;
    int leftLength;
    char[] centerBuffer;
    int centerLength;
    char[] rightBuffer;
    int rightLength;

    public ReaderCharSequence(InputStream in, int bufferSize) {
        this(new InputStreamReader(in, StandardCharsets.UTF_8), bufferSize);
    }

    public ReaderCharSequence(Reader reader, int bufferSize) {
        this.reader = reader;
        this.leftBuffer = new char[bufferSize];
        this.centerBuffer = new char[bufferSize];
        this.rightBuffer = new char[bufferSize];

        init();
    }

    private void init() {
        offset = 0;
        leftLength = 0;
        centerLength = read(centerBuffer);
        if (centerLength >= 0) {
            rightLength = read(rightBuffer);
        } else {
            centerLength = 0;
            rightLength = -1;
        }
    }

    private void shiftRight() {
        char[] bufferTemp = leftBuffer;
        offset += leftLength;
        leftBuffer = centerBuffer;
        leftLength = centerLength;
        centerBuffer = rightBuffer;
        centerLength = rightLength;
        rightBuffer = bufferTemp;
        rightLength = read(rightBuffer);
    }

    private int read(char[] buffer) {
        try {
            return reader.read(buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int length() {
        return offset + leftLength + centerLength;
    }

    private boolean reachedEnd() {
        return rightLength < 0;
    }

    @Override
    public char charAt(int index) {
        while (index >= length()) {
            if (reachedEnd()) {
                throw new IndexOutOfBoundsException("index: " + index + ", offset: " + offset + ", length: " + length());
            }
            shiftRight();
        }
        int normalizedIndex = index - offset;
        if (normalizedIndex < 0) {
            throw new IndexOutOfBoundsException("Minimum index supported is " + offset + " at this point.");
        }
        if (normalizedIndex < leftLength) {
            return leftBuffer[normalizedIndex];
        }
        return centerBuffer[normalizedIndex - leftLength];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int extendedLength = reachedEnd() ? length() : length() + rightLength;

        if (start < offset || start >= extendedLength) {
            throw new IndexOutOfBoundsException("index: " + start + ", offset: " + offset + ", length: " + extendedLength);
        }
        if (end < offset || end > extendedLength) {
            throw new IndexOutOfBoundsException("index: " + end + ", offset: " + offset + ", length: " + extendedLength);
        }
        if (start > end) {
            throw new IndexOutOfBoundsException("start: " + start + ", end: " + end + ", offset: " + offset + ", length: " + extendedLength);
        }

        int normalizedStart = start - offset;
        int normalizedEnd = end - offset;
        if (normalizedStart < leftLength) {
            if (normalizedEnd < leftLength) {
                return new String(leftBuffer, normalizedStart, normalizedEnd - normalizedStart);
            } else {
                if (normalizedEnd < leftLength + rightLength) {
                    return  new String(leftBuffer, normalizedStart, leftLength - normalizedStart) +
                            new String(centerBuffer, 0, normalizedEnd - leftLength);

                } else {
                    return  new String(leftBuffer, normalizedStart, leftLength - normalizedStart) +
                            new String(centerBuffer) +
                            new String(rightBuffer, 0, normalizedEnd - leftLength - centerLength);
                }
            }
        }

        normalizedStart -= leftLength;
        normalizedEnd -= leftLength;
        if (normalizedStart < centerLength) {
            if (normalizedEnd < centerLength) {
                return new String(centerBuffer, normalizedStart, normalizedEnd - normalizedStart);
            } else {
                return  new String(centerBuffer, normalizedStart, centerLength - normalizedStart) +
                        new String(rightBuffer, 0, normalizedEnd - centerLength);
            }
        }

        normalizedStart -= centerLength;
        normalizedEnd -= centerLength;
        return new String(rightBuffer, normalizedStart, normalizedEnd - normalizedStart);
    }
}
