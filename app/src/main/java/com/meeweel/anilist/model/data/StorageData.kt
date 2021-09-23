package com.meeweel.anilist.model.data

import android.view.View
import com.meeweel.anilist.R

private var repositoryOfAnime: MutableList<Anime> = mutableListOf(
    Anime("Attack on Titan", "Уже многие годы человечество ведёт борьбу с титанами — огромными существами, которые не обладают особым интеллектом, зато едят людей и получают от этого удовольствие. После продолжительной борьбы остатки человечества построили высокую стену, окружившую страну людей, через которую титаны пройти не могли. С тех пор прошло сто лет, люди мирно живут под защитой стены. Но однажды подросток Эрэн и его сводная сестра Микаса становятся свидетелями страшного события — участок стены разрушается супертитаном, появившимся прямо из воздуха. Титаны нападают на город, и дети в ужасе видят, как один из монстров заживо съедает их мать. Эрэн клянётся, что убьёт всех титанов и отомстит за человечество.",
    R.drawable.titan),
    Anime("Death Note", "Старшекласснику Лайту Ягами в руки попадает тетрадь синигами Рюка. Каждый человек, чьё имя записать в эту тетрадку, умрёт, поэтому Лайт решает бороться со злом на земле.",
    R.drawable.death),
    Anime("OnePunchMan", "Парень по имени Сайтама живёт в мире, иронично похожем на наш. Ему 25, он лыс и прекрасен и к тому же силен настолько, что с одного удара аннигилирует всё, что представляет опасность для человечества. Он ищет себя в этой жизни, попутно раздавая подзатыльники монстрам и злодеям.",
    R.drawable.punch),
    Anime("Tokio Ghoul", "С обычным студентом Кэном Канэки случается беда, парень попадает в больницу. Но на этом неприятности не заканчиваются: ему пересаживают органы гулей – существ, поедающих плоть людей. После злосчастной операции Канэки становится одним из чудовищ, пытается стать своим, но для людей он теперь изгой, обреченный на уничтожение.",
    R.drawable.ghoul),
    Anime("Naruto", "История о начинающих ниндзя, только-только окончивших академию и получивших удостоверения в виде повязок. Сразу же после окончания герои попадают во множество переделок и опасных ситуаций, находят друзей, встречают врагов.",
    R.drawable.naruto),
    Anime("Sword art Online", "Опытному геймеру Кирито повезло поучаствовать в бета-тестировании самой ожидаемой компьютерной игры нового поколения - Sword Art Online. Когда наконец на прилавках появились диски с финальной версией, тысячи геймеров устремились в совершенный виртуальный мир MMORPG. Там их ждал неприятный сюрприз - гейм-мастер объявил, что выйти из игры по собственной воле невозможно. Единственный шанс это сделать - пройти все сто уровней до конца. А смерть в игре означает смерть и в реальной жизни.",
    R.drawable.sao),
    Anime("Slayer Demon", "Эпоха Тайсё. Ещё с древних времён ходят слухи, что в лесу обитают человекоподобные демоны, которые питаются людьми и выискивают по ночам новых жертв. Тандзиро Камадо — старший сын в семье, потерявший отца и взявший на себя заботу о родных. Однажды он уходит в соседний город, чтобы продать древесный уголь. Вернувшись утром, парень обнаруживает перед собой страшную картину: вся родня зверски убита, а единственная выжившая - младшая сестра Нэдзуко, обращённая в демона, но пока не потерявшая человечность. С этого момента начинается долгое и опасное путешествие Тандзиро и Нэдзуко, в котором мальчик намерен разыскать убийцу и узнать способ исцеления сестры.",
    R.drawable.dimon),
    Anime("Dr. Stone", "В один роковой день всё человечество превратилось в камень. Много тысячелетий спустя старшеклассник Тайдзю освобождается от окаменения и оказывается в окружении статуй. Однако он не одинок: его другу Сэнку также удалось сбросить каменную оболочку, и теперь, используя научные знания, они начинают восстанавливать былую цивилизацию.",
    R.drawable.stone),
    Anime(title = "Anime 9"),
    Anime()
)
private var repositoryOfWatchedAnime: MutableList<Anime> = mutableListOf(
)
private var repositoryOfNotWatchedAnime: MutableList<Anime> = mutableListOf(
)
private var repositoryOfWantedAnime: MutableList<Anime> = mutableListOf(
)
private var repositoryOfUnwantedAnime: MutableList<Anime> = mutableListOf(
)

fun addAnime(title: String = "Anime", description: String = "Description", image: Int = R.drawable.anig,
             author: String = "Shikamaru Usman", genre: String = "Isekai", data: String = "21.01.23") {
    val newAnime = Anime(title, description, image, author, genre, data)
    repositoryOfAnime.add(newAnime)
}
fun mainToNotWatched(anime: Anime) {
    for (item in repositoryOfAnime) {
        if (item == anime) {
            repositoryOfNotWatchedAnime.add(item)
            repositoryOfAnime.remove(item)
            break
        }
    }
}
fun mainToWatched(anime: Anime) {
    for (item in repositoryOfAnime) {
        if (item == anime) {
            repositoryOfWatchedAnime.add(item)
            repositoryOfAnime.remove(item)
            break
        }
    }
}
fun notWatchedToWanted(anime: Anime) {
    for (item in repositoryOfNotWatchedAnime) {
        if (item == anime) {
            repositoryOfWantedAnime.add(item)
            repositoryOfNotWatchedAnime.remove(item)
            break
        }
    }
}
fun notWatchedToUnwanted(anime: Anime) {
    for (item in repositoryOfNotWatchedAnime) {
        if (item == anime) {
            repositoryOfUnwantedAnime.add(item)
            repositoryOfNotWatchedAnime.remove(item)
            break
        }
    }
}
fun wantedToWatched(anime: Anime) {
    for (item in repositoryOfWantedAnime) {
        if (item == anime) {
            repositoryOfWatchedAnime.add(item)
            repositoryOfWantedAnime.remove(item)
            break
        }
    }
}
fun getRepo() = repositoryOfAnime
fun getWatchedRepo() = repositoryOfWatchedAnime
fun getNotWatchedRepo() = repositoryOfNotWatchedAnime
fun getWantedRepo() = repositoryOfWantedAnime
fun getUnwantedRepo() = repositoryOfUnwantedAnime
