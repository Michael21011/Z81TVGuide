package info.z81.z81tvguide;

/**
 * Created by Michael on 24.04.2015.
 */
public  class  Utils {
    public static int getChannelLogoByName(String ChannelName){
        return R.drawable.empty;
        /*
        int id;
        if (ChannelName.equalsIgnoreCase("24 Техно")){
            id = R.drawable.a24tehno;
        }
        else if (ChannelName.equalsIgnoreCase("8 канал")) {
            id = R.drawable.a8moj;
        }
        else if (ChannelName.equalsIgnoreCase("Animal Planet")) {
            id =R.drawable.animalplanet;
        }
        else if (ChannelName.equalsIgnoreCase("Да Винчи")) {
            id =R.drawable.davinci;
        }
        else if (ChannelName.equalsIgnoreCase("Discovery")) {
            id =R.drawable.discovery;
        }
        else if (ChannelName.equalsIgnoreCase("Discovery Science")) {
            id =R.drawable.discoveryscience;
        }
        else if (ChannelName.equalsIgnoreCase("Discovery World")) {
            id =R.drawable.discoveryworld;
        }
        else if (ChannelName.equalsIgnoreCase("Gulli")) {
            id =R.drawable.gulli;
        }
        else if (ChannelName.equalsIgnoreCase("Nat Geo Wild")) {
            id =R.drawable.natgeowild;
        }
        else if (ChannelName.equalsIgnoreCase("National Geographic Channel")) {
            id =R.drawable.nationalgeographic;
        }
        else if (ChannelName.equalsIgnoreCase("Nautical Channel")) {
            id =R.drawable.nautical;
        }
        else if (ChannelName.equalsIgnoreCase("RU.TV")) {
            id =R.drawable.rutv;
        }
        else if (ChannelName.equalsIgnoreCase("Travel and Adventure")) {
            id =R.drawable.traveladventure;
        }
        else if (ChannelName.equalsIgnoreCase("ТВ 1000-Экшн")) {
            id =R.drawable.tv1000action;
        }
        else if (ChannelName.equalsIgnoreCase("TV 1000 Comedy")) {
            id =R.drawable.tv1000comedy;
        }
        else if (ChannelName.equalsIgnoreCase("Zee Russia")) {
            id =R.drawable.zeetv;
        }
        else if (ChannelName.equalsIgnoreCase("Виасат Эксплорер")) {
            id =R.drawable.viasatexplorer;
        }
        else if (ChannelName.equalsIgnoreCase("Виасат Хистори")) {
            id =R.drawable.viasathistory;
        }
        else if (ChannelName.equalsIgnoreCase("Виасат Нэйчер")) {
            id =R.drawable.viasatnature;
        }
        else if (ChannelName.equalsIgnoreCase("Спортивный телеканал Виасат")) {
            id =R.drawable.viasatsport;
        }
        else if (ChannelName.equalsIgnoreCase("Авто Плюс")) {
            id =R.drawable.autoplus;
        }
        else if (ChannelName.equalsIgnoreCase("Беларусь 1")) {
            id =R.drawable.belarus1;
        }
        else if (ChannelName.equalsIgnoreCase("Беларусь 2")) {
            id =R.drawable.belarus2;
        }
        else if (ChannelName.equalsIgnoreCase("Беларусь 3")) {
            id =R.drawable.belarus3;
        }
        else if (ChannelName.equalsIgnoreCase("Беларусь 5")) {
            id =R.drawable.belarus5;
        }
        else if (ChannelName.equalsIgnoreCase("БЕЛМУЗТВ")) {
            id =R.drawable.belmuztv;
        }
        else if (ChannelName.equalsIgnoreCase("ВТВ")) {
            id =R.drawable.vtv;
        }
        else if (ChannelName.equalsIgnoreCase("Детский мир")) {
            id =R.drawable.detskijmir;
        }
        else if (ChannelName.equalsIgnoreCase("Жест")) {
            id =R.drawable.zhest;
        }
        else if (ChannelName.equalsIgnoreCase("Карусель")) {
            id =R.drawable.karusel;
        }
        else if (ChannelName.equalsIgnoreCase("Комедия ТВ")) {
            id =R.drawable.camedytv;
        }
        else if (ChannelName.equalsIgnoreCase("Мать и дитя")) {
            id =R.drawable.momditya;
        }
        else if (ChannelName.equalsIgnoreCase("Минск 24ДОК")) {
            id =R.drawable.minsk24doc;
        }
        else if (ChannelName.equalsIgnoreCase("Мир")) {
            id =R.drawable.mir;
        }
        else if (ChannelName.equalsIgnoreCase("Моя Планета")) {
            id =R.drawable.myplanet;
        }
        else if (ChannelName.equalsIgnoreCase("Наше кино")) {
            id =R.drawable.nashekino;
        }
        else if (ChannelName.equalsIgnoreCase("НТВ Беларусь")) {
            id =R.drawable.ntvbelarus;
        }
        else if (ChannelName.equalsIgnoreCase("НТВ-ПЛЮС КИНО ПЛЮС")) {
            id =R.drawable.ntvkino;
        }
        else if (ChannelName.equalsIgnoreCase("ОНТ")) {
            id =R.drawable.ont;
        }
        else if (ChannelName.equalsIgnoreCase("Охота и рыбалка")) {
            id =R.drawable.oxotarybalka;
        }
        else if (ChannelName.equalsIgnoreCase("БелБизнесЧенел (РБК)")) {
            id =R.drawable.rbk;
        }
        else if (ChannelName.equalsIgnoreCase("Россия-Культура")) {
            id =R.drawable.rossiyaculture;
        }
        else if (ChannelName.equalsIgnoreCase("РТР Беларусь")) {
            id =R.drawable.rtrbelarus;
        }
        else if (ChannelName.equalsIgnoreCase("Русское экстремальное телевидение")) {
            id =R.drawable.russianextrim;
        }
        else if (ChannelName.equalsIgnoreCase("Русский иллюзион")) {
            id =R.drawable.russkijilluzion;
        }
        else if (ChannelName.equalsIgnoreCase("Сетанта спорт")) {
            id =R.drawable.setantasport;
        }
        else if (ChannelName.equalsIgnoreCase("Союз")) {
            id =R.drawable.souz;
        }
        else if (ChannelName.equalsIgnoreCase("СТВ")) {
            id =R.drawable.ctv;
        }
        else if (ChannelName.equalsIgnoreCase("ТВ 3")) {
            id =R.drawable.tv3;
        }
        else if (ChannelName.equalsIgnoreCase("ТВ 1000")) {
            id =R.drawable.tv1000;
        }
        else if (ChannelName.equalsIgnoreCase("ТВ-1000. Русское кино")) {
            id =R.drawable.tv1000russkoekino;
        }
        else if (ChannelName.equalsIgnoreCase("Телеклуб")) {
            id =R.drawable.teleclub;
        }
        else if (ChannelName.equalsIgnoreCase("Усадьба-ТВ")) {
            id =R.drawable.usadba;
        }
        else if (ChannelName.equalsIgnoreCase("Перец I")) {
            id =R.drawable.perec;
        }

        else if (ChannelName.equalsIgnoreCase("Мультимания")) {
            id =R.drawable.multimania;
        }

        else if (ChannelName.equalsIgnoreCase("Домашние животные")) {
            id =R.drawable.home_animale;
        }
        else if (ChannelName.equalsIgnoreCase("Zee Russia")) {
            id =R.drawable.zeetv;
        }
        else if (ChannelName.equalsIgnoreCase("TV 1000 Comedy")) {
            id =R.drawable.tv1000comedy;
        }
        else if (ChannelName.equalsIgnoreCase("Парк развлечений")) {
            id =R.drawable.park;
        }
        else if (ChannelName.equalsIgnoreCase("ДОМАШНИЕ ЖИВОТНЫЕ")) {
            id =R.drawable.home_animale;
        }
        else if (ChannelName.equalsIgnoreCase("Любимое кино")) {
            id =R.drawable.lubimoe_kino;
        }
        else if (ChannelName.equalsIgnoreCase("Наука")) {
            id =R.drawable.nauka_2_0;
        }
        else if (ChannelName.equalsIgnoreCase("ТНТ Int")) {
            id =R.drawable.tnt;
        }
        else
            id = R.drawable.empty;



        return id;
        */
    }
}
