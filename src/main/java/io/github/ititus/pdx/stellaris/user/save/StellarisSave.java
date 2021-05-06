package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.util.io.FileNameFilter;
import io.github.ititus.pdx.util.IOUtil;
import io.github.ititus.pdx.util.io.PathFilter;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DTF;

public class StellarisSave {

    private static final String META = "meta";
    private static final String GAMESTATE = "gamestate";

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of();
    private static final PathFilter FILTER = FileNameFilter.ofNames(META, GAMESTATE);
    public final Meta meta;
    public final GameState gameState;
    private final Path save;
    private final PdxRawDataLoader saveDataLoader;

    public StellarisSave(Path saveFile) {
        if (!isValidSaveFileOrDir(saveFile)) {
            throw new IllegalArgumentException();
        }

        this.save = saveFile;
        System.out.println("Loading Save " + saveFile);

        StopWatch s = StopWatch.createRunning();
        this.saveDataLoader = new PdxRawDataLoader(saveFile, BLACKLIST, FILTER);
        System.out.println("Parsing: " + DurationFormatter.format(s.stop()));

        s.start();
        this.meta = this.saveDataLoader.getRawData().getObjectAs(META, Meta::new);
        System.out.println("Meta: " + DurationFormatter.format(s.stop()));

        s.start();
        this.gameState = this.saveDataLoader.getRawData().getObjectAs(GAMESTATE, GameState::new);
        System.out.println("Gamestate: " + DurationFormatter.format(s.stop()));
    }

    public static StellarisSave loadLastModified(Path saveDir) {
        if (!Files.isDirectory(saveDir)) {
            throw new IllegalArgumentException();
        }

        Optional<Path> latest;
        try (Stream<Path> stream = Files.list(saveDir)) {
            latest = stream
                    .filter(StellarisSave::isValidSaveFile)
                    .max(Comparator.comparing(p -> {
                        try {
                            return Files.getLastModifiedTime(p);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return latest.map(StellarisSave::new).orElseThrow();
    }

    public static StellarisSave loadNewest(Path saveDir) {
        if (!Files.isDirectory(saveDir)) {
            throw new IllegalArgumentException();
        }

        Optional<Path> latest;
        try (Stream<Path> stream = Files.list(saveDir)) {
            latest = stream
                    .filter(StellarisSave::isValidSaveFile)
                    .max(Comparator.comparing(p -> LocalDate.parse(IOUtil.getNameWithoutExtension(p), DTF)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return latest.map(StellarisSave::new).orElseThrow();
    }

    public static boolean isValidSaveFile(Path saveFile) {
        return saveFile != null && Files.isRegularFile(saveFile) && IOUtil.getExtension(saveFile).orElseThrow().equals("sav");
    }

    public static boolean isValidSaveFileOrDir(Path saveFile) {
        return saveFile != null && (Files.isDirectory(saveFile) || isValidSaveFile(saveFile));
    }

    public PdxRawDataLoader getSaveDataLoader() {
        return saveDataLoader;
    }

    public Path getSave() {
        return save;
    }

    public ImmutableList<String> getErrors() {
        return saveDataLoader.getRawData().getUsageStatistic().getErrorStrings();
    }
}
