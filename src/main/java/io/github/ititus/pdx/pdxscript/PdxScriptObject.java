package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.*;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.primitive.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

public final class PdxScriptObject implements IPdxScript {

    // Disabled because of mutability due to debug usage tracking
    // private static final Deduplicator<PdxScriptObject> DEDUPLICATOR = new Deduplicator<>(o -> !o.map.isEmpty());

    private final PdxRelation relation;
    private final ImmutableMap<String, IPdxScript> map;
    private final PdxUsageStatistic usageStatistic;

    private PdxScriptObject(PdxRelation relation, ImmutableMap<String, IPdxScript> map) {
        this.relation = relation;
        this.map = map;
        this.usageStatistic = new PdxUsageStatistic(map);
    }

    /*public static <S extends IPdxScript, T> Function<S, T> nullOr(Function<S, T> fct) {
        return s -> s != null && (!(s instanceof PdxScriptValue) || ((PdxScriptValue) s).getValue() != null) ? fct
        .apply(s) : null;
    }

    public static <S extends IPdxScript, T> org.eclipse.collections.api.block.function.Function<S, T> nullOr(org
    .eclipse.collections.api.block.function.Function<S, T> fct) {
        return s -> s != null && (!(s instanceof PdxScriptValue) || ((PdxScriptValue) s).getValue() != null) ? fct
        .valueOf(s) : null;
    }

    public static <S extends IPdxScript, T> Function<S, T> objectOrNull(Function<S, T> fct) {
        return s -> s instanceof PdxScriptObject ? fct.apply(s) : null;
    }*/

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    /*public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public boolean hasKey(String key, String type) {
        return map.containsKey(key) && PdxConstants.getTypeString(get(key)).equals(type);
    }*/

    private IPdxScript getRaw(String key) {
        return map.get(key);
    }

    public IPdxScript get(String key) {
        return getRaw(key);
    }

    public PdxScriptObject getObject(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, OBJECT, s);
        if (s instanceof PdxScriptObject) {
            return (PdxScriptObject) s;
        }
        return null;
    }

    public PdxScriptList getImplicitList(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, IMPLICIT_LIST, s);
        if (s instanceof PdxScriptList && ((PdxScriptList) s).getMode() == PdxScriptList.Mode.IMPLICIT) {
            return (PdxScriptList) s;
        } else if (s != null) {
            return PdxScriptList.builder().add(s).buildRaw(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
        }
        return PdxScriptList.builder().buildRaw(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
    }

    public PdxScriptList getList(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, LIST, s);
        if (s instanceof PdxScriptList) {
            return (PdxScriptList) s;
        }
        return null;
    }

    public String getString(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, STRING, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof String) {
                return (String) v;
            }
        }
        return null;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        IPdxScript s = get(key);
        usageStatistic.use(key, BOOLEAN, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Boolean) {
                return (boolean) v;
            }
        }
        return def;
    }

    public int getUnsignedInt(String key) {
        return getUnsignedInt(key, 0);
    }

    public int getUnsignedInt(String key, int def) {
        IPdxScript s = get(key);
        usageStatistic.use(key, U_INT, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    return (int) l;
                }
            }
        }
        return def;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        IPdxScript s = get(key);
        usageStatistic.use(key, INT, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            }
        }
        return def;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        IPdxScript s = get(key);
        usageStatistic.use(key, LONG, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Long || v instanceof Integer) {
                return ((Number) v).longValue();
            }
        }
        return def;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double def) {
        IPdxScript s = get(key);
        usageStatistic.use(key, DOUBLE, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Double) {
                return (double) v;
            }
        }
        return def;
    }

    public LocalDate getDate(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, DATE, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof LocalDate) {
                return (LocalDate) v;
            }
        }
        return null;
    }

    public PdxColorWrapper getColor(String key) {
        IPdxScript s = get(key);
        usageStatistic.use(key, COLOR, s);
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof PdxColorWrapper) {
                return (PdxColorWrapper) v;
            }
        }
        return null;
    }

    public <T> T getAs(Function<PdxScriptObject, T> fct) {
        return fct.apply(this);
    }

    public <V> ImmutableIntObjectMap<V> getAsIntObjectMap(Function<PdxScriptObject, V> valueFct) {
        MutableIntObjectMap<V> map = IntObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            if (!(oldV instanceof PdxScriptValue) || ((PdxScriptValue) oldV).getValue() != null) {
                if (!(oldV instanceof PdxScriptObject)) {
                    throw new RuntimeException("expected object, but got " + oldV);
                }
                map.put(Integer.parseInt(oldK), valueFct.apply((PdxScriptObject) oldV));
                usageStatistic.use(oldK, OBJECT, oldV);
            } else {
                usageStatistic.use(oldK, NULL, oldV);
            }
        });
        return map.toImmutable();
    }

    public ImmutableIntObjectMap<String> getAsIntStringMap() {
        MutableIntObjectMap<String> map = IntObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            if (!(oldV instanceof PdxScriptValue) || ((PdxScriptValue) oldV).getValue() != null) {
                if (!(oldV instanceof PdxScriptValue)) {
                    throw new RuntimeException("expected value, but got " + oldV);
                }
                Object v = ((PdxScriptValue) oldV).getValue();
                if (!(v instanceof String)) {
                    throw new RuntimeException("expected string in value, but got " + v);
                }
                map.put(Integer.parseInt(oldK), (String) v);
                usageStatistic.use(oldK, OBJECT, oldV);
            } else {
                usageStatistic.use(oldK, NULL, oldV);
            }
        });
        return map.toImmutable();
    }

    public <V> ImmutableLongObjectMap<V> getAsLongObjectMap(Function<PdxScriptObject, V> valueFct) {
        MutableLongObjectMap<V> map = LongObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            if (!(oldV instanceof PdxScriptValue) || ((PdxScriptValue) oldV).getValue() != null) {
                if (!(oldV instanceof PdxScriptObject)) {
                    throw new RuntimeException("expected object, but got " + oldV);
                }
                map.put(Long.parseLong(oldK), valueFct.apply((PdxScriptObject) oldV));
                usageStatistic.use(oldK, OBJECT, oldV);
            } else {
                usageStatistic.use(oldK, NULL, oldV);
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectBooleanMap<K> getAsObjectBooleanMap(Function<String, K> keyFct) {
        MutableObjectBooleanMap<K> map = ObjectBooleanMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (!(oldV instanceof PdxScriptValue)) {
                    throw new RuntimeException("expected value, but got " + oldV);
                }
                Object v = ((PdxScriptValue) oldV).getValue();
                if (!(v instanceof Boolean)) {
                    throw new RuntimeException("expected boolean in value, but got " + v);
                }
                map.put(k, (boolean) v);
                usageStatistic.use(oldK, BOOLEAN, oldV);
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectIntMap<K> getAsObjectIntMap(Function<String, K> keyFct) {
        MutableObjectIntMap<K> map = ObjectIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (!(oldV instanceof PdxScriptValue)) {
                    throw new RuntimeException("expected value, but got " + oldV);
                }
                Object v = ((PdxScriptValue) oldV).getValue();
                if (!(v instanceof Integer)) {
                    throw new RuntimeException("expected int in value, but got " + v);
                }
                map.put(k, (int) v);
                usageStatistic.use(oldK, INT, oldV);
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectLongMap<K> getAsObjectLongMap(Function<String, K> keyFct) {
        MutableObjectLongMap<K> map = ObjectLongMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (!(oldV instanceof PdxScriptValue)) {
                    throw new RuntimeException("expected value, but got " + oldV);
                }
                Object v = ((PdxScriptValue) oldV).getValue();
                if (!(v instanceof Long)) {
                    throw new RuntimeException("expected long in value, but got " + v);
                }
                map.put(k, (long) v);
                usageStatistic.use(oldK, OBJECT, oldV);
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectDoubleMap<K> getAsObjectDoubleMap(Function<String, K> keyFct) {
        MutableObjectDoubleMap<K> map = ObjectDoubleMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (!(oldV instanceof PdxScriptValue)) {
                    throw new RuntimeException("expected value, but got " + oldV);
                }
                Object v = ((PdxScriptValue) oldV).getValue();
                if (!(v instanceof Number)) {
                    throw new RuntimeException("expected double in value, but got " + v);
                }
                map.put(k, ((Number) v).doubleValue());
                usageStatistic.use(oldK, DOUBLE, oldV);
            }
        });
        return map.toImmutable();
    }

    public <K, V> ImmutableMap<K, V> getAsMap(Function<String, K> keyFct, Function<PdxScriptObject, V> valueFct) {
        MutableMap<K, V> map = Maps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (!(oldV instanceof PdxScriptValue) || ((PdxScriptValue) oldV).getValue() != null) {
                    if (!(oldV instanceof PdxScriptObject)) {
                        throw new RuntimeException("expected object, but got " + oldV);
                    }
                    map.put(k, valueFct.apply((PdxScriptObject) oldV));
                    usageStatistic.use(oldK, OBJECT, oldV);
                } else {
                    usageStatistic.use(oldK, NULL, oldV);
                }
            }
        });
        return map.toImmutable();
    }

    public <K, V> ImmutableMultimap<K, V> getAsMultimap(Function<String, K> keyFct,
                                                        org.eclipse.collections.api.block.function.Function<IPdxScript, V> valueFct) {
        MutableMultimap<K, V> map = Multimaps.mutable.list.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                if (oldV instanceof PdxScriptList) {
                    PdxScriptList l = (PdxScriptList) oldV;
                    map.putAll(k, l.getAsList(valueFct));
                    usageStatistic.use(oldK, l.getMode() == PdxScriptList.Mode.IMPLICIT ? IMPLICIT_LIST : LIST, oldV);
                } else {
                    map.putAll(k, PdxScriptList.builder().add(oldV).buildRaw(PdxScriptList.Mode.IMPLICIT,
                            PdxRelation.EQUALS).getAsList(valueFct));
                    usageStatistic.use(oldK, IMPLICIT_LIST, oldV);
                }
            }
        });
        return map.toImmutable();
    }

    public PdxUsageStatistic getUsageStatistic() {
        MutableMap<String, PdxUsage> usages = Maps.mutable.empty();
        usageStatistic.getUsages().forEachKeyValue((key, usage) -> usages.merge(key, usage, PdxUsage::merge));
        map.forEachKeyValue((key, s) -> {
            if (s instanceof PdxScriptObject) {
                String prefix = key.chars().allMatch(Character::isDigit) ? EMPTY : key + DOT_CHAR;
                ((PdxScriptObject) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(prefix + k, usage, PdxUsage::merge));
            } else if (s instanceof PdxScriptList) {
                String prefix = key + DOT_CHAR;
                ((PdxScriptList) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(prefix + k, usage, PdxUsage::merge));
            }
        });
        return new PdxUsageStatistic(usages);
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && key != null)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        IPdxScript.listObjectOpen(indent, root, key, b, relation, map.isEmpty());

        // TODO: Print alphabetically sorted by k
        map.forEachKeyValue((k, v) -> {
            b.append(v.toPdxScript(root ? indent : indent + 1, false, k));
            b.append(LINE_FEED);
        });

        IPdxScript.listObjectClose(indent, root, b, map.isEmpty());

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdxScriptObject)) {
            return false;
        }
        PdxScriptObject that = (PdxScriptObject) o;
        return relation == that.relation && Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, map);
    }

    @Override
    public String toString() {
        return "PdxScriptObject{" +
                "relation=" + relation +
                ", map=" + map +
                '}';
    }

    public static class Builder {

        /*private static final ImmutableMap<PdxRelation, PdxScriptObject> EMPTY_CACHE;

        static {
            Map<PdxRelation, PdxScriptObject> map = new EnumMap<>(PdxRelation.class);
            Arrays.stream(PdxRelation.values()).forEach(relation -> map.put(relation, new PdxScriptObject(relation,
            Maps.immutable.empty())));
            EMPTY_CACHE = Maps.immutable.withAll(map);
        }*/

        private final MutableMap<String, IPdxScript> map;

        public Builder() {
            this.map = Maps.mutable.empty();
        }

        public Builder add(String key, IPdxScript value) {
            String interned = key; // key.intern();
            IPdxScript object = map.get(interned);
            if (object == null) {
                map.put(interned, value);
            } else if (object.canAppend(value)) {
                map.put(interned, object.append(value));
            } else {
                throw new UnsupportedOperationException("key=" + interned + ", existing=" + object + ", appendix=" + value);
            }
            return this;
        }

        public PdxScriptObject build(PdxRelation relation) {
            /*if (map.isEmpty()) {
                return EMPTY_CACHE.get(relation);
            }*/
            return /*DEDUPLICATOR.deduplicate(*/new PdxScriptObject(relation, map.toImmutable())/*)*/;
        }
    }
}
