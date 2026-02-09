provider "aws" {
  region = var.region

  default_tags {
    tags = {
      "Created By" = "Suyash Gupta"
      "Purpose"    = "AWS hands on"
    }
  }
}

module "dynamodb" {
  source = "./modules/dynamodb"
  name   = "${var.name_prefix}-counter-table"
}

module "lambda" {
  source         = "./modules/lambda"
  name           = "${var.name_prefix}-counter-lambda"
  dynamodb_table = module.dynamodb.table_name
  dynamodb_arn   = module.dynamodb.table_arn
}

module "api" {
  source        = "./modules/api"
  name          = "${var.name_prefix}-counter-api"
  lambda_arn    = module.lambda.lambda_arn
  lambda_name   = module.lambda.lambda_name
}

module "frontend_s3" {
  source                 = "./modules/frontend_s3"
  bucket_name            = var.frontend_bucket_name
  api_base_url           = module.api.api_base_url
  index_html_source_path = "${path.module}/frontend/index.html"
}
