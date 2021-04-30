package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TechStatus {

    public final Map<String, Integer> technologies;
    public final TechQueueItem physicsQueueItem;
    public final TechQueueItem societyQueueItem;
    public final TechQueueItem engineeringQueueItem;
    public final ImmutableObjectDoubleMap<String> storedTechpointsForTech;
    public final ImmutableDoubleList storedTechpoints;
    public final TechAlternatives alternatives;
    public final ImmutableObjectIntMap<String> potential;
    public final TechLeaders leaders;
    public final ImmutableList<String> alwaysAvailableTech;
    public final boolean auto_researching_physics;
    public final boolean auto_researching_society;
    public final boolean auto_researching_engineering;
    public final String lastIncreasedTech;

    public TechStatus(PdxScriptObject o) {
        this.technologies = getTechnologies(o);
        this.physicsQueueItem = getQueueItem(o, "physics_queue");
        this.societyQueueItem = getQueueItem(o, "society_queue");
        this.engineeringQueueItem = getQueueItem(o, "engineering_queue");
        this.storedTechpointsForTech = o.getObjectAsEmptyOrStringDoubleMap("stored_techpoints_for_tech");
        this.storedTechpoints = o.getListAsEmptyOrDoubleList("stored_techpoints");
        this.alternatives = o.getObjectAs("alternatives", TechAlternatives::new);
        this.potential = o.getObjectAsEmptyOrStringIntMap("potential");
        this.leaders = o.getObjectAs("leaders", TechLeaders::new, null);
        this.alwaysAvailableTech = o.getImplicitListAsStringList("always_available_tech");
        this.auto_researching_physics = o.getBoolean("auto_researching_physics");
        this.auto_researching_society = o.getBoolean("auto_researching_society");
        this.auto_researching_engineering = o.getBoolean("auto_researching_engineering");
        this.lastIncreasedTech = o.getString("last_increased_tech", null);
    }

    private static TechQueueItem getQueueItem(PdxScriptObject o, String name) {
        ImmutableList<TechQueueItem> queue = o.getListAsEmptyOrList(name, TechQueueItem::new);
        return queue.isEmpty() ? null : queue.getOnly();
    }

    private static Map<String, Integer> getTechnologies(PdxScriptObject o) {
        ImmutableList<String> technology = o.getImplicitListAsStringList("technology");
        ImmutableIntList level = o.getImplicitListAsIntList("level");
        if (technology.size() != level.size()) {
            throw new IllegalArgumentException("size mismatch between technologies and levels");
        }

        Map<String, Integer> technologies = new LinkedHashMap<>();
        for (int i = 0; i < technology.size(); i++) {
            String techName = technology.get(i);
            int techLevel = level.get(i);
            technologies.put(techName, techLevel);
        }

        return Collections.unmodifiableMap(technologies);
    }
}
