package by.epam.onlinetraining.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordValidIncorrectTest {
    private String stringForValidation;

    public PasswordValidIncorrectTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"a a "},
                {"a3,b"},
                {"4.556AAb"},
                {"4*55-AAb"},
                {"Ab5698^C"},
        });
    }

    @Test
    public void shouldReturnFalseWhenPasswordIsInvalid() throws Exception {
        assertFalse(Validator.isPasswordValid(stringForValidation));
    }
}