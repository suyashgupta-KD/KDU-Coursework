resource "aws_dynamodb_table" "counter" {
  name         = var.name
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "counter_id"

  attribute {
    name = "counter_id"
    type = "S"
  }
}
