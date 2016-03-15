package info.z81.z81tvguide;

/**
 * Created by michael on 05.12.15.
 */


import java.io.Serializable;
import java.util.HashMap;

public  class DefaultChannelNumbers {
    private HashMap<String, Integer> digitalMap;

    public DefaultChannelNumbers() {
        digitalMap = new HashMap<>(200);
        digitalMap.put("24 Техно", 35);
        digitalMap.put("8 канал", 1);
        digitalMap.put("Gulli", 13);
        digitalMap.put("MIНСК ТV", 20);
        digitalMap.put("Nautical Channel", 23);
        digitalMap.put("RU.TV", 30);
        digitalMap.put("Travel and Adventure", 38);
        digitalMap.put("TV 1000 Comedy", 43);
        digitalMap.put("Zee Russia", 50);
        digitalMap.put("Авто Плюс", 51);
        digitalMap.put("Беларусь 1", 3);
        digitalMap.put("Беларусь 2", 4);
        digitalMap.put("Беларусь 3", 5);
        digitalMap.put("Беларусь 5", 6);
        digitalMap.put("БелБизнесЧенел (РБК)", -1);
        digitalMap.put("БЕЛМУЗТВ", 7);
        digitalMap.put("Виасат Нэйчер", 48);
        digitalMap.put("Виасат Хистори", 47);
        digitalMap.put("Виасат Эксплорер", 46);
        digitalMap.put("ВТВ", 9);
        digitalMap.put("Да Винчи", -1);
        digitalMap.put("Детский мир", -1);
        digitalMap.put("ДОМАШНИЕ ЖИВОТНЫЕ", 52);
        digitalMap.put("Жест", -1);
        digitalMap.put("Карусель", -1);
        digitalMap.put("Комедия ТВ", -1);
        digitalMap.put("Любимое кино", -1);
        digitalMap.put("Мать и дитя", 19);
        digitalMap.put("Мир", 21);
        digitalMap.put("Моя Планета", 22);
        digitalMap.put("Наука", 24);
        digitalMap.put("НТВ Беларусь", 25);
        digitalMap.put("НТВ-ПЛЮС КИНО ПЛЮС", 53);
        digitalMap.put("ОНТ", 26);
        digitalMap.put("Охота и рыбалка", -1);
        digitalMap.put("Парк развлечений", 27);
        digitalMap.put("Перец I", 28);
        digitalMap.put("Россия-Культура", 59);
        digitalMap.put("РТР Беларусь", 29);
        digitalMap.put("Русский иллюзион", 31);
        digitalMap.put("Русское экстремальное телевидение", -1);
        digitalMap.put("Сетанта Спорт", 32);
        digitalMap.put("Союз", 60);
        digitalMap.put("Спортивный телеканал Виасат", -1);
        digitalMap.put("СТВ", 34);
        digitalMap.put("ТВ 1000", -1);
        digitalMap.put("ТВ 1000-Экшн", -1);
        digitalMap.put("ТВ 3", 45);
        digitalMap.put("ТВ-1000. Русское кино", -1);
        digitalMap.put("Телеклуб", -1);
        digitalMap.put("ТНТ Int", 36);
        digitalMap.put("Усадьба-ТВ", 62);


    }

    public int GetByName(String ChannelName) {
        if (digitalMap.containsKey(ChannelName))
            return digitalMap.get(ChannelName);
        else
            return -1;
    }
}
