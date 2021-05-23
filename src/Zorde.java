public class Zorde {
    private String name="";
    private int HP=-1;
    private int AP=-1;
    private int maxMove=-1;
    private int column =-1;
    private int row =-1;

    public int getHP() {
        return HP;
    }

    //I created it to get full hp of characters.
    //I wrote such a method because it is not given in the constants class.
    public int getFullHP(){
        return HP;
    }

    //method used to change the character's hps
    //If the opposite character is enemy, we put a minus in front of the value.
    //This method is overridden on each character involved.
    public void setHP(int HP) {
    }

    public int getAP() {
        return AP;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getColumn(){
        return column;
    }

    public int getRow(){return row;}

    public void setXY(int column, int row){
        this.column +=column;
        this.row +=row;
    }

    public void setXYFirst(int column, int row){
        this.column =column;
        this.row =row;
    }
}
