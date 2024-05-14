package Petstore;

public class PetUpdate {
    private Integer id;
    private String name;
    private String status;

    public PetUpdate(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public PetUpdate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
