package com.vasnatech.commons.properties;

public final class Properties {

    static Properties properties;

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        Properties.properties = properties;
    }

    public static Encoder encoder() {
        return properties.encoder;
    }

    public static Decoder decoder() {
        return properties.decoder;
    }


    Encoder encoder;
    Decoder decoder;

    public Properties(Encoder encoder, Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public Decoder getDecoder() {
        return decoder;
    }
}
