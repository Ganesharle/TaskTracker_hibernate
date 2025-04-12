package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import DAO.TaskDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Task;

@WebServlet(urlPatterns = {"/task/*"})
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
    	taskDAO = new TaskDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
      
    	resp.setContentType("application/json");
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        System.out.println("GET request received for path: " + path);

        try {
            if (path.equals("/")) {
                System.out.println("Fetching all task");
                HashMap<String,Object> obj=new HashMap<>();
                obj.put("task",taskDAO.getAll());
                resp.getWriter().write(gson.toJson(obj));
            } else if (path.startsWith("/edit/")) {
                int id = Integer.parseInt(path.substring(6));
                System.out.println("Fetching task with ID: " + id);
                Task task = taskDAO.get(id);
                if (task != null) {
                    resp.getWriter().write(gson.toJson(task));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"task not found\"}");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Invalid endpoint\"}");
            }
        } catch (Exception e) {
            System.out.println("Error in doGet: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            resp.getWriter().write(gson.toJson(error));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String action = req.getParameter("action");
        System.out.println("POST request received with action: " + action);
        Map<String, String> response = new HashMap<>();

        try {
            if ("register".equals(action)) {
                Task tsk = new Task(
                	
                    req.getParameter("title"),
                    req.getParameter("description"),
                    req.getParameter("status"));
                
                taskDAO.save(tsk);
                response.put("message", "task registered successfully");
            } else if ("update".equals(action)) {
                String taskIdParam = req.getParameter("taskId");
                System.out.println("Received taskId: " + taskIdParam);

                if (taskIdParam != null && taskIdParam.matches("\\d+")) {
                    int taskId = Integer.parseInt(taskIdParam);
                    Task tsk = taskDAO.get(taskId);

                    if (tsk != null) {
                        tsk.setTitle(req.getParameter("title"));
                        tsk.setDescription(req.getParameter("description"));
                        tsk.setStatus(req.getParameter("status"));

                        taskDAO.update(tsk);
                        response.put("message", "task updated successfully");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.put("error", "task not found");
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.put("error", "Invalid or missing task ID");
                }
            

            } else if ("delete".equals(action)) {
                int taskId = Integer.parseInt(req.getParameter("taskId"));
                taskDAO.delete(taskId);
                response.put("message", "Task deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.put("error", "Invalid action");
            }
        } catch (Exception e) {
            System.out.println("Error in doPost: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.put("error", e.getMessage());
        }
        resp.getWriter().write(gson.toJson(response));
    }

    @Override
    public void destroy() {
        System.out.println("Servlet destroyed, SessionFactory closed");
    }
}