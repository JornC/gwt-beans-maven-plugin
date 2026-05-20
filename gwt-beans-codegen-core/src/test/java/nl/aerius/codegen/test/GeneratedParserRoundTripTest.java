package nl.aerius.codegen.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import nl.aerius.codegen.test.types.TestRecordType;
import nl.aerius.codegen.test.types.TestRootObjectType;

/**
 * Tests the round trip functionality using the dynamically generated parser.
 * This test verifies that our parser generator produces code that correctly
 * handles all fields and types. If this test fails but
 * ExpectedParserRoundTripTest passes, it means our generator is not producing
 * correct code.
 */
class GeneratedParserRoundTripTest extends AbstractRoundTripTest {
  @Override
  protected void prepareParser() throws Exception {
    generateParser(TestRootObjectType.class);
  }

  @Test
  void shouldRoundTripBasicTypes() throws Exception {
    // First generate the parser
    prepareParser();

    // Find the appropriate parser for TestRootObjectType
    Class<?> parserClass = findParserForType(TestRootObjectType.class);

    // Create test object with all fields set
    TestRootObjectType original = TestRootObjectType.createFullObject();

    assertRoundTrip(original, parserClass);
  }

  @Test
  void shouldHandleNullValues() throws Exception {
    // First generate the parser
    prepareParser();

    // Find the appropriate parser for TestRootObjectType
    Class<?> parserClass = findParserForType(TestRootObjectType.class);

    // Create object with null values
    TestRootObjectType original = TestRootObjectType.createNullObject();

    assertRoundTrip(original, parserClass);
  }

  /**
   * The constructor-based parser must tolerate JSON that omits non-primitive fields entirely
   * (not just sends them as {@code null}). Jackson's default null-suppressing serialization
   * produces this shape, so records would otherwise blow up with "Required field is missing"
   * the moment any optional component is null on the server side.
   */
  @Test
  void shouldHandleOmittedNonPrimitiveFieldsInRecord() throws Exception {
    prepareParser();

    final Class<?> parserClass = findParserForType(TestRecordType.class);
    final Method parseMethod = parserClass.getMethod("parse", String.class);

    // Only the primitive component is present; "name" and "tags" are missing keys, not null values.
    final Object parsed = parseMethod.invoke(null, "{\"value\":42}");

    assertNotNull(parsed, "Parser should construct a record despite missing non-primitive fields");
    assertEquals(42, parsed.getClass().getMethod("value").invoke(parsed));
    assertNull(parsed.getClass().getMethod("name").invoke(parsed),
        "Missing String component should default to null");
    assertNull(parsed.getClass().getMethod("tags").invoke(parsed),
        "Missing collection component should default to null");
  }

  /**
   * Primitives can't be null, so their absence still indicates a malformed payload - keep throwing.
   */
  @Test
  void shouldThrowOnMissingPrimitiveFieldInRecord() throws Exception {
    prepareParser();

    final Class<?> parserClass = findParserForType(TestRecordType.class);
    final Method parseMethod = parserClass.getMethod("parse", String.class);

    final InvocationTargetException ex = assertThrows(InvocationTargetException.class,
        () -> parseMethod.invoke(null, "{}"));
    assertTrue(ex.getCause() instanceof RuntimeException);
    assertTrue(ex.getCause().getMessage().contains("Required field 'value' is missing"),
        "Expected message about missing primitive 'value', got: " + ex.getCause().getMessage());
  }
}