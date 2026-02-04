import {
  Body,
  Controller,
  Get,
  Param,
  ParseUUIDPipe,
  Post,
} from '@nestjs/common';
import { CreateTodoDto } from './dto/create-todo.dto';
import { TodoResponseDto } from './dto/todo-response.dto';
import { TodosService } from './todos.service';

@Controller('v1/todos')
export class TodosController {
  constructor(private readonly todosService: TodosService) {}

  @Get()
  getTodos(): TodoResponseDto[] {
    return this.todosService.getAllTodos();
  }

  @Get(':id')
  getTodoById(
    @Param('id', new ParseUUIDPipe({ version: '4' })) id: string,
  ): TodoResponseDto {
    return this.todosService.getTodoById(id);
  }

  @Post()
  createTodo(@Body() payload: CreateTodoDto): TodoResponseDto {
    return this.todosService.createTodo(payload);
  }
}
