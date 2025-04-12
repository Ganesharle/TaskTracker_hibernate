<%@ page import="DAO.TaskDAO" %>
<%@ page import="model.Task" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Task task = new TaskDAO().get(id);
%>
<html>
<head>
    <title>Edit Task</title>
</head>
<body>
    <h1>Edit Task</h1>
    <form action="editTask" method="post">
        <input type="hidden" name="id" value="<%= task.getId() %>">
        Title: <input type="text" name="title" value="<%= task.getTitle() %>"><br><br>
        Description: <textarea name="description"><%= task.getDescription() %></textarea><br><br>
        Status:
        <select name="status">
            <option <%= task.getStatus().equals("Pending") ? "selected" : "" %>>Pending</option>
            <option <%= task.getStatus().equals("In Progress") ? "selected" : "" %>>In Progress</option>
            <option <%= task.getStatus().equals("Done") ? "selected" : "" %>>Done</option>
        </select><br><br>
        <input type="submit" value="Update Task">
    </form>
    <a href="listTasks">Back to Task List</a>
</body>
</html>
