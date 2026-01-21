import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.yandex.practicum.Bun;
import ru.yandex.practicum.Burger;
import ru.yandex.practicum.Ingredient;
import ru.yandex.practicum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    // Параметры, которые передаются в конструкторе теста
    private final IngredientType type;
    private final String name;
    private final float price;

    // Поля для моков
    private Bun bun;
    private Ingredient ingredientFirst;
    private Burger burger;

    // Массив данных для параметризации
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {IngredientType.SAUCE, "hot sauce", 100},
                {IngredientType.SAUCE, "sour cream", 200},
                {IngredientType.SAUCE, "chili sauce", 300},
                {IngredientType.FILLING, "cutlet", 100},
                {IngredientType.FILLING, "dinosaur", 200},
                {IngredientType.FILLING, "sausage", 300},
                {IngredientType.SAUCE, "", 0},
                {IngredientType.SAUCE, "", -100},
        });
    }

    // Конструктор для приема параметров
    public BurgerTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;

        // Создание моков вручную
        this.bun = Mockito.mock(Bun.class);
        this.ingredientFirst = Mockito.mock(Ingredient.class);
        this.burger = new Burger(); // Настоящий объект, поскольку мы тестируем его логику
    }

    @Test
    public void setBunsTest() {
        // Настройка моков
        Mockito.when(bun.getName()).thenReturn("Red Bun");
        Mockito.when(bun.getPrice()).thenReturn(300f);

        // Действие
        burger.setBuns(bun);

        // Проверка результата
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        // Настройка моков
        Mockito.when(ingredientFirst.getType()).thenReturn(type);
        Mockito.when(ingredientFirst.getName()).thenReturn(name);
        Mockito.when(ingredientFirst.getPrice()).thenReturn(price);

        // Действие
        burger.addIngredient(ingredientFirst);

        // Проверка результата
        assertFalse("Ошибка: список ингредиентов пустой", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredient() {
        // Создание второго мока ингредиента
        Ingredient secondIngredient = Mockito.mock(Ingredient.class);
        Mockito.when(secondIngredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(secondIngredient.getName()).thenReturn("Sour Cream");
        Mockito.when(secondIngredient.getPrice()).thenReturn(200f);

        // Действие
        burger.addIngredient(ingredientFirst);
        burger.addIngredient(secondIngredient);
        burger.moveIngredient(0, 1);

        // Проверка результата
        assertEquals("Sour Cream", burger.ingredients.get(0).getName());
    }

    @Test
    public void getPriceTest() {
        // Настройка моков
        Mockito.when(bun.getPrice()).thenReturn(300f);
        Mockito.when(ingredientFirst.getPrice()).thenReturn(150f);

        // Действие
        burger.setBuns(bun);
        burger.addIngredient(ingredientFirst);
        burger.addIngredient(ingredientFirst);

        // Оцениваем цену
        float expectedPrice = 300f * 2 + 150f * 2;

        // Проверка результата
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    public void getReceiptTest() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        String result = String.format(
                "(==== Краторная булка N-200i ====)%n" +
                        "= filling Флюоресцентная булка R2-D3 =%n" +
                        "(==== Краторная булка N-200i ====)%n" +
                        "%n" +
                        "Price: 450,000000%n");

        burger.addIngredient(ingredientFirst);

        Mockito.when(bun.getPrice()).thenReturn(150F);
        Mockito.when(ingredientFirst.getPrice()).thenReturn(150F);
        Mockito.when(bun.getName()).thenReturn("Краторная булка N-200i");
        Mockito.when(ingredientFirst.getName()).thenReturn("Флюоресцентная булка R2-D3");
        Mockito.when(ingredientFirst.getType()).thenReturn(IngredientType.FILLING);

        Assert.assertEquals(result, burger.getReceipt());
    }
}