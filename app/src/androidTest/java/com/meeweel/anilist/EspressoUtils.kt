package com.meeweel.anilist

import android.content.Context
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.io.*
import java.nio.channels.FileChannel

object EspressoUtils {

    val isRu get() = InstrumentationRegistry.getInstrumentation().context.resources.getBoolean(R.bool.isRussian)
    private const val CLICK_DELAY = 150L

    // Constants
    const val DB_NAME = "Repository.db"


    /** Кнопка назад */
    fun pressBack() {
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }

    /** isVisible */
    fun ViewInteraction.isVisible() {
        check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /** isNotVisible */
    fun ViewInteraction.isNotVisible() {
        check { view, _ -> (view as View).visibility != View.VISIBLE }
    }

    /** isClickable */
    fun ViewInteraction.isClickable() {
        check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    /** isNotClickable */
    fun ViewInteraction.isNotClickable() {
        check { view, _ ->
            assert(!(view as View).isClickable)
        }
    }

    /** Нажать */
    fun ViewInteraction.click() {
        perform(ViewActions.click())
        delay(CLICK_DELAY)
    }

    /** Найти вью по id */
    fun findViewById(id: Int): ViewInteraction {
        return Espresso.onView(ViewMatchers.withId(id))
    }

    /** Найти по тексту карточку в RecyclerView с прокруткой */
    fun findCardByText(text: String): ViewInteraction {
        scrollToCardByText(text)
        return Espresso.onView(withChild(withChild(withText(text))))
    }

    /**
     * Найти итем PopupMenu по тексту
     */
    fun findPopupItemByText(text: String) : ViewInteraction {
        return Espresso.onView(
            Matchers.allOf(
                withText(text),
                withId(com.google.android.material.R.id.title),
                withParent(withParent(withId(androidx.appcompat.R.id.content)))
            )
        )
    }

    fun findCardButtonByAnimeTitleAndId(
        animeTitle: String,
        buttonId: Int
    ): ViewInteraction {
        scrollToCardByText(animeTitle)
        return Espresso.onView(Matchers.allOf(
            withParent(withChild(withText(animeTitle))),
            withId(buttonId)
        ))
    }

    /** Найти вью по тексту */
    fun findViewByText(text: String): ViewInteraction {
        return Espresso.onView(ViewMatchers.withText(text))
    }

    /**
     *  Прокрутить Recycler до карточки с заданным текстом
     */
    fun scrollToCardByText(text: String) {
        Espresso.onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(text)
                    )
                )
            )
    }

    /**
     *  Получить путь к базе данных с названием самой базы данных
     *  Пример: "data/databases/Repository.db"
     */
    fun getFullPathDB(nameDB: String = DB_NAME) {
        InstrumentationRegistry.getInstrumentation().targetContext.getDatabasePath(nameDB).absolutePath
    }

    /**
     *  Получить путь к базе данных без названия самой базы данных
     *  Пример: "data/databases/"
     */
    fun getPathDB(nameDB: String = DB_NAME): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getDatabasePath(nameDB).absolutePath
    }

    /**
     *  Загрузить тестовую базу данных из директории assets
     */
    fun insertDB(fromNameDB: String = DB_NAME) {
        val pathDB: String = deleteDB()
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        val inputStream: InputStream = context.assets.open(fromNameDB)
        val outputStream: OutputStream = FileOutputStream(pathDB)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    /**
     *  Удалить базу данных
     */
    fun deleteDB(dbName: String = DB_NAME): String {
        val path = getPathDB()
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(dbName)
        return path
    }

    // льтернативный способ загрузки БД в
    private fun importDBFile(context: Context) {
        val importDB: File = context.getDatabasePath(DB_NAME)
        val dataDir: String = Environment.getDataDirectory().path
        val packageName: String = context.packageName
        val importDir = File("$dataDir/data/$packageName/databases/")
        if (!importDir.exists()) {
            Toast.makeText(
                context,
                "There was a problem importing the Database",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        val importFile = File(importDir.path + "/" + importDB.name)
        try {
            importFile.createNewFile()
            copyDB(importDB, importFile)
            Toast.makeText(context, "Import Successful", Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Toast.makeText(
                context,
                "There was a problem importing the Database",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    @Throws(IOException::class)
    private fun copyDB(from: File, to: File) {
        val inChannel: FileChannel = FileInputStream(from).channel
        val outChannel: FileChannel = FileOutputStream(to).channel
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel)
        } finally {
            inChannel.close()
            outChannel.close()
        }
    }

    /** Задержка без остановки главного потока */
    fun delay(millis: Long) {
        Espresso.onView(ViewMatchers.isRoot()).perform(delayAction(millis))
    }

    // ViewAction для задержки без остановки главного потока
    private fun delayAction(time: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $time seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(time)
            }
        }
    }
}