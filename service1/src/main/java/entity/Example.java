package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.cli.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String var1;
    private String var2;


    public Example(long id, String var1, String var2) {
        this.id = id;
        this.var1 = var1;
        this.var2 = var2;
    }

    public Example(String var2, String var1) {
        this.var2 = var2;
        this.var1 = var1;
    }

    public Example() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }
}
