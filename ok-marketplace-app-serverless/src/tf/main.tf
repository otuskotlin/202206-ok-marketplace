terraform {
  required_providers {
    yandex = {
      source = "yandex-cloud/yandex"
    }
  }
  required_version = ">= 0.13"
}
locals {
  bucket             = "jar"
  version            = "1"
  jar_name           = "ok-marketplace/marketplace-serverless-${local.version}.jar"

  // TODO add params
  service_account_id = ""
  //Secrets
  token      = ""
  folder_id  = ""
  access_key = ""
  secret_key = ""
}

provider "yandex" {
  token     = local.token
  folder_id = local.folder_id
  zone      = "ru-central1-a"
}


resource "yandex_storage_object" "marketplace-serverless-jar" {
  access_key = local.access_key
  secret_key = local.secret_key
  bucket     = local.bucket
  key        = local.jar_name
  source     = "../../../../build/libs/ok-marketplace-app-serverless-0.0.1-SNAPSHOT-all.jar"
}

resource "yandex_function" "marketplace-serverless" {
  name               = "marketplace-serverless"
  description        = "marketplace serverless"
  user_hash          = "marketplace-serverless-${local.version}"
  runtime            = "java11"
  entrypoint         = "ru.otus.otuskotlin.marketplace.serverlessapp.api.RoutingHandler"
  memory             = "128"
  execution_timeout  = "10"
  service_account_id = local.service_account_id
  package {
    bucket_name = local.bucket
    object_name = local.jar_name
  }
}
