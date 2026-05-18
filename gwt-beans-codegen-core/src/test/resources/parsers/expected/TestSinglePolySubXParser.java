package nl.aerius.codegen.test.generated;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.polymorphic.TestSinglePolySubX;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "GENERATION_TIMESTAMP")
public class TestSinglePolySubXParser {

  public static TestSinglePolySubX parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestSinglePolySubX parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    final TestSinglePolySubX config = new TestSinglePolySubX();
    parse(baseObj, config);
    return config;
  }

  public static void parse(final JSONObjectHandle baseObj, final TestSinglePolySubX config) {
    if (baseObj == null || config == null) {
      return;
    }

    // Parse fields from parent class (TestSinglePolyBase)
    TestSinglePolyBaseParser.parse(baseObj, config);

    // Parse valueX
    if (baseObj.has("valueX")) {
      final int value = baseObj.getInteger("valueX");
      config.setValueX(value);
    }
  }
}
