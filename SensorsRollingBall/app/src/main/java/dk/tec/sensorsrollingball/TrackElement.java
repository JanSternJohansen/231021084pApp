package dk.tec.sensorsrollingball;

public class TrackElement
{
    private boolean hole;
    private boolean horWall;
    private boolean verWall;

    public TrackElement(boolean hole, boolean horWall, boolean verWall) {
        this.hole = hole;
        this.horWall = horWall;
        this.verWall = verWall;
    }

    public boolean isHole() {
        return hole;
    }
    public void setHole(boolean isHole)
    {
        hole = isHole;
    }

    public boolean isHorWall() {
        return horWall;
    }
    public void setHorWall(boolean horWall) {
        this.horWall = horWall;
    }

    public boolean isVerWall() {
        return verWall;
    }
    public void setVerWall(boolean verWall) {
        this.verWall = verWall;
    }





}
