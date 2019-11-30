package zy;

/**
 * Created by kaiser_zhao on 2018/7/28.
 */
public class XmlCase {
    private String tag;
    private String name;
    private String dir;

    public XmlCase(String tag, String name, String dir) {
        this.tag = tag;
        this.name = name;
        this.dir = dir;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }
}