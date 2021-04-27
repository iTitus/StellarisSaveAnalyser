package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class AnyNeighborCountryTrigger extends TriggerBasedTrigger {

    public AnyNeighborCountryTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country
        /*
        for (Country c : scope.getNeighborCountries()) {
            if (evaluate(c, children)) {
                return true;
            }
        }
        return false;
        */
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("any_neighbor_country:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}