package info.z81.z81tvguide;

/**
 * Created by michael on 05.12.15.
 */


import java.util.HashMap;

public class ChannelNumbers {
    private HashMap<String, Integer> digitalMap;

    public ChannelNumbers() {
        digitalMap = new HashMap<>(200);
        digitalMap.put("JimJam", -1);
        digitalMap.put("Boomerang", -1);
        digitalMap.put("Nat Geo Wild Россия", -1);
        digitalMap.put("Психология21", -1);
        digitalMap.put("Paramount Comedy Россия", -1);
        digitalMap.put("Nick Jr", -1);
        digitalMap.put("НСТ", -1);
        digitalMap.put("VH1 Europe", -1);
        digitalMap.put("Investigation Discovery", -1);
        digitalMap.put("STV", -1);
        digitalMap.put("Боец", -1);
        digitalMap.put("Феникс+Кино", -1);
        digitalMap.put("Драйв", -1);
        digitalMap.put("Discovery World", -1);
        digitalMap.put("Детский", -1);
        digitalMap.put("Travel+Adventure", -1);
        digitalMap.put("Успех", -1);
        digitalMap.put("Русский экстрим", -1);
        digitalMap.put("Luxury World", -1);
        digitalMap.put("ZooПарк", -1);
        digitalMap.put("NANO TV", -1);
        digitalMap.put("MTV Россия", -1);
        digitalMap.put("Nickelodeon Россия", -1);
        digitalMap.put("Наука 2.0", -1);
        digitalMap.put("Сетанта Спорт", -1);
        digitalMap.put("Мужской", -1);
        digitalMap.put("Совершенно секретно", -1);
        digitalMap.put("Иллюзион+", -1);
        digitalMap.put("Еврокино", -1);
        digitalMap.put("Кто есть кто", -1);
        digitalMap.put("Discovery Science", -1);
        digitalMap.put("Мама", -1);
        digitalMap.put("Ocean TV", -1);
        digitalMap.put("Мультимания", -1);
        digitalMap.put("Mezzo", -1);
        digitalMap.put("Парк развлечений", -1);
        digitalMap.put("Здоровое ТВ", -1);
        digitalMap.put("ID Xtra (Россия)", -1);
        digitalMap.put("Setanta Sports +", -1);
        digitalMap.put("Русская ночь", -1);
        digitalMap.put("ТВ XXI", -1);
        digitalMap.put("Paramount channel Россия", -1);
        digitalMap.put("НТВ+ Премьера", -1);
        digitalMap.put("AMC", -1);
        digitalMap.put("Cartoon network", -1);
        digitalMap.put("КХЛ ТВ", -1);
        digitalMap.put("Наше новое кино", -1);
        digitalMap.put("НТВ+ Кинохит", -1);
        digitalMap.put("Беларусь 1", -1);
        digitalMap.put("ОНТ", -1);
        digitalMap.put("СТВ", -1);
        digitalMap.put("Мир", -1);
        digitalMap.put("РТР-Беларусь", -1);
        digitalMap.put("НТВ-Беларусь", -1);
        digitalMap.put("Беларусь 2", -1);
        digitalMap.put("Беларусь 3", -1);
        digitalMap.put("ТВ3 Беларусь", -1);
        digitalMap.put("Cinema (Космос ТВ)", -1);
        digitalMap.put("РБК ТВ", -1);
        digitalMap.put("Euronews", 10);
        digitalMap.put("Беларусь 5", -1);
        digitalMap.put("Дом кино", -1);
        digitalMap.put("Кино Плюс", -1);
        digitalMap.put("CBS Drama", -1);
        digitalMap.put("Карусель International", -1);
        digitalMap.put("RTVI", -1);
        digitalMap.put("National Geographic Россия", -1);
        digitalMap.put("ТНТ-International (Беларусь)", -1);
        digitalMap.put("CBS Reality", -1);
        digitalMap.put("Discovery Восточная Европа", -1);
        digitalMap.put("TLC Pan Regional", -1);
        digitalMap.put("Animal planet Europe", -1);
        digitalMap.put("Телекафе", -1);
        digitalMap.put("Культура", -1);
        digitalMap.put("Охота и рыбалка", -1);
        digitalMap.put("Усадьба", -1);
        digitalMap.put("Eurosport", -1);
        digitalMap.put("Eurosport 2 North-East", -1);
        digitalMap.put("Музыка Первого", -1);
        digitalMap.put("ВТВ", 9);
        digitalMap.put("БелМузТВ", -1);
        digitalMap.put("MCM Top", -1);
        digitalMap.put("Шансон ТВ", -1);
        digitalMap.put("Кухня ТВ", -1);
        digitalMap.put("Союз (Екатеринбург)", -1);
        digitalMap.put("Авто24", -1);
        digitalMap.put("Жест (Космос ТВ)", -1);
        digitalMap.put("Моя планета", -1);
        digitalMap.put("Время", -1);
        digitalMap.put("Русский бестселлер (международный)", -1);
        digitalMap.put("Europa Plus TV", -1);
        digitalMap.put("ТРО", -1);
        digitalMap.put("Оружие", -1);
        digitalMap.put("Комедия ТВ", -1);
        digitalMap.put("8 канал (Беларусь)", -1);
        digitalMap.put("Ностальгия", -1);
        digitalMap.put("Мульт", -1);
        digitalMap.put("Загородная жизнь", -1);
        digitalMap.put("Театр", -1);
        digitalMap.put("9 волна", -1);
        digitalMap.put("ТВ-3 Россия", -1);
        digitalMap.put("2х2", -1);
        digitalMap.put("Русский иллюзион", -1);
        digitalMap.put("Еда", -1);
    }

    public int GetByName(String ChannelName) {
        if (digitalMap.containsKey(ChannelName))
            return digitalMap.get(ChannelName);
        else
            return -1;
    }
}
