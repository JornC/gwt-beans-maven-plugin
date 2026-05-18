package nl.aerius.codegen.test.generated;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.polymorphic.TestSinglePolyBase;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "GENERATION_TIMESTAMP")
public class TestSinglePolyBaseParser {

  public static TestSinglePolyBase parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestSinglePolyBase parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    throw new UnsupportedOperationException("Cannot directly instantiate abstract class or interface " + TestSinglePolyBase.class.getName() + ". Use @JsonTypeInfo or a custom parser.");
  }

  public static void parse(final JSONObjectHandle baseObj, final TestSinglePolyBase config) {
    if (baseObj == null || config == null) {
      return;
    }

    // Parse baseLabel
    if (baseObj.has("baseLabel") && !baseObj.isNull("baseLabel")) {
      final String value = baseObj.getString("baseLabel");
      config.setBaseLabel(value);
    }
  }
}
