package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EmailValidCorrectTest {
    private String stringForValidation;

    public EmailValidCorrectTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"punch@mail.ru"},
                {"fedia@test.net"},
                {"vadim@gmail.com"},
        });
    }

    @Test
    public void shouldReturnTrueWhenEmailValid() throws Exception {
        assertTrue(Validator.isEmailValid(stringForValidation));
    }

}