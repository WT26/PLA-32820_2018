package alanko.wt.firebase;

public class Struct {
    private long id;
    private String name;
    private String score;
    private String genre;

    public Struct (){

    }
    public Struct (long id, String name, String score, String genre){
        this.id = id;
        this.name = name;
        this.score = score;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Will be used by the ArrayAdapter in the ListView
    //@Override
    //public String toString() {
    //    return name;
    //}
}