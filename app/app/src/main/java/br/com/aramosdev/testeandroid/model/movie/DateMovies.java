package br.com.aramosdev.testeandroid.model.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class DateMovies implements Serializable{

    @SerializedName("maximum")
    private String maximum;
    @SerializedName("minimum")
    private String minimum;


    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }
}
