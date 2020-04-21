package utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import domain.TodoItem;
import exceptions.IdExistsException;
import exceptions.IdNotExistsException;
import exceptions.TodoException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatabaseUtils {

    Dao<TodoItem, String> todoDao;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");

    public DatabaseUtils() {
        this("todoItems.db");
    }

    public DatabaseUtils(String dbName) {
        try {
            var connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + dbName);
            TableUtils.createTableIfNotExists(connectionSource, TodoItem.class);
            this.todoDao = DaoManager.createDao(connectionSource, TodoItem.class);
        } catch (SQLException sqlException) {
            throw new TodoException("Couldn't connect", sqlException);
        }
    }

    public TodoItem addItemToDB(String title, String due) {
        return addItemToDB(new TodoItem(title, due));
    }

    public TodoItem addItemToDB(TodoItem item) {
        try {
            if (!todoDao.idExists(item.getId())) {
                todoDao.create(item);
                return todoDao.queryForId(item.getId());
            } else {
                throw new IdExistsException(item.getId());
            }
        } catch (SQLException e) {
            throw new TodoException("Couldn't add item to database!", e);
        }
    }

    public List<TodoItem> getAllItems() {
        try {
            List<TodoItem> allItems = todoDao.queryForAll();
            return allItems;
        } catch (SQLException e) {
            throw new TodoException("Couldn't retrieve all items!", e);
        }
    }

    public TodoItem getOneItem(String id) {
        try {
            TodoItem item = todoDao.queryForId(id);
            return item;
        } catch (SQLException sqlException) {
            throw new TodoException("Couldn't retrieve that item", sqlException);
        }
    }

    public void deleteItem(String id) {
        try {
            if (todoDao.idExists(id)) {
                var item = todoDao.queryForId(id);
                todoDao.deleteById(id);
            } else {
                throw new IdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new TodoException("Couldn't delete item!", e);
        }
    }

    public TodoItem completeItem(String id) {
        try {
            if (todoDao.idExists(id)) {
                var item = todoDao.queryForId(id);
                item.setCompleted();

                LocalDateTime now = LocalDateTime.now();
                String completedDate = now.format(formatter);

                todoDao.update(new TodoItem(item.getTitle(), item.getOwner(), item.getCreatedDate(), item.getDueDate(), item.getCompleted(), item.getOverdue(), item.getId(), completedDate));
                return todoDao.queryForId(id);
            } else {
                throw new IdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new TodoException("Couldn't complete item.", e);
        }
    }

    public TodoItem snooze(String id) {
        try {
            if (todoDao.idExists(id)) {
                var item = todoDao.queryForId(id);
                item.snooze(item);
                todoDao.update(new TodoItem(item.getTitle(), item.getOwner(), item.getCreatedDate(), item.getDueDate(), item.getCompleted(), item.getOverdue(), id, item.getCompletedDate()));
                return todoDao.queryForId(id);
            } else {
                throw new IdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new TodoException("Couldn't snooze item", e);
        }
    }

    public TodoItem setOverdue(String id) {
        try {
            if (todoDao.idExists(id)) {
                var item = todoDao.queryForId(id);
                item.setOverdue();
                todoDao.update(new TodoItem(item.getTitle(), item.getOwner(), item.getCreatedDate(), item.getDueDate(), item.getCompleted(), item.getOverdue(), id, item.getCompletedDate()));
                return todoDao.queryForId(id);
            } else {
                throw new IdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new TodoException("Couldn't set item as overdue", e);
        }
    }

    public void disposeResources() {
        try {
            todoDao.getConnectionSource().close();
        } catch (IOException e) {
            throw new TodoException("Couldn't close source", e);
        }
    }

    public int findHighestID() {
        try {
            List<TodoItem> allItems = todoDao.queryForAll();
            int highest = 0;

            for (TodoItem i : allItems) {
                int tempID = Integer.parseInt(i.getId());
                if (tempID > highest) {
                    highest = tempID;
                }
            }

            return highest;

        } catch (SQLException e) {
            throw new TodoException("Couldn't calculate highest ID", e);
        }
    }
}