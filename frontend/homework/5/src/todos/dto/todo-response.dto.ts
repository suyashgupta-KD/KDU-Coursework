export class TodoResponseDto {
  id!: string;
  title!: string;
  description?: string;
  completed!: boolean;
  dueDate?: string;
  priority?: 'low' | 'medium' | 'high';
  createdAt!: string;
  updatedAt!: string;
}
