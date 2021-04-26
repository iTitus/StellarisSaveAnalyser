package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class AndTrigger extends TriggerBasedTrigger {

    public AndTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateAnd(scope, children);
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("AND:");
        localiseChildren(list, localisation, language, indent);
        return list.toImmutable();
    }
}
