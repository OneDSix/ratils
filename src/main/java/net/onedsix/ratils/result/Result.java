package net.onedsix.ratils.result;

import lombok.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/** A port of Rust's Result syntax to Java.<br>
 * <br>
 * Allows for methods to return either a value, referred to as {@link V} or {@link Result#value};<br>
 * or a second value, usually a {@link Throwable} (and thus {@link Exception} and {@link Error}), referred to as {@link E} or {@link Result#error}.<br>
 * Note that {@link E} doesn't require a {@link Throwable} after {@code Ratils 1.0.1}.<br>
 * <br>
 * This aims to help with error handling, and help make developers decide how they want to handle errors. The
 * {@link Result#match(Consumer, Consumer)} and {@link Result#match(Function, Function)} methods will separate out each path,
 * helping with the separation of concerns.<br>
 * <br>
 * The {@link Result#error} value is always preferred in {@link Result#getResult()}, {@link Result#match(Consumer, Consumer)},
 * and {@link Result#match(Function, Function)}. Even if there is a value present in the {@link Result#value} field, the error path is always chosen.
 * This should not affect normal usage of this class, but its something to be aware of.<br>
 * <br>
 * Below is an example usage of this class:<br>
 * <pre>{@code
 * import net.onedsix.ratils.Result;
 * import java.io.*;
 *
 * // Optional import for OK and ERR shorthands
 * //import static net.onedsix.ratils.Result.*;
 *
 * public class ResultTest {
 *
 *     public static void main(String[] args) {
 *         Result<String, Throwable> readRes = readResult(new File("./test.txt"));
 *
 *         // Check if the result is an error
 *         boolean containsError = readRes.errored();
 *
 *         // Lambda-based port of Rust's switch statement
 *         readRes.match(
 *             ok -> {
 *                 System.out.println("Contents of ./test.txt:");
 *                 System.out.println(ok);
 *             },
 *             err -> {
 *                 System.out.println("Errored while reading:");
 *                 System.out.println(err.getMessage());
 *             }
 *         );
 *     }
 *
 *     public static Result<String, IOException> readResult(File file) {
 *         try {
 *             // Reading a file line-by-line, might throw a IOException
 *             BufferedReader br = new BufferedReader(new FileReader(file));
 *             StringBuilder content = new StringBuilder();
 *             String line;
 *             while ((line = br.readLine()) != null)
 *                  content.append(line).append("\n");
 *             br.close();
 *
 *             // Return an OK!
 *             return Result.Ok(content.toString());
 *         } catch (IOException e) {
 *             // On error, return ERR!
 *             return Result.Err(e);
 *         }
 *     }
 *
 * }
 * }</pre> */
@EqualsAndHashCode
@ToString
@Getter @Setter
@SuppressWarnings("unused")
public class Result<V, E> {
    public @Nullable V value;
    public @Nullable E error;
    
    /** Allows usage of Result without having to use {@link Result#Ok(V)} or {@link Result#Err(E)}<br>
     * Using Ok and Err is still preferred, but this exists for cases that don't belong in either of those camps.<br>
     * @see Result#Ok(V)
     * @see Result#Err(E)
     * */
    @ApiStatus.Internal
    public Result(@Nullable V value, @Nullable E error) {
        this.value = value;
        this.error = error;
    }
    
    /** Returns an "OK!" Result
     * @return {@link Result#error} will always be {@code null}; {@link Result#value} will be a valid object.
     * @see Result#Err(E)  */
    public static <V, E> Result<V, E> Ok(@NotNull V ok) {
        return new Result<>(ok, null);
    }
    
    /** Results an "ERR!" Result
     * @return {@link Result#value} will always be {@code null}; {@link Result#error} is a {@link Throwable}, {@link Exception}, or {@link Error}
     * @see Result#Ok(V ok) */
    public static <V, E> Result<V, E> Err(@NotNull E bad) {
        return new Result<>(null, bad);
    }
    
    public boolean errored() {
        return error != null;
    }
    
    public Object getResult() {
        return errored() ? error : value;
    }
    
    /**
     *
     * @see Result#match(Function, Function)  */
    public void match(Consumer<V> okFork, Consumer<E> errFork) {
        if (errored()) errFork.accept(error);
        else okFork.accept(value);
    }
    
    /**
     *
     * @see Result#match(Consumer, Consumer) */
    public <R> R match(Function<V, R> okFork, Function<E, R> errFork) {
        if (errored()) return errFork.apply(error);
        else return okFork.apply(value);
    }
}
