package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class CountStarbaseSizesTrigger extends Trigger {

    public final String starbaseSize;
    public final PdxRelation relation;
    public final int count;

    public CountStarbaseSizesTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.starbaseSize = o.getString("starbase_size");
        PdxScriptValue v = o.get("count").expectValue();
        this.relation = v.getRelation();
        this.count = v.expectInt();
    }

    @Override
    public boolean evaluate(Scope scope) {
        return relation.compare(CountryScope.expect(scope).getStarbaseCount(starbaseSize), count);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("count_starbase_sizes" + relation.getSign() + count + " with size " + starbaseSize);
    }
}
