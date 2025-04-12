<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Task List</title>
</head>
<body>
    <h1>All Tasks</h1>
    <a href="add-task.jsp">Add New Task</a>
    <table border="1">
        <tr>
            <th>Title</th><th>Description</th><th>Status</th><th>Actions</th>
        </tr>
        <%
            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
            for (Task task : tasks) {
        %>
        <tr>
            <td><%= task.getTitle() %></td>
            <td><%= task.getDescription() %></td>
            <td><%= task.getStatus() %></td>
            <td>
                <form action="deleteTask" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= task.getId() %>">
                    <input type="submit" value="Delete">
                </form>
                <form action="edit-task.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= task.getId() %>">
                    <input type="submit" value="Edit">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
