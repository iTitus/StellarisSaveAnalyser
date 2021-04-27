package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class ScopeTrigger extends TriggerBasedTrigger {

    public final String name;

    private ScopeTrigger(Triggers triggers, IPdxScript s, String name) {
        super(triggers, s);
        this.name = name;
    }

    public static TriggerFactory factory(String name) {
        return (triggers, s) -> new ScopeTrigger(triggers, s, name);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // switch scope to given scope and evaluate children
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("in scope " + name + ":");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}