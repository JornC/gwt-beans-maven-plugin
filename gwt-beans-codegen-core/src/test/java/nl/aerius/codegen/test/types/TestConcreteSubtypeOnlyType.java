package nl.aerius.codegen.test.types;

import nl.aerius.codegen.test.types.polymorphic.TestSinglePolySubX;

/**
 * References only one concrete subtype of a polymorphic hierarchy. The
 * sibling subtype (TestSinglePolySubY) must not get a generated parser.
 */
public class TestConcreteSubtypeOnlyType {
  private TestSinglePolySubX onlySubtype;

  public TestSinglePolySubX getOnlySubtype() {
    return onlySubtype;
  }

  public void setOnlySubtype(TestSinglePolySubX onlySubtype) {
    this.onlySubtype = onlySubtype;
  }

  public static TestConcreteSubtypeOnlyType createFullObject() {
    TestConcreteSubtypeOnlyType obj = new TestConcreteSubtypeOnlyType();
    obj.setOnlySubtype(new TestSinglePolySubX("labelX", 99));
    return obj;
  }
}
