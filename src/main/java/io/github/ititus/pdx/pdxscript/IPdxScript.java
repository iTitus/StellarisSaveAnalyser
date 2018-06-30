package io.github.ititus.pdx.pdxscript;

public interface IPdxScript {

    static void listObjectOpen(int indent, boolean root, String key, StringBuilder b, PdxRelation relation, boolean empty) {
        if (!root) {
            b.append(PdxScriptParser.indent(indent));
            if (key != null) {
                b.append(PdxScriptParser.quoteIfNecessary(key));
                b.append(relation.getSign());
            }
            b.append('{');
            if (!empty) {
                b.append('\n');
            }
        }
    }

    static void listObjectClose(int indent, boolean root, StringBuilder b, boolean empty) {
        if (!root) {
            if (!empty) {
                b.append(PdxScriptParser.indent(indent));
            }
            b.append('}');
        } else if (!empty) {
            b.deleteCharAt(b.length() - 1);
        }
    }

    default boolean canAppend(IPdxScript script) {
        return true;
    }

    default PdxScriptList append(IPdxScript script) {
        return PdxScriptList.builder().add(this).add(script).build(true, PdxRelation.EQUALS);
    }

    default String toPdxScript() {
        return toPdxScript(0, true, null);
    }

    String toPdxScript(int indent, boolean root, String key);

    PdxRelation getRelation();
}