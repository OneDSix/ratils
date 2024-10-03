package net.onedsix.ratils.gdx;

import com.badlogic.gdx.utils.Disposable;

import java.lang.AutoCloseable;

/** A shorthand for implementing both {@link AutoCloseable} and {@link Disposable},
 * and routing them to call the single {@link AutoDisposable#free()} method.<br>
 * Usable in both try-with-resource statements and LibGDX's ECS near seamlessly.
 * @see Disposable
 * @see Disposable#dispose()
 * @see AutoCloseable
 * @see AutoCloseable#close()
 * @see AutoDisposable#free() */
@SuppressWarnings("unused")
public interface AutoDisposable extends AutoCloseable, Disposable {
    @Override
    default void close() {
        this.free();
    }
    
    @Override
    default void dispose() {
        this.free();
    }
    
    /** Called whenever {@link AutoCloseable#close()} or {@link Disposable#dispose()} is called on this object.<br>
     * Note for GDX usage: any exceptions that occur in this method must be handled gracefully, LibGDX tends to not like it if you don't. */
    void free();
}
