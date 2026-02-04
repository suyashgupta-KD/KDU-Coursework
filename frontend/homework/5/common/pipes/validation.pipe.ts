import { BadRequestException, Injectable, ValidationPipe } from '@nestjs/common';
import { ValidationError } from 'class-validator';

@Injectable()
export class AppValidationPipe extends ValidationPipe {
  constructor() {
    super({
      transform: true,
      whitelist: true,
      forbidNonWhitelisted: true,
      transformOptions: { enableImplicitConversion: true },
      exceptionFactory: (errors: ValidationError[]) => {
        const formatted = errors.map((error) => ({
          field: error.property,
          errors: Object.values(error.constraints ?? {})
        }));

        return new BadRequestException({
          message: 'Validation failed',
          errors: formatted
        });
      }
    });
  }
}
