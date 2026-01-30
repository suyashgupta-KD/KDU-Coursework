// ================================PART 1===================================

//Task 1.1

function Task(title, priority) {
  this.id = Date.now();
  this.title = title;
  this.priority = priority;
  this.completed = false;
}

Task.prototype.getInfo = function () {
  let completionInfo = completed ? "completed" : "not yet completed";
  return `The task ${this.id} : ${this.title} is of ${this.priority} and is ${completionInfo}`;
};

//Task 1.2

Task.prototype.markComplete = function () {
  this.completed = true;
  return Task;
};

Task.prototype.updatePriority = function (newPriority) {
  if (["low", "medium", "high"].includes(newPriority))
    this.priority = newPriority;
  return Task;
};

//Task 1.3

function PriorityTask(title, priority, dueDate) {
  Task.call(this, title, priority);
  this.dueDate = dueDate;
}

PriorityTask.prototype = Object.create(Task.prototype);

PriorityTask.prototype.constructor = PriorityTask;

PriorityTask.prototype.getDueInfo = function () {
  return `Task ${this.title} is due on ${this.dueDate}`;
};

//Task 1.4

Task.prototype.getAllTasksInfo = function (tasks) {
  tasks.forEach((task) => task.getInfo());
};

// ================================PART 2===================================
