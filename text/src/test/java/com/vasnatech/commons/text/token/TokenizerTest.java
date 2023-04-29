package com.vasnatech.commons.text.token;

import com.vasnatech.commons.text.ReaderCharSequence;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TokenizerTest {

    @Test
    void test01() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("111{{name}}222{{title}}333");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("111", null),
                new Token<>("{{", 1),
                new Token<>("name", null),
                new Token<>("}}", 2),
                new Token<>("222", null),
                new Token<>("{{", 1),
                new Token<>("title", null),
                new Token<>("}}", 2),
                new Token<>("333", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    @Test
    void test02() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("{{name}}{{title}}{{}}");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("{{", 1),
                new Token<>("name", null),
                new Token<>("}}", 2),
                new Token<>("{{", 1),
                new Token<>("title", null),
                new Token<>("}}", 2),
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    @Test
    void test03() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("quick brown fox");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("quick brown fox", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    @Test
    void test04() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("{111{{name}}{{title}}222}");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("{111", null),
                new Token<>("{{", 1),
                new Token<>("name", null),
                new Token<>("}}", 2),
                new Token<>("{{", 1),
                new Token<>("title", null),
                new Token<>("}}", 2),
                new Token<>("222}", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    @Test
    void test05() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("111{{{name}}}{{{title}}}222");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("111", null),
                new Token<>("{{", 1),
                new Token<>("{name", null),
                new Token<>("}}", 2),
                new Token<>("}", null),
                new Token<>("{{", 1),
                new Token<>("{title", null),
                new Token<>("}}", 2),
                new Token<>("}222", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    @Test
    void test06() {
        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("{{", 1),
                new Token<>("}}", 2),
                new Token<>("{#", 3),
                new Token<>("#}", 4)
        );

        Iterator<Token<Integer>> iterator = tokenizer.tokenize("111{{name}}222{#title#}333");
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("111", null),
                new Token<>("{{", 1),
                new Token<>("name", null),
                new Token<>("}}", 2),
                new Token<>("222", null),
                new Token<>("{#", 3),
                new Token<>("title", null),
                new Token<>("#}", 4),
                new Token<>("333", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }


    @Test
    void test07() {
        StringReader reader = new StringReader("0123456789@abcdef@0123456789@abcdef@0123456789@abcdef@0123456789@abcdef");
        ReaderCharSequence sequence = new ReaderCharSequence(reader, 16);

        Tokenizer<Integer> tokenizer = new Tokenizer<>(
                new Token<>("@", 1)
        );
        Iterator<Token<Integer>> iterator = tokenizer.tokenize(sequence);
        List<Token<Integer>> actual = toList(iterator);
        List<Token<Integer>> expected = List.of(
                new Token<>("0123456789", null),
                new Token<>("@", 1),
                new Token<>("abcdef", null),
                new Token<>("@", 1),
                new Token<>("0123456789", null),
                new Token<>("@", 1),
                new Token<>("abcdef", null),
                new Token<>("@", 1),
                new Token<>("0123456789", null),
                new Token<>("@", 1),
                new Token<>("abcdef", null),
                new Token<>("@", 1),
                new Token<>("0123456789", null),
                new Token<>("@", 1),
                new Token<>("abcdef", null)
        );

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).containsExactlyElementsOf(expected);
        softly.assertAll();
    }

    static <T> List<T> toList(Iterator<T> iterator) {
        ArrayList<T> list = new ArrayList<>();
        while (iterator.hasNext())
            list.add(iterator.next());
        return list;
    }
}

