import os
import json
import boto3

ddb = boto3.client("dynamodb")
TABLE = os.environ["TABLE_NAME"]
COUNTER_ID = "main"

def response(code, body):
    return {
        "statusCode": code,
        "headers": {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        "body": json.dumps(body),
    }

def get_counter():
    res = ddb.get_item(
        TableName=TABLE,
        Key={"counter_id": {"S": COUNTER_ID}},
        ConsistentRead=True
    )
    if "Item" not in res:
        return 0
    return int(res["Item"]["value"]["N"])

def inc_counter():
    res = ddb.update_item(
        TableName=TABLE,
        Key={"counter_id": {"S": COUNTER_ID}},
        UpdateExpression="ADD #v :inc",
        ExpressionAttributeNames={"#v": "value"},
        ExpressionAttributeValues={":inc": {"N": "1"}},
        ReturnValues="UPDATED_NEW",
    )
    return int(res["Attributes"]["value"]["N"])

def lambda_handler(event, context):
    method = event.get("requestContext", {}).get("http", {}).get("method", "")

    if method == "GET":
        value = get_counter()
        return response(200, {"value": value})

    if method == "PUT":
        value = inc_counter()
        return response(200, {"value": value})

    return response(405, {"message": "Method Not Allowed"})
