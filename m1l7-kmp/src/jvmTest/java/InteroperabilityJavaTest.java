import jvm.InteroperabilityJava;
import org.junit.jupiter.api.Test;

public class InteroperabilityJavaTest {

    @Test
    void test1() {
        InteroperabilityJava.Companion.functionOne();
        InteroperabilityJava.functionOne();
//
//        new InteroperabilityJava().defaults("123", 123, true);
    }

//    @Test
//    void test2() {
//        InteroperabilityJava.functionOne();
//    }

    @Test
    void test3() {
        System.out.println(
                new InteroperabilityJava().defaults()
        );
    }
//
//    @Test
//    void test4() {
//        System.out.println(new InteroperabilityJava().defaults("p1"));
//    }

//    @Test
//    void test5() {
//        System.out.println(new InteroperabilityJava().customName());
//    }
}
