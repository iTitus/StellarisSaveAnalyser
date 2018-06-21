package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Traits {

    private final List<String> traits;

    public Traits(PdxScriptObject o) {
        PdxScriptList l = o.getList("trait");
        this.traits = l != null ? l.getAsStringList() : new ArrayList<>(Collections.singleton(o.getString("trait")));
    }

    public Traits(Collection<String> traits) {
        this.traits = new ArrayList<>(traits);
    }

    public List<String> getTraits() {
        return Collections.unmodifiableList(traits);
    }
}
