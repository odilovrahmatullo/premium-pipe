package premium_pipe.model.request;

public record RequestParams(Integer page,Integer size, String search) {
    public RequestParams(Integer page, Integer size, String search) {
        this.page =  (page !=null) ? page : 0;
        this.size = (size != null) ? size : 5;
        this.search = (search !=null) ? search : "";
    }
}
