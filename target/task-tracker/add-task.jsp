<html>
<head>
    <title>Add Task</title>
</head>
<body>
    <h1>Add New Task</h1>
    <form action="addTask" method="post">
        Title: <input type="text" name="title"><br><br>
        Description: <textarea name="description"></textarea><br><br>
        Status:
        <select name="status">
            <option>Pending</option>
            <option>In Progress</option>
            <option>Done</option>
        </select><br><br>
        <input type="submit" value="Add Task">
    </form>
    <a href="listTasks">Back to Task List</a>
</body>
</html>
