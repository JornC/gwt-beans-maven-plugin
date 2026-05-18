package nl.aerius.codegen.analyzer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.palantir.javapoet.ClassName;

import nl.aerius.codegen.util.ClassFinder;
import nl.aerius.codegen.util.Logger;

class TypeAnalyzerTest {
  private TypeAnalyzer analyzer;

  @BeforeEach
  void setUp() {
    analyzer = new TypeAnalyzer(new ClassFinder() {}, new Logger() {});
  }

  @Test
  void testBasicTypeAnalysis() {
    final Set<ClassName> types = analyzer.analyzeClass(TestClass.class.getName());

    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_TestClass")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_NestedClass")));
  }

  @Test
  void testUnsupportedTypeDetection() {
    assertThrows(UnsupportedTypeException.class, () -> {
      analyzer.analyzeClass(UnsupportedTypeTestClass.class.getName());
    });
  }

  @Test
  void testCustomParserTypeHandling() {
    analyzer.setCustomParserTypes(Set.of("CustomParserType"));
    final Set<ClassName> types = analyzer.analyzeClass(CustomParserTestClass.class.getName());
    System.out.println("Discovered types with custom parser: " + types);

    assertFalse(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_CustomParserType")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_CustomParserTestClass")));
  }

  @Test
  void abstractBaseReachedViaConcreteSubtypeOnlyDoesNotExpandSiblings() {
    final Set<ClassName> types = analyzer.analyzeClass(ConcreteOnlyRootClass.class.getName());

    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_ConcreteOnlyRootClass")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolySubAlpha")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolyAbstractBase")),
        "Abstract base should still be discovered (its parse(handle, config) is invoked by the subtype parser)");
    assertFalse(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolySubBeta")),
        "Sibling subtype must be skipped when the base is only reached via a concrete subtype");
    assertFalse(analyzer.getPolymorphicallyReachedTypes().contains(PolyAbstractBase.class),
        "Base reached only via concrete subtype's superclass walk should NOT be flagged for polymorphic dispatch");
  }

  @Test
  void abstractBaseReachedDirectlyExpandsAllSubtypes() {
    final Set<ClassName> types = analyzer.analyzeClass(AbstractFieldRootClass.class.getName());

    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolyAbstractBase")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolySubAlpha")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_PolySubBeta")),
        "Abstract field type must walk all subtypes for the discriminator switch");
    assertTrue(analyzer.getPolymorphicallyReachedTypes().contains(PolyAbstractBase.class),
        "Direct abstract reach must flag the base for polymorphic dispatch generation");
  }

  @Test
  void testNestedCollectionTypes() {
    final Set<ClassName> types = analyzer.analyzeClass(NestedCollectionTestClass.class.getName());
    System.out.println("Discovered nested collection types: " + types);

    assertTrue(
        types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_NestedCollectionTestClass")));
    assertTrue(types.contains(ClassName.get("nl.aerius.codegen.analyzer", "TypeAnalyzerTest_DeepNestedClass")));
  }

  // Test classes
  private static class TestClass {
    private String primitiveField;
    private NestedClass nestedField;
  }

  private static class NestedClass {
    private int primitiveInt;
  }

  private static class UnsupportedTypeTestClass {
    private LocalDate unsupportedField;
  }

  private static class CustomParserType {
    private String field;
  }

  private static class CustomParserTestClass {
    private CustomParserType customParserField;
    private String regularField;
  }

  private static class DeepNestedClass {
    private String field;
  }

  private static class NestedCollectionTestClass {
    private List<Map<String, DeepNestedClass>> nestedCollection;
  }

  // Test class for complex nested structures with primitive types and enums
  private enum TestEnum {
    A, B, C
  }

  private static class ComplexNestedTestClass {
    private Map<TestEnum, Map<Integer, Map<Integer, String>>> complexNestedMap;
  }

  // Polymorphic hierarchy used by reach-tracking tests.
  @JsonTypeInfo(use = Id.NAME, property = "_type")
  @JsonSubTypes({
      @Type(value = PolySubAlpha.class, name = "alpha"),
      @Type(value = PolySubBeta.class, name = "beta")
  })
  private static abstract class PolyAbstractBase {
    private String baseLabel;

    public String getBaseLabel() {
      return baseLabel;
    }

    public void setBaseLabel(final String baseLabel) {
      this.baseLabel = baseLabel;
    }
  }

  private static class PolySubAlpha extends PolyAbstractBase {
    private int alphaValue;

    public int getAlphaValue() {
      return alphaValue;
    }

    public void setAlphaValue(final int alphaValue) {
      this.alphaValue = alphaValue;
    }
  }

  private static class PolySubBeta extends PolyAbstractBase {
    private boolean betaFlag;

    public boolean isBetaFlag() {
      return betaFlag;
    }

    public void setBetaFlag(final boolean betaFlag) {
      this.betaFlag = betaFlag;
    }
  }

  // Field declares the concrete subtype - base is reached only via its superclass chain.
  private static class ConcreteOnlyRootClass {
    private PolySubAlpha onlyConcrete;

    public PolySubAlpha getOnlyConcrete() {
      return onlyConcrete;
    }

    public void setOnlyConcrete(final PolySubAlpha onlyConcrete) {
      this.onlyConcrete = onlyConcrete;
    }
  }

  // Field declares the abstract base - every subtype must be reachable.
  private static class AbstractFieldRootClass {
    private PolyAbstractBase polymorphic;

    public PolyAbstractBase getPolymorphic() {
      return polymorphic;
    }

    public void setPolymorphic(final PolyAbstractBase polymorphic) {
      this.polymorphic = polymorphic;
    }
  }
}