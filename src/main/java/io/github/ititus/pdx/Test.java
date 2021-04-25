package io.github.ititus.pdx;

import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.tuple.Pair;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Test {

    private static final Path USER_HOME = Path.of(System.getProperty("user.home"));
    private static final Path DEBUG_OUT = USER_HOME.resolve("Desktop/pdx/out.txt");
    private static final Path USER_DATA_DIR = USER_HOME.resolve("Documents/Paradox Interactive/Stellaris");
    private static final Path INSTALL_DIR = Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris");
    private static final Path SAVE = USER_HOME.resolve("Desktop/pdx/3.0.1");
    private static final Path[] TEST_FILES = { /*USER_HOME.resolve("Desktop/pdx/test.txt")*/ };

    private static StellarisGame getStellarisGame() {
        StopWatch s = StopWatch.createRunning();
        StellarisGame game = new StellarisGame(INSTALL_DIR, 0, new TestProgressMessageUpdater());
        System.out.println("Game Data Load Time: " + DurationFormatter.format(s.stop()));
        return game;
    }

    private static StellarisUserData getStellarisUserData() {
        StopWatch s = StopWatch.createRunning();
        StellarisUserData userData = new StellarisUserData(USER_DATA_DIR, 1, new TestProgressMessageUpdater());
        System.out.println("User Data Load Time: " + DurationFormatter.format(s.stop()));
        return userData;
    }

    private static StellarisSave getStellarisSave() {
        StopWatch s = StopWatch.createRunning();
        StellarisSave save = new StellarisSave(SAVE);
        System.out.println("Test Save Load Time: " + DurationFormatter.format(s.stop()));
        return save;
    }

    public static void main(String[] args) {
        if (TEST_FILES.length > 0) {
            System.out.println("Running tests:");
            IPdxScript testScript = PdxScriptParser.parse(TEST_FILES);
            System.out.println(testScript.toPdxScript());
            System.out.println("done");
            System.out.println();
        }

        StopWatch s = StopWatch.createRunning();

        StellarisGame game = /*null; //*/ getStellarisGame();
        StellarisUserData userData = null; // getStellarisUserData();
        StellarisSave save = null; // getStellarisSave();

        ImmutableList<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();
        ImmutableList<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getErrors() : null;
        ImmutableList<String> gameParseErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getRawData().getUsageStatistic().getErrorStrings() : null;
        ImmutableList<Pair<String, Throwable>> userDataErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getErrors() : null;
        ImmutableList<String> userDataParseErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getRawData().getUsageStatistic().getErrorStrings() : null;
        ImmutableList<String> saveParseErrors = save != null ? save.getErrors() : null;
        ImmutableMap<String, ImmutableMap<String, String>> missingLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;
        ImmutableMap<String, ImmutableMap<String, String>> extraLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;

        try {
            Files.createDirectories(DEBUG_OUT.getParent());
            Files.write(DEBUG_OUT, new byte[0]);
            if (unknownLiterals != null && !unknownLiterals.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Unknown Literals:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, unknownLiterals, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (gameErrors != null && !gameErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Game Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, gameErrors.collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (gameParseErrors != null && !gameParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Game Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, gameParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (userDataErrors != null && !userDataErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "User Data Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, userDataErrors.collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (userDataParseErrors != null && !userDataParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "User Data Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, userDataParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (saveParseErrors != null && !saveParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Save Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, saveParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (missingLocalisation != null && !missingLocalisation.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Missing Localisation:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, missingLocalisation.keyValuesView().collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (extraLocalisation != null && !extraLocalisation.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Extra Localisation:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, extraLocalisation.keyValuesView().collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        System.out.println("Total Loading Time: " + DurationFormatter.format(s.stop()));
        System.out.println("done");
    }

    private static final class TestProgressMessageUpdater implements StellarisSaveAnalyser.ProgressMessageUpdater {

        private final MutableList<StopWatch> stopWatches = Lists.mutable.empty();

        @Override
        public void updateProgressMessage(int index, boolean visible, int workDone, int totalWork, String msg) {
            String indent = "  ".repeat(index);
            int last = stopWatches.size() - 1;
            StopWatch current;
            if (last >= index) {
                for (; last > index; last--) {
                    stopWatches.remove(last);
                }

                current = stopWatches.get(last);
            } else if (last == index - 1) {
                current = StopWatch.create();
                stopWatches.add(current);
            } else {
                throw new IllegalArgumentException();
            }

            if (current.isRunning()) {
                System.out.println(indent + "...done (" + DurationFormatter.format(current.stop()) + ")");
            }

            System.out.println(indent + msg);
            current.start();
        }
    }
}
