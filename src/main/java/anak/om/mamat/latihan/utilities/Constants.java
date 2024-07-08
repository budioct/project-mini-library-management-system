package anak.om.mamat.latihan.utilities;

public class Constants {

    // status code
    public static final Integer OK = 200;
    public static final Integer CREATED = 201;
    public static final Integer ACCEPTED = 202;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer PAYMENT_REQUIRED = 402;
    public static final Integer FORBIDDEN = 403;
    public static final Integer NOT_FOUND = 404;
    public static final Integer METHOD_NOT_ALLOWED = 405;
    public static final Integer INTERNAL_SERVER_ERROR = 500;
    public static final Integer NOT_IMPLEMENTED = 501;
    public static final Integer BAD_GATEWAY = 502;


    // boolean
    public static final Boolean TRUE =  true;
    public static final Boolean FALSE =  false;

    // message
    public static final String VALIDATION_MESSAGE = "Validation errors in your request";
    public static final String CREATE_MESSAGE = "The item was created successfully";
    public static final String UPDATE_MESSAGE = "The item was updated successfully";
    public static final String DELETE_MESSAGE = "The item was deleted successfully";
    public static final String ITEM_EXIST_MESSAGE = "The item exist";
    public static final String BAD_REQUEST_MESSAGE = "The request was not valid";
    public static final String REGISTER_MESSAGE = "Account has been registered";
    public static final String UPLOAD_ITEM_AVATAR = "Avatar successfully uploaded";
    public static final String AUTH_LOGIN_MESSAGE = "Successfully logged in";
    public static final String AUTH_LOGOUT_MESSAGE = "Successfully logged out";
    public static final String NOT_FOUND_MESSAGE = "The item does not exist";
    public static final String NOT_AUTH_MESSAGE = "Authentication credentials were missing or incorrect";
    public static final String FORBIDDEN_MESSAGE = "The request is understood, but it has been refused or access is not allowed";
    public static final String CONFLICT_MESSAGE = "Any message which should help the user to resolve the conflict";
    public static final String SERVER_ERROR_MESSAGE = "Something is broken";

}
