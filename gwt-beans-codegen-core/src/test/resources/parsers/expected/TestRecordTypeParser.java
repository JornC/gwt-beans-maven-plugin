package nl.aerius.codegen.test.generated;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.TestRecordType;
import nl.aerius.json.JSONArrayHandle;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "2024-01-01T00:00:00")
public class TestRecordTypeParser {
  public static TestRecordType parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestRecordType parse(final JSONObjectHandle baseObj) {
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

    // Parse tags
    List<String> tags = null;
    if (baseObj.has("tags") && !baseObj.isNull("tags")) {
      final JSONArrayHandle tagsValueArray = baseObj.getArray("tags");
      final List<String> tagsValue = new ArrayList<>();
      tagsValueArray.forEachString(tagsValue::add);
      tags = tagsValue;
    }

    return new TestRecordType(name, value, tags);
  }
}
