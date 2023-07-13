package resources.utility;

public class Arguments {
    private String data;
    private String author;

    public Arguments() {
        this.data = "";
        this.author = "unlnown";
    }
    public Arguments(String data, String author) {
        this.data = data;
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public String getAuthor() {
        return author;
    }
}
