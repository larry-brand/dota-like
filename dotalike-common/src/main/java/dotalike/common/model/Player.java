package dotalike.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import dotalike.common.model.external.PlayerGlobalDetails;

@Entity
public class Player {
    
    @Id
    private int steamId;
    
    private String name;
    
    @OneToOne
    private Rating rating;
    
    @OneToMany
    private List<Report> reports = new ArrayList<Report>();
    
    @Transient
    private PlayerGlobalDetails playerDetails;
    
    public Player() {
    }
    
    public Player(int steamId) {
	this.steamId = steamId;
    }

    public int getSteamId() {
        return steamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
    
    public PlayerGlobalDetails getPlayerDetails() {
        return playerDetails;
    }

    public void setPlayerDetails(PlayerGlobalDetails playerDetails) {
        this.playerDetails = playerDetails;
    }

    @Override
    public String toString() {
        return steamId + "";
    }
    
}