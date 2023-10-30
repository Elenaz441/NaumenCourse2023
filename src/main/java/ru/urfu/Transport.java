package ru.urfu;

/**
 * Интерфейс транспорта
 */
public interface Transport extends Positioned {

    /**
     * Подвезти человека до текущей точки
     */
    void ride(Person person, Position destination);

}
