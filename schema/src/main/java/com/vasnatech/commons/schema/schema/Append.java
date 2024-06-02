package com.vasnatech.commons.schema.schema;

public record Append(String name, Anchor anchor) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String name;
        Anchor anchor;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder anchor(Anchor anchor) {
            this.anchor = anchor;
            return this;
        }

        public Append build() {
            return new Append(name, anchor);
        }
    }
}
