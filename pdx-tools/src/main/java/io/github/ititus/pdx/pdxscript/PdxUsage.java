package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.SetIterable;
import org.eclipse.collections.impl.factory.Sets;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

final class PdxUsage {

    private static final SetIterable<String> EXPECTED_INT = Sets.immutable.of(INT, NEG_INT);
    private static final SetIterable<String> EXPECTED_U_INT = Sets.immutable.of(U_INT, INT);
    private static final SetIterable<String> EXPECTED_LONG = Sets.immutable.of(LONG, INT, NEG_INT, U_INT);
    private static final SetIterable<String> EXPECTED_DOUBLE = Sets.immutable.of(INT, NEG_INT, U_INT, LONG, DOUBLE);

    private final MutableSet<String> expectedTypes;
    private final MutableSet<String> actualTypes;

    PdxUsage() {
        this.expectedTypes = Sets.mutable.empty();
        this.actualTypes = Sets.mutable.empty();
    }

    static PdxUsage merge(PdxUsage a, PdxUsage b) {
        PdxUsage c = new PdxUsage();
        a.actualTypes.forEach(c::actual);
        b.actualTypes.forEach(c::actual);
        a.expectedTypes.forEach(c::expected);
        b.expectedTypes.forEach(c::expected);
        return c;
    }

    PdxUsage expected(String type) {
        expectedTypes.add(type);
        return this;
    }

    PdxUsage actual(String type) {
        actualTypes.add(type);
        return this;
    }

    boolean isError() {
        if (expectedTypes.equals(actualTypes)) {
            return false;
        } else if (expectedTypes.size() == 1) {
            String expected = expectedTypes.getOnly();
            List<String> actualNonNull = actualTypes.stream().filter(t -> !NULL.equals(t)).collect(Collectors.toList());

            if (INT.equals(expected) && actualTypes.difference(EXPECTED_INT).isEmpty()) {
                return false;
            } else if (U_INT.equals(expected) && actualTypes.difference(EXPECTED_U_INT).isEmpty()) {
                return false;
            } else if (LONG.equals(expected) && actualTypes.difference(EXPECTED_LONG).isEmpty()) {
                return false;
            } else if (DOUBLE.equals(expected) && actualTypes.contains(DOUBLE) && actualTypes.difference(EXPECTED_DOUBLE).isEmpty()) {
                return false;
            } else if (IMPLICIT_LIST.equals(expected) && !actualNonNull.isEmpty()) {
                return false;
            }

            if (actualNonNull.size() == 1) {
                String actual = actualNonNull.get(0);
                return !expected.equals(actual);
            }
        }

        return true;
    }

    boolean isObject() {
        return actualTypes.size() == 1 && OBJECT.equals(actualTypes.getOnly());
    }

    ImmutableSet<String> getActualTypes() {
        return actualTypes.toImmutable();
    }

    ImmutableSet<String> getExpectedTypes() {
        return expectedTypes.toImmutable();
    }

    @Override
    public String toString() {
        return "expected=" + expectedTypes + ", actual=" + actualTypes;
    }
}
