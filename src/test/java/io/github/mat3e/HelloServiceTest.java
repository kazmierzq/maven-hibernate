package io.github.mat3e;

import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

public class HelloServiceTest {
    private HelloService SUT = new HelloService();
    private final static String WELCOME = "Hello";
    
    @Test
    public void test_prepareGreeting_nullName_returnsGreetingsWithFallbackName() {
        // given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreeting(null, "-1");

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnsGreetingsWithName() {
        // given
        var SUT = new HelloService();
        var name = "test";

        // when
        var result = SUT.prepareGreeting(name, "-1");

        //then
        assertEquals(WELCOME + " " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullName_returnsGreetingsWithFallbackIdLang() {
        // given
        var fallbackIdWelcome = "Hola";
        var mockRepository = new LangRepository(){
            @Override
            Optional<Lang> findById(Long id) {
                if(id.equals(HelloService.FALLBACK_LANG.getId())){
                    return Optional.of(new Lang(null, fallbackIdWelcome, null));
                }
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(fallbackIdWelcome + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository() {
            @Override
            Optional<Lang> findById(Long id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }
}
