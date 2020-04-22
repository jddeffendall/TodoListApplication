package utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import domain.TodoItem;
import exceptions.IdExistsException;
import exceptions.IdNotExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilsTest {

    DatabaseUtils todoManager;

    @BeforeEach
    void setupTodoManagerAndAddTodos() throws SQLException, IOException {

        var testConnection = new JdbcConnectionSource("jdbc:sqlite:test.db");
        TableUtils.createTableIfNotExists(testConnection, TodoItem.class);
        Dao<TodoItem, String> quoteDao = DaoManager.createDao(testConnection, TodoItem.class);
        for (int i = 0; i < 2; i++) {
            quoteDao.create(new TodoItem("Title", Integer.toString(i), "04 22 2020 12:00", "04 25 2020 12:00", "false", "false", Integer.toString(i), "Incomplete"));
        }
        testConnection.close();

        todoManager = new DatabaseUtils("test.db");
    }

    @Test
    void getALlQuotes() {
        List<TodoItem> items = todoManager.getAllItems();

        for (int i=0; i<2; i++) {
            TodoItem tempTodo = items.get(i);
            assertEquals(Integer.toString(i), tempTodo.getOwner());
        }
    }

    @Test
    void addItemToDB() {
        var item = new TodoItem("Test", "TestOwner", "04 24 2020 12:00", "04 24 2020 23:59", "false", "false", "7", "Incomplete");
        var result = todoManager.addItemToDB(item);
        assertEquals(item.getTitle(), result.getTitle());
        assertEquals(item.getOwner(), result.getOwner());
    }

    @Test
    void addItem_Failure() {
        var item = new TodoItem("Title", Integer.toString(0), "04 22 2020 12:00", "04 25 2020 12:00", "false", "false", Integer.toString(0), "Incomplete");
        assertThrows(IdExistsException.class, () -> {
            var result = todoManager.addItemToDB(item);
        });
    }

    @Test
    void deleteItem_Failure() {
        var item = new TodoItem("Test", "TestOwner", "04 24 2020 12:00", "04 24 2020 23:59", "false", "false", "1000", "Incomplete");
        assertThrows(IdNotExistsException.class, () -> {
           var result = todoManager.deleteItem(item.getId());
        });
    }

    @Test
    void completeItem() {
        var item = new TodoItem("Test", "TestOwner", "04 24 2020 12:00", "04 24 2020 23:59", "false", "false", "7", "Incomplete");
        var result = todoManager.addItemToDB(item);
        var completed = todoManager.completeItem(result.getId());
        assertEquals("true", completed.getCompleted());
    }

    @Test
    void snoozeItem() {
        var item = new TodoItem("Test", "TestOwner", "04 24 2020 12:00", "04 24 2020 12:00", "false", "false", "7", "Incomplete");
        var result = todoManager.addItemToDB(item);
        var snoozedItem = todoManager.snooze(result.getId());
        assertEquals("04 24 2020 12:15", snoozedItem.getDueDate());
    }

    @Test
    void setOverdue() {
        var item = new TodoItem("Test", "TestOwner", "04 24 2020 12:00", "04 24 2020 23:59", "false", "false", "7", "Incomplete");
        var result = todoManager.addItemToDB(item);
        var overdueItem = todoManager.setOverdue(result.getId());
        assertEquals("true", overdueItem.getOverdue());
    }


    @AfterEach
    void disposeDB() {
        todoManager.disposeResources();
        File dbFile = new File(Paths.get(".").normalize().toAbsolutePath() + "\\test.db");
        dbFile.delete();
    }
}