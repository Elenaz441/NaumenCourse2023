package shopping;

import customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import product.Product;
import product.ProductDao;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование {@link ShoppingService}
 */
class ShoppingServiceTest {

    private final ProductDao productDaoMock = Mockito.mock(ProductDao.class);

    private final ShoppingService shoppingService = new ShoppingServiceImpl(productDaoMock);

    /**
     * Здесь нечего тестировать,
     * так как функцию содержит всего одну строчку,
     * где вызывается конструктор {@link Cart},
     * здесь не стоит задачи протестировать класс {@link Cart}
     */
    @Test
    void getCart() {
        // Почему создаётся новая корзина, хотя метод называется get?
        // Тогда метод должен называться createCart (возможно логическая ошибка здесь?)

        // И ещё возник вопрос. Почему мы не можем добавить товар в корзину с тем количеством, которое осталось?
        // То есть почему в классе Cart в сточке 58 стоит "<=", а не просто "<"?
        // (Пользователь захочет купить 2 товара и на складе осталось 2 товара,
        // и у пользователя не получится купить этот товар в таком количестве)

        // Здесь нечего тестировать...
    }

    /**
     * Здесь нечего тестировать,
     * так как функцию содержит всего одну строчку,
     * где вызывается метод из {@link ProductDao},
     * а поведение {@link ProductDao} здесь мы задаем сами
     */
    @Test
    void getAllProducts() {
        // Здесь нечего тестировать...
    }

    /**
     * Здесь нечего тестировать,
     * так как функцию содержит всего одну строчку,
     * где вызывается метод из {@link ProductDao},
     * а поведение {@link ProductDao} здесь мы задаем сами
     */
    @Test
    void getProductByName() {
        // Здесь нечего тестировать...
    }

    /**
     * Простая проверка функции {@link ShoppingService#buy(Cart)}
     */
    @Test
    void buy() throws BuyException {
        Product product1 = new Product();
        product1.setName("p1");
        product1.addCount(2);
        Product product2 = new Product();
        product2.setName("p2");
        product2.addCount(2);
        Customer customer = new Customer(1L, "1111111");
        Cart cart = new Cart(customer);
        cart.add(product1, 1);
        cart.add(product2, 1);

        boolean result = shoppingService.buy(cart);

        assertTrue(result);
        assertEquals(1, product1.getCount());
        assertEquals(1, product2.getCount());
    }

    /**
     * Тест случая, когда нет товара в нужном количестве
     */
    @Test
    void buyIfNotNeededCount() throws BuyException {
        Product product1 = new Product();
        product1.setName("p1");
        product1.addCount(3);

        Customer customer1 = new Customer(1L, "1111111");
        Customer customer2 = new Customer(2L, "2222222");

        Cart cart1 = new Cart(customer1);
        cart1.add(product1, 2);
        Cart cart2 = new Cart(customer2);
        cart2.add(product1, 2);

        boolean firstBuy = shoppingService.buy(cart1);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            boolean secondBuy = shoppingService.buy(cart2);
            assertFalse(secondBuy);
        });
        assertTrue(firstBuy);
        Assertions.assertEquals("В наличии нет необходимого количества товара p1", exception.getMessage());
    }

    /**
     * Проверка случая, когда корзина пуста
     */
    @Test
    void buyIfCartIsEmpty() throws BuyException {
        Customer customer = new Customer(1L, "1111111");
        Cart cart = new Cart(customer);
        boolean result = shoppingService.buy(cart);
        assertFalse(result);
    }
}