package nl.aerius.codegen.test.types;

import com.fasterxml.jackson.annotation.JsonProperty;

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
