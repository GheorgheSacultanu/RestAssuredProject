package helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class Random_generator {
    public String generateRandomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);

        return generatedString;
    }
}
