package com.vasnatech.commons.json;

import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.serialize.Encoder;

public final class Json {

    static Json json;

    public static Json getJson() {
        return json;
    }

    public static void setJson(Json json) {
        Json.json = json;
    }

    public static Encoder encoder() {
        return json.encoder;
    }

    public static Decoder decoder() {
        return json.decoder;
    }


    Encoder encoder;
    Decoder decoder;

    public Json(Encoder encoder, Decoder decoder) {
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
