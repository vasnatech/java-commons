package com.vasnatech.commons.text;

import com.vasnatech.commons.collection.Maps;
import org.apache.commons.text.StringSubstitutor;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class ParametrizedStringTest {

    @Test
    void test01() {
        String template = "111{{fatih}}222{{$$$}}(Guven)333{{fatih2}}444(Guven2)555";
        String expected = "111FATIH222%%%(Guven)333FATIH3444(Guven2)555";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString("fatih", "FATIH", "$$$", "%%%", "fatih2", "FATIH3");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void test02() {
        String template = "1{11{{fatih}}{{$$$}}(Gu}v}en)333{{fatih2}}{444(Guven2)555{}{}";
        String expected = "1{11FATIH%%%(Gu}v}en)333FATIH3{444(Guven2)555{}{}";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString("fatih", "FATIH", "$$$", "%%%", "fatih2", "FATIH3");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void test03() {
        String template = "111${fatih}222${$$$}(Guven)333${fatih2}}444(Guven2)555{{}}{}{}";
        String expected = "111FATIH222%%%(Guven)333FATIH3}444(Guven2)555{{}}{}{}";
        ParametrizedString parametrizedString = new ParametrizedString(template, "${", "}");
        String actual = parametrizedString.toString("fatih", "FATIH", "$$$", "%%%", "fatih2", "FATIH3");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void empty_parameter_name() {
        String template = "111{{}}555";
        String expected = "111{{}}555";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void un_closed_parameter() {
        String template = "111{{name";
        String expected = "111{{name";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void un_closed_parameter2() {
        String template = "111{{";
        String expected = "111{{";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

    @Test
    void unexpected_closed_parameter() {
        String template = "111}}222{{name}}";
        String expected = "111}}222XyZ";
        ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
        String actual = parametrizedString.toString("name", "XyZ");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).isEqualTo(expected);
        softly.assertAll();
    }

        //@Test
    void compare() throws Exception {
        int iterationCount = 10;


        Map<String, ?> parameters = Maps.of(
                "fatih", "FATIH",
                "$$$", "%%%",
                "fatih2", "FATIH3",
                "aaa", "aaa",
                "bbb", "bbb",
                "ccc", "ccc",
                "ddd", "ddd",
                "eee", "eee",
                "fff", "fff",
                "ggg", "ggg",
                "hhh", "hhh",
                "iii", "iii",
                "jjj", "jjj",
                "kkk", "kkk",
                "lll", "lll",
                "mmm", "mmm",
                "nnn", "nnn",
                "ooo", "ooo",
                "ppp", "ppp",
                "qqq", "qqq"
        );

        AtomicLong parametrizedMillis = new AtomicLong();
        AtomicLong replaceMillis = new AtomicLong();
        AtomicLong substitutorMillis = new AtomicLong();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for(int ite=0; ite<iterationCount; ++ite) {
            int tryCount = 100000;
            String temp = "111{{fatih}}222{{$$$}}(Guven)333{{fatih2}}444(Guven2)555";
            for (int index=0; index<5; ++index)
                temp += temp;
            String template = temp;
            System.gc();

            Runnable parametrizedRunnable = () -> {
                long begin = System.currentTimeMillis();
                ParametrizedString parametrizedString = new ParametrizedString(template, "{{", "}}");
                for (int index=0; index<tryCount; ++index)
                    parametrizedString.toString(parameters);
                long end = System.currentTimeMillis();
                parametrizedMillis.addAndGet((end - begin));
            };

            Runnable replaceRunnable = () -> {
                long begin = System.currentTimeMillis();
                for (int index=0; index<tryCount; ++index) {
                    String worker = template;
                    for (Entry<String, ?> entry : parameters.entrySet()) {
                        worker = worker.replace("{{" + entry.getKey() + "}}", (String)entry.getValue());
                    }
                }
                long end = System.currentTimeMillis();
                replaceMillis.addAndGet((end - begin));
            };

            Runnable substitutorRunnable = () -> {
                long begin = System.currentTimeMillis();
                StringSubstitutor substitutor = new StringSubstitutor(parameters, "{{", "}}");
                for (int index=0; index<tryCount; ++index)
                    substitutor.replace(template);
                long end = System.currentTimeMillis();
                substitutorMillis.addAndGet((end - begin));
            };

            executorService.invokeAll(
                    List.of(
                            Executors.callable(replaceRunnable),
                            Executors.callable(substitutorRunnable),
                            Executors.callable(parametrizedRunnable)
                    ),
                    1,
                    TimeUnit.MINUTES
            );
            System.out.println("replace: " + replaceMillis + "  substitutor: " + substitutorMillis + "  parametrized: " + parametrizedMillis);
        }

    }
}