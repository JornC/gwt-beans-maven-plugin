package nl.aerius.codegen.test.generated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Generated;

import nl.aerius.codegen.test.types.TestConstructorWithGenericsType;
import nl.aerius.json.JSONArrayHandle;
import nl.aerius.json.JSONObjectHandle;

@Generated(value = "nl.aerius.codegen.ParserGenerator", date = "2024-01-01T00:00:00")
public class TestConstructorWithGenericsTypeParser {
  public static TestConstructorWithGenericsType parse(final String jsonText) {
    if (jsonText == null) {
      return null;
    }

    return parse(JSONObjectHandle.fromText(jsonText));
  }

  public static TestConstructorWithGenericsType parse(final JSONObjectHandle baseObj) {
    if (baseObj == null) {
      return null;
    }

    // Parse tags
    List<String> tags = null;
    if (baseObj.has("tags") && !baseObj.isNull("tags")) {
      final JSONArrayHandle tagsValueArray = baseObj.getArray("tags");
      final List<String> tagsValue = new ArrayList<>();
      tagsValueArray.forEachString(tagsValue::add);
      tags = tagsValue;
    }

    // Parse counts
    Map<String, Integer> counts = null;
    if (baseObj.has("counts") && !baseObj.isNull("counts")) {
      final JSONObjectHandle obj = baseObj.getObject("counts");
      final Map<String, Integer> countsValue = new LinkedHashMap<>();
      obj.keySet().forEach(key -> {
        final Integer level2Value = obj.getInteger(key);
        countsValue.put(key, level2Value);
      });
      counts = countsValue;
    }

    // Parse labels
    Set<String> labels = null;
    if (baseObj.has("labels") && !baseObj.isNull("labels")) {
      final JSONArrayHandle labelsValueArray = baseObj.getArray("labels");
      final Set<String> labelsValue = new HashSet<>();
      labelsValueArray.forEachString(labelsValue::add);
      labels = labelsValue;
    }

    // Parse sizes
    int[] sizes = null;
    if (baseObj.has("sizes") && !baseObj.isNull("sizes")) {
      int[] sizesValue = null;
      final JSONArrayHandle sizesValueJsonArray = baseObj.getArray("sizes");
      if (sizesValueJsonArray != null) {
        final List<Integer> sizesValueTempList = new ArrayList<>();
        sizesValueJsonArray.forEachInteger(sizesValueTempList::add);
        sizesValue = sizesValueTempList.stream().mapToInt(i -> i != null ? i.intValue() : 0).toArray();
      }
      sizes = sizesValue;
    }

    // Parse aliases
    String[] aliases = null;
    if (baseObj.has("aliases") && !baseObj.isNull("aliases")) {
      String[] aliasesValue = null;
      final JSONArrayHandle aliasesValueJsonArray = baseObj.getArray("aliases");
      if (aliasesValueJsonArray != null) {
        final List<String> aliasesValueTempList = new ArrayList<>();
        aliasesValueJsonArray.forEachString(aliasesValueTempList::add);
        aliasesValue = aliasesValueTempList.toArray(new String[0]);
      }
      aliases = aliasesValue;
    }

    return new TestConstructorWithGenericsType(tags, counts, labels, sizes, aliases);
  }
}
