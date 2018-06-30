package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Player {

    private final String name;
    private final int country;

    public Player(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.name = o.getString("name");
        this.country = o.getInt("country");
    }

    public Player(String name, int country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public int getCountry() {
        return country;
    }
}