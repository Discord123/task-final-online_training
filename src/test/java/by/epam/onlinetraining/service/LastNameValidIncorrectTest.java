package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LastNameValidIncorrectTest {
    private String stringForValidation;

    public LastNameValidIncorrectTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"Book"},
                {"Langines"},
                {"Medvedov"},
        });
    }

    @Test
    public void shouldReturnTrueWhenSurnameValid() throws Exception {
        assertTrue(Validator.isLastNameValid(stringForValidation));
    }
}