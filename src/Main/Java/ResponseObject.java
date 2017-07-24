package GigsterPractical;

public class ResponseObject {

    private Integer responseCode;
    private String responseStatus;

    public ResponseObject(Integer responseCode, String responseStatus) {
        this.responseCode = responseCode;
        this.responseStatus = responseStatus;

    }

    public Integer getResponseCode() {
        return this.responseCode;
    }

    public String getResponseStatus() {
        return this.responseStatus;
    }

}