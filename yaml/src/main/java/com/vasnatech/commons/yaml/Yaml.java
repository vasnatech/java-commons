package com.vasnatech.commons.yaml;

import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.serialize.Encoder;

public final class Yaml {

    static Yaml yaml;

    public static Yaml getYaml() {
        return yaml;
    }

    public static void setYaml(Yaml yaml) {
        Yaml.yaml = yaml;
    }

    public static Encoder encoder() {
        return yaml.encoder;
    }

    public static Decoder decoder() {
        return yaml.decoder;
    }


    Encoder encoder;
    Decoder decoder;

    public Yaml(Encoder encoder, Decoder decoder) {
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
