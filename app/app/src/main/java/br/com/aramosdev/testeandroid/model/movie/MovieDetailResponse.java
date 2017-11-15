package br.com.aramosdev.testeandroid.model.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class MovieDetailResponse extends Movie implements Serializable {

    @SerializedName("genres")
    private List<MovieProperty> genres;
    @SerializedName("production_companies")
    private List<MovieProperty> productionCompanies;
    @SerializedName("revenue")
    private int revenue;

    public List<MovieProperty> getGenres() {
        return genres;
    }

    public void setGenres(List<MovieProperty> genres) {
        this.genres = genres;
    }

    public List<MovieProperty> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<MovieProperty> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
