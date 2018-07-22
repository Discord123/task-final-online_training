package by.epam.onlinetraining.util;

import by.epam.onlinetraining.service.util.PasswordHasher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class PasswordHasherIncorrectTest {
    private String expected;
    private String password;

    public PasswordHasherIncorrectTest(String expected, String password){
        this.expected = expected;
        this.password = password;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"5baa61e4c9b93f3f0ga2250b6cf8331b7ee68fd8", "password"},
                {"22665f9cd19cc9946cf921623d4dtkb834b221e4", "pa55word"},
                {"null", "wordpass"}
        });
    }

    @Test
    public void shouldReturnSameHashedValue() throws Exception {
        String result = PasswordHasher.shaHashing(password);
        assertNotEquals(expected, result);
    }

}
