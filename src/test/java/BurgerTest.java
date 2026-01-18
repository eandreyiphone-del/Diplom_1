import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import ru.yandex.practicum.Bun;
import ru.yandex.practicum.Burger;
import ru.yandex.practicum.Ingredient;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerTest {

    private final float bunPrice;
    private final float firstIngredientPrice;
    private final float secondIngredientPrice;
    private final float expectedTotalPrice;

    public BurgerTest(float bunPrice, float firstIngredientPrice, float secondIngredientPrice, float expectedTotalPrice) {
        this.bunPrice = bunPrice;
        this.firstIngredientPrice = firstIngredientPrice;
        this.secondIngredientPrice = secondIngredientPrice;
        this.expectedTotalPrice = expectedTotalPrice;
    }

    @Parameterized.Parameters(name = "{index}: Test with prices {0},{1},{2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {50F, 25F, 30F, 185F}, // Случай с нормальными ценами
                {150F, 100F, 150F, 550F}, // Дороже компоненты
                {200F, 0F, 0F, 400F}, // Только булочка
                {0F, 100F, 150F, 250F}, // Отсутствие булочки
        });
    }

    @Test
    public void testCalculatePrice() {
        // Создаем mock-объекты для булочки и ингредиентов
        Bun bun = Mockito.mock(Bun.class);
        Ingredient firstIngredient = Mockito.mock(Ingredient.class);
        Ingredient secondIngredient = Mockito.mock(Ingredient.class);

        // Настроим mock-объекты на возврат нужных цен
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(firstIngredient.getPrice()).thenReturn(firstIngredientPrice);
        Mockito.when(secondIngredient.getPrice()).thenReturn(secondIngredientPrice);

        // Формируем бургер
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        // Проверяем итоговую цену
        float totalPrice = burger.getPrice();
        assertEquals(expectedTotalPrice, totalPrice, 0.001); // Погрешность 0.001
    }
}
