package ru.urfu;

/**
 * Автобус
 */
public class Bus implements Transport {

    /**
     * Принимает номер маршрута и человека
     */
    public Bus(String routeNumber, Person person) {
        // TODO
    }

    /**
     * Текущее местоположение
     */
    @Override
    public Position getPosition() {
        return null;
    }

    /**
     * Подвезти человека до текущей точки
     */
    @Override
    public void ride(Person person, Position destination) {
        // TODO
    }
}
