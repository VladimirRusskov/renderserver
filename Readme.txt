    2. Сервер. Принимает от клиента запросы и обрабатывает их.
        ◦ Регистрация пользователя с занесением его в базу данных.
        ◦ Создание новой задачи для конкретного пользователя с занесением ее в базу данных.
        ◦ Обработка запроса списка текущих задач.
 
*Задача - у нас задача, это запуск некого приложения на стороне сервера + запись в базе данных об этом. Вы можете ограничиться только записью в базе данных.
 
После создания задачи ей присваивается статус RENDERING, по истечению случайного количества времени от 1 до 5 минут она считается выполненной, и ей присваивается статус COMPLETE. 
 
По какому событию в клиенте будет обновляться список задач со статусами - выбор за вами. Каким образом будет реализована связь между пользователем и задачами, а также: будете ли вы реализовывать авторизацию пользователя, или будете иным образом выбирать задачи пользователя - тоже выбор за вами.
