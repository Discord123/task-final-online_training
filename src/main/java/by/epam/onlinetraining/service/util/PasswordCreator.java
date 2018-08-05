package by.epam.onlinetraining.service.util;

import java.util.Random;

public class PasswordCreator {

    public static String createPassword(){
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 10; i++){
            int isDigitDefiner = random.nextInt(2);
            boolean isDigit = (isDigitDefiner == 0);
            if (isDigit){
                int digit = random.nextInt(9);
                password.append(digit);
            } else {
                int letterValue = 'A' + random.nextInt(22);
                char letter = (char) letterValue;
                if(random.nextBoolean()){
                    letter = Character.toLowerCase(letter);
                }
                password.append(letter);
            }
        }
        return password.toString();
    }
}
