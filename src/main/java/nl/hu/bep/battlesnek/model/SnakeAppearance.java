package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnakeAppearance {
    private String apiversion;
    private String author;
    private String color;
    private String head;
    private String tail;
    private String version;

    public SnakeAppearance() {
        // Default waarden (optioneel)
        this.apiversion = "1";
        this.author = "turbootzz";
        this.color = "#00FF00";
        this.head = "default";
        this.tail = "default";
        this.version = "1.0";
    }

    public String getApiversion() { return apiversion; }
    public void setApiversion(String apiversion) { this.apiversion = apiversion; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getHead() { return head; }
    public void setHead(String head) { this.head = head; }

    public String getTail() { return tail; }
    public void setTail(String tail) { this.tail = tail; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
