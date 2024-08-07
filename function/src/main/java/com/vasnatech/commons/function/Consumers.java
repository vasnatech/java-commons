package com.vasnatech.commons.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Consumers {

    private Consumers() {}



    private static final Consumer<?> DO_NOTHING = first -> {};

    @SuppressWarnings("unchecked")
    public static <FIRST> Consumer<FIRST> doNothing() {
        return (Consumer<FIRST>) DO_NOTHING;
    }

    private static final BiConsumer<?, ?> DO_NOTHING_BI = (first, second) -> {};

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND> BiConsumer<FIRST, SECOND> doNothingBi() {
        return (BiConsumer<FIRST, SECOND>) DO_NOTHING_BI;
    }

    private static final TriConsumer<?, ?, ?> DO_NOTHING_TRI = (first, second, third) -> {};

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> doNothingTri() {
        return (TriConsumer<FIRST, SECOND, THIRD>) DO_NOTHING_TRI;
    }

    private static final TetraConsumer<?, ?, ?, ?> DO_NOTHING_TETRA = (first, second, third, fourth) -> {};

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> doNothingTetra() {
        return (TetraConsumer<FIRST, SECOND, THIRD, FOURTH>) DO_NOTHING_TETRA;
    }

    private static final PentaConsumer<?, ?, ?, ?, ?> DO_NOTHING_PENTA = (first, second, third, fourth, fifth) -> {};

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> doNothingPenta() {
        return (PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH>) DO_NOTHING_PENTA;
    }


    public static <FIRST> Consumer<FIRST> of(Consumer<FIRST> consumer) {
        return consumer;
    }

    public static <FIRST, SECOND> BiConsumer<FIRST, SECOND> of(Consumer<? super FIRST> firstConsumer, Consumer<? super SECOND> secondConsumer) {
        return (first, second) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
        };
    }

    public static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> of(Consumer<? super FIRST> firstConsumer, Consumer<? super SECOND> secondConsumer, Consumer<? super THIRD> thirdConsumer) {
        return (first, second, third) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
        };
    }

    public static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> of(Consumer<? super FIRST> firstConsumer, Consumer<? super SECOND> secondConsumer, Consumer<? super THIRD> thirdConsumer, Consumer<? super FOURTH> fourthConsumer) {
        return (first, second, third, fourth) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
            fourthConsumer.accept(fourth);
        };
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> of(Consumer<? super FIRST> firstConsumer, Consumer<? super SECOND> secondConsumer, Consumer<? super THIRD> thirdConsumer, Consumer<? super FOURTH> fourthConsumer, Consumer<? super FIFTH> fifthConsumer) {
        return (first, second, third, fourth, fifth) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
            fourthConsumer.accept(fourth);
            fifthConsumer.accept(fifth);
        };
    }


    public static <FIRST> CheckedConsumer<FIRST> of(CheckedConsumer<FIRST> consumer) {
        return consumer;
    }

    public static <FIRST, SECOND> CheckedBiConsumer<FIRST, SECOND> of(CheckedConsumer<? super FIRST> firstConsumer, CheckedConsumer<? super SECOND> secondConsumer) {
        return (first, second) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
        };
    }

    public static <FIRST, SECOND, THIRD> CheckedTriConsumer<FIRST, SECOND, THIRD> of(CheckedConsumer<? super FIRST> firstConsumer, CheckedConsumer<? super SECOND> secondConsumer, CheckedConsumer<? super THIRD> thirdConsumer) {
        return (first, second, third) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
        };
    }

    public static <FIRST, SECOND, THIRD, FOURTH> CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> of(CheckedConsumer<? super FIRST> firstConsumer, CheckedConsumer<? super SECOND> secondConsumer, CheckedConsumer<? super THIRD> thirdConsumer, CheckedConsumer<? super FOURTH> fourthConsumer) {
        return (first, second, third, fourth) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
            fourthConsumer.accept(fourth);
        };
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> of(CheckedConsumer<? super FIRST> firstConsumer, CheckedConsumer<? super SECOND> secondConsumer, CheckedConsumer<? super THIRD> thirdConsumer, CheckedConsumer<? super FOURTH> fourthConsumer, CheckedConsumer<? super FIFTH> fifthConsumer) {
        return (first, second, third, fourth, fifth) -> {
            firstConsumer.accept(first);
            secondConsumer.accept(second);
            thirdConsumer.accept(third);
            fourthConsumer.accept(fourth);
            fifthConsumer.accept(fifth);
        };
    }


    public static <FIRST> Consumer<FIRST> unchecked(CheckedConsumer<FIRST> checkedConsumer) {
        return checkedConsumer.unchecked();
    }

    public static <FIRST, SECOND> BiConsumer<FIRST, SECOND> unchecked(CheckedBiConsumer<FIRST, SECOND> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> unchecked(CheckedTriConsumer<FIRST, SECOND, THIRD> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked) {
        return checked.unchecked();
    }



    public static <FIRST> CheckedConsumer<FIRST> checked(Consumer<FIRST> unchecked) {
        return CheckedConsumer.checked(unchecked);
    }

    public static <FIRST, SECOND> CheckedBiConsumer<FIRST, SECOND> checked(BiConsumer<FIRST, SECOND> unchecked) {
        return CheckedBiConsumer.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD> CheckedTriConsumer<FIRST, SECOND, THIRD> checked(TriConsumer<FIRST, SECOND, THIRD> unchecked) {
        return CheckedTriConsumer.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH> CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> checked(TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return CheckedTetraConsumer.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked(PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return CheckedPentaConsumer.checked(unchecked);
    }
}
