package nl.aerius.codegen.test.generated;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.TestConcreteSubtypeOnlyType;
import nl.aerius.codegen.test.types.polymorphic.TestSinglePolySubX;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "GENERATION_TIMESTAMP")
public class TestConcreteSubtypeOnlyTypeParser {

  public static TestConcreteSubtypeOnlyType parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestConcreteSubtypeOnlyType parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    final TestConcreteSubtypeOnlyType config = new TestConcreteSubtypeOnlyType();
    parse(baseObj, config);
    return config;
  }

  public static void parse(final JSONObjectHandle baseObj,
      final TestConcreteSubtypeOnlyType config) {
    if (baseObj == null || config == null) {
      return;
    }

    // Parse onlySubtype
    if (baseObj.has("onlySubtype") && !baseObj.isNull("onlySubtype")) {
      final TestSinglePolySubX value = TestSinglePolySubXParser.parse(baseObj.getObject("onlySubtype"));
      config.setOnlySubtype(value);
    }
  }
}
