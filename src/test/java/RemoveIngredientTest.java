import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.Bun;
import ru.yandex.practicum.Burger;
import ru.yandex.practicum.Ingredient;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class RemoveIngredientTest {

    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredientFirst;
    private Burger burger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void testRemoveIngredient() {
        when(ingredientFirst.getType()).thenReturn(null); // Мы можем задать любые значения, потому что они не влияют на этот тест
        when(ingredientFirst.getName()).thenReturn("");
        when(ingredientFirst.getPrice()).thenReturn(-1f);

        // Действие
        burger.addIngredient(ingredientFirst);
        burger.removeIngredient(0);

        // Проверка результата
        assertTrue("Ошибка: ингредиент не удалён", burger.ingredients.isEmpty());
    }
}
