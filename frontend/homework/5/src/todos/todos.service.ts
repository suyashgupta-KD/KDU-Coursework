import { Injectable, NotFoundException } from '@nestjs/common';
import { randomUUID } from 'crypto';
import { CreateTodoDto } from './dto/create-todo.dto';
import { TodoResponseDto } from './dto/todo-response.dto';
import { Todo } from './interfaces/todos.interface';

@Injectable()
export class TodosService {
  private readonly todos: Todo[] = [];

  getAllTodos(): TodoResponseDto[] {
    return this.todos.map((todo) => ({ ...todo }));
  }

  getTodoById(id: string): TodoResponseDto {
    const todo = this.todos.find((item) => item.id === id);
    if (!todo) {
      throw new NotFoundException(`Todo with id "${id}" not found.`);
    }

    return { ...todo };
  }

  createTodo(payload: CreateTodoDto): TodoResponseDto {
    const now = new Date().toISOString();
    const todo: Todo = {
      id: randomUUID(),
      title: payload.title.trim(),
      description: payload.description?.trim() || undefined,
      completed: false,
      dueDate: payload.dueDate,
      priority: payload.priority,
      createdAt: now,
      updatedAt: now,
    };

    this.todos.push(todo);
    return { ...todo };
  }
}
