package by.epam.onlinetraining.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FirstNameValidCorrectTest {
    private String stringForValidation;

    public FirstNameValidCorrectTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"Artsiom"},
                {"Greg"},
                {"Sabrina"},
        });
    }

    @Test
    public void shouldReturnTrueWhenNameValid() throws Exception {
        assertTrue(Validator.isFirstNameValid(stringForValidation));
    }
}