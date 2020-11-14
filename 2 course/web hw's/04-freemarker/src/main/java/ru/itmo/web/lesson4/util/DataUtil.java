package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Color;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
        private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Task 1", "Улучшите страницу профиля пользователя /user?handle=?. \n" +
                    "Результат должен быть похож на такой:\n", 6),
            new Post(2, "Task 4", "Поддержите новый объект предметной области Post.\n" +
                    "У Post должно быть четыре поля id (long), title (String), text (String) и user_id (long). \n" +
                    "Создайте в системе по аналогии с User серию постов с разумными содержаниями (модифицируйте DataUtil).\n" +
                    " Используя вашу разметку из второго ДЗ отобразите на главной список всех постов в обратном порядке (от последнего к первому). \n" +
                    "Если длина text превышает 250 символов, то обрезайте его и используйте символ многоточия в конце (сокращайте длинные тексты).\n" +
                    " Страницу со списком пользователей перенесите в отдельную страницу /users.\n" +
                    " Измените её разметку так, чтобы использовать вёрстку таблицы из второго ДЗ для их отображения.\n" +
                    " Добавьте в меню пункт USERS.\n", 1),
            new Post(3, "Task 5", "В сайдбаре в блоках Information в сайдбаре тоже отображайте тексты постов (тоже не более 250 символов).\n" +
                    " Фразу Information замените на “Post #?” (его id). \n" +
                    "По ссылке View all уводите на страницу /post?post_id=?, на которой должен быть уже один не сокращённый пост.\n" +
                    " Таким образом, посты у вас будут отображаться на двух страницах (/index и /post?post_id=?).\n" +
                    " Не допускайте copy-paste: они обе должны пользоваться одним макросом отображения одного поста.\n" +
                    " На странице с одним постом текст поста сокращать не надо (ваш макрос должен поддерживать как сокращенный способ отображения, так и полный).\n", 6),
            new Post(4, "Task 6", "Добавьте пользователю свойство color (как цвет на Codeforces), которое должно быть enum с одним из значений: {RED, GREEN, BLUE}. Измените userlink, чтобы он отображал\n" +
                    "пользователей по окрашенному хэндлу (прям как на Codeforces).\n" +
                    " То есть уберите подчеркивание, поменяйте чуток шрифт, навесьте правильный цвет в зависимости от color.\n" +
                    " Старый режим тоже сохраните, сделав дополнительный параметр у userlink (назовите его nameOnly).\n", 9)

            );

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.RED,
                    new ArrayList<Integer>(){{add(1);}}),
            new User(6, "pashka", "Pavel Mavrin", Color.GREEN,
                    new ArrayList<Integer>(){{add(0);add(2);}}),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.BLUE,
                    new ArrayList<Integer>(){{add(3);}}),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED,
                    new ArrayList<>())
    );
    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
