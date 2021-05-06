package io.github.ititus.pdx.util.mutable;

import java.util.Objects;

public final class MutableString {

    private String s;

    public MutableString() {
        this(null);
    }

    public MutableString(String s) {
        this.s = s;
    }

    public String get() {
        return s;
    }

    public MutableString set(String s) {
        this.s = s;
        return this;
    }

    public boolean isNull() {
        return s == null;
    }

    public boolean isNotNull() {
        return s != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof MutableString)) {
            return false;
        }

        MutableString that = (MutableString) o;
        return Objects.equals(s, that.s);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(s);
    }

    @Override
    public String toString() {
        return Objects.toString(s);
    }
}
