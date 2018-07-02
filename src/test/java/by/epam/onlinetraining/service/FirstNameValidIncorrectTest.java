package by.epam.onlinetraining.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FirstNameValidIncorrectTest {
    private String stringForValidation;

    public FirstNameValidIncorrectTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"artsiom"},
                {"4reg"},
                {"Sabr-ina"},
        });
    }

    @Test
    public void shouldReturnFalseWhenNameInvalid() throws Exception {
        assertFalse(Validator.isFirstNameValid(stringForValidation));
    }
}