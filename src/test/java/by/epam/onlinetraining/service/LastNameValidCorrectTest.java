package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LastNameValidCorrectTest {
    private String stringForValidation;

    public LastNameValidCorrectTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"fedorov"},
                {"Gle-b"},
                {"MaliNa"},
        });
    }

    @Test
    public void shouldReturnFalseWhenSurnameValid() throws Exception {
        assertFalse(Validator.isLastNameValid(stringForValidation));
    }
}