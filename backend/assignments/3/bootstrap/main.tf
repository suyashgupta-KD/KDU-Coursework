provider "aws" {
  region = "ap-northeast-2"

  default_tags {
    tags = {
      "Created By" = "Suyash Gupta"
      "Purpose"    = "AWS hands on"
    }
  }
}

resource "aws_s3_bucket" "tf_state" {
  bucket = "suyash-tf-state"
}

resource "aws_s3_bucket_versioning" "tf_state" {
  bucket = aws_s3_bucket.tf_state.id
  versioning_configuration { status = "Enabled" }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "tf_state" {
  bucket = aws_s3_bucket.tf_state.id
  rule {
    apply_server_side_encryption_by_default { sse_algorithm = "AES256" }
  }
}

resource "aws_dynamodb_table" "tf_lock" {
  name         = "suyash-tf-lock"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }
}
