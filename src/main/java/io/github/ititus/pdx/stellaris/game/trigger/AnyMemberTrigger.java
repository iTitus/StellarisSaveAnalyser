package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class AnyMemberTrigger extends TriggerBasedTrigger {

    public AnyMemberTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: federation
        /*
        for (Country c : scope.getMembers()) {
            if (evaluate(c, children)) {
                return true;
            }
        }
        return false;
        */
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of("any_member:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}
