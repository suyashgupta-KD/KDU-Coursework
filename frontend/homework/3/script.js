// ================================PART 1===================================

//Task 1.1

function Task(title, priority) {
  this.id = Date.now();
  this.title = title;
  this.priority = priority;
  this.completed = false;
}

Task.prototype.getInfo = function () {
  let completionInfo = this.completed ? "completed" : "not yet completed";
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
  let info = Task.prototype.getInfo.call(this);

  return info + " and is due by " + this.dueDate;
};

//Task 1.4

Task.prototype.getAllTasksInfo = function (tasks) {
  tasks.forEach((task) => task.getInfo());
};

// ================================PART 2===================================

//Task 2.1

function createTaskAsync(title, priority) {
  console.log("Creating tasks...");

  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log("Task created!");
      resolve(new Task(title, priority));
    }, 1000);
  });
}

//Task 2.2
function demonstrateEventLoop() {
  setTimeout(() => console.log(1), 2000);
  setTimeout(() => console.log(2), 8000);
  setTimeout(() => console.log(3), 6000);
  setTimeout(() => console.log(4), 4000);
}

//Task 2.3

async function createAndSaveTask(title, priority) {
  try {
    const task = await createTaskAsync(title, priority);
    await createTaskAsync(`${title}--new`, priority);
    console.log("Task created and saved successfully!");
    return task;
  } catch (err) {
    throw err;
  }
}

//Task 2.4

function createMultipleTasksAsync(taskDataArray) {
  console.log(`Creating ${taskDataArray.length} tasks...`);

  const promises = taskDataArray.map((task) =>
    createTaskAsync(task.title, task.priority),
  );

  return Promise.all(promises).then((tasks) => {
    console.log("All tasks created!");
    return tasks;
  });
}
