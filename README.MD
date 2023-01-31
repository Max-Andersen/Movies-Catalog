# Movies Catalog
---
Проект создан в рамках модуля Android-разработки на втором курсе Высшей IT-школы ТГУ, и представляет из себя клиент-серверное приложение-каталог фильмов с возможностями:
- Регистрация пользователя
- Вход и выход из аккаунта
- Добавление фильма в избранное
- Удаление фильма из избранного
- Добавление, редактирование, удаление отзыва на фильм
- Редактирование информации о пользователе

Функциональность приложения диктуется api, [подготовленной преподавателями](https://react-midterm.kreosoft.space/swagger/index.html).
При разработке использовался тулкит декларативной вёрстки [__Jetpack Compose__](https://developer.android.com/jetpack/compose/documentation).

### Архитектурный подход
Это была моя первая попытка реализовать Model-View-ViewModel подход.
_Слой модели_ - Auth, FavoriteMovies, Movie, Review, User репозитории, работающие с сгенерированными классами Retrofit'а по интерфейсам и заранее созданным моделям данных.
_Слой представления_ - _Composable_ функции.
_ViewModel_ - для каждого экрана, кроме SplashScreen создан объект viewModel, содержащий mutableState, а также flow для показа данных в соответствующих View и функции, вызываемые View.



### Используемые библиотеки:
- [Retrofit](https://github.com/square/retrofit) - библиотека, упрощающая взаимодействие с [REST API](https://ru.wikipedia.org/wiki/REST)
- [Paging compose](https://developer.android.com/reference/kotlin/androidx/paging/compose/package-summary) - Интеграция [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) и Jetpack Compose, использовалась для реализации пагинации при отображении списка всех фильмов "посылками" по 6 фильмов, при достижении дна текущего количества фильмов.
- [Lottie Compose](http://airbnb.io/lottie/#/android-compose) - Интеграция [Lottie](https://github.com/airbnb/lottie-android/) и Compose. В банке анимаций Lottie была найдена анимация like/unlike, вставленная на экран фильма.
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) <details><summary>Навигация</summary>С её помощью было построено 2 графа навигации и реализована сама навигация внутри приложения.<br>    Первый граф включает в себя экраны:<br>    - SplashScreen<br>    - SignInScreen<br>    - SignUpScreen<br>    - MovieScreen<br>    - Переход на другой граф<br>    Второй граф включается в себя два экрана: MainScreen и ProfileScreen.<br>    Два графа нужны из-за потребности не везде иметь нижнюю панель навигации.<br></details>
- [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) - Ассинхронной работа с интернет-запросами.
- [Flow Layout](https://google.github.io/accompanist/flowlayout/) - Использовалась для красивого отображения и авто-переносов жанров фильма.<details><summary>Просмотр</summary><img src=https://user-images.githubusercontent.com/44117017/215264005-1c866299-e9c6-45e2-9f42-be2da600b7c3.png height=85></details>

- [Security crypto](https://developer.android.com/jetpack/androidx/releases/security) - для хранения токена пользователя в [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)


### Визуальная составляющая

|__SplashScreen__|__Favorites__|__Galery__|
| ------------- |:-------------:| -----:|
| <img src=https://user-images.githubusercontent.com/44117017/215264744-be28886e-96d2-406d-aa74-192448cdc7a8.gif width=250>      | <img src=https://user-images.githubusercontent.com/44117017/215264755-67c4c53a-eb98-448a-a2ac-df23a2a25d09.gif width=250> | <img src=https://user-images.githubusercontent.com/44117017/215264757-f91049c0-00af-4fcf-900f-05cfaa8e28a4.gif width=250> |

|__Parallax__|__Like/Unlike__ |__Review__|
| ------------- |:-------------:| -----:|
| <img src=https://user-images.githubusercontent.com/44117017/215264764-c975c59b-e63f-4890-a546-c71ef2ba2260.gif width=250>      | <img src=https://user-images.githubusercontent.com/44117017/215264768-981413e5-f7dc-4478-b3be-40a2667e7f1e.gif width=250> | <img src=https://user-images.githubusercontent.com/44117017/215264771-22086537-47d3-48a1-bc4c-9656710dfa6d.gif width=250> |

