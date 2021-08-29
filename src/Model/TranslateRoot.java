package Model;

public class TranslateRoot {

    private TranslateData data;

    public TranslateData getData() {
        return data;
    }

    public void setData(TranslateData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }
}