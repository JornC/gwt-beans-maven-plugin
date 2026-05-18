package nl.aerius.codegen.test.types.polymorphic;

import java.util.Objects;

public class TestSinglePolySubX extends TestSinglePolyBase {

  private static final long serialVersionUID = 1L;

  private int valueX;

  public TestSinglePolySubX() {
    super();
  }

  public TestSinglePolySubX(String baseLabel, int valueX) {
    super(baseLabel);
    this.valueX = valueX;
  }

  public int getValueX() {
    return valueX;
  }

  public void setValueX(int valueX) {
    this.valueX = valueX;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TestSinglePolySubX)) return false;
    if (!super.equals(o)) return false;
    TestSinglePolySubX that = (TestSinglePolySubX) o;
    return getValueX() == that.getValueX();
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getValueX());
  }
}
