package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EmailValidIncorrectTest {
    private String stringForValidation;

    public EmailValidIncorrectTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"@mail.ru"},
                {".@test.net"},
                {"vadim@"},
        });
    }

    @Test
    public void shouldReturnFalseWhenEmailInvalid() throws Exception {
        assertFalse(Validator.isEmailValid(stringForValidation));
    }

}