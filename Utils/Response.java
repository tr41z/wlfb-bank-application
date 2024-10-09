package Utils;

enum ResponseCode {
    ACCOUNT_NOT_FOUND, 
    INTERNAL_SERVER_ERROR,
    OK,
}

class ResponseMessage {
    public ResponseCode code;
    public String message;
}


