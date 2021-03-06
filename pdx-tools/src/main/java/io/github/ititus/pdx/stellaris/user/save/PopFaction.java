package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;

public class PopFaction {

    public final int id;
    public final int country;
    public final String type;
    public final String name;
    public final int leader;
    public final ImmutableList<Parameter> parameters;
    public final double support;
    public final double factionApproval;
    public final ImmutableList<TimedModifier> timedModifiers;
    public final ImmutableIntList members;
    public final ImmutableMap<String, FlagData> flags;

    public PopFaction(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.country = o.getInt("country");
        this.type = o.getString("type");
        this.name = o.getString("name");
        this.leader = o.getInt("leader", -1);
        this.parameters = o.getListAsList("parameters", Parameter::new);
        this.support = o.getDouble("support", 0);
        this.factionApproval = o.getDouble("faction_approval", 0);
        this.timedModifiers = o.getImplicitListAsList("timed_modifier", TimedModifier::new);
        this.members = o.getListAsIntList("members");
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
    }

    public boolean hasModifier(String name) {
        return timedModifiers.anySatisfy(m -> name.equals(m.modifier));
    }
}
