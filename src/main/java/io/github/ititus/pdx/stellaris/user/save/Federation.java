package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

import java.time.LocalDate;

public class Federation {

    public final String name;
    public final ImmutableIntList members;
    public final ImmutableIntList associates;
    public final LocalDate startDate;
    public final ImmutableIntList shipDesigns;
    public final int leader;

    public Federation(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.members = o.getListAsIntList("members");
        this.associates = o.getListAsEmptyOrIntList("associates");
        this.startDate = o.getDate("start_date");
        this.shipDesigns = o.getListAsIntList("ship_design");
        this.leader = o.getInt("leader");
    }
}