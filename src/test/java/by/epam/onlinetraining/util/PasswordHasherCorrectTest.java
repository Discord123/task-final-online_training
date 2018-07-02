package by.epam.onlinetraining.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordHasherCorrectTest {
    private String expected;
    private String password;

    public PasswordHasherCorrectTest(String expected, String password){
        this.expected = expected;
        this.password = password;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "password"},
                {"22665f9cd19cc9946cf921623d4dcab834b221e4", "pa55word"}
        });
    }

    @Test
    public void shouldReturnSameHashedValue() throws Exception {
        String result = PasswordHasher.shaHashing(password);
        assertEquals(expected, result);
    }

}
