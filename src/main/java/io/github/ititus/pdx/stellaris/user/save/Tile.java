package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Tile {

    private final boolean active;
    private final int pop;
    private final String prevBuilding, blocker;
    private final ImmutableList<String> deposits;
    private final Resources resources;
    private final Building building;

    public Tile(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.active = o.getBoolean("active");
        this.pop = o.getInt("pop", -1);
        PdxScriptObject o1 = o.getObject("resources");
        this.resources = o1 != null ? o1.getAs(Resources::of) : null;
        o1 = o.getObject("building");
        this.building = o1 != null ? o1.getAs(Building::new) : null;
        this.prevBuilding = o.getString("prev_building");
        this.blocker = o.getString("blocker");
        this.deposits = o.getImplicitList("deposit").getAsStringList();
    }

    public Tile(boolean active, int pop, String prevBuilding, String blocker, ImmutableList<String> deposits, Resources resources, Building building) {
        this.active = active;
        this.pop = pop;
        this.prevBuilding = prevBuilding;
        this.blocker = blocker;
        this.deposits = deposits;
        this.resources = resources;
        this.building = building;
    }

    public boolean isActive() {
        return active;
    }

    public int getPop() {
        return pop;
    }

    public String getPrevBuilding() {
        return prevBuilding;
    }

    public String getBlocker() {
        return blocker;
    }

    public ImmutableList<String> getDeposits() {
        return deposits;
    }

    public Resources getResources() {
        return resources;
    }

    public Building getBuilding() {
        return building;
    }
}
