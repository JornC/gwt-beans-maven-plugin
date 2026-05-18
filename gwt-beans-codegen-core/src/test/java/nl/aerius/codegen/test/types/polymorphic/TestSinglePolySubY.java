package nl.aerius.codegen.test.types.polymorphic;

import java.util.Objects;

public class TestSinglePolySubY extends TestSinglePolyBase {

  private static final long serialVersionUID = 1L;

  private boolean flagY;

  public TestSinglePolySubY() {
    super();
  }

  public TestSinglePolySubY(String baseLabel, boolean flagY) {
    super(baseLabel);
    this.flagY = flagY;
  }

  public boolean isFlagY() {
    return flagY;
  }

  public void setFlagY(boolean flagY) {
    this.flagY = flagY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TestSinglePolySubY)) return false;
    if (!super.equals(o)) return false;
    TestSinglePolySubY that = (TestSinglePolySubY) o;
    return isFlagY() == that.isFlagY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), isFlagY());
  }
}
