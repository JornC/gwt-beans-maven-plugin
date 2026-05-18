package nl.aerius.codegen.test.types.polymorphic;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, property = "_type")
@JsonSubTypes({
    @Type(value = TestSinglePolySubX.class, name = "TypeX"),
    @Type(value = TestSinglePolySubY.class, name = "TypeY")
})
public abstract class TestSinglePolyBase implements Serializable {

  private static final long serialVersionUID = 1L;

  private String baseLabel;

  protected TestSinglePolyBase() {}

  protected TestSinglePolyBase(String baseLabel) {
    this.baseLabel = baseLabel;
  }

  public String getBaseLabel() {
    return baseLabel;
  }

  public void setBaseLabel(String baseLabel) {
    this.baseLabel = baseLabel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TestSinglePolyBase)) return false;
    TestSinglePolyBase that = (TestSinglePolyBase) o;
    return Objects.equals(getBaseLabel(), that.getBaseLabel());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBaseLabel());
  }
}
