package nl.aerius.codegen.test.generated;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.TestConstructorBasedType;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "2024-01-01T00:00:00")
public class TestConstructorBasedTypeParser {
  public static TestConstructorBasedType parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestConstructorBasedType parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    // Parse name
    String name = null;
    if (baseObj.has("name") && !baseObj.isNull("name")) {
      final String nameValue = baseObj.getString("name");
      name = nameValue;
    }

    // Parse value
    if (!baseObj.has("value")) {
      throw new RuntimeException("Required field 'value' is missing");
    }
    final int value = baseObj.getInteger("value");

    // Parse optionalValue
    Double optionalValue = null;
    if (baseObj.has("optionalValue") && !baseObj.isNull("optionalValue")) {
      final Double optionalValueValue = baseObj.getNumber("optionalValue");
      optionalValue = optionalValueValue;
    }

    return new TestConstructorBasedType(name, value, optionalValue);
  }
}
