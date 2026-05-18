package nl.aerius.codegen.test.types;

import nl.aerius.codegen.test.types.polymorphic.TestSinglePolySubX;

/**
 * Wires only a concrete subtype of a polymorphic hierarchy. The hierarchy's
 * abstract base (TestSinglePolyBase) is reachable via the subtype's superclass
 * chain, but no field is declared as the base itself - so the generator should
 * skip sibling subtype parsers (TestSinglePolySubY) and emit a plain parser
 * for the abstract base rather than a polymorphic discriminator switch.
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
