package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderRoles {

    public final Traits admiral;
    public final Traits general;
    public final Traits scientist;
    public final Traits governor;
    public final Traits ruler;

    public LeaderRoles(PdxScriptObject o) {
        this.admiral = o.getObjectAsNullOr("admiral", Traits::new);
        this.general = o.getObjectAsNullOr("general", Traits::new);
        this.scientist = o.getObjectAsNullOr("scientist", Traits::new);
        this.governor = o.getObjectAsNullOr("governor", Traits::new);
        this.ruler = o.getObjectAsNullOr("ruler", Traits::new);
    }
}
