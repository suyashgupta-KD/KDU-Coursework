import { employees } from "./employee.js";

// -------Task 1.2-------

let getEmployeeInfo = ({ name, department, salary }) =>
  `${name} works in ${department} and earns ${salary}`;

let addSkill = (employee, skill) =>
  (employee.skills = [...employee.skills, skill]);

// -------Task 1.3-------

function getInfo() {
  return console.log(
    `${this.name} is ${this.age}, works for ${this.department} department,has these skills - ${this.skills} with ${this.experience} years of experience and earns ${this.salary}`,
  );
}

employees.forEach((employee) => (employee.getFullInfo = getInfo));

let compareEmployees = (emp1, emp2) =>
  emp1.skills.length > emp2.skills.length ? emp1.name : emp2.name;
