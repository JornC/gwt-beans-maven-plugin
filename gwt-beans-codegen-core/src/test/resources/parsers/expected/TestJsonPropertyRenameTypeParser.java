package nl.aerius.codegen.test.generated;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.TestJsonPropertyRenameType;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "2024-01-01T00:00:00")
public class TestJsonPropertyRenameTypeParser {
  public static TestJsonPropertyRenameType parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestJsonPropertyRenameType parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    final TestJsonPropertyRenameType config = new TestJsonPropertyRenameType();
    parse(baseObj, config);
    return config;
  }

  public static void parse(final JSONObjectHandle baseObj,
      final TestJsonPropertyRenameType config) {
    if (baseObj == null || config == null) {
      return;
    }

    // Parse assessmentAreaId
    if (baseObj.has("id")) {
      final int value = baseObj.getInteger("id");
      config.setId(value);
    }

    // Parse name
    if (baseObj.has("name") && !baseObj.isNull("name")) {
      final String value = baseObj.getString("name");
      config.setName(value);
    }
  }
}
