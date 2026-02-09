output "api_gateway_url" {
  value = module.api.api_base_url
}

output "s3_website_endpoint" {
  value = module.frontend_s3.website_endpoint
}

