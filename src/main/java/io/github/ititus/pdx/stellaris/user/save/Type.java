package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Type {

    public final String type;

    public Type(PdxScriptObject o) {
        this.type = o.getString("type");
    }
}
