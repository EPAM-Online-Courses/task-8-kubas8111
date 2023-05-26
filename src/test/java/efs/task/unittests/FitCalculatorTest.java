package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietRecommended() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightZero() {
        //given
        double weight = 60.2;
        double height = 0.0;

        //when
        Class expectedException = IllegalArgumentException.class;

        //then
        assertThrows(expectedException, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "weight={arguments}")
    @ValueSource(doubles = {91.6, 88.3, 96.4, 101.5})
    void shouldThrowTrue_whenDietRecommended(double weight) {
        double height = 1.79;

        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertTrue(recommended);
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            weight,    source
            66.5,  1.71
            70.1,  1.85
            54.1,  1.65
            91.5,  1.99
            """)
    void shouldReturnFalse_whenDietRecommended(double weight, double height) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/data.csv", useHeadersInDisplayName = true)
    void returnFalse_whenDietIsRecommendedFromSource(double weight, double height) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }


    @Test
    void shouldReturnWorstBMI_whenListOfUsersGiven() {
        List<User> test_data = TestConstants.TEST_USERS_LIST;
        double weight = 97.3;
        double height = 1.79;

        User userWithTheWorstBMI = FitCalculator.findUserWithTheWorstBMI(test_data);

        assertEquals(weight, userWithTheWorstBMI.getWeight());
        assertEquals(height, userWithTheWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull_whenListOfUsersIsEmpty() {
        List<User> test_data = new ArrayList<>();

        User userWithTheWorstBMI = FitCalculator.findUserWithTheWorstBMI(test_data);

        assertNull(userWithTheWorstBMI);
    }

    @Test
    void shouldReturnTestUsersBMIScore_whenTestUserListGiven() {
        List<User> test_data = TestConstants.TEST_USERS_LIST;
        double[] expectedBMIScores = TestConstants.TEST_USERS_BMI_SCORE;

        double[] bmiScore = FitCalculator.calculateBMIScore(test_data);

        assertArrayEquals(expectedBMIScores, bmiScore);
    }
}