import { employees } from "./employee.js";

// -----------------------------TASK 1-------------------------------

// ------- Task 1.2 -------

const getEmployeeInfo = ({ name, department, salary }) =>
  `${name} works in ${department} and earns ${salary}`;

const addSkill = (employee, skill) =>
  (employee.skills = [...employee.skills, skill]);

// ------- Task 1.3-------

function getInfo() {
  return console.log(
    `${this.name} is ${this.age}, works for ${this.department} department,has these skills - ${this.skills} with ${this.experience} years of experience and earns ${this.salary}`,
  );
}

// -----------------------------TASK 2-------------------------------

employees.forEach((employee) => (employee.getFullInfo = getInfo));

const compareEmployees = (emp1, emp2) =>
  emp1.skills.length > emp2.skills.length ? emp1.name : emp2.name;

// -------- Task 2.1 -------

console.log(employees);

// -------- Task 2.2 -------

const filterByExperience = (employees, minExperience) =>
  employees.filter((emp) => emp.experience >= minExperience);

// -------- Task 2.3 -------

const getSummary = (employees) =>
  employees.map(
    (employee) =>
      `${employee.name}, ${employee.department} , ${employee.salary}`,
  );

// -------- Task 2.4 -------

const getAverageSalary = (employees) => {
  let sum = employees.reduce((sum, current) => sum + current.salary, 0);
  return sum / employees.length;
};

const departmentWiseCount = employees.reduce((acc, curr) => {
  acc[curr.department] = (acc[curr.department] || 0) + 1;
  return acc;
}, {});

// -------- Task 2.5 -------
const maxSalary = Math.max(...employees.map((e) => e.salary));

const highestPaidEmployee = employees.find((e) => e.salary === maxSalary);

let employeesSortedByExperience = employees;
employeesSortedByExperience.sort(
  (emp1, emp2) => emp2.experience - emp1.experience,
);
