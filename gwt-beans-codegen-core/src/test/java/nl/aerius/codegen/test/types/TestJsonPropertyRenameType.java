package nl.aerius.codegen.test.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exercises the {@code @JsonProperty} key/accessor rename: the Java field is named
 * {@code assessmentAreaId} but the wire JSON uses {@code "id"}, and the accessors are
 * {@code getId()/setId(int)} (mimicking a {@code HasId} pattern on a JsType overlay).
 *
 * Round-trip success here proves that the generator (a) reads from {@code "id"} in the
 * JSON, (b) emits {@code config.setId(...)} not {@code config.setAssessmentAreaId(...)},
 * and that the validator accepts this shape.
 */
public class TestJsonPropertyRenameType {
  @JsonProperty("id")
  private int assessmentAreaId;
  private String name;

  public int getId() {
    return assessmentAreaId;
  }

  public void setId(final int id) {
    this.assessmentAreaId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public static TestJsonPropertyRenameType createFullObject() {
    final TestJsonPropertyRenameType obj = new TestJsonPropertyRenameType();
    obj.setId(42);
    obj.setName("Veluwe");
    return obj;
  }

  public static TestJsonPropertyRenameType createNullObject() {
    final TestJsonPropertyRenameType obj = new TestJsonPropertyRenameType();
    obj.setName(null);
    return obj;
  }
}
