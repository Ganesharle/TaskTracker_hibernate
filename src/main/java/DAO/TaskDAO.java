package DAO;

import model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class TaskDAO {
    public void save(Task task) {
    	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving task: " + e.getMessage());
        }
    }
    public void update(Task task) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(task); // For existing tasks
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task: " + e.getMessage());
        }
    }

    public List<Task> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Task", Task.class).list();
        }
    }

    public Task get(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        }
    }

    public void delete(int id) {
    	 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             session.beginTransaction();
             Task task = session.get(Task.class, id);
             if (task != null) {
                 session.remove(task);
             }
             session.getTransaction().commit();
         } catch (Exception e) {
             throw new RuntimeException("Error deleting task: " + e.getMessage());
         }
    }
}
