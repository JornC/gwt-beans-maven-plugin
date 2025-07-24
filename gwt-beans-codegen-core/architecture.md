# Architecture

## Overview

The *gwt-beans code-generation* module produces compile-time JSON parsers for ordinary Java beans.  Starting from one or more **root types** supplied by the Maven plugin, the generator performs a full graph traversal, discovers every reachable user-defined class or enum and emits a dedicated `*Parser` class for each of them.  All work happens **at build time** – no reflection or byte-code tricks are required at runtime, ensuring zero overhead and full GWT compatibility.

## Main components

| # | Component | Responsibility |
|---|-----------|----------------|
| 1 | **ParserGenerator** | Entry point; wires the analyser, validator and writers together and kicks off generation. |
| 2 | **TypeAnalyzer** | Performs recursive inspection of the Java types, keeps track of visited classes, resolves generics and reports everything that must receive a parser. |
| 3 | **ConfigurationValidator** | Validates user configuration and verifies that every encountered type is actually supported; throws a descriptive `UnsupportedTypeException` otherwise. |
| 4 | **FieldParsers** | A family of small helpers that know how to generate source code for a single *kind* of field.  Current implementations:<br/>• `SimpleFieldParser` – primitives & boxed types<br/>• `EnumFieldParser`<br/>• `PrimitiveArrayFieldParser`<br/>• `CollectionFieldParser`<br/>• `MapFieldParser`<br/>• `CustomObjectFieldParser` |
| 5 | **ParserWriter** | Assembles snippets coming from the FieldParsers into a complete, compilable Java source file for each analysed type. |
| 6 | **ParserWriterUtils** & **util.FileUtils** | Low-level utilities for indentation handling, file I/O and common code fragments. |

## Generated parser skeleton

```java
@Generated("nl.aerius.codegen.ParserGenerator")
public final class TypeNameParser {
  public static TypeName parse(final JSONObjectHandle obj) {
    if (obj == null) {
      return null;
    }

    final TypeName instance = new TypeName();
    // field parsing logic inserted here
    return instance;
  }
}
```

The exact body depends on the field composition but always follows the same, predictable template which simplifies debugging and diff-based regression testing.

## Data flow

1. **Input collection** – Maven plugin collects the configured root types.
2. **Type analysis** – `TypeAnalyzer` walks the object graph and records every encountered type.
3. **Validation** – `ConfigurationValidator` ensures all types and options are supported.
4. **Code generation** – For each type, the appropriate `FieldParser` instances create field-level code that `ParserWriter` stitches together into a class.
5. **File output** – `FileUtils` writes the resulting source into the configured target directory.

## Extension points

● **Custom parsers** – annotate a user-provided class with `@CustomParser`; if present the generator will *reuse* it instead of generating a duplicate.

● **Polymorphic hierarchies** – every concrete subclass receives its own parser; the caller decides which parser to invoke.

## Design decisions

1. **Static, stateless parse methods** – keep memory footprint minimal and avoid allocation of helper objects.
2. **Null-safety** – every generated parser returns `null` when the JSON object itself is `null`.
3. **Deterministic output** – sorting of classes and fields guarantees stable builds and minimal git diffs.
4. **Fail-fast validation** – unsupported types are detected during the *analysis* phase, not when running generated code.
5. **Strict dependency segregation** – the generator depends only on the *handle* layer (`JSONObjectHandle`, `JSONArrayHandle`) making the output usable in both server and GWT environments.

## Currently unsupported (by design)

• `Optional<T>` and the java.time API  
• `BigDecimal`, `BigInteger`  
• `UUID`, `Date`, `Calendar`  
• Queue/Deque and all sorted collections  
• Maps with non-String keys **except** enums