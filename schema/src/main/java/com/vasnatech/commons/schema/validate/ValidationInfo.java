package com.vasnatech.commons.schema.validate;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public record ValidationInfo(String path, String message) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final Deque<String> path = new LinkedList<>();
        final List<ValidationInfo> results = new ArrayList<>();

        public Builder() {
        }

        public Builder addPath(String path) {
            this.path.offer(path);
            return this;
        }

        public Builder removePath() {
            this.path.pollLast();
            return this;
        }

        public Builder message(String message) {
            results.add(new ValidationInfo(String.join(".", this.path), message));
            return this;
        }

        public List<ValidationInfo> build() {
            return results;
        }
    }
}
