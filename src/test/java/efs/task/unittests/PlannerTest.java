package efs.task.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

public class PlannerTest {
    private Planner planner = new Planner();
    @BeforeEach
    void createNewPlanner() {
        planner = new Planner();
    }

    @ParameterizedTest
    @EnumSource(ActivityLevel.class)
    void shouldReturnValidCaloriesValues_whenUserGiven(ActivityLevel activityLevel) {
        User user = TestConstants.TEST_USER;
        Map<ActivityLevel, Integer> calories_on_activity_level = TestConstants.CALORIES_ON_ACTIVITY_LEVEL;

        int activityLevelInt = planner.calculateDailyCaloriesDemand(user, activityLevel);

        assertEquals(calories_on_activity_level.get(activityLevel), activityLevelInt);
    }

    @Test
    void shouldReturnTrue_whenDailyIntakeGiven() {
        User user = TestConstants.TEST_USER;
        DailyIntake dailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        DailyIntake calculatedDailyIntake = planner.calculateDailyIntake(user);

        assertEquals(dailyIntake.getCalories(), calculatedDailyIntake.getCalories());
        assertEquals(dailyIntake.getFat(), calculatedDailyIntake.getFat());
        assertEquals(dailyIntake.getCarbohydrate(), calculatedDailyIntake.getCarbohydrate());
        assertEquals(dailyIntake.getProtein(), calculatedDailyIntake.getProtein());
    }
}
