package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordValidCorrectTest {
    private String stringForValidation;

    public PasswordValidCorrectTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"AAAAA5555"},
                {"bakjhajk55"},
                {"AgabBa4422"},
        });
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsValid() throws Exception {
        assertTrue(Validator.isPasswordValid(stringForValidation));
    }

}