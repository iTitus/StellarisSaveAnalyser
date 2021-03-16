package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TechLeaders {

    public final int physics;
    public final int society;
    public final int engineering;

    public TechLeaders(PdxScriptObject o) {
        this.physics = o.getInt("physics", -1);
        this.society = o.getInt("society", -1);
        this.engineering = o.getInt("engineering", -1);
    }
}
