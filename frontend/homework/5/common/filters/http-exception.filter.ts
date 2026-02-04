import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
  HttpStatus
} from '@nestjs/common';
import { Request, Response } from 'express';

type ErrorResponseBody = {
  message?: string | string[];
  error?: string;
  errors?: unknown;
};

@Catch()
export class HttpExceptionFilter implements ExceptionFilter {
  catch(exception: unknown, host: ArgumentsHost): void {
    const context = host.switchToHttp();
    const response = context.getResponse<Response>();
    const request = context.getRequest<Request>();

    const isHttpException = exception instanceof HttpException;
    const status = isHttpException ? exception.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    const timestamp = new Date().toISOString();

    let message = 'Internal server error';
    let errors: unknown;

    if (isHttpException) {
      const exceptionResponse = exception.getResponse() as ErrorResponseBody | string;
      if (typeof exceptionResponse === 'string') {
        message = exceptionResponse;
      } else if (exceptionResponse) {
        if (typeof exceptionResponse.message === 'string') {
          message = exceptionResponse.message;
        } else if (Array.isArray(exceptionResponse.message)) {
          message = 'Validation failed';
          errors = exceptionResponse.errors ?? exceptionResponse.message;
        }

        if (!errors && exceptionResponse.errors) {
          errors = exceptionResponse.errors;
        }
      }
    }

    response.status(status).json({
      statusCode: status,
      timestamp,
      path: request.url,
      message,
      errors
    });
  }
}
