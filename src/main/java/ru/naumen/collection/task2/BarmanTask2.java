package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class Ticket {
 *     private long id;
 *     private String client;
 *     …
 * }</pre>
 * <p>Разработать программу для бармена в холле огромного концертного зала.
 * Зрители в кассе покупают билет (класс Ticket), на котором указан идентификатор билета (id) и имя зрителя.
 * При этом, есть возможность докупить наборы разных товаров (комбо-обед): нет товаров, напитки, еда и напитки.
 * Доп. услуги оформляются через интернет и привязываются к билету, но хранятся отдельно от билета
 * (нельзя добавить товары в класс Ticket).</p>
 * <p>Бармен сканирует билет и получает объект Ticket. По этому объекту нужно уметь
 * находить необходимые товары по номеру билета. И делать это нужно очень быстро,
 * ведь нужно как можно быстрее всех накормить.</p>
 * <p>
 * См. {@link Ticket}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class BarmanTask2 {

    public List<Product> findProductsByTicket(Ticket ticket, Map<Long, Combo> orders) {
        List<Product> result = new LinkedList<>(); // linked list для быстрого добавления продуктов
        Map<Long, Combo> ordersHM = new HashMap<>(orders); // hash map для быстрого поиска заказа
        Combo combo = ordersHM.get(ticket.getId());
        switch (combo) {
            case Drinks -> {
                result.add(Product.Drink);
                result.add(Product.Drink);
            }
            case FoodAndDrinks -> {
                result.add(Product.Food);
                result.add(Product.Drink);
                result.add(Product.Drink);
            }
        }
        return result.stream().toList();
        /*
        * Сложность функции - O(1) = 4
        * Мы находим заказ быстро благодаря использованию hash map (находим за O(1))
        * Далее наполняем заказ.
        *
        * LinkedList - для быстрого добавления продуктов
        * HashMap - для быстрого поиска заказа
        * */
    }

}
