# Ratils

**Ra**ndom U**tils** used by 1D6 and its sub projects.\
The main goal being adding smaller basic utilities, mostly porting existing features from other languages/frameworks.\
MIT licensed; feel free to use on its own in any projects you have.\
Primarily targets Java 8, but should be compatible up to the latest Java release (as of now, 22).

## Features

### Poly-Structs

The 2 classes in the `net.onedsix.ratils.poly` package are meant for storing multiple objects in one, packing them up for later.\
All of them implement the `PolyStruct` interface.

* `Duo` is used by most of the `net.onedsix.ratils.cache` package,
* `Trio` is used by `net.onedsix.ratils.trimap.TrioList`,
* `Quad` is not used internally,
* `Quinto` does not exist, but if requested can be easily added.

### `TriMap`s

`TriMap`s, `TriArray`s, and `TriList`s allow for multiple layer data structures, similar to JSON or TOML files.\
Their one downside is memory and computation inefficiencies.

`TriMap`s are internally a `Map<D, Map<K, V>>`, meaning their lookup speed is pretty good, but memory usage is a serious
concern for large datasets.

`TriArray`s are internally `D[]; K[]; V[];`, meaning their lookup speed is atrocious, but memory usage isn't too bad and
can be managed manually if needed.

`TriList`s are internally `List<D>; List<K>; List<V>;`, meaning they're a fair middle ground for most use cases, and can
maybe be slightly more memory efficient by using `LinkedTriList` specifically.

All of these classes implement the base `TriMap<D,K,V>` interface, containing all of the common methods between them.
As such, all of these different backends are interchangeable on demand.

Here are some example uses:
```java
import net.onedsix.ratils.trimap.*;
import java.util.Locale;

public class TriTest {
    // Maybe for translations?
    // Fun Fact: this is what TriMap was originally made for
    public final TriMap<Locale, String, String> tri = new HashTriMap(Locale.getDefault());
    
    // Timestamped events in an ECS?
    public LinkedTriList<Date, ? extends MyEvent, ? extends MyEntity> timestamped = null;
    
    // An entire Json file in a single object? Just use Gson instead...
    public static TriMap<Object, Object, Object> json = new LinkedTriList(null);
}
```

If you plan on letting any dev-facing parts use a `TriStruct` or any of its extensions, consider using the base
`TriStruct` interface, as the dev can then choose what extension is best in their specific scenario.

### `Result`s

A Java port of [Rust's `Result` syntax](https://doc.rust-lang.org/std/result/enum.Result.html), allowing for better returns and error handling.

```java
import net.onedsix.ratils.Result;
import java.io.*;

public class ResultTest {
    
    public static void main(String[] args) {
        Result<String, Throwable> readRes = readResult(new File("./test.txt"));
        
        // Check if the result is an error
        boolean containsError = readRes.errored();
        
        // Lambda-based port of Rust's switch statement
        readRes.match(
            ok -> {
                System.out.println("Contents of ./test.txt:");
                System.out.println(ok);
            },
            err -> {
                System.out.println("Errored while reading:");
                System.out.println(err.getMessage());
            }
        );
    }
    
    public static Result<String, IOException> readResult(File file) {
        try {
            // Reading a file line-by-line,
            // might throw a IOException
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) content.append(line).append("\n");
            br.close();
            
            // Return an OK!
            return Result.Ok(content.toString());
        } catch (IOException e) {
            // On error, return ERR!
            return Result.Err(e);
            // Note that Err doesn't require a Throwable after 1.0.1
        }
    }

}
```

### Caches

This package is based off [klinbee's SimpleCache implementation](https://github.com/klinbee/Bad-Apple-World-Preset/blob/main/common/src/main/java/com/klinbee/badapple/SimpleCache.java).



## Installing

```kotlin
repositories {
    // You probably have these already
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Ratils by itself
    implementaion("com.github.OneDSix:ratils:main-SNAPSHOT")
    
    // Optional Dependencies:
    // LibGDX (for the "net.onedsix.ratils.gdx" package
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
}
```

## Changelog

### TODO

* All the documentation
* Finish the `TriList` and `ConcurentTriMap` implementations of `TriMap`

### 1.0.3

Revert the `error instanceof Boolean` check in `Result` as it was causing compilation errors.\
This will be added back in later.

### 1.0.2

* Fixed an issue when using `boolean` as a `Result`'s error type
* Added `SwitchBuilder`
  * Ports Java 14 and 18's enhanced switches to Java 8, heavily relying on lambdas, with an optional return type like Rust's match statement
  * See [`SwitchTest`](./src/test/java/net/onedsix/ratils/tests/SwitchTest.java) for an example

### 1.0.1

* Reorganized quite a few classes:
  * All package begin with `net.onedsix.ratils`.
  * `Result` was moved to `result` package
    * A wrapper for common `io` and `net` methods returning `Result`s is planned for this package
  * All `TriMap` extension are now in `trimap.hash`, `trimap.list`, and `trimap.thread` as to organize it a bit
  * All classes in the `poly` package now extend `PolyStruct`
* Added the `cache` package, containing utilities for temporarily containing objects
* Moved `AutoDisposable` from 1D6 to Ratils, nothing else has changed with it besides some documentation and the package path
* `Result`'s `<E>` generic no longer extends `Throwable`
* Small ReadMe and documentation updates

### 1.0.0

**Initial Release**

* Added Result, Trimap, and Poly features
* Most of Trimap is non-functional