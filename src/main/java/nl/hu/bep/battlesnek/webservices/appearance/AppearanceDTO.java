package nl.hu.bep.battlesnek.webservices.appearance;

public class AppearanceDTO {
    public String color;
    public String head;
    public String tail;

    public AppearanceDTO(String color, String head, String tail) {
        this.color = color;
        this.head = head;
        this.tail = tail;
    }

    public AppearanceDTO() {}
}
