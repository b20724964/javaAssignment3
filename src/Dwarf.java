public class Dwarf extends Calliance{
    private int HP = 120;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 120;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 120) {
            this.HP =120;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.dwarfAP;
    }

    public int getMaxMove() {
        return Constants.dwarfMaxMove;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c,int[] moves, String lineFirstEle, Dwarf d){
        c.control=true;
        if (d.getName().equals(lineFirstEle) && d.getHP() > 0 && moves.length == Constants.dwarfMaxMove * 2) {
            int count=0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[d.getRow() + moves[i + 1]][d.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        d.setXY(moves[i], moves[i + 1]);
                        for (int row=-1;row<=1;row++) {
                            for (int column=-1;column<=1;column++) {
                                c.findZorde(d.getColumn() +column, d.getRow() +row).setHP(-d.getAP());
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[d.getRow() + moves[i + 1]][d.getColumn() + moves[i]].charAt(0) == 'T' || c.getBoard()[d.getRow() + moves[i + 1]][d.getColumn() + moves[i]].charAt(0) == 'G' || c.getBoard()[d.getRow() + moves[i + 1]][d.getColumn() + moves[i]].charAt(0) == 'O') {
                        c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).setHP(-d.getAP());
                        if (c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).getHP() < d.getHP()) {
                            d.setHP(-c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).getHP());
                            c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).setHP(-d.getHP());
                            d.setXY(moves[i], moves[i + 1]);
                        } else if (c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).getHP() > d.getHP()) {
                            c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).setHP(-d.getHP());
                            d.setHP(-d.getHP());
                        } else {
                            d.setHP(-d.getHP());
                            c.findZorde(d.getColumn() + moves[i], d.getRow() + moves[i + 1]).setHP(-d.getHP());
                        }
                        c.setBoard();
                        break;
                    } else {
                        break;
                    }
                } catch (Exception de) {
                    if(count>0){c.printBoard();}
                    c.writer.println("Error : Game board boundaries are exceeded. Input line ignored.\n");
                    c.control=false;
                    break;
                }
            }
        } else {
            try {
                c.control=false;
                throw new Exception("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
            } catch (Exception exception) {
                c.writer.println(exception.getMessage());
            }
        }
    }
}
