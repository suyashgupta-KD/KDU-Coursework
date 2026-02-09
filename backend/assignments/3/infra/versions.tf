terraform {
  required_version = ">= 1.5"

  backend "s3" {
    bucket         = "suyash-tf-state"    
    key            = "suyash/serverless-counter/terraform.tfstate"
    region         = "ap-northeast-2"
    dynamodb_table = "suyash-tf-lock"
    encrypt        = true
  }

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}
