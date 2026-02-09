variable "region" {
  type    = string
  default = "ap-northeast-2"
}

variable "name_prefix" {
  type    = string
  default = "suyash-serverless-counter"
}

variable "frontend_bucket_name" {
  type = string
}
