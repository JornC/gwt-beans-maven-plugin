# Development Plan

## Purpose

Generate fast, type-safe JSON parsers that eliminate the need for GWT-RPC serialization while keeping full compatibility with the original client runtime.

## Current Status ✓

1. **Primitive & Boxed Types** – string, numeric, boolean, char
2. **Enums** – inner and outer classes
3. **Collections & Maps** – lists, sets, maps with `String` or enum keys, primitive arrays
4. **Nested & Composite Objects** – arbitrary object graphs, circular reference protection via graph tracking in the analyser
5. **Polymorphic Hierarchies** – each concrete subtype gets its own parser, base fields are handled once per type
6. **Custom Parser Integration** – user-supplied `@CustomParser` classes are detected and reused
7. **Validation Layer** – descriptive errors for unsupported types and mis-configurations
8. **CI Test Suite** – round-trip tests compare generated parsers against a reference implementation for every supported type combination

## Remaining Implementation Tasks

1. **Deeply Nested Collections** [HIGH]  
   • Support structures with 3+ levels of nested generics, e.g. `List<List<Map<String, Custom>>>`.
2. **Performance Benchmarks** [HIGH]  
   • JMH micro-benchmarks for parser throughput and memory footprint.
3. **Stress & Memory Tests** [HIGH]  
   • Large (10⁶+) element collections, repeated parser invocations.
4. **Documentation Overhaul** [MEDIUM]  
   • Replace hand-written examples with snippets generated from integration tests.
5. **Gradle Plugin** [MEDIUM]  
   • Provide first-class support outside Maven ecosystems.
6. **IDE Integration** [LOW]  
   • Annotation-processor variant to generate parsers directly inside the IDE.

## Explicitly Unsupported Types (unchanged)

1. **Java 8+ Functional & Temporal** – `Optional<T>`, `LocalDate`, `LocalDateTime`, etc.
2. **High-Precision Numbers** – `BigDecimal`, `BigInteger`
3. **Identities & Dates** – `UUID`, `Date`, `Calendar`
4. **Specific Collections** – Queue/Deque, `SortedSet`, `SortedMap` and any `Map` whose key is not a `String` or enum.

## Milestones

| Version | Focus | Target Date |
|---------|-------|-------------|
| 0.9.0 | Deeply nested collection support | Q4-2025 |
| 1.0.0 | Performance & documentation freeze | Q1-2026 |
| 1.1.0 | Gradle plugin GA | Q2-2026 |
| 1.2.0 | IDE annotation-processor | Q3-2026 |

## Testing Strategy

• **Round-trip tests** – JSON → object → JSON equality checks for every generated parser.  
• **Golden-file comparison** – emitted source is compared to an *expected* folder to catch accidental template changes.  
• **Contract tests** – negative tests confirming that unsupported types trigger `UnsupportedTypeException`.

## Performance Goals

| Metric | Target |
|--------|--------|
| Parse throughput | ≥ 1 M objects/s on JDK 21, x86-64 desktop |
| Allocation rate | ≤ 30 bytes per parsed object |
| Build-time footprint | ≤ 300 ms per generated parser |