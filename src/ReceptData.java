import java.io.Serializable;

public class ReceptData implements Serializable {

    private String name;
    private Boolean[] flags;
    private String text;

    public String getName(){
        return name;
    }

    public Boolean[] getFlags(){
        return flags;
    }

    public String getText(){
        return text;
    }

    public void setName(String str){
        name = str;
    }

    public void setFlags(Boolean[] array){
        flags = array;
    }

    public void setText(String str){
        text = str;
    }

    public ReceptData(String n, Boolean[] f, String t ){
        this.name = n;
        this.flags = f;
        this.text = t;
    }
}
